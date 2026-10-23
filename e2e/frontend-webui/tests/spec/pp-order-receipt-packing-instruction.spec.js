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

// switching the packing instruction
const QTY_PER_TU_B = 10; // packing instruction "x 10 Stk"
const PALLET_CAPACITY_B = 6; // its first pallet takes 6 TUs
const PALLET_CAPACITY_UNLIMITED = 7; // pallet of the unlimited-capacity packing instruction
const TUS_BEFORE_SWITCH = 3; // changed by hand before the switch, so the recalculated total differs from the opening one
// unlimited capacity, loose CUs
const QTY_UNLIMITED = 37;
const QTY_LOOSE = 23;
const QTY_COPRODUCT = 29;
// individual serial-numbered CUs
const QTY_INDIVIDUAL_CUS = 3;

const ORDER_DATE = '2025-03-01T00:00:00.000+02:00';
const COPRODUCT_ROW_INDEX = 1; // lines view of the co-product order: main product, co-product, component

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

// one product, three packing instructions: "x 15" (pallet 4), "x 10" (pallet 6), unlimited capacity (pallet 7)
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
          qty: 200,
          lutuConfigurationTU: 'TU_A',
          lutuConfigurationLU: 'PI_A',
          piItemProduct: 'TU_A',
          datePromised: ORDER_DATE,
        },
      },
    },
  });

// unlimited-capacity packing instruction: with a pallet (7 TUs) and without
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
          lutuConfigurationTU: 'TU_UNL',
          piItemProduct: 'TU_UNL',
          datePromised: ORDER_DATE,
        },
      },
    },
  });

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

// product received as individual serial-numbered CUs
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

  for (const { language, label, quantityLabel, quantityCULabel } of LANGUAGES) {
    // `page` is not referenced, but the config's page fixture must run (publishes global.currentPage).
    // eslint-disable-next-line no-unused-vars
    test(`pallet of ${PALLET_CAPACITY}, packing instruction x ${QTY_PER_TU}, wrong stored configuration (${label})`, async ({ page }) => {
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
        // the pallet carries its TUs as one aggregate HU: 4 TUs holding 60 = 4 x 15
        hus: {
          lu_1: {
            storages: { PROD_PALLET: `${PALLET_CAPACITY * QTY_PER_TU} PCE` },
            tus: [{ qtyTUs: PALLET_CAPACITY, storages: { PROD_PALLET: `${PALLET_CAPACITY * QTY_PER_TU} PCE` } }],
          },
        },
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
          // each pallet: one aggregate HU of 3 TUs holding 45 = 3 x 15
          lu_2: { storages: { PROD_PALLET: palletQty }, tus: [{ qtyTUs: TUS_PER_PALLET_USED, storages: { PROD_PALLET: palletQty } }] },
          lu_3: { storages: { PROD_PALLET: palletQty }, tus: [{ qtyTUs: TUS_PER_PALLET_USED, storages: { PROD_PALLET: palletQty } }] },
        },
      });
    });

    // eslint-disable-next-line no-unused-vars
    test(`no pallet, packing instruction x ${QTY_PER_TU}, TU count gives the total (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);
      await openDialog(masterdata, 'PP_PLAIN');

      expect(await ReceiptDialog.getLabel('QtyTotalReceived')).toBe(quantityLabel);
      expect(await ReceiptDialog.isFieldVisible('QtyCUsPerTU')).toBe(false);

      await ReceiptDialog.setNumber('QtyTU', TUS_PER_PALLET_USED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_PER_PALLET_USED * QTY_PER_TU);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);

      // no TU count: no total, and the dialog does not let the user start
      await ReceiptDialog.clear('QtyTU');
      expect(await ReceiptDialog.getValue('QtyTotalReceived'), 'Menge is empty without TU Anzahl').toBe('');
      expect(await ReceiptDialog.isStartDisabled(), 'Start is disabled while TU Anzahl is empty').toBe(true);
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

    // eslint-disable-next-line no-unused-vars
    test(`switching the packing instruction resets pallet, counts and total (${label})`, async ({ page }) => {
      const masterdata = await createSwitchFixture(language);
      await openDialog(masterdata, 'PP_SWITCH');

      // "x 15" on a pallet of 4: opens at 4 TUs / 1 LU / 60, Menge follows the counts
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.setNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * QTY_PER_TU);

      // -> "x 10": first pallet holds 6 -> pallet reset to it, TU 6, LU 1, Menge 6 x 10 = 60 (from 45)
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'x 10');
      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID'), 'pallet of the new packing instruction').toContain(`(${PALLET_CAPACITY_B} x `);
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY_B);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      await ReceiptDialog.expectNumber('QtyTotalReceived', PALLET_CAPACITY_B * QTY_PER_TU_B);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(false);

      // after a switch a changed "TU Anzahl" is kept (no repeated reset) and Menge follows
      await ReceiptDialog.setNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTU', TUS_BEFORE_SWITCH);
      await ReceiptDialog.expectNumber('QtyTotalReceived', TUS_BEFORE_SWITCH * QTY_PER_TU_B);

      // -> unlimited capacity, with its pallet: TU / LU 1 and read-only, Menge editable
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'TU_UNL');
      expect(await ReceiptDialog.getValue('M_HU_PI_Item_ID'), 'pallet of the unlimited packing instruction').toContain(`(${PALLET_CAPACITY_UNLIMITED} x `);
      await ReceiptDialog.expectNumber('QtyTU', 1);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyLU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(false);
      await ReceiptDialog.setNumber('QtyTotalReceived', QTY_UNLIMITED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_UNLIMITED);

      // -> back to "x 15": counts editable again, Menge read-only and derived from the pallet of 4 again
      await ReceiptDialog.selectOption('M_HU_PI_Item_Product_ID', 'x 15');
      await ReceiptDialog.expectNumber('QtyTU', PALLET_CAPACITY);
      await ReceiptDialog.expectNumber('QtyLU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(false);
      expect(await ReceiptDialog.isReadOnly('QtyLU')).toBe(false);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(true);
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

      // the switched dialog still receives correctly: 3 TUs x 15 on a pallet
      await ReceiptDialog.start();
      const qty = `${TUS_BEFORE_SWITCH * QTY_PER_TU} PCE`;
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_SWITCH')]: { receivedHUs: [{ lu: 'lu_1', qty }] } },
        hus: { lu_1: { storages: { PROD_SWITCH: qty }, tus: [{ qtyTUs: TUS_BEFORE_SWITCH, storages: { PROD_SWITCH: qty } }] } },
      });
    });

    // eslint-disable-next-line no-unused-vars
    test(`unlimited capacity: one TU, Menge entered, pallet keeps 1 / 1 (${label})`, async ({ page }) => {
      const masterdata = await createUnlimitedFixture(language);
      await openDialog(masterdata, 'PP_UNL');

      // no pallet yet: TU 1 read-only, Menge editable
      await ReceiptDialog.expectNumber('QtyTU', 1);
      expect(await ReceiptDialog.isReadOnly('QtyTU')).toBe(true);
      expect(await ReceiptDialog.isReadOnly('QtyTotalReceived')).toBe(false);
      await ReceiptDialog.setNumber('QtyTotalReceived', QTY_UNLIMITED);
      await ReceiptDialog.expectNumber('QtyTotalReceived', QTY_UNLIMITED);

      // selecting the pallet keeps TU and LU at 1, read-only, and the entered Menge
      await ReceiptDialog.selectOption('M_HU_PI_Item_ID', `(${PALLET_CAPACITY_UNLIMITED} x `);
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

      // one pallet with one TU holding the entered quantity
      await ReceiptDialog.start();
      const qty = `${QTY_UNLIMITED} PCE`;
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_UNL')]: { receivedHUs: [{ lu: 'lu_1', qty }] } },
        hus: { lu_1: { storages: { PROD_UNL: qty }, tus: [{ qtyTUs: 1, storages: { PROD_UNL: qty } }] } },
      });
    });

    // eslint-disable-next-line no-unused-vars
    test(`"No Packing Item" shows only Menge CU and builds no pallet (${label})`, async ({ page }) => {
      const masterdata = await createFixture(language);
      await openDialog(masterdata, 'PP_PALLET_1');

      // opens with the packing instruction and the pallet
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
      // loose CUs holding the entered quantity, neither on a pallet nor in a TU
      await Backend.expect({
        manufacturings: { [orderId(masterdata, 'PP_PALLET_1')]: { receivedHUs: [{ loose: true, qty: `${QTY_LOOSE} PCE` }] } },
      });
    });

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
            receivedHUs: Array.from({ length: QTY_INDIVIDUAL_CUS }, () => ({ loose: true, qty: '1 PCE' })),
          },
        },
      });
    });
  }

  // eslint-disable-next-line no-unused-vars
  test('refusal is shown right after the UI login, without a page reload', async ({ page }) => {
    const { language, quantityLabel } = LANGUAGES[1];
    const masterdata = await createFixture(language);

    // login through the form and stay in the running SPA: the app booted on /login, before the login completed
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await ReceiptDialog.openLinesView({ ppOrderId: masterdata.manufacturingOrders.PP_PALLET_1.ppOrderId, reload: false });
    await ReceiptDialog.openReceiptDialog();

    expect(await ReceiptDialog.getLabel('QtyTotalReceived')).toBe(quantityLabel);
    await ReceiptDialog.setNumber('QtyTU', PALLET_CAPACITY + 1);
    const refusal = await ReceiptDialog.startExpectingRefusal();
    expect(refusal, 'refusal names the pallet capacity').toMatch(new RegExp(`\\(${PALLET_CAPACITY} TUs?\\)`));
  });
});
