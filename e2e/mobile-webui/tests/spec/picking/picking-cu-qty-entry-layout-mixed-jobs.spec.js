import { test } from "../../../playwright.config";
import { expectLayoutSound, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES } from "../../utils/layout";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { uniqueLot } from "../../utils/cuLabel";
import { allureMeta, book, createMasterdata, labelOf, MODE_MANUAL, MODE_SCAN_ONCE, uniqueFormatName, openLine, restoreSysconfigsAfterEach, scanLabel, startJob } from "../../utils/cuQtyEntryCommon";
import { createScanEachMasterdata, MODE_SCAN_EACH, MSG_ALREADY_PICKED, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`Scan once and mixed job screens ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        for (const lang of LAYOUT_LANGUAGES) {
            // noinspection JSUnusedLocalSymbols
            test(`Scan once: re-entry, closed short, over-max, smaller count, count over several source TUs [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Scan once states');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

                const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }], language: lang });
                await startJob(masterdata, { qtyToDeliver: 12 });
                await openLine({ index: 1 });
                await PickLineScanScreen.openTU();
                await scanLabel(labelOf({ masterdata }));

                // above the open quantity
                await GetQuantityDialog.typeQtyEntered(13);
                await GetQuantityDialog.expectDoneDisabled();
                await GetQuantityDialog.expectQtyValidationErrorVisible();
                await layout('Once quantity block, count above the open quantity (validation text)');

                // smaller than open, no rejection prompt
                await GetQuantityDialog.typeQtyEntered(3);
                await GetQuantityDialog.expectNoQtyRejected();
                await layout('Once quantity block, count 3 of 12 (no reject reason asked)');
                await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 9 });
                await layout('Once quantity block after booking 3 of 12');

                // 5 needs pieces from two source TUs of 4 (one CU of 5)
                await PickLineScanScreen.nextTU();
                await book({ qty: 5, expectedQtyTarget: 4 });
                await layout('Once quantity block after a count of 5 from two source TUs');

                // line screen, "HU verpacken" reopens the block with the remembered count
                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();
                await PickingJobScreen.clickLineButton({ index: 1 });
                await PickingJobLineScreen.waitForScreen();
                await layout('Once line screen with booked TUs, before closing');
                await PickingJobLineScreen.clickPickHUButton();
                await GetQuantityDialog.waitForDialog();
                await layout('Once quantity block reopened by "HU verpacken" (remembered count)');
                await GetQuantityDialog.clickCancel();
                await GetQuantityDialog.waitToClose();
                await PickingJobLineScreen.waitForScreen(); // cancel leads back to the line screen the block was opened from

                // close short, no reject reason asked; reopen
                await PickingJobLineScreen.clickCloseButton();
                await PickingJobLineScreen.expectReOpenButtonVisible();
                await GetQuantityDialog.expectNotShown();
                await layout('Once line screen, line closed short ("Wieder oeffnen" shown)');
                await expectReachable(PickingJobLineScreen.reOpenButton(), 'Reopen button', viewport);
                await PickingJobLineScreen.clickReOpenButton();
                await PickingJobLineScreen.expectCloseButtonVisible();
                await layout('Once line screen, line reopened');
                await PickingJobLineScreen.clickCloseButton();
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
                await layout('Once job screen, line closed short (ready to complete)');
            });
        }

        for (const lang of LAYOUT_LANGUAGES) {
            // noinspection JSUnusedLocalSymbols
            test(`mixed job: Manual, Scan once and Scan each lines, switching between them [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive mixed job');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

                const formatName = uniqueFormatName();
                const masterdata = await createScanEachMasterdata({
                    packFormatName: formatName,
                    profileMode: MODE_SCAN_ONCE,
                    orders: [{ key: 'A', qty: 12 }, { key: 'B', qty: 12 }, { key: 'C', qty: 2 }],
                    customers: [
                        { customer: 'custB', cuQtyEntryMode: MODE_MANUAL },
                        { customer: 'custC', cuQtyEntryMode: MODE_SCAN_EACH, cuLabelFormatName: formatName },
                    ],
                    language: lang,
                });
                const lot = uniqueLot();
                await startJob(masterdata, { qtyToDeliver: 26 });
                await layout('mixed job screen (Once, Manual and Each line buttons)');

                // line 1: Once
                await openLine({ index: 1 });
                await layout('mixed: Once line scan screen');
                await PickLineScanScreen.openTU();
                await scanLabel(labelOf({ masterdata, lot }));
                await layout('mixed: Once quantity block');
                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();

                // line 2: Manual (own slot)
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
                await PickingJobScreen.clickLineButton({ index: 2 });
                await GetQuantityDialog.waitForDialog();
                await layout('mixed: Manual quantity dialog (looks as in a Manual-only job)');
                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();

                // line 3: Each
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot3.qrCode });
                await openScanEachLine({ index: 3, openTU: false });
                await layout('mixed: Each scan screen, no TU open');
                await PickLineScanScreen.openTU();
                await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
                await layout('mixed: Each scan screen after a CU was booked');
                await PickLineScanScreen.goBack();
                await PickingJobLineScreen.waitForScreen();
                await layout('mixed: Each line screen with a booked TU');
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
                await layout('mixed job screen after switching lines');
            });
        }

        // noinspection JSUnusedLocalSymbols
        test(`real case: Once buyer with TUs by Next TU and GRAI, Each buyer with GRAI TUs and a duplicate warning (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive real case');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const onceFormat = uniqueFormatName();
            const packFormat = uniqueFormatName();
            const masterdata = await createScanEachMasterdata({
                packFormatName: packFormat,
                profileMode: MODE_SCAN_ONCE,
                orders: [{ key: 'A', qty: 14 }, { key: 'B', qty: 4, graiRequired: 'Y' }],
                customers: [{ customer: 'custB', cuQtyEntryMode: MODE_SCAN_EACH, cuLabelFormatName: packFormat }],
                formats: [{ name: onceFormat, withPackId: false }],
                packingInstructions: { G1: { tu: 'TU_G1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true } },
            });
            const [companyPrefix, assetType] = masterdata.packingInstructions.G1.grai.split('.');
            const serialBase = Date.now();
            const graiOfTU = (tu) => `${companyPrefix}.${assetType}.${serialBase + tu}`;
            const lot = uniqueLot();
            await startJob(masterdata, { qtyToDeliver: 18 });
            await layout('job screen, two customers');

            await openLine({ index: 1 });
            await PickLineScanScreen.openTU();
            await scanLabel(labelOf({ masterdata, lot }));
            await book({ qty: 3, expectedQtyTarget: 11 });
            await PickLineScanScreen.rescanGrai(graiOfTU(3));
            await layout('Once quantity block, TU opened by a GRAI scan');
            await book({ qty: 5, expectedQtyTarget: 6 });
            await PickLineScanScreen.nextTU();
            await layout('Once quantity block, TU opened by Next TU');
            await GetQuantityDialog.typeQtyEntered(6);
            await GetQuantityDialog.clickDoneStayOpen();
            await PickingJobScreen.waitForScreen();
            await layout('job screen, buyer A line done');

            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
            await openScanEachLine({ index: 2, openTU: false });
            await PickLineScanScreen.scanGrai(graiOfTU(12));
            await layout('Each scan screen, TU opened by GRAI (GRAI customer)');
            for (const packId of [1, 2]) await scanPack(packLabelOf({ masterdata, lot, packId }));
            await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 2 }), MSG_ALREADY_PICKED(2), {
                whileToastShown: async () => await layout('Each refusal, the duplicate warning (scan screen + toast)'),
            });
            await PickLineScanScreen.scanGrai(graiOfTU(13));
            for (const packId of [3, 4]) await scanPack(packLabelOf({ masterdata, lot, packId }));
            await PickingJobScreen.waitForScreen();
            await layout('job screen, both lines done, ready to complete');
        });
    });
}
