/**
 * Playwright E2E — GRAI-scan picking.
 *
 * Scanning a GRAI barcode on the pick-target TU screen resolves the TU type via its
 * M_HU_PI_GRAI mapping, auto-creates that TU, and stamps the GRAI as an HU attribute.
 *
 * GRAI canonical format (see de.metas.handlingunits.grai.GRAI):
 *   "{companyPrefix}.{assetType}.{serial}"  — companyPrefix is 7 chars.
 *
 * All masterdata (GRAIRequired on the bpartner, the M_HU_PI_GRAI mappings) is created
 * via the Backend masterdata API:
 *   - bpartners.<id>.graiRequired: 'Y' | 'N' | 'D'  (Yes / No / YesWithDummyGRAIs)
 *   - packingInstructions.<id>.graiMapping: true  → the API generates a unique scannable
 *     GRAI, inserts its M_HU_PI_GRAI mapping for that TU PI, and returns it as
 *     masterdata.packingInstructions.<id>.grai
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
import { SelectPickTargetTUScreen } from '../../utils/screens/picking/SelectPickTargetTUScreen';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { expectErrorToast } from '../../utils/common';

/** Completely unparseable barcode (not GRAI canonical, not GS1 AI 8003) */
const BARCODE_UNPARSEABLE = 'NOT-A-GRAI-BARCODE-12345';

/**
 * Build a valid-format canonical GRAI ("{companyPrefix}.{assetType}.{serial}") whose
 * (companyPrefix, assetType) pair has NO M_HU_PI_GRAI mapping.
 *
 * The mapping key is (companyPrefix, assetType) — serial is ignored. We take a mapped
 * GRAI, keep its companyPrefix, and replace the assetType with one that does not match
 * any of the mapped pairs, yielding a parseable-but-unmapped GRAI.
 *
 * @param {string} mappedGrai  – a canonical GRAI returned by the masterdata API
 * @param {string[]} mappedAssetTypes  – every assetType that DOES have a mapping
 */
const buildUnmappedGrai = (mappedGrai, mappedAssetTypes) => {
    const [companyPrefix] = mappedGrai.split('.');
    let assetType = '99999';
    while (mappedAssetTypes.includes(assetType)) {
        // decrement until we find an assetType not used by any mapping (5-digit, zero-padded)
        assetType = String(parseInt(assetType, 10) - 1).padStart(5, '0');
    }
    return `${companyPrefix}.${assetType}.unmappedserial`;
};

/** assetType part of a canonical GRAI */
const assetTypeOf = (grai) => grai.split('.')[1];

/**
 * Creates the standard masterdata for the mapped-TU scenarios.
 *
 * Three packing instruction sets, each with its own generated GRAI mapping:
 *   PI_MAIN       — LU_MAIN + TU_MAPPED (capacity for P1) → happy path / no-mapping / multiple / unparseable
 *   PI_NOTALLOWED — LU_OTHER + TU_NOTALLOWED (different LU) → TU-not-allowed-on-LU
 *   PI_NOCAPACITY — LU_MAIN + TU_NOCAPACITY → no-capacity (its PIIP is removed below)
 *
 * The bpartner has GRAIRequired='Y' so the GRAI scanner is enabled.
 */
const createMasterdataForGraiScan = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    // PRODUCT aggregation is required for line-level pick targets:
                    // with SALES_ORDER aggregation, isLineLevelPickTarget=false which means
                    // the TU button does not appear on PickLineScreen, so the GRAI scanner
                    // (which requires a lineId in the URL) is unreachable.
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
                // happy path / no-mapping / multiple / unparseable — TU_MAPPED allowed on LU_MAIN with capacity
                PI_MAIN: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_MAPPED', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                // TU on a different LU (LU_OTHER), not associable with the picking-target LU_MAIN
                PI_NOTALLOWED: { lu: 'LU_OTHER', qtyTUsPerLU: 10, tu: 'TU_NOTALLOWED', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                // a second mapped TU on LU_MAIN, providing a distinct GRAI mapping with P1 capacity (the no-capacity scenario builds its own masterdata)
                PI_NOCAPACITY: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_NOCAPACITY', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU_MAPPED' }],
                },
            },
        },
    });
};

/**
 * Navigate to the pick-target TU screen for the first line (line index 1).
 *
 * The GRAI scan REST endpoint requires a lineId query parameter.  The job-level
 * TU button (PickProductsActivity → SelectCurrentLUTUButtons without a lineId)
 * does NOT supply a lineId and the backend rejects the call.  We must open the
 * line detail first (PickLineScreen, which carries the lineId in its URL) and
 * click the TU button from there.
 *
 * Precondition: PickingJobScreen is showing.
 * Postcondition: SelectPickTargetTUScreen is showing.
 */
