import React, { Component } from 'react';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module BlankPage
 * @extends Component
 */
class BlankPage extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * @method render
   * @summary ToDo: Describe the method.
   * @todo Write the documentation
   */
  render() {
    const { what } = this.props;

    return (
      <div className="blank-page">
        <h1>404</h1>
        <h3>{what ? what + ' not found.' : 'Not found'}</h3>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} what
 */
BlankPage.propTypes = {
  what: PropTypes.any,
};

export default BlankPage;
