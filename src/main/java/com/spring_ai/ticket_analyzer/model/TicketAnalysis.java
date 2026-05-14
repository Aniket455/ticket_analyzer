package com.spring_ai.ticket_analyzer.model;

public class TicketAnalysis {

    private String category;
    private String subCategory;
    private Priority priority;
    private Sentiment sentiment;
    private String suggestedTeam;
    private int estimatedResolutionMinutes;
    private String summary;
    private String keyIssues;


    public TicketAnalysis() {
    }

    public TicketAnalysis(String category, String subCategory, Priority priority, Sentiment sentiment, String suggestedTeam, int estimatedResolutionMinutes, String summary, String keyIssues) {
        this.category = category;
        this.subCategory = subCategory;
        this.priority = priority;
        this.sentiment = sentiment;
        this.suggestedTeam = suggestedTeam;
        this.estimatedResolutionMinutes = estimatedResolutionMinutes;
        this.summary = summary;
        this.keyIssues = keyIssues;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getSuggestedTeam() {
        return suggestedTeam;
    }

    public void setSuggestedTeam(String suggestedTeam) {
        this.suggestedTeam = suggestedTeam;
    }

    public int getEstimatedResolutionMinutes() {
        return estimatedResolutionMinutes;
    }

    public void setEstimatedResolutionMinutes(int estimatedResolutionMinutes) {
        this.estimatedResolutionMinutes = estimatedResolutionMinutes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyIssues() {
        return keyIssues;
    }

    public void setKeyIssues(String keyIssues) {
        this.keyIssues = keyIssues;
    }
}
