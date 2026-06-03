/**
 * Playwright E2E — GRAI-scan picking (GRAI barcode → new-TU creation + GRAI attribute stamp)
 *
 * Covers REQUIREMENTS §5 test scenarios TC1–TC7:
 *   TC1  Scan one valid GRAI → auto-create the correct TU + GRAI attribute attached
 *   TC2  Scanned GRAI has no M_HU_PI_GRAI mapping → error GRAINoMatchingTUType
 *   TC3  Resolved TU type not allowed on the picking-target LU → error GRAITUNotAllowedOnLU
 *   TC4  Two distinct GRAIs in one debounce window → error GRAIMultipleScanned, no list
 *   TC5  Unparseable barcode → scanner ignores it and stays live
 *   TC6  Resolved TU type has no M_HU_PI_Item_Product for the line's product → error GRAINoCapacityForProduct
 *   TC7  BPartner GRAIRequired=No → no GRAI scanner shown
 *
 * Spec file size rule: ≤ 8 tests per new spec file (TC1–TC7 = 7 tests here). ✓
 *
 * GRAI format: canonical dot-separated form accepted directly by parseGraiFromRawInput:
 *   "{companyPrefix}.{assetType}.{serial}"
 *   Company prefix 7613204, asset type 00307 → maps to TU_MAPPED (TC1 happy path)
 *   Company prefix 7613204, asset type 00308 → unmapped (TC2)
 *   Company prefix 7613205, asset type 00307 → maps to TU_NOTALLOWED (TC3)
 *   Company prefix 7613206, asset type 00307 → maps to TU_NOCAPACITY (TC6)
 *
 * GRAIRequired DB codes (X_C_BPartner):  'Y' = Yes,  'N' = No,  'D' = YesWithDummyGRAIs
 */

import { execSync } from 'child_process';
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
import { SelectPickTargetLUScreen } from '../../utils/screens/picking/SelectPickTargetLUScreen';
import { SelectPickTargetTUScreen } from '../../utils/screens/picking/SelectPickTargetTUScreen';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { expectErrorToast } from '../../utils/common';

// ─── GRAI test constants ───────────────────────────────────────────────────────

/** Canonical GRAI for TC1 — maps to TU_MAPPED; allowed on LU; has capacity for P1 */
const GRAI_MAPPED = '7613204.00307.testserial001';

/** Canonical GRAI with no M_HU_PI_GRAI row (TC2) */
const GRAI_UNMAPPED = '7613204.00308.testserial002';

/** Canonical GRAI for TC3 — maps to TU_NOTALLOWED (not associable with the picking-target LU) */
const GRAI_TU_NOT_ON_LU = '7613205.00307.testserial003';

/** Second distinct GRAI for TC4 (two-GRAI debounce error; same prefix/type as MAPPED) */
const GRAI_MAPPED_B = '7613204.00307.testserial004';

/** Completely unparseable string for TC5 */
const BARCODE_UNPARSEABLE = 'NOT-A-GRAI-BARCODE-12345';

/** Canonical GRAI for TC6 — maps to TU_NOCAPACITY (associated with LU but no PIIP for P1) */
const GRAI_NO_CAPACITY = '7613206.00307.testserial006';

// ─── DB setup helpers ──────────────────────────────────────────────────────────

/**
 * Execute a SQL statement against the mfstack test DB via docker exec.
 * The mfstack-db-1 container runs the E2E test database.
 */
const execSql = (sql) => {
    execSync(
        `docker exec mfstack-db-1 psql -U metasfresh -d metasfresh -c ${JSON.stringify(sql)}`,
        { stdio: 'pipe' }
    );
};

/**
 * Set C_BPartner.GRAIRequired for the BPartner by its numeric ID.
 *
 * @param {number} bpartnerId  – from masterdata.bpartners.BP1.id
 * @param {'Y'|'N'|'D'} code  – DB code: Y=Yes, N=No, D=YesWithDummyGRAIs
 */
const setGraiRequired = (bpartnerId, code) => {
    execSql(`UPDATE C_BPartner SET GRAIRequired = '${code}' WHERE C_BPartner_ID = ${bpartnerId}`);
};

/**
 * Insert (or replace) an M_HU_PI_GRAI mapping row linking a (companyPrefix, assetType) pair
 * to a TU M_HU_PI_ID. Uses ON CONFLICT DO UPDATE to always point to the freshly-created PI.
 *
 * @param {{ companyPrefix: string, assetType: string, tuPiId: number }} params
 */
