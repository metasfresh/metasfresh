import { test, testContext } from "../../../../playwright.config";
import { expectErrorToastIf, holdForCaptureIfEnabled, page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, BARCODE_HOOK_FLUSH_MS } from "../../common";
import { expect } from "@playwright/test";
import { BarcodeScannerComponent } from "../../components/BarcodeScannerComponent";
import { ErrorToast } from "../../dialogs/ErrorToast";

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

    expectUserInfoValue: async ({ captionKey, expectedValue }) => await test.step(`${NAME} - Expect ${captionKey} to contain '${expectedValue}'`, async () => {
        const testId = `userInfo_${captionKey}`;
        await expect(page.getByTestId(testId)).toContainText(expectedValue);
    }),

    typeQtyEntered: async (qty) => await test.step(`${NAME} - Type QtyEntered '${qty}'`, async () => {
        await page.locator('#qty-input').fill(`${qty}`);
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

    // ---------------------------------------------------------------------------------------------
    // Generic editable-attributes section (EditableAttributesSection). Used by the mfg receive dialog,
    // where Lot / Best-before / any configured attribute render as attr-<code>-field inside
    // editable-attributes-section - NOT via the picking-only dedicated lotNo/bestBeforeDate rows above.
    //
    // Every method below accepts EITHER a raw M_Attribute code (e.g. a well-known system attribute like
    // 'Lot-Nummer' / 'HU_BestBeforeDate') OR a masterdata identifier (a createMasterdata `attributes`
    // map-key, e.g. 'sizeAttr'). A known identifier resolves to its response-reported per-run code
    // (masterdata.attributes.<id>.attributeValue); anything else falls back to the raw code. This is the
    // single resolution point, so specs reference attributes by identifier and never hardcode a per-run
    // Value string. See resolveAttributeCode() at the bottom of this file.
    // ---------------------------------------------------------------------------------------------
    expectEditableAttributeVisible: async (codeOrIdentifier) => await test.step(`${NAME} - Expect editable attribute '${codeOrIdentifier}' visible`, async () => {
        const code = resolveAttributeCode(codeOrIdentifier);
        await expect(page.getByTestId(`attr-${code}-field`)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectEditableAttributeNotVisible: async (codeOrIdentifier) => await test.step(`${NAME} - Expect editable attribute '${codeOrIdentifier}' not visible`, async () => {
        const code = resolveAttributeCode(codeOrIdentifier);
        await expect(page.getByTestId(`attr-${code}-field`)).not.toBeVisible();
    }),

    typeEditableAttribute: async (codeOrIdentifier, value) => await test.step(`${NAME} - Type editable attribute '${codeOrIdentifier}' = '${value}'`, async () => {
        const field = page.getByTestId(`attr-${resolveAttributeCode(codeOrIdentifier)}-field`);
        await clickAndType(field, value);
    }),

    // Date-type editable attribute (renders the DateInput component). Same DD.MM.YYYY display-format
    // contract as the dedicated best-before field; fill() sets the whole value in one event.
    typeEditableAttributeDate: async (codeOrIdentifier, value) => await test.step(`${NAME} - Type editable date attribute '${codeOrIdentifier}' = '${value}'`, async () => {
        const field = page.getByTestId(`attr-${resolveAttributeCode(codeOrIdentifier)}-field`);
        await field.tap();
        await field.fill(value);
    }),

    // LIST-type editable attribute (renders a native <select>). `value` is the M_AttributeValue.Value code.
    selectEditableAttribute: async (codeOrIdentifier, value) => await test.step(`${NAME} - Select editable attribute '${codeOrIdentifier}' = '${value}'`, async () => {
        const field = page.getByTestId(`attr-${resolveAttributeCode(codeOrIdentifier)}-field`);
        // The frontend holds the operator's selection across a background reload, so a single select
        // and verify is enough - the option the operator picked must be the one the control holds.
        await field.selectOption(value);
        await expect(field).toHaveValue(value);
    }),

    expectEditableAttributesSectionVisible: async () => await test.step(`${NAME} - Expect editable-attributes section visible`, async () => {
        await expect(page.getByTestId('editable-attributes-section')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectEditableAttributesSectionNotVisible: async () => await test.step(`${NAME} - Expect editable-attributes section not visible`, async () => {
        await expect(page.getByTestId('editable-attributes-section')).toHaveCount(0);
    }),

    // Asserts the rendered order of the editable-attributes section's rows (attr-<code>-row) equals the
    // given order — proves SeqNo (config order), not e.g. alphabetical, drives field order. Each entry may
    // be a raw code or a masterdata identifier (resolved to its per-run code, same as the methods above).
    expectEditableAttributesOrder: async (codesOrIdentifiersInOrder) => await test.step(`${NAME} - Expect editable attributes in order '${codesOrIdentifiersInOrder.join(', ')}'`, async () => {
        const expectedCodes = codesOrIdentifiersInOrder.map(resolveAttributeCode);
        const rows = page.locator('[data-testid="editable-attributes-section"] [data-testid$="-row"]');
        const actualTestIds = await rows.evaluateAll((elements) => elements.map((el) => el.getAttribute('data-testid')));
        const actualCodes = actualTestIds.map((testId) => testId.replace(/^attr-/, '').replace(/-row$/, ''));
        expect(actualCodes).toEqual(expectedCodes);
    }),

    expectSerialNoScanButtonVisible: async () => await test.step(`${NAME} - Expect SerialNo scan button visible`, async () => {
        await expect(page.getByTestId('serialNo-scan-button')).toBeVisible();
    }),

    expectSerialNoNotVisible: async () => await test.step(`${NAME} - Expect SerialNo controls not visible`, async () => {
        await expect(page.getByTestId('serialNo-scan-button')).not.toBeVisible();
        await expect(page.getByTestId('serialNo-scan-again-button')).not.toBeVisible();
        await expect(page.getByTestId('serialNo-count')).not.toBeVisible();
    }),

    // "X of N scanned" progress text in the qty dialog's serial row.
    expectSerialNoCount: async ({ scanned, total }) => await test.step(`${NAME} - Expect SerialNo count '${scanned} of ${total}'`, async () => {
        await expect(page.getByTestId('serialNo-count')).toContainText(`${scanned} of ${total}`, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectSerialNoChipCount: async (expectedCount) => await test.step(`${NAME} - Expect ${expectedCount} SerialNo chip(s)`, async () => {
        await expect(page.getByTestId('serialNo-chip')).toHaveCount(expectedCount, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Opens the live multi-scan sub-view, scans each serial (asserting the chip count rises between
    // scans so the keyboard hook flushes each barcode), then taps Done to return to the qty dialog.
    scanSerialNos: async (serialNos) => await test.step(`${NAME} - Scan ${serialNos.length} SerialNo(s)`, async () => {
        const reScan = await page.getByTestId('serialNo-scan-again-button').count() > 0
            && await page.getByTestId('serialNo-scan-again-button').isVisible();
        await page.getByTestId(reScan ? 'serialNo-scan-again-button' : 'serialNo-scan-button').tap();
        // Wait for the scan sub-view to mount (keyboard hook active) before dispatching keystrokes.
        await expect(page.getByTestId('serialNo-scan-done-button')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        const before = await page.getByTestId('serialNo-chip').count();
        for (let i = 0; i < serialNos.length; i++) {
            await BarcodeScannerComponent.type({ scannedCode: serialNos[i] });
            // assertion between scans → lets the interval flush process each barcode (avoids concat)
            await expect(page.getByTestId('serialNo-chip')).toHaveCount(before + i + 1, { timeout: SLOW_ACTION_TIMEOUT });
        }
        await page.getByTestId('serialNo-scan-done-button').tap();
    }),

    // Scans a serial that is already present; asserts the chip count does NOT change (silent dedup).
    scanDuplicateSerialNo: async (serialNo) => await test.step(`${NAME} - Scan duplicate SerialNo '${serialNo}'`, async () => {
        const reScan = await page.getByTestId('serialNo-scan-again-button').count() > 0
            && await page.getByTestId('serialNo-scan-again-button').isVisible();
        await page.getByTestId(reScan ? 'serialNo-scan-again-button' : 'serialNo-scan-button').tap();
        await expect(page.getByTestId('serialNo-scan-done-button')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        const before = await page.getByTestId('serialNo-chip').count();
        await BarcodeScannerComponent.type({ scannedCode: serialNo });
        // give the hook time to flush, then assert the count is unchanged (dedup)
        await page.waitForTimeout(BARCODE_HOOK_FLUSH_MS);
        await expect(page.getByTestId('serialNo-chip')).toHaveCount(before);
        await page.getByTestId('serialNo-scan-done-button').tap();
    }),

    typeLotNo: async (lotNo) => await test.step(`${NAME} - Type LotNo '${lotNo}'`, async () => {
        const field = page.getByTestId('lotNo');
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

    expectQtyNotFoundReasonOffered: async ({ reason, offered = true }) => await test.step(`${NAME} - Expect qty not found reason '${reason}' offered=${offered}`, async () => {
        const radioButton = page.getByTestId(`qty-reason-radio-${reason}`);
        if (offered) {
            await expect(radioButton).toBeVisible();
        } else {
            await expect(radioButton).toHaveCount(0);
        }
    }),

    /**
     * `offered: false` on a single reason (above) passes even if only that one reason were filtered
     * out of an otherwise-rendered group. Use this alongside it when the scenario expects the WHOLE
     * reason group to be absent (e.g. a zero-target step, where the group only renders once
     * `qtyRejected > 0` — `GetQuantityDialog.jsx`), so the test documents "no reasons at all" rather
     * than "at least this one is filtered".
     */
    expectQtyRejectedReasonsGroupVisible: async ({ visible }) => await test.step(`${NAME} - Expect qty-rejected-reasons group visible=${visible}`, async () => {
        const group = page.locator('#qty-rejected');
        if (visible) {
            await expect(group).toBeVisible();
        } else {
            await expect(group).toHaveCount(0);
        }
    }),

    expectQtyNotFoundReasonCaption: async ({ reason, caption }) => await test.step(`${NAME} - Expect qty not found reason '${reason}' caption '${caption}'`, async () => {
        const label = page.getByTestId(`qty-reason-radio-${reason}`).locator('xpath=..');
        await expect(label).toContainText(caption);
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

    // Taps OK/Done but does NOT wait for the dialog to close. Used when pressing Done is
    // expected to surface a follow-up dialog on top (e.g. the shelf-life RLZ confirmation),
    // which keeps this qty dialog open until that follow-up is resolved.
    clickDoneExpectingFollowupDialog: async () => await test.step(`${NAME} - Press OK (expecting follow-up dialog)`, async () => {
        await page.getByTestId('done-button').tap();
    }),

    /**
     * Presses Done for a submit the BACKEND is expected to refuse, and asserts the refusal exactly as
     * the mobile UI actually renders it: the error toast carrying the server message, with the dialog
     * left open underneath (nothing was booked, so the operator stays on the step).
     *
     * Deliberately NOT `clickDone({ expectedError })`: that helper's own flow requires the dialog to
     * CLOSE and then dismisses the toast via `.Toastify__close-button--error`. Neither is possible for
     * a server-side refusal on this screen — the dialog stays mounted, and a metasfresh
     * `AdempiereException` toast renders its full "Additional parameters: ..." dump, which on the
     * mobile viewport is taller than the screen, so the toast's close button sits outside the viewport
     * and `tap()` never becomes actionable ("element is outside of the viewport").
     *
     * The toast is therefore left standing: it covers the whole screen, so no further UI interaction
     * is possible after this call — assert the backend state and end the test.
     *
     * While the toast stands, any LATER call wrapped in `common.step()` (the exported `step()`, via
     * `runAndWatchForErrors`) throws "Unexpected error toast detected" the moment it starts watching —
     * so callers must use plain `test.step` calls after this one, exactly as `Backend.expect` does
     * (`screens/Backend.js`), never `step()`.
     */
    clickDoneExpectingBackendRefusal: async ({ expectedError }) => await test.step(`${NAME} - Press OK, expecting the backend to refuse with '${expectedError}'`, async () => {
        await page.getByTestId('done-button').tap();

        await expect(ErrorToast.locator())
            .toContainText(expectedError, { timeout: VERY_SLOW_ACTION_TIMEOUT });

        // Nothing was booked, so the operator is not navigated away.
        await expect(containerElement()).toBeVisible();
    }),

    /**
     * Presses Done and captures the POST request body it fires (matched by a URL substring) — for
     * asserting a wire-level field with no visible UI counterpart (e.g.
     * `issueTo.huWeightGrossBeforeIssue` on the manufacturing issue event).
     * @returns {Promise<object>} the parsed JSON request body.
     */
    clickDoneAndCaptureRequestBody: async ({ urlFragment }) => await test.step(`${NAME} - Press OK (capture request '${urlFragment}')`, async () => {
        const [request] = await Promise.all([
            page.waitForRequest((req) => req.url().includes(urlFragment) && req.method() === 'POST'),
            GetQuantityDialog.clickDone(),
        ]);
        return request.postDataJSON();
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

    fillAndPressDone: async ({ switchToManualInput, expectQtyInputVisible, expectCatchWeightVisible, expectQtyEntered, qtyEntered, lotNo, bestBeforeDate, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason, expectedError, closeTarget = false }) => await test.step(`${NAME} - Fill dialog`, async () => {
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

// Resolves a code-or-identifier to the actual M_Attribute code used in the attr-<code>-field testId.
// A masterdata identifier (a createMasterdata `attributes` map-key registered in the last response)
// resolves to its per-run Value (masterdata.attributes.<id>.attributeValue); anything else - notably a
// well-known system code such as 'Lot-Nummer' or 'HU_BestBeforeDate' - is returned unchanged.
const resolveAttributeCode = (codeOrIdentifier) =>
    testContext.lastMasterdata?.attributes?.[codeOrIdentifier]?.attributeValue ?? codeOrIdentifier;

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
