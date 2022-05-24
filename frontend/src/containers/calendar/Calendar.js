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

import * as api from '../../api/calendar';
import { convertToMoment, normalizeDateTime } from './calendarUtils';

import CalendarEventEditor from '../../components/calendar/CalendarEventEditor';

const extractResourcesFromCalendarsArray = (calendars) => {
  const resourcesById = calendars
    .flatMap((calendar) => calendar.resources)
    .reduce((accum, resource) => {
      accum[resource.id] = resource;
      return accum;
    }, {});

  return Object.values(resourcesById);
};

const mergeCalendarEventToArray = (eventsArray, eventToAdd) => {
  let added = false;
  const result = [];
  for (const event of eventsArray) {
    if (event.id === eventToAdd.id) {
      result.push(eventToAdd);
      added = true;
    } else {
      result.push(event);
    }
  }

  if (!added) {
    result.push(eventToAdd);
  }

  return result;
};

const suggestCalendarIdAndResourceIdByResourceId = (calendars, resourceId) => {
  if (!calendars || calendars.length === 0) {
    return {};
  }

  if (resourceId) {
    for (const calendar of calendars) {
      for (const resource of calendar.resources) {
        if (resource.id === resourceId) {
          return {
            calendarId: calendar.calendarId,
            resourceId: resource.id,
          };
        }
      }
    }
  }

  const firstCalendar = calendars[0];
  return {
    calendarId: firstCalendar.calendarId,
    resourceId: firstCalendar.resources?.[0].id,
  };
};

const Calendar = ({ className = 'container' }) => {
  //console.log('Calendar ctor------------------');
  const [calendarEvents, setCalendarEvents] = React.useState({
    startStr: null,
    endStr: null,
    events: [],
  });
  const [availableCalendars, setAvailableCalendars] = React.useState([]);
  const [editingEvent, setEditingEvent] = React.useState(null);

  useEffect(() => {
    //console.log('fetching available calendars');
    api.getAvailableCalendars().then(setAvailableCalendars);
  }, []);

  const fetchCalendarEvents = (params) => {
    //console.log('fetchCalendarEvents', { params, calendarEvents });

    const startDate = normalizeDateTime(params.startStr);
    const endDate = normalizeDateTime(params.endStr);

    if (
      calendarEvents.startDate === startDate &&
      calendarEvents.endDate === endDate
    ) {
      //console.log('fetchCalendarEvents: already fetched', calendarEvents);
      return Promise.resolve(calendarEvents.events);
    } else {
      //console.log('fetchCalendarEvents: start fetching from backend');

      const calendarIds = availableCalendars.map(
        (calendar) => calendar.calendarId
      );

      return api
        .getCalendarEvents({ calendarIds, startDate, endDate })
        .then((events) => {
          //console.log('fetchCalendarEvents: got result', { events });
          setCalendarEvents({ startDate, endDate, events });
        });
    }
  };

  const handleCreateNewEvent = (params) => {
    //console.log('handleCreateNewEvent', { params });
    const { calendarId, resourceId } =
      suggestCalendarIdAndResourceIdByResourceId(
        availableCalendars,
        params?.resource?.id
      );
    //console.log('handleCreateNewEvent', { calendarId, resourceId });

    setEditingEvent({
      calendarId,
      resourceId,
      start: convertToMoment(params.date),
      end: convertToMoment(params.date),
      allDay: params.allDay,
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
