package com.example.Orchestrator.Controller;

import com.example.Orchestrator.Config.ChatClientConfig;
import com.example.Orchestrator.Repo.ChatMemoryRepo;
import com.example.Orchestrator.Repo.UserChatHistoryRepo;
import com.example.Orchestrator.Service.ChatClientService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ChatClientService chatClientService;

    @Autowired
    ChatMemoryRepo chatMemoryRepository;

    @Autowired
    @Qualifier("openai")
    ChatClient chatClient;

    @Autowired
    ChatClientConfig chatClientConfig;
    @Autowired
    UserChatHistoryRepo chatHistoryRepository;


}
