import React from 'react';
import { shallow } from 'enzyme';

import { ListWidget } from '../../../../components/widget/List/List';

// The widget asks the backend for its options as soon as it mounts focused; these scenarios are
// about focus, not about the option list.
jest.mock('../../../../actions/GenericActions', () => ({
  dropdownRequest: jest.fn(() => Promise.resolve({ data: { values: [] } })),
  dropdownModalRequest: jest.fn(() => Promise.resolve({ data: { values: [] } })),
}));

const createDummyProps = (props = {}) => ({
  entity: 'window',
  windowType: '116',
  properties: { field: 'C_Currency_ID' },
  mainProperty: { field: 'C_Currency_ID' },
  dataId: '1000001',
  //
  // Callbacks:
  dispatch: jest.fn(),
  onFocus: jest.fn(),
  onBlur: jest.fn(),
  onChange: jest.fn(),
  disableAutofocus: jest.fn(),
  enableAutofocus: jest.fn(),
  setNextProperty: jest.fn(),
  //
  ...props,
});

// A dropdown first field focuses on the edge between the incoming autoFocus prop and the widget's
// own state, and that edge never recurs inside a mounted window. These pin when the focus is
// repeated on a document change - and where it must stay out.
const shallowWithSpentEdge = (props) => {
  const wrapper = shallow(<ListWidget {...createDummyProps(props)} />);
  // Moving on to another field blurs the widget, which is what turns its own autoFocus state off.
  wrapper.setState({ autoFocus: false, listFocused: false });
  const focusSpy = jest.spyOn(wrapper.instance(), 'handleFocus');
  return { wrapper, focusSpy };
};

describe('ListWidget component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('focuses the first field again when the document changes', () => {
    const { wrapper, focusSpy } = shallowWithSpentEdge({ autoFocus: true });

    wrapper.setProps({ dataId: '1000002' });

    expect(focusSpy).toHaveBeenCalled();
  });

  it('does not focus on an id change outside a window document', () => {
    // A process parameter panel and the barcode overlay pass a pinstance id as `dataId` with an
    // unconditional autoFocus; that is not a document change and must not take focus.
    const { wrapper, focusSpy } = shallowWithSpentEdge({
      autoFocus: true,
      entity: 'process',
    });

    wrapper.setProps({ dataId: '1000002' });

    expect(focusSpy).not.toHaveBeenCalled();
  });

  it('does not focus on a document change in a quick-input row', () => {
    // The quick-input row is the same window and the same document, and its first field carries a
    // permanently-set autoFocus - but the field that must get the focus is the header's.
    const { wrapper, focusSpy } = shallowWithSpentEdge({
      autoFocus: true,
      subentity: 'quickInput',
    });

    wrapper.setProps({ dataId: '1000002' });

    expect(focusSpy).not.toHaveBeenCalled();
  });

  it('does not focus on a document change when it is a sub-field of a composed lookup', () => {
    // There the parent decides which sub-field is in turn, and React updates this child first.
    const { wrapper, focusSpy } = shallowWithSpentEdge({
      autoFocus: true,
      lookupList: true,
    });

    wrapper.setProps({ dataId: '1000002' });

    expect(focusSpy).not.toHaveBeenCalled();
  });
});
