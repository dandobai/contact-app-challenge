import { createRouter, createWebHistory } from "vue-router";
import ContactList from "../views/ContactList.vue";
import ContactForm from "../views/ContactForm.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import { useAuthStore } from "../stores/auth";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", redirect: "/contacts" },

    { path: "/login", component: Login },
    { path: "/register", component: Register },

    { path: "/contacts", component: ContactList },
    { path: "/contacts/new", component: ContactForm },
    { path: "/contacts/:id", component: ContactForm },
  ],
});

// Route guard
router.beforeEach((to) => {
  const auth = useAuthStore();

  const publicPages = ["/login", "/register"];
  const isPublic = publicPages.includes(to.path);

  if (!isPublic && !auth.isAuthenticated) {
    return "/login";
  }
});

export default router;
