import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import { postStepUnPicked, postStepPicked } from '../../../api/picking';
import { toastError } from '../../../utils/toast';
import { getPickFrom, getQtyToPick } from '../../../utils/picking';
import { pickingStepScreenLocation, pickingStepScanScreenLocation } from '../../../routes/picking';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import * as CompleteStatus from '../../../constants/CompleteStatus';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ScreenToaster from '../../../components/ScreenToaster';
import NotFoundButton from '../NotFoundButton';

class PickStepScreen extends Component {
  componentDidMount() {
    const {
      pushHeaderEntry,
      stepProps: { mainPickFrom },
    } = this.props;
    const location = pickingStepScreenLocation(this.props);

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Locator'),
          value: mainPickFrom.locatorName,
        },
      ],
    });
  }

  componentWillUnmount() {
    const { wfProcessId, activityId, lineId, stepId, updatePickingStepQty } = this.props;
    const qtyPicked = getPickFrom(this.props).qtyPicked;

    qtyPicked === '' &&
      updatePickingStepQty({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        qtyPicked: 0,
      });
  }

  onUnpickButtonClick = () => {
    const { wfProcessId, activityId, lineId, stepId, postStepUnPicked, push, updatePickingStepQty } = this.props;

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: getPickFrom(this.props).huBarcode,
    })
      .then(() => {
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyPicked: 0,
          qtyRejected: 0,
          qtyRejectedReasonCode: null,
        });
        push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  handleNotFound = () => {
    const { wfProcessId, stepId, altStepId, lineId, activityId, updatePickingStepQty } = this.props;
    const huBarcode = this.getPickFrom().huBarcode;
    const qtyRejected = this.getQtyToPick();

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      qtyPicked: 0,
      qtyRejected,
      qtyRejectedReasonCode: 'N',
      huBarcode,
    }).then(() => {
      updatePickingStepQty({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        altStepId,
        qtyPicked: 0,
        qtyRejected,
        qtyRejectedReasonCode: 'N',
      });
    });
  };

  onScanButtonClick = () => {
    const { push } = this.props;
    const location = pickingStepScanScreenLocation(this.props);

    push(location);
  };

  render() {
    const { wfProcessId, activityId, lineId, stepId } = this.props;
    const qtyToPick = getQtyToPick(this.props);
    const pickFrom = getPickFrom(this.props);
    const isPickedFromHU = pickFrom.qtyPicked > 0;

    const scanButtonCaption = isPickedFromHU
      ? `${pickFrom.huBarcode}`
      : counterpart.translate('activities.picking.scanHUBarcode');

    const scanButtonStatus = isPickedFromHU ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
    const nothingPicked = !isPickedFromHU && !pickFrom.qtyRejectedReasonCode;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <div>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('general.Barcode')}
              </div>
              <div className="column is-half has-text-left pb-0">{pickFrom.huBarcode}</div>
            </div>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('general.QtyToPick')}:
              </div>
              <div className="column is-half has-text-left pb-0">{qtyToPick}</div>
            </div>
            <div className="mt-0">
              <button
                className="button is-outlined complete-btn"
                disabled={isPickedFromHU}
                onClick={this.onScanButtonClick}
              >
                <ButtonWithIndicator caption={scanButtonCaption} completeStatus={scanButtonStatus} />
              </button>
            </div>
            {/* Unpick button */}
            <div className="mt-5">
              <button
                className="button is-outlined complete-btn"
                disabled={nothingPicked}
                onClick={this.onUnpickButtonClick}
              >
                <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
              </button>
            </div>
            <NotFoundButton
              onNotFound={this.handleNotFound}
              disabled={!nothingPicked}
              {...{ wfProcessId, activityId, stepId, lineId }}
            />
          </div>
          <ScreenToaster />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = ownProps.match.params;

  const activity = selectWFProcessFromState(state, wfProcessId).activities[activityId];
  const stepProps = activity.dataStored.lines[lineId].steps[stepId];
  const appId = state.applications.activeApplication ? state.applications.activeApplication.id : null;

  return {
    appId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    stepProps,
  };
};

PickStepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  altStepId: PropTypes.string,
  //
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  pushHeaderEntry: PropTypes.func.isRequired,
  postStepUnPicked: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, { pushHeaderEntry, postStepUnPicked, push, updatePickingStepQty })(PickStepScreen)
);
