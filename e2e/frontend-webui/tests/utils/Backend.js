import { test, testContext } from '../../playwright.config';
import { getPage } from './common';
import { WEBAPI_BASE_URL } from './WebAPIValidation';

/**
 * Backend API client for the /api/v2/frontendTesting endpoints (module de.metas.frontend-testing).
 * Used to create test master data. The endpoints only work if AD_SysConfig `frontend.testing`='Y'
 * on the app server; otherwise they answer HTTP 403.
 */
export const Backend = {
  /**
   * Create master data for testing via POST /api/v2/frontendTesting.
   * @param {Object} options
   * @param {string} options.authToken - Optional auth token
   * @param {string} options.language - Optional language (en_US, de_DE, etc.)
   * @param {Object} options.request - Master data request object
   *   (sections: context, sysconfigs, bpartners, externalReferences)
   * @returns {Promise<Object>} Response body with created master data
   */
  createMasterdata: async ({ authToken, language, request } = {}) =>
    await test.step('Backend: create master data', async () => {
      const page = getPage();
      const headers = { 'Content-Type': 'application/json' };

      if (authToken) {
        headers['Authorization'] = authToken;
      }
      if (language) {
        headers['Accept-Language'] = language;
      }

      const backendBaseUrl = getBackendBaseUrl();
      const response = await page.request.post(
        `${backendBaseUrl}/frontendTesting`,
        {
          data: request,
          headers,
        }
      );

      const responseText = await response.text();
      let responseBody;
      try {
        responseBody = JSON.parse(responseText);
      } catch (e) {
        throw Error(
          `Failed to create master data: HTTP ${response.status()} ${response.statusText()}: ${responseText.slice(0, 500)}`
        );
      }
      assertNoErrors({ responseBody });
      if (!response.ok()) {
        throw Error(
          `Failed to create master data: HTTP ${response.status()} ${response.statusText()}`
        );
      }

      console.log('Created master data:', JSON.stringify(responseBody, null, 2));

      // Store masterdata in test context for later use
      testContext.lastMasterdata = responseBody;

      // The sysconfigs in the request are written on the app node (8282). Any
      // sysconfig-gated query runs on the separate webapi node (8080), whose
      // AD_SysConfig cache never time-expires and is refreshed across JVMs only by a
      // fire-and-forget RabbitMQ broadcast with no delivery guarantee. Force webapi to
      // reload AD_SysConfig synchronously so the just-committed values are observable.
      if (request?.sysconfigs && Object.keys(request.sysconfigs).length > 0) {
        await resetWebApiSysConfigCache();
      }

      return responseBody;
    }),

  /**
   * Set sysconfigs directly via POST /api/v2/frontendTesting/setSysconfigs, independent of
   * masterdata creation. The endpoint is exempted from authentication, so it can be
   * called at ANY point in a test, including post-login cleanup (test.afterEach).
   *
   * Does NOT reset the AD_SysConfig cache on the webapi node; call resetWebApiCaches()
   * afterwards if a webapi-node-cached view depends on the new value.
   */
  setSysconfigs: async (sysconfigs) =>
    await test.step('Backend: set sysconfigs', async () => {
      const page = getPage();
      const response = await page.request.post(
        `${getBackendBaseUrl()}/frontendTesting/setSysconfigs`,
        {
          data: sysconfigs,
          headers: { 'Content-Type': 'application/json' },
        }
      );
      if (!response.ok()) {
        throw new Error(
          `Failed to set sysconfigs: HTTP ${response.status()} ${response.statusText()}`
        );
      }
    }),

  /**
   * Force the webapi node (8080) to drop ALL caches (global cacheMgt.reset()).
   *
   * Needed after the app node changed data the webapi node may have cached.
   * The caller must be logged in.
   */
  resetWebApiCaches: async () =>
    await test.step('Backend: reset ALL webapi caches', async () => {
      const page = getPage();
      const response = await page.request.get(`${WEBAPI_BASE_URL}/cache/reset`);
      if (!response.ok()) {
        throw new Error(
          `Failed to reset webapi caches: HTTP ${response.status()} ${response.statusText()}`
        );
      }
    }),
};

/**
 * Base URL of the frontendTesting API, which runs on the app-server component (port 8282),
 * while the web frontend API runs on port 8080.
 */
const getBackendBaseUrl = () =>
  process.env.TESTING_API_BASE_URL || 'http://localhost:8282/api/v2';

/**
 * Force the webapi node (8080) to reload its AD_SysConfig cache synchronously
 * (resetByTable clears that cache inside the request thread, so the 200 is a hard confirmation).
 */
const resetWebApiSysConfigCache = async () =>
  await test.step('Backend: reset webapi AD_SysConfig cache', async () => {
    const page = getPage();
    const response = await page.request.get(
      `${WEBAPI_BASE_URL}/cache/resetByTable?tableName=AD_SysConfig`
    );
    if (!response.ok()) {
      throw new Error(
        `Failed to reset webapi AD_SysConfig cache: HTTP ${response.status()} ${response.statusText()}`
      );
    }
  });

/**
 * Assert that the response body contains no errors.
 * Throws an error if the response indicates failure.
 */
const assertNoErrors = ({ responseBody }) => {
  if (
    responseBody.error ||
    responseBody.errors ||
    responseBody.stackTrace ||
    responseBody.failure
  ) {
    throw Error(
      'Backend API error:\n' + JSON.stringify(responseBody, null, 2)
    );
  }
};
