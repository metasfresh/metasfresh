import React from 'react';
import PropTypes from 'prop-types';
import SimpleList from '../widget/List/SimpleList';

const toKeyCaption = (calendar) => {
  return calendar ? { key: calendar.calendarId, caption: calendar.name } : null;
};

const CalendarsDropDown = ({ calendars, selectedCalendar, onSelect }) => {
  const calendarsById = calendars.reduce((accum, calendar) => {
    accum[calendar.calendarId] = calendar;
    return accum;
  }, {});

  const handleOnSelect = (calendarKeyCaptionEntry) => {
    const calendar = calendarKeyCaptionEntry
      ? calendarsById[calendarKeyCaptionEntry.key]
      : null;
    onSelect(calendar);
  };

  return (
    <SimpleList
      list={calendars.map(toKeyCaption)}
      selected={toKeyCaption(selectedCalendar)}
      onSelect={handleOnSelect}
    />
  );
};

CalendarsDropDown.propTypes = {
  calendars: PropTypes.array.isRequired,
  selectedCalendar: PropTypes.object,
  onSelect: PropTypes.func.isRequired,
};

export default CalendarsDropDown;
