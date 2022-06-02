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
import { normalizeDateTime } from './calendarUtils';

import CalendarEventEditor from '../../components/calendar/CalendarEventEditor';

const extractResourcesFromCalendarsArray = (calendars) => {
  const resourcesById = calendars
    .flatMap((calendar) => calendar.resources)
    .reduce((accum, resource) => {
      accum[resource.id] = resource;
      return accum;
    }, {});

  const resources = Object.values(resourcesById);

  // IMPORTANT: completely remove 'parentId' property if it's not found in our list of resources
  // Else fullcalendar.io won't render that resource at all.
  resources.forEach((resource) => {
    if ('parentId' in resource && !resourcesById[resource.parentId]) {
      console.log('removing parentId because was not found: ', resource);
      delete resource.parentId;
    }
  });
  //console.log('extractResourcesFromCalendarsArray', resources);

  return resources;
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

// const suggestCalendarIdAndResourceId = (calendars, preferredResourceId) => {
//   if (!calendars || calendars.length === 0) {
//     return {};
//   }
//
//   if (preferredResourceId) {
//     for (const calendar of calendars) {
//       for (const resource of calendar.resources) {
//         if (resource.id === preferredResourceId) {
//           return {
//             calendarId: calendar.calendarId,
//             resourceId: resource.id,
//           };
//         }
//       }
//     }
//   }
//
//   const firstCalendar = calendars[0];
//   return {
//     calendarId: firstCalendar.calendarId,
//     resourceId: firstCalendar.resources?.[0].id,
//   };
// };

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

  const handleDateClick = (params) => {
    console.log('handleDateClick', { params });
    // FIXME for now, consider the calendar not editable, i.e. do nothing
    // const { calendarId, resourceId } = suggestCalendarIdAndResourceId(
    //   availableCalendars,
    //   params?.resource?.id
    // );
    // //console.log('handleDateClick', { calendarId, resourceId });
    //
    // setEditingEvent({
    //   calendarId,
    //   resourceId,
    //   start: convertToMoment(params.date),
    //   end: convertToMoment(params.date),
    //   allDay: params.allDay,
    // });
  };

  const handleEventClick = (params) => {
    if (params.event.url) {
      params.jsEvent.preventDefault();
      window.open(params.event.url, '_blank');
    } else {
      const eventId = params.event.id;
      const event = calendarEvents.events.find((event) => event.id === eventId);
      console.log('handleEventClick', { params, event });
      setEditingEvent(event);
    }
  };

  const handleEventEditOK = (event) => {
    api
      .addOrUpdateCalendarEvent({
        id: event.id,
        calendarId: event.calendarId,
        resourceId: event.resourceId,
        startDate: event.start,
        endDate: event.end,
        allDay: event.allDay,
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
        initialView="resourceTimelineMonth"
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
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        eventDragStart={(event) => {
          console.log('eventDragStart', { event });
        }}
        eventDragStop={(event) => {
          console.log('eventDragStop', { event });
        }}
        eventDrop={(event) => {
          console.log('eventDrop', { event });
        }}
        drop={(event) => {
          console.log('drop', { event });
        }}
        eventResizeStart={(event) => {
          console.log('eventResizeStart', { event });
        }}
        eventResizeStop={(event) => {
          console.log('eventResizeStop', { event });
        }}
        eventResize={(event) => {
          console.log('eventResize', { event });
        }}
        eventReceive={(event) => {
          console.log('eventReceive', { event });
        }}
        eventLeave={(event) => {
          console.log('eventLeave', { event });
        }}
      />
    </div>
  );
};

Calendar.propTypes = {
  className: PropTypes.string,
};

export default Calendar;
