import Home from "./components/Home.vue";
import PersonList from "./components/persons/PersonList.vue";
import CarList from "./components/cars/CarList.vue";

export const routes = [
  { path: "/", component: Home },
  { path: "/persons", component: PersonList },
  { path: "/cars", component: CarList }
];
