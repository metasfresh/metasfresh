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
     * Set AD_WF_Node.IsActive / PP_AlwaysAvailableToUser (omit = unchanged; alwaysAvailableToUser '' clears it).
     * Returns the previous values, shaped so they can be passed back to restore the node.
     *
     * @returns {Promise<{adWfNodeId: number, isActive: boolean, alwaysAvailableToUser: string}>}
     */
    setWFNodeFlags: async ({ adWfNodeId, isActive, alwaysAvailableToUser }) => await test.step(`Backend: set WF node ${adWfNodeId} flags`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/wfNode/flags`, {
            data: { adWfNodeId, isActive, alwaysAvailableToUser },
            headers: { 'Content-Type': 'application/json' },
        });
        const responseBody = await response.json();
        assertNoErrors({ responseBody });

        // null (flag was empty) -> '' so that passing the result back clears the flag again
        return { ...responseBody, alwaysAvailableToUser: responseBody.alwaysAvailableToUser ?? '' };
    }),

    /**
     * Switch C_AcctSchema.CostingMethod of the test client and activate the matching cost elements.
     * Returns the previous state; pass it to restoreAcctSchemaCostingMethod in afterEach.
     * Production costing is MovingAverageInvoice, code 'M' (the E2E seed uses 'S').
     *
     * @param {string} code - e.g. 'M'
     * @returns {Promise<{previousCostingMethod: string, activatedCostElementIds: number[]}>}
     */
    setAcctSchemaCostingMethod: async (code) => await test.step(`Backend: set acct schema costing method ${code}`, async () => {
        return await postAcctSchemaCostingMethod({ costingMethod: code });
    }),

    /**
     * Undo setAcctSchemaCostingMethod: puts back the costing method and deactivates the cost elements it activated.
     *
     * A no-op for an undefined/null state, so that an afterEach after a failed set does not mask the original failure.
     *
     * @param {{previousCostingMethod: string, activatedCostElementIds: number[]}} [previousState] - the result of setAcctSchemaCostingMethod
     */
    restoreAcctSchemaCostingMethod: async (previousState) => {
        if (!previousState) {
            return;
        }
        const { previousCostingMethod, activatedCostElementIds } = previousState;
        await test.step(`Backend: restore acct schema costing method ${previousCostingMethod}`, async () => {
            await postAcctSchemaCostingMethod({ costingMethod: previousCostingMethod, deactivateCostElementIds: activatedCostElementIds ?? [] });
        });
    },

    /**
     * Noon of yesterday / today / tomorrow in the SERVER's time zone (ISO instants), the days that server-side
     * "today" filters use. Use these instead of the test machine's clock, whose time zone may differ.
     *
     * @returns {Promise<{zoneId: string, yesterdayNoon: string, todayNoon: string, tomorrowNoon: string}>}
     */
    getServerDates: async () => await test.step(`Backend: get server dates`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/serverDates`, {
            headers: { 'Content-Type': 'application/json' },
        });
        const responseBody = await response.json();
        assertNoErrors({ responseBody });
        return responseBody;
    }),

    /**
     * Set PP_Order.DateStartSchedule of an existing manufacturing order.
     *
     * @param {string} args.documentNo - the order's DocumentNo
     * @param {string} args.dateStartSchedule - ISO date-time, e.g. '2026-11-02T08:00:00+01:00'
     */
    setManufacturingOrderDateStartSchedule: async ({ documentNo, dateStartSchedule }) => await test.step(`Backend: set start date of PP order ${documentNo}`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        const response = await page.request.post(`${backendBaseUrl}/frontendTesting/ppOrder/dateStartSchedule`, {
            data: { ppOrder: documentNo, dateStartSchedule },
            headers: { 'Content-Type': 'application/json' },
        });
        if (!response.ok()) {
            const responseBody = await response.json();
            assertNoErrors({ responseBody });
            throw new Error(`setManufacturingOrderDateStartSchedule failed: ${response.status()}`);
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

    // Returns the operator's CURRENT active workplace (system of record) — shape:
    // { workplaceRequired, assignedWorkplace: { ...id/name/caption... } }.
    // Used to prove the operator's active workplace, as opposed to a workstation SCREEN's
    // statically-linked workplace (which never drifts and would not catch a re-assign bug).
    getCurrentWorkplace: async () => await test.step(`Backend: get current workplace`, async () => {
        const backendBaseUrl = await getBackendBaseUrl();
        // The mobile REST API authenticates via the login token header (not a browser cookie),
        // so page.request must carry it explicitly — same token the logged-in UI uses.
        const response = await page.request.get(`${backendBaseUrl}/workplace`, {
            headers: { 'Authorization': getAuthToken() },
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
}

//
//
//
//
//

let _backendBaseUrl = process.env.BACKEND_BASE_URL ? process.env.BACKEND_BASE_URL + '/api/v2' : null;

const postAcctSchemaCostingMethod = async (data) => {
    const backendBaseUrl = await getBackendBaseUrl();
    const response = await page.request.post(`${backendBaseUrl}/frontendTesting/acctSchema/costingMethod`, {
        data,
        headers: { 'Content-Type': 'application/json' },
    });
    const responseBody = await response.json();
    assertNoErrors({ responseBody });
    return responseBody;
};

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