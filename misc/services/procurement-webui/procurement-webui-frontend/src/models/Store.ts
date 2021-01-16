import { useContext, createContext } from 'react';
import { types, Instance, onSnapshot } from 'mobx-state-tree';

// import { fetchDailyReport } from '../api';
// import { formDate } from '../utils/date';
import { i18n } from './i18n';

import Navigation from './Navigation';
// import { Day } from './Day';
import { DailyProductList } from './DailyProductList';
import { App } from './App';
// import { Week } from './Week';

export const Store = types.model('Store', {
  i18n: i18n,
  navigation: Navigation,
  // day: Day,
  // week: Week,
  dailyProducts: DailyProductList,
  app: App,
});

// const { caption, day } = formDate({ lang: 'de_DE', currentDay: new Date(), to: 'next' }); // TODO: lang - this should be changed with whatever we get from /login

let initialState = Store.create({
  i18n: { lang: '' },
  navigation: { viewName: '' },
  // day: { caption, currentDay: day },
  // week: {
  //   caption: 'Week',
  //   prevWeek: '04.2021',
  //   currentWeek: '05.2021',
  //   nextWeek: '06.2021',
  // },
  dailyProducts: {},
  app: {
    language: '',
    loggedIn: false,
    loginError: '',
    countUnconfirmed: 0,
    email: '',
    dayCaption: '',
    currentDay: '',
    // date: '',
    week: '',
    weekCaption: '',
    previousWeek: '',
    nextWeek: '',
  },
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
const RootStoreContext = createContext<null | RootInstance>(null);

export const Provider = RootStoreContext.Provider;
export function useMst(): RootInstance {
  const store = useContext(RootStoreContext);
  if (store === null) {
    throw new Error('Please add a context provider');
  }
  return store;
}
