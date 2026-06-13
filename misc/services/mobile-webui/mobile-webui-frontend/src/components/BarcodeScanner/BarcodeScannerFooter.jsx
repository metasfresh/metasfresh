import React from 'react';
import PropTypes from 'prop-types';
import { MODE } from '../../hooks/useBarcodeScannerModes';

import '../../assets/BarcodeScannerFooter.scss';
import ButtonWithIndicator from '../buttons/ButtonWithIndicator';

const BarcodeScannerFooter = ({
  activeMode,
  enabledModes,
  onSelectManual,
  onToggleHardwareCamera,
  onBackToScanner,
}) => {
  const isManualEnabled = !!enabledModes?.manual;
  const isHardwareCameraToggleShown = !!(enabledModes?.hardware && enabledModes?.camera);
  // Show "Back to scanner" when user is in manual mode and there is at least one scanner mode to go back to.
  const isBackToScannerShown = activeMode === MODE.MANUAL && (!!enabledModes?.hardware || !!enabledModes?.camera);

  // The manual button is only useful when not already in manual mode.
  const isManualButtonShown = isManualEnabled && activeMode !== MODE.MANUAL;

  if (!isManualButtonShown && !isHardwareCameraToggleShown && !isBackToScannerShown) {
    return null;
  }

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
          additionalCssClass="barcode-scanner-footer__btn"
          onClick={onToggleHardwareCamera}
          testId="barcode-scanner-toggle-hw-camera"
        />
      )}
      {isBackToScannerShown && (
        <ButtonWithIndicator
          captionKey="components.BarcodeScannerComponent.backToScanner"
          typeFASIconName="fa-barcode"
          additionalCssClass="barcode-scanner-footer__btn"
          onClick={onBackToScanner}
          testId="barcode-scanner-back-to-scanner"
        />
      )}
      {isManualButtonShown && (
        <ButtonWithIndicator
          captionKey="components.BarcodeScannerComponent.enterManually"
          typeFASIconName="fa-keyboard"
          additionalCssClass="barcode-scanner-footer__btn"
          onClick={onSelectManual}
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
  onSelectManual: PropTypes.func.isRequired,
  onToggleHardwareCamera: PropTypes.func.isRequired,
  onBackToScanner: PropTypes.func.isRequired,
};

export default BarcodeScannerFooter;
