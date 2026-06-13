import React, { useEffect, useMemo, useRef } from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../utils/translations';
import { useBooleanSetting, usePositiveNumberSetting, useSetting } from '../../reducers/settings';
import { useKeyboardBarcodeReader } from '../../hooks/useKeyboardBarcodeReader';
import { debounce } from 'lodash';

const useHardwareConfigParams = () => {
  const hardwareInputMode = useSetting('barcodeScanner.mode.hardware.input.inputMode') ?? 'none';
  const hardwareInputReadOnly = useBooleanSetting('barcodeScanner.mode.hardware.input.readOnly', false);

  return {
    hardwareInputMode,
    hardwareInputReadOnly,
    triggerOnChangeIfLengthGreaterThan: usePositiveNumberSetting(
      'barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan',
      0
    ),
    textChangedDebounceMillis: usePositiveNumberSetting('barcodeScanner.inputText.debounceMillis', 300),
  };
};

const HardwareModePanel = ({ inputPlaceholderText, invisible, isProcessing, onBarcodeScanned, testId }) => {
  const { hardwareInputMode, hardwareInputReadOnly, triggerOnChangeIfLengthGreaterThan, textChangedDebounceMillis } =
    useHardwareConfigParams();
  const inputTextRef = useRef();

  useEffect(
    () => {
      if (hardwareInputMode !== 'none' && !hardwareInputReadOnly) {
        inputTextRef?.current?.focus();
      }
    } /* no deps, call it on each render */
  );

  useEffect(() => {
    return () => handleInputTextChangedDebounced.cancel();
  });

  // DataWedge IME needs a focused editable input to establish InputConnection.
  // Focus once on mount; the window-level hook handles all subsequent scan events.
  useEffect(() => {
    if (hardwareInputMode === 'none') {
      inputTextRef?.current?.focus();
    }
  }, []);

  useKeyboardBarcodeReader({
    onReadDone: (barcode) => {
      // console.log('onReadDone', barcode);
      // Clear the input BEFORE calling validateScannedBarcodeAndForward.
      // validateScannedBarcodeAndForward calls setProcessing(true), which in React 17 legacy
      // mode (outside a React event handler) triggers a synchronous re-render that unmounts
      // the input ({!isProcessing && <input/>}) and nulls inputTextRef.current.  If we clear
      // after the call, inputTextRef.current is already null and the clear is silently skipped.
      // The un-cleared input value then reaches handleInputTextKeyPress via the keyup event
      // that follows the Enter keydown, causing a second validateScannedBarcodeAndForward
      // invocation and a duplicate error toast.
      if (inputTextRef?.current) {
        inputTextRef.current.value = '';
      }
      onBarcodeScanned({ scannedBarcode: barcode });
    },
    onReadInProgress: (barcode) => {
      // console.log('onReadInProgress', barcode);
      if (inputTextRef?.current) {
        inputTextRef.current.value = barcode;
      }
    },
    rateMs: textChangedDebounceMillis,
    minLength: triggerOnChangeIfLengthGreaterThan,
    disabled: isProcessing,
  });

  const handleInputTextKeyPress = (e) => {
    if (e.key === 'Enter') {
      const scannedBarcode = e.target.value?.trim();
      if (!scannedBarcode) return;

      onBarcodeScanned({
        scannedBarcode,
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
      inputTextRef?.current?.focus();
    }, 2000);
  };

  const handleInputTextChanged = (e) => {
    const scannedBarcode = e.target.value;

    if (
      scannedBarcode &&
      triggerOnChangeIfLengthGreaterThan &&
      triggerOnChangeIfLengthGreaterThan > 0 &&
      scannedBarcode.length >= triggerOnChangeIfLengthGreaterThan
    ) {
      onBarcodeScanned({ scannedBarcode });
    }
  };
  const handleInputTextChangedDebounced = useMemo(() => {
    return debounce(handleInputTextChanged, textChangedDebounceMillis);
  }, [textChangedDebounceMillis]);

  return (
    <>
      {!invisible && (
        <div className="scan-prompt">
          <i className="fas fa-barcode scan-prompt-icon" aria-hidden="true" />
          {/* Caption swap — idle text by default, "Scanning in progress…" while the input
              has content (mid-burst). CSS-only via :has() — see BarcodeScannerComponent.scss. */}
          <div className="scan-prompt-text">
            <span className="scan-prompt-text-idle">
              {inputPlaceholderText || trl('components.BarcodeScannerComponent.scanPrompt')}
            </span>
            <span className="scan-prompt-text-progress">
              {trl('components.BarcodeScannerComponent.scanInProgress')}
            </span>
          </div>
        </div>
      )}
      {!isProcessing && (
        <input
          id="input-text"
          key="input-text"
          ref={inputTextRef}
          className="input-text input-text-offscreen"
          type="text"
          placeholder={inputPlaceholderText || trl('components.BarcodeScannerComponent.scanTextPlaceholder')}
          inputMode={hardwareInputMode}
          readOnly={hardwareInputReadOnly}
          onFocus={handleInputTextFocus}
          onBlur={handleInputTextBlur}
          onChange={handleInputTextChangedDebounced}
          onKeyUp={handleInputTextKeyPress}
          data-testid={testId ?? 'qrCode-input'}
        />
      )}
    </>
  );
};
HardwareModePanel.propTypes = {
  inputPlaceholderText: PropTypes.string,
  invisible: PropTypes.bool,
  isProcessing: PropTypes.bool,
  onBarcodeScanned: PropTypes.func.isRequired,
  testId: PropTypes.string,
};

export default HardwareModePanel;
