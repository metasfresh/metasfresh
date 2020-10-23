import React from 'react';
import { mount, shallow, render } from 'enzyme';

import Amount from '../../../components/widget/Amount';
import DevicesWidget from '../../../components/widget/Amount';
import fixtures from '../../../../test_setup/fixtures/widget/amount.json';

const createDummyProps = function(props) {
  return {
    onPatch: jest.fn(),
    getClassNames: jest.fn(),
    ...props,
  };
};

describe('Amount component', () => {
  it('renders without errors', () => {
    const props = createDummyProps({
      ...fixtures.props1,
    });
    const wrapper = shallow(<Amount {...props} />);
    const html = wrapper.html();

    expect(html).toContain('number-field');
    expect(wrapper.find('input').length).toBe(1);

    expect(wrapper.find('input').props().value).toEqual(props.widgetProperties.value);
  });

  it.todo('renders devices widget');
});