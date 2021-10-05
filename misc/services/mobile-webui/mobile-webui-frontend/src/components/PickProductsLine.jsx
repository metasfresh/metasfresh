import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
class PickProductsLine extends Component {
  /**
   *
   * @param {string} id of the line
   */
  handleClick = () => {
    const { wfProcessId, activityId, lineIndex, push } = this.props;
    push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineIndex}`);
  };

  render() {
    const { id, caption, isLinesListVisible } = this.props;
    return (
      <div>
        {isLinesListVisible && (
          <button key={id} className="button is-outlined complete-btn" onClick={() => this.handleClick()}>
            {caption}
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
};

export default connect(mapStateToProps, { push })(PickProductsLine);
