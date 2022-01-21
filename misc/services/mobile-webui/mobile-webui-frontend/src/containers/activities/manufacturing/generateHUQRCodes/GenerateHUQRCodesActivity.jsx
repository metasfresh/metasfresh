import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';
import { selectOptionsLocation } from '../../../../routes/generateHUQRCodes';
import Button from '../../../../components/buttons/Button';

const GenerateHUQRCodesActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityState: {
      activityId,
      caption,
      dataStored: { isUserEditable },
    },
  } = props;

  const history = useHistory();

  const handleClick = () => {
    history.push(selectOptionsLocation({ applicationId, wfProcessId, activityId }));
  };

  return <Button caption={caption} disabled={!isUserEditable} onClick={handleClick} />;
};

GenerateHUQRCodesActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default GenerateHUQRCodesActivity;
