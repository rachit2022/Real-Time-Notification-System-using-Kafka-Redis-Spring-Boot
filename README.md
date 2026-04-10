I recently worked on where I implemented a real-time notification system using event-driven architecture.

The goal was to design a backend system capable of handling high-volume notification events efficiently while ensuring both low-latency delivery and reliable storage.

⚙ Tech Stack

Spring Boot
Apache Kafka
Redis
MySQL
Docker

📌 How the System Works

1️⃣ A user action (e.g., placing an order) generates an event.
2️⃣ The event is published to a Kafka topic (user-events).
3️⃣ Multiple Kafka consumer groups process the same event independently:

• notification-group → Redis

Stores notifications for instant retrieval via API
Enables real-time user notifications

• db-group → Database

Persists notifications for long-term storage
Uses batch inserts to efficiently handle high throughput

⚡ Key Features
✔ Event-driven architecture
✔ Scalable Kafka consumer groups
✔ Redis caching for real-time notifications
✔ Batch database inserts for handling large volumes of events
✔ Decoupled services for better scalability and reliability

This project helped me understand how systems at scale (like Uber, Netflix, and Amazon) process events and build high-performance backend architectures.

