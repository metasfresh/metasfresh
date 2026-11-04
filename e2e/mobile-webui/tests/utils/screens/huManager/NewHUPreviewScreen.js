import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT, ID_BACK_BUTTON } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'NewHUPreviewScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#NewHUPreviewScreen');
const changeLocatorDialog = () => page.getByTestId('ChangeCurrentLocatorDialog');

const expectRowText = async ({ label, testId, expectedValue }) => await test.step(`${NAME} - Expect ${label} to be "${expectedValue}"`, async () => {
    await expect(containerElement().getByTestId(testId)).toHaveText(expectedValue);
});

/** The new HU as the server answered the create call (same JSON as scanning an existing HU: id + qrCode) */
const toCreatedHU = async (response) => {
    const hu = (await response.json()).result; // JsonGetSingleHUResponse { result: JsonHU }
    // displayName = M_HU.Value + the packing instruction in brackets (HUDisplayNameBuilder.build()); the toast shows the display name
    return { huId: hu.id, huQRCode: hu.qrCode?.code, huValue: hu.displayName.split(' ')[0], displayName: hu.displayName, locatorValue: hu.locatorValue };
};
const waitForCreateResponse = () => page.waitForResponse((r) => r.url().includes('createFromScannedCode') && r.request().method() === 'POST', { timeout: SLOW_ACTION_TIMEOUT });

/** counts the POSTs to the given endpoints (url fragments) from now on */
const countPostCalls = (...urlParts) => {
    const counter = { count: 0 };
    page.on('request', (r) => {
        if (r.method() === 'POST' && urlParts.some((urlPart) => r.url().includes(urlPart))) counter.count++;
    });
    return counter;
};

/**
 * The "New HU" preview of the HU Manager: shown after a scanned product label found no existing HU.
 * One expect function per row (product, quantity, location, lot number, best-before date, production date, weight); specs never see the test ids.
 * Location: button change-locator-button opens the dialog ChangeCurrentLocatorDialog (scan a location; the preview has no scanner of its own).
 * Quantity: an inline field (qty-input) in the quantity row; the unit is shown in the field. Buttons: ok-button, cancel-button.
 */
