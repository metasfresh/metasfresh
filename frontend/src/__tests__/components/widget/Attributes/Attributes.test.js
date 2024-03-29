import React from 'react';
import { shallow } from 'enzyme';
import nock from 'nock';
import Attributes from '../../../../components/widget/Attributes/Attributes';
import fixtures from '../../../../../test_setup/fixtures/attributes.json';
import AttributesDropdown
  from '../../../../components/widget/Attributes/AttributesDropdown';
import { useSelector } from 'react-redux';
import { createStore } from 'redux';

nock.enableNetConnect();

const mockStore = createStore((state = {}, action) => state);

jest.mock("react-redux", () => ({
  ...jest.requireActual("react-redux"),
  useSelector: jest.fn()
}));

describe('Attributes component', () => {
  beforeEach(() => {
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post('/address')
      .reply(200, fixtures.data1.newInstanceResponse);

    useSelector.mockImplementation(callback => {
      return callback(mockStore);
    });
  });

  describe('renders', () => {
    it('without errors', () => {
      shallow(<Attributes {...(fixtures.data1.widgetProps)} patch={jest.fn()} />);
    });
  });

  describe('click on button, expect dropdown to be rendered', () => {
    afterEach(() => {
      jest.clearAllMocks();
    });

    it('click on button', (done) => {
      const wrapper = shallow(<Attributes {...(fixtures.data1.widgetProps)} patch={jest.fn()} />);

      expect(wrapper.find(AttributesDropdown)).toHaveLength(0);
      wrapper.find('button').simulate('click');

      setTimeout(() => {
        try {
          wrapper.update();
          expect(wrapper.find(AttributesDropdown)).toHaveLength(1);

          done();
        } catch (error) {
          done(error);
        }
      }, 1000);
    });
  });
});
