package vn.rikkei.exam.equipmentloan.service.rag;

import vn.rikkei.exam.equipmentloan.dto.RagResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    /**
     * Nạp tài liệu vào VectorStore
     * Pipeline: Load (TextReader) -> Split (TokenTextSplitter) -> Store (vectorStore.add)
     */
    public int ingestDocument(String fileName) {
        // TODO: Viết logic nạp tài liệu từ classpath:documents/ vào VectorStore

        TextReader reader = new TextReader(new ClassPathResource("documents/" + fileName));
        List<Document> rawDocs = reader.get();

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(500)
                .withMinChunkSizeChars(50)
                .withMaxNumChunks(10000)
                .build();

        List<Document> chunks = splitter.apply(rawDocs);

        vectorStore.add(chunks);

        return chunks.size();
    }

    /**
     * Tra cứu quy chế bằng RAG
     * Pipeline: Search VectorStore -> Ghép context -> Prompt ChatClient -> Trả về RagResponse
     */
    public RagResponse ask(String question) {
        // TODO: Viết logic similarity search, ghép context vào system prompt và gọi LLM
        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)
                .topK(5)
                .similarityThreshold(0.5)
                .build();
        List<Document> matchedDocs = vectorStore.similaritySearch(searchRequest);

        String context = matchedDocs.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining("\n\n"));

        String systemPrompt = """
            Bạn là trợ lý giải đáp quy chế vận chuyển RikkeiExpress.
            CHỈ trả lời dựa vào nội dung tài liệu sau đây.
            Nếu trong tài liệu không có thông tin, hãy trả lời: 'Tôi không tìm thấy thông tin trong quy chế'.
            
            TÀI LIỆU QUY CHẾ:
            """ + context;

        String answer = chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();

        return RagResponse.builder()
                .question(question)
                .answer(answer)
                .retrievedChunks(matchedDocs.size())
                .build();
    }
}
