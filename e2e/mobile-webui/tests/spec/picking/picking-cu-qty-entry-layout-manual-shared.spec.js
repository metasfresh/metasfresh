import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { page } from "../../utils/common";
import { expectLayoutSound, expectOnScreenWithoutScrolling, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, saveScreenshot, expectInsideScreen } from "../../utils/layout";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { PickGraiScreen } from "../../utils/screens/picking/PickGraiScreen";
import { YesNoDialog } from "../../utils/dialogs/YesNoDialog";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from "../../utils/screens/distribution/DistributionJobScreen";
import { DistributionLinePickFromScreen } from "../../utils/screens/distribution/DistributionLinePickFromScreen";
import { HUManagerScreen } from "../../utils/screens/huManager/HUManagerScreen";
import { HUMoveScreen } from "../../utils/screens/huManager/HUMoveScreen";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { generateEAN13 } from "../../utils/ean13";
import { allureMeta, createMasterdata, createMasterdataRememberingSysconfigs, LAUNCHERS_LIMIT_SYSCONFIGS, labelOf, MODE_MANUAL, uniqueFormatName, openLine, restoreSysconfigsAfterEach, scanLabel, startJob } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * Manual mode and shared screens (the regression surface of the CU quantity entry PR): every Manual screen and dialog that shares a
 * changed component (quantity dialog, scan step, button row, job screen) must look and behave , at each phone size.
 * Covered: over-pick confirmation, pallet target dialog, catch weight view, inline GRAI capture, Quick Pack All button, mode by profile / customer
 * record, on-screen keyboard, the shared product-code scan step and the HU Manager dialogs.
 * Same checker and sizes as picking-cu-qty-entry-layout-label-modes.spec.js (see there for the size list).
 */


const login = async (masterdata, application = 'picking') => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication(application);
};

/** Sales-order job of SO1 (launcher found by document no). */
const startSalesOrderJob = async (masterdata) => {
    await login(masterdata);
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    return await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
};

const BP_LOCATION_ORDER = { warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00' };

/** Masterdata of overPickingPrompt.spec.js (LU/TU picking, 4 CUs per TU, 20 TUs per LU). */
const createLuTuMasterdata = async ({ language, orderQtyCUs = 12 }) =>
    await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
            mobileConfig: { picking: { cuQtyEntryMode: MODE_MANUAL, aggregationType: 'sales_order', allowPickingAnyCustomer: true, createShipmentPolicy: 'CL', allowPickingAnyHU: true, pickTo: ['LU_TU'], shipOnCloseLU: false, allowCompletingPartialPickingJob: true, showPromptWhenOverPicking: true } },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { prices: [{ price: 1 }] } },
            packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' } },
            salesOrders: { SO1: { bpartner: 'BP1', ...BP_LOCATION_ORDER, lines: [{ product: 'P1', qty: orderQtyCUs, piItemProduct: 'TU' }] } },
        },
    });

/** Masterdata of picking_catchWeight.spec.js (catch weight product). */
const createCatchWeightMasterdata = async ({ language }) =>
    await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
            mobileConfig: { picking: { cuQtyEntryMode: MODE_MANUAL, aggregationType: 'sales_order', allowPickingAnyCustomer: true, createShipmentPolicy: 'CL', allowPickingAnyHU: true, pickTo: ['LU_TU'], allowCompletingPartialPickingJob: true } },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: {
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }],
                },
            },
            packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', qty: 100, weightNet: 10, lotNo: 'lot1', bestBeforeDate: '2031-11-23' } },
            salesOrders: { SO1: { bpartner: 'BP1', ...BP_LOCATION_ORDER, lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }] } },
        },
    });

/** Masterdata of picking-grai-duplicate-within-lu.spec.js (GRAI required customer, 2 products on one LU). */
const createGraiMasterdata = async ({ language }) =>
    await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
            mobileConfig: { picking: { cuQtyEntryMode: MODE_MANUAL, aggregationType: 'sales_order', allowPickingAnyCustomer: true, createShipmentPolicy: 'CL', allowPickingAnyHU: true, shipOnCloseLU: false, pickTo: ['LU_TU'], allowCompletingPartialPickingJob: false } },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { prices: [{ price: 1 }] }, P2: { prices: [{ price: 1 }] } },
            packingInstructions: {
                PI_P1: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P2', product: 'P2', qtyCUsPerTU: 4 },
            },
            handlingUnits: { HU_SOURCE_P1: { product: 'P1', warehouse: 'wh', qty: 200 }, HU_SOURCE_P2: { product: 'P2', warehouse: 'wh', qty: 200 } },
            salesOrders: { SO1: { bpartner: 'BP1', ...BP_LOCATION_ORDER, lines: [{ product: 'P1', qty: 40, piItemProduct: 'TU_IFCO_P1' }, { product: 'P2', qty: 40, piItemProduct: 'TU_IFCO_P2' }] } },
        },
    });

