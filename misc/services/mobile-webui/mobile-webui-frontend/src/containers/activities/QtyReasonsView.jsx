import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import counterpart from 'counterpart';
import { get } from 'lodash';

import { selectWFProcessFromState } from '../../reducers/wfProcesses_status';
import QtyReasonsRadioGroup from '../../components/QtyReasonsRadioGroup';

class QtyReasonsView extends PureComponent {
  constructor(props) {
    super(props);

    this.state = { rejectedReason: '' };
  }

  onRejectedReasonSelected = (rejectedReason) => {
    this.setState({ rejectedReason });
  };

  onSubmit = () => {
    const { onHide } = this.props;

    onHide(this.state.rejectedReason);
  };

  render() {
    const { rejectedReason } = this.state;
    const { rejectedReasons, uom, qtyRejected } = this.props;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="content">
          <h5>{`${counterpart.translate('activities.picking.rejectedPrompt', { qtyRejected, uom })}`}</h5>
        </div>
        <div className="picking-step-details centered-text is-size-5">
          <QtyReasonsRadioGroup reasons={rejectedReasons} onReasonSelected={this.onRejectedReasonSelected} />
          <div className="buttons is-centered mt-4">
            <button
              className="button is-medium btn-green confirm-button"
              disabled={!rejectedReason}
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
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess.activities[activityId];
  const rejectedReasons = get(activity, ['componentProps', 'qtyRejectedReasons', 'reasons'], []);

  return {
    rejectedReasons,
  };
};

QtyReasonsView.propTypes = {
  rejectedReasons: PropTypes.array.isRequired,
  uom: PropTypes.string.isRequired,
  qtyRejected: PropTypes.number.isRequired,
  //
  onHide: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps)(QtyReasonsView));
