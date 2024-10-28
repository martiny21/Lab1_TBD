<template>
    <div>
        <h1>Gestión de Clientes</h1>
        <form @submit.prevent="addClient">
            <div>
                <label for="name">Nombre:</label>
                <input type="text" v-model="newClient.name" required />
            </div>
            <div>
                <label for="address">Dirección:</label>
                <input type="text" v-model="newClient.address" required />
            </div>
            <div>
                <label for="email">Correo Electrónico:</label>
                <input type="email" v-model="newClient.email" required />
            </div>
            <div>
                <label for="phone">Teléfono:</label>
                <input type="tel" v-model="newClient.phone" required />
            </div>
            <button type="submit">Registrar Cliente</button>
        </form>

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
/* Add your styles here */
</style>