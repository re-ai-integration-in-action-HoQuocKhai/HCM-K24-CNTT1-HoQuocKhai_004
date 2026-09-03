package vn.rikkei.exam.equipmentloan.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response DTO cho Module 1 RAG API.
 * Trả về câu trả lời kèm trích dẫn nguồn tài liệu (anti-hallucination).
 */
@Data
@Builder
public class RagResponse {

    /** Câu trả lời từ LLM dựa trên tài liệu quy chế */
    private String answer;

    /** Danh sách nguồn tài liệu được trích dẫn (tên file, số trang, điều khoản) */
    private List<SourceDocument> sourceDocuments;

    /** Câu hỏi gốc của người dùng */
    private String question;

    /** Số lượng chunk được truy xuất từ vector store */
    private int retrievedChunks;

    /**
     * Chi tiết một tài liệu nguồn được trích dẫn.
     */
    @Data
    @Builder
    public static class SourceDocument {
        /** Tên file tài liệu */
        private String fileName;
        /** Số trang hoặc số điều khoản */
        private String pageOrSection;
        /** Đoạn trích ngắn từ tài liệu */
        private String excerpt;
        /** Điểm tương đồng (cosine similarity) */
        private Double similarityScore;
    }
}