const insertHuPiGrai = ({ companyPrefix, assetType, tuPiId }) => {
    execSql(
        `INSERT INTO M_HU_PI_GRAI (M_HU_PI_GRAI_ID, M_HU_PI_ID, GRAI_CompanyPrefix, GRAI_AssetType, ` +
        `AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy) ` +
        `VALUES (nextval('M_HU_PI_GRAI_SEQ'), ${tuPiId}, '${companyPrefix}', '${assetType}', ` +
        `(SELECT AD_Client_ID FROM M_HU_PI WHERE M_HU_PI_ID = ${tuPiId}), ` +
        `(SELECT AD_Org_ID FROM M_HU_PI WHERE M_HU_PI_ID = ${tuPiId}), ` +
        `'Y', now(), 100, now(), 100) ` +
        `ON CONFLICT (GRAI_CompanyPrefix, GRAI_AssetType) DO UPDATE ` +
        `SET M_HU_PI_ID = EXCLUDED.M_HU_PI_ID, Updated = now(), UpdatedBy = 100`
    );
};

/**
 * Clean up M_HU_PI_GRAI rows inserted by this spec.
 * Uses the well-known company prefix values used only in this spec.
 */
const cleanupHuPiGrais = () => {
    execSql(`DELETE FROM M_HU_PI_GRAI WHERE GRAI_CompanyPrefix IN ('7613204', '7613205', '7613206')`);
};

// ─── Shared masterdata factory ─────────────────────────────────────────────────

/**
 * Creates the standard masterdata for TC1–TC6.
 *
 * Three packing instruction sets:
 *   PI_MAIN      — LU_MAIN + TU_MAPPED (capacity for P1) → used for TC1/TC2/TC4/TC5
 *   PI_NOTALLOWED — LU_OTHER + TU_NOTALLOWED (different LU) → used for TC3
 *   PI_NOCAPACITY — LU_MAIN + TU_NOCAPACITY (will have its PIIP deleted) → used for TC6
 *
 * After creation, sets BP1.GRAIRequired='Y' and inserts three M_HU_PI_GRAI rows.
 */
const createMasterdataForGraiScan = async () => {
    const masterdata = await Backend.createMasterdata({
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
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                // TC1/TC2/TC4/TC5 — the "good" PI: TU_MAPPED allowed on LU_MAIN with capacity
                PI_MAIN: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_MAPPED', product: 'P1', qtyCUsPerTU: 4 },
                // TC3 — TU on a different LU (LU_OTHER), not associable with the picking-target LU_MAIN
                PI_NOTALLOWED: { lu: 'LU_OTHER', qtyTUsPerLU: 10, tu: 'TU_NOTALLOWED', product: 'P1', qtyCUsPerTU: 4 },
                // TC6 — TU associated with LU_MAIN but PIIP for P1 will be deleted
                PI_NOCAPACITY: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_NOCAPACITY', product: 'P1', qtyCUsPerTU: 4 },
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

    // Activate GRAIRequired on BP1
    setGraiRequired(masterdata.bpartners.BP1.id, 'Y');

    // tuPITestId is returned as e.g. "pi-3001629" — extract the numeric M_HU_PI_ID
    const parsePiId = (testId) => parseInt(testId.replace(/^pi-/, ''), 10);
    const tuMappedPiId = parsePiId(masterdata.packingInstructions.PI_MAIN.tuPITestId);
    const tuNotAllowedPiId = parsePiId(masterdata.packingInstructions.PI_NOTALLOWED.tuPITestId);
    const tuNocapacityPiId = parsePiId(masterdata.packingInstructions.PI_NOCAPACITY.tuPITestId);

    insertHuPiGrai({ companyPrefix: '7613204', assetType: '00307', tuPiId: tuMappedPiId });
    insertHuPiGrai({ companyPrefix: '7613205', assetType: '00307', tuPiId: tuNotAllowedPiId });
    insertHuPiGrai({ companyPrefix: '7613206', assetType: '00307', tuPiId: tuNocapacityPiId });

    return masterdata;
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
    await PickingGraiScanPanel.scanGrai({ graiString: GRAI_MAPPED });
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
                attributes: { GRAI: GRAI_MAPPED },
            },
        },
    });

    cleanupHuPiGrais();
});

// ─── TC2 — Scanned GRAI has no mapping → GRAINoMatchingTUType error ───────────

