import React from 'react';
import { render } from 'enzyme';
import ProductList from '../components/ProductList';
import { Store, RootStoreContext } from '../models/Store';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

test('renders ProductList component correctly', () => {
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

  const wrapper = render(
    <Router history={history}>
      <RootStoreContext.Provider value={store}>
        <ProductList store={store} />
      </RootStoreContext.Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('<div class="column is-size-7 no-p">G1 x 10 kg</div>');
  expect(html).toContain('<div class="column mt-2 is-size-2-mobile no-p has-text-right">12</div>');
  expect(html).toContain('<i class="fas fa-check"></i>');
});
