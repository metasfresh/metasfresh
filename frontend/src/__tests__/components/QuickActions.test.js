import React from 'react';
import { mount, shallow } from 'enzyme';
import nock from 'nock';
import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import { QuickActions } from '../../components/app/QuickActions';
import fixtures from '../../../test_setup/fixtures/quickactions.json';

import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';
jest.mock('../../api');

const createDummyProps = function(override = {}) {
  return {
    quickActions: override.quickActions || {
      actions: fixtures.data,
      pending: false,
    },
    openModal: override.openModal || jest.fn(),
    deleteQuickActions: override.deleteQuickActions || jest.fn(),

    selected: override.selected || null,
    childView: override.childView || {},
    parentView: override.parentView || {},
    windowId: override.windowType || fixtures.props.windowId,
    viewId: override.viewId || fixtures.props.viewId,
    inBackground: override.inBackground || false,
    inModal: override.inModal || false,
    disabled: override.disabled || false,
    stopShortcutPropagation: override.stopShortcutPropagation || false,
    shouldNotUpdate: override.shouldNotUpdate || false,
    processStatus: override.processStatus || 'saved',
  };
};

describe('QuickActions standalone component', () => {
  const emptyViewId = '540485-a';
  describe('rendering tests:', () => {
    beforeEach(() => {
      const data1 = fixtures.props;

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/documentView/${data1.windowId}/${emptyViewId}/quickActions`)
        .reply(200, { data: { actions: [] } });

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/documentView/${data1.windowId}/${data1.viewId}/quickActions?selectedIds=${data1.selected.join(',')}`)
        .reply(200, { data: { actions: fixtures.data } });
    });

    it('renders nothing when no actions', () => {
      const props = createDummyProps({ viewId: emptyViewId, quickActions: { actions: [], pending: false } });
      const wrapper = mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <QuickActions {...props} />
        </ShortcutProvider>
      );

      expect(wrapper.html()).toBeFalsy();
    });

    it('renders actions', () => {
      const props = createDummyProps();
      const wrapper = mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <QuickActions {...props} />,
        </ShortcutProvider>
      );

      wrapper.update();

      expect(wrapper.html()).toContain('quick-actions-wrapper');
    });
  });
});