const navigateToTUTargetScreen = async (masterdata) => {
    // 1. Scan picking slot → stays on PickingJobScreen with product aggregation
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // 2. Set LU target at job level (enables the line buttons — they are disabled without an LU)
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_MAIN.luName });
    // 3. Click line 1 (enabled since LU is set). PickLineScreen has lineId in the URL.
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    // 4. Click TU target button from the line screen. lineId is in the URL so the GRAI scan
    //    REST endpoint receives it. clickTUTargetButton uses .first() to handle any transient
    //    #SelectPickTargetScreen from the previous LU selection still in the DOM.
    // clickTUTargetButton already waits for the screen (using .first() for safety)
    await PickingJobLineScreen.clickTUTargetButton();
};

// ─── TC1 — Scan one GRAI → TU created, GRAI attribute attached ────────────────

// noinspection JSUnusedLocalSymbols
test('TC1 — Scan one GRAI → TU created with GRAI attribute', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC1 happy path');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();
    const graiMapped = masterdata.packingInstructions.PI_MAIN.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);

    // GRAI scanner must be visible (graiScanEnabled=true for GRAIRequired=Yes)
    await PickingGraiScanPanel.expectScannerVisible();

    // Scan one valid GRAI — debounce fires, REST sets the TU target, navigates back to PickLineScreen
    await PickingGraiScanPanel.scanGrai({ graiString: graiMapped });
    await PickingJobLineScreen.waitForScreen();

    // Pick the HU from the line scan screen.
    // With product aggregation, after picking: the app navigates back to PickLineScreen.
    // Use the scan screen directly (typeQRCode), then wait for PickLineScreen, go back to PickJobScreen.
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    // GetQuantityDialog appears — confirm with 1 TU (as computed from the sales order)
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    // After pick with product aggregation, app navigates back to PickLineScreen
    await PickingJobLineScreen.waitForScreen();
    // Go back to PickJobScreen and complete
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();

    // Verify: the picked TU carries the GRAI as an attribute
    await Backend.expect({
        title: 'TC1: TU has GRAI attribute attached',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }],
                    },
                },
            },
        },
        hus: {
            tu1: {
                attributes: { GRAI: graiMapped },
            },
        },
    });
});

// ─── TC2 — Scanned GRAI has no mapping → GRAINoMatchingTUType error ───────────

// noinspection JSUnusedLocalSymbols
test('TC2 — Scanned GRAI has no M_HU_PI_GRAI mapping → GRAINoMatchingTUType error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC2 no mapping');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

    // A valid-format GRAI whose (companyPrefix, assetType) pair matches none of the created mappings.
    const mappedAssetTypes = [
        assetTypeOf(masterdata.packingInstructions.PI_MAIN.grai),
        assetTypeOf(masterdata.packingInstructions.PI_NOTALLOWED.grai),
        assetTypeOf(masterdata.packingInstructions.PI_NOCAPACITY.grai),
    ];
    const graiUnmapped = buildUnmappedGrai(masterdata.packingInstructions.PI_MAIN.grai, mappedAssetTypes);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // graiUnmapped has no M_HU_PI_GRAI row.
    // The debounce fires ~1500ms after the scan; keep the race alive until the error toast appears.
    await expectErrorToast('GRAINoMatchingTUType error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: graiUnmapped });
        // waitForScreen uses .first() to tolerate 2 simultaneous #SelectPickTargetScreen elements
        await SelectPickTargetTUScreen.waitForScreen();
    });
});

// ─── TC3 — Resolved TU not allowed on the picking-target LU → error ───────────

// noinspection JSUnusedLocalSymbols
test('TC3 — Resolved TU type not allowed on picking-target LU → GRAITUNotAllowedOnLU error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC3 TU not on LU');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();
    const graiNotOnLU = masterdata.packingInstructions.PI_NOTALLOWED.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // graiNotOnLU resolves to TU_NOTALLOWED, which is only on LU_OTHER (not LU_MAIN)
    await expectErrorToast('GRAITUNotAllowedOnLU error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: graiNotOnLU });
        await SelectPickTargetTUScreen.waitForScreen();
    });
});

// ─── TC4 — Two distinct GRAIs → GRAIMultipleScanned error, no list ─────────────

