import React from 'react';
import { render } from 'enzyme';
import ProductWeeklyScreen from '../components/ProductWeeklyScreen';
import { Store, RootStoreContext } from '../models/Store';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured
import { MemoryRouter, Route } from 'react-router';

const history = createMemoryHistory();
const forcedState = {
  path: '/login',
  text: 'Test',
};
history.push('/', forcedState);
const store = Store.create({
  i18n: { lang: '' },
  navigation: { topViewName: '', bottomViewName: '' },
  dailyProducts: {
    products: [
      {
        confirmedByUser: true,
        packingInfo: 'G1 x 10 kg',
        productId: '2',
        productName: 'Frisée Industrie',
        qty: 12,
        isEdited: false,
      },
    ],
  },
  weeklyProducts: {
    products: [
      {
        dailyQuantities: [
          { confirmedByUser: true, date: '2021-02-15', dayCaption: 'Montag', qty: 0 },
          { confirmedByUser: false, date: '2021-02-16', dayCaption: 'Dienstag', qty: 3 },
          { confirmedByUser: true, date: '2021-02-17', dayCaption: 'Mittwoch', qty: 0 },
          { confirmedByUser: true, date: '2021-02-18', dayCaption: 'Donnerstag', qty: 0 },
          { confirmedByUser: true, date: '2021-02-19', dayCaption: 'Freitag', qty: 0 },
          { confirmedByUser: true, date: '2021-02-20', dayCaption: 'Samstag', qty: 0 },
          { confirmedByUser: true, date: '2021-02-21', dayCaption: 'Sonntag', qty: 0 },
        ],
        nextWeekTrend: null,
        packingInfo: 'G2 x 17 Stk',
        productId: '356',
        productName: 'contracted product 2',
        qty: 3,
      },
    ],
  },
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

test('renders ProductWeeklyScreen component correctly', () => {
  const wrapper = render(
    <MemoryRouter initialEntries={['/weekly/356']}>
      <RootStoreContext.Provider value={store}>
        <Route exact path="/weekly/:productId" component={ProductWeeklyScreen} />
      </RootStoreContext.Provider>
    </MemoryRouter>
  );
  const html = wrapper.html();
  expect(html).toContain('WeeklyDetailedReportingView.toolbar.caption');
  expect(html).toContain('<i class="fas fa-check"></i>');
  expect(html).toContain('15/02/2021');
  expect(html).toContain('16/02/2021');
  expect(html).toContain('17/02/2021');
  expect(html).toContain('18/02/2021');
  expect(html).toContain('19/02/2021');
  expect(html).toContain('20/02/2021');
  expect(html).toContain('21/02/2021');
  expect(html).toContain('Donnerstag');
  expect(html).toContain('Sonntag');
});
