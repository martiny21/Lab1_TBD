import { createRouter, createWebHistory } from 'vue-router'
import home from '../views/home.vue'
import register from '../views/register.vue'
import login from '../views/Login.vue'
import productList from '../views/productList.vue'
import clients from '../views/clients.vue'
import logged from '../views/logged.vue'
import order from '../views/order.vue'
import OrderDetail from "../views/orderdetail.vue"
import history from "../views/history.vue"
import admin from "../views/admin.vue"
import almacenado from "../views/alma.vue"

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

  },
  {
    path: "/orderdetail/:id",
    name: "OrderDetail",
    component: OrderDetail,
  },
  {
    path: "/history",
    name: "history",
    component: history,
  },
  {
    path: "/admin",
    name: "admin",
    component: admin,
  },
  {
    path: "/almacenado",
    name: "almacenado",
    component: almacenado,
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router