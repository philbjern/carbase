import Vue from "vue";
import VueRouter from "vue-router";
import VueResource from "vue-resource";
import App from "./App.vue";
import { routes } from "./routes";
import { config } from "./config";

Vue.use(VueRouter);
const router = new VueRouter({
  routes,
  mode: "history"
});

Vue.use(VueResource);
Vue.http.options.root = config.apiBaseUrl;

new Vue({
  el: "#app",
  router,
  render: h => h(App)
});
