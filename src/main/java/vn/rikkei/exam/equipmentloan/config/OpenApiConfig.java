package vn.rikkei.exam.equipmentloan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Cấu hình Swagger UI / OpenAPI 3 cho SmartHub.
 * Truy cập: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI smartHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartHub API — AI Logistics Operations Center")
                        .description("""
                    **Trung tâm vận hành Logistics thông minh tích hợp AI (SmartHub)**
                    
                    4 phân hệ nghiên cứu:
                    - **Module 1 — RAG**: Tra cứu quy chế vận chuyển bằng ngôn ngữ tự nhiên
                    - **Module 2 — Agent**: Điều phối sự cố tự động với Function Calling
                    - **Module 3 — MCP**: Phân tích dữ liệu logistics qua Model Context Protocol
                    - **Module 4 — LLMOps**: Giám sát chi phí và vết chạy với Langfuse
                    
                    **Profiles**: `local` (Ollama) | `cloud` (OpenAI + Langfuse)
                    """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SmartHub Team")
                                .email("smarthub@rikkeiexpress.vn"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .tags(List.of(
                        new Tag().name("Module 1 — RAG")
                                .description("Tra cứu quy chế vận chuyển với Retrieval-Augmented Generation"),
                        new Tag().name("Module 2 — Agent")
                                .description("Điều phối sự cố tự động với AI Agent & Function Calling"),
                        new Tag().name("Module 3 — MCP")
                                .description("Phân tích dữ liệu qua Model Context Protocol"),
                        new Tag().name("Module 4 — LLMOps")
                                .description("Giám sát Observability, Latency & Token Cost")
                ));
    }
}
