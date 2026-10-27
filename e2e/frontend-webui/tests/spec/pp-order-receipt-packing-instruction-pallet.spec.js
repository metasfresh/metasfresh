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
 * This file: one product with a pallet packing instruction, one without pallet; both "x 15 Stk".
 * Covers the pallet and no-pallet receipts and "No Packing Item".
 *
 * Test values are pairwise different and neither 0 nor 1, so a wrong formula, default or limit gives a
 * different result: packing instruction "x 15 Stk", pallet capacity 4 TUs, pallet orders of 470 pieces (opening at 8 pallets),
 * target 45 pieces, TU counts 3 / 4 / 5, LU count 2.
 */

const QTY_PER_TU = 15; // packing instruction "x 15 Stk"
const PALLET_CAPACITY = 4;
const ORDER_QTY_PALLET = 470; // 32 TUs of 15
const OPENING_LUS = 8; // pallets of 4 x 15 = 60 needed for 470, rounded up
const TARGET_QTY = 45; // what the operators want to receive
const TUS_PER_PALLET_USED = 3;
const LUS_USED = 2;
const QTY_LOOSE = 23;

// The label is part of the requirement, so it is the one place that compares localized text.
const LANGUAGES = [
  { language: 'de_DE', label: 'German', quantityLabel: 'Menge', quantityCULabel: 'Menge CU' },
  { language: 'en_US', label: 'English', quantityLabel: 'Quantity', quantityCULabel: 'Quantity CU' },
];

const createFixture = async (language) =>
  Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      products: {
        COMP1: {},
        PROD_PALLET: { prices: [{ price: 1 }], bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
        PROD_PLAIN: { prices: [{ price: 1 }], bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
      },
      packingInstructions: {
        PI_PALLET: { lu: 'LU', qtyTUsPerLU: PALLET_CAPACITY, tu: 'TU_PALLET', product: 'PROD_PALLET', qtyCUsPerTU: QTY_PER_TU },
        PI_PLAIN: { tu: 'TU_PLAIN', product: 'PROD_PLAIN', qtyCUsPerTU: QTY_PER_TU },
      },
      manufacturingOrders: {
        PP_PALLET_1: palletOrder(),
        PP_PALLET_2: palletOrder(),
        PP_PLAIN: {
          warehouse: 'wh',
          product: 'PROD_PLAIN',
          qty: 200,
          piItemProduct: 'TU_PLAIN',
          datePromised: '2025-03-01T00:00:00.000+02:00',
        },
      },
    },
  });

