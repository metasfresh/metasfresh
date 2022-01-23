import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import Button from '../../../../components/buttons/Button';
import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';

import { toastError } from '../../../../utils/toast';

const PickQuantityButton = ({ qtyTarget, uom, caption, isDisabled, onClick }) => {
  const [isDialogOpen, setDialogOpen] = React.useState(false);

  const onQtyPickedChanged = (qty) => {
    const qtyEntered = parseFloat(qty);
    if (isNaN(qtyEntered)) {
      return;
    }

    if (qtyEntered >= 0 && qtyEntered <= qtyTarget) {
      setDialogOpen(false);
      onClick(qtyEntered);
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
  };

  return (
    <>
      {isDialogOpen && (
        <GetQuantityDialog
          qtyCaption={counterpart.translate('activities.mfg.receipts.pickPromptTitle')}
          qtyTarget={qtyTarget}
          uom={uom}
          onQtyChange={onQtyPickedChanged}
          onCloseDialog={() => setDialogOpen(false)}
        />
      )}
      <Button caption={caption} onClick={() => setDialogOpen(true)} disabled={isDisabled} />
    </>
  );
};

PickQuantityButton.propTypes = {
  qtyTarget: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isDisabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default PickQuantityButton;
