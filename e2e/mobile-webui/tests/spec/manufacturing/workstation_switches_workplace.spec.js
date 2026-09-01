import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkstationManagerScreen } from "../../utils/screens/workstationManager/WorkstationManagerScreen";
import { WorkplaceManagerScreen } from "../../utils/screens/workplaceManager/WorkplaceManagerScreen";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            // No workplace preassigned to the login user — the operator's active workplace is
            // established purely by scanning, which is exactly what this scenario drives.
            login: { user: { language: "en_US" } },
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
