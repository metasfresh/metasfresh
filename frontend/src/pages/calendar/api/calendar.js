import axios from 'axios';
import converters from './converters';
import { getQueryString } from '../../../utils';

const API_URL = `${config.API_URL}/calendars`;

const extractAxiosResponseData = (axiosReponse) => axiosReponse.data;

export const fetchAvailableCalendars = () => {
  return axios
    .get(`${API_URL}/available`)
    .then(extractAxiosResponseData)
    .then(({ calendars }) => calendars.map(converters.fromAPICalendar));
};

export const fetchCalendarEntries = ({
  calendarIds = null,
  simulationId = null,
  startDate = null,
  endDate = null,
}) => {
  return axios
    .post(`${API_URL}/entries/query`, {
      calendarIds: calendarIds || [],
      simulationId,
      startDate,
      endDate,
    })
    .then(extractAxiosResponseData)
    .then(({ entries }) => entries.map(converters.fromAPIEntry));
};

export const addOrUpdateCalendarEntry = ({
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
  const url = !id ? `${API_URL}/entries/add` : `${API_URL}/entries/${id}`;

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

export const fetchAvailableSimulations = ({ alwaysIncludeId = null }) => {
  const queryParams = getQueryString({ alwaysIncludeId });
  return axios
    .get(`${API_URL}/simulations?${queryParams}`)
    .then(extractAxiosResponseData)
    .then(({ simulations }) => simulations.map(converters.fromAPISimulation));
};

export const createSimulation = ({ copyFromSimulationId }) => {
  return axios
    .post(`${API_URL}/simulations/new`, {
      name: null, // to be generated
      copyFromSimulationId,
    })
    .then(extractAxiosResponseData)
    .then((simulation) => converters.fromAPISimulation(simulation));
};

export const fetchConflicts = ({ simulationId = null }) => {
  const queryParams = getQueryString({ simulationId });
  return axios
    .get(`${API_URL}/conflicts/query?${queryParams}`)
    .then(extractAxiosResponseData)
    .then(({ conflicts }) => conflicts.map(converters.fromAPIConflict));
};
