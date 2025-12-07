const { expect } = require('@playwright/test');
const { test } = require('../../playwright.config');
const { Backend } = require('../utils/Backend');
const { LoginPage } = require('../utils/pages/LoginPage');
const { DashboardPage } = require('../utils/pages/DashboardPage');
const { SalesOrderPage } = require('../utils/pages/SalesOrderPage');
const { ShipmentSchedulePage } = require('../utils/pages/ShipmentSchedulePage');
const { AllureHelpers } = require('../utils/AllureHelpers');

/**
 * Sales Order to Shipment Schedule E2E test suite.
 *
 * Features tested (from Google Sheets):
 * - F00100: Sales Order
 * - F00105: Sales Order Document (PDF)
 * - F00130: Shipment Schedule
 *
 * Tests the sales order to shipment schedule workflow:
 * 1. Create a sales order with customer and product
 * 2. Complete the sales order
 * 3. Generate and validate PDF (Alt+P)
 * 4. Navigate to Shipment Schedule (Alt+6)
 * 5. Validate the ordered quantity appears in the shipment schedule
 *
 * This validates language-independent selectors for:
 * - Sales order creation and completion
 * - PDF generation modal and download
 * - PDF content validation (document number, customer, product, quantity)
 * - Related documents navigation (Alt+6)
 * - Shipment schedule window and quantity field
 */

// Test cases for multi-language validation
const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
    test.describe(`Sales Order to Shipment Schedule (${label})`, () => {
        test(`Create SO and validate quantity in shipment schedule (${label} UI)`, async ({ page }) => {
            // === ALLURE METADATA ===
            // Link to features from Google Sheets
            await AllureHelpers.setFeature('F00100'); // Sales Order - also sets Epic: Sales
            await AllureHelpers.setStory('Create SO → View Shipment Schedule');
            await AllureHelpers.setSeverity('critical');
            await AllureHelpers.addParameter('Language', language);
            await AllureHelpers.addParameter('UI Label', label);
            await AllureHelpers.addTags('sales', 'shipment-schedule', 'pdf', language);

            await AllureHelpers.setDescription(`
## Test Scenario
This test validates the complete sales order to shipment schedule workflow:

1. **Create Sales Order** - New SO with customer and product line
2. **Complete Order** - Mark as completed to trigger downstream processes
3. **Generate PDF** - Create and validate Sales Order PDF document
4. **Navigate to Shipment Schedule** - Use Alt+6 to open related shipment schedule
5. **Verify Quantity** - Confirm ordered quantity appears in schedule

## Features Tested
- **F00100**: Sales Order
- **F00105**: Sales Order Document (PDF)
- **F00130**: Shipment Schedule

## Business Value
Ensures the sales-to-delivery flow works correctly across UI languages.
            `);

            // === TEST DATA CREATION ===
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: {
                        user: {
                            language,
                            firstname: 'first',
                            lastname: 'last'
                        },
                    },
                    bpartners: {
                        CUSTOMER1: {
                            isVendor: false,
                            isCustomer: true,
                            isSoPriceList: true,
                            name: 'Customer'
                        },
                    },
                    products: {
                        Product1: {
                            name: 'PROD',
                            type: 'Item',
                            prices: [
                                {
                                    price: 50.0,
                                    currencyCode: 'EUR',
                                },
                            ],
                        },
                    },
                },
            });

            // Attach test data summary to Allure report
            await AllureHelpers.attachTestData(masterdata);

            console.log(`[${language}] Master data created:`, {
                customer: masterdata.bpartners.CUSTOMER1.bpartnerCode,
                product: masterdata.products.Product1.productName,
            });

            // === TEST EXECUTION ===

            // Step 1: Login
            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();

            // Step 2: Create Sales Order
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();

            const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
            console.log(`[${language}] Sales Order ${recordId} created and saved`);

            // Add order line
            const orderLineData = {
                product: masterdata.products.Product1.productCode,
                quantity: '10',
                recordId,
            };
            await SalesOrderPage.addOrderLine(orderLineData);

            // Attach order line details as table
            await AllureHelpers.attachTable('Order Lines',
                [{
                    Product: masterdata.products.Product1.productCode,
                    Quantity: '10',
                    'Unit Price': '50.00 EUR',
                    'Line Total': '500.00 EUR'
                }],
                ['Product', 'Quantity', 'Unit Price', 'Line Total']
            );

            // Step 3: Complete the order
            await SalesOrderPage.complete();

            const soDocumentNo = await SalesOrderPage.getDocumentNo();
            expect(soDocumentNo).toBeTruthy();
            expect(soDocumentNo.length).toBeGreaterThan(0);

            // Add document number as parameter for easy identification
            await AllureHelpers.addParameter('Document No', soDocumentNo);

            console.log(`[${language}] Sales Order created: ${soDocumentNo}`);

            // Step 4: Generate and validate PDF
            await SalesOrderPage.openPrintModal();

            const download = await SalesOrderPage.downloadPDF();
            console.log(`[${language}] PDF downloaded: ${download.suggestedFilename()}`);

            // Attach PDF to Allure report
            const pdfPath = await download.path();
            await AllureHelpers.attachPdf('Sales Order PDF', pdfPath, {
                documentNo: soDocumentNo,
                customer: masterdata.bpartners.CUSTOMER1.bpartnerCode,
                product: masterdata.products.Product1.productCode,
                quantity: '10',
                language: language,
            });

            // Validate PDF content
            await SalesOrderPage.validatePdfContent(download, {
                documentNo: soDocumentNo,
                productCode: masterdata.products.Product1.productCode,
                quantity: '10',
                language,
            });

            console.log(`[${language}] PDF content validated successfully`);

            await SalesOrderPage.closePrintModal().catch(() => {});

            // Step 5: Navigate to Shipment Schedule
            await SalesOrderPage.openRelatedShipmentCandidate();
            await ShipmentSchedulePage.expectVisible();

            // Take screenshot of shipment schedule for report
            await AllureHelpers.attachScreenshot(page, 'Shipment Schedule View');

            console.log(`[${language}] Shipment Schedule opened for SO ${soDocumentNo}`);

            // Step 6: Validate ordered quantity
            await ShipmentSchedulePage.expectOrderedQuantity('10');

            console.log(`[${language}] Verified ordered quantity: 10`);

            // Attach validation summary
            await AllureHelpers.attachTable('Validation Results',
                [
                    { Check: 'Sales Order Created', Status: '✓ PASS', Value: soDocumentNo },
                    { Check: 'PDF Generated', Status: '✓ PASS', Value: download.suggestedFilename() },
                    { Check: 'Shipment Schedule Visible', Status: '✓ PASS', Value: 'Yes' },
                    { Check: 'Ordered Quantity', Status: '✓ PASS', Value: '10' },
                ],
                ['Check', 'Status', 'Value']
            );
        });
    });
});
