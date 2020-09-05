<template>
  <div class="row bg-white">
    <div class="container navbar">
      <h1 class="title">
        Carbase
      </h1>
      <!-- <i class="fa fa-car"></i> -->
      <ul class="nav">
        <router-link to="/" tag="li" exact>Home</router-link>
        <router-link to="/persons" tag="li">Persons</router-link>
        <router-link to="/cars" tag="li">Cars</router-link>
        <router-link to="/login" tag="li">Login</router-link>
        <li>Register</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.title {
  font-size: 2.2rem;
  font-weight: lighter;
  margin: 0 1rem 0 0.5rem;
}

.bg-white {
  background: rgba(255, 255, 255, 0.8);
  border-bottom: 5px solid #f9dc5c;
}

.navbar {
  display: flex;
  align-items: baseline;
  padding: 0.25rem 0;
}

.nav {
  margin: 0;
  padding: 0;
  list-style: none;
  align-self: center;
  flex: 1 0 auto;
  display: flex;
  justify-content: flex-end;
}

.nav li {
  display: inline;
  padding: 0.75rem 1rem;
  border-radius: 5px;
  margin: 0.25rem;
  color: #c21f40;
  transition: all 0.5s;
}

.nav li:hover {
  background: rgba(0, 0, 0, 0.08);
  color: #ed254e;
  cursor: pointer;
}

.router-link-active {
  background: #f9dc5c;
}
</style>
