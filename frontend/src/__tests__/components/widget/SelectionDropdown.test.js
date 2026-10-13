import React from 'react';
import { mount, shallow } from 'enzyme';

import SelectionDropdown from '../../../components/widget/SelectionDropdown';
import fixtures from '../../../../test_setup/fixtures/selection_dropdown.json';

const createDummyProps = function(props, data) {
  return {
    onCancel: jest.fn(),
    onChange: jest.fn(),
    onSelect: jest.fn(),
    ...props,
    options: data,
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
    const options = fixtures.data1.options;
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
    expect(onChangeSpy).toHaveBeenCalledWith(options[1]);

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
    const options = fixtures.data1.options;
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

    const ref = wrapper.instance().optionToRef.get(options[2]);
    expect(spyScroll).toHaveBeenCalledWith(ref, false);

    map.keydown({ ...eventProps, key: 'Escape' });

    wrapper.instance().forceUpdate();
    wrapper.update();

    expect(onCancelSpy).toHaveBeenCalled();
  });

  // The ref Map is keyed by option object identity and is cleared whenever `listHash`
  // changes, while `handleKeyDown` stays registered on `window`. An arrow key handled
  // while the Map holds no entry for the newly-selected option therefore reached
  // `scrollIntoView(undefined)`, which dereferenced it and threw an uncaught
  // "Cannot read properties of undefined (reading 'getBoundingClientRect')".
  // Navigation itself must still happen — the guard skips scrolling, not selecting.
  describe('arrow navigation when the option ref is missing', () => {
    const arm = () => {
      const map = {};
      window.addEventListener = jest.fn((event, cb) => {
        map[event] = cb;
      });
      return map;
    };
    const eventProps = () => ({
      preventDefault: jest.fn(),
      stopPropagation: jest.fn(),
    });

    it('does not throw and still selects when the ref map was cleared', () => {
      const onChangeSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.widgetData1,
          selected: fixtures.data1.options[0],
          onChange: onChangeSpy,
        },
        fixtures.data1.options
      );
      const map = arm();
      const wrapper = mount(<SelectionDropdown {...props} />);

      // the state the listHash change leaves behind
      wrapper.instance().optionToRef.clear();

      expect(() => map.keydown({ ...eventProps(), key: 'ArrowDown' })).not.toThrow();
      expect(onChangeSpy).toHaveBeenCalledWith(fixtures.data1.options[1]);
    });

    it('scrollIntoView tolerates a missing element', () => {
      const props = createDummyProps(
        { ...fixtures.widgetData1, selected: fixtures.data1.options[0] },
        fixtures.data1.options
      );
      arm();
      const instance = mount(<SelectionDropdown {...props} />).instance();

      expect(() => instance.scrollIntoView(undefined, false)).not.toThrow();
      expect(() => instance.scrollIntoView(null, false)).not.toThrow();
    });
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
    const options = fixtures.data1.options;
    const newOption = options[1];

    const wrapper = mount(<SelectionDropdown {...props} />);
    const spyEnter = jest.spyOn(wrapper.instance(), 'handleMouseEnter');
    const spyDown = jest.spyOn(wrapper.instance(), 'handleMouseDown');

    const optionEl = wrapper.find(`[data-testid="option-${newOption.key}"]`);
    optionEl.prop('onMouseEnter')();

    expect(spyEnter).toHaveBeenCalled();
    expect(onChangeSpy).toHaveBeenCalled();

    optionEl.prop('onMouseDown')();
    expect(spyDown).toHaveBeenCalled();
    expect(onSelectSpy).toHaveBeenCalledWith(newOption, true);
  });
});
