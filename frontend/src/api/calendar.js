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
  startDate = null,
  endDate = null,
}) => {
  return axios
    .post(`${config.API_URL}/calendars/entries/query`, {
      calendarIds: calendarIds || [],
      startDate,
      endDate,
    })
    .then(extractAxiosResponseData)
    .then(({ entries }) => entries.map(converters.fromAPIEvent));
};

export const addOrUpdateCalendarEvent = ({
  id,
  calendarId,
  resourceId,
  title,
  description = null,
  startDate,
  endDate,
  allDay,
}) => {
  const url = !id
    ? `${config.API_URL}/calendars/entries/add`
    : `${config.API_URL}/calendars/entries/${id}`;

  return axios
    .post(url, {
      calendarId,
      resourceId,
      title,
      description,
      startDate,
      endDate,
      isAllDay: allDay,
    })
    .then(extractAxiosResponseData)
    .then(converters.fromAPIEvent);
};

export const deleteCalendarEventById = (eventId) => {
  return axios
    .delete(`${config.API_URL}/calendars/entries/${eventId}`)
    .then(extractAxiosResponseData);
};

//
//
// Converters
//
//

const converters = {
  fromAPICalendar: (calendar) => ({
    calendarId: calendar.calendarId,
    name: calendar.name,
    resources: calendar.resources.map(converters.fromAPIResource),
  }),

  fromAPIResource: (resource) =>
    converters.fullcalendar_io.fromAPIResource(resource),

  fromAPIEvent: (entry) => ({
    calendarId: entry.calendarId,
    ...converters.fullcalendar_io.fromAPIEvent(entry),
  }),

  /**
   * https://fullcalendar.io converters
   */
  fullcalendar_io: {
    fromAPIResource: (resource) => ({
      id: resource.resourceId,
      title: resource.name,
    }),

    fromAPIEvent: (entry) => ({
      id: entry.entryId,
      resourceId: entry.resourceId,
      title: entry.title,
      start: entry.startDate,
      end: entry.endDate,
      allDay: entry.isAllDay,
    }),
  },
};
