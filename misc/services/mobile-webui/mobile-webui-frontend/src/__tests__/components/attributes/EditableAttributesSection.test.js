import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent } from '@testing-library/react';

import EditableAttributesSection from '../../../components/attributes/EditableAttributesSection';

// Legacy (non-native) date control, so DateInput renders a plain text input using the
// DD.MM.YYYY display format — no redux Provider needed.
jest.mock('../../../reducers/settings', () => ({
  useBooleanSetting: () => false,
}));

const LIST_VALUES = [
  { value: 'S', caption: 'Small' },
  { value: 'M', caption: 'Medium' },
];

// Order deliberately NOT alphabetical / NOT grouped by valueType, so the order-follows-array
// assertion actually proves something.
const ATTRIBUTES = [
  { code: 'SizeCM', caption: 'Size (cm)', valueType: 'LIST', value: null, listValues: LIST_VALUES },
  { code: 'LotNumber', caption: 'Lot number', valueType: 'STRING', value: null },
  { code: 'BestBeforeDate', caption: 'Best before', valueType: 'DATE', value: null },
  { code: 'NetWeight', caption: 'Net weight', valueType: 'NUMBER', value: null },
];

const renderSection = ({ attributes = ATTRIBUTES, onFieldChange = jest.fn() } = {}) => {
  const { rerender } = render(<EditableAttributesSection attributes={attributes} onFieldChange={onFieldChange} />);
  return {
    onFieldChange,
    rerenderWithAttributes: (nextAttributes) =>
      rerender(<EditableAttributesSection attributes={nextAttributes} onFieldChange={onFieldChange} />),
  };
};

describe('EditableAttributesSection', () => {
  it('renders each of the 4 value types with its correct control', () => {
    renderSection();

    const stringField = screen.getByTestId('attr-LotNumber-field');
    expect(stringField.tagName).toBe('INPUT');
    expect(stringField).toHaveAttribute('type', 'text');

    const numberField = screen.getByTestId('attr-NetWeight-field');
    expect(numberField.tagName).toBe('INPUT');
    expect(numberField).toHaveAttribute('type', 'number');

    const dateField = screen.getByTestId('attr-BestBeforeDate-field');
    expect(dateField.tagName).toBe('INPUT');
    expect(dateField).toHaveAttribute('placeholder', 'DD.MM.YYYY');

    const listField = screen.getByTestId('attr-SizeCM-field');
    expect(listField.tagName).toBe('SELECT');
  });

  it('renders the list control options from listValues and emits the selected value', () => {
    const { onFieldChange } = renderSection();

    const listField = screen.getByTestId('attr-SizeCM-field');
    const optionValues = Array.from(listField.querySelectorAll('option'))
      .map((option) => option.value)
      .filter((value) => value !== '');
    expect(optionValues).toEqual(['S', 'M']);

    fireEvent.change(listField, { target: { value: 'M' } });

    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ SizeCM: 'M' }));
  });

  it('emits the entered value keyed by attribute code when editing a string field', () => {
    const { onFieldChange } = renderSection();

    fireEvent.change(screen.getByTestId('attr-LotNumber-field'), { target: { value: 'LOT-0001' } });

    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ LotNumber: 'LOT-0001' }));
  });

  it('emits the entered value keyed by attribute code when editing a number field', () => {
    const { onFieldChange } = renderSection();

    fireEvent.change(screen.getByTestId('attr-NetWeight-field'), { target: { value: '42' } });

    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ NetWeight: '42' }));
  });

  it('emits the entered value keyed by attribute code when editing a date field', () => {
    const { onFieldChange } = renderSection();

    fireEvent.change(screen.getByTestId('attr-BestBeforeDate-field'), { target: { value: '24.12.2026' } });

    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ BestBeforeDate: '2026-12-24' }));
  });

  it('emits no value for a field left empty', () => {
    const { onFieldChange } = renderSection();

    // Never touched -> never part of the emitted map.
    fireEvent.change(screen.getByTestId('attr-LotNumber-field'), { target: { value: 'LOT-0001' } });
    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ LotNumber: 'LOT-0001' }));

    // Typed, then cleared -> dropped from the emitted map again.
    fireEvent.change(screen.getByTestId('attr-LotNumber-field'), { target: { value: '' } });
    const lastCallArg = onFieldChange.mock.calls[onFieldChange.mock.calls.length - 1][0];
    expect(lastCallArg).not.toHaveProperty('LotNumber');
    expect(lastCallArg).not.toHaveProperty('NetWeight');
    expect(lastCallArg).not.toHaveProperty('BestBeforeDate');
    expect(lastCallArg).not.toHaveProperty('SizeCM');
  });

  it('renders the fields in the given array order (SeqNo order)', () => {
    renderSection();

    const captions = screen.getAllByRole('row').map((row) => row.querySelector('th')?.textContent);
    expect(captions).toEqual(['Size (cm)', 'Lot number', 'Best before', 'Net weight']);
  });

  it('does not emit a partial/invalid date, but keeps it as the displayed text', () => {
    const { onFieldChange } = renderSection();

    const dateField = screen.getByTestId('attr-BestBeforeDate-field');
    fireEvent.change(dateField, { target: { value: '24.12.202' } });

    // The raw partial text is kept for display so the operator can keep typing...
    expect(dateField).toHaveValue('24.12.202');
    // ...but must never reach the emitted map.
    const lastCallArg = onFieldChange.mock.calls[onFieldChange.mock.calls.length - 1][0];
    expect(lastCallArg).not.toHaveProperty('BestBeforeDate');
  });

  it('emits the ISO date once a partial date is completed to a valid one', () => {
    const { onFieldChange } = renderSection();

    const dateField = screen.getByTestId('attr-BestBeforeDate-field');
    fireEvent.change(dateField, { target: { value: '24.12.202' } });
    expect(onFieldChange.mock.calls[onFieldChange.mock.calls.length - 1][0]).not.toHaveProperty('BestBeforeDate');

    fireEvent.change(dateField, { target: { value: '24.12.2026' } });
    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ BestBeforeDate: '2026-12-24' }));
  });

  it('resets collected values when the set of attribute codes changes', () => {
    const OTHER_ATTRIBUTES = [{ code: 'Color', caption: 'Color', valueType: 'STRING', value: null }];
    const { onFieldChange, rerenderWithAttributes } = renderSection();

    fireEvent.change(screen.getByTestId('attr-LotNumber-field'), { target: { value: 'LOT-0001' } });
    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ LotNumber: 'LOT-0001' }));

    // Attributes prop changes to a different set of codes (e.g. a different receive line)...
    rerenderWithAttributes(OTHER_ATTRIBUTES);
    expect(screen.queryByTestId('attr-LotNumber-field')).not.toBeInTheDocument();

    // ...and back to (a set including) the original code -> the stale value must not resurface.
    rerenderWithAttributes(ATTRIBUTES);
    expect(screen.getByTestId('attr-LotNumber-field')).toHaveValue('');
  });
});
