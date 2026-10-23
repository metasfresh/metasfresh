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
 * Test values are pairwise different and neither 0 nor 1, so a wrong formula, default or limit gives a
 * different result: packing instruction "x 15 Stk", pallet capacity 4 TUs, stored (wrong) configuration
 * 20 per TU / 6 TUs, target 45 pieces, TU counts 3 / 4 / 5, LU count 2.
 */

const QTY_PER_TU = 15; // packing instruction "x 15 Stk"
const PALLET_CAPACITY = 4; // TUs per pallet
const STORED_QTY_PER_TU = 20; // wrong: differs from the packing instruction
const STORED_QTY_TU = 6; // wrong: exceeds the pallet capacity
const TARGET_QTY = 45; // what the operators want to receive
const TUS_PER_PALLET_USED = 3;
const LUS_USED = 2;

// The label is part of the requirement (AC8), so it is the one place that compares localized text.
const LANGUAGES = [
  { language: 'de_DE', label: 'German', quantityLabel: 'Menge' },
  { language: 'en_US', label: 'English', quantityLabel: 'Quantity' },
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
        // "x 15 Stk" on a pallet that takes 4 TUs
        PI_PALLET: { lu: 'LU', qtyTUsPerLU: PALLET_CAPACITY, tu: 'TU_PALLET', product: 'PROD_PALLET', qtyCUsPerTU: QTY_PER_TU },
        // "x 15 Stk", no pallet
        PI_PLAIN: { tu: 'TU_PLAIN', product: 'PROD_PLAIN', qtyCUsPerTU: QTY_PER_TU },
      },
      manufacturingOrders: {
        // The stored LU/TU configuration is wrong the way operators produced it: 20 per TU, 6 TUs
        PP_PALLET_1: palletOrder(),
        PP_PALLET_2: palletOrder(),
        PP_PLAIN: {
          warehouse: 'wh',
          product: 'PROD_PLAIN',
          qty: 200,
          lutuConfigurationTU: 'TU_PLAIN',
          piItemProduct: 'TU_PLAIN',
          datePromised: '2025-03-01T00:00:00.000+02:00',
        },
      },
    },
  });

const palletOrder = () => ({
  warehouse: 'wh',
  product: 'PROD_PALLET',
  qty: 200,
  lutuConfigurationTU: 'TU_PALLET',
  lutuConfigurationLU: 'PI_PALLET',
  lutuConfigurationQtyCUsPerTU: STORED_QTY_PER_TU,
  lutuConfigurationQtyTUs: STORED_QTY_TU,
  piItemProduct: 'TU_PALLET',
  datePromised: '2025-03-01T00:00:00.000+02:00',
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

  for (const { language, label, quantityLabel } of LANGUAGES) {
    // `page` is not referenced, but the config's page fixture must run (publishes global.currentPage).
    // eslint-disable-next-line no-unused-vars
    test(`TC1 pallet of ${PALLET_CAPACITY}, packing instruction x ${QTY_PER_TU}, wrong stored configuration (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);

      // --- order 1: opening state, operators' habit, refusal, acceptance
      await openDialog(masterdata, 'PP_PALLET_1');

      expect(await ReceiptDialog.getLabel('QtyTotalReceived')).toBe(quantityLabel);
      expect(await ReceiptDialog.isFieldVisible('QtyCUsPerTU'), 'no input field for the quantity per TU').toBe(false);

      // stored 6 TUs is clamped to the pallet capacity; stored 20 per TU is replaced by the packing instruction's 15
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY * QTY_PER_TU);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);

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
      await Backend.expect({ manufacturings: { [orderId(masterdata, 'PP_PALLET_1')]: { receivedHUs: [] } } });

      // exactly the pallet capacity: accepted -> one pallet with 4 TUs of 15
      await ReceiptDialog.setNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_PALLET_1')]: { receivedHUs: [{ lu: 'lu_1', qty: `${PALLET_CAPACITY * QTY_PER_TU} PCE` }] } },
        hus: { lu_1: { qtyTUs: PALLET_CAPACITY, storages: { PROD_PALLET: `${PALLET_CAPACITY * QTY_PER_TU} PCE` } } },
      });

      // --- order 2: 3 TUs on 2 pallets -> Menge 90 -> 2 pallets x 3 TUs x 15
      await openDialogOnCurrentSession(masterdata, 'PP_PALLET_2');
      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);
      await ReceiptDialog.setNumber('QtyLU', LUS_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_PER_PALLET_USED * LUS_USED * QTY_PER_TU);
      await ReceiptDialog.start();

      const palletQty = `${TUS_PER_PALLET_USED * QTY_PER_TU} PCE`;
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_PALLET_2')]: { receivedHUs: [{ lu: 'lu_2', qty: palletQty }, { lu: 'lu_3', qty: palletQty }] } },
        hus: {
          lu_2: { qtyTUs: TUS_PER_PALLET_USED, storages: { PROD_PALLET: palletQty } }, // 3 TUs holding 45 = 3 x 15
          lu_3: { qtyTUs: TUS_PER_PALLET_USED, storages: { PROD_PALLET: palletQty } },
        },
      });
    });

    // eslint-disable-next-line no-unused-vars
    test(`TC2 no pallet, packing instruction x ${QTY_PER_TU}, TU count gives the total (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);
      await openDialog(masterdata, 'PP_PLAIN');

      expect(await ReceiptDialog.getLabel('QtyTotalReceived')).toBe(quantityLabel);
      expect(await ReceiptDialog.isFieldVisible('QtyCUsPerTU')).toBe(false);

      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_PER_PALLET_USED * QTY_PER_TU);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);

      // no TU count: no total, and Start is refused
      await ReceiptDialog.clear('QtyTU');
      expect(await ReceiptDialog.getValue('QtyTotalReceived'), 'Menge is empty without TU Anzahl').toBe('');
      await ReceiptDialog.startExpectingRefusal();
      await Backend.expect({ manufacturings: { [orderId(masterdata, 'PP_PLAIN')]: { receivedHUs: [] } } });

      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);
      await ReceiptDialog.start();
      await Backend.expect({
        manufacturings: {
          [orderId(masterdata, 'PP_PLAIN')]: {
            receivedHUs: [
              { tu: 'tu_1', qty: `${QTY_PER_TU} PCE` },
              { tu: 'tu_2', qty: `${QTY_PER_TU} PCE` },
              { tu: 'tu_3', qty: `${QTY_PER_TU} PCE` },
            ],
          },
        },
      });
    });
  }
});
