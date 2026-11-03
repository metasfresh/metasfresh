import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT, ID_BACK_BUTTON } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'NewHUPreviewScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#NewHUPreviewScreen');
const changeLocatorDialog = () => page.getByTestId('ChangeCurrentLocatorDialog');

/**
 * The "New HU" preview of the HU Manager: shown after a scanned product label found no existing HU.
 * Rows use the same `<name>-value` test ids as the HU info table: product-value, qty-value, locator-value,
 * attribute-<code>-value (e.g. attribute-Lot-Nummer-value), weight-value (the label weight, or the "not used for this product" text).
 * Location: button change-locator-button opens the dialog ChangeCurrentLocatorDialog (scan a location; the preview has no scanner of its own).
 * Quantity: an inline field (qty-input) in the quantity row; the unit is shown in the field. Buttons: ok-button, cancel-button.
 */
export const NewHUPreviewScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectNotDisplayed: async () => await test.step(`${NAME} - Expect screen NOT to be displayed`, async () => {
        await expect(containerElement()).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    expectValue: async ({ name, expectedValue }) => await test.step(`${NAME} - Expect "${name}" to be "${expectedValue}"`, async () => {
        await expect(containerElement().getByTestId(name)).toHaveText(expectedValue);
    }),

    /** The product row contains the product name (the row may carry more than the name, e.g. the product value). */
    expectProductContains: async (productName) => await test.step(`${NAME} - Expect the product to contain "${productName}"`, async () => {
        await expect(containerElement().getByTestId('product-value')).toContainText(productName);
    }),

    /** An attribute row is exactly the given display text (e.g. a date as the screen renders it). */
    expectAttributeValue: async ({ code, expectedValue }) => await test.step(`${NAME} - Expect attribute "${code}" to be "${expectedValue}"`, async () => {
        await expect(containerElement().getByTestId(`attribute-${code}-value`)).toHaveText(expectedValue);
    }),

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
        await container.locator('table').evaluate((el) => { for (let e = el; e; e = e.parentElement) e.scrollTop = 0; window.scrollTo(0, 0); });
        const ok = await container.getByTestId('ok-button').boundingBox();
        const bottomBar = await page.locator(ID_BACK_BUTTON).boundingBox();
        expect(ok.y + ok.height, 'OK bottom edge vs top of the bottom navigation bar').toBeLessThanOrEqual(bottomBar.y);
    }),

    /** the OK button of the preview (scoped: the clearance dialog in the HU Manager uses the same test ids) */
    okButton: () => containerElement().getByTestId('ok-button'),

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

    clickCancel: async () => await test.step(`${NAME} - Click Cancel`, async () => {
        await containerElement().getByTestId('cancel-button').tap();
    }),
};
