# Eş Zamanlı Sipariş ve Stok Yönetim Uygulaması

Bu proje, işletmelerin stok ve sipariş yönetimini eş zamanlı olarak gerçekleştirebilecekleri modern bir web uygulamasıdır. React ve Vite kullanılarak geliştirilmiştir.

## Özellikler

### Yönetici Paneli
- Yönetici girişi
- Ürün yönetimi (ekleme, düzenleme, silme)
- Müşteri listesi görüntüleme
- Sipariş onaylama sistemi
- İşlem logları takibi

### Müşteri Paneli
- Müşteri girişi
- Ürün kataloğu görüntüleme
- Sipariş oluşturma
- Sipariş geçmişi görüntüleme

## Teknolojiler

- React
- Vite
- React Router
- CSS Modules
- Modern JavaScript (ES6+)

## Proje Yapısı

```
src/
├── Component/
│   ├── Admin/
│   │   ├── AdminLogin.jsx
│   │   ├── Products.jsx
│   │   ├── AddProduct.jsx
│   │   ├── Log.jsx
│   │   ├── Confirm.jsx
│   │   └── Customers.jsx
│   ├── Customer/
│   │   ├── CustomerLogin.jsx
│   │   ├── ShowProduct.jsx
│   │   └── MyOrders.jsx
│   └── FirstPage.jsx
├── CSS/
├── App.jsx
└── main.jsx
```

## Kurulum

1. Projeyi klonlayın:
```bash
git clone [repo-url]
```

2. Bağımlılıkları yükleyin:
```bash
npm install
```

3. Geliştirme sunucusunu başlatın:
```bash
npm run dev
```

## Kullanım

1. Ana sayfadan yönetici veya müşteri girişi yapın
2. Yönetici olarak:
   - Ürün ekleyin/düzenleyin
   - Siparişleri onaylayın
   - Müşteri listesini görüntüleyin
   - İşlem loglarını takip edin
3. Müşteri olarak:
   - Ürünleri görüntüleyin
   - Sipariş oluşturun
   - Sipariş geçmişinizi görüntüleyin



# Simultaneous Order and Stock Management Application

This project is a modern web application that allows businesses to manage their inventory and orders simultaneously. It is developed using React and Vite.

## Features

### Admin Panel
- Admin login
- Product management (add, edit, delete)
- Customer list viewing
- Order approval system
- Transaction log tracking

### Customer Panel
- Customer login
- Product catalog viewing
- Order creation
- Order history viewing

## Technologies

- React
- Vite
- React Router
- CSS Modules
- Modern JavaScript (ES6+)

## Project Structure

```
src/
├── Component/
│   ├── Admin/
│   │   ├── AdminLogin.jsx
│   │   ├── Products.jsx
│   │   ├── AddProduct.jsx
│   │   ├── Log.jsx
│   │   ├── Confirm.jsx
│   │   └── Customers.jsx
│   ├── Customer/
│   │   ├── CustomerLogin.jsx
│   │   ├── ShowProduct.jsx
│   │   └── MyOrders.jsx
│   └── FirstPage.jsx
├── CSS/
├── App.jsx
└── main.jsx
```

## Installation

1. Clone the project:
```bash
git clone [repo-url]
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

## Usage

1. Login as admin or customer from the main page
2. As an admin:
   - Add/edit products
   - Approve orders
   - View customer list
   - Track transaction logs
3. As a customer:
   - View products
   - Create orders
   - View order history 