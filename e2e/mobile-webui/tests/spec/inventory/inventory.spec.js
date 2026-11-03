import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { InventoryJobsListScreen } from '../../utils/screens/inventory/InventoryJobsListScreen';
import { InventoryJobScreen } from '../../utils/screens/inventory/InventoryJobScreen';
import { InventoryScanScreen } from '../../utils/screens/inventory/InventoryScanScreen';
import { expectErrorToast } from '../../utils/common';
import { expect } from '@playwright/test';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            warehouses: { "wh": {} },
            workplaces: { workplace1: { warehouse: 'wh' } },
            products: { "P1": {} },
            packingInstructions: { "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 }, },
            handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' } },
            inventories: {
                "inv1": {
                    warehouse: 'wh',
                    date: '2025-03-01T00:00:00.000+02:00',
                    products: ['P1'],
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Simple inventory test', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5310');
    allure.story('Inventory - Basic Count');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();

    await InventoryJobsListScreen.startJob({ index: 1 })
    await InventoryJobScreen.expectLineButton({
        productId: masterdata.products.P1.id,
        locatorId: masterdata.warehouses.wh.locatorId,
        qtyBooked: '80 Stk',
        qtyCount: '80 Stk',
    })
    await InventoryJobScreen.countHU({
        locatorQRCode: masterdata.warehouses.wh.locatorQRCode,
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        expectQtyBooked: '80 Stk',
        qtyCount: 90,
        attributes: {
            'HU_BestBeforeDate': '12.11.2029',
            'Lot-Nummer': 'lot33',
        }
    })
    await InventoryJobScreen.expectLineButton({
        productId: masterdata.products.P1.id,
        locatorId: masterdata.warehouses.wh.locatorId,
        qtyBooked: '80 Stk',
        qtyCount: '90 Stk',
    })

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: {
                huStatus: 'A',
                storages: { P1: '80 PCE' },
                attributes: {
                    'HU_BestBeforeDate': null,
                    'Lot-Nummer': null,
                },
            },
        },
    });

    await InventoryJobScreen.complete();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: {
                huStatus: 'A',
                storages: { P1: '90 PCE' },
                attributes: {
                    'HU_BestBeforeDate': '2029-11-12',
                    'Lot-Nummer': 'lot33',
                },
            },
        },
    });

});

// noinspection JSUnusedLocalSymbols
test('Scan locator QR code where HU is expected in inventory → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5310');
    allure.story('Inventory - Scan errors');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();
    await InventoryJobsListScreen.startJob({ index: 1 });
    await InventoryJobScreen.openScanHUStep({ locatorQRCode: masterdata.warehouses.wh.locatorQRCode });

    await expectErrorToast('Scan locator QR code where HU is expected', async () => {
        await InventoryScanScreen.typeQRCode(masterdata.warehouses.wh.locatorQRCode);
        await InventoryScanScreen.waitForPanel('FillData');
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_WRONG_TYPE_LOCATOR');
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan workplace QR code where HU is expected in inventory → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5310');
    allure.story('Inventory - Scan errors');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();
    await InventoryJobsListScreen.startJob({ index: 1 });
    await InventoryJobScreen.openScanHUStep({ locatorQRCode: masterdata.warehouses.wh.locatorQRCode });

    await expectErrorToast('Scan workplace QR code where HU is expected', async () => {
        await InventoryScanScreen.typeQRCode(masterdata.workplaces.workplace1.qrCode);
        await InventoryScanScreen.waitForPanel('FillData');
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_WRONG_TYPE');
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan same HU twice in one inventory job → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5310');
    allure.story('Inventory - Scan errors');
    allure.severity('normal');

    // Two inventory lines (P1+HU1, P2+HU2) so that after counting HU1,
    // P2's uncounted line keeps the locator eligible for resolveLocator,
    // allowing the second HU1 scan to reach resolveHU which throws HU_ALREADY_COUNTED.
    // createDraftLines only creates a line when a HU exists for the product,
    // so HU2 is required for the P2 line to appear in the inventory.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            warehouses: { "wh": {} },
            workplaces: { workplace1: { warehouse: 'wh' } },
            products: { "P1": {}, "P2": {} },
            packingInstructions: {
                "PI1": { lu: "LU1", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU2", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI1' },
                "HU2": { product: 'P2', warehouse: 'wh', packingInstructions: 'PI2' },
            },
            inventories: {
                "inv1": {
                    warehouse: 'wh',
                    date: '2025-03-01T00:00:00.000+02:00',
                    products: ['P1', 'P2'],
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();
    await InventoryJobsListScreen.startJob({ index: 1 });

    await InventoryJobScreen.countHU({
        locatorQRCode: masterdata.warehouses.wh.locatorQRCode,
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        qtyCount: 80,
    });

    await InventoryJobScreen.openScanHUStep({ locatorQRCode: masterdata.warehouses.wh.locatorQRCode });

    await expectErrorToast('Scan same HU twice in one inventory job', async () => {
        await InventoryScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
        await InventoryScanScreen.waitForPanel('FillData');
    }, ({ textContent }) => {
        expect(textContent).toContain('INVENTORY_HU_ALREADY_COUNTED');
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan HU not in this inventory → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5310');
    allure.story('Inventory - Scan errors');
    allure.severity('normal');

    // P2+HU2 exist in the warehouse but are NOT included in the inventory (products: ['P1'] only).
    // Scanning HU2 at the HU scan step should throw HU_NOT_IN_INVENTORY.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            warehouses: { "wh": {} },
            workplaces: { workplace1: { warehouse: 'wh' } },
            products: { "P1": {}, "P2": {} },
            packingInstructions: {
                "PI1": { lu: "LU1", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU2", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI1' },
                "HU2": { product: 'P2', warehouse: 'wh', packingInstructions: 'PI2' },
            },
            inventories: {
                "inv1": {
                    warehouse: 'wh',
                    date: '2025-03-01T00:00:00.000+02:00',
                    products: ['P1'],
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();
    await InventoryJobsListScreen.startJob({ index: 1 });
    await InventoryJobScreen.openScanHUStep({ locatorQRCode: masterdata.warehouses.wh.locatorQRCode });

    await expectErrorToast('Scan HU not in this inventory', async () => {
        await InventoryScanScreen.typeQRCode(masterdata.handlingUnits.HU2.qrCode);
        await InventoryScanScreen.waitForPanel('FillData');
    }, ({ textContent }) => {
        expect(textContent).toContain('INVENTORY_HU_NOT_IN_INVENTORY');
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan unrecognized barcode where HU is expected in inventory → user-friendly error', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5310');
    allure.story('Inventory - Scan errors');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();
    await InventoryJobsListScreen.startJob({ index: 1 });
    await InventoryJobScreen.openScanHUStep({ locatorQRCode: masterdata.warehouses.wh.locatorQRCode });

    await expectErrorToast('Scan unrecognized barcode where HU is expected', async () => {
        await InventoryScanScreen.typeQRCode('TOTALLY_UNKNOWN_FORMAT_XYZ');
        await InventoryScanScreen.waitForPanel('FillData');
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_NOT_RECOGNIZED');
    });
});

