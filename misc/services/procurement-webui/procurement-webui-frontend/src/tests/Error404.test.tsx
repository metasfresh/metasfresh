import React from 'react';
import { render } from 'enzyme';
import Error404 from '../components/Error404';
import './testsSetup'; // this is where the adapter is configured

test('renders Error404 component', () => {
  const wrapper = render(<Error404 />);
  const html = wrapper.html();
  expect(html).toContain('<p>{error.default}</p>');
});
