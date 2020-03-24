<template>
  <div class="container content-wrapper">
    <h1>Car List</h1>
    <p>Table of all cars available in carbase</p>
    <div class="row">
      <router-link to="cars/new">
        <button class="btn">Add New Car</button>
      </router-link>
    </div>
    <table v-if="carArr.length != 0" class="table">
      <thead>
        <tr>
          <td>Make</td>
          <td>Model</td>
          <td>Production Year</td>
          <td>Fuel Type</td>
          <td>Engine Volume</td>
          <td>Actions</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(car, index) in carArr" :key="index">
          <td>{{ car.make }}</td>
          <td>{{ car.model }}</td>
          <td>{{ car.productionYear }}</td>
          <td>{{ car.fuel.toLowerCase() }}</td>
          <td>{{ car.engineVolume }}L</td>
          <td>
            <button class="btn" @click="editCar(car.id)">Edit Car</button>
            <button class="btn" @click="deleteCar(car.id)">Delete Car</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="message" v-else>
      No data available.
    </div>
  </div>
</template>

<script>
import { getNotification } from "../../utils";

export default {
  data() {
    return {
      carArr: []
    };
  },
  methods: {
    fetchData() {
      this.$http
        .get("cars")
        .then(response => response.json())
        .then(
          data => (this.carArr = data),
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification("error", "Could not load car data.")
            );
          }
        );
    },
    editCar(carId) {
      this.$router.push({ path: `cars/${carId}` });
    },
    deleteCar(carId) {
      this.$http
        .delete(`cars/${carId}`)
        .then(response => response)
        .then(
          success => {
            // splice carArr, push notification
            let arrIdx = -1;
            for (let i = 0; i < this.carArr.length; i++) {
              if (this.carArr[i].id === carId) {
                arrIdx = i;
                break;
              }
            }
            if (arrIdx > -1) {
              this.carArr.splice(arrIdx, 1);
              this.$emit(
                "notify",
                getNotification(
                  "success",
                  "Successfully deleted car with id " + carId
                )
              );
            }
          },
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification(
                "error",
                "Error while deleting car with id " + carId
              )
            );
          }
        );
    }
  },
  created() {
    // fetch data
    this.fetchData();
  }
};
</script>

<style scoped></style>
