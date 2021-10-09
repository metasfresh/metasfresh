import React from 'react';
import { render } from 'enzyme';
import Daily from '../components/Daily';
import { store } from '../models/Store';
import { Provider } from 'mobx-react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

const history = createMemoryHistory();
const forcedState = {
  path: '/login',
  text: 'Test',
};
history.push('/', forcedState);

test('renders Daily component correctly', () => {
  store.app.setCurrentDay('2021-02-06');
  store.app.setDayCaption('Saturday');
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <Daily />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('daily-nav');
  expect(html).toContain('<div class="row is-full"> 06/02/2021 </div>');
  expect(html).toContain('<div class="row is-full"> Saturday </div>');
});

test('renders daily correctly on the language change to DE', () => {
  store.i18n.changeLang('de_DE');
  store.app.setCurrentDay('2021-02-06');
  store.app.setDayCaption('Samstag');
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <Daily />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('<div class="row is-full"> 06.02.2021 </div>');
  expect(html).toContain('<div class="row is-full"> Samstag </div>');
});
