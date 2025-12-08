import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { expect } from "@playwright/test";
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

test.describe('Manufacturing - Auto-issue read-only UI', () => {
    test('Auto-issue first line hides Scan button; manual second line shows it (assume same order as created)', async () => {
        const masterdata = await createMasterdata();

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();

        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        // 1) Auto-issue line (created first) => Scan button hidden
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.expectScanButtonVisible({ visible: false });
        await RawMaterialIssueLineScreen.goBack();

        // 2) Manual line (created second) => Scan button visible
        await ManufacturingJobScreen.clickIssueButton({ index: 2 });
        await RawMaterialIssueLineScreen.expectScanButtonVisible({ visible: true });
    });
});
