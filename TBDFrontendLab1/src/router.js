import { createRouter, createWebHistory } from 'vue-router'
import Home from './components/Home.vue'
import Register from './components/Register.vue'
import Login from './components/Login.vue'
import ProductList from './components/ProductList.vue'
import Clients from './components/Clients.vue'

const routes = [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/productList',
      name: 'ProductList',
      component: ProductList
    },
    {
      path: '/clients',
      name: 'Clients',
      component: Clients
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router