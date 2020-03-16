<template>
  <div class="container content-wrapper">
    <h1>Car List</h1>
    <p>List of all cars available in carbase</p>
    <table>
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
            <button class="btn">Edit Car</button
            ><button class="btn">Delete Car</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
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
          error => console.log(error)
        );
    }
  },
  created() {
    // fetch data
    console.log(this.fetchData());
  }
};
</script>

<style scoped></style>
