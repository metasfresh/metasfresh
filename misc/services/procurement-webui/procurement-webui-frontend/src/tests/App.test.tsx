import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '../App';
import { store } from '../models/Store';
import { Provider } from 'mobx-react';

test('renders the App component and login button is present', () => {
  render(
    <Provider store={store}>
      <App />
    </Provider>
  );
  const linkElement = screen.getByText(/LoginView.fields.loginButton/i);
  expect(linkElement).toBeInTheDocument();
});
