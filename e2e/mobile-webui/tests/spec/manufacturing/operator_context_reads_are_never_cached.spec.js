import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { recordResponsesFor } from "../../utils/network";

// End-to-end guard for the cache directives on the operator-context reads: proof that a real handheld
// request comes back uncacheable, asserted on the response the device actually receives.
//
// The servlet filter that sets the directive (https://github.com/metasfresh/metasfresh/pull/25368) is
// merged into this branch's base, so this is now a genuine end-to-end proof of the no-store contract,
// not a pending true-negative.

// Endpoints the Production screen reads the operator's context from.
const WORKPLACE_ENDPOINT = '/api/v2/workplace';
const WORKSTATION_ENDPOINT = '/api/v2/workstation';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // Required, or the Production screen never reads the workstation at all.
            mobileConfig: { manufacturing: { isScanResourceRequired: true } },
            warehouses: { whA: {} },
            workplaces: { wpA: { warehouse: 'whA' } },
            resources: { WS1: { type: 'WS', workplace: 'wpA' } },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('The operator context is re-read from the server, never served from the device cache', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('A handheld must never answer the operator-context reads from its own cache');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    // Installed before the app is opened, so the reads fired on the way into Production are recorded.
    const workplaceReads = recordResponsesFor(WORKPLACE_ENDPOINT);
    const workstationReads = recordResponsesFor(WORKSTATION_ENDPOINT);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('Open Production -> the operator context is read from the server', async () => {
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.expectAsksForWorkstation();
    });

    await test.step('Neither operator-context response may be stored by the device', async () => {
        workplaceReads.stopRecording();
        workstationReads.stopRecording();

        await workplaceReads.expectAllForbidStoring();
        await workstationReads.expectAllForbidStoring();
    });
});
