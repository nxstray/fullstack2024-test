package com.peepl.peepl_codetest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peepl.peepl_codetest.data.ClientCreateRequest;
import com.peepl.peepl_codetest.data.ClientResponse;
import com.peepl.peepl_codetest.data.ClientUpdateRequest;
import com.peepl.peepl_codetest.model.Client;
import com.peepl.peepl_codetest.handler.ResourceNotFoundHandler;
import com.peepl.peepl_codetest.repository.ClientRepository;
import com.peepl.peepl_codetest.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    
    private final ClientRepository clientRepository;
    private final RedisService redisService;
    private final LocalStorageService localStorageService;
    
    private static final String REDIS_KEY_PREFIX = "client:";
    private static final long REDIS_TTL = 3600;
    
    public ClientService(ClientRepository clientRepository, 
                        RedisService redisService, 
                        LocalStorageService localStorageService,
                        ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.redisService = redisService;
        this.localStorageService = localStorageService;;
    }
    
    /**
     * a. CRUD
     */
    @Transactional
    public ClientResponse createClient(ClientCreateRequest request, MultipartFile logoFile) {
        String slug = SlugUtil.toSlug(request.getName());
        
        if (clientRepository.existsBySlug(slug)) {
            slug = slug + "-" + System.currentTimeMillis();
        }
        
        Client client = new Client();
        client.setName(request.getName());
        client.setSlug(slug);
        client.setIsProject(request.getIsProject() != null ? request.getIsProject() : "0");
        client.setSelfCapture(request.getSelfCapture() != null ? request.getSelfCapture() : "1");
        client.setClientPrefix(request.getClientPrefix());
        client.setAddress(request.getAddress());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setCity(request.getCity());
        
        // c.
        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                String logoUrl =  localStorageService.uploadFile(logoFile);
                client.setClientLogo(logoUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload logo", e);
            }
        }

        Client savedClient = clientRepository.save(client);

        // b.
        saveToRedis(savedClient);
        return ClientResponse.fromEntity(savedClient);
    }

    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAllActive();
        return clients.stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ClientResponse getClientBySlug(String slug) {
        String redisKey =  REDIS_KEY_PREFIX + slug;
        Client cachedClient = redisService.get(redisKey, Client.class);

        if (cachedClient != null) {
            return ClientResponse.fromEntity(cachedClient);
        }
        
        Client client = clientRepository.findBySlugActive(slug)
                .orElseThrow(() -> new ResourceNotFoundHandler("Client not found with slug: " + slug));
        
        saveToRedis(client);

        return ClientResponse.fromEntity(client);
    }

    /**
     * d.
     */
    @Transactional
    public ClientResponse updateClient(String slug, ClientUpdateRequest request, MultipartFile logoFile) {
        Client client = clientRepository.findBySlugActive(slug)
                .orElseThrow(() -> new ResourceNotFoundHandler("Client not found with slug" + slug));

        if (request.getName() != null) {
            client.setName(request.getName());

            String newSlug = SlugUtil.toSlug(request.getName());
            if (!newSlug.equals(slug) && clientRepository.existsBySlug(newSlug)) {
                newSlug = newSlug + "-" + System.currentTimeMillis();
            }
            client.setSlug(newSlug);
        }
        if (request.getIsProject() != null) {
            client.setIsProject(request.getIsProject());
        }
        if (request.getSelfCapture() != null) {
            client.setSelfCapture(request.getSelfCapture());
        }
        if (request.getClientPrefix() != null) {
            client.setClientPrefix(request.getClientPrefix());
        }
        if (request.getAddress() != null) {
            client.setAddress(request.getAddress());
        }
        if (request.getPhoneNumber() != null) {
            client.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getCity() != null) {
            client.setCity(request.getCity());
        }

        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                String logoUrl = localStorageService.uploadFile(logoFile);
                client.setClientLogo(logoUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload logo", e);
            }
        }

        Client updatedClient =  clientRepository.save(client);

        deleteFromRedis(slug);
        saveToRedis(updatedClient);

        return ClientResponse.fromEntity(updatedClient);
    }

    /**
     * e.
     */
    @Transactional
    public void deleteClient(String slug) {
        Client client = clientRepository.findBySlugActive(slug)
                .orElseThrow(() -> new ResourceNotFoundHandler("Client not found with slug: " + slug));

        client.setDeletedAt(LocalDateTime.now());
        clientRepository.save(client);

        deleteFromRedis(slug);
    }

    private void saveToRedis(Client client) {
        String redisKey = REDIS_KEY_PREFIX + client.getSlug();
        redisService.save(redisKey, client, REDIS_TTL);
    }

    public void deleteFromRedis(String slug) {
        String redisKey =  REDIS_KEY_PREFIX + slug;
        redisService.delete(redisKey);
    }
}