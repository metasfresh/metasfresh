import React, { useEffect } from 'react';
import PropTypes from 'prop-types';

import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from '@fullcalendar/timegrid';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';

import '@fullcalendar/common/main.css';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';

import * as api from '../api/calendar';
import CalendarAddEvent from '../components/calendar/CalendarAddEvent';

const Calendar = ({ className = 'container' }) => {
  const [calendarEvents, setCalendarEvents] = React.useState([]);
  const [calendars, setCalendars] = React.useState([]);
  const [addEventRequest, setAddEventRequest] = React.useState(null);

  useEffect(() => {
    api.getAvailableCalendars().then(setCalendars);
    api.getCalendarEvents({}).then(setCalendarEvents);
  }, []);

  const handleDateClick = (params) => {
    setAddEventRequest({
      date: params.date,
      allDay: true,
    });
  };

  const handleAddEventOK = (event) => {
    api
      .addCalendarEvent({
        calendarId: event.calendarId,
        resourceId: event.resourceId,
        startDate: event.start,
        endDate: event.end,
        title: event.title,
      })
      .then((eventFromBackend) => {
        setCalendarEvents([...calendarEvents, eventFromBackend]);
        setAddEventRequest(null);
      });
  };
  const handleAddEventCancel = () => {
    setAddEventRequest(null);
  };

  const resources = api.extractResourcesFromCalendarsArray(calendars);

  return (
    <div className={className}>
      {addEventRequest && (
        <CalendarAddEvent
          calendars={calendars}
          date={addEventRequest.date}
          allDay={addEventRequest.allDay}
          onAddEvent={handleAddEventOK}
          onCancel={handleAddEventCancel}
        />
      )}
      <FullCalendar
        schedulerLicenseKey="GPL-My-Project-Is-Open-Source"
        initialView="resourceTimeline"
        plugins={[
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin,
          resourceTimelinePlugin,
        ]}
        weekends="true"
        editable="true"
        headerToolbar={{
          left: 'prev,next today',
          center: 'title',
          right:
            'dayGridMonth resourceTimelineDay,resourceTimelineWeek,resourceTimelineMonth',
        }}
        resourceAreaHeaderContent="Resources"
        resources={resources}
        events={calendarEvents}
        dateClick={handleDateClick}
      />
    </div>
  );
};

Calendar.propTypes = {
  className: PropTypes.string,
};

export default Calendar;
