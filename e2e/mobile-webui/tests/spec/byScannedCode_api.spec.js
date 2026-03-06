import { test } from "../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend, getBackendBaseUrl } from "../utils/screens/Backend";
import { LoginScreen } from "../utils/screens/LoginScreen";
import { page } from "../utils/common";
import { expect } from '@playwright/test';

/**
 * Regression tests for the QR code # truncation fix (me03#28652).
 *
 * All metasfresh QR codes use the format TYPE#VERSION#JSON_PAYLOAD.
 * These endpoints previously used GET+@RequestParam, which caused
 * the # characters to be interpreted as URL fragment delimiters,
 * truncating the QR code before it reached the server.
 *
 * Fix: Changed all three to POST+@RequestBody.
 * These tests verify that QR codes with # characters are correctly
 * transmitted and processed by each endpoint.
 */

// noinspection JSUnusedLocalSymbols
test('POST /picking/hu/byScannedCode accepts QR codes with # characters', async ({ page: testPage }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('API: byScannedCode accepts # in QR codes');
    allure.severity('critical');
    allure.issue('28652', 'QR code truncation when # in scanned code');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            products: { "P1": { price: 1 } },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);

    const backendBaseUrl = await getBackendBaseUrl();
    const huQRCode = masterdata.handlingUnits.HU1.qrCode;

    await test.step('Verify HU QR code contains # separator', async () => {
        expect(huQRCode).toContain('#');
    });

    await test.step('POST /picking/hu/byScannedCode with QR code containing #', async () => {
        const response = await page.request.post(`${backendBaseUrl}/picking/hu/byScannedCode`, {
            data: { scannedCode: huQRCode },
            headers: {
                'Content-Type': 'application/json',
                'Authorization': masterdata.login.user.token,
            }
        });

        expect(response.status()).toBe(200);
        const body = await response.json();
        expect(body).toBeTruthy();
        expect(body.huQRCode).toBeTruthy();
        expect(body.huQRCode.code).toContain('#');
    });
});

// noinspection JSUnusedLocalSymbols
test('POST /distribution/hu/byScannedCode accepts QR codes with # characters', async ({ page: testPage }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('API: byScannedCode accepts # in QR codes');
    allure.severity('critical');
    allure.issue('28652', 'QR code truncation when # in scanned code');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 100 }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: 100 }],
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);

    const backendBaseUrl = await getBackendBaseUrl();
    const huQRCode = masterdata.handlingUnits.HU1.qrCode;

    await test.step('Verify HU QR code contains # separator', async () => {
        expect(huQRCode).toContain('#');
    });

    await test.step('POST /distribution/hu/byScannedCode with QR code containing #', async () => {
        const response = await page.request.post(`${backendBaseUrl}/distribution/hu/byScannedCode`, {
            data: { scannedCode: huQRCode },
            headers: {
                'Content-Type': 'application/json',
                'Authorization': masterdata.login.user.token,
            }
        });

        expect(response.status()).toBe(200);
        const body = await response.json();
        expect(body).toBeTruthy();
        expect(body.qrCode).toBeTruthy();
    });
});

// noinspection JSUnusedLocalSymbols
test('POST /material/warehouses/resolveLocator accepts QR codes with # characters', async ({ page: testPage }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F4065: MobileUI HU Manager');
    allure.tag('F4065');
    allure.story('API: resolveLocator accepts # in QR codes');
    allure.severity('critical');
    allure.issue('28652', 'QR code truncation when # in scanned code');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            warehouses: { "wh1": {} },
        }
    });

    await LoginScreen.login(masterdata.login.user);

    const backendBaseUrl = await getBackendBaseUrl();
    const locatorQRCode = masterdata.warehouses.wh1.locatorQRCode;

    await test.step('Verify locator QR code contains # separator', async () => {
        expect(locatorQRCode).toContain('#');
    });

    await test.step('POST /material/warehouses/resolveLocator with QR code containing #', async () => {
        const response = await page.request.post(`${backendBaseUrl}/material/warehouses/resolveLocator`, {
            data: { scannedBarcode: locatorQRCode },
            headers: {
                'Content-Type': 'application/json',
                'Authorization': masterdata.login.user.token,
            }
        });

        expect(response.status()).toBe(200);
        const body = await response.json();
        expect(body).toBeTruthy();
        expect(body.locator).toBeTruthy();
    });
});
