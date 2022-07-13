import axios from 'axios';

import * as StompJs from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useEffect } from 'react';

const WS_DEBUG = true;

const extractAxiosResponseData = (axiosReponse) => axiosReponse.data;

export const fetchAvailableCalendars = () => {
  return axios
    .get(`${config.API_URL}/calendars/available`)
    .then(extractAxiosResponseData)
    .then(({ calendars }) => calendars.map(converters.fromAPICalendar));
};

export const fetchCalendarEvents = ({
  calendarIds = null,
  simulationId = null,
  startDate = null,
  endDate = null,
}) => {
  return axios
    .post(`${config.API_URL}/calendars/entries/query`, {
      calendarIds: calendarIds || [],
      simulationId,
      startDate,
      endDate,
    })
    .then(extractAxiosResponseData)
    .then(({ entries }) => entries.map(converters.fromAPIEvent));
};

export const addOrUpdateCalendarEvent = ({
  id,
  simulationId = null,
  //
  resourceId = null,
  title = null,
  description = null,
  startDate = null,
  endDate = null,
  allDay = null,
}) => {
  const url = !id
    ? `${config.API_URL}/calendars/entries/add`
    : `${config.API_URL}/calendars/entries/${id}`;

  return axios
    .post(url, {
      simulationId,
      resourceId,
      title,
      description,
      startDate,
      endDate,
      isAllDay: allDay,
    })
    .then(extractAxiosResponseData)
    .then(converters.fromAPIUpdateResult);
};

export const fetchAvailableSimulations = () => {
  return axios
    .get(`${config.API_URL}/calendars/simulations`)
    .then(extractAxiosResponseData)
    .then(({ simulations }) => simulations.map(converters.fromAPISimulation));
};

export const createSimulation = ({ copyFromSimulationId }) => {
  return axios
    .post(`${config.API_URL}/calendars/simulations/new`, {
      name: null, // to be generated
      copyFromSimulationId,
    })
    .then(extractAxiosResponseData)
    .then((simulation) => converters.fromAPISimulation(simulation));
};

export const useWebsocketEvents = ({ simulationId, onWSEventsArray }) => {
  useEffect(() => {
    return connectToWS({ simulationId, onWSEventsArray });
  }, [simulationId]);
};

const connectToWS = ({ simulationId, onWSEventsArray }) => {
  const wsTopicName = `/v2/calendar/${simulationId || 'actual'}`;

  const stompJsConfig = {
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  };
  if (WS_DEBUG) {
    stompJsConfig.debug = (msg) => console.log('STOMP DEBUG: ' + msg);
  }

  const client = new StompJs.Client(stompJsConfig);

  client.webSocketFactory = () => new SockJS(config.WS_URL);

  client.onConnect = (frame) => {
    if (WS_DEBUG) console.log('websocket connected: ', frame);

    client.subscribe(wsTopicName, (msg) => {
      const wsEventsArray =
        JSON.parse(msg.body)?.events?.map(converters.fromAPIWebsocketEvent) ||
        [];
      onWSEventsArray(wsEventsArray);
    });
  };

  client.onStompError = function (frame) {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  client.activate();

  return () => {
    client && client.deactivate();
  };
};

///////////////////////////////////////////////////////////////////////////////
//
// Converters
//
///////////////////////////////////////////////////////////////////////////////

export const converters = {
  fromAPICalendar: (calendar) => ({
    calendarId: calendar.calendarId,
    name: calendar.name,
    resources: calendar.resources.map(converters.fromAPIResource),
  }),

  fromAPIResource: (resource) =>
    converters.fullcalendar_io.fromAPIResource(resource),

  fromAPIUpdateResult: (updateResult) => {
    //console.log('fromAPIUpdateResult', { updateResult });
    return [
      converters.fromAPIEvent(updateResult.changedEntry),
      ...updateResult.otherChangedEntries.map((entry) =>
        converters.fromAPIEvent(entry)
      ),
    ];
  },

  fromAPIEvent: (entry) => {
    //console.log('fromAPIEvent', { entry });
    return {
      calendarId: entry.calendarId,
      ...converters.fullcalendar_io.fromAPIEvent(entry),
    };
  },

  fromAPISimulation: (simulation) => ({
    simulationId: simulation.id,
    name: simulation.name,
  }),

  // see de.metas.ui.web.calendar.json.JsonWebsocketEvent
  fromAPIWebsocketEvent: (wsEvent) => ({
    type: wsEvent.type,
    entry: wsEvent.entry ? converters.fromAPIEvent(wsEvent.entry) : null,
    entryId: wsEvent.entryId,
    simulationId: wsEvent.simulationId,
  }),

  /**
   * https://fullcalendar.io converters
   */
  fullcalendar_io: {
    fromAPIResource: (apiResource) => {
      const resource = {
        id: apiResource.resourceId,
        title: apiResource.name,
      };

      // IMPORTANT: fullcalendar does not display the resource if parentId is not found (even if is null or undefined).
      if (apiResource.parentId) {
        resource.parentId = apiResource.parentId;
      }

      return resource;
    },

    fromAPIEvent: (apiEntry) => ({
      id: apiEntry.entryId,
      resourceId: apiEntry.resourceId,
      title: apiEntry.title,
      start: apiEntry.startDate,
      end: apiEntry.endDate,
      allDay: apiEntry.allDay,
      editable: apiEntry.editable,
      color: apiEntry.color,
      url: apiEntry.url,
    }),
  },
};
