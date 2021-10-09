import React from 'react';
import { shallow } from 'enzyme';

import Switch from '../../../components/widget/Switch';
import fixtures from '../../../../test_setup/fixtures/widget/switch.json';

const createDummyProps = function(props) {
  return {
    onPatch: jest.fn(),
    ...props,
  };
};

describe('Switch component', () => {
  it('renders without errors', () => {
    const props = createDummyProps({
      ...fixtures.props1,
    });
    const wrapper = shallow(<Switch {...props} />);
    const html = wrapper.html();

    expect(html).toContain('input-switch');
    expect(html).toEqual(expect.not.stringContaining('input-table'));
    expect(html).toEqual(expect.not.stringContaining('input-error'));
    expect(wrapper.find('input').props().checked).toBeTruthy();
  });
});