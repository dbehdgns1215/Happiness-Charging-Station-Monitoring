package com.example.happyDream.DTO;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDTO {
    @SerializedName("api_version")
    private String apiVersion;
    private String status;
    @SerializedName("response_code")
    private String responseCode;
    private String message;
    private Integer count;
    private List<Object> data;

    @Builder
    public ResponseDTO(String apiVersion, String status, String responseCode, String message, Integer count, List<Object> data) {
        this.apiVersion = apiVersion;
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
        this.count = count;
        this.data = data;
    }
}
