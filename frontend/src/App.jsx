import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import FirstPage from './Component/FirstPage'
import AdminLogin from './Component/Admin/AdminLogin'
import CustomerLogin from './Component/Customer/CustomerLogin'
import Products from './Component/Admin/Products'
import AddProduct from './Component/Admin/AddProduct'
import ShowProduct from './Component/Customer/ShowProduct'
import MyOrders from './Component/Customer/MyOrders'
import Log from './Component/Admin/Log'
import Confirm from './Component/Admin/Confirm'
import Customers from './Component/Admin/Customers'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<FirstPage />} />
        <Route path="/admin" element={<AdminLogin />} />
        <Route path="/products" element={<Products />} />
        <Route path="/add-product" element={<AddProduct />} />
        <Route path='/log' element={<Log />} />
        <Route path='/confirm' element={<Confirm />} />
        <Route path='/customers' element={<Customers />} />

        <Route path="/customer" element={<CustomerLogin />} />
        <Route path="/show-products" element={<ShowProduct />} />
        <Route path='/my-orders' element={<MyOrders />} />

      </Routes>
    </BrowserRouter>
  )
}

export default App
