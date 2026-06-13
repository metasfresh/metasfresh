import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../utils/translations';
import ButtonWithIndicator from '../buttons/ButtonWithIndicator';

const ManualModePanel = ({ isProcessing, onBarcodeScanned }) => {
  const manualInputRef = useRef();

  // Autofocus the visible manual input whenever MANUAL mode becomes active.
  useEffect(() => {
    manualInputRef?.current?.focus();
  }, []);

  // Manual entry mode: submit the typed value, auto-return to default mode on success,
  // or keep the text and select it on error so the user can correct and retry.
  const handleManualSubmit = () => {
    if (isProcessing) return;

    const scannedBarcode = manualInputRef?.current?.value?.trim();
    if (!scannedBarcode) return;
    onBarcodeScanned({
      scannedBarcode,
      onSuccess: () => {
        if (manualInputRef?.current) {
          manualInputRef.current.value = '';
        }
      },
      onError: () => {
        manualInputRef?.current?.select();
      },
    });
  };

  const handleManualKeyUp = (e) => {
    if (isProcessing) return;
    if (e.key === 'Enter') {
      handleManualSubmit();
    }
  };

  return (
    <div className="manual-entry">
      <input
        disabled={isProcessing}
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
  );
};
ManualModePanel.propTypes = {
  isProcessing: PropTypes.bool,
  onBarcodeScanned: PropTypes.func.isRequired,
};

export default ManualModePanel;
