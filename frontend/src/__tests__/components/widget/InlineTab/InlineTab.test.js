import React from 'react';
import nock from 'nock';
import { shallow, mount } from 'enzyme';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import viewHandler from '../../../../reducers/viewHandler';
import InlineTab from '../../../../components/widget/InlineTab';
import hotkeys from '../../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../../test_setup/fixtures/keymap.json';
import { ShortcutProvider } from '../../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../../reducers/windowHandler';
import tablesHandler from '../../../../reducers/tables';
import { Provider } from 'react-redux';
import props from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_wrapper.json';
import tabData from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_data.json';
import fieldsByName from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_fieldsByName.json';
import inlineTabStore from '../../../../../test_setup/fixtures/widget/inlinetab/inlineTabStore.json';
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

describe('InlineTab component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      shallow(<InlineTab {...props} />);
    });

    it('renders the InlineTab item correctly', () => {
      const initialState = createStore({
        windowHandler: {
          allowShortcut: true,
          modal: {
            visible: true,
          },
          inlineTab: inlineTabStore,
        },
      });
      const store = mockStore(initialState);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/123/2156425/AD_Tab-222/`)
        .reply(200, tabData);

      const fieldsOrder = ['Name', 'GLN', 'IsActive', 'IsShipTo'];
      const wrapper = shallow(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Provider store={store}>
            <InlineTab
              id={`2155894`}
              rowId={`2205238`}
              tabId={`AD_Tab-222`}
              fieldsOrder={fieldsOrder}
              fieldsByName={fieldsByName}
              {...props}
            />
          </Provider>
        </ShortcutProvider>
      );
      let htmlOutput = wrapper.html();
      expect(htmlOutput).toContain(
        'Antarktis (Sonderstatus durch Antarktis-Vertrag)'
      );
    });
  });
});
