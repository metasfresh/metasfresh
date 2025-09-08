import React from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';
import { formatQtyToHumanReadableStr } from '../../utils/qtys';

const ButtonQuantityProp = ({ qtyTarget, qtyCurrent, qtyCurrentCatchWeight, uom, applicationId, subtypeId }) => {
  const qtyTargetStr = formatQtyToHumanReadableStr({ qty: qtyTarget, uom });
  const qtyCurrentStr = formatQtyToHumanReadableStr({ qty: qtyCurrent, uom });
  const qtyCurrentCatchWeightStr = qtyCurrentCatchWeight ?? '';
  const trlPrefix = `activities.${applicationId}${subtypeId ? `.${subtypeId}` : ''}`;

  return (
    <div className="row is-full is-size-7">
      <div
        className="picking-row-info"
        data-qtytarget={qtyTargetStr}
        data-qtycurrent={qtyCurrentStr}
        data-qtycurrentcatchweight={qtyCurrentCatchWeightStr}
      >
        <div className="picking-to-pick">{trl(`${trlPrefix}.target`)}:</div>
        <div className="picking-row-qty">{qtyTargetStr}</div>
        <div className="picking-row-picking">{trl(`${trlPrefix}.picked`)}:</div>
        <div className="picking-row-picked">
          {qtyCurrentStr}
          {qtyCurrentCatchWeightStr ? ` (${qtyCurrentCatchWeightStr})` : ''}
        </div>
      </div>
    </div>
  );
};

ButtonQuantityProp.propTypes = {
  qtyTarget: PropTypes.number.isRequired,
  qtyCurrent: PropTypes.number.isRequired,
  qtyCurrentCatchWeight: PropTypes.string,
  uom: PropTypes.string.isRequired,
  applicationId: PropTypes.string.isRequired,
  subtypeId: PropTypes.string,
};

export default ButtonQuantityProp;
