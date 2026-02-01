package io.quarkiverse.flow.casehub;

import jakarta.enterprise.context.ApplicationScoped;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
@ApplicationScoped
@SystemMessage("You draft a short, friendly newsletter paragraph. Return ONLY the final draft text.")
public interface DrafterAgent {
    @UserMessage("Brief:{{brief}}")
    String draft(@MemoryId String memoryId, @V("brief") String brief);
}