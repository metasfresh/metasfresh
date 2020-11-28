import React from 'react';
import { mount, shallow, render } from 'enzyme';

import Checkbox from '../../../components/widget/Checkbox';
import fixtures from '../../../../test_setup/fixtures/widget/checkbox.json';

const createDummyProps = function(props) {
  return {
    disabled: false,
    tabIndex: 2,
    fullScreen: false,
    handlePatch: jest.fn(),
    ...props,
  };
};

describe('Checkbox component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const props = createDummyProps({
        ...fixtures.data1.widgetProps,
        widgetData: [
          {
            ...fixtures.data1.widgetProps.widgetData,
            value: false,
          }
        ]
      });

      const wrapper = shallow(<Checkbox {...props} />);
      const html = wrapper.html();

      expect(html).toContain('input-checkbox');
      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain('checkbox');
      expect(wrapper.find('.input-checkbox-tick').html()).not.toContain('checked'); 
    });

    it('renders without errors with props set', () => {
      const props = createDummyProps({
        ...fixtures.data1.widgetProps,
        disabled: true,
      });

      const wrapper = shallow(<Checkbox {...props} />);
      const html = wrapper.html();

      expect(html).toContain('input-checkbox');
      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain('checkbox');
      expect(wrapper.find('input').html()).toContain('disabled');
      expect(wrapper.find('.input-checkbox-tick').html()).toContain('checked'); 
    });
  });

  describe('functional tests', () => {
    it('renders without errors with props set', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...fixtures.data1.widgetProps,
        handlePatch: handlePatchSpy
      });

      const updatedWidgetData = [
        {
          ...fixtures.data1.widgetProps.widgetData,
          value: false,
        }
      ];
      const wrapper = mount(<Checkbox {...props} />);

      expect(wrapper.find('.input-checkbox-tick').html()).toContain('checked');
      wrapper.find('input[type="checkbox"]').simulate('change', { target: { checked: false } })

      expect(handlePatchSpy).toHaveBeenCalled();

      wrapper.setProps({ widgetData: updatedWidgetData });

      expect(wrapper.find('.input-checkbox-tick').html()).not.toContain('checked');
    });
  });
});
