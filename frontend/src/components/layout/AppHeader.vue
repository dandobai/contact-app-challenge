<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import ContactModal from '@/components/contact/modal/ContactModal.vue'
import { useContactStore } from '@/stores/contactStore'
import { useImageStore } from '@/stores/imageStore'

const auth = useAuthStore()
const contactStore = useContactStore()
const imageStore = useImageStore()
const showAddModal = ref(false)
const s3Url = "https://contact-app-images-daniel.s3.eu-north-1.amazonaws.com/"

// Oldal betöltésekor hívjuk meg
onMounted(() => {
  if (auth.isAuthenticated) {
    auth.fetchUserImage()
    console.log("User image path:", auth.userImage) // Ellenőrzés
  }
})

async function handleAddSubmit(data: any) {
  try {
    const createdContact = await contactStore.addContact({
      name: data.name,
      phone: data.phone,
      email: data.email
    });
    if (createdContact && createdContact.id && data.imageFile) {
      await imageStore.uploadContactImage(createdContact.id, data.imageFile);
    }
    await contactStore.fetchContacts();
    showAddModal.value = false;
  } catch (error) {
    console.error("Hiba a mentés során:", error);
  }
}
</script>

<template>
  <header class="w-full bg-sotet text-white border-b border-gray-800">
    <div class="mx-auto max-w-5xl h-16 px-6 flex items-center justify-between">
      <h1 class="font-glysa font-medium text-[32px] leading-[48px]">Contacts</h1>

      <div class="flex items-center gap-4">
        <button class="text-white hover:text-gray-300 transition">
          <img src="/edit-icon.svg" class="w-4 h-4 mr-3" />
        </button>

        <!-- Dinamikus profilkép -->
        <img 
          :src="auth.userImage ? s3Url + auth.userImage : '/default-avatar.png'" 
          alt="User avatar" 
          class="w-9 h-9 rounded-full object-cover border border-gray-700 bg-gray-800" 
        />

        <button
          class="bg-[#282828B2] opacity-70 rounded-2xl hover:opacity-85 text-white text-sm px-4 py-2 transition"
          @click="showAddModal = true"
        >
          + Add new
        </button>
      </div>
    </div>
  </header>

  <ContactModal
    v-if="showAddModal"
    mode="add"
    @close="showAddModal = false"
    @submit="handleAddSubmit"
  />
</template>