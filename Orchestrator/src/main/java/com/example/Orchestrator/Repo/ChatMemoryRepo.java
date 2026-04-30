package com.example.Orchestrator.Repo;

import com.example.Orchestrator.Model.SpringAiChatMemory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatMemoryRepo extends ReactiveCrudRepository<SpringAiChatMemory, Long> {

    @Query("""
               SELECT content, type 
               FROM SPRING_AI_CHAT_MEMORY 
               WHERE conversation_id = :conversationId 
               ORDER BY `timestamp`
               LIMIT :limit OFFSET :offset
            """)
    Flux<SpringAiChatMemory> findByConversationIdPaged(
            String conversationId,
            int limit,
            long offset
    );

}
