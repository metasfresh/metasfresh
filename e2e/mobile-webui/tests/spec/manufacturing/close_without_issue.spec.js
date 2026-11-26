import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// Closing a manufacturing order from the mobile app when the operator never hand-issued any raw material:
// the close must settle plainly (docStatus CL, no cost difference distribution) and the WIP account must show
// nothing but the receipt and the auto-issued packaging.
//
// Test data mirrors a real production order: the finished good is priced per piece, the foil is priced per
// metre and only sits on a Packing BOM line (IssueOnlyForReceived), its product category is the source-HU
// category of the warehouse, the label is a Packing line without such a category, the raw material is a
// kg line that is issued by hand (Backflush) and is never issued here.

/** AD_WF_Node of the production workflow: the "issue raw material" activity (type MI) */
const NODE_540277 = 540277;

// production costing method is MovingAverageInvoice
const COSTING_METHOD_MOVING_AVERAGE_INVOICE = 'M';

// the real foil is stocked and priced per metre; metre (MTR) is inactive in the E2E seed, so the foil is stocked per centimetre
const FOIL_UOM = 'CM';
const FG_COST = 16.93;
const FOIL_COST_PER_UOM = 2.5;
const FOIL_QTY_PER_PIECE = 0.3;
const LABEL_COST = 0.05;
const QTY_RECEIVED = 2;

let costingMethodState;
let wfNodeStatesToRestore = [];

// Restore in afterEach so it runs even when the test body fails or times out; otherwise the costing method
// or the workflow node flags leak into the sibling specs.
// the hooks must declare the page fixture: Backend uses the page of the current test
test.beforeEach(async ({ page }) => {
    wfNodeStatesToRestore = [];
    costingMethodState = undefined;
    // before createMasterdata: the current-cost fixture writes under the costing method that is current at that time
    costingMethodState = await Backend.setAcctSchemaCostingMethod(COSTING_METHOD_MOVING_AVERAGE_INVOICE);
});

test.afterEach(async ({ page }) => {
    for (const state of wfNodeStatesToRestore.reverse()) {
        await Backend.setWFNodeFlags(state);
    }
    wfNodeStatesToRestore = [];
    await Backend.restoreAcctSchemaCostingMethod(costingMethodState);
    costingMethodState = undefined;
});

/** sets the flags of NODE_540277 and remembers how to put it back */
const setNode540277 = async ({ isActive, alwaysAvailableToUser }) => {
    const previous = await Backend.setWFNodeFlags({ adWfNodeId: NODE_540277, isActive, alwaysAvailableToUser });
    wfNodeStatesToRestore.push(previous);
};

