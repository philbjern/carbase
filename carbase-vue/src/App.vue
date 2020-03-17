<template>
  <div>
    <div class="app-body">
      <app-header></app-header>
      <app-notifications
        :notifications="notifications"
        @notificationremoved="removeNotification($event)"
      ></app-notifications>
      <transition name="fade" mode="out-in">
        <router-view @notify="notify($event)"></router-view>
      </transition>
    </div>
    <footer class="footer">
      <p class="text-center">Carbase &copy; {{ new Date().getFullYear() }}</p>
    </footer>
  </div>
</template>

<script>
import Header from "./components/Header.vue";
import Notifications from "./components/Notifications.vue";

const NOTIFICATION_TIMEOUT = 4000;

export default {
  data() {
    return {
      notifications: [
        {
          type: "error",
          message: "Error occured while deleting person"
        },
        {
          type: "success",
          message: "Successfully edited car info"
        }
      ]
    };
  },
  components: {
    appHeader: Header,
    appNotifications: Notifications
  },
  methods: {
    removeNotification(index) {
      this.notifications.splice(index, 1);
    },
    notify(notification) {
      this.notifications.push(notification);
      // const id = this.notifications.length - 1;
      // setTimeout(() => {
      //   this.removeNotification(id);
      // }, NOTIFICATION_TIMEOUT);
    }
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

.app-body {
  min-height: 95vh;
}

.row {
  width: 100%;
}

.content-wrapper {
  background: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.05);
  padding: 1rem 2rem;
}

.container {
  max-width: 1200px;
  min-width: 850px;
  margin: auto;
}

.container.content-wrapper {
  margin-top: 1rem;
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
  color: #c21f40;
  background: #f9dc5c;
  font-size: 1rem;
  padding: 0.75rem 1rem;
  border: 0;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.25s;
  margin: 0 0.2rem 0 0;
}

.btn:hover {
  background: rgba(0, 0, 0, 0.12);
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

.table {
  width: 100%;
  border-spacing: 0;
  margin: 1rem 0;
}

.table thead td {
  font-weight: bold;
  background: #fff;
}

.table tr:nth-child(odd) {
  background-color: #fff8d9;
}

.table td {
  text-align: center;
  width: auto;
  padding: 1rem;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.table td ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.table td ul li {
  padding: 0.25rem;
}

a {
  color: inherit;
}

.footer {
  color: rgba(0, 0, 0, 0.45);
  font-size: 0.8rem;
  padding: 1rem;
}

.narrow {
  padding: 0 13rem;
}

.narrow p {
  line-height: 2rem;
  text-align: justify;
}

.mt-3 {
  margin-top: 3rem;
}

.message {
  text-align: center;
  padding: 5rem 0;
  font-size: 2.5rem;
  color: rgba(0, 0, 0, 0.15);
}
</style>
