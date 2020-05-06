import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import uuid from 'uuid/v4';

import { showSpinner, hideSpinner } from '../../actions/WindowActions';

/**
 * @file Class based component.
 * @module SpinnerOverlay
 * @extends Component
 */
class SpinnerOverlay extends Component {
  timeout = null;

  constructor(props) {
    super(props);

    this.state = {
      show: false,
    };
  }

  UNSAFE_componentWillMount() {
    this.ID = uuid();
  }

  componentDidMount() {
    const { showSpinner, spinnerDisplayed, delay, parent } = this.props;

    if (this.props.displayCondition && !spinnerDisplayed && !this.timeout) {
      this.timeout = setTimeout(() => {
        this.setState({
          show: true,
        });

        showSpinner(this.ID);

        parent.forceUpdate();
      }, delay);
    }
  }

  /**
   * @method renderCancelButton
   * @summary This whole wizardry here is to force showing the spinner, when timeout
   * already finished but the parent component is not updating (because waiting
   * for data or doing some other operations).
   */
  componentDidUpdate() {
    const {
      displayCondition,
      spinnerDisplayed,
      hideSpinner,
      showSpinner,
      delay,
      parent,
    } = this.props;

    if (!displayCondition && spinnerDisplayed) {
      if (spinnerDisplayed === this.ID) {
        this.setState({
          show: false,
        });
        this.timeout = null;

        hideSpinner();
      }
    } else if (displayCondition && !spinnerDisplayed && !this.timeout) {
      this.timeout = setTimeout(() => {
        this.setState({
          show: true,
        });

        showSpinner(this.ID);

        parent.forceUpdate();
      }, delay);
    }
  }

  render() {
    const { iconSize } = this.props;
    const { show } = this.state;
    let style = {};

    if (!show) {
      return null;
    }

    if (iconSize) {
      style = {
        width: `${iconSize}px`,
        height: `${iconSize}px`,
      };
    }

    return (
      <div className="screen-freeze screen-prompt-freeze spinner">
        <i style={style} className="icon spinner" />
      </div>
    );
  }
}

SpinnerOverlay.defaultProps = {
  delay: 200,
  iconSize: 32,
};

/**
 * @typedef {object} Props Component props
 * @prop {*} parent
 * @prop {bool} displayCondition
 * @prop {bool} hideCondition
 * @prop {number} [iconSize]
 * @prop {number} [delay]
 * @prop {string} [spinnerDisplayed]
 * @prop {func} [showSpinner]
 * @prop {func} [hideSpinner]
 * @todo Check props. Which proptype? Required or optional?
 */
SpinnerOverlay.propTypes = {
  parent: PropTypes.any.isRequired,
  displayCondition: PropTypes.bool.isRequired,
  hideCondition: PropTypes.bool.isRequired,
  iconSize: PropTypes.number,
  delay: PropTypes.number,
  spinnerDisplayed: PropTypes.string,
  showSpinner: PropTypes.func.isRequired,
  hideSpinner: PropTypes.func.isRequired,
};

const mapStateToProps = ({ windowHandler }) => ({
  spinnerDisplayed: windowHandler.spinner,
});

export default connect(
  mapStateToProps,
  {
    showSpinner,
    hideSpinner,
  }
)(SpinnerOverlay);
