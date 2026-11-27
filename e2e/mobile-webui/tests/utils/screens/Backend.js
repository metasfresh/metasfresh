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
     * Removes all HUs from the queues of the given picking slots (`M_PickingSlot_ID`s), then releases each slot if possible.
     */
    emptyPickingSlots: async ({ pickingSlotIds }) => await test.step(`Backend: empty picking slots ${pickingSlotIds.join(', ')}`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/emptyPickingSlots`, {
            data: { pickingSlotIds },
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
     * Registers the picking slot's queue under the given HU aliases (in pick order) and returns { context, qrCodes }
     * (the global QR code per alias). Fails when the slot's queue does not hold exactly these HUs.
     */
    getPickingSlotCrates: async ({ title, slotQrCode, aliases }) => {
        const { context } = await Backend.expect({
            title,
            pickingSlots: { [slotQrCode]: { queue: aliases.map((hu) => ({ hu })) } },
        });
        const qrCodes = {};
        for (const alias of aliases) qrCodes[alias] = await Backend.getHUQRCodeByIdentifier({ identifier: alias });
        return { context, qrCodes };
    },

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
     * Reverse (document-engine Reverse-Correct) a previously-created, completed shipment — the
     * faithful equivalent of the customer's "void shipment". Restores the HU snapshot taken at
     * completion and replays the per-transport-unit HU-trx lines through the aggregate VHU.
     *
     * @param {Object} args
     * @param {string} args.shipmentId - the M_InOut_ID returned by a prior shipment-create call.
     * @returns {Promise<{id: string, documentNo: string, docStatus: string}>}
     */
    reverseShipment: async ({ shipmentId }) => await test.step(`Backend: reverse shipment ${shipmentId}`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/reverseShipment`, {
            data: { shipmentId: String(shipmentId) },
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        return responseBody;
    }),

    /**
     * Update a business partner after the fact (test support): sets {@code C_BPartner.GRAIRequired}, e.g. to flip the partner
     * between picking and consolidation.
     *
     * @param {Object} args
     * @param {string} [args.bpartnerKey='BP1'] - the key the partner got in the earlier createMasterdata call (`bpartners.<key>`).
     * @param {'Y'|'N'|'D'} args.graiRequired - the new C_BPartner.GRAIRequired.
     */
    updateBPartner: async ({ bpartnerKey = 'BP1', graiRequired }) => await test.step(`Backend: update bpartner ${bpartnerKey} GRAIRequired=${graiRequired}`, async () => {
        // the createMasterdata channel updates an already created partner when its key is sent again;
        // keep the first call's masterdata (login, ids): the update response only carries the partner
        const lastMasterdata = testContext.lastMasterdata;
        await Backend.createMasterdata({
            request: {
                bpartners: {
                    [bpartnerKey]: { graiRequired },
                },
            },
        });
        testContext.lastMasterdata = lastMasterdata;
    }),

    /**
     * Generate shipments from a sales order's shipment schedules via the REAL "Generate Shipments"
     * process path ({@code ShipmentService.generateShipmentsForScheduleIds}) — NOT the
     * {@code shipments} masterdata shortcut. With quantityType 'P' (Picked) this ships the already-picked
     * qty through the {@code GenerateInOutFromShipmentSchedules} workpackage. Returns the shipments NEWLY
     * created by this call; an empty {@code newShipmentIds} means nothing could be shipped (e.g. the
     * picked qty did not survive a prior reverse — the "can't recreate shipment after void" defect).
     *
     * @param {Object} args
     * @param {string} args.salesOrderId - the C_Order_ID (e.g. masterdata.salesOrders.SO1.id).
     * @param {string} [args.quantityType='P'] - 'P' = Picked qty, 'D' = QtyToDeliver.
     * @param {boolean} [args.complete=true] - complete the generated shipment.
     * @returns {Promise<{newShipmentIds: string[], newShipmentCount: number}>}
     */
    generateShipments: async ({ salesOrderId, quantityType = 'P', complete = true }) => await test.step(`Backend: generate shipments for order ${salesOrderId} (qtyType=${quantityType})`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/generateShipments`, {
            data: { salesOrderId: String(salesOrderId), quantityType, complete },
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        return responseBody;
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

    /** POSTs Quick Pack All of the job straight to the REST endpoint (not through the button); returns the HTTP status and the body text, errors are not asserted away */
    postPickAll: async ({ wfProcessId }) => await test.step(`Backend: POST pickAll of ${wfProcessId}`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/picking/job/${wfProcessId}/pickAll`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getAuthToken(),
            }
        });
        return { status: response.status(), body: await response.text() };
    }),
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