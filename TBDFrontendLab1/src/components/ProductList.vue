<template>
    <div class = "box">
    <title>
        Lista de productos
    </title>
    <h1>
        Lista de productos
    </h1>
    <div>
        <button>
            Agregar producto
        </button>
    </div>
    <div>
        <table>
            <thead>
                <tr>
                    <th>
                        Nombre
                    </th>
                    <th>
                        Descripción
                    </th>
                    <th>
                        Precio
                    </th>
                    <th>
                        Stock
                    </th>
                    <th>
                        Categoría
                    </th>
                    <th>
                        Acciones
                    </th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</template>

<style>
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
    
</style>
