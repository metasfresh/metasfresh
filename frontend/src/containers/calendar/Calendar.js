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

import * as api from '../../api/calendar';
import { normalizeDateTime } from './calendarUtils';

import SimulationsDropDown from '../../components/calendar/SimulationsDropDown';
import { getCurrentActiveLanguage } from '../../utils/locale';
import { useCalendarEvents } from './useCalendarEvents';
import { useAvailableCalendars } from './useAvailableCalendars';
import { useSimulations } from './useSimulations';

const Calendar = ({
  simulationId: initialSelectedSimulationId,
  onParamsChanged,
}) => {
  const simulations = useSimulations(initialSelectedSimulationId);
  const availableCalendars = useAvailableCalendars();
  const calendarEvents = useCalendarEvents();

  useEffect(() => {
    console.log('Loading simulations and calendars');
    api.getAvailableSimulations().then(simulations.setFromArray);
    api.getAvailableCalendars().then(availableCalendars.setFromArray);
  }, []);

  if (onParamsChanged) {
    useEffect(() => {
      onParamsChanged({
        simulationId: simulations.getSelectedSimulationId(),
      });
    }, [simulations.getSelectedSimulationId()]);
  }

  useEffect(() => {
    return api.connectToWS({
      simulationId: simulations.getSelectedSimulationId(),
      onWSEventsArray: calendarEvents.applyWSEventsArray,
    });
  }, [simulations.getSelectedSimulationId()]);

  const fetchCalendarEvents = (params) => {
    const calendarIds = availableCalendars.getCalendarIds();
    const startDate = normalizeDateTime(params.startStr);
    const endDate = normalizeDateTime(params.endStr);
    const simulationId = simulations.getSelectedSimulationId();

    return calendarEvents.updateEventsAndGet({
      calendarIds,
      startDate,
      endDate,
      simulationId,
      newEventsSupplier: api.getCalendarEvents,
    });
  };

  const handleEventClick = (params) => {
    if (params.event.url) {
      params.jsEvent.preventDefault();
      window.open(params.event.url, '_blank');
    }
  };

  return (
    <div className="calendar-container">
      <div className="calendar-top">
        <div className="calendar-top-left" />
        <div className="calendar-top-center" />
        <div className="calendar-top-right">
          <SimulationsDropDown
            simulations={simulations.toArray()}
            selectedSimulationId={simulations.getSelectedSimulationId()}
            onSelect={(simulation) => {
              simulations.setSelectedSimulationId(simulation?.simulationId);
            }}
            onNew={() => {
              api
                .createSimulation({
                  copyFromSimulationId: simulations.getSelectedSimulationId(),
                })
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
        events={fetchCalendarEvents}
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
          const simulationId = simulations.getSelectedSimulationId();
          console.log('eventDrop', { params, simulationId });

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
