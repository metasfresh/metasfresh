import { useEffect } from 'react';
import { postEventsToBackend } from '../../api/ui_trace';
import { clearEvents, getAllEvents } from './db';

const SYNC_INTERVAL_MILLIS = 1000;

const syncEventsToBackend = async () => {
  const events = await getAllEvents();

  if (!events?.length) return;

  try {
    // console.log('Posting to backend', { events });
    await postEventsToBackend(events);
    await clearEvents();
    // console.log('Posting to backend DONE');
  } catch (error) {
    console.error('Error syncing events:', error);
  }
};

const useSyncToBackend = () => {
  useEffect(() => {
    const handleOnline = () => {
      console.log('Back online. Attempting to sync events...');
      // noinspection JSIgnoredPromiseFromCall
      syncEventsToBackend();
    };

    const interval = setInterval(() => {
      if (navigator.onLine) {
        //console.log('Scheduled events syncing...');

        // noinspection JSIgnoredPromiseFromCall
        syncEventsToBackend();
      } else {
        console.log('Skip scheduled events syncing because not online');
      }
    }, SYNC_INTERVAL_MILLIS);

    window.addEventListener('online', handleOnline);

    console.log('Sync UI events to backend enabled');

    return () => {
      window.removeEventListener('online', handleOnline);
      clearInterval(interval);
      console.log('Sync UI events to backend disabled');
    };
  }, []);
};

export const useUIEventsTracing = () => {
  useSyncToBackend();
};
