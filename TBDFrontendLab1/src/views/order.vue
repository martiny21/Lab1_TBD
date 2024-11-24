<template>
  <div class="container">
    <header class="header">
      <button class="btn btn-home" @click="goToHome">Home</button>
      <h1>Órdenes del Cliente</h1>
    </header>
    <section>
      <button class="btn btn-primary" @click="fetchOrders">
        Buscar Órdenes
      </button>

      <button class="btn btn-secondary" @click="createOrder">
        Crear Nueva Orden
      </button>

      <div v-if="orders.length === 0" class="no-orders">
        No hay órdenes disponibles para el cliente.
      </div>
      <div v-else class="orders-container">
        <div
          class="order-card"
          v-for="order in orders"
          :key="order.order_id"
        >
          <h2>Orden ID: {{ order.order_id }}</h2>
          <p><strong>Fecha:</strong> {{ order.order_date }}</p>
          <p><strong>Estado:</strong> {{ order.state }}</p>
          <p><strong>Total:</strong> ${{ order.total }}</p>
          <!-- Botón para ver más detalles -->
          <button
            class="btn btn-detail"
            @click="viewOrderDetail(order.order_id)"
          >
            Ver más detalles
          </button>
        </div>
      </div>
    </section>
  </div>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      orders: [],
      userLogged: JSON.parse(localStorage.getItem("userLogged")),
      token: localStorage.getItem("jwt"),
    };
  },
  methods: {
    goToHome() {
      this.$router.push("/logged");
    },
    fetchOrders() {
  const clientId = this.userLogged.client_id;

  axios
    .get(`http://localhost:8080/order/getByClientId/${clientId}`, {
      headers: {
        Authorization: `Bearer ${this.token}`,
      },
    })
    .then((response) => {
      // Filtrar las órdenes pendientes antes de asignarlas
      this.orders = response.data.filter(order => order.state === "pendiente");
    })
    .catch((error) => {
      console.error("Error al obtener las órdenes:", error);
      if (error.response && error.response.status === 404) {
        alert("No se encontraron órdenes para este cliente.");
      } else {
        alert("Hubo un problema al cargar las órdenes.");
      }
    });
},

createOrder() {
  // Verificar si ya existe una orden con estado pendiente
  const hasPendingOrder = this.orders.some(order => order.state === "pendiente");

  if (hasPendingOrder) {
    alert("Ya existe una orden pendiente. No se puede crear una nueva hasta que se complete.");
    return; // Salir del método si ya hay una orden pendiente
  }

  const clientId = this.userLogged.client_id;

  const newOrder = {
    order_date: new Date().toISOString(),
    state: "pendiente",
    client_id: clientId,
    total: 0.0,
  };

  axios
    .post("http://localhost:8080/order/", newOrder, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
      },
    })
    .then((response) => {
      this.orders.push(response.data);
      alert("Orden creada exitosamente.");
    })
    .catch((error) => {
      console.error("Error al crear la orden:", error);
      alert("No se pudo crear la orden.");
    });
},

    viewOrderDetail(orderId) {
      // Redirige a la ruta con el ID de la orden
      this.$router.push(`/orderdetail/${orderId}`);
    },
  },
  created() {
    this.fetchOrders();
  },
};
</script>


<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.header h1 {
  font-size: 2rem;
  color: #ffffff;
  margin: 0;
}

.btn-home {
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-home:hover {
  background-color: #218838;
}

.orders-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.order-card {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.order-card h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

.order-card p {
  margin: 5px 0;
  font-size: 14px;
  color: #555;
}

.no-orders {
  text-align: center;
  color: gray;
  font-size: 16px;
}

button {
  display: inline-block;
  margin: 10px 0;
  padding: 10px 15px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button.btn-primary {
  background-color: #007bff;
  color: white;
}

button.btn-primary:hover {
  background-color: #0056b3;
}

button.btn-secondary {
  background-color: #6c757d;
  color: white;
}

button.btn-secondary:hover {
  background-color: #5a6268;
}
button.btn-detail {
  background-color: #17a2b8;
  color: white;
  margin-top: 10px;
}

button.btn-detail:hover {
  background-color: #117a8b;
}

</style>
