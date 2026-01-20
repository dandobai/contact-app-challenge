import api from "./axios";

export async function uploadUserImage(file: File): Promise<string> {
  const form = new FormData();
  form.append("file", file);

  const res = await api.post<string>("/images/user", form, {
    headers: { "Content-Type": "multipart/form-data" },
  });

  return res.data;
}

export async function uploadContactImage(
  id: number,
  file: File
): Promise<string> {
  const form = new FormData();
  form.append("file", file);

  const res = await api.post<string>(`/images/contact/${id}`, form, {
    headers: { "Content-Type": "multipart/form-data" },
  });

  return res.data;
}