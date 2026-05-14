package com.spring_ai.ticket_analyzer.controller;

import com.spring_ai.ticket_analyzer.model.Priority;
import com.spring_ai.ticket_analyzer.model.Sentiment;
import com.spring_ai.ticket_analyzer.model.SuggestedResponse;
import com.spring_ai.ticket_analyzer.model.TicketAnalysis;
import com.spring_ai.ticket_analyzer.service.TicketAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

        // if ticket priority is critical and user sentiment is angry
        // then i want to take help of AI LLM to give three responses
        //these three responses will be shown to user on the ui
        //so that the manager/user can copy the response
        // send it to user immediately

        if (ticketAnalysis.getPriority().equals(Priority.CRITICAL)
                && ticketAnalysis.getSentiment().equals(Sentiment.ANGRY)){
            List<SuggestedResponse> responses = ticketAnalysisService.generateUrgentResponse(ticketAnalysis, provider,model);
            return Map.of("success", true, "ticketAnalysis", ticketAnalysis,
                    "responses", responses,
                    "priorityColor",ticketAnalysisService.getPriorityColor(ticketAnalysis.getPriority()),
                    "sentimentEmoji",ticketAnalysisService.getSentimentEmoji(ticketAnalysis.getSentiment()));
        }

        return Map.of("success", true, "ticketAnalysis", ticketAnalysis,
                "priorityColor",ticketAnalysisService.getPriorityColor(ticketAnalysis.getPriority()),
                "sentimentEmoji",ticketAnalysisService.getSentimentEmoji(ticketAnalysis.getSentiment()));
    }
}