package com.example.Orchestrator.Config;

import com.example.Orchestrator.Advisor.CustomChatMemory;
import com.example.Orchestrator.Tools.ChatTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatClientConfig {

    //    @Bean("gemini")
//    public ChatClient geminiChatClient(
//            @Qualifier("gemini-cli") ChatClient.Builder builder,
//            ChatMemory chatMemory, CustomAdv customAdv
//    ) {
//
//        return builder
//                .defaultAdvisors(
//                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
//                        customAdv
//                )
//                .build();
//    }
//
//    @Bean("openai")
//    public ChatClient openaiChatClient(
//            @Qualifier("openai-cli") ChatClient.Builder builder,
//            ChatMemory chatMemory, CustomAdv customAdv
//    ) {
//        log.error("REGISTERING CustomAdv with ChatClient");
//
//        return builder
//                .defaultAdvisors(
//                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
//                        customAdv
//                )
//                .build();
//    }
    @Bean("openai")
    public ChatClient openaiChatClient(
            @Qualifier("openai-cli") ChatClient.Builder builder,
            CustomChatMemory customAdv, ChatTools chatTools) {
        return builder
                .defaultAdvisors(customAdv)
//                .defaultTools(chatTools)
                .build();
    }
}
