package com.incapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AIResponseParser {
	public static AIResponse parse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, AIResponse.class);
        } catch (Exception e) {

            // fallback if AI fails
            return new AIResponse("Title","General", "Medium", "General");
        }
    }
}
