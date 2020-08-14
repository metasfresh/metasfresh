import React from 'react';
import { mount, shallow, render } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';

import Modal, { Modal as DisconnectedModal } from '../../../components/app/Modal';
import fixtures from '../../../../test_setup/fixtures/modal.json';
import testModal from '../../../../test_setup/fixtures/test_modal.json';

import hotkeys from '../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../test_setup/fixtures/keymap.json';
import thunk from 'redux-thunk';
const mockStore = configureStore([thunk]);

windowHandlerState.modal = testModal;

describe('Modal test', () => {
  it('renders without errors', () => {
    const dummyProps = fixtures;
    const initialState = function(state = {}) {
      const res = merge.recursive(
        true,
        {
          appHandler: { ...appHandlerState },
          windowHandler: { ...windowHandlerState },
        },
        state
      );

      return res;
    };
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
  it.skip(`calls startProcess when initializing a modal of 'process' type`, async (done) => {
    const dummyProps = fixtures;
    const startProcessMock = jest.fn().mockResolvedValue({});
    const wrapper = mount(
      <DisconnectedModal
        {...dummyProps}
        createProcess={startProcessMock}
        data={null}
        layout={null}
      />
    );

    expect(startProcessMock).toHaveBeenCalled();
    done();
  });
});
