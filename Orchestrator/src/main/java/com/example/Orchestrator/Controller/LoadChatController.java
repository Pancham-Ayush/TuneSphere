package com.example.Orchestrator.Controller;

import com.example.Orchestrator.Model.SpringAiChatMemory;
import com.example.Orchestrator.Model.UserChatHistory;
import com.example.Orchestrator.Repo.ChatMemoryRepo;
import com.example.Orchestrator.Repo.UserChatHistoryRepo;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/load")
public class LoadChatController {

    private final ChatMemoryRepo springAiChatMemoryRepo;


    private final UserChatHistoryRepo userChatHistoryRepo;


    public LoadChatController(ChatMemoryRepo springAiChatMemoryRepo, UserChatHistoryRepo userChatHistoryRepo) {
        this.springAiChatMemoryRepo = springAiChatMemoryRepo;
        this.userChatHistoryRepo = userChatHistoryRepo;
    }

    @GetMapping()
    public Flux<SpringAiChatMemory> getUserChatHistoriesByChatId(
            @RequestParam String chatId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        long offset = (long) page * size;
        return springAiChatMemoryRepo.findByConversationIdPaged(chatId, size, offset).log();
    }


    @GetMapping("/all")
    public Flux<UserChatHistory> getAll(@RequestHeader("X-User-Email") String userId) {
        return userChatHistoryRepo.findUserChatHistoriesByUserId(userId);
    }
}
