import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';

// The component is stocked in pieces but consumed by weight: the BOM line is in kg while the HU's
// storage is in Stk. The HU also weighs less than its nominal 2 x 35 kg, as a real cheese pallet does.
const NOMINAL_KG_PER_PIECE = 35;
const HU_PIECES = 2;
const HU_WEIGHT_NET = 68.4;

const createMasterdata = async ({ componentAsLU = false } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {},
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP_CW": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: NOMINAL_KG_PER_PIECE, isCatchUOMForProduct: true }],
                },
                "FG": {
                    bom: {
                        // in kg, so the issue plan is denominated in a different UOM than the HU's storage
                        lines: [{ product: 'COMP_CW', qty: 100, uom: 'KGM' }],
                    },
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "FG", qtyCUsPerTU: 4 },
                // one piece per TU, so an LU of two TUs holds the same two pieces as the flat HU -
                // but the issue has to walk it one TU at a time
                "CW_PI": { lu: "LU_CW", qtyTUsPerLU: HU_PIECES, tu: "TU_CW", product: "COMP_CW", qtyCUsPerTU: 1 },
            },
            handlingUnits: {
                "HU_CW": componentAsLU
                    ? { product: 'COMP_CW', warehouse: 'wh', packingInstructions: 'CW_PI', weightNet: HU_WEIGHT_NET }
                    : { product: 'COMP_CW', warehouse: 'wh', qty: HU_PIECES, weightNet: HU_WEIGHT_NET },
                // the order needs more than this one HU holds, and the job refuses to start unless the
                // whole demand is covered - so a second HU carries the rest
                "HU_REST": { product: 'COMP_CW', warehouse: 'wh', qty: 1 },
            },
            manufacturingOrders: {
                // demand exceeds what HU_CW holds, so its step consumes it whole
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

test.describe('Manufacturing issue of a whole catch-weight HU across UOMs', () => {
    // noinspection JSUnusedLocalSymbols
    test('Issuing a whole HU stocked in Stk to a BOM line in kg consumes it and keeps its weight', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('Whole-HU issue when the stocking UOM differs from the BOM line UOM');
        allure.severity('critical');
        allure.description(
            'The component is stocked in Stk and the BOM line asks for kg. Issuing the whole HU must ' +
            'succeed rather than fail with "Could not issue the whole quantity required", and the HU ' +
            'that ends up issued must still carry the weight that was captured on it.'
        );

        const masterdata = await createMasterdata();

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_CW.qrCode });

        await Backend.expect({
            title: 'the whole HU was issued, carrying its captured weight',
            manufacturings: {
                "PP1": {
                    issuedHUs: [{ attributes: { 'WeightNet': String(HU_WEIGHT_NET) } }],
                },
            },
        });
    });

    // noinspection JSUnusedLocalSymbols
    test('The same issue from an LU of several TUs keeps the weight on every piece it moves', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('Whole-HU issue when the stocking UOM differs from the BOM line UOM');
        allure.severity('critical');
        allure.description(
            'Same issue as above, but the component arrives as an LU holding several TUs, which the issue ' +
            'has to walk one TU at a time. Each of those steps must still carry the captured weight.'
        );

        const masterdata = await createMasterdata({ componentAsLU: true });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_CW.qrCode });

        await Backend.expect({
            title: 'the pieces issued off the LU carry the captured weight',
            manufacturings: {
                "PP1": {
                    issuedHUs: [{ attributes: { 'WeightNet': String(HU_WEIGHT_NET) } }],
                },
            },
        });
    });
});
