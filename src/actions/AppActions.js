import axios from "axios";
import Moment from "moment";
import numeral from "numeral";
import { replace } from "react-router-redux";

import * as types from "../constants/ActionTypes";
import { LOCAL_LANG } from "../constants/Constants";

// REQUESTS

export function getAvatar(id) {
  return config.API_URL + "/image/" + id + "?maxWidth=200&maxHeight=200";
}

export function getUserSession() {
  return axios.get(config.API_URL + "/userSession");
}

export function getUserLang() {
  return axios.get(config.API_URL + "/userSession/language");
}

export function setUserLang(payload) {
  return axios.put(config.API_URL + "/userSession/language", payload);
}

export function getAvailableLang() {
  return axios.get(config.API_URL + "/login/availableLanguages");
}

export function loginRequest(username, password) {
  return axios.post(config.API_URL + "/login/authenticate", {
    username,
    password
  });
}

export function localLoginRequest() {
  return axios.get(config.API_URL + "/login/isLoggedIn");
}

export function loginCompletionRequest(role) {
  return axios.post(config.API_URL + "/login/loginComplete", role);
}

export function logoutRequest() {
  return axios.get(config.API_URL + "/login/logout");
}

export function getNotifications() {
  return axios.get(config.API_URL + "/notifications/all?limit=20");
}

export function getNotificationsEndpoint() {
  return axios.get(config.API_URL + "/notifications/websocketEndpoint");
}

export function markAllAsRead() {
  return axios.put(config.API_URL + "/notifications/all/read");
}

export function markAsRead(id) {
  return axios.put(config.API_URL + "/notifications/" + id + "/read");
}

export function deleteUserNotification(id) {
  return axios.delete(config.API_URL + `/notifications?ids=${id}`);
}

export function getAttributesInstance(
  attrType,
  tmpId,
  docType,
  docId,
  tabId,
  rowId,
  fieldName,
  entity
) {
  const type = entity === "process" ? "processId" : "windowId";

  return axios.post(config.API_URL + "/" + attrType, {
    templateId: tmpId,
    source: {
      [type]: docType,
      documentId: docId,
      tabid: tabId,
      rowId: rowId,
      fieldName: fieldName
    }
  });
}

export function getImageAction(id) {
  return axios({
    url: `${config.API_URL}/image/${id}?maxWidth=200&maxHeight=200`,
    responseType: "blob"
  }).then(response => response.data);
}

export function postImageAction(data) {
  return axios
    .post(`${config.API_URL}/image`, data)
    .then(response => response.data);
}

export function getKPIsDashboard() {
  return axios.get(config.API_URL + "/dashboard/kpis?silentError=true");
}

export function getTargetIndicatorsDashboard() {
  return axios.get(
    config.API_URL + "/dashboard/targetIndicators?silentError=true"
  );
}

export function getKPIData(id) {
  return axios.get(
    config.API_URL + "/dashboard/kpis/" + id + "/data?silentError=true"
  );
}

export function changeKPIItem(id, path, value) {
  return axios.patch(config.API_URL + "/dashboard/kpis/" + id, [
    {
      op: "replace",
      path: path,
      value: value
    }
  ]);
}

export function changeTargetIndicatorsItem(id, path, value) {
  return axios.patch(config.API_URL + "/dashboard/targetIndicators/" + id, [
    {
      op: "replace",
      path: path,
      value: value
    }
  ]);
}

export function getTargetIndicatorsData(id) {
  return axios.get(
    config.API_URL +
      "/dashboard/targetIndicators/" +
      id +
      "/data?silentError=true"
  );
}

export function setUserDashboardWidgets(payload) {
  return axios.patch(config.API_URL + "/dashboard/kpis", payload);
}

export function getMessages(lang) {
  return axios.get(
    config.API_URL + "/i18n/messages" + (lang ? "?=" + lang : "")
  );
}

export function createUrlAttachment({ windowId, documentId, name, url }) {
  return axios.post(
    config.API_URL +
      "/window/" +
      windowId +
      "/" +
      documentId +
      "/attachments/addUrl",
    { name, url }
  );
}

// END OF REQUESTS

