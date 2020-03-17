<template>
  <div class="card" v-if="person">
    <div class="row">
      <img src="../../../static/img/user.png" alt="" class="user-image" />
      <div class="info flex-grow">
        <p class="text-bigger">{{ person.firstName }} {{ person.lastName }}</p>
        <p class="text-bigger">{{ person.email }}</p>
        <p>
          Number of cars
          <span class="text-bigger">{{ person.cars.length }}</span>
        </p>
        <p>Cars</p>
        <ul>
          <li v-for="(car, idx) in person.cars" :key="idx">
            {{ car.make }} {{ car.model }}
          </li>
        </ul>
      </div>
    </div>
    <div class="row flex-end">
      <div class="buttons">
        <button class="btn">Edit Profile</button>
        <button class="btn">Delete Person</button>
      </div>
    </div>
    <!-- <table>
      <tbody>
        <tr>
          <td>First Name</td>
          <td>
            <span class="text-bigger">{{ person.firstName }}</span>
          </td>
          <td>E-mail</td>
          <td>
            <span class="text-bigger">{{ person.email }}</span>
          </td>
        </tr>
        <tr>
          <td>Last Name</td>
          <td>
            <span class="text-bigger">{{ person.lastName }}</span>
          </td>
          <td>Number of cars</td>
          <td>
            <span class="text-bigger">{{ person.cars.length }}</span>
          </td>
        </tr>
      </tbody>
    </table> -->
  </div>
  <div v-else>
    <p>No data</p>
  </div>
</template>

<script>
export default {
  props: ["person"]
};
</script>

<style scoped>
.text-bigger {
  font-size: 2.1rem;
}

.user-image {
  width: 200px;
  height: 200px;
  margin: 1rem;
}

table {
  width: 100%;
}

td {
  min-width: 150px;
}

.flex-end {
  justify-content: flex-end;
}

.flex-grow {
  flex: 1 0 auto;
}

.card {
  max-width: 90%;
  margin: 1.5rem auto;
  padding: 1rem;
  border: 1px solid lightgray;
}

.card .row {
  display: flex;
  align-items: center;
}

@media screen and (max-width: 1120px) {
  .buttons {
    display: block;
  }
}
</style>
