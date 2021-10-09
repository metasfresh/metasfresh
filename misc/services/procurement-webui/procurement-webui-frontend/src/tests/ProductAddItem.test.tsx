import React from 'react';
import { render } from 'enzyme';
import ProductAddItem from '../components/ProductAddItem';
import { Store, RootStoreContext } from '../models/Store';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

test('renders ProductAddItem component correctly', () => {
  const history = createMemoryHistory();
  const forcedState = {
    path: '/login',
    text: 'Test',
  };
  history.push('/', forcedState);
  const store = Store.create({
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

  const wrapper = render(
    <Router history={history}>
      <RootStoreContext.Provider value={store}>
        <ProductAddItem />
      </RootStoreContext.Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain(
    '<div class="box"><div class="columns is-mobile"><div class="column is-12"><div class="columns"><div class="column is-size-4-mobile no-p"></div><div class="column is-size-7 no-p"></div></div></div></div></div>'
  );
});

test('renders ProductItem with specific props', () => {
  const wrapper = render(<ProductAddItem id="2" name="Test Name" packType="packingInfo" />);
  const html = wrapper.html();
  expect(html).toContain('Test Name');
  expect(html).toContain('packingInfo');
});
