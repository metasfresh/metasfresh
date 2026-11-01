import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { DistributionJobsListScreen } from '../../utils/screens/distribution/DistributionJobsListScreen';
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';

// Doc-type name of a PP_Order as shown by the "Source Doc" caption element (en_US, the login language of
// this spec). Verified on the local DB: C_DocType.Name = C_DocType_Trl.Name(en_US) = 'Produktionsauftrag'
// for the only DocBaseType 'MOP' production-order doc type (PP_Order default doc type).
const MO = 'Produktionsauftrag';
// English label of the new element (AD_Ref_List_Trl en_US); this spec logs in as en_US, like its sibling specs.
const HEADER_CAPTION = 'Source documents with picking note';

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

    const request = baseRequest('ProductValueAndName,Qty,PickingInstruction,SourceDocsWithPickingNote');
    // SYSTEM-level write: DDOrderAggregation reads this sysconfig only at SYSTEM level
    request.systemSysconfigs = { 'DDOrderAggregation.header.byPPOrderRef': 'N' };
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
    await DistributionJobsListScreen.expectJobButtonCaptionMultiline({ launcherTestId: dd.launcherTestId });

    // D2: the job header shows the same lines joined with ", "
    await DistributionJobsListScreen.startJob({ launcherTestId: dd.launcherTestId });
    await DistributionJobScreen.expectHeaderProperty({ caption: HEADER_CAPTION, value: lines.join(', ') });
});

// noinspection JSUnusedLocalSymbols
test('Sales order is listed without note; a candidate with both lists only its manufacturing order', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution caption format');
    allure.severity('normal');

    const request = baseRequest('ProductValueAndName,Qty,SourceDocsWithPickingNote');
    // SYSTEM-level writes: DDOrderAggregation reads these sysconfigs only at SYSTEM level.
    // All three off, so that the candidates of different orders end up on one DD_Order line.
    request.systemSysconfigs = {
        'DDOrderAggregation.header.byPPOrderRef': 'N',
        'DDOrderAggregation.header.bySalesOrderId': 'N',
        'DDOrderAggregation.line.bySalesOrderLineId': 'N',
    };
    request.products.FG.price = 1;
    request.bpartners = { customer: {} };
    const salesOrder = () => ({
        bpartner: 'customer',
        warehouse: 'whTarget',
        datePromised: '2025-03-01T00:00:00.000+02:00',
        lines: [{ product: 'FG', qty: 10 }],
    });
    request.salesOrders = { SO1: salesOrder(), SO2: salesOrder() };
    request.manufacturingOrders = {
        PP1: ppOrder({ lineId: 'PP1_L1' }),
        PP2: ppOrder({ lineId: 'PP2_L1', pickingInstruction: 'ohne Rinde' }),
    };
    request.distributionOrdersFromCandidates = {
        DDAGG: {
            warehouseFrom: 'whSource',
            warehouseTo: 'whTarget',
            product: 'COMP',
            shipper: 'shipper1',
            candidates: [
                { ppOrder: 'PP1', ppOrderBOMLine: 'PP1_L1', qty: 10 },
                { salesOrderLine: 'SO1_line1', qty: 10 },
                // both: only the manufacturing order is listed, it carries the picking note; SO2 must not show up
                { ppOrder: 'PP2', ppOrderBOMLine: 'PP2_L1', salesOrderLine: 'SO2_line1', qty: 10 },
            ],
        },
    };
    const masterdata = await Backend.createMasterdata({ language: 'en_US', request });

    const so1 = masterdata.salesOrders.SO1;
    const lines = [
        expectedPPOrderLine(masterdata.manufacturingOrders.PP1.documentNo, 'Sahne'),
        expectedPPOrderLine(masterdata.manufacturingOrders.PP2.documentNo, 'ohne Rinde'),
        `${so1.docTypeName} ${so1.documentNo}`,
    ];
    const dd = masterdata.distributionOrdersFromCandidates.DDAGG;
    const caption = masterdata.products.COMP.productCode + '_' + masterdata.products.COMP.productName
        + ' | 30 Stk'
        + lines.map(line => '\n' + line).join('');

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: dd.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.expectJobButtons([{ testId: dd.launcherTestId, caption }]);

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

    const request = baseRequest('ProductValueAndName,Qty,SourceDoc,PickingInstruction,SourceDocsWithPickingNote');
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
