import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import Indicator from '../../../components/Indicator';
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
    const { lineId, caption, uom, qtyPicked, qtyToPick, isUserEditable, completeStatus } = this.props;

    return (
      <div className="buttons">
        <button
          key={lineId}
          className="button is-outlined complete-btn"
          disabled={!isUserEditable}
          onClick={this.handleClick}
        >
          <div className="full-size-btn">
            <div className="left-btn-side" />
            <div className="caption-btn">
              <div className="rows">
                <div className="row is-full pl-5">{caption}</div>
                <div className="row is-full is-size-7">
                  <div className="picking-row-info">
                    <div className="picking-to-pick">To Pick:</div>
                    <div className="picking-row-qty">
                      {qtyToPick} {uom}
                    </div>
                    <div className="picking-row-picking">Picked:</div>
                    <div className="picking-row-picked">
                      {qtyPicked} {uom}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="right-btn-side pt-4">
              <Indicator completeStatus={completeStatus} />
            </div>
          </div>
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
  uom: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default connect(null, { push, pushHeaderEntry })(PickLineButton);
