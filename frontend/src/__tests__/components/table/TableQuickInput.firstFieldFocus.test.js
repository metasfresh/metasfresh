import React from 'react';
import { shallow } from 'enzyme';

import quickInputData from '../../../../test_setup/fixtures/table/table_quickinput.json';
import { TableQuickInput } from '../../../components/table/TableQuickInput';
import WidgetWrapper from '../../../containers/WidgetWrapper';

const promiseMock = () => Promise.resolve(true);
const baseProps = {
  ...quickInputData.basicProps,
  addNotification: jest.fn(),
  data: quickInputData.data1.fieldsByName,
  layout: quickInputData.layout.elements,
  quickInputId: 'QI1',
  fetchQuickInputData: promiseMock,
  fetchQuickInputLayout: promiseMock,
  deleteQuickInput: jest.fn(),
  updateQuickinputData: jest.fn(),
  patchQuickInput: promiseMock,
  closeBatchEntry: jest.fn(),
};

/** @returns {{autoFocus: boolean, initialFocus: boolean}} the focus props of the quick input's first field */
const firstFieldFocusProps = (wrapper) => {
  const { autoFocus, initialFocus } = wrapper.find(WidgetWrapper).first().props();
  return { autoFocus, initialFocus };
};

describe('TableQuickInput: focus of the first field when the quick input is shown', () => {
  let headerField;

  beforeEach(() => {
    headerField = document.createElement('input');
    document.body.appendChild(headerField);
  });

  afterEach(() => {
    headerField.remove();
  });

  it('opened by the tab itself while the cursor is in a field outside it: the first field does not take the cursor', () => {
    headerField.focus();

    const wrapper = shallow(<TableQuickInput {...baseProps} isOpenedAutomatically={true} />);

    expect(firstFieldFocusProps(wrapper)).toEqual({ autoFocus: false, initialFocus: false });
  });

  it('opened by the tab itself while the cursor is nowhere: the first field takes it', () => {
    headerField.blur();

    const wrapper = shallow(<TableQuickInput {...baseProps} isOpenedAutomatically={true} />);

    expect(firstFieldFocusProps(wrapper)).toEqual({ autoFocus: true, initialFocus: true });
  });

  it('opened by the user (Alt+Q in a header field): the first field takes the cursor', () => {
    headerField.focus();

    const wrapper = shallow(<TableQuickInput {...baseProps} isOpenedAutomatically={false} />);

    expect(firstFieldFocusProps(wrapper)).toEqual({ autoFocus: true, initialFocus: true });
  });

  it('decides once per quick input: kept on re-render, decided again for the next quick input (after a line was added)', () => {
    headerField.focus();
    const wrapper = shallow(<TableQuickInput {...baseProps} isOpenedAutomatically={true} />);
    expect(firstFieldFocusProps(wrapper).autoFocus).toBe(false);

    headerField.blur();
    wrapper.setProps({ data: { ...baseProps.data } });
    // same quick input: the decision is kept
    expect(firstFieldFocusProps(wrapper).autoFocus).toBe(false);

    wrapper.setProps({ quickInputId: 'QI2' });
    // next quick input, cursor nowhere
    expect(firstFieldFocusProps(wrapper)).toEqual({
      autoFocus: true,
      initialFocus: true,
    });
  });

  it('after loading, focuses the first field with errors only when the first field takes the cursor', async () => {
    headerField.focus();
    const opened = shallow(<TableQuickInput {...baseProps} isOpenedAutomatically={true} />).instance();
    opened.focusFirstFieldWithErrors = jest.fn();
    await opened.initQuickInput();
    expect(opened.focusFirstFieldWithErrors).not.toHaveBeenCalled();

    const openedByUser = shallow(<TableQuickInput {...baseProps} isOpenedAutomatically={false} />).instance();
    openedByUser.focusFirstFieldWithErrors = jest.fn();
    await openedByUser.initQuickInput();
    expect(openedByUser.focusFirstFieldWithErrors).toHaveBeenCalled();
  });
});
