import api from "./axios";
import type { ContactResponseDto, ContactForm } from "../types/contact";

export async function getContacts(): Promise<ContactResponseDto[]> {
  const res = await api.get<ContactResponseDto[]>("/contacts");
  return res.data;
}

export async function getContact(id: number): Promise<ContactResponseDto> {
  const res = await api.get<ContactResponseDto>(`/contacts/${id}`);
  return res.data;
}

export async function createContact(
  payload: ContactForm
): Promise<ContactResponseDto> {
  const res = await api.post<ContactResponseDto>("/contacts", payload);
  return res.data;
}

export async function updateContact(
  id: number,
  payload: ContactForm
): Promise<ContactResponseDto> {
  const res = await api.put<ContactResponseDto>(`/contacts/${id}`, payload);
  return res.data;
}

export async function deleteContact(id: number): Promise<void> {
  await api.delete(`/contacts/${id}`);
}