const palletOrder = () => ({
  warehouse: 'wh',
  product: 'PROD_PALLET',
  qty: ORDER_QTY_PALLET,
  piItemProduct: 'TU_PALLET',
  datePromised: '2025-03-01T00:00:00.000+02:00',
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

  for (const { language, label, quantityLabel, quantityCULabel } of LANGUAGES) {
    // `page` is not referenced, but the config's page fixture must run (publishes global.currentPage).
    // eslint-disable-next-line no-unused-vars
    test(`pallet of ${PALLET_CAPACITY}, packing instruction x ${QTY_PER_TU} (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);

      // --- order 1: opening state, operators' habit, refusal, acceptance
      await ReceiptDialog.openForOrder(masterdata, 'PP_PALLET_1');

      expect(await ReceiptDialog.getLabel('QtyTotalReceived')).toBe(quantityLabel);
      expect(await ReceiptDialog.isFieldVisible('QtyCUsPerTU'), 'no input field for the quantity per TU').toBe(false);

      // opens with the pallets needed for the order, each filled to the pallet capacity
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.expectNumber('QtyLU', OPENING_LUS);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY * OPENING_LUS * QTY_PER_TU);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);

      // one pallet at a time
      await ReceiptDialog.setNumber('QtyLU', 1);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY * QTY_PER_TU);

      // operators' habit: wanted 45 pieces, "TU Anzahl" left at 1 -> 15, and 45 cannot be entered anywhere
      await ReceiptDialog.setNumber('QtyTU', 1);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_PER_TU);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived'), `cannot enter ${TARGET_QTY} into Menge`).toBe(true);
      expect(await ReceiptDialog.isFieldVisible('QtyCUsPerTU')).toBe(false);

      // more TUs than the pallet takes: refused, naming the capacity; nothing created
      await ReceiptDialog.setNumber('QtyTU', PALLET_CAPACITY + 1);
      const refusal = await ReceiptDialog.startExpectingRefusal();
      // AD_Message M_HU_LUTU_Configuration_QtyTU_ExceedsLUCapacity: "... (5) ... Kapazität der Palette (4 TU) ..." / "... (5) ... capacity ({4} TUs) ..."
      expect(refusal, 'refusal names the pallet capacity').toMatch(new RegExp(`\\(${PALLET_CAPACITY} TUs?\\)`));
      await Backend.expect({ manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_PALLET_1')]: { receivedHUs: [] } } });

      // exactly the pallet capacity: accepted -> one pallet with 4 TUs of 15
      await ReceiptDialog.setNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_PALLET_1')]: { receivedHUs: [{ lu: 'lu_1', qty: `${PALLET_CAPACITY * QTY_PER_TU} PCE` }] } },
        // the pallet carries its TUs as one aggregate HU: 4 TUs holding 60 = 4 x 15
        hus: {
          lu_1: {
            storages: { PROD_PALLET: `${PALLET_CAPACITY * QTY_PER_TU} PCE` },
            tus: [{ qtyTUs: PALLET_CAPACITY, storages: { PROD_PALLET: `${PALLET_CAPACITY * QTY_PER_TU} PCE` } }],
          },
        },
      });

      // --- order 2: 3 TUs on 2 pallets -> Menge 90 -> 2 pallets x 3 TUs x 15
      await ReceiptDialog.openForOrderOnCurrentSession(masterdata, 'PP_PALLET_2');
      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);

      // pallet selected, "LU Anzahl" 0: refused, nothing created
      await ReceiptDialog.setNumber('QtyLU', 0);
      expect(await ReceiptDialog.startExpectingRefusal(), 'refusal for 0 pallets').not.toBe('');
      await Backend.expect({ manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_PALLET_2')]: { receivedHUs: [] } } });

      await ReceiptDialog.setNumber('QtyLU', LUS_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_PER_PALLET_USED * LUS_USED * QTY_PER_TU);
      await ReceiptDialog.start();

      const palletQty = `${TUS_PER_PALLET_USED * QTY_PER_TU} PCE`;
      await Backend.expect({
        manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_PALLET_2')]: { receivedHUs: [{ lu: 'lu_2', qty: palletQty }, { lu: 'lu_3', qty: palletQty }] } },
        hus: {
          // each pallet: one aggregate HU of 3 TUs holding 45 = 3 x 15
          lu_2: { storages: { PROD_PALLET: palletQty }, tus: [{ qtyTUs: TUS_PER_PALLET_USED, storages: { PROD_PALLET: palletQty } }] },
          lu_3: { storages: { PROD_PALLET: palletQty }, tus: [{ qtyTUs: TUS_PER_PALLET_USED, storages: { PROD_PALLET: palletQty } }] },
        },
      });
    });

    // eslint-disable-next-line no-unused-vars
    test(`no pallet, packing instruction x ${QTY_PER_TU}, TU count gives the total (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);
      await ReceiptDialog.openForOrder(masterdata, 'PP_PLAIN');

      expect(await ReceiptDialog.getLabel('QtyTotalReceived')).toBe(quantityLabel);
      expect(await ReceiptDialog.isFieldVisible('QtyCUsPerTU')).toBe(false);

      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_PER_PALLET_USED * QTY_PER_TU);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);

      await ReceiptDialog.clear('QtyTU');
      expect(await ReceiptDialog.getValue('QtyTotalReceived'), 'Menge is empty without TU Anzahl').toBe('');
      expect(await ReceiptDialog.isStartDisabled(), 'Start is disabled while TU Anzahl is empty').toBe(true);
      await Backend.expect({ manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_PLAIN')]: { receivedHUs: [] } } });

      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: {
          [ReceiptDialog.orderId(masterdata, 'PP_PLAIN')]: {
            receivedHUs: [
              { tu: 'tu_1', qty: `${QTY_PER_TU} PCE` },
              { tu: 'tu_2', qty: `${QTY_PER_TU} PCE` },
              { tu: 'tu_3', qty: `${QTY_PER_TU} PCE` },
            ],
          },
        },
      });
    });

    // eslint-disable-next-line no-unused-vars
    test(`"No Packing Item" shows only Menge CU and builds no pallet (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);
      await ReceiptDialog.openForOrder(masterdata, 'PP_PALLET_1');

      expect(await ReceiptDialog.isFieldVisible('QtyTotalReceived')).toBe(true);
      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID')).not.toBe('');

      await ReceiptDialog.selectNoPackingItem();
      expect(await ReceiptDialog.isFieldVisible('QtyCUsReceived'), 'Menge CU').toBe(true);
      expect(await ReceiptDialog.getLabel('QtyCUsReceived')).toBe(quantityCULabel);
      for (const hidden of ['QtyTotalReceived', 'QtyTU', 'QtyLU', 'M_HU_PI_Item_ID', 'QtyCUsPerTU']) {
        expect(await ReceiptDialog.isFieldVisible(hidden), `${hidden} is hidden for "No Packing Item"`).toBe(false);
      }

      // back to the packing instruction: pallet and counts come back as defaults (4 TUs, 1 LU, 60), then "No Packing Item" again
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', `x ${QTY_PER_TU}`);
      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID')).toContain(`(${PALLET_CAPACITY} x `);
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY * QTY_PER_TU);
      await ReceiptDialog.selectNoPackingItem();
      expect(await ReceiptDialog.isFieldVisible('M_HU_PI_Item_ID')).toBe(false);

      await ReceiptDialog.setNumber('QtyCUsReceived', QTY_LOOSE);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: { [ReceiptDialog.orderId(masterdata, 'PP_PALLET_1')]: { receivedHUs: [{ looseCUs: true, qty: `${QTY_LOOSE} PCE` }] } },
      });
    });
  }
});
