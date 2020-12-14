import React from 'react';
import { shallow, mount } from 'enzyme';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import viewHandler from '../../../reducers/viewHandler';
import InlineTabWrapper from '../../../components/widget/InlineTabWrapper';
import hotkeys from '../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../test_setup/fixtures/keymap.json';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import tablesHandler from '../../../reducers/tables';
import { Provider } from 'react-redux';
import props from '../../../../test_setup/fixtures/widget/inline_tab_wrapper.json';
import thunk from 'redux-thunk';
const middlewares = [thunk];

const mockStore = configureStore(middlewares);

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      appHandler: {
        ...appHandlerState,
        me: { timeZone: 'America/Los_Angeles' },
      },
      windowHandler: { ...windowHandlerState },
      ...viewHandler,
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );

  return res;
};

const initialState = createStore({
  windowHandler: {
    allowShortcut: true,
    modal: {
      visible: false,
    },
  },
});
const store = mockStore(initialState);

describe('InlineTabWrapper component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      shallow(<InlineTabWrapper {...props} />);
    });
    it('renders without errors', () => {
      mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Provider store={store}>
            <InlineTabWrapper {...props} />
          </Provider>
        </ShortcutProvider>
      );
    });
  });
});
