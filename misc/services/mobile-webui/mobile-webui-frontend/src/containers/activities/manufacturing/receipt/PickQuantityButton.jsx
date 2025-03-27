import React from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../../utils/translations';

import Button from '../../../../components/buttons/Button';
import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';

const PickQuantityButton = ({ qtyTarget, uom, catchWeightUom, caption, isDisabled, onClick }) => {
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

  const onQtyPickedChanged = ({ qtyEnteredAndValidated, catchWeight, catchWeightUom, bestBeforeDate, lotNo }) => {
    setDialogOpen(false);
    onClick({ qtyEnteredAndValidated, catchWeight, catchWeightUom, bestBeforeDate, lotNo });
  };

  return (
    <>
      {isDialogOpen && (
        <GetQuantityDialog
          qtyTargetCaption={trl('activities.mfg.receipts.qtyToReceive')}
          qtyTarget={qtyTarget}
          uom={uom}
          catchWeightUom={catchWeightUom}
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
};

export default PickQuantityButton;
