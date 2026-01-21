<script setup lang="ts">
import { ref, onMounted } from "vue";
import AppHeader from "./components/layout/AppHeader.vue";
import AppContainer from "./components/layout/AppContainer.vue";
import ContactModal from "./components/contact/modal/ContactModal.vue";

import { useAuthStore } from "./stores/auth";
import { useContactStore } from "./stores/contactStore";
import { useImageStore } from "./stores/imageStore";
import { useRouter } from "vue-router";
import api from "./api/axios"; // Importáljuk az api-t a közvetlen híváshoz

const auth = useAuthStore();
const contactStore = useContactStore();
const imageStore = useImageStore();
const router = useRouter();

const s3Url = import.meta.env.VITE_S3_URL as string;
const userImage = ref<string | null>(null);

// Fetch user image directly here to keep auth.ts clean and stable
async function fetchProfileImage() {
  if (!auth.token) return;
  try {
    const res = await api.get<{ imagePath: string }>("/user/image");
    userImage.value = res.data.imagePath;
  } catch (error) {
    console.warn("Profile image fetch failed");
  }
}

onMounted(() => {
  if (auth.isAuthenticated) {
    fetchProfileImage();
  }
});

async function logout() {
  auth.logout();
  userImage.value = null;
  await router.push("/login");
}

async function handleEditSave(data: any) {
  try {
    const id = contactStore.editingContact?.id;
    if (!id) return;

    await contactStore.updateContact(id, {
      name: data.name,
      phone: data.phone,
      email: data.email
    });

    if (data.imageFile) {
      await imageStore.uploadContactImage(id, data.imageFile);
    }

    await contactStore.fetchContacts();
    contactStore.editingContact = null;
  } catch (error) {
    console.error("Save error:", error);
  }
}
</script>

<template>
  <div class="min-h-screen grid grid-cols-[1fr_2fr_1fr] grid-rows-[auto_auto_1fr] gap-px bg-res">
    <div class="h-16 bg-sotet"></div>
    <div class="h-16 bg-sotet"></div>
    <div class="h-16 bg-sotet"></div>

    <div class="flex items-center justify-end bg-sotet pr-6 gap-4">
      <!-- User image integration -->
      <div v-if="userImage" class="w-8 h-8 rounded-full overflow-hidden border border-gray-700">
        <img :src="s3Url + userImage" class="w-full h-full object-cover" />
      </div>
      <button @click="logout" class="text-sm text-gray-400 hover:text-white transition">
        <img src="/back-logo.svg" class="w-4 h-4 mr-3 transition-transform duration-300 ease-out 
           group-hover:scale-125 group-hover:rotate-[-10deg] 
           group-active:scale-90" />
      </button>
    </div>

    <div class="flex items-center justify-center bg-sotet">
      <AppHeader />
    </div>

    <div class="flex items-center justify-end bg-sotet"></div>

    <div class="bg-sotet"></div>
    <div class="bg-sotet flex flex-col w-full h-full">
      <AppContainer>
        <router-view />
      </AppContainer>
    </div>
    <div class="bg-sotet"></div>

    <ContactModal v-if="contactStore.editingContact" mode="edit" :initialData="contactStore.editingContact"
      @close="contactStore.editingContact = null" @submit="handleEditSave" />
  </div>
</template>
