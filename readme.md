# Multi-Threaded Proxy Web Server 

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)


This project implements a **multi-threaded proxy server** in Java that can function as both:
- **Forward Proxy**: Protects client identity, enables access control, and caches responses.
- **Reverse Proxy**: Shields backend services, performs load balancing, and improves security.

## Features

- **Multi-Threaded Request Handling**: Uses a **thread pool** (`ExecutorService`) to efficiently manage multiple concurrent requests.
- **Forward Proxy Mode**: Routes client requests to external servers while masking their identity.
- **Reverse Proxy Mode**: Balances incoming client requests across multiple backend servers.
- **Load Balancing**: Implements **round-robin distribution** for backend requests.
- **Logging & Monitoring**: Tracks request flow and errors for debugging.
- **Configurable Execution Mode**: Easily switch between **Forward** and **Reverse** proxy.

---

## Prerequisites

- **Java 11+** installed
- **Maven** (if using for dependency management)
- **A Server Machine (Localhost or Cloud VM)**

---

## Setup & Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/sankhlatarun/Multi_threaded_proxy_webserver.git
   cd Multi_threaded_proxy_webserver
   ```

2. **Compile and Run**
   ```bash
   javac ProxyServer.java
   java ProxyServer
   ```

3. **Change Proxy Mode**
   - Open `ProxyServer.java`
   - Modify:
     ```java
     private static final boolean FORWARD_PROXY_MODE = true; // Set false for reverse proxy
     ```

---

## Usage

### **1. Forward Proxy**
A **forward proxy** routes client requests to external websites while **masking their identity**.

#### **How It Works**
- Clients send requests to the proxy server.
- The proxy server fetches the requested resource from the **external server**.
- The response is returned to the client.

#### **Example Request**
- **Client:** `http://proxyserver:8080/http://example.com`
- **Proxy Server:** Fetches `example.com` and forwards the response.

#### **Enable Forward Proxy**
- Set `FORWARD_PROXY_MODE = true` in `ProxyServer.java`
- Start the server.

---

### **2. Reverse Proxy**
A **reverse proxy** routes requests to backend services, acting as a protective layer.

#### **How It Works**
- Clients send requests to the **proxy server** instead of directly accessing backend services.
- The proxy determines which **backend server** should handle the request.
- The response is returned to the client as if it originated from the proxy.

#### **Example Request**
- **Client:** `http://proxyserver:8080/api/v1/data`
- **Proxy Server:** Routes request to backend (e.g., `http://backend1.example.com/api/v1/data`).

#### **Enable Reverse Proxy**
- Set `FORWARD_PROXY_MODE = false` in `ProxyServer.java`
- Start the server.

---

## **Thread Pooling for High Performance**
- Uses **Java's ExecutorService** for **efficient request handling**.
- Prevents **thread explosion** by limiting the number of concurrent threads.
- Enhances **scalability**, allowing the proxy to handle thousands of simultaneous requests.

#### **Adjust Thread Pool Size in `ProxyServer.java`**
```java
private static final int THREAD_POOL_SIZE = 10;
ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
```

---

## **Advanced Features**
- **Load Balancing (Reverse Proxy)**
  - Uses **round-robin** to distribute requests across backend servers.
  - Backend servers can be modified in `ReverseProxyHandler.java`.

- **Access Control (Forward Proxy)**
  - Restrict access to specific domains or IP addresses.
  - Implement firewall-like behavior.

- **Logging & Monitoring**
  - Track request logs for security and debugging.
  - Detect suspicious activity and log failed requests.

---

## **Performance Benchmarking**
You can use **Apache JMeter** or `wrk` to simulate high-load testing.

#### **Example JMeter Setup**
1. Install **JMeter**:
   ```bash
   sudo apt install jmeter
   ```
2. Run Load Test:
   ```bash
   jmeter -n -t proxy_test_plan.jmx -l results.jtl
   ```
3. Analyze performance and tweak `THREAD_POOL_SIZE` for optimization.

---

## **Troubleshooting**
### **1. Proxy Not Responding**
- Ensure **Java version** is 11+.
- Check that the **server is running** on the correct port.

### **2. Requests Timing Out**
- Increase `THREAD_POOL_SIZE` in `ProxyServer.java`.
- Use **connection pooling** for better efficiency.

---

## **Future Enhancements**
✅ **Support for HTTPS Proxying (SSL Termination)**  
✅ **Implement Caching for Faster Responses**  
✅ **Integrate Authentication for Secure Access**  

---

## **License**
This project is licensed under the MIT License.

---

## **Contributing**
Pull requests are welcome! Feel free to **suggest features** or **report issues** in the [GitHub repository](https://github.com/sankhlatarun/Multi_threaded_proxy_webserver).

---
```

### **Key Enhancements in this README**
✅ **Clear Explanation of Forward & Reverse Proxy**  
✅ **Step-by-Step Installation & Usage Guide**  
✅ **Thread Pooling & Performance Optimization Details**  
✅ **Load Testing with JMeter for Benchmarking**  
✅ **Future Enhancements for Scalability & Security**  

Would you like **HTTPS Proxy support** added to this project? 🚀
