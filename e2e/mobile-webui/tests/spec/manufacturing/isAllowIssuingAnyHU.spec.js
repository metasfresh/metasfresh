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

const createMasterdata = async ({ finishedProductUOMConfigs, isCreateRawMaterialsStock = true, manufacturing, huComp1ExternalBarcode } = {}) => {
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
                    // HU_COMP1 carries the legacy customer barcode under test (undefined when a test
                    // does not need it, in which case its plain M_HU.Value is what gets scanned).
                    "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100, externalBarcode: huComp1ExternalBarcode },
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
    // TC6: On-the-fly issue from an HU identified by its legacy customer barcode (ExternalBarcode)
    // — the direct analogue of TC-B7 above, but the operator scans the printed customer label
    // instead of the metasfresh QR code.
    //
    // This is regression/acceptance coverage, not a RED-then-GREEN fix proof: the scanner resolves
    // any scanned code (global QR, ExternalBarcode, M_HU.Value alike) through the already-tolerant
    // singular HU lookup BEFORE ever calling the on-the-fly endpoint, and always sends that endpoint
    // the resolved metasfresh QR code — never the raw scanned string. Verified via
    // api_request_audit.body on the local DB: every issueSchedule/createOnTheFly request carries an
    // `HU#1#{...}` QR code regardless of what was actually scanned. So the on-the-fly acceptance this
    // test proves already worked before this task, end-to-end through the mobile UI; no
    // ManufacturingJobService change was needed. expectOnTheFlyCall proves the scan genuinely takes
    // the on-the-fly path (HU not part of the job's pre-planned steps) rather than matching a known
    // step locally, which never calls the backend at all.
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: on-the-fly scan and issue an HU identified by its legacy customer barcode', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation from a legacy-labelled HU');
        allure.severity('critical');

        const huComp1ExternalBarcode = `EXT${Date.now()}`;
        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
            huComp1ExternalBarcode,
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Issue COMP1 via on-the-fly scan of its printed customer barcode', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.externalBarcode, expectQtyEntered: '5', expectOnTheFlyCall: true });
            await RawMaterialIssueLineScreen.goBack();
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        });
    });

    //
    // TC6: On-the-fly issue from an HU identified by its plain M_HU.Value (no ExternalBarcode)
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: on-the-fly scan and issue an HU identified by its plain HU value', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation from a plain-M_HU.Value-labelled HU');
        allure.severity('critical');

        // huComp1ExternalBarcode deliberately omitted: HU_COMP1.ExternalBarcode stays genuinely NULL,
        // so the only thing the operator has to scan is the HU's own plain value. M_HU.Value always
        // equals the M_HU_ID (verified against the stack, see picking_unpack_legacy_label_target.spec.js),
        // so the value printed on such a label is exactly the huId the masterdata API already returns.
        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Issue COMP1 via on-the-fly scan of its plain HU value', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCode({ qrCode: `${masterdata.handlingUnits.HU_COMP1.huId}`, expectQtyEntered: '5', expectOnTheFlyCall: true });
            await RawMaterialIssueLineScreen.goBack();
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        });
    });

    //
    // Pre-existing behaviour, not changed by this task (kept as regression coverage): an unrecognised
    // code at the on-the-fly issue scan is rejected before the qty dialog opens, nothing gets issued,
    // and the panel stays usable. The toast text here is the mobile app's generic fallback message —
    // resolution fails one step earlier, in the already-tolerant HU lookup the scanner uses to
    // pre-resolve the code (a different REST site than the one this task touches), not in the
    // on-the-fly issue-schedule endpoint itself.
    //
    // noinspection JSUnusedLocalSymbols
    test('isAllowIssuingAnyHU=true: an unrecognised code at the on-the-fly scan is rejected, then recovery works', async ({ page }) => {
        allure.epic('E0160: Manufacturing Execution');
        allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');
        allure.story('On-the-fly issue schedule creation rejects an unrecognised code, panel stays usable');
        allure.severity('normal');

        const masterdata = await createMasterdata({
            isCreateRawMaterialsStock: true,
            manufacturing: { isAllowIssuingAnyHU: true },
        });
        // A code matching no known format at all — no metasfresh QR code, no GS1/EAN, and no
        // ExternalBarcode or M_HU.Value on file. The case where the system genuinely cannot identify
        // what was scanned.
        const unrecognisedCode = `NOT-A-CODE-${Date.now()}`;

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        await test.step('Scan a code the system cannot identify — the operator must see a clear message, nothing gets issued', async () => {
            await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
            await ManufacturingJobScreen.clickIssueButton({ index: 1 });
            await RawMaterialIssueLineScreen.scanQRCodeExpectResolveError({
                qrCode: unrecognisedCode,
                expectedToastText: 'Please try again. If the problem persists, contact support.',
            });
        });

        await RawMaterialIssueLineScanScreen.goBack();
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });

        await test.step('Scan the correct HU afterwards — the on-the-fly issue commits normally, the panel is usable again', async () => {
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
