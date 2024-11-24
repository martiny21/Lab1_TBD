import { createRouter, createWebHistory } from 'vue-router'
import home from './components/home.vue'
import register from './components/register.vue'
import login from './components/login.vue'
import productList from './components/productList.vue'
import clients from './components/clients.vue'
import logged from './components/logged.vue'

const routes = [
    {
      path: '/',
      name: 'home',
      component: home
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/productList',
      name: 'productList',
      component: productList
    },
    {
      path: '/clients',
      name: 'clients',
      component: clients
    },
    {
      path: '/logged',
      name: 'logged',
      component: logged
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router