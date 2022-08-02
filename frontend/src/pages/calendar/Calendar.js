import React from 'react';
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
import { useCalendarData } from './hooks/useCalendarData';
import { useCalendarWebsocketEvents } from './hooks/useCalendarWebsocketEvents';

import './Calendar.scss';
import ConflictsSummary from './components/ConflictsSummary';
import CalendarResourceLabel from './components/CalendarResourceLabel';
import CalendarFilters from './components/CalendarFilters';
import {
  getEventClassNames,
  renderEventContent,
} from './components/CalendarEvent';

const Calendar = ({
  view,
  simulationId,
  onlyResourceIds,
  onlyProjectId,
  onlyCustomerId,
  onParamsChanged,
}) => {
  const notifyParamsChanged = (changedParams) => {
    const params = {
      view,
      simulationId,
      onlyResourceIds,
      onlyProjectId,
      onlyCustomerId,
      ...changedParams,
    };
    console.log('notifyParamsChanged', { changedParams, params });
    onParamsChanged && onParamsChanged(params);
  };

  const calendarData = useCalendarData({
    simulationId,
    onlyResourceIds,
    onlyProjectId,
    onlyCustomerId,
    fetchAvailableCalendarsFromAPI: api.fetchAvailableCalendars,
    fetchAvailableSimulationsFromAPI: api.fetchAvailableSimulations,
    fetchEntriesFromAPI: api.fetchCalendarEntries,
    fetchConflictsFromAPI: api.fetchConflicts,
  });

  useCalendarWebsocketEvents({
    simulationId,
    onlyResourceIds,
    onlyProjectId,
    onWSEvents: calendarData.applyWSEvents,
  });

  const fetchCalendarEntries = (fetchInfo, successCallback) => {
    calendarData.loadEntries({
      startDate: normalizeDateTime(fetchInfo.startStr),
      endDate: normalizeDateTime(fetchInfo.endStr),
      onFetchSuccess: successCallback,
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
      .addOrUpdateCalendarEntry({
        id: params.event.id,
        simulationId,
        startDate: params.event.start,
        endDate: params.event.end,
        allDay: params.event.allDay,
      })
      .then(calendarData.addEntriesArray)
      .catch((error) => {
        console.log('Got error', error);
        params.revert();
      });
  };

  // Calendar Key:
  // * view - it's important to be part of the key, else the Calendar component when we do browser back/forward between different view types
  // noinspection UnnecessaryLocalVariableJS
  const calendarKey = view;

  return (
    <div className="calendar">
      <div className="calendar-top">
        <div className="calendar-top-left">
          <ConflictsSummary conflictsCount={calendarData.getConflictsCount()} />
        </div>
        <div className="calendar-top-center">
          <CalendarFilters resolvedQuery={calendarData.getResolvedQuery()} />
        </div>
        <div className="calendar-top-right">
          <SimulationsDropDown
            simulations={calendarData.getSimulationsArray()}
            selectedSimulationId={simulationId}
            onOpenDropdown={() => calendarData.loadSimulationsFromAPI()}
            onSelect={(simulation) => {
              calendarData.setSimulationId(simulation?.simulationId);
            }}
            onNew={() => {
              api
                .createSimulation({ copyFromSimulationId: simulationId })
                .then((simulation) => {
                  calendarData.addSimulation(simulation);
                  notifyParamsChanged({
                    simulationId: simulation.simulationId,
                  });
                });
            }}
          />
        </div>
      </div>
      <div className="calendar-content">
        <FullCalendar
          schedulerLicenseKey="GPL-My-Project-Is-Open-Source"
          key={calendarKey}
          height="100%"
          locales={[deLocale]}
          locale={getCurrentActiveLanguage()}
          views={{
            resourceTimelineYear: {
              slotDuration: { months: 1 },
              slotLabelInterval: { months: 1 },
              slotLabelFormat: [{ month: 'long' }],
            },
          }}
          initialView={view}
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
          resources={calendarData.getResourcesArray()}
          resourceLabelContent={(params) => (
            <CalendarResourceLabel
              title={params.resource.title}
              conflictsCount={params.resource.extendedProps.conflictsCount}
            />
          )}
          eventSources={[{ events: fetchCalendarEntries }]}
          datesSet={(params) => {
            const newView = params.view.type;
            if (view !== newView) {
              notifyParamsChanged({ view: newView });
            }
          }}
          //dateClick={handleDateClick}
          eventClick={handleEventClick}
          eventClassNames={getEventClassNames}
          eventContent={renderEventContent}
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
    </div>
  );
};

Calendar.propTypes = {
  view: PropTypes.string.isRequired,
  simulationId: PropTypes.string,
  onlyResourceIds: PropTypes.array,
  onlyProjectId: PropTypes.string,
  onlyCustomerId: PropTypes.string,
  onParamsChanged: PropTypes.func,
};

export default Calendar;
