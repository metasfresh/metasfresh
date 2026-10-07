import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import DateInput from '../DateInput';
import { trl } from '../../utils/translations';

/**
 * Generic mobile-UI attribute-edit section: renders one control per editable attribute (in the
 * given array order = the backend config's SeqNo order) and reports the operator's edits as one
 * `{ [attributeCode]: value }` map via `onFieldChange`.
 *
 * Backend contract: `de.metas.handlingunits.attribute.json.JsonAttribute` — `code`, `caption`,
 * `valueType` (STRING/NUMBER/DATE/LIST), and, for LIST, `listValues: [{ value, caption }]`.
 *
 * Inputs always start empty (no pre-fill from `attribute.value`) — attributes are optional, so an
 * untouched/cleared field is simply absent from the emitted map.
 *
 * DATE fields: the legacy `DateInput` fires on every keystroke, so while the typed text isn't yet
 * a valid date it reports the raw display text with `isValid: false`. That raw/partial text is
 * kept as the field's displayed value (so the operator can keep typing), but is withheld from the
 * emitted map until it becomes a valid ISO date — mirroring the `isBestBeforeDateValid` idiom in
 * `GetQuantityDialog` / `ChangeHUQtyDialog` / `InventoryCountComponent`.
 *
 * If the `attributes` prop changes to a different set of attribute codes while mounted (e.g. the
 * consumer re-renders this section for a different receive line), values for codes that disappear
 * are dropped — a stale value from the previous attribute set must never leak into the new one —
 * while selections for codes that persist are kept.
 *
 * A transient EMPTY `attributes` array is a loading state, NOT an attribute-set change: a background
 * reload under load can momentarily clear the prop before repopulating it with the same codes, and
 * the operator's in-progress values must survive that transient — clobbering them to blank would
 * post the receive with a blank value.
 */
const EditableAttributesSection = ({ attributes, disabled, onFieldChange }) => {
  const [valuesByCode, setValuesByCode] = useState({});
  const [invalidCodes, setInvalidCodes] = useState({});

  const attributeCodesKey = (attributes ?? []).map(({ code }) => code).join('|');
  const lastNonEmptyCodesKeyRef = useRef(attributeCodesKey);
  useEffect(() => {
    // A transient EMPTY attribute set is a loading state (a background reload under load momentarily
    // clears the prop), not a real change of the receive line's attributes — keep the operator's
    // in-progress values until the real attributes come back, otherwise a just-picked value is
    // silently clobbered to blank.
    if (attributeCodesKey === '') {
      return;
    }
    // Only a genuine change of the (non-empty) attribute code-set resets, keyed on the codes and not
    // on array identity: drop values for codes that disappeared (so a stale value never leaks into
    // the new set), keep selections for codes that persist.
    if (attributeCodesKey === lastNonEmptyCodesKeyRef.current) {
      return;
    }
    lastNonEmptyCodesKeyRef.current = attributeCodesKey;
    const codesInNewSet = attributeCodesKey.split('|');
    const keepPersistingCodes = (prev) =>
      Object.fromEntries(Object.entries(prev).filter(([code]) => codesInNewSet.includes(code)));
    setValuesByCode(keepPersistingCodes);
    setInvalidCodes(keepPersistingCodes);
  }, [attributeCodesKey]);

  if (!attributes || attributes.length === 0) {
    return null;
  }

  const handleFieldChange = (code, value, isValid = true) => {
    const nextValuesByCode = { ...valuesByCode };
    if (value === '' || value === null || value === undefined) {
      delete nextValuesByCode[code];
    } else {
      nextValuesByCode[code] = value;
    }
    setValuesByCode(nextValuesByCode);

    const nextInvalidCodes = { ...invalidCodes };
    if (isValid) {
      delete nextInvalidCodes[code];
    } else {
      nextInvalidCodes[code] = true;
    }
    setInvalidCodes(nextInvalidCodes);

    if (onFieldChange) {
      const emittedValuesByCode = { ...nextValuesByCode };
      Object.keys(nextInvalidCodes).forEach((invalidCode) => delete emittedValuesByCode[invalidCode]);
      onFieldChange(emittedValuesByCode);
    }
  };

  return (
    <table className="table" data-testid="editable-attributes-section">
      <tbody>
        {attributes.map(({ code, caption, valueType, listValues }) => (
          <tr key={code} data-testid={`attr-${code}-row`}>
            <th>{caption}</th>
            <td>
              <EditableAttributeField
                code={code}
                valueType={valueType}
                value={valuesByCode[code]}
                listValues={listValues}
                disabled={disabled}
                onChange={(value, isValid) => handleFieldChange(code, value, isValid)}
              />
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
EditableAttributesSection.propTypes = {
  attributes: PropTypes.arrayOf(
    PropTypes.shape({
      code: PropTypes.string.isRequired,
      caption: PropTypes.string.isRequired,
      valueType: PropTypes.oneOf(['STRING', 'NUMBER', 'DATE', 'LIST']).isRequired,
      value: PropTypes.any,
      listValues: PropTypes.arrayOf(
        PropTypes.shape({
          value: PropTypes.string.isRequired,
          caption: PropTypes.string.isRequired,
        })
      ),
    })
  ),
  disabled: PropTypes.bool,
  onFieldChange: PropTypes.func,
};

export default EditableAttributesSection;

//
//
// ------------------------------------
//
//

const EditableAttributeField = ({ code, valueType, value, listValues, disabled, onChange }) => {
  const testId = `attr-${code}-field`;

  switch (valueType) {
    case 'STRING':
      return (
        <input
          className="input"
          type="text"
          data-testid={testId}
          value={value ?? ''}
          disabled={disabled}
          onChange={(e) => onChange(e.target.value)}
        />
      );
    case 'NUMBER':
      return (
        <input
          className="input"
          type="number"
          data-testid={testId}
          value={value ?? ''}
          disabled={disabled}
          onChange={(e) => onChange(e.target.value)}
        />
      );
    case 'DATE':
      return (
        <DateInput
          testId={testId}
          value={value ?? ''}
          readOnly={disabled}
          onChange={({ date, isValid }) => onChange(date, isValid)}
        />
      );
    case 'LIST':
      return (
        <div className="select is-fullwidth">
          <select
            data-testid={testId}
            value={value ?? ''}
            disabled={disabled}
            onChange={(e) => onChange(e.target.value)}
          >
            <option value="">{trl('attributes.list.pleaseSelect')}</option>
            {(listValues ?? []).map((listValue) => (
              <option key={listValue.value} value={listValue.value}>
                {listValue.caption}
              </option>
            ))}
          </select>
        </div>
      );
    default:
      return null;
  }
};
EditableAttributeField.propTypes = {
  code: PropTypes.string.isRequired,
  valueType: PropTypes.oneOf(['STRING', 'NUMBER', 'DATE', 'LIST']).isRequired,
  value: PropTypes.any,
  listValues: PropTypes.arrayOf(
    PropTypes.shape({
      value: PropTypes.string.isRequired,
      caption: PropTypes.string.isRequired,
    })
  ),
  disabled: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
};
