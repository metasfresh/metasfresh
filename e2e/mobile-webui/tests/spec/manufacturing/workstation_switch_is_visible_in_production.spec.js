import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkstationManagerScreen } from "../../utils/screens/workstationManager/WorkstationManagerScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { ViewHeaderComponent } from "../../utils/components/ViewHeaderComponent";

// REGRESSION GUARD — this spec PASSES BOTH BEFORE AND AFTER the fix, BY DESIGN. It is NOT a broken
// RED test, and must never be reported as one.
//
// It drives the operator path the customer reported: switch workstation in the Arbeitsstation
// (workstation) app, then enter the Produktion (manufacturing) app and read its header. The stale
// header the customer saw is served by the ANDROID WEBVIEW's own HTTP cache on the handheld, which
// this suite cannot instantiate — Playwright drives Chromium, and Chromium re-fetches these
// operator-context responses (measured: 13 mounts / 3 profiles / 0 reuses, plus a 4/4 desktop
// control). So on Chromium the header is correct with or without the cache directives.
//
// What this guard is for: the two-app user path itself — that a switch made in one app is what the
// other app displays. The RED->GREEN proof of the cache directives is the sibling spec
// `operator_context_reads_are_never_cached.spec.js`, which asserts the responses forbid storing.

// English captions of the two operator-context header rows (general.workplace / general.workstation).
const HEADER_WORKPLACE = 'Workplace';
const HEADER_WORKSTATION = 'Workstation';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // The Production app shows the workstation header row — and filters its job list by the
            // workstation — only when scanning a resource is required.
            mobileConfig: { manufacturing: { isScanResourceRequired: true } },
            warehouses: { whA: {}, whB: {} },
            workplaces: {
                wpA: { warehouse: 'whA' },
                wpB: { warehouse: 'whB' },
            },
            // Two workstations on DIFFERENT workplaces, so switching between them must move BOTH
            // header rows; workstations sharing one workplace would hide a stale workplace row.
            resources: {
                WS1: { type: 'WS', workplace: 'wpA' },
                WS2: { type: 'WS', workplace: 'wpB' },
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A workstation switched in the Workstation app is the one the Production app shows', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('Switching workstation in one app is what every other app displays');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator starts the shift on workstation WS1 (workplace A)', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS1.qrCode);
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS1.name);
        await WorkstationManagerScreen.expectCurrentWorkplace(masterdata.workplaces.wpA.name);
        await WorkstationManagerScreen.goBack();
    });

    await test.step('The operator works in Production, seeing WS1 / workplace A', async () => {
        // The first visit is part of the scenario, not scaffolding: the customer hit the stale header
        // on a RE-entry, after the app had already shown them the old workstation once.
        await ApplicationsListScreen.startApplication('mfg');
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS1.name });
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
        await ManufacturingJobsListScreen.goBack();
    });

    await test.step('The operator moves to workstation WS2 (workplace B) in the Workstation app', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS2.qrCode);
        // The scan screen confirms the switch — exactly what the customer saw before it failed to
        // show up in Production.
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS2.name);
        await WorkstationManagerScreen.expectCurrentWorkplace(masterdata.workplaces.wpB.name);
        await WorkstationManagerScreen.goBack();
    });

    await test.step('The system of record has moved the operator to workplace B', async () => {
        // Asserted BEFORE the screen assertions below, so that a failure there can only mean the
        // SCREEN is stale: the server has demonstrably already moved the operator.
        const { assignedWorkplace } = await Backend.getCurrentWorkplace();
        expect(assignedWorkplace?.name).toBe(masterdata.workplaces.wpB.name);
    });

    await test.step('Re-entering Production shows WS2 / workplace B, not the workstation left behind', async () => {
        await ApplicationsListScreen.startApplication('mfg');
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS2.name });
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpB.name });
    });
});
