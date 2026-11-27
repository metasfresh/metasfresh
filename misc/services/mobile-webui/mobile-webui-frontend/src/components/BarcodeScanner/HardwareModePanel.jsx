import React, { useEffect, useMemo, useRef } from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../utils/translations';
import { useBooleanSetting, usePositiveNumberSetting, useSetting } from '../../reducers/settings';
import { useKeyboardBarcodeReader } from '../../hooks/useKeyboardBarcodeReader';
import { debounce } from 'lodash';

const useHardwareConfigParams = () => {
  const hardwareInputMode = useSetting('barcodeScanner.mode.hardware.input.inputMode') ?? 'none';
  const isHardwareInputReadOnly = useBooleanSetting('barcodeScanner.mode.hardware.input.readOnly', false);

  return {
    hardwareInputMode,
    isHardwareInputReadOnly,
    triggerOnChangeIfLengthGreaterThan: usePositiveNumberSetting(
      'barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan',
      0
    ),
    textChangedDebounceMillis: usePositiveNumberSetting('barcodeScanner.inputText.debounceMillis', 300),
    idleAbandonMillis: usePositiveNumberSetting('barcodeScanner.inputText.idleAbandonMillis', 15000),
  };
};

const isEditableElement = (element) =>
  !!element && (['INPUT', 'TEXTAREA', 'SELECT'].includes(element.tagName) || element.isContentEditable === true);

// Next to a field the operator types into, the scanner input must not take the focus away from that field.
// While the operator is not typing, it keeps the focus: in IME-style configs the OS commits the scanned text into the focused element.
const isOperatorTypingElsewhere = (inputElement) =>
  isEditableElement(document.activeElement) && document.activeElement !== inputElement;

/**
 * The scanner's input reacts to Enter on key-up (see handleInputTextKeyPress). Its Enter must never also submit a form the
 * input sits in: the hidden scanner of the quantity block is in the same form as the OK button, and a scan would book the quantity.
 */
const preventEnterFromSubmittingForm = (event) => {
  if (event.key === 'Enter') {
    event.preventDefault();
  }
};

