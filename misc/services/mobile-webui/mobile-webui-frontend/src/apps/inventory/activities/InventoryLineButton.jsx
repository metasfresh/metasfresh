import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import ButtonDetails from '../../../components/buttons/ButtonDetails';
import { trl } from '../../../utils/translations';
import { CountStatus } from '../utils/CountStatus';

const InventoryLineButton = ({
  lineId,
  caption,
  uom,
  qtyBooked,
  qtyCount,
  productId,
  locatorId,
  countStatus,
  onClick,
}) => {
  return (
    <ButtonWithIndicator
      key={lineId}
      caption={caption}
      onClick={onClick}
      testId={`line-P${productId}-L${locatorId}-button`}
      completeStatus={CountStatus.computeCompleteStatus({ countStatus })}
    >
      <ButtonDetails
        caption1={trl('inventory.qtyBooked')}
        value1={formatQtyToHumanReadableStr({ qty: qtyBooked, uom })}
        caption2={trl('inventory.qtyCount')}
        value2={formatQtyToHumanReadableStr({ qty: qtyCount, uom })}
      />
    </ButtonWithIndicator>
  );
};
InventoryLineButton.propTypes = {
  lineId: PropTypes.number.isRequired,
  caption: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyBooked: PropTypes.number.isRequired,
  qtyCount: PropTypes.number.isRequired,
  productId: PropTypes.number.isRequired,
  locatorId: PropTypes.number.isRequired,
  countStatus: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default InventoryLineButton;
