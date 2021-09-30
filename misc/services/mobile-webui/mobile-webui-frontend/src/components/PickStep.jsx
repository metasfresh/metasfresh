import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { updatePickingStepQty } from '../actions/PickingActions';
import { push } from 'connected-react-router';
class PickStep extends PureComponent {
  constructor(props) {
    super(props);
  }

  handleClick = () => {
    const { wfProcessId, activityId, lineIndex, stepId, push } = this.props;
    push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineIndex}/stepId/${stepId}`);
  };

  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  render() {
    const { id, locatorName, productName, uom, pickstepState, qtyToPick } = this.props;
    const { qtyPicked } = pickstepState;
    return (
      <div>
        <div key={id} className="ml-3 mr-3 is-light launcher" onClick={() => this.handleClick(id)}>
          <div className="box">
            <div className="columns is-mobile">
              <div className="column is-12">
                <div className="columns">
                  <div className="column is-size-4-mobile no-p">Product: {productName}</div>
                  <div className="column is-size-7 no-p">
                    To Pick: <span className="has-text-weight-bold">{qtyToPick}</span> Quantity picked:{' '}
                    <span className="has-text-weight-bold">{qtyPicked}</span> UOM: {uom} Locator Name: {locatorName}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId, lineIndex, stepId } = ownProps;
  return {
    pickstepState:
      state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineIndex].steps[stepId],
  };
};

PickStep.propTypes = {
  id: PropTypes.string.isRequired,
  locatorName: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number,
  qtyToPick: PropTypes.number.isRequired,
  uom: PropTypes.string,
  updatePickingStepQty: PropTypes.func.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  stepId: PropTypes.number.isRequired,
  lineIndex: PropTypes.number.isRequired,
  pickstepState: PropTypes.object,
  huBarcode: PropTypes.string,
  push: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { updatePickingStepQty, push })(PickStep);
