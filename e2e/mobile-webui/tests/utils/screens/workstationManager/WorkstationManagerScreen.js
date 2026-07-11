import { ID_BACK_BUTTON, page, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'WorkstationManagerScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WorkstationManagerScreen');

// Header-table captions rendered by WorkstationInfoComponent.js (misc/services/mobile-webui/mobile-webui-frontend/
// src/apps/workstationManager/components/WorkstationInfoComponent.js). Both are hardcoded English captions
// because the e2e suite runs against the English locale (see sibling WorkplaceManagerScreen usage in
// home_screen.spec.js: caption: 'Name' / caption: 'Assigned').
const CAPTION_WORKSTATION_NAME = 'Name'; // i18n key 'workstationName' -> apps/workstationManager/i18n/en.json
// The 'Workplace' row (i18n key 'general.workplace' -> translations_en.js) renders the operator's
// CURRENT active workplace (read from GET /workplace), which can drift from the workstation's
// statically-linked workplace — so a mismatch is visible to the operator.
const CAPTION_CURRENT_WORKPLACE = 'Workplace'; // i18n key 'general.workplace'

export const WorkstationManagerScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanWorkstation: async (qrCode) => await test.step(`${NAME} - Scan workstation QR '${qrCode}'`, async () => {
        // Scanning into a just-mounted home screen can race the barcode reader's keydown listener,
        // which attaches in a post-render effect slightly after its input is in the DOM — a scan fired
        // in that window is silently dropped and never routes. Retry the scan until the workstation
        // screen actually opens (a genuinely non-routing scan still fails once the outer timeout elapses).
        await expect(async () => {
            await BarcodeScannerComponent.type(qrCode);
            await containerElement().waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
        }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
        await WorkstationManagerScreen.waitForScreen();
    }),

    // The frontend's Assign button (apps/workstationManager/containers/AppScreen.js) carries
    // testId="assign-button" (mirroring WorkplaceManagerScreen's own assign-button) — select by testId,
    // not by rendered caption, which is i18n-fragile. Under scan-and-go the scan assigns the workstation,
    // so this button is expected to be hidden after a scan.
    expectAssignButtonNotVisible: async () => await test.step(`${NAME} - Expect Assign button NOT visible`, async () => {
        await expect(containerElement().getByTestId('assign-button')).toHaveCount(0);
    }),

    expectHeaderProperty: async ({ caption, value }) => await test.step(`${NAME} - Check header property '${caption}'='${value}'`, async () => {
        const row = await page.locator(
            `tr:has(th:has-text("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),

    // Asserts the operator's current workstation (the one currently scanned/assigned) = name.
    expectCurrentWorkstation: async (name) => await test.step(`${NAME} - Expect current workstation = '${name}'`, async () => {
        await WorkstationManagerScreen.expectHeaderProperty({ caption: CAPTION_WORKSTATION_NAME, value: name });
    }),

    // Asserts the operator's current active workplace = name (see CAPTION_CURRENT_WORKPLACE above).
    expectCurrentWorkplace: async (name) => await test.step(`${NAME} - Expect current workplace = '${name}'`, async () => {
        await WorkstationManagerScreen.expectHeaderProperty({ caption: CAPTION_CURRENT_WORKPLACE, value: name });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await WorkstationManagerScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),
}
