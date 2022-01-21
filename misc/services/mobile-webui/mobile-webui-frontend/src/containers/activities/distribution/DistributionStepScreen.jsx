import React, { PureComponent } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator_OLD';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import {
  distributionStepDropToScreenLocation,
  distributionStepPickFromScreenLocation,
  distributionStepScreenLocation,
} from '../../../routes/distribution';

const HIDE_UNDO_BUTTONS = true; // hide them because they are not working

class DistributionStepScreen extends PureComponent {
  componentDidMount() {
    const {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { pickFromLocator },
      dispatch,
    } = this.props;
    const location = distributionStepScreenLocation({ wfProcessId, activityId, lineId, stepId });

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Locator'),
            value: pickFromLocator.caption,
          },
        ],
      })
    );
  }

  onScanPickFromHU = () => {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;
    const location = distributionStepPickFromScreenLocation({
      wfProcessId,
      activityId,
      lineId,
      stepId,
    });

    dispatch(push(location));
  };

  onScanDropToLocator = () => {
    const {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { qtyToMove, dropToLocator },
      dispatch,
    } = this.props;

    const location = distributionStepDropToScreenLocation({
      wfProcessId,
      activityId,
      lineId,
      stepId,
    });
    dispatch(push(location));
    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.DropToLocator'),
            value: dropToLocator.caption + ' (' + dropToLocator.barcode + ')',
          },
          {
            caption: counterpart.translate('general.QtyToMove'),
            value: qtyToMove,
          },
        ],
      })
    );
  };

  render() {
    const {
      stepProps: {
        qtyToMove,
        //
        // Pick From:
        isPickedFrom,
        pickFromHU,
        qtyPicked,
        //
        // Drop To
        droppedToLocator: isDroppedToLocator,
        dropToLocator,
      },
    } = this.props;

    console.log(this.props);

    const pickFromHUCaption = isPickedFrom
      ? pickFromHU.caption
      : counterpart.translate('activities.distribution.scanHU');
    const pickFromHUStatus = isPickedFrom ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

    const dropToLocatorCaption = isDroppedToLocator
      ? dropToLocator.caption
      : counterpart.translate('activities.distribution.scanLocator');
    const dropToLocatorStatus = isDroppedToLocator ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('activities.distribution.scanHU')}
            </div>
            <div className="column is-half has-text-left pb-0">{pickFromHU.barcode}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('general.QtyToMove')}:
            </div>
            <div className="column is-half has-text-left pb-0">{qtyToMove}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('general.QtyPicked')}:
            </div>
            <div className="column is-half has-text-left pb-0">{qtyPicked}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('general.DropToLocator')}
            </div>
            <div className="column is-half has-text-left pb-0">{dropToLocator.caption}</div>
          </div>

          <div className="mt-0">
            <button className="button is-outlined complete-btn" disabled={isPickedFrom} onClick={this.onScanPickFromHU}>
              <ButtonWithIndicator caption={pickFromHUCaption} completeStatus={pickFromHUStatus} />
            </button>
          </div>

          {!HIDE_UNDO_BUTTONS && (
            <div className="mt-5">
              <button
                className="button is-outlined complete-btn"
                disabled={!(isPickedFrom && !isDroppedToLocator)}
                onClick={this.onUndoPickFromHU}
              >
                <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
              </button>
            </div>
          )}

          <div className="mt-5">
            <button
              className="button is-outlined complete-btn"
              disabled={!(isPickedFrom && !isDroppedToLocator)}
              onClick={this.onScanDropToLocator}
            >
              <ButtonWithIndicator caption={dropToLocatorCaption} completeStatus={dropToLocatorStatus} />
            </button>
          </div>
          {!HIDE_UNDO_BUTTONS && (
            <div className="mt-5">
              <button
                className="button is-outlined complete-btn"
                disabled={!isDroppedToLocator}
                onClick={this.onUndoDropToLocator}
              >
                <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
              </button>
            </div>
          )}
        </div>
      </div>
    );
  }
}

DistributionStepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;

  const activity = selectWFProcessFromState(state, wfProcessId).activities[activityId];
  const stepProps = activity.dataStored.lines[lineId].steps[stepId];

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    stepProps,
  };
};

export default withRouter(connect(mapStateToProps)(DistributionStepScreen));
