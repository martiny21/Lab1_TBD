<template>
    <div>
        <h1>Registro</h1>
        <form>
        <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" class="form-control" id="nombre" v-model= "nombre">
        </div>
        <div class="form-group">
            <label for="Correo">Correo</label>
            <input type="email" class="form-control" id="Correo" v-model= "Correo">
        </div>
        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" class="form-control" id="password" v-model= "password">
        </div>
        <button type="submit" class="btn btn-primary" @click="registrar">Registrar</button>
        </form>
    </div>
</template>

<style>
label {
    margin-right: 10px;
}
.form-group {
    margin: 10px;
}
input {
    background-color: white;
    color: black;
}
</style>
  