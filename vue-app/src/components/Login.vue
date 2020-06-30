<template>
  <div class="container content-wrapper flex">
    <div class="photo"></div>
    <div class="form login-form">
      <h1>Login</h1>
      <form>
        <div class="row">
          <p>Username</p>
          <input type="text" />
        </div>
        <div class="row">
          <p>Password</p>
          <input type="password" name="password" id="pass" />
        </div>
        <div class="row">
          <button class="btn btn-primary width-full">Login</button>
        </div>
        <div class="row text-center">
          Don't have an account? <a href="">Register</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: false
    };
  }
};
</script>

<style scoped>
.photo {
  background-image: url("../../static/img/parking-lot.jpg");
  background-size: cover;
  min-height: 650px;
  flex: 5 0 auto;
}

.form p {
  margin: 0;
}

.login-form {
  flex: 1 1 auto;
  padding: 3rem;
}
</style>
