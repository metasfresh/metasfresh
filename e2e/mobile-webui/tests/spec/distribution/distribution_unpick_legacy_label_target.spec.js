import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { DistributionStepScreen } from '../../utils/screens/distribution/DistributionStepScreen';
import { UnpickDialog } from '../../utils/dialogs/UnpickDialog';
import { expectErrorToast } from '../../utils/common';
import { expect } from '@playwright/test';

// The distribution unpick target is the direct analogue of the picking unpack target — a legacy
// customer barcode (ExternalBarcode) must resolve the target HU exactly like a metasfresh global
// QR code does today.
const createMasterdata = async ({ qtyToMove, targetExternalBarcode }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // allowPickingAnyHU MUST be set explicitly. MobileConfigDistributionCommand guards it with
            // `if (request.getAllowPickingAnyHU() != null)`, so unlike its neighbours it is NOT reset
            // to a default when omitted — it inherits whatever the previously-run spec left in the one
            // shared MobileUI_UserProfile_DD header row. It is load-bearing here: with it false,
            // DistributionJobCreateCommand takes a different job-creation path (it builds and saves a
            // DDOrderMovePlan with failIfNotFullAllocated(true)). true matches the column's DDL
            // default 'Y', i.e. the everyday customer configuration.
            mobileConfig: { distribution: { allowPickingAnyHU: true } },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // HU1: the pickable source HU moved by the DD order line.
                "HU1": { product: "P1", warehouse: "wh1", qty: qtyToMove },
                // HU2: the unpick target HU, already holding some stock on the floor. Carries the
                // legacy customer barcode under test instead of a metasfresh QR code.
                "HU2": { product: "P1", warehouse: "wh1", qty: 10, externalBarcode: targetExternalBarcode },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                }
            },
        }
    });
}

// Drives a fresh job up to the point where the moved HU sits on the step and the unpick dialog can be
// opened: log in, start the DD job, scan HU1 as the HU to move, open the step.
const startJobAndPickTheLine = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
    await DistributionLineScreen.clickStepButton({ index: 1 });
}

// noinspection JSUnusedLocalSymbols
test('Unpick onto a target HU identified by its legacy customer barcode', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution unpick to a target HU identified by a legacy/customer label');
    allure.severity('critical');

    const targetExternalBarcode = `EXT${Date.now()}`;
    const masterdata = await createMasterdata({ qtyToMove: 100, targetExternalBarcode });

    await startJobAndPickTheLine({ masterdata });

    // The target HU is already open on the floor and carries the customer's own printed barcode
    // (an ExternalBarcode) instead of a metasfresh QR code — the everyday case for a customer who
    // labels their own reusable pallets. The operator scans that label to return the moved HU.
    await test.step('Unpick, returning the moved HU into the target HU by scanning its printed customer barcode', async () => {
        await DistributionStepScreen.unpickToTarget({ targetHUQRCode: masterdata.handlingUnits.HU2.externalBarcode });
        await DistributionLineScreen.expectNoStepButton();
    });

    // The moved 100 PCE is merged into the target HU, which started at 10 PCE -> 110 PCE.
    await Backend.expect({
        title: 'legacy-barcode target: the moved HU is merged into the target HU (10 start + 100 = 110)',
        hus: {
            [masterdata.handlingUnits.HU2.qrCode]: { storages: { P1: '110 PCE' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Unpick onto a target HU identified by its plain HU value (no ExternalBarcode)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution unpick to a target HU identified by its plain M_HU.Value');
    allure.severity('critical');

    // The other everyday legacy case: the target pallet carries no ExternalBarcode AT ALL, only the
    // plain M_HU.Value printed on it. M_HU.Value always equals the M_HU_ID (confirmed by the feature
    // owner), so the value on such a label is exactly the huId the masterdata API already returns.
    // targetExternalBarcode is deliberately omitted so HU2.ExternalBarcode is genuinely NULL in the
    // DB — passing one here would leave this a near-duplicate of test 1's data shape and would never
    // exercise resolution against an unlabelled target.
    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await startJobAndPickTheLine({ masterdata });

    await test.step('Unpick, returning the moved HU into the target HU by scanning its plain HU value', async () => {
        await DistributionStepScreen.unpickToTarget({ targetHUQRCode: masterdata.handlingUnits.HU2.huId });
        await DistributionLineScreen.expectNoStepButton();
    });

    await Backend.expect({
        title: 'plain-HU-value target: the moved HU is merged into the target HU (10 start + 100 = 110)',
        hus: {
            [masterdata.handlingUnits.HU2.qrCode]: { storages: { P1: '110 PCE' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan an unrecognised code as the unpick target — clear message, nothing moves, recovery works', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution unpick target rejects an unrecognised code with a clear message');
    allure.severity('critical');

    const masterdata = await createMasterdata({ qtyToMove: 100, targetExternalBarcode: `EXT${Date.now()}` });
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;
    // A code matching no known format at all — no metasfresh QR code, no GS1/EAN, and no
    // ExternalBarcode or M_HU.Value on file. The case where the system genuinely cannot identify
    // what was scanned.
    const unrecognisedCode = `NOT-A-CODE-${Date.now()}`;

    await startJobAndPickTheLine({ masterdata });

    await test.step('Scan a code the system cannot identify as the unpick target — the operator must see a clear, actionable message', async () => {
        await DistributionStepScreen.openUnpickDialog();
        await expectErrorToast('Unrecognised code at unpick target', async () => {
            await UnpickDialog.scanTargetHU(unrecognisedCode);
        }, ({ textContent }) => {
            // The operator must be told the scan was not recognised, not handed a generic
            // "please try again / contact support" report with no actionable detail.
            expect(textContent).toContain('QR code not recognized');
        });
        // The dialog stayed open with its scanner armed, so the operator can just scan the right one.
        await UnpickDialog.expectDialogVisible();
    });

    await Backend.expect({
        title: 'after the unrecognised scan: nothing moved — the moved HU still holds all 100 PCE and the target HU its original 10 PCE',
        hus: {
            // Both sides asserted: "nothing moved" is then a direct observation rather than an
            // inference from the later recovery total.
            [masterdata.handlingUnits.HU1.qrCode]: { storages: { P1: '100 PCE' } },
            [targetHUQRCode]: { storages: { P1: '10 PCE' } },
        },
    });

    await test.step('Scan the correct target HU — the unpick commits normally and the panel is usable again', async () => {
        await UnpickDialog.scanTargetHU(targetHUQRCode);
        await DistributionLineScreen.waitForScreen();
        await DistributionLineScreen.expectNoStepButton();
    });

    await Backend.expect({
        title: 'recovery: the moved HU is merged into the target HU (10 start + 100 = 110)',
        hus: {
            [targetHUQRCode]: { storages: { P1: '110 PCE' } },
        },
    });
});
