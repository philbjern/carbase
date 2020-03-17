<template>
  <div class="container content-wrapper">
    <h1>Person List</h1>
    <p>List of all carbase users</p>
    <div v-if="personArr.length != 0">
      <app-person-card
        v-for="(person, i) in personArr"
        :key="i"
        :person="person"
      ></app-person-card>
    </div>
    <div v-else class="message">
      No data available.
    </div>
  </div>
</template>

<script>
import PersonCard from "./PersonCard.vue";

import { getNotification } from "../../utils";

export default {
  data() {
    return {
      personArr: []
    };
  },
  components: {
    appPersonCard: PersonCard
  },
  methods: {
    fetchData() {
      this.$http
        .get("persons")
        .then(response => response.json())
        .then(
          data => (this.personArr = data),
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification("error", "Could not load users data.")
            );
          }
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
