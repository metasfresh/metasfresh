import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkstationManagerScreen } from "../../utils/screens/workstationManager/WorkstationManagerScreen";
import { WorkplaceManagerScreen } from "../../utils/screens/workplaceManager/WorkplaceManagerScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { AppLifecycleComponent } from "../../utils/components/AppLifecycleComponent";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            // No workplace preassigned to the login user — the operator's active workplace is
            // established purely by scanning, which is exactly what this scenario drives.
            login: { user: { language: "en_US" } },
            // The manufacturing app only reads (and only displays) the operator's workstation when its
            // config says a resource scan is required — MobileUI_UserProfile_MFG.IsScanResourceRequired
            // (ManufacturingMobileApplication.java) — whose built-in default is off. It must be on here
            // to reproduce the reported screen at all: the customer's Produktion screenshots show an
            // Arbeitsstation header row, which only renders when the workstation is read. Set in this
            // spec's own masterdata rather than inherited, per the fresh-fixture rule.
            mobileConfig: { manufacturing: { isScanResourceRequired: true } },
            warehouses: {
                whA: {},
                whB: {},
            },
            workplaces: {
                wpA: { warehouse: 'whA' },
                wpB: { warehouse: 'whB' },
            },
            resources: {
                // WS1 is a workstation statically linked to workplace A.
                WS1: { type: 'WS', workplace: 'wpA' },
                // WS2 belongs to a DIFFERENT workplace (B), so switching to it must move both the
                // displayed workstation and the displayed active workplace.
                WS2: { type: 'WS', workplace: 'wpB' },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Re-scanning an already-assigned workstation must re-switch a drifted active workplace', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('Scanning a workstation re-assigns the operator to its linked workplace');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('Scan workstation WS1 (linked to workplace A) -> auto-assigned, active workplace = A', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS1.qrCode);
        // Scan-and-go (mirrors the workplace app): the scan itself assigns the workstation and sets the
        // operator's active workplace to the linked one (A) — no separate Assign tap. The screen reflects it.
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS1.name);
        await WorkstationManagerScreen.expectCurrentWorkplace(masterdata.workplaces.wpA.name);
        await WorkstationManagerScreen.goBack();
    });

    await test.step('Scan workplace B directly -> active workplace drifts to B', async () => {
        await WorkplaceManagerScreen.scanWorkplace(masterdata.workplaces.wpB.qrCode);
        await WorkplaceManagerScreen.goBack();
    });

    await test.step('Re-scan workstation WS1 -> the operator\'s active workplace must switch back to A', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS1.qrCode);

        // The screen shows the operator's current workstation and current active workplace,
        // so any drift is visible. After the re-scan the active workplace is back on A.
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS1.name);
        await WorkstationManagerScreen.expectCurrentWorkplace(masterdata.workplaces.wpA.name);

        // Decisive assertion: NOT the workstation SCREEN's statically-linked workplace (always A,
        // would not catch this bug) — the operator's ACTUAL active workplace, read from the system
        // of record (Backend.getCurrentWorkplace, backed by the same /api/v2/workplace endpoint the
        // app itself reads its current workplace from).
        const { assignedWorkplace } = await Backend.getCurrentWorkplace();
        // The re-scan must re-assign the workstation and switch the active workplace back to A; a
        // read-only scan leaves it on the drifted workplace B and this assertion fails.
        expect(assignedWorkplace?.name).toBe(masterdata.workplaces.wpA.name);
    });
});

// noinspection JSUnusedLocalSymbols
test('A workstation switch made while the Produktion screen stays open must show on re-entry', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('A screen showing the operator\'s workstation re-reads it when the operator returns to it');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('Scan workstation WS1 (linked to workplace A), then open the manufacturing job list', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS1.qrCode);
        await WorkstationManagerScreen.goBack();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.expectCurrentWorkstation(masterdata.resources.WS1.name);
        await ManufacturingJobsListScreen.expectCurrentWorkplace(masterdata.workplaces.wpA.name);
    });

    await test.step('While this screen stays open, the operator\'s workstation switches to WS2 (workplace B)', async () => {
        // Not a fabricated state: this posts to the very endpoint the workstation app posts to on a
        // scan. It stands in for the real-world trigger — the operator scanning in a second app
        // instance (installed PWA plus a browser tab) while this screen remains mounted.
        await Backend.assignWorkstationByQRCode({ qrCode: masterdata.resources.WS2.qrCode });
        const { assignedWorkplace } = await Backend.getCurrentWorkplace();
        expect(assignedWorkplace?.name).toBe(masterdata.workplaces.wpB.name);
    });

    await test.step('Returning to the screen must re-read the operator context — it must not stay on WS1 / A', async () => {
        await AppLifecycleComponent.leaveAndReturnToForeground();

        // RED before the fix: both of these still show WS1 / wpA, because the screen read the
        // operator's workstation and workplace once at mount and has no refresh trigger at all.
        await ManufacturingJobsListScreen.expectCurrentWorkstation(masterdata.resources.WS2.name);
        await ManufacturingJobsListScreen.expectCurrentWorkplace(masterdata.workplaces.wpB.name);
    });
});
