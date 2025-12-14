import { test } from "../../playwright.config";
import { Backend } from "../utils/screens/Backend";
import { LoginScreen } from "../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../utils/screens/huManager/HUManagerScreen';
import { WorkplaceManagerScreen } from '../utils/screens/workplaceManager/WorkplaceManagerScreen';
import { AllureHelpers } from '../../../common/AllureHelpers';

const createMasterdata = async ({ externalBarcode } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            products: { "P1": {} },
            warehouses: { "wh1": {} },
            workplaces: {
                workplace1: {},
                workplace2: {},
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 80, externalBarcode }
            },
            generatedHUQRCodes: { g1: { packingInstructions: 'LU', product: 'P1' } }
        }
    });
}

test.describe('Scan HU codes', () => {
    const runTest = async ({ masterdata, huBarcode }) => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.scanBarcode(huBarcode);
        await HUManagerScreen.waitForScreen();
        await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    };

    // noinspection JSUnusedLocalSymbols
    test('Scan HU QR Code', async ({ page }) => {
        // === ALLURE METADATA ===
        await AllureHelpers.setFeature({
            id: 'F12000',
            name: 'Frontend MobileUI',
            epicId: 'E0295',
            epicName: 'Frontend MobileUI'
        });
        await AllureHelpers.setStory('Scan HU codes from home screen');
        await AllureHelpers.setSeverity('normal');

        const masterdata = await createMasterdata();
        await runTest({ masterdata, huBarcode: masterdata.handlingUnits.HU1.qrCode });
    });

    // noinspection JSUnusedLocalSymbols
    test('Scan HU ID Code', async ({ page }) => {
        // === ALLURE METADATA ===
        await AllureHelpers.setFeature({
            id: 'F12000',
            name: 'Frontend MobileUI',
            epicId: 'E0295',
            epicName: 'Frontend MobileUI'
        });
        await AllureHelpers.setStory('Scan HU codes from home screen');
        await AllureHelpers.setSeverity('normal');

        const masterdata = await createMasterdata();
        await runTest({ masterdata, huBarcode: masterdata.handlingUnits.HU1.huId });
    });

    // noinspection JSUnusedLocalSymbols
    test('Scan ExternalBarcode', async ({ page }) => {
        // === ALLURE METADATA ===
        await AllureHelpers.setFeature({
            id: 'F12000',
            name: 'Frontend MobileUI',
            epicId: 'E0295',
            epicName: 'Frontend MobileUI'
        });
        await AllureHelpers.setStory('Scan HU codes from home screen');
        await AllureHelpers.setSeverity('normal');

        const externalBarcode = "EXT" + Date.now();
        const masterdata = await createMasterdata({ externalBarcode });
        await runTest({ masterdata, huBarcode: externalBarcode });
    });

});

// noinspection JSUnusedLocalSymbols
test('Scan Workplace code', async ({ page }) => {
    // === ALLURE METADATA ===
    await AllureHelpers.setFeature({
        id: 'F12000',
        name: 'Frontend MobileUI',
        epicId: 'E0295',
        epicName: 'Frontend MobileUI'
    });
    await AllureHelpers.setStory('Scan workplace codes from home screen');
    await AllureHelpers.setSeverity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('Scan workplace1', async () => {
        await ApplicationsListScreen.scanBarcode(masterdata.workplaces.workplace1.qrCode);
        await WorkplaceManagerScreen.waitForScreen();
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Name', value: masterdata.workplaces.workplace1.name });
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Assigned', value: 'Yes' });
        await WorkplaceManagerScreen.goBack();
    });

    await test.step('Scan workplace2', async () => {
        await ApplicationsListScreen.scanBarcode(masterdata.workplaces.workplace2.qrCode);
        await WorkplaceManagerScreen.waitForScreen();
        await WorkplaceManagerScreen.expectHeaderProperty({
            caption: 'Name', value: masterdata.workplaces.workplace2
                .name
        });
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Assigned', value: 'Yes' });
        await WorkplaceManagerScreen.goBack();
    });

});
