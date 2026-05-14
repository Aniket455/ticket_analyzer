package com.spring_ai.ticket_analyzer.service;

import com.spring_ai.ticket_analyzer.model.Priority;
import com.spring_ai.ticket_analyzer.model.Sentiment;
import com.spring_ai.ticket_analyzer.model.SuggestedResponse;
import com.spring_ai.ticket_analyzer.model.TicketAnalysis;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class TicketAnalysisService {

    private final ModelService modelService;
    private final ResourceLoader resourceLoader;

    public TicketAnalysisService(ModelService modelService, ResourceLoader resourceLoader) {
        this.modelService = modelService;
        this.resourceLoader = resourceLoader;
    }

    public TicketAnalysis analysisTicket(String ticketText,
                                         String provider,
                                         String model) {
        ChatClient chatClient = modelService.getChatClient(provider);

        Prompt prompt = createCodeReviewPrompt(ticketText);

        return chatClient.prompt(prompt).options(OpenAiChatOptions.builder().model(model).build()).call().entity(TicketAnalysis.class);

    }

    public Prompt createCodeReviewPrompt(String ticketText) {
        // load template from resources folder
        String templateContent = loadTemplate("classpath:templates/ticket-analysis.txt");
        // create prompt template from template content
        PromptTemplate promptTemplate = new PromptTemplate(templateContent);
        // create variables map with ticket text variable
        Map<String, Object> variables = Map.of( "ticketText", ticketText );
        // create prompt from prompt template and variables
        return promptTemplate.create(variables);
    }


    public List<SuggestedResponse> generateUrgentResponse(TicketAnalysis ticketAnalysis,
                                                          String provider,
                                                          String model) {
        ChatClient chatClient = modelService.getChatClient(provider);

        Prompt prompt = createTicketAnalysisResponsePrompt(ticketAnalysis);

        return chatClient.prompt(prompt).options(OpenAiChatOptions.builder().model(model).build()).call().entity(new ParameterizedTypeReference<List<SuggestedResponse>>() {
        });
    }

    public Prompt createTicketAnalysisResponsePrompt(TicketAnalysis ticketAnalysis) {
        // load template from resources folder
        String templateContent = loadTemplate("classpath:templates/ticket-analysis-responses.txt");
        // create prompt template from template content
        PromptTemplate promptTemplate = new PromptTemplate(templateContent);
        // create variables map with ticket text variable
        Map<String, Object> variables = Map.of( "category", ticketAnalysis.getCategory(),
                "issues", ticketAnalysis.getKeyIssues());
        // create prompt from prompt template and variables
        return promptTemplate.create(variables);
    }

    private String loadTemplate(String location) {
        try {
            Resource resource = resourceLoader.getResource(
                    location
            );
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ticket analysis template", e);
        }
    }

    public String getPriorityColor(Priority priority) {
        return switch (priority) {
            case CRITICAL -> "#dc3545"; // Red
            case HIGH -> "#fd7e14";     // Orange
            case MEDIUM -> "#ffc107";   // Yellow
            case LOW -> "#28a745";      // Green
        };
    }

    public String getSentimentEmoji(Sentiment sentiment) {
        return switch (sentiment) {
            case HAPPY -> "😊";
            case NEUTRAL -> "😐";
            case FRUSTRATED -> "😤";
            case ANGRY -> "😡";
        };
    }
}
