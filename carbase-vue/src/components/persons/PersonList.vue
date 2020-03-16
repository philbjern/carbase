<template>
  <div class="container content-wrapper">
    <h1>Person List</h1>
    <p>List of all carbase users</p>
    <table>
      <thead>
        <tr>
          <td>First Name</td>
          <td>Last Name</td>
          <td>E-mail</td>
          <td>Cars</td>
          <td>Actions</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(person, index) in personArr" :key="index">
          <td>{{ person.firstName }}</td>
          <td>{{ person.lastName }}</td>
          <td>{{ person.email }}</td>
          <td>
            <ul>
              <li v-for="(car, i) in person.cars" :key="i">
                {{ car.make }} {{ car.model }}
              </li>
            </ul>
          </td>
          <td>
            <button class="btn">Edit Person</button
            ><button class="btn">Delete Person</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
const testPersons = [
  {
    firstName: "Jan",
    lastName: "Kowalski",
    email: "jankowalski@gmail.com",
    cars: [
      {
        make: "Toyota",
        model: "Avensis",
        productionYear: 2003,
        fuel: "GASOLINE",
        engineVolume: 1.6
      }
    ]
  }
];

export default {
  data() {
    return {
      personArr: testPersons
    };
  },
  methods: {
    fetchData() {
      this.$http
        .get("persons")
        .then(response => response.json())
        .then(
          data => (this.personArr = data),
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
