import React, { useState } from 'react';
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
 */
const EditableAttributesSection = ({ attributes, disabled, onFieldChange }) => {
  const [valuesByCode, setValuesByCode] = useState({});

  if (!attributes || attributes.length === 0) {
    return null;
  }

  const handleFieldChange = (code, value) => {
    setValuesByCode((prevValuesByCode) => {
      const nextValuesByCode = { ...prevValuesByCode };
      if (value === '' || value === null || value === undefined) {
        delete nextValuesByCode[code];
      } else {
        nextValuesByCode[code] = value;
      }

      if (onFieldChange) {
        onFieldChange(nextValuesByCode);
      }

      return nextValuesByCode;
    });
  };

  return (
    <table className="table view-header is-size-6" data-testid="editable-attributes-section">
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
                onChange={(value) => handleFieldChange(code, value)}
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
        <DateInput testId={testId} value={value ?? ''} readOnly={disabled} onChange={({ date }) => onChange(date)} />
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
