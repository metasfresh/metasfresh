import React from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../../utils/translations';

import Button from '../../../../components/buttons/Button';
import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';

const PickQuantityButton = ({
  qtyTarget,
  uom,
  catchWeightUom,
  caption,
  isDisabled,
  customQRCodeFormats,
  readAttributes,
  defaultLotNo,
  defaultBestBeforeDate,
  isCatchWeightScanMandatory = false,
  onClick,
}) => {
  const [isDialogOpen, setDialogOpen] = React.useState(false);

  const validateQtyEntered = (qtyEntered) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl('activities.picking.notPositiveQtyNotAllowed');
    }

    // OK
    // NOTE: receiving over the target shall fine
    return null;
  };

  // Only a value the operator changed (or a scanned QR code supplied) is sent. An unchanged pre-filled
  // default is left out so the backend fills it at receipt with the real receipt day.
  // Clearing a pre-filled field cannot express "no value": the backend then applies its default (intended).
  const sendOnlyIfChanged = ({ value, defaultValue, isScanned }) => {
    if (!value) return null;
    return isScanned || value !== defaultValue ? value : null;
  };

  const onQtyPickedChanged = ({
    qtyEnteredAndValidated,
    catchWeight,
    catchWeightUom,
    bestBeforeDate,
    productionDate,
    lotNo,
    barcodeType,
    barcode, // i.e. the catch weight QR code
    isDone = true,
  }) => {
    const isScanned = !!barcode;
    if (isDone) {
      setDialogOpen(false);
    }
    onClick({
      qtyEnteredAndValidated,
      catchWeight,
      catchWeightUom,
      bestBeforeDate: sendOnlyIfChanged({ value: bestBeforeDate, defaultValue: defaultBestBeforeDate, isScanned }),
      productionDate,
      lotNo: sendOnlyIfChanged({ value: lotNo, defaultValue: defaultLotNo, isScanned }),
      barcode,
      barcodeType,
      isDone,
    });
  };

  return (
    <>
      {isDialogOpen && (
        <GetQuantityDialog
          qtyTargetCaption={trl('activities.mfg.receipts.qtyToReceive')}
          qtyTarget={qtyTarget}
          uom={uom}
          catchWeightUom={catchWeightUom}
          customQRCodeFormats={customQRCodeFormats}
          isCatchWeightScanMandatory={isCatchWeightScanMandatory}
          readAttributes={readAttributes}
          lotNo={defaultLotNo}
          bestBeforeDate={defaultBestBeforeDate}
          validateQtyEntered={validateQtyEntered}
          onQtyChange={onQtyPickedChanged}
          onCloseDialog={() => setDialogOpen(false)}
        />
      )}
      <Button caption={caption} onClick={() => setDialogOpen(true)} disabled={isDisabled} testId="receive-qty-button" />
    </>
  );
};

PickQuantityButton.propTypes = {
  qtyTarget: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  catchWeightUom: PropTypes.string,
  caption: PropTypes.string.isRequired,
  isDisabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
  customQRCodeFormats: PropTypes.array,
  readAttributes: PropTypes.array,
  defaultLotNo: PropTypes.string,
  defaultBestBeforeDate: PropTypes.string,
  // Catch-weight only: the L&M QR code must be scanned and the scan fills the fields for review
  isCatchWeightScanMandatory: PropTypes.bool,
};

export default PickQuantityButton;
