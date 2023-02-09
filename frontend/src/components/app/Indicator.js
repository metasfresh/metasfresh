import React from 'react';
import PropTypes from 'prop-types';

/**
 * Indicator is a component that shows the save status to user in form of a save progress line beneath the Header.
 */
const Indicator = ({ indicator, isDocumentNotSaved }) => {
  const className = isDocumentNotSaved
    ? 'indicator-error'
    : `indicator-${indicator}`;

  return (
    <div>
      <div className={`indicator-bar ${className}`} />
    </div>
  );
};

Indicator.propTypes = {
  indicator: PropTypes.oneOf(['pending', 'saving', 'saved', 'error'])
    .isRequired,
  isDocumentNotSaved: PropTypes.bool,
};

export default Indicator;
