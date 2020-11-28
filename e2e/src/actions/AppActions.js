import axios from 'axios';
import Moment from 'moment';
import { Settings } from 'luxon';
import numeral from 'numeral';
import config from '../../cypress/config';

const LOCAL_LANG = 'metasfreshLanguage';

function getUserSession() {
  return axios.get(`${config.API_URL}/userSession`);
}

function getNotifications() {
  return axios.get(`${config.API_URL}/notifications/all?limit=20`);
}

function getNotificationsEndpoint() {
  return axios.get(`${config.API_URL}/notifications/websocketEndpoint`);
}

function languageSuccess(lang) {
  localStorage.setItem(LOCAL_LANG, lang);
  Moment.locale(lang);
  Settings.defaultLocale = lang.replace('_', '-');

  axios.defaults.headers.common['Accept-Language'] = lang;
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

export function loginSuccess(auth) {
  return dispatch => {
    localStorage.setItem('isLogged', true);

    getUserSession().then(({ data }) => {
      dispatch(userSessionInit(data));
      languageSuccess(data.language['key']);
      initNumeralLocales(data.language['key'], data.locale);
      Settings.defaultLocale = data.language['key'].replace('_', '-');
      Settings.defaultZoneName = `utc${data.timeZone.replace(/[0,:]/gi, '')}`;

      auth.initSessionClient(data.websocketEndpoint, msg => {
        const me = JSON.parse(msg.body);
        dispatch(userSessionUpdate(me));
        me.language && languageSuccess(me.language['key']);
        me.locale && initNumeralLocales(me.language['key'], me.locale);

        getNotifications().then(response => {
          dispatch(getNotificationsSuccess(response.data.notifications, response.data.unreadCount));
        });
      });
    });

    getNotificationsEndpoint().then(topic => {
      auth.initNotificationClient(topic, msg => {
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
    });

    getNotifications().then(response => {
      dispatch(getNotificationsSuccess(response.data.notifications, response.data.unreadCount));
    });
  };
}
