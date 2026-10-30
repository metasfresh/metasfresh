import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { DistributionJobsListScreen } from '../../utils/screens/distribution/DistributionJobsListScreen';
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';

// Doc-type name of a PP_Order as shown by the "Source Doc" caption element (en_US, the login language of
// this spec). Verified on the local DB: C_DocType.Name = C_DocType_Trl.Name(en_US) = 'Produktionsauftrag'
// for the only DocBaseType 'MOP' production-order doc type (PP_Order default doc type).
const MO = 'Produktionsauftrag';
const HEADER_CAPTION = 'Manufacturing orders with picking instruction';
const CAPTION_MULTILINE_CLASS = /(^|\s)caption-multiline(\s|$)/;

let previousSysconfigs = null;

// Restore the sysconfig even if the test body fails (pattern of picking/close_partially_shipped.spec.js)
test.afterAll(async () => {
    if (previousSysconfigs && Object.keys(previousSysconfigs).length > 0) {
        await Backend.setSysconfigs(previousSysconfigs);
    }
});

const ppOrder = ({ lineId, pickingInstruction }) => ({
    warehouse: 'whTarget',
    product: 'FG',
    qty: 10,
    datePromised: '2025-03-01T00:00:00.000+02:00',
    lines: { [lineId]: { product: 'COMP', ...(pickingInstruction !== undefined && { pickingInstruction }) } },
});

const baseRequest = (captionFormat) => ({
    login: { user: { language: 'en_US' } },
    mobileConfig: { distribution: { captionFormat } },
    shippers: { shipper1: {} },
    warehouses: {
        whSource: {},
        whTarget: {},
        whInTransit: { inTransit: true },
    },
    products: {
        COMP: {},
        FG: { bom: { lines: [{ product: 'COMP', qty: 1, pickingInstruction: 'Sahne' }] } },
    },
});

const expectedPPOrderLine = (ppOrderDocumentNo, pickingInstruction) =>
    `${MO} ${ppOrderDocumentNo}` + (pickingInstruction ? `: ${pickingInstruction}` : '');

// noinspection JSUnusedLocalSymbols
test('Aggregated DD_Order lists every PP_Order with its picking instruction', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution caption format');
    allure.severity('normal');

    const request = baseRequest('ProductValueAndName,Qty,PickingInstruction,PPOrdersWithPickingInstruction');
    request.sysconfigs = { 'DDOrderAggregation.header.byPPOrderRef': 'N' };
    request.manufacturingOrders = {
        PP1: ppOrder({ lineId: 'PP1_L1' }),
        PP2: ppOrder({ lineId: 'PP2_L1', pickingInstruction: 'ohne Rinde' }),
        PP3: ppOrder({ lineId: 'PP3_L1', pickingInstruction: '  ' }), // blank => no picking instruction
    };
    request.distributionOrdersFromCandidates = {
        DDAGG: {
            warehouseFrom: 'whSource',
            warehouseTo: 'whTarget',
            product: 'COMP',
            shipper: 'shipper1',
            candidates: [
                { ppOrder: 'PP1', ppOrderBOMLine: 'PP1_L1', qty: 10 },
                { ppOrder: 'PP1', ppOrderBOMLine: 'PP1_L1', qty: 5 }, // same PP_Order twice => listed once
                { ppOrder: 'PP2', ppOrderBOMLine: 'PP2_L1', qty: 10 },
                { ppOrder: 'PP3', ppOrderBOMLine: 'PP3_L1', qty: 10 },
            ],
        },
    };
    const masterdata = await Backend.createMasterdata({ language: 'en_US', request });
    previousSysconfigs = masterdata.previousSysconfigs;

    const lines = [
        expectedPPOrderLine(masterdata.manufacturingOrders.PP1.documentNo, 'Sahne'),
        expectedPPOrderLine(masterdata.manufacturingOrders.PP2.documentNo, 'ohne Rinde'),
        expectedPPOrderLine(masterdata.manufacturingOrders.PP3.documentNo, null),
    ];
    const dd = masterdata.distributionOrdersFromCandidates.DDAGG;
    // No qty per order, and no "PickingInstruction" part: the aggregated header has no BOM line.
    const caption = masterdata.products.COMP.productCode + '_' + masterdata.products.COMP.productName
        + ' | 35 Stk'
        + lines.map(line => '\n' + line).join('');

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: dd.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.expectJobButtons([{ testId: dd.launcherTestId, caption }]);
    await expect(page.getByTestId(dd.launcherTestId).locator('.caption-btn .row span')).toHaveClass(CAPTION_MULTILINE_CLASS);

    // D2: the job header shows the same lines joined with ", "
    await DistributionJobsListScreen.startJob({ launcherTestId: dd.launcherTestId });
    await DistributionJobScreen.expectHeaderProperty({ caption: HEADER_CAPTION, value: lines.join(', ') });
});

// noinspection JSUnusedLocalSymbols
test('Single PP_Order renders one line next to Source Doc and Picking Instruction', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution caption format');
    allure.severity('normal');

    const request = baseRequest('ProductValueAndName,Qty,SourceDoc,PickingInstruction,PPOrdersWithPickingInstruction');
    request.manufacturingOrders = { PP1: ppOrder({ lineId: 'PP1_L1' }) };
    request.distributionOrders = {
        DD1: {
            warehouseFrom: 'whSource',
            warehouseTo: 'whTarget',
            warehouseInTransit: 'whInTransit',
            forwardPPOrder: 'PP1',
            forwardPPOrderBOMLine: 'PP1_L1',
            lines: [{ product: 'COMP', qtyEntered: 10 }],
        },
    };
    const masterdata = await Backend.createMasterdata({ language: 'en_US', request });
    previousSysconfigs = masterdata.previousSysconfigs;

    const dd = masterdata.distributionOrders.DD1;
    const pp1DocNo = masterdata.manufacturingOrders.PP1.documentNo;
    const caption = masterdata.products.COMP.productCode + '_' + masterdata.products.COMP.productName
        + ` | 10 Stk | ${MO} ${pp1DocNo} | Sahne`
        + '\n' + expectedPPOrderLine(pp1DocNo, 'Sahne');

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: dd.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.expectJobButtons([{ testId: dd.launcherTestId, caption }]);
});
