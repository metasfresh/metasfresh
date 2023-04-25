import axios from 'axios';
import converters from './converters';
import { buildURL } from '../../../utils';

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
  onlyResourceIds = null,
  onlyProjectId = null,
  onlyCustomerId = null,
  onlyResponsibleId = null,
  startDate = null,
  endDate = null,
}) => {
  const query = {
    calendarIds,
    simulationId,
    onlyResourceIds,
    onlyProjectId,
    onlyCustomerId,
    onlyResponsibleId,
    startDate,
    endDate,
  };

  if (!query.calendarIds || query.calendarIds.length === 0) {
    console.log(
      'fetchCalendarEntries: return empty because no calendarIds',
      query
    );

    return Promise.resolve({
      query: {},
      entries: [],
    });
  }

  return axios
    .post(`${API_URL}/queryEntries`, query)
    .then(extractAxiosResponseData)
    .then(({ query, entries }) => ({
      query,
      entries: entries.map(converters.fromAPIEntry),
    }));
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
  return axios
    .get(buildURL(`${API_URL}/simulations`, { alwaysIncludeId }))
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

export const fetchConflicts = ({
  simulationId = null,
  onlyResourceIds = null,
}) => {
  return axios
    .get(
      buildURL(`${API_URL}/queryConflicts`, {
        simulationId,
        onlyResourceIds,
      })
    )
    .then(extractAxiosResponseData)
    .then(({ conflicts }) => conflicts.map(converters.fromAPIConflict));
};

export const getSimulationOptimizerStatus = ({ simulationId }) => {
  return axios
    .get(buildURL(`${API_URL}/simulations/optimizer`, { simulationId }))
    .then(extractAxiosResponseData)
    .then(converters.fromAPISimulationOptimizerStatus);
};

export const startSimulationOptimizer = ({ simulationId }) => {
  return axios
    .post(buildURL(`${API_URL}/simulations/optimizer/start`, { simulationId }))
    .then(extractAxiosResponseData)
    .then(converters.fromAPISimulationOptimizerStatus);
};

export const stopSimulationOptimizer = ({ simulationId }) => {
  return axios
    .post(buildURL(`${API_URL}/simulations/optimizer/stop`, { simulationId }))
    .then(extractAxiosResponseData)
    .then(converters.fromAPISimulationOptimizerStatus);
};
