import { test } from "../../../playwright.config";
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
        console.log(`Created master data (${language}):\n` + JSON.stringify(responseBody, null, 2));

        if (responseBody.errors || responseBody.stackTrace) {
            throw Error("Got error while creating master data:\n" + JSON.stringify(responseBody, null, 2));
        }

        return responseBody;
    }),

    getFreePickingSlot: async ({ bpartnerCode } = {}) => await test.step(`Backend: get free picking slot`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const request = { bpartnerCode };
        console.log(`Sending request":\n` + JSON.stringify(request, null, 2));
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/getFreePickingSlot`, {
            data: request,
            headers: {
                'Content-Type': 'application/json',
            }
        });
        const responseBody = await response.json();
        console.log(`Got response:\n` + JSON.stringify(responseBody, null, 2));

        if (responseBody.error || responseBody.errors || responseBody.stackTrace) {
            throw "Got error on last backend call";
        }

        const { qrCode: pickingSlotQRCode } = responseBody;
        console.log(`Found free picking slot: ${pickingSlotQRCode}`);
        return { pickingSlotQRCode };
    }),
}

//
//
//
//
//

let _backendBaseUrl = null;

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
