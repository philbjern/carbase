<template>
  <div class="container content-wrapper">
    <h1>Welcome to Carbase</h1>
    <p>
      Choose persons to see table of car users, or cars to see list of all cars
      available for rent.
    </p>

    <div class="cards">
      <router-link to="/persons" tag="div" class="card">
        <div class="content">
          <h1>Persons</h1>
          <p class="color-gray">Show list of all car users</p>
        </div>
      </router-link>
      <router-link to="/cars" tag="div" class="card">
        <div class="content">
          <h1>Cars</h1>
          <p class="color-gray">Show list of all cars</p>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.cards {
  margin-top: 2rem;
  display: flex;
}

.color-gray {
  color: darkslategray;
}

.card {
  cursor: pointer;
  flex: 1 0 auto;
  flex-direction: column;
  min-height: 350px;
  background-color: #f9dc5c;
  margin: 1rem;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 1s;
}

.card:hover {
  background: black;
  transform: translateY(-15px);
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.75);
}

.card .content {
  transition: all 1s;
}

.card:hover .content {
  transform: translateY(-20px) scale(1.4);
  transition: transform 0.5s;
  color: white;
}

.content p {
  color: inherit;
}
</style>
