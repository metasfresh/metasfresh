import React from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';

const ButtonQuantityProp = ({ qtyTarget, qtyPicked, uom, appId }) => (
  <div className="row is-full is-size-7">
    <div className="picking-row-info">
      <div className="picking-to-pick">{counterpart.translate(`activities.${appId}.target`)}:</div>
      <div className="picking-row-qty">
        {qtyTarget} {uom}
      </div>
      <div className="picking-row-picking">{counterpart.translate(`activities.${appId}.picked`)}:</div>
      <div className="picking-row-picked">
        {qtyPicked} {uom}
      </div>
    </div>
  </div>
);

ButtonQuantityProp.propTypes = {
  qtyTarget: PropTypes.number.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  appId: PropTypes.string.isRequired,
};

export default ButtonQuantityProp;
