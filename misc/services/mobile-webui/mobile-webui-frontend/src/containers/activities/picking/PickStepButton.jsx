import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { withRouter } from 'react-router';

import { pickingStepScreenLocation } from '../../../routes/picking';
import { computePickFromStatus } from '../../../reducers/wfProcesses_status/picking';
import PickAlternatives from './PickAlternatives';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';

class PickStepButton extends PureComponent {
  handleClick = () => {
    const { push, applicationId, wfProcessId, activityId, lineId, stepId, altStepId } = this.props;
    const location = pickingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId, altStepId });

    push(location);
  };

  render() {
    const {
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
      pickFromAlternatives,
      uom,
      qtyToPick,
      pickFrom,
    } = this.props;
    const isAlternative = altStepId;
    const completeStatus = computePickFromStatus(pickFrom);

    return (
      <div className="mt-3">
        <ButtonWithIndicator
          caption={(isAlternative ? 'ALT:' : '') + pickFrom.locatorName}
          completeStatus={completeStatus}
          onClick={this.handleClick}
        >
          <ButtonQuantityProp
            qtyCurrent={pickFrom.qtyPicked}
            qtyTarget={qtyToPick}
            uom={uom}
            applicationId={applicationId}
          />
        </ButtonWithIndicator>

        {pickFromAlternatives && !altStepId && (
          <PickAlternatives
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityId={activityId}
            lineId={lineId}
            stepId={stepId}
            pickFromAlternatives={pickFromAlternatives}
            uom={uom}
          />
        )}
      </div>
    );
  }
}

PickStepButton.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  pickFrom: PropTypes.object.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  altStepId: PropTypes.string,
  pickFromAlternatives: PropTypes.object,
  uom: PropTypes.string.isRequired,
  //
  // Actions/Functions
  push: PropTypes.func.isRequired,
};

export default withRouter(connect(null, { push })(PickStepButton));
