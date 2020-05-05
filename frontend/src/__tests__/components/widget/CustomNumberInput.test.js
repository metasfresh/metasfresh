import React from 'react';
import { mount, shallow, render } from 'enzyme';

import NumberInput from '../../../components/widget/CustomNumberInput';
import fixtures from '../../../../test_setup/fixtures/custom_number_input.json';

const createDummyProps = function(props) {
  return {
    onBlur: jest.fn(),
    onChange: jest.fn(),
    onFocus: jest.fn(),
    onKeyDown: jest.fn(),
    ...props,
  };
};

describe('CustomerNumberInput component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const props = createDummyProps({
        ...fixtures,
      });

      const wrapper = shallow(<NumberInput {...props} />);

      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain('text');
    });

    it('renders disabled state', () => {
      const props = createDummyProps({
        ...fixtures,
        disabled: true
      });

      const wrapper = shallow(<NumberInput {...props} />);

      expect(wrapper.find('input').html()).toContain('disabled');
    });
  });

  describe('functional tests', () => {
    it('renders without errors with props set', () => {
      const props = createDummyProps({
        ...fixtures,
      });

      const wrapper = shallow(<NumberInput {...props} />);

      wrapper.find('input').simulate(
        'change',
        {
          target: { value: '14,50' },
          preventDefault: jest.fn(),
        },
      );

      expect(wrapper.state('value')).toEqual('14,50');
    });
  });
});
