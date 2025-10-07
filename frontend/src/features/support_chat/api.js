import { post } from 'axios';

export const postChatMessage = ({ message }) => {
  return post(`${config.API_URL}/support-chat/post`, {
    message,
  });
};
