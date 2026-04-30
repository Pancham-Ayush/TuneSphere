package com.example.Orchestrator.Tools;

import com.example.Orchestrator.Repo.ChatMemoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatTools {


    @Tool(
            name = "get_weather_tanjavur",
            description = "Returns current weather and season in Thanjavur, India"
    )
    public String getWeatherTanjavur() {
        log.debug("Tool getWeatherTanjavur invoked");
        return "It is night 2 AM in Thanjavur and very cold its snowing heavily.";
    }
    @Tool(
            name = "get_user_info",
            description = "Returns user info"
    )
    public String getInfo(@ToolParam String name) {
        String sex =  (name.length()%2==0)?"male":"female";
        log.debug("Tool get info invoked");
        return "name "+ name+" age 24 sex "+sex+" height 6.11";
    }
}
