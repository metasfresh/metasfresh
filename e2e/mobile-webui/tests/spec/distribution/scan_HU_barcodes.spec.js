import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { DistributionStepScreen } from '../../utils/screens/distribution/DistributionStepScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { expectErrorToast } from '../../utils/common';
import { expect } from '@playwright/test';

const createMasterdata = async ({ externalBarcode } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                distribution: {
                    // mobileConfig->distribution entry is important to make sure we get the default distribution config
                }
            },
            resources: {
                "plantId": { type: "PT" },
            },
            products: {
                "P1": {},
            },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 100, externalBarcode }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: 100 }],
                }
            },
        }
    });
};

const expectHU = async ({ warehouse }) => {
    await Backend.expect({
        hus: {
            HU1: { warehouse, huStatus: 'A', storages: { P1: '100 PCE' } },
        }
    });
};

// noinspection JSUnusedLocalSymbols
const standardTest = async ({ masterdata, huBarcodeToScan }) => {
    await expectHU({ warehouse: 'wh1' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({
        huQRCode: huBarcodeToScan,
        qtyToMove: '100',
        expectedQtyToMove: '100'
    });
    await expectHU({ warehouse: 'whInTransit' });

    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
    await expectHU({ warehouse: 'wh2' });

    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
};

// noinspection JSUnusedLocalSymbols
test('Scan by HU QR Code', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    await standardTest({ masterdata, huBarcodeToScan: masterdata.handlingUnits.HU1.qrCode });
});

// noinspection JSUnusedLocalSymbols
test('Scan by M_HU_ID', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    await standardTest({ masterdata, huBarcodeToScan: masterdata.handlingUnits.HU1.huId });
});

// noinspection JSUnusedLocalSymbols
test('Scan by ExternalBarcode', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const externalBarcode = "EXT" + Date.now();
    const masterdata = await createMasterdata({ externalBarcode });
    await standardTest({ masterdata, huBarcodeToScan: externalBarcode });
});

// noinspection JSUnusedLocalSymbols
test('Scan locator QR code where HU is expected → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.openPickFromScreen();

    await expectErrorToast('Scan locator QR code where HU is expected', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(masterdata.warehouses.wh1.locatorQRCode);
        await GetQuantityDialog.waitForDialog();
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_WRONG_TYPE_LOCATOR');
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan unrecognized barcode where HU is expected → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.openPickFromScreen();

    await expectErrorToast('Scan unrecognized barcode where HU is expected', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode('TOTALLY_UNKNOWN_FORMAT_XYZ');
        await GetQuantityDialog.waitForDialog();
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_NOT_RECOGNIZED');
    });
});

// A long HU QR code can be split mid-stream by a slow scanner device: it arrives as TWO bad scans —
// the head fragment (keeps the valid "HU#<version>#" prefix but carries truncated, unparseable JSON) and
// the tail fragment (the prefix-less remainder). Each fragment, scanned on its own exactly as the device
// delivers it, must surface the friendly QR_NOT_RECOGNIZED message — never a silent failure or the raw
// "Failed converting payload" developer error.
//
// The two fragments are verified in SEPARATE tests, each starting from a clean toast state. The app shows
// exactly one error toast per scan by design (a fixed toastId — see mobile-webui CLAUDE.md "the user must
// see exactly ONE error"), so two back-to-back scans within one test would race that de-duplication and
// leave the second fragment's toast suppressed. Asserting a single shared toast instead would also fail to
// catch a tail-specific regression — a raw tail error would be hidden under the head's friendly toast. One
// scan per scenario keeps each fragment's handling independently observable.
const expectTruncatedHuQRFragmentShowsFriendlyError = async ({ which }) => {
    const masterdata = await createMasterdata();

    const fullHuQRCode = masterdata.handlingUnits.HU1.qrCode;
    const splitAt = Math.floor(fullHuQRCode.length / 2);
    const truncatedHead = fullHuQRCode.substring(0, splitAt);
    const truncatedTail = fullHuQRCode.substring(splitAt);
    expect(truncatedHead).toMatch(/^HU#/);     // head keeps the HU# prefix, payload is cut off
    expect(truncatedTail).not.toMatch(/^HU#/); // tail is the prefix-less remainder
    const fragment = which === 'head' ? truncatedHead : truncatedTail;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.openPickFromScreen();

    await expectErrorToast(`Scan the truncated HU QR ${which} where HU is expected`, async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(fragment);
        await GetQuantityDialog.waitForDialog();
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_NOT_RECOGNIZED');
    });
};

// noinspection JSUnusedLocalSymbols
test('Scan a truncated (split) HU QR HEAD where HU is expected → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    await expectTruncatedHuQRFragmentShowsFriendlyError({ which: 'head' });
});

// noinspection JSUnusedLocalSymbols
test('Scan a truncated (split) HU QR TAIL where HU is expected → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    await expectTruncatedHuQRFragmentShowsFriendlyError({ which: 'tail' });
});
