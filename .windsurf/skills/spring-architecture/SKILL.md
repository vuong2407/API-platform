---
name: spring-architecture
description: Enforce layered architecture for Spring Boot applications.
---

# Spring Boot Architecture

Follow this structure:

Controller → Service → Repository

## Controller
Responsibilities

- handle HTTP requests
- validate request
- return response

Controllers must NOT contain business logic.

## Service
Responsibilities

- implement business logic
- coordinate repositories
- transaction boundaries

## Repository

Responsibilities

- database access only

## DTO rule

Controller <-> DTO
Service <-> Domain Model