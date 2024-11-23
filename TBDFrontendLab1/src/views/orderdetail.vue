
<template>
  <div class="order-details-container">
    <h1>Detalles de la Orden</h1>
    <div v-if="loading">
      <p>Cargando datos de la orden...</p>
    </div>
    <div v-else>
      <div v-if="orderDetails.length === 0">
        <p>No se encontraron detalles para esta orden.</p>
      </div>
      <div v-else>
        <table class="order-details-table">
          <thead>
            <tr>
              <th>Producto</th>
              <th>Cantidad</th>
              <th>Precio Unitario</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="detail in orderDetails" :key="detail.detail_id">
              <td>{{ detail.product_name || "Cargando..." }}</td>
              <td>{{ detail.amount }}</td>
              <td>${{ detail.unit_price.toFixed(2) }}</td>
              <td>${{ (detail.amount * detail.unit_price).toFixed(2) }}</td>
            </tr>
          </tbody>
        </table>
        <div class="order-total">
          <h2>Total de la Orden: ${{ totalOrder.toFixed(2) }}</h2>
        </div>
        <button @click="payOrder" class="btn btn-primary">Pagar</button>
        <button @click="goBack" class="btn btn-secondary">Volver Atrás</button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      loading: true,
      orderDetails: [],
      totalOrder: 0,
    };
  },
  methods: {
    async fetchOrderDetails() {
      const orderId = this.$route.params.id;

      try {
        const response = await axios.get(
          `http://localhost:8080/detail/getByOrderId/${orderId}`,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("jwt")}`,
            },
          }
        );

        const details = response.data;

        for (let detail of details) {
          const productResponse = await axios.get(
            `http://localhost:8080/product/getById/${detail.product_id}`,
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("jwt")}`,
              },
            }
          );
          detail.product_name = productResponse.data.product_name;
        }

        this.orderDetails = details;
        this.calculateTotal();
      } catch (error) {
        console.error("Error al cargar los detalles de la orden:", error);
        alert("Hubo un problema al cargar los detalles de la orden.");
      } finally {
        this.loading = false;
      }
    },
    calculateTotal() {
      this.totalOrder = this.orderDetails.reduce(
        (acc, detail) => acc + detail.amount * detail.unit_price,
        0
      );
    },
    payOrder() {
      alert("Orden pagada exitosamente. Redirigiendo a la página de inicio...");
      this.$router.push("/logged");
    },
    goBack() {
      this.$router.push("/order");
    },
  },

  created() {
    this.fetchOrderDetails();
  },
};
</script>

<style scoped>
.order-details-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  background-color: white; /* Fondo blanco */
  color: black; /* Letras negras */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 2.5rem;
  color: #3a777b;
  margin-bottom: 20px;
}

.order-details-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
  background-color: white; /* Fondo blanco */
}

.order-details-table th,
.order-details-table td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: center;
  color: black; /* Letras negras */
}

.order-details-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.order-details-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.order-details-table tr:hover {
  background-color: #e0e0e0;
}

.order-total {
  margin-top: 20px;
  font-size: 1.5rem;
  color: black; /* Letras negras */
  font-weight: bold;
}

.btn {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #0056b3;
}
</style>
