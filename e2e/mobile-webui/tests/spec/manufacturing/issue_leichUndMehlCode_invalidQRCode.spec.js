import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import { expectErrorToast } from '../../utils/common';

// A Leich+Mehl (LM) code scanned on a manufacturing raw-material issue line must get the answer it got before
// the HU Manager "create from label" feature: "invalid QR code" (the HU lookup shared by every scanner screen
// is unchanged; the feature adds no answer of its own for LM codes there).
// noinspection JSUnusedLocalSymbols
test('raw-material issue: an LM code is answered "invalid QR code", nothing is issued', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F5120');
    allure.story('Shared HU lookup keeps its answer for LM codes');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            // no step is preselected: the scan on the issue line goes to the backend HU lookup
            mobileConfig: { manufacturing: { isAllowIssuingAnyHU: true } },
            warehouses: { wh: {} },
            products: {
                COMP1: {},
                BOM: { bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
            },
            packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4 } },
            handlingUnits: { HU_COMP1: { product: 'COMP1', warehouse: 'wh', qty: 100 } },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 5, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
    await ManufacturingJobScreen.clickIssueButton({ index: 1 });

    await RawMaterialIssueLineScreen.openScanScreen();
    await expectErrorToast('LM code on the issue line', async () => {
        await RawMaterialIssueLineScanScreen.typeQRCode('LMQ#1#25.5#31.12.2025#L2311');
    }, async ({ textContent }) => {
        expect(textContent).toMatch(/invalid/i);
        expect(textContent).not.toMatch(/not found/i);
    });
});
