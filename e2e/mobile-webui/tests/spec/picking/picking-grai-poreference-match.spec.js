/**
 * Playwright E2E — a Migros returnable-asset GRAI whose PO-reference-derived serial matches the
 * current sales order's PO reference is ACCEPTED by the target-selection GRAI scanner: the TU type
 * is resolved as usual and the pick proceeds to a genuine capture, with the GRAI stamped on the
 * picked TU.
 *
 * This exercises the GRAI-scan (target-selection) flow — `PickingGraiScanPanel` +
 * `SelectPickTargetTUScreen` (see `picking-grai-scan.spec.js`) — the ONLY flow that runs the
 * PO-reference-ownership gate (`PickingJobGraiTargetService.resolveTuTypeAndCapacity`); the inline
 * mass-capture flow (`PickGraiScreen`) never hits this gate.
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

const MIGROS_COMPANY_PREFIX = '7613204';
const MIGROS_ASSET_TYPE = '00307';
const PO_REFERENCE = '12345';

/** Builds a canonical Migros dummy-GRAI: see de.metas.handlingunits.grai.DummyGRAITemplate.migros(poReference).buildGRAI(counter). */
const buildMigrosGrai = (poReference, counter) =>
    `${MIGROS_COMPANY_PREFIX}.${MIGROS_ASSET_TYPE}.${poReference.padStart(10, '0')}${String(counter).padStart(2, '0')}`;

/**
 * A GRAIRequired customer whose sales order carries the PO reference that the scanned Migros GRAI
 * is derived from, and whose TU packing instruction is mapped to the FIXED Migros (companyPrefix,
 * assetType) pair — so the scanned GRAI resolves to a real TU type and the pick can complete.
 * Product aggregation is required for the line-level TU-target screen (see picking-grai-scan.spec.js).
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
                    poReference: PO_REFERENCE,
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
test('Migros GRAI matching the order\'s PO reference is accepted, TU created with the GRAI attribute', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F5230: GRAI on Returnable Assets');
    await allure.story('GRAI scan picking — Migros GRAI matching the order\'s PO reference is accepted');
    await allure.severity('critical');

    const masterdata = await createMasterdata();
    const matchingGrai = buildMigrosGrai(PO_REFERENCE, 1);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // The Migros GRAI matches the order's PO reference -> accepted, no mismatch toast, TU resolved.
    await PickingGraiScanPanel.scanGrai({ graiString: matchingGrai });
    await PickingJobLineScreen.waitForScreen();

    // Pick the HU from the line scan screen — proceeds to a genuine capture (not just gate-passed).
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();

    // Real end result: the picked TU carries the Migros GRAI as an attribute.
    await Backend.expect({
        title: 'PO-reference match: TU carries the matching Migros GRAI attribute',
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
                attributes: { GRAI: matchingGrai },
                bpartner: 'BP1',
                bpartnerLocation: 'BP1',
            },
        },
    });
});
