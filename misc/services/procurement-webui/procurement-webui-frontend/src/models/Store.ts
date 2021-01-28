import { useContext, createContext } from 'react';
import { types, flow, Instance, onSnapshot, addMiddleware, IMiddlewareEvent, IAnyStateTreeNode } from 'mobx-state-tree';

import { fetchDailyReport, fetchWeeklyReport, loginRequest, postDailyReport, getMessages } from '../api';
import { i18n } from './i18n';
import { Info } from './Info';
import Navigation from './Navigation';
import { DailyProductList } from './DailyProductList';
import { App } from './App';
import { ProductSelection } from './ProductSelection';
import { WeeklyProductList } from './WeeklyProductList';
import { RFQList } from './RFQList';

export default function actionCatch(store: IAnyStateTreeNode) {
  return (
    call: IMiddlewareEvent,
    next: (actionCall: IMiddlewareEvent, callback?: (value: any) => any) => void
  ): void => {
    try {
      return next(call);
    } catch (err) {
      if (err.response && err.response.status === 401) {
        store.app.logOut();
      }
      throw err;
    }
  };
}

export const Store = types
  .model('Store', {
    i18n: i18n,
    navigation: Navigation,
    dailyProducts: DailyProductList,
    weeklyProducts: WeeklyProductList,
    app: App,
    info: Info,
    productSelection: ProductSelection,
    rfqs: RFQList,
  })
  .actions((self) => ({
    logIn: flow(function* logIn(email: string, password: string) {
      self.app.setResponseError('');
      let result;

      try {
        const { data } = yield loginRequest(email, password);
        yield self.app.getUserSession();
        getMessages().then(async (response) => {
          if (response.status === 200 && response.data) {
            const { language, messages } = response.data;
            self.i18n.changeLang(language);
            self.i18n.changeMessages(messages);
          }
        });
        result = { ...data };
      } catch (error) {
        result = {
          loginError: error,
        };
      }

      return self.app.logIn(result);
    }),
    postDailyReport: flow(function* postDailyReportLocal(dataObj: unknown) {
      yield postDailyReport(dataObj);
    }),
    fetchDailyReport: flow(function* dailyReport(reportDate: string) {
      const {
        data: { date, dayCaption, products },
      } = yield fetchDailyReport(reportDate);

      self.app.setCurrentDay(date);
      self.app.setDayCaption(dayCaption);
      self.dailyProducts.updateProductList(products);

      return Promise.resolve();
    }),
    fetchWeeklyReport: flow(function* weeklyReport(reportWeek: string) {
      const {
        data: { week, weekCaption, nextWeek, previousWeek, products, nextWeekCaption },
      } = yield fetchWeeklyReport(reportWeek);

      self.app.setCurrentWeek(week);
      self.app.setWeekCaption(weekCaption);
      self.app.setNextWeek(nextWeek);
      self.app.setPrevWeek(previousWeek);

      self.weeklyProducts.updateProductList(products);
      // update the model values also even if the week info are held on the app... Not really necessarry but for the sake of clear data
      // Todo: WAT ? - Kuba
      self.weeklyProducts.updateNextWeek(nextWeek);
      self.weeklyProducts.updateNextCaption(nextWeekCaption);
      self.weeklyProducts.updatePreviousWeek(previousWeek);
      self.weeklyProducts.updateWeek(week);
      self.weeklyProducts.updateWeekCaption(weekCaption);
    }),
  }));

let initialState = Store.create({
  i18n: { lang: '' },
  navigation: { topViewName: '', bottomViewName: '' },
  dailyProducts: {},
  weeklyProducts: {},
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
  productSelection: {},
  rfqs: {},
});

const data = localStorage.getItem('initialState');
if (data) {
  const json = JSON.parse(data);
  if (Store.is(json)) {
    initialState = Store.create(json);
  }
}

addMiddleware(initialState, actionCatch(initialState));

export const store = initialState;
/**
 * Create a localStorage entry from the snapshot
 */
onSnapshot(store, (snapshot) => {
  // console.log('Snapshot: ', snapshot); // this is for debugging purposes - can be removed in prod
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
