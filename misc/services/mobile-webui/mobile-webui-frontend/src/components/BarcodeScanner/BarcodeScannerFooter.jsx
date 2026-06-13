import React from 'react';
import PropTypes from 'prop-types';
import { MODE } from './useBarcodeScannerModes';
import ButtonWithIndicator from '../buttons/ButtonWithIndicator';

/*
 * Footer button cluster — shown only in scanner modes (HARDWARE / CAMERA).
 *
 * In MANUAL mode the footer renders nothing (returns null) so the empty area below the
 * manual input stays free for the mobile virtual keyboard. The "back to scanner" button
 * lives inside ManualModePanel itself in that mode (stacked under Submit, anchored at the
 * top of the panel) so it doesn't get overlapped by the keyboard.
 *
 * Two buttons can show here, both signal the parent via onModeSelected(targetMode):
 *   - HW⇄CAM toggle  → opposite scanner mode of the current activeMode
 *   - Enter manually → MODE.MANUAL
 */
const BarcodeScannerFooter = ({ activeMode, enabledModes, onModeSelected }) => {
  // Both buttons are gated on activeMode !== MANUAL → in MANUAL the footer is empty and
  // the early return below kicks in.
  const isHardwareCameraToggleShown = !!(enabledModes?.hardware && enabledModes?.camera) && activeMode !== MODE.MANUAL;
  const isManualButtonShown = !!enabledModes?.manual && activeMode !== MODE.MANUAL;

  if (!isManualButtonShown && !isHardwareCameraToggleShown) {
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
