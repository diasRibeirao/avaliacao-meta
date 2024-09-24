<template>
    <div class="container">
         <h2 class="text-center mt-3 mb-3">Transferências entre Contas</h2>
         <div class="card">            
             <div class="card-header">    
                 <router-link class="btn btn-outline-success float-right" to="agendar">Agendar</router-link>            
                 <router-link class="btn btn-outline-info float-right" to="extrato" :disabled="true">Extrato</router-link>
             </div>
             <div class="card-body m-4">  

              <div class="row">
                <div class="col-md-4 mt-2 mb-2">                  
                  <div class="card">
                    <div class="card-body">
                      <h5 class="card-title">{{totalTransferencias}}</h5>
                      <p class="card-text">Total de Transferências</p>
                    </div>
                  </div>
                </div>
        
                <div class="col-md-4 mt-2 mb-2">                  
                  <div class="card">
                    <div class="card-body">
                      <h5 class="card-title">{{totalAgendadas}}</h5>
                      <p class="card-text">Agendadas</p>
                    </div>
                  </div>
                </div>
          
                <div class="col-md-4 mt-2 mb-2">                  
                  <div class="card">
                    <div class="card-body">
                      <h5 class="card-title">{{totalConfirmadas}}</h5>
                      <p class="card-text">Confirmadas</p>
                    </div>
                  </div>
                </div>
              </div>


             </div>
         </div>
    </div>
 </template>
  
 <script>
 import axios from 'axios';
 import Swal from 'sweetalert2'
  
 export default {
   name: 'TranserenciasHome',   
   data() {
     return {
      totalTransferencias:0,
      totalAgendadas:0,
      totalConfirmadas:0
     };
   },
   created() {
    this.buscarQuantitativos(); 
   },
   methods: {
      buscarQuantitativos() { 
        axios.get(`/v1/transferencias`)
        .then(response => { 
            this.totalTransferencias = response.data.length;
            if (this.totalTransferencias > 0) {
              this.totalAgendadas = response.data.reduce((acc, curr) => {
                  return acc + (curr.status === 'AGENDADA' ? 1 : 0);
              }, 0);

              this.totalConfirmadas = response.data.reduce((acc, curr) => {
                  return acc + (curr.status === 'CONFIRMADA' ? 1 : 0);
              }, 0);
            }
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
        })
      }
   },
 };
 </script>