import React from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../../utils/translations';

import Button from '../../../../components/buttons/Button';
import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';
import { HU_ATTRIBUTE_BestBeforeDate, HU_ATTRIBUTE_LotNo } from '../../../../constants/HUAttributes';
import { PickAttribute } from '../../../../reducers/wfProcesses/picking/PickAttribute';

const ATTRIBUTE_ProductionDate = 'ProductionDate';

// With the mandatory catch-weight scan, the scanned lot number and best-before date are always shown for review,
// whether or not they are configured as editable attributes: in the dialog's own lot / best-before rows, sent
// through the dedicated lotNo / bestBeforeDate fields. They are therefore left out of the editable-attribute rows,
// so that each value is shown once and sent through one channel only. The production date a scanned code may
// carry is sent through the dedicated productionDate field as well, so its row is left out too.
const SCAN_FOR_REVIEW_READ_ATTRIBUTES = [PickAttribute.LotNo, PickAttribute.BestBeforeDate];
const SCAN_FOR_REVIEW_ATTRIBUTE_CODES = [HU_ATTRIBUTE_LotNo, HU_ATTRIBUTE_BestBeforeDate, ATTRIBUTE_ProductionDate];

const PickQuantityButton = ({
  qtyTarget,
  uom,
  catchWeightUom,
  caption,
  isDisabled,
  customQRCodeFormats,
  editableAttributes,
  readOnlyAttributes = [],
  defaultLotNo,
  defaultBestBeforeDate,
  isCatchWeightScanMandatory = false,
  isOnePiecePerScan = false,
  onClick,
}) => {
  const [isDialogOpen, setDialogOpen] = React.useState(false);

  // A lot number that is not editable is still shown, read-only, with its preview (the receipt generates it).
  const isLotNoReadOnly = readOnlyAttributes.includes(PickAttribute.LotNo);

  const dialogEditableAttributes = isCatchWeightScanMandatory
    ? (editableAttributes ?? []).filter(({ code }) => !SCAN_FOR_REVIEW_ATTRIBUTE_CODES.includes(code))
    : editableAttributes;

  // The pre-filled defaults: the initial values of the lot number / best-before date attribute rows,
  // or, with the mandatory scan, of the dialog's own lot / best-before rows.
  const editableAttributesDefaultValues = isCatchWeightScanMandatory
    ? {}
    : {
        [HU_ATTRIBUTE_LotNo]: defaultLotNo,
        [HU_ATTRIBUTE_BestBeforeDate]: defaultBestBeforeDate,
      };

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

  const withoutUnchangedDefaults = (attributeValues) => {
    if (!attributeValues) return attributeValues;
    return Object.fromEntries(
      Object.entries(attributeValues).filter(
        ([code, value]) =>
          editableAttributesDefaultValues[code] == null || value !== editableAttributesDefaultValues[code]
      )
    );
  };

  const onQtyPickedChanged = ({
    qtyEnteredAndValidated,
    catchWeight,
    catchWeightUom,
    bestBeforeDate,
    productionDate,
    lotNo,
    attributeValues,
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
      attributeValues: withoutUnchangedDefaults(attributeValues),
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
          isOnePiecePerScan={isOnePiecePerScan}
          readAttributes={isCatchWeightScanMandatory ? SCAN_FOR_REVIEW_READ_ATTRIBUTES : []}
          readOnlyAttributes={readOnlyAttributes}
          lotNo={isCatchWeightScanMandatory || isLotNoReadOnly ? defaultLotNo : undefined}
          bestBeforeDate={isCatchWeightScanMandatory ? defaultBestBeforeDate : undefined}
          editableAttributes={dialogEditableAttributes}
          editableAttributesInitialValues={editableAttributesDefaultValues}
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
  editableAttributes: PropTypes.array,
  // shown but not editable (set by the receipt); only LotNo is supported
  readOnlyAttributes: PropTypes.array,
  defaultLotNo: PropTypes.string,
  defaultBestBeforeDate: PropTypes.string,
  // Catch-weight only: the L&M QR code must be scanned and the scan fills the fields for review
  isCatchWeightScanMandatory: PropTypes.bool,
  // the backend's flag: each receipt is one piece (qty fixed at 1)
  isOnePiecePerScan: PropTypes.bool,
};

export default PickQuantityButton;
