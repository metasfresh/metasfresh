import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from './buttons/ButtonWithIndicator';
import { MODE } from '../hooks/useBarcodeScannerModes';

import '../assets/BarcodeScannerFooter.scss';

const BarcodeScannerFooter = ({ activeMode, enabledModes, onSelectManual, onToggleHardwareCamera }) => {
  const showManual = !!enabledModes?.manual;
  const showHardwareCameraToggle = !!(enabledModes?.hardware && enabledModes?.camera);

  if (!showManual && !showHardwareCameraToggle) {
    return null;
  }

  const toggleCaptionKey =
    activeMode === MODE.HARDWARE
      ? 'components.BarcodeScannerComponent.scanWithCamera'
      : 'components.BarcodeScannerComponent.useHardwareScanner';

  const toggleIconName = activeMode === MODE.HARDWARE ? 'fa-camera' : 'fa-barcode';

  return (
    <div className="barcode-scanner-footer">
      {showHardwareCameraToggle && (
        <ButtonWithIndicator
          captionKey={toggleCaptionKey}
          typeFASIconName={toggleIconName}
          additionalCssClass="barcode-scanner-footer__btn"
          onClick={onToggleHardwareCamera}
          testId="barcode-scanner-toggle-hw-camera"
        />
      )}
      {showManual && (
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
};

export default BarcodeScannerFooter;
