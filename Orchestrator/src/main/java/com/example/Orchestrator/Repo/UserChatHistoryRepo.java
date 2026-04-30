package com.example.Orchestrator.Repo;

import com.example.Orchestrator.Model.UserChatHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserChatHistoryRepo extends ReactiveCrudRepository<UserChatHistory, Long> {

    Mono<UserChatHistory> findUserChatHistoriesByChatId(String chatId);

    Mono<UserChatHistory> deleteByChatId(String chatId);

    Mono<UserChatHistory> findByUserIdAndChatId(String userId, String chatId);

    Flux<UserChatHistory> findByUserId(String userId);

    Flux<UserChatHistory> findUserChatHistoriesByUserId(String userId);
}
