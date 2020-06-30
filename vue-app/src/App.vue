<template>
  <div>
    <div class="app-body">
      <app-header></app-header>
      <app-notifications
        :notifications="notifications"
        @notificationremoved="removeNotification($event)"
      ></app-notifications>
      <transition name="fade" mode="out-in">
        <router-view @notify="notify($event)" ref="routerView"></router-view>
      </transition>
    </div>
    <footer class="footer">
      <p class="text-center">Carbase &copy; {{ new Date().getFullYear() }}</p>
    </footer>
    <transition name="fade" mode="out-in">
      <div class="gotop" @click="scrollUp" v-if="showGoTopButton">
        <i class="fa fa-arrow-up"></i>
      </div>
    </transition>
  </div>
</template>

<script>
import Header from "./components/Header.vue";
import Notifications from "./components/Notifications.vue";
import { Utils } from "./utils";

const NOTIFICATION_TIMEOUT = 3000;

export default {
  data() {
    return {
      notifications: [],
      showGoTopButton: false
    };
  },
  mounted() {
    let _this = this;
    window.onscroll = function(e) {
      if (
        window.innerHeight + window.scrollY >=
          0.95 * document.body.offsetHeight &&
        document.body.offsetHeight >= 1.25 * window.innerHeight
      ) {
        // you're at the bottom of the page
        _this.showGoTopButton = true;
      } else {
        if (_this.showGoTopButton == true) {
          _this.showGoTopButton = false;
        }
      }
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
      const timeout = setTimeout(() => {
        // clear notifications
        this.notifications = this.notifications.filter(n => n != notification);
        clearTimeout(timeout);
      }, NOTIFICATION_TIMEOUT);
    },
    scrollUp() {
      Utils.scrollTopSmooth();
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Playfair+Display|Roboto");

* {
  box-sizing: border-box;
}

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
  margin: 0;
  font-weight: lighter;
}

p {
  line-height: 1.5rem;
}

.mt-1 {
  margin: 1rem 0 0 0 !important;
}

.mt-3 {
  margin: 3rem 0 0 0 !important;
}

.mb-1 {
  margin: 0 0 1rem 0;
}

.my-3 {
  margin: 3rem 0 3rem 0 !important;
}

.my-4 {
  margin: 4rem 0 4rem 0 !important;
}

.mt-3 {
  margin: 3rem 0 0 0;
}

.mt-2 {
  margin: 2rem 0 0 0;
}

.mb-3 {
  margin: 0 0 3rem 0;
}

.my-1 {
  margin: 1rem 0 1rem 0;
}

.p-1 {
  padding: 1rem;
}

.p-2 {
  padding: 2rem;
}

.p-3 {
  padding: 3rem;
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
  padding: 2rem;
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

.text-right {
  text-align: right;
}

.text-gray {
  color: rgba(0, 0, 0, 0.65);
}

.btn {
  color: #000;
  background: #f9dc5c;
  font-size: 1rem;
  padding: 0.75rem 1rem;
  border: 0;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.25s;
  margin: 0 0.2rem 0 0.2rem;
}

.btn:hover {
  background: #f5f23d;
  color: rgba(0, 0, 0, 0.6);
}

.btn-primary {
  background: #f9dc5c;
}

.btn-secondary {
  background: rgba(0, 0, 0, 0.35);
}

.btn-light {
  background: #fff;
}

.btn-danger {
  background: #ed254e;
}

.btn-success {
  background: lightgreen;
}
.fade-enter {
  opacity: 0;
}

.fade-enter-active {
  transition: opacity 0.5s;
}

.fade-leave-active {
  opacity: 0;
  transition: opacity 0.5s;
}

.fade-slide-up-enter {
  opacity: 0;
  transform: translateY(25px);
}

.fade-slide-up-enter-active {
  transform: translateY(0);
  transition: all 0.25s;
}

.fade-slide-up-leave {
}

.fade-slide-up-leave-active {
  opacity: 0;
  transform: translateY(-25px);
  transition: all 0.25s;
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

/* .table tr:nth-child(odd) {
  background-color: #fff8d9;
} */

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
  color: #011936;
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

.flex {
  display: flex;
}

.flex-column {
  display: flex;
  flex-direction: column;
}

.flex-end {
  justify-content: flex-end;
}

.flex-grow {
  flex: 1 0 auto;
}

.margin-auto {
  margin: auto;
}

.message {
  text-align: center;
  padding: 5rem 0;
  font-size: 2.5rem;
  color: rgba(0, 0, 0, 0.15);
}

/* PERSON FORM, CAR FORM */

.pill {
  padding: 0.5rem 1rem;
  display: inline-block;
  margin: 0.2rem;
  background: rgba(0, 0, 0, 0.15);
  border-radius: 5px;
  position: relative;
}

.pill i {
  opacity: 0;
  position: absolute;
  right: -5px;
  top: -5px;
  transition: all 0.25s;
  cursor: pointer;
}

.pill:hover i {
  opacity: 1;
  color: black;
}

/** Delete from here */

.form input {
  border: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.25);
  padding: 0.5rem 0.5rem;
  font-size: 1rem;
  width: 100%;
  position: relative;
}

.form table {
  width: 700px;
}

.form table td {
  padding: 1.5rem 0.5rem;
}

.form table .fix-width {
  width: 150px;
}

.form select {
  background: white;
  font-size: 1rem;
}

.form input.missing-field {
  border-bottom: 1px solid lightcoral;
}

.missing-field::after {
  color: lightcoral;
  position: absolute;
  content: "<div class='tooltip'>Missing field</div>";
  display: block;
  right: 0px;
  top: 0px;
  width: 10px;
  height: 10px;
  background: black;
}
/** to here */

.form input {
  border: 2px solid rgba(0, 0, 0, 0.08);
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.05);
  padding: 0.5rem 0.5rem;
  font-size: 1rem;
  width: 100%;
}

.form select {
  width: 100%;
  padding: 0.5rem 0.5rem;
  border: 2px solid rgba(0, 0, 0, 0.08);
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.05);
  background: white;
  font-size: 1rem;
}

.form select option {
  padding: 0.5rem 0.5rem;
}

.form-wrapper {
  width: 60%;
  margin: auto;
  min-width: 600px;
}

.form h2 {
  font-family: Roboto, sans-serif;
  font-size: 1.1rem;
}

.form .row {
  margin: 2em 0 2em 0;
}

.btn-wide {
  width: 100%;
}

input.error {
  border-color: lightcoral;
}

select.error {
  border-color: lightcoral;
}

.error-message {
  color: lightcoral;
}

.gotop {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 60px;
  height: 60px;
  background: #f9dc5c;
  color: rgba(0, 0, 0, 0.25);
  content: "Go top";
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  cursor: pointer;
  transition: all 0.25s;
}

.gotop:hover {
  transform: translateY(-5px);
}

.width-full {
  width: 100%;
}
</style>
