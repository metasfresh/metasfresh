import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, VERY_FAST_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobsListFiltersScreen } from "./PickingJobsListFiltersScreen";
import { PickingJobsListScanScreen } from './PickingJobsListScanScreen';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { expectClasses } from '../../expectations';
import { MassPrintingScanScreen } from './MassPrintingScanScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { OperatorContextErrorPanel } from '../../components/OperatorContextErrorPanel';
// Bounded tap-and-recover for the launcher-start navigation (see tapLauncherUntilJobScreen), shared
// with the distribution job-start helper. Small attempt count with explicit per-step timeouts so the
// retry cost stays modest: only the first attempt pays the full slow-action settle budget; retries
// re-settle an already-populated list on a short budget, so a few attempts do not multiply the full
// 20s screen wait.
import { JOB_START_ARRIVAL_TIMEOUT, JOB_START_TAP_ATTEMPTS, recoverToLauncherList } from '../jobStartRecovery';

const NAME = 'PickingJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');
// The job (workflow-process) screen reached after starting a launcher. This id mirrors the private
// containerElement in PickingJobScreen.js — keep the two in sync if the workflow-process screen id changes.
/** @returns {import('@playwright/test').Locator} */
const jobScreenElement = () => page.locator('#WFProcessScreen');

