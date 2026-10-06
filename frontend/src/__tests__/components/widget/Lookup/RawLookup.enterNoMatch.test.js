import React from 'react';
import { mount } from 'enzyme';

import { RawLookup } from '../../../../components/widget/Lookup/RawLookup';

/**
 * Enter on a filled Lookup whose typed text matches nothing must put the
 * PREVIOUS value back - the same outcome as Tab / click-away
 * (handleInputTextBlur) - instead of committing null.
 *
 * Concrete failure pinned here: a filled Lookup (e.g. order line Product),
 * user types "qzzx" (no search results), presses Enter. The typeahead
 * leaves nothing selected (populateTypeaheadData -> selected = null, or
 * values[0] = undefined in a modal), SelectionDropdown hands that to
 * onSelect on Enter, and handleSelect committed onChange(field, null) ->
 * PATCH null -> a mandatory field is cleared ("Fill mandatory fields").
 * This is shared Lookup code: grid cells and single-row forms alike.
 *
 * A deliberate clear must keep working: empty text + Enter, and picking the
 * "none" entry, still commit null.
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

  it('filter widget (view filter / process parameter): restores the previous value and does not commit', () => {
    const { instance, onChange } = mountFilledLookup({
      filterWidget: true,
      mainProperty: { field: 'C_BPartner_ID', parameterName: 'C_BPartner_ID' },
      item: { field: 'C_BPartner_ID', parameterName: 'C_BPartner_ID' },
    });
    // a filter Lookup does not copy its value into the input on mount (handleValueChanged
    // skips filter widgets): the input shows the previous value because the user had picked it
    instance.inputSearch.value = PREVIOUS.caption;

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

describe('RawLookup — deliberate clear still clears (regression control)', () => {
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
