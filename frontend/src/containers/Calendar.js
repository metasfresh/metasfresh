import React, { useEffect } from 'react';
import PropTypes from 'prop-types';

import * as api from '../api/calendar';

import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from '@fullcalendar/timegrid';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';

import '@fullcalendar/common/main.css';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';
import CalendarAddEvent from '../components/calendar/CalendarAddEvent';

const Calendar = ({ className = 'container' }) => {
  const [calendarEvents, setCalendarEvents] = React.useState([]);
  const [calendarResources, setCalendarResources] = React.useState([]);
  const [addEventRequest, setAddEventRequest] = React.useState(null);

  useEffect(() => {
    api.getAvailableCalendars().then(setCalendarResources);
  }, []);
  useEffect(() => {
    api.getCalendarEvents({}).then(setCalendarEvents);
  }, []);

  const handleDateClick = (params) => {
    console.log('handleDateClick', { params });
    setAddEventRequest({
      date: params.date,
      allDay: true,
    });
  };

  const handleAddEventOK = (event) => {
    console.log('handleAddEventOK', { event });
    api
      .addCalendarEvent({
        calendarId: event.resourceId,
        startDate: event.start,
        endDate: event.end,
        title: event.title,
      })
      .then((eventFromBackend) => {
        console.log('handleAddEventOK: got from backend', { eventFromBackend });
        setCalendarEvents([...calendarEvents, eventFromBackend]);
        setAddEventRequest(null);
      });
  };
  const handleAddEventCancel = (event) => {
    console.log('handleAddEventCancel', { event });
    setAddEventRequest(null);
  };

  console.log('render:', { calendarResources, calendarEvents });
  return (
    <div className={className}>
      {addEventRequest && (
        <CalendarAddEvent
          calendars={calendarResources.map((resource) => ({
            key: resource.id,
            caption: resource.title,
          }))}
          date={addEventRequest.date}
          allDay={addEventRequest.allDay}
          onAddEvent={handleAddEventOK}
          onCancel={handleAddEventCancel}
        />
      )}
      <FullCalendar
        defaultView="resourceTimeline"
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
        resources={calendarResources}
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
