import React from 'react';
import { shallow } from 'enzyme';
import Calendar from '../../../pages/calendar/Calendar';

describe('Calendar', () => {
  it('renders without errors', () => {
    shallow(<Calendar view="resourceTimelineYear" />);
  });
});
