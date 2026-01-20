// src/stores/imageStore.ts
import { defineStore } from "pinia";
import api from "@/api/axios";

export const useImageStore = defineStore("images", {
  actions: {
    async uploadContactImage(contactId: number, file: File) {
      const formData = new FormData();
      formData.append("file", file); // A kulcs ("file") egyezzen a backend @RequestParam nevével!

      return await api.post(`/images/contact/${contactId}`, formData, {
        headers: { "Content-Type": "multipart/form-data" }
      });
    }
  }
});