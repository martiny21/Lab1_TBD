<template>
    <div class="box">
        <h1>Login</h1>
        <form>
        <div class="form-group">
            <label for="email">Correo</label>
            <input type="email" class="form-control" id="email" v-model= "email">
        </div>
        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" class="form-control" id="password" v-model= "password">
        </div>
        </form>
        <button type="submit" class="btn btn-primary" @click="login">Login</button>
        <a href="/" class="redirect-link">Volver</a>
    </div>
</template>

<script>
export default {
    data() {
        return {
            email: '',
            password: ''
        };
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