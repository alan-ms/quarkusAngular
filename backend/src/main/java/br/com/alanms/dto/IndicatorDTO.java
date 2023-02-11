package br.com.alanms.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "País")
public class IndicatorDTO {

    @Schema(description = "Indicador de probreza", example = "7.1")
    public Double value;

    @Schema(description = "Ano do indicador de probreza", example = "2014")
    public String date;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
