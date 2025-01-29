import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Bar } from "react-chartjs-2";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from "chart.js";
import UpdateProduct from "./UpdateProduct";
import "../../CSS/Product.css";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

function Products() {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = () => {
        fetch("http://localhost:8081/admin/products")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => setProducts(data))
            .catch((err) => setError(err.message));
    };

    const handleLogout = () => {
        fetch("http://localhost:8081/admin/logout", {
            method: "POST",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to log out");
                }
                navigate("/admin");
            })
            .catch((err) => setError(err.message));
    };

    const handleUpdateClick = (product) => {
        setSelectedProduct(product);
    };

    const handleCloseUpdate = () => {
        setSelectedProduct(null);
        fetchProducts();
    };

    const handleDeleteClick = (productId) => {
        const confirmDelete = window.confirm(
            "Are you sure you want to delete this product?"
        );
        if (!confirmDelete) return;

        fetch(`http://localhost:8081/admin/delete-product-by/${productId}`, {
            method: "DELETE",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to delete the product");
                }
                fetchProducts();
            })
            .catch((err) => setError(err.message));
    };

    const handleAddProduct = () => {
        navigate("/add-product");
    };

    const goToLogSection = () => {
        navigate("/log");
    };
    const showCustomers = () => {
        navigate("/customers");
    };

    const goToConfirmSection = () => {
        navigate("/confirm");
    };

    const stockData = {
        labels: products.map((product) => product.productName),
        datasets: [
            {
                label: "Stock Level",
                data: products.map((product) => product.stock),
                backgroundColor: products.map((product) =>
                    product.stock <= 5
                        ? "rgba(255, 99, 132, 0.6)"
                        : "rgba(75, 192, 192, 0.6)"
                ),
                borderColor: products.map((product) =>
                    product.stock <= 5
                        ? "rgba(255, 99, 132, 1)"
                        : "rgba(75, 192, 192, 1)"
                ),
                borderWidth: 1,
            },
        ],
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: "top",
            },
            title: {
                display: true,
                text: "Product Stock Levels",
            },
        },
    };

    return (
        <div className="products-page">
            <h1 className="products-title">Products</h1>
            <div className="products-buttons">
                <button className="products-button" onClick={goToLogSection}>
                    Log Section
                </button>
                <button className="products-button" onClick={goToConfirmSection}>
                    Confirm Section
                </button>
                <button className="products-button" onClick={handleAddProduct}>
                    Add New Product
                </button>
                <button className="products-button" onClick={showCustomers}>
                    All Customers
                </button>
                <button className="products-button" onClick={handleLogout}>
                    Logout
                </button>
            </div>
            {error && <p className="products-error">Error: {error}</p>}
            <ul className="products-list">
                {products.map((product) => (
                    <li key={product.productId} className="products-list-item">
                        <span>{product.productId}</span>{" "}
                        <span>{product.productName}</span>
                        <span>{product.price} $</span>{" "}
                        <span> Stock: {product.stock}</span>
                        <button
                            className="products-button-update"
                            onClick={() => handleUpdateClick(product)}
                        >
                            Update
                        </button>
                        <button
                            className="products-button-delete"
                            onClick={() => handleDeleteClick(product.productId)}
                        >
                            Delete
                        </button>
                    </li>
                ))}
            </ul>
            {products.length > 0 && (
                <div className="products-chart">
                    <Bar data={stockData} options={options} />
                </div>
            )}
            {selectedProduct && (
                <UpdateProduct product={selectedProduct} onClose={handleCloseUpdate} />
            )}
        </div>
    );
}

export default Products;
