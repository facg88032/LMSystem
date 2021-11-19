import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from './store'
import * as ElIconModules from '@element-plus/icons'
import { transElIconName } from './utils/utils.js'

const axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'
axios.defaults.withCredentials = true

const app = createApp(App)
app.config.globalProperties.$axios = axios
for(let iconName in ElIconModules){
    app.component(transElIconName(iconName),ElIconModules[iconName])
}
app.use(store)
app.use(ElementPlus);
app.use(router)
app.mount('#app')
