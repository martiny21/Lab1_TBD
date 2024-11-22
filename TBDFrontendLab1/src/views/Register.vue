<template>
    <div class="box">
        <h1>Registro</h1>
        <form @submit.prevent="registerClient">
            <div>
                <label for="name">Nombre:</label>
                <input type="text" v-model="name" required />
            </div>
            <div>
                <label for="direction">Dirección:</label>
                <input type="text" v-model="direction" required />
            </div>
            <div>
                <label for="email">Correo Electrónico:</label>
                <input type="email" v-model="email" required />
            </div>
            <div>
                <label for="number">Teléfono:</label>
                <input type="tel" v-model="number" required />
            </div>
            <div>
                <label for="password">Contraseña:</label>
                <input type="password" v-model="password" required />
            </div>
            <button type="submit">Registrar Cliente</button>
        </form>
        <a href="/" class="redirect-link">Volver</a>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            name: '',
            email: '',
            number: '',
            direction: '',
            password: ''
        };
    },
    methods: {
        async registerClient() {
            try {
                const clientData = {
                    client_name: this.name,
                    email: this.email,
                    client_number: this.number,
                    direction: this.direction,
                    client_password: this.password
                };

                const response = await axios.post('http://localhost:8080/client/register', clientData);

                if (response.status === 200) {
                    alert('Registro exitoso');
                    this.clearForm();
                }
            } catch (error) {
                console.error('Error al registrar el cliente:', error);
                alert('Hubo un error al registrar. Intenta de nuevo.');
            }
        },
        clearForm() {
            this.name = '';
            this.email = '';
            this.number = '';
            this.direction = '';
            this.password = '';
        }
    }
};
</script>

<style>
h1 {
    text-align:center;
}

label {
    display: flex;
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

.card {
        margin: 20px;
        padding: 20px;
        border: 5px solid #3a777b;
        background-color: #ffffff;
        color: #3a777b;
        border-radius: 5px;
        flex: auto;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
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
    flex: auto;
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
