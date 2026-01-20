import { defineStore } from "pinia";
import api from "../api/axios";

interface LoginRequest { email: string; password: string; }
interface RegisterRequest { email: string; password: string; }
interface AuthResponse { token: string; }
interface UserImageResponse { imagePath: string; }

// Meghatározzuk a State típusát, hogy a TS ne panaszkodjon
interface AuthState {
  token: string;
  isAuthenticated: boolean;
  userImage: string | null;
}

export const useAuthStore = defineStore("auth", {
  state: (): AuthState => ({
    token: localStorage.getItem("token") || "",
    isAuthenticated: !!localStorage.getItem("token"),
    userImage: null,
  }),

  actions: {
    async fetchUserImage() {
      if (!this.token) return;
      try {
        const res = await api.get<UserImageResponse>("images/user/image");
        this.userImage = res.data.imagePath;
      } catch (error) {
        console.warn("Could not fetch user image");
      }
    },

    async login(payload: LoginRequest) {
      const res = await api.post<AuthResponse>("/auth/login", payload);
      this.token = res.data.token;
      this.isAuthenticated = true;
      localStorage.setItem("token", this.token);
      await this.fetchUserImage(); // Belépéskor egyből lekérjük
    },

    async register(payload: RegisterRequest) {
      const res = await api.post<AuthResponse>("/auth/register", payload);
      this.token = res.data.token;
      this.isAuthenticated = true;
      localStorage.setItem("token", this.token);
      await this.fetchUserImage();
    },

    logout() {
      this.token = "";
      this.isAuthenticated = false;
      this.userImage = null;
      localStorage.removeItem("token");
    },
  },
});
