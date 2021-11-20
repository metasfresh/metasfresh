import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { go } from 'connected-react-router';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
// import { updateManufacturingIssueQty } from '../../../actions/ManufacturingActions';

class ReceiptNewHUScreen extends PureComponent {
  handleClick = (target) => {
    // const { wfProcessId, activityId, lineId } = this.props;
    console.log('ID/CAPTION: ', target);

    go(-1);
  };

  render() {
    const { availableReceivingTargets } = this.props.lineProps;

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <div className="buttons">
            {availableReceivingTargets.values.map((target) => (
              <button
                key={target.luPIItemId}
                className="button is-outlined complete-btn"
                onClick={() => this.handleClick(target)}
              >
                <div className="full-size-btn">
                  <div className="left-btn-side"></div>
                  <div className="caption-btn">
                    <div className="rows">
                      <div className="row is-full pl-5">{target.caption}</div>
                    </div>
                  </div>
                </div>
              </button>
            ))}
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;
  const appId = state.applications.activeApplication ? state.applications.activeApplication.id : null;

  return {
    wfProcessId,
    activityId,
    lineId,
    lineProps,
    appId,
  };
};

ReceiptNewHUScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  lineId: PropTypes.string.isRequired,
  // dispatch: PropTypes.func.isRequired,
  // appId: PropTypes.string.isRequired,
};

export default withRouter(connect(mapStateToProps, { go })(ReceiptNewHUScreen));
