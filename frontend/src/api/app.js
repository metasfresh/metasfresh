import axios from 'axios';

export function getNotificationsRequest() {
  return axios.get(`${config.API_URL}/notifications/all?limit=20`);
}

export function getNotificationsEndpointRequest() {
  return axios.get(`${config.API_URL}/notifications/websocketEndpoint`);
}
