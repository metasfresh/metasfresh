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

/**
 * API: products-rest-controller
 * POST /rest/products/favorite/add
 * @method favoriteAdd
 * @summary adds the product to favorite
 * @param Array<string> data:
 *        {
 *          "productIds": [
 *             "string"
 *          ]
 *        }
 */
export function favoriteAdd(data: Array<string>): Promise<AxiosResponse> {
  return axios.post(`/rest/products/favorite/add`, { productIds: data });
}

/**
 * API: products-rest-controller
 * POST /rest/products/favorite/remove
 * @method favoriteRemove
 * @summary removes the product from favorite
 * @param Array<string> data:
 *        {
 *          "productIds": [
 *             "string"
 *          ]
 *        }
 */
export function favoriteRemove(data: Array<string>): Promise<AxiosResponse> {
  return axios.post(`/rest/products/favorite/remove`, { productIds: data });
}

/**
 * API: products-rest-controller
 * POST /rest/products/notFavorite
 * @method getNotFavorite
 * @summary get the products that are not favorite
 */
export function getNotFavorite(): Promise<AxiosResponse> {
  return axios.get(`/rest/products/notFavorite`);
}

/**
 * API: weekly-report-rest-controller
 * GET /rest/weeklyReport/{weekYear}
 * @method fetchWeeklyReport
 * @summary gets the weekly report for a given week
 * @param string - `weekYear` - format i.e: 05.2021
 */
export function fetchWeeklyReport(weekYear: string): Promise<AxiosResponse> {
  return axios.get(`/rest/weeklyReport/${weekYear}`);
}

/**
 * API: weekly-report-rest-controller
 * POST /rest/weeklyReport/nextWeekTrend
 * @method postNextWeekTrend
 * @summary updates the next weekly trend
 * @param object - data { productId: string; trend: string; week: string }
 */
export function postNextWeekTrend(data: { productId: string; trend: string; week: string }): Promise<AxiosResponse> {
  return axios.post(`/rest/weeklyReport/nextWeekTrend`, data);
}

/**
 * API: rf-q-rest-controller
 * POST /rest/rfq
 * @method postRfQ
 * @summary updates the data for the Request for Quotation
 * @param object - data {
 *                    price?: number;
 *                    quantities?: { date: string; qtyPromised: number }[];
 *                    rfqId: string;
 *                 }
 */
export function postRfQ(data: {
  price?: number;
  quantities?: { date: string; qtyPromised: number }[];
  rfqId: string;
}): Promise<AxiosResponse> {
  // patch - case when NaN
  if (isNaN(data.price) && !data.quantities) data.price = 0;
  // patch - case when qtyPromised is null
  data.quantities &&
    data.quantities.length &&
    data.quantities.map((item) => {
      if (isNaN(item.qtyPromised)) item.qtyPromised = 0;
      return item;
    });

  return axios.post(`/rest/rfq`, data);
}

/**
 * API: rf-q-rest-controller - getRfqs
 * GET /rest/rfq
 * @method fetchRFQuotations
 * @summary retrieve the existing Requests for Quotation entries
 */
export function fetchRFQuotations(): Promise<AxiosResponse> {
  return axios.get(`/rest/rfq`);
}

/**
 * API: session-rest-controller
 * POST /rest/session/confirmDataEntry
 * @method saveUnconfirmed
 * @summary confirm the data that was introduced (Save)
 */
export function saveUnconfirmed(): Promise<AxiosResponse> {
  return axios.post(`/rest/session/confirmDataEntry`);
}

/**
 * API: session-rest-controller
 * GET /rest/session/logout
 * @method logoutRequest
 * @summary invalidate the session, user logout
 */
export function logoutRequest(): Promise<AxiosResponse> {
  return axios.get(`/rest/session/logout`);
}

/**
 * API: session-rest-controller
 * POST /rest/session/login
 * @method loginRequest
 * @summary authenticates with the username and the password provided as strings
 * @param string `user`
 * @param string `password`
 */
export function loginRequest(username: string, password: string): Promise<AxiosResponse> {
  return axios.post(`/rest/session/login`, {
    email: username,
    password,
  });
}

/**
 * API: session-rest-controller
 * GET /rest/session/resetUserPassword
 * @method passwordResetRequest
 * @summary initialize the recovery of the password. An email will be sent to the target email provided as string
 * @param string `email`
 */
export function passwordResetRequest(email: string): Promise<AxiosResponse> {
  return axios.get(`/rest/session/resetUserPassword?email=${email}`, {
    validateStatus: () => {
      // returning true so that we can get the error message
      // TODO: This is temporary, as in the final solution we will have 400's for errors - not 500
      return true;
    },
  });
}

/**
 * API: session-rest-controller
 * GET /rest/session/resetUserPasswordConfirm
 * @method passwordResetConfirm
 * @summary confirms the token received via email against the backend
 * @param string `token`
 */
export function passwordResetConfirm(token: string): Promise<AxiosResponse> {
  return axios.get(`/rest/session/resetUserPasswordConfirm?token=${token}`, {
    validateStatus: () => true,
  });
}

/**
 * API: session-rest-controller
 * GET /rest/session/
 * @method getUserSession
 * @summary fetches the session data, on success you get in here the count of unconfirmed entries, loggedIn value aso
 */
export function getUserSession(): Promise<AxiosResponse> {
  return axios.get(`/rest/session/`);
}
