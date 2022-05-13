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
  console.log('Calendar ctor------------------');
  const [calendarEvents, setCalendarEvents] = React.useState({
    startStr: null,
    endStr: null,
    events: [],
  });
  const [availableCalendars, setAvailableCalendars] = React.useState([]);
  const [editingEvent, setEditingEvent] = React.useState(null);

  useEffect(() => {
    console.log('fetching available calendars');
    api.getAvailableCalendars().then(setAvailableCalendars);
  }, []);

  const fetchCalendarEvents = (params) => {
    console.log('fetchCalendarEvents', { params, calendarEvents });

    if (
      calendarEvents.startStr === params.startStr &&
      calendarEvents.endStr === params.endStr
    ) {
      console.log('fetchCalendarEvents: already fetched', calendarEvents);
      return Promise.resolve(calendarEvents.events);
    } else {
      console.log('fetchCalendarEvents: start fetching from backend');
      return api
        .getCalendarEvents({}) // TODO
        .then((events) => {
          console.log('fetchCalendarEvents: got result', { events });
          setCalendarEvents({
            startStr: params.startStr,
            endStr: params.endStr,
            events,
          });
        });
    }
  };

  const handleCreateNewEvent = (params) => {
    const calendar = availableCalendars?.[0];
    setEditingEvent({
      calendarId: calendar?.calendarId,
      resourceId: calendar?.resources?.[0].id,
      start: params.date,
      end: params.date,
      allDay: true,
    });
  };

  const handleEditEvent = (params) => {
    const eventId = params.event.id;
    const event = calendarEvents.events.find((event) => event.id === eventId);
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
        const events = mergeCalendarEventToArray(
          calendarEvents.events,
          eventFromBackend
        );
        setCalendarEvents({ ...calendarEvents, events });
        setEditingEvent(null);
      });
  };

  const handleEventDelete = (eventId) => {
    api.deleteCalendarEventById(eventId).then(() => {
      const events = calendarEvents.events.filter(
        (event) => event.id !== eventId
      );
      setCalendarEvents({ ...calendarEvents, events });
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
        events={fetchCalendarEvents}
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
