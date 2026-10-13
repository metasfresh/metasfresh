/**
 * Playwright E2E — a Migros returnable-asset GRAI whose PO-reference-derived serial does NOT match
 * the current sales order's PO reference is now ACCEPTED by the target-selection GRAI scanner: the
 * TU type is resolved as usual and the pick proceeds to a genuine capture, with the GRAI stamped on
 * the picked TU. This is the CORRECTED behaviour after the scan-time PO-reference ownership match was
 * removed: previously such a GRAI was refused ("belongs to another order"); that gate is gone, so the
 * mismatch no longer blocks the pick.
 *
 * This is the ACCEPT mirror of the (removed) refusal spec `picking-grai-poreference-mismatch.spec.js`:
 * same navigation (`navigateToTUTargetScreen`, `PickingGraiScanPanel`, product aggregation, per-line
 * TU-target screen) and the same mismatched Migros GRAI, but the scan is asserted ACCEPTED (no error
 * toast, TU resolved, GRAI stamped) instead of refused.
 *
 * The GRAI-scan (target-selection) flow — `PickingGraiScanPanel` + `SelectPickTargetTUScreen` (see
 * `picking-grai-scan.spec.js`) — is the ONLY flow that ran the removed PO-reference gate
 * (`PickingJobGraiTargetService.resolveTuTypeAndCapacity`); the inline mass-capture flow
 * (`PickGraiScreen`) never hit it. TU resolution is by the scanned GRAI's (companyPrefix, assetType)
 * pair via its `M_HU_PI_GRAI` mapping — NOT by PO reference — so the mismatched Migros GRAI still
 * resolves to the mapped TU; only the removed ownership gate ever cared about the PO reference.
 *
 * Migros GRAI structure (see de.metas.handlingunits.grai.DummyGRAITemplate):
 *   "{MIGROS_COMPANY_PREFIX}.{MIGROS_ASSET_TYPE}.{PO reference, zero-padded to 10}{2-digit counter}"
 *   = "7613204.00307.<poReference padStart(10,'0')><counter>"
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';

const MIGROS_COMPANY_PREFIX = '7613204';
const MIGROS_ASSET_TYPE = '00307';
const ORDER_PO_REFERENCE = '12345';
const OTHER_PO_REFERENCE = '99999';

/** Builds a canonical Migros dummy-GRAI: see de.metas.handlingunits.grai.DummyGRAITemplate.migros(poReference).buildGRAI(counter). */
const buildMigrosGrai = (poReference, counter) =>
    `${MIGROS_COMPANY_PREFIX}.${MIGROS_ASSET_TYPE}.${poReference.padStart(10, '0')}${String(counter).padStart(2, '0')}`;

/**
 * A GRAIRequired customer whose sales order carries a PO reference, and whose TU packing instruction
 * is mapped to the FIXED Migros (companyPrefix, assetType) pair — so the scanned Migros GRAI resolves
 * to a real TU type regardless of which PO reference it was derived from. Product aggregation is
 * required for the line-level TU-target screen (see picking-grai-scan.spec.js).
 */
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                },
            },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_MAIN: {
                    lu: 'LU_MAIN',
                    qtyTUsPerLU: 20,
                    tu: 'TU_MAPPED',
                    product: 'P1',
                    qtyCUsPerTU: 4,
                    graiMapping: true,
                    graiCompanyPrefix: MIGROS_COMPANY_PREFIX,
                    graiAssetType: MIGROS_ASSET_TYPE,
                },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    poReference: ORDER_PO_REFERENCE,
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU_MAPPED' }],
                },
            },
        },
    });
};

/**
 * Navigate to the pick-target TU screen for the first line (per-line path, carries a lineId — the
 * GRAI scan REST endpoint needs it to resolve the order's PO reference).
 *
 * Precondition: PickingJobScreen is showing.
 * Postcondition: SelectPickTargetTUScreen is showing.
 */
const navigateToTUTargetScreen = async (masterdata) => {
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_MAIN.luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
};

// noinspection JSUnusedLocalSymbols
test('Migros GRAI not matching the order\'s PO reference is accepted, TU created with the GRAI attribute (PO-reference match removed)', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F5230: GRAI on Returnable Assets');
    await allure.story('GRAI scan picking — a Migros GRAI not matching the order\'s PO reference is accepted (PO-reference match removed)');
    await allure.severity('critical');

    const masterdata = await createMasterdata();
    // Migros-structured (matches companyPrefix/assetType, so it resolves to the mapped TU) but derived
    // from a DIFFERENT PO reference than the current order's — formerly refused, now accepted.
    const mismatchedGrai = buildMigrosGrai(OTHER_PO_REFERENCE, 1);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    // Product-aggregated jobs (aggregationType: 'product') are listed by product, so the launcher
    // caption carries no documentNo — start by index after the documentNo filter has narrowed the
    // list to a single launcher (mirrors picking-grai-scan.spec.js's product-aggregation flow).
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // CORRECTED behaviour: the mismatched Migros GRAI is accepted -> the TU type resolves and the flow
    // advances back to the line screen (formerly a 'belongs to another order' error toast blocked here).
    await PickingGraiScanPanel.scanGrai({ graiString: mismatchedGrai });
    await PickingJobLineScreen.waitForScreen();
    // No refusal toast: the removed PO-reference gate no longer fires (waitForScreen above already
    // waited out the scanner debounce + REST round-trip, so any error toast would be present by now).
    await ErrorToast.expectNoErrorToast();

    // Pick the HU from the line scan screen — proceeds to a genuine capture (not just gate-passed).
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();

    // Real end result: the picked TU carries the mismatched Migros GRAI as an attribute — the scan was
    // accepted end-to-end despite deriving from a different PO reference than the order's.
    await Backend.expect({
        title: 'PO-reference mismatch now accepted: TU carries the mismatched Migros GRAI attribute',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }],
                    },
                },
            },
        },
        hus: {
            tu1: {
                attributes: { GRAI: mismatchedGrai },
                bpartner: 'BP1',
                bpartnerLocation: 'BP1',
            },
        },
    });
});
