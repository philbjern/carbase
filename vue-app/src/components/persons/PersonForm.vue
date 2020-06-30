<template>
  <div class="container content-wrapper">
    <div class="form-wrapper">
      <h1 v-if="this.$route.params.id">Edit Person Profile</h1>
      <h1 v-else>Add New Person</h1>
      <button class="btn btn-primary mt-1" @click="rerouteToPersonList">
        Go back
      </button>
      <form class="form mt-3">
        <div class="row">
          <h2 class="mb-1">First Name</h2>
          <input
            type="text"
            v-model="person.firstName"
            :class="{
              error: this.validationMessages.firstName.length > 0
            }"
          />
          <div v-if="this.validationMessages.firstName.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in this.validationMessages.firstName"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Last Name</h2>
          <input
            type="text"
            v-model="person.lastName"
            :class="{
              error: this.validationMessages.lastName.length > 0
            }"
          />
          <div v-if="this.validationMessages.lastName.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in this.validationMessages.lastName"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Email</h2>
          <input
            type="text"
            v-model="person.email"
            :class="{
              error: this.validationMessages.email.length > 0
            }"
          />
          <div v-if="this.validationMessages.email.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in this.validationMessages.email"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Cars</h2>
          <div class="flex-container">
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
            <button class="btn flex-shrink" @click.prevent="addCar()">
              Add Car
            </button>
          </div>
          <div class="row">
            <div
              class="pill"
              v-for="(car, index) in person.carsList"
              :key="index"
            >
              {{ car.make }} {{ car.model }}
              <i class="fa fa-times-circle" @click="removeCar(car.id)"></i>
            </div>
          </div>
        </div>

        <div class="row my-4">
          <button
            class="btn btn-wide"
            @click.prevent="editPerson"
            v-if="this.$route.params.id"
          >
            Edit Person
          </button>
          <button class="btn btn-wide" @click.prevent="addPerson" v-else>
            Add Person
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
//TODO: change to actually use vuelidate
import { contains, getNotification, scrollTop } from "../../utils";

export default {
  data() {
    return {
      person: {},
      cars: [],
      carToAddId: 0,
      validation: [],
      validationMessages: {
        firstName: [],
        lastName: [],
        email: []
      }
    };
  },
  computed: {
    availableCars() {
      let result = [];
      if (this.person.carsList !== undefined) {
        for (let car of this.cars) {
          if (!contains(this.person.carsList, car.id)) {
            result.push(car);
          }
        }
      }
      return result;
    }
  },
  created() {
    if (this.$route.params.id) {
      this.fetchPersonData();
    } else {
      this.person = { firstName: "", lastName: "", email: "", carsList: [] };
    }
    this.fetchAllCars();
    scrollTop();
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
        this.person.carsList.push(car);
        this.carToAddId = 0;
      }
    },
    removeCar(id) {
      let indexToRemove = -1;
      for (let i = 0; i < this.person.carsList.length; i++) {
        if (this.person.carsList[i].id === id) {
          indexToRemove = i;
          break;
        }
      }
      if (indexToRemove >= 0) {
        this.person.carsList.splice(indexToRemove, 1);
      }
    },
    clearValidationMessages() {
      this.validationMessages = {
        firstName: [],
        lastName: [],
        email: []
      };
    },
    isFormValid() {
      for (const field in this.validationMessages) {
        if (this.validationMessages[field].length > 0) {
          return false;
        }
      }
      return true;
    },
    isValidEmail(email) {
      const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(email).toLowerCase());
    },
    validate() {
      this.validation = [];
      this.clearValidationMessages();
      if (this.person.firstName === "") {
        this.validation.push("firstName");
        this.validationMessages.firstName.push("First name is required");
      }
      if (this.person.lastName === "") {
        this.validation.push("lastName");
        this.validationMessages.lastName.push("Last name is required");
      }
      if (this.person.email === "") {
        this.validation.push("email");
        this.validationMessages.email.push("Email is required");
      }
      if (!this.isValidEmail(this.person.email)) {
        this.validation.push("email");
        this.validationMessages.email.push("Please enter valid email address");
      }

      return this.isFormValid();
    },
    editPerson() {
      if (this.validate()) {
        const url = `persons/${this.person.id}`;
        this.$http.put(url, this.person).then(
          success => {
            this.$emit(
              "notify",
              getNotification("success", "Sucessfully edited person profile")
            );
            this.rerouteToPersonList();
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
            this.rerouteToPersonList();
          },
          error => {
            this.$emit(
              "notify",
              getNotification("error", "Error while adding new person")
            );
          }
        );
      }
    },
    rerouteToPersonList() {
      this.$router.push({ path: "/persons" });
    }
  }
};
</script>

<style scoped>
.flex-container {
  display: flex;
}

.flex-container select {
  width: auto;
}

.flex-grow {
  flex: 1 0 auto;
}

.flex-shrink {
  flex: 0 1 auto;
}
</style>
