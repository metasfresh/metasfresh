import React from 'react';
import { shallow } from 'enzyme';
import merge from 'merge';

import Password from '../../../components/widget/Password';
import fixtures from '../../../../test_setup/fixtures/widget/password.json';

const createDummyProps = function(props) {
  return {
    onSetWidgetType: jest.fn(),
    getClassNames: jest.fn(),
    ...props,
    widgetProperties: merge.recursive(
    true,
    {
      onChange: jest.fn(),
    },
    props.widgetProprties)
  };
};

describe('Password component', () => {
  it('renders without errors', () => {
    const props = createDummyProps({
      ...fixtures.props1,
    });
    const wrapper = shallow(<Password {...props} />);
    const html = wrapper.html();

    expect(html).toContain('input-inner-container');
    expect(html).toContain('meta-icon-edit');
    expect(html).toEqual(expect.not.stringContaining('meta-icon-show'));
  });

  it.todo('renders password as text');
});