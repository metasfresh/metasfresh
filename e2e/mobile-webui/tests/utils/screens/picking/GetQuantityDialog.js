import { test } from "../../../../playwright.config";
import { expectErrorToast, expectErrorToastIf, holdForCaptureIfEnabled, page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { expect } from "@playwright/test";
import { BarcodeScannerComponent } from "../../components/BarcodeScannerComponent";

const NAME = 'GetQuantityDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.get-qty-dialog');

export const QTY_NOT_FOUND_REASON_NOT_FOUND = 'N';
// noinspection JSUnusedGlobalSymbols
export const QTY_NOT_FOUND_REASON_DAMAGED = 'D';
export const QTY_NOT_FOUND_REASON_IGNORE = 'IgnoreReason';

export const GetQuantityDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    waitToClose: async () => await test.step(`${NAME} - Wait to close`, async () => {
        await containerElement().waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    expectQtyEntered: async (expected) => await test.step(`${NAME} - Expect QtyEntered to be '${expected}'`, async () => {
        await expect(page.locator('#qty-input')).toHaveValue(`${expected}`);
    }),

    // The quantity is shown but cannot be changed by the operator.
    expectQtyEnteredFixedAt: async (expected) => await test.step(`${NAME} - Expect QtyEntered fixed at '${expected}'`, async () => {
        const qtyInput = page.locator('#qty-input');
        await expect(qtyInput).toHaveValue(`${expected}`, { timeout: SLOW_ACTION_TIMEOUT });
        await expect(qtyInput).toBeDisabled();
    }),

    // The quantity is pre-filled and the operator can change it.
    expectQtyEnteredEditable: async (expected) => await test.step(`${NAME} - Expect QtyEntered '${expected}' and editable`, async () => {
        const qtyInput = page.locator('#qty-input');
        await expect(qtyInput).toHaveValue(`${expected}`, { timeout: SLOW_ACTION_TIMEOUT });
        await expect(qtyInput).toBeEnabled();
    }),

    typeQtyEntered: async (qty) => await test.step(`${NAME} - Type QtyEntered '${qty}'`, async () => {
        await page.locator('#qty-input').type(`${qty}`);
    }),

    expectQtyEnteredVisible: async () => await test.step(`${NAME} - Expect QtyEntered field visible`, async () => {
        await expect(page.locator('#qty-input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectCatchWeightVisible: async () => await test.step(`${NAME} - Expect CatchWeight field visible`, async () => {
        await expect(page.locator('#catch-weight')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    // The catch-weight row exists only while the line carries a catch-weight UOM, so what the
    // operator sees when the weight is not captured is the row being ABSENT, not merely hidden.
    expectCatchWeightNotVisible: async () => await test.step(`${NAME} - Expect CatchWeight field not present`, async () => {
        await expect(page.locator('#catch-weight')).toHaveCount(0);
    }),

    expectLotNoVisible: async () => await test.step(`${NAME} - Expect LotNo visible`, async () => {
        await expect(page.getByTestId('lotNo')).toBeVisible();
    }),

    expectLotNoNotVisible: async () => await test.step(`${NAME} - Expect LotNo not visible`, async () => {
        await expect(page.getByTestId('lotNo')).not.toBeVisible();
    }),

    expectBestBeforeDateVisible: async () => await test.step(`${NAME} - Expect BestBeforeDate visible`, async () => {
        await expect(page.getByTestId('bestBeforeDate')).toBeVisible();
    }),

    expectBestBeforeDateNotVisible: async () => await test.step(`${NAME} - Expect BestBeforeDate not visible`, async () => {
        await expect(page.getByTestId('bestBeforeDate')).not.toBeVisible();
    }),

    typeLotNo: async (lotNo) => await test.step(`${NAME} - Type LotNo '${lotNo}'`, async () => {
        const field = page.getByTestId('lotNo');
        // the field may already show a pre-filled or scanned lot, and it does not select its text on focus
        await field.fill('');
        await clickAndType(field, lotNo);
    }),

    typeBestBeforeDate: async (bestBeforeDate) => await test.step(`${NAME} - Type BestBeforeDate '${bestBeforeDate}'`, async () => {
        // The Best-Before field is a DateInput. With the default config
        // (mobileui.frontend.dateInput.isUseNativeComponent=N) it renders as a text input
        // expecting the DD.MM.YYYY display format; fill() sets the whole value in one event.
        const field = page.getByTestId('bestBeforeDate');
        await field.tap();
        await field.fill(bestBeforeDate);
    }),

    typeCatchWeight: async (qty) => await test.step(`${NAME} - Type CatchWeight '${qty}'`, async () => {
        // Replace `.` with locale-appropriate decimal, e.g., `,` for some regions
        const correctedQty = `${qty}`.replace('.', (1.1).toLocaleString().substring(1, 2));

        const field = page.locator('#catch-weight');
        await clickAndType(field, correctedQty);
    }),

    // Types the catch weight EXACTLY as given, one real key press per character (digits and the decimal
    // separator keystroke included), with no locale substitution: what reaches the field is what the
    // operator's keyboard sends, whatever the browser's language is.
    typeCatchWeightKeystrokes: async (keys) => await test.step(`${NAME} - Type CatchWeight keystrokes '${keys}'`, async () => {
        const field = page.locator('#catch-weight');
        // focus first, so the field selects its current text and typing overrides it
        await field.tap();
        await field.pressSequentially(`${keys}`);
    }),

    // Empties the catch weight the way an operator does: select all, Delete.
    clearCatchWeight: async () => await test.step(`${NAME} - Clear CatchWeight`, async () => {
        const field = page.locator('#catch-weight');
        await field.tap();
        await field.press('ControlOrMeta+a');
        await field.press('Delete');
        await expect(field).toHaveValue('');
    }),

    scanCatchWeightQRCode: async ({ qrCode, stepName }) => await test.step(`${NAME} - Scan ${stepName}: ${qrCode}`, async () => {
        const prevQtyTarget = await page.locator('[data-testid="qty-target"]').innerText();

        await page.getByTestId('qrCode-input').type(qrCode);
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

    // Mandatory catch-weight scan: the dialog opens on the scanner and offers neither the quantity form
    // (so nothing can be confirmed) nor the switch to manual input.
    expectOnlyCatchWeightScanOffered: async () => await test.step(`${NAME} - Expect only the catch weight scan to be offered`, async () => {
        await expect(page.getByTestId('qrCode-input')).toBeAttached({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.getByTestId('switchToManualInput-button')).toHaveCount(0);
        await expect(page.locator('#qty-input')).toHaveCount(0);
    }),

    // Mandatory catch-weight scan: a valid scan receives nothing yet, it fills the fields for review.
    scanCatchWeightQRCodeForReview: async ({ qrCode }) => await test.step(`${NAME} - Scan for review: ${qrCode}`, async () => {
        await BarcodeScannerComponent.type(qrCode);
        await expect(page.locator('#qty-input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.getByTestId('qrCode-input')).toHaveCount(0);
    }),

    // Mandatory catch-weight scan: a code that cannot be received is rejected with a message,
    // and the scanner stays open with nothing filled in.
    scanCatchWeightQRCodeExpectingError: async ({ qrCode, expectedError }) => await test.step(`${NAME} - Scan ${qrCode}, expecting error '${expectedError}'`, async () => {
        await expectErrorToast(
            expectedError,
            async () => await BarcodeScannerComponent.type(qrCode),
            ({ textContent }) => expect(textContent).toContain(expectedError)
        );
        // the closed message has left the screen (its close animation can outlive the message text)
        await expect(page.locator('.Toastify__toast')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        await GetQuantityDialog.expectOnlyCatchWeightScanOffered();
    }),

    // A piece-goods receipt is entered by hand: no scanner is offered, the quantity form shows right away.
    expectNoScanOffered: async () => await test.step(`${NAME} - Expect no scan offered`, async () => {
        await expect(page.locator('#qty-input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.getByTestId('qrCode-input')).toHaveCount(0);
        await expect(page.getByTestId('switchToQrCodeInput-button')).toHaveCount(0);
    }),

    // Back from the quantity form to the catch weight scanner.
    clickSwitchToQRCodeInput: async () => await test.step(`${NAME} - Switch to QR code input`, async () => {
        await page.getByTestId('switchToQrCodeInput-button').tap();
        await expect(page.getByTestId('qrCode-input')).toBeAttached({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Returns the lot shown in the field, e.g. a pre-filled one whose exact value the test does not know upfront.
    expectLotNoMatching: async (regex) => await test.step(`${NAME} - Expect LotNo matching ${regex}`, async () => {
        const field = page.getByTestId('lotNo');
        await expect(field).toHaveValue(regex);
        return await field.inputValue();
    }),

    expectLotNo: async (expected) => await test.step(`${NAME} - Expect LotNo '${expected}'`, async () => {
        await expect(page.getByTestId('lotNo')).toHaveValue(`${expected}`);
    }),

    // shown read-only: the receipt generates the lot
    expectLotNoDisabled: async () => await test.step(`${NAME} - Expect LotNo shown but not editable`, async () => {
        await expect(page.getByTestId('lotNo')).toBeVisible();
        await expect(page.getByTestId('lotNo')).toBeDisabled();
    }),

    expectLotNoEditable: async () => await test.step(`${NAME} - Expect LotNo editable`, async () => {
        await expect(page.getByTestId('lotNo')).toBeEnabled();
    }),

    // expected in the displayed format DD.MM.YYYY, or '' for empty
    expectBestBeforeDate: async (expected) => await test.step(`${NAME} - Expect BestBeforeDate '${expected}'`, async () => {
        await expect(page.getByTestId('bestBeforeDate')).toHaveValue(`${expected}`);
    }),

    // Like expectBestBeforeDate, accepting any of the given values (e.g. a receipt-day default when the run may cross midnight).
    expectBestBeforeDateOneOf: async (expectedValues) => await test.step(`${NAME} - Expect BestBeforeDate one of ${expectedValues.join(', ')}`, async () => {
        const anyOf = new RegExp(`^(${expectedValues.map((value) => value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')).join('|')})$`);
        await expect(page.getByTestId('bestBeforeDate')).toHaveValue(anyOf);
    }),

    // The scanner view's close button: leaves the dialog without receiving anything.
    clickCloseScanner: async () => await test.step(`${NAME} - Close scanner`, async () => {
        await page.getByTestId('done-button').tap();
        await GetQuantityDialog.waitToClose();
    }),

    expectCatchWeight: async (expected) => await test.step(`${NAME} - Expect CatchWeight '${expected}'`, async () => {
        await expect(page.locator('#catch-weight')).toHaveValue(`${expected}`);
    }),

    clickQtyNotFoundReason: async ({ reason }) => await test.step(`${NAME} - Click qty not found reason '${reason}'`, async () => {
        await page.getByTestId(`qty-reason-radio-${reason}`).tap();
    }),

    expectQtyNotFoundReason: async ({ reason }) => await test.step(`${NAME} - Expect qty not found reason '${reason}'`, async () => {
        const radioButton = page.getByTestId(`qty-reason-radio-${reason}`);
        await expect(radioButton).toBeChecked();
    }),

    expectDoneDisabled: async () => await test.step(`${NAME} - Expect Done button disabled`, async () => {
        await expect(page.getByTestId('done-button')).toBeDisabled();
    }),

    expectDoneEnabled: async () => await test.step(`${NAME} - Expect Done button enabled`, async () => {
        await expect(page.getByTestId('done-button')).toBeEnabled();
    }),

    expectQtyValidationError: async (expectedText) => await test.step(`${NAME} - Expect qty validation error '${expectedText}'`, async () => {
        await expect(page.getByTestId('qty-validation-error')).toContainText(expectedText);
    }),

    clickDone: async ({ expectedError } = {}) => await test.step(`${NAME} - Press OK`, async () => {
        let doneButton = page.getByTestId('done-button');

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

    // Taps OK/Done but does NOT wait for the dialog to close. Used when pressing Done is
    // expected to surface a follow-up dialog on top (e.g. the shelf-life RLZ confirmation),
    // which keeps this qty dialog open until that follow-up is resolved.
    clickDoneExpectingFollowupDialog: async () => await test.step(`${NAME} - Press OK (expecting follow-up dialog)`, async () => {
        await page.getByTestId('done-button').tap();
    }),

    clickCancel: async () => await test.step(`${NAME} - Press Cancel`, async () => {
        await page.getByTestId('cancel-button').tap();
        await GetQuantityDialog.expectComponentsDisabled();
        await GetQuantityDialog.waitToClose();
    }),

    clickManual: async () => await test.step(`${NAME} - Press Manual`, async () => {
        await page.getByTestId('switchToManualInput-button').tap();
        // The quantity field appearing is what tells the operator - and us - that the switch happened;
        // atm it is also the only indicator.
        await expect(page.locator('#qty-input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectComponentsDisabled: async () => await test.step(`${NAME} - Expect fields and buttons disabled`, async () => {
        await expectMissingOrDisabled(page.locator('#qty-input'));
        // await expectMissingOrDisabled(page.getByTestId('bestBeforeDate'));
        // await expectMissingOrDisabled(page.getByTestId('lotNo'));
        await expectMissingOrDisabled(page.getByTestId('done-button'));
        await expectMissingOrDisabled(page.getByTestId('cancel-button'));
        await expectMissingOrDisabled(page.getByTestId('confirmDoneAndCloseTarget-button'));
    }),

    fillAndPressDone: async ({ switchToManualInput, expectQtyInputVisible, expectCatchWeightVisible, expectQtyEntered, qtyEntered, lotNo, bestBeforeDate, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason, expectedError }) => await test.step(`${NAME} - Fill dialog`, async () => {
        await GetQuantityDialog.waitForDialog();

        // run this first!
        if (switchToManualInput) {
            await GetQuantityDialog.clickManual();
        }

        if (expectQtyInputVisible) {
            await GetQuantityDialog.expectQtyEnteredVisible();
        }
        if (expectCatchWeightVisible != null) {
            if (expectCatchWeightVisible) {
                await GetQuantityDialog.expectCatchWeightVisible();
            } else {
                await GetQuantityDialog.expectCatchWeightNotVisible();
            }
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

        await GetQuantityDialog.clickDone({ expectedError });
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