import axios from 'axios';

export function getKPIsDashboard() {
  return axios.get(`${config.API_URL}/dashboard/kpis?silentError=true`);
}

export function getTargetIndicatorsDashboard() {
  return axios.get(
    `${config.API_URL}/dashboard/targetIndicators?silentError=true`
  );
}

export function getKPIData(id) {
  return axios.get(
    `${config.API_URL}/dashboard/kpis/${id}/data?silentError=true`
  );
}

export function changeKPIItem(id, values) {
  const data = convertObjectToPatchRequestsArray(values);
  if (data.length <= 0) return;

  return axios.patch(`${config.API_URL}/dashboard/kpis/${id}`, data);
}

function convertObjectToPatchRequestsArray(values) {
  if (!values) return [];
  return Object.keys(values).map((key) => ({
    op: 'replace',
    path: key,
    value: values[key],
  }));
}

export function changeTargetIndicatorsItem(id, values) {
  const data = convertObjectToPatchRequestsArray(values);
  if (data.length <= 0) return;
  return axios.patch(
    `${config.API_URL}/dashboard/targetIndicators/${id}`,
    values
  );
}

export function getTargetIndicatorsData(id) {
  return axios.get(
    `${config.API_URL}/dashboard/targetIndicators/${id}/data?silentError=true`
  );
}

export function setUserDashboardWidgets(payload) {
  return axios.patch(`${config.API_URL}/dashboard/kpis`, payload);
}

export function getAvailableKPIsToAdd() {
  return axios.get(`${config.API_URL}/dashboard/kpis/available`);
}

/**
 * @method getTargetIndicatorsDetails
 * @summary calls the API to get the details info
 * @param {string} indicatorId
 */
export function getTargetIndicatorsDetails(indicatorId) {
  return axios.get(
    `${config.API_URL}/dashboard/targetIndicators/${indicatorId}/details`
  );
}

export function addDashboardWidget(entity, id, pos) {
  return axios.post(config.API_URL + '/dashboard/' + entity + '/new', {
    kpiId: id,
    position: pos,
  });
}

export function removeDashboardWidget(entity, id) {
  return axios.delete(config.API_URL + '/dashboard/' + entity + '/' + id);
}
