import React from 'react';
import { shallow } from 'enzyme';

import { Lookup } from '../../../../components/widget/Lookup/Lookup';
import RawLookup from '../../../../components/widget/Lookup/RawLookup';

/**
 * A composite lookup (partner → location) renders one RawLookup per sub-field.
 * Only index 0 may receive the real callback / forwarded ref; non-primary sub-fields
 * must receive `undefined` — never the boolean `false`.
 */
describe('Lookup — props handed to composite sub-fields', () => {
  const properties = [
    { field: 'C_BPartner_ID', source: 'lookup', caption: 'Partner' },
    { field: 'C_BPartner_Location_ID', source: 'lookup', caption: 'Location' },
  ];
  const widgetData = [
    { field: 'C_BPartner_ID', value: null, readonly: false, mandatory: true },
    { field: 'C_BPartner_Location_ID', value: null, readonly: false, mandatory: false },
  ];
  const forwardedRef = React.createRef();

  const render = () =>
    shallow(
      <Lookup
        properties={properties}
        widgetData={widgetData}
        forwardedRef={forwardedRef}
        windowType="143"
        entity="window"
        onChange={jest.fn()}
        onSelectBarcode={jest.fn()}
        onScanBarcode={jest.fn()}
      />
    );

  it('primary sub-field gets the real handleInputEmptyStatus callback and the forwarded ref', () => {
    const wrapper = render();
    const primary = wrapper.find(RawLookup).at(0);
    expect(typeof primary.prop('handleInputEmptyStatus')).toBe('function');
    expect(primary.getElement().ref).toBe(forwardedRef);
  });

  it('secondary sub-field gets undefined — not false — for handleInputEmptyStatus and ref', () => {
    const wrapper = render();
    const secondary = wrapper.find(RawLookup).at(1);
    expect(secondary.prop('handleInputEmptyStatus')).toBeUndefined();
    expect(secondary.getElement().ref).toBeNull(); // React normalises an undefined ref to null
  });
});
