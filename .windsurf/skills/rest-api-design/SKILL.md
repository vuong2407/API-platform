---
name: rest-api-design
description: Apply REST API best practices when designing endpoints with explicit DTO usage.
---

# REST API Design

Follow REST conventions and best practices for designing APIs in Java Spring Boot.

## Endpoints

Use standard RESTful naming:

- GET /campaigns — Retrieve all campaigns
- GET /campaigns/{id} — Retrieve a campaign by ID
- POST /campaigns — Create a new campaign
- PUT /campaigns/{id} — Update a campaign
- DELETE /campaigns/{id} — Delete a campaign

## Request & Response Design

- ALWAYS use explicit DTO classes for request and response bodies.
- DO NOT use generic types such as:
  - Map<String, Object>
  - Object
  - raw JSON structures

### Example

#### Request DTO
```java
public class CreateCampaignRequest {
    private String name;
    private String description;
    private BigDecimal budget;
}