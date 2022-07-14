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
import deLocale from '@fullcalendar/core/locales/de';

import * as api from './api/calendar';
import { normalizeDateTime } from './utils/calendarUtils';

import SimulationsDropDown from './components/SimulationsDropDown';
import { getCurrentActiveLanguage } from '../../utils/locale';
import { useCalendarEvents } from './hooks/useCalendarEvents';
import { useAvailableCalendars } from './hooks/useAvailableCalendars';
import { useSimulations } from './hooks/useSimulations';
import { useCalendarWebsocketEvents } from './hooks/useCalendarWebsocketEvents';

const Calendar = ({
  simulationId: initialSelectedSimulationId,
  onParamsChanged,
}) => {
  const simulations = useSimulations(initialSelectedSimulationId);
  const simulationId = simulations.getSelectedSimulationId();
  const availableCalendars = useAvailableCalendars();
  const calendarEvents = useCalendarEvents();

  useEffect(() => {
    console.log('Loading simulations and calendars');
    api.fetchAvailableSimulations().then(simulations.setFromArray);
    api.fetchAvailableCalendars().then(availableCalendars.setFromArray);
  }, []);

  useEffect(() => {
    api.fetchConflicts({ simulationId }).then(calendarEvents.setConflicts);
  }, [simulationId]);

  if (onParamsChanged) {
    useEffect(() => {
      onParamsChanged({ simulationId });
    }, [simulationId]);
  }

  useCalendarWebsocketEvents({
    simulationId,
    onWSEvents: calendarEvents.applyWSEvents,
  });

  const fetchCalendarEvents = (fetchInfo, successCallback, failureCallback) => {
    calendarEvents.updateEventsFromAPI({
      calendarIds: availableCalendars.getCalendarIds(),
      startDate: normalizeDateTime(fetchInfo.startStr),
      endDate: normalizeDateTime(fetchInfo.endStr),
      simulationId,
      fetchFromAPI: api.fetchCalendarEvents,
      onFetchSuccess: successCallback,
      onFetchError: failureCallback,
    });
  };

  const handleEventClick = (params) => {
    if (params.event.url) {
      params.jsEvent.preventDefault();
      window.open(params.event.url, '_blank');
    }
  };

  const handleEventDragOrResize = (params) => {
    if (
      params.event.startStr === params.oldEvent.startStr &&
      params.event.endStr === params.oldEvent.endStr &&
      params.event.allDay === params.oldEvent.allDay
    ) {
      console.log('handleEventDragOrResize: no change', { params });
      return;
    }

    api
      .addOrUpdateCalendarEvent({
        id: params.event.id,
        simulationId,
        startDate: params.event.start,
        endDate: params.event.end,
        allDay: params.event.allDay,
      })
      .then(calendarEvents.addEventsArray)
      .catch((error) => {
        console.log('Got error', error);
        params.revert();
      });
  };

  return (
    <div className="calendar-container">
      <div className="calendar-top">
        <div className="calendar-top-left" />
        <div className="calendar-top-center" />
        <div className="calendar-top-right">
          <SimulationsDropDown
            simulations={simulations.toArray()}
            selectedSimulationId={simulationId}
            onSelect={(simulation) => {
              simulations.setSelectedSimulationId(simulation?.simulationId);
            }}
            onNew={() => {
              api
                .createSimulation({ copyFromSimulationId: simulationId })
                .then(simulations.addSimulationAndSelect);
            }}
          />
        </div>
      </div>
      <FullCalendar
        schedulerLicenseKey="GPL-My-Project-Is-Open-Source"
        locales={[deLocale]}
        locale={getCurrentActiveLanguage()}
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
        resources={availableCalendars.getResourcesArray()}
        eventSources={[
          {
            events: fetchCalendarEvents,
          },
        ]}
        //events={fetchCalendarEvents}
        //dateClick={handleDateClick}
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
          console.log('eventDrop', { params });

          if (params.oldResource?.id !== params.newResource?.id) {
            console.log('moving event to another resource is not allowed');
            params.revert();
            return;
          }

          handleEventDragOrResize(params);
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
        eventResize={(params) => {
          console.log('eventResize', { params });
          handleEventDragOrResize(params);
        }}
        eventReceive={(event) => {
          console.log('eventReceive', { event });
          event.revert();
        }}
        eventLeave={(event) => {
          console.log('eventLeave', { event });
          event.revert();
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
