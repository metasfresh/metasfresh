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
import { newCalendarEventsHolder } from './calendarEventsHolder';
import { newAvailableCalendarsHolder } from './availableCalendarsHolder';
import { newSimulationsHolder } from './simulationsHolder';

const Calendar = ({
  simulationId: initialSelectedSimulationId,
  onParamsChanged,
}) => {
  const availableCalendarsHolder = newAvailableCalendarsHolder();
  const calendarEventsHolder = newCalendarEventsHolder();
  const simulationsHolder = newSimulationsHolder(initialSelectedSimulationId);
  //const [editingEvent, setEditingEvent] = React.useState(null);

  useEffect(() => {
    console.log('Loading simulations and calendars');
    api
      .getAvailableSimulations()
      .then(simulationsHolder.setAvailableSimulations);
    api
      .getAvailableCalendars()
      .then(availableCalendarsHolder.setCalendarsArray);
  }, []);

  if (onParamsChanged) {
    useEffect(() => {
      onParamsChanged({
        simulationId: simulationsHolder.getSelectedSimulationId(),
      });
    }, [simulationsHolder.getSelectedSimulationId()]);
  }

  useEffect(() => {
    return api.connectToWS({
      simulationId: simulationsHolder.getSelectedSimulationId(),
      onWSEventsArray: calendarEventsHolder.applyWSEventsArray,
    });
  }, [simulationsHolder.getSelectedSimulationId()]);

  const fetchCalendarEvents = (params) => {
    //console.log('fetchCalendarEvents', { params, calendarEvents });

    const startDate = normalizeDateTime(params.startStr);
    const endDate = normalizeDateTime(params.endStr);
    const simulationId = simulationsHolder.getSelectedSimulationId();

    if (calendarEventsHolder.isMatching({ startDate, endDate, simulationId })) {
      //console.log('fetchCalendarEvents: already fetched', calendarEvents);
      return Promise.resolve(calendarEventsHolder.getEventsArray());
    } else {
      console.log('fetchCalendarEvents: start fetching from backend');

      const calendarIds = availableCalendarsHolder.getCalendarIds();
      return api
        .getCalendarEvents({ calendarIds, simulationId, startDate, endDate })
        .then((events) => {
          //console.log('fetchCalendarEvents: got result', { events });
          calendarEventsHolder.setEvents({
            simulationId,
            startDate,
            endDate,
            events,
          });

          return events;
        });
    }
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
            simulations={simulationsHolder.getAvailableSimulations()}
            selectedSimulationId={simulationsHolder.getSelectedSimulationId()}
            onSelect={(simulation) => {
              simulationsHolder.setSelectedSimulationId(
                simulation?.simulationId
              );
            }}
            onNew={() => {
              api
                .createSimulation({
                  copyFromSimulationId:
                    simulationsHolder.getSelectedSimulationId(),
                })
                .then(simulationsHolder.addSimulationAndSelect);
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
        resources={availableCalendarsHolder.getResourcesArray()}
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
          const simulationId = simulationsHolder.getSelectedSimulationId();
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
              .then(calendarEventsHolder.addEventsArray)
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
