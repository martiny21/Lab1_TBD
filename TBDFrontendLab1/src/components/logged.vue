<template>
    <div class="card">
        <title>
            Te has logueado correctamente
        </title>
        <h1>
            Bienvenido a tu pagina de E-commerce
        </h1>
    </div>
    <div>
        <button>
            <router-link to="/logged/order">
                Ordenes de compra
            </router-link>
        </button>
        <button>
            <!--<router-link to="/login">-->
                Subir productos
            <!--</router-link> -->
        </button>
    </div>
</template>

<style>
    .card {
        margin: 20px;
        padding: 20px;
        border: 5px solid #3a777b;
        background-color: #ffffff;
        color: #3a777b;
        border-radius: 5px;
        flex: auto;
    }
    h1 {
        text-align: center;
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

    .button-link {
        text-decoration: none;
        color: white;
    }

</style>