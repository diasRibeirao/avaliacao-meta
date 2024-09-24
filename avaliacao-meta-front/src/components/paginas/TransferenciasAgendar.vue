<template>
  <div class="container">
     <h2 class="text-center mt-3 mb-3">Agendar Transferência</h2>
     <div class="card">
        <div class="card-header">
           <router-link to="/"
              class="btn btn-outline-secondary"
              >Voltar
           </router-link>
        </div>
        <div class="card-body">
           <form autocomplete="off">
              <div class="container">
                 <div class="row">
                    <div class="col-sm-6">
                       <div class="form-group mb-3">
                          <label 
                             htmlFor="contaOrigem" 
                             class="form-label">Conta de Origem</label>&nbsp;
                          <span class="required">(padrão XXXXXXXXXX)</span>
                          <MaskInput 
                             v-model="transferencia.contaOrigem"
                             class="form-control" 
                             id="contaOrigem" 
                             name="contaOrigem" 
                             mask="##########"
                             placeholder="Conta de Origem (padrão XXXXXXXXXX)" 
                             required />
                          <span class="error" v-if="v$.transferencia.contaOrigem.$error"> 
                          {{ v$.transferencia.contaOrigem.$errors[0].$message }} 
                          </span>
                       </div>
                    </div>
                    <div class="col-sm-6">
                       <div class="form-group mb-3">
                          <label 
                             htmlFor="contaDestino" 
                             class="form-label">Conta de Destino</label>&nbsp;
                          <span class="required">(padrão XXXXXXXXXX)</span>
                          <MaskInput 
                             v-model="transferencia.contaDestino"
                             class="form-control" 
                             id="contaDestino"
                             name="contaDestino" 
                             mask="##########"
                             placeholder="Conta de Destino (padrão XXXXXXXXXX)" />
                          <span class="error" v-if="v$.transferencia.contaDestino.$error"> 
                          {{ v$.transferencia.contaDestino.$errors[0].$message }} 
                          </span>
                       </div>
                    </div>
                 </div>
                 <div class="row">
                    <div class="col-sm-6">
                       <div class="form-group mb-3">
                          <label 
                             htmlFor="valorTransferencia" 
                             class="form-label required">Valor da Transferência</label>                          
                            <money3 
                              v-model.number="transferencia.valorTransferencia" 
                              v-bind="config" 
                              class="form-control" 
                              id="valorTransferencia"
                              name="valorTransferencia" 
                              placeholder="Valor da Transferência"/>
                            <span class="error" v-if="v$.transferencia.valorTransferencia.$error"> 
                            {{ v$.transferencia.valorTransferencia.$errors[0].$message }} 
                            </span>
                       </div>
                    </div>
                    <div class="col-sm-6">
                       <div class="form-group mb-3">
                          <label 
                             htmlFor="dataTransferencia" 
                             class="form-label required">Data da Transferência</label>
                             <datepicker 
                              v-model="transferencia.dataTransferencia" 
                              :lowerLimit="new Date()"  
                              inputFormat="dd/MM/yyyy"
                              :locale="pt"
                              class="form-control" 
                              id="dataTransferencia"
                              name="dataTransferencia"
                              placeholder="Data da Transferência" />
                             <span class="error" v-if="v$.transferencia.dataTransferencia.$error"> 
                              {{ v$.transferencia.dataTransferencia.$errors[0].$message }} 
                             </span>
                       </div>
                    </div>
                 </div>
              </div>
              <button 
                 @click="agendar()"
                 :disabled="isSaving"
                 type="button"
                 class="btn btn-outline-primary mt-3">
              Agendar
              </button>
           </form>
        </div>
     </div>
  </div>
</template>
  
<script>
import axios from 'axios';
import Swal from 'sweetalert2'
import { MaskInput } from 'vue-3-mask';
import useValidate from '@vuelidate/core'
import { required, minLength, helpers, minValue } from '@vuelidate/validators';
import { Money3Component } from 'v-money3'
import Datepicker from 'vue3-datepicker' 
import { pt } from 'date-fns/locale';
import moment from 'moment';

  
export default {
  name: 'TransferenciasAgendar',  
  components: {
    MaskInput,
    Datepicker,
    money3: Money3Component,
  },  
  mounted() {
    document.title = 'Agendar Transferência';
  },
  data() {
    return {
      v$: useValidate(),
      transferencia: {
        contaOrigem: '',
        contaDestino: '',
        valorTransferencia: '0',
        dataTransferencia: null,
      },
      isSaving:false,
      pt: pt,
      config: {
        decimal: ',',
        thousands: '.',
        prefix: 'R$ ',
        suffix: '',
        precision: 2,
        masked: false, /* doesn't work with directive */
        disableNegative: false,
        disabled: false,
        min: null,
        max: null,
        allowBlank: false,
        minimumNumberOfCharacters: 0,
        debug: false,
      }
    };
  },
  methods: {      
    agendar() {
      this.v$.$validate() 
      if (!this.v$.$error) {
        this.isSaving = true;

        const transferenciaAgendar = {
          contaOrigem: this.transferencia.contaOrigem,
          contaDestino: this.transferencia.contaDestino,
          valorTransferencia: this.transferencia.valorTransferencia,
          // Substituindo a string pela data convertida
          dataTransferencia: moment(this.transferencia.dataTransferencia).format('DD/MM/YYYY')
        };

        axios.post('/v1/transferencias', transferenciaAgendar)
          .then(() => {
            Swal.fire({
                icon: 'success',
                title: 'Transferência agendada com sucesso!',
                showConfirmButton: false,
                timer: 1500
            })
            this.isSaving = false;
            this.$router.push('/');
          })
          .catch(error => {
            this.isSaving = false;
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
  },
  validations() {
    return {
      transferencia: {
        contaOrigem: {
          required: helpers.withMessage('A conta de origem é obrigatória', required),
          minLength: helpers.withMessage('A conta de origem deve conter 10 números', minLength(10))
        },
        contaDestino: {
          required: helpers.withMessage('A conta de destino é obrigatória', required),
          minLength: helpers.withMessage('A conta de destino deve conter 10 números', minLength(10)),
          differentFromOrigem: helpers.withMessage(
            'A conta de origem e de destino não devem ser a mesma',
            (value) => value !== this.transferencia.contaOrigem
          )
        },
        valorTransferencia: {
          required: helpers.withMessage('O valor da transferência é obrigatório', required),
          minValue: helpers.withMessage('O valor da transferência deve ser maior que zero', minValue(0.01))
        },
        dataTransferencia: {
          required: helpers.withMessage('A data da transferência é obrigatória', required)
        }
      }
    }
  },
};
</script>