import PropTypes from 'prop-types';
import React from 'react';

/**
 * @file Indicator is a component that shows the save status to user in form of a save progress
 * line beneath the Header.
 * @module Indicator
 */
const Indicator = ({ indicator, isDocumentNotSaved }) => (
  <div>
    <div
      className={`indicator-bar ${
        isDocumentNotSaved ? 'indicator-error' : ''
      } ${!isDocumentNotSaved ? `indicator-${indicator}` : ''}`}
    />
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
};

export default Indicator;
