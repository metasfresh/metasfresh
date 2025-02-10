import React from 'react';
import PropTypes from 'prop-types';

import './CalendarEvent.scss';

export const getEventClassNames = (params) => {
  if (params.event.extendedProps.conflict) {
    return ['has-conflict'];
  }
};

export const renderEventContent = (params) => {
  return (
    <CalendarEvent
      title={params.event.title}
      help={params.event?.extendedProps?.help}
    />
  );
};

const CalendarEvent = ({ title, help }) => {
  return (
    <div className="calendar-event">
      <span title={help}>{title}</span>
    </div>
  );
};

CalendarEvent.propTypes = {
  title: PropTypes.string.isRequired,
  help: PropTypes.string,
};
