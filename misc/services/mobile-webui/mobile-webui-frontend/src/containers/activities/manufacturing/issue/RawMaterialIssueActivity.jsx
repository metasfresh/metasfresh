import React from 'react';
import PropTypes from 'prop-types';

import LineButton from './RawMaterialsIssueLineButton';
import * as CompleteStatus from '../../../../constants/CompleteStatus';

const RawMaterialsIssueActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  return (
    <div className="mfg-rawMaterialsIssue-activity-container mt-5">
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
                isUserEditable={isUserEditable}
                completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                qtyIssued={lineItem.qtyIssued}
                qtyToIssue={lineItem.qtyToIssue}
                uom={lineItem.uom}
              />
            );
          })
        : null}
    </div>
  );
};

RawMaterialsIssueActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object,
};

export default RawMaterialsIssueActivity;
