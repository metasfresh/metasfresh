import { useContext, createContext } from 'react';
import { types, flow, Instance, onSnapshot } from 'mobx-state-tree';

import { fetchDailyReport, fetchWeeklyReport, loginRequest, postDailyReport } from '../api';
import { i18n } from './i18n';
import { Info } from './Info';
import Navigation from './Navigation';
import { DailyProductList } from './DailyProductList';
import { App } from './App';

export const Store = types
  .model('Store', {
    i18n: i18n,
    navigation: Navigation,
    dailyProducts: DailyProductList,
    app: App,
    info: Info,
  })
  .actions((self) => ({
    logIn: flow(function* logIn(email: string, password: string) {
      self.app.setResponseError('');
      let result;

      try {
        const { data } = yield loginRequest(email, password);
        yield self.app.getUserSession();

        result = { ...data };
      } catch (error) {
        result = {
          loginError: error,
        };
      }

      return self.app.logIn(result);
    }),
    postDailyReport: flow(function* postDailyReportLocal(dataObj: unknown) {
      try {
        yield postDailyReport(dataObj);
      } catch (error) {
        console.error('Failed to post', error);
      }
    }),
    fetchDailyReport: flow(function* dailyReport(reportDate: string) {
      try {
        const {
          data: { date, dayCaption, products },
        } = yield fetchDailyReport(reportDate);

        self.app.setCurrentDay(date);
        self.app.setDayCaption(dayCaption);
        self.dailyProducts.updateProductList(products);
      } catch (error) {
        console.error('Failed to fetch', error);
      }
    }),
    fetchWeeklyReport: flow(function* weeklyReport(reportWeek: string) {
      try {
        const {
          data: { week, weekCaption, nextWeek, previousWeek },
        } = yield fetchWeeklyReport(reportWeek);

        self.app.setCurrentWeek(week);
        self.app.setWeekCaption(weekCaption);
        self.app.setNextWeek(nextWeek);
        self.app.setPrevWeek(previousWeek);

        // TODO: Should we just have `products` for both week and day ?
        // self.dailyProducts.updateProductList(products);
      } catch (error) {
        console.error('Failed to fetch', error);
      }
    }),
  }));

let initialState = Store.create({
  i18n: { lang: '' },
  navigation: { topViewName: '', bottomViewName: '' },
  dailyProducts: {},
  app: {
    loggedIn: false,
    loginError: '',
    countUnconfirmed: 0,
    email: '',
    dayCaption: '',
    currentDay: '',
    week: '',
    weekCaption: '',
    previousWeek: '',
    nextWeek: '',
  },
  info: { content: '' },
});

const data = localStorage.getItem('initialState');
if (data) {
  const json = JSON.parse(data);
  if (Store.is(json)) {
    initialState = Store.create(json);
  }
}

export const store = initialState;
/**
 * Create a localStorage entry from the snapshot
 */
onSnapshot(store, (snapshot) => {
  console.log('Snapshot: ', snapshot); // this is for debugging purposes - can be removed in prod
  localStorage.setItem('initialState', JSON.stringify(snapshot));
});

/**
 * we can use also useMst through the app to reach the data by using the context
 */
export type RootInstance = Instance<typeof Store>;
export const RootStoreContext = createContext<null | RootInstance>(null);

export const Provider = RootStoreContext.Provider;
export function useMst(): RootInstance {
  const store = useContext(RootStoreContext);
  if (store === null) {
    throw new Error('Please add a context provider');
  }
  return store;
}
