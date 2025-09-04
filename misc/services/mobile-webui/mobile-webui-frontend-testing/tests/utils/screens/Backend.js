import { test, testContext } from "../../../playwright.config";
import { FRONTEND_BASE_URL, page } from "../common";

export const Backend = {
    createMasterdata: async ({
                                 authToken,
                                 language,
                                 request
                             }) => await test.step(`Backend: create master data`, async () => {
        const headers = {
            'Content-Type': 'application/json',
        };
        if (authToken) headers['Authorization'] = authToken;
        if (language) headers['Accept-Language'] = language;

        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting`, {
            data: request,
            headers
        });

        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        console.log(`Created master data (${language}):\n` + JSON.stringify(responseBody, null, 2));

        testContext.lastMasterdata = responseBody;

        return responseBody;
    }),

    expect: async (expectations) => await test.step(`Backend: expect`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/expect`, {
            data: {
                ...expectations,
                masterdata: testContext.lastMasterdata,
                context: testContext.lastExpectContext,
            },
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const responseBody = await response.json();
        if (responseBody?.context != null) {
            testContext.lastExpectContext = responseBody.context;
        }

        assertNoErrors({ responseBody });

        return {
            ...responseBody,
            context: stripTypePrefix(responseBody.context)
        };
    }),

    getWFProcess: async ({ wfProcessId }) => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.get(`${backendBaseUrl}/userWorkflows/wfProcess/${wfProcessId}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getAuthToken(),
            }
        });
        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        return responseBody;
    },
}

//
//
//
//
//

let _backendBaseUrl = process.env.BACKEND_BASE_URL ? process.env.BACKEND_BASE_URL + '/api/v2' : null;

export const getBackendBaseUrl = async () => {
    if (!_backendBaseUrl) {
        _backendBaseUrl = await loadConfigFromFrontendApp();
        console.log('Backend server base URL: ', _backendBaseUrl);
    }
    return _backendBaseUrl;
}

export const loadConfigFromFrontendApp = async () => await test.step(`Fetching from mobile-webui-frontend/public/config.js`, async () => {
    const url = await page.url();
    if (!url || url === 'about:blank') {
        await page.goto(FRONTEND_BASE_URL, { waitUntil: 'load' });
    }

    await page.waitForFunction(() => window.config !== undefined);
    const serverUrlRef = await page.waitForFunction(() => window.config?.SERVER_URL);
    const serverUrl = await serverUrlRef.jsonValue();
    if (!serverUrl) {
        throw new Error('window.config.SERVER_URL is not defined in the frontend app. ' +
            'Does mobile-webui-frontend/public/config.js exist and is correctly configured?');
    }

    return serverUrl + '/api/v2';
});

const assertNoErrors = ({ responseBody }) => {
    if (responseBody.error
        || responseBody.errors
        || responseBody.stackTrace
        || responseBody.failure) {
        throw Error("Got error on last backend call:\n" + JSON.stringify(responseBody, null, 2));
    }
};

const getAuthToken = () => {
    const token = lastMasterdata?.login?.user?.token;
    if (!token) {
        throw new Error('No token found in masterdata:\n' + JSON.stringify(lastMasterdata, null, 2));
    }
    return token;
}

const stripTypePrefix = (context) => {
    if (!context) return {};
    const result = {};
    for (const key in context) {
        const [, identifier] = key.split(':');
        result[identifier] = context[key];
    }
    return result;
};