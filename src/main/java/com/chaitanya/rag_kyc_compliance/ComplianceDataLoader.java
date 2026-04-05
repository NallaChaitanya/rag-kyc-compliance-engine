package com.chaitanya.rag_kyc_compliance;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ComplianceDataLoader {

    @Value("classpath:compliance-rules.txt")
    private Resource rulesFile;

    @Bean
    public VectorStore vectorStore(
            EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }

    @Bean
    public CommandLineRunner loadComplianceRules(
            VectorStore vectorStore) {
        return args -> {
            String content = rulesFile
                .getContentAsString(StandardCharsets.UTF_8);

            List<Document> documents = Arrays
                .stream(content.split("\n\n"))
                .filter(rule -> !rule.trim().isEmpty())
                .map(rule -> new Document(rule.trim()))
                .collect(Collectors.toList());

            vectorStore.add(documents);
            System.out.println(
                "✅ Loaded " + documents.size() +
                " compliance rules into vector store");
        };
    }
}