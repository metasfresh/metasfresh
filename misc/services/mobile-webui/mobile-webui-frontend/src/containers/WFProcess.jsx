import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { continueWorkflow } from '../actions/WorkflowActions';
import { postScannedBarcode } from '../api/scanner';

import ViewHeader from '../containers/ViewHeader';
import CodeScanner from '../components/containers/CodeScanner';
import ConfirmActivity from '../components/containers/ConfirmActivity';
import PickProductsActivity from '../components/containers/PickProductsActivity';
import { stopScanning } from '../actions/ScanActions';
import toast, { Toaster } from 'react-hot-toast';

class WorkflowProcess extends PureComponent {
  componentDidMount() {
    const { wfProcessId, activities, continueWorkflow } = this.props;

    if (!activities.length) {
      continueWorkflow(wfProcessId);
    }
  }

  scanActivityPostDetection = ({ detectedCode, activityId }) => {
    const { wfProcessId, token, stopScanning } = this.props;
    postScannedBarcode({ detectedCode, wfProcessId, activityId, token })
      .then(() => {
        toast('Successful scanning!', { type: 'success', style: { color: 'white' } });
      })
      .catch(() => {
        toast('Scanned code is invalid!', { type: 'error', style: { color: 'white' } });
      });
    stopScanning();
  };

  render() {
    const { wfProcessId, activities, status } = this.props;
    const { activities: activitiesState } = status;

    return (
      <div className="container pick-products-container">
        <ViewHeader />
        <div className="activities">
          {activities.length > 0 &&
            activities.map((activityItem) => {
              let uniqueId = uuidv4();

              switch (activityItem.componentType) {
                case 'common/scanBarcode':
                  return (
                    <CodeScanner
                      key={uniqueId}
                      id={uniqueId}
                      {...activityItem}
                      onDetection={this.scanActivityPostDetection}
                    />
                  );
                case 'picking/pickProducts':
                  return (
                    <PickProductsActivity
                      key={uniqueId}
                      id={uniqueId}
                      wfProcessId={wfProcessId}
                      activityId={activityItem.activityId}
                      activityState={activitiesState[activityItem.activityId]}
                      {...activityItem}
                    />
                  );
                case 'common/confirmButton':
                  return <ConfirmActivity key={uniqueId} id={uniqueId} wfProcessId={wfProcessId} {...activityItem} />;
              }
            })}
        </div>
        <Toaster
          position="bottom-center"
          toastOptions={{
            success: {
              style: {
                background: 'green',
              },
            },
            error: {
              style: {
                background: 'red',
              },
            },
          }}
        />
      </div>
    );
  }
}

function mapStateToProps(state, { match }) {
  const { workflowId } = match.params;
  // TODO: We need a selector for this
  const workflow = state.wfProcesses[workflowId];
  const activities = workflow ? workflow.activities : [];
  const status = state.wfProcesses_status[workflowId] || { activities: [] };

  return {
    wfProcessId: workflowId,
    activities,
    status,
    token: state.appHandler.token,
  };
}

WorkflowProcess.propTypes = {
  activities: PropTypes.array,
  status: PropTypes.object,
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  continueWorkflow: PropTypes.func.isRequired,
  token: PropTypes.string,
  stopScanning: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { continueWorkflow, stopScanning })(WorkflowProcess));
