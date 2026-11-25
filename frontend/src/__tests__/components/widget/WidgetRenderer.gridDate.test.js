import React from 'react';
import { shallow } from 'enzyme';

import WidgetRenderer from '../../../components/widget/WidgetRenderer';
import DatePicker from '../../../components/widget/DateTime/DatePicker';

/**
 * Grid date cell: WidgetRenderer forwards the grid-only props to the DatePicker of
 * a plain `Date` widget, and to no other date flavour.
 */
const renderWidget = (widgetType, extra) =>
  shallow(
    <WidgetRenderer
      widgetType={widgetType}
      widgetField="DatePromised"
      widgetData={[{ value: '2026-11-20' }]}
      widgetProperties={{ tabIndex: 0 }}
      fields={[{ field: 'DatePromised' }]}
      modalVisible={false}
      handleChange={jest.fn()}
      onPatch={jest.fn()}
      allowShortcut={jest.fn()}
      disableShortcut={jest.fn()}
      onListFocus={jest.fn()}
      onBlurWithParams={jest.fn()}
      onSetWidgetType={jest.fn()}
      onHandleProcess={jest.fn()}
      timeZone="Europe/Berlin"
      isGridCell={true}
      isPointerEdit={true}
      gridCellValue="2026-11-20"
      isOpenDatePicker={true}
      {...extra}
    />
  ).dive(); // WidgetRenderer is exported wrapped in withForwardedRef (a forwardRef)

describe('WidgetRenderer — grid date props', () => {
  it('a Date widget forwards isGridCell, isPointerEdit, gridCellValue and isOpenDatePicker', () => {
    const props = renderWidget('Date').find(DatePicker).props();
    expect(props.isGridCell).toBe(true);
    expect(props.isPointerEdit).toBe(true);
    expect(props.gridCellValue).toBe('2026-11-20');
    expect(props.isOpenDatePicker).toBe(true);
  });

  it('a Date widget outside the grid receives no grid flags', () => {
    const props = renderWidget('Date', {
      isGridCell: undefined,
      isPointerEdit: undefined,
      gridCellValue: undefined,
    })
      .find(DatePicker)
      .props();
    expect(props.isGridCell).toBeUndefined();
    expect(props.isPointerEdit).toBeUndefined();
    expect(props.gridCellValue).toBeUndefined();
  });

  it.each(['ZonedDateTime', 'Time'])(
    'a %s widget does not receive the grid-only props',
    (widgetType) => {
      const props = renderWidget(widgetType).find(DatePicker).props();
      expect(props.isGridCell).toBeUndefined();
      expect(props.isPointerEdit).toBeUndefined();
      expect(props.gridCellValue).toBeUndefined();
    }
  );
});
