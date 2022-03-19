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
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post('/address')
      .reply(200, fixtures.data1.instanceData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/address/${fixtures.data1.instanceData.id}/layout`)
      .reply(200, fixtures.data1.layout);
  });

  describe('handlers test', () => {
    afterEach(() => {
      jest.clearAllMocks();
    });

    it('toggle triggers init', (done) => {
      const props = fixtures.data1.widgetProps;
      const patchFn = jest.fn();
      const setTableNavigationFn = jest.fn();
      const wrapper = shallow(<Attributes {...props} patch={patchFn} setTableNavigation={setTableNavigationFn} />);
      const spy_showHideDropdown = jest.spyOn(wrapper.instance(), 'showHideDropdown');
      const spy_loadDropdownData = jest.spyOn(wrapper.instance(), 'loadDropdownData');

      wrapper.find('button').simulate('click');

      expect(spy_showHideDropdown).toHaveBeenCalled();
      expect(spy_loadDropdownData).toHaveBeenCalled();

      wrapper.update();

      setTimeout(() => {
        try {
          expect(wrapper.state().isDropdownOpen).toBe(true);
          done();
        } catch (e) {
          done.fail(e);
        }
      }, 1000);
    });
  });
});
