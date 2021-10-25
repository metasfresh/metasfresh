import React from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';

const ButtonQuantityProp = ({ qtyToPick, qtyPicked, uom }) => (
  <div className="row is-full is-size-7">
    <div className="picking-row-info">
      <div className="picking-to-pick">{counterpart.translate('activities.picking.pickingBtn.toPick')}:</div>
      <div className="picking-row-qty">
        {qtyToPick} {uom}
      </div>
      <div className="picking-row-picking">{counterpart.translate('activities.picking.pickingBtn.toPick')}:</div>
      <div className="picking-row-picked">
        {qtyPicked} {uom}
      </div>
    </div>
  </div>
);

ButtonQuantityProp.propTypes = {
  qtyToPick: PropTypes.number.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
};

export default ButtonQuantityProp;
