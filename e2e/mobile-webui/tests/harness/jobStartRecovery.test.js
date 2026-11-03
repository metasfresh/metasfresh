import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { setCurrentPage } from '../utils/common';
import { recoverToLauncherList } from '../utils/screens/jobStartRecovery';

/**
 * The recovery DECISION, driven against a scripted fake page, with no browser.
 *
 * A job start does not normally land on the applications menu, so a real E2E run never exercises the
 * recovery branch. This is the only test that runs the recovery decision across all of its outcomes.
 *
 * How it can be browserless: tests/utils/common.js exports setCurrentPage(currentPage), which assigns
 * the module-level `page` every screen helper reads. A test that does NOT destructure the `page`
 * fixture never launches a browser, so it can install a fake and drive the helper against it.
 *
 * NEVER declare `{ page }` in a test signature in this file — that pulls in the real browser fixture
 * (playwright.config.js's `page` override calls setCurrentPage(realPage)) and overwrites the fake.
 *
 * Scope note: this targets `recoverToLauncherList` rather than `tapLauncherUntilJobScreen`, because
 * that one cannot be driven with a fake page at all — `resolveLauncherTapTarget` calls
 * `expect(locator).toHaveCount(1)`, and Playwright's `expect` only accepts a real Locator. That is
 * exactly why the recovery decision lives in its own module with no `expect` in it.
 */

// Which selectors are currently attached to the fake DOM.
let attached = {};
// Selector -> the attachment changes a tap on it causes (the navigation it triggers).
let afterTap = {};
// Every selector tapped, in order.
let tapped = [];

const fakePage = {
    // setCurrentPage attaches a console recorder to whatever page it is given (see
    // tests/utils/common.js startConsoleRecorder), so a page that cannot register a listener makes
    // every test in this file throw in beforeEach, before its body runs. Inert is enough: nothing
    // here asserts on console output.
    on: () => {},
    off: () => {},
    locator: (selector) => ({
        waitFor: async () => {
            if (!attached[selector]) {
                throw new Error(`fakePage: ${selector} is not attached`);
            }
        },
        tap: async () => {
            tapped.push(selector);
            Object.assign(attached, afterTap[selector] ?? {});
        },
    }),
};

test.beforeEach(() => {
    attached = {};
    afterTap = {};
    tapped = [];
    setCurrentPage(fakePage);
});

test('recoverToLauncherList: already back on the launcher list -> launcherList, no tap', async () => {
    attached['#WFLaunchersScreen'] = true;

    const outcome = await recoverToLauncherList({ applicationId: 'picking' });

    expect(outcome).toBe('launcherList');
    expect(tapped).toEqual([]);
});

test('recoverToLauncherList: landed on the applications menu and re-entry succeeds -> recovered', async () => {
    attached['#ApplicationsListScreen'] = true;
    afterTap['#picking-button'] = { '#WFLaunchersScreen': true };

    const outcome = await recoverToLauncherList({ applicationId: 'picking' });

    expect(outcome).toBe('recovered');
    expect(tapped).toEqual(['#picking-button']);
});

test('recoverToLauncherList: neither screen attached (a navigation still in flight) -> unknown, no tap', async () => {
    const outcome = await recoverToLauncherList({ applicationId: 'picking' });

    expect(outcome).toBe('unknown');
    expect(tapped).toEqual([]);
});

// This case matters most: a broken re-entry must never authorise a re-tap of the launcher, which
// could double-start the workflow.
test('recoverToLauncherList: menu attached but re-entry does not bring up the list -> unknown', async () => {
    attached['#ApplicationsListScreen'] = true;

    const outcome = await recoverToLauncherList({ applicationId: 'picking' });

    expect(outcome).toBe('unknown');
    expect(tapped).toEqual(['#picking-button']);
});
