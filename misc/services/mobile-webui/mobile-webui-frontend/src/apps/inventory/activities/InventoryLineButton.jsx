import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

const InventoryLineButton = ({ lineId, caption, uom, qtyBooked, qtyCount, onClick }) => {
  console.log('InventoryLineButton', { lineId, caption, uom, qtyBooked, qtyCount });
  return (
    <ButtonWithIndicator
      testId={`line-${lineId}-button`}
      key={lineId}
      caption={caption}
      // completeStatus={line.completeStatus || CompleteStatus.NOT_STARTED}
      // disabled={!isUserEditable || isLineReadOnly({ activity, line })}
      onClick={onClick}
    ></ButtonWithIndicator>
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
