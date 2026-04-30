package com.example.Orchestrator;

import com.example.Orchestrator.Tools.ChatTools;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorApplication.class, args);
	}


    @Bean
    List<ToolCallback> toolCallbacks(ChatTools tools) {
        return Arrays.asList(ToolCallbacks.from(tools));
    }
    }
