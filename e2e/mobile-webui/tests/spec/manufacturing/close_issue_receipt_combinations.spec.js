import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { page, expectErrorToast } from '../../utils/common';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { YesNoDialog } from '../../utils/dialogs/YesNoDialog';

// Closing a manufacturing order from the mobile app in the issue/receipt combinations that are not the reference case:
//  (c) raw material issued by hand, nothing received: the whole issued cost is booked to COGS,
//  (d) packaging whose product category is not a source-HU category: nothing is auto-issued and the close is unaffected,
//  (f) the settlement fails (a close validation throws after the settlement was created): the operator sees the error
//      and the order stays Completed with nothing saved, so the close can be retried.
//
// Test data mirrors a real production order, see close_settles_cost_difference.spec.js.

// production costing method is MovingAverageInvoice
const COSTING_METHOD_MOVING_AVERAGE_INVOICE = 'M';

// the real foil is priced per metre; metre (MTR) is inactive in the E2E seed, so the foil is stocked per centimetre
const FOIL_UOM = 'CM';

const FG_COST = 16.93;
const RAW_COST_PER_KG = 5;
const FOIL_COST_PER_UOM = 2.5;
const LABEL_COST = 0.05;

const RAW_KG_PER_PIECE = 1.2;
const RAW_HU_KG = 4.5; // the whole HU is issued, although 2 pieces need only 2.4 kg
const FOIL_QTY_PER_PIECE = 0.3;
const FOIL_STOCK_QTY = 100;
const LABEL_STOCK_QTY = 1000;
const QTY_ORDERED = 10;
const QTY_RECEIVED = 2;
const RAW_ISSUING_TOLERANCE_PERC = 10;

// above the up-to-30 s posting wait of the close
const CLOSE_RESPONSE_TIMEOUT = 45_000;

const SYSCONFIG_LaunchersLimit = 'WorkflowRestAPIService.LaunchersLimit';

let costingMethodState;
let previousSysconfigs = null;
let collectedFailures = [];

// the hooks must declare the page fixture: Backend uses the page of the current test
test.beforeEach(async ({ page }) => {
    collectedFailures = [];
    // before createMasterdata: the current-cost fixture writes under the costing method that is current at that time
    costingMethodState = await Backend.setAcctSchemaCostingMethod(COSTING_METHOD_MOVING_AVERAGE_INVOICE);
});

test.afterEach(async ({ page }) => {
    try {
        await Backend.restoreAcctSchemaCostingMethod(costingMethodState);
    } finally {
        costingMethodState = undefined;
        if (previousSysconfigs) {
            // fallback when createMasterdata threw after writing: the migration seed of the limit (20)
            await Backend.setSysconfigs({ [SYSCONFIG_LaunchersLimit]: '20', ...previousSysconfigs });
        }
        previousSysconfigs = null;
    }
});

// Runs the check and remembers its failure instead of throwing it, so that a red run reports every wrong
// expectation of the flow, not only the first one. Call throwCollectedFailures at the end.
const collectFailure = async (title, check) => {
    try {
        await check();
    } catch (error) {
        collectedFailures.push(`[${title}] ${error.message}`);
    }
};
const throwCollectedFailures = () => {
    const failures = collectedFailures;
    collectedFailures = [];
    if (failures.length > 0) {
        throw new Error(failures.join('\n\n'));
    }
};

// The jobs list is capped by the launchers limit and sorted by DocumentNo; the orders of the sibling specs that
// share the one E2E DB would crowd this spec's order out of the list, so the limit is raised for the test.
const createMasterdata = async ({ packaging, rawIssuingTolerancePerc }) => {
    const bomLines = [{ product: 'RAW', qty: RAW_KG_PER_PIECE, componentType: 'Component', issueMethod: 'Backflush', uom: 'KGM', issuingTolerancePerc: rawIssuingTolerancePerc }];
    if (packaging) {
        bomLines.push(
            { product: 'FOIL', qty: FOIL_QTY_PER_PIECE, componentType: 'Packing', issueMethod: 'IssueOnlyForReceived', uom: FOIL_UOM },
            { product: 'LABEL', qty: 1, componentType: 'Packing', issueMethod: 'IssueOnlyForReceived' },
        );
    }

    previousSysconfigs = {};
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            sysconfigs: { [SYSCONFIG_LaunchersLimit]: '10000' },
            productCategories: { foilCat: {} },
            // the foil category is NOT a source-HU category of the warehouse (case d), and in the other cases there is no packaging
            warehouses: { wh: {} },
            products: {
                RAW: { uom: 'KGM', currentCostPrice: RAW_COST_PER_KG },
                FOIL: { uom: FOIL_UOM, currentCostPrice: FOIL_COST_PER_UOM, productCategory: 'foilCat' },
                LABEL: { currentCostPrice: LABEL_COST },
                FG: { currentCostPrice: FG_COST, bom: { lines: bomLines } },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU', product: 'FG', qtyCUsPerTU: QTY_RECEIVED },
            },
            handlingUnits: {
                HU_RAW: { product: 'RAW', warehouse: 'wh', qty: RAW_HU_KG },
                // the job start needs enough raw material on stock for the whole order
                HU_RAW_STOCK: { product: 'RAW', warehouse: 'wh', qty: 100 },
                ...(packaging
                    ? {
                        HU_FOIL: { product: 'FOIL', warehouse: 'wh', qty: FOIL_STOCK_QTY },
                        HU_LABEL: { product: 'LABEL', warehouse: 'wh', qty: LABEL_STOCK_QTY },
                    }
                    : {}),
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'FG', qty: QTY_ORDERED, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
    previousSysconfigs = masterdata.previousSysconfigs ?? {};
    return masterdata;
};

const startJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    return await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
};

