import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import ButtonDetails from '../../../components/buttons/ButtonDetails';

const InventoryLineButton = ({ lineId, caption, uom, qtyBooked, qtyCount, onClick }) => {
  // console.log('InventoryLineButton', { lineId, caption, uom, qtyBooked, qtyCount });

  const qtyBookedStr = formatQtyToHumanReadableStr({ qty: qtyBooked, uom });
  const qtyCountStr = formatQtyToHumanReadableStr({ qty: qtyCount, uom });

  return (
    <ButtonWithIndicator
      testId={`line-${lineId}-button`}
      key={lineId}
      caption={caption}
      // completeStatus={line.completeStatus || CompleteStatus.NOT_STARTED}
      // disabled={!isUserEditable || isLineReadOnly({ activity, line })}
      onClick={onClick}
    >
      <ButtonDetails
        caption1={'Booked' /* TODO trl */}
        value1={qtyBookedStr}
        caption2={'Count' /* TODO trl */}
        value2={qtyCountStr}
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
  onClick: PropTypes.func.isRequired,
};

export default InventoryLineButton;
