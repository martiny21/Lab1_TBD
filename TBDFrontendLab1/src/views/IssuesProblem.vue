<template>
    <div class="alert-container">
      <h1>Reporte de Problemas en Órdenes</h1>
      <div v-if="loading" class="loading">Cargando reporte...</div>
      <div v-else-if="error" class="error">{{ error }}</div>
      <div v-else-if="reportData.length === 0" class="no-data">
        No hay datos disponibles.
      </div>
      <div v-else>
        <table class="alert-table">
          <thead>
            <tr>
              <th>Cliente ID</th>
              <th>Nombre del Cliente</th>
              <th>Total Órdenes</th>
              <th>Órdenes Problemáticas</th>
              <th>% de Problemas</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="report in reportData" :key="report.client_id">
              <td>{{ report.client_id }}</td>
              <td>{{ report.client_name }}</td>
              <td>{{ report.total_orders }}</td>
              <td>{{ report.problematic_orders }}</td>
              <td>{{ report.issue_percentage }}%</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>


<script>
import axios from "axios";

export default {
  name: "ClientProblematicReport",
  data() {
    return {
      loading: true,
      error: null,
      reportData: [], // Cambiado a reportData para reflejar mejor el contenido
    };
  },
  methods: {
    fetchClientProblematicReport() {
      axios
        .get("http://localhost:8080/client/geproblem", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        })
        .then((response) => {
          this.reportData = response.data; // Asignar directamente los datos a reportData
          this.loading = false;
          console.log("Reporte de problemas:", this.reportData);
        })
        .catch((err) => {
          console.error("Error al obtener el reporte:", err);
          this.error = "Hubo un problema al cargar el reporte.";
          this.loading = false;
        });
    },
  },
  created() {
    this.fetchClientProblematicReport();
  },
};
</script>


  <style scoped>
  .alert-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
    background-color: #ffffff;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    color: #000; /* Garantizar que todo el texto dentro sea negro */
  }

  h1 {
    text-align: center;
    margin-bottom: 20px;
    color: #000; /* Asegura que el título sea negro */
  }

  .loading,
  .error,
  .no-data {
    text-align: center;
    font-size: 1.2em;
    margin-top: 20px;
    color: #000; /* Garantiza texto negro para mensajes */
  }

  .error {
    color: red; /* Error debe mantenerse rojo */
  }

  .alert-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }

  .alert-table th,
  .alert-table td {
    padding: 10px;
    text-align: left;
    border: 1px solid #ddd;
    color: #000; /* Texto negro para encabezados y celdas */
  }

  .alert-table th {
    background-color: #f4f4f4;
    color: #000; /* Texto negro en encabezados */
  }

  .alert-table tbody tr:hover {
    background: #f9f9f9;
    color: #000; /* Texto negro al pasar el ratón */
  }
  </style>
