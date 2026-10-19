import React from 'react';
import { mount } from 'enzyme';

import CostPrice from '../../../components/widget/CostPrice';

describe('CostPrice component', () => {
  const renderInEditMode = () => {
    const wrapper = mount(
      <CostPrice value={'12.35'} precision={2} onChange={jest.fn()} onBlur={jest.fn()} />
    );
    wrapper.instance().focus();
    wrapper.update();
    return wrapper;
  };

  it('shows the formatted value in a text input while not edited', () => {
    const wrapper = mount(
      <CostPrice value={'12.35'} precision={2} onChange={jest.fn()} onBlur={jest.fn()} />
    );

    expect(wrapper.find('input').props().type).toBe('text');
  });

  it('accepts a fractional price in edit mode (no whole-number step constraint)', () => {
    const wrapper = renderInEditMode();
    const input = wrapper.find('input');

    // A text input with a decimal keyboard, not a browser number input: that one drops a decimal comma, and its
    // default step of 1 would make 12.35 invalid, which blocks a <form> submit.
    expect(input.props().type).toBe('text');
    expect(input.props().inputMode).toBe('decimal');
    expect(input.getDOMNode().validity.stepMismatch).toBe(false);
    expect(input.getDOMNode().checkValidity()).toBe(true);
  });
});
