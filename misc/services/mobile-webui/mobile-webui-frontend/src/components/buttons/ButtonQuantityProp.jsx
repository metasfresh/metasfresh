import React from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

const ButtonQuantityProp = ({ qtyTarget, qtyCurrent, uom, applicationId, subtypeId }) => (
  <div className="row is-full is-size-7">
    <div className="picking-row-info">
      <div className="picking-to-pick">
        {trl(`activities.${applicationId}${subtypeId ? `.${subtypeId}` : ''}.target`)}:
      </div>
      <div className="picking-row-qty">
        {qtyTarget} {uom}
      </div>
      <div className="picking-row-picking">
        {trl(`activities.${applicationId}${subtypeId ? `.${subtypeId}` : ''}.picked`)}:
      </div>
      <div className="picking-row-picked">
        {qtyCurrent} {uom}
      </div>
    </div>
  </div>
);

ButtonQuantityProp.propTypes = {
  qtyTarget: PropTypes.number.isRequired,
  qtyCurrent: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  applicationId: PropTypes.string.isRequired,
  subtypeId: PropTypes.string,
};

export default ButtonQuantityProp;
