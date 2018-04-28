<template>
  <v-layout>
    <v-flex text-xs-center>
      <img src="/v.png"
           alt="Vuetify.js"
           class="mb-5"
           @click.prevent="getData">
      <div v-if="!!data.length" class="headline text-xs-center pa-5">Date: {{ data }}</div>
      <blockquote class="blockquote">
        &#8220;First, solve the problem. Then, write the code.&#8221;
        <footer>
          <small>
            <em>&mdash;John Johnson</em>
          </small>
        </footer>
      </blockquote>
    </v-flex>
  </v-layout>
</template>

<script>
  export default {
    data() {
      return {
        data: '',
      };
    },
    methods: {
      async getData() {
        const response = await this.$axios.$get('http://0.0.0.0:8080/api/data');
        const { data } = response;
        this.data = data;
      },
    },
  };
</script>
