import React from 'react';
import { shallow } from 'enzyme';
import App from '../../containers/App';

describe('Application', () => {
  it('renders without errors', () => {
    shallow(<App />);
  });
});
