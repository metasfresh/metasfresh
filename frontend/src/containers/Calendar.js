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

import CalendarEventEditor from '../components/calendar/CalendarEventEditor';

import * as api from '../api/calendar';

const extractResourcesFromCalendarsArray = (calendars) => {
  const resourcesById = calendars
    .flatMap((calendar) => calendar.resources)
    .reduce((accum, resource) => {
      accum[resource.id] = resource;
      return accum;
    }, {});

  return Object.values(resourcesById);
};

const mergeCalendarEventToArray = (eventsArray, newEvent) => {
  let added = false;
  const result = [];
  for (const event of eventsArray) {
    if (event.id === newEvent.id) {
      result.push(newEvent);
      added = true;
    } else {
      result.push(event);
    }
  }

  if (!added) {
    result.push(newEvent);
  }

  return result;
};

const Calendar = ({ className = 'container' }) => {
  const [calendarEvents, setCalendarEvents] = React.useState([]);
  const [availableCalendars, setAvailableCalendars] = React.useState([]);
  const [editingEvent, setEditingEvent] = React.useState(null);

  useEffect(() => {
    api.getAvailableCalendars().then(setAvailableCalendars);
    api.getCalendarEvents({}).then(setCalendarEvents);
  }, []);

  const handleCreateNewEvent = (params) => {
    setEditingEvent({
      start: params.date,
      end: params.date,
      allDay: true,
    });
  };

  const handleEditEvent = (params) => {
    const eventId = params.event.id;
    const event = calendarEvents.find((event) => event.id === eventId);
    setEditingEvent(event);
  };

  const handleEventEditOK = (event) => {
    api
      .addOrUpdateCalendarEvent({
        id: event.id,
        calendarId: event.calendarId,
        resourceId: event.resourceId,
        startDate: event.start,
        endDate: event.end,
        title: event.title,
      })
      .then((eventFromBackend) => {
        const newCalendarEvents = mergeCalendarEventToArray(
          calendarEvents,
          eventFromBackend
        );
        setCalendarEvents(newCalendarEvents);
        setEditingEvent(null);
      });
  };

  const handleEventDelete = (eventId) => {
    api.deleteCalendarEventById(eventId).then(() => {
      const newCalendarEvents = calendarEvents.filter(
        (event) => event.id !== eventId
      );
      setCalendarEvents(newCalendarEvents);
      setEditingEvent(null);
    });
  };

  return (
    <div className={className}>
      {editingEvent && (
        <CalendarEventEditor
          availableCalendars={availableCalendars}
          initialEvent={editingEvent}
          onOK={handleEventEditOK}
          onCancel={() => setEditingEvent(null)}
          onDelete={handleEventDelete}
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
        resources={extractResourcesFromCalendarsArray(availableCalendars)}
        events={calendarEvents}
        dateClick={handleCreateNewEvent}
        eventClick={handleEditEvent}
        eventDragStop={(event) => {
          console.log('eventDragStop', { event });
        }}
        eventDrop={(event) => {
          console.log('eventDrop', { event });
        }}
        //   eventDragStart: Identity<(arg: EventDragStartArg) => void>;
        // eventDragStop: Identity<(arg: EventDragStopArg) => void>;
        // eventDrop: Identity<(arg: EventDropArg) => void>;
        // eventResizeStart: Identity<(arg: EventResizeStartArg) => void>;
        // eventResizeStop: Identity<(arg: EventResizeStopArg) => void>;
        // eventResize: Identity<(arg: EventResizeDoneArg) => void>;
        // drop: Identity<(arg: DropArg) => void>;
        // eventReceive: Identity<(arg: EventReceiveArg) => void>;
        // eventLeave: Identity<(arg: EventLeaveArg) => void>;
      />
    </div>
  );
};

Calendar.propTypes = {
  className: PropTypes.string,
};

export default Calendar;
