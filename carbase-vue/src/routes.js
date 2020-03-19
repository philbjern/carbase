import Home from "./components/Home.vue";
import PersonList from "./components/persons/PersonList.vue";
import PersonForm from "./components/persons/PersonForm.vue";
import CarList from "./components/cars/CarList.vue";

export const routes = [
  { path: "/", component: Home },
  { path: "/persons", component: PersonList },
  { path: "/persons/new", component: PersonForm },
  { path: "/persons/:id/edit", component: PersonForm },
  { path: "/cars", component: CarList }
];
