import axios from 'axios';
import MomentTZ from 'moment-timezone';

import * as types from '../constants/ActionTypes';
import { initNumeralLocales, setCurrentActiveLocale } from '../utils/locale';
import { getNotificationsRequest } from '../api/notifications';
import { updateDefaultPrecisionsFromUserSettings } from '../utils/tableHelpers';
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
        //console.log('User notifications loaded');
      })
      .catch((error) => {
        console.log('Failed to load user notifications', error);
      });
  };
}

export function loginSuccess() {
  return async (dispatch) => {
    dispatch({ type: types.LOGIN_SUCCESS });

    return await getUserSession()
      .then(({ data }) => {
        dispatch(userSessionInit(data));

        setCurrentActiveLocale(data.language['key']);
        initNumeralLocales(data.language['key'], data.locale);
        MomentTZ.tz.setDefault(data.timeZone);
        updateDefaultPrecisionsFromUserSettings(data.settings);
      })
      .catch((error) => {
        console.log('Failed to getting user session', error);
      });
  };
}
