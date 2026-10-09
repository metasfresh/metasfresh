import React from 'react';
import { shallow } from 'enzyme';

import Amount from '../../../components/widget/Amount';

describe('Amount component', () => {
  it('renders without errors', () => {
    const wrapper = shallow(
      <Amount
        widgetField={"QtyOrdered"}
        value={"8"}
        // step,
        // devices,
        //
        // id,
        autoComplete={"off"}
        className={jest.fn()}
        // inputClassName,
        disabled={true}
        placeholder={"none"}
        tabIndex={-1}
        title={"8"}
        //
        onChange={jest.fn()}
        onFocus={jest.fn()}
        onBlur={jest.fn()}
        onKeyDown={jest.fn()}
        onPatch={jest.fn()}
      />
    );
    const html = wrapper.html();

    expect(html).toContain('number-field');
    expect(wrapper.find('input').length).toBe(1);

    expect(wrapper.find('input').props().value).toEqual("8");
  });

  it.todo('renders devices widget');
});
