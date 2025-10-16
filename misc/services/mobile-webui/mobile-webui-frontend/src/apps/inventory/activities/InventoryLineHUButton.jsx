import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import ButtonDetails from '../../../components/buttons/ButtonDetails';
import { trl } from '../../../utils/translations';

const InventoryLineHUButton = ({ id, caption, uom, qtyBooked, qtyCount, onClick }) => {
  return (
    <ButtonWithIndicator key={id} caption={caption} onClick={onClick} testId={`line-${id}-button`}>
      <ButtonDetails
        caption1={trl('inventory.qtyBooked')}
        value1={formatQtyToHumanReadableStr({ qty: qtyBooked, uom })}
        caption2={trl('inventory.qtyCount')}
        value2={formatQtyToHumanReadableStr({ qty: qtyCount, uom })}
      />
    </ButtonWithIndicator>
  );
};
InventoryLineHUButton.propTypes = {
  id: PropTypes.number.isRequired,
  caption: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyBooked: PropTypes.number.isRequired,
  qtyCount: PropTypes.number.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default InventoryLineHUButton;
