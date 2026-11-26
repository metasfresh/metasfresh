import React from 'react';
import PropTypes from 'prop-types';

import DialogButton from '../../../components/dialogs/DialogButton';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';

/**
 * Button "Next TU" (picking, lines with a CU quantity entry mode that books into TUs).
 * The scanner that reads the code of the next TU lives here too: tapping the button and scanning the code are the same action.
 *
 * @param scanner `{ resolveScannedBarcode, onResolvedResult }` of the ONE hidden scanner of the quantity block (it reads crate codes
 *   and pack labels). Absent where the screen has its own visible scanner (first scan): the button then adds no second scanner.
 */
const OpenNextTUButton = ({ isProcessing = false, onNextTU, scanner }) => (
  <>
    {scanner && (
      <BarcodeScannerComponent
        testId="rescanCULabel-input"
        invisible
        isCoexistingWithTypedInput
        resolveScannedBarcode={scanner.resolveScannedBarcode}
        onResolvedResult={scanner.onResolvedResult}
      />
    )}
    <DialogButton
      captionKey="picking.nextTU"
      testId="next-tu-button"
      icon="fa-barcode"
      className="is-link"
      type="button"
      disabled={isProcessing}
      onClick={onNextTU}
    />
  </>
);

OpenNextTUButton.propTypes = {
  isProcessing: PropTypes.bool,
  onNextTU: PropTypes.func.isRequired,
  scanner: PropTypes.shape({
    resolveScannedBarcode: PropTypes.func,
    onResolvedResult: PropTypes.func.isRequired,
  }),
};

export default OpenNextTUButton;
