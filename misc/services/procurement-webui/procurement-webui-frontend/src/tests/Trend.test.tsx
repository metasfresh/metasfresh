import React from 'react';
import { render } from 'enzyme';
import Trend from '../components/Trend';
import './testsSetup'; // this is where the adapter is configured

test('renders trend-up arrow', () => {
  const wrapper = render(<Trend trend="trend-up" />);
  const html = wrapper.html();
  expect(html).toContain('<i class="fas fa-lg fa-arrow-up up"></i>');
});

test('renders trend-down arrow', () => {
  const wrapper = render(<Trend trend="trend-down" />);
  const html = wrapper.html();
  expect(html).toContain('<i class="fas fa-lg fa-arrow-down down"></i>');
});

test('renders fa-arrow-right arrow', () => {
  const wrapper = render(<Trend trend="trend-even" />);
  const html = wrapper.html();
  expect(html).toContain('<i class="fas fa-lg fa-arrow-right right"></i>');
});

test('renders trend-zero', () => {
  const wrapper = render(<Trend trend="trend-zero" />);
  const html = wrapper.html();
  expect(html).toContain('<i class="fas fa-lg fa-times disabled"></i>');
});
