import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkplaceManagerScreen } from "../../utils/screens/workplaceManager/WorkplaceManagerScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { ViewHeaderComponent } from "../../utils/components/ViewHeaderComponent";

// REGRESSION GUARD — this spec PASSES BOTH BEFORE AND AFTER the fix, BY DESIGN. It is NOT a broken
// RED test, and must never be reported as one.
//
// It is the workplace direction of the same guarantee the sibling
// `manufacturing/workstation_switch_is_visible_in_production.spec.js` covers for the workstation: a
// switch made in one app is what the next app displays. The stale header the customer reported is
// served by the ANDROID WEBVIEW's own HTTP cache on the handheld, which this suite cannot
// instantiate — Playwright drives Chromium, and Chromium re-fetches these operator-context
// responses. So on Chromium the header is correct with or without the cache directives.
//
// The RED->GREEN proof of the cache directives is
// `manufacturing/operator_context_reads_are_never_cached.spec.js`, which asserts the responses
// forbid storing. What THIS guard is for is the two-app user path itself.
//
// Picking is the app the contract names for this direction (REQUIREMENTS.md AC3): it requires an
// active workplace and filters its job list by it, so an operator reading a stale workplace here is
// told they are somewhere they are not, above a list already filtered for where they actually are.

// English caption of the operator-context header row (general.workplace).
const HEADER_WORKPLACE = 'Workplace';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            // Deliberately NO workplace on the user: every workplace this operator has comes from a
            // scan in the Workplace app, which is the action under test.
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    // The Packing app shows the workplace header row — and filters its job list by
                    // the workplace — only when an active workplace is required.
                    activeWorkplaceRequired: true,
                    // A picking profile is rejected without at least one pick-to structure; the
                    // scenario never reaches a job, so the simplest one will do.
                    pickTo: ['CU'],
                }
            },
            warehouses: { wh: {} },
            workplaces: { wpA: {}, wpB: {} },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A workplace switched in the Workplace app is the one the Packing app shows', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('Switching workplace in one app is what every other app displays');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator starts the shift on workplace A', async () => {
        await WorkplaceManagerScreen.scanWorkplace(masterdata.workplaces.wpA.qrCode);
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Name', value: masterdata.workplaces.wpA.name });
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Assigned', value: 'Yes' });
        await WorkplaceManagerScreen.goBack();
    });

    await test.step('The operator works in Packing, seeing workplace A', async () => {
        // The first visit is part of the scenario, not scaffolding: the stale header is only
        // reachable on a RE-entry, after the app has already displayed the old workplace once.
        await ApplicationsListScreen.startPickingApplication();
        await PickingJobsListScreen.waitForScreen();
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
        await PickingJobsListScreen.goBack();
    });

    await test.step('The operator moves to workplace B in the Workplace app', async () => {
        await WorkplaceManagerScreen.scanWorkplace(masterdata.workplaces.wpB.qrCode);
        // The scan screen confirms the switch — the same confirmation the customer got before the
        // other app went on showing the workplace they had left.
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Name', value: masterdata.workplaces.wpB.name });
        await WorkplaceManagerScreen.expectHeaderProperty({ caption: 'Assigned', value: 'Yes' });
        await WorkplaceManagerScreen.goBack();
    });

    await test.step('The system of record has moved the operator to workplace B', async () => {
        // Asserted BEFORE the screen assertion below, so that a failure there can only mean the
        // SCREEN is stale: the server has demonstrably already moved the operator.
        const { assignedWorkplace } = await Backend.getCurrentWorkplace();
        expect(assignedWorkplace?.name).toBe(masterdata.workplaces.wpB.name);
    });

    await test.step('Re-entering Packing shows workplace B, not the workplace left behind', async () => {
        await ApplicationsListScreen.startPickingApplication();
        await PickingJobsListScreen.waitForScreen();
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpB.name });
    });
});
