import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import { postStepUnPicked, postStepPicked } from '../../../api/picking';
import { toastError } from '../../../utils/toast';
import { getPickFrom, getQtyToPick } from '../../../utils/picking';
import {
  pickingStepScreenLocation,
  pickingStepScanScreenLocation,
  pickingLineScreenLocation,
} from '../../../routes/picking';
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
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
      stepProps: { mainPickFrom },
      pushHeaderEntry,
    } = this.props;
    const location = pickingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId, altStepId });

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
    const { wfProcessId, activityId, lineId, stepId, updatePickingStepQty, altStepId, stepProps } = this.props;
    const qtyPicked = getPickFrom({ stepProps, altStepId }).qtyPicked;

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
    const { applicationId, wfProcessId, activityId, lineId, stepId, altStepId, stepProps, push, updatePickingStepQty } =
      this.props;
    const location = pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId });

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: getPickFrom({ stepProps, altStepId }).huBarcode,
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
        push(location);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  handleNotFound = () => {
    const { wfProcessId, stepId, altStepId, lineId, activityId, updatePickingStepQty, stepProps } = this.props;
    const huBarcode = getPickFrom({ stepProps, altStepId }).huBarcode;
    const qtyRejected = getQtyToPick({ stepProps, altStepId });

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
    const { applicationId, wfProcessId, activityId, lineId, stepId, altStepId } = this.props;
    const { push } = this.props;

    const location = pickingStepScanScreenLocation({
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
    });

    push(location);
  };

  render() {
    const { wfProcessId, activityId, lineId, stepId, altStepId, stepProps } = this.props;
    const qtyToPick = getQtyToPick({ stepProps, altStepId });
    const pickFrom = getPickFrom({ stepProps, altStepId });
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
  const { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = ownProps.match.params;

  const activity = selectWFProcessFromState(state, wfProcessId).activities[activityId];
  const stepProps = activity.dataStored.lines[lineId].steps[stepId];

  return {
    applicationId,
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
  applicationId: PropTypes.string.isRequired,
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
  push: PropTypes.func.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { pushHeaderEntry, push, updatePickingStepQty })(PickStepScreen));
