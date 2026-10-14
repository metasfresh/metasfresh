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

  // Partial overlap: the code-set genuinely changes but still contains one of the previous codes.
  // The persisting code's value MUST be kept while the vanished code's value MUST be dropped -
  // the reset prunes per-code, it is not all-or-nothing (docstring: "selections for codes that
  // persist are kept" while values for disappearing codes "are dropped").
  it('keeps a persisting code value and drops a vanished code value on a partial-overlap code-set change', () => {
    const AB_ATTRIBUTES = [
      { code: 'LotNumber', caption: 'Lot number', valueType: 'STRING', value: null },
      { code: 'BatchRef', caption: 'Batch ref', valueType: 'STRING', value: null },
    ];
    // Different, still-non-empty code-set that STILL contains LotNumber (A) but drops BatchRef (B).
    const AC_ATTRIBUTES = [
      { code: 'LotNumber', caption: 'Lot number', valueType: 'STRING', value: null },
      { code: 'Color', caption: 'Color', valueType: 'STRING', value: null },
    ];
    const { rerenderWithAttributes } = renderSection({ attributes: AB_ATTRIBUTES });

    fireEvent.change(screen.getByTestId('attr-LotNumber-field'), { target: { value: 'LOT-0001' } });
    fireEvent.change(screen.getByTestId('attr-BatchRef-field'), { target: { value: 'BATCH-9' } });

    // Genuine code-set change [A,B] -> [A,C]: A persists, B disappears, C is new.
    rerenderWithAttributes(AC_ATTRIBUTES);

    // A persists -> its just-entered value is kept, not wiped by the reset.
    expect(screen.getByTestId('attr-LotNumber-field')).toHaveValue('LOT-0001');
    // B is gone from the set -> no longer rendered.
    expect(screen.queryByTestId('attr-BatchRef-field')).not.toBeInTheDocument();

    // Bringing B back proves its value was DROPPED from state (pruned), not merely hidden.
    rerenderWithAttributes(AB_ATTRIBUTES);
    expect(screen.getByTestId('attr-LotNumber-field')).toHaveValue('LOT-0001');
    expect(screen.getByTestId('attr-BatchRef-field')).toHaveValue('');
  });

  // Under a background wfProcess reload the `attributes` prop is transiently emptied and then
  // repopulated with the SAME code-set (a new array identity). The operator's just-picked value
  // MUST survive that transient - clobbering it to blank posted a receive with a BLANK value.
  // A transient empty set is a loading state, NOT a genuine attribute-set change.
  it("keeps the operator's selection across a transient empty attributes reload (same code-set)", () => {
    const { onFieldChange, rerenderWithAttributes } = renderSection();

    fireEvent.change(screen.getByTestId('attr-SizeCM-field'), { target: { value: 'M' } });
    expect(screen.getByTestId('attr-SizeCM-field')).toHaveValue('M');
    expect(onFieldChange).toHaveBeenLastCalledWith(expect.objectContaining({ SizeCM: 'M' }));

    // Background reload under load: the prop is briefly emptied (section renders nothing)...
    rerenderWithAttributes([]);
    expect(screen.queryByTestId('attr-SizeCM-field')).not.toBeInTheDocument();

    // ...then repopulated with a NEW array carrying the SAME codes.
    rerenderWithAttributes(ATTRIBUTES.map((attr) => ({ ...attr })));

    // The operator's selection must still be there - not silently reset to blank.
    expect(screen.getByTestId('attr-SizeCM-field')).toHaveValue('M');
  });

  // A pure re-render with a NEW array identity but the SAME codes (no empty in between) must also
  // preserve the value - the reset keys on the code-set, never on array identity.
  it('keeps the operator selection when re-rendered with a new array of the same codes', () => {
    const { rerenderWithAttributes } = renderSection();

    fireEvent.change(screen.getByTestId('attr-LotNumber-field'), { target: { value: 'LOT-0001' } });
    expect(screen.getByTestId('attr-LotNumber-field')).toHaveValue('LOT-0001');

    rerenderWithAttributes(ATTRIBUTES.map((attr) => ({ ...attr })));

    expect(screen.getByTestId('attr-LotNumber-field')).toHaveValue('LOT-0001');
  });
});
