import { types, flow, SnapshotIn } from 'mobx-state-tree';

import { getUserSession, logoutRequest, passwordResetRequest, passwordResetConfirm } from '../api';
import { formDate, slashSeparatedYYYYmmdd } from '../utils/date';

export const App = types
  .model('App', {
    loggedIn: types.boolean,
    loginError: types.string,
    countUnconfirmed: types.number,
    email: types.string,
    dayCaption: types.string,
    currentDay: types.string,
    week: types.string,
    weekCaption: types.string,
    nextWeek: types.string,
    previousWeek: types.string,
  })
  .actions((self) => {
    const logIn = function logIn(loginData) {
      if (!loginData.loginError) {
        setInitialData(loginData);
      } else {
        setResponseError(loginData.loginError);
      }

      return loginData;
    };

    const logOut = flow(function* logOut() {
      const response = yield logoutRequest();

      self.loggedIn = false;

      return response;
    });

    const getSession = flow(function* getSession() {
      let response;

      try {
        response = yield getUserSession();

        // FRESH-196
        const date = formDate({ currentDay: new Date(response.data.date), to: 'next' });
        const newDate = slashSeparatedYYYYmmdd(date);

        response.data.currentDay = newDate;

        delete response.data.date;
        delete response.data.language;

        setInitialData(response.data);
      } catch (e) {
        // 401 (Unauthorized)
        if (e.response && e.response.status === 401 && self.loggedIn) {
          logOut();
        }
      }

      return response;
    });

    const requestPasswordReset = flow(function* requestPasswordReset(email: string) {
      let response;

      // This is because right now we're getting 500 for all backend errors and we would not
      // get the error message.
      response = yield passwordResetRequest(email);

      if (response.status !== 200) {
        response = response.data.message;

        return Promise.reject(response);
      }

      return response;
    });

    const confirmPasswordReset = flow(function* requestPasswordReset(token: string) {
      let response;

      response = yield passwordResetConfirm(token);

      if (response.status !== 200) {
        response = response.data.message;

        return Promise.reject(response);
      }

      return response;
    });

    const setInitialData = function setInitialData(dataToSet: Partial<SnapshotIn<typeof self>>) {
      Object.assign(self, dataToSet);
    };

    const setResponseError = function setResponseError(errorMsg) {
      self.loginError = errorMsg;
    };

    const setDayCaption = function (dayCaption: string) {
      self.dayCaption = dayCaption;
    };

    const setCurrentDay = function (newDay: string) {
      self.currentDay = newDay;
    };

    const setWeekCaption = function (newCaption: string) {
      self.weekCaption = newCaption;
    };

    const setPrevWeek = function (newPrevWeek: string) {
      self.previousWeek = newPrevWeek;
    };

    const setCurrentWeek = function (newWeek: string) {
      self.week = newWeek;
    };

    const setNextWeek = function (newNextWeek: string) {
      self.nextWeek = newNextWeek;
    };

    const setUnconfirmed = function (unconfirmedNo: number) {
      self.countUnconfirmed = unconfirmedNo;
    };

    return {
      logIn,
      logOut,
      requestPasswordReset,
      confirmPasswordReset,
      getUserSession: getSession,
      setInitialData,
      setResponseError,
      setDayCaption,
      setCurrentDay,
      setWeekCaption,
      setPrevWeek,
      setCurrentWeek,
      setNextWeek,
      setUnconfirmed,
    };
  });
