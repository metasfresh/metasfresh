import React, { useEffect } from 'react';
import PropTypes from 'prop-types';

import * as api from '../api/calendar';

import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from '@fullcalendar/timegrid';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

import '@fullcalendar/common/main.css';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';

const Calendar = ({ className = 'container' }) => {
  const [calendarEvents, setCalendarEvents] = React.useState([]);

  useEffect(() => {
    api
      .getCalendarEntries({})
      .then(convertCalendarEntriesFromApi)
      .then(setCalendarEvents);
  }, []);

  const handleDateClick = (params) => {
    if (confirm(`Would you like to add an event to ${params.dateStr}?`)) {
      setCalendarEvents(
        calendarEvents.concat({
          title: 'New Event',
          start: params.date,
          allDay: params.allDay,
        })
      );
    }
  };

  return (
    <div className={className}>
      <FullCalendar
        defaultView="dayGridMonth"
        header={{
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
        }}
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        weekends="true"
        events={calendarEvents}
        dateClick={handleDateClick}
      />
    </div>
  );
};

Calendar.propTypes = {
  className: PropTypes.string,
};

const convertCalendarEntriesFromApi = (apiResponse) => {
  return apiResponse.entries.map((entry) => ({
    title: entry.title,
    start: entry.startDate,
    end: entry.endDate,
  }));
};

export default Calendar;
