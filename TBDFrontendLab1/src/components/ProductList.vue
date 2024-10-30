<template>
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
            <tbody>
                <tr>
                    <td>
                        Producto 1
                    </td>
                    <td>
                        Descripción del producto 1
                    </td>
                    <td>
                        $100
                    </td>
                    <td>
                        10
                    </td>
                    <td>
                        Categoría 1
                    </td>
                    <td>
                        <button>
                            Editar
                        </button>
                        <button>
                            Eliminar
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>
                        Producto 2
                    </td>
                    <td>
                        Descripción del producto 2
                    </td>
                    <td>
                        $200
                    </td>
                    <td>
                        0
                    </td>
                    <td>
                        Categoría 2
                    </td>
                    <td>
                        <button>
                            Editar
                        </button>
                        <button>
                            Eliminar
                        </button>
                        <span style="color: red;">
                            Agotado
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Producto 3
                    </td>
                    <td>
                        Descripción del producto 3
                    </td>
                    <td>
                        $300
                    </td>
                    <td>
                        30
                    </td>
                    <td>
                        Categoría 3
                    </td>
                    <td>
                        <button>
                            Editar
                        </button>
                        <button>
                            Eliminar
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</template>