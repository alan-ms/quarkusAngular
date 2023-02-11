package br.com.alanms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "País")
public class CountryDTO {

    @Schema(description = "Id do País", example = "BRA")
    private String id;

    @Schema(description = "Nome do país Internacionalmente", example = "Brazil")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
