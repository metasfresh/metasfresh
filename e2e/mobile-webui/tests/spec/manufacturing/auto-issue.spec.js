import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';

// This spec validates that BOM lines configured with IssueOnlyForReceived are
// presented as read-only in the mobile UI and the Scan button is not shown.

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: {
                user: { language: 'en_US' },
            },
            mobileConfig: {},
            warehouses: {
                wh: {},
            },
            products: {
                COMP_AUTO: {},
                COMP_MANUAL: {},
                BOM: {
                    bom: {
                        lines: [
                            // Auto-issue component: the test framework supports an optional issueMethod flag
                            { product: 'COMP_AUTO', qty: 1, issueMethod: 'IssueOnlyForReceived' },
                            // Manual issue component
                            { product: 'COMP_MANUAL', qty: 1 },
                        ],
                    },
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU', product: 'BOM', qtyCUsPerTU: 2 },
            },
            handlingUnits: {
                HU_AUTO: { product: 'COMP_AUTO', warehouse: 'wh', qty: 100 },
                HU_MAN: { product: 'COMP_MANUAL', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                PP1: {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 3,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Auto-issue first line hides Scan button; manual second line shows it', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step('Line 1: Auto-issue line (created first) => Scan button hidden and no indicators', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 1, noIndicators: true })
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.expectScanButtonVisible({ visible: false });
        await RawMaterialIssueLineScreen.goBack();
    });

    await test.step('Line 2: Manual line (created second) => Scan button visible', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 2 })
        await ManufacturingJobScreen.clickIssueButton({ index: 2 });
        await RawMaterialIssueLineScreen.expectScanButtonVisible({ visible: true });
    });
});
