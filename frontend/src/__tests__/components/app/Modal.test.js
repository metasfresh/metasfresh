import React from "react";
import * as Immutable from "immutable";
import { mount, shallow, render } from "enzyme";
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';

import Modal from "../../../components/app/Modal";
import fixtures from '../../../../test_setup/fixtures/modal.json';
import testModal from '../../../../test_setup/fixtures/test_modal.json';

const mockStore = configureStore([]);

windowHandlerState.modal = testModal;

describe("Modal test", () => {
  it("renders without errors", () => {
    const dummyProps = fixtures;
    const initialState = function (state = {}) {
      const res = merge.recursive(
        true,
        {
          appHandler: { ...appHandlerState },
          windowHandler: { ...windowHandlerState }
        },
        state
      );

      return res;
    };
    const store = mockStore(initialState)
    const wrapper = render(
      <Provider store={store}>
        <ShortcutProvider hotkeys={{}} keymap={{}} >
          <Modal {...dummyProps} />
        </ShortcutProvider>
      </Provider>
    );

    const html = wrapper.html();
    expect(html).not.toBe(null);
    expect(html.includes('Action')).toBe(true);
  });
});
