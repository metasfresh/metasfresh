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
    const { showSpinner, spinnerDisplayed } = this.props;

    if (!spinnerDisplayed) {
      setTimeout(() => {
        this.setState({
          show: true,
        });

        showSpinner(this.ID);
      }, this.props.delay);
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
    const { show } = this.state;

    if (!show) {
      return null;
    }

    return (
      <div className="screen-freeze screen-prompt-freeze spinner">
        <i className="icon hourglass" />
      </div>
    );
  }
}

SpinnerOverlay.defaultProps = {
  delay: 3000,
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
