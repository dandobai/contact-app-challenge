<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/auth";

const router = useRouter();
const auth = useAuthStore();

const email = ref("");
const password = ref("");
const error = ref("");
const loading = ref(false);

async function submit() {
  loading.value = true;
  error.value = "";

  try {
    await auth.register({
      email: email.value,
      password: password.value,
    });
    // Sikeres regisztráció után irány a kontaktok
    await router.push("/contacts");
  } catch (err: any) {
    error.value = err.message || "Registration failed";
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="max-w-sm mx-auto mt-16 flex flex-col gap-6 text-white">
    <h1 class="text-3xl font-semibold text-center tracking-tight text-white">Register</h1>

    <div v-if="error" class="text-red-400 text-sm text-center bg-red-900/20 py-2 rounded-md border border-red-900/50">
      {{ error }}
    </div>

    <form @submit.prevent="submit" class="flex flex-col gap-4">
      <label class="flex flex-col gap-1">
        <span class="text-sm font-medium text-gray-300">Email</span>
        <input
          v-model="email"
          type="email"
          required
          placeholder="new@user.com"
          class="bg-[#1E1E1E] border border-gray-700 rounded-md px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-gray-600 transition"
        />
      </label>

      <label class="flex flex-col gap-1">
        <span class="text-sm font-medium text-gray-300">Password</span>
        <input
          v-model="password"
          type="password"
          required
          placeholder="Create a password"
          class="bg-[#1E1E1E] border border-gray-700 rounded-md px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-gray-600 transition"
        />
      </label>

      <button
        type="submit"
        :disabled="loading"
        class="bg-blue-600 text-white py-2 rounded-md hover:bg-blue-500 active:bg-blue-700 disabled:opacity-50 transition font-medium mt-2 shadow-lg shadow-blue-900/20"
      >
        {{ loading ? "Registering…" : "Register" }}
      </button>
    </form>

    <p class="text-center text-sm text-gray-400 font-medium">
      Already have an account? 
      <router-link to="/login" class="text-blue-400 hover:text-blue-300 hover:underline ml-1">
        Login here
      </router-link>
    </p>
  </div>
</template>
