package com.peepl.peepl_codetest.controller;

import com.peepl.peepl_codetest.data.ApiResponse;
import com.peepl.peepl_codetest.data.ClientCreateRequest;
import com.peepl.peepl_codetest.data.ClientResponse;
import com.peepl.peepl_codetest.data.ClientUpdateRequest;
import com.peepl.peepl_codetest.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // post data client
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ClientResponse>> createClient(
            @RequestPart("client") String clientJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ClientCreateRequest request = mapper.readValue(clientJson, ClientCreateRequest.class);

        ClientResponse response = clientService.createClient(request, logo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Client created successfully", response));
    }

    // get all client
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponse>>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(ApiResponse.success("Client retrieved successfully", clients));
    }

    // get client by slug
    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ClientResponse>> getClientBySlug(@PathVariable String slug) {
        ClientResponse response = clientService.getClientBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Client retrieved successfully", response));
    }

    // update client by slug
    @PutMapping(value = "/{slug}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ClientResponse>> updateClient(
            @PathVariable String slug,
            @RequestPart("client") String clientJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ClientUpdateRequest request = mapper.readValue(clientJson, ClientUpdateRequest.class);

        ClientResponse response = clientService.updateClient(slug, request, logo);

        return ResponseEntity.ok(ApiResponse.success("Client updated successfully", response));
    }

    // delete client by slug
    @DeleteMapping("/{slug}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable String slug) {
        clientService.deleteClient(slug);
        return ResponseEntity.ok(ApiResponse.success("Client deleted successfully", null));
    }
}
