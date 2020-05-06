import React from 'react';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module BlankPage
 * @extends Component
 */
const BlankPage = ({ what }) => (
  <div className="blank-page">
    <h1>404</h1>
    <h3>{what ? what + ' not found.' : 'Not found'}</h3>
  </div>
);

/**
 * @typedef {object} Props Component props
 * @prop {string} what
 */
BlankPage.propTypes = {
  what: PropTypes.any,
};

export default BlankPage;
