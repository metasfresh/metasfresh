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
          bold: true,
        },
      ],
    });
  };

  render() {
    const { lineId, caption, isUserEditable, completeStatus } = this.props;
    return (
      <div className="buttons">
        <button
          key={lineId}
          className="button is-outlined complete-btn"
          disabled={!isUserEditable}
          onClick={this.handleClick}
        >
          <ButtonWithIndicator caption={caption} completeStatus={completeStatus} />
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
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default connect(null, { push, pushHeaderEntry })(PickLineButton);
