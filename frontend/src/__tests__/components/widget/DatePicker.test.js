import React from 'react';
import { mount } from 'enzyme';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import MomentTZ from 'moment-timezone';

import DatePicker from '../../../components/widget/DateTime/DatePicker';
import TetheredDateTime from '../../../components/widget/DateTime/TetheredDateTime';

/**
 * Grid date cell: the grid passes isGridCell / isPointerEdit / gridCellValue (only
 * TableCell does, only for Date cells). Every other date widget (form, modal, filter) must keep
 * its behaviour: calendar auto-opens on focus, Escape/Enter are swallowed, typing patches at once.
 */
const DISPLAY_FORMAT = 'DD.MM.YYYY';

const mounted = [];

const mountPicker = (props) => {
  const store = createStore(() => ({}));
  const wrapper = mount(
    <Provider store={store}>
      <DatePicker
        field="DatePromised"
        dateFormat={DISPLAY_FORMAT}
        timeZone="Europe/Berlin"
        inputProps={{}}
        patch={jest.fn()}
        handleChange={jest.fn()}
        handleBackdropLock={jest.fn()}
        {...props}
      />
    </Provider>
  );
  mounted.push(wrapper);
  return {
    wrapper,
    // the inner (unwrapped) component, found by what it is rather than by a displayName
    picker: () =>
      wrapper
        .findWhere(
          (node) =>
            node.instance() &&
            typeof node.instance().commitTypedDate === 'function'
        )
        .first()
        .instance(),
  };
};

// react-onclickoutside registers document listeners on mount; convertToMoment sets the global locale
const initialLocale = MomentTZ.locale();
afterEach(() => {
  mounted.splice(0).forEach((wrapper) => wrapper.unmount());
  MomentTZ.locale(initialLocale);
});

const keyEvent = (key) => ({ key, stopPropagation: jest.fn() });

describe('DatePicker — grid date cell', () => {
  it('the grid date input carries js-input-field (TableRow._editProperty focuses by it)', () => {
    const { wrapper } = mountPicker({ isGridCell: true, value: null });
    const input = wrapper.find('input');
    expect(input.hasClass('js-input-field')).toBe(true);
    expect(input.hasClass('form-control')).toBe(true);
  });

  it('a non-grid date input does NOT carry js-input-field', () => {
    const { wrapper } = mountPicker({ value: null });
    expect(wrapper.find('input').hasClass('js-input-field')).toBe(false);
  });

  it('viewDate is the cell value parsed with the display format (value itself unparsed/empty)', () => {
    const { wrapper } = mountPicker({
      isGridCell: true,
      gridCellValue: '20.11.2026',
      value: null,
    });
    const viewDate = wrapper.find(TetheredDateTime).prop('viewDate');
    expect(MomentTZ.isMoment(viewDate)).toBe(true);
    expect(viewDate.isValid()).toBe(true);
    expect(viewDate.format('YYYY-MM-DD')).toBe('2026-11-20');
  });

  it('no viewDate is passed for an unparseable cell value (react-datetime default applies)', () => {
    const { wrapper } = mountPicker({
      isGridCell: true,
      gridCellValue: 'not a date',
      value: null,
    });
    expect(wrapper.find(TetheredDateTime).prop('viewDate')).toBeUndefined();
  });

  it('the opened grid calendar shows the month of the cell value, not today\'s', () => {
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      isPointerEdit: true,
      gridCellValue: '20.03.2031',
      value: null,
    });
    picker().handleFocus();
    wrapper.update();
    expect(wrapper.find('.rdtSwitch').first().text()).toMatch(/2031/);
  });

  it('a grid cell with an empty value gets no viewDate (calendar opens on today)', () => {
    const { wrapper } = mountPicker({
      isGridCell: true,
      gridCellValue: '',
      value: null,
    });
    expect(wrapper.find(TetheredDateTime).prop('viewDate')).toBeUndefined();
  });

  it('a non-grid date widget gets no viewDate override', () => {
    const { wrapper } = mountPicker({
      gridCellValue: '20.11.2026',
      value: null,
    });
    expect(wrapper.find(TetheredDateTime).prop('viewDate')).toBeUndefined();
  });

  it('keyboard-entered grid edit: focusing the input does NOT auto-open the calendar', () => {
    const { picker } = mountPicker({
      isGridCell: true,
      isPointerEdit: false,
      value: null,
    });
    picker().handleFocus();
    expect(picker().state.isCalendarOpen).toBe(false);
  });

  it('pointer-entered grid edit: focusing the input opens the calendar', () => {
    const { picker } = mountPicker({
      isGridCell: true,
      isPointerEdit: true,
      value: null,
    });
    picker().handleFocus();
    expect(picker().state.isCalendarOpen).toBe(true);
  });

  it('a click on the grid date input opens the calendar even after keyboard entry', () => {
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      isPointerEdit: false,
      value: null,
    });
    wrapper.find('input').simulate('click');
    expect(picker().state.isCalendarOpen).toBe(true);
  });

  it('a NON-grid date widget STILL auto-opens its calendar on focus', () => {
    const { picker } = mountPicker({ value: null });
    picker().handleFocus();
    expect(picker().state.isCalendarOpen).toBe(true);
  });

  it('grid Escape reaches the grid (not swallowed), discards the typed date and restores the stored one', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      value: MomentTZ('2026-11-20', 'YYYY-MM-DD'),
      patch,
    });
    wrapper.find('input').simulate('change', { target: { value: '21.11.2026' } });
    // precondition: the typed date IS pending, so the Escape below has something to discard
    expect(picker().pendingTypedDate.format('YYYY-MM-DD')).toBe('2026-11-21');
    expect(wrapper.find('input').prop('value')).toBe('21.11.2026');

    const escape = keyEvent('Escape');
    picker().handleKeydown(escape);
    expect(escape.stopPropagation).not.toHaveBeenCalled();
    expect(picker().pendingTypedDate).toBeNull();

    picker().handleBlur();
    expect(patch).not.toHaveBeenCalled(); // stored value kept
    wrapper.update();
    expect(wrapper.find('input').prop('value')).toBe('20.11.2026'); // and shown again
  });

  it('grid Enter reaches the grid (not swallowed) and saves the typed date', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      value: MomentTZ('2026-11-20', 'YYYY-MM-DD'),
      patch,
    });
    wrapper.find('input').simulate('change', { target: { value: '21.11.2026' } });
    expect(patch).not.toHaveBeenCalled(); // pending until the cell is left
    const enter = keyEvent('Enter');
    picker().handleKeydown(enter);
    expect(enter.stopPropagation).not.toHaveBeenCalled();
    expect(patch).toHaveBeenCalledTimes(1);
    expect(patch.mock.calls[0][0].format('YYYY-MM-DD')).toBe('2026-11-21');
  });

  it('grid Enter without typing keeps the stored value (no patch)', () => {
    const patch = jest.fn();
    const { picker } = mountPicker({
      isGridCell: true,
      value: MomentTZ('2026-11-20', 'YYYY-MM-DD'),
      patch,
    });
    picker().handleKeydown(keyEvent('Enter'));
    expect(patch).not.toHaveBeenCalled();
  });

  it('NON-grid Escape/Enter keep being swallowed (stopPropagation)', () => {
    const { picker } = mountPicker({ value: null });
    ['Escape', 'Enter'].forEach((key) => {
      const event = keyEvent(key);
      picker().handleKeydown(event);
      expect(event.stopPropagation).toHaveBeenCalledTimes(1);
    });
  });

  it('NON-grid typing patches immediately (unchanged)', () => {
    const patch = jest.fn();
    const { wrapper } = mountPicker({
      value: MomentTZ('2026-11-20', 'YYYY-MM-DD'),
      patch,
    });
    wrapper.find('input').simulate('change', { target: { value: '21.11.2026' } });
    expect(patch).toHaveBeenCalledTimes(1);
  });
});