export const PickingJobsListScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    // Wait until a specific job launcher is visible in the list. The list is websocket-driven and
    // populates a moment after the screen renders, so callers that act on the list (e.g. opening the
    // filter, whose facets are computed from the listed jobs) must wait for the job to be present first.
    // Locate by an exact data attribute (e.g. customerId) — never by a documentNo substring, which
    // collides with other jobs in a shared/full-suite run.
    waitForJobVisible: async ({ documentNo, salesOrderId, customerId, customerLocationId } = {}) => await test.step(`${NAME} - Wait for job visible`, async () => {
        await locateJobButtons({ documentNo, salesOrderId, customerId, customerLocationId }).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Reads a single job launcher's caption (the " | "-joined display fields) and asserts on it.
     * Locate the job by an exact data attribute (customerId / salesOrderId / customerLocationId) —
     * documentNo is only a caption substring and collides with other jobs in a full-suite run.
     * @param contains - substrings that must all be present in the caption
     * @param fieldCount - if set, the expected number of non-empty " | "-separated caption fields
     * @returns the caption text (also logged, to aid assertion tuning)
     */
    expectJobCaption: async ({ documentNo, salesOrderId, customerId, customerLocationId, contains = [], fieldCount } = {}) => await test.step(`${NAME} - Expect job caption`, async () => {
        const button = locateJobButtons({ documentNo, salesOrderId, customerId, customerLocationId });
        await button.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toHaveCount(1);
        const caption = (await button.innerText()).trim();
        console.log(`${NAME} - job caption = ${JSON.stringify(caption)}`);
        for (const text of contains) {
            expect(caption, `caption should contain "${text}"`).toContain(text);
        }
        if (fieldCount != null) {
            const fields = caption.split('|').map(s => s.trim()).filter(s => s.length > 0);
            expect(fields.length, `expected ${fieldCount} non-empty caption fields, caption was: ${caption}`).toBe(fieldCount);
        }
        return caption;
    }),

    clickFilterButton: async () => await test.step(`${NAME} - Click filter button`, async () => {
        await page.locator('#filter-button').tap();
        await PickingJobsListFiltersScreen.waitForScreen();
    }),
    filterByDocumentNo: async (documentNo) => await test.step(`${NAME} - Filter by documentNo ${documentNo}`, async () => {
        await PickingJobsListScreen.clickFilterButton();
        await PickingJobsListFiltersScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.waitForScreen();
    }),

    filterByQRCode: async (scannedCode) => await test.step(`${NAME} - Filter by scanned code ${scannedCode}`, async () => {
        await PickingJobsListScreen.clickQRCodeFilterButton();
        await PickingJobsListScanScreen.scanQRCode(scannedCode);
    }),

    clearQRCodeFilter: async () => await test.step(`${NAME} - Clear QR code filter`, async () => {
        await PickingJobsListScreen.clickQRCodeFilterButton();
        // NOTE when we navigate to PickingJobsListScanScreen we expect the current QR code filter to be cleared,
        // so we just have to go back
        await PickingJobsListScanScreen.goBack();
    }),

    clickQRCodeFilterButton: async () => await test.step(`${NAME} - Click QR code filter button`, async () => {
        await page.getByTestId('filterByQRCode-button').tap({ timeout: VERY_FAST_ACTION_TIMEOUT });
        await PickingJobsListScanScreen.waitForScreen();
    }),

    startJob: async ({ index, documentNo, qtyToDeliver, customerLocationId }) => {
        if (documentNo != null) {
            return await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
                for (let attempt = 1; attempt <= JOB_START_TAP_ATTEMPTS; attempt++) {
                    await locateJobButtons({ documentNo }).tap();
                    const arrived = await jobScreenElement()
                        .waitFor({ state: 'attached', timeout: JOB_START_ARRIVAL_TIMEOUT })
                        .then(() => true, () => false);
                    if (arrived || attempt === JOB_START_TAP_ATTEMPTS) {
                        break;
                    }
                    if ((await recoverToLauncherList({ applicationId: 'picking' })) === 'unknown') {
                        break;
                    }
                }
                await PickingJobScreen.waitForScreen();
                return {
                    pickingJobId: await PickingJobScreen.getPickingJobId(),
                }
            });
        } else if (index != null) {
            return await test.step(`${NAME} - Start job by index ${index - 1}`, async () => {
                await tapLauncherUntilJobScreen({ index, qtyToDeliver, customerLocationId });
                return {
                    pickingJobId: await PickingJobScreen.getPickingJobId(),
                }
            });
        } else {
            throw "No documentNo or index provided";
        }
    },

    // Start a job by dispatching the click event directly on the launcher (by document number),
    // bypassing hit-testing. Use ONLY when a foreground `.loading` overlay would intercept a normal
    // tap yet the launcher itself is the intended target — e.g. a test that deliberately holds a
    // launchers refresh in flight while starting the job. Waits for the job (WF-process) screen.
    startJobByDispatchClick: async ({ documentNo }) => await test.step(`${NAME} - Start job by dispatched click (documentNo ${documentNo})`, async () => {
        const launcher = locateJobButtons({ documentNo });
        await launcher.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await launcher.dispatchEvent('click');
        await PickingJobScreen.waitForScreen();
    }),

    expectJobButtons: async (expectationsArray) => await test.step(`${NAME} - Expect ${expectationsArray.length} job buttons`, async () => {
        await test.step(`Wait for all expected buttons to be attached`, async () => {
            for (const expectation of expectationsArray) {
                await locateJobButtons(expectation).waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
            }
        });

        //
        // Check it again to make sure all expected buttons are still there and there is one of each
        for (let i = 0; i < expectationsArray.length; i++) {
            const expectation = expectationsArray[i];
            await expectJobButton({
                name: `${i + 1}/${expectationsArray.length}`,
                button: locateJobButtons(expectation),
                expectation
            });
        }

        //
        // Make sure we have the expected number of buttons
        // NOTE: we do this at the end because expect does not wait for the elements to stabilize
        await expect(locateJobButtons()).toHaveCount(expectationsArray.length);
    }),

    clickMassPrintingButton: async () => await test.step(`${NAME} - Click Mass Printing button`, async () => {
        const button = page.getByTestId('massPrinting-button');
        await button.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toBeEnabled();
        await button.tap();
        await MassPrintingScanScreen.waitForScreen();
    }),

    expectMassPrintingButtonHidden: async () => await test.step(`${NAME} - Expect Mass Printing button hidden`, async () => {
        await page.getByTestId('massPrinting-button').waitFor({ state: 'detached', timeout: VERY_FAST_ACTION_TIMEOUT });
    }),

    // The jobs list itself asks for a workplace when the picking profile requires an active one and
    // the operator has none assigned yet: the screen renders its own scanner instead of the job list.
    expectAsksForWorkplace: async () => await test.step(`${NAME} - Expect the screen to ask for a workplace`, async () => {
        await PickingJobsListScreen.expectVisible();
        await BarcodeScannerComponent.expectAttached({});
    }),

    // Scan without asserting the job list takes over — for scenarios where the assign is expected to
    // fail (e.g. the connection dropped), so the scanner does NOT give way to the job list.
    typeWorkplaceQRCode: async (qrCode) => await test.step(`${NAME} - Scan workplace QR '${qrCode}'`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

    // When the operator's workplace cannot be read — or assigned from a scan — because the connection
    // dropped, the screen must say so and offer a retry, instead of silently showing no workplace.
    expectConnectionErrorPanel: async () => await test.step(`${NAME} - Expect operator-context connection error panel`, async () => {
        await OperatorContextErrorPanel.expectVisible();
    }),

    expectNoConnectionErrorPanel: async () => await test.step(`${NAME} - Expect no operator-context connection error panel`, async () => {
        await OperatorContextErrorPanel.expectNotVisible();
    }),

    retryLoadingOperatorContext: async () => await test.step(`${NAME} - Retry loading workplace/workstation`, async () => {
        await OperatorContextErrorPanel.tapRetry();
        await PickingJobsListScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobsListScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),

};

/**
 * Settle the launcher list, then pin the tap target by stable identity — its data-testid when the
 * launcher exposes one (unique, reorder-immune), else its attribute/index locator — read at runtime.
 * @param settleTimeout - wait budget for the list to settle (spinner detached + launcher visible).
 *        Defaults to the full slow-action budget for a first, freshly-navigated (websocket-populating)
 *        list; a retry that is already back on a populated list can pass a shorter budget.
 * @returns {Promise<import('@playwright/test').Locator>} the locator to tap
 */
const resolveLauncherTapTarget = async ({ index, qtyToDeliver, customerLocationId, settleTimeout = SLOW_ACTION_TIMEOUT }) => {
    await page.locator('.loading').waitFor({ state: 'detached', timeout: settleTimeout });

    const byAttribute = qtyToDeliver != null || customerLocationId != null;
    const identified = byAttribute
        ? locateJobButtons({ qtyToDeliver, customerLocationId })
        : locateJobButtons({ index });
    await identified.waitFor({ state: 'visible', timeout: settleTimeout });

    // Exactly one launcher must be addressed. A bare index (.nth is always ≤1) is reorder-safe only
    // against a single-candidate list (a prior filterByDocumentNo / single-order masterdata), so the
    // real guard there is the unfiltered set — a bare index against a multi-launcher list fails loud.
    await expect(byAttribute ? identified : locateJobButtons()).toHaveCount(1);

    const testId = await identified.getAttribute('data-testid');
    return (testId != null && testId.length > 0) ? page.getByTestId(testId) : identified;
};

/**
 * Reach the picking job screen from a launcher tap, recovering like a real user from a slow or lost
 * workflow-start round-trip: tap the launcher; if the job screen has not come up and we are still on
 * the launcher list, re-resolve the launcher FRESH and tap again — bounded to a few attempts.
 *
 * This retries ONLY the setup navigation (reaching the job screen); it asserts nothing about the
 * feature under test. Each wait is explicitly bounded (no unbounded/120s fallback). Idempotency guard:
 * a re-tap fires only while we are demonstrably back on the (websocket-driven) launcher list — never
 * mid-transition, so a start already in flight is not double-fired. If the job screen never arrives,
 * the trailing full-settle waitForScreen throws and the test fails loud (a genuine broken start is
 * never swallowed).
 */
const tapLauncherUntilJobScreen = async ({ index, qtyToDeliver, customerLocationId }) => {
    for (let attempt = 1; attempt <= JOB_START_TAP_ATTEMPTS; attempt++) {
        // First attempt settles the freshly-navigated (websocket-populating) list on the full budget;
        // a retry is already back on a populated list, so it re-settles on a short budget.
        const settleTimeout = attempt === 1 ? SLOW_ACTION_TIMEOUT : FAST_ACTION_TIMEOUT;
        const target = await resolveLauncherTapTarget({ index, qtyToDeliver, customerLocationId, settleTimeout });
        await target.tap();

        const arrived = await jobScreenElement()
            .waitFor({ state: 'attached', timeout: JOB_START_ARRIVAL_TIMEOUT })
            .then(() => true, () => false);
        if (arrived || attempt === JOB_START_TAP_ATTEMPTS) {
            break;
        }

        // Not on the job screen yet, and attempts remain. recoverToLauncherList also handles the case the
        // old code could not: the app landed on the applications menu, where neither screen is attached,
        // so breaking out here burned the remaining attempts on the 20s wait.
        if ((await recoverToLauncherList({ applicationId: 'picking' })) === 'unknown') {
            break;
        }
    }

    await PickingJobScreen.waitForScreen();
};

const locateJobButtons = ({ documentNo, index, salesOrderId, customerId, qtyToDeliver, productId, customerLocationId, caption } = {}) => {
    let selector = '.wflauncher-button';
    if (salesOrderId != null) {
        selector += `[data-salesorderid="${salesOrderId}"]`;
    }
    if (customerId != null) {
        selector += `[data-customerid="${customerId}"]`;
    }
    if (qtyToDeliver != null) {
        selector += `[data-qtytodeliver="${qtyToDeliver}"]`;
    }
    if (productId != null) {
        selector += `[data-productid="${productId}"]`;
    }
    if (customerLocationId != null) {
        selector += `[data-customerlocationid="${customerLocationId}"]`;
    }

    let locator = page.locator(selector);

    if (documentNo != null) {
        locator = locator.filter({ hasText: documentNo })
    }

    if (index != null) {
        locator = locator.nth(index - 1);
    }

    if (caption != null) {
        locator = locator.filter({ hasText: caption })
    }

    return locator;
};

const expectJobButton = async ({ name, button, expectation }) => await test.step(`Expect job button ${name}`, async () => {
    await button.waitFor({ state: 'visible', timeout: VERY_FAST_ACTION_TIMEOUT });
    await expect(button).toHaveCount(1);

    if (expectation.indicator != null) {
        await expectClasses({
            locator: button.locator(`[data-testid="indicator"]`),
            expectedClasses: expectation.indicator
        });
    }

    if (expectation.alreadyStarted != null) {
        const indicatorLocator = button.locator(`[data-testid="indicator2"]`);
        if (expectation.alreadyStarted) {
            await expectClasses({ locator: indicatorLocator, expectedClasses: 'fa-lock indicator-box' });
        } else {
            await expect(indicatorLocator).toHaveCount(0);
        }
    }

    if (expectation.caption != null) {
        await expect(button).toHaveText(expectation.caption);
    }
});

