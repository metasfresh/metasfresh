import React from 'react';
import { render } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';

import PrintingOptions from '../../../components/app/PrintingOptions';
import testModal from '../../../../test_setup/fixtures/test_modal.json';
import printingOptions from '../../../../test_setup/fixtures/window/printingOptions.json';
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
    },
    state
  );

  return res;
};

describe('PrintingOptions test', () => {
  it('renders without errors', () => {
    let dummyProps = printingOptions;
    const initialState = getInitialState();
    const store = mockStore(initialState);

    const wrapper = render(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <PrintingOptions printingOptions={dummyProps} />
        </ShortcutProvider>
      </Provider>
    );

    const html = wrapper.html();
    expect(html).not.toBe(null);
  });
});
