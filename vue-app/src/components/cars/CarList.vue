<template>
  <div class="container content-wrapper">
    <h1>Cars list</h1>
    <p>Table of all cars available in carbase</p>
    <div class="row">
      <router-link to="cars/new">
        <button class="btn">Add New Car</button>
      </router-link>
    </div>
    <table v-if="carArr.length != 0" class="table">
      <thead>
        <tr>
          <td>Manufacturer</td>
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
          <td>{{ getPrettyFuelType(car.fuelType) }}</td>
          <td>{{ car.engineVolume }}L</td>

          <td>
            <button class="btn" @click="editCar(car.id)">Edit</button>
            <button class="btn" @click="confirmCarDelete(car.id)">
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="message" v-else>
      No data available.
    </div>
    <modal name="delete-car-modal" :width="450" :height="200">
      <div class="p-2 flex-column" style="height: 100%;">
        <h2>Confirm</h2>
        <div class="flex-grow">
          <p class="my-1">
            Are you sure you want to delete car
            <b>{{ carToDelete.make + " " + carToDelete.model }}</b
            >?
          </p>
        </div>
        <div class="text-right">
          <button @click="hideDeleteModal" class="btn btn-light">Cancel</button>
          <button @click="carDeletionConfirmed" class="btn btn-danger">
            Delete
          </button>
        </div>
      </div>
    </modal>
  </div>
</template>

<script>
import { getNotification } from "../../utils";
import { Urls } from "../../urls";

export default {
  data() {
    return {
      carArr: [],
      carToDelete: {}
    };
  },
  methods: {
    fetchData() {
      this.$http
        .get(Urls.APIGATEWAY_SERVICE_URL + "/api/cars")
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
    confirmCarDelete(carId) {
      this.carToDelete = this.carArr.find(car => car.id == carId);
      this.showDeleteModal();
    },
    carDeletionConfirmed() {
      this.hideDeleteModal();
      this.deleteCar(this.carToDelete.id);
    },
    showDeleteModal() {
      this.$modal.show("delete-car-modal");
    },
    hideDeleteModal() {
      this.$modal.hide("delete-car-modal");
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
    },
    getPrettyFuelType(fuelType) {
      if (fuelType.toLowerCase() == "petrol_lpg") {
        return "Petrol+LPG";
      } else {
        return fuelType.slice(0, 1) + fuelType.slice(1).toLowerCase();
      }
    }
  },
  created() {
    this.fetchData();
  }
};
</script>

<style scoped>
tr:hover {
  background-color: rgba(0, 0, 0, 0.04);
}
</style>
