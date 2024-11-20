<template>
    <div class="box">
        <h2>Lista de Clientes</h2>
        <ul>
            <li v-for="client in clients" :key="client.id">
                <p>Nombre: {{ client.name }}</p>
                <p>Dirección: {{ client.address }}</p>
                <p>Correo Electrónico: {{ client.email }}</p>
                <p>Teléfono: {{ client.phone }}</p>
                <h3>Historial de Compras</h3>
                <ul>
                    <li v-for="purchase in client.purchaseHistory" :key="purchase.id">
                        {{ purchase.details }}
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    data() {
        return {
            newClient: {
                name: '',
                address: '',
                email: '',
                phone: '',
                purchaseHistory: []
            },
            clients: []
        };
    },
    methods: {
        addClient() {
            const newClient = { ...this.newClient, id: Date.now() };
            this.clients.push(newClient);
            this.newClient = {
                name: '',
                address: '',
                email: '',
                phone: '',
                purchaseHistory: []
            };
        }
    }
};
</script>

<style scoped>
h1 {
    text-align:center;
}

label {
    display: flex;
}

.form-group {
    margin: 10px;
}

input {
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    border: 2px solid #3a777b;
    border-radius: 10px;
    background-color: #ffffff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    color: #333;
    font-size: 1rem;
    outline: none;
    transition: border-color 0.3s, box-shadow 0.3s;
}
input:focus {
    border-color: #3a777b;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    background-color: #f9f9f9;
}


.box {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    background-color: #f0f0f0;
    margin: 20px;
    padding: 20px;
    border: 5px solid #3a777b;
    border-radius: 5px;
    color: #3a777b;
}


button {
    margin: 10px;
    justify-content: center;
    padding: 10px 20px;
    font-size: 1.2em;
    background-color: #3a777b;
    color: rgb(255, 255, 255);
    border: none;
    border-radius: 5px;
    cursor: pointer;
    }

.form-group {
    margin-left: 20px;
    margin-right: 20px;
    margin-top: 20px;
    margin-bottom: 20px;
}
</style>