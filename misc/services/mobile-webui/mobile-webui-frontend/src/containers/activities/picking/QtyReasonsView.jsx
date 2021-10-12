import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import counterpart from 'counterpart';
import { get } from 'lodash';

import { selectWFProcess } from '../../../reducers/wfProcesses';

class QtyReasonsView extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      rejectedReason: '',
    };

    this.setRejectedReason = this.setRejectedReason.bind(this);
  }

  setRejectedReason(e) {
    this.setState({ rejectedReason: e.target.name });
  }

  onSubmit = () => {
    const { onHide } = this.props;

    onHide(this.state.rejectedReason);
  };

  render() {
    const { rejectedReasons } = this.props;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <div className="control">
            {rejectedReasons.map((reason, idx) => (
              <div key={idx} className="columns is-mobile">
                <div className="column is-full">
                  <label className="radio">
                    <input type="radio" name={reason.key} onChange={this.setRejectedReason} />
                    {reason.caption}
                  </label>
                </div>
              </div>
            ))}
          </div>
          <div className="buttons is-centered mt-4">
            <button
              className="button is-success confirm-button"
              disabled={!this.state.rejectedReason}
              onClick={this.onSubmit}
            >
              {counterpart.translate('activities.picking.confirmDone')}
            </button>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId } = ownProps.match.params;
  const workflowProcess = selectWFProcess(state, wfProcessId);
  const activity = workflowProcess.activities.length ? workflowProcess.activities[activityId - 1] : null;
  const rejectedReasons = get(activity, ['componentProps', 'qtyRejectedReasons', 'reasons'], []);

  return {
    rejectedReasons,
  };
};

QtyReasonsView.propTypes = {
  onHide: PropTypes.func.isRequired,
  rejectedReasons: PropTypes.array.isRequired,
};

export default withRouter(connect(mapStateToProps)(QtyReasonsView));
