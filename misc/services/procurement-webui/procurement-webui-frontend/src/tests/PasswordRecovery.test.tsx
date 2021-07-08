import React from 'react';
import { render, screen } from '@testing-library/react';
import PasswordRecovery from '../components/PasswordRecovery';
import { store } from '../models/Store';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { Provider } from 'mobx-react';

test('renders ProsuctRecovery', () => {
  const history = createMemoryHistory();
  const forcedState = {
    path: '/login',
    text: 'Test',
  };
  history.push('/', forcedState);
  render(
    <Router history={history}>
      <Provider store={store}>
        <PasswordRecovery splat={location.pathname.replace('/', '')} />
      </Provider>
    </Router>
  );
  const linkElement = screen.getByPlaceholderText(/{LoginView.fields.email}/i);
  expect(linkElement).toBeInTheDocument();
});
