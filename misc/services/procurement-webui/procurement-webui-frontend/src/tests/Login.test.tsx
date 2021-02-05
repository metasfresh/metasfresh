import React from 'react';
import { render } from 'enzyme';
import Login from '../components/Login';
import { Store, RootStoreContext } from '../models/Store';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import './testsSetup'; // this is where the adapter is configured

test('renders Login component correctly', () => {
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
        <Login store={store} />
      </RootStoreContext.Provider>
    </Router>
  );
  const html = wrapper.html();
  expect(html).toContain('logo.png');
  expect(html).toContain(
    '<input type="email" class="input is-medium" id="email" value="" placeholder="{LoginView.fields.email}">'
  );
  expect(html).toContain(
    '<input type="password" class="input is-medium" id="password" value="" placeholder="{LoginView.fields.password}">'
  );
  expect(html).toContain('<p class="control"><a class="green-color">{LoginView.fields.forgotPasswordButton}</a></p>');
  expect(html).toContain(
    '<button type="submit" class="button is-medium is-fullwidth is-success is-green-bg">{LoginView.fields.loginButton}</button>'
  );
  expect(html).toContain('poweredby.png');
  expect(html).toContain('login-poweredby-logo');
});
