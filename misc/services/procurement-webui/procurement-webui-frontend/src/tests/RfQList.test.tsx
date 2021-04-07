import React from 'react';
import { render } from 'enzyme';
import RfQList from '../components/RfQList';
import { Store, RootStoreContext } from '../models/Store';
import { Provider } from 'mobx-react';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

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
  rfqs: {
    quotations: [
      {
        confirmedByUser: true,
        dateClose: '2021-07-31',
        dateEnd: '2021-09-01',
        dateStart: '2021-01-01',
        packingInfo: 'G1 x 10 kg',
        price: 1.2,
        priceRendered: '1,2 EUR',
        productName: 'Frisée Industrie',
        qtyPromised: '27 kg',
        qtyRequested: '900 kg',
        quantities: [
          {
            confirmedByUser: true,
            date: '2021-01-01',
            dayCaption: 'Freitag',
            qtyPromised: 2,
            qtyPromisedRendered: '2 kg',
          },
        ],
        rfqId: '15',
      },
    ],
  },
});

test('renders RfQList component correctly', () => {
  const wrapper = render(
    <Provider store={store}>
      <RootStoreContext.Provider value={store}>
        <RfQList />
      </RootStoreContext.Provider>
    </Provider>
  );
  const html = wrapper.html();

  expect(html).toContain('<div class="column is-size-7 no-p">01/01/2021 - 01/09/2021</div>');
  expect(html).toContain('<div class="column mt-2 is-size-2-mobile no-p">27 kg</div>');
  expect(html).toContain('<span><i class="fas fa-check"></i></span>');
});
