import React from 'react';
import { render } from 'enzyme';
import WeeklyNav from '../components/WeeklyNav';
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

test('renders WeeklyNav component correctly', () => {
  store.app.setCurrentWeek('06.2021');
  store.app.setWeekCaption('KW06');
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <WeeklyNav />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  // left and right arrows for navigation need to be present
  expect(html).toContain('<i class="fas fa-arrow-right fa-3x"></i></div>');
  expect(html).toContain('<i class="fas fa-arrow-left fa-3x"></i>');
  expect(html).toContain('KW06');
  expect(html).toContain('06.2021');
});
