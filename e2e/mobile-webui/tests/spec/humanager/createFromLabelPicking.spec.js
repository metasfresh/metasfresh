import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { NewHUPreviewScreen } from '../../utils/screens/huManager/NewHUPreviewScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { expectErrorToast, page, SLOW_ACTION_TIMEOUT } from '../../utils/common';

// HU Manager "create from label" and picking: a GS1 label booked in the HU Manager is refused by picking as pick-from code;
// rescanning the same label in the HU Manager opens a new preview, not the HU created from it.

let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `8${seq}${ts}`; // 14 digits
};
const customLabel = (gtin) => `${gtin}L2311271231`;
const gs1Label = (gtin) => `01${gtin}1727123110L2311`;

const FORMAT_GTIN_LOT_BBD = {
    name: 'my gtin label (HU Manager + picking)',
    parts: [
        { startPosition: 1, endPosition: 14, type: 'GTIN' },
        { startPosition: 15, endPosition: 19, type: 'LOT' },
        { startPosition: 20, endPosition: 25, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
    ],
};

const createMasterdata = async ({ gtin, externalBarcode } = {}) => await Backend.createMasterdata({
    language: "en_US",
    request: {
        // the workplace is the HU Manager's source for the location proposed to the created CU: the default location of warehouse `wh`
        login: { user: { language: "en_US", workplace: "workplace1" } },
        workplaces: { "workplace1": { warehouse: "wh" } },
        mobileConfig: {
            picking: {
                aggregationType: "sales_order",
                allowPickingAnyCustomer: true,
                createShipmentPolicy: 'CL',
                allowPickingAnyHU: true,
                pickTo: ['TU'],
                allowCompletingPartialPickingJob: true,
            }
        },
        bpartners: { "BP1": {} },
        warehouses: { "wh": {} },
        pickingSlots: { slot1: {} },
        products: { "P1": { gtin, uom: 'PCE', price: 1 } },
        packingInstructions: { "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100 } },
        ...(externalBarcode ? { handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh', qty: 80, externalBarcode } } } : {}),
        salesOrders: {
            "SO1": {
                bpartner: 'BP1',
                warehouse: 'wh',
                datePromised: '2025-03-01T00:00:00.000+02:00',
                lines: [{ product: 'P1', qty: 1 }],
            }
        },
        customQRCodeFormats: [FORMAT_GTIN_LOT_BBD],
    }
});

const openHUManager = async ({ user }) => {
    await LoginScreen.login(user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
};

/** scan a label in the HU Manager, press OK, return the new HU as the server answered it */
const createHUFromLabel = async ({ label }) => {
    await HUManagerScreen.scanLabel({ label });
    await NewHUPreviewScreen.waitForScreen();
    const [response] = await Promise.all([
        page.waitForResponse((r) => r.url().includes('createFromScannedCode') && r.request().method() === 'POST', { timeout: SLOW_ACTION_TIMEOUT }),
        NewHUPreviewScreen.clickOK(),
    ]);
    const hu = (await response.json()).result;
    // after OK the user is back on the scan screen (the new HU is not opened)
    await HUManagerScreen.waitForScreen();
    await NewHUPreviewScreen.expectNotDisplayed();
    return hu.id;
};

const goBackToApplications = async () => {
    await HUManagerScreen.goBack();
    await ApplicationsListScreen.expectVisible();
};

const startPickingJob = async ({ masterdata }) => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });
};

// noinspection JSUnusedLocalSymbols
test('custom label books a CU in the HU Manager; HU Manager scan of the same label afterwards opens a new preview, not the first HU', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const label = customLabel(gtin);
    const masterdata = await createMasterdata({ gtin });
    await openHUManager({ user: masterdata.login.user });

    const huId = await createHUFromLabel({ label });
    await Backend.expect({ hus: { [huId]: { huStatus: 'A', storages: { 'P1': '1 PCE' } } } });

    // (c) the HU Manager does not resolve labels to the HU created from them: the same label gives a new preview, not the first HU.
    // After OK the app is back on the scan screen.
    await HUManagerScreen.scanLabel({ label });
    await NewHUPreviewScreen.waitForScreen();
    await NewHUPreviewScreen.clickCancel();
    await HUManagerScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('GS1 label books a CU, picking refuses the same GS1 label as the wrong code type, nothing picked (today\'s behaviour, pinned)', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const label = gs1Label(gtin);
    const masterdata = await createMasterdata({ gtin });
    await openHUManager({ user: masterdata.login.user });

    const huId = await createHUFromLabel({ label });
    await goBackToApplications();
    await startPickingJob({ masterdata });

    await expectErrorToast('GS1 label is no pick-from code', async () => {
        await PickingJobScreen.pickHU({ qrCode: label, isScanDirectly: true, expectedPickDirectly: true });
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' });
    await Backend.expect({ hus: { [huId]: { huStatus: 'A' } } });
});

// noinspection JSUnusedLocalSymbols
test('label that is also the external barcode of an existing HU opens that HU (existing HUs keep priority, no preview, no propose call)', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const label = customLabel(gtin); // a complete, valid label of product P1 - but also the external barcode of the existing HU1
    const masterdata = await createMasterdata({ gtin, externalBarcode: label });
    let proposeCalls = 0;
    page.on('request', (r) => { if (r.url().includes('proposeFromScannedCode') && r.method() === 'POST') proposeCalls++; });
    await openHUManager({ user: masterdata.login.user });

    await HUManagerScreen.scanHUQRCode({ huQRCode: label });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    await NewHUPreviewScreen.expectNotDisplayed();
    expect(proposeCalls).toBe(0);
});
