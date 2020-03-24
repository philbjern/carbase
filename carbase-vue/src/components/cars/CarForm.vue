<template>
  <div class="container content-wrapper">
    <h1 v-if="this.$route.params.id">Edit Car Info</h1>
    <h1 v-else>New Car</h1>
    <div class="row mt-3 mb-3">
      <form class="form">
        <table class="margin-auto">
          <tbody>
            <tr>
              <td class="fix-width">Make</td>
              <td>
                <input
                  type="text"
                  v-model="car.make"
                  :class="{
                    'missing-field': this.validation.includes('make')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td>Model</td>
              <td>
                <input
                  type="text"
                  v-model="car.model"
                  :class="{
                    'missing-field': this.validation.includes('model')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td>Production Year</td>
              <td>
                <input
                  type="text"
                  v-model="car.productionYear"
                  :class="{
                    'missing-field': this.validation.includes('productionYear')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td>Fuel</td>
              <td>
                <select
                  v-model="car.fuel"
                  :class="{
                    'missing-field': this.validation.includes('fuel')
                  }"
                >
                  <option value="0">Select fuel type</option>
                  <option value="DIESEL">Diesel</option>
                  <option value="PETROL">Petrol</option>
                  <option value="PETROL_LPG">Petrol+LPG</option>
                  <option value="ELECTRIC">Electric</option>
                </select>
              </td>
            </tr>
            <tr>
              <td>Engine Volume</td>
              <td>
                <input
                  type="text"
                  v-model="car.engineVolume"
                  :class="{
                    'missing-field': this.validation.includes('engineVolume')
                  }"
                />
              </td>
            </tr>
            <tr>
              <td colspan="2" class="text-center">
                <button
                  class="btn"
                  @click.prevent="editCarPostRequest"
                  v-if="this.$route.params.id"
                >
                  Edit Car
                </button>
                <button class="btn" @click.prevent="newCarPostRequest" v-else>
                  Add Car
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
      car: {},
      validation: []
    };
  },
  created() {
    if (this.$route.params.id) {
      this.fetchCarData();
    } else {
      this.car = {
        make: "",
        model: "",
        productionYear: "",
        fuel: 0,
        engineVolume: ""
      };
    }
  },
  methods: {
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
      this.validation = [];
      if (this.car.make === "") {
        this.validation.push("make");
        this.$emit("notify", getNotification("error", "Missing field: Make"));
      }
      if (this.car.model === "") {
        this.validation.push("model");
        this.$emit("notify", getNotification("error", "Missing field: Model"));
      }
      if (this.car.productionYear === "") {
        this.validation.push("productionYear");
        this.$emit(
          "notify",
          getNotification("error", "Missing field: Production Year")
        );
      }
      if (this.car.fuel == "0") {
        this.validation.push("fuel");
        this.$emit(
          "notify",
          getNotification("error", "Missing field: Fuel Type")
        );
      }
      if (this.car.engineVolume === "") {
        this.validation.push("engineVolume");
        this.$emit(
          "notify",
          getNotification("error", "Missing field: Engine Volume")
        );
      }
      if (this.validation.length === 0) {
        return true;
      }
      return false;
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
}
</style>
