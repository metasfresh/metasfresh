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

/**
 * Masterdata for the TOP-LEVEL-TU GRAI scenario (TC9) — exercises branch (a)
 * (`PickingJobPickCommand.updatePickingTarget` → `result.isSingleTopLevelTUOnly()`),
 * the one runtime-unverified GRAI stamp branch.
 *
 * The single key difference from {@link createMasterdataForGraiScan} is the pick-target
 * structure: {@code pickTo: ['TU']} ONLY, and the GRAI-mapped TU PI carries **no LU**
 * (PI_TU_TOPLEVEL has no `lu`). Per the frontend eligibility rule
 * (isCurrentTargetEligibleForLine_TU): with a `'TU'` pick-to structure the line is
 * eligible iff NO LU target is set — so this scenario can NEVER route through an LU.
 * That forces the pick to materialise as a genuine top-level TU (branch (a)), not the
 * TU-under-LU shape that TC1 covers (branch (b)).
 *
 * The bpartner keeps GRAIRequired='Y' so the GRAI scanner is enabled.
 */
const createMasterdataForTopLevelTUGraiScan = async () => {
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
                    // 'TU' ONLY → the line is eligible iff NO LU target is set (top-level-TU path).
                    // There is no 'LU_TU' option, so an LU target can never make the line eligible:
                    // every pick on this job is a top-level TU → branch (a).
                    pickTo: ['TU'],
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
                // GRAI-mapped TU PI with NO lu → a genuine top-level TU when materialised.
                PI_TU_TOPLEVEL: { tu: 'TU_MAPPED', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
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
 * Navigate to the LINE-LEVEL pick-target TU screen WITHOUT setting any LU target.
 *
 * This is the top-level-TU counterpart of {@link navigateToTUTargetScreen}. With the
 * {@code pickTo: ['TU']} config (see {@link createMasterdataForTopLevelTUGraiScan}) the
 * line button is enabled even though no LU is set — the 'TU' pick-to structure makes the
 * line eligible precisely when there is NO LU target. We therefore open the line detail
 * directly (no setTargetLU step) so the GRAI scan endpoint gets a lineId and the pick
 * materialises as a top-level TU (branch (a)).
 *
 * Precondition: PickingJobScreen is showing.
 * Postcondition: SelectPickTargetTUScreen is showing.
 */
const navigateToTopLevelTUTargetScreen = async (masterdata) => {
    // 1. Scan picking slot → stays on PickingJobScreen with product aggregation
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // 2. NO setTargetLU — with pickTo:['TU'] the line is eligible only while no LU is set.
    // 3. Click line 1 (enabled via the 'TU' structure). PickLineScreen has lineId in the URL.
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    // 4. Click TU target button from the line screen. lineId is in the URL so the GRAI scan
    //    REST endpoint receives it.
    await PickingJobLineScreen.clickTUTargetButton();
};

/**
 * Navigate to the JOB-LEVEL (header) pick-target TU screen — the path that has NO line in
 * context (lineId=null). The job-level TU button (PickProductsActivity →
 * SelectCurrentLUTUButtons, no lineId) opens SelectPickTargetTUScreen with no lineId in the URL.
 *
 * On a GRAI-required customer the backend now supports a GRAI scan here: it resolves the TU
 * type, runs the TU-allowed-on-LU check against the JOB-LEVEL LU, skips the per-product
 * capacity check, sets a job-level TU target carrying the GRAI, and stamps the GRAI on the
 * shipped HU at pick time. (Previously this 500'd — see me03 29853 follow-up.) The GRAI
 * scanner is therefore offered at job level too.
 *
 * Precondition: PickingJobScreen is showing.
 * Postcondition: SelectPickTargetTUScreen (job-level) is showing.
 */
const navigateToJobLevelTUTargetScreen = async (masterdata, { lu } = {}) => {
    // 1. Scan picking slot → stays on PickingJobScreen with product aggregation
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // 2. Set LU target at job level (the job-level TU button is enabled once an LU is set)
    await PickingJobScreen.setTargetLU({ lu: lu ?? masterdata.packingInstructions.PI_MAIN.luName });
    // 3. Click the JOB-LEVEL TU target button WITHOUT opening any line — no lineId in the URL.
    await PickingJobScreen.clickTUTargetButton();
    await SelectPickTargetTUScreen.waitForScreen();
};

// ─── TC-H1 — Job-level (no line) TU target on GRAI customer → scanner visible ──
//
// me03 https://github.com/metasfresh/me03/issues/29853 follow-up. Header-level GRAI support:
// on a GRAI-required customer the GRAI scanner is offered on the JOB/HEADER-level TU
// pick-target (lineId=null). AC-H1.

// noinspection JSUnusedLocalSymbols
test('TC-H1 — Job-level TU target (no line) on GRAI customer → GRAI scanner visible', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC-H1 header scanner visible (lineId=null)');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    // Reach the JOB-LEVEL TU target screen (no line in context → lineId=null).
    await navigateToJobLevelTUTargetScreen(masterdata);

    // The GRAI scanner must be present at job level for a GRAIRequired=Yes customer.
    await PickingGraiScanPanel.expectScannerVisible();
});

// ─── TC-H2 — Header scan → job-level TU + GRAI on the shipped HU ───────────────
//
// At the job-level target, scan a mapped GRAI: the new TU target is created (no 500). Then
// complete the pick + ship, and assert the scanned GRAI is present as the GRAI M_HU_Attribute
// on the SHIPPED HU. AC-H2 + AC-H6.

// noinspection JSUnusedLocalSymbols
test('TC-H2 — Header GRAI scan → job-level TU created, GRAI on shipped HU', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC-H2 header scan → shipped-HU GRAI');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();
    const graiMapped = masterdata.packingInstructions.PI_MAIN.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1 });

    // Reach the JOB-LEVEL TU target screen (no line in context → lineId=null).
    await navigateToJobLevelTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // Scan one valid GRAI at job level — the new job-level TU target is created (no 500),
    // debounce fires, REST sets the target, navigates back to the job screen.
    await PickingGraiScanPanel.scanGrai({ graiString: graiMapped });
    await PickingJobScreen.waitForScreen();

    // Pick the line into the job-level TU target.
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();

    // Complete + ship.
    await PickingJobScreen.complete();

    // Verify: the SHIPPED HU (the TU packed by the header GRAI scan) carries the scanned GRAI
    // as its GRAI M_HU_Attribute. Same TU-under-LU shape as TC1 (an LU target was set), so the
    // shipped/processed picked HU is the TU (tu1) under its LU (lu1).
    await Backend.expect({
        title: 'TC-H2: header-scanned GRAI present on the shipped HU',
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
            // The shipped TU carries the GRAI stamped at pick time by the header-level scan (AC-H6).
            tu1: {
                attributes: { GRAI: graiMapped },
            },
        },
    });
});

