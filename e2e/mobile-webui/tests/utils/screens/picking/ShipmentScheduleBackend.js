import { test } from "../../../../playwright.config";
import { page } from "../../common";

/**
 * Backend helper that reproduces the desktop-WebUI "generate shipments" action
 * (AD_Process M_ShipmentSchedule_EnqueueSelection, AD_Process_ID 540458) for a
 * single sales order's shipment schedule, with IsCompleteShipments=false so that
 * a DRAFT shipment (M_InOut DocStatus='DR', Processed='N') is produced.
 *
 * This mirrors the exact data state described in the bug: a draft shipment line
 * bound to the already-picked M_ShipmentSchedule_QtyPicked rows (M_InOutLine_ID set,
 * Processed='N'), which previously caused the picked order to wrongly re-appear in
 * the mobileUI picking list.
 *
 * It drives the real WebUI REST API (the same endpoints the desktop frontend uses):
 *   1. POST /rest/api/login/authenticate          (token auth, reuses the picking user's token)
 *   2. POST /rest/api/documentView/500221         (create a view on the Lieferdisposition window)
 *   3. POST /rest/api/process/ADP_540458          (create the process instance for the selected row)
 *   4. PATCH /rest/api/process/ADP_540458/{pinstance}  (set IsCompleteShipments=false)
 *   5. GET  /rest/api/process/ADP_540458/{pinstance}/start (enqueue the async workpackage)
 * then polls the DB-backed view until the shipment schedule has a bound M_InOutLine_ID.
 */

// WebUI REST API base. The webapi (rest/api) is a different server than the app
// server used by Backend.createMasterdata (/api/v2/frontendTesting).
const WEBUI_BASE_URL = (process.env.WEBUI_BASE_URL || 'http://localhost:8080') + '/rest/api';

// AD_Window_ID of the "Lieferdisposition" (shipment schedule) window.
const SHIPMENT_SCHEDULE_WINDOW_ID = '500221';
// AD_Process_ID of M_ShipmentSchedule_EnqueueSelection, encoded as a WebUI ProcessId (handler type ADP).
const GENERATE_SHIPMENTS_PROCESS_ID = 'ADP_540458';

const assertOk = (label, response, body) => {
    if (!response.ok()) {
        throw new Error(`${label} failed: HTTP ${response.status()} — ${JSON.stringify(body)}`);
    }
};

