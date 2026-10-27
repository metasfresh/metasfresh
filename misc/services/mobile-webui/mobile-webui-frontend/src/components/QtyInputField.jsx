import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { qtyInfos } from '../utils/qtyInfos';
import { isValidDecimalNumberString, normalizeDecimalNumberString } from '../utils/decimalInput';
import { isAllowedDecimalInput } from '../utils/decimalKeystroke';

const QtyInputField = ({
  id,
  testId,
  qty: qtyInitial,
  uom,
  integerValuesOnly = false,
  isRequestFocus,
  readonly = false,
  validateQtyEntered,
  onQtyChange,
}) => {
  const initialString = qtyInitial != null ? `${qtyInitial}` : '';

  //
  // QtyInfo lifecycle:
  // - rawText is what the user sees/typed (e.g. "1,"); it is never rewritten while typing
  // - qtyInfo.qtyStr is the NORMALIZED dot-decimal which is forwarded to the parent (qtyInfos.toNumberOrString)
  const [rawText, setRawText] = useState(initialString);
  const [qtyInfo, setQtyInfo] = useState(
    computeQtyInfoFromString({
      qtyInputString: initialString,
      integerValuesOnly,
      prevQtyInfo: null,
      validateQtyEntered,
      uom,
    })
  );
  // the value this field last forwarded; the parent echoes it back as qtyInitial
  const lastForwardedQtyStrRef = useRef(initialString);

  useEffect(() => {
    // skip the echo of what the user is typing, so the raw text (e.g. "1,") is not overwritten
    if (initialString === lastForwardedQtyStrRef.current) {
      return;
    }
    const newQtyInfo = computeQtyInfoFromString({
      qtyInputString: initialString,
      integerValuesOnly,
      prevQtyInfo: {
        qty: qtyInfo?.qty ?? 0,
        notValidMessage: qtyInfo?.notValidMessage ?? null,
      },
      validateQtyEntered,
      uom,
    });
    lastForwardedQtyStrRef.current = newQtyInfo.qtyStr;
    setRawText(initialString);
    setQtyInfo(newQtyInfo);
  }, [initialString, integerValuesOnly]);

  //
  // Inform parent about initial value
  useEffect(() => forwardQtyInfoToParent(qtyInfo), [qtyInfo]);

  //
  // Request Focus
  const qtyInputRef = useRef(null);
  useEffect(() => {
    if (isRequestFocus && !readonly && qtyInputRef.current) {
      qtyInputRef.current.focus();
      qtyInputRef.current.select();
    }
  }, [isRequestFocus, readonly]);

  const handleQtyEntered = (e) => {
    const rawInput = e.target.value;
    const newQtyInfo = computeQtyInfoFromString({
      qtyInputString: rawInput,
      integerValuesOnly,
      prevQtyInfo: qtyInfo,
      validateQtyEntered,
      uom,
    });

    lastForwardedQtyStrRef.current = newQtyInfo.qtyStr;
    setRawText(rawInput);
    setQtyInfo(newQtyInfo);
    forwardQtyInfoToParent(newQtyInfo);
  };

  // block keystrokes the decimal parser would reject (desktop parity: CostPrice.handleBeforeInput)
  const handleBeforeInput = (e) => {
    if (!isAllowedDecimalInput(e.data)) {
      e.preventDefault();
    }
  };

  const forwardQtyInfoToParent = (qtyInfoToForward) => {
    onQtyChange(qtyInfoToForward);
  };

  return (
    <div className="field">
      <div className={cx('control', { 'has-icons-right': !!uom })}>
        <input
          id={id}
          data-testid={testId}
          ref={qtyInputRef}
          className="input"
          type="text"
          inputMode="decimal"
          value={rawText}
          disabled={!!readonly}
          onBeforeInput={handleBeforeInput}
          onChange={handleQtyEntered}
          onClick={() => qtyInputRef.current.select()}
        />
        {uom && <span className="icon is-small is-right">{uom}</span>}
      </div>
      <p data-testid="qty-validation-error" className="help is-danger">
        {qtyInfo.notValidMessage}&nbsp;
      </p>
    </div>
  );
};

QtyInputField.propTypes = {
  id: PropTypes.string,
  testId: PropTypes.string,
  qty: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  uom: PropTypes.string,
  integerValuesOnly: PropTypes.bool,
  isRequestFocus: PropTypes.bool,
  readonly: PropTypes.bool,
  //
  validateQtyEntered: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
};

const computeQtyInfoFromString = ({ qtyInputString, integerValuesOnly, prevQtyInfo, validateQtyEntered, uom }) => {
  // The user may type the decimal separator as ',' or '.'; forward the dot-decimal.
  const normalized = isValidDecimalNumberString(qtyInputString) ? normalizeDecimalNumberString(qtyInputString) : null;
  let qty = normalized ? parseFloat(normalized) : NaN;

  if (isNaN(qty)) {
    // empty / partial / unparseable: not valid, and never forwarded as 0
    return qtyInfos.invalidOf({
      qtyInputString: normalized ?? qtyInputString,
      prevQtyInfo,
    });
  } else {
    if (integerValuesOnly) {
      qty = Math.floor(qty);
    }

    const notValidMessage = validateQtyEntered ? validateQtyEntered(qty, uom) : null;

    return qtyInfos.of({ qty, qtyStr: normalized, notValidMessage });
  }
};

export default QtyInputField;
