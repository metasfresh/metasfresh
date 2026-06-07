import React, { useState } from 'react';
import PropTypes from 'prop-types';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import { postMassPrintingScan } from '../../../api/picking';

const MassPrintingScanScreen = () => {
  const { history, applicationId, wfProcessId } = useScreenDefinition({
    screenId: 'MassPrintingScanScreen',
    captionKey: 'activities.picking.massPrinting.scanCaption',
    back: getWFProcessScreenLocation,
  });

  const [result, setResult] = useState(null);

  const onResolvedResult = ({ scannedBarcode }) => {
    postMassPrintingScan({ scannedCode: scannedBarcode })
      .then((data) => setResult(data))
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onDone = () => {
    history.replace(getWFProcessScreenLocation({ applicationId, wfProcessId }));
  };

  if (result) {
    return (
      <div className="mt-5" data-testid="mass-printing-result">
        <MassPrintingResult result={result} onDone={onDone} />
      </div>
    );
  }

  return <BarcodeScannerComponent onResolvedResult={onResolvedResult} />;
};

//
//
// ----------------------------------------------------------
//
//

const MassPrintingResult = ({ result, onDone }) => {
  const { productResults = [], skippedNonSelfPackedProductIds = [] } = result;

  return (
    <div className="mass-printing-result">
      {productResults.length === 0 && skippedNonSelfPackedProductIds.length === 0 && (
        <p data-testid="mass-printing-result-empty">{trl('activities.picking.massPrinting.noResults')}</p>
      )}

      {productResults.map((pr, idx) => (
        <div key={idx} className="mass-printing-product-result" data-testid="mass-printing-product-result">
          <p className="mass-printing-product-id" data-testid="mass-printing-product-id">
            {trl('activities.picking.massPrinting.product')} {pr.productId}
          </p>
          <p data-testid="mass-printing-boxes-packed">
            {trl('activities.picking.massPrinting.boxesPacked')}: {pr.boxesPacked}
          </p>
          <p data-testid="mass-printing-labels-printed">
            {trl('activities.picking.massPrinting.labelsPrinted')}: {pr.labelsPrinted}
          </p>
          {pr.labelPrintFailures > 0 && (
            <p className="has-text-danger" data-testid="mass-printing-label-failures">
              {trl('activities.picking.massPrinting.labelPrintFailures')}: {pr.labelPrintFailures}
            </p>
          )}
          {pr.unitsLeftOnLU > 0 && (
            <p data-testid="mass-printing-units-left">
              {trl('activities.picking.massPrinting.unitsLeftOnLU')}: {pr.unitsLeftOnLU}
            </p>
          )}
          {pr.unitsOfOpenDemandRemaining > 0 && (
            <p data-testid="mass-printing-demand-remaining">
              {trl('activities.picking.massPrinting.unitsOfOpenDemandRemaining')}: {pr.unitsOfOpenDemandRemaining}
            </p>
          )}
        </div>
      ))}

      {skippedNonSelfPackedProductIds.length > 0 && (
        <div data-testid="mass-printing-skipped">
          <p>
            {trl('activities.picking.massPrinting.skippedProducts')}: {skippedNonSelfPackedProductIds.length}
          </p>
        </div>
      )}

      <div className="mt-4">
        <ButtonWithIndicator
          captionKey="activities.picking.massPrinting.doneButton"
          testId="mass-printing-done-button"
          onClick={onDone}
          additionalCssClass="action-button"
        />
      </div>
    </div>
  );
};

MassPrintingResult.propTypes = {
  result: PropTypes.shape({
    productResults: PropTypes.arrayOf(
      PropTypes.shape({
        productId: PropTypes.number.isRequired,
        boxesPacked: PropTypes.number.isRequired,
        labelsPrinted: PropTypes.number.isRequired,
        labelPrintFailures: PropTypes.number.isRequired,
        unitsLeftOnLU: PropTypes.number.isRequired,
        unitsOfOpenDemandRemaining: PropTypes.number.isRequired,
      })
    ),
    skippedNonSelfPackedProductIds: PropTypes.arrayOf(PropTypes.number),
  }).isRequired,
  onDone: PropTypes.func.isRequired,
};

export default MassPrintingScanScreen;