// noinspection JSUnusedLocalSymbols
test('TC4 — Two distinct GRAIs in debounce window → GRAIMultipleScanned error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC4 multiple GRAIs');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();
    // Two distinct mapped GRAIs (PI_MAIN and PI_NOCAPACITY are both on LU_MAIN; either resolves)
    const graiA = masterdata.packingInstructions.PI_MAIN.grai;
    const graiB = masterdata.packingInstructions.PI_NOCAPACITY.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // Scan two distinct GRAIs back-to-back before the 1500ms debounce fires.
    // The assertion between scans gives the keyboard hook's interval flush time to process
    // each code individually so they don't concatenate in the buffer.
    await PickingGraiScanPanel.scanGrai({ graiString: graiA });
    await PickingGraiScanPanel.expectScannerVisible(); // flush gap assertion
    await PickingGraiScanPanel.scanGrai({ graiString: graiB });

    // After the debounce timer fires with 2 distinct GRAIs → error toast
    await expectErrorToast('GRAIMultipleScanned error', async () => {
        await SelectPickTargetTUScreen.waitForScreen();
    });
});

// ─── TC5 — Unparseable barcode → scanner ignores it and stays live ─────────────

// noinspection JSUnusedLocalSymbols
test('TC5 — Unparseable barcode → scanner ignores it, stays live for valid scan', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC5 unparseable barcode');
    await allure.severity('normal');

    const masterdata = await createMasterdataForGraiScan();
    const graiMapped = masterdata.packingInstructions.PI_MAIN.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // The frontend's parseGraiFromRawInput returns null for a non-GRAI barcode and
    // silently ignores it — no debounce fires, no REST call, no navigation.
    await PickingGraiScanPanel.scanGrai({ graiString: BARCODE_UNPARSEABLE });

    // Scanner must still be present — screen hasn't navigated away
    await PickingGraiScanPanel.expectScannerVisible();

    // Follow-up: a valid GRAI scan still works (scanner is live) — navigates back to PickLineScreen
    await PickingGraiScanPanel.scanGrai({ graiString: graiMapped });
    await PickingJobLineScreen.waitForScreen();
});

// ─── TC6 — Resolved TU has no capacity for the product → error ───────────────

// noinspection JSUnusedLocalSymbols
test('TC6 — Resolved TU has no capacity for line product → GRAINoCapacityForProduct error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC6 no capacity');
    await allure.severity('critical');

    // PI_NOCAPACITY's TU has a M_HU_PI_Item_Product only for P2 (not the line's product P1),
    // so when its GRAI resolves the TU there is no capacity row for P1 → the no-capacity error.
    // It is still associable with LU_MAIN (same LU as the good PI).
    const masterdata = await Backend.createMasterdata({
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
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                // The "good" PI so the LU target + line are pickable and the GRAI scanner is reachable
                PI_MAIN: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_MAPPED', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                // TU on LU_MAIN whose only capacity row is for P2 → no M_HU_PI_Item_Product for the line product P1
                PI_NOCAPACITY: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_NOCAPACITY', product: 'P2', qtyCUsPerTU: 4, graiMapping: true },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU_MAPPED' }],
                },
            },
        },
    });
    const graiNoCapacity = masterdata.packingInstructions.PI_NOCAPACITY.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // graiNoCapacity resolves to TU_NOCAPACITY which is allowed on LU_MAIN
    // but has no M_HU_PI_Item_Product for P1
    await expectErrorToast('GRAINoCapacityForProduct error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: graiNoCapacity });
        await SelectPickTargetTUScreen.waitForScreen();
    });
});

// ─── TC7 — GRAIRequired=No → no scanner shown ────────────────────────────────

// noinspection JSUnusedLocalSymbols
test('TC7 — BPartner GRAIRequired=No → no GRAI scanner on pick-target screen', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC7 no scanner when GRAIRequired=No');
    await allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
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
            bpartners: { BP_NOGRAI: { graiRequired: 'N' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP_NOGRAI',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU' }],
                },
            },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // Set LU target at job level — this enables the line button (disabled when no LU is set)
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    // Now click the line (enabled after LU set)
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    // Open TU target screen — clickTUTargetButton uses .first() to handle brief transition
    // where the previous SelectPickTargetScreen from LU selection is still in the DOM
    await PickingJobLineScreen.clickTUTargetButton();

    // No GRAI scanner must be present — graiScanEnabled=false for GRAIRequired=No
    await PickingGraiScanPanel.expectScannerNotVisible();
});
