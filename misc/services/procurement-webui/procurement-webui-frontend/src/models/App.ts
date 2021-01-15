import { types, flow, SnapshotIn } from 'mobx-state-tree';

import { getUserSession, loginRequest, logoutRequest } from '../api';

export const App = types
  .model('App', {
    language: types.string,
    loggedIn: types.boolean,
    loginError: types.string,
    countUnconfirmed: types.number,
    email: types.string,
    dayCaption: types.string,
    week: types.string,
    weekCaption: types.string,
  })
  .actions((self) => {
    const logIn = flow(function* logIn(email: string, password: string) {
      self.loginError = '';
      let result;

      try {
        const { data } = yield loginRequest(email, password);

        if (!data.loginError) {
          setInitialData(data);
        } else {
          setResponseError(data.loginError);
        }
        result = { ...data };
      } catch (error) {
        setResponseError(error);
        result = {
          loginError: error,
        };
      }

      return result;
    });

    const logOut = flow(function* logOut() {
      yield logoutRequest();

      self.loggedIn = false;
    });

    const getSession = flow(function* getSession() {
      const response = yield getUserSession();

      setInitialData(response.data);

      return response;
    });

    const setInitialData = function setInitialData(dataToSet: Partial<SnapshotIn<typeof self>>) {
      Object.assign(self, dataToSet);
    };

    const setResponseError = function setResponseError(errorMsg) {
      self.loginError = errorMsg;
    };

    return {
      logIn,
      logOut,
      getUserSession: getSession,
      setInitialData,
      setResponseError,
    };
  });
