import React from 'react';
import PropTypes from 'prop-types';

import LineButton from './RawMaterialsIssueLineButton';
import * as CompleteStatus from '../../../constants/CompleteStatus';

const RawMaterialsIssueActivity = (props) => {
  const {
    activityState: { dataStored },
    wfProcessId,
    id,
  } = props;
  const data = dataStored ? dataStored : {};
  const { /*isUserEditable,*/ lines } = data;

  return (
    <div className="mfg-rawMaterialsIssue-activity-container mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;

            return (
              <LineButton
                key={lineId}
                wfProcessId={wfProcessId}
                activityId={id}
                lineId={lineId}
                caption={lineItem.productName}
                isUserEditable={true}
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
  wfProcessId: PropTypes.string,
  id: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default RawMaterialsIssueActivity;
