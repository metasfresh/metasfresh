import React from 'react';
import { mount, render } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import nock from 'nock';

import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { SET_PROCESS_STATE_PENDING } from '../../../constants/ActionTypes';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { initialState as viewHandlerState } from '../../../reducers/viewHandler';

import Modal, { DisconnectedModal } from '../../../components/app/Modal';
import fixtures from '../../../../test_setup/fixtures/modal/modal.json';
import testModal from '../../../../test_setup/fixtures/modal/test_modal.json';
import layoutFixtures from '../../../../test_setup/fixtures/modal/layout.json';
import processResponses from '../../../../test_setup/fixtures/process/responses.json'

import hotkeys from '../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../test_setup/fixtures/keymap.json';
import thunk from 'redux-thunk';
const mockStore = configureStore([thunk]);

const getInitialState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      appHandler: { ...appHandlerState },
      windowHandler: {
        ...windowHandlerState,
        modal: testModal,
      },
      viewHandler: { ...viewHandlerState },
    },
    state
  );

  return res;
};

describe('Modal test', () => {
  beforeAll(() => {
    // Avoid `attachTo: document.body` Warning
    const div = document.createElement('div');
    div.setAttribute('id', 'container');
    document.body.appendChild(div);
  });

  afterAll(() => {
    const div = document.getElementById('container');
    if (div) {
      document.body.removeChild(div);
    }
  });

  it('renders without errors', () => {
    const dummyProps = fixtures;
    const initialState = getInitialState();
    const store = mockStore(initialState);

    const wrapper = render(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Modal {...dummyProps} />
        </ShortcutProvider>
      </Provider>
    );

    const html = wrapper.html();
    expect(html).not.toBe(null);
    expect(html.includes('Action')).toBe(true);
  });

  // TODO: For this to work it implies having the Modal component disconnected and
  // TODO: test all functionality of it before adding back (https://github.com/metasfresh/metasfresh/issues/7128)
  // TODO: the startProcess test it is skipped for now for that reason
  // TODO: will be added along with the refactoring issue (https://github.com/metasfresh/metasfresh/issues/7126)
  // As far as I remember failures that caused this to be skipped were because of some actionCreator not
  // being read from the props. Modal was and still is connected to the store, but it's using dispatch instead
  // of mapDispatchToProps/object shorthand binding
  it(`calls startProcess when initializing a modal of 'process' type`, async (done) => {
    const initialState = getInitialState();
    const store = mockStore(initialState);
    // setProcessPending() is called only by createProcess and we can use that as an indicator to know if it was called
    const expectedActions = [{ type: SET_PROCESS_STATE_PENDING }];
    expect(store.getActions()).toEqual([]); // before mounting the modal the should'n be any action

    const layoutResponse = layoutFixtures.layout1;
    const response = processResponses['540189_Transform'];
    const { windowId } = fixtures;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/process/${windowId}/layout`)
      .reply(200, layoutResponse);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/process/${windowId}`)
      .reply(200, response);

    const startProcessMock = jest.fn().mockResolvedValue({ body: {} });
    const wrapper = mount(
      <DisconnectedModal
        {...fixtures}
        createProcess={startProcessMock}
        data={null}
        dispatch={store.dispatch}
        layout={null}
      />,
    { attachTo: document.getElementById('container') });

    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    done();
  });
});
