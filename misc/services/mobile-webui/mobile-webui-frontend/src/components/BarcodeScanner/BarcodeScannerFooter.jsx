import React from 'react';
import PropTypes from 'prop-types';
import { MODE, PRIORITY } from './useBarcodeScannerModes';
import ButtonWithIndicator from '../buttons/ButtonWithIndicator';

// "Back to scanner" — when in MANUAL, the button targets the highest-priority scanner mode
// that is currently enabled. HARDWARE wins over CAMERA (per PRIORITY in useBarcodeScannerModes).
// If neither scanner mode is enabled the button is hidden — there's nowhere to go back to.
const SCANNER_MODES = PRIORITY.filter((m) => m === MODE.HARDWARE || m === MODE.CAMERA);

const BACK_TO_SCANNER_CAPTION_KEY = {
  [MODE.HARDWARE]: 'components.BarcodeScannerComponent.useHardwareScanner',
  [MODE.CAMERA]: 'components.BarcodeScannerComponent.scanWithCamera',
};
const BACK_TO_SCANNER_ICON = {
  [MODE.HARDWARE]: 'fa-barcode',
  [MODE.CAMERA]: 'fa-camera',
};

/*
 * Footer button cluster. Renders the mode-switching buttons applicable to the current state
 * and signals the parent via ONE callback: onModeSelected(targetMode).
 *
 * Each button resolves its own target mode locally — the footer is the source of truth for
 * "which mode does this button switch to":
 *   - HW⇄CAM toggle  → the opposite scanner mode of the current activeMode
 *   - Back-to-scanner → first enabled of HARDWARE|CAMERA (PRIORITY order)
 *   - Enter manually → MODE.MANUAL
 *
 * The parent's only job is to apply the new mode (typically `onModeSelected={setActiveMode}`).
 */
const BarcodeScannerFooter = ({ activeMode, enabledModes, onModeSelected }) => {
  const isManualEnabled = !!enabledModes?.manual;

  // HW↔Camera toggle only when BOTH scanner modes are enabled AND the user is currently in a
  // scanner mode. Hidden in MANUAL — the dedicated back-to-scanner button covers that case.
  const isHardwareCameraToggleShown = !!(enabledModes?.hardware && enabledModes?.camera) && activeMode !== MODE.MANUAL;

  // Manual button — only useful when not already in manual mode.
  const isManualButtonShown = isManualEnabled && activeMode !== MODE.MANUAL;

  // Back-to-scanner — only relevant from MANUAL; resolves to the highest-priority enabled
  // scanner mode (HARDWARE > CAMERA). Undefined if no scanner mode is enabled → button hidden.
  const backToScannerTarget = activeMode === MODE.MANUAL ? SCANNER_MODES.find((m) => enabledModes?.[m]) : undefined;
  const isBackToScannerShown = !!backToScannerTarget;

  if (!isManualButtonShown && !isHardwareCameraToggleShown && !isBackToScannerShown) {
    return null;
  }

  const toggleTarget = activeMode === MODE.HARDWARE ? MODE.CAMERA : MODE.HARDWARE;
  const toggleCaptionKey =
    activeMode === MODE.HARDWARE
      ? 'components.BarcodeScannerComponent.scanWithCamera'
      : 'components.BarcodeScannerComponent.useHardwareScanner';
  const toggleIconName = activeMode === MODE.HARDWARE ? 'fa-camera' : 'fa-barcode';

  return (
    <div className="barcode-scanner-footer">
      {isHardwareCameraToggleShown && (
        <ButtonWithIndicator
          captionKey={toggleCaptionKey}
          typeFASIconName={toggleIconName}
          additionalCssClass="barcode-scanner-btn"
          onClick={() => onModeSelected(toggleTarget)}
          testId="barcode-scanner-toggle-hw-camera"
        />
      )}
      {isBackToScannerShown && (
        <ButtonWithIndicator
          captionKey={BACK_TO_SCANNER_CAPTION_KEY[backToScannerTarget]}
          typeFASIconName={BACK_TO_SCANNER_ICON[backToScannerTarget]}
          additionalCssClass="barcode-scanner-btn"
          onClick={() => onModeSelected(backToScannerTarget)}
          testId="barcode-scanner-back-to-scanner"
        />
      )}
      {isManualButtonShown && (
        <ButtonWithIndicator
          captionKey="components.BarcodeScannerComponent.enterManually"
          typeFASIconName="fa-keyboard"
          additionalCssClass="barcode-scanner-btn"
          onClick={() => onModeSelected(MODE.MANUAL)}
          testId="barcode-scanner-enter-manually"
        />
      )}
    </div>
  );
};

BarcodeScannerFooter.propTypes = {
  activeMode: PropTypes.string.isRequired,
  enabledModes: PropTypes.shape({
    hardware: PropTypes.bool,
    camera: PropTypes.bool,
    manual: PropTypes.bool,
  }).isRequired,
  onModeSelected: PropTypes.func.isRequired,
};

export default BarcodeScannerFooter;
