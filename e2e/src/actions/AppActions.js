import axios from 'axios';
import numeral from 'numeral';
import Moment from 'moment';
import { Settings } from 'luxon';

import config from '../../cypress/config';

const LOCAL_LANG = 'metasfreshLanguage';

function getUserSession() {
  return axios.get(`${config.API_URL}/userSession`);
}

function getNotificationsRequest() {
  return axios.get(`${config.API_URL}/notifications/all?limit=20`);
}

function getNotificationsEndpointRequest() {
  return axios.get(`${config.API_URL}/notifications/websocketEndpoint`);
}

export function requestNotifications() {
  return {
    type: 'GET_NOTIFICATIONS_REQUEST',
  };
}

export function clearNotifications() {
  return (dispatch, getState) => {
    const { appHandler } = getState();

    if (appHandler.inbox.notifications.length === 0 || appHandler.inbox.pending) {
      return;
    }

    dispatch({ type: 'CLEAR_NOTIFICATIONS' });
  };
}

function readNotification(notificationId, unreadCount) {
  return {
    type: 'READ_NOTIFICATION',
    notificationId,
    unreadCount,
  };
}

export function addNotification(title, msg, time, notifType, shortMsg, onCancel) {
  return {
    type: 'ADD_NOTIFICATION',
    title: title,
    msg: msg,
    shortMsg: shortMsg,
    time: time,
    notifType: notifType,
    id: Date.now(),
    onCancel,
  };
}

function newNotification(notification, unreadCount) {
  return {
    type: 'NEW_NOTIFICATION',
    notification,
    unreadCount,
  };
}

function removeNotification(notificationId, unreadCount) {
  return {
    type: 'REMOVE_NOTIFICATION',
    notificationId,
    unreadCount,
  };
}

function readAllNotifications() {
  return {
    type: 'READ_ALL_NOTIFICATIONS',
  };
}

function deleteAllNotifications() {
  return {
    type: 'REMOVE_ALL_NOTIFICATIONS',
  };
}

function getNotificationsSuccess(notifications, unreadCount) {
  return {
    type: 'GET_NOTIFICATIONS_SUCCESS',
    notifications: notifications,
    unreadCount: unreadCount,
  };
}

function userSessionUpdate(me) {
  return {
    type: 'USER_SESSION_UPDATE',
    me,
  };
}

function userSessionInit(me) {
  return {
    type: 'USER_SESSION_INIT',
    me,
  };
}

function initNumeralLocales(lang, locale) {
  const language = lang.toLowerCase();
  const LOCAL_NUMERAL_FORMAT = {
    defaultFormat: '0,0.00[000]',
    delimiters: {
      thousands: locale.numberGroupingSeparator || ',',
      decimal: locale.numberDecimalSeparator || '.',
    },
  };

  if (typeof numeral.locales[language] === 'undefined') {
    numeral.register('locale', language, LOCAL_NUMERAL_FORMAT);
  }

  if (typeof numeral.locales[language] !== 'undefined') {
    numeral.locale(language);

    if (LOCAL_NUMERAL_FORMAT.defaultFormat) {
      numeral.defaultFormat(LOCAL_NUMERAL_FORMAT.defaultFormat);
    }
  }
}

function languageSuccess(lang) {
  localStorage.setItem(LOCAL_LANG, lang);
  Moment.locale(lang);
  Settings.defaultLocale = lang.replace('_', '-');

  axios.defaults.headers.common['Accept-Language'] = lang;
}

export function getNotificationsEndpoint(auth) {
  return (dispatch) => {
    return getNotificationsEndpointRequest()
      .then((topic) => {
        auth.initNotificationClient(topic, (msg) => {
          const notification = JSON.parse(msg.body);

          if (notification.eventType === 'Read') {
            dispatch(readNotification(notification.notificationId, notification.unreadCount));
          } else if (notification.eventType === 'ReadAll') {
            dispatch(readAllNotifications());
          } else if (notification.eventType === 'Delete') {
            dispatch(removeNotification(notification.notificationId, notification.unreadCount));
          } else if (notification.eventType === 'DeleteAll') {
            dispatch(deleteAllNotifications());
          } else if (notification.eventType === 'New') {
            dispatch(newNotification(notification.notification, notification.unreadCount));
            const notif = notification.notification;
            if (notif.important) {
              dispatch(addNotification('Important notification', notif.message, 5000, 'primary'));
            }
          }
        });
      })
      .catch((e) => {
        if (e.response) {
          let { status } = e.response;
          if (status === 401) {
            history.push('/');
          }
        }
      });
  };
}

export function getNotifications() {
  return (dispatch, getState) => {
    const state = getState();

    if (state.appHandler.inbox.pending) {
      return Promise.resolve(true);
    }

    dispatch(requestNotifications());

    return getNotificationsRequest()
      .then((response) => dispatch(getNotificationsSuccess(response.data.notifications, response.data.unreadCount)))
      .catch((e) => e);
  };
}

export function loginSuccess(auth) {
  return async (dispatch) => {
    const requests = [];

    requests.push(
      getUserSession()
        .then(({ data }) => {
          dispatch(userSessionInit(data));

          languageSuccess(data.language['key']);
          initNumeralLocales(data.language['key'], data.locale);
          Settings.defaultLocale = data.language['key'].replace('_', '-');
          Settings.defaultZoneName = `utc${data.timeZone.replace(/[0,:]/gi, '')}`;

          auth.initSessionClient(data.websocketEndpoint, (msg) => {
            const me = JSON.parse(msg.body);
            dispatch(userSessionUpdate(me));

            me.language && languageSuccess(me.language['key']);
            me.locale && initNumeralLocales(me.language['key'], me.locale);

            dispatch(getNotifications());
          });
        })
        .catch((e) => e)
    );

    requests.push(dispatch(getNotificationsEndpoint(auth)));
    requests.push(dispatch(getNotifications()));

    return await Promise.all(requests);
  };
}