export function loginSuccess(auth) {
  return dispatch => {
    localStorage.setItem("isLogged", true);

    /*
        getMessages().then(response => {
            counterpart.registerTranslations('lang', response.data);
            counterpart.setLocale('lang');
        });
*/

    getUserSession().then(session => {
      dispatch(userSessionInit(session.data));
      languageSuccess(session.data.language["key"]);
      initNumeralLocales(session.data.language["key"], session.data.locale);

      auth.initSessionClient(session.data.websocketEndpoint, msg => {
        const me = JSON.parse(msg.body);
        dispatch(userSessionUpdate(me));
        me.language && languageSuccess(me.language["key"]);
        me.locale && initNumeralLocales(me.language["key"], me.locale);

        getNotifications().then(response => {
          dispatch(
            getNotificationsSuccess(
              response.data.notifications,
              response.data.unreadCount
            )
          );
        });

        /*
                getMessages().then(response => {
                    counterpart.registerTranslations('lang', response.data);
                    counterpart.setLocale('lang');
                });
*/
      });
    });

    getNotificationsEndpoint().then(topic => {
      auth.initNotificationClient(topic, msg => {
        const notification = JSON.parse(msg.body);

        if (notification.eventType === "Read") {
          dispatch(
            updateNotification(
              notification.notification,
              notification.unreadCount
            )
          );
        } else if (notification.eventType === "Delete") {
          dispatch(
            removeNotification(
              notification.notificationId,
              notification.unreadCount
            )
          );
        } else if (notification.eventType === "New") {
          dispatch(
            newNotification(notification.notification, notification.unreadCount)
          );
          const notif = notification.notification;
          if (notif.important) {
            dispatch(
              addNotification(
                "Important notification",
                notif.message,
                5000,
                "primary"
              )
            );
          }
        }
      });
    });

    getNotifications().then(response => {
      dispatch(
        getNotificationsSuccess(
          response.data.notifications,
          response.data.unreadCount
        )
      );
    });
  };
}

function initNumeralLocales(lang, locale) {
  const language = lang.toLowerCase();
  const LOCAL_NUMERAL_FORMAT = {
    defaultFormat: "0,0.00[000]",
    delimiters: {
      thousands: locale.numberGroupingSeparator || ",",
      decimal: locale.numberDecimalSeparator || "."
    }
  };

  if (typeof numeral.locales[language] === "undefined") {
    numeral.register("locale", language, LOCAL_NUMERAL_FORMAT);
  }

  if (typeof numeral.locales[language] !== "undefined") {
    numeral.locale(language);

    if (LOCAL_NUMERAL_FORMAT.defaultFormat) {
      numeral.defaultFormat(LOCAL_NUMERAL_FORMAT.defaultFormat);
    }
  }
}

export function languageSuccess(lang) {
  localStorage.setItem(LOCAL_LANG, lang);
  Moment.locale(lang);

  axios.defaults.headers.common["Accept-Language"] = lang;
}

export function logoutSuccess(auth) {
  auth.close();
  localStorage.removeItem("isLogged");
}

export function enableTutorial(flag = true) {
  return {
    type: types.ENABLE_TUTORIAL,
    flag: flag
  };
}

export function addNotification(title, msg, time, notifType, shortMsg) {
  return {
    type: types.ADD_NOTIFICATION,
    title: title,
    msg: msg,
    shortMsg: shortMsg,
    time: time,
    notifType: notifType,
    id: Date.now()
  };
}

export function setNotificationProgress(key, progress) {
  return {
    type: types.SET_NOTIFICATION_PROGRESS,
    key: key,
    progress: progress
  };
}

export function deleteNotification(key) {
  return {
    type: types.DELETE_NOTIFICATION,
    key: key
  };
}

export function clearNotifications() {
  return {
    type: types.CLEAR_NOTIFICATIONS
  };
}

export function updateUri(pathname, query, prop, value) {
  return dispatch => {
    let url = pathname + "?";

    // add new prop or overwrite existing
    query[prop] = value;

    const queryKeys = Object.keys(query);

    for (let i = 0; i < queryKeys.length; i++) {
      url +=
        queryKeys[i] +
        "=" +
        query[queryKeys[i]] +
        (queryKeys.length - 1 !== i ? "&" : "");
    }

    dispatch(replace(url));
  };
}

export function getNotificationsSuccess(notifications, unreadCount) {
  return {
    type: types.GET_NOTIFICATIONS_SUCCESS,
    notifications: notifications,
    unreadCount: unreadCount
  };
}

export function updateNotification(msg, count) {
  return {
    type: types.UPDATE_NOTIFICATION,
    notification: msg,
    unreadCount: count
  };
}

export function newNotification(msg, count) {
  return {
    type: types.NEW_NOTIFICATION,
    notification: msg,
    unreadCount: count
  };
}

export function removeNotification(msg, count) {
  return {
    type: types.REMOVE_NOTIFICATION,
    notification: msg,
    unreadCount: count
  };
}

export function setProcessPending() {
  return {
    type: types.SET_PROCESS_STATE_PENDING
  };
}

export function setProcessSaved() {
  return {
    type: types.SET_PROCESS_STATE_SAVED
  };
}

export function userSessionInit(me) {
  return {
    type: types.USER_SESSION_INIT,
    me
  };
}

export function userSessionUpdate(me) {
  return {
    type: types.USER_SESSION_UPDATE,
    me
  };
}
