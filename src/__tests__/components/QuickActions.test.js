import React from 'react';
import { mount, shallow, render } from 'enzyme';
import nock from 'nock';
import { connect } from 'react-redux';
import { shallowWithStore } from 'enzyme-redux';
import { createMockStore } from 'redux-test-utils';

import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import { QuickActions } from '../../components/app/QuickActions';
import fixtures from '../../../test_setup/fixtures/quickactions.json';


import { quickActionsRequest } from '../../api';
jest.mock('../../api');

const createDummyProps = function(props) {
  return {
    dispatch: props.dispatch || jest.fn(),
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
      nock('http://api.test.url')
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('rest/api/documentView/540485/540485-a/quickActions')
        .reply(200, { data: { actions: [] }} );

      nock('http://api.test.url')
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('rest/api/documentView/540485/540485-b/quickActions')
        .reply(200, { data: fixtures.data1.actions } );

      nock('http://api.test.url')
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('rest/api/documentView/540485/540485-a/quickActions')
        .reply(200, { data: fixtures.data2.actions } );
    });

    it('renders nothing when no actions', () => {
      const props = createDummyProps({ viewId: '540485-a' });
      const wrapper = shallow(<QuickActions {...props} />);

      expect(wrapper.html()).toBe(null);
    });

    it('fetches and renders actions on mount with `fetchOnInit` param', async function foo() {
      const props = createDummyProps({ viewId: '540485-b', fetchOnInit: true });
      const promise = Promise.resolve({ data: { actions: fixtures.data1.actions }});

      quickActionsRequest.mockImplementation(() => {
        return promise;
      });

      const wrapper = mount(
        <ShortcutProvider hotkeys={{}} keymap={{}} >
          <QuickActions {...props} />,
        </ShortcutProvider>
      );

      return promise.then(() => {
        wrapper.update();

        expect(wrapper.find(QuickActions).instance().state.actions).toHaveLength(1);
        expect(wrapper.html()).toContain('quick-actions-wrapper');
      });
    });
  });
});