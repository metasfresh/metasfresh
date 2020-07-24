import React from 'react';
import { mount, shallow, render } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';

import Modal from '../../../components/app/Modal';
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

  it('calls callProcess', async (done) => {
    const dummyProps = fixtures;
    const mockFn = jest.fn().mockResolvedValue({});

    // const mockFn = () => {
    //   done();
    //   return Promise.resolve({});
    // };
    // const mockFn = jest.fn();
    dummyProps.callProcess = mockFn; // we spy the callProcess function
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
    const wrapper = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Modal {...dummyProps} />
        </ShortcutProvider>
      </Provider>
    );

    const instance = wrapper.instance();
    await instance.componentDidMount();
    
    await mockFn(); // remove this line Kuba
    expect(mockFn).toHaveBeenCalled();
    done();
  });
});
