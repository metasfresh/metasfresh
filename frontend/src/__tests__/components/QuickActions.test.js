import React from 'react';
import { mount, shallow } from 'enzyme';
import nock from 'nock';
import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import { QuickActions } from '../../components/app/QuickActions';
import fixtures from '../../../test_setup/fixtures/quickactions.json';

import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';
jest.mock('../../api');

const createDummyProps = function(props) {
  return {
    actions: props.actions || [],
    openModal: props.openModal || jest.fn(),
    fetchedQuickActions: props.fetchedQuickActions || jest.fn(),
    deleteQuickActions: props.deleteQuickActions || jest.fn(),

    selected: props.selected || null,
    childView: props.childView || {},
    parentView: props.parentView || {},
    windowType: props.windowType || '540485',
    viewId: props.viewId || '540485-b',
    fetchOnInit: props.fetchOnInit || false,
    inBackground: props.inBackground || false,
    inModal: props.inModal || false,
    disabled: props.disabled || false,
    stopShortcutPropagation: props.stopShortcutPropagation || false,
    shouldNotUpdate: props.shouldNotUpdate || false,
    processStatus: props.processStatus || 'saved',
  };
};

describe('QuickActions standalone component', () => {
  describe('rendering tests:', () => {
    beforeEach(() => {
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('/documentView/540485/540485-a/quickActions')
        .reply(200, { data: { actions: [] } });

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('/documentView/540485/540485-b/quickActions')
        .reply(200, { data: fixtures.data1.actions });

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('/documentView/540485/540485-a/quickActions')
        .reply(200, { data: fixtures.data2.actions });
    });

    it('renders nothing when no actions', () => {
      const props = createDummyProps({ viewId: '540485-a' });
      const wrapper = shallow(<QuickActions {...props} />);

      expect(wrapper.html()).toBe(null);
    });

    it('renders actions', async function asyncTest() {
      const props = createDummyProps({
        viewId: '540485-b',
        actions: fixtures.data1.actions,
      });

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
