import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { WorkplaceManagerScreen } from "../../utils/screens/workplaceManager/WorkplaceManagerScreen";
import { ManufacturingJobsListScreen } from "../../utils/screens/manufacturing/ManufacturingJobsListScreen";
import { ViewHeaderComponent } from "../../utils/components/ViewHeaderComponent";

// English captions of the two operator-context header rows (general.workplace / general.workstation).
const HEADER_WORKPLACE = 'Workplace';
const HEADER_WORKSTATION = 'Workstation';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // The Production app only asks for a workstation — and only filters its job list by it —
            // when scanning a resource is required.
            mobileConfig: { manufacturing: { isScanResourceRequired: true } },
            warehouses: { whA: {}, whB: {} },
            // Two workplaces linked to DIFFERENT workstations, so an un-refreshed workplace is visible.
            workplaces: {
                wpA: { warehouse: 'whA' },
                wpB: { warehouse: 'whB' },
            },
            resources: {
                WS1: { type: 'WS', workplace: 'wpA' },
            },
            products: {
                COMP1: {},
                BOM: { bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
            },
            // A production order that belongs to NO workstation: it is offered while the operator has
            // no workstation, and must disappear once they are on WS1.
            manufacturingOrders: {
                PP1: { warehouse: 'whA', product: 'BOM', qty: 10, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Scanning the workstation on the Production jobs list updates workplace and job list', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.tag('F8046: Workstation');
    allure.tag('F8046');  // Standalone tag for Tags section;
    allure.story('The jobs list reflects the workstation the operator just scanned');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('The operator starts out on workplace B', async () => {
        await WorkplaceManagerScreen.scanWorkplace(masterdata.workplaces.wpB.qrCode);
        await WorkplaceManagerScreen.goBack();
    });

    await test.step('Open Production -> it asks for a workstation', async () => {
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.expectAsksForWorkstation();
    });

    await test.step('Scan workstation WS1', async () => {
        await ManufacturingJobsListScreen.scanWorkstation(masterdata.resources.WS1.qrCode);
        await ManufacturingJobsListScreen.expectDoesNotAskForWorkstation();
    });

    await test.step('The system of record has moved the operator to workplace A', async () => {
        // Asserted BEFORE the screen assertions below, so that a failure there can only mean the
        // SCREEN is stale: the server has demonstrably already moved the operator.
        const { assignedWorkplace } = await Backend.getCurrentWorkplace();
        expect(assignedWorkplace?.name).toBe(masterdata.workplaces.wpA.name);
    });

    await test.step('The operator sees workplace A and only WS1 jobs', async () => {
        // Scanning the workstation moves the operator to the workplace it belongs to (A). The header
        // must show where the operator now IS — not the workplace they came from (B).
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKSTATION, value: masterdata.resources.WS1.name });
        await ViewHeaderComponent.expectProperty({ caption: HEADER_WORKPLACE, value: masterdata.workplaces.wpA.name });

        // The offered jobs must be the ones for WS1. PP1 belongs to no workstation, so an operator
        // standing at WS1 must not be offered it; seeing it means the list was never refreshed
        // for the workstation that was just scanned.
        await ManufacturingJobsListScreen.expectJobNotListed({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
    });
});
