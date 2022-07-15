import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

const CalendarResourceLabel = ({ title, conflict }) => {
  return (
    <span className={cx('fc-resource-label', { 'has-conflict': conflict })}>
      {title}
      {conflict && <span className="warning-indicator">&nbsp;&#9888;</span>}
    </span>
  );
};

CalendarResourceLabel.propTypes = {
  title: PropTypes.string.isRequired,
  conflict: PropTypes.bool,
};

export default CalendarResourceLabel;
