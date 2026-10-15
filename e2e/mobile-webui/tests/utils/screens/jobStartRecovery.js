import { FAST_ACTION_TIMEOUT, page } from '../common';
import { ApplicationsListScreen } from './ApplicationsListScreen';

// Bounded tap-and-recover budget, shared by the picking and distribution job-start helpers.
export const JOB_START_TAP_ATTEMPTS = 3;
// Short per-attempt wait for the job screen to appear after a tap. On the happy path the first attempt
// succeeds immediately; only a slow/lost workflow-start round-trip pays this.
export const JOB_START_ARRIVAL_TIMEOUT = 8000; // 8sec

const isAttached = async (selector) =>
    await page
        .locator(selector)
        .waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT })
        .then(() => true, () => false);

/**
 * Decide how to recover after a launcher tap failed to reach the job screen.
 *
 * Three outcomes, and only two of them make a re-tap safe:
 *  - 'launcherList' - we are demonstrably back on the launcher list, so no start is mid-flight and the
 *    caller may re-tap;
 *  - 'recovered' - neither screen was attached because the app landed on the APPLICATIONS MENU (the
 *    observed job-start bounce: the just-started job was pruned from the store and ApplicationLayout
 *    redirected home). We re-entered the application and the launcher list is up, so a re-tap is safe;
 *  - 'unknown' - a navigation may still be in flight, or re-entry did not bring the list up. The caller
 *    must NOT re-tap (that could double-start the workflow); let its trailing full settle decide.
 *
 * Known limitation, deliberate: re-entering the application from the menu loses any list filter the spec
 * had applied (e.g. filterByDocumentNo). A caller that tapped by documentNo still finds its job (that
 * locator filters by text); a caller that tapped by bare index may then fail its exactly-one-candidate
 * guard and fail LOUD. That is correct - a silent re-tap on the wrong launcher would be worse.
 */
export const recoverToLauncherList = async ({ applicationId, launcherListSelector = '#WFLaunchersScreen' }) => {
    if (await isAttached(launcherListSelector)) {
        return 'launcherList';
    }
    if (!(await isAttached('#ApplicationsListScreen'))) {
        return 'unknown';
    }
    await ApplicationsListScreen.startApplication(applicationId);
    return (await isAttached(launcherListSelector)) ? 'recovered' : 'unknown';
};
