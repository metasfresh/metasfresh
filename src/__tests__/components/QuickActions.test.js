import React from 'react';
import { mount, shallow, render } from 'enzyme';
import nock from 'nock';
import { connect } from 'react-redux';
import { shallowWithStore } from 'enzyme-redux';
import { createMockStore } from 'redux-test-utils';

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

      // nock('http://api.test.url')
      //   .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      //   .get('rest/api/documentView/540485/540485-b/quickActions')
      //   .reply(200, { data: fixtures.data1.actions } );

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

    it('fetches and renders actions', async function foo() {
      const props = createDummyProps({ viewId: '540485-b', fetchOnInit: true });
    //   // const QAComponent = () => (<QuickActions {...props} />);

      // const wrapper = mount(<QuickActions {...props} />);
    //   // const ConnectedComponent = connect(false, false)(QAComponent);
    //   // const component = shallowWithStore(<ConnectedComponent />, createMockStore('test'));
    //   // expect(component.props().state).toBe(expectedState); 

    //   console.log('SHALLOW: ', wrapper)
    // await expect(wrapper.instance().init()).to.eventually.be.fulfilled();

    //   // expect(component.html()).not.toContain('quick-actions-wrapper');
      // expect(wrapper.html()).toContain('quick-actions-wrapper');


    //   // expect(wrapper.find('input').length).toBe(1);
    //   // expect(wrapper.find('input').html()).toContain(
    //   //   'Testpreisliste Lieferanten'
    //   // );
      console.log('ACTCIONS: ', fixtures.data1.actions);

      const promise = Promise.resolve({ data: { actions: fixtures.data1.actions }});

      quickActionsRequest.mockImplementation(() => {
        return promise;
      });

      const wrapper = mount(
        <QuickActions {...props} />,
        {
          context: {
            shortcuts: {
              subscribe: jest.fn(),
              unsubscribe: jest.fn(),
            },
          }
        }
      );

      // await expect(wrapper.instance().init()).to.eventually.be.fulfilled();
      console.log('context: ', wrapper.context())

      return promise.then(() => {
        expect(wrapper.instance().state.actions).toHaveLength(1);

        wrapper.update();
      }).then(() => {
      //   // expect(wrapper.text()).to.contain('data is ready');
        expect(wrapper.html()).toContain('quick-actions-wrapper');
      });
    });

    // it('renders without errors', () => {
    //   const props = createDummyProps(
    //     {
    //       ...fixtures.data1.widgetProps,
    //       isFocused: true,
    //     },
    //     fixtures.data1.listData
    //   );

    //   const wrapper = mount(<RawList {...props} />);

    //   expect(wrapper.html()).toContain('focused');
    //   expect(wrapper.find('input').length).toBe(1);
    //   expect(wrapper.find('input').html()).toContain(
    //     'Testpreisliste Lieferanten'
    //   );
    // });
  });
});