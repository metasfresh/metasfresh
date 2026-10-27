import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { ManufacturingOrderReceiptPage as ReceiptDialog } from '../utils/pages/ManufacturingOrderReceiptPage';

/**
 * Features:
 * - F8020: Manufacturing Order
 * - F5001: Packing Instruction
 *
 * Production receipt dialog ("Empfangen") of a manufacturing order follows the packing instruction:
 * the quantity per TU is the packing instruction's (no input field), the dialog shows the derived
 * total ("Menge" / "Quantity"), and configurations that contradict the packing instruction or the
 * pallet capacity are refused.
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

// Backend.expect addresses manufacturing orders by the numeric PP_Order_ID (not registered as an identifier)
const orderId = (masterdata, orderIdentifier) => masterdata.manufacturingOrders[orderIdentifier].ppOrderId;

const openDialogOnCurrentSession = async (masterdata, orderIdentifier) => {
  await ReceiptDialog.openLinesView({ ppOrderId: masterdata.manufacturingOrders[orderIdentifier].ppOrderId });
  await ReceiptDialog.openReceiptDialog();
};

const openDialog = async (masterdata, orderIdentifier) => {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await openDialogOnCurrentSession(masterdata, orderIdentifier);
};

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
      await openDialog(masterdata, 'PP_SERIAL');

      for (const hidden of ['QtyTU', 'QtyLU', 'QtyTotalReceived', 'QtyCUsReceived', 'M_HU_PI_Item_Product_ID', 'M_HU_PI_Item_ID', 'QtyCUsPerTU']) {
        expect(await ReceiptDialog.isFieldVisible(hidden), `${hidden} is hidden for individual CUs`).toBe(false);
      }
      expect(await ReceiptDialog.isFieldVisible('NumberOfCUs')).toBe(true);

      // the value survives the dialog's parameter-change round trip and is received
      await ReceiptDialog.setNumber('NumberOfCUs', QTY_INDIVIDUAL_CUS);
      await ReceiptDialog.expectNumber('NumberOfCUs', QTY_INDIVIDUAL_CUS);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: {
          [orderId(masterdata, 'PP_SERIAL')]: {
            receivedHUs: Array.from({ length: QTY_INDIVIDUAL_CUS }, () => ({ looseCUs: true, qty: '1 PCE' })),
          },
        },
      });
    });
  }
});
