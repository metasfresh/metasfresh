import React from 'react';
import PropTypes from 'prop-types';

//import LineButton from './RawMaterialsIssueLineButton';
import * as CompleteStatus from '../../../../constants/CompleteStatus';
import ButtonWithIndicator from '../../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/ButtonQuantityProp';
import { useHistory } from 'react-router-dom';
import { manufacturingLineScreenLocation } from '../../../../routes/manufacturing_issue';

const RawMaterialsIssueActivity = (props) => {
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
    const location = manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId });
    history.push(location);
  };

  return (
    <div className="mfg-rawMaterialsIssue-activity-container mt-5">
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

RawMaterialsIssueActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object,
};

export default RawMaterialsIssueActivity;