const issueRawHU = async ({ masterdata }) => {
    await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_RAW.qrCode, expectQtyEntered: `${RAW_HU_KG}` });
};

const receive = async ({ masterdata }) => {
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({ qtyEntered: QTY_RECEIVED });
    await ManufacturingJobScreen.waitForScreen();
};

// presses "Produktion abschliessen" and confirms, expecting the close to fail; returns the error toast text
const completeExpectingError = async (title) => {
    let toastText;
    await expectErrorToast(title, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        // on success the jobs list shows up; on failure the toast wins the race. The close first waits up to 30 s for
        // the posting of the order's collectors, so the wait must outlast that (the screen helper's own wait is 20 s).
        await page.locator('#WFLaunchersScreen').waitFor({ timeout: CLOSE_RESPONSE_TIMEOUT });
    }, ({ textContent }) => {
        toastText = textContent;
    });
    return toastText;
};

// the job is reopened from the jobs list so that the activity state is read from the server again
const reopenJob = async (masterdata) => {
    await ManufacturingJobScreen.goBack();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
};

// noinspection JSUnusedLocalSymbols
test('issuedNothingReceived_closeBooksIssuedCostToCogs', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F1500: Costing');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Close with raw material issued but nothing received books the issued cost to COGS');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packaging: false });
    const { jobId } = await startJob(masterdata);

    await issueRawHU({ masterdata });
    await ManufacturingJobScreen.complete();

    // issued cost = raw (whole HU) = 4.5 * 5 = 22.5
    // nothing received, so the whole issued cost is the residual and is booked to COGS (Dr)
    const issuedCost = RAW_HU_KG * RAW_COST_PER_KG;

    await Backend.expect({
        title: 'Closed: the difference is distributed, WIP is empty and the whole issued cost is on COGS',
        manufacturings: {
            [jobId]: {
                waitUntilPostedSeconds: 60,
                docStatus: 'CL',
                costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: true }],
                costDifference: 0,
                wipAcctBalance: 0,
                cogsAcctBalance: issuedCost,
                orderFactAcctPresent: false,
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('packagingWithoutSourceHU_doesNotAffectClose', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F1500: Costing');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Packaging that is not marked as source HU is not issued and does not affect the close');
    allure.severity('normal');

    const masterdata = await createMasterdata({ packaging: true });
    const { jobId } = await startJob(masterdata);

    await issueRawHU({ masterdata });
    await receive({ masterdata });
    await ManufacturingJobScreen.complete();

    // issued cost = raw (whole HU) = 4.5 * 5 = 22.5 (no packaging is issued)
    // receipt value = 2 * 16.93 = 33.86; residual = 22.5 - 33.86 = -11.36 (settled by the close, WIP ends at 0)
    await Backend.expect({
        title: 'Closed: distribution present, WIP empty, no packaging was issued',
        manufacturings: {
            [jobId]: {
                waitUntilPostedSeconds: 60,
                docStatus: 'CL',
                costCollectors: [
                    { type: 'CostDifferenceDistribution', product: 'FG', present: true },
                    { type: 'ComponentIssue', product: 'FOIL', present: false },
                    { type: 'ComponentIssue', product: 'LABEL', present: false },
                ],
                costDifference: 0,
                wipAcctBalance: 0,
                orderFactAcctPresent: false,
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('closeFailsAfterSettlement_operatorSeesError_orderStaysCompleted', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F1500: Costing');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('A close validation error after the settlement was created rolls the close back, shows the error and can be retried');
    allure.severity('critical');

    // The failure trigger: the raw-material line enforces an issuing tolerance and the operator issues less than its
    // lower bound. The close validation (closeOrder) then throws after the settlement was created.
    //   required = 1.2 * 10 = 12 kg; lower bound = 12 - 10 % = 10.8 kg; issued = 4.5 kg  ->  below the bound
    // residual = issued cost - receipt value = 22.5 - 33.86 <> 0, so the settlement is created before the failure.
    const masterdata = await createMasterdata({ packaging: false, rawIssuingTolerancePerc: RAW_ISSUING_TOLERANCE_PERC });
    const { jobId } = await startJob(masterdata);

    await issueRawHU({ masterdata });
    await receive({ masterdata });

    let firstError;
    await test.step('Close fails: the operator sees the error', async () => {
        firstError = await completeExpectingError('close below the issuing tolerance');
        // WHICH error: the close validation (AD_Message OrderBOMLineQuantities.CannotIssueLessThan, en_US login) and not
        // an earlier failure such as a posting error; otherwise the rollback of the created settlement is not what is shown
        expect(firstError).toContain('Cannot issue less than');
    });

    await collectFailure('after failed close - order', () => Backend.expect({
        title: 'Nothing of the close is saved: order stays Completed, no settlement',
        manufacturings: {
            [jobId]: {
                docStatus: 'CO',
                costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: false }],
                orderFactAcctPresent: false,
            },
        },
    }));

    await collectFailure('after failed close - step not completed', async () => {
        await reopenJob(masterdata);
        await expect(page.locator('#last-confirm-button [data-testid="indicator"].indicator-color-green')).toHaveCount(0);
    });

    await collectFailure('retry shows the same error', async () => {
        const secondError = await completeExpectingError('retry of the close');
        expect(secondError).toBe(firstError);
    });

    throwCollectedFailures();
});
