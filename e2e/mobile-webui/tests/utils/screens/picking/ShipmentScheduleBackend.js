import { test } from "../../../../playwright.config";
import { Backend } from "../Backend";

/**
 * Backend helper that creates a DRAFT shipment (M_InOut DocStatus='DR', Processed='N')
 * from the shipment schedule of a previously-created sales order.
 *
 * It drives ONLY the app-server frontendTesting masterdata API (`/api/v2/frontendTesting`,
 * the same channel as Backend.createMasterdata) — NOT the WebUI `rest/api` (:8080) webapi,
 * which is not part of the mobile-profile CI stack. The sales order created in the initial
 * Backend.createMasterdata call is still registered in the masterdata context (carried
 * forward via testContext.lastContext), so the `shipments` block can reference it by its
 * map key.
 *
 * The masterdata `shipments` command is invoked with:
 *   - quantityType: 'P'  => ship the already-PICKED quantity (the schedule was fully picked
 *                           in the mobileUI; with createShipmentPolicy='NO' QtyToDeliver is 0,
 *                           so the default 'D' would yield an empty shipment).
 *   - complete: false    => leave the M_InOut as a DRAFT (DocStatus='DR', Processed='N').
 *
 * This produces the exact data state described in the bug: a draft shipment line bound to
 * the already-picked M_ShipmentSchedule_QtyPicked rows (M_InOutLine_ID set, Processed='N'),
 * which previously caused the picked order to wrongly re-appear in the mobileUI picking list.
 */

export const ShipmentScheduleBackend = {
    /**
     * Create a DRAFT shipment from the shipment schedule of the given sales order.
     *
     * @param {Object} args
     * @param {string} [args.orderIdentifier='SO1'] - the sales order's masterdata map key
     *        (the key under `salesOrders` in the original createMasterdata request).
     * @returns {Promise<{shipmentId: string, documentNo: string}>}
     */
    createDraftShipmentForOrder: async ({ orderIdentifier = 'SO1' } = {}) =>
        await test.step(`ShipmentScheduleBackend - Create DRAFT shipment for order ${orderIdentifier}`, async () => {
            const response = await Backend.createMasterdata({
                request: {
                    shipments: {
                        DRAFT_SHIPMENT: {
                            salesOrder: orderIdentifier,
                            // 'P' == PICKED quantity (the schedule is fully picked, QtyToDeliver is 0).
                            quantityType: 'P',
                            // complete=false => DRAFT shipment (DocStatus='DR', Processed='N').
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
};
