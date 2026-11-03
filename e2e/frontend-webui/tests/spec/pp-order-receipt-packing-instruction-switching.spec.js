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
 * This file: one product with three packing instructions ("x 15 Stk" on a pallet of 4, "x 10 Stk" on a pallet of 6,
 * and one with unlimited capacity on a pallet of 7). Covers switching the packing instruction and the pallet in the open dialog.
 */

const QTY_PER_TU = 15; // packing instruction "x 15 Stk"
const PALLET_CAPACITY = 4;
const ORDER_QTY_PALLET = 470; // 32 TUs of 15
const OPENING_LUS = 8; // pallets of 4 x 15 = 60 needed for 470, rounded up
const LUS_USED = 2;

const QTY_PER_TU_B = 10; // packing instruction "x 10 Stk"
const PALLET_CAPACITY_B = 6; // its first pallet takes 6 TUs
const PALLET_CAPACITY_UNLIMITED = 7; // pallet of the unlimited-capacity packing instruction
const TUS_BEFORE_SWITCH = 3; // changed by hand before the switch, so the recalculated total differs from the opening one
const QTY_UNLIMITED = 37;

const ORDER_DATE = '2025-03-01T00:00:00.000+02:00';

const LANGUAGES = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

const createSwitchFixture = async (language) =>
  Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      products: {
        COMP1: {},
        PROD_SWITCH: { prices: [{ price: 1 }], bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
      },
      packingInstructions: {
        PI_A: { lu: 'LU_A', qtyTUsPerLU: PALLET_CAPACITY, tu: 'TU_A', product: 'PROD_SWITCH', qtyCUsPerTU: QTY_PER_TU },
        PI_B: { lu: 'LU_B', qtyTUsPerLU: PALLET_CAPACITY_B, tu: 'TU_B', product: 'PROD_SWITCH', qtyCUsPerTU: QTY_PER_TU_B },
        PI_UNL: { lu: 'LU_UNL', qtyTUsPerLU: PALLET_CAPACITY_UNLIMITED, tu: 'TU_UNL', product: 'PROD_SWITCH' },
      },
      manufacturingOrders: {
        PP_SWITCH: {
          warehouse: 'wh',
          product: 'PROD_SWITCH',
          qty: ORDER_QTY_PALLET,
          piItemProduct: 'TU_A',
          datePromised: ORDER_DATE,
        },
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
    test(`switching the packing instruction resets pallet, counts and total (${label})`, async ({ page }) => {
      const masterdata = await createSwitchFixture(language);
      await ReceiptDialog.openForOrder(masterdata, 'PP_SWITCH');

      // "x 15" on a pallet of 4: opens at 4 TUs / 8 LUs, Menge follows the counts
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.expectNumber('QtyLU', OPENING_LUS);
      await ReceiptDialog.setNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * OPENING_LUS * QTY_PER_TU);

      // -> "x 10": first pallet holds 6 -> pallet reset to it, TU 6, LU 1, Menge 6 x 10 = 60 (from 360)
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'x 10');
      await expect.poll(() => ReceiptDialog.getValue('M_HU_PI_Item_ID'), { message: 'pallet of the new packing instruction' }).toContain(`(${PALLET_CAPACITY_B} x `);
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY_B);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY_B * QTY_PER_TU_B);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyTU')).toBe(false);

      // after a switch a changed "TU Anzahl" is kept (no repeated reset) and Menge follows
      await ReceiptDialog.setNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * QTY_PER_TU_B);

      // -> unlimited capacity, with its pallet: TU / LU 1 and read-only, Menge editable
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'TU_UNL');
      await expect.poll(() => ReceiptDialog.getValue('M_HU_PI_Item_ID'), { message: 'pallet of the unlimited packing instruction' }).toContain(`(${PALLET_CAPACITY_UNLIMITED} x `);
      await ReceiptDialog.expectNumber('QtyTU', 1);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyLU')).toBe(true);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(false);
      await ReceiptDialog.setNumber('QtyTotalReceived', QTY_UNLIMITED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_UNLIMITED);

      // -> back to "x 15": counts editable again, Menge read-only and derived from the pallet of 4 again
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'x 15');
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyTU')).toBe(false);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyLU')).toBe(false);
      await expect.poll(() => ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY * QTY_PER_TU);
      await ReceiptDialog.setNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * QTY_PER_TU);

      // pallet change in the open dialog (fixed capacity): without pallet Menge ignores "LU Anzahl"; choosing it again keeps the counts
      await ReceiptDialog.setNumber('QtyLU', LUS_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * LUS_USED * QTY_PER_TU);
      await ReceiptDialog.clearOption('M_HU_PI_Item_ID');
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * QTY_PER_TU);
      await ReceiptDialog.selectOption('M_HU_PI_Item_ID', `(${PALLET_CAPACITY} x `);
      await ReceiptDialog.expectNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyLU', LUS_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * LUS_USED * QTY_PER_TU);
      await ReceiptDialog.setNumber('QtyLU', 1);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * QTY_PER_TU);

      await ReceiptDialog.start();
      const qty = `${TUS_BEFORE_SWITCH * QTY_PER_TU} PCE`;
      await Backend.expect({
        manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_SWITCH')]: { receivedHUs: [{ lu: 'lu_1', qty }] } },
        hus: { lu_1: { storages: { PROD_SWITCH: qty }, tus: [{ qtyTUs: TUS_BEFORE_SWITCH, storages: { PROD_SWITCH: qty } }] } },
      });
    });
  }
});
