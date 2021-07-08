import React from 'react';
import { render } from 'enzyme';
import WeeklyProductList from '../components/WeeklyProductList';
import { Store, RootStoreContext } from '../models/Store';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured
import { MemoryRouter } from 'react-router';

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

test('renders ProductWeeklyEdit component correctly', () => {
  const wrapper = render(
    <MemoryRouter initialEntries={['/weekly/edit/356/2021-02-17/Mittwoch']}>
      <RootStoreContext.Provider value={store}>
        <WeeklyProductList headerText="Test Header" />
      </RootStoreContext.Provider>
    </MemoryRouter>
  );
  const html = wrapper.html();
  expect(html).toContain('contracted product 2');
  expect(html).toContain('<div class="column is-size-7 no-p">G2 x 17 Stk</div>');
  expect(html).toContain('<div class="column mt-2 is-size-2-mobile no-p has-text-right">3</div');
  expect(html).toContain('<i class="fas fa-check"></i>');
});
