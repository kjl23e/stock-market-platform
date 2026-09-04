# Real-Time Stock & Crypto Analytics Platform

A distributed, event-driven microservices platform designed to ingest, process, and display live market telemetry and real-time streaming analytics (SMA, VWAP, Min/Max) with low latency.

---
## Key Features

* **Real-time Ingestion:** Streams raw tick data (symbol, price, volume, timestamp) into Apache Kafka.
* **Low-Latency Analytics:** Consumes tick streams asynchronously to compute rolling 20-period **Simple Moving Average (SMA)**, **Volume Weighted Average Price (VWAP)**, and session **Min/Max bounds** in memory.
* **Event-Driven Architecture:** Decoupled producers and consumers linked via Kafka (`stock-ticks` topic).
* **Live Visualizations:** Interactive frontend dashboard built with Chart.js displaying real-time price graphs and dynamic metric cards.

---

## Tech Stack

* **Backend Framework:** Java 17+, Spring Boot 3.2
* **Event Streaming:** Apache Kafka, Spring for Apache Kafka
* **Real-Time Data Delivery:** WebSockets (STOMP / SockJS)
* **Frontend:** HTML5, Modern JavaScript (ES6+), Chart.js
* **Build System:** Apache Maven

---

## Repository Structure

stock-market-platform/
├── ingestion-service/      # Ingests raw market data & publishes to Kafka
├── analytics-service/      # Consumes ticks from Kafka & computes SMA, VWAP, Min/Max
├── websocket-service/      # Relays live ticks over WebSockets to client applications
└── frontend-dashboard/     # UI rendering interactive graphs & dynamic analytics


---

## Getting Started

### Prerequisites
* **Java Development Kit (JDK) 17+**
* **Apache Maven**
* **Docker & Docker Compose** (for running Kafka & Zookeeper)

### 1. Start Infrastructure (Kafka & Zookeeper)
Start local Kafka broker on `localhost:9092`:
```bash
docker-compose up -d

# Ingestion Service
cd ingestion-service
mvn clean spring-boot:run

# Analytics Service (Port 8083)
cd analytics-service
mvn clean spring-boot:run

# WebSocket Service
cd websocket-service
mvn clean spring-boot:run

cd frontend-dashboard
python -m http.server 3000

Navigate to http://localhost:3000 to view live telemetry.

API Reference
Analytics Endpoint
Returns calculated metrics for a given financial symbol.

URL: /api/analytics/{symbol}

Method: GET

Example: /api/analytics/BINANCE%3ABTCUSDT

Response Payload:

JSON
{
  "sma": 81735.42,
  "vwap": 81738.10,
  "minPrice": 81703.54,
  "maxPrice": 81765.04
}