const buildDistinctGrais = (baseGrai, count) => {
    const [companyPrefix, assetType] = baseGrai.split('.');
    return Array.from({ length: count }, (_, i) => `${companyPrefix}.${assetType}.${String(i + 1).padStart(6, '0')}`);
};

/** Quick Pack All masterdata of pickAllButton.spec.js; `customers` = records of the mobile config. */
const createQuickPackMasterdata = async ({ cuQtyEntryMode, customers = [{ customer: 'customer1' }], aggregationType = 'sales_order', secondCustomer = false }) => {
    const orderLines = [{ product: 'P1', qty: 10 }, { product: 'P2', qty: 10 }];
    return await createMasterdataRememberingSysconfigs({
        language: 'en_US',
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType, createShipmentPolicy: 'CL', allowPickingAnyHU: true, allowQuickPackAll: true, shipOnCloseLU: false,
                    pickTo: ['CU', 'LU_CU'], allowCompletingPartialPickingJob: false, allowPickingAnyCustomer: false,
                    cuQtyEntryMode: cuQtyEntryMode ?? MODE_MANUAL, customers,
                },
            },
            bpartners: { customer1: {}, ...(secondCustomer ? { customer2: {} } : {}) },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            workplaces: { workplace1: { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: { P1: { price: 1 }, P2: { price: 2 } },
            packingInstructions: { LU_CU: { cu: true, lu: 'LU', qtyTUsPerLU: 1 } },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' }, HU2: { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' } },
            salesOrders: {
                SO1: { bpartner: 'customer1', ...BP_LOCATION_ORDER, lines: orderLines },
                ...(secondCustomer ? { SO2: { bpartner: 'customer2', ...BP_LOCATION_ORDER, lines: orderLines } } : {}),
            },
        },
    });
};


