import React from 'react';
import PropTypes from 'prop-types';

import './ConflictsSummary.scss';

const ConflictsSummary = ({ conflictsCount }) => {
  //
  if (conflictsCount <= 0) {
    return null;
  }

  return (
    <div className="conflicts-summary">&#9888; {conflictsCount} conflicts</div>
  );
};

ConflictsSummary.propTypes = {
  conflictsCount: PropTypes.number.isRequired,
};

export default ConflictsSummary;
