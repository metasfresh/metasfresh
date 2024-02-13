import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseWorkplaceQRCodeString } from '../../../utils/qrCode/workplace';
import PropTypes from 'prop-types';
import { trl } from '../../../utils/translations';
import { assignWorkplace } from '../../../apps/workplaceManager/api';

const WorkplaceScanner = ({ onComplete }) => {
  const resolveScannedBarcode = ({ scannedBarcode }) => parseWorkplaceQRCodeString(scannedBarcode);

  const onResolvedResult = ({ workplaceId }) => {
    assignWorkplace(workplaceId).then(() => onComplete());
  };

  return (
    <BarcodeScannerComponent
      resolveScannedBarcode={resolveScannedBarcode}
      onResolvedResult={onResolvedResult}
      inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkplacePlaceholder')}
    />
  );
};

WorkplaceScanner.propTypes = {
  onComplete: PropTypes.func.isRequired,
};

export default WorkplaceScanner;
