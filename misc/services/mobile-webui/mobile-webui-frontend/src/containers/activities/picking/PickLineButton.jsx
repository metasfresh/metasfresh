import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import counterpart from 'counterpart';

class PickLineButton extends Component {
  handleClick = () => {
    const { wfProcessId, activityId, lineId, caption } = this.props;
    const { push, pushHeaderEntry } = this.props;

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;
    push(location);
    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('activities.picking.PickingLine'),
          value: caption,
        },
      ],
    });
  };

  render() {
    const { lineId, caption, isActivityEnabled, isComplete } = this.props;
    const indicatorType = isComplete ? 'complete' : 'incomplete';
    return (
      <div className="buttons">
        <button
          key={lineId}
          className="button is-outlined complete-btn"
          disabled={!isActivityEnabled}
          onClick={this.handleClick}
        >
          <ButtonWithIndicator caption={caption} indicatorType={indicatorType} />
        </button>
      </div>
    );
  }
}

PickLineButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isActivityEnabled: PropTypes.bool.isRequired,
  isComplete: PropTypes.bool.isRequired,
  steps: PropTypes.array.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default connect(null, { push, pushHeaderEntry })(PickLineButton);
