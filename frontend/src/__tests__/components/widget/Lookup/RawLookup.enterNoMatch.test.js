import React from 'react';
import { mount, shallow } from 'enzyme';

import ConnectedRawLookup, { RawLookup } from '../../../../components/widget/Lookup/RawLookup';
import { Lookup } from '../../../../components/widget/Lookup/Lookup';

/**
 * Enter on a filled Lookup whose typed text matches nothing puts the previous value back, as
 * leaving the field does, and commits nothing. A deliberate clear (empty text + Enter, or picking
 * the "none" entry) still commits null.
 */

const PREVIOUS = { key: '2005598', caption: '1000001_TestProduct1' };
const NONE_ITEM = { key: null, caption: 'none' };

const mountFilledLookup = (extraProps = {}) => {
  const onChange = jest.fn();
  const props = {
    entity: 'window',
    windowType: '143',
    idValue: 'dummyIdValue',
    item: { field: 'M_Product_ID' },
    mainProperty: { field: 'M_Product_ID' },
    defaultValue: PREVIOUS,
    initialFocus: false,
    dispatch: jest.fn(),
    onDropdownListToggle: jest.fn(),
    onChange,
    enableAutofocus: jest.fn(),
    setNextProperty: jest.fn(() => false),
    ...extraProps,
  };
  const wrapper = mount(<RawLookup {...props} />);
  return { wrapper, instance: wrapper.instance(), onChange };
};

/** type a query, let the typeahead answer, then press Enter in the dropdown */
const typeAndPressEnter = (instance, text, typeaheadValues) => {
  instance.inputSearch.value = text;
  instance.populateTypeaheadData({ values: typeaheadValues });
  // SelectionDropdown.handleKeyDown 'Enter' -> onSelect(selected)
  instance.handleSelect(instance.state.selected);
};

describe('RawLookup — Enter with typed text that matches nothing', () => {
  it('mandatory Lookup: restores the previous value and does not commit', () => {
    const { instance, onChange } = mountFilledLookup({ mandatory: true });

    typeAndPressEnter(instance, 'qzzx', []);

    expect(onChange).not.toHaveBeenCalled();
    expect(instance.inputSearch.value).toBe(PREVIOUS.caption);
  });

  it('non-mandatory Lookup: restores the previous value and does not commit', () => {
    const { instance, onChange } = mountFilledLookup({ mandatory: false });

    typeAndPressEnter(instance, 'qzzx', []);

    expect(onChange).not.toHaveBeenCalled();
    expect(instance.inputSearch.value).toBe(PREVIOUS.caption);
  });

  it('modal (selected = values[0] = undefined): restores the previous value and does not commit', () => {
    const { instance, onChange } = mountFilledLookup({ isModal: true });

    typeAndPressEnter(instance, 'qzzx', []);

    expect(onChange).not.toHaveBeenCalled();
    expect(instance.inputSearch.value).toBe(PREVIOUS.caption);
  });

  it('filter widget, not applied yet: restores the value picked in the filter', () => {
    // A not-applied view filter keeps the picked value in its parameter's `defaultValue`
    // (`updateItems`), and Lookup restores from that `defaultValue`.
    const parameter = {
      field: 'C_BPartner_ID',
      parameterName: 'C_BPartner_ID',
      widgetType: 'Lookup',
      defaultValue: null,
      value: null,
    };
    const updateItems = jest.fn(({ widgetField, value }) => {
      if (widgetField === parameter.parameterName) {
        parameter.defaultValue = value;
        parameter.value = value;
      }
    });
    const lookup = shallow(
      <Lookup
        properties={[parameter]}
        widgetData={[parameter]}
        filterWidget={true}
        isFilterActive={false}
        updateItems={updateItems}
        windowType="143"
        entity="documentView"
        onChange={jest.fn()}
        onSelectBarcode={jest.fn()}
        onScanBarcode={jest.fn()}
      />
    );
    const defaultValueFromLookup = () => {
      lookup.setProps({});
      return lookup.find(ConnectedRawLookup).at(0).prop('defaultValue');
    };

    const { wrapper, instance, onChange } = mountFilledLookup({
      filterWidget: true,
      mainProperty: parameter,
      item: parameter,
      updateItems,
      defaultValue: defaultValueFromLookup(),
    });

    // the user picks the partner in the filter
    instance.inputSearch.value = 'Pre';
    instance.handleSelect(PREVIOUS);
    expect(updateItems).toHaveBeenCalledWith({ widgetField: 'C_BPartner_ID', value: PREVIOUS });
    wrapper.setProps({ defaultValue: defaultValueFromLookup() });
    onChange.mockClear();

    typeAndPressEnter(instance, 'qzzx', []);

    expect(onChange).not.toHaveBeenCalled();
    expect(instance.inputSearch.value).toBe(PREVIOUS.caption);
  });

  it('closes the dropdown list after restoring', () => {
    const { instance } = mountFilledLookup({ mandatory: true });
    const toggleSpy = jest.spyOn(instance, 'fireOnDropdownListToggle');

    typeAndPressEnter(instance, 'qzzx', []);

    const lastCall = toggleSpy.mock.calls[toggleSpy.mock.calls.length - 1];
    expect(lastCall[0]).toBe(false);
  });
});

describe('RawLookup — a deliberate clear commits null', () => {
  it('empty text + Enter with nothing selected commits null', () => {
    const { instance, onChange } = mountFilledLookup({ mandatory: false });

    instance.inputSearch.value = '';
    instance.handleSelect(null);

    expect(onChange).toHaveBeenCalledTimes(1);
    expect(onChange).toHaveBeenCalledWith('M_Product_ID', null);
  });

  it('picking the "none" entry commits null even with text typed', () => {
    const { instance, onChange } = mountFilledLookup({ mandatory: false });

    instance.inputSearch.value = 'qzzx';
    instance.handleSelect(NONE_ITEM);

    expect(onChange).toHaveBeenCalledTimes(1);
    expect(onChange).toHaveBeenCalledWith('M_Product_ID', null);
  });

  it('a real match is still selected on Enter', () => {
    const { instance, onChange } = mountFilledLookup({ mandatory: true });
    const other = { key: '42', caption: 'Other product' };

    typeAndPressEnter(instance, 'Oth', [other]);

    expect(onChange).toHaveBeenCalledTimes(1);
    expect(onChange).toHaveBeenCalledWith('M_Product_ID', other);
  });
});
