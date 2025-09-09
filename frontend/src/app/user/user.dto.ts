export interface UserDTO {
  id: number;
  email: string;
  displayName?: string;
  roles?: string[];
  createdAt?: string;
  enabled?: boolean;
}
