import { createRouter, createWebHistory } from 'vue-router'
import home from '../views/home.vue'
import register from '../views/register.vue'
import login from '../views/login.vue'
import productList from '../views/productList.vue'
import clients from '../views/clients.vue'
import logged from '../views/logged.vue'
import order from '../views/order.vue'

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
  },

  {
    path: '/order',
    name: 'order',
    component: order

  }
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router