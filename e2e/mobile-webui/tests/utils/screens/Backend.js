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
            data: {
                ...request,
                context: testContext.lastContext
            },
            headers
        });

        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        console.log(`Created master data (${language}):\n` + JSON.stringify(responseBody, null, 2));

        testContext.lastMasterdata = responseBody;
        testContext.lastContext = responseBody.context;

        return responseBody;
    }),

    expect: async (expectations) => await test.step(`Backend: expect ${expectations?.title ?? ''}`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/expect`, {
            data: {
                ...expectations,
                masterdata: testContext.lastMasterdata,
                context: testContext.lastContext,
            },
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const responseBody = await response.json();
        if (responseBody?.context != null) {
            testContext.lastContext = responseBody.context;
        }

        assertNoErrors({ responseBody });

        return {
            ...responseBody,
            context: stripTypePrefix(responseBody.context)
        };
    }),

    setSysconfigs: async (sysconfigs) => await test.step(`Backend: set sysconfigs`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/setSysconfigs`, {
            data: sysconfigs,
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok()) {
            const responseBody = await response.json();
            assertNoErrors({ responseBody });
        }
    }),

    /**
     * Resolve a masterdata identifier (e.g. `"lu1"`) to the underlying handling-unit's
     * global QR code string. Used by tests that need to scan an HU which was created
     * indirectly (typically by a picking flow) and therefore has no QR code under
     * `masterdata.handlingUnits.*`.
     */
    getHUQRCodeByIdentifier: async ({ identifier }) => await test.step(`Backend: get HU QR code for "${identifier}"`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/getHUQRCode`, {
            data: {
                identifier,
                masterdata: testContext.lastMasterdata,
                context: testContext.lastContext,
            },
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        return responseBody.qrCode;
    }),

    /**
     * Create a DRAFT shipment (M_InOut DocStatus='DR', Processed='N') from the shipment schedule
     * of a previously-created sales order, via the same frontendTesting masterdata channel as
     * {@link Backend.createMasterdata}. The order created earlier stays in the masterdata context
     * (carried via testContext.lastContext), so the `shipments` block references it by its map key.
     *
     * quantityType:'P' ships the already-PICKED qty (with createShipmentPolicy='NO' QtyToDeliver is 0,
     * so the default 'D' would yield an empty shipment); complete:false leaves the M_InOut as a draft.
     *
     * @param {Object} args
     * @param {string} [args.orderIdentifier='SO1'] - the sales order's masterdata map key.
     * @returns {Promise<{shipmentId: string, documentNo: string}>}
     */
    createDraftShipmentForOrder: async ({ orderIdentifier = 'SO1' } = {}) => await test.step(`Backend: create DRAFT shipment for order ${orderIdentifier}`, async () => {
        const response = await Backend.createMasterdata({
            request: {
                shipments: {
                    DRAFT_SHIPMENT: {
                        salesOrder: orderIdentifier,
                        quantityType: 'P',
                        complete: false,
                    },
                },
            },
        });

        const shipment = response?.shipments?.DRAFT_SHIPMENT;
        if (!shipment?.id) {
            throw new Error('Draft shipment was not created:\n' + JSON.stringify(response, null, 2));
        }

        return { shipmentId: shipment.id, documentNo: shipment.documentNo };
    }),

    /**
     * Resolves the QR code (global QR code JSON string) of the HU that a distribution `pickFrom`
     * event picked, for the LAST recorded step of the given line. Used to assert the backend
     * end-state of a distribution pick when the picked HU has no masterdata identifier — a partial
     * pick off a shared staging LU creates a NEW split HU only at pick time, so it can only be
     * referenced via the QR code the job step reports (`Backend.expect`'s HU matcher resolves a raw
     * QR-code-JSON string as a fallback identifier).
     *
     * A distribution job can have several lines (one per DD Order line), so `lineId` is REQUIRED
     * whenever the job has more than one line; it may be omitted only for a genuinely single-line
     * job (this throws rather than silently guessing the wrong line's steps otherwise).
     */
    getDistributionPickedHUQRCode: async ({ wfProcessId, lineId }) => await test.step(`Backend: get distribution picked HU QR code for wfProcess "${wfProcessId}"${lineId != null ? ` line "${lineId}"` : ''}`, async () => {
        const wfProcess = await Backend.getWFProcess({ wfProcessId });
        const moveActivity = wfProcess.activities?.find((activity) => activity.componentProps?.job?.lines != null);
        const lines = moveActivity?.componentProps?.job?.lines ?? [];

        let line;
        if (lineId != null) {
            line = lines.find((candidate) => String(candidate.lineId) === String(lineId));
        } else if (lines.length === 1) {
            line = lines[0];
        } else {
            throw new Error(
                `wfProcess "${wfProcessId}" has ${lines.length} distribution lines; pass "lineId" to disambiguate:\n` +
                JSON.stringify(lines.map((l) => l.lineId), null, 2)
            );
        }

        const steps = line?.steps ?? [];
        const lastStep = steps[steps.length - 1];
        const qrCode = lastStep?.pickFromHU?.qrCode?.code;
        if (!qrCode) {
            throw new Error(`No picked HU found for wfProcess "${wfProcessId}"${lineId != null ? ` line "${lineId}"` : ''}:\n` + JSON.stringify(wfProcess, null, 2));
        }
        return qrCode;
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
    if (!page) {
        throw Error("page is not set yet. Make sure you test has page as parameter, even if not used!");
    }
    
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
    const token = testContext.lastMasterdata?.login?.user?.token;
    if (!token) {
        throw new Error('No token found in masterdata:\n' + JSON.stringify(testContext.lastMasterdata, null, 2));
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