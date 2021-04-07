import React from 'react';
import { render } from 'enzyme';
import Prognose from '../components/Prognose';
import './testsSetup'; // this is where the adapter is configured

test('renders Prognose correctly', () => {
  const wrapper = render(<Prognose productId="2" nextWeek="06.2021" nextWeekCaption="KW06" currentWeek="05.2021" />);
  const html = wrapper.html();

  expect(html).toContain('WeeklyDetailedReportingView.toolbar.caption');
  expect(html).toContain('<i class="fas fa-arrow-up fa-lg"></i>');
  expect(html).toContain('<i class="fas fa-arrow-down fa-lg"></i>');
  expect(html).toContain('<i class="fas fa-arrow-right fa-lg"></i>');
  expect(html).toContain('<i class="fas fa-times fa-lg"></i>');
});
