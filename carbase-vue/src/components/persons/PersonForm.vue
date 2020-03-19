<template>
  <div class="container content-wrapper">
    <h1 v-if="this.$route.params.id">Edit Person Profile</h1>
    <h1 v-else>New Person</h1>
    <div class="row mt-3 mb-3">
      <form class="form">
        <table class="margin-auto">
          <tbody>
            <tr>
              <td class="fix-width">First Name</td>
              <td>
                <input
                  type="text"
                  v-model="person.firstName"
                  :class="{
                    'missing-field': this.validation.includes('firstName')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td>Last Name</td>
              <td>
                <input
                  type="text"
                  v-model="person.lastName"
                  :class="{
                    'missing-field': this.validation.includes('lastName')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td>Email</td>
              <td>
                <input
                  type="text"
                  v-model="person.email"
                  :class="{
                    'missing-field': this.validation.includes('email')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td>Cars</td>
              <td class="flex">
                <select
                  name=""
                  id=""
                  class="flex-grow"
                  value="0"
                  v-model="carToAddId"
                >
                  <option value="0">Select Car</option>
                  <option
                    v-for="(car, idx) in availableCars"
                    :key="idx"
                    :value="car.id"
                    >{{ car.make }} {{ car.model }} - {{ car.productionYear }},
                    {{ car.engineVolume }}L</option
                  >
                </select>
                <button class="btn" @click.prevent="addCar()">
                  Add Car
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <div
                  class="pill"
                  v-for="(car, index) in person.cars"
                  :key="index"
                >
                  {{ car.make }} {{ car.model }}
                  <i class="fa fa-times-circle" @click="removeCar(car.id)"></i>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="2" class="text-center">
                <button
                  class="btn"
                  @click.prevent="editPerson"
                  v-if="this.$route.params.id"
                >
                  Edit Person
                </button>
                <button class="btn" @click.prevent="addPerson" v-else>
                  Add Person
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>
  </div>
</template>

<script>
import { contains, getNotification } from "../../utils";

export default {
  data() {
    return {
      person: {},
      cars: [],
      carToAddId: 0,
      validation: []
    };
  },
  computed: {
    availableCars() {
      let result = [];
      for (let car of this.cars) {
        if (!contains(this.person.cars, car.id)) {
          result.push(car);
        }
      }
      return result;
    }
  },
  created() {
    if (this.$route.params.id) {
      this.fetchPersonData();
    } else {
      this.person = { firstName: "", lastName: "", email: "", cars: [] };
    }
    this.fetchAllCars();
  },
  methods: {
    fetchPersonData() {
      const url = `persons/${this.$route.params.id}`;
      this.$http
        .get(url)
        .then(response => response.json())
        .then(
          data => (this.person = data),
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification("error", "Could not load user data.")
            );
          }
        );
    },
    fetchAllCars() {
      this.$http
        .get("cars")
        .then(response => response.json())
        .then(
          data => (this.cars = data),
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification("error", "Could not load car data.")
            );
          }
        );
    },
    getCar(carId, carArr) {
      for (let car of carArr) {
        if (car.id === carId) {
          return car;
        }
      }
      return {};
    },
    addCar() {
      let car = this.getCar(this.carToAddId, this.availableCars);
      if (car.id) {
        this.person.cars.push(car);
        this.carToAddId = 0;
      }
    },
    removeCar(id) {
      let indexToRemove = -1;
      for (let i = 0; i < this.person.cars.length; i++) {
        if (this.person.cars[i].id === id) {
          indexToRemove = i;
          break;
        }
      }
      if (indexToRemove >= 0) {
        this.person.cars.splice(indexToRemove, 1);
      }
    },
    validate() {
      this.validation = [];
      if (this.person.firstName === "") {
        this.validation.push("firstName");
        this.$emit(
          "notify",
          getNotification("error", "Missing field: First Name")
        );
      }
      if (this.person.lastName === "") {
        this.validation.push("lastName");
        this.$emit(
          "notify",
          getNotification("error", "Missing field: Last Name")
        );
      }
      if (this.person.email === "") {
        this.validation.push("email");
        this.$emit("notify", getNotification("error", "Missing field: Email"));
      }
      if (this.validation.length === 0) {
        return true;
      }
      return false;
    },
    editPerson() {
      if (this.validate()) {
        const url = `persons/${this.person.id}`;
        this.$http.put("persons", this.person).then(
          success => {
            this.$emit(
              "notify",
              getNotification("success", "Sucessfully edited person profile")
            );
          },
          error => {
            this.$emit(
              "notify",
              getNotification("error", "Error while updating person profile")
            );
          }
        );
      }
    },
    addPerson() {
      if (this.validate()) {
        this.$http.post("persons", this.person).then(
          success => {
            this.$emit(
              "notify",
              getNotification("success", "Sucessfully added new person")
            );
          },
          error => {
            this.$emit(
              "notify",
              getNotification("error", "Error while adding new person")
            );
          }
        );
      }
    }
  }
};
</script>

<style scoped>
.pill {
  padding: 0.5rem 1rem;
  display: inline-block;
  margin: 0.2rem;
  background: rgba(0, 0, 0, 0.15);
  border-radius: 5px;
  position: relative;
}

.pill i {
  opacity: 0;
  position: absolute;
  right: -5px;
  top: -5px;
  transition: all 0.25s;
  cursor: pointer;
}

.pill:hover i {
  opacity: 1;
  color: black;
}

.form input {
  border: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.25);
  padding: 0.5rem 0.5rem;
  font-size: 1rem;
  width: 100%;
  position: relative;
}

.form table {
  width: 700px;
}

.form table td {
  padding: 1.5rem 0.5rem;
}

.form table .fix-width {
  width: 150px;
}

.form select {
  background: white;
  font-size: 1rem;
}

.form input.missing-field {
  border-bottom: 1px solid lightcoral;
}

.missing-field::after {
  color: lightcoral;
  position: absolute;
  content: "<div class='tooltip'>Missing field</div>";
  display: block;
  right: 0px;
  top: 0px;
  width: 10px;
  height: 10px;
  background: black;
}
</style>
