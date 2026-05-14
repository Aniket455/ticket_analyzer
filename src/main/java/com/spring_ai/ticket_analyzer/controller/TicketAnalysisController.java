package com.spring_ai.ticket_analyzer.controller;

import com.spring_ai.ticket_analyzer.model.Priority;
import com.spring_ai.ticket_analyzer.model.TicketAnalysis;
import com.spring_ai.ticket_analyzer.service.TicketAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketAnalysisController {
    //support team
    // 100s of ticket
    //setting priority
    // routing the ticket to appropriate team

    // send ticket to ai/llm
    // we will be using prompt template
    // we will be having small ui screen
    // json format
    // ai to convert the response to object /model/entity


    @Autowired
    private TicketAnalysisService ticketAnalysisService;

    @PostMapping("/analyze")
    public Map<String, Object> reviewCode(
            @RequestBody Map<String, String> request,
            @RequestParam(defaultValue = "openai") String provider,
            @RequestParam(defaultValue = "openai/gpt-oss-20b") String model) {
        String ticketText = request.get("ticketText");
        TicketAnalysis ticketAnalysis = ticketAnalysisService.analysisTicket(ticketText, provider, model);

        System.out.println(ticketAnalysis.getPriority());

        if (ticketAnalysis.getPriority().equals(Priority.HIGH)){

        }

        return Map.of("success", true, "ticketAnalysis", ticketAnalysis,
                "priorityColor",ticketAnalysisService.getPriorityColor(ticketAnalysis.getPriority()),
                "sentimentEmoji",ticketAnalysisService.getSentimentEmoji(ticketAnalysis.getSentiment()));
    }
}