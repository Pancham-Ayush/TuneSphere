package com.example.Orchestrator.Service;

import com.example.Orchestrator.Tools.ChatTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ChatClientService {

    ChatClient chatClient;

    ChatMemory chatMemory;

    ChatTools chatTools;


    public ChatClientService(@Qualifier("openai") ChatClient chatClient, ChatMemory chatMemory, ChatTools chatTools) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
        this.chatTools = chatTools;
    }

    public Flux<String> aiResponse(String ques, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        return chatClient
                .prompt()
                .user(ques)
                .tools(chatTools)
                .system("""
                        You MUST call the appropriate tool when the user asks
                        about current date or time. Do not answer from your own knowledge.
                        """)
                .advisors(a -> a.param("chatId", id))
                .stream()
                .content()
                .doOnNext(stringBuilder::append)
                .contextWrite(ctx -> ctx.put("chatId", id));

    }

}
