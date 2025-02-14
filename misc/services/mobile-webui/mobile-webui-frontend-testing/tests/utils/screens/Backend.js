import {test} from "../../../playwright.config";
import {BACKEND_BASE_URL, page} from "../common";

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

        const response = await page.request.post(`${BACKEND_BASE_URL}/frontendTesting`, {
            data: request,
            headers
        });

        const responseBody = await response.json();
        console.log(`Created master data (${language}):\n` + JSON.stringify(responseBody, null, 2));

        if (responseBody.errors || responseBody.stackTrace) {
            throw "Got error while creating master data";
        }

        return responseBody;
    }),

    getFreePickingSlot: async ({bpartnerCode}) => await test.step(`Backend: get free picking slot`, async () => {
        const response = await page.request.post(`${BACKEND_BASE_URL}/frontendTesting/getFreePickingSlot`, {
            data: {bpartnerCode},
            headers: {
                'Content-Type': 'application/json',
            }
        });
        const responseBody = await response.json();
        console.log(`Got response:\n` + JSON.stringify(responseBody, null, 2));

        if (responseBody.error || responseBody.errors || responseBody.stackTrace) {
            throw "Got error on last backend call";
        }

        const {qrCode: pickingSlotQRCode} = responseBody;
        console.log(`Found free picking slot: ${pickingSlotQRCode}`);
        return {pickingSlotQRCode};
    }),
}