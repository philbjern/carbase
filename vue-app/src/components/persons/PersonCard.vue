<template>
  <div class="card flex-column" v-if="person">
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
    <div class="row flex-grow">
      <img src="../../../static/img/user.png" alt="" class="user-image" />
      <div class="info">
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
          <span class="text-big">{{ person.carsList.length }}</span>
        </p>
        <div v-if="person.carsList.length > 0">
          <p>Cars</p>
          <div class="car-list">
            <div v-for="(car, idx) in person.carsList" :key="idx">
              {{ car.make }} {{ car.model }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row flex-end mt-1">
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
      if (timestamp == null) {
        return "unknown";
      }
      const registrationDate = new Date(Date.parse(timestamp));
      return (
        registrationDate.getDate() +
        " " +
        monthNames[registrationDate.getMonth()] +
        " " +
        registrationDate.getFullYear()
      );
    }
  }
};
</script>

<style scoped>
.text-bigger {
  font-size: 2.1rem;
}

.text-big {
  font-size: 1.5rem;
}

.user-image {
  width: 150px;
  height: 150px;
  margin: 0.5rem;
}

table {
  width: 100%;
}

td {
  min-width: 150px;
}

.card {
  max-width: 48%;
  width: 100%;
  margin: 1.5rem auto;
  padding: 1rem;
  border: 5px solid #f9dc5c;
  /* border: 5px solid rgba(0, 0, 0, 0.25); */
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
    max-width: 80%;
  }
}

.car-list {
  margin: 0;
  padding: 0 0 0 1rem;
  display: flex;
  flex-wrap: wrap;
}

.car-list div {
  padding: 0.5rem;
  margin: 0.2rem 0.2rem 0 0;
  border: 2px solid rgba(0, 0, 0, 0.15);
  border-radius: 5px;
}
</style>
