import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';
import { MaskInput } from 'vue-3-mask';
import money from 'v-money3'
import 'bootstrap/dist/css/bootstrap.css';
import "./assets/style.css";
import { createRouter, createWebHistory } from 'vue-router';
import TransferenciasHome from './components/paginas/TransferenciasHome';
import TransferenciasAgendar from './components/paginas/TransferenciasAgendar';
import TransferenciasExtrato from './components/paginas/TrasferenciasExtrato';

axios.defaults.baseURL = process.env.VUE_APP_API_URL

const router = createRouter({ 
    history: createWebHistory(),
    routes: [
      { path: '/', component: TransferenciasHome, },
      { path: '/agendar', component: TransferenciasAgendar },
      { path: '/extrato', component: TransferenciasExtrato },
    ],
  });

  // Função para formatar valores monetários
function formatCurrency(value) {
  if (typeof value !== 'number') {
    value = parseFloat(value);
  }
  return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

const app = createApp(App);

app.config.globalProperties.$filters = {
  formatCurrency,
};

app.use(router);
app.use(money)
app.component('MaskInput', MaskInput);
app.mount('#app');
