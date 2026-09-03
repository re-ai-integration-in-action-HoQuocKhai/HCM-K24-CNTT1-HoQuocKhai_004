package vn.rikkei.exam.equipmentloan.controller;

import vn.rikkei.exam.equipmentloan.dto.RagResponse;
import vn.rikkei.exam.equipmentloan.service.rag.RagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/rag")
@RequiredArgsConstructor
@Tag(name = "1. RAG Controller", description = "Phân hệ RAG - Tra cứu quy chế")
public class RagController {

    private final RagService ragService;

    @PostMapping("/ingest")
    @Operation(summary = "Nạp tài liệu quy chế mặc định vào PgVector")
    public ResponseEntity<Map<String, Object>> ingest() {
        int chunks = ragService.ingestDocument("tai_lieu_noi_bo.md");
        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "chunksIngestDocument", chunks
        ));
    }


    @GetMapping("/ask")
    @Operation(summary = "Tra cứu quy chế bằng câu hỏi tự nhiên")
    public ResponseEntity<RagResponse> ask(@RequestParam String question) {
        RagResponse response = ragService.ask(question);
        return ResponseEntity.ok(response);
    }
}
