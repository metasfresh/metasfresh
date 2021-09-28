import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { switchoffLinesVisibility } from '../actions/PickingActions';
import PickProductsSteps from './containers/PickProductsSteps';
class PickProductsLine extends Component {
  /**
   *
   * @param {string} id of the line
   */
  handleClick = (id) => {
    const { wfProcessId, switchoffLinesVisibility } = this.props;
    switchoffLinesVisibility({ wfProcessId, activityId: id });
  };

  render() {
    const { id, activityId, caption, isLinesListVisible, steps } = this.props;
    console.log('isLINe:', isLinesListVisible);
    return (
      <div>
        <div key={id} className="ml-3 mr-3 is-light pick-product" onClick={() => this.handleClick(activityId)}>
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
        {/* Steps Listing */}
        {/* {!isLinesListVisible && <PickProductsSteps />} */}
        <PickProductsSteps steps={steps} />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    isLinesListVisible:
      state.wfProcesses_status[ownProps.wfProcessId].activities[ownProps.activityId].isLinesListVisible || true,
  };
};

PickProductsLine.propTypes = {
  id: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  switchoffLinesVisibility: PropTypes.func.isRequired,
  isLinesListVisible: PropTypes.bool.isRequired,
  steps: PropTypes.array.isRequired,
};

export default connect(mapStateToProps, { switchoffLinesVisibility })(PickProductsLine);
