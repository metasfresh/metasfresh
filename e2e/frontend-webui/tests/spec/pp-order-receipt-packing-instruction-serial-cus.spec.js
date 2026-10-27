import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { ManufacturingOrderReceiptPage as ReceiptDialog } from '../utils/pages/ManufacturingOrderReceiptPage';

/**
 * Features:
 * - F8020: Manufacturing Order
 * - F5001: Packing Instruction
 *
 * This file: one product with a serial number sequence, received as individual CUs; that dialog is unchanged.
 */

const QTY_INDIVIDUAL_CUS = 3;

const ORDER_DATE = '2025-03-01T00:00:00.000+02:00';

const LANGUAGES = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

const createSerialFixture = async (language) =>
  Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      products: {
        COMP1: {},
        PROD_SERIAL: { prices: [{ price: 1 }], bom: { lines: [{ product: 'COMP1', qty: 1 }], serialNoSequence: true } },
      },
      manufacturingOrders: {
        PP_SERIAL: { warehouse: 'wh', product: 'PROD_SERIAL', qty: 200, datePromised: ORDER_DATE },
      },
    },
  });

test.describe('PP_Order receipt - dialog follows the packing instruction', () => {
  test.beforeEach(() => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8020: Manufacturing Order');
    allure.tag('F8020');
    allure.tag('F5001: Packing Instruction');
    allure.tag('F5001');
    allure.story('Production receipt dialog follows the packing instruction');
  });

  for (const { language, label } of LANGUAGES) {
    // eslint-disable-next-line no-unused-vars
    test(`individual serial-numbered CUs are unchanged (${label})`, async ({ page }) => {
      const masterdata = await createSerialFixture(language);
      await ReceiptDialog.openForOrder(masterdata, 'PP_SERIAL');

      for (const hidden of ['QtyTU', 'QtyLU', 'QtyTotalReceived', 'QtyCUsReceived', 'M_HU_PI_Item_Product_ID', 'M_HU_PI_Item_ID', 'QtyCUsPerTU']) {
        await expect.poll(() => ReceiptDialog.isFieldVisible(hidden), { message: `${hidden} is hidden for individual CUs` }).toBe(false);
      }
      await expect.poll(() => ReceiptDialog.isFieldVisible('NumberOfCUs')).toBe(true);

      // the value survives the dialog's parameter-change round trip and is received
      await ReceiptDialog.setNumber('NumberOfCUs', QTY_INDIVIDUAL_CUS);
      await ReceiptDialog.expectNumber('NumberOfCUs', QTY_INDIVIDUAL_CUS);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: {
          [ReceiptDialog.orderId(masterdata, 'PP_SERIAL')]: {
            receivedHUs: Array.from({ length: QTY_INDIVIDUAL_CUS }, () => ({ looseCUs: true, qty: '1 PCE' })),
          },
        },
      });
    });
  }
});
