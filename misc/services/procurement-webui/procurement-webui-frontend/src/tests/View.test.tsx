import React from 'react';
import { render } from 'enzyme';
import View from '../components/View';
import './testsSetup'; // this is where the adapter is configured

test('renders Weekly arrow', () => {
  const wrapper = render(<View>Test string</View>);
  const html = wrapper.html();
  expect(html).toContain('Test string');
});
