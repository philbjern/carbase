<template>
  <div class="container content-wrapper">
    <div class="form-wrapper">
      <h1 v-if="this.$route.params.id">Edit Car Info</h1>
      <h1 v-else>Add New Car</h1>
      <button class="btn btn-primary mt-1" @click="rerouteToCarList">
        Go back
      </button>
      <form class="form mt-3">
        <div class="row">
          <h2 class="mb-1">Manufacturer</h2>
          <input
            type="text"
            v-model="car.make"
            :class="{
              error: this.validationMessages.make.length > 0
            }"
          />
          <div v-if="this.validationMessages.make.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in validationMessages.make"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Model</h2>
          <input
            type="text"
            v-model="car.model"
            :class="{
              error: this.validationMessages.model.length > 0
            }"
          />
          <div v-if="this.validationMessages.model.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in validationMessages.model"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Production Year</h2>
          <input
            type="text"
            v-model="car.productionYear"
            :class="{
              error: this.validationMessages.productionYear.length > 0
            }"
          />
          <div v-if="this.validationMessages.productionYear.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in validationMessages.productionYear"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Fuel</h2>
          <select
            v-model="car.fuelType"
            :class="{
              error: this.validationMessages.fuel.length > 0
            }"
          >
            <option value="0">Select fuel type</option>
            <option value="DIESEL">Diesel</option>
            <option value="PETROL">Petrol</option>
            <option value="PETROL_LPG">Petrol+LPG</option>
            <option value="HYBRID">Hybrid</option>
            <option value="ELECTRIC">Electric</option>
          </select>
          <div v-if="this.validationMessages.fuel.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in validationMessages.fuel"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row">
          <h2 class="mb-1">Engine Volume</h2>
          <input
            type="text"
            v-model="car.engineVolume"
            :class="{
              error: this.validationMessages.engineVolume.length > 0
            }"
          />
          <div v-if="this.validationMessages.engineVolume.length > 0">
            <p
              class="error-message"
              v-for="(message, i) in validationMessages.engineVolume"
              :key="i"
            >
              {{ message }}
            </p>
          </div>
        </div>

        <div class="row my-4">
          <button
            class="btn btn-wide"
            @click.prevent="editCarPostRequest"
            v-if="this.$route.params.id"
          >
            Edit Car
          </button>
          <button
            class="btn btn-wide"
            @click.prevent="newCarPostRequest"
            v-else
          >
            Add New Car
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { getNotification, scrollTop } from "../../utils";

export default {
  data() {
    return {
      car: {},
      validationMessages: {
        make: [],
        model: [],
        productionYear: [],
        fuel: [],
        engineVolume: []
      }
    };
  },
  created() {
    scrollTop();
    if (this.$route.params.id) {
      this.fetchCarData();
    } else {
      this.car = {
        make: "",
        model: "",
        productionYear: "",
        fuelType: 0,
        engineVolume: ""
      };
    }
  },
  methods: {
    clearValidationMessages() {
      this.validationMessages = {
        make: [],
        model: [],
        productionYear: [],
        fuel: [],
        engineVolume: []
      };
    },
    isFormValid(messages) {
      for (const key in messages) {
        if (messages[key].length != 0) {
          return false;
        }
      }
      return true;
    },
    fetchCarData() {
      const url = `cars/${this.$route.params.id}`;
      this.$http
        .get(url)
        .then(response => response.json())
        .then(
          data => (this.car = data),
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification("error", "Could not load car data.")
            );
          }
        );
    },
    validate() {
      this.clearValidationMessages();
      if (this.car.make === "") {
        this.validationMessages.make.push("This field is required");
      }
      if (this.car.model === "") {
        this.validationMessages.model.push("This field is required");
      }
      if (this.car.productionYear === "") {
        this.validationMessages.productionYear.push("This field is required");
      } else {
        const year = this.car.productionYear.split("");
        if (year.length != 4) {
          this.validationMessages.productionYear.push(
            "Production year must be 4 digits"
          );
        }
        for (const digit of year) {
          if (!Number.isInteger(parseInt(digit))) {
            this.validationMessages.productionYear.push(
              "Production year must contain only digits"
            );
            break;
          }
        }
      }

      if (this.car.fuelType == "0") {
        this.validationMessages.fuel.push("This field is required");
      }
      if (this.car.engineVolume === "") {
        this.validationMessages.engineVolume.push("This field is required");
      } else {
        const value = this.car.engineVolume;
        console.log(parseFloat(value));
        if (isNaN(parseFloat(value))) {
          this.validationMessages.engineVolume.push(
            "Engine volume must be a number"
          );
        }
      }

      return this.isFormValid(this.validationMessages);
    },
    editCarPostRequest() {
      if (this.validate()) {
        const url = `cars/${this.car.id}`;
        this.$http.put("cars", this.car).then(
          success => {
            this.$emit(
              "notify",
              getNotification("success", "Sucessfully edited car info")
            );
            this.rerouteToCarList();
          },
          error => {
            this.$emit(
              "notify",
              getNotification("error", "Error while updating car info")
            );
          }
        );
      }
    },
    newCarPostRequest() {
      if (this.validate()) {
        this.$http.post("cars", this.car).then(
          success => {
            this.$emit(
              "notify",
              getNotification("success", "Sucessfully added new car")
            );
            this.rerouteToCarList();
          },
          error => {
            this.$emit(
              "notify",
              getNotification("error", "Error while adding new car")
            );
          }
        );
      }
    },
    rerouteToCarList() {
      this.$router.push({ path: "/cars" });
    }
  }
};
</script>

<style scoped>
.form select {
  width: 100%;
  padding: 0.5rem 0.5rem;
  border: 2px solid rgba(0, 0, 0, 0.08);
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.05);
}

.form select option {
  padding: 0.5rem 0.5rem;
}

.form-wrapper {
  width: 60%;
  margin: auto;
  min-width: 600px;
}

.form {
  margin-top: 3em;
}

.form h2 {
  font-family: Roboto, sans-serif;
  font-size: 1.1rem;
}

.form input {
  border: 2px solid rgba(0, 0, 0, 0.08);
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.05);
}

.form .row {
  margin: 2em 0 2em 0;
}

.form button {
  width: 100%;
}

input.error {
  border-color: lightcoral;
}

select.error {
  border-color: lightcoral;
}

.error-message {
  color: lightcoral;
}
</style>
