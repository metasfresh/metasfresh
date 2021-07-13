import React from 'react';
import { List } from 'immutable';
import { mount, shallow, render } from 'enzyme';

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
      fixtures.data1.options
    );

    const wrapper = shallow(<SelectionDropdown {...props} />);
    const html = wrapper.html();

    expect(html).toContain('input-dropdown-list');
    expect(wrapper.find('.input-dropdown-list-option').length).toBe(3);
    expect(html).toContain(`${fixtures.widgetData1.width}px`);
    expect(html).toContain(fixtures.data1.options[0].caption);
  });

  it('renders loading state', () => {
    const props = createDummyProps(
      {
        ...fixtures.widgetData1,
        loading: true,
      },
      []
    );

    const wrapper = shallow(<SelectionDropdown {...props} />);

    const html = wrapper.html();

    expect(html).toContain('input-dropdown-list');
    expect(wrapper.find('.input-dropdown-list-header').length).toBe(1);
    expect(html).toContain('rotate icon-rotate');
  });

  it('properly handles keyboard events and selects options', () => {
    const onChangeSpy = jest.fn();
    const props = createDummyProps(
      {
        ...fixtures.widgetData1,
        selected: fixtures.data1.options[0],
        onChange: onChangeSpy,
      },
      fixtures.data1.options
    );
    const options = List(fixtures.data1.options);
    const map = {};
    window.addEventListener = jest.fn((event, cb) => {
      map[event] = cb;
    });
    const eventProps = {
      preventDefault: jest.fn(),
      stopPropagation: jest.fn(),
    };

    const wrapper = mount(<SelectionDropdown {...props} />);
    const instance = wrapper.instance();

    expect(instance.ignoreMouse).toBe(false);
    map.keydown({ ...eventProps, key: 'ArrowDown' });

    expect(instance.ignoreMouse).toBe(true);
    expect(onChangeSpy).toHaveBeenCalledWith(options.get(1));

    map.keyup({ ...eventProps, key: 'ArrowDown' });
    expect(instance.ignoreMouse).toBe(false);
  });

  it('properly handles keyboard events and selects options', () => {
    const onCancelSpy = jest.fn();
    const props = createDummyProps(
      {
        ...fixtures.widgetData1,
        selected: fixtures.data1.options[0],
        onCancel: onCancelSpy,
      },
      fixtures.data1.options
    );
    const options = List(fixtures.data1.options);
    const map = {};
    window.addEventListener = jest.fn((event, cb) => {
      map[event] = cb;
    });
    const eventProps = {
      preventDefault: jest.fn(),
      stopPropagation: jest.fn(),
    };

    // this type of spy should also work with `shallow` mounting
    const spyDown = jest.spyOn(SelectionDropdown.prototype, 'handleKeyDown');
    const spyScroll = jest.spyOn(SelectionDropdown.prototype, 'scrollIntoView');

    const wrapper = mount(<SelectionDropdown {...props} />);
    map.keydown({ ...eventProps, keyCode: 110, key: 'n' });
    wrapper.instance().forceUpdate();
    wrapper.update();

    expect(spyDown).toHaveBeenCalledWith({
      ...eventProps,
      keyCode: 110,
      key: 'n',
    });

    const ref = wrapper.instance().optionToRef.get(options.get(2));
    expect(spyScroll).toHaveBeenCalledWith(ref, false);

    map.keydown({ ...eventProps, key: 'Escape' });

    wrapper.instance().forceUpdate();
    wrapper.update();

    expect(onCancelSpy).toHaveBeenCalled();
  });

  it('properly handles keyboard events and selects options', () => {
    const onSelectSpy = jest.fn();
    const onChangeSpy = jest.fn();
    const props = createDummyProps(
      {
        ...fixtures.widgetData1,
        selected: fixtures.data1.options[0],
        onSelect: onSelectSpy,
        onChange: onChangeSpy,
      },
      fixtures.data1.options
    );
    const options = List(fixtures.data1.options);
    const newOption = options.get(1);

    const wrapper = mount(<SelectionDropdown {...props} />);
    const spyEnter = jest.spyOn(wrapper.instance(), 'handleMouseEnter');
    const spyDown = jest.spyOn(wrapper.instance(), 'handleMouseDown');

    const optionEl = wrapper.find(
      `[data-test-id="${newOption.key}${newOption.caption}"]`
    );
    optionEl.prop('onMouseEnter')();

    expect(spyEnter).toHaveBeenCalled();
    expect(onChangeSpy).toHaveBeenCalled();

    optionEl.prop('onMouseDown')();
    expect(spyDown).toHaveBeenCalled();
    expect(onSelectSpy).toHaveBeenCalledWith(newOption, true);
  });
});