for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        for (const lang of LAYOUT_LANGUAGES) {
            // noinspection JSUnusedLocalSymbols
            test(`Manual: LU target dialog with "OK und LU schliessen", over-pick confirmation [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Manual screens');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);
                const masterdata = await createLuTuMasterdata({ language: lang });
                await startSalesOrderJob(masterdata);
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
                await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
                await layout('Manual job screen with pallet target');

                await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
                await GetQuantityDialog.waitForDialog();
                await layout('Manual quantity dialog with OK und LU schliessen (pallet target set)');
                await expectInsideScreen(page.getByTestId('confirmDoneAndCloseTarget-button'), 'OK und LU schliessen button', viewport);
                await expectInsideScreen(page.getByTestId('done-button'), 'OK button', viewport);

                await GetQuantityDialog.typeQtyEntered(8); // more than the 3 TUs of the line
                await page.getByTestId('done-button').tap();
                await YesNoDialog.waitForDialog();
                await layout('Manual over-pick confirmation (YesNoDialog)');
                await expectInsideScreen(page.locator('#yes-button'), 'Over-pick Yes button', viewport);
                await expectInsideScreen(page.locator('#no-button'), 'Over-pick No button', viewport);
                await YesNoDialog.clickNoButton();
            });

            // noinspection JSUnusedLocalSymbols
            test(`Manual: catch weight QR view and manual catch weight input [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Manual screens');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);
                const masterdata = await createCatchWeightMasterdata({ language: lang });
                await startSalesOrderJob(masterdata);
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
                await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
                await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

                await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
                await GetQuantityDialog.waitForDialog();
                await page.getByTestId('switchToManualInput-button').waitFor({ state: 'visible' });
                await layout('Manual catch weight QR view');
                await expectInsideScreen(page.getByTestId('switchToManualInput-button'), 'Switch to manual input button', viewport);
                await GetQuantityDialog.clickManual();
                await layout('Manual quantity dialog of a catch weight product (manual input)');
                await expectInsideScreen(page.locator('#catch-weight'), 'Catch weight field', viewport);
                await expectInsideScreen(page.getByTestId('done-button'), 'OK button', viewport);
            });

            // noinspection JSUnusedLocalSymbols
            test(`Manual: inline GRAI capture panel and the "skipped" notice [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Manual screens');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);
                const masterdata = await createGraiMasterdata({ language: lang });
                const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, 20);
                await startSalesOrderJob(masterdata);
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
                await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

                await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode, expectQtyEntered: '10', expectNextScreen: 'PickGraiScreen' });
                await layout('Manual GRAI capture panel, nothing captured');
                await PickGraiScreen.scanGraiBatch({ graiStrings: allGrais.slice(0, 10) });
                await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
                // not layout-checked with the generic checker: the chips' remove button "x" is 20x20 px by the base design (GRAIScreen.scss .grai-chip-remove,
                // not part of the feature); the panel's own controls are checked for reachability instead
                await expectInsideScreen(page.locator('.grai-screen'), 'GRAI capture panel with 10 chips', { width: viewport.width, height: 100000 });
                await expectReachable(page.getByTestId('grai-save-button'), 'GRAI Save button', viewport);
                await PickGraiScreen.clickSave();
                await PickingJobScreen.waitForScreen();
                await layout('Manual job screen after a GRAI pick');

                await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU_SOURCE_P2.qrCode, expectQtyEntered: '10', expectNextScreen: 'PickGraiScreen' });
                await PickGraiScreen.scanGrai({ graiString: allGrais[0] }); // already on the LU: skipped
                await PickGraiScreen.expectSkippedNotice({ count: 1 });
                await layout('Manual GRAI capture panel with the "1 skipped" notice');
            });
        }

        // noinspection JSUnusedLocalSymbols
        test(`Manual: job screen with the Quick Pack All button; button hidden in a job with a Scan once line (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const manual = await createQuickPackMasterdata({});
            await login(manual);
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.filterByDocumentNo(manual.salesOrders.SO1.documentNo);
            await PickingJobsListScreen.startJob({ documentNo: manual.salesOrders.SO1.documentNo });
            await PickingJobScreen.expectPickAllButtonVisible();
            await layout('Manual job screen with Quick Pack All button');
            await expectReachable(page.getByTestId('pickAll-button'), 'Quick Pack All button', viewport);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Mixed job (Manual and Scan once customer): Quick Pack All button hidden, job screen sound (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const masterdata = await createQuickPackMasterdata({
                aggregationType: 'product',
                secondCustomer: true,
                customers: [{ customer: 'customer1', cuQtyEntryMode: MODE_MANUAL }, { customer: 'customer2', cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY' }],
            });
            await login(masterdata);
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 20, productId: masterdata.products.P1.id });
            await PickingJobScreen.expectPickAllButtonHiddenAfterQtyAvailable();
            await expectLayoutSound('Mixed job screen, no Quick Pack All button', viewport);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Manual by profile default, empty customer record, customer record Manual: job and line screens are sound (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            // product job with three customers: A has no record (profile default = not sent = Manual), B has an empty record, C has a record Manual
            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 4 }, { key: 'B', qty: 4 }, { key: 'C', qty: 4 }],
                profileMode: MODE_MANUAL, // sent explicitly: the picking profile is shared by every test run on this DB
                customers: [{ customer: 'custB' }, { customer: 'custC', cuQtyEntryMode: MODE_MANUAL }],
            });
            await login(masterdata);
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 12, productId: masterdata.products.P1.id });
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
            await layout('Manual job screen, three customers (profile default / empty record / record Manual)');
            for (const index of [1, 2, 3]) {
                await PickingJobScreen.clickLineButton({ index });
                await PickingJobLineScreen.waitForScreen();
                await layout(`Manual line screen of customer ${index}`);
                await PickingJobLineScreen.clickScanButton();
                await PickLineScanScreen.waitForScreen();
                await PickLineScanScreen.expectCUQtyEntryMode(MODE_MANUAL);
                await layout(`Manual scan step of customer ${index}`);
                await PickLineScanScreen.goBack();
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
            }
        });

        const reachableWithKeyboard = async (label) => {
            const keyboardHeight = Math.round(viewport.height * 0.55);
            const dialog = page.locator('.get-qty-dialog');
            const smaller = { width: viewport.width, height: keyboardHeight };
            await page.setViewportSize(smaller);
            await dialog.locator('#qty-input').tap();
            for (const [testId, name] of [['done-button', 'OK'], ['cancel-button', 'Cancel']]) {
                const button = dialog.getByTestId(testId);
                await button.evaluate((el) => el.scrollIntoView({ block: 'center' }));
                await expectInsideScreen(button, `${label} ${name} button with keyboard`, smaller);
            }
            await dialog.locator('#qty-input').evaluate((el) => el.scrollIntoView({ block: 'center' }));
            await expectInsideScreen(dialog.locator('#qty-input'), `${label} quantity field with keyboard`, smaller);
            // the generic overlap check is not used here: with the keyboard the table area scrolls inside the dialog (by design) and the generic
            // checker would report a lot field scrolled out of that area as overlapping the buttons; the screenshot shows the clean state
            await saveScreenshot(`${viewport.width}x${keyboardHeight}-${label}-with-keyboard`);
        };

        // noinspection JSUnusedLocalSymbols
        test(`Manual: on-screen keyboard open (screen height cut to 55 %), quantity field and OK stay reachable (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const manual = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }], profileMode: MODE_MANUAL });
            await startJob(manual, { qtyToDeliver: 12 });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await GetQuantityDialog.waitForDialog();
            await reachableWithKeyboard('Manual quantity dialog');
        });

        // noinspection JSUnusedLocalSymbols
        test(`Scan once: on-screen keyboard open (screen height cut to 55 %), quantity field and OK stay reachable (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const once = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }] });
            await startJob(once, { qtyToDeliver: 12 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata: once }));
            await reachableWithKeyboard('Scan once quantity block');
        });

        // noinspection JSUnusedLocalSymbols
        test(`Shared components in distribution: product code scan step, quantity dialog (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);
            const masterdata = await createMasterdataRememberingSysconfigs({
                language: 'en_US',
                request: {
                    login: { user: { language: 'en_US', workplace: 'workplace2' } },
                    mobileConfig: { distribution: { requireScanningProductCode: true } },
                    resources: { plantId: { type: 'PT' } },
                    products: { P1: { gtin: generateEAN13().ean13 } },
                    warehouses: { wh1: {}, wh2: { locators: { wh2_l1: {} } }, whInTransit: { inTransit: true } },
                    workplaces: { workplace2: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
                    handlingUnits: { HU1: { product: 'P1', warehouse: 'wh1', qty: 100000 } },
                    distributionOrders: { DD1: { warehouseFrom: 'wh1', warehouseTo: 'wh2', warehouseInTransit: 'whInTransit', plant: 'plantId', lines: [{ product: 'P1', qtyEntered: 100 }] } },
                },
            });
            await login(masterdata, 'distribution');
            await DistributionJobsListScreen.waitForScreen();
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
            await DistributionJobScreen.waitForScreen();
            await layout('Distribution job screen');
            await BarcodeScannerComponent.type({ scannedCode: masterdata.handlingUnits.HU1.qrCode });
            await DistributionLinePickFromScreen.waitForScreen();
            await page.getByTestId('scanProductCode-input').waitFor({ state: 'attached' });
            await layout('Product code scan step after the HU scan (shared scan component)');
            await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P1.gtin);
            await GetQuantityDialog.waitForDialog();
            await layout('Distribution quantity dialog (shared GetQuantityDialog)');
            await expectInsideScreen(page.getByTestId('done-button'), 'Distribution OK button', viewport);
        });

        // noinspection JSUnusedLocalSymbols
        test(`HU Manager: scan screen, HU info with action buttons, change quantity dialog (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);
            const masterdata = await createMasterdataRememberingSysconfigs({
                language: 'en_US',
                request: {
                    login: { user: { language: 'en_US' } },
                    products: { P1: {} },
                    warehouses: { wh1: {} },
                    packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
                    handlingUnits: { HU1: { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI' } },
                },
            });
            await login(masterdata, 'huManager');
            await HUManagerScreen.waitForScreen();
            await layout('HU Manager scan screen');
            await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
            await layout('HU Manager HU info and action buttons');
            await page.getByTestId('change-qty-button').tap();
            await page.getByTestId('ChangeHUQtyDialog').waitFor();
            await layout('HU Manager change quantity dialog');
        });

        // The quantity dialog of the HU Manager move (ReadQtyDialog) shares the dialog class with the picking quantity dialog: it must look .
        // noinspection JSUnusedLocalSymbols
        test(`HU Manager move: number of TUs dialog of an aggregated TU (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);
            const masterdata = await createMasterdataRememberingSysconfigs({
                language: 'en_US',
                request: {
                    login: { user: { language: 'en_US' } },
                    products: { P1: {} },
                    warehouses: { wh1: {} },
                    packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
                    handlingUnits: { HU1: { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI', qrCodeOfAggregatedTU: true }, HU2: { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI' } },
                },
            });
            await login(masterdata, 'huManager');
            await HUManagerScreen.waitForScreen();
            await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
            await page.getByTestId('move-button').tap();
            await HUMoveScreen.waitForScreen();
            await BarcodeScannerComponent.type(masterdata.handlingUnits.HU2.qrCode);
            await page.locator('.get-qty-dialog').waitFor();
            await layout('HU Manager move: number of TUs dialog');
            await saveScreenshot(`${viewport.width}x${viewport.height}-HU-Manager-move-number-of-TUs-dialog`);
        });
    });
}
