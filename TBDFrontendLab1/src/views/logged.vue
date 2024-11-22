<template>
  <div class="container main">
    <header>
      <section class="header-section">
        <img class="main-logo" @click="handleClick" />
        <div class="button-container1">
          <router-link to="/create">
            <div type="button" class="btn btn-secondary">
              <i class="fi fi-rr-user"></i> Crear Producto
            </div>
          </router-link>
          <div v-if="isLogged">
            <div
              type="button"
              class="btn btn-secondary"
              @click="toggleIsLogged"
            >
              <i class="fi fi-rr-user"></i> Cerrar sesión
            </div>
          </div>
          <div v-else>
            <router-link to="/login">
              <div type="button" class="btn btn-secondary">
                <i class="fi fi-rr-user"></i> Ingreso
              </div>
            </router-link>
          </div>
        </div>
        <!-- Botones de Ver Orden e Historial -->
        <div class="order-buttons">
          <router-link to="/order">
            <button type="button" class="btn btn-info">
              Ver Orden
            </button>
          </router-link>
          <router-link to="/history">
            <button type="button" class="btn btn-warning">
              Historial
            </button>
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
          <p><strong>Descripción:</strong> {{ product.description }}</p>
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
            @click="addToOrder(product)"
          >
            Agregar a la Orden
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLogged: localStorage.getItem("isLogged") === "true",
      userLogged: localStorage.getItem("isLogged") === "true"
        ? JSON.parse(sessionStorage.getItem("userLogged"))
        : null,
      products: [
        {
          productid: 1,
          name: "Laptop Lenovo",
          description: "Laptop de 15.6 pulgadas con procesador i7",
          price: 1200,
          stock: 10,
        },
        {
          productid: 2,
          name: "Auriculares Sony WH-1000XM4",
          description: "Auriculares inalámbricos con cancelación de ruido",
          price: 300,
          stock: 5,
        },
        {
          productid: 3,
          name: "Smartphone Samsung Galaxy S23",
          description: "Smartphone de última generación con cámara avanzada",
          price: 900,
          stock: 0,
        },
        {
          productid: 4,
          name: "Teclado Mecánico Logitech",
          description: "Teclado mecánico con iluminación RGB",
          price: 150,
          stock: 15,
        },
        {
          productid: 5,
          name: "Monitor Dell Ultrasharp",
          description: "Monitor de 27 pulgadas 4K con panel IPS",
          price: 500,
          stock: 8,
        },
      ],
    };
  },
  methods: {
    addToOrder(product) {
      if (!this.isLogged) {
        alert("Debe iniciar sesión para agregar productos a la orden.");
        return;
      }
      alert(`Producto "${product.name}" agregado a la orden.`);
    },
    toggleIsLogged() {
      // Eliminar token, datos del usuario y cambiar el estado de sesión
      localStorage.removeItem("jwt"); // Eliminar el token
      sessionStorage.removeItem("userLogged"); // Eliminar datos del usuario
      localStorage.setItem("isLogged", "false"); // Actualizar el estado de sesión
      this.isLogged = false; // Cambiar el estado en el componente
      this.userLogged = null; // Asegurarse de limpiar los datos del usuario
      alert("Has cerrado sesión correctamente.");
      this.$router.push("/login"); // Redirigir al login
    },
    handleClick() {
      alert("Logo clickeado");
    },
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
</style>
