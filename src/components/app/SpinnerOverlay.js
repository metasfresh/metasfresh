import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { v4 as uuid } from 'uuid';

import { showSpinner, hideSpinner } from '../../actions/WindowActions';

class SpinnerOverlay extends Component {
  timeout = null;

  constructor(props) {
    super(props);

    this.state = {
      show: false,
    };
  }

  componentWillMount() {
    this.ID = uuid();
  }

  componentDidMount() {
    const { showSpinner, spinnerDisplayed, delay } = this.props;

    if (!spinnerDisplayed) {
      setTimeout(() => {
        this.setState({
          show: true,
        });
        showSpinner(this.ID);
      }, delay);
    }
  }

  componentWillUnmount() {
    const { hideSpinner, spinnerDisplayed } = this.props;

    if (spinnerDisplayed === this.ID) {
      this.setState({
        show: false,
      });
      hideSpinner();
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
        <i style={style} className="icon hourglass" />
      </div>
    );
  }
}

SpinnerOverlay.defaultProps = {
  delay: 1000,
  iconSize: 32,
};

SpinnerOverlay.propTypes = {
  iconSize: PropTypes.number,
  delay: PropTypes.number,
  spinnerDisplayed: PropTypes.string,
  showSpinner: PropTypes.func.isRequired,
  hideSpinner: PropTypes.func.isRequired,
};

const mapStateToProps = ({ windowHandler }) => ({
  spinnerDisplayed: windowHandler.spinner,
});

export default connect(mapStateToProps, {
  showSpinner,
  hideSpinner,
})(SpinnerOverlay);
