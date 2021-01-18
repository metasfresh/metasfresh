import { types, flow, SnapshotIn } from 'mobx-state-tree';

import { getUserSession, logoutRequest } from '../api';
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
      yield logoutRequest();

      self.loggedIn = false;
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
        logOut();
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

    return {
      logIn,
      logOut,
      getUserSession: getSession,
      setInitialData,
      setResponseError,
      setDayCaption,
      setCurrentDay,
      setWeekCaption,
      setPrevWeek,
      setCurrentWeek,
      setNextWeek,
    };
  });
