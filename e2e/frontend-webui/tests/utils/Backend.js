import { test, testContext } from '../../playwright.config';
import { getPage } from './common';

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
