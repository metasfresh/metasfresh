import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import PickStepButton from './PickStepButton';

import counterpart from 'counterpart';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getLocation } from '../../../utils';

const PickLineScreen = (props) => {
  const {
    wfProcessId,
    activityId,
    lineId,
    steps,
    dispatch,
    lineProps: { caption },
  } = props;

  useEffect(() => {
    const location = getLocation(props);

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('activities.picking.PickingLine'),
            value: caption,
            bold: true,
          },
        ],
      })
    );
  }, []);

  return (
    <div className="pt-2 section lines-screen-container">
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <PickStepButton
                key={idx}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.pickingStepId}
                pickFromAlternatives={stepItem.pickFromAlternatives}
                //
                uom={stepItem.uom}
                qtyToPick={stepItem.qtyToPick}
                pickFrom={stepItem.mainPickFrom}
              />
            );
          })}
      </div>
    </div>
  );
};

PickLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
  lineProps: PropTypes.object.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default PickLineScreen;
