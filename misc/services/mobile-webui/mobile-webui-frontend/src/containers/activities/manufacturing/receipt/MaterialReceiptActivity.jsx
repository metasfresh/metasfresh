import React from 'react';
import { useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';

import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { manufacturingReceiptScreenLocation } from '../../../../routes/manufacturing_receipt';
import ButtonWithIndicator from '../../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/ButtonQuantityProp';

const MaterialReceiptActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  const history = useHistory();

  const onButtonClick = ({ lineId }) => {
    const location = manufacturingReceiptScreenLocation({ applicationId, wfProcessId, activityId, lineId });
    history.push(location);
  };

  return (
    <div className="mfg-materialReceipt-activity-container mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;

            return (
              <ButtonWithIndicator
                key={lineId}
                caption={lineItem.productName}
                completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                disabled={!isUserEditable && false}
                onClick={() => onButtonClick({ lineId })}
              >
                <ButtonQuantityProp
                  qtyCurrent={lineItem.qtyReceived}
                  qtyTarget={lineItem.qtyToReceive}
                  uom={lineItem.uom}
                  applicationId={applicationId}
                  subtypeId="receipts"
                />
              </ButtonWithIndicator>
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
