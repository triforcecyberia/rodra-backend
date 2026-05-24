package com.rodra.bot.service;

import com.rodra.bot.dto.LeadRequest;
import com.rodra.bot.telegram.RodraBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeadService {

    private final RodraBot rodraBot;

    public void processLead(LeadRequest request) {
        log.info("Новая заявка: {} / {}", request.getName(), request.getPhone());
        rodraBot.sendLead(
                request.getName(),
                request.getPhone(),
                request.getService(),
                request.getComment()
        );
    }
}
