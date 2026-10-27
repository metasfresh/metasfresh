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
 * This file: one product whose packing instruction has unlimited capacity, with a pallet of 7.
 * Covers receiving one TU with the entered Menge, with and without the pallet.
 */

const PALLET_CAPACITY_UNLIMITED = 7; // pallet of the unlimited-capacity packing instruction
const QTY_UNLIMITED = 37;

const ORDER_DATE = '2025-03-01T00:00:00.000+02:00';

const LANGUAGES = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

const createUnlimitedFixture = async (language) =>
  Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      products: {
        COMP1: {},
        PROD_UNL: { prices: [{ price: 1 }], bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
      },
      packingInstructions: {
        PI_UNL: { lu: 'LU_UNL', qtyTUsPerLU: PALLET_CAPACITY_UNLIMITED, tu: 'TU_UNL', product: 'PROD_UNL' },
      },
      manufacturingOrders: {
        PP_UNL: {
          warehouse: 'wh',
          product: 'PROD_UNL',
          qty: 200,
          piItemProduct: 'TU_UNL',
          datePromised: ORDER_DATE,
        },
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
    test(`unlimited capacity: one TU, Menge entered, pallet keeps 1 / 1 (${label})`, async ({ page }) => {
      const masterdata = await createUnlimitedFixture(language);
      await openDialog(masterdata, 'PP_UNL');

      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID'), 'preselected pallet').toContain(`(${PALLET_CAPACITY_UNLIMITED} x `);

      // remove the pallet: no pallet, TU 1 read-only, Menge editable
      await ReceiptDialog.clearOption('M_HU_PI_Item_ID');
      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID'), 'pallet removed').toBe('');
      await ReceiptDialog.expectNumber('QtyTU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(false);
      await ReceiptDialog.setNumber('QtyTotalReceived', QTY_UNLIMITED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_UNLIMITED);

      // selecting the pallet (a real change now) keeps TU and LU at 1, read-only, and the entered Menge
      await ReceiptDialog.selectOption('M_HU_PI_Item_ID', `(${PALLET_CAPACITY_UNLIMITED} x `);
      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID'), 'selected pallet').toContain(`(${PALLET_CAPACITY_UNLIMITED} x `);
      await ReceiptDialog.expectNumber('QtyTU', 1);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyLU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(false);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_UNLIMITED);

      // removing and choosing the pallet again changes nothing: TU / LU stay 1 and read-only, Menge stays
      await ReceiptDialog.clearOption('M_HU_PI_Item_ID');
      await ReceiptDialog.selectOption('M_HU_PI_Item_ID', `(${PALLET_CAPACITY_UNLIMITED} x `);
      await ReceiptDialog.expectNumber('QtyTU', 1);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyLU')).toBe(true);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_UNLIMITED);

      await ReceiptDialog.start();
      const qty = `${QTY_UNLIMITED} PCE`;
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_UNL')]: { receivedHUs: [{ lu: 'lu_1', qty }] } },
        hus: { lu_1: { storages: { PROD_UNL: qty }, tus: [{ qtyTUs: 1, storages: { PROD_UNL: qty } }] } },
      });
    });
  }
});
