import PropTypes from 'prop-types';
import React from 'react';

/**
 * @file Indicator is a component that shows the save status to user in form of a save progress
 * line beneath the Header.
 * @module Indicator
 */
const Indicator = ({ indicator, isDocumentNotSaved, error }) => (
  <div>
    <div
      className={`indicator-bar ${
        isDocumentNotSaved ? 'indicator-error' : ''
      } ${!isDocumentNotSaved ? `indicator-${indicator}` : ''}`}
    />
    {error ? (
      <div className="container-fluid indicator-error-message" title={error}>
        {error}
      </div>
    ) : (
      <div className="container-fluid indicator-error-message">&nbsp;</div>
    )}
  </div>
);

/**
 * @typedef {object} Props Component props
 * @prop {string} indicator
 * @prop {bool} [isDocumentNotSaved]
 */
Indicator.propTypes = {
  indicator: PropTypes.string.isRequired,
  isDocumentNotSaved: PropTypes.bool,
  error: PropTypes.string,
};

export default Indicator;
