import { test } from "../../../playwright.config";
import { expectLayoutSound, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, DIALOG_SCOPE } from "../../utils/layout";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { SelectPickTargetTUScreen } from "../../utils/screens/picking/SelectPickTargetTUScreen";
import { uniqueGTIN, uniqueLot } from "../../utils/cuLabel";
import {
    allureMeta, createMasterdata, createMasterdataRememberingSysconfigs, graiTuType, labelOf, LAUNCHERS_LIMIT_SYSCONFIGS, login, MODE_MANUAL, MODE_SCAN_ONCE, uniqueFormatName, openLine,
    restoreSysconfigsAfterEach, scanLabel, setPalletTarget, startJob,
} from "../../utils/cuQtyEntryCommon";
import { txt } from "../../utils/cuQtyEntryMessages";
import { createScanEachMasterdata, MODE_SCAN_EACH, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

const MODES = [MODE_SCAN_EACH, MODE_SCAN_ONCE];
const modeName = (mode) => (mode === MODE_SCAN_EACH ? 'Each' : 'Once');

const openModeLine = async (mode, index = 1) => {
    if (mode === MODE_SCAN_EACH) {
        await openScanEachLine({ index, openTU: false });
    } else {
        await openLine({ index });
    }
};

const masterdataOf = async (mode, { lang = 'en_US', orders, ...rest }) =>
    mode === MODE_SCAN_EACH
        ? await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders, language: lang, ...rest })
        : await createMasterdata({ formatName: uniqueFormatName(), orders, language: lang, ...rest });

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`label-required mode screens ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });
        const layoutFor = (lang) => (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

        // English and German at every size
        const languages = LAYOUT_LANGUAGES;
        for (const lang of languages) {
            const layout = layoutFor(lang);

            test(`Each: line without TU type, GRAI required: TU list, "Fertigstellen" refused [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createScanEachMasterdata({
                    packFormatName: uniqueFormatName(), language: lang,
                    orders: [{ key: 'A', qty: 2, graiRequired: 'Y', piItemProduct: null }], packingInstructions: graiTuType('G1'),
                });
                const tuName = masterdata.packingInstructions.P1_20x4.tuName;
                const lot = uniqueLot();
                await startJob(masterdata, { qtyToDeliver: 2 });
                await openScanEachLine({ index: 1, openTU: false });
                await layout('Each scan screen, GRAI-required customer, no TU open');

                await PickLineScanScreen.nextTU();
                await SelectPickTargetTUScreen.waitForScreen();
                await layout('Each "Gebinde" list opened by Next TU (line without TU type)');
                await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
                await PickingJobLineScreen.waitForScreen();
                await layout('Each line screen after the TU type was chosen');
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await layout('Each scan screen, TU open');
                await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));

                await PickLineScanScreen.nextTU();
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
                await layout('Each job screen, line done, TUs without GRAI');
                await PickingJobScreen.completeRefused({ expectedError: txt(lang, 'graiCount'), whileToastShown: async () => await layout('Each "Fertigstellen" refused: every TU needs a GRAI (job screen + toast)') });
            });

            // noinspection JSUnusedLocalSymbols
            test(`Once: line without TU type, GRAI required: TU list, "Fertigstellen" refused [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createMasterdata({
                    formatName: uniqueFormatName(), language: lang,
                    orders: [{ key: 'A', qty: 4, graiRequired: 'Y', piItemProduct: null }], packingInstructions: graiTuType('G1'),
                });
                const tuName = masterdata.packingInstructions.P1_20x4.tuName;
                await startJob(masterdata, { qtyToDeliver: 4 });
                await openLine({ index: 1 });
                await layout('Once scan screen, GRAI-required customer, no TU open');
                await scanLabel(labelOf({ masterdata }));
                await GetQuantityDialog.typeQtyEntered(4);
                await layout('Once quantity block, GRAI-required customer, no TU open', DIALOG_SCOPE);

                await PickLineScanScreen.nextTU();
                await SelectPickTargetTUScreen.waitForScreen();
                await layout('Once "Gebinde" list opened by Next TU (line without TU type)');
                await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
                await PickingJobLineScreen.waitForScreen();
                await layout('Once line screen after the TU type was chosen');
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await layout('Once scan step after the TU type was chosen');
                await GetQuantityDialog.expectNotShown(); // nothing was booked on the line: no remembered label, the scan step stays
                await scanLabel(labelOf({ masterdata }));
                await layout('Once quantity block, TU open (opened without GRAI)', DIALOG_SCOPE);
                await GetQuantityDialog.typeQtyEntered(4);
                await GetQuantityDialog.clickDoneStayOpen();
                await PickingJobLineScreen.waitForScreen(); // entered from the line screen: the done line leads back there
                await layout('Once line screen, line done');
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
                await layout('Once job screen, line done, TU without GRAI');
                await PickingJobScreen.completeRefused({ expectedError: txt(lang, 'graiCount'), whileToastShown: async () => await layout('Once "Fertigstellen" refused: every TU needs a GRAI (job screen + toast)') });
            });

            for (const mode of MODES) {
                const M = modeName(mode);
                const germanToo = (wanted) => lang === 'en_US' || mode === wanted;

                if (germanToo(MODE_SCAN_EACH)) test(`${M}: no label format on the profile: refusal [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                    allureMeta('Responsive shared states');
                    const masterdata = await createMasterdata({
                        formatName: undefined, formats: [], profileMode: mode, language: lang, orders: [{ key: 'A', qty: 3 }],
                    });
                    await startJob(masterdata, { qtyToDeliver: 3 });
                    await openModeLine(mode);
                    await PickLineScanScreen.openTU();
                    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), txt(lang, 'noFormat'), {
                        whileToastShown: async () => await layout(`${M} refusal: no label format on the profile (scan screen + toast)`),
                    });
                    await layout(`${M} scan screen after the refusal`);
                });

                if (germanToo(MODE_SCAN_ONCE)) test(`${M}: label of another product: refusal [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                    allureMeta('Responsive shared states');
                    const masterdata = await masterdataOf(mode, {
                        lang, orders: [{ key: 'A', qty: 3 }],
                        products: { P2: { price: 1, randomValue: { size: 7, isIncludeDigits: true } } },
                    });
                    await startJob(masterdata, { qtyToDeliver: 3 });
                    await openModeLine(mode);
                    await PickLineScanScreen.openTU();
                    const otherProductLabel = packLabelOf({ masterdata: { products: { P1: masterdata.products.P2 } }, packId: 1 });
                    await PickLineScanScreen.scanCULabelRefused(otherProductLabel, txt(lang, 'differentProduct'), {
                        whileToastShown: async () => await layout(`${M} refusal: label of another product (scan screen + toast)`),
                    });
                    await layout(`${M} scan screen after the refusal`);
                });
            }

            test(`Each: line counted in kg: "pieces only" refusal [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const masterdata = await createScanEachMasterdata({
                    packFormatName: uniqueFormatName(), language: lang, orders: [{ key: 'A', qty: 3 }],
                    products: { P1: { price: 1, uom: 'KGM', randomValue: { size: 7, isIncludeDigits: true } } },
                });
                await startJob(masterdata, { qtyToDeliver: 3 });
                await openScanEachLine({ index: 1 });
                await layout('Each scan screen, kg line');
                await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), txt(lang, 'eachOnlyPieces'), {
                    whileToastShown: async () => await layout('Each refusal: Scan each CU works only for pieces (scan screen + toast, long text)'),
                });
            });
        }

        for (const lang of languages) {
            const layout = layoutFor(lang);

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
                await layout('Each scan screen after a second lot was booked (mixed-lot TU)');
                await scanPack(pack(lotB, 2));
                await PickingJobScreen.waitForScreen();
                await layout('Each job screen, line done (3 of 3)');

                await PickingJobScreen.clickLineButton({ index: 1 });
                await PickingJobLineScreen.waitForScreen();
                await layout('Each line screen of a done line');
                await expectReachable(PickingJobLineScreen.stepButton({ index: 0 }), 'booked CU button', viewport);
                await PickingJobLineScreen.tapPickHUButton();
                await PickLineScanScreen.waitForScreen();
                await layout('Each scan screen entered again via Pack HU on a done line');
                await PickLineScanScreen.scanCULabelRefused(pack(lotB, 4), txt(lang, 'overPick'), {
                    whileToastShown: async () => await layout('Each refusal: scan on a done line (scan screen + toast)'),
                });

                await PickLineScanScreen.goBack();
                await layout('Each line screen with three booked CUs');
                await PickingJobLineScreen.clickStepButton({ index: 0 });
                await PickingJobStepScreen.waitForScreen();
                await layout('Each step screen of a booked CU (unpack)');
                await expectReachable(PickingJobStepScreen.unpickButton(), 'Unpack button', viewport);
                await PickingJobStepScreen.tapUnpick();
                await layout('Each unpack dialog', DIALOG_SCOPE);
                            });
        }

        test(`Each: the same CU in a second job: job list, second job scan screen (${viewport.name})`, async ({ page: _page }) => {
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
            await layout('Each scan screen of job 2, TU open');
            await scanPack(label);
            await PickingJobScreen.waitForScreen();
            await layout('Each job screen of job 2 after the same CU booked without a warning');
        });

        // pallet target and TU packing, both modes
        for (const mode of MODES) {
            const M = modeName(mode);
            // noinspection JSUnusedLocalSymbols
            test(`${M}: pallet target + new TU, second booking into the open TU (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive shared states');
                const layout = layoutFor('en_US');
                const masterdata = await masterdataOf(mode, { orders: [{ key: 'A', qty: 4 }], pickTo: ['LU_TU', 'TU'] });
                const lot = uniqueLot();
                await startJob(masterdata, { qtyToDeliver: 4 });
                await openModeLine(mode);
                await layout(`${M} scan screen before the pallet target is set`);
                await setPalletTarget({ lu: masterdata.packingInstructions.P1_20x4.luName });
                await layout(`${M} scan screen with a pallet target, no TU open`);
                await PickLineScanScreen.openTU();
                await layout(`${M} scan screen with a pallet target, TU open`);
                if (mode === MODE_SCAN_EACH) {
                    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
                    await layout(`${M} scan screen after the first booking into the TU on the pallet`);
                    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
                    await layout(`${M} scan screen after the second booking into the same TU`);
                } else {
                    await scanLabel(labelOf({ masterdata, lot }));
                    await layout(`${M} quantity block with a pallet target ("OK und LU schliessen" shown if the app offers it)`, DIALOG_SCOPE);
                    await GetQuantityDialog.typeQtyEntered(1);
                    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 3 });
                    await layout(`${M} quantity block after the first booking`, DIALOG_SCOPE);
                    await GetQuantityDialog.typeQtyEntered(1);
                    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });
                    await layout(`${M} quantity block after the second booking into the same TU`, DIALOG_SCOPE);
                }
            });
        }

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
            await login(masterdata);
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
