import { test } from "../../../../playwright.config";
import { expectErrorToast, expectErrorToastIf, holdForCaptureIfEnabled, page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { expect } from "@playwright/test";
import { BarcodeScannerComponent } from "../../components/BarcodeScannerComponent";
import { expectInsideScreen, measureButtonRow, removeNextTUIconForReference, saveScreenshot } from "../../layout";

const NAME = 'GetQuantityDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.get-qty-dialog');

export const QTY_NOT_FOUND_REASON_NOT_FOUND = 'N';
// noinspection JSUnusedGlobalSymbols
export const QTY_NOT_FOUND_REASON_DAMAGED = 'D';
export const QTY_NOT_FOUND_REASON_IGNORE = 'IgnoreReason';

export const GetQuantityDialog = {
    /** The dialog element; for layout checks (see utils/layout.js) */
    container: () => containerElement(),

    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    waitToClose: async () => await test.step(`${NAME} - Wait to close`, async () => {
        await containerElement().waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts the dialog is not on screen */
    expectNotShown: async () => await test.step(`${NAME} - Expect dialog not shown`, async () => {
        await expect(containerElement()).toHaveCount(0);
    }),

    /** Asserts the open quantity shown by the dialog (`qty-target`, raw number) */
    expectQtyTarget: async (expected) => await test.step(`${NAME} - Expect qty target '${expected}'`, async () => {
        await expect(page.getByTestId('qty-target')).toBeVisible();
        await expect(page.getByTestId('qty-target')).toHaveAttribute('data-internalvalue', `${expected}`, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts that no rejected quantity / reject reason is shown (Scan once */
    expectNoQtyRejected: async () => await test.step(`${NAME} - Expect no rejected qty`, async () => {
        await expect(page.locator('#qty-rejected')).toHaveCount(0);
    }),

    /** Presses OK in Scan once and stays */
    clickDoneStayOpen: async ({ expectedQtyTarget } = {}) => await test.step(`${NAME} - Press OK, stay on the line`, async () => {
        await GetQuantityDialog.doneButton().tap();
        if (expectedQtyTarget != null) {
            await GetQuantityDialog.expectQtyTarget(expectedQtyTarget);
        }
    }),

    /** Presses OK where the backend must refuse the booking */
    clickDoneRefused: async ({ expectedError, whileToastShown }) => await test.step(`${NAME} - Press OK, expect refusal '${expectedError}'`, async () => {
        await expectErrorToast(
            `${NAME} refusal`,
            async () => {
                await GetQuantityDialog.doneButton().tap();
            },
            async ({ textContent }) => {
                expect(textContent).toContain(expectedError);
                await whileToastShown?.();
            }
        );
        await GetQuantityDialog.waitForDialog();
    }),

    expectQtyEntered: async (expected) => await test.step(`${NAME} - Expect QtyEntered to be '${expected}'`, async () => {
        await expect(GetQuantityDialog.qtyInputField()).toHaveValue(`${expected}`);
    }),

    expectUserInfoValue: async ({ captionKey, expectedValue }) => await test.step(`${NAME} - Expect ${captionKey} to contain '${expectedValue}'`, async () => {
        const testId = `userInfo_${captionKey}`;
        await expect(page.getByTestId(testId)).toContainText(expectedValue);
    }),

    typeQtyEntered: async (qty) => await test.step(`${NAME} - Type QtyEntered '${qty}'`, async () => {
        await GetQuantityDialog.qtyInputField().fill(`${qty}`);
    }),

    expectLotNoVisible: async () => await test.step(`${NAME} - Expect LotNo visible`, async () => {
        await expect(GetQuantityDialog.lotNoField()).toBeVisible();
    }),

    expectLotNoNotVisible: async () => await test.step(`${NAME} - Expect LotNo not visible`, async () => {
        await expect(GetQuantityDialog.lotNoField()).not.toBeVisible();
    }),

    /** Asserts the value of the LotNo field */
    expectLotNoValue: async (expected) => await test.step(`${NAME} - Expect LotNo to be '${expected}'`, async () => {
        await expect(GetQuantityDialog.lotNoField()).toHaveValue(expected);
    }),

    /** The message box: the whole pop-up as drawn */
    messageBox: () => containerElement().locator('.message'),
    /** The table area of the dialog (the part that shrinks and scrolls inside) */
    tableArea: () => containerElement().locator('.table-container'),
    packingName: () => containerElement().locator('#packing-name'),
    firstLabel: () => containerElement().locator('th').first(),

    /** Boxes (Playwright boundingBox) of the quantity field, OK, Cancel and the table area of the dialog */
    measureBoxes: async () => ({
        field: await GetQuantityDialog.qtyInputField().boundingBox(),
        ok: await GetQuantityDialog.doneButton().boundingBox(),
        cancel: await GetQuantityDialog.cancelButton().boundingBox(),
        tableArea: await GetQuantityDialog.tableArea().boundingBox(),
    }),
    /** Sideways scroll of the table area, how much too wide its content is, and where each label starts relative to the area's left edge */
    measureTableAreaSideways: async () => await GetQuantityDialog.tableArea().evaluate((area) => {
        const areaBox = area.getBoundingClientRect();
        const labelLefts = [...area.querySelectorAll('th')].map((th) => Math.round(th.getBoundingClientRect().left - areaBox.left));
        return { scrollLeft: area.scrollLeft, tooWide: area.scrollWidth - area.clientWidth, labelLefts };
    }),
    /** True when the rows are taller than the table area: it scrolls inside */
    isTableAreaScrolling: async () => await GetQuantityDialog.tableArea().evaluate((area) => area.scrollHeight > area.clientHeight),
    /** Boxes and font size of the given buttons of the dialog (by data-testid) */
    measureButtonRow: async (testIds) => await measureButtonRow(containerElement(), testIds),
    /** Reference layout for a check: the barcode icon of the Next TU button of the dialog removed */
    removeNextTUIconForReference: async () => await removeNextTUIconForReference(containerElement()),

    /** Box (Playwright boundingBox) and the styles that decide the look of the buttons OK, Cancel and Next TU of the dialog */
    measureButtons: async () => await test.step(`${NAME} - Measure OK, Cancel and Next TU buttons`, async () => {
        const read = async (testId) => {
            const button = containerElement().getByTestId(testId);
            const box = await button.boundingBox();
            const style = await button.evaluate((el) => {
                const cs = getComputedStyle(el);
                return { fontSize: cs.fontSize, fontFamily: cs.fontFamily, lineHeight: cs.lineHeight, paddingTop: cs.paddingTop, paddingBottom: cs.paddingBottom, borderTopWidth: cs.borderTopWidth };
            });
            return { ...box, ...style };
        };
        return { ok: await read('done-button'), cancel: await read('cancel-button'), nextTU: await read('next-tu-button') };
    }),

    expectBestBeforeDateVisible: async () => await test.step(`${NAME} - Expect BestBeforeDate visible`, async () => {
        await expect(GetQuantityDialog.bestBeforeDateField()).toBeVisible();
    }),

    expectBestBeforeDateNotVisible: async () => await test.step(`${NAME} - Expect BestBeforeDate not visible`, async () => {
        await expect(GetQuantityDialog.bestBeforeDateField()).not.toBeVisible();
    }),

    typeLotNo: async (lotNo) => await test.step(`${NAME} - Type LotNo '${lotNo}'`, async () => {
        const field = GetQuantityDialog.lotNoField();
        await clickAndType(field, lotNo);
    }),

    typeBestBeforeDate: async (bestBeforeDate) => await test.step(`${NAME} - Type BestBeforeDate '${bestBeforeDate}'`, async () => {
        // The Best-Before field is a DateInput. With the default config
        // (mobileui.frontend.dateInput.isUseNativeComponent=N) it renders as a text input
        // expecting the DD.MM.YYYY display format; fill() sets the whole value in one event.
        const field = GetQuantityDialog.bestBeforeDateField();
        await field.tap();
        await field.fill(bestBeforeDate);
    }),

    typeCatchWeight: async (qty) => await test.step(`${NAME} - Type CatchWeight '${qty}'`, async () => {
        // Replace `.` with locale-appropriate decimal, e.g., `,` for some regions
        const correctedQty = `${qty}`.replace('.', (1.1).toLocaleString().substring(1, 2));

        const field = page.locator('#catch-weight');
        await clickAndType(field, correctedQty);
    }),

    scanCatchWeightQRCode: async ({ qrCode, stepName }) => await test.step(`${NAME} - Scan ${stepName}: ${qrCode}`, async () => {
        const prevQtyTarget = await page.locator('[data-testid="qty-target"]').innerText();

        await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: qrCode, testId: 'qrCode-input' });
        await page.locator('[data-testid="qrCode-input"]').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        await page.waitForFunction((prevQtyTarget) => {
            const el = document.querySelector('[data-testid="qty-target"]');
            return el && el.textContent !== prevQtyTarget;
        }, prevQtyTarget);

        await page.waitForFunction(
            () => {
                const el = document.querySelector('[data-testid="qrCode-input"]');
                return el && el.value === '';
            });
    }),

    clickQtyNotFoundReason: async ({ reason }) => await test.step(`${NAME} - Click qty not found reason '${reason}'`, async () => {
        await page.getByTestId(`qty-reason-radio-${reason}`).tap();
    }),

    expectQtyNotFoundReason: async ({ reason }) => await test.step(`${NAME} - Expect qty not found reason '${reason}'`, async () => {
        const radioButton = page.getByTestId(`qty-reason-radio-${reason}`);
        await expect(radioButton).toBeChecked();
    }),

    expectDoneDisabled: async () => await test.step(`${NAME} - Expect Done button disabled`, async () => {
        await expect(GetQuantityDialog.doneButton()).toBeDisabled();
    }),

    expectDoneEnabled: async () => await test.step(`${NAME} - Expect Done button enabled`, async () => {
        await expect(GetQuantityDialog.doneButton()).toBeEnabled();
    }),

    expectQtyValidationError: async (expectedText) => await test.step(`${NAME} - Expect qty validation error '${expectedText}'`, async () => {
        await expect(page.getByTestId('qty-validation-error')).toContainText(expectedText);
    }),

    /** The quantity (count) field, looked up inside the dialog */
    qtyInputField: () => containerElement().locator('#qty-input'),

    /** The LotNo field, looked up inside the dialog */
    lotNoField: () => containerElement().getByTestId('lotNo'),

    /** The best-before date field, looked up inside the dialog */
    bestBeforeDateField: () => containerElement().getByTestId('bestBeforeDate'),

    /** The Cancel button, looked up inside the dialog */
    cancelButton: () => containerElement().getByTestId('cancel-button'),

    /** The "Weiteres TU" (next TU) button, looked up inside the dialog */
    nextTUButton: () => containerElement().getByTestId('next-tu-button'),

    /** The open quantity shown by the dialog (`qty-target`) */
    qtyTargetElement: () => page.getByTestId('qty-target'),

    /** The first row of the dialog's table (the one with the target quantity) */
    firstRow: () => page.locator('tr').filter({ has: page.getByTestId('qty-target') }).first(),

    /** The OK button, looked up inside the dialog; for layout checks (see utils/layout.js) */
    doneButton: () => containerElement().getByTestId('done-button'),

    /** The "OK und LU schließen" button; for layout checks */
    doneAndCloseTargetButton: () => page.getByTestId('confirmDoneAndCloseTarget-button'),

    /** The "switch to manual input" button of a catch weight product; for layout checks */
    switchToManualInputButton: () => page.getByTestId('switchToManualInput-button'),

    /** The catch weight field of the manual input; for layout checks */
    catchWeightField: () => page.locator('#catch-weight'),

    expectSwitchToManualInputVisible: async () => await test.step(`${NAME} - Expect switch to manual input button`, async () => {
        await expect(GetQuantityDialog.switchToManualInputButton()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Taps OK without waiting for the dialog to close (a prompt or a refusal may follow) */
    tapDone: async () => await test.step(`${NAME} - Tap OK`, async () => {
        await GetQuantityDialog.doneButton().tap();
    }),

    /** Asserts the validation text under the quantity field is shown */
    expectQtyValidationErrorVisible: async () => await test.step(`${NAME} - Expect qty validation error shown`, async () => {
        await expect(page.getByTestId('qty-validation-error')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Cuts the screen height to 55 % (an on-screen keyboard is open), taps the quantity field and asserts that
     * quantity field, OK and Cancel can still be reached
     */
    expectReachableWithKeyboard: async (label, viewport) => await test.step(`${NAME} - Expect ${label} reachable with keyboard`, async () => {
        const smaller = { width: viewport.width, height: Math.round(viewport.height * 0.55) };
        await page.setViewportSize(smaller);
        const qtyInput = GetQuantityDialog.qtyInputField();
        await qtyInput.tap();
        for (const [testId, name] of [['done-button', 'OK'], ['cancel-button', 'Cancel']]) {
            const button = containerElement().getByTestId(testId);
            await button.evaluate((el) => el.scrollIntoView({ block: 'center' }));
            await expectInsideScreen(button, `${label} ${name} button with keyboard`, smaller);
        }
        await qtyInput.evaluate((el) => el.scrollIntoView({ block: 'center' }));
        await expectInsideScreen(qtyInput, `${label} quantity field with keyboard`, smaller);
        await saveScreenshot(`${viewport.width}x${smaller.height}-${label}-with-keyboard`);
    }),

    clickDone: async ({ expectedError } = {}) => await test.step(`${NAME} - Press OK`, async () => {
        let doneButton = GetQuantityDialog.doneButton();

        await expectErrorToastIf(
            !!expectedError,
            `${expectedError}`,
            async () => {
                await doneButton.tap();
                await GetQuantityDialog.expectComponentsDisabled();
                await GetQuantityDialog.waitToClose();
            },
            ({ textContent }) => expect(textContent).toContain(expectedError)
        );
    }),

    clickDoneAndCloseTarget: async ({ expectedError } = {}) => await test.step(`${NAME} - Press OK und LU schließen`, async () => {
        const doneAndCloseButton = page.getByTestId('confirmDoneAndCloseTarget-button');

        await expectErrorToastIf(
            !!expectedError,
            `${expectedError}`,
            async () => {
                await doneAndCloseButton.tap();
                await GetQuantityDialog.expectComponentsDisabled();
                await GetQuantityDialog.waitToClose();
            },
            ({ textContent }) => expect(textContent).toContain(expectedError)
        );
    }),

    clickCancel: async () => await test.step(`${NAME} - Press Cancel`, async () => {
        await GetQuantityDialog.cancelButton().tap();
        await GetQuantityDialog.expectComponentsDisabled();
        await GetQuantityDialog.waitToClose();
    }),

    clickManual: async () => await test.step(`${NAME} - Press Manual`, async () => {
        await page.getByTestId('switchToManualInput-button').tap();
        await GetQuantityDialog.qtyInputField().waitFor({ timeout: SLOW_ACTION_TIMEOUT }); // atm that's the only indicator that we switched to manual input
    }),

    expectComponentsDisabled: async () => await test.step(`${NAME} - Expect fields and buttons disabled`, async () => {
        await expectMissingOrDisabled(GetQuantityDialog.qtyInputField());
        // await expectMissingOrDisabled(GetQuantityDialog.bestBeforeDateField());
        // await expectMissingOrDisabled(GetQuantityDialog.lotNoField());
        await expectMissingOrDisabled(GetQuantityDialog.doneButton());
        await expectMissingOrDisabled(GetQuantityDialog.cancelButton());
        await expectMissingOrDisabled(page.getByTestId('confirmDoneAndCloseTarget-button'));
    }),

    fillAndPressDone: async ({ switchToManualInput, expectQtyEntered, qtyEntered, lotNo, bestBeforeDate, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason, expectedError, closeTarget = false }) => await test.step(`${NAME} - Fill dialog`, async () => {
        await GetQuantityDialog.waitForDialog();

        // run this first!
        if (switchToManualInput) {
            await GetQuantityDialog.clickManual();
        }

        if (expectQtyEntered != null) {
            await GetQuantityDialog.expectQtyEntered(expectQtyEntered);
        }
        if (qtyEntered != null) {
            await GetQuantityDialog.typeQtyEntered(qtyEntered);
        }
        if (lotNo != null) {
            await GetQuantityDialog.typeLotNo(lotNo);
        }
        if (bestBeforeDate != null) {
            await GetQuantityDialog.typeBestBeforeDate(bestBeforeDate);
        }
        if (catchWeight != null) {
            await GetQuantityDialog.typeCatchWeight(catchWeight);
        }
        if (catchWeightQRCode != null) {
            const qrCodesArray = Array.isArray(catchWeightQRCode) ? catchWeightQRCode : [catchWeightQRCode];
            const length = qrCodesArray.length;
            for (let idx = 0; idx < length; idx++) {
                const qrCode = qrCodesArray[idx];
                await GetQuantityDialog.scanCatchWeightQRCode({ qrCode, stepName: `#${idx + 1}/${length}` });
            }
        }
        if (expectQtyNotFoundReason != null) {
            await GetQuantityDialog.expectQtyNotFoundReason({ reason: expectQtyNotFoundReason });
        }
        if (qtyNotFoundReason != null) {
            await GetQuantityDialog.clickQtyNotFoundReason({ reason: qtyNotFoundReason });
        }

        // Capture mode only (UAT_CAPTURE): hold the filled dialog on the recorder for a few frames
        // so the entered values are captured before OK closes it. No-op / full speed otherwise.
        await holdForCaptureIfEnabled();

        if (closeTarget) {
            await GetQuantityDialog.clickDoneAndCloseTarget({ expectedError });
        } else {
            await GetQuantityDialog.clickDone({ expectedError });
        }
    }),
};

//
//
//
//
//

const expectMissingOrDisabled = async (locator) => {
    // Element should either not exist (dialog already closed) or be disabled (dialog closing).
    // Race condition: count() > 0 may be true, but by the time toBeDisabled() runs the dialog
    // may have unmounted. In that case, re-check count — if 0, element is gone (OK).
    if (await locator.count() > 0) {
        try {
            await expect(locator).toBeDisabled();
        } catch (e) {
            if (await locator.count() === 0) return;
            throw e;
        }
    }
};

const clickAndType = async (field, value) => {
    // Tap the field before, to gain focus so our code will react and select all text
    await field.tap();
    // ... so when typing, we will actually override the current value
    await field.type(value);

    // cannot check in case of qtys, because we set "0,789" but we get "0.789"
    // const enteredValue = await field.inputValue();
    // if (enteredValue !== value) {
    //     throw new Error(`Expected value '${value}', but got '${enteredValue}'`);
    // }
}
