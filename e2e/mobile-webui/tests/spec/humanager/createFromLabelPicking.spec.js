import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { NewHUPreviewScreen } from '../../utils/screens/huManager/NewHUPreviewScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { expectErrorToast } from '../../utils/common';
import { uniqueGtin14 } from '../../utils/gtin';
import { bracketedLabel, FORMAT_BRACKETED_GTIN_PRODDATE_LOT, FORMAT_GTIN_LOT_BBD, gs1Label } from '../../utils/huLabels';

// HU Manager "create from label" and picking: a GS1 label booked in the HU Manager is refused by picking as pick-from code;
// rescanning the same label in the HU Manager opens a new preview, not the HU created from it.


const createMasterdata = async ({ gtin, externalBarcode, format = FORMAT_BRACKETED_GTIN_PRODDATE_LOT } = {}) => await Backend.createMasterdata({
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
        customQRCodeFormats: [format],
    }
});

/** scan a label in the HU Manager, press OK, return the new HU as the server answered it */
const createHUFromLabel = async ({ label }) => {
    await HUManagerScreen.scanLabelAndWaitForPreview({ label });
    await NewHUPreviewScreen.setQty(1);
    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    // after OK the user is back on the scan screen (the new HU is not opened)
    await HUManagerScreen.waitForScreen();
    await NewHUPreviewScreen.expectNotDisplayed();
    return created.huId;
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

    const gtin = uniqueGtin14('8');
    const label = bracketedLabel(gtin);
    const masterdata = await createMasterdata({ gtin });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

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

    const gtin = uniqueGtin14('8');
    const label = gs1Label(gtin);
    const masterdata = await createMasterdata({ gtin, format: FORMAT_GTIN_LOT_BBD }); // the custom format the GS1 scenario was pinned with
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

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
test('bracketed UDI label (fixed-position format) books a CU, picking refuses the same label with "no HU with this attribute", nothing picked', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14('8');
    const label = bracketedLabel(gtin);
    const masterdata = await createMasterdata({ gtin });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    const huId = await createHUFromLabel({ label });
    await goBackToApplications();
    await startPickingJob({ masterdata });

    // the label code is not stored on the new HU, so the picking lookup by the HU_QRCode attribute finds no HU
    // (message de.metas.handlingunits.picking.job.QR_CODE_HU_NOT_FOUND_BY_ATTRIBUTE, shown here by its key)
    await expectErrorToast('Bracketed label is refused by picking: no HU carries the code', async () => {
        await PickingJobScreen.pickHU({ qrCode: label, isScanDirectly: true, expectedPickDirectly: true });
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_QR_HU_NOT_FOUND_BY_ATTRIBUTE');
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

    const gtin = uniqueGtin14('8');
    const label = bracketedLabel(gtin); // a complete, valid label of product P1 - but also the external barcode of the existing HU1
    const masterdata = await createMasterdata({ gtin, externalBarcode: label });
    const proposeCalls = NewHUPreviewScreen.countProposeCalls();
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanHUQRCode({ huQRCode: label });
    await HUManagerScreen.expectHUQty('80 PCE');
    await NewHUPreviewScreen.expectNotDisplayed();
    expect(proposeCalls.count).toBe(0);
});
