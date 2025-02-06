import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

const CalendarResourceLabel = ({ title, conflictsCount }) => {
  //console.log('ctor', { title, conflictsCount });
  const hasConflicts = conflictsCount > 0;
  return (
    <span className={cx('fc-resource-label', { 'has-conflict': hasConflicts })}>
      {title}
      {hasConflicts && (
        <span className="warning-indicator">
          &nbsp;| &#9888;{conflictsCount}
        </span>
      )}
    </span>
  );
};

CalendarResourceLabel.propTypes = {
  title: PropTypes.string.isRequired,
  conflictsCount: PropTypes.number,
};

export default CalendarResourceLabel;
