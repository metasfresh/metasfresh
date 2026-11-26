import { Backend, getBackendBaseUrl } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { captionStartsWithField } from '../../utils/launcherCaption';
import { FAST_ACTION_TIMEOUT, page as currentPage, SLOW_ACTION_TIMEOUT } from '../../utils/common';

// With the default filter TodayDateStartSchedule the manufacturing jobs list shows only the orders that are
// scheduled to start today, plus the orders the operator already started (whatever their start date).
// The list is capped by WorkflowRestAPIService.LaunchersLimit and sorted by DocumentNo, so the 4 yesterday
// orders (lowest DocumentNos) would crowd today's order out of the list if the filter were off.

const SYSCONFIG_defaultFilters = 'mobileui.manufacturing.defaultFilters';
const SYSCONFIG_LaunchersLimit = 'WorkflowRestAPIService.LaunchersLimit';

// noon of yesterday / today / tomorrow in the SERVER's time zone: the server's "today" filter decides which day an
// order is on, so the dates must not come from the clock/time zone of the machine that runs the test
let serverDates = null;

test.afterEach(() => {
    serverDates = null;
});

const manufacturingOrder = ({ qty }) => ({
    warehouse: 'wh',
    product: 'BOM',
    qty,
    datePromised: serverDates.todayNoon,
});

const createMasterdataWithoutOrders = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: {
                [SYSCONFIG_defaultFilters]: 'TodayDateStartSchedule',
                // temporary: counting and logging in must not hit the (default) limit; set to N + 3 below
                [SYSCONFIG_LaunchersLimit]: '10000',
            },
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            warehouses: { wh: {} },
            products: {
                COMP1: {},
                BOM: { bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
            },
            handlingUnits: {
                HU_COMP1: { product: 'COMP1', warehouse: 'wh', qty: 1000 },
            },
        },
    });
};

/** all jobs the jobs-list API returns for the logged-in user (started and not started), with the limit lifted */
const countJobsOfUser = async ({ authToken }) => {
    const backendBaseUrl = await getBackendBaseUrl();
    const response = await currentPage.request.post(`${backendBaseUrl}/userWorkflows/launchers/query`, {
        data: { applicationId: 'mfg', limit: 10000 },
        headers: { 'Content-Type': 'application/json', 'Authorization': authToken },
    });
    expect(response.ok(), `jobs-list API status ${response.status()}`).toBeTruthy();
    const body = await response.json();
    return (body.launchers ?? []).length;
};

const openJobsList = async () => {
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
};

const jobButton = ({ documentNo }) => currentPage.locator('.wflauncher-button').filter({ hasText: captionStartsWithField(documentNo) });

const expectJobListed = async ({ documentNo }) => await test.step(`Expect job ${documentNo} listed`, async () => {
    await expect(jobButton({ documentNo })).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
});

// noinspection JSUnusedLocalSymbols
test('todayOnlyJobsList_showsTodayAndStarted_hidesOtherDays', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8032: Manufacturing Job List');
    allure.tag('F8032');
    allure.tag('F8034');
    allure.story('Jobs list: today-only default filter, started jobs stay listed');
    allure.severity('critical');

    serverDates = await Backend.getServerDates();
    console.log(`Server time zone: ${serverDates.zoneId}; today noon ${serverDates.todayNoon}`);
    const base = await createMasterdataWithoutOrders();

    const { authToken } = await LoginScreen.login(base.login.user);

    // N: all jobs this user already sees with the filter on (started + not started), left by earlier specs
    const n = await countJobsOfUser({ authToken });
    console.log(`Jobs already listed for the user: N=${n}`);
    await Backend.setSysconfigs({ [SYSCONFIG_LaunchersLimit]: String(n + 3) });

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            manufacturingOrders: {
                Y1: manufacturingOrder({ qty: 1 }),
                Y2: manufacturingOrder({ qty: 2 }),
                Y3: manufacturingOrder({ qty: 3 }),
                Y4: manufacturingOrder({ qty: 4 }),
                T1: manufacturingOrder({ qty: 5 }),
                TOMORROW: manufacturingOrder({ qty: 6 }),
            },
        },
    });
    const documentNo = (identifier) => masterdata.manufacturingOrders[identifier].documentNo;

    // the orders are created for today; place them: 4 yesterday, 1 today (T1), 1 tomorrow
    for (const identifier of ['Y1', 'Y2', 'Y3', 'Y4']) {
        await Backend.setManufacturingOrderDateStartSchedule({ documentNo: documentNo(identifier), dateStartSchedule: serverDates.yesterdayNoon });
    }
    await Backend.setManufacturingOrderDateStartSchedule({ documentNo: documentNo('TOMORROW'), dateStartSchedule: serverDates.tomorrowNoon });

    await test.step('Start yesterday order Y1 (it is listed while scheduled today), then schedule it back to yesterday', async () => {
        await Backend.setManufacturingOrderDateStartSchedule({ documentNo: documentNo('Y1'), dateStartSchedule: serverDates.todayNoon });
        await openJobsList();
        await ManufacturingJobsListScreen.startJob({ documentNo: documentNo('Y1') });
        await ManufacturingJobScreen.goBack();
        await ManufacturingJobsListScreen.waitForScreen();
        await Backend.setManufacturingOrderDateStartSchedule({ documentNo: documentNo('Y1'), dateStartSchedule: serverDates.yesterdayNoon });
    });

    await test.step('List shows today\'s order and the started one only', async () => {
        await ManufacturingJobsListScreen.goBack();
        await openJobsList();
        await expectJobListed({ documentNo: documentNo('T1') });
        await expectJobListed({ documentNo: documentNo('Y1') });
        for (const identifier of ['Y2', 'Y3', 'Y4', 'TOMORROW']) {
            await ManufacturingJobsListScreen.expectJobNotListed({ documentNo: documentNo(identifier) });
        }
    });

    await test.step('An unstarted yesterday order moved to today appears', async () => {
        await Backend.setManufacturingOrderDateStartSchedule({ documentNo: documentNo('Y2'), dateStartSchedule: serverDates.todayNoon });
        await ManufacturingJobsListScreen.goBack();
        await openJobsList();
        await expectJobListed({ documentNo: documentNo('Y2') });
        await expectJobListed({ documentNo: documentNo('T1') });
        await expectJobListed({ documentNo: documentNo('Y1') });
        for (const identifier of ['Y3', 'Y4', 'TOMORROW']) {
            await ManufacturingJobsListScreen.expectJobNotListed({ documentNo: documentNo(identifier) });
        }
    });
});
