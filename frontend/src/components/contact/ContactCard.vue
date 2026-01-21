<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import type { ContactResponseDto } from "../../types/contact";
import { useContactStore } from "@/stores/contactStore";

const props = defineProps<{ contact: ContactResponseDto }>();
const contactStore = useContactStore();

const s3Url = import.meta.env.VITE_S3_URL;
const emit = defineEmits(['delete', 'favorite']); // 'edit' removed as we use store

const isMenuOpen = ref(false);
const toggleMenu = () => isMenuOpen.value = !isMenuOpen.value;

const closeMenu = (e: MouseEvent) => {
  if (!(e.target as HTMLElement).closest('.dropdown-container')) isMenuOpen.value = false;
};

// Set contact for editing in global store
const handleEditClick = () => {
  contactStore.editingContact = props.contact;
  isMenuOpen.value = false;
};

onMounted(() => window.addEventListener('click', closeMenu));
onUnmounted(() => window.removeEventListener('click', closeMenu));
</script>

<template>
  <div class="w-full flex items-center justify-between gap-4 p-2 group transition-colors relative">
    <div class="flex items-center gap-4">
      <img v-if="contact.imagePath" :src="s3Url + contact.imagePath" class="w-12 h-12 rounded-lg object-cover" />
      <div v-else class="w-12 h-12 rounded-lg bg-gray-800 shrink-0"></div>
      <div class="flex flex-col">
        <strong class="text-lg font-medium text-white">{{ contact.name }}</strong>
        <div class="text-gray-300 text-sm">{{ contact.email }}</div>
        <div class="text-gray-300 text-sm">{{ contact.phone }}</div>
      </div>
    </div>

    <div class="dropdown-container relative opacity-0 group-hover:opacity-100 transition-opacity duration-200">
      <button @click.stop="toggleMenu" class="p-2 hover:bg-gray-700 rounded-full transition-colors">
        <img src="/3-dot-icon.svg" class="w-6 h-6" alt="menu" />
      </button>

      <div v-if="isMenuOpen" class="absolute right-0 mt-2 w-48 rounded-lg shadow-xl z-50 overflow-hidden border border-gray-700 bg-[#1E1E1E]">
        <div class="flex flex-col">
          <button @click="handleEditClick" class="dropdown-item flex items-center w-full px-4 py-3 text-sm text-white">
            <img src="/edit-icon.svg" class="w-4 h-4 mr-3" /> Edit
          </button>
          <button @click="$emit('favorite', contact.id); isMenuOpen = false" class="dropdown-item flex items-center w-full px-4 py-3 text-sm text-white border-t border-gray-800">
            <img src="/favourite-icon.svg" class="w-4 h-4 mr-3" /> Favourite
          </button>
          <button @click="$emit('delete', contact.id); isMenuOpen = false" class="dropdown-item flex items-center w-full px-4 py-3 text-sm text-red-500 border-t border-gray-800">
            <img src="/drop-delete.svg" class="w-4 h-4 mr-3 opacity-70" /> Remove
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dropdown-item { background-color: #1E1E1E; transition: all 0.2s ease; cursor: pointer; }
.dropdown-item:hover { filter: brightness(1.2); }
.dropdown-item:active { filter: brightness(1.4); }
</style>
