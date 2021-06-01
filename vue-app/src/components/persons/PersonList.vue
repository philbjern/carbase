<template>
  <div class="container content-wrapper">
    <h1>Car users list</h1>
    <p>List of all carbase users</p>
    <router-link to="persons/new">
      <button class="btn">Add New Person</button>
    </router-link>
    <div class="cards-container" v-if="personArr.length != 0">
      <app-person-card
        v-for="(person, i) in personArr"
        :key="i"
        :person="person"
        @notify="notify($event)"
        @ondelete="deletePerson($event)"
      ></app-person-card>
    </div>
    <div v-else class="message">
      No data available.
    </div>
  </div>
</template>

<script>
import PersonCard from "./PersonCard.vue";
import { getNotification, Utils } from "../../utils";
import { Urls } from "../../urls";

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
      const url = `${Urls.APIGATEWAY_SERVICE_URL}/api/persons`;
      console.log(url);
      this.$http
        .get(url)
        .then(response => response.json())
        .then(
          data => {
            this.personArr = data;
          },
          error => {
            console.log(error);
            this.$emit(
              "notify",
              getNotification("error", "Could not load users data.")
            );
          }
        );
    },
    deletePerson(personId) {
      let indexToRemove = -1;
      for (let i = 0; i < this.personArr.length; i++) {
        if (this.personArr[i].id === personId) {
          indexToRemove = i;
          break;
        }
      }
      if (indexToRemove >= 0) {
        this.personArr.splice(indexToRemove, 1);
      }
    },
    notify(notification) {
      this.$emit("notify", notification);
    }
  },
  created() {
    this.fetchData();
  },
  mounted() {
    Utils.scrollTop();
  }
};
</script>

<style scoped>
.cards-container {
  display: flex;
  flex-wrap: wrap;
}
</style>
