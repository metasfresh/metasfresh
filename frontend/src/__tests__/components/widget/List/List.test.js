import React from 'react';
import { mount, shallow } from 'enzyme';

import { ListWidget } from '../../../../components/widget/List/List';
import { dropdownRequest } from '../../../../actions/GenericActions';

// The widget asks the backend for its options as soon as it mounts focused; these scenarios are
// about focus, not about the option list.
jest.mock('../../../../actions/GenericActions', () => ({
  dropdownRequest: jest.fn(() => Promise.resolve({ data: { values: [] } })),
  dropdownModalRequest: jest.fn(() =>
    Promise.resolve({ data: { values: [] } })
  ),
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

  it('does not take the focus back when it already holds it', () => {
    const wrapper = shallow(
      <ListWidget {...createDummyProps({ autoFocus: true })} />
    );
    wrapper.setState({ autoFocus: false, listFocused: true });
    const focusSpy = jest.spyOn(wrapper.instance(), 'handleFocus');

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

  describe('as a sub-list of a lookup that declines the focus', () => {
    const twoOptions = [
      { key: '1', caption: 'Contact A' },
      { key: '2', caption: 'Contact B' },
    ];
    // Upper bound for the run of the test only: if the list kept asking for its options, an
    // unbounded mock would loop in microtasks forever. Each call past it never answers.
    const maxAnsweredRequests = 10;

    let lookupWrapper;
    let listContainer;
    let cursorElsewhere;

    beforeEach(() => {
      let answeredRequests = 0;
      dropdownRequest.mockImplementation(() =>
        ++answeredRequests > maxAnsweredRequests
          ? new Promise(() => {})
          : Promise.resolve({ data: { values: twoOptions } })
      );

      lookupWrapper = document.createElement('div');
      listContainer = document.createElement('div');
      lookupWrapper.appendChild(listContainer);
      document.body.appendChild(lookupWrapper);
      cursorElsewhere = document.createElement('input');
      document.body.appendChild(cursorElsewhere);
    });

    afterEach(() => {
      document.body.removeChild(lookupWrapper);
      document.body.removeChild(cursorElsewhere);
      dropdownRequest.mockImplementation(() =>
        Promise.resolve({ data: { values: [] } })
      );
    });

    const flushResponses = async () => {
      for (let i = 0; i < 2 * maxAnsweredRequests; i++) {
        await new Promise((resolve) => setTimeout(resolve, 0));
      }
    };

    const mountSubList = (props) =>
      mount(
        <ListWidget
          {...createDummyProps({
            properties: { field: 'AD_User_ID' },
            mainProperty: { field: 'AD_User_ID' },
            lookupList: true,
            autoFocus: false,
            readonly: false,
            doNotOpenOnFocus: false,
            wrapperElement: lookupWrapper,
            ...props,
          })}
        />,
        { attachTo: listContainer }
      );

    // The normal hand-over: the cursor is still in the lookup's previous sub-field.
    it('takes the focus and opens its options when the cursor is in the lookup', async () => {
      const previousSubField = document.createElement('input');
      lookupWrapper.insertBefore(previousSubField, listContainer);
      const onBlur = jest.fn();
      const wrapper = mountSubList({ onBlur });
      previousSubField.focus();

      wrapper.setProps({ autoFocus: true });
      await flushResponses();

      expect(dropdownRequest).toHaveBeenCalledTimes(1);
      expect(onBlur).not.toHaveBeenCalled();
      expect(wrapper.state('listFocused')).toBe(true);
      expect(wrapper.state('listToggled')).toBe(true);
      expect(lookupWrapper.contains(document.activeElement)).toBe(true);
      expect(document.activeElement).not.toBe(previousSubField);
      wrapper.detach();
    });

    // E.g. the header hands over from the business partner to its contact sub-list while Alt+Q has
    // already put the cursor in the quick entry, and the partner has two contacts.
    it('asks for its options once and stays unfocused when they arrive', async () => {
      const onBlur = jest.fn();
      const wrapper = mountSubList({ onBlur });
      cursorElsewhere.focus();

      wrapper.setProps({ autoFocus: true });
      await flushResponses();

      expect(dropdownRequest).toHaveBeenCalledTimes(1);
      expect(onBlur).toHaveBeenCalledTimes(1);
      expect(wrapper.state('listFocused')).toBe(false);
      expect(wrapper.state('listToggled')).toBe(false);
      expect(document.activeElement).toBe(cursorElsewhere);
      wrapper.detach();
    });
  });

  // E.g. the user leaves a lookup sub-list while its options are loading and comes back before they arrive.
  it('opens its options when it is left and entered again before they arrive', async () => {
    let answer;
    dropdownRequest.mockImplementationOnce(
      () => new Promise((resolve) => (answer = resolve))
    );
    const wrapper = shallow(
      <ListWidget
        {...createDummyProps({
          properties: { field: 'AD_User_ID' },
          mainProperty: { field: 'AD_User_ID' },
          lookupList: true,
          doNotOpenOnFocus: false,
        })}
      />
    );
    const instance = wrapper.instance();

    instance.handleFocus();
    instance.handleBlur();
    instance.handleFocus();
    answer({
      data: {
        values: [
          { key: '1', caption: 'Contact A' },
          { key: '2', caption: 'Contact B' },
        ],
      },
    });
    await new Promise((resolve) => setTimeout(resolve, 0));

    expect(dropdownRequest).toHaveBeenCalledTimes(1);
    expect(wrapper.state('listFocused')).toBe(true);
    expect(wrapper.state('listToggled')).toBe(true);
  });
});
