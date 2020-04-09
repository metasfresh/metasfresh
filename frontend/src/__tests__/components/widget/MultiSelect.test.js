import React from 'react';
import { mount, shallow, render } from 'enzyme';

import MultiSelect from '../../../components/widget/MultiSelect';
import props from '../../../../test_setup/fixtures/multiselect.json';

describe('MultiSelect component', () => {
  describe('rendering tests:', () => {
    it('renders without errors when selection is present', () => {
      const wrapper = shallow(<MultiSelect onFocus={jest.fn()} {...props} />);
      const html = wrapper.html();

      expect(html).toContain('input-checkbox');
      expect(html).toContain('checkbox');
      expect(html).toContain('filter-multiselect');
      expect(html).toContain('checked=');
    });

    it('renders without errors when no selection is made', () => {
      props.selectedItems = [];
      const wrapper = shallow(<MultiSelect onFocus={jest.fn()} {...props} />);
      const html = wrapper.html();

      expect(html).toContain('input-checkbox');
      expect(html).toContain('checkbox');
      expect(html).toContain('filter-multiselect');
      expect(html).not.toContain('checked=');
    });

  });

  describe('functional tests', () => {
    it('when we have an item in options and is not checked result should contain any checked input', () => {
      const handlePatchSpy = jest.fn();
      props.options = [{
        "key": "1000310",
        "caption": "0208"
      }];
      const wrapper = mount(<MultiSelect onFocus={handlePatchSpy} onSelect={jest.fn()} {...props} />);

      wrapper.find('input[type="checkbox"]').simulate('change', { target: { checked: false } })
      expect(wrapper.find('.input-checkbox-tick').html()).not.toContain('checked');
    });
  });

  describe('functional tests', () => {
    it('the function should be called', () => {
      const handlePatchSpy = jest.fn();
      mount(<MultiSelect onFocus={handlePatchSpy} onSelect={jest.fn()} {...props} />);

      expect(handlePatchSpy).toHaveBeenCalled();
    });
  });
});


