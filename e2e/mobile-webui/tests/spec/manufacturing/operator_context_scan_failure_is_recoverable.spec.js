import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { ViewHeaderComponent } from "../../utils/components/ViewHeaderComponent";
import { restoreConnectionFor, simulateConnectionLossFor } from "../../utils/network";

// English captions of the two operator-context header rows (general.workplace / general.workstation).
const HEADER_WORKPLACE = 'Workplace';
const HEADER_WORKSTATION = 'Workstation';

// The endpoint the Production jobs list ASSIGNS the scanned workstation through (as opposed to the
// plain GET it reads the current one from).
const WORKSTATION_ASSIGN_ENDPOINT = '**/api/v2/workstation/assign';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // The Production app only asks for a workstation when scanning a resource is required.
            mobileConfig: { manufacturing: { isScanResourceRequired: true } },
            warehouses: { whA: {} },
            workplaces: { wpA: { warehouse: 'whA' } },
            resources: { WS1: { type: 'WS', workplace: 'wpA' } },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A workstation scan that fails because the connection dropped is recoverable', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('A dropped connection during a workstation scan must be retryable, not a vanishing toast');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator opens Production, which asks for a workstation', async () => {
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.expectAsksForWorkstation();
    });

    await test.step('The connection drops, then the operator scans WS1', async () => {
        await simulateConnectionLossFor(WORKSTATION_ASSIGN_ENDPOINT);
        await ManufacturingJobsListScreen.typeWorkstationQRCode(masterdata.resources.WS1.qrCode);

        // Nothing reached the server, so retrying the very same scan may well succeed. The operator
        // must be offered that retry on screen — not a toast that fades while they are still holding
        // the scanner, leaving them staring at the scan prompt with no idea the scan failed.
        await ManufacturingJobsListScreen.expectConnectionErrorPanel();
    });

    await test.step('The connection comes back and the operator retries -> WS1 is assigned', async () => {
        await restoreConnectionFor(WORKSTATION_ASSIGN_ENDPOINT);
        await ManufacturingJobsListScreen.retryLoadingOperatorContext();

        // Retry re-fires the SCAN, so the operator ends up on the workstation they scanned — being
        // put back in front of the scan prompt would mean the scan was silently dropped.
        await ManufacturingJobsListScreen.expectNoConnectionErrorPanel();
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS1.name });
        // Assigning WS1 moves the operator to its workplace, so the header shows that too.
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
    });
});
