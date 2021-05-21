<template>
  <div>
    <transition-group name="fade" mode="out-in">
      <div
        class="notification"
        v-for="(notification, i) in notifications"
        :key="notification.type + ' ' + i"
        :class="{
          'notification-error': notification.type == 'error',
          'notification-success': notification.type == 'success',
          'notification-info': notification.type == 'info'
        }"
      >
        <div class="container">
          <p style="flex: 1 0 auto;">
            <i class="fa fa-bell-o"></i> {{ notification.message }}
          </p>
          <p>
            <a href="#" @click.prevent="removeNotification(i)"
              ><i class="fa fa-times-circle"></i
            ></a>
          </p>
        </div>
      </div>
    </transition-group>
  </div>
</template>

<script>
export default {
  props: ["notifications"],
  methods: {
    removeNotification(index) {
      this.$emit("notificationremoved", index);
    }
  }
};
</script>

<style scoped>
.notification {
  background: lightcoral;
  padding: 0.25rem 1rem;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.notification-error {
  background: lightcoral;
}

.notification-success {
  background: lightgreen;
}

.notification-info {
  background: lightskyblue;
}

.notification .container {
  display: flex;
}

.fade-enter {
  opacity: 0;
}

.fade-enter-active {
  transition: all 0.5s;
}

.fade-leave {
}

.fade-leave-active {
  opacity: 0;
  transition: all 0.25s;
}
</style>
