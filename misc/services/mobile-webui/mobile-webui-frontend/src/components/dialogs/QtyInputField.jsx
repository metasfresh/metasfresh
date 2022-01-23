import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';

const QtyInputField = ({ qtyInitial, integerValuesOnly, validateQtyEntered, onQtyChange, isRequestFocus }) => {
  const [qtyInfo, setQtyInfo] = useState(
    computeQtyInfoFromString({
      qtyInputString: qtyInitial != null ? `${qtyInitial}` : '',
      integerValuesOnly,
      prevQtyInfo: null,
      validateQtyEntered,
    })
  );

  // Inform parent about initial value
  useEffect(() => forwardQtyInfoToParent(qtyInfo), []);

  const qtyInputRef = useRef(null);
  if (isRequestFocus) {
    useEffect(() => {
      qtyInputRef.current.focus();
      qtyInputRef.current.select();
    }, [isRequestFocus]);
  }

  const handleQtyEntered = (e) => {
    const qtyInputString = e.target.value ? e.target.value : '0';
    const newQtyInfo = computeQtyInfoFromString({
      qtyInputString,
      integerValuesOnly,
      prevQtyInfo: qtyInfo,
      validateQtyEntered,
    });
    console.log(`For e.target.value="${e.target.value}" computed: `, { qtyInputString, newQtyInfo });
    setQtyInfo(newQtyInfo);
    forwardQtyInfoToParent(newQtyInfo);
  };

  const forwardQtyInfoToParent = (qtyInfoToForward) => {
    onQtyChange(qtyInfoToForward.isQtyValid ? qtyInfoToForward.qty : null);
  };

  return (
    <div className="field">
      <p className="help is-danger">{qtyInfo.notValidMessage}&nbsp;</p>
      <div className="control has-icons-right">
        <input ref={qtyInputRef} className="input" type="number" value={qtyInfo.qtyStr} onChange={handleQtyEntered} />
        {!qtyInfo.isQtyValid && (
          <span className="icon is-small is-right">
            <i className="fas fa-exclamation-triangle" />
          </span>
        )}
      </div>
    </div>
  );
};

QtyInputField.propTypes = {
  qtyInitial: PropTypes.number,
  integerValuesOnly: PropTypes.bool,
  validateQtyEntered: PropTypes.func,
  isRequestFocus: PropTypes.bool,
  onQtyChange: PropTypes.func.isRequired,
};

const computeQtyInfoFromString = ({ qtyInputString, integerValuesOnly, prevQtyInfo, validateQtyEntered }) => {
  let qty = parseFloat(qtyInputString);

  if (isNaN(qty)) {
    return {
      qtyStr: qtyInputString,
      qty: prevQtyInfo?.qty ?? 0,
      isQtyValid: false,
      notValidMessage: prevQtyInfo?.notValidMessage ?? null, // preserve last notValidMessage
    };
  } else {
    if (integerValuesOnly) {
      qty = Math.floor(qty);
    }

    const notValidMessage = validateQtyEntered ? validateQtyEntered(qty) : null;
    return {
      qtyStr: `${qty}`,
      qty,
      isQtyValid: !notValidMessage,
      notValidMessage,
    };
  }
};

export default QtyInputField;
