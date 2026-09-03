import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';

// The component is stocked in pieces but consumed by weight: the BOM line is in kg while the HU's
// storage is in Stk. The HU also weighs less than its nominal 2 x 35 kg, as a real cheese pallet does.
const NOMINAL_KG_PER_PIECE = 35;
const HU_PIECES = 2;
const HU_WEIGHT_NET = 68.4;

const createMasterdata = async () => {
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
            },
            handlingUnits: {
                "HU_CW": { product: 'COMP_CW', warehouse: 'wh', qty: HU_PIECES, weightNet: HU_WEIGHT_NET },
            },
            manufacturingOrders: {
                // demand exceeds what the HU holds, so the whole HU is consumed by one step
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

        await test.step('Issue the whole HU', async () => {
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_CW.qrCode });
            await RawMaterialIssueLineScreen.goBack();
        });

        await Backend.expect({
            title: 'the whole HU was issued, carrying its captured weight',
            manufacturings: {
                "PP1": {
                    issuedHUs: [{ attributes: { 'WeightNet': String(HU_WEIGHT_NET) } }],
                },
            },
        });
    });
});
