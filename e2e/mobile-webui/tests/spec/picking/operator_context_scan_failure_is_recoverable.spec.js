import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { ViewHeaderComponent } from '../../utils/components/ViewHeaderComponent';
import { restoreConnectionFor, simulateConnectionLossFor } from '../../utils/network';

// English caption of the operator-context header row (general.workplace).
const HEADER_WORKPLACE = 'Workplace';

// The endpoint the Packing jobs list ASSIGNS the scanned workplace through (as opposed to the plain
// GET it reads the current one from).
const WORKPLACE_ASSIGN_ENDPOINT = '**/api/v2/workplace/*/assign';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            // Deliberately NO workplace on the user: the jobs list is what has to ask for it.
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    // The Packing app only asks for a workplace when an active one is required.
                    activeWorkplaceRequired: true,
                    // A picking profile is rejected without at least one pick-to structure; the
                    // scenario never reaches a job, so the simplest one will do.
                    pickTo: ['CU'],
                }
            },
            warehouses: { wh: {} },
            workplaces: { wpA: {} },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A workplace scan that fails because the connection dropped is recoverable', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.3');
    allure.story('A dropped connection during a workplace scan must be retryable, not a vanishing toast');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator opens Packing, which asks for a workplace', async () => {
        await ApplicationsListScreen.startPickingApplication();
        await PickingJobsListScreen.expectAsksForWorkplace();
    });

    await test.step('The connection drops, then the operator scans workplace A', async () => {
        await simulateConnectionLossFor(WORKPLACE_ASSIGN_ENDPOINT);
        await PickingJobsListScreen.typeWorkplaceQRCode(masterdata.workplaces.wpA.qrCode);

        // Nothing reached the server, so retrying the very same scan may well succeed. The operator
        // must be offered that retry on screen — not a toast that fades while they are still holding
        // the scanner, leaving them staring at the scan prompt with no idea the scan failed.
        await PickingJobsListScreen.expectConnectionErrorPanel();
    });

    await test.step('The connection comes back and the operator retries -> workplace A is assigned', async () => {
        await restoreConnectionFor(WORKPLACE_ASSIGN_ENDPOINT);
        await PickingJobsListScreen.retryLoadingOperatorContext();

        // Retry re-fires the SCAN, so the operator ends up on the workplace they scanned — being put
        // back in front of the scan prompt would mean the scan was silently dropped.
        await PickingJobsListScreen.expectNoConnectionErrorPanel();
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });
    });
});
