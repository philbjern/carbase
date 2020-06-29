<template>
  <div class="card" v-if="person">
    <modal
      :name="'delete-person-modal-' + person.id"
      :width="400"
      :height="200"
    >
      <div class="p-2 flex-column" style="height: 100%;">
        <h2>Confirm</h2>
        <div class="flex-grow">
          <p class="my-1">
            Are you sure you want to delete person
            <b>{{ person.firstName + " " + person.lastName }}</b
            >?
          </p>
        </div>
        <div class="text-right">
          <button @click="hideDeleteModal" class="btn btn-light">Cancel</button>
          <button @click="deletionConfirmed" class="btn btn-danger">
            Delete
          </button>
        </div>
      </div>
    </modal>
    <div class="row">
      <img src="../../../static/img/user.png" alt="" class="user-image" />
      <div class="info flex-grow">
        <p class="text-bigger">{{ person.firstName }} {{ person.lastName }}</p>
        <p>
          <a :href="'mailto:' + person.email">{{ person.email }}</a>
        </p>
        <p v-if="person.registeredOn">
          Carbase user since
          {{ getTimeSinceRegistered(person.registeredOn) }}
        </p>
        <p>
          Number of cars
          <span class="text-bigger">{{ person.carsList.length }}</span>
        </p>
        <div v-if="person.carsList.length > 0">
          <p>Cars</p>
          <ul class="car-list">
            <li v-for="(car, idx) in person.carsList" :key="idx">
              {{ car.make }} {{ car.model }}
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="row flex-end">
      <div class="buttons">
        <button class="btn" @click="editProfile">Edit Profile</button>
        <button class="btn" @click="showDeleteModal">Delete Person</button>
      </div>
    </div>
  </div>
  <div v-else>
    <p>No data</p>
  </div>
</template>

<script>
import { getNotification, scrollTop } from "../../utils";
import VModal from "vue-js-modal";

const monthNames = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December"
];

export default {
  props: ["person"],

  methods: {
    editProfile() {
      this.$router.push({ path: `/persons/${this.person.id}` });
    },
    showDeleteModal() {
      this.$modal.show("delete-person-modal-" + this.person.id);
    },
    hideDeleteModal() {
      this.$modal.hide("delete-person-modal-" + this.person.id);
    },
    deletionConfirmed() {
      this.hideDeleteModal();
      this.deletePerson();
    },
    deletePerson() {
      const url = `persons/${this.person.id}`;
      console.log(url);
      this.$http.delete(url).then(
        () => {
          this.$emit(
            "notify",
            getNotification("success", "Successfully deleted person profile")
          );
          this.$emit("ondelete", this.person.id);
          scrollTop();
        },
        error => {
          this.$emit(
            "notify",
            getNotification("error", "Error while deleting person profile")
          );
        }
      );
    },
    getTimeSinceRegistered(timestamp) {
      if (timestamp != null) {
        const registrationDate = new Date(Date.parse(timestamp));
        return (
          registrationDate.getDate() +
          " " +
          monthNames[registrationDate.getMonth()] +
          " " +
          registrationDate.getFullYear()
        );
      }
      return "unknown";
    }
  }
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

.card {
  max-width: 60%;
  margin: 1.5rem auto;
  padding: 1rem;
  border: 5px solid #f9dc5c;
  box-shadow: 5px 5px 0 rgba(0, 0, 0, 0.05);
}

.card .row {
  display: flex;
  align-items: center;
}

@media screen and (max-width: 900px) {
  .buttons {
    display: block;
  }

  .card {
    max-width: 100%;
  }
}

.car-list {
  margin: 0;
  padding: 0 0 0 1rem;
  list-style: none;
}
</style>
