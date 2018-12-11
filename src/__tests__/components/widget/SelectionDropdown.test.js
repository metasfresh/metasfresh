import React from 'react';
import { List } from 'immutable';
import { mount, shallow } from 'enzyme';

import SelectionDropdown from '../../../components/widget/SelectionDropdown';
import fixtures from '../../../../test_setup/fixtures/selection_dropdown.json';

const createDummyProps = function(props, data) {
  return {
    onCancel: jest.fn(),
    onChange: jest.fn(),
    onSelect: jest.fn(),
    ...props,
    options: List(data),
  };
};

describe('SelectionDropdown component', () => {
  it('renders without errors', () => {
    const props = createDummyProps(
      {
        ...fixtures.widgetData1,
      },
      fixtures.data1.options,
    );

    const wrapper = shallow(<SelectionDropdown {...props} />);
    const html = wrapper.html();

    expect(html).toContain('input-dropdown-list');
    expect(wrapper.find('.input-dropdown-list-option').length).toBe(3);
    expect(html).toContain(`${fixtures.widgetData1.width}px`);
    expect(html).toContain(fixtures.data1.options[0].caption);
  });
});