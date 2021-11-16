import React from 'react';
import PropTypes from 'prop-types';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

/**
 * NOTE TO DEVELOPER: atm this is just a placeholder component. Just to have something on the screen.
 * Feel free to change it or delete it or whatever you want.
 */

const ManufacturingActivity = (props) => {
  const { activityState } = props;
  const isUserEditable = activityState.dataStored.isUserEditable;
  const activityCompleteStatus = activityState.dataStored.completeStatus;

  return (
    <div className="mt-0">
      <button className="button is-outlined complete-btn" disabled={!isUserEditable}>
        <ButtonWithIndicator caption={activityState.caption} completeStatus={activityCompleteStatus} />
      </button>
    </div>
  );
};

ManufacturingActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default ManufacturingActivity;
