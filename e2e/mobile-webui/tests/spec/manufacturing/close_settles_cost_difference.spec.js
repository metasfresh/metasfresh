import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// Closing a manufacturing order from the mobile app settles the difference between what was issued and what was
// received: the close books a CostDifferenceDistribution, the order ends with costDifference 0, and the WIP account
// of the order's cost collectors balances to 0.
//
// Test data mirrors a real production order: the finished good is priced per piece, the raw material is a kg line that
// the operator issues by hand (Backflush) as a whole HU that holds more than the 2 received pieces need, the foil is
// priced per length unit and sits only on a Packing BOM line (IssueOnlyForReceived), its product category is the
// source-HU category of the warehouse, and the label is a Packing line whose category is not configured.
//
// The foil arrives by purchase order + receipt (NOT created with sourceHU: true): that the receipt marks it as source HU
// is part of what is tested, otherwise the receipt of the finished good would not issue the foil.

// production costing method is MovingAverageInvoice
const COSTING_METHOD_MOVING_AVERAGE_INVOICE = 'M';

// the real foil is stocked and priced per metre with 0.3 per piece; metre (MTR) is inactive in the E2E seed, so the
// foil is stocked, priced and consumed per centimetre. The shape stays 0.3 / Stk and 2.5 per stock unit in the converted unit.
const FOIL_UOM = 'CM';

const FG_COST = 16.93;
const RAW_COST_PER_KG = 5;
const FOIL_COST_PER_UOM = 2.5;
const LABEL_COST = 0.05;

const RAW_KG_PER_PIECE = 1.2;
const RAW_HU_KG = 4.5; // the whole HU is issued, although 2 pieces need only 2.4 kg
const FOIL_QTY_PER_PIECE = 0.3;
const FOIL_PURCHASED_QTY = 100;
const LABEL_STOCK_QTY = 1000;
const QTY_ORDERED = 10;
const QTY_RECEIVED = 2;
const FG_PRIOR_STOCK_QTY = 8;
const CO_PRODUCT_PERCENT = 20;

const round = (value) => Number(value.toFixed(6));

const SYSCONFIG_LaunchersLimit = 'WorkflowRestAPIService.LaunchersLimit';

let costingMethodState;
let previousSysconfigs = null;

