import React from 'react';
import PropTypes from 'prop-types';
import { selectOptionsLocation } from '../../../../routes/generateHUQRCodes';
import Button from '../../../../components/buttons/Button';
import { useMobileNavigation } from '../../../../hooks/useMobileNavigation';

const GenerateHUQRCodesActivity = ({
  applicationId,
  wfProcessId,
  activityState: {
    activityId,
    caption,
    dataStored: { isUserEditable },
  },
}) => {
  const history = useMobileNavigation();

  return (
    <div className="mt-5">
      <Button
        caption={caption}
        disabled={!isUserEditable}
        onClick={() => history.push(selectOptionsLocation({ applicationId, wfProcessId, activityId }))}
      />
    </div>
  );
};

GenerateHUQRCodesActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default GenerateHUQRCodesActivity;
