import React from 'react';
import nock from 'nock';
import { shallow, mount } from 'enzyme';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import thunk from 'redux-thunk';
import { Provider } from 'react-redux';

import viewHandler from '../../../../reducers/viewHandler';
import
  InlineTabWrapper,
  { InlineTabWrapper as DisconnectedInlineTabWrapper }
from '../../../../components/widget/InlineTabWrapper';
import hotkeys from '../../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../../test_setup/fixtures/keymap.json';
import { ShortcutProvider } from '../../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../../reducers/windowHandler';
import tablesHandler from '../../../../reducers/tables';

import props from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_wrapper.json';
import tabData from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_data.json';
import inlineTabStoreMore from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_data_more.json';
import inlineTabStore from '../../../../../test_setup/fixtures/widget/inlinetab/inlineTabStore.json';
import addNewData from '../../../../../test_setup/fixtures/widget/inlinetab/addNewData.json';
import inlineTabInvalid from '../../../../../test_setup/fixtures/widget/inlinetab/inline_tab_invalid.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createStore = function(state = {}) {
  const res = merge(
    {
      appHandler: {
        ...appHandlerState,
        me: { timeZone: 'America/Los_Angeles' },
      },
      windowHandler: {
        ...windowHandlerState,
        master: {
          validStatus: {
            valid: true,
          },
        },
      },
      ...viewHandler,
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );

  return res;
};

describe('InlineTabWrapper component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const properties = {
        ...props,
        fetchInlineTabWrapperData: jest.fn(),
        createWindow: jest.fn(),
        setInlineTabAddNew: jest.fn(),
        setInlineTabShowMore: jest.fn(),
        updateDataValidStatus: jest.fn(),
      };
      shallow(<DisconnectedInlineTabWrapper {...properties} />);
    });

    it('renders a line properly', () => {
      const initialState = createStore({
        windowHandler: {
          allowShortcut: true,
          modal: {
            visible: true,
          },
          inlineTab: inlineTabStore,
          master: {
            includedTabsInfo: {
              "AD_Tab-222": {
                allowCreateNew: true,
                allowDelete: true,
              }
            }
          }
        },
      });
      const store = mockStore(initialState);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/123/2156425/AD_Tab-222/`)
        .reply(200, tabData);

      const wrapper = mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Provider store={store}>
            <InlineTabWrapper {...props} />
          </Provider>
        </ShortcutProvider>
      );
      const htmlOutput = wrapper.html();
      expect(htmlOutput).toContain('<span>Testadresse 3</span');
      expect(htmlOutput).toContain('inlinetab-action-button');
      expect(htmlOutput).not.toContain('meta-icon-fullscreen');
    });
  });

  it('renders more lines properly', () => {
    props.dataId = '2155894'; // we pass different docId such that will match the selector used for the mocked up data
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: true,
        },
        inlineTab: inlineTabStoreMore,
      },
    });
    const store = mockStore(initialState);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/123/2155894/AD_Tab-222/`)
      .reply(200, tabData);

    const wrapper = mount(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTabWrapper {...props} />
        </Provider>
      </ShortcutProvider>
    );
    const htmlOutput = wrapper.html();

    expect(htmlOutput).toContain('Amerikanische Jungferninseln');
    expect(htmlOutput).toContain('Amerikanisch-Samoa');
    expect(htmlOutput).toContain('Amerikanisch-Samoa (2)');
    expect(htmlOutput).toContain('Am Nossbacher Weg 2');
    expect(htmlOutput).toContain(
      'Antarktis (Sonderstatus durch Antarktis-Vertrag)'
    );
    expect(htmlOutput).not.toContain('Russland'); // only five rows shown - this should not be rendered
    expect(htmlOutput).toContain('meta-icon-fullscreen');
  });

  it('renders the form in full screen correctly', () => {
    props.dataId = '2155894'; // we pass different docId such that will match the selector used for the mocked up data
    inlineTabStoreMore.showMore['123_AD_Tab-222_2155894'] = false;
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: true,
        },
        inlineTab: inlineTabStoreMore,
      },
    });
    const store = mockStore(initialState);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/123/2155894/AD_Tab-222/`)
      .reply(200, tabData);

    const wrapper = mount(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTabWrapper {...props} />
        </Provider>
      </ShortcutProvider>
    );
    const htmlOutput = wrapper.html();

    expect(htmlOutput).toContain('Amerikanische Jungferninseln');
    expect(htmlOutput).toContain('Amerikanisch-Samoa');
    expect(htmlOutput).toContain('Amerikanisch-Samoa (2)');
    expect(htmlOutput).toContain('Am Nossbacher Weg 2');
    expect(htmlOutput).toContain(
      'Antarktis (Sonderstatus durch Antarktis-Vertrag)'
    );
    expect(htmlOutput).toContain('Russland'); // all rows should be visible now, including this one
    expect(htmlOutput).toContain('meta-icon-fullscreen');
  });

  it('renders the add new form', () => {
    props.dataId = '2155894'; // we pass different docId such that will match the selector used for the mocked up data
    // inlineTabStoreMore.showMore['123_AD_Tab-222_2155894'] = false;
    inlineTabStoreMore.addNew[`123_AD_Tab-222_2155894`] = {
      visible: true,
      windowId: '123',
      tabId: 'AD_Tab-222',
      rowId: '2205259',
    };
    inlineTabStoreMore[`123_AD_Tab-222_2205259`] = addNewData;
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: true,
        },
        inlineTab: inlineTabStoreMore,
      },
    });
    const store = mockStore(initialState);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/123/2155894/AD_Tab-222/`)
      .reply(200, tabData);

    const wrapper = mount(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTabWrapper {...props} />
        </Provider>
      </ShortcutProvider>
    );
    const htmlOutput = wrapper.html();

    expect(htmlOutput).toContain(
      `<div class="form-group form-field-BPartnerName">`
    );
    expect(htmlOutput).toContain(`<i class="meta-icon-close-alt">`);
  });

  it('show error correctly', () => {
    props.dataId = '2156435'; // we pass different docId such that will match the selector used for the mocked up data
    // inlineTabStoreMore.showMore['123_AD_Tab-222_2155894'] = false;
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: true,
        },
        inlineTab: inlineTabInvalid,
      },
    });
    const store = mockStore(initialState);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/123/2156435/AD_Tab-222/`)
      .reply(200, tabData);

    const wrapper = mount(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <InlineTabWrapper {...props} />
        </Provider>
      </ShortcutProvider>
    );
    const htmlOutput = wrapper.html();

    expect(htmlOutput).toContain('inline-tab form-control-label row-not-saved');
  });
});