describe('DatePicker — isOpenDatePicker (the pointer-entry auto-open on mount)', () => {
  beforeEach(() => jest.useFakeTimers());
  afterEach(() => jest.useRealTimers());

  const mountAndSpyOpen = (props) => {
    const { picker } = mountPicker({ value: null, ...props });
    return jest.spyOn(picker().picker, 'openCalendar');
  };

  it('opens the calendar 100ms after mounting with isOpenDatePicker', () => {
    const openSpy = mountAndSpyOpen({ isGridCell: true, isOpenDatePicker: true });
    expect(openSpy).not.toHaveBeenCalled();
    jest.advanceTimersByTime(100);
    expect(openSpy).toHaveBeenCalledTimes(1);
  });

  it('does not open the calendar on mount without isOpenDatePicker (keyboard entry)', () => {
    const openSpy = mountAndSpyOpen({ isGridCell: true, isOpenDatePicker: false });
    jest.advanceTimersByTime(1000);
    expect(openSpy).not.toHaveBeenCalled();
  });
});

describe('DatePicker — grid date cell, review follow-ups', () => {
  const stored = () => MomentTZ('2026-11-20', 'YYYY-MM-DD');

  it('clearing the typed text is pending: Escape discards it, no patch', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      value: stored(),
      patch,
    });
    wrapper.find('input').simulate('change', { target: { value: '' } });
    expect(patch).not.toHaveBeenCalled();
    picker().handleKeydown(keyEvent('Escape'));
    picker().handleBlur();
    expect(patch).not.toHaveBeenCalled();
  });

  it('clearing the typed text is saved (null) when the cell is left', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      value: stored(),
      patch,
    });
    wrapper.find('input').simulate('change', { target: { value: '' } });
    picker().handleBlur();
    expect(patch).toHaveBeenCalledTimes(1);
    expect(patch).toHaveBeenCalledWith(null);
  });

  it('a calendar pick after a typed change is not taken for typing (flag reset on every change)', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountPicker({
      isGridCell: true,
      value: stored(),
      patch,
    });
    wrapper.find('input').simulate('change', { target: { value: '' } });
    picker().handleDateChange(MomentTZ('2026-12-05', 'YYYY-MM-DD')); // calendar pick
    expect(patch).toHaveBeenCalledTimes(1);
  });

  it('the grid viewDate keeps its identity across re-renders with the same cell value', () => {
    const { picker } = mountPicker({
      isGridCell: true,
      gridCellValue: '20.11.2026',
      value: null,
    });
    const first = picker().getGridViewDate();
    expect(first.format(DISPLAY_FORMAT)).toBe('20.11.2026');
    expect(picker().getGridViewDate()).toBe(first); // react-datetime compares viewDate by reference
  });
});
