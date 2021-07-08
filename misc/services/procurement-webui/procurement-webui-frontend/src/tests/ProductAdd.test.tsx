import React from 'react';
import { render } from 'enzyme';
import ProductAdd from '../components/ProductAdd';
import { Store, RootStoreContext } from '../models/Store';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

test('renders ProductAdd component correctly', () => {
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
        <ProductAdd />
      </RootStoreContext.Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('<div class="box">{SelectProductView.showMeNotContractedButton}</div>');
});
