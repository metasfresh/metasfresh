import React from 'react';
import { mount } from 'enzyme';

import RawWidget from '../../../components/widget/RawWidget';
import fixtures from '../../../../test_setup/fixtures/raw_widget.json';

/**
 * A grid cell's editor carries `form-group-table`, the class every grid-editor geometry rule in
 * table.scss hangs on (fills the column, fits the row height). A grid shown inside a modal (e.g.
 * the order's text-lines modal) passes isModal=true; its cell editors must still get the class,
 * otherwise a long-text editor there grows the row from 42px to ~147px.
 */
const createProps = (props) => ({
  allowShortcut: jest.fn(),
  disableShortcut: jest.fn(),
  openModal: jest.fn(),
  patch: jest.fn(),
  updatePropertyValue: jest.fn(),
  onBlurWidget: jest.fn(),
  handleFocus: jest.fn(),
  handleBlur: jest.fn(),
  handlePatch: jest.fn(),
  handleChange: jest.fn(),
  handleProcess: jest.fn(),
  handleZoomInto: jest.fn(),
  modalVisible: false,
  timeZone: 'Europe/Berlin',
  entity: 'documentView',
  dataId: '1001282',
  ...fixtures.longText.layout1,
  widgetData: [{ ...fixtures.longText.data1 }],
  ...props,
});

const formGroupClass = (wrapper) =>
  wrapper.find('div.form-group').first().prop('className');

describe('RawWidget — grid-cell editor class', () => {
  it('a grid cell editor inside a modal gets form-group-table', () => {
    const wrapper = mount(
      <RawWidget {...createProps({ rowId: '1', isModal: true, dataSource: 'table' })} />
    );
    expect(formGroupClass(wrapper)).toContain('form-group-table');
  });

  it('a grid cell editor outside a modal gets form-group-table', () => {
    const wrapper = mount(
      <RawWidget {...createProps({ rowId: '1', isModal: false, dataSource: 'table' })} />
    );
    expect(formGroupClass(wrapper)).toContain('form-group-table');
  });

  it('a single-record form field inside a modal does not', () => {
    const wrapper = mount(
      <RawWidget {...createProps({ rowId: '1', isModal: true, dataSource: 'modal' })} />
    );
    expect(formGroupClass(wrapper)).not.toContain('form-group-table');
  });
});
