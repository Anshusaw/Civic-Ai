package com.incapp.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIService {
	private final ChatClient chatClient;

    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public AIResponse classifyComplaint(String complaintText) {

        // Step 1: Build Prompt
        String prompt = """
        	    You are a strict AI classifier.

        	    Classify the complaint into JSON ONLY.

        	    Fields:
        	    - title 
        	    - category
        	    - priority (Low, Medium, High, Critical)
        	    - department

        	    Departments:
        	    Sanitation, Road Maintenance, Electricity, Water Department, Traffic

        	    Complaint:
        	    """+complaintText+"""

        	    Return ONLY JSON. No explanation.
        	    """;

        // Step 2: Call AI (Spring AI)
        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        
        System.out.println(response);

        return AIResponseParser.parse(response);
    }
}