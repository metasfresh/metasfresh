import React from 'react';
import { mount } from 'enzyme';
import { act } from 'react-dom/test-utils';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import MomentTZ from 'moment-timezone';
import counterpart from 'counterpart';

import DatePicker from '../../../components/widget/DateTime/DatePicker';
import TetheredDateTime from '../../../components/widget/DateTime/TetheredDateTime';

/**
 * Grid date cell: the grid passes isGridCell / isPointerEdit / gridCellValue (only
 * TableCell does, only for Date cells). Every other date widget (form, modal, filter) must keep
 * its behaviour: calendar auto-opens on focus, Escape/Enter are swallowed, typing patches at once.
 */
const DISPLAY_FORMAT = 'DD.MM.YYYY';

const mounted = [];

const mountPicker = (props, store = createStore(() => ({}))) => {
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

/**
 * Typed dates: besides the exact display format, a missing leading zero and a two-digit year are
 * accepted when the input is left; a typed text that is no date is reported, not silently dropped.
 */
describe('DatePicker — lenient typed dates and invalid-date feedback', () => {
  const stored = () => MomentTZ('2026-11-20', 'YYYY-MM-DD');
  const typeInto = (wrapper, text) =>
    wrapper.find('input').simulate('change', { target: { value: text } });
  const patchedDay = (patch) => patch.mock.calls[0][0].format('YYYY-MM-DD');
  const mountWithStore = (props) => {
    const store = createStore(() => ({}));
    const dispatch = jest.spyOn(store, 'dispatch');
    const notifications = () =>
      dispatch.mock.calls
        .map(([action]) => action)
        .filter((action) => action && action.type === 'ADD_NOTIFICATION');
    return { ...mountPicker(props, store), notifications };
  };

  beforeAll(() =>
    counterpart.registerTranslations('en', {
      widget: {
        datePicker: {
          invalidDate: {
            title: 'Invalid date',
            text: '"%(value)s" is not a valid date. Example: %(example)s',
          },
        },
      },
    })
  );

  [
    ['4.12.2027', '2027-12-04'],
    ['04.1.2027', '2027-01-04'],
    ['05.12.27', '2027-12-05'],
    ['6.1.27', '2027-01-06'],
  ].forEach(([typed, expected]) => {
    it(`grid: "${typed}" is saved as ${expected} when the cell is left`, () => {
      const patch = jest.fn();
      const { wrapper, picker, notifications } = mountWithStore({
        isGridCell: true,
        value: stored(),
        patch,
      });
      typeInto(wrapper, typed);
      picker().handleBlur();
      expect(patch).toHaveBeenCalledTimes(1);
      expect(patchedDay(patch)).toBe(expected);
      expect(notifications()).toHaveLength(0);
    });
  });

  it('grid: a short form is saved by Enter too', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountWithStore({
      isGridCell: true,
      value: stored(),
      patch,
    });
    typeInto(wrapper, '4.12.27');
    picker().handleKeydown(keyEvent('Enter'));
    expect(patch).toHaveBeenCalledTimes(1);
    expect(patchedDay(patch)).toBe('2027-12-04');
  });

  it('grid: Escape discards a typed short form', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      isGridCell: true,
      value: stored(),
      patch,
    });
    typeInto(wrapper, '4.12.2027');
    picker().handleKeydown(keyEvent('Escape'));
    picker().handleBlur();
    expect(patch).not.toHaveBeenCalled();
    expect(notifications()).toHaveLength(0);
  });

  ['31.02.2027', '12/09/2027', '07122027', '10.12.', 'abc'].forEach((typed) => {
    it(`grid: "${typed}" is not saved, the stored date stays and an error is shown`, () => {
      const patch = jest.fn();
      const { wrapper, picker, notifications } = mountWithStore({
        isGridCell: true,
        value: stored(),
        patch,
      });
      typeInto(wrapper, typed);
      picker().handleBlur();
      expect(patch).not.toHaveBeenCalled();
      wrapper.update();
      expect(wrapper.find('input').prop('value')).toBe('20.11.2026');
      const shown = notifications();
      expect(shown).toHaveLength(1);
      expect(shown[0].notifType).toBe('error');
      expect(shown[0].title).toBe('Invalid date');
      expect(shown[0].msg).toBe(
        `"${typed}" is not a valid date. Example: ${MomentTZ().format(DISPLAY_FORMAT)}`
      );
    });
  });

  it('grid: leaving the cell without typing shows no error, also for an unparseable stored value', () => {
    const { picker, notifications } = mountWithStore({
      isGridCell: true,
      value: 'not a date',
    });
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
  });

  it('grid, session format "L" in English: "12/5/27" is saved as 2027-12-05', () => {
    MomentTZ.locale('en');
    window.localStorage.setItem('metasfreshLanguage', 'en');
    try {
      const patch = jest.fn();
      const { wrapper, picker } = mountWithStore({
        isGridCell: true,
        dateFormat: true,
        value: stored(),
        patch,
      });
      typeInto(wrapper, '12/5/27');
      picker().handleBlur();
      expect(patch).toHaveBeenCalledTimes(1);
      expect(patchedDay(patch)).toBe('2027-12-05');
    } finally {
      window.localStorage.removeItem('metasfreshLanguage');
    }
  });

  it('non-grid: a short form is not patched while typing but when the field is left', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    typeInto(wrapper, '4.12.27');
    expect(patch).not.toHaveBeenCalled();
    picker().handleBlur();
    expect(patch).toHaveBeenCalledTimes(1);
    expect(patchedDay(patch)).toBe('2027-12-04');
    expect(notifications()).toHaveLength(0);
  });

  it('non-grid: a typed text that is no date is reported when the field is left', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    typeInto(wrapper, '31.02.2027');
    picker().handleBlur();
    expect(patch).not.toHaveBeenCalled();
    expect(notifications()).toHaveLength(1);
  });

  it('non-grid: leaving the field without typing shows no error', () => {
    const { picker, notifications } = mountWithStore({ value: stored() });
    picker().handleFocus();
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
  });

  it('non-grid: a full date typed and saved while typing raises no error when the field is left', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    typeInto(wrapper, '21.11.2026');
    picker().handleBlur();
    expect(patch).toHaveBeenCalledTimes(1);
    expect(notifications()).toHaveLength(0);
  });

  it('time-only field: a typed valid time raises no error', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      dateFormat: false,
      timeFormat: 'HH:mm',
      value: null,
      patch,
    });
    typeInto(wrapper, '12:30');
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
  });

  [
    ['04.01.2027', '2027-01-04'],
    ['4.1.27', '2027-01-04'],
  ].forEach(([typed, expected]) => {
    it(`date+time field: the date "${typed}" without a time is saved once as the end of ${expected}, no error`, () => {
      const patch = jest.fn();
      const { wrapper, picker, notifications } = mountWithStore({
        timeFormat: 'HH:mm',
        value: null,
        patch,
      });
      typeInto(wrapper, typed);
      picker().handleBlur();
      expect(notifications()).toHaveLength(0);
      expect(patch).toHaveBeenCalledTimes(1);
      expect(patch.mock.calls[0][0].format('YYYY-MM-DD HH:mm')).toBe(`${expected} 23:59`);
    });
  });

  it('date+time field: a short form with a time is saved with that time', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      timeFormat: 'HH:mm',
      value: null,
      patch,
    });
    typeInto(wrapper, '4.1.27 10:30');
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
    expect(patch).toHaveBeenCalledTimes(1);
    expect(
      patch.mock.calls[0][0].clone().tz('Europe/Berlin').format('YYYY-MM-DD HH:mm')
    ).toBe('2027-01-04 10:30');
  });

  it('non-grid: Escape cancels the typing, no error when the field is left', () => {
    const { wrapper, picker, notifications } = mountWithStore({ value: stored() });
    typeInto(wrapper, 'abc');
    picker().handleKeydown(keyEvent('Escape'));
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
  });

  it('non-grid: a calendar pick after typing an invalid text raises no error', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    typeInto(wrapper, 'abc');
    picker().handleDateChange(MomentTZ('2026-12-05', 'YYYY-MM-DD'));
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
    expect(patch).toHaveBeenCalledTimes(1);
  });

  it('empty field: a calendar pick after typing an invalid text raises no error (react-datetime blurs before onChange)', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: null,
      patch,
    });
    typeInto(wrapper, 'abc');
    const picked = MomentTZ('2026-12-05', 'YYYY-MM-DD');
    // react-datetime's order for a field without value: onBlur(date), then onChange(date)
    picker().handleBlur(picked);
    picker().handleDateChange(picked);
    expect(notifications()).toHaveLength(0);
    expect(patch).toHaveBeenCalledTimes(1);
  });

  it('spaces around a typed date are ignored', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      isGridCell: true,
      value: stored(),
      patch,
    });
    typeInto(wrapper, ' 4.12.2027 ');
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
    expect(patchedDay(patch)).toBe('2027-12-04');
  });

  it('typing only spaces keeps the stored value without an error', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      isGridCell: true,
      value: stored(),
      patch,
    });
    typeInto(wrapper, '   ');
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
    expect(patch).not.toHaveBeenCalled();
  });

  it('a text with "-" is read as full ISO YYYY-MM-DD only: "12-05-27" is no date', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      isGridCell: true,
      value: stored(),
      patch,
    });
    typeInto(wrapper, '12-05-27');
    picker().handleBlur();
    expect(patch).not.toHaveBeenCalled();
    expect(notifications()).toHaveLength(1);
  });

  it('the calendar icon resets the input: the earlier typing is neither saved nor reported later', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    picker().handleFocus(); // opens the calendar
    typeInto(wrapper, 'abc');
    picker().toggleCalendarOpenClosed(); // closes it and resets the input to the stored date
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
    expect(patch).not.toHaveBeenCalled();
  });

  it('non-grid: an ISO date with time zone accepted while typing raises no error when the field is left', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    typeInto(wrapper, '2026-12-05T00:00:00.000+01:00');
    picker().handleBlur();
    expect(notifications()).toHaveLength(0);
    expect(patch).toHaveBeenCalledTimes(1);
  });

  it('typing replaced in the input by a new value (e.g. from the server) is not saved on leave', () => {
    const patch = jest.fn();
    const { wrapper, picker, notifications } = mountWithStore({
      value: stored(),
      patch,
    });
    typeInto(wrapper, '5.1.27');
    // react-datetime rewrites the input when the field gets a new value
    picker().picker.setSelectedDateAndInputValue({
      selectedDate: MomentTZ('2026-11-21', 'YYYY-MM-DD'),
      inputValue: '21.11.2026',
    });
    picker().handleBlur();
    expect(patch).not.toHaveBeenCalled();
    expect(notifications()).toHaveLength(0);
  });

  it('non-grid: after a short form is saved on leave, the input shows the saved date (blur batched as in react-datetime)', () => {
    const patch = jest.fn();
    const { wrapper, picker } = mountWithStore({ value: stored(), patch });
    typeInto(wrapper, '4.12.27');
    act(() => {
      picker().handleBlur();
    });
    expect(patch).toHaveBeenCalledTimes(1);
    wrapper.update();
    expect(wrapper.find('input').prop('value')).toBe('04.12.2027');
  });

  ['21.11.2026', ''].forEach((typed) => {
    it(`grid: the calendar icon resets the input, so the typed "${typed}" is not saved on leave`, () => {
      const patch = jest.fn();
      const { wrapper, picker } = mountWithStore({
        isGridCell: true,
        isPointerEdit: true,
        value: stored(),
        patch,
      });
      picker().handleFocus(); // pointer entry opens the calendar
      typeInto(wrapper, typed);
      picker().toggleCalendarOpenClosed(); // closes it and resets the input to the stored date
      picker().handleBlur();
      expect(patch).not.toHaveBeenCalled();
    });
  });
});

