# Eş Zamanlı Sipariş ve Stok Yönetim Uygulaması

## 📋 Proje Özeti

Bu proje, eş zamanlı sipariş ve stok yönetimi için geliştirilmiş bir backend uygulamasıdır. Sistem, müşterilerin siparişlerini yönetmek, stok durumunu takip etmek ve siparişleri önceliklendirmek için tasarlanmıştır. Özellikle yüksek trafikli e-ticaret sistemleri için optimize edilmiş bir çözüm sunmaktadır.

## 🎯 Temel Özellikler

- **Eş Zamanlı Sipariş İşleme**: Birden fazla siparişin aynı anda işlenmesi
- **Akıllı Önceliklendirme**: Siparişlerin öncelik skorlarına göre sıralanması
- **Gerçek Zamanlı Stok Takibi**: Stok seviyelerinin anlık güncellenmesi
- **Müşteri Yönetimi**: Müşteri bilgilerinin ve sipariş geçmişinin takibi
- **Admin Paneli**: Sistem yönetimi ve raporlama özellikleri
- **Detaylı Loglama**: Tüm işlemlerin kayıt altına alınması

## 🚀 Teknoloji Altyapısı

### Backend Teknolojileri
- **Java 17**: Modern Java özellikleri (Records, Pattern Matching, Sealed Classes)
- **Spring Boot 3.4.0**: 
  - Spring Web: RESTful API desteği
  - Spring Data JPA: Veritabanı işlemleri
  - Spring Security: Güvenlik yönetimi
- **MySQL**: İlişkisel veritabanı yönetim sistemi
- **Lombok**: Kod tekrarını azaltma ve temiz kod yazımı
- **Maven**: Proje yönetimi ve bağımlılık kontrolü

### Mimari Yapı
- **N-Katmanlı Mimari**:
  - Controller Layer: API endpoint'leri
  - Service Layer: İş mantığı
  - DAO Layer: Veritabanı erişimi
  - Model Layer: Veri modelleri
  - DTO Layer: Veri transfer objeleri


## 🔄 İş Akışı

1. **Sipariş Oluşturma**
   - Müşteri sipariş oluşturur
   - Sistem stok kontrolü yapar
   - Öncelik skoru hesaplanır
   - Sipariş kuyruğa eklenir

2. **Sipariş İşleme**
   - Öncelik skoruna göre sıralama
   - Stok güncelleme
   - Durum güncelleme
   - Müşteriye bildirim

3. **Stok Yönetimi**
   - Gerçek zamanlı stok takibi
   - Kritik stok uyarıları
   - Otomatik stok yenileme önerileri

## 🔄 Eş Zamanlı İşlem Yönetimi

### Thread Yapısı ve Senkronizasyon

Sistem, eş zamanlı sipariş işleme için gelişmiş bir thread yönetimi mekanizması kullanmaktadır:

#### Thread Havuzu ve Kaynak Yönetimi
- **Thread Havuzu**: `ExecutorService` kullanılarak dinamik thread havuzu oluşturulmuştur
- **Semaphore**: Maksimum 5 eş zamanlı sipariş işleme için `Semaphore` kullanılmıştır
- **Thread Takibi**: `ConcurrentHashMap` ile aktif thread'lerin takibi yapılmaktadır

#### Sipariş İşleme Thread'i (`OrderThread`)
Her sipariş için ayrı bir thread oluşturulur ve şu işlemleri gerçekleştirir:

1. **Öncelik Hesaplama**:
   ```java
   private void updatePriorityScore() {
       long waitingTime = java.time.Duration.between(order.getOrderDate(), LocalDateTime.now()).getSeconds();
       double newPriorityScore = (order.getCustomer().getCustomerType().equals(CustomerType.PREMIUM) ? 15 : 10)
               + (waitingTime * 0.5);
       order.setPriorityScore(newPriorityScore);
       order.setWaitingTime(waitingTime);
   }
   ```

2. **Otomatik İptal Mekanizması**:
   - Öncelik skoru 100'ü geçen siparişler otomatik iptal edilir
   - Stok ve bütçe iadesi yapılır
   - Log kaydı oluşturulur

3. **Thread Yaşam Döngüsü**:
   - Thread başlatma: Sipariş oluşturulduğunda
   - Thread sonlandırma: Sipariş tamamlandığında veya iptal edildiğinde
   - Thread kesme: Admin onayı veya iptali durumunda