// noinspection JSUnusedLocalSymbols
test('TC2 — Scanned GRAI has no M_HU_PI_GRAI mapping → GRAINoMatchingTUType error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC2 no mapping');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // GRAI_UNMAPPED (prefix 7613204, type 00308) has no M_HU_PI_GRAI row.
    // The debounce fires ~1500ms after the scan; keep the race alive until the error toast appears.
    await expectErrorToast('GRAINoMatchingTUType error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: GRAI_UNMAPPED });
        // waitForScreen uses .first() to tolerate 2 simultaneous #SelectPickTargetScreen elements
        await SelectPickTargetTUScreen.waitForScreen();
    });

    cleanupHuPiGrais();
});

// ─── TC3 — Resolved TU not allowed on the picking-target LU → error ───────────

// noinspection JSUnusedLocalSymbols
test('TC3 — Resolved TU type not allowed on picking-target LU → GRAITUNotAllowedOnLU error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC3 TU not on LU');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // GRAI_TU_NOT_ON_LU resolves to TU_NOTALLOWED, which is only on LU_OTHER (not LU_MAIN)
    await expectErrorToast('GRAITUNotAllowedOnLU error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: GRAI_TU_NOT_ON_LU });
        await SelectPickTargetTUScreen.waitForScreen();
    });

    cleanupHuPiGrais();
});

// ─── TC4 — Two distinct GRAIs → GRAIMultipleScanned error, no list ─────────────

// noinspection JSUnusedLocalSymbols
test('TC4 — Two distinct GRAIs in debounce window → GRAIMultipleScanned error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC4 multiple GRAIs');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

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
    await PickingGraiScanPanel.scanGrai({ graiString: GRAI_MAPPED });
    await PickingGraiScanPanel.expectScannerVisible(); // flush gap assertion
    await PickingGraiScanPanel.scanGrai({ graiString: GRAI_MAPPED_B });

    // After the debounce timer fires with 2 distinct GRAIs → error toast
    await expectErrorToast('GRAIMultipleScanned error', async () => {
        await SelectPickTargetTUScreen.waitForScreen();
    });

    cleanupHuPiGrais();
});

// ─── TC5 — Unparseable barcode → scanner ignores it and stays live ─────────────

// noinspection JSUnusedLocalSymbols
test('TC5 — Unparseable barcode → scanner ignores it, stays live for valid scan', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC5 unparseable barcode');
    await allure.severity('normal');

    const masterdata = await createMasterdataForGraiScan();

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
    await PickingGraiScanPanel.scanGrai({ graiString: GRAI_MAPPED });
    await PickingJobLineScreen.waitForScreen();

    cleanupHuPiGrais();
});

// ─── TC6 — Resolved TU has no capacity for the product → error ───────────────

// noinspection JSUnusedLocalSymbols
test('TC6 — Resolved TU has no capacity for line product → GRAINoCapacityForProduct error', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI scan picking — TC6 no capacity');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiScan();

    // The masterdata API creates a M_HU_PI_Item_Product for P1 on TU_NOCAPACITY.
    // Delete it to simulate the "no capacity" scenario.
    const tuNocapacityPiId = parseInt(masterdata.packingInstructions.PI_NOCAPACITY.tuPITestId.replace(/^pi-/, ''), 10);
    execSql(
        `DELETE FROM M_HU_PI_Item_Product piip ` +
        `USING M_HU_PI_Item item ` +
        `WHERE piip.M_HU_PI_Item_ID = item.M_HU_PI_Item_ID ` +
        `AND item.M_HU_PI_Version_ID = ` +
        `  (SELECT M_HU_PI_Version_ID FROM M_HU_PI_Version WHERE M_HU_PI_ID = ${tuNocapacityPiId} AND IsCurrent = 'Y')`
    );

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    // GRAI_NO_CAPACITY resolves to TU_NOCAPACITY which is allowed on LU_MAIN
    // but has no M_HU_PI_Item_Product for P1
    await expectErrorToast('GRAINoCapacityForProduct error', async () => {
        await PickingGraiScanPanel.scanGrai({ graiString: GRAI_NO_CAPACITY });
        await SelectPickTargetTUScreen.waitForScreen();
    });

    cleanupHuPiGrais();
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
            bpartners: { BP_NOGRAI: {} },
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

    // Ensure GRAIRequired=No (the default; explicit for test clarity)
    setGraiRequired(masterdata.bpartners.BP_NOGRAI.id, 'N');

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
