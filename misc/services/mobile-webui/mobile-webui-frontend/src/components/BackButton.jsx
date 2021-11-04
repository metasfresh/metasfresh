import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

class BackButton extends Component {
  /**
   * Execute the function passed in when this button is clicked.
   */
  render() {
    const { onClickExec } = this.props;

    return (
      <span className="btn-icon" onClick={onClickExec}>
        <i className="icon-chevron-left-solid" />
      </span>
    );
  }
}

BackButton.propTypes = {
  onClickExec: PropTypes.func.isRequired,
};

export default connect(null, null)(BackButton);
