<template>
  <div class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
    <div class="bg-sotet text-white p-8 rounded-lg w-full max-w-sm shadow-xl">

      <!-- Title -->
      <h2 class="text-xl font-semibold mb-4">
        {{ mode === 'add' ? 'Add contact' : 'Edit contact' }}
      </h2>

      <!-- Picture upload -->
      <div class="flex items-center gap-4 mb-4">
        <img
          :src="imagePreview || '/default-avatar.png'"
          alt="Profile preview"
          class="w-16 h-16 rounded-full object-cover bg-gray-700"
        />

        <!-- Gomb csoport a kép kezeléséhez -->
        <div class="flex items-center gap-2">
          <!-- Add / Change picture gomb -->
          <div
            class="flex items-center gap-2 px-4 py-2 rounded-md bg-[#2D2D2D] text-white cursor-pointer hover:bg-[#3A3A3A] transition"
            @click="triggerFileInput"
          >
            <!-- Ikon váltás: ha van kép, az új ikon, egyébként a régi Vector -->
            <img :src="imagePreview ? '/refresh-icon.svg' : '/plus-icon.svg'" class="w-4 h-4" />
            <span class="text-sm font-medium">
              {{ imagePreview ? 'Change picture' : 'Add picture' }}
            </span>
          </div>

          <!-- Törlés gomb: Csak akkor jelenik meg, ha van bármilyen kép -->
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

      <!-- Form fields -->
      <div class="space-y-4">
        <div class="flex flex-col gap-1">
          <label class="text-gray-300 text-sm">Name</label>
          <input
            v-model="name"
            placeholder="Enter name"
            class="w-full px-3 py-2 rounded-md bg-[#1E1E1E] border border-[#282828] text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
          />
        </div>

        <div class="flex flex-col gap-1">
          <label class="text-gray-300 text-sm">Phone number</label>
          <input
            v-model="phone"
            placeholder="Enter phone number"
            class="w-full px-3 py-2 rounded-md bg-[#1E1E1E] border border-[#282828] text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
          />
        </div>

        <div class="flex flex-col gap-1">
          <label class="text-gray-300 text-sm">Email address</label>
          <input
            v-model="email"
            placeholder="Enter email"
            class="w-full px-3 py-2 rounded-md bg-[#1E1E1E] border border-[#282828] text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
          />
        </div>
      </div>

      <!-- Buttons -->
      <div class="flex justify-end mt-6 space-x-2">
        <button
          @click="$emit('close')"
          class="px-4 py-2 bg-gray-700 rounded-md hover:bg-gray-600"
        >
          Cancel
        </button>

        <button
          @click="submit"
          class="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-md"
        >
          {{ mode === 'add' ? 'Done' : 'Save changes' }}
        </button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  mode: { type: String, required: true },
  initialData: { type: Object, default: () => ({}) }
})

const emit = defineEmits(['close', 'submit'])

const name = ref('')
const phone = ref('')
const email = ref('')
const imagePreview = ref(null)
const imageFile = ref(null)
const fileInput = ref(null)

// Az S3 URL-nek egyeznie kell a ContactCard-ban használttal
const s3Url = import.meta.env.VITE_S3_URL;

watch(
  () => props.initialData,
  (data) => {
    name.value = data.name ?? ''
    phone.value = data.phone ?? ''
    email.value = data.email ?? ''

    // FIX: Ha van imagePath, akkor az S3 URL-t fűzzük elé, nem a localhost-ot
    if (data.imagePath) {
      // Megnézzük, hogy a path már tartalmaz-e teljes URL-t vagy csak egy kulcsot
      imagePreview.value = data.imagePath.startsWith('http') 
        ? data.imagePath 
        : s3Url + data.imagePath
    } else {
      imagePreview.value = null
    }
    
    imageFile.value = null
  },
  { immediate: true }
)

function triggerFileInput() {
  fileInput.value?.click()
}

function handleFileChange(event) {
  const file = event.target.files[0]
  if (!file) return

  imageFile.value = file

  const reader = new FileReader()
  reader.onload = () => {
    // Ez a lokális preview (amikor új fájlt választasz)
    imagePreview.value = reader.result
  }
  reader.readAsDataURL(file)
}

function removePicture() {
  imageFile.value = null
  imagePreview.value = null
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}

function submit() {
  emit('submit', {
    name: name.value,
    phone: phone.value,
    email: email.value,
    imageFile: imageFile.value,
    imageDeleted: !imagePreview.value 
  })
}
</script>
