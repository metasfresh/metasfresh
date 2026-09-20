import { test, testContext } from '../../playwright.config';
import { getPage } from './common';
import { WEBAPI_BASE_URL } from './WebAPIValidation';

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

      // The sysconfigs in the request are written on the app node (8282). Any
      // sysconfig-gated query (e.g. the product-lookup picker) runs on the
      // separate webapi node (8080), whose AD_SysConfig cache never time-expires
      // and is refreshed across JVMs only by a fire-and-forget RabbitMQ
      // broadcast with no delivery guarantee (see CacheInvalidationRemoteHandler).
      // A test that opens a gated view can therefore race that broadcast and read
      // the stale (pre-write) value. Force webapi to reload AD_SysConfig
      // synchronously so the just-committed values are observable before the test
      // proceeds; the reset runs inside webapi's own request thread, so the 200 is
      // a hard confirmation. Only needed when the request actually set sysconfigs.
      if (request && request.sysconfigs && Object.keys(request.sysconfigs).length > 0) {
        await resetWebApiSysConfigCache();
      }

      return responseBody;
    }),

  /**
   * Set sysconfigs directly via /frontendTesting/setSysconfigs, independent of
   * masterdata creation. Unlike createMasterdata's `sysconfigs` option (which only
   * runs once, before login), this can be called at ANY point in a test — including
   * post-login cleanup (test.afterEach) — because the frontendTesting endpoint is
   * exempted from auth (see FrontendTestingRestController#postConstruct:
   * doNotAuthenticatePathsContaining(ENDPOINT)).
   *
   * Note: SysconfigCommand (the handler behind this endpoint) also resets the
   * barcode-scanner sysconfigs to their defaults as a side effect on every call —
   * harmless here since tests using this for cleanup don't touch those.
   *
   * Does NOT reset the AD_SysConfig cache on the webapi node — call
   * resetWebApiCaches() afterwards if a webapi-node-cached view depends on the
   * new value (see resetWebApiCaches doc below).
   */
  setSysconfigs: async (sysconfigs) =>
    await test.step('Backend: set sysconfigs', async () => {
      const page = getPage();
      const backendBaseUrl = await getBackendBaseUrl();
      const response = await page.request.post(
        `${backendBaseUrl}/frontendTesting/setSysconfigs`,
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
   * Needed after a runtime sysconfig change that feeds a cache NOT registered for
   * AD_SysConfig invalidation — notably the order-line quick-input descriptor
   * (QuickInputDescriptorFactoryService "QuickInputDescriptors" CCache, built once
   * per window/tab with no table-reset). resetByTable(AD_SysConfig) refreshes the
   * sysconfig VALUE but leaves that cached descriptor carrying the pre-change
   * widgetSize/field-set, so the quick-input layout ignores the new value. In
   * production the sysconfig is set by migration before any descriptor is built, so
   * this only matters for tests that flip such a sysconfig at runtime.
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
 * Force the webapi node (8080) to reload its AD_SysConfig cache synchronously.
 *
 * The frontendTesting endpoint writes sysconfigs on the app node (8282). The
 * webapi node caches the whole AD_SysConfig table with no time-expiry and is
 * only refreshed cross-JVM by an unconfirmed RabbitMQ broadcast, so a gated
 * query on webapi can serve a stale value right after the write. Hitting
 * webapi's own /cache/resetByTable clears that cache inside the request thread,
 * making the just-committed values immediately observable. The endpoint needs
 * no auth (it is called before the UI login).
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
