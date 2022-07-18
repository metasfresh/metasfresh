import React from 'react';
import PropTypes from 'prop-types';

const ConflictsSummary = ({ conflictsCount }) => {
  //
  if (conflictsCount <= 0) {
    return null;
  }

  return (
    <span className="conflicts-summary">
      &#9888; {conflictsCount} conflicts
    </span>
  );
};

ConflictsSummary.propTypes = {
  conflictsCount: PropTypes.number.isRequired,
};

export default ConflictsSummary;
