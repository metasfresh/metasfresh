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
 * This file: the main product has no packing instruction of its own; the co-product has an unlimited-capacity one.
 * Covers receiving the co-product line.
 */

const QTY_COPRODUCT = 29;

const ORDER_DATE = '2025-03-01T00:00:00.000+02:00';
const COPRODUCT_ROW_INDEX = 1; // lines view of the co-product order: main product, co-product, component

const LANGUAGES = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

// the main product has no packing instruction of its own; the co-product has an unlimited-capacity one (edge pieces)
const createCoProductFixture = async (language) =>
  Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      products: {
        COMP1: {},
        COPROD: { prices: [{ price: 1 }] },
        PROD_MAIN: {
          prices: [{ price: 1 }],
          bom: { lines: [{ product: 'COMP1', qty: 1 }, { product: 'COPROD', qty: -10, percentage: true, componentType: 'BY' }] },
        },
      },
      packingInstructions: {
        PI_EDGE: { tu: 'TU_EDGE', product: 'COPROD' },
      },
      manufacturingOrders: {
        PP_COPROD: { warehouse: 'wh', product: 'PROD_MAIN', qty: 200, datePromised: ORDER_DATE },
      },
    },
  });

// Backend.expect addresses manufacturing orders by the numeric PP_Order_ID (not registered as an identifier)
const orderId = (masterdata, orderIdentifier) => masterdata.manufacturingOrders[orderIdentifier].ppOrderId;

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
    test(`co-product line with unlimited-capacity packing instruction (${label})`, async ({ page }) => {
      const masterdata = await createCoProductFixture(language);
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await ReceiptDialog.openLinesView({ ppOrderId: masterdata.manufacturingOrders.PP_COPROD.ppOrderId });
      await ReceiptDialog.openReceiptDialog({ rowIndex: COPRODUCT_ROW_INDEX });

      // the co-product line has no packing instruction yet: "No Packing Item" -> only Menge CU
      expect(await ReceiptDialog.isFieldVisible('QtyCUsReceived')).toBe(true);
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'TU_EDGE');

      await ReceiptDialog.expectNumber('QtyTU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(false);
      await ReceiptDialog.setNumber('QtyTotalReceived', QTY_COPRODUCT);

      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_COPROD')]: { receivedCoProductHUs: [{ tu: 'tu_1', qty: `${QTY_COPRODUCT} PCE` }] } },
      });
    });
  }
});
