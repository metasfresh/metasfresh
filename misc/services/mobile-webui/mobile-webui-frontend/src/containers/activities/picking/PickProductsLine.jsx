import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

class PickProductsLine extends Component {
  handleClick = () => {
    const { wfProcessId, activityId, lineIndex, push } = this.props;
    push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineIndex}`);
  };

  render() {
    const { id, caption, isLinesListVisible, isActivityEnabled } = this.props;
    return (
      <div className="buttons">
        {isLinesListVisible && (
          <button
            key={id}
            className="button is-outlined complete-btn"
            disabled={!isActivityEnabled}
            onClick={() => this.handleClick()}
          >
            <ButtonWithIndicator caption={caption} indicatorType="incomplete" />
          </button>
        )}
      </div>
    );
  }
}

const mapStateToProps = () => {
  return {
    isLinesListVisible: true,
  };
};

PickProductsLine.propTypes = {
  id: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  isLinesListVisible: PropTypes.bool,
  steps: PropTypes.array.isRequired,
  lineIndex: PropTypes.number.isRequired,
  push: PropTypes.func.isRequired,
  isActivityEnabled: PropTypes.bool,
};

export default connect(mapStateToProps, { push })(PickProductsLine);
