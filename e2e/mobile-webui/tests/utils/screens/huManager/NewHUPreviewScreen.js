import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT, ID_BACK_BUTTON } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'NewHUPreviewScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#NewHUPreviewScreen');

/**
 * The "New HU" preview of the HU Manager: shown after a scanned product label found no existing HU.
 * Rows use the same `<name>-value` test ids as the HU info table: product-value, qty-value, locator-value,
 * attribute-<code>-value (e.g. attribute-Lot-Nummer-value), weight-value (the label weight, or the "not used for this product" text).
 * Quantity: button qty-edit-button opens the dialog NewHUQtyDialog (qty-input, ok-button, cancel-button). Buttons: ok-button, cancel-button.
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

    /**
     * The quantity is changed in a dialog (the scanner is unmounted while it is open; next to a live hardware scanner typed digits
     * would be swallowed). It is typed with real key events (pressSequentially): fill() sends no keydown and would not see that.
     * The read-only row `qty-value` shows the effective quantity + UOM.
     */
    setQty: async (qty) => await test.step(`${NAME} - Set quantity "${qty}"`, async () => {
        await containerElement().getByTestId('qty-edit-button').tap();
        const dialog = page.getByTestId('NewHUQtyDialog');
        await dialog.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        const qtyInput = dialog.getByTestId('qty-input');
        await qtyInput.click();
        await qtyInput.pressSequentially(`${qty}`); // the field is pre-selected on focus: typing replaces the proposed quantity
        await expect(qtyInput).toHaveValue(`${qty}`);
        await dialog.getByTestId('ok-button').tap();
        await dialog.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
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

    /** a scan on the preview (no button): a location QR code replaces the location, any other code is refused with an error toast */
    scan: async (code) => await test.step(`${NAME} - Scan on the preview`, async () => {
        await NewHUPreviewScreen.waitForScreen();
        await BarcodeScannerComponent.type(code);
    }),

    clickOK: async () => await test.step(`${NAME} - Click OK`, async () => {
        await containerElement().getByTestId('ok-button').tap();
    }),

    clickCancel: async () => await test.step(`${NAME} - Click Cancel`, async () => {
        await containerElement().getByTestId('cancel-button').tap();
    }),
};
