import React from 'react';
import { render } from 'enzyme';
import RfQPriceEdit from '../components/RfQPriceEdit';
import { Store, RootStoreContext } from '../models/Store';
import { Provider } from 'mobx-react';
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

test('renders RfQDailyPriceEdit component correctly', () => {
  const wrapper = render(
    <MemoryRouter initialEntries={['/rfq/price/15']}>
      <Provider store={store}>
        <RootStoreContext.Provider value={store}>
          <Route exact path="/rfq/price/:rfqId" component={RfQPriceEdit} />
        </RootStoreContext.Provider>
      </Provider>
    </MemoryRouter>
  );
  const html = wrapper.html();

  expect(html).toContain('<div class="column is-11"><input type="number" class="product-input" value="1.2">');
  expect(html).toContain('<div class="column is-6"><i class="fas fa-2x fa-arrow-up"></i></div>');
  expect(html).toContain('<i class="fas fa-2x fa-arrow-down"></i>');
});
