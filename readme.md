# Multi-Threaded Proxy Web Server with LRU Caching

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

A high-performance proxy server supporting HTTP/1.0 and HTTP/1.1, designed to handle **10,000+ requests per second** using multi-threading, LRU caching, and persistent connections. Includes performance analysis via JMeter and graph-based metrics.

---

## Features
- **Multi-threaded architecture** with thread pooling (`ExecutorService`).
- **LRU caching** for reduced latency and backend load.
- **HTTP/HTTPS support** on ports `80`, `443`, and `8080`.
- **Persistent connections** (HTTP/1.1) and graceful termination (`FIN`/`ACK`).
- **Performance graphs** for throughput, latency, and cache efficiency.
- **JMeter integration** for load testing.

---

## Architecture

### Core Components
1. **Proxy Server**
   - Listens on ports `80`, `443`, and `8080` using Java `ServerSocket`.
   - Parses HTTP headers via `BufferedReader` and `PrintWriter`.
   - Supports both non-persistent (HTTP/1.0) and persistent (HTTP/1.1) connections.

2. **Thread Pool & Task Queue**
   - **Thread Pool**: Configurable size (e.g., `T1`, `T2`, ..., `T100`).
   - **Task Queue**: Holds pending requests (e.g., `Qave` = average queue length).
   - Workflow example:
     ```
     T1 → T5 (context switching)
     T100 → T4 (load balancing)
     ```

3. **LRU Cache**
   - Evicts least recently used items at capacity.
   - Thread-safe with synchronized access.

4. **Graph Analysis**
   - **X-axis**: Request rate (e.g., 0–10K req/sec).
   - **Y-axis**: Latency (ms) or throughput (req/sec).
   - **Z-axis**: Cache size or concurrent threads (3D analysis).

### Workflow
1. **TCP Handshake**: Establishes connection via three-way handshake.
2. **Request Handling**:
   - Checks LRU cache for cached responses.
   - Proxies requests to backend servers on cache misses.
3. **Thread Execution**:
   - Tasks are assigned to threads (e.g., `T1`, `T2`) from the pool.
   - Blocking I/O minimized via asynchronous processing.
4. **Response**:
   - Sends response to client.
   - Closes or reuses connections based on HTTP version.

---

## Installation

### Prerequisites
- Java 17+
- Maven/Gradle
- JMeter (for load testing)

### Build & Run
```bash
# Clone the repository
git clone https://github.com/your-username/proxy-server.git
cd proxy-server

# Build with Maven
mvn clean package

# Start the server (default: port 8080)
java -jar target/proxy-server.jar

# Custom port (e.g., 80)
java -jar target/proxy-server.jar --port 80
```


## Testing with JMeter
### Load Testing:

Simulate 10K+ concurrent users.

Metrics: Throughput, latency, error rate.

### Graph Results:

Generate graphs for analysis (example below).

Example Graph Output
Performance Graph
Hypothetical graph showing latency vs. request rate.

single thread
![image](https://github.com/user-attachments/assets/ede5fbbc-d015-48e3-98bc-de613c33babf)

multi thread
![image](https://github.com/user-attachments/assets/8d8f3abe-223b-4757-a2a1-70ed3e54e702)


thread pool
![image](https://github.com/user-attachments/assets/b5782bb8-6c89-4269-8b49-8e1a506cdd4f)

## 6M request
single thread
![image](https://github.com/user-attachments/assets/5789dc9b-8564-486e-908c-38837bb23c7b)

multi thread
![image](https://github.com/user-attachments/assets/76714026-4ed1-442a-9c17-b2d4b8af3712)


thread pool
![image](https://github.com/user-attachments/assets/ec5f279d-f055-46f0-a9c6-b790f7a80f3d)


### Results
Single-threaded: ~1,000 req/sec (600K req/min).

Multi-threaded: ~10,000 req/sec (6M req/min) with 100 threads.

Cache Hit Rate: 85%+ for frequently accessed resources.

Challenges & Solutions
Challenge	Solution
Thread starvation	Limited thread pool + task queue (Qave)
Cache invalidation	LRU eviction + TTL for dynamic content
HTTP/1.1 persistence	Proper FIN/ACK handling
