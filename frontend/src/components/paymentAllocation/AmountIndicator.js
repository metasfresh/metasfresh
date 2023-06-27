import React from 'react';
import PropTypes from 'prop-types';

const AmountIndicator = ({ caption, value }) => {
  if (!value) {
    return null;
  }

  return (
    <span className="amount-indicator">
      <p className="caption">{caption}:</p>
      <p className="value">{value}</p>
    </span>
  );
};

AmountIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  value: PropTypes.string,
};

export default AmountIndicator;
