<script setup>
import home from './views/home.vue'
import register from './views/register.vue'
import login from './views/login.vue'
import productList from './views/productList.vue'
import clients from './views/clients.vue'
import logged from './views/logged.vue'
import order from './views/order.vue'

</script>


<template>
  <router-view />
  <!--<Clients />-->
</template>

<!--
<template>
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <HelloWorld msg="Vite + Vue" />
</template>
-->


<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
