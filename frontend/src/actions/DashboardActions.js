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

export function changeKPIItem(id, path, value) {
  return axios.patch(`${config.API_URL}/dashboard/kpis/${id}`, [
    {
      op: 'replace',
      path: path,
      value: value,
    },
  ]);
}

export function changeTargetIndicatorsItem(id, path, value) {
  return axios.patch(`${config.API_URL}/dashboard/targetIndicators/${id}`, [
    {
      op: 'replace',
      path: path,
      value: value,
    },
  ]);
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
