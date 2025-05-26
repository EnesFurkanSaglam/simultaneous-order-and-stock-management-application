# 🧠 Eş Zamanlı Sipariş ve Stok Yönetim Uygulaması

Bu proje, işletmelerin **sipariş ve stok işlemlerini eş zamanlı** yönetebilmesini sağlayan modern ve ölçeklenebilir bir web uygulamasıdır. Sistem; **Java Spring Boot** tabanlı bir backend ve **React + Vite** ile geliştirilmiş bir frontend arayüzünden oluşmaktadır.

## 🔧 Proje Yapısı

```
project-root/
├── backend/   → Spring Boot ile geliştirilmiş servis katmanı
├── frontend/  → React (Vite) ile geliştirilmiş kullanıcı arayüzü
└── README.md  → Bu genel açıklama dosyası
```

## ✨ Genel Özellikler

- Eş zamanlı sipariş işleme altyapısı
- Gerçek zamanlı stok takibi
- Yönetici ve müşteri panelleri
- Sipariş önceliklendirme ve yönetimi
- Gelişmiş loglama ve hata yönetimi
- Role-based güvenlik (JWT desteği)
- Temiz, kullanıcı dostu arayüz

---

## 🖥️ Backend (Spring Boot)

Backend tarafı, eş zamanlı sipariş işleme ve stok yönetimini sağlayacak şekilde çok katmanlı mimari ile tasarlanmıştır.

### Temel Özellikler

- **N-Katmanlı Mimari**: Controller, Service, DAO, DTO katmanları
- **Eş Zamanlı Sipariş Yönetimi**: `ExecutorService`, `Semaphore`, `OrderThread` kullanımı
- **Stok & Sipariş Takibi**: Gerçek zamanlı güncellemeler
- **Admin Panel API’leri**: Raporlama ve loglama sistemleri
- **Veritabanı**: MySQL
- **Güvenlik**: JWT, role-based yetkilendirme, input validasyonu

Daha fazla bilgi için [backend/README.md](./backend/README.md) dosyasına göz atabilirsiniz.

---

## 🌐 Frontend (React + Vite)

Frontend tarafı hem yöneticiler hem de müşteriler için kullanıcı dostu arayüzler sunar.

### Ana Özellikler

- **Yönetici Paneli**:
  - Ürün yönetimi (ekle, düzenle, sil)
  - Müşteri ve sipariş yönetimi
  - Sipariş onay ve log görüntüleme
- **Müşteri Paneli**:
  - Ürün kataloğu
  - Sipariş oluşturma ve geçmiş görüntüleme

### Kullanılan Teknolojiler

- React + Vite
- React Router
- CSS Modules
- ES6+ modern JavaScript

Detaylı frontend kurulumu ve yapısı için [frontend/README.md](./frontend/README.md) dosyasını inceleyebilirsiniz.

---

## 🚀 Kurulum ve Başlatma

### Backend İçin

1. Java 17, Maven ve MySQL yüklü olmalı.
2. `backend/README.md` içindeki kurulum adımlarını takip edin.

### Frontend İçin

1. Node.js kurulu olmalı.
2. `frontend/README.md` içindeki adımları takip ederek React arayüzünü başlatabilirsiniz.

---

## 📌 Not

Bu dosya sadece genel bir bilgilendirme sunmaktadır. Her iki alt bileşen hakkında daha fazla detay için ilgili klasörlerin içindeki `README.md` dosyalarını incelemeniz tavsiye edilir:

- [📁 Backend README](./backend/README.md)
- [📁 Frontend README](./frontend/README.md)


# 🧠 Simultaneous Order and Stock Management Application

This project is a modern and scalable web application that enables businesses to **manage orders and stock simultaneously**. The system consists of a **Java Spring Boot** based backend and a **React + Vite** developed frontend interface.

## 🔧 Project Structure

```
project-root/
├── backend/   → Service layer developed with Spring Boot
├── frontend/  → User interface developed with React (Vite)
└── README.md  → This general description file
```

## ✨ General Features

- Simultaneous order processing infrastructure
- Real-time stock tracking
- Admin and customer panels
- Order prioritization and management
- Advanced logging and error handling
- Role-based security (JWT support)
- Clean, user-friendly interface

---

## 🖥️ Backend (Spring Boot)

The backend is designed with a multi-layered architecture to provide simultaneous order processing and stock management.

### Core Features

- **N-Tier Architecture**: Controller, Service, DAO, DTO layers
- **Simultaneous Order Management**: Using `ExecutorService`, `Semaphore`, `OrderThread`
- **Stock & Order Tracking**: Real-time updates
- **Admin Panel APIs**: Reporting and logging systems
- **Database**: MySQL
- **Security**: JWT, role-based authorization, input validation

For more information, please check the [backend/README.md](./backend/README.md) file.

---

## 🌐 Frontend (React + Vite)

The frontend provides user-friendly interfaces for both administrators and customers.

### Main Features

- **Admin Panel**:
  - Product management (add, edit, delete)
  - Customer and order management
  - Order approval and log viewing
- **Customer Panel**:
  - Product catalog
  - Order creation and history viewing

### Technologies Used

- React + Vite
- React Router
- CSS Modules
- ES6+ modern JavaScript

For detailed frontend setup and structure, you can examine the [frontend/README.md](./frontend/README.md) file.

---

## 🚀 Installation and Startup

### For Backend

1. Java 17, Maven, and MySQL must be installed.
2. Follow the installation steps in `backend/README.md`.

### For Frontend

1. Node.js must be installed.
2. You can start the React interface by following the steps in `frontend/README.md`.

---

## 📌 Note

This file only provides general information. For more details about both sub-components, it is recommended to examine the `README.md` files in the relevant folders:

- [📁 Backend README](./backend/README.md)
- [📁 Frontend README](./frontend/README.md) 
