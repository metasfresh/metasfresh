import React from 'react';
import { shallow } from 'enzyme';
import Calendar from '../../../containers/calendar/Calendar';

describe('Calendar', () => {
  it('renders without errors', () => {
    shallow(<Calendar />);
  });
});
