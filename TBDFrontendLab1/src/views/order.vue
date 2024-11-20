<template>
    <div class="order-container">
      <h1>Resumen de Orden</h1>
      <div v-if="orderItems.length === 0" class="no-order">
        No hay productos en la orden.
      </div>
      <div v-else>
        <table class="order-table">
          <thead>
            <tr>
              <th>Producto</th>
              <th>Descripción</th>
              <th>Precio Unitario</th>
              <th>Cantidad</th>
              <th>Subtotal</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in orderItems" :key="item.productid">
              <td>{{ item.name }}</td>
              <td>{{ item.description }}</td>
              <td>${{ item.price }}</td>
              <td>{{ item.quantity }}</td>
              <td>${{ item.price * item.quantity }}</td>
              <td>
                <button class="btn btn-danger" @click="removeItem(item.productid)">
                  Eliminar
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="order-summary">
          <h2>Total a Pagar: ${{ total }}</h2>
          <button class="btn btn-success" @click="proceedToPayment">
            Pagar
          </button>
        </div>
      </div>
    </div>
  </template>

  <script>
  export default {
    data() {
      return {
        // Lista de productos agregados a la orden (simulada para fines de demostración)
        orderItems: [
          {
            productid: 1,
            name: "Laptop Lenovo",
            description: "Laptop de 15.6 pulgadas con procesador i7",
            price: 1200,
            quantity: 1,
          },
          {
            productid: 2,
            name: "Auriculares Sony WH-1000XM4",
            description: "Auriculares inalámbricos con cancelación de ruido",
            price: 300,
            quantity: 2,
          },
        ],
      };
    },
    computed: {
      // Calcula el total a pagar
      total() {
        return this.orderItems.reduce(
          (acc, item) => acc + item.price * item.quantity,
          0
        );
      },
    },
    methods: {
      // Eliminar un producto de la orden
      removeItem(productid) {
        this.orderItems = this.orderItems.filter(
          (item) => item.productid !== productid
        );
      },
      // Proceder al pago
      proceedToPayment() {
        if (this.orderItems.length === 0) {
          alert("No hay productos en la orden para pagar.");
          return;
        }
        alert("¡Gracias por tu compra! Total a pagar: $" + this.total);
        // Aquí podrías redirigir al usuario a una página de confirmación o iniciar el flujo de pago.
      },
    },
  };
  </script>

  <style scoped>
  .order-container {
    padding: 20px;
    max-width: 900px;
    margin: 0 auto;
    background-color: #f8f9fa;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }

  h1 {
    text-align: center;
    margin-bottom: 20px;
    font-size: 2em;
    color: #3a777b;
  }

  .no-order {
    text-align: center;
    font-size: 18px;
    color: gray;
  }

  .order-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
  }

  .order-table th,
  .order-table td {
    border: 1px solid #dee2e6;
    padding: 15px;
    text-align: left;
    font-size: 14px;
  }

  .order-table th {
    background-color: #3a777b;
    color: #fff;
    font-weight: bold;
    text-align: center;
  }

  .order-table td {
    color: #333;
  }

  .order-summary {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
  }

  .order-summary h2 {
    color: #3a777b;
    font-size: 1.5em;
  }

  .btn {
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    font-size: 14px;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  .btn-danger {
    background-color: #dc3545;
    color: #fff;
  }

  .btn-danger:hover {
    background-color: #a71d2a;
  }

  .btn-success {
    background-color: #28a745;
    color: #fff;
  }

  .btn-success:hover {
    background-color: #218838;
  }
  </style>
