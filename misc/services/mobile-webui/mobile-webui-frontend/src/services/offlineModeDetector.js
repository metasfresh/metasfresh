import { networkStatusOffline, networkStatusOnline } from '../actions/NetworkActions';

export const setupOfflineModeDetector = ({ globalStore }) => {
  const broadcast = new BroadcastChannel('network-status-channel');

  /**
   * Listen to the response broadcasted by the service worker when the network status changes
   * This is needed for the special case when user refreshes the page while being off
   */
  broadcast.onmessage = (event) => {
    event.data.payload === 'offline' && globalStore.dispatch(networkStatusOffline());
  };

  window.addEventListener('offline', () => {
    globalStore.dispatch(networkStatusOffline());
  });

  window.addEventListener('online', () => {
    globalStore.dispatch(networkStatusOnline());
  });
};
