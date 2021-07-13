import React from 'react';
import { render } from 'enzyme';
import ProductScreen from '../components/ProductScreen';
import { Store } from '../models/Store';
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

test('renders ProductScreen component correctly', () => {
  const wrapper = render(
    <MemoryRouter initialEntries={['/products/2/']}>
      <Provider store={store}>
        <Route exact path="/products/:productId" component={ProductScreen} />
      </Provider>
    </MemoryRouter>
  );
  const html = wrapper.html();
  expect(html).toContain('<input type="number" class="product-input" step="1" value="12"></div>');
  expect(html).toContain('<i class="fas fa-2x fa-arrow-down"></i>');
  expect(html).toContain('<i class="fas fa-2x fa-arrow-up"></i>');
});
