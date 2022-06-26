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
import SimulationsDropDown from '../../components/calendar/SimulationsDropDown';

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

const mergeCalendarEventsArrayToArray = (eventsArray, eventsToAdd) => {
  const resultEventsById = [];
  eventsArray.forEach((event) => {
    resultEventsById[event.id] = event;
  });
  eventsToAdd.forEach((event) => {
    resultEventsById[event.id] = event;
  });

  return Object.values(resultEventsById);
};

const Calendar = ({
  simulationId: initialSelectedSimulationId,
  onParamsChanged,
}) => {
  const [calendarEvents, setCalendarEvents] = React.useState({
    startStr: null,
    endStr: null,
    simulationId: null,
    events: [],
  });
  const [availableSimulations, setAvailableSimulations] = React.useState([]);

  const [selectedSimulationId, setSelectedSimulationId] = React.useState(
    initialSelectedSimulationId
  );
  const [availableCalendars, setAvailableCalendars] = React.useState([]);
  const [editingEvent, setEditingEvent] = React.useState(null);

  useEffect(() => {
    api.getAvailableSimulations().then(setAvailableSimulations);
    api.getAvailableCalendars().then(setAvailableCalendars);
  }, []);

  if (onParamsChanged) {
    useEffect(() => {
      onParamsChanged({ simulationId: selectedSimulationId });
    }, [selectedSimulationId]);
  }

  const fetchCalendarEvents = (params) => {
    //console.log('fetchCalendarEvents', { params, calendarEvents });

    const startDate = normalizeDateTime(params.startStr);
    const endDate = normalizeDateTime(params.endStr);
    const simulationId = selectedSimulationId;

    if (
      calendarEvents.startDate === startDate &&
      calendarEvents.endDate === endDate &&
      calendarEvents.simulationId === simulationId
    ) {
      //console.log('fetchCalendarEvents: already fetched', calendarEvents);
      return Promise.resolve(calendarEvents.events);
    } else {
      //console.log('fetchCalendarEvents: start fetching from backend');

      const calendarIds = availableCalendars.map(
        (calendar) => calendar.calendarId
      );

      return api
        .getCalendarEvents({ calendarIds, simulationId, startDate, endDate })
        .then((events) => {
          //console.log('fetchCalendarEvents: got result', { events });
          setCalendarEvents({ startDate, endDate, simulationId, events });
        });
    }
  };

  const addCalendarEventsArray = (eventsArrayToAdd) => {
    setCalendarEvents({
      ...calendarEvents,
      events: mergeCalendarEventsArrayToArray(
        calendarEvents.events,
        eventsArrayToAdd
      ),
    });
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
        resourceId: event.resourceId,
        startDate: event.start,
        endDate: event.end,
        allDay: event.allDay,
        title: event.title,
      })
      .then((changedEvents) => {
        addCalendarEventsArray(changedEvents);
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
    <div className="calendar-container">
      {editingEvent && (
        <CalendarEventEditor
          availableCalendars={availableCalendars}
          initialEvent={editingEvent}
          onOK={handleEventEditOK}
          onCancel={() => setEditingEvent(null)}
          onDelete={handleEventDelete}
        />
      )}
      <div className="calendar-top">
        <div className="calendar-top-left" />
        <div className="calendar-top-center" />
        <div className="calendar-top-right">
          <SimulationsDropDown
            simulations={availableSimulations}
            selectedSimulationId={selectedSimulationId}
            onSelect={(simulation) => {
              setSelectedSimulationId(simulation?.simulationId);
            }}
            onNew={() => {
              api
                .createSimulation({
                  copyFromSimulationId: selectedSimulationId,
                })
                .then((simulation) => {
                  setAvailableSimulations([
                    ...availableSimulations,
                    simulation,
                  ]);
                  setSelectedSimulationId(simulation.simulationId);
                });
            }}
          />
        </div>
      </div>
      <FullCalendar
        schedulerLicenseKey="GPL-My-Project-Is-Open-Source"
        views={{
          resourceTimelineYear: {
            slotDuration: { months: 1 },
            slotLabelInterval: { months: 1 },
            slotLabelFormat: [{ month: 'long' }],
          },
        }}
        initialView="resourceTimelineYear"
        plugins={[
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin,
          resourceTimelinePlugin,
        ]}
        weekends="true"
        editable="true"
        headerToolbar={{
          left: 'prev,today,next',
          center: 'title',
          right:
            'dayGridMonth resourceTimelineDay,resourceTimelineWeek,resourceTimelineMonth,resourceTimelineYear',
        }}
        resourceAreaHeaderContent="Resources"
        resources={extractResourcesFromCalendarsArray(availableCalendars)}
        events={fetchCalendarEvents}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        eventContent={(params) => {
          //console.log('eventContent', { params });
          return <div>{params.event.title}</div>;
        }}
        eventDragStart={(event) => {
          console.log('eventDragStart', { event });
        }}
        eventDragStop={(event) => {
          console.log('eventDragStop', { event });
        }}
        eventDrop={(params) => {
          console.log('eventDrop', { params, selectedSimulationId });

          if (params.oldResource?.id !== params.newResource?.id) {
            console.log('moving event to another resource is not allowed');
            params.revert();
            return;
          }

          if (
            params.event.startStr !== params.oldEvent.startStr ||
            params.event.endStr !== params.oldEvent.endStr ||
            params.event.allDay !== params.oldEvent.allDay
          ) {
            api
              .addOrUpdateCalendarEvent({
                id: params.event.id,
                simulationId: selectedSimulationId,
                startDate: params.event.start,
                endDate: params.event.end,
                allDay: params.event.allDay,
              })
              .then((changedEvents) => addCalendarEventsArray(changedEvents))
              .catch((error) => {
                console.log('Got error', error);
                params.revert();
              });
          }
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
  simulationId: PropTypes.string,
  onParamsChanged: PropTypes.func,
};

export default Calendar;
