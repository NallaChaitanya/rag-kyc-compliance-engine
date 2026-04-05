package com.chaitanya.rag_kyc_compliance;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rag/compliance")
public class ComplianceChatController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public ComplianceChatController(
            ChatClient.Builder builder,
            VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    @PostMapping("/query")
    public Map<String, Object> queryCompliance(
            @RequestBody String question) {

        // Step 1 → Search relevant policies
        List<Document> relevantPolicies = vectorStore
            .similaritySearch(
                SearchRequest
                    .query(question)
                    .withTopK(3));

        // Step 2 → Extract policies
        String policies = relevantPolicies.stream()
            .map(Document::getContent)
            .collect(Collectors.joining("\n\n"));

        // Step 3 → AI answer generate
        String answer = chatClient.prompt()
            .user("""
                You are a Senior Banking Compliance
                Expert at a major US bank.
                
                A bank employee has asked a question.
                Answer using ONLY the bank policies
                provided below.
                
                If answer not found in policies:
                "Please escalate to compliance team
                 for guidance on this matter."
                
                Bank Compliance Policies:
                """ + policies + """
                
                Employee Question: """ + question + """
                
                Respond in this format:
                
                POLICY_REFERENCE: [policy number]
                
                ANSWER: [clear detailed answer]
                
                ACTION_REQUIRED: [specific steps
                employee must take]
                
                ESCALATE_TO: [which team if needed]
                """)
            .call()
            .content();

        // Step 4 → Build response
        Map<String, Object> response = new HashMap<>();
        response.put("question", question);
        response.put("answer", answer);
        response.put("timestamp",
            java.time.LocalDateTime.now().toString());
        response.put("queried_by", "bank_employee");

        return response;
    }
}