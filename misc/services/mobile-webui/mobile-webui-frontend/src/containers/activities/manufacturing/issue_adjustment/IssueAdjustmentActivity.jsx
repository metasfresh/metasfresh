import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import PropTypes from 'prop-types';
import React from 'react';
import { useHistory } from 'react-router-dom';
import { issueAdjustmentScreenLocation } from '../../../../routes/manufacturing_issue_adjustment';

const IssueAdjustmentActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      caption,
      dataStored: { isUserEditable },
    },
  } = props;

  const history = useHistory();
  const onButtonClick = () => {
    history.push(issueAdjustmentScreenLocation({ applicationId, wfProcessId, activityId }));
  };

  return (
    <div className="mt-5">
      <ButtonWithIndicator caption={caption} disabled={!isUserEditable} onClick={() => onButtonClick()} />
    </div>
  );
};

IssueAdjustmentActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default IssueAdjustmentActivity;
