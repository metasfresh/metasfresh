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

const HardwareModePanel = ({ invisible, inputPlaceholderText, isProcessing, disabled, onBarcodeScanned, testId }) => {
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

  // Refocus the offscreen <input> after every render — necessary for IME-style configs (the
  // input is editable, OS expects focus to route InputConnection). Skipped for keystroke-wedge
  // configs (readOnly + inputMode=none) which capture at window level and don't need focus.
  // Always skipped when `disabled` so the visible ManualModePanel input keeps its focus.
  useEffect(() => {
    if (disabled) return;
    if (hardwareInputMode !== 'none' && !isHardwareInputReadOnly) {
      inputTextRef?.current?.focus();
    }
  });

  // Mount-time focus for DataWedge IME (inputMode=none) — establishes InputConnection once;
  // the window-level hook handles subsequent scans.
  useEffect(() => {
    if (disabled) return;
    if (hardwareInputMode === 'none') {
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

  useKeyboardBarcodeReader({
    onReadDone: (barcode) => {
      // console.log('onReadDone', barcode);
      // Clear the input BEFORE calling onBarcodeScanned.
      // onBarcodeScanned triggers setProcessing(true) in the parent, which in React 17 legacy
      // mode (outside a React event handler) re-renders synchronously and unmounts the input
      // ({!isProcessing && <input/>}), nulling inputTextRef.current. Clearing AFTER the call
      // would be silently skipped and the un-cleared value could reach handleInputTextKeyPress
      // via the trailing keyup event, double-firing the scan.
      if (inputTextRef?.current) {
        inputTextRef.current.value = '';
      }
      onBarcodeScanned({ scannedBarcode: barcode, traceParams });
    },
    onReadInProgress: (barcode) => {
      // console.log('onReadInProgress', barcode);
      if (inputTextRef?.current) {
        inputTextRef.current.value = barcode;
      }
    },
    rateMs: textChangedDebounceMillis,
    minLength: triggerOnChangeIfLengthGreaterThan,
    idleAbandonMs: idleAbandonMillis,
    // Parent owns the disabled decision (see BarcodeScannerComponent — combines isProcessing
    // with `activeMode === MANUAL` so keystrokes flow to the visible manual input instead
    // of the offscreen hardware one).
    disabled,
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
      onKeyUp={handleInputTextKeyPress}
      data-testid={testId ?? 'qrCode-input'}
    />
  );

  if (invisible) {
    return offscreenInput || null;
  }

  return (
    <div className="hardware-mode-panel scan-prompt">
      {/* FontAwesome SVG-with-JS (src/index.js → @fortawesome/fontawesome-free/js/all.min) mutates
          <i className="fas …"> into <svg> in place. React's fiber keeps a stale stateNode pointer
          to the detached <i>; if the conditional <input> below were ever a sibling needing
          insertBefore against the icon, React would throw NotFoundError. Wrapping in <span>
          (codebase convention — see ButtonWithIndicator.jsx) gives React a stable, React-owned
          parent that FA never touches. */}
      <span>
        <i className="fas fa-barcode scan-prompt-icon" aria-hidden="true" />
      </span>
      {/* Caption swap — idle text by default, "Scanning in progress…" while the input has
          content (mid-burst). CSS-only via :has() — see BarcodeScannerComponent.scss. */}
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
  onBarcodeScanned: PropTypes.func.isRequired,
  testId: PropTypes.string,
};
HardwareModePanel.defaultProps = {
  invisible: false,
  disabled: false,
};

export default HardwareModePanel;