export const ShipmentScheduleBackend = {
    /**
     * Create a DRAFT shipment from the shipment schedule of the given sales order documentNo.
     *
     * @param {Object} args
     * @param {string} args.documentNo - the sales order document number
     * @param {string} args.token      - WebUI auth token (from masterdata.login.user.token)
     * @returns {Promise<{shipmentScheduleId: string}>}
     */
    createDraftShipmentForOrder: async ({ documentNo, token }) =>
        await test.step(`ShipmentScheduleBackend - Create DRAFT shipment for order ${documentNo}`, async () => {
            // 1. WebUI login via token (establishes the session cookie in the page's request context)
            let resp = await page.request.post(`${WEBUI_BASE_URL}/login/authenticate`, {
                headers: { 'Content-Type': 'application/json' },
                data: { type: 'token', token },
            });
            assertOk('WebUI authenticate', resp, await resp.text());

            // 2. Create a view on the shipment-schedule window and locate the row for our order.
            resp = await page.request.post(`${WEBUI_BASE_URL}/documentView/${SHIPMENT_SCHEDULE_WINDOW_ID}`, {
                headers: { 'Content-Type': 'application/json' },
                data: {
                    windowId: SHIPMENT_SCHEDULE_WINDOW_ID,
                    viewType: 'grid',
                    filters: [],
                    queryFirstRow: 0,
                    queryPageLength: 500,
                },
            });
            const view = await resp.json();
            assertOk('Create shipment-schedule view', resp, view);

            const viewId = view.viewId;
            const rows = view.result || [];
            // The row's C_Order_ID field caption is the sales order document number — exact match (no substring collisions).
            const matchingRow = rows.find((row) => {
                const orderField = row?.fieldsByName?.C_Order_ID?.value;
                const caption = orderField && typeof orderField === 'object' ? orderField.caption : orderField;
                return String(caption) === String(documentNo);
            });
            if (!matchingRow) {
                throw new Error(`No shipment-schedule row found for order ${documentNo} (view had ${rows.length} rows)`);
            }
            const rowId = matchingRow.id; // == M_ShipmentSchedule_ID
            const shipmentScheduleId = String(rowId);

            // 3. Create the process instance with the selected row.
            resp = await page.request.post(`${WEBUI_BASE_URL}/process/${GENERATE_SHIPMENTS_PROCESS_ID}`, {
                headers: { 'Content-Type': 'application/json' },
                data: {
                    processId: GENERATE_SHIPMENTS_PROCESS_ID,
                    viewId,
                    viewDocumentIds: [rowId],
                },
            });
            const proc = await resp.json();
            assertOk('Create generate-shipments process instance', resp, proc);
            const pinstanceId = proc.pinstanceId;

            // 4. Set the process parameters:
            //    - QuantityType='P' (PICKED quantity) — the schedule was already fully picked in the
            //      mobileUI, so QtyToDeliver is 0; the default 'D' (quantity to deliver) would produce
            //      an empty shipment. 'P' ships the already-picked quantity.
            //    - IsCompleteShipments=false => DRAFT shipment (DocStatus='DR').
            //    - IsShipToday=true.
            resp = await page.request.patch(`${WEBUI_BASE_URL}/process/${GENERATE_SHIPMENTS_PROCESS_ID}/${pinstanceId}`, {
                headers: { 'Content-Type': 'application/json' },
                data: [
                    { op: 'replace', path: 'QuantityType', value: 'P' },
                    { op: 'replace', path: 'IsCompleteShipments', value: false },
                    { op: 'replace', path: 'IsShipToday', value: true },
                ],
            });
            const patchResult = await resp.json();
            assertOk('Set IsCompleteShipments=false', resp, patchResult);

            // 5. Start the process (enqueues the async shipment-generation workpackage).
            resp = await page.request.get(`${WEBUI_BASE_URL}/process/${GENERATE_SHIPMENTS_PROCESS_ID}/${pinstanceId}/start`);
            const startResult = await resp.json();
            assertOk('Start generate-shipments process', resp, startResult);
            if (startResult.error) {
                throw new Error(`generate-shipments process returned error: ${JSON.stringify(startResult)}`);
            }

            // 6. The shipment is generated asynchronously. Poll the view until the schedule row
            //    reflects that the (draft) shipment line was bound. When the M_InOutLine is created
            //    and bound to the M_ShipmentSchedule_QtyPicked rows, QtyToDeliver drops to 0 (the
            //    quantity is now committed to the draft shipment line), even though the shipment is
            //    still DocStatus='DR' and QtyDelivered stays 0 (draft lines are not "delivered" yet).
            await ShipmentScheduleBackend.waitForBoundInOutLine({ viewId, rowId });

            return { shipmentScheduleId };
        }),

    /**
     * Poll the shipment-schedule view until the given row's QtyToDeliver becomes 0, which signals
     * that the draft shipment line has been generated and bound to the schedule's QtyPicked rows.
     */
    waitForBoundInOutLine: async ({ viewId, rowId, timeoutMs = 40000, intervalMs = 1500 }) =>
        await test.step(`ShipmentScheduleBackend - Wait for draft shipment line on schedule ${rowId}`, async () => {
            const deadline = Date.now() + timeoutMs;
            while (Date.now() < deadline) {
                const resp = await page.request.get(`${WEBUI_BASE_URL}/documentView/${SHIPMENT_SCHEDULE_WINDOW_ID}/${viewId}/byIds?ids=${rowId}`);
                if (resp.ok()) {
                    const body = await resp.json();
                    const row = Array.isArray(body) ? body[0] : (body?.result ? body.result[0] : body);
                    const qtyToDeliver = row?.fieldsByName?.QtyToDeliver?.value;
                    if (qtyToDeliver != null && Number(qtyToDeliver) === 0) {
                        return;
                    }
                }
                await new Promise((r) => setTimeout(r, intervalMs));
            }
            throw new Error(`Timed out waiting for the draft shipment line to be bound to schedule ${rowId}`);
        }),
};
