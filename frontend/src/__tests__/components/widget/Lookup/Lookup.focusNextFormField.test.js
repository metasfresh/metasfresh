import React from 'react';
import { shallow } from 'enzyme';

import { Lookup } from '../../../../components/widget/Lookup/Lookup';

/**
 * After the last sub-field of a header lookup is picked (business partner → address → contact), the pick is
 * saved first and only then the cursor moves on to the next field of the form. If the user moved the cursor
 * elsewhere in the meantime (Alt+Q opened the quick entry and put it in the product field), it must stay there.
 */
describe('Lookup — moving on after the last sub-field is picked', () => {
  const properties = [
    { field: 'C_BPartner_ID', source: 'lookup', caption: 'Partner' },
    { field: 'AD_User_ID', source: 'list', caption: 'Contact' },
  ];
  const widgetData = [
    { field: 'C_BPartner_ID', value: null, readonly: false, mandatory: true },
    { field: 'AD_User_ID', value: null, readonly: false, mandatory: false },
  ];

  let lookupEl, contactList, nextInput, elsewhere;

  beforeEach(() => {
    document.body.innerHTML = '';
    const form = document.createElement('form');
    lookupEl = document.createElement('div');
    const partnerInput = document.createElement('input');
    contactList = document.createElement('div');
    contactList.tabIndex = 0;
    lookupEl.append(partnerInput, contactList);
    nextInput = document.createElement('input');
    form.append(lookupEl, nextInput);
    elsewhere = document.createElement('input');
    document.body.append(form, elsewhere);
  });

  const lookup = (onBlurWidget) => {
    const instance = shallow(
      <Lookup
        properties={properties}
        widgetData={widgetData}
        windowType="143"
        entity="window"
        onChange={jest.fn()}
        onSelectBarcode={jest.fn()}
        onScanBarcode={jest.fn()}
        onBlurWidget={onBlurWidget}
      />
    ).instance();
    instance.setWrapperElement(lookupEl);
    return instance;
  };

  it('moves the cursor to the next form field when it is still in the lookup', () => {
    const onBlurWidget = jest.fn();
    contactList.focus();

    lookup(onBlurWidget).setNextProperty('AD_User_ID');

    expect(onBlurWidget).toHaveBeenCalled();
    expect(document.activeElement).toBe(nextInput);
  });

  it('moves the cursor to the next form field when no element holds it', () => {
    document.activeElement.blur();

    lookup(jest.fn()).setNextProperty('AD_User_ID');

    expect(document.activeElement).toBe(nextInput);
  });

  it('moves the cursor to the next form field also when onBlurWidget already moved it there', () => {
    contactList.focus();

    lookup(() => nextInput.focus()).setNextProperty('AD_User_ID');

    expect(document.activeElement).toBe(nextInput);
  });

  it('leaves the cursor where it is when it left the lookup before the pick was saved', () => {
    elsewhere.focus();

    lookup(jest.fn()).setNextProperty('AD_User_ID');

    expect(document.activeElement).toBe(elsewhere);
  });
});

/**
 * After a sub-field that is not the last one is picked (business partner → address), the next sub-list gets the
 * cursor from within the lookup. The cursor is not first sent to the next field of the form: from there the
 * sub-list would have to take it from outside the lookup, which it must not do (Alt+Q may have put the cursor in
 * the quick entry while the pick was being saved).
 */
describe('Lookup — moving on after a sub-field that is not the last one is picked', () => {
  const properties = [
    { field: 'C_BPartner_ID', source: 'lookup', caption: 'Partner' },
    { field: 'C_BPartner_Location_ID', source: 'list', caption: 'Address' },
    { field: 'AD_User_ID', source: 'list', caption: 'Contact' },
  ];
  // The business partner is picked (has a value).
  const widgetDataWithAddress = (isAddressReadonly) => [
    {
      field: 'C_BPartner_ID',
      value: { key: '1', caption: 'Partner 1' },
      readonly: false,
      mandatory: true,
    },
    {
      field: 'C_BPartner_Location_ID',
      value: null,
      readonly: isAddressReadonly,
      mandatory: true,
    },
    { field: 'AD_User_ID', value: null, readonly: false, mandatory: false },
  ];

  let lookupEl, partnerInput;

  beforeEach(() => {
    document.body.innerHTML = '';
    lookupEl = document.createElement('div');
    partnerInput = document.createElement('input');
    lookupEl.append(partnerInput);
    document.body.append(lookupEl);
  });

  const lookup = ({
    onBlurWidget,
    isAddressReadonly = false,
    addressDescriptor = properties[1],
  }) => {
    const wrapper = shallow(
      <Lookup
        properties={[properties[0], addressDescriptor, properties[2]]}
        widgetData={widgetDataWithAddress(isAddressReadonly)}
        windowType="143"
        entity="window"
        onChange={jest.fn()}
        onSelectBarcode={jest.fn()}
        onScanBarcode={jest.fn()}
        onBlurWidget={onBlurWidget}
      />
    );
    wrapper.instance().setWrapperElement(lookupEl);
    return wrapper;
  };

  it('hands on to the next sub-field and keeps the cursor in the lookup when the next sub-field is editable', () => {
    const onBlurWidget = jest.fn();
    partnerInput.focus();
    const wrapper = lookup({ onBlurWidget });

    const hasNextSubField = wrapper.instance().setNextProperty('C_BPartner_ID');

    expect(hasNextSubField).toBe(true);
    expect(wrapper.state('property')).toBe('C_BPartner_Location_ID');
    expect(onBlurWidget).not.toHaveBeenCalled();
    expect(document.activeElement).toBe(partnerInput);
  });

  it('moves the cursor on to the next form field when the next sub-field is read-only', () => {
    const onBlurWidget = jest.fn();
    partnerInput.focus();
    const wrapper = lookup({ onBlurWidget, isAddressReadonly: true });

    wrapper.instance().setNextProperty('C_BPartner_ID');

    expect(wrapper.state('property')).toBe('C_BPartner_Location_ID');
    expect(onBlurWidget).toHaveBeenCalledTimes(1);
  });

  // Only a List part gets `autoFocus` when it becomes the current property; any other kind of part never takes the
  // cursor, so the cursor must be moved on to the next form field as for a read-only one.
  [
    {
      partLabel: 'a tooltip',
      addressDescriptor: {
        field: 'C_BPartner_Location_ID',
        type: 'Tooltip',
        caption: 'Address',
      },
    },
    {
      partLabel: 'a lookup',
      addressDescriptor: {
        field: 'C_BPartner_Location_ID',
        source: 'lookup',
        caption: 'Address',
      },
    },
  ].forEach(({ partLabel, addressDescriptor }) => {
    it(`moves the cursor on to the next form field when the next sub-field is ${partLabel}`, () => {
      const onBlurWidget = jest.fn();
      partnerInput.focus();
      const wrapper = lookup({ onBlurWidget, addressDescriptor });

      wrapper.instance().setNextProperty('C_BPartner_ID');

      expect(wrapper.state('property')).toBe('C_BPartner_Location_ID');
      expect(onBlurWidget).toHaveBeenCalledTimes(1);
    });
  });
});
