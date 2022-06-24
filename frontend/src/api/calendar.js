import axios from 'axios';

const extractAxiosResponseData = (axiosReponse) => axiosReponse.data;

export const getAvailableCalendars = () => {
  return axios
    .get(`${config.API_URL}/calendars/available`)
    .then(extractAxiosResponseData)
    .then(({ calendars }) => calendars.map(converters.fromAPICalendar));
};

export const getCalendarEvents = ({
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

export const deleteCalendarEventById = (eventId) => {
  return axios
    .delete(`${config.API_URL}/calendars/entries/${eventId}`)
    .then(extractAxiosResponseData);
};

export const getAvailableSimulations = () => {
  return axios
    .get(`${config.API_URL}/calendars/simulations`)
    .then(extractAxiosResponseData)
    .then(({ simulations }) => simulations.map(converters.fromAPISimulation));
};

export const createSimulation = () => {
  return axios
    .post(`${config.API_URL}/calendars/simulations/new`, {
      name: null, // to be generated
    })
    .then(extractAxiosResponseData)
    .then((simulation) => converters.fromAPISimulation(simulation));
};

///////////////////////////////////////////////////////////////////////////////
//
// Converters
//
///////////////////////////////////////////////////////////////////////////////

const converters = {
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
