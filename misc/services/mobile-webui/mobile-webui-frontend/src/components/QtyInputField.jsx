import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { qtyInfos } from '../utils/qtyInfos';

const QtyInputField = ({
  qty: qtyInitial,
  uom,
  integerValuesOnly = false,
  isRequestFocus,
  readonly = false,
  validateQtyEntered,
  onQtyChange,
}) => {
  //
  // QtyInfo lifecycle:
  const [qtyInfo, setQtyInfo] = useState(
    computeQtyInfoFromString({
      qtyInputString: qtyInitial != null ? `${qtyInitial}` : '',
      integerValuesOnly,
      prevQtyInfo: null,
      validateQtyEntered,
      uom,
    })
  );

  useEffect(() => {
    const newQtyInfo = computeQtyInfoFromString({
      qtyInputString: qtyInitial != null ? `${qtyInitial}` : '',
      integerValuesOnly,
      prevQtyInfo: {
        qty: qtyInfo?.qty ?? 0,
        notValidMessage: qtyInfo?.notValidMessage ?? null,
      },
      validateQtyEntered,
      uom,
    });
    setQtyInfo(newQtyInfo);
  }, [qtyInitial, integerValuesOnly, qtyInfo?.qty, qtyInfo?.notValidMessage, qtyInfo?.isQtyValid, validateQtyEntered]);
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
    const qtyInputString = e.target.value ? e.target.value : '0';
    const newQtyInfo = computeQtyInfoFromString({
      qtyInputString,
      integerValuesOnly,
      prevQtyInfo: qtyInfo,
      validateQtyEntered,
      uom,
    });

    setQtyInfo(newQtyInfo);
    forwardQtyInfoToParent(newQtyInfo);
  };

  const forwardQtyInfoToParent = (qtyInfoToForward) => {
    onQtyChange(qtyInfoToForward);
  };

  return (
    <div className="field">
      <div className={cx('control', { 'has-icons-right': !!uom })}>
        <input
          ref={qtyInputRef}
          className="input"
          type="number"
          value={qtyInfo.qtyStr}
          disabled={!!readonly}
          onChange={handleQtyEntered}
          onClick={() => qtyInputRef.current.select()}
        />
        {uom && <span className="icon is-small is-right">{uom}</span>}
      </div>
      <p className="help is-danger">{qtyInfo.notValidMessage}&nbsp;</p>
    </div>
  );
};

QtyInputField.propTypes = {
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
  let qty = parseFloat(qtyInputString);

  if (isNaN(qty)) {
    return qtyInfos.invalidOf({
      qtyInputString,
      prevQtyInfo,
    });
  } else {
    if (integerValuesOnly) {
      qty = Math.floor(qty);
    }

    const notValidMessage = validateQtyEntered ? validateQtyEntered(qty, uom) : null;

    return qtyInfos.of({ qty, qtyStr: qtyInputString, notValidMessage });
  }
};

export default QtyInputField;
