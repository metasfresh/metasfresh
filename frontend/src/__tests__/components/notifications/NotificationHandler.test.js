import React from 'react';
import { mount } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import { merge } from 'merge-anything';

import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import NotificationHandler from '../../../components/notifications/NotificationHandler';

const mockStore = configureStore([thunk]);

const getInitialState = (state = {}) =>
  merge(
    {
      appHandler: { ...appHandlerState },
    },
    state
  );

/**
 * Layering-guard regression test for NotificationHandler.
 *
 * A process modal carries `.screen-freeze`, sharing `z-index: $z-index-screen-freeze` with the
 * acknowledge dialog's `.screen-prompt-freeze`. At equal z-index, CSS stacking resolves the
 * winner by DOM order — the later sibling paints on top. `<Prompt>` used to be rendered BEFORE
 * `{children}`, so the acknowledge dialog painted BEHIND any open modal (invisible, and swallowing
 * no clicks since nothing pointed at it). The fix moved `<Prompt>` to render AFTER `{children}`.
 *
 * jsdom does not compute layout or CSS z-index, so this test cannot assert the actual visual
 * stacking. What it CAN assert, and does: the DOM order of `.root-children` (which wraps
 * `{children}`) versus the `Prompt` markup — proving the ordering the fix relies on, not the
 * rendered stacking itself.
 */
describe('NotificationHandler', () => {
  it('renders no Prompt when there is no acknowledgeDialog in state', () => {
    const store = mockStore(getInitialState());

    const wrapper = mount(
      <Provider store={store}>
        <ShortcutProvider>
          <NotificationHandler>
            <div className="my-modal-child" />
          </NotificationHandler>
        </ShortcutProvider>
      </Provider>
    );

    expect(wrapper.find('.panel-prompt').exists()).toBe(false);
    expect(wrapper.find('.root-children').exists()).toBe(true);
  });

  it('renders the Prompt AFTER .root-children in the DOM when acknowledgeDialog is set', () => {
    const store = mockStore(
      getInitialState({
        appHandler: {
          acknowledgeDialog: { title: 'Some title', text: 'Some message' },
        },
      })
    );

    const wrapper = mount(
      <Provider store={store}>
        <ShortcutProvider>
          <NotificationHandler>
            <div className="my-modal-child" />
          </NotificationHandler>
        </ShortcutProvider>
      </Provider>
    );

    const rootChildrenNode = wrapper.find('.root-children').getDOMNode();
    const promptNode = wrapper.find('.panel-prompt').getDOMNode();

    expect(rootChildrenNode).toBeTruthy();
    expect(promptNode).toBeTruthy();

    // DOCUMENT_POSITION_FOLLOWING (4) set means `promptNode` comes AFTER `rootChildrenNode` in
    // document order — i.e. Prompt is emitted after {children}, not before it.
    // eslint-disable-next-line no-bitwise
    const promptFollowsRootChildren =
      // eslint-disable-next-line no-bitwise
      rootChildrenNode.compareDocumentPosition(promptNode) & Node.DOCUMENT_POSITION_FOLLOWING;
    expect(Boolean(promptFollowsRootChildren)).toBe(true);
  });
});
