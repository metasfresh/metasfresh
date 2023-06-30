import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { manufacturingLineScreenLocation } from '../../../../routes/manufacturing_issue';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';

const RawMaterialIssueActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  const history = useHistory();
  const onButtonClick = (lineId) => {
    history.push(manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;

            return (
              <ButtonWithIndicator
                key={lineId}
                caption={lineItem.productName}
                completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                disabled={!isUserEditable}
                onClick={() => onButtonClick(lineId)}
              >
                <ButtonQuantityProp
                  qtyCurrent={lineItem.qtyIssued}
                  qtyTarget={lineItem.qtyToIssue}
                  uom={lineItem.uom}
                  applicationId={applicationId}
                  subtypeId="issues"
                />
              </ButtonWithIndicator>
            );
          })
        : null}
    </div>
  );
};

RawMaterialIssueActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default RawMaterialIssueActivity;
