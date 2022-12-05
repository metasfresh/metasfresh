import React from 'react';
import PropTypes from 'prop-types';

import './CalendarEvent.scss';

export const getEventClassNames = (params) => {
  if (params.event.extendedProps.conflict) {
    return ['has-conflict'];
  }
};

export const renderEventContent = (params) => {
  return <CalendarEvent title={params.event.title} />;
};

const CalendarEvent = ({ title }) => {
  return <div className="calendar-event">{title}</div>;
};

CalendarEvent.propTypes = {
  title: PropTypes.string.isRequired,
};
