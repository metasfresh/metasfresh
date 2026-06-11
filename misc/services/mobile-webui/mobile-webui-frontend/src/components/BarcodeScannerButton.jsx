import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from './buttons/ButtonWithIndicator';
import BarcodeScannerComponent from './BarcodeScannerComponent';

export const BarcodeScannerButton = ({ captionKey, enableScanning, disabled, onBarcodeScanned, onClick }) => {
  return (
    <>
      <ButtonWithIndicator id="scanQRCode-button" captionKey={captionKey} disabled={disabled} onClick={onClick} />
      {enableScanning && !disabled && (
        <BarcodeScannerComponent isShowInputText={false} isShowVideo={false} onResolvedResult={onBarcodeScanned} />
      )}
    </>
  );
};

BarcodeScannerButton.propTypes = {
  captionKey: PropTypes.string.isRequired,
  enableScanning: PropTypes.bool,
  disabled: PropTypes.bool,
  onBarcodeScanned: PropTypes.func,
  onClick: PropTypes.func.isRequired,
};
