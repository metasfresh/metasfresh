import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkplaceManagerScreen } from "../../utils/screens/workplaceManager/WorkplaceManagerScreen";
import { WorkstationManagerScreen } from "../../utils/screens/workstationManager/WorkstationManagerScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { ViewHeaderComponent } from "../../utils/components/ViewHeaderComponent";
import { restoreConnectionFor, simulateConnectionLossFor } from "../../utils/network";

// English captions of the two operator-context header rows (general.workplace / general.workstation).
const HEADER_WORKPLACE = 'Workplace';
const HEADER_WORKSTATION = 'Workstation';

// Endpoints the Production jobs list reads the operator's context from.
const WORKPLACE_ENDPOINT = '**/api/v2/workplace';
const WORKSTATION_ENDPOINT = '**/api/v2/workstation';

const createMasterdata = async ({ isScanResourceRequired }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { manufacturing: { isScanResourceRequired } },
            warehouses: { whA: {} },
            workplaces: { wpA: { warehouse: 'whA' } },
            resources: { WS1: { type: 'WS', workplace: 'wpA' } },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A workplace that cannot be read because the connection dropped is recoverable', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('A dropped connection must not silently blank the operator context');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isScanResourceRequired: false });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator is working on workplace A', async () => {
        await WorkplaceManagerScreen.scanWorkplace(masterdata.workplaces.wpA.qrCode);
        await WorkplaceManagerScreen.goBack();
    });

    await test.step('The connection drops, then the operator opens Production', async () => {
        await simulateConnectionLossFor(WORKPLACE_ENDPOINT);
        await ApplicationsListScreen.startApplication('mfg');

        // The operator must be told the workplace could not be read and be offered a retry —
        // not left looking at a screen that simply shows no workplace at all, with no way back
        // short of restarting the app.
        await ManufacturingJobsListScreen.expectConnectionErrorPanel();
    });

    await test.step('The connection comes back and the operator retries -> workplace A is shown again', async () => {
        await restoreConnectionFor(WORKPLACE_ENDPOINT);
        await ManufacturingJobsListScreen.retryLoadingOperatorContext();

        await ManufacturingJobsListScreen.expectNoConnectionErrorPanel();
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
    });
});

// noinspection JSUnusedLocalSymbols
test('A workstation that cannot be read because the connection dropped is recoverable', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('A dropped connection must not look like a lost workstation assignment');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isScanResourceRequired: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator is set up on workstation WS1', async () => {
        await WorkstationManagerScreen.scanWorkstation(masterdata.resources.WS1.qrCode);
        await WorkstationManagerScreen.expectCurrentWorkstation(masterdata.resources.WS1.name);
        await WorkstationManagerScreen.goBack();
    });

    await test.step('The connection drops, then the operator opens Production', async () => {
        await simulateConnectionLossFor(WORKSTATION_ENDPOINT);
        await ApplicationsListScreen.startApplication('mfg');

        // The operator IS assigned to WS1 — the read just failed. Asking them to scan a workstation
        // is misleading (and rescanning fails too while the connection is down); the screen must
        // report the connection problem and offer a retry.
        await ManufacturingJobsListScreen.expectConnectionErrorPanel();
        await ManufacturingJobsListScreen.expectDoesNotAskForWorkstation();
    });

    await test.step('The connection comes back and the operator retries -> workstation WS1 is shown again', async () => {
        await restoreConnectionFor(WORKSTATION_ENDPOINT);
        await ManufacturingJobsListScreen.retryLoadingOperatorContext();

        await ManufacturingJobsListScreen.expectNoConnectionErrorPanel();
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS1.name });
    });
});
