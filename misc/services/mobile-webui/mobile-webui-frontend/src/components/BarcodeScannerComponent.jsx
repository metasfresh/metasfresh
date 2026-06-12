import PropTypes from 'prop-types';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { BarcodeFormat, BrowserMultiFormatReader } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import { toastError, toastErrorFromObj } from '../utils/toast';
import { trl } from '../utils/translations';
import { useBooleanSetting, useNumber, usePositiveNumberSetting, useSetting } from '../reducers/settings';
import { debounce } from 'lodash';
import { beep } from '../utils/audio';
import * as uiTrace from '../utils/ui_trace';
import Spinner from './Spinner';
import ButtonWithIndicator from './buttons/ButtonWithIndicator';
import { useKeyboardBarcodeReader } from '../hooks/useKeyboardBarcodeReader';
import { useBarcodeScannerModes, MODE } from '../hooks/useBarcodeScannerModes';
import BarcodeScannerFooter from './BarcodeScannerFooter';

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
  BarcodeFormat.QR_CODE,
  BarcodeFormat.CODE_128,
  BarcodeFormat.ITF,
]);

const READER_OPTIONS = {
  delayBetweenScanSuccess: 2000,
  delayBetweenScanAttempts: 600,
};

const useConfigParams = () => {
  const { enabledModes, defaultMode } = useBarcodeScannerModes();

  // Hardware off-screen input HTML attributes — driven directly by per-mode knobs.
  // Two orthogonal keyboard-suppression knobs feed the scan <input>. They are named after their
  // mechanism (not their UX effect) so the two cannot be confused:
  //   • inputMode  → sets `inputMode` on the input (a HINT — soft. Honoured by recent Chrome/Android,
  //                  ignored by some firmware, e.g. Honeywell CT60 / Android 11).
  //   • readOnly   → sets the HTML `readOnly` attribute (a HARD GUARANTEE — the browser never
  //                  opens the soft keyboard on a readOnly input, AND manual typing is blocked).
  // Per the scanner-framework design (https://github.com/metasfresh/me03/issues/29246), readOnly is
  // controlled by per-mode knobs so the two modes tune independently. Default OFF ⇒ byte-for-byte
  // today's behaviour. DataWedge IME deployments MUST keep readOnly off — readOnly kills the
  // InputConnection.
  const hardwareInputMode = useSetting('barcodeScanner.mode.hardware.input.inputMode') ?? 'none';
  const hardwareInputReadOnly = useBooleanSetting('barcodeScanner.mode.hardware.input.readOnly', false);

  return {
    enabledModes,
    defaultMode,
    hardwareInputMode,
    hardwareInputReadOnly,
    okBeepParams: {
      name: 'OK',
      beepFrequency: useNumber('barcodeScanner.onSuccess.beep.frequency', 1000),
      beepVolume: useNumber('barcodeScanner.onSuccess.beep.volume', 0.1),
      beepDurationMillis: useNumber('barcodeScanner.onSuccess.beep.durationMillis', 100),
      vibrateMillis: useNumber('barcodeScanner.onSuccess.vibrate.durationMillis', 100),
    },
    errorBeepParams: {
      name: 'error',
      beepFrequency: useNumber('barcodeScanner.onError.beep.frequency', 100),
      beepVolume: useNumber('barcodeScanner.onError.beep.volume', 0.1),
      beepDurationMillis: useNumber('barcodeScanner.onError.beep.durationMillis', 100),
      vibrateMillis: useNumber('barcodeScanner.onError.vibrate.durationMillis', 100),
    },
    triggerOnChangeIfLengthGreaterThan: usePositiveNumberSetting(
      'barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan',
      0
    ),
    textChangedDebounceMillis: usePositiveNumberSetting('barcodeScanner.inputText.debounceMillis', 300),
    scanDuplicatesIntervalMillis: usePositiveNumberSetting('barcodeScanner.scanDuplicatesIntervalMillis', 0),
  };
};

