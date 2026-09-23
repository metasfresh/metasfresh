import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import rowFixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableCell from '../../../components/table/TableCell';
import TableRow from '../../../components/table/TableRow';

/**
 * Address grid cells must not be clobbered to a raw string on Tab/Enter.
 *
 * An `Address` cell is object-valued ({key,caption}) and renders through the
 * SAME <Attributes> button-overlay as `ProductAttributes` (WidgetRenderer:
 * attributeType="address" vs "pattribute") — its value commits through that
 * overlay, never through the grid-nav raw-text row-write. But TableCell only
 * flagged `ProductAttributes` as an attribute widget, so on an Address cell it
 * emitted isAttributeWidget=false; TableRow's Tab/Enter handlers then wrote the
 * editor's raw text (e.g. the empty button value "") over the {key,caption}
 * object via updatePropertyValue -> silent data loss.
 *
 * Concrete failure pinned here:
 *   1) TableCell.handleKeyDown must classify an Address cell as an attribute
 *      widget (isAttributeWidget === true) — the fix locus.
 *   2) Fed that flag, TableRow's Tab/Enter handlers must skip the raw-text
 *      row-write for the Address cell (no clobber), exactly as for
 *      ProductAttributes.
 *
 * `Address` object-value commits via the overlay (like ProductAttributes), so
 * skipping the nav-layer raw-text write is correct and loses nothing.
 */

const ADDRESS_PROPERTY = 'C_BPartner_Location_ID_Address';
const RAW_EDITOR_TEXT = ''; // the <Attributes> button's .value is empty text

function cellProps(overrides) {
  const item = {
    widgetType: 'Address',
    gridAlign: 'left',
    fields: [{ field: ADDRESS_PROPERTY }],
  };
  return {
    item,
    property: ADDRESS_PROPERTY,
    tableId: 'anyid',
    isReadonly: false,
    isEdited: false,
    tabIndex: 0,
    docId: '1',
    ...overrides,
  };
}

function createRowProps(customProps) {
  const propsSeed = rowFixtures.oldProps1;
  return {
    ...propsSeed,
    tableId: getTableId(propsSeed),
    onClick: jest.fn(),
    handleSelect: jest.fn(),
    onDoubleClick: jest.fn(),
    changeListenOnTrue: jest.fn(),
    changeListenOnFalse: jest.fn(),
    handleRowCollapse: jest.fn(),
    handleRightClick: jest.fn(),
    updatePropertyValue: jest.fn(),
    ...customProps,
  };
}

function keyEvent(key, value) {
  return {
    key,
    keyCode: key === 'Tab' ? 9 : 13,
    target: { value, textContent: value },
    stopPropagation: jest.fn(),
    persist: jest.fn(),
  };
}

function callsForProperty(updatePropertyValue, property) {
  return updatePropertyValue.mock.calls
    .map((args) => args[0])
    .filter((payload) => payload && payload.property === property);
}

describe('TableCell/TableRow — Address object-valued cell guard on Tab/Enter', () => {
  it('classifies an Address cell as an attribute widget (isAttributeWidget=true)', () => {
    const onKeyDown = jest.fn();
    const cell = shallow(
      <TableCell
        {...cellProps({
          tableCellData: { widgetType: 'Address' },
          onKeyDown,
        })}
      />
    );

    cell.instance().handleKeyDown(keyEvent('Tab', RAW_EDITOR_TEXT));

    expect(onKeyDown).toHaveBeenCalledTimes(1);
    expect(onKeyDown.mock.calls[0][0].isAttributeWidget).toBe(true);
  });

  it('does NOT clobber an Address cell with the raw editor text on Tab or Enter', () => {
    // 1) The flag TableCell actually emits for an Address cell.
    const onKeyDown = jest.fn();
    const cell = shallow(
      <TableCell
        {...cellProps({
          tableCellData: { widgetType: 'Address' },
          onKeyDown,
        })}
      />
    );
    cell.instance().handleKeyDown(keyEvent('Tab', RAW_EDITOR_TEXT));
    const isAttributeWidget = onKeyDown.mock.calls[0][0].isAttributeWidget;

    // 2) Fed that flag, TableRow must skip the raw-text row-write for Address.
    const updatePropertyValue = jest.fn();
    const row = shallow(
      <TableRow {...createRowProps({ updatePropertyValue })} />
    ).instance();

    row.handleKeyDown({
      event: keyEvent('Tab', RAW_EDITOR_TEXT),
      property: ADDRESS_PROPERTY,
      readonly: false,
      isAttributeWidget,
    });
    row.handleKeyDown({
      event: keyEvent('Enter', RAW_EDITOR_TEXT),
      property: ADDRESS_PROPERTY,
      readonly: false,
      isAttributeWidget,
    });

    expect(callsForProperty(updatePropertyValue, ADDRESS_PROPERTY)).toHaveLength(
      0
    );
  });

  it('still flags a non-attribute Lookup cell as isAttributeWidget=false (regression control)', () => {
    const onKeyDown = jest.fn();
    const cell = shallow(
      <TableCell
        {...cellProps({
          item: {
            widgetType: 'Lookup',
            gridAlign: 'left',
            fields: [{ field: 'M_Product_ID' }],
          },
          property: 'M_Product_ID',
          tableCellData: { widgetType: 'Lookup' },
          onKeyDown,
        })}
      />
    );

    cell.instance().handleKeyDown(keyEvent('Tab', 'someText'));

    expect(onKeyDown.mock.calls[0][0].isAttributeWidget).toBe(false);
  });
});