#### Senkronizasyon Mekanizmaları

1. **Semaphore Kullanımı**:
   ```java
   private final Semaphore semaphore = new Semaphore(5);
   ```
   - Maksimum 5 eş zamanlı sipariş işleme
   - Kaynak yönetimi ve sistem yükü kontrolü

2. **Thread Güvenliği**:
   - `volatile` anahtar kelimesi ile sipariş durumu senkronizasyonu
   - `ConcurrentHashMap` ile thread-safe veri yapıları
   - Atomic işlemler için transaction yönetimi

3. **Hata Yönetimi**:
   - Thread kesme (interrupt) mekanizması
   - Exception handling ve loglama
   - Kaynak temizleme (cleanup) işlemleri

#### Performans Optimizasyonları

1. **Thread Havuzu Yönetimi**:
   - Dinamik thread oluşturma ve yönetimi
   - Kaynak kullanımının optimize edilmesi
   - Sistem yüküne göre otomatik ölçeklendirme

2. **Bellek Yönetimi**:
   - Thread'lerin düzgün sonlandırılması
   - Kaynakların serbest bırakılması
   - Memory leak önleme

3. **Ölçeklenebilirlik**:
   - Yüksek eş zamanlı sipariş işleme kapasitesi
   - Sistem kaynaklarının verimli kullanımı
   - Yük dengeleme mekanizmaları


## 🛠️ Geliştirme Prensipleri

