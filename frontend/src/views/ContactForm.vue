<script setup lang="ts">
import { onMounted } from "vue";
import { useContactStore } from "@/stores/contactStore";
import ContactCard from "@/components/contact/ContactCard.vue";

const store = useContactStore();

// első betöltés
onMounted(() => {
  store.fetchContacts();
});

async function handleDelete(id: number) {
  const confirmed = confirm("Are you sure you want to delete this contact?");
  if (!confirmed) return;

  await store.deleteContact(id); // fontos: await
}
</script>

<template>
  <div class="max-w-5xl mx-auto mt-10 px-4 flex flex-col gap-6">

    <div v-if="store.contacts.length === 0" class="text-gray-600">
      No contacts yet…
    </div>

    <div class="flex flex-col gap-4">
      <ContactCard
        v-for="c in store.contacts"
        :key="c.id"
        :contact="c"
        @delete="handleDelete"
      />
    </div>

  </div>
</template>
