import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';

const allureMeta = async () => {
    await allure.epic('E0160: Manufacturing Execution');
    await allure.feature('F8030: MobileUI Manufacturing');
    await allure.severity('critical');
};

// Drain scenario: HU(30), HU_SPARE(20), PP1(FG/BOM=20), PP2(FG_DRAIN/BOM=25).
//
// Plan creation (ID-order): HU gets a lower M_HU_ID than HU_SPARE, so both PP1 and PP2
// pick HU first. PP1's plan: step1.qtyToIssue=20 (stored; HU=30>20). PP2's plan:
// step1.qtyToIssue=25 (stored; HU=30>25).
//
// PP2 then issues 25 from HU → live HU drops to 5.
// Total stock after drain: HU(5) + HU_SPARE(20) = 25 >= PP1's BOM(20) → backend allows resume.
//
// When PP1 is resumed via the launcher (continueWorkflow → createJob), the backend recreates
// non-issued PP_Order_Issue_Schedule records from fresh HU availability: step.qtyToIssue = min(5, 20) = 5.
// These E2E tests verify that backend plan recalculation (createJob) produces correct suggestions;
// the JS fix (capping qtyToIssueTarget at qtyHUCapacity) is covered by the Jest unit test.
const createMasterdataForDrainTest = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            warehouses: { wh: {} },
            products: {
                COMP: {},
                FG: { bom: { lines: [{ product: 'COMP', qty: 20 }] } },
                FG_DRAIN: { bom: { lines: [{ product: 'COMP', qty: 25 }] } },
            },
            handlingUnits: {
                // HU listed first → lower M_HU_ID → plan creation picks it before HU_SPARE
                HU: { product: 'COMP', warehouse: 'wh', qty: 30 },
                // HU_SPARE ensures total stock >= PP1's BOM(20) after PP2 drains HU to 5
                HU_SPARE: { product: 'COMP', warehouse: 'wh', qty: 20 },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'FG', qty: 1, datePromised: '2025-03-01T00:00:00.000+02:00' },
                PP2: { warehouse: 'wh', product: 'FG_DRAIN', qty: 1, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
};

// Simple scenario: one HU, one PP order (FG/BOM=20).
const createMasterdataSimple = async ({ huQty }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            warehouses: { wh: {} },
            products: {
                COMP: {},
                FG: { bom: { lines: [{ product: 'COMP', qty: 20 }] } },
            },
            handlingUnits: {
                HU: { product: 'COMP', warehouse: 'wh', qty: huQty },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'FG', qty: 1, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
};

const loginAndStartMfg = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
};

// Issues PP2's raw-material step from the shared HU, then returns to the jobs list.
// This simulates a concurrent job consuming HU stock after PP1's plan was created.
const drainHUViaPP2 = async ({ masterdata }) => {
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP2.documentNo });
    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    // PP2 BOM=25, HU=30 → plan: step.qtyToIssue=25, qtyHUCapacity=30, suggestion=25.
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU.qrCode, expectQtyEntered: '25' });
    await RawMaterialIssueLineScreen.goBack();
    await ManufacturingJobScreen.goBack();
    // HU now has 30 - 25 = 5 COMP remaining.
};

// AC1 — Backend recalculates plan on resume: when HU stock dropped after PP1's plan was stored,
// createJob recreates the schedule with fresh HU data → suggestion reflects actual remaining HU qty.
test('AC1: Backend plan recalculation on resume reflects actual HU qty after concurrent drain', async ({ page }) => {
    await allureMeta();
    await allure.story('AC1: Backend plan recalculation reflects actual HU qty after concurrent drain');

    const masterdata = await createMasterdataForDrainTest();
    await loginAndStartMfg(masterdata);

    await test.step('Start PP1 to create its issue plan (step.qtyToIssue=20 stored for HU=30)', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.goBack();
    });

    await test.step('PP2 issues 25 from HU — HU reduced from 30 to 5', async () => {
        await drainHUViaPP2({ masterdata });
    });

    await test.step('Resume PP1 — backend createJob recalculates plan: suggestion = 5 (fresh HU capacity)', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        // createJob recreates schedule: step.qtyToIssue = min(5, 20) = 5. Backend suggestion = 5.
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU.qrCode, expectQtyEntered: '5' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1 });
    });
});

// AC2 — [REGRESSION] When HU capacity >= remaining BOM qty, suggestion = BOM remaining (fix must not regress this)
test('AC2 (regression): Suggestion unchanged when HU capacity exceeds remaining BOM qty', async ({ page }) => {
    await allureMeta();
    await allure.story('AC2 regression: large HU does not change suggestion');

    // HU(100): plan step.qtyToIssue=20 (capped at BOM=20). Fix: min(20,20,20,100)=20 — same result.
    const masterdata = await createMasterdataSimple({ huQty: 100 });
    await loginAndStartMfg(masterdata);

    await test.step('Scan HU(100) against BOM remaining(20) — suggestion must be 20', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU.qrCode, expectQtyEntered: '20' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1 });
    });
});

// AC3 — [REGRESSION] Confirming the (now correct) HU-capacity suggestion depletes HU with no inventory adjustment
test('AC3 (regression): Confirming HU-capacity suggestion depletes HU with no inventory adjustment', async ({ page }) => {
    await allureMeta();
    await allure.story('AC3 regression: no inventory adjustment when confirming correct suggestion');

    const masterdata = await createMasterdataForDrainTest();
    await loginAndStartMfg(masterdata);

    await test.step('Start PP1 to create its plan', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.goBack();
    });

    await test.step('PP2 drains HU to 5', async () => {
        await drainHUViaPP2({ masterdata });
    });

    await test.step('Resume PP1, confirm suggestion 5 — HU depleted, no inventory adjustment', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU.qrCode, expectQtyEntered: '5' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1 });
    });

    await Backend.expect({
        title: 'Confirming HU-capacity suggestion: HU depleted, no inventory adjustment',
        hus: {
            HU: { storages: { COMP: '0 PCE' } },
        },
    });
});

// AC4 — [REGRESSION] Worker typing qty > current HU capacity over-issues; backend handles it (inventory adjustment)
test('AC4 (regression): Over-issuing by typing more than current HU capacity creates inventory adjustment', async ({ page }) => {
    await allureMeta();
    await allure.story('AC4 regression: manual over-issue behavior unchanged');

    const masterdata = await createMasterdataForDrainTest();
    await loginAndStartMfg(masterdata);

    await test.step('Start PP1 to create its plan', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.goBack();
    });

    await test.step('PP2 drains HU to 5', async () => {
        await drainHUViaPP2({ masterdata });
    });

    await test.step('Resume PP1, type 8 (over HU capacity 5) — over-issues by 3, HU depleted', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.scanQRCode({
            qrCode: masterdata.handlingUnits.HU.qrCode,
            qtyEntered: '8',
        });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1 });
    });

    await Backend.expect({
        title: 'Over-issue: typed 8 against HU capacity 5 — HU depleted, inventory adjustment of 3 created',
        hus: {
            HU: { storages: { COMP: '0 PCE' } },
        },
    });
});
