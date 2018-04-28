<template>
  <v-card>
    <v-container
      fluid
      style="min-height: 0;"
      grid-list-lg
    >
      <v-layout row wrap>
        <v-flex xs12>
          <v-card color="blue-grey darken-2" class="white--text">
            <v-card-title primary-title>
              <h1 class="headline" v-if="error.statusCode === 404">Page not found</h1>
              <h1 class="headline" v-else>An error occurred</h1>
            </v-card-title>
            <v-card-actions>
              <nuxt-link to="/" tag="v-btn">Home page</nuxt-link>
            </v-card-actions>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-card>
</template>

<script>
  export default {
    props: ['error'],
    layout: 'default' // you can set a custom layout for the error page
  };
</script>
