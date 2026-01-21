<script setup lang="ts">
import { ref, watch } from "vue";
import { useAuthStore } from "@/stores/auth";

const props = defineProps<{
  onClose: () => void;
}>();

const authStore = useAuthStore();

const s3Url = import.meta.env.VITE_S3_URL;

const imagePreview = ref<string | null>(null);
const imageFile = ref<File | null>(null);
const fileInput = ref<HTMLInputElement | null>(null);

// 🔥 Load existing avatar when modal opens
watch(
  () => authStore.userImage,
  (val) => {
    if (val) {
      imagePreview.value = s3Url + val;
    }
  },
  { immediate: true }
);

function triggerFileInput() {
  fileInput.value?.click();
}

function handleFileChange(e: Event) {
  const target = e.target as HTMLInputElement;
  if (!target.files?.length) return;

  const file = target.files[0];
  imageFile.value = file;

  const reader = new FileReader();
  reader.onload = () => {
    imagePreview.value = reader.result as string;
  };
  reader.readAsDataURL(file);
}

function removePicture() {
  imageFile.value = null;
  imagePreview.value = null;
  if (fileInput.value) fileInput.value.value = "";
}

async function upload() {
  if (!imageFile.value) return;

  await authStore.uploadUserImage(imageFile.value);
  await authStore.fetchUserImage();

  props.onClose();
}
</script>

<template>
  <div class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
    <div class="bg-sotet text-white p-8 rounded-lg w-full max-w-sm shadow-xl">

      <!-- Title -->
      <h2 class="text-xl font-semibold mb-4">
        Update profile picture
      </h2>

      <!-- Picture upload -->
      <div class="flex items-center gap-4 mb-4">
        <img
          :src="imagePreview || '/default-avatar.png'"
          alt="Profile preview"
          class="w-16 h-16 rounded-full object-cover bg-gray-700"
        />

        <div class="flex items-center gap-2">
          <!-- Add / Change picture -->
          <div
            class="flex items-center gap-2 px-4 py-2 rounded-md bg-[#2D2D2D] text-white cursor-pointer hover:bg-[#3A3A3A] transition"
            @click="triggerFileInput"
          >
            <img
              :src="imagePreview ? '/refresh-icon.svg' : '/plus-icon.svg'"
              class="w-4 h-4"
            />
            <span class="text-sm font-medium">
              {{ imagePreview ? 'Change picture' : 'Add picture' }}
            </span>
          </div>

          <!-- Delete -->
          <div
            v-if="imagePreview"
            @click="removePicture"
            class="flex items-center justify-center p-2.5 rounded-md bg-[#2D2D2D] text-white cursor-pointer hover:bg-red-900/40 transition"
          >
            <img src="/trash-icon.svg" class="w-4 h-4" />
          </div>
        </div>

        <input
          ref="fileInput"
          type="file"
          accept="image/*"
          class="hidden"
          @change="handleFileChange"
        />
      </div>

      <!-- Buttons -->
      <div class="flex justify-end mt-6 space-x-2">
        <button
          @click="props.onClose"
          class="px-4 py-2 bg-gray-700 rounded-md hover:bg-gray-600"
        >
          Cancel
        </button>

        <button
          @click="upload"
          :disabled="!imageFile"
          class="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-md disabled:opacity-50"
        >
          Save
        </button>
      </div>

    </div>
  </div>
</template>