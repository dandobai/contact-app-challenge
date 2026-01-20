import { defineStore } from "pinia";
import api from "@/api/axios";

export interface Contact {
  id: number;
  name: string;
  phone: string;
  email: string;
  imagePath: string | null;
}

export interface NewContactPayload {
  name: string;
  phone: string;
  email: string;
}

export const useContactStore = defineStore("contacts", {
  state: () => ({
    contacts: [] as Contact[],
    editingContact: null as Contact | null, // State for the contact currently being edited
  }),

  actions: {
    async fetchContacts() {
      try {
        const res = await api.get<Contact[]>("/contacts");
        this.contacts = res.data;
      } catch (error) {
        console.error("Fetch error:", error);
      }
    },

    async addContact(payload: NewContactPayload): Promise<Contact> {
      const res = await api.post<Contact>("/contacts", payload);
      return res.data;
    },

    async updateContact(id: number, payload: NewContactPayload): Promise<Contact> {
      const res = await api.put<Contact>(`/contacts/${id}`, payload);
      // Update local state to reflect changes immediately
      const index = this.contacts.findIndex(c => c.id === id);
      if (index !== -1) this.contacts[index] = res.data;
      return res.data;
    },

    async deleteContact(id: number) {
      await api.delete(`/contacts/${id}`);
      this.contacts = this.contacts.filter(c => c.id !== id);
    },
  },
});
