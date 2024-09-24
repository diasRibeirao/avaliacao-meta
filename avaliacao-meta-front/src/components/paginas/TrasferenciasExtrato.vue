<template>
    <div>
          <div class="container">
              <h2 class="text-center mt-3 mb-3">Extrato de Transferências</h2>
              <div class="card">
                  <div class="card-header">
                      <router-link to="/"
                          class="btn btn-outline-secondary"
                          >Voltar
                      </router-link>
                  </div>
                  <div class="card-body table-responsive">
               
                      <table class="table table-striped table-hover" v-if="transferencias.length > 0">
                          <thead>
                              <tr>
                                  <th nowrap>Conta Origem</th>
                                  <th nowrap>Conta Destino</th>
                                  <th>Valor</th>
                                  <th>Taxa</th>
                                  <th>Data</th>
                                  <th>Status</th>
                              </tr>
                          </thead>
                          <tbody>                               
                              <tr v-for="transferencia in transferencias" :key="transferencia.id">
                                  <td>{{ transferencia.contaOrigem }}</td>
                                  <td>{{ transferencia.contaDestino }}</td>         
                                  <td>{{ $filters.formatCurrency(transferencia.valorTransferencia) }}</td>
                                  <td>{{ $filters.formatCurrency(transferencia.valorTaxa) }}</td>
                                  <td>{{ transferencia.dataTransferencia }}</td>        
                                  <td>{{ transferencia.status }}</td>                               
                              </tr>                                   
                          </tbody>
                      </table>
                      <h5 v-else>Extrato sem movimentações.</h5>
                  </div>
              </div>
          </div>
      </div>
  </template>
   
  <script>
  import axios from 'axios';
  import Swal from 'sweetalert2'
   
  export default {
    name: 'TrasferenciasExtrato',   
    components: {
    },    
    data() {
      return {
        transferencias:[]
      }        
    },
    created() {
      this.buscarTrasferencias();
    },
    methods: {
        buscarTrasferencias() {
            axios.get(`/v1/transferencias`)
          .then(response => {
              this.transferencias = response.data;
              return response
          })
          .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Um erro ocorreu!',
                showConfirmButton: false,
                timer: 1500
            })
            return error
          });
      }     
    },
  };
  </script>