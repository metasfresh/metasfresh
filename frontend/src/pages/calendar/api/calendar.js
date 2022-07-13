import axios from 'axios';
import converters from './converters';

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
