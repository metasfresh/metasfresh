import React from 'react';
import { render } from 'enzyme';
import DailyNav from '../components/DailyNav';
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

test('renders DailyNav component correctly', () => {
  store.app.setCurrentDay('2021-02-06');
  store.app.setDayCaption('Saturday');
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <DailyNav />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  // left and right arrows for navigation need to be present
  expect(html).toContain(
    '<div class="column is-3 arrow-navigation is-vcentered p-4"><i class="fas fa-arrow-left fa-3x"></i></div>'
  );
  expect(html).toContain(
    'div class="column is-3 has-text-right arrow-navigation is-vcentered p-4"><i class="fas fa-arrow-right fa-3x"></i></div>'
  );
  expect(html).toContain('06/02/2021');
  expect(html).toContain('<div class="row is-full"> Saturday </div>');
});

test('renders DailyNav component correctly when static prop passed', () => {
  const staticDay = '03.02.2021';
  const staticCaption = 'Saturday';
  const wrapper = render(
    <Router history={history}>
      <Provider store={store}>
        <DailyNav isStatic={true} staticDay={staticDay} staticCaption={staticCaption} />
      </Provider>
    </Router>
  );
  const html = wrapper.html();
  // navigation arrows should not be present
  expect(html).not.toContain(
    '<div class="column is-3 arrow-navigation is-vcentered p-4"><i class="fas fa-arrow-left fa-3x"></i></div>'
  );
  expect(html).not.toContain(
    'div class="column is-3 has-text-right arrow-navigation is-vcentered p-4"><i class="fas fa-arrow-right fa-3x"></i></div>'
  );
  expect(html).toContain('03.02.2021');
  expect(html).toContain('<div class="row is-full"> Saturday </div>');
});
