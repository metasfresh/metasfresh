import React from 'react';
import { mount, shallow, render } from 'enzyme';

import Checkbox from '../../../components/widget/Checkbox';
import fixtures from '../../../../test_setup/fixtures/widget/checkbox.json';

const createDummyProps = function(props) {
  return {
    disabled: false,
    tabIndex: 2,
    fullScreen: false,
    handlePatch: jest.fn(() => Promise.resolve(true)),
    ...props,
  };
};

describe('Checkbox component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const props = createDummyProps({
        ...fixtures.data1.widgetProps,
        widgetData: 
            {...fixtures.data1.widgetProps.widgetData}
        
      });
      props.widgetData.value = false;
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
      props.widgetData.value = true;
      const wrapper = shallow(<Checkbox {...props} />);
      const html = wrapper.html();

      expect(html).toContain('input-checkbox');
      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain('checkbox');
      expect(wrapper.find('input').html()).toContain('disabled');
      expect(html).toContain('input-checkbox-tick');
    });
  });

  describe('functional tests', () => {
    it('renders without errors with props set', () => {
      const handlePatchSpy = jest.fn(() => Promise.resolve(true));
      const updateItemsSpy = jest.fn();
      const props = createDummyProps({
        ...fixtures.data1.widgetProps,
        handlePatch: handlePatchSpy,
        updateItems: updateItemsSpy,
      });

      const updatedWidgetData = 
        {
          ...fixtures.data1.widgetProps.widgetData,
          value: false,
        }
      ;
      const wrapper = mount(<Checkbox {...props} />);

      const html = wrapper.html();

      expect(html).toContain('input-checkbox-tick');
      expect(wrapper.find('.input-checkbox-tick').html()).not.toContain('checked');
      
      wrapper.find('input[type="checkbox"]').simulate('change', { target: { checked: true } })

      expect(handlePatchSpy).toHaveBeenCalled();

      wrapper.setProps({ widgetData: updatedWidgetData });
      expect(wrapper.find('.input-checkbox-tick').html()).toContain('checked');
    });
  });
});
