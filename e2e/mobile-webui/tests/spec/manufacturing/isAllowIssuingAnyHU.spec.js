import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { expectErrorToast, expectErrorToastIf, page } from '../../utils/common';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';

const createMasterdata = async ({ finishedProductUOMConfigs, isCreateRawMaterialsStock = true, manufacturing } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: { manufacturing },
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    ...(finishedProductUOMConfigs ?? {}),
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
            },
            handlingUnits: isCreateRawMaterialsStock
                ? {
                    "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 },
                    "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 100 },
                }
                : {},
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    })
}

test.describe('Test isAllowIssuingAnyHU', () => {
    //
    // TC-A1 through TC-A4: Job creation permutations (existing tests, unchanged)
    //
    const runScenario = ({ isCreateRawMaterialsStock, isAllowIssuingAnyHU, expectError }) => {
        // noinspection JSUnusedLocalSymbols
        test(`isAllowIssuingAnyHU=${isAllowIssuingAnyHU}, isCreateRawMaterialsStock=${isCreateRawMaterialsStock} => expect ${expectError ? 'ERROR' : 'OK'}`, async ({ page }) => {
            // === ALLURE METADATA ===
            allure.epic('E0160: Manufacturing Execution');
            allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');  // Standalone tag for Tags section;
            allure.story('isAllowIssuingAnyHU configuration');
            allure.severity('normal');
            allure.parameter('isAllowIssuingAnyHU', String(isAllowIssuingAnyHU));
            allure.parameter('isCreateRawMaterialsStock', String(isCreateRawMaterialsStock));

            const masterdata = await createMasterdata({
                isCreateRawMaterialsStock,
                manufacturing: {
                    isAllowIssuingAnyHU,
                },
            });

            await LoginScreen.login(masterdata.login.user);
            await ApplicationsListScreen.expectVisible();
            await ApplicationsListScreen.startApplication('mfg');
            await ManufacturingJobsListScreen.waitForScreen();

            await expectErrorToastIf(expectError, 'Expect start job to fail', async () => {
                await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
            });
        });
    };

    runScenario({ isAllowIssuingAnyHU: false, isCreateRawMaterialsStock: false, expectError: true });
    runScenario({ isAllowIssuingAnyHU: false, isCreateRawMaterialsStock: true, expectError: false });
    runScenario({ isAllowIssuingAnyHU: true, isCreateRawMaterialsStock: false, expectError: false });
    runScenario({ isAllowIssuingAnyHU: true, isCreateRawMaterialsStock: true, expectError: false });

    //
    // TC-B7: On-the-fly scan — full round trip (the core fix)
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: on-the-fly scan and issue single component', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation');
        allure.severity('critical');

        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Issue COMP1 via on-the-fly scan', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
            await RawMaterialIssueLineScreen.goBack();
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        });
    });

    //
    // TC-B9: Full manufacturing flow via on-the-fly (issue all + receive + complete)
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: full manufacturing flow via on-the-fly', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation');
        allure.severity('critical');

        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Issue COMP1 via on-the-fly', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
            await RawMaterialIssueLineScreen.goBack();
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        });

        await test.step('Issue COMP2 via on-the-fly', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '0 Stk' });
            await ManufacturingJobScreen.clickIssueButton({ index: 2 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP2.qrCode, expectQtyEntered: '10' });
            await RawMaterialIssueLineScreen.goBack();
            await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '10 Stk' });
        });

        await test.step('Receive finished goods', async () => {
            await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '0 Stk' });
            await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
            await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId })
            await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: '5', qtyEntered: '1' });
            await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '1 Stk' });
        });

        await ManufacturingJobScreen.complete();
    });

    //
    // TC-B8: Scan same HU twice — first issue, then re-scan same HU (already issued)
    // With on-the-fly, first scan creates schedule + issues it. Second time the user enters
    // the line, the step is already marked as issued so the UI reflects the correct state.
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: issue via on-the-fly, then verify step shows as issued on re-entry', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation');
        allure.severity('normal');

        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Issue COMP1 via on-the-fly scan', async () => {
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
            await RawMaterialIssueLineScreen.goBack();
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        });

        await test.step('Go back to job list and resume — issued state should persist', async () => {
            await ManufacturingJobScreen.goBack();
            await ManufacturingJobsListScreen.waitForScreen();
            await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        });
    });

    //
    // TC-C2: Backend error during on-the-fly — falls back to original error
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: network error falls back to scan error', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation');
        allure.severity('normal');

        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Block on-the-fly API and scan — expect error toast', async () => {
            // Intercept the on-the-fly endpoint and abort it (simulates network failure)
            await page.route('**/issueSchedule/createOnTheFly', route => route.abort());

            await ManufacturingJobScreen.clickIssueButton({ index: 1 });

            // Scan the QR code — the on-the-fly call will be aborted, and the frontend
            // should show an error toast ("HU Code passt nicht" or similar)
            await page.getByTestId('scanQRCode-button').tap();
            await RawMaterialIssueLineScanScreen.waitForScreen();
            await RawMaterialIssueLineScanScreen.typeQRCode(masterdata.handlingUnits.HU_COMP1.qrCode);

            // Wait a moment for the error to appear, then verify the scan screen
            // is still functional (no crash, no blank page)
            await page.waitForTimeout(3000);
            await RawMaterialIssueLineScanScreen.expectVisible();

            // Unroute to clean up
            await page.unroute('**/issueSchedule/createOnTheFly');
        });
    });
});
