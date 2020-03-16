<template>
  <div>
    <app-header></app-header>
    <div class="content">
      <div
        class="notification"
        v-for="(notification, i) in notifications"
        :key="i"
      >
        {{ notification.message }}
      </div>
    </div>
    <transition name="fade" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import Header from "./components/Header.vue";

export default {
  data() {
    return {
      notifications: [
        {
          type: "error",
          message: "Error occured while deleting person"
        }
      ]
    };
  },
  components: {
    appHeader: Header
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Playfair+Display|Roboto");

body {
  margin: 0;
  color: rgba(0, 0, 0, 0.85);
  font-family: Roboto, sans-serif;
  min-height: 100vh;
  background-color: #f4fffd;
}

h1,
h2,
h3,
h4,
h5,
h6 {
  font-family: "Playfair Display", serif;
  margin: 1rem 0;
  font-weight: lighter;
}

p {
  line-height: 1.5rem;
}

.row {
  width: 100%;
}

.content-wrapper {
  background: rgba(255, 255, 255, 0.8);
  border-radius: 5px;
  padding: 1rem 2rem;
}

.container {
  max-width: 1200px;
  min-width: 500px;
  margin: auto;
}

.shadow {
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
}

.text-yellow {
  color: #f9dc5c;
}

.text-center {
  text-align: center;
}

.btn {
  background: #f9dc5c;
  padding: 0.75rem 1rem;
  border: 0;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.25s;
  margin: 0 0.2rem 0 0;
}

.btn:hover {
  color: #ed254e;
}

.fade-enter {
  opacity: 0;
}

.fade-enter-active {
  transition: opacity 0.5s;
}

.fade-leave {
}

.fade-leave-active {
  opacity: 0;
  transition: opacity 0.5s;
}

table {
  width: 100%;
  border-spacing: 0;
}

thead td {
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: rgba(0, 0, 0, 0.05);
}

td {
  text-align: center;
  width: auto;
  padding: 1rem;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

td ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

td ul li {
  padding: 0.25rem;
}
</style>