const HardwareModePanel = ({
  invisible,
  inputPlaceholderText,
  isProcessing,
  disabled,
  isCoexistingWithTypedInput,
  onBarcodeScanned,
  testId,
}) => {
  const {
    hardwareInputMode,
    isHardwareInputReadOnly,
    triggerOnChangeIfLengthGreaterThan,
    textChangedDebounceMillis,
    idleAbandonMillis,
  } = useHardwareConfigParams();
  const inputTextRef = useRef();
  // Tracks the LIVE `disabled` prop so the 2s blur-refocus setTimeout below can check the
  // current value at fire time, not the stale closure value at schedule time. Without this,
  // a HW→MANUAL switch right after an offscreen-blur would silently re-grab focus 2s later
  // and yank it from the visible manual input.
  const disabledRef = useRef(disabled);
  useEffect(() => {
    disabledRef.current = disabled;
  }, [disabled]);
  // Same live-value reason: the scanner must not pull the focus out of a field the operator types into
  const isCoexistingWithTypedInputRef = useRef(isCoexistingWithTypedInput);
  useEffect(() => {
    isCoexistingWithTypedInputRef.current = isCoexistingWithTypedInput;
  }, [isCoexistingWithTypedInput]);
  // the scanner leaves the focus where the operator types (reads the live values, so it is also right in a timer)
  const isFocusLeftToTypedField = () =>
    isCoexistingWithTypedInputRef.current && isOperatorTypingElsewhere(inputTextRef.current);

  // Refocus the offscreen <input> after every render — necessary for IME-style configs (the
  // input is editable, OS expects focus to route InputConnection). Skipped for keystroke-wedge
  // configs (readOnly + inputMode=none) which capture at window level and don't need focus.
  // Always skipped when `disabled` so the visible ManualModePanel input keeps its focus.
  useEffect(() => {
    if (disabled || isFocusLeftToTypedField()) return;
    if (hardwareInputMode !== 'none' && !isHardwareInputReadOnly) {
      inputTextRef?.current?.focus();
    }
  });

  // Mount-time focus for DataWedge IME (inputMode=none) — establishes InputConnection once;
  // the window-level hook handles subsequent scans.
  useEffect(() => {
    if (disabled || isFocusLeftToTypedField()) return;
    // next to a typed-in field only an IME config (editable input) needs the focus; a read-only input is read at window level
    if (hardwareInputMode === 'none' && !(isCoexistingWithTypedInput && isHardwareInputReadOnly)) {
      inputTextRef?.current?.focus();
    }
  }, []);

  // Hardware-specific telemetry forwarded to the parent's uiTrace context on every
  // onBarcodeScanned call. BSC owns the canonical fields (eventName, scannedBarcode,
  // activeMode, scanDuplicatesIntervalMillis); this panel adds the hardware-input
  // attributes + buffering knobs that the parent doesn't know (and shouldn't read).
  const traceParams = {
    hardwareInputMode,
    isHardwareInputReadOnly,
    triggerOnChangeIfLengthGreaterThan,
    textChangedDebounceMillis,
    idleAbandonMillis,
  };

  const handleInputTextChanged = (e) => {
    const scannedBarcode = e.target.value;

    if (
      scannedBarcode &&
      triggerOnChangeIfLengthGreaterThan &&
      triggerOnChangeIfLengthGreaterThan > 0 &&
      scannedBarcode.length >= triggerOnChangeIfLengthGreaterThan
    ) {
      onBarcodeScanned({ scannedBarcode, traceParams });
    }
  };
  const handleInputTextChangedDebounced = useMemo(() => {
    return debounce(handleInputTextChanged, textChangedDebounceMillis);
  }, [textChangedDebounceMillis]);

  useEffect(() => {
    return () => handleInputTextChangedDebounced.cancel();
  }, [handleInputTextChangedDebounced]);

  const { isReadInProgress } = useKeyboardBarcodeReader({
    onReadDone: (barcode, scanStats) => {
      // Nothing to clear in the input: the scan comes from the hook's buffer, its keystrokes are kept out of
      // the input, and the parent's processing state unmounts the input for every forwarded scan.
      onBarcodeScanned({ scannedBarcode: barcode, traceParams: { ...traceParams, ...scanStats } });
    },
    rateMs: textChangedDebounceMillis,
    minLength: triggerOnChangeIfLengthGreaterThan,
    idleAbandonMs: idleAbandonMillis,
    // Parent owns the disabled decision (see BarcodeScannerComponent — combines isProcessing
    // with `activeMode === MANUAL` so keystrokes flow to the visible manual input instead
    // of the offscreen hardware one).
    disabled,
    // Next to a field the operator types into: only scanner-speed bursts from that field are scans
    isTypedInputTarget: isCoexistingWithTypedInput
      ? (target) => target !== inputTextRef.current && isEditableElement(target)
      : undefined,
  });

  const handleInputTextKeyPress = (e) => {
    if (e.key === 'Enter') {
      const scannedBarcode = e.target.value?.trim();
      if (!scannedBarcode) return;

      onBarcodeScanned({
        scannedBarcode,
        traceParams,
        onStart: () => {
          inputTextRef?.current?.select();
        },
        onFinally: () => {
          if (inputTextRef?.current) {
            inputTextRef.current.value = '';
          }
        },
      });
    }
  };

  const handleInputTextFocus = () => {
    inputTextRef?.current?.select();
  };

  const handleInputTextBlur = () => {
    setTimeout(() => {
      // Check the LIVE `disabled` value via ref — the prop at schedule time may be stale 2s
      // later. If the user switched into MANUAL during the window, we must NOT yank focus
      // back from the visible manual input.
      if (disabledRef.current) return;
      if (isFocusLeftToTypedField()) return;
      inputTextRef?.current?.focus();
    }, 2000);
  };

  // The off-screen input is the only DOM element needed for both the visible hardware-mode
  // scan-prompt UI and the invisible variant (BarcodeScannerButton, ApplicationsListScreen,
  // DistributionMoveActivity). Render it once; conditionally surround with the scan-prompt
  // chrome when not invisible. When invisible, return ONLY the off-screen input so the
  // component contributes zero visible layout — no padding, no background, no animated icon.
  const offscreenInput = !isProcessing && (
    <input
      id="input-text"
      key="input-text"
      ref={inputTextRef}
      className="input-text input-text-offscreen"
      type="text"
      autoComplete="off"
      autoCorrect="off"
      autoCapitalize="none"
      spellCheck="false"
      placeholder={inputPlaceholderText || trl('components.BarcodeScannerComponent.scanTextPlaceholder')}
      inputMode={hardwareInputMode}
      readOnly={isHardwareInputReadOnly}
      onFocus={handleInputTextFocus}
      onBlur={handleInputTextBlur}
      onChange={handleInputTextChangedDebounced}
      onKeyDown={preventEnterFromSubmittingForm}
      onKeyUp={handleInputTextKeyPress}
      data-testid={testId ?? 'qrCode-input'}
    />
  );

  if (invisible) {
    return offscreenInput || null;
  }

  return (
    <div className={`hardware-mode-panel scan-prompt${isReadInProgress ? ' scan-in-progress' : ''}`}>
      <i className="fas fa-barcode scan-prompt-icon" aria-hidden="true" />
      {/* Caption swap — idle text by default, "Scanning in progress…" while the reader hook has a scan
          in progress (.scan-in-progress, see BarcodeScannerComponent.scss). Deliberately not derived
          from the input's content: writing scanned chars into the input costs a style recalc + layout
          per keystroke on the handheld. */}
      <div className="scan-prompt-text">
        <span className="scan-prompt-text-idle">
          {inputPlaceholderText || trl('components.BarcodeScannerComponent.scanPrompt')}
        </span>
        <span className="scan-prompt-text-progress">{trl('components.BarcodeScannerComponent.scanInProgress')}</span>
      </div>
      {offscreenInput}
    </div>
  );
};
HardwareModePanel.propTypes = {
  invisible: PropTypes.bool,
  inputPlaceholderText: PropTypes.string,
  isProcessing: PropTypes.bool,
  disabled: PropTypes.bool,
  /** the scanner sits next to a field the operator types into */
  isCoexistingWithTypedInput: PropTypes.bool,
  onBarcodeScanned: PropTypes.func.isRequired,
  testId: PropTypes.string,
};
HardwareModePanel.defaultProps = {
  invisible: false,
  disabled: false,
  isCoexistingWithTypedInput: false,
};

export default HardwareModePanel;
