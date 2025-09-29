import { post } from 'axios';

export const postChatMessage = ({ message }) => {
  return post(`${config.API_URL}/chat/post`, {
    message,
  });
};
