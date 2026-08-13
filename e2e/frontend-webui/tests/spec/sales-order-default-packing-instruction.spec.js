import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { getFieldData, getValidationStatus } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * A product's Standard-Packvorschrift (`M_HU_PI_Item_Product.IsDefaultForProduct`) must only be
 * pre-filled into the sales order line quick entry when a product price of the order's price list
 * version references it.
 *
 * Pricing matches on the packing instruction, so a pre-filled one that no price references makes the
 * line unpriceable and the order impossible to complete ("Produkt ist nicht auf der Preisliste").
 *
 * Both cases run under the seeded default of
 * `de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper.EnforcePrecisePricePerHUItemProduct`,
 * which is `Y` — the value real installations run — so the specs deliberately do NOT write it.
 */

/**
 * @param {boolean} priceReferencesPackingInstruction
 *   false → the product has a price, but nothing references the Standard-Packvorschrift (the reported case)
 *   true  → the product's price references it
 */
const createFixture = async (priceReferencesPackingInstruction) =>
  Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US' } },
      bpartners: {
        CUSTOMER: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'Customer',
        },
      },
      products: {
        PROD: {
          name: 'PROD',
          type: 'Item',
          prices: [{ price: 17.35, currencyCode: 'EUR' }],
        },
      },
      packingInstructions: {
        // No qtyCUsPerTU => infinite capacity, and no bpartner: the shape a product carries when the
        // packing instruction exists for mobileUI production rather than for selling.
        DEFAULT_PI: {
          tu: 'TU_DEFAULT',
          product: 'PROD',
          isDefaultForProduct: true,
          referencedByProductPrice: priceReferencesPackingInstruction,
        },
      },
    },
  });

test.describe('Sales order quick entry - default packing instruction', () => {
  test.beforeEach(() => {
    // The custom `test` fixture in playwright.config.js already publishes the page globally.
    allure.epic('E0100: Sales');
    allure.tag('F00101.10: Sales Order Quick Entry packing instruction');
    allure.tag('F00101.10');
  });

  test('no product price references the Standard-Packvorschrift - not defaulted, order completes', async () => {
    const masterdata = await createFixture(false);
    const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;
    const product = masterdata.products.PROD.productName;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(customer);

    await SalesOrderPage.openQuickEntryAndSelectProduct({ product, recordId });

    // The defect: the Standard-Packvorschrift was pre-filled here even though no price references it.
    const packingInstruction = await SalesOrderPage.getQuickEntryPackingInstruction();
    console.log(`[TC1] packing instruction after product select: "${packingInstruction}"`);
    expect(packingInstruction).toBe('');

    await SalesOrderPage.submitQuickEntryLine({ quantity: 1 });

    // End result, read back from the server - not merely "the field looked empty".
    const validation = await getValidationStatus(SALES_ORDER_WINDOW_ID, recordId);
    expect(validation.valid, `order ${recordId} must be saveable: ${validation.reason}`).toBe(true);

    await SalesOrderPage.complete();

    const docStatus = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, 'DocStatus');
    expect(docStatus.value?.key ?? docStatus.value).toBe('CO');
  });

  test('a product price references the packing instruction - still defaulted, order completes', async () => {
    const masterdata = await createFixture(true);
    const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;
    const product = masterdata.products.PROD.productName;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(customer);

    await SalesOrderPage.openQuickEntryAndSelectProduct({ product, recordId });

    // The other half of the rule: a priced packing instruction must still be offered, otherwise the
    // fix would have stripped a legitimate default.
    const packingInstruction = await SalesOrderPage.getQuickEntryPackingInstruction();
    console.log(`[TC2] packing instruction after product select: "${packingInstruction}"`);
    expect(packingInstruction).not.toBe('');

    await SalesOrderPage.submitQuickEntryLine({ quantity: 1 });

    const validation = await getValidationStatus(SALES_ORDER_WINDOW_ID, recordId);
    expect(validation.valid, `order ${recordId} must be saveable: ${validation.reason}`).toBe(true);

    await SalesOrderPage.complete();

    const docStatus = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, 'DocStatus');
    expect(docStatus.value?.key ?? docStatus.value).toBe('CO');
  });
});