const createMasterdata = async ({ withPackaging }) => {
    const bomLines = [{ product: 'RAW', qty: 1.2, componentType: 'Component', issueMethod: 'Backflush', uom: 'KGM' }];
    if (withPackaging) {
        bomLines.push(
            { product: 'FOIL', qty: FOIL_QTY_PER_PIECE, componentType: 'Packing', issueMethod: 'IssueOnlyForReceived', uom: FOIL_UOM },
            { product: 'LABEL', qty: 1, componentType: 'Packing', issueMethod: 'IssueOnlyForReceived' },
        );
    }

    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            productCategories: { foilCat: {} },
            warehouses: {
                wh: withPackaging ? { receiveAsSourceHU: true, sourceHUProductCategories: ['foilCat'] } : {},
            },
            products: {
                RAW: { uom: 'KGM', currentCostPrice: 5 },
                FOIL: { uom: FOIL_UOM, currentCostPrice: FOIL_COST_PER_UOM, productCategory: 'foilCat' },
                LABEL: { currentCostPrice: LABEL_COST },
                FG: {
                    currentCostPrice: FG_COST,
                    bom: { lines: bomLines },
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU', product: 'FG', qtyCUsPerTU: QTY_RECEIVED },
            },
            handlingUnits: {
                // in stock (the job start needs it) but never issued by the operator
                HU_RAW: { product: 'RAW', warehouse: 'wh', qty: 100 },
                ...(withPackaging
                    ? {
                        HU_FOIL: { product: 'FOIL', warehouse: 'wh', qty: 100, sourceHU: true },
                        HU_LABEL: { product: 'LABEL', warehouse: 'wh', qty: 1000, sourceHU: true },
                    }
                    : {}),
            },
            manufacturingOrders: {
                PP1: {
                    warehouse: 'wh',
                    product: 'FG',
                    qty: 10,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

const startJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    return await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
};

const receiveFinishedGood = async ({ masterdata, qtyReceived }) => {
    const receiveButtonIndex = 1;
    await ManufacturingJobScreen.clickReceiveButton({ index: receiveButtonIndex });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({ qtyEntered: qtyReceived });
    await ManufacturingJobScreen.waitForScreen();
};

// noinspection JSUnusedLocalSymbols
test('labelOnly_foilsAutoIssued_closesPlainly', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Close without hand-issued raw material: packaging auto-issued, no distribution');
    allure.severity('critical');

    // Production shape of the workflow: the issue activity is available to the operator without being mandatory.
    await setNode540277({ isActive: true, alwaysAvailableToUser: '' });
    // NOTE: the production workflow also carries the PrintReceivedHUQRCodes label-print activity. The E2E database
    // has no such node (workflow 540118 has none), so the label print is not exercised here.
    const masterdata = await createMasterdata({ withPackaging: true });
    const { jobId } = await startJob(masterdata);

    await test.step('Receive 2 Stk without scanning any raw material', async () => {
        await receiveFinishedGood({ masterdata, qtyReceived: QTY_RECEIVED });
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '10 Stk', qtyReceived: `${QTY_RECEIVED} Stk` });

        await Backend.expect({
            title: 'The receipt works; the foil and the label are auto-issued',
            manufacturings: {
                [jobId]: {
                    receivedHUs: [{ tu: 'tu1', qty: `${QTY_RECEIVED} PCE` }],
                    costCollectors: [
                        { type: 'ComponentIssue', product: 'FOIL', present: true },
                        { type: 'ComponentIssue', product: 'LABEL', present: true },
                        { type: 'ComponentIssue', product: 'RAW', present: false },
                    ],
                },
            },
        });
    });

    await test.step('Close the order', async () => {
        await ManufacturingJobScreen.complete();

        const foilCost = QTY_RECEIVED * FOIL_QTY_PER_PIECE * FOIL_COST_PER_UOM;
        const labelCost = QTY_RECEIVED * LABEL_COST;
        const receiptValue = QTY_RECEIVED * FG_COST;

        await Backend.expect({
            title: 'Closed plainly: receipt value and auto-issued packaging stay on the WIP account',
            manufacturings: {
                [jobId]: {
                    waitUntilPostedSeconds: 60,
                    docStatus: 'CL',
                    costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: false }],
                    currentCostPrices: { FG: FG_COST },
                    wipAcctBalance: foilCost + labelCost - receiptValue,
                },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('nothingIssuedNothingReceived_closesPlainly', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F1500: Costing');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Close an order on which nothing was issued and nothing was received');
    allure.severity('normal');

    await setNode540277({ isActive: true, alwaysAvailableToUser: '' });
    const masterdata = await createMasterdata({ withPackaging: false });
    const { jobId } = await startJob(masterdata);

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Closed plainly, nothing on the WIP account',
        manufacturings: {
            [jobId]: {
                waitUntilPostedSeconds: 60,
                docStatus: 'CL',
                costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: false }],
                wipAcctBalance: 0,
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('orderCreatedWithoutIssueStep_receiveAndClose_closesPlainly', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Close an order whose routing has no raw material issue step');
    allure.severity('normal');

    // The order routing is copied from the workflow when the order is created: switch the issue activity off for
    // the creation only, then put it back (afterEach restores it as well should the test fail in between).
    const nodeBefore = await Backend.setWFNodeFlags({ adWfNodeId: NODE_540277, isActive: false });
    wfNodeStatesToRestore.push(nodeBefore);
    const masterdata = await createMasterdata({ withPackaging: false });
    await Backend.setWFNodeFlags({ adWfNodeId: NODE_540277, isActive: nodeBefore.isActive, alwaysAvailableToUser: nodeBefore.alwaysAvailableToUser });

    const { jobId } = await startJob(masterdata);
    await receiveFinishedGood({ masterdata, qtyReceived: QTY_RECEIVED });
    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Closed plainly although the order has no issue step',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: `${QTY_RECEIVED} PCE` }],
                waitUntilPostedSeconds: 60,
                docStatus: 'CL',
                costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: false }],
            },
        },
    });
});
