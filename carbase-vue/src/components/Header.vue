<template>
  <div class="row shadow bg-white">
    <div class="container navbar">
      <h1 class="title">Carbase</h1>
      <!-- <i class="fa fa-car"></i> -->
      <ul class="nav">
        <router-link to="/" tag="li" exact>Home</router-link>
        <router-link to="/persons" tag="li" exact>Persons</router-link>
        <router-link to="/cars" tag="li" exact>Cars</router-link>
      </ul>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.title {
  font-size: 3rem;
  font-weight: lighter;
  margin: 0 1rem 0 0;
}

.bg-white {
  background: rgba(255, 255, 255, 0.8);
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
}

.nav li:hover {
  background: rgba(0, 0, 0, 0.12);
  color: #ed254e;
  cursor: pointer;
  transition: all 0.25s;
}

.router-link-active {
  background: #f9dc5c;
}
</style>
