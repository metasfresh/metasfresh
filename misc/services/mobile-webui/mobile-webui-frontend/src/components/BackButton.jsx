import React, { Component } from 'react';
import PropTypes from 'prop-types';

class BackButton extends Component {
  /**
   * Execute the function passed in when this button is clicked.
   */
  handleClick = () => {
    const { onClickExec } = this.props;
    onClickExec();
  };

  render() {
    const btnCaption = this.props.caption ? this.props.caption : 'Back';
    return (
      <button className="button" onClick={this.handleClick}>
        {btnCaption}
      </button>
    );
  }
}

BackButton.propTypes = {
  caption: PropTypes.string,
  onClickExec: PropTypes.func.isRequired,
};

export default BackButton;
