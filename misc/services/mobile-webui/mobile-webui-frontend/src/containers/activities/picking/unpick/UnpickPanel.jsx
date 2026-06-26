import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { toastError } from '../../../../utils/toast';
import { toQRCodeString } from '../../../../utils/qrCode/hu';
import { postStepPartiallyUnPickedThunk } from '../../../../apps/picking/redux/postStepPartiallyUnPickedThunk';
import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';
import UnpickProductScanDialog from './UnpickProductScanDialog';
import UnpickTargetScanDialog from './UnpickTargetScanDialog';

const STAGE = {
  SCAN_PRODUCT: 'SCAN_PRODUCT',
  ENTER_QTY: 'ENTER_QTY',
  SCAN_TARGET: 'SCAN_TARGET',
};

// Orchestrates the job-level partial-unpick flow as a panel that takes over the picking job screen,
// driven by a single `stage` state through three steps: scan the product to unpick
// (UnpickProductScanDialog) → enter the qty (GetQuantityDialog) → scan the target HU, or skip to drop
// on the floor (UnpickTargetScanDialog).
const UnpickPanel = ({ wfProcessId, activityId, lineId, onClose }) => {
  const dispatch = useDispatch();

  const [stage, setStage] = useState(STAGE.SCAN_PRODUCT);
  const [resolved, setResolved] = useState(null);
  const [unpickQty, setUnpickQty] = useState(null);

  const onProductResolved = (response) => {
    setResolved(response);
    setStage(STAGE.ENTER_QTY);
  };

  const onQtyChange = ({ qtyEnteredAndValidated }) => {
    setUnpickQty(qtyEnteredAndValidated);
    setStage(STAGE.SCAN_TARGET);
  };

  const onTargetSubmitted = ({ unpickToTargetQRCode }) => {
    return dispatch(
      postStepPartiallyUnPickedThunk({
        wfProcessId,
        activityId,
        lineId,
        scannedCode: resolved.scannedCode,
        unpickProductId: resolved.productId,
        unpickQty,
        unpickToTargetQRCode: toQRCodeString(unpickToTargetQRCode),
      })
    )
      .then(() => {
        onClose();
      })
      .catch((axiosError) => {
        // Any submit failure — a server rejection (4xx/5xx, e.g. a mis-scanned/incompatible target HU) OR a
        // transient network failure — keeps the panel on SCAN_TARGET: toast the error and let the operator
        // correct and re-scan, or Cancel to abort. A server rejection is recoverable in place (rescan the
        // right target), so it should NOT kick the operator out of the flow any more than a network blip does;
        // closing only on success keeps both failure modes consistent. onClose() runs on the success (.then) path.
        toastError({ axiosError });
      });
  };

  if (stage === STAGE.SCAN_PRODUCT) {
    return <UnpickProductScanDialog wfProcessId={wfProcessId} onResolved={onProductResolved} onCloseDialog={onClose} />;
  } else if (stage === STAGE.ENTER_QTY) {
    const packedQty = Number(resolved?.packedQty ?? 0);
    return (
      <div data-testid="unpick-qty-dialog">
        <GetQuantityDialog
          qtyTarget={packedQty}
          qtyInitial={packedQty}
          uom={resolved?.packedQtyUom ?? ''}
          qtyCaption={trl('activities.picking.unpick.qtyCaption')}
          userInfo={[{ caption: trl('general.Product'), value: resolved?.productName }]}
          onQtyChange={onQtyChange}
          onCloseDialog={onClose}
        />
      </div>
    );
  } else {
    return (
      <UnpickTargetScanDialog
        scanPlaceholderKey="activities.picking.unpick.scanTargetHU"
        scannerTestId="unpick-target-hu-scanner"
        skipTestId="unpick-skip-to-floor"
        onSubmit={onTargetSubmitted}
        onCloseDialog={onClose}
      />
    );
  }
};

UnpickPanel.propTypes = {
  // Properties
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  // Callbacks
  onClose: PropTypes.func.isRequired,
};

export default UnpickPanel;
