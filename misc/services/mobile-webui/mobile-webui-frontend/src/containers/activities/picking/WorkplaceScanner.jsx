import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseWorkplaceQRCodeString } from '../../../utils/workplaceQRCodes';
import PropTypes from 'prop-types';
import { postWorkplaceAssignment } from '../../../api/workplace';
import { trl } from '../../../utils/translations';

const WorkplaceScanner = ({ onComplete }) => {
  const assignWorkplace = async ({ workplaceId }) => {
    await postWorkplaceAssignment(workplaceId);

    onComplete && onComplete();
  };

  return (
    <BarcodeScannerComponent
      resolveScannedBarcode={({ scannedBarcode }) => parseWorkplaceQRCodeString(scannedBarcode)}
      onResolvedResult={assignWorkplace}
      inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkplacePlaceholder')}
    />
  );
};

WorkplaceScanner.propTypes = {
  //
  // Props:
  onComplete: PropTypes.func,
};

export default WorkplaceScanner;
