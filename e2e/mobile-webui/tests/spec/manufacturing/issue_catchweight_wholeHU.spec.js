import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// The component is stocked in pieces but consumed by weight: the recipe line asks for kg while the
// HUs' storage is in Stk. Both HUs weigh less than their nominal pieces x rate, as real cheese does.
const NOMINAL_KG_PER_PIECE = 35;

const HU_A_PIECES = 2;
const HU_A_WEIGHT = '68.400'; // three decimals: that is how the attribute is stored and compared
const HU_B_PIECES = 1;
const HU_B_WEIGHT = '34.200';
// a third HU holding more than the line still needs, so its step gets capped to the remainder
const HU_C_PIECES = 2;
const HU_C_WEIGHT = '69.000';
// giving up one of its two pieces takes half of its weight with it
const HU_C_PIECES_LEFT = HU_C_PIECES - 1;
const HU_C_WEIGHT_LEFT = '34.500';

// The line needs what HU_A and HU_B nominally weigh plus one piece more, so those two are consumed
// whole while HU_C - which holds two pieces - can only give up part of itself
const LINE_DEMAND_KG = (HU_A_PIECES + HU_B_PIECES + 1) * NOMINAL_KG_PER_PIECE;

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {},
            warehouses: { "wh": {} },
            products: {
                "COMP_CW": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: NOMINAL_KG_PER_PIECE, isCatchUOMForProduct: true }],
                },
                // A second component that is only issued for what was received. Its presence is what makes
                // a receipt recompute the steps of EVERY line of the job, the catch-weight one included.
                "COMP_AUTO": {},
                "FG": {
                    bom: {
                        lines: [
                            { product: 'COMP_CW', qty: LINE_DEMAND_KG, uom: 'KGM' },
                            { product: 'COMP_AUTO', qty: 1, issueMethod: 'IssueOnlyForReceived' },
                        ],
                    },
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "FG", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_A": { product: 'COMP_CW', warehouse: 'wh', qty: HU_A_PIECES, weightNet: Number(HU_A_WEIGHT) },
                "HU_B": { product: 'COMP_CW', warehouse: 'wh', qty: HU_B_PIECES, weightNet: Number(HU_B_WEIGHT) },
                "HU_C": { product: 'COMP_CW', warehouse: 'wh', qty: HU_C_PIECES, weightNet: Number(HU_C_WEIGHT) },
                "HU_AUTO": { product: 'COMP_AUTO', warehouse: 'wh', qty: 1000, sourceHU: true },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'FG',
                    qty: 1,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        }
    });
}

test.describe('Manufacturing issue of whole catch-weight HUs across UOMs', () => {
    // noinspection JSUnusedLocalSymbols
    test('Issuing whole HUs stocked in Stk to a recipe line in kg keeps each HU weight through the flow', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('Whole-HU issue when the stocking UOM differs from the recipe line UOM');
        allure.severity('critical');
        allure.description(
            'Two handling units, each consumed whole by its own step, are issued one after the other. The ' +
            'weights are checked three times - before anything is issued, with one HU issued and one still ' +
            'on stock, and after both are issued - so a weight that is lost or overwritten at any point in ' +
            'the flow shows up, not only at the end.'
        );

        const masterdata = await createMasterdata();

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Initial: both HUs are on stock with the weight they were captured at', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: `${LINE_DEMAND_KG} kg`, qtyIssued: '0 kg' });
            await Backend.expect({
                title: 'initial weights',
                hus: {
                    'HU_A': { huStatus: 'A', storages: { 'COMP_CW': `${HU_A_PIECES} PCE` }, attributes: { 'WeightNet': HU_A_WEIGHT } },
                    'HU_B': { huStatus: 'A', storages: { 'COMP_CW': `${HU_B_PIECES} PCE` }, attributes: { 'WeightNet': HU_B_WEIGHT } },
                    'HU_C': { huStatus: 'A', storages: { 'COMP_CW': `${HU_C_PIECES} PCE` }, attributes: { 'WeightNet': HU_C_WEIGHT } },
                },
            });
        });

        // Issuing a whole HU consumes its content and destroys the HU, so its end status is Destroyed
        await test.step('In progress: the first HU is consumed, the second is untouched', async () => {
            await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_A.qrCode });
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyIssued: `${HU_A_PIECES * NOMINAL_KG_PER_PIECE} kg` });

            await Backend.expect({
                title: 'weights with one HU issued',
                hus: {
                    'HU_A': { huStatus: 'D', attributes: { 'WeightNet': HU_A_WEIGHT } },
                    'HU_B': { huStatus: 'A', storages: { 'COMP_CW': `${HU_B_PIECES} PCE` }, attributes: { 'WeightNet': HU_B_WEIGHT } },
                },
                manufacturings: {
                    'PP1': { issuedHUs: [{ attributes: { 'WeightNet': HU_A_WEIGHT } }] },
                },
            });
        });

        await test.step('Both whole HUs consumed, each having kept its own weight', async () => {
            await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_B.qrCode });
            await ManufacturingJobScreen.expectIssueButton({
                index: 1,
                qtyIssued: `${(HU_A_PIECES + HU_B_PIECES) * NOMINAL_KG_PER_PIECE} kg`,
            });

            await Backend.expect({
                title: 'weights after both whole HUs',
                hus: {
                    'HU_A': { huStatus: 'D', attributes: { 'WeightNet': HU_A_WEIGHT } },
                    'HU_B': { huStatus: 'D', attributes: { 'WeightNet': HU_B_WEIGHT } },
                    'HU_C': { huStatus: 'A', storages: { 'COMP_CW': `${HU_C_PIECES} PCE` }, attributes: { 'WeightNet': HU_C_WEIGHT } },
                },
                manufacturings: {
                    'PP1': {
                        issuedHUs: [
                            { attributes: { 'WeightNet': HU_A_WEIGHT } },
                            { attributes: { 'WeightNet': HU_B_WEIGHT } },
                        ],
                    },
                },
            });
        });

        // Receiving finished goods auto-issues the received-only component and, on the way, recomputes the
        // steps of every line - including this one, whose steps are denominated in pieces. That recompute
        // used to throw on the mismatched units before the operator could issue anything else.
        await test.step('A receipt recomputes the steps of a line denominated in pieces', async () => {
            await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
            await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });
            await MaterialReceiptLineScreen.receiveQty({ qtyEntered: '1' });

            await ManufacturingJobScreen.expectIssueButton({
                index: 1,
                qtyIssued: `${(HU_A_PIECES + HU_B_PIECES) * NOMINAL_KG_PER_PIECE} kg`,
            });
        });

        // The line needs one more piece than HU_C's step was planned for, so that step is capped to the
        // remainder and becomes a partial issue: HU_C gives up part of itself and stays on stock.
        await test.step('Final: the capped step takes only part of the third HU, which survives', async () => {
            await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_C.qrCode });
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyIssued: `${LINE_DEMAND_KG} kg` });

            await Backend.expect({
                title: 'the partially issued HU is still on stock, with less quantity and less weight',
                hus: {
                    'HU_C': {
                        huStatus: 'A',
                        storages: { 'COMP_CW': `${HU_C_PIECES_LEFT} PCE` },
                        attributes: { 'WeightNet': HU_C_WEIGHT_LEFT },
                    },
                },
            });
        });
    });
});
