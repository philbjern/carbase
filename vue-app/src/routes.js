import Home from "./components/Home.vue";
import PersonList from "./components/persons/PersonList.vue";
import PersonForm from "./components/persons/PersonForm.vue";
import CarList from "./components/cars/CarList.vue";
import CarForm from "./components/cars/CarForm.vue";
import Login from "./components/Login.vue";

export const routes = [
  { path: "/", component: Home },
  { path: "/persons", component: PersonList },
  { path: "/persons/new", component: PersonForm },
  { path: "/persons/:id", component: PersonForm },
  { path: "/cars", component: CarList },
  { path: "/cars/new", component: CarForm },
  { path: "/cars/:id", component: CarForm },
  { path: "/login", component: Login },
  { path: "*", redirect: "/" }
];
