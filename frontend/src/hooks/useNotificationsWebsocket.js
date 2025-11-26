import { useEffect, useState } from 'react';
import { getNotificationsEndpointRequest } from '../api/notifications';
import { useWebsocket } from './useWebsocket';
import { useDispatch } from 'react-redux';
import {
  addNotification,
  deleteAllNotifications,
  getNotifications,
  newNotification,
  readAllNotifications,
  readNotification,
  removeNotification,
} from '../actions/AppActions';

export const useNotificationsWebsocket = ({ isLoggedIn }) => {
  const dispatch = useDispatch();
  const [topic, setTopic] = useState(null);

  useEffect(() => {
    if (isLoggedIn) {
      getNotificationsEndpointRequest().then(({ data: topic }) => {
        setTopic(topic);
      });

      //
      // Fetch notifications from the backend and load them to redux state
      dispatch(getNotifications());
    } else {
      setTopic(null);
    }
  }, [isLoggedIn]);

  useWebsocket({
    topic,
    onMessage: ({ event }) => onNotificationEvent({ event, dispatch }),
  });
};

const onNotificationEvent = ({ event, dispatch }) => {
  if (event.eventType === 'Read') {
    dispatch(readNotification(event.notificationId, event.unreadCount));
  } else if (event.eventType === 'ReadAll') {
    dispatch(readAllNotifications());
  } else if (event.eventType === 'Delete') {
    dispatch(removeNotification(event.notificationId, event.unreadCount));
  } else if (event.eventType === 'DeleteAll') {
    dispatch(deleteAllNotifications());
  } else if (event.eventType === 'New') {
    dispatch(newNotification(event.notification, event.unreadCount));
    const notif = event.notification;
    if (notif.important) {
      dispatch(
        addNotification(
          'Important notification',
          notif.message,
          5000,
          'primary'
        )
      );
    }
  }
};
