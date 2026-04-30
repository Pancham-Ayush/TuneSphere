package com.example.Orchestrator.Advisor;

import com.example.Orchestrator.Repo.ChatMemoryRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/*
Use :
        Pass this Custom advisor to the ChatClient builder default advisor
        then while calling the client pass the chatId parm as "chatId"
 */
@Slf4j
@Service
public class CustomChatMemory implements StreamAdvisor {

    private final ChatMemoryRepo chatMemoryRepo;

    public CustomChatMemory(ChatMemoryRepo chatMemoryRepo) {
        this.chatMemoryRepo = chatMemoryRepo;
    }

    @PostConstruct
    public void init() {
        log.info(">>> CustomAdv bean created <<< :)");
    }

    @Override
    public String getName() {
        return "custom-chat-memory-advisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {

        Object chatIdObj = request.context().get("chatId");

        if (chatIdObj == null) {
            log.warn("chat_id not found in advisor context");
            return chain.nextStream(request);
        }

        String chatId = chatIdObj.toString();

        return chatMemoryRepo.findByConversationIdPaged(chatId, 10, 0)
                .collectList()
                .flatMapMany(history -> {

                    StringBuilder historyBlock = new StringBuilder();
                    history.forEach(h -> {
                        historyBlock.append(h.getType())
                                .append(": ")
                                .append(h.getContent())
                                .append("\n");
                    });

                    // Prepend memory to existing prompt
                    String newPromptText =
                            "Conversation so far:\n" +
                                    historyBlock +
                                    "\nUser: " + request.prompt().toString();

                    ChatClientRequest mutated = request.mutate()
                            .prompt(new Prompt(newPromptText))
                            .build();

                    return chain.nextStream(mutated);
                })
                .doOnError(err -> log.error("error", err))
                .log();
    }

}