export const NewHUPreviewScreen = {
    /** counts the create calls (OK click) from now on: `{ count }` */
    countCreateCalls: () => countPostCalls('createFromScannedCode'),

    /** counts the propose calls (preview of a scanned label) from now on: `{ count }` */
    countProposeCalls: () => countPostCalls('proposeFromScannedCode'),

    /** counts the propose and the create calls (preview and OK click) from now on: `{ count }` */
    countProposeAndCreateCalls: () => countPostCalls('proposeFromScannedCode', 'createFromScannedCode'),

    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectNotDisplayed: async () => await test.step(`${NAME} - Expect screen NOT to be displayed`, async () => {
        await expect(containerElement()).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    /** The product row contains the product name (the row may carry more than the name, e.g. the product value). */
    expectProductContains: async (productName) => await test.step(`${NAME} - Expect the product to contain "${productName}"`, async () => {
        await expect(containerElement().getByTestId('product-value')).toContainText(productName);
    }),

    /** The location (the code of the locator) shown on the preview. */
    expectLocator: async (locatorCode) => await expectRowText({ label: 'location', testId: 'locator-value', expectedValue: locatorCode }),

    /** The lot number row is exactly the given text. */
    expectLotNumber: async (lot) => await expectRowText({ label: 'lot number', testId: 'attribute-Lot-Nummer-value', expectedValue: lot }),

    /** The best-before date (MHD) row is exactly the given display text (a date as the screen renders it: en_US MM/DD/YYYY). */
    expectBestBeforeDate: async (displayText) => await expectRowText({ label: 'best-before date', testId: 'attribute-HU_BestBeforeDate-value', expectedValue: displayText }),

    /** The production date row is exactly the given display text (a date as the screen renders it: en_US MM/DD/YYYY). */
    expectProductionDate: async (displayText) => await expectRowText({ label: 'production date', testId: 'attribute-ProductionDate-value', expectedValue: displayText }),

    /** The label weight row, e.g. '12.345 KGM'. */
    expectWeight: async (weightText) => await expectRowText({ label: 'weight', testId: 'weight-value', expectedValue: weightText }),

    /** The weight row tells that the weight on the label is not used for this product. */
    expectWeightNotUsed: async () => await expectRowText({ label: 'weight', testId: 'weight-value', expectedValue: 'The weight on the label is not used for this product' }),

    /**
     * The proposed location is not hard-coded by tests that log in a user without a workplace: for such a user the location rule
     * ends at the organisation's default warehouse (preset stack data, never a fixture warehouse). This waits for a non-empty
     * `locator-value` and returns its text, so the test can assert that the HU info screen after OK shows the same location.
     */
    expectLocatorProposedAndGet: async () => await test.step(`${NAME} - Expect a proposed location and read it`, async () => {
        const locator = containerElement().getByTestId('locator-value');
        await expect(locator).toHaveText(/\S/);
        return (await locator.innerText()).trim();
    }),

    expectOKEnabled: async () => await test.step(`${NAME} - Expect OK enabled`, async () => {
        await expect(containerElement().getByTestId('ok-button')).toBeEnabled();
    }),

    expectOKDisabled: async () => await test.step(`${NAME} - Expect OK disabled`, async () => {
        await expect(containerElement().getByTestId('ok-button')).toBeDisabled();
    }),

    /** the inline quantity field of the quantity row */
    qtyInput: () => containerElement().getByTestId('qty-input'),

    /**
     * The quantity is typed into the inline field with real key events (pressSequentially): fill() sends no keydown and would not see
     * a hardware scanner's window-level key reader swallowing the digits. The proposed quantity is pre-selected on focus: typing replaces it.
     */
    setQty: async (qty) => await test.step(`${NAME} - Type quantity "${qty}"`, async () => {
        const qtyInput = NewHUPreviewScreen.qtyInput();
        await qtyInput.click();
        await qtyInput.pressSequentially(`${qty}`);
        await expect(qtyInput).toHaveValue(`${qty}`);
    }),

    /** the field is emptied with real key events (select all + Backspace) */
    clearQty: async () => await test.step(`${NAME} - Empty the quantity field`, async () => {
        const qtyInput = NewHUPreviewScreen.qtyInput();
        await qtyInput.click();
        await qtyInput.press('Control+A');
        await qtyInput.press('Backspace');
        await expect(qtyInput).toHaveValue('');
    }),

    /** the quantity shown in the field and its unit, e.g. ('12.345', 'KGM') */
    expectQty: async ({ qty, uom }) => await test.step(`${NAME} - Expect quantity "${qty} ${uom}"`, async () => {
        await expect(NewHUPreviewScreen.qtyInput()).toHaveValue(qty);
        await expect(containerElement().getByTestId('qty-value')).toContainText(uom);
    }),

    /** Phone ergonomics: every value cell ends inside the viewport (a long value wraps; nothing is clipped on the right). */
    expectValuesNotClipped: async () => await test.step(`${NAME} - Expect values inside the viewport, not clipped`, async () => {
        const viewport = page.viewportSize();
        const cells = containerElement().locator('td');
        const cellCount = await cells.count();
        for (let i = 0; i < cellCount; i++) {
            const cell = cells.nth(i);
            const box = await cell.boundingBox();
            expect(box.x + box.width, `value cell #${i} right edge`).toBeLessThanOrEqual(viewport.width);
            const overflow = await cell.evaluate((el) => el.scrollWidth - el.clientWidth);
            expect(overflow, `value cell #${i} is clipped (scrollWidth > clientWidth)`).toBeLessThanOrEqual(1);
        }
    }),

    /**
     * Phone ergonomics: from the top of the screen, the primary action OK is fully visible without scrolling — above the bottom
     * navigation bar —.
     */
    expectOKWithoutScrolling: async () => await test.step(`${NAME} - Expect OK visible without scrolling`, async () => {
        const container = containerElement();
        await container.locator('table').first().evaluate((el) => { for (let e = el; e; e = e.parentElement) e.scrollTop = 0; window.scrollTo(0, 0); });
        const ok = await container.getByTestId('ok-button').boundingBox();
        const bottomBar = await page.locator(ID_BACK_BUTTON).boundingBox();
        expect(ok.y + ok.height, 'OK bottom edge vs top of the bottom navigation bar').toBeLessThanOrEqual(bottomBar.y);
    }),

    /** the preview itself has no scanner: a location is scanned in the dialog of the Change locator button */
    expectNoScanner: async () => await test.step(`${NAME} - Expect no scanner on the preview`, async () => {
        await BarcodeScannerComponent.expectNotAttached({});
    }),

    /** the "Change locator" button -> the dialog of the HU Manager (the only scanner on screen) */
    openChangeLocatorDialog: async () => await test.step(`${NAME} - Tap Change locator`, async () => {
        await containerElement().getByTestId('change-locator-button').tap();
        await changeLocatorDialog().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** scan in the open dialog: a location is applied and the dialog closes; any other code is refused with an error toast (dialog stays) */
    scanInChangeLocatorDialog: async (code) => await test.step(`${NAME} - Scan "${code}" in the Change locator dialog`, async () => {
        await BarcodeScannerComponent.type(code);
    }),

    expectChangeLocatorDialogClosed: async () => await test.step(`${NAME} - Expect the Change locator dialog closed`, async () => {
        await changeLocatorDialog().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    cancelChangeLocatorDialog: async () => await test.step(`${NAME} - Cancel the Change locator dialog`, async () => {
        await changeLocatorDialog().getByTestId('cancelText-button').tap();
        await NewHUPreviewScreen.expectChangeLocatorDialogClosed();
    }),

    /** tap Change locator, scan the location code, the dialog closes with the location applied */
    changeLocator: async (code) => await test.step(`${NAME} - Change locator to "${code}"`, async () => {
        await NewHUPreviewScreen.openChangeLocatorDialog();
        await NewHUPreviewScreen.scanInChangeLocatorDialog(code);
        await NewHUPreviewScreen.expectChangeLocatorDialogClosed();
    }),

    clickOK: async () => await test.step(`${NAME} - Click OK`, async () => {
        await containerElement().getByTestId('ok-button').tap();
    }),

    /** Presses OK and returns the new HU as the server answered the create call: { huId, huQRCode, huValue, displayName, locatorValue } */
    clickOKAndGetCreatedHU: async () => await test.step(`${NAME} - Click OK and read the created HU`, async () => {
        const [response] = await Promise.all([waitForCreateResponse(), NewHUPreviewScreen.clickOK()]);
        return await toCreatedHU(response);
    }),

    /** Double click on OK (a nervous user): returns the HU of the first create call */
    doubleClickOKAndGetCreatedHU: async () => await test.step(`${NAME} - Double click OK and read the created HU`, async () => {
        const [response] = await Promise.all([waitForCreateResponse(), containerElement().getByTestId('ok-button').dblclick()]);
        return await toCreatedHU(response);
    }),

    clickCancel: async () => await test.step(`${NAME} - Click Cancel`, async () => {
        await containerElement().getByTestId('cancel-button').tap();
    }),
};