// Restore in afterEach so it runs even when the test body fails or times out; otherwise the costing method leaks into the sibling specs.
// the hooks must declare the page fixture: Backend uses the page of the current test
test.beforeEach(async ({ page }) => {
    costingMethodState = undefined;
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

// The jobs list is capped by the launchers limit and sorted by DocumentNo; the orders of the sibling specs that
// share the one E2E DB would crowd this spec's order out of the list, so the limit is raised for the test.
const createMasterdata = async ({ request, ...rest }) => {
    previousSysconfigs = {};
    const masterdata = await Backend.createMasterdata({
        ...rest,
        request: { ...request, sysconfigs: { [SYSCONFIG_LaunchersLimit]: '10000' } },
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

// Runs the check and remembers its failure instead of throwing it, so that the rest of the flow still runs and a
// red run reports every wrong expectation of the flow, not only the first one. Call throwCollectedFailures at the end.
let collectedFailures = [];
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

const receive = async ({ index, tuPIItemProductTestId, qtyEntered }) => {
    await ManufacturingJobScreen.clickReceiveButton({ index });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({ qtyEntered });
    await ManufacturingJobScreen.waitForScreen();
};

// noinspection JSUnusedLocalSymbols
test('referenceCase_issueReceiveClose_settlesDifference', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F1500: Costing');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Close settles the difference between issued material and received goods');
    allure.severity('critical');

    const masterdata = await createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            bpartners: { VENDOR: { isVendor: true, isCustomer: false, isSoPriceList: false } },
            productCategories: { foilCat: {} },
            warehouses: {
                wh: { receiveAsSourceHU: true, sourceHUProductCategories: ['foilCat'] },
            },
            products: {
                RAW: { uom: 'KGM', currentCostPrice: RAW_COST_PER_KG },
                FOIL: { uom: FOIL_UOM, currentCostPrice: FOIL_COST_PER_UOM, productCategory: 'foilCat', price: FOIL_COST_PER_UOM },
                LABEL: { currentCostPrice: LABEL_COST },
                FG: {
                    currentCostPrice: FG_COST,
                    bom: {
                        lines: [
                            { product: 'RAW', qty: RAW_KG_PER_PIECE, componentType: 'Component', issueMethod: 'Backflush', uom: 'KGM' },
                            { product: 'FOIL', qty: FOIL_QTY_PER_PIECE, componentType: 'Packing', issueMethod: 'IssueOnlyForReceived', uom: FOIL_UOM },
                            { product: 'LABEL', qty: 1, componentType: 'Packing', issueMethod: 'IssueOnlyForReceived' },
                        ],
                    },
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU', product: 'FG', qtyCUsPerTU: QTY_RECEIVED },
                // the purchased foil arrives in one TU (a foil roll), without pallet
                PI_FOIL: { tu: 'TU_FOIL', product: 'FOIL', qtyCUsPerTU: FOIL_PURCHASED_QTY },
            },
            purchaseOrders: {
                PO_FOIL: { bpartner: 'VENDOR', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'FOIL', qty: FOIL_PURCHASED_QTY, piItemProduct: 'TU_FOIL' }] },
            },
            receipts: {
                // the foil HU is registered here; it is NOT created with sourceHU: true
                RECEIPT_FOIL: { purchaseOrder: 'PO_FOIL', receivedHU: 'HU_FOIL' },
            },
            handlingUnits: {
                HU_RAW: { product: 'RAW', warehouse: 'wh', qty: RAW_HU_KG },
                // the job start needs enough raw material on stock for the whole order
                HU_RAW_STOCK: { product: 'RAW', warehouse: 'wh', qty: 100 },
                // the label category is not a source-HU category, so this HU is not marked
                HU_LABEL: { product: 'LABEL', warehouse: 'wh', qty: LABEL_STOCK_QTY },
                // prior stock of the finished good, valued at its current cost
                HU_FG_PRIOR: { product: 'FG', warehouse: 'wh', qty: FG_PRIOR_STOCK_QTY },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'FG', qty: QTY_ORDERED, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
    const { jobId } = await startJob(masterdata);

    await test.step('Issue the whole 4.5 kg HU and receive 2 Stk', async () => {
        await issueRawHU({ masterdata });
        await receive({ index: 1, tuPIItemProductTestId: masterdata.packingInstructions.PI.tuPIItemProductTestId, qtyEntered: QTY_RECEIVED });

        const foilIssued = round(QTY_RECEIVED * FOIL_QTY_PER_PIECE); // 2 * 0.3 = 0.6
        await collectFailure('after receipt', () => Backend.expect({
            title: 'The receipt auto-issues the foil (marked as source HU by the purchase receipt) but not the label',
            manufacturings: {
                [jobId]: {
                    costCollectors: [
                        { type: 'ComponentIssue', product: 'RAW', present: true },
                        { type: 'ComponentIssue', product: 'FOIL', qty: foilIssued, present: true },
                        { type: 'ComponentIssue', product: 'LABEL', present: false },
                    ],
                },
            },
        }));
        // own call: the backend asserts manufacturings before hus and stops at the first failing block
        await collectFailure('after receipt - foil HU', () => Backend.expect({
            title: 'The foil HU is reduced by the issued foil',
            hus: { HU_FOIL: { storages: { FOIL: `${round(FOIL_PURCHASED_QTY - foilIssued)} ${FOIL_UOM}` } } },
        }));
        await collectFailure('after receipt - label HU', () => Backend.expect({
            title: 'The label HU is unchanged',
            hus: { HU_LABEL: { storages: { LABEL: `${LABEL_STOCK_QTY} PCE` } } },
        }));
    });

    await test.step('Close the order', async () => {
        await ManufacturingJobScreen.complete();

        // issued cost = raw (whole HU) + foil
        //             = 4.5 * 5 + (2 * 0.3) * 2.5 = 22.5 + 1.5 = 24
        // receipt value (at the finished good's cost) = 2 * 16.93 = 33.86
        // residual = issued cost - receipt value = 24 - 33.86 = -9.86 (settled by the close)
        // finished good's new cost = (prior stock value + receipt value + residual) / qty
        //                          = (8 * 16.93 + 33.86 - 9.86) / (8 + 2) = 159.44 / 10 = 15.944
        const issuedCost = RAW_HU_KG * RAW_COST_PER_KG + QTY_RECEIVED * FOIL_QTY_PER_PIECE * FOIL_COST_PER_UOM;
        const receiptValue = QTY_RECEIVED * FG_COST;
        const residual = issuedCost - receiptValue;
        const fgCost = round((FG_PRIOR_STOCK_QTY * FG_COST + receiptValue + residual) / (FG_PRIOR_STOCK_QTY + QTY_RECEIVED));

        await collectFailure('after close', () => Backend.expect({
            title: 'Closed: the difference is distributed and nothing is left on the WIP account',
            manufacturings: {
                [jobId]: {
                    waitUntilPostedSeconds: 60,
                    docStatus: 'CL',
                    costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: true }],
                    costDifference: 0,
                    wipAcctBalance: 0,
                    // the facts are booked on the cost collectors only, never on the order itself
                    orderFactAcctPresent: false,
                    currentCostPrices: { FG: fgCost },
                },
            },
        }));
    });

    throwCollectedFailures();
});

// noinspection JSUnusedLocalSymbols
test('coProduct_issueReceiveClose_valuesCoProductByPercent', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F1500: Costing');
    allure.tag('F1500');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Close values the co-product by its cost distribution percent');
    allure.severity('critical');

    // Reduced shape on purpose (no packaging, no prior FG stock, no FG cost): it isolates the co-product percent split
    const masterdata = await createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            warehouses: { wh: {} },
            products: {
                RAW: { uom: 'KGM', currentCostPrice: RAW_COST_PER_KG },
                CP: { coProductCostDistributionPercent: CO_PRODUCT_PERCENT },
                FG: {
                    bom: {
                        lines: [
                            { product: 'RAW', qty: RAW_KG_PER_PIECE, componentType: 'Component', issueMethod: 'Backflush', uom: 'KGM' },
                            // one co-product piece per finished piece
                            { product: 'CP', qty: -1, componentType: 'CoProduct' },
                        ],
                    },
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU', product: 'FG', qtyCUsPerTU: QTY_RECEIVED },
                PI_CP: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU_CP', product: 'CP', qtyCUsPerTU: QTY_RECEIVED },
            },
            handlingUnits: {
                HU_RAW: { product: 'RAW', warehouse: 'wh', qty: RAW_HU_KG },
                // the job start needs enough raw material on stock for the whole order
                HU_RAW_STOCK: { product: 'RAW', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'FG', qty: QTY_ORDERED, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
    const { jobId } = await startJob(masterdata);

    await test.step('Issue the whole 4.5 kg HU, receive 2 Stk and 2 co-product pieces', async () => {
        await issueRawHU({ masterdata });
        await receive({ index: 1, tuPIItemProductTestId: masterdata.packingInstructions.PI.tuPIItemProductTestId, qtyEntered: QTY_RECEIVED });
        await receive({ index: 2, tuPIItemProductTestId: masterdata.packingInstructions.PI_CP.tuPIItemProductTestId, qtyEntered: QTY_RECEIVED });
    });

    await test.step('Close the order', async () => {
        await ManufacturingJobScreen.complete();

        // input cost = 4.5 * 5 = 22.5 (the only issue)
        // co-product value = 20 % of the input cost = 0.2 * 22.5 = 4.5  ->  per piece 4.5 / 2 = 2.25
        // finished good gets the rest = 22.5 - 4.5 = 18  ->  per piece 18 / 2 = 9 (no prior stock)
        const inputCost = RAW_HU_KG * RAW_COST_PER_KG;
        const coProductValue = (inputCost * CO_PRODUCT_PERCENT) / 100;
        const fgValue = inputCost - coProductValue;

        await Backend.expect({
            title: 'Closed: the co-product is valued at 20 % of the input cost, the finished good gets the rest',
            manufacturings: {
                [jobId]: {
                    waitUntilPostedSeconds: 60,
                    docStatus: 'CL',
                    costCollectors: [{ type: 'CostDifferenceDistribution', product: 'FG', present: true }],
                    costDifference: 0,
                    wipAcctBalance: 0,
                    orderFactAcctPresent: false,
                    currentCostPrices: {
                        CP: round(coProductValue / QTY_RECEIVED),
                        FG: round(fgValue / QTY_RECEIVED),
                    },
                },
            },
        });
    });
});
