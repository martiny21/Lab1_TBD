<template>
  <div class="container main">
    <header>
      <section class="header-section">
        <div class="button-container1">
          <router-link to="/create">
            <div class="btn btn-secondary">
              <i class="fi fi-rr-user"></i> Crear Producto
            </div>
          </router-link>
          <div v-if="isLogged">
            <div class="btn btn-secondary" @click="toggleIsLogged">
              <i class="fi fi-rr-user"></i> Cerrar sesión
            </div>
          </div>
          <div v-else>
            <router-link to="/login">
              <div class="btn btn-secondary">
                <i class="fi fi-rr-user"></i> Ingreso
              </div>
            </router-link>
          </div>
        </div>
        <div class="order-buttons">
          <router-link to="/order">
            <button type="button" class="btn btn-info">Ver mis Ordenes</button>
          </router-link>
          <router-link to="/history">
            <button type="button" class="btn btn-warning">Historial</button>
          </router-link>
        </div>
      </section>
    </header>

    <section>
      <h1>Productos Disponibles</h1>
      <div v-if="products.length === 0" class="no-products">
        No hay productos disponibles.
      </div>
      <div v-else class="products-container">
        <div
          class="product-card"
          v-for="product in products"
          :key="product.productid"
        >
          <h2>{{ product.name }}</h2>
          <p><strong>Nombre:</strong> {{ product.product_name }}</p>
          <p><strong>Descripción:</strong> {{ product.product_desc }}</p>
          <p><strong>Precio:</strong> ${{ product.price }}</p>
          <p><strong>Stock:</strong> {{ product.stock }}</p>
          <p>
            <strong>Estado:</strong>
            <span
              :class="{ available: product.stock > 0, outOfStock: product.stock === 0 }"
            >
              {{ product.stock > 0 ? "Disponible" : "Agotado" }}
            </span>
          </p>
          <button
            class="btn btn-success"
            :disabled="product.stock === 0"
            @click="openOrderSelection(product)"
          >
            Agregar a la Orden
          </button>
        </div>
      </div>
    </section>

    <!-- Modal para seleccionar una orden -->
    <div v-if="showOrderModal" class="modal">
      <div class="modal-content">
        <h2>Seleccionar Orden</h2>
        <div v-if="orders.length === 0">
          No tienes órdenes disponibles.
        </div>
        <div v-else>
          <ul>
            <li
              v-for="order in orders"
              :key="order.order_id"
              class="order-item"
            >
              <span>Orden ID: {{ order.order_id }}</span>
              <button @click="addProductToOrder(order, selectedProduct)">
                Seleccionar
              </button>
            </li>
          </ul>
        </div>
        <button @click="closeOrderModal">Cerrar</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      isLogged: localStorage.getItem("isLogged") === "true",
      userLogged: localStorage.getItem("isLogged") === "true"
        ? JSON.parse(localStorage.getItem("userLogged"))
        : null,
      products: [],
      orders: [],
      showOrderModal: false,
      selectedProduct: null,
    };
  },
  methods: {
    fetchProducts() {
      axios
        .get("http://localhost:8080/product/getall", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        })
        .then((response) => {
          this.products = response.data;
        })
        .catch((error) => {
          console.error("Error al obtener los productos:", error);
          alert("Hubo un problema al obtener los productos.");
        });
    },
    fetchOrders() {
      const clientId = this.userLogged.client_id;
      axios
        .get(`http://localhost:8080/order/getByClientId/${clientId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        })
        .then((response) => {
          this.orders = response.data;
        })
        .catch((error) => {
          console.error("Error al obtener las órdenes:", error);
          alert("Hubo un problema al obtener las órdenes.");
        });
    },
    openOrderSelection(product) {
      this.selectedProduct = product;
      this.fetchOrders();
      this.showOrderModal = true;
    },
    addProductToOrder(order, product) {
      const detail = {
        order_id: order.order_id,
        product_id: product.product_id,
        amount: 1, // Por simplicidad, ajusta según sea necesario
        unit_price: product.price,
      };

      axios
        .post("http://localhost:8080/detail/", detail, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        })
        .then(() => {
          alert(`Producto "${product.name}" agregado a la orden ID ${order.order_id}`);
          this.closeOrderModal();
          //recargar productos
          this.fetchProducts();
        })
        .catch((error) => {
          console.error("Error al agregar producto a la orden:", error);
          alert("No se pudo agregar el producto a la orden.");
        });
    },
    closeOrderModal() {
      this.showOrderModal = false;
      this.selectedProduct = null;
    },
    toggleIsLogged() {
      localStorage.removeItem("jwt");
      sessionStorage.removeItem("userLogged");
      localStorage.setItem("isLogged", "false");
      this.isLogged = false;
      this.userLogged = null;
      alert("Has cerrado sesión correctamente.");
      this.$router.push("/login");
    },
  },
  created() {
    this.fetchProducts();
  },
};
</script>

<style scoped>
.container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.order-buttons {
  display: flex;
  gap: 10px;
}

.order-buttons .btn {
  font-size: 14px;
  font-weight: bold;
  border-radius: 5px;
  padding: 8px 15px;
  cursor: pointer;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 2em;
  color: #3a777b;
}

.no-products {
  text-align: center;
  font-size: 18px;
  color: gray;
}

.products-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.product-card {
  background-color: #ffffff;
  border: 1px solid #dee2e6;
  border-radius: 10px;
  padding: 20px;
  width: calc(33.333% - 20px); /* Tres tarjetas por fila */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  color: #000; /* Cambiar color del texto a negro */
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.product-card h2 {
  margin: 0 0 10px;
  font-size: 1.5em;
  color: #3a777b;
}

.product-card p {
  margin: 5px 0;
  font-size: 1em;
  color: #000; /* Asegurar que el texto de los párrafos sea negro */
}

.product-card .available {
  color: green;
  font-weight: bold;
}

.product-card .outOfStock {
  color: red;
  font-weight: bold;
}

.product-card .btn {
  margin-top: 15px;
  padding: 10px 15px;
  font-size: 14px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.product-card .btn-success {
  background-color: #3a777b;
  color: white;
  border: none;
}

.product-card .btn-success:hover {
  background-color: #2a5a5c;
}

.product-card .btn-success:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

button {
  font-size: 14px;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #2a5a5c;
  color: white;
}

.main-logo {
  width: 150px;
  height: auto;
  cursor: pointer;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
  border-radius: 5px;
  font-size: 14px;
  padding: 10px 15px;
  margin-right: 10px;
  text-align: center;
}

.btn-secondary:hover {
  background-color: #5a6268;
}

.btn-info {
  background-color: #17a2b8;
  color: white;
  border-radius: 5px;
  padding: 10px 15px;
}

.btn-info:hover {
  background-color: #117a8b;
}

.btn-warning {
  background-color: #ffc107;
  color: white;
  border-radius: 5px;
  padding: 10px 15px;
}

.btn-warning:hover {
  background-color: #d39e00;
}

/* Modal estilos */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  color: black;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
  max-width: 90%;
}

.modal-content h2 {
  margin-top: 0;
}

.order-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
</style>
