import React from 'react';
import { render } from 'enzyme';
import Header from '../components/Header';
import { store } from '../models/Store';
import { Provider } from 'mobx-react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

test('renders Header component correctly', () => {
  const history = createMemoryHistory();
  const forcedState = {
    path: '/login',
    text: 'Test',
  };
  history.push('/', forcedState);
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <Header />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('{Logout.caption}');
});

test('renders Header component when params change', () => {
  store.navigation.setTopViewName('Info');
  const history = createMemoryHistory();
  const forcedState = {
    path: '/Info',
    text: 'Info',
  };
  history.push('/info', forcedState);
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <Header />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('<div class="header-title"><h4 class="title is-4">Info</h4></div');
});
