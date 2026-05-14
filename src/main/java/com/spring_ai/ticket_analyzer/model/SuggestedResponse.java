package com.spring_ai.ticket_analyzer.model;

public class SuggestedResponse {


    private String tone;
    private String responseText;


    private int estimatedReadingTime;


    public SuggestedResponse() {
    }
    public SuggestedResponse(String tone, String responseText, int estimatedReadingTime) {
        this.tone = tone;
        this.responseText = responseText;
        this.estimatedReadingTime = estimatedReadingTime;
    }

    // ==================== GETTERS AND SETTERS ====================
    // Required for JSON serialization/deserialization

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public int getEstimatedReadingTime() {
        return estimatedReadingTime;
    }

    public void setEstimatedReadingTime(int estimatedReadingTime) {
        this.estimatedReadingTime = estimatedReadingTime;
    }
}