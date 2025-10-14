import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import DateInput from '../../../components/DateInput';
import QtyInputField from '../../../components/QtyInputField';
import { qtyInfos } from '../../../utils/qtyInfos';
import DialogButton from '../../../components/dialogs/DialogButton';
import { trl } from '../../../utils/translations';

const InventoryCountComponent = ({ disabled, resolvedHU, onInventoryCountSubmit }) => {
  const { huDisplayName, productName, locatorName, qtyBooked, uom, attributes } = resolvedHU;

  const [qtyCount, setQtyCount] = useState(() => qtyInfos.invalidOfNumber());
  const [attributeValuesByCode, setAttributeValuesByCode] = useState(() => {
    if (!attributes) {
      return {};
    }
    return attributes.reduce((acc, attribute) => {
      acc[attribute.code] = { value: attribute.value, isInvalid: false };
      return acc;
    }, {});
  });

  const isAllValid = qtyCount?.isQtyValid && isAttributeValuesValid({ attributes, attributeValuesByCode });

  const onQtyCountChanged = (qtyInfo) => {
    // console.log('onQtyCountChanged', qtyInfo);
    setQtyCount(qtyInfo);
  };
  const onAttributeChanged = ({ code, value, isInvalid }) => {
    // console.log('onAttributeChanged', { code, value, isInvalid });
    setAttributeValuesByCode((prev) => ({ ...prev, [code]: { value, isInvalid } }));
  };
  const onOK = () => {
    if (disabled) return;

    onInventoryCountSubmit({
      qtyCount: qtyCount.qty,
      attributes: Object.keys(attributeValuesByCode).map((code) => {
        return {
          code,
          value: attributeValuesByCode[code]?.value,
        };
      }),
      lineCountingDone: false, // TODO
    });
  };

  return (
    <>
      <div className="table-container">
        <table className="table">
          <tbody>
            <tr>
              <th>{trl('inventory.locator')}</th>
              <td>
                <span data-testid="locator">{locatorName}</span>
              </td>
            </tr>
            <tr>
              <th>{trl('inventory.HU')}</th>
              <td>
                <span data-testid="hu">{huDisplayName}</span>
              </td>
            </tr>
            <tr>
              <th>{trl('inventory.product')}</th>
              <td>
                <span data-testid="product">{productName}</span>
              </td>
            </tr>
            <tr>
              <th>{trl('inventory.qtyBooked')}</th>
              <td>
                <span data-testid="qty-booked">{formatQtyToHumanReadableStr({ qty: qtyBooked, uom })}</span>
              </td>
            </tr>
            <tr>
              <th>{trl('inventory.qtyCount')}</th>
              <td>
                <QtyInputField
                  testId="qty-count"
                  qty={qtyInfos.toNumberOrString(qtyCount)}
                  uom={uom}
                  onQtyChange={onQtyCountChanged}
                  readonly={disabled}
                />
              </td>
            </tr>
            {attributes &&
              attributes.map(({ caption, code, valueType }) => {
                const { value } = attributeValuesByCode[code] || {};
                return (
                  <AttributeRow
                    key={code}
                    caption={caption}
                    code={code}
                    value={value}
                    valueType={valueType}
                    disabled={disabled}
                    onChange={onAttributeChanged}
                  />
                );
              })}
          </tbody>
        </table>
      </div>
      <div className="buttons is-centered">
        <DialogButton
          caption="OK"
          className="is-success"
          disabled={disabled || !isAllValid}
          onClick={() => onOK()}
          testId="ok-button"
        />
      </div>
    </>
  );
};
InventoryCountComponent.propTypes = {
  disabled: PropTypes.bool,
  resolvedHU: PropTypes.object.isRequired,
  onInventoryCountSubmit: PropTypes.func.isRequired,
};

export default InventoryCountComponent;

//
//
//
//
//
// ------------------------------------
//
//
//
//
//

const isAttributeValuesValid = ({ attributes, attributeValuesByCode }) => {
  if (!attributes) {
    return true;
  }

  for (const attribute of attributes) {
    const valueHolder = attributeValuesByCode?.[attribute.code];
    if (!valueHolder) {
      return false;
    }

    if (valueHolder.isInvalid) {
      return false;
    }
  }

  return true;
};

//
//
// ------------------------------------
//
//

const AttributeRow = ({ caption, code, value, valueType, disabled, onChange }) => {
  return (
    <tr>
      <th>{caption}</th>
      <td>
        <AttributeInputField
          code={code}
          valueType={valueType}
          value={value}
          disabled={disabled}
          onChange={({ value, isInvalid }) => onChange({ code, valueType, value, isInvalid })}
          testId={`attr-${code}-field`}
        />
      </td>
    </tr>
  );
};
AttributeRow.propTypes = {
  caption: PropTypes.string.isRequired,
  code: PropTypes.string.isRequired,
  valueType: PropTypes.string.isRequired,
  value: PropTypes.any,
  disabled: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
};

//
//
// ------------------------------------
//
//

const AttributeInputField = ({ valueType, value, disabled, onChange, testId }) => {
  switch (valueType) {
    case 'STRING':
      return (
        <input
          className="input"
          type="text"
          value={value ?? ''}
          disabled={disabled}
          onChange={(e) => onChange({ value: e.target.value ? e.target.value : '' })}
          data-testid={testId}
        />
      );
    case 'DATE':
      return (
        <DateInput
          type="date"
          value={value}
          readOnly={disabled}
          onChange={({ date, isValid }) => onChange({ value: date, isInvalid: !isValid })}
          testId={testId}
        />
      );

    default:
      return value;
  }
};
AttributeInputField.propTypes = {
  valueType: PropTypes.string.isRequired,
  value: PropTypes.any,
  disabled: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
  testId: PropTypes.string.isRequired,
};
