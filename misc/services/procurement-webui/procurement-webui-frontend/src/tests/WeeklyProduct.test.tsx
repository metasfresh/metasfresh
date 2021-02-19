import React from 'react';
import { render } from 'enzyme';
import WeeklyProduct from '../components/WeeklyProduct';
import './testsSetup'; // this is where the adapter is configured

test('renders WeeklyProduct', () => {
  const wrapper = render(
    <WeeklyProduct productId="321" name="Brocolli" packType="pack-test" qty={10} nextWeekTrend="trend-up" />
  );
  const html = wrapper.html();
  expect(html).toContain('<i class="fas fa-lg fa-arrow-up up"></i>');
  expect(html).toContain('Brocolli');
  expect(html).toContain('pack-test');
  expect(html).toContain('<div class="column mt-2 is-size-2-mobile no-p has-text-right">10</div>');
});
