import PropTypes from 'prop-types';
import React, { useCallback, useRef, useState } from 'react';
import { toastError, toastErrorFromObj } from '../utils/toast';
import { useIsSettingsLoaded, useNumber, usePositiveNumberSetting } from '../reducers/settings';
import { beep } from '../utils/audio';
import * as uiTrace from '../utils/ui_trace';
import Spinner from './Spinner';
import { MODE, useBarcodeScannerModes } from './BarcodeScanner/useBarcodeScannerModes';
import ManualModePanel from './BarcodeScanner/ManualModePanel';
import CameraModePanel from './BarcodeScanner/CameraModePanel';
import HardwareModePanel from './BarcodeScanner/HardwareModePanel';
import BarcodeScannerFooter from './BarcodeScanner/BarcodeScannerFooter';

const TOAST_ID = 'BarcodeScannerComponentError';

const BarcodeScannerComponent = ({
  testId,
  resolveScannedBarcode,
  onResolvedResult,
  inputPlaceholderText,
  invisible,
}) => {
  const { enabledModes, defaultMode, okBeepParams, errorBeepParams, scanDuplicatesIntervalMillis } = useConfigParams({
    invisible,
  });

  const [activeMode, setActiveMode] = useState(defaultMode);

  // Set true the moment the operator explicitly picks a mode (footer HW/CAM toggle, footer
  // "enter manually", or the ManualModePanel "back to scanner" button). Read by the async-settings
  // adoption below so a deliberate operator choice made during the settings-load window is never
  // overridden. NOT set by the internal auto-returns to the default mode (post-scan / camera
  // cancel) — those are not operator mode selections.
  const hasOperatorSelectedModeRef = useRef(false);
  const selectMode = useCallback((mode) => {
    hasOperatorSelectedModeRef.current = true;
    setActiveMode(mode);
  }, []);

  // ── Async-settings default adoption ─────────────────────────────────────────────────────────
  // Settings load asynchronously (ApplicationRoot fetches them fire-and-forget after login). If
  // this scanner mounts BEFORE they arrive, useBarcodeScannerModes returns its hook defaults so
  // defaultMode resolves to HARDWARE, and the useState initializer above freezes activeMode there
  // — it would NOT pick up the configured default once settings resolve (e.g. defaultMode=manual),
  // leaving the operator stuck in hardware mode with no visible manual input (flaky e2e case:
  // barcode_scanner_modes.spec.js "manual mode — visible editable input rendered…").
  //
  // Adjust the state DURING RENDER (React's documented "adjust state when an input changes"
  // pattern) rather than in a useEffect, so the switch happens before paint — on a handheld this
  // avoids a one-frame flash of the wrong-mode scanner UI. Guarded so it fires exactly once, on
  // the not-loaded→loaded transition.
  //
  // Adopt the configured default ONLY if the operator has NOT explicitly picked a mode during the
  // load window (the footer's "enter manually" / toggle buttons are live before settings load) —
  // otherwise a late settings arrival would silently revert the operator's own selection.
  const isSettingsLoaded = useIsSettingsLoaded();
  const [didAdoptSettingsDefault, setDidAdoptSettingsDefault] = useState(false);
  if (isSettingsLoaded && !didAdoptSettingsDefault) {
    setDidAdoptSettingsDefault(true);
    if (!hasOperatorSelectedModeRef.current && activeMode !== defaultMode) {
      setActiveMode(defaultMode);
    }
  }
  // ─────────────────────────────────────────────────────────────────────────────────────────────

  const scanningStatusRef = useRef({ running: false, done: false });
  const [isProcessing, setProcessing] = useState(false);
  const { trackDuplicateScan } = useDuplicateScansGuard({ scanDuplicatesIntervalMillis });

  const validateScannedBarcodeAndForward0 = async ({ scannedBarcode, onStart, onSuccess, onError, onFinally }) => {
    if (!scannedBarcode?.trim()) {
      uiTrace.traceLogWarn('Ignoring blank barcode', { scannedBarcode });
      return;
    }

    onStart?.();

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
        toastError({ plainMessage: resolvedResult.error, toastId: TOAST_ID });
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
      toastErrorFromObj(error, TOAST_ID);
      onError?.();
    } finally {
      scanningStatus.running = false;
      setProcessing(false);

      onFinally?.();
    }
  };
  const validateScannedBarcodeAndForward = uiTrace.traceFunction(
    validateScannedBarcodeAndForward0,
    ({ scannedBarcode, traceParams }) => ({
      ...traceParams,
      eventName: 'barcodeScanned',
      scannedBarcode,
      activeMode,
      scanDuplicatesIntervalMillis,
    })
  );

  return (
    <div className="barcode-scanner">
      {!invisible && isProcessing && <Spinner />}
      {/* HardwareModePanel is rendered in EVERY visible mode:
            HARDWARE → full scan-prompt UI + off-screen <input>
            MANUAL / CAMERA → invisible mode: off-screen <input> only, no visible chrome
          Two reasons:
            (1) keeps `#input-text` mounted across mode switches so DataWedge IME
                InputConnection survives — matches the E2E contract
                (barcode_scanner_modes.spec.js — `expectAttached({})` in MANUAL mode).
            (2) keeps the window-level useKeyboardBarcodeReader hook attached during
                CAMERA mode (workplaces with a plugged scanner can still scan into the
                camera view). The hook is `disabled` in MANUAL mode so keystrokes go
                straight to the visible manual input. */}
      <HardwareModePanel
        inputPlaceholderText={inputPlaceholderText}
        invisible={invisible || activeMode !== MODE.HARDWARE}
        isProcessing={isProcessing}
        disabled={isProcessing || activeMode === MODE.MANUAL}
        onBarcodeScanned={validateScannedBarcodeAndForward}
        testId={testId}
      />
      {!invisible && activeMode === MODE.MANUAL && (
        <ManualModePanel
          isProcessing={isProcessing}
          enabledModes={enabledModes}
          onModeSelected={selectMode}
          onBarcodeScanned={({ scannedBarcode, onSuccess, onError }) =>
            validateScannedBarcodeAndForward({
              scannedBarcode,
              onSuccess: () => {
                onSuccess?.();
                setActiveMode(defaultMode);
              },
              onError,
            })
          }
        />
      )}
      {!invisible && activeMode === MODE.CAMERA && (
        <CameraModePanel
          isProcessing={isProcessing}
          onBarcodeScanned={validateScannedBarcodeAndForward}
          onCancel={() => setActiveMode(defaultMode)}
        />
      )}
      {!invisible && (
        <BarcodeScannerFooter activeMode={activeMode} enabledModes={enabledModes} onModeSelected={selectMode} />
      )}
    </div>
  );
};

BarcodeScannerComponent.propTypes = {
  testId: PropTypes.string,
  resolveScannedBarcode: PropTypes.func,
  inputPlaceholderText: PropTypes.string,
  onResolvedResult: PropTypes.func.isRequired,
  invisible: PropTypes.bool,
};

BarcodeScannerComponent.defaultProps = {
  invisible: false,
};

export default BarcodeScannerComponent;

//
//
//
//
//

const useConfigParams = ({ invisible }) => {
  const { enabledModes, defaultMode } = useBarcodeScannerModes({ invisible });

  return {
    enabledModes,
    defaultMode,
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
    scanDuplicatesIntervalMillis: usePositiveNumberSetting('barcodeScanner.scanDuplicatesIntervalMillis', 0),
  };
};

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
