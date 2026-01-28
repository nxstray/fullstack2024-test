# Peepl Code Test - Client Management API

REST API untuk manajemen client dengan fitur CRUD, dan Redis caching.

## Table of Contents

- [Teknologi yang Digunakan](#teknologi-yang-digunakan)
- [Build dan Run](#build-dan-run)
- [API Endpoints](#api-endpoints)
  - [1. Create Client](#1-create-client)
  - [2. Get All Clients](#2-get-all-clients)
  - [3. Get Client by Slug](#3-get-client-by-slug)
  - [4. Update Client](#4-update-client)
  - [5. Delete Client (Soft Delete)](#5-delete-client-soft-delete)

---

## Teknologi yang Digunakan

- **Java 17**
- **Spring Boot 4.0.2**
- **PostgreSQL** - Database utama
- **Redis** - Caching layer
- **Maven** - Build tool

## Build dan Run

```bash
# Build project
mvn clean install -DSkipTests

# Run application
mvn spring-boot:run
```

## API Endpoints

### 1. Create Client

**POST** `/api/clients`

**Content-Type:** `multipart/form-data`

**Request:**
- `client` (JSON):
```json
{
  "name": "PT. Contoh Indonesia",
  "isProject": "0",
  "selfCapture": "1",
  "clientPrefix": "CONT",
  "address": "Jl. Contoh No. 123",
  "phoneNumber": "021-12345678",
  "city": "Jakarta"
}
```
- `logo` (file): Image file untuk logo

**Response:**
```json
{
  "success": true,
  "message": "Client created successfully",
  "data": {
    "id": 1,
    "name": "PT. Contoh Indonesia",
    "slug": "pt-contoh-indonesia",
    "isProject": "0",
    "selfCapture": "1",
    "clientPrefix": "CONT",
    "clientLogo": "blablabla.jpg",
    "address": "Jl. Contoh No. 123",
    "phoneNumber": "021-12345678",
    "city": "Jakarta",
    "createdAt": "2024-01-27T10:00:00",
    "updatedAt": "2024-01-27T10:00:00",
    "deletedAt": null
  }
}
```

### 2. Get All Clients

**GET** `/api/clients`

**Response:**
```json
{
  "success": true,
  "message": "Clients retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "PT. Contoh Indonesia",
      "slug": "pt-contoh-indonesia",
      ...
    }
  ]
}
```

### 3. Get Client by Slug

**GET** `/api/clients/{slug}`

**Response:**
```json
{
  "success": true,
  "message": "Client retrieved successfully",
  "data": {
    "id": 1,
    "name": "PT. Contoh Indonesia",
    "slug": "pt-contoh-indonesia",
    ...
  }
}
```

### 4. Update Client

**PUT** `/api/clients/{slug}`

**Content-Type:** `multipart/form-data`

**Request:**
- `client` (JSON):
```json
{
  "name": "PT. Contoh Indonesia Updated",
  "phoneNumber": "021-87654321"
}
```
- `logo` (file, optional): New image file

### 5. Delete Client (Soft Delete)

**DELETE** `/api/clients/{slug}`

**Response:**
```json
{
  "success": true,
  "message": "Client deleted successfully",
  "data": null
}
```