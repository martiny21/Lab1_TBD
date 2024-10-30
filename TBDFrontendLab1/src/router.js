import { createRouter, createWebHistory } from 'vue-router'
import Home from './components/Home.vue'
import Register from './components/Register.vue'

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
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router