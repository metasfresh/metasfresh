import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { expectErrorToast, page } from "../../utils/common";
import { expectLayoutSound, expectOnScreenWithoutScrolling, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES } from "../../utils/layout";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { SelectPickTargetTUScreen } from "../../utils/screens/picking/SelectPickTargetTUScreen";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { uniqueGTIN, uniqueLot } from "../../utils/cuLabel";
import {
    allureMeta, createMasterdata, createMasterdataRememberingSysconfigs, labelOf, LAUNCHERS_LIMIT_SYSCONFIGS, MODE_MANUAL, MODE_SCAN_ONCE, uniqueFormatName, openLine,
    restoreSysconfigsAfterEach, scanLabel, setPalletTarget, startJob, unpackFirstBooking,
} from "../../utils/cuQtyEntryScanOnce";
import { createScanEachMasterdata, MODE_SCAN_EACH, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/**
 * Layout of the screens and refusals shared by Scan each CU and Scan once (box list, label refusals, GRAI-required customers, pallet target,
 * quick pack all) and of the Scan each CU only states (pieces-only refusal, second lot, finished line, unpack, second job) at the phone sizes
 * (PHONE_VIEWPORTS), in English and German. Same checker as picking-cu-qty-entry-responsive.spec.js.
 */
const MODES = [MODE_SCAN_EACH, MODE_SCAN_ONCE];
const modeName = (mode) => (mode === MODE_SCAN_EACH ? 'Each' : 'Once');

/**
 * Text of a refusal in the session language. Refusals of the backend whose German text is not pinned here (`de` undefined) are
 * asserted by their English text only; in German the test checks that a refusal toast is shown and judges its layout.
 */
const txt = (lang, { en, de }) => (lang === 'en_US' ? en : de ?? '');
const TEXT = {
    noFormat: { en: 'No label format set on the picking profile', de: 'Kein Etikettenformat im Kommissionierprofil' },
    differentProduct: { en: 'The scanned QR Product does not match', de: 'Das gescannte QR Produkt stimmt nicht mit dem' },
    graiCount: { en: 'TUs have a GRAI' },
    eachOnlyPieces: { en: "'Scan each CU' works only for lines counted in pieces", de: 'funktioniert nur für Zeilen, die in Stück geführt werden' },
    overPick: { en: 'Cannot pick more than the ordered quantity for this line.' },
};

const graiTuType = (key) => ({ [key]: { tu: `TU_${key}`, product: 'P1', qtyCUsPerTU: 4, graiMapping: true } });

/** Presses "Fertigstellen" on the job screen and confirms: the refusal toast is judged while shown. */
const completeRefusedWithLayout = async (expectedText, whileToastShown) => {
    await expectErrorToast(
        'complete refusal',
        async () => {
            await page.locator('#last-confirm-button').tap();
            await page.locator('.prompt-dialog').first().waitFor();
            await YesNoDialog.clickYesButton();
        },
        async ({ textContent }) => {
            expect(textContent).toContain(expectedText);
            await whileToastShown?.();
        }
    );
    await PickingJobScreen.waitForScreen();
};

/** Opens the scan screen of line `index` in the mode; Each opens its first box when asked, Once never (the caller decides). */
const openModeLine = async (mode, index = 1) => {
    if (mode === MODE_SCAN_EACH) {
        await openScanEachLine({ index, openBox: false });
    } else {
        await openLine({ index });
    }
};

const masterdataOf = async (mode, { lang = 'en_US', orders, ...rest }) =>
    mode === MODE_SCAN_EACH
        ? await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders, language: lang, ...rest })
        : await createMasterdata({ formatName: uniqueFormatName(), orders, language: lang, ...rest });

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`label-based mode screens ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });
        const layoutFor = (lang) => (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

        // English and German at every size
        const languages = LAYOUT_LANGUAGES;
        for (const lang of languages) {
            const layout = layoutFor(lang);

            // a line without TU type: "Next box" opens the box list; customer GRAI required: no pick-time GRAI screen, "Fertigstellen" refused
            // noinspection JSUnusedLocalSymbols
            test(`Each: line without TU type, GRAI required: box list, "Fertigstellen" refused [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createScanEachMasterdata({
                    packFormatName: uniqueFormatName(), language: lang,
                    orders: [{ key: 'A', qty: 2, graiRequired: 'Y', piItemProduct: null }], packingInstructions: graiTuType('G1'),
                });
                const tuName = masterdata.packingInstructions.P1_20x4.tuName;
                const lot = uniqueLot();
                await startJob(masterdata, { qtyToDeliver: 2 });
                await openScanEachLine({ index: 1, openBox: false });
                await layout('Each scan screen, GRAI-required customer, no box open');

                await PickLineScanScreen.nextBox();
                await SelectPickTargetTUScreen.waitForScreen();
                await layout('Each "Gebinde" list opened by Next box (line without TU type)');
                await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
                await PickingJobLineScreen.waitForScreen();
                await layout('Each line screen after the box type was chosen');
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await layout('Each scan screen, box open');
                await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));

                await PickLineScanScreen.nextBox();
                await SelectPickTargetTUScreen.waitForScreen();
                await layout('Each "Gebinde" list again after a booking');
                await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
                await PickingJobLineScreen.waitForScreen();
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
                await PickingJobLineScreen.waitForScreen();
                await layout('Each line screen, line done');
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
                await layout('Each job screen, line done, boxes without GRAI');
                await completeRefusedWithLayout(txt(lang, TEXT.graiCount), async () => await layout('Each "Fertigstellen" refused: every box needs a GRAI (job screen + toast)'));
            });

            // noinspection JSUnusedLocalSymbols
            test(`Once: line without TU type, GRAI required: box list, "Fertigstellen" refused [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createMasterdata({
                    formatName: uniqueFormatName(), language: lang,
                    orders: [{ key: 'A', qty: 4, graiRequired: 'Y', piItemProduct: null }], packingInstructions: graiTuType('G1'),
                });
                const tuName = masterdata.packingInstructions.P1_20x4.tuName;
                await startJob(masterdata, { qtyToDeliver: 4 });
                await openLine({ index: 1 });
                await layout('Once scan screen, GRAI-required customer, no box open');
                await scanLabel(labelOf({ masterdata }));
                await GetQuantityDialog.typeQtyEntered(4);
                await layout('Once quantity block, GRAI-required customer, no box open');

                await PickLineScanScreen.nextBox();
                await SelectPickTargetTUScreen.waitForScreen();
                await layout('Once "Gebinde" list opened by Next box (line without TU type)');
                await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
                await PickingJobLineScreen.waitForScreen();
                await layout('Once line screen after the box type was chosen');
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await layout('Once scan step after the box type was chosen');
                if (await page.locator('.get-qty-dialog').count() === 0) {
                    await scanLabel(labelOf({ masterdata }));
                }
                await layout('Once quantity block, box open (opened without GRAI)');
                await GetQuantityDialog.typeQtyEntered(4);
                await GetQuantityDialog.clickDoneStayOpen();
                await PickingJobLineScreen.waitForScreen(); // entered from the line screen: the done line leads back there
                await layout('Once line screen, line done');
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
                await layout('Once job screen, line done, box without GRAI');
                await completeRefusedWithLayout(txt(lang, TEXT.graiCount), async () => await layout('Once "Fertigstellen" refused: every box needs a GRAI (job screen + toast)'));
            });

            for (const mode of MODES) {
                const M = modeName(mode);
                const germanToo = (wanted) => lang === 'en_US' || mode === wanted;

                // no effective format
                // noinspection JSUnusedLocalSymbols
                if (germanToo(MODE_SCAN_EACH)) test(`${M}: no label format on the profile: refusal [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                    allureMeta('Responsive shared states');
                    const masterdata = await createMasterdata({
                        formatName: undefined, formats: [], profileMode: mode, language: lang, orders: [{ key: 'A', qty: 3 }],
                    });
                    await startJob(masterdata, { qtyToDeliver: 3 });
                    await openModeLine(mode);
                    await PickLineScanScreen.openBox();
                    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), txt(lang, TEXT.noFormat), {
                        whileToastShown: async () => await layout(`${M} refusal: no label format on the profile (scan screen + toast)`),
                    });
                    await layout(`${M} scan screen after the refusal`);
                });

                // label of another product
                // noinspection JSUnusedLocalSymbols
                if (germanToo(MODE_SCAN_ONCE)) test(`${M}: label of another product: refusal [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                    allureMeta('Responsive shared states');
                    const masterdata = await masterdataOf(mode, {
                        lang, orders: [{ key: 'A', qty: 3 }],
                        products: { P2: { price: 1, randomValue: { size: 7, isIncludeDigits: true } } },
                    });
                    await startJob(masterdata, { qtyToDeliver: 3 });
                    await openModeLine(mode);
                    await PickLineScanScreen.openBox();
                    const otherProductLabel = packLabelOf({ masterdata: { products: { P1: masterdata.products.P2 } }, packId: 1 });
                    await PickLineScanScreen.scanCULabelRefused(otherProductLabel, txt(lang, TEXT.differentProduct), {
                        whileToastShown: async () => await layout(`${M} refusal: label of another product (scan screen + toast)`),
                    });
                    await layout(`${M} scan screen after the refusal`);
                });
            }

            // a line counted in kg
            // noinspection JSUnusedLocalSymbols
            test(`Each: line counted in kg: "pieces only" refusal [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createScanEachMasterdata({
                    packFormatName: uniqueFormatName(), language: lang, orders: [{ key: 'A', qty: 3 }],
                    products: { P1: { price: 1, uom: 'KGM', randomValue: { size: 7, isIncludeDigits: true } } },
                });
                await startJob(masterdata, { qtyToDeliver: 3 });
                await openScanEachLine({ index: 1 });
                await layout('Each scan screen, kg line');
                await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), txt(lang, TEXT.eachOnlyPieces), {
                    whileToastShown: async () => await layout('Each refusal: Scan each CU works only for pieces (scan screen + toast, long text)'),
                });
            });
        }

        // The remaining states have no own text; checked in English and German by the language loop below where a header or toast shows text.
        for (const lang of languages) {
            const layout = layoutFor(lang);

            // second lot on the same line, scan after the line is done, unpack
            // noinspection JSUnusedLocalSymbols
            test(`Each: second lot, finished line re-entered, unpack and rescan [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createScanEachMasterdata({ packFormatName: uniqueFormatName(), language: lang, orders: [{ key: 'A', qty: 3 }] });
                const lotA = uniqueLot();
                const lotB = uniqueLot();
                const gtin = uniqueGTIN();
                const pack = (lot, packId) => packLabelOf({ masterdata, lot, gtin, packId });
                await startJob(masterdata, { qtyToDeliver: 3 });
                await openScanEachLine({ index: 1 });
                await scanPack(pack(lotA, 1));
                await scanPack(pack(lotB, 1));
                await layout('Each scan screen after a second lot was booked (mixed-lot box)');
                await scanPack(pack(lotB, 2));
                await PickingJobScreen.waitForScreen();
                await layout('Each job screen, line done (3 of 3)');

                await PickingJobScreen.clickLineButton({ index: 1 });
                await PickingJobLineScreen.waitForScreen();
                await layout('Each line screen of a done line');
                await expectReachable(page.locator('#step-0-button'), 'booked CU button', viewport);
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await layout('Each scan screen entered again via Pack HU on a done line');
                await PickLineScanScreen.scanCULabelRefused(pack(lotB, 4), txt(lang, TEXT.overPick), {
                    whileToastShown: async () => await layout('Each refusal: scan on a done line (scan screen + toast)'),
                });

                await PickLineScanScreen.goBack();
                await layout('Each line screen with three booked CUs');
                await PickingJobLineScreen.clickStepButton({ index: 0 });
                await PickingJobStepScreen.waitForScreen();
                await layout('Each step screen of a booked CU (unpack)');
                await expectReachable(page.locator('#unpick-button'), 'Unpack button', viewport);
                await page.locator('#unpick-button').tap();
                await layout('Each unpack dialog');
                            });
        }

        // the same pack in a second job
        // noinspection JSUnusedLocalSymbols
        test(`Each: the same pack in a second job: job list, second job scan screen (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive shared states');
            const layout = layoutFor('en_US');
            const masterdata = await createScanEachMasterdata({
                packFormatName: uniqueFormatName(), orders: [{ key: 'A', qty: 1 }, { key: 'B', qty: 1 }], allowCompletingPartialPickingJob: true,
            });
            const label = packLabelOf({ masterdata, lot: uniqueLot(), packId: 2 });
            await startJob(masterdata, { qtyToDeliver: 2 });
            await openScanEachLine({ index: 1 });
            await scanPack(label);
            await PickingJobScreen.waitForScreen();
            await layout('Each job screen of job 1, line 1 done, line 2 open');
            await PickingJobScreen.complete();
            await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 1, productId: masterdata.products.P1.id });
            await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
            await layout('Each job screen of job 2');
            await openScanEachLine({ index: 1 });
            await layout('Each scan screen of job 2, box open');
            await scanPack(label);
            await PickingJobScreen.waitForScreen();
            await layout('Each job screen of job 2 after the same pack booked without a warning');
        });

        // pallet target and box packing, both modes
        for (const mode of MODES) {
            const M = modeName(mode);
            // noinspection JSUnusedLocalSymbols
            test(`${M}: pallet target + new box, second booking into the open box (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const layout = layoutFor('en_US');
                const masterdata = await masterdataOf(mode, { orders: [{ key: 'A', qty: 4 }], pickTo: ['LU_TU', 'TU'] });
                const lot = uniqueLot();
                await startJob(masterdata, { qtyToDeliver: 4 });
                await openModeLine(mode);
                await layout(`${M} scan screen before the pallet target is set`);
                await setPalletTarget({ lu: masterdata.packingInstructions.P1_20x4.luName });
                await layout(`${M} scan screen with a pallet target, no box open`);
                await PickLineScanScreen.openBox();
                await layout(`${M} scan screen with a pallet target, box open`);
                if (mode === MODE_SCAN_EACH) {
                    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
                    await layout(`${M} scan screen after the first booking into the box on the pallet`);
                    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
                    await layout(`${M} scan screen after the second booking into the same box`);
                } else {
                    await scanLabel(labelOf({ masterdata, lot }));
                    await layout(`${M} quantity block with a pallet target ("OK und LU schliessen" shown if the app offers it)`);
                    await GetQuantityDialog.typeQtyEntered(1);
                    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 3 });
                    await layout(`${M} quantity block after the first booking`);
                    await GetQuantityDialog.typeQtyEntered(1);
                    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });
                    await layout(`${M} quantity block after the second booking into the same box`);
                }
            });
        }

        // Quick Pack All is hidden for a job with a label-based-mode line (mixed job); the Manual-only job still shows it
        const buildMasterdata = async (customers) => await createMasterdataRememberingSysconfigs({
            language: 'en_US',
            request: {
                sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
                login: { user: { language: 'en_US', workplace: 'workplace1' } },
                mobileConfig: {
                    picking: {
                        aggregationType: 'product', createShipmentPolicy: 'CL', allowPickingAnyHU: true, allowQuickPackAll: true, shipOnCloseLU: false,
                        pickTo: ['CU', 'LU_CU'], allowCompletingPartialPickingJob: false, allowPickingAnyCustomer: false, customers,
                    },
                },
                bpartners: { customer1: {}, customer2: {} },
                warehouses: { wh: {} },
                pickingSlots: { slot1: {} },
                workplaces: { workplace1: { warehouse: 'wh', pickingSlot: 'slot1' } },
                products: { P1: { price: 1 }, P2: { price: 2 }, P3: { price: 3 } },
                packingInstructions: { LU_CU: { cu: true, lu: 'LU', qtyTUsPerLU: 1 } },
                handlingUnits: {
                    HU1: { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                    HU2: { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                    HU3: { product: 'P3', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                },
                salesOrders: {
                    SO1: { bpartner: 'customer1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 10 }, { product: 'P2', qty: 10 }, { product: 'P3', qty: 10 }] },
                    SO2: { bpartner: 'customer2', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 10 }, { product: 'P2', qty: 10 }, { product: 'P3', qty: 10 }] },
                },
            },
        });
        const openProductJob = async (masterdata) => {
            await LoginScreen.login(masterdata.login.user);
            await ApplicationsListScreen.expectVisible();
            await ApplicationsListScreen.startApplication('picking');
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 20, productId: masterdata.products.P1.id });
        };


        // noinspection JSUnusedLocalSymbols
        test(`mixed job (Manual + Scan once): job screen without Quick Pack All (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive shared states');
            const layout = layoutFor('en_US');
            const mixed = await buildMasterdata([{ customer: 'customer1', cuQtyEntryMode: MODE_MANUAL }, { customer: 'customer2', cuQtyEntryMode: MODE_SCAN_ONCE }]);
            await openProductJob(mixed);
            await PickingJobScreen.expectPickAllButtonHiddenAfterQtyAvailable();
            await layout('mixed job screen (Manual line + Scan once line), Quick Pack All hidden');
            await PickingJobScreen.clickLineButton({ index: 2 });
            await layout('mixed job: line screen or scan screen of the Scan once line');
        });

        // noinspection JSUnusedLocalSymbols
        test(`control: Manual-only job screen with Quick Pack All shown, so its absence in the mixed job is the mode (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive shared states');
            const layout = layoutFor('en_US');
            const manualOnly = await buildMasterdata([{ customer: 'customer1' }, { customer: 'customer2' }]);
            await openProductJob(manualOnly);
            await PickingJobScreen.expectPickAllButtonVisible();
            await layout('control: Manual-only job screen with Quick Pack All shown');
        });
    });
}
