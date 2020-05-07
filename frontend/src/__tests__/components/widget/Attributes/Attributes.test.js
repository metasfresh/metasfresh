import React from 'react';
import { shallow } from 'enzyme';
import nock from 'nock';
import Attributes from '../../../../components/widget/Attributes/Attributes';
import fixtures from '../../../../../test_setup/fixtures/attributes.json';

nock.enableNetConnect();

describe('Attributes component', () => {
  describe('renders', () => {
    it('without errors', () => {
      const props = fixtures.data1.widgetProps;
      const patchFn = jest.fn();

      shallow(<Attributes {...props} patch={patchFn} />);
    });
  });

  beforeEach(() => {
    nock('http://api.test.url')
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post('/address')
      .reply(200, fixtures.data1.instanceData);

    nock('http://api.test.url')
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/address/${fixtures.data1.instanceData.id}/layout`)
      .reply(200, fixtures.data1.layout);
  });

  describe('handlers test', () => {
    afterEach(() => {
      jest.clearAllMocks();
    });

    it('toggle triggers init', done => {
      const props = fixtures.data1.widgetProps;
      const patchFn = jest.fn();
      const wrapper = shallow(<Attributes {...props} patch={patchFn} />);
      const spy = jest.spyOn(wrapper.instance(), 'handleToggle');
      const spyInit = jest.spyOn(wrapper.instance(), 'handleInit');

      wrapper.find('button').simulate('click');
      expect(spy).toHaveBeenCalled();
      expect(spyInit).toHaveBeenCalled();

      wrapper.update();

      setTimeout(function() {
        try {
          expect(wrapper.state().dropdown).toBe(true);
          done();
        } catch (e) {
          done.fail(e);
        }
      }, 1000);
    });

    it.skip('keydown triggers onHandleKeyDown', () => {
      const props = fixtures.data1.widgetProps;
      const patchFn = jest.fn();
      const handleCompletion = jest.fn();
      const wrapper = shallow(<Attributes {...props} patch={patchFn} />);
      const spy = jest.spyOn(wrapper.instance(), 'handleKeyDown');

      const completionSpy = jest
        .spyOn(wrapper.instance(), 'handleCompletion')
        .mockImplementation(() => handleCompletion);

      wrapper.find('.attributes').simulate('keyDown', { keyCode: 27 });

      expect(spy).toHaveBeenCalled();
      expect(completionSpy).toHaveBeenCalled();
    });
  });
});
