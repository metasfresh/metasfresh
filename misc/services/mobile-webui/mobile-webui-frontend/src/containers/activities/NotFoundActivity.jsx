import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import counterpart from 'counterpart';

import { postStepPicked } from '../../api/picking';
import { updatePickingStepQty } from '../../actions/PickingActions';
import ConfirmButton from './confirmButton/ConfirmButton';

class NotFoundActivity extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId, stepId, lineId, activityId, updatePickingStepQty } = this.props;

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      qtyPicked: 0,
      qtyRejectedReasonCode: 'N',
    }).then(() => {
      updatePickingStepQty({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        qtyPicked: 0,
        qtyRejectedReasonCode: 'N',
      });
    });
  };

  render() {
    const caption = counterpart.translate('activities.confirmButton.notfound.caption');

    return (
      <div className="mt-5">
        <ConfirmButton
          isCancelMode={true}
          isUserEditable={true}
          caption={caption}
          onUserConfirmed={this.onUserConfirmed}
        />
      </div>
    );
  }
}

NotFoundActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
  activityId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
};

export default connect(null, { updatePickingStepQty })(NotFoundActivity);
