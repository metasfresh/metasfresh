import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import PickProductsSteps from './containers/PickProductsSteps';
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
    const { id, activityId, wfProcessId, caption, isLinesListVisible, steps, lineIndex } = this.props;
    return (
      <div>
        {isLinesListVisible && (
          <div key={id} className="ml-3 mr-3 is-light pick-product" onClick={() => this.handleClick()}>
            <div className="box">
              <div className="columns is-mobile">
                <div className="column is-12">
                  <div className="columns">
                    <div className="column is-size-4-mobile no-p">{caption}</div>
                    <div className="column is-size-7 no-p">-line-</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}
        {/* Steps Listing */}
        {!isLinesListVisible && (
          <PickProductsSteps steps={steps} activityId={activityId} wfProcessId={wfProcessId} lineIndex={lineIndex} />
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
