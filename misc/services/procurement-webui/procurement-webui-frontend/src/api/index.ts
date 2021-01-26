import axios, { AxiosResponse } from 'axios';

/**
 * API: daily-report-rest-controller
 * GET /rest/dailyReport/{date}
 * @param date <string>
 */
export function fetchDailyReport(date: string): Promise<AxiosResponse> {
  return axios.get(`/rest/dailyReport/${date}`);
}

/**
 * API: daily-report-rest-controller
 * POST /rest/dailyReport
 * @method postDailyReport
 * @summary -
 * @param data:
 *        {
 *          "items": [
 *            {
 *              "date": "string",
 *              "productId": "string",
 *              "qty": 0
 *            }
 *          ]
 *        }
 */
export function postDailyReport(data: unknown): Promise<AxiosResponse> {
  return axios.post(`/rest/dailyReport`, data);
}

/**
 * API: i-18-n-rest-controller
 * GET /rest/i18n/messages
 * @method getMessages
 * @summary gets the current active language and a messages object containing key value pairs with the translated strings
 */
export function getMessages(): Promise<AxiosResponse> {
  return axios.get('/rest/i18n/messages');
}

/**
 * API: info-messages-rest-controller
 * GET /rest/infoMessages
 * @method infoMessages
 * @summary gets the existing info messages which then are displayed pre-formatated preserving <b>, <i>
 */
export function infoMessages(): Promise<AxiosResponse> {
  return axios.get(`/rest/infoMessages`);
}

export function fetchWeeklyReport(weekYear: string): Promise<AxiosResponse> {
  return axios.get(`/rest/weeklyReport/${weekYear}`);
}

export function postNextWeekTrend(data: { productId: string; trend: string; week: string }): Promise<AxiosResponse> {
  return axios.post(`/rest/weeklyReport/nextWeekTrend`, data);
}

/** changeRfq */
export function postRfQ(data: {
  price?: number;
  quantities?: { date: string; qtyPromised: number }[];
  rfqId: string;
}): Promise<AxiosResponse> {
  return axios.post(`/rest/rfq`, data);
}

export function confirmDataEntry(): Promise<AxiosResponse> {
  return axios.post(`/rest/session/confirmDataEntry`);
}

/** products-rest-controller */
export function favoriteAdd(data: Array<string>): Promise<AxiosResponse> {
  return axios.post(`/rest/products/favorite/add`, { productIds: data });
}

export function favoriteRemove(data: Array<string>): Promise<AxiosResponse> {
  return axios.post(`/rest/products/favorite/remove`, { productIds: data });
}

export function getNotFavorite(): Promise<AxiosResponse> {
  return axios.get(`/rest/products/notFavorite`);
}

export function logoutRequest(): Promise<AxiosResponse> {
  return axios.get(`/rest/session/logout`);
}

export function loginRequest(username: string, password: string): Promise<AxiosResponse> {
  return axios.post(`/rest/session/login`, {
    email: username,
    password,
  });
}

export function passwordResetRequest(email: string): Promise<AxiosResponse> {
  return axios.get(`/rest/session/resetUserPassword?email=${email}`, {
    validateStatus: () => {
      // returning true so that we can get the error message
      // TODO: This is temporary, as in the final solution we will have 400's for errors - not 500
      return true;
    },
  });
}

export function passwordResetConfirm(token: string): Promise<AxiosResponse> {
  return axios.get(`/rest/session/resetUserPasswordConfirm?token=${token}`, {
    validateStatus: () => true,
  });
}

export function getUserSession(): Promise<AxiosResponse> {
  return axios.get(`/rest/session/`);
}

export function fetchRFQuotations(): Promise<AxiosResponse> {
  return axios.get(`/rest/rfq`);
}
