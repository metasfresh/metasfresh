import React from 'react';
import { render } from 'enzyme';
import BottomNav from '../components/BottomNav';
import { store } from '../models/Store';
import { Provider } from 'mobx-react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

test('renders bottom nav correctly on main screen', () => {
  const history = createMemoryHistory();
  const forcedState = {
    path: '/login',
    text: 'Test',
  };
  history.push('/', forcedState);
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <BottomNav forcedState={forcedState} />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('{DailyReportingView.weekViewButton}');
  expect(html).toContain('{DailyReportingView.addProductButton}');
  expect(html).toContain('{DailyReportingView.sendButton}');
  expect(html).toContain('{InfoMessageView.caption.short}');
  expect(html).toContain('{RfQView.caption}');
  expect(html).toContain('far fa-money-bill-alt');
});

test('renders bottom nav correctly on sub-screen', () => {
  const history = createMemoryHistory();
  const forcedState = {
    path: '/info',
    text: 'Info',
  };
  history.push('/info', forcedState);
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <BottomNav forcedState={forcedState} />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('<span class="is-size-4">Info</span>');
});
