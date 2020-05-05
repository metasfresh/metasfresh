import React from 'react';
import { shallow } from 'enzyme';
import Calendar from '../../containers/Calendar';

describe('Calendar', () => {
  it('renders without errors', () => {
    shallow(<Calendar />);
  });
});
