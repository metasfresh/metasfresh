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
import inlineTabItemRow from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_item_row.json';
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
  nock(config.API_URL)
    .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
    .get(`/window/123/2156425/AD_Tab-222/`)
    .reply(200, tabData);

  const fieldsOrder = ['Name', 'GLN', 'IsActive', 'IsShipTo'];

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

  it('renders the InlineTab item collapsed when isOpen is true', () => {
    inlineTabStore[`123_AD_Tab-222_2205262`] = inlineTabItemRow;
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

    const wrapper = shallow(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTab
            id={`2155894`}
            rowId={`2205262`}
            tabId={`AD_Tab-222`}
            fieldsOrder={fieldsOrder}
            fieldsByName={fieldsByName}
            {...props}
          />
        </Provider>
      </ShortcutProvider>
    );
    let htmlOutput = wrapper.html();

    expect(htmlOutput).toContain('inline-tab-active');
    expect(htmlOutput).toContain('Business Partner Shipment Address');
    // delete button needs to be present in the open state
    expect(htmlOutput).toContain(
      '<button class="btn btn-meta-outline-secondary btn-sm btn-pull-right">missing translation: en.window.Delete.caption</button>'
    );
  });

  it('renders the InlineTab item unexpanded when isOpen is false', () => {
    inlineTabItemRow.isOpen = false;
    inlineTabStore[`123_AD_Tab-222_2205262`] = inlineTabItemRow;
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

    const wrapper = shallow(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTab
            id={`2155894`}
            rowId={`2205262`}
            tabId={`AD_Tab-222`}
            fieldsOrder={fieldsOrder}
            fieldsByName={fieldsByName}
            {...props}
          />
        </Provider>
      </ShortcutProvider>
    );
    let htmlOutput = wrapper.html();
    expect(htmlOutput).not.toContain('inline-tab-active');
    expect(htmlOutput).not.toContain('Business Partner Shipment Address');
    expect(htmlOutput).toContain(
      'Antarktis (Sonderstatus durch Antarktis-Vertrag)'
    );
  });

  it('renders the deletion prompt', () => {
    inlineTabItemRow.isOpen = true;
    inlineTabItemRow.promptOpen = true;
    inlineTabStore[`123_AD_Tab-222_2205262`] = inlineTabItemRow;
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

    const wrapper = mount(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTab
            id={`2155894`}
            rowId={`2205262`}
            tabId={`AD_Tab-222`}
            fieldsOrder={fieldsOrder}
            fieldsByName={fieldsByName}
            {...props}
          />
        </Provider>
      </ShortcutProvider>
    );
    let htmlOutput = wrapper.html();

    expect(htmlOutput).toContain('inline-tab-active');
    // check if we are displaying the confirmation dialog in case of a deletion
    expect(htmlOutput).toContain(
      'span class="panel-prompt-header-title panel-modal-header-title">'
    );
  });
});
