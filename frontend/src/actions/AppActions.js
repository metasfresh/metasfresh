import axios from 'axios';
import MomentTZ from 'moment-timezone';
import numeral from 'numeral';

import * as types from '../constants/ActionTypes';
import { setCurrentActiveLocale } from '../utils/locale';
import {
  getNotificationsEndpointRequest,
  getNotificationsRequest,
} from '../api';
import { getUserSession } from '../api/userSession';

// TODO: All requests should be moved to API

export function markAllAsRead() {
  return axios.put(`${config.API_URL}/notifications/all/read`);
}

export function markAsRead(id) {
  return axios.put(`${config.API_URL}/notifications/${id}/read`);
}

export function deleteUserNotification(id) {
  return axios.delete(`${config.API_URL}/notifications?ids=${id}`);
}

export function getImageAction(id) {
  return axios({
    url: `${config.API_URL}/image/${id}?maxWidth=200&maxHeight=200`,
    responseType: 'blob',
  }).then((response) => response.data);
}

export function postImageAction(data) {
  return axios
    .post(`${config.API_URL}/image`, data)
    .then((response) => response.data);
}

export function getMessages(lang) {
  return axios.get(`${config.API_URL}/i18n/messages${lang ? '?=' + lang : ''}`);
}

export function createUrlAttachment({ windowId, documentId, name, url }) {
  return axios.post(
    `${config.API_URL}/window/${windowId}/${documentId}/attachments/addUrl`,
    { name, url }
  );
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

// REDUX ACTIONS

export function enableTutorial(flag = true) {
  return {
    type: types.ENABLE_TUTORIAL,
    flag: flag,
  };
}

export function connectionError({ errorType }) {
  return {
    type: types.CONNECTION_ERROR,
    errorType,
  };
}

export function addNotification(
  title,
  msg,
  time,
  notifType,
  shortMsg,
  onCancel
) {
  return {
    type: types.ADD_NOTIFICATION,
    title: title,
    msg: msg,
    shortMsg: shortMsg,
    time: time,
    notifType: notifType,
    id: Date.now(),
    onCancel,
  };
}

export function setNotificationProgress(key, progress) {
  return {
    type: types.SET_NOTIFICATION_PROGRESS,
    key: key,
    progress: progress,
  };
}

export function deleteNotification(key) {
  return {
    type: types.DELETE_NOTIFICATION,
    key: key,
  };
}

export function clearNotifications() {
  return (dispatch, getState) => {
    const { appHandler } = getState();

    if (
      appHandler.inbox.notifications.length === 0 ||
      appHandler.inbox.pending
    ) {
      return;
    }

    dispatch({ type: types.CLEAR_NOTIFICATIONS });
  };
}

export function requestNotifications() {
  return {
    type: types.GET_NOTIFICATIONS_REQUEST,
  };
}

export function getNotificationsSuccess(notifications, unreadCount) {
  return {
    type: types.GET_NOTIFICATIONS_SUCCESS,
    notifications: notifications,
    unreadCount: unreadCount,
  };
}

export function readNotification(notificationId, unreadCount) {
  return {
    type: types.READ_NOTIFICATION,
    notificationId,
    unreadCount,
  };
}

export function newNotification(notification, unreadCount) {
  return {
    type: types.NEW_NOTIFICATION,
    notification,
    unreadCount,
  };
}

export function removeNotification(notificationId, unreadCount) {
  return {
    type: types.REMOVE_NOTIFICATION,
    notificationId,
    unreadCount,
  };
}

export function readAllNotifications() {
  return {
    type: types.READ_ALL_NOTIFICATIONS,
  };
}

export function deleteAllNotifications() {
  return {
    type: types.REMOVE_ALL_NOTIFICATIONS,
  };
}

export function setProcessPending() {
  return {
    type: types.SET_PROCESS_STATE_PENDING,
  };
}

export function setProcessSaved() {
  return {
    type: types.SET_PROCESS_STATE_SAVED,
  };
}

export function userSessionInit(me) {
  return {
    type: types.USER_SESSION_INIT,
    me,
  };
}

export function userSessionUpdate(me) {
  return {
    type: types.USER_SESSION_UPDATE,
    me,
  };
}

/**
 * @summary updates the lastBackPage in the store to have it for comparison when back button is used
 * @param {string} lastBackPage
 */
export function updateLastBackPage(lastBackPage) {
  return {
    type: types.UPDATE_LAST_BACK_PAGE,
    lastBackPage,
  };
}

export function setLanguages(data) {
  return {
    type: types.SET_LANGUAGES,
    data,
  };
}

export function initKeymap(keymap) {
  return {
    type: types.INIT_KEYMAP,
    payload: keymap,
  };
}

export function updateKeymap(keymap) {
  return {
    type: types.UPDATE_KEYMAP,
    payload: keymap,
  };
}

export function initHotkeys(hotkeys) {
  return {
    type: types.INIT_HOTKEYS,
    payload: hotkeys,
  };
}

export function updateHotkeys(hotkeys) {
  return {
    type: types.UPDATE_HOTKEYS,
    payload: hotkeys,
  };
}

export function getNotificationsEndpoint(auth) {
  return (dispatch) => {
    return getNotificationsEndpointRequest().then((topic) => {
      auth.initNotificationClient(topic, (msg) => {
        const notification = JSON.parse(msg.body);

        if (notification.eventType === 'Read') {
          dispatch(
            readNotification(
              notification.notificationId,
              notification.unreadCount
            )
          );
        } else if (notification.eventType === 'ReadAll') {
          dispatch(readAllNotifications());
        } else if (notification.eventType === 'Delete') {
          dispatch(
            removeNotification(
              notification.notificationId,
              notification.unreadCount
            )
          );
        } else if (notification.eventType === 'DeleteAll') {
          dispatch(deleteAllNotifications());
        } else if (notification.eventType === 'New') {
          dispatch(
            newNotification(notification.notification, notification.unreadCount)
          );
          const notif = notification.notification;
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
      });
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
      .then((response) => {
        dispatch(
          getNotificationsSuccess(
            response.data.notifications,
            response.data.unreadCount
          )
        );
      })
      .catch((e) => e);
  };
}

export function loginSuccess(auth) {
  return async (dispatch) => {
    const requests = [];

    dispatch({ type: types.LOGIN_SUCCESS });

    requests.push(
      getUserSession()
        .then(({ data }) => {
          dispatch(userSessionInit(data));

          setCurrentActiveLocale(data.language['key']);
          initNumeralLocales(data.language['key'], data.locale);
          MomentTZ.tz.setDefault(data.timeZone);

          auth.initSessionClient(data.websocketEndpoint, (msg) => {
            const me = JSON.parse(msg.body);
            dispatch(userSessionUpdate(me));

            me.language && setCurrentActiveLocale(me.language['key']);
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