// ─── TC-H3 — Header scan, TU not allowed on the job LU → error ────────────────
//
// The TU-allowed-on-LU check runs against the JOB-LEVEL LU even with no line in context. Scan
// a GRAI whose resolved TU type is not associable with the job LU → GRAITUNotAllowedOnLU error,
// no TU created. AC-H3.

// noinspection JSUnusedLocalSymbols
test('TC-H3 — Header GRAI scan, TU not allowed on job LU → GRAITUNotAllowedOnLU error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC-H3 header scan TU not on job LU');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();
    // PI_NOTALLOWED's GRAI resolves to TU_NOTALLOWED, which is only on LU_OTHER — NOT the
    // job-level LU (LU_MAIN). The TU-allowed-on-LU check against the job LU must reject it.
    const graiNotOnLU = masterdata.packingInstructions.PI_NOTALLOWED.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    // Job-level target with LU_MAIN as the job LU.
    await navigateToJobLevelTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // graiNotOnLU resolves to TU_NOTALLOWED (only on LU_OTHER, not the job LU LU_MAIN) → error, no TU created.
    await expectErrorToast('GRAITUNotAllowedOnLU error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: graiNotOnLU });
        await SelectPickTargetTUScreen.waitForScreen();
    });
});

// ─── TC-H8 — Non-GRAI header target still works (nullable-lineId regression) ───
//
// At the job-level TU target, use the manual per-type button (no scan) → a TU target is set
// normally. Regression guard for the new nullable-lineId path. AC-H8.

