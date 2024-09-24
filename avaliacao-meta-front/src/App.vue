<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>
 
<script>
export default {
  name: 'App',
  mounted() {
    document.title = 'Avaliação Prática Bank';
  }
};

</script>