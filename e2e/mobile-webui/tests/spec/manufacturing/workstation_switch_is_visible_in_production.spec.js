import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkstationManagerScreen } from "../../utils/screens/workstationManager/WorkstationManagerScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { ViewHeaderComponent } from "../../utils/components/ViewHeaderComponent";

// REGRESSION GUARD — both tests in this spec PASS BOTH BEFORE AND AFTER the fix, BY DESIGN. They are
// NOT broken RED tests, and must never be reported as such.
//
// They drive the operator path the customer reported: switch workstation in the Arbeitsstation
// (workstation) app, then enter the Produktion (manufacturing) app and read its header. The stale
// header the customer saw is served by the ANDROID WEBVIEW's own HTTP cache on the handheld, which
// this suite cannot instantiate — Playwright drives Chromium, and Chromium re-fetches these
// operator-context responses (measured: 13 mounts / 3 profiles / 0 reuses, plus a 4/4 desktop
// control). So on Chromium the header is correct with or without the cache directives.
//
// What this guard is for: the two-app user path itself — that a switch made in one app is what the
// other app displays. The RED->GREEN proof of the cache directives is the sibling spec
// `operator_context_reads_are_never_cached.spec.js`, which asserts the responses forbid storing.
//
// The customer hit the stale header twice in one session, once per sub-case, and the contract
// (REQUIREMENTS.md AC1) names both — so there is one test each:
//  1. the new workstation is on a DIFFERENT workplace -> both header rows must move;
//  2. the new workstation is on the SAME workplace   -> the workstation row must move while the
//     workplace row must stay put. Only the second one catches a header that refreshes by replacing
//     every row wholesale, or an assertion that merely checks "something changed".

// English captions of the two operator-context header rows (general.workplace / general.workstation).
const HEADER_WORKPLACE = 'Workplace';
const HEADER_WORKSTATION = 'Workstation';

const createMasterdataOnDifferentWorkplaces = async () => {
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
            // header rows. The sub-case where they share one workplace is the second test below.
            resources: {
                WS1: { type: 'WS', workplace: 'wpA' },
                WS2: { type: 'WS', workplace: 'wpB' },
            },
        }
    });
};

const createMasterdataOnOneWorkplace = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { manufacturing: { isScanResourceRequired: true } },
            warehouses: { whA: {} },
            workplaces: { wpA: { warehouse: 'whA' } },
            // Two workstations on THE SAME workplace: switching between them must move the
            // workstation row while leaving the workplace row exactly where it is.
            resources: {
                WS1: { type: 'WS', workplace: 'wpA' },
                WS2: { type: 'WS', workplace: 'wpA' },
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

    const masterdata = await createMasterdataOnDifferentWorkplaces();

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

// noinspection JSUnusedLocalSymbols
test('Switching to a workstation of the same workplace moves only the workstation the Production app shows', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('Switching workstation in one app is what every other app displays');
    allure.severity('critical');

    const masterdata = await createMasterdataOnOneWorkplace();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator starts the shift on workstation WS1 of workplace A', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS1.qrCode);
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS1.name);
        await WorkstationManagerScreen.expectCurrentWorkplace(masterdata.workplaces.wpA.name);
        await WorkstationManagerScreen.goBack();
    });

    await test.step('The operator works in Production, seeing WS1 / workplace A', async () => {
        // As in the sibling test above, the first visit is part of the scenario: the customer hit the
        // stale header on a RE-entry, after Production had already shown them the old workstation.
        await ApplicationsListScreen.startApplication('mfg');
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS1.name });
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
        await ManufacturingJobsListScreen.goBack();
    });

    await test.step('The operator moves to workstation WS2, one bench over on the same workplace', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS2.qrCode);
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS2.name);
        await WorkstationManagerScreen.expectCurrentWorkplace(masterdata.workplaces.wpA.name);
        await WorkstationManagerScreen.goBack();
    });

    await test.step('The operator has NOT left workplace A', async () => {
        // Asserted against the system of record, so the workplace-row assertion below is a real
        // invariant — the operator genuinely still belongs to workplace A — and not merely the
        // observation that nothing happened to that row.
        const { assignedWorkplace } = await Backend.getCurrentWorkplace();
        expect(assignedWorkplace?.name).toBe(masterdata.workplaces.wpA.name);
    });

    await test.step('Re-entering Production shows WS2, with workplace A still standing', async () => {
        await ApplicationsListScreen.startApplication('mfg');
        // The row that MUST have moved. A header still naming WS1 is exactly what the customer
        // reported on this sub-case (`Portionen2` scanned, `Portionen1` displayed).
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS2.name });
        // The row that MUST NOT have moved — the half a "did anything change?" check would miss.
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
    });
});
