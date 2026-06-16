package com.example.Orchestrator.Config;

import com.example.Orchestrator.OrchestratorApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = OrchestratorApplication.class)
class ChatClientBuilderConfigTest {

    @Autowired
    ChatClient chatClient;

    @Test
    public void testChatClientBuilderConfig() {

        log.info(chatClient
                .prompt()
                .user("what is apendix")
                .system("no emoji in chat msg ")
                .call()
                .content()
                .toString());
    }
}