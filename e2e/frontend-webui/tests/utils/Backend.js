import { test, testContext } from '../../playwright.config';
import { getPage } from './common';

// The webapi node (mailbox/cache endpoints live here), distinct from the app-node testing API.
export const WEBAPI_BASE_URL =
  process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';

/**
 * Backend API client for interacting with /rest/api/v2/frontendTesting endpoints.
 * Used to create test master data and validate expectations.
 */
export const Backend = {
  /**
   * Create master data for testing via /api/v2/frontendTesting endpoint.
   * @param {Object} options
   * @param {string} options.authToken - Optional auth token
   * @param {string} options.language - Optional language (en_US, de_DE, etc.)
   * @param {Object} options.request - Master data request object
   * @returns {Promise<Object>} Response body with created master data
   */
  createMasterdata: async ({ authToken, language, request }) =>
    await test.step('Backend: create master data', async () => {
      const page = getPage();
      const headers = { 'Content-Type': 'application/json' };

      if (authToken) {
        headers['Authorization'] = authToken;
      }
      if (language) {
        headers['Accept-Language'] = language;
      }

      const backendBaseUrl = await getBackendBaseUrl();
      const response = await page.request.post(
        `${backendBaseUrl}/frontendTesting`,
        {
          data: request,
          headers,
        }
      );

      const responseBody = await response.json();
      assertNoErrors({ responseBody });

      // Mailboxes are created on the app node (:8282). MailService.findMailbox runs on
      // the webapi node (:8080) with its own MailboxRepository caches (AD_MailBox /
      // AD_MailConfig). A prior findMailbox call (e.g. an earlier test opening the Email
      // dialog) may have primed those caches empty, so the cross-JVM invalidation isn't
      // guaranteed to have arrived. Reset them synchronously so the freshly-seeded
      // mailbox is visible before the Email dialog is opened.
      if (request.mailboxes) {
        for (const tableName of ['AD_MailBox', 'AD_MailConfig']) {
          const resetResponse = await page.request.get(
            `${WEBAPI_BASE_URL}/cache/resetByTable?tableName=${tableName}`
          );
          if (!resetResponse.ok()) {
            throw Error(
              `Failed resetting webapi cache for ${tableName}: HTTP ${resetResponse.status()}`
            );
          }
        }
      }

      console.log('Created master data:', JSON.stringify(responseBody, null, 2));

      // Store masterdata in test context for later use
      testContext.lastMasterdata = responseBody;

      return responseBody;
    }),

  /**
   * Validate expectations against created master data.
   * @param {Object} expectations - Expected state to validate
   * @returns {Promise<Object>} Response body with validation results
   */
  expect: async (expectations) =>
    await test.step(`Backend: expect ${expectations?.title ?? ''}`, async () => {
      const page = getPage();
      const backendBaseUrl = await getBackendBaseUrl();

      const response = await page.request.post(
        `${backendBaseUrl}/frontendTesting/expect`,
        {
          data: {
            ...expectations,
            masterdata: testContext.lastMasterdata,
            context: testContext.lastExpectContext,
          },
          headers: { 'Content-Type': 'application/json' },
        }
      );

      const responseBody = await response.json();

      // Update context for chained expectations
      if (responseBody?.context != null) {
        testContext.lastExpectContext = responseBody.context;
      }

      assertNoErrors({ responseBody });

      return responseBody;
    }),

  /**
   * Call a REST endpoint of the app server's API (the base of the testing API, e.g. the mobile picking API `/picking/...`)
   * as the given user.
   * @param {Object} options
   * @param {string} options.method - HTTP method
   * @param {string} options.path - path below the API base, e.g. `/picking/event`
   * @param {Object} [options.data] - JSON request body
   * @param {string} options.authToken - the user's auth token (e.g. `masterdata.login.user.token`)
   * @returns {Promise<Object|null>} the parsed JSON response, null for an empty one
   */
  callAppApi: async ({ method, path, data, authToken }) => {
    const page = getPage();
    const backendBaseUrl = await getBackendBaseUrl();
    const response = await page.request.fetch(`${backendBaseUrl}${path}`, {
      method,
      data,
      headers: { 'Content-Type': 'application/json', Authorization: authToken },
    });
    const text = await response.text();
    if (!response.ok()) {
      throw Error(`${method} ${path} failed with HTTP ${response.status()}: ${text.substring(0, 1000)}`);
    }
    return text ? JSON.parse(text) : null;
  },

  /**
   * Describe HUs by id (read-only), e.g. pallets built by a WebUI action whose ids are known only at runtime.
   * @param {Object} options
   * @param {Array<number|string>} [options.huIds] - M_HU_IDs to describe
   * @param {number|string} [options.inventoryId] - M_Inventory_ID: also describe the HUs of its lines (e.g. the pallets built on completion)
   * @returns {Promise<Array<{huId: number, topLevelHuId: number, huUnitType: string, storages: Object<string, string>, includedTUsCount: number, tuParentPIItemQtys: number[]}>>}
   *   one entry per HU; storages map M_Product.Value to "<qty> <X12DE355>" (e.g. "160 PCE")
   */
  describeHUs: async ({ huIds = [], inventoryId = null }) =>
    await test.step(`Backend: describe HUs ${[huIds.length ? huIds : null, inventoryId ? `of inventory ${inventoryId}` : null].filter(Boolean).join(' ')}`, async () => {
      const page = getPage();
      const backendBaseUrl = await getBackendBaseUrl();

      const response = await page.request.post(
        `${backendBaseUrl}/frontendTesting/describeHUs`,
        {
          data: {
            huIds: huIds.map((huId) => Number(huId)),
            ...(inventoryId ? { inventoryId: Number(inventoryId) } : {}),
          },
          headers: { 'Content-Type': 'application/json' },
        }
      );

      const responseBody = await response.json();
      assertNoErrors({ responseBody });

      return responseBody.hus;
    }),
};

// Cached testing API base URL
// The frontendTesting API runs on the app-server component (port 8282)
// while the web frontend API runs on port 8080
let _testingApiBaseUrl = process.env.TESTING_API_BASE_URL
  ? process.env.TESTING_API_BASE_URL
  : null;

/**
 * Get the testing API base URL for frontendTesting endpoints.
 * Defaults to http://localhost:8282/api/v2 (app-server component).
 */
const getBackendBaseUrl = async () => {
  if (!_testingApiBaseUrl) {
    // Default to port 8282 where the app-server (mobile UI + testing API) runs
    _testingApiBaseUrl = process.env.TESTING_API_BASE_URL || 'http://localhost:8282/api/v2';
    console.log('Testing API base URL:', _testingApiBaseUrl);
  }
  return _testingApiBaseUrl;
};

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
