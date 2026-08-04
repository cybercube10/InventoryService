                                    ## INVENTORY MANAGEMENT 
                                    
A microservices-based retail management platform built using **Spring Boot**, **Spring Cloud**, **Apache Kafka**, and **PostgreSQL** to help small grocery stores digitize inventory management, customer credit, billing, and stock monitoring.

The application replaces traditional handwritten registers by automating inventory updates, customer credit tracking, and stock notifications through an event-driven architecture.

---

# Problem Statement

Small retail stores often rely on manual registers to manage inventory and customer credit. As a business grows, it becomes increasingly difficult to:

- Track available inventory
- Manage multiple batches of the same product
- Identify products nearing expiry
- Monitor low stock levels
- Track customer credit
- Remember repayment deadlines

Retail Smart aims to simplify these operations using a distributed microservices architecture.

---

# Features

- Vendor Authentication using JWT
- Product & Category Management
- Batch-wise Inventory Tracking
- Customer Billing
- Credit Ledger Management
- Low Stock Notifications
- Product Expiry Notifications
- Event-driven communication using Apache Kafka
- API Gateway for centralized routing
- Service Discovery using Eureka

---

# Why Microservices?

Instead of implementing the entire application as a monolithic system, Smart Dukaan follows a microservices architecture where every service is responsible for a single business capability.

This allows:

- Independent development of services
- Better separation of concerns
- Easier feature additions
- Loosely coupled communication
- Improved scalability

Business workflows communicate asynchronously through Apache Kafka instead of tightly coupling services using direct service-to-service calls.

---

# Architecture


<img width="652" height="972" alt="hi drawio" src="https://github.com/user-attachments/assets/897e8127-172b-46c6-8440-cbf57235d9d2" />

```

Project Structure

api-gateway/
auth-service/
inventory-service/
order-service/
payment-service/
credit-service/
notification-service/
service-registry/
common-dto/

Service Responsibilities
Authentication Service

Responsible for merchant/vendor registration and authentication.

User Registration
Login
JWT Generation
Authentication

Users are authenticated once at the API Gateway, allowing downstream services to trust authenticated requests without requiring repeated authentication.

Inventory Service

Responsible for inventory management.

Maintains:

Categories
Products
Inventory Batches

Features:

Product Catalog
Batch-wise Inventory
Available Quantity
Expiry Tracking
Low Stock Detection
Expiry Notifications
Order Service

Responsible for customer billing.

Features:

Create Orders
Verify Inventory Availability
Generate Customer Receipt
Publish inventory update events
Payment Service

Responsible for handling customer payments.

(Currently under development.)

Credit Service

Allows vendors to maintain a digital credit ledger for customers.

Features:

Record Outstanding Credit
Configure Repayment Date
Payment Due Reminders
Notification Service

Consumes Kafka events and notifies vendors regarding:

Low Stock
Expiring Inventory
Credit Repayment Due
API Gateway

Acts as the single entry point for all client requests.

Responsibilities:

Request Routing
JWT Authentication
Centralized API Access
Service Registry

Implements service discovery using Netflix Eureka.

Allows services to dynamically discover and communicate with each other without hardcoding service locations.

Common DTO

Contains shared DTOs and Kafka event models used across multiple services.

Inventory Model

The inventory system follows a batch-based tracking strategy.

Each incoming inventory batch is assigned a unique Batch ID.

Instead of automatically deducting inventory using FIFO, Smart Dukaan deducts stock from the scanned batch selected during billing.

This provides explicit control over which inventory batch is being sold while maintaining accurate inventory and expiry tracking.

Event-Driven Communication

Services communicate asynchronously using Apache Kafka.

Typical workflow:


Customer places order

↓

Order Service

↓

Publishes Kafka Event

↓

Inventory Service

↓

Verifies & Updates Stock

↓

Publishes Inventory Event

↓

Notification Service

↓

Notify Vendor


This approach keeps services loosely coupled while allowing business workflows to execute independently.

Tech Stack
Backend
Java
Spring Boot
Spring Cloud
Security
Spring Security
JWT
Database
PostgreSQL
Messaging
Apache Kafka
Service Discovery
Netflix Eureka
API Gateway
Spring Cloud Gateway
Build Tool
Maven
Future Improvements
Payment Gateway Integration
Barcode / QR Based Batch Scanning
Vendor Dashboard
Analytics Service
Report Generation
Docker & Kubernetes Deployment
Monitoring using Prometheus & Grafana
Design Decisions
Batch-based Inventory

Instead of relying on FIFO, inventory is tracked using unique batch identifiers, giving vendors explicit control over which batch is sold.

Event-driven Architecture

Services communicate using Apache Kafka, enabling asynchronous workflows and reducing tight coupling between business services.

API Gateway

All client requests pass through a centralized API Gateway that performs authentication and request routing.

Microservices

Business capabilities are separated into independent services, making the system easier to extend and maintain.