const BarcodeScannerComponent = ({ testId, resolveScannedBarcode, onResolvedResult, inputPlaceholderText }) => {
  const {
    enabledModes,
    defaultMode,
    hardwareInputMode,
    hardwareInputReadOnly,
    okBeepParams,
    errorBeepParams,
    triggerOnChangeIfLengthGreaterThan,
    textChangedDebounceMillis,
    scanDuplicatesIntervalMillis,
  } = useConfigParams();

  const [activeMode, setActiveMode] = useState(defaultMode);

  const handleSelectManual = () => setActiveMode(MODE.MANUAL);
  const handleToggleHardwareCamera = () =>
    setActiveMode((prev) => (prev === MODE.HARDWARE ? MODE.CAMERA : MODE.HARDWARE));
  // Back to scanner from manual mode — go to hardware if enabled, otherwise camera.
  const handleBackToScanner = () => setActiveMode(enabledModes.hardware ? MODE.HARDWARE : MODE.CAMERA);

  const inputTextRef = useRef();
  const manualInputRef = useRef();
  const scanningStatusRef = useRef({ running: false, done: false });
  const [isProcessing, setProcessing] = useState(false);
  const { trackDuplicateScan } = useDuplicateScansGuard({ scanDuplicatesIntervalMillis });

  //
  // Video
  const mountedRef = useRef(true);
  const videoRef = useRef();
  useEffect(() => {
    mountedRef.current = true;

    if (activeMode === MODE.CAMERA) {
      const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
      codeReader.decodeFromVideoDevice(undefined, videoRef.current, (result, error, controls) => {
        if (mountedRef.current === false) {
          controls.stop();
        } else if (typeof result !== 'undefined') {
          validateScannedBarcodeAndForward({ scannedBarcode: result.text, controls });
        }
      });
    }

    return () => {
      mountedRef.current = false;
    };
  }, [activeMode]);

  useEffect(() => {
    return () => handleInputTextChangedDebounced.cancel();
  });

  useEffect(
    () => {
      if (activeMode === MODE.CAMERA) {
        videoRef?.current?.scrollIntoView({ behaviour: 'smooth', block: 'center', inline: 'end' });
      }
      if (hardwareInputMode !== 'none' && !hardwareInputReadOnly) {
        inputTextRef?.current?.focus();
      }
    } /* no deps, call it on each render */
  );

  // DataWedge IME needs a focused editable input to establish InputConnection.
  // Focus once on mount; the window-level hook handles all subsequent scan events.
  useEffect(() => {
    if (hardwareInputMode === 'none') {
      inputTextRef?.current?.focus();
    }
  }, []);

  // Autofocus the visible manual input whenever MANUAL mode becomes active.
  useEffect(() => {
    if (activeMode === MODE.MANUAL) {
      manualInputRef?.current?.focus();
    }
  }, [activeMode]);

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
      validateScannedBarcodeAndForward({ scannedBarcode: barcode });
    },
    onReadInProgress: (barcode) => {
      // console.log('onReadInProgress', barcode);
      if (inputTextRef?.current) {
        inputTextRef.current.value = barcode;
      }
    },
    rateMs: textChangedDebounceMillis,
    minLength: triggerOnChangeIfLengthGreaterThan,
    // Disable the window-level keyboard reader while in MANUAL mode so that
    // keystrokes go to the visible manual input field instead of being captured
    // by the hook (which would double-process each character).
    disabled: isProcessing || activeMode === MODE.MANUAL,
  });

  const validateScannedBarcodeAndForward0 = async ({ scannedBarcode, onSuccess, onError }) => {
    if (!scannedBarcode?.trim()) {
      uiTrace.traceLogWarn('Ignoring blank barcode', { scannedBarcode });
      return;
    }
    inputTextRef?.current?.select();

    const scanningStatus = scanningStatusRef.current;
    if (scanningStatus.running || scanningStatus.done) {
      uiTrace.putContext({ isIgnored: true, ignoreReason: `scanning is already running or done` });
      console.log('Ignore scanned barcode because we are already running or done', { scannedBarcode, scanningStatus });
      return;
    }

    scanningStatus.running = true;
    setProcessing(true);

    // console.log('Resolving scanned barcode', {
    //   scannedBarcode,
    //   resolveScannedBarcode,
    //   onResolvedResult,
    //   scanningStatus: { ...scanningStatus },
    // });

    try {
      if (trackDuplicateScan({ scannedBarcode })) {
        beep(errorBeepParams);
        uiTrace.putContext({ isIgnored: true, ignoreReason: 'duplicate' });
        console.log('Ignore scanned barcode because it is a duplicate', { scannedBarcode });
        return;
      }

      let resolvedResult;
      if (resolveScannedBarcode) {
        resolvedResult = await resolveScannedBarcode({ scannedBarcode });
      } else {
        resolvedResult = { scannedBarcode, error: null };
      }
      console.debug('Got resolvedResult', resolvedResult);

      if (resolvedResult.error) {
        toastError({ plainMessage: resolvedResult.error });
        beep(errorBeepParams);
        scanningStatus.done = false; // not done yet
        onError?.();
      } else {
        await onResolvedResult(resolvedResult);
        beep(okBeepParams);
        onSuccess?.();
      }
    } catch (error) {
      beep(errorBeepParams);
      toastErrorFromObj(error);
      onError?.();
    } finally {
      scanningStatus.running = false;
      setProcessing(false);

      if (inputTextRef?.current) {
        inputTextRef.current.value = '';
      }
    }
  };
  const validateScannedBarcodeAndForward = uiTrace.traceFunction(
    validateScannedBarcodeAndForward0,
    ({ scannedBarcode }) => ({
      eventName: 'barcodeScanned',
      scannedBarcode,
      activeMode,
      hardwareInputMode,
      hardwareInputReadOnly,
      triggerOnChangeIfLengthGreaterThan,
      textChangedDebounceMillis,
      scanDuplicatesIntervalMillis,
    })
  );

  const handleInputTextChanged = (e) => {
    const scannedBarcode = e.target.value;

    if (
      scannedBarcode &&
      triggerOnChangeIfLengthGreaterThan &&
      triggerOnChangeIfLengthGreaterThan > 0 &&
      scannedBarcode.length >= triggerOnChangeIfLengthGreaterThan
    ) {
      validateScannedBarcodeAndForward({ scannedBarcode });
    }
  };
  const handleInputTextChangedDebounced = useMemo(() => {
    return debounce(handleInputTextChanged, textChangedDebounceMillis);
  }, [textChangedDebounceMillis]);

  const handleInputTextKeyPress = (e) => {
    if (e.key === 'Enter') {
      const scannedBarcode = e.target.value?.trim();
      if (!scannedBarcode) return;

      validateScannedBarcodeAndForward({ scannedBarcode });
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

  // Manual entry mode: submit the typed value, auto-return to default mode on success,
  // or keep the text and select it on error so the user can correct and retry.
  const handleManualSubmit = () => {
    const scannedBarcode = manualInputRef?.current?.value?.trim();
    if (!scannedBarcode) return;
    validateScannedBarcodeAndForward({
      scannedBarcode,
      onSuccess: () => {
        if (manualInputRef?.current) {
          manualInputRef.current.value = '';
        }
        setActiveMode(defaultMode);
      },
      onError: () => {
        manualInputRef?.current?.select();
      },
    });
  };

  const handleManualKeyUp = (e) => {
    if (e.key === 'Enter') {
      handleManualSubmit();
    }
  };

  return (
    <div className="barcode-scanner">
      {isProcessing && <Spinner />}
      {/* Scan prompt — visible on hardware-scanner deployments (no camera) when the input is
          OFF-SCREEN (the actual empty-screen case: the only thing the component otherwise renders
          is the invisible off-screen input). Reuses inputPlaceholderText so
          a caller can override the default caption (e.g. HUScanner's locator-scan branch passes
          'Scan LU or locator…'); when the caller passes none, the default scanPrompt translation
          is used. See https://github.com/metasfresh/me03/issues/30363. */}
      {!isProcessing && activeMode !== MODE.CAMERA && activeMode !== MODE.MANUAL && (
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
      {/* IMPORTANT: Always use type="text" — never type="hidden".
          The input is always visually hidden via CSS (input-text-offscreen) instead of
          type="hidden". This is critical for Zebra MC3300x DataWedge IME mode:
          type="hidden" inputs cannot receive focus, so Android InputConnection is never established
          and DataWedge text injection silently fails. CSS hiding keeps the input focusable and
          IME-compatible while remaining invisible to the user.
          (https://github.com/metasfresh/me03/issues/28834) */}
      {/* NOTE: Input is rendered BEFORE video to avoid Android 11 WebView SurfaceView
          compositing issue where the native video layer covers CSS-overlaid content.
          (https://github.com/metasfresh/me03/issues/28964) */}
      {/* ⚠️ HARDWARE CONTRACT — TWO device classes; each has its own #input-text attribute combo.
          Any edit to type / inputMode / readOnly / the focus useEffects MUST preserve BOTH.

                                  │ DataWedge IME             │ Keystroke-wedge
                                  │ (e.g. Zebra MC3300x)      │ (e.g. Honeywell CT60 / Android 11)
          ────────────────────────┼───────────────────────────┼──────────────────────────────────────
          type                    │ "text"                    │ "text"
          inputMode               │ "none"                    │ "none" (ignored on Android 11)
          readOnly                │ ABSENT (else kills the    │ PRESENT (load-bearing keyboard
                                  │ InputConnection — text    │ suppression — Android 11 ignores
                                  │ injection silently fails) │ inputMode="none")
          Focus useEffects        │ Mount-time focus()        │ Per-render skipped (readOnly guard
                                  │ establishes IME           │ prevents keyboard trigger)
          ────────────────────────┼───────────────────────────┼──────────────────────────────────────
          Sysconfig — hardware    │ mode.hardware.input.      │ mode.hardware.input.
                                  │   readOnly=N              │   readOnly=Y

          The `readOnly` attribute is driven by `barcodeScanner.mode.hardware.input.readOnly`
          (default false) and `inputMode` by `barcodeScanner.mode.hardware.input.inputMode`
          (default "none") per the framework design (https://github.com/metasfresh/me03/issues/29246).
          DataWedge IME requires readOnly ABSENT or InputConnection breaks
          (https://github.com/metasfresh/me03/issues/28834). Keystroke-wedge devices that ignore
          inputMode="none" require readOnly PRESENT — first deployed for Honeywell CT60 /
          Android 11.

          Regression guards (BOTH must stay green — `e2e/mobile-webui/tests/spec/barcode_scanner_modes.spec.js`):
            • DataWedge IME:        "#input-text HTML: type=text, inputMode=none, readOnly absent, CSS-hidden"
            • CT60 keystroke-wedge: "Honeywell CT60 keystroke-wedge mode — input is off-screen + readOnly, no camera"

          Do NOT relax either test to make a change land. A red test means the CODE broke a
          contract — fix the code, not the test. Any change here MUST be re-validated on physical
          hardware for BOTH device classes (e2e/mobile-webui/CLAUDE.md → "Manual Hardware Test Rule"). */}
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
      {/* Manual entry mode — visible editable input + submit button.
          Rendered only in MANUAL mode; the off-screen hardware input stays mounted
          below to preserve DataWedge IME InputConnection on mode switches. */}
      {activeMode === MODE.MANUAL && !isProcessing && (
        <div className="manual-entry">
          <input
            ref={manualInputRef}
            className="input-text manual-entry__input"
            type="text"
            inputMode="text"
            placeholder={trl('components.BarcodeScannerComponent.manualInputPlaceholder')}
            onKeyUp={handleManualKeyUp}
            data-testid="manual-entry-input"
          />
          <ButtonWithIndicator
            captionKey="components.BarcodeScannerComponent.manualInputSubmit"
            typeFASIconName="fa-check"
            additionalCssClass="manual-entry__submit"
            onClick={handleManualSubmit}
            testId="manual-entry-submit"
          />
        </div>
      )}
      {activeMode === MODE.CAMERA && <video key="video" ref={videoRef} width="100%" height="100%" />}
      <BarcodeScannerFooter
        activeMode={activeMode}
        enabledModes={enabledModes}
        onSelectManual={handleSelectManual}
        onToggleHardwareCamera={handleToggleHardwareCamera}
        onBackToScanner={handleBackToScanner}
      />
    </div>
  );
};

BarcodeScannerComponent.propTypes = {
  testId: PropTypes.string,
  resolveScannedBarcode: PropTypes.func,
  inputPlaceholderText: PropTypes.string,
  onResolvedResult: PropTypes.func.isRequired,
};

export default BarcodeScannerComponent;

//
//
//
//
//

const useDuplicateScansGuard = ({ scanDuplicatesIntervalMillis }) => {
  const lastScanRef = useRef(null);
  // console.log('useDuplicateScansGuard', { lastScan: lastScanRef.current, scanDuplicatesIntervalMillis });

  const trackDuplicateScan = ({ scannedBarcode }) => {
    const lastScan = lastScanRef.current;
    const thisScan = { scannedBarcode, timestamp: Date.now() };
    const isDuplicateScan =
      scanDuplicatesIntervalMillis > 0 &&
      lastScan &&
      lastScan.scannedBarcode === thisScan.scannedBarcode &&
      thisScan.timestamp - lastScan.timestamp < scanDuplicatesIntervalMillis;

    if (isDuplicateScan) {
      uiTrace.putContext({ duplicateIntervalMillis: thisScan.timestamp - lastScan?.timestamp });
    }

    lastScanRef.current = thisScan;

    return isDuplicateScan;
  };

  return {
    trackDuplicateScan,
  };
};