### Kod Kalitesi
- Clean Code prensipleri
- SOLID prensipleri
- DRY (Don't Repeat Yourself)
- KISS (Keep It Simple, Stupid)

### Güvenlik
- JWT tabanlı kimlik doğrulama
- Role-based yetkilendirme
- Input validasyonu
- SQL injection koruması

### Performans
- Connection pooling
- Caching mekanizmaları
- Asenkron işlem yönetimi
- Optimize edilmiş sorgular

## 📊 API Endpoint'leri

### Müşteri İşlemleri
- `POST /api/customers`: Yeni müşteri oluşturma
- `GET /api/customers/{id}`: Müşteri bilgilerini görüntüleme
- `PUT /api/customers/{id}`: Müşteri bilgilerini güncelleme

### Sipariş İşlemleri
- `POST /api/orders`: Yeni sipariş oluşturma
- `GET /api/orders/{id}`: Sipariş detaylarını görüntüleme
- `PUT /api/orders/{id}/status`: Sipariş durumunu güncelleme

### Admin İşlemleri
- `GET /api/admin/dashboard`: Yönetim paneli istatistikleri
- `GET /api/admin/reports`: Raporlama endpoint'leri

## 🔧 Kurulum ve Çalıştırma

### Gereksinimler
- Java 17 JDK
- MySQL 8.0+
- Maven 3.6+
- IDE (IntelliJ IDEA önerilir)

### Kurulum Adımları
1. Projeyi klonlayın:
```bash
git clone [proje-url]
```

2. Veritabanını oluşturun:
```sql
CREATE DATABASE order_management;
```

3. `application.properties` dosyasını düzenleyin:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/order_management
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Projeyi derleyin:
```bash
mvn clean install
```

5. Uygulamayı başlatın:
```bash
mvn spring-boot:run
```


# Simultaneous Order and Stock Management Application

## 📋 Project Overview

This project is a backend application developed for simultaneous order and stock management. The system is designed to manage customer orders, track inventory, and prioritize orders. It provides an optimized solution especially for high-traffic e-commerce systems.

## 🎯 Core Features

- **Simultaneous Order Processing**: Processing multiple orders at the same time
- **Smart Prioritization**: Ordering based on priority scores
- **Real-time Stock Tracking**: Instant stock level updates
- **Customer Management**: Tracking customer information and order history
- **Admin Panel**: System management and reporting features
- **Detailed Logging**: Recording all transactions

## 🚀 Technology Stack

### Backend Technologies
- **Java 17**: Modern Java features (Records, Pattern Matching, Sealed Classes)
- **Spring Boot 3.4.0**: 
  - Spring Web: RESTful API support
  - Spring Data JPA: Database operations
  - Spring Security: Security management
- **MySQL**: Relational database management system
- **Lombok**: Reducing code repetition and clean code writing
- **Maven**: Project management and dependency control

### Architectural Structure
- **N-Tier Architecture**:
  - Controller Layer: API endpoints
  - Service Layer: Business logic
  - DAO Layer: Database access
  - Model Layer: Data models
  - DTO Layer: Data transfer objects

## 🔄 Workflow

1. **Order Creation**
   - Customer creates an order
   - System checks stock
   - Priority score is calculated
   - Order is added to queue

2. **Order Processing**
   - Sorting by priority score
   - Stock update
   - Status update
   - Customer notification

3. **Stock Management**
   - Real-time stock tracking
   - Critical stock alerts
   - Automatic stock renewal suggestions

## 🔄 Concurrent Process Management

### Thread Structure and Synchronization

The system uses an advanced thread management mechanism for concurrent order processing:

#### Thread Pool and Resource Management
- **Thread Pool**: Dynamic thread pool created using `ExecutorService`
- **Semaphore**: `Semaphore` used for maximum 5 concurrent order processing
- **Thread Tracking**: Active threads tracked using `ConcurrentHashMap`

#### Order Processing Thread (`OrderThread`)
A separate thread is created for each order and performs the following operations:

1. **Priority Calculation**:
   ```java
   private void updatePriorityScore() {
       long waitingTime = java.time.Duration.between(order.getOrderDate(), LocalDateTime.now()).getSeconds();
       double newPriorityScore = (order.getCustomer().getCustomerType().equals(CustomerType.PREMIUM) ? 15 : 10)
               + (waitingTime * 0.5);
       order.setPriorityScore(newPriorityScore);
       order.setWaitingTime(waitingTime);
   }
   ```

2. **Automatic Cancellation Mechanism**:
   - Orders with priority score exceeding 100 are automatically cancelled
   - Stock and budget refund is processed
   - Log record is created

3. **Thread Lifecycle**:
   - Thread start: When order is created
   - Thread termination: When order is completed or cancelled
   - Thread interruption: On admin approval or cancellation

#### Synchronization Mechanisms

1. **Semaphore Usage**:
   ```java
   private final Semaphore semaphore = new Semaphore(5);
   ```
   - Maximum 5 concurrent order processing
   - Resource management and system load control

2. **Thread Safety**:
   - Order status synchronization using `volatile` keyword
   - Thread-safe data structures using `ConcurrentHashMap`
   - Transaction management for atomic operations

3. **Error Management**:
   - Thread interruption mechanism
   - Exception handling and logging
   - Resource cleanup operations

#### Performance Optimizations

1. **Thread Pool Management**:
   - Dynamic thread creation and management
   - Optimization of resource usage
   - Automatic scaling based on system load

2. **Memory Management**:
   - Proper thread termination
   - Resource release
   - Memory leak prevention

3. **Scalability**:
   - High concurrent order processing capacity
   - Efficient use of system resources
   - Load balancing mechanisms

## 🛠️ Development Principles

### Code Quality
- Clean Code principles
- SOLID principles
- DRY (Don't Repeat Yourself)
- KISS (Keep It Simple, Stupid)

### Security
- JWT-based authentication
- Role-based authorization
- Input validation
- SQL injection protection

### Performance
- Connection pooling
- Caching mechanisms
- Asynchronous process management
- Optimized queries

## 📊 API Endpoints

### Customer Operations
- `POST /api/customers`: Create new customer
- `GET /api/customers/{id}`: View customer information
- `PUT /api/customers/{id}`: Update customer information

### Order Operations
- `POST /api/orders`: Create new order
- `GET /api/orders/{id}`: View order details
- `PUT /api/orders/{id}/status`: Update order status

### Admin Operations
- `GET /api/admin/dashboard`: Admin panel statistics
- `GET /api/admin/reports`: Reporting endpoints

## 🔧 Installation and Running

### Requirements
- Java 17 JDK
- MySQL 8.0+
- Maven 3.6+
- IDE (IntelliJ IDEA recommended)

### Installation Steps
1. Clone the project:
```bash
git clone [project-url]
```

2. Create the database:
```sql
CREATE DATABASE order_management;
```

3. Edit `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/order_management
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Build the project:
```bash
mvn clean install
```

5. Start the application:
```bash
mvn spring-boot:run
``` 


