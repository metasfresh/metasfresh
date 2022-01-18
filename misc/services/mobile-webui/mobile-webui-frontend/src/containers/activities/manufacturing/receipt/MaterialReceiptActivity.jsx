import React from 'react';
import PropTypes from 'prop-types';

import LineButton from './MaterialReceiptLineButton';
import * as CompleteStatus from '../../../../constants/CompleteStatus';

const MaterialReceiptActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  return (
    <div className="mfg-materialReceipt-activity-container mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;

            return (
              <LineButton
                key={lineId}
                applicationId={applicationId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                caption={lineItem.productName}
                isUserEditable={isUserEditable || true}
                completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                qtyReceived={lineItem.qtyReceived}
                qtyToReceive={lineItem.qtyToReceive}
                uom={lineItem.uom}
              />
            );
          })
        : null}
    </div>
  );
};

MaterialReceiptActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default MaterialReceiptActivity;
