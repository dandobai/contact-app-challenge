export interface ContactResponseDto {
  id: number;
  name: string;
  email: string;
  phone: string;
  imagePath: string | null;
}

export interface ContactForm {
  name: string;
  email: string;
  phone: string;
}