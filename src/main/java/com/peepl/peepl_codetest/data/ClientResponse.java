package com.peepl.peepl_codetest.data;

import com.peepl.peepl_codetest.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    
    private Integer id;
    private String name;
    private String slug;
    private String isProject;
    private String selfCapture;
    private String clientPrefix;
    private String clientLogo;
    private String address;
    private String phoneNumber;
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    
    public static ClientResponse fromEntity(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setSlug(client.getSlug());
        response.setIsProject(client.getIsProject());
        response.setSelfCapture(client.getSelfCapture());
        response.setClientPrefix(client.getClientPrefix());
        response.setClientLogo(client.getClientLogo());
        response.setAddress(client.getAddress());
        response.setPhoneNumber(client.getPhoneNumber());
        response.setCity(client.getCity());
        response.setCreatedAt(client.getCreatedAt());
        response.setUpdatedAt(client.getUpdatedAt());
        response.setDeletedAt(client.getDeletedAt());
        return response;
    }
}