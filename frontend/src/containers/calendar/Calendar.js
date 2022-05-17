/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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

import CalendarEventEditor from '../../components/calendar/CalendarEventEditor';

import * as api from '../../api/calendar';
import { convertToMoment, normalizeDateTime } from './calendarUtils';

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
    const calendar = availableCalendars?.[0];
    setEditingEvent({
      calendarId: calendar?.calendarId,
      resourceId: calendar?.resources?.[0].id,
      start: convertToMoment(params.date),
      end: convertToMoment(params.date),
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
