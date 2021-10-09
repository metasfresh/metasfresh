import React from 'react';
import { shallow } from 'enzyme';

import widgetProps from '../../../test_setup/fixtures/widget/widget_wrapper.json';

import { WidgetWrapper } from '../../containers/WidgetWrapper';
import MasterWidget from '../../components/widget/MasterWidget';
import RawWidget from '../../components/widget/RawWidget';

const getProps = (propsVersion = '1', overrideProps = {}) => {
  const props = widgetProps[`props${propsVersion}`];
  const widgetData = widgetProps[`widgetData${propsVersion}`];

  return {
    ...props,
    widgetData,
    dropdownOpenCallback: jest.fn(),
    updatePropertyValue: jest.fn(),
    openModal: jest.fn(),
    patch: jest.fn(),
    allowShortcut: jest.fn(),
    disableShortcut: jest.fn(),
    renderMaster: true,
    ...overrideProps
  }
}

describe('WidgetWrapper disconnected', () => {
  it('renders MasterWidget without errors with the given props for document status', () => {
    const props = getProps();
    const wrapper = shallow(<WidgetWrapper {...props} />);

    expect(wrapper.find(MasterWidget).length).toEqual(1);
  });

  it('renders RawWidget without errors with the given props for Element widget', () => {
    const props = getProps("2", {
      onBlurWidget: jest.fn(),
    });
    const wrapper = shallow(<WidgetWrapper {...props} />);

    expect(wrapper.find(MasterWidget).length).toEqual(1);
    expect(wrapper.dive().find(RawWidget).length).toEqual(1);
  });

  it('renders RawWidget without errors with the given props for Table widget', () => {
    const props = getProps('3', {
      updateHeight: jest.fn(),
      closeTableField: jest.fn(),
      listenOnKeysFalse: jest.fn(),
      listenOnKeysTrue: jest.fn(),
      handleBackdropLock: jest.fn(),
      onChange: jest.fn(),
      onClickOutside: jest.fn(),
    });
    const wrapper = shallow(<WidgetWrapper {...props} />);

    expect(wrapper.find(MasterWidget).length).toEqual(1);
    expect(wrapper.dive().find(RawWidget).length).toEqual(1);
  });  
});
