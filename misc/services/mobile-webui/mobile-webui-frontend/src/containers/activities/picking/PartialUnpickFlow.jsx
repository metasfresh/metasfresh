import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError, toastNotification } from '../../../utils/toast';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { postStepPartiallyUnPickedThunk } from '../../../apps/picking/redux/postStepPartiallyUnPickedThunk';
import GetQuantityDialog from '../../../components/dialogs/GetQuantityDialog';
import UnpickProductScanDialog from './UnpickProductScanDialog';
import UnpickDialog from './UnpickDialog';

const STAGE = {
  SCAN_PRODUCT: 'SCAN_PRODUCT',
  ENTER_QTY: 'ENTER_QTY',
  SCAN_TARGET: 'SCAN_TARGET',
};

/**
 * Partial unpick: remove a chosen qty of a single product from a packed picking step into a
 * target HU, leaving the rest of the package packed. Distinct from the whole-step Unpack.
 *
 * Flow (linear, scanner-first): scan product GTIN -> resolve (AC2 error if not packed) ->
 * enter qty (default = packedQty) -> scan target HU (MANDATORY, no Skip / no floor) -> commit.
 */
const PartialUnpickFlow = ({ wfProcessId, activityId, lineId, stepId, huQRCode, onClose }) => {
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

  const onTargetScanned = ({ unpickToTargetQRCode }) => {
    return dispatch(
      postStepPartiallyUnPickedThunk({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        huQRCode: toQRCodeString(huQRCode),
        unpickProductId: resolved.productId,
        unpickQty,
        unpickToTargetQRCode: toQRCodeString(unpickToTargetQRCode),
      })
    )
      .then(() => {
        toastNotification({ messageKey: 'activities.picking.unpick.success' });
        onClose();
      })
      .catch((axiosError) => {
        // Commit always exits the flow on error (server rejection → toast), mirroring the
        // whole-step unpick handler — no inline retry branch for the multi-stage scan flow.
        toastError({ axiosError });
        onClose();
      });
  };

  if (stage === STAGE.SCAN_PRODUCT) {
    return <UnpickProductScanDialog wfProcessId={wfProcessId} onResolved={onProductResolved} onCloseDialog={onClose} />;
  }

  if (stage === STAGE.ENTER_QTY) {
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
  }

  // STAGE.SCAN_TARGET: mandatory target-HU scan, no Skip (unlike whole-step unpack).
  return (
    <UnpickDialog
      allowSkip={false}
      scanPlaceholderKey="activities.picking.unpick.scanTargetHU"
      scannerTestId="unpick-target-hu-scanner"
      onSubmit={onTargetScanned}
      onCloseDialog={onClose}
    />
  );
};

PartialUnpickFlow.propTypes = {
  // Properties
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  huQRCode: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
  // Callbacks
  onClose: PropTypes.func.isRequired,
};

export default PartialUnpickFlow;
