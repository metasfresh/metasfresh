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
