import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';
import ButtonWithIndicator from '../../../../components/ButtonWithIndicator';
import { selectOptionsLocation } from '../../../../routes/generateHUQRCodes';

const GenerateHUQRCodesActivity = (props) => {
  const history = useHistory();
  const { applicationId, wfProcessId, activityState } = props;

  const isUserEditable = activityState.dataStored.isUserEditable;

  const handleClick = () => {
    const { activityId } = activityState;
    history.push(selectOptionsLocation({ applicationId, wfProcessId, activityId }));
  };

  return (
    <div className="mt-0">
      <button className="button is-outlined complete-btn" disabled={!isUserEditable} onClick={handleClick}>
        <ButtonWithIndicator caption={activityState.caption} />
      </button>
    </div>
  );
};

GenerateHUQRCodesActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default GenerateHUQRCodesActivity;
