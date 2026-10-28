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
  }, [initialString, integerValuesOnly, validateQtyEntered, uom]);

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

  // Replace-on-type for a PREFILLED field. A type="number" input replaced its value when the user typed; the
  // type="text" input needed for locale-aware decimals would instead append, silently inflating a prefilled
  // field (e.g. generateHUQRCodes numberOfHUs="25" + "1" -> "125", a wrong count). So on the FIRST insertion
  // since (re)focusing we select the whole value, and the inserted character then replaces it. Selecting
  // right before the insert (never a deferred/macrotask select, which raced the keystroke under CI load)
  // keeps it race-free. We trigger from BOTH keydown (physical keys, fires first) AND beforeinput (fires for
  // EVERY insertion incl. Android soft keyboards / IME, which report keydown as key='Unidentified'), so
  // replace-on-type does not depend on the device reporting an identifiable key. "Pristine" = the field still
  // shows a value the user has not edited since (re)focusing it; handleFocus arms it, the first insertion
  // consumes it.
  const pristineSinceFocusRef = useRef(true);

  const handleFocus = () => {
    pristineSinceFocusRef.current = true;
  };

  const selectPristinePrefill = (inputEl) => {
    if (pristineSinceFocusRef.current) {
      inputEl.select();
      pristineSinceFocusRef.current = false;
    }
  };

  const handleBeforeInput = (e) => {
    // block keystrokes the decimal parser would reject (desktop parity: CostPrice.handleBeforeInput)
    if (!isAllowedDecimalInput(e.data)) {
      e.preventDefault();
      return;
    }
    // e.data present = an actual text insertion (not deletion/navigation). This is the device-independent
    // replace-on-type trigger: for a physical key, keydown fired first and already consumed `pristine`
    // (no-op here); for IME / soft keyboards where keydown had no usable key, this is what selects+replaces.
    if (e.data) {
      selectPristinePrefill(e.currentTarget);
    }
  };

  const handleKeyDown = (e) => {
    // physical character key: select synchronously before the browser inserts it (keydown fires before
    // beforeinput, so this wins for a hardware keyboard; IME keydowns carry key='Unidentified' and fall
    // through to handleBeforeInput)
    if (e.key && e.key.length === 1) {
      selectPristinePrefill(e.currentTarget);
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
          // suppress Android/iOS text processing (autofill, autocorrect, autocapitalise) that can
          // mutate a typed number mid-entry on a handheld -- same OS-interference class as the
          // barcode-input attributes (see this module's CLAUDE.md)
          autoComplete="off"
          autoCorrect="off"
          autoCapitalize="none"
          spellCheck="false"
          value={rawText}
          disabled={!!readonly}
          onBeforeInput={handleBeforeInput}
          onChange={handleQtyEntered}
          onFocus={handleFocus}
          onKeyDown={handleKeyDown}
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