// noinspection JSUnusedLocalSymbols
test('TC-H8 — Job-level non-GRAI TU target (manual button, no scan) still works', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC-H8 non-GRAI header target regression');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    // Reach the JOB-LEVEL TU target screen (no line in context → lineId=null).
    await navigateToJobLevelTUTargetScreen(masterdata);
    // The scanner is offered (GRAIRequired=Yes) but we deliberately do NOT scan.
    await PickingGraiScanPanel.expectScannerVisible();

    // Use the manual per-type button instead — pick TU_MAPPED. The TU target must be set
    // normally via the nullable-lineId job-level path, returning to the job screen.
    await SelectPickTargetTUScreen.clickTUButton({ tu: masterdata.packingInstructions.PI_MAIN.tuName });
    await PickingJobScreen.waitForScreen();

    // The job-level TU target is now set: the line button is enabled, so the pick can proceed.
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
});

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
                        // The GRAI pick packs the CUs into a genuine (non-aggregate) TU, so the picked
                        // handling unit recorded on M_ShipmentSchedule_QtyPicked is the TU (M_TU_HU_ID) and
                        // its LU (M_LU_HU_ID). VHU_ID is null by design: PickingJobPickCommand passes the TU
                        // to HUShipmentScheduleBL.addQtyPickedAndUpdateHU, and getTopLevelParentAsLUTUCUPair
                        // resolves a TU to LUTUCUPair.ofTU(tu, lu) with no VHU. (VHU_ID is only populated when
                        // the picked HU is itself virtual/aggregate, e.g. an aggregate-TU pick where the TU
                        // record IS the VHU — see picking.spec.js / pick_from_LUs.spec.js where vhu === tu.)
                        qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }],
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

// ─── TC9 — Scan one GRAI into a TOP-LEVEL TU (no LU) → branch (a) ──────────────
//
// This is the top-level-TU counterpart of TC1. TC1 sets an LU target first, so its
// pick materialises as a TU-under-LU (PickingJobPickCommand.updatePickingTarget branch
// (b): result.isSingleLU() && singleTU). TC9 uses a pickTo:['TU'] config with a
// GRAI-mapped TU PI that has NO LU, and sets NO LU target — so the pick materialises as
// a genuine top-level TU (branch (a): result.isSingleTopLevelTUOnly()). Both branches
// route the scanned GRAI through huService.setGrais → setGraisInAmbientContext (the
// ambient-context flush fix). Branch (a) was previously only "covered" by an in-memory
// JUnit test whose non-buffering DAO cannot detect the flush bug — TC9 closes that gap
// by proving, end-to-end against the full stack, that the GRAI lands on the top-level TU.

// noinspection JSUnusedLocalSymbols
test('TC9 — Scan one GRAI into top-level TU (no LU) → TU created with GRAI attribute', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC9 top-level TU (no LU)');
    await allure.severity('critical');

    const masterdata = await createMasterdataForTopLevelTUGraiScan();
    const graiMapped = masterdata.packingInstructions.PI_TU_TOPLEVEL.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1 });

    // Reach the line-level TU target screen WITHOUT an LU target (top-level-TU path).
    await navigateToTopLevelTUTargetScreen(masterdata);

    // GRAI scanner must be visible (graiScanEnabled=true for GRAIRequired=Yes)
    await PickingGraiScanPanel.expectScannerVisible();

    // Scan one valid GRAI — debounce fires, REST sets the top-level TU target, navigates back.
    await PickingGraiScanPanel.scanGrai({ graiString: graiMapped });
    await PickingJobLineScreen.waitForScreen();

    // Pick the HU from the line scan screen (same flow as TC1).
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();

    // Before complete: the picked HU is a genuine TOP-LEVEL TU (no LU) — qtyLUs=0, lu='-'.
    // This shape is impossible for TC1 (TU-under-LU always carries an LU), so it proves the
    // pick went through branch (a) (isSingleTopLevelTUOnly), not branch (b).
    await Backend.expect({
        title: 'TC9: before complete — picked HU is a top-level TU (no LU)',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }],
                    },
                },
            },
        },
        hus: {
            // GRAI landed on the top-level TU at pick time (branch (a) flush worked).
            tu1: { huStatus: 'S', storages: { P1: '4 PCE' }, attributes: { GRAI: graiMapped } },
        },
    });

    // Complete must succeed (no GRAI_COUNT_MISMATCH) — the GRAI attribute is present.
    await PickingJobScreen.complete();

    // After complete: the picked HU stays a genuine TOP-LEVEL TU (qtyLUs=0, lu='-') — its PI
    // has no LU so completion does not wrap it into one. It is processed onto the shipment line,
    // and the GRAI attribute is still on the TU — proving the pick-time stamp (branch (a))
    // persisted through completion.
    await Backend.expect({
        title: 'TC9: after complete — top-level TU still carries the GRAI attribute',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: true, shipmentLineId: 'shipmentLineId1' }],
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
