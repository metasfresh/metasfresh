import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { ManufacturingOrderReceiptPage as ReceiptDialog } from '../utils/pages/ManufacturingOrderReceiptPage';

/**
 * Features:
 * - F5001: Packing Instruction
 *
 * Desktop production receipt dialog ("Empfangen"): the pallet field (M_HU_PI_Item_ID) offers the received
 * product's pallet item, and the pallets are built on it.
 *
 * Pallet packing instruction "LU" holds TUs of 4 pieces:
 * - generic pallet item: 30 TUs per pallet
 * - pallet item bound to product "XY": 40 TUs per pallet
 * Product "OTHER" has no pallet item of its own and uses the same pallet and TU packing instructions.
 * The XY order (320 pieces = 80 TUs) fills 2 pallets of 40, the OTHER order (360 pieces = 90 TUs) 3 pallets of 30.
 * The list entries read "<PI version name> (<qty> x <TU PI name>)" (WEBUI_ProcessHelper.buildHUPIItemString).
 */

const PIECES_PER_TU = 4;

const createMasterdata = async ({ orderProduct }) =>
  Backend.createMasterdata({
    language: 'en_US',
    request: {
      login: { user: { language: 'en_US' } },
      warehouses: { wh: {} },
      products: {
        COMP1: {},
        COMP2: {},
        XY: { bom: { lines: [{ product: 'COMP1', qty: 1 }, { product: 'COMP2', qty: 2 }] } },
        OTHER: { bom: { lines: [{ product: 'COMP1', qty: 1 }, { product: 'COMP2', qty: 2 }] } },
      },
      packingInstructions: {
        PI: {
          lu: 'LU',
          qtyTUsPerLU: 30,
          tu: 'TU',
          product: 'XY',
          qtyCUsPerTU: PIECES_PER_TU,
          additionalLUItems: [{ product: 'XY', qtyTUsPerLU: 40 }],
        },
        // same LU and TU packing instructions (reused by identifier), without a product-specific pallet item
        PI_OTHER: { tu: 'TU', product: 'OTHER', qtyCUsPerTU: PIECES_PER_TU },
      },
      handlingUnits: {
        HU_COMP1: { product: 'COMP1', warehouse: 'wh', qty: 2000 },
        HU_COMP2: { product: 'COMP2', warehouse: 'wh', qty: 2000 },
      },
      manufacturingOrders: {
        PP: {
          warehouse: 'wh',
          product: orderProduct,
          qty: orderProduct === 'XY' ? 320 : 360,
          // the order's TU packing instruction (registered by the testing API as "<tu>_<product>"):
          // without it the dialog opens on "No Packing Item" and shows no pallet field
          piItemProduct: `TU_${orderProduct}`,
          datePromised: '2025-03-01T00:00:00.000+02:00',
        },
      },
    },
  });

/** @returns {Promise<number>} the PP_Order_ID of order "PP" (Backend.expect addresses manufacturing orders by that ID) */
const openReceiptDialog = async ({ masterdata, orderProduct }) => {
  const ppOrderId = ReceiptDialog.ppOrderIdOf({ masterdata, ppOrderIdentifier: 'PP' });
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await ReceiptDialog.openManufacturingOrder({ ppOrderId });
  await ReceiptDialog.openIssueReceiptView();
  await ReceiptDialog.selectMainProductRow({ productValue: masterdata.products[orderProduct].productCode });
  await ReceiptDialog.openReceiptDialog();
  return ppOrderId;
};

/** The testing API's pallet item test ID is "luPIItem-<M_HU_PI_Item_ID>"; the dialog's list entries carry the bare ID. */
const piItemIdOf = (luPIItemTestId) => luPIItemTestId.replace(/^luPIItem-/, '');

const expectedPallet = ({ product, tusPerPallet }) => ({
  storages: { [product]: `${tusPerPallet * PIECES_PER_TU} PCE` },
  tus: [{ isAggregatedTU: true, qtyTUs: tusPerPallet, parentPIItemQtyTUs: tusPerPallet }],
});

test.describe('Manufacturing receipt dialog - product-specific pallet item', () => {
  test.beforeEach(() => {
    allure.epic('E0193: Handling Units');
    allure.tag('F5001: Packing Instruction');
    allure.tag('F5001');
    allure.story('Production receipt dialog offers the received product\'s pallet item');
  });

  // `page` is not referenced, but the config's page fixture must run (publishes global.currentPage).
  // eslint-disable-next-line no-unused-vars
  test('XY with its own pallet item: only the 40 entry is offered, pallets hold 40 TUs', async ({ page }) => {
    const masterdata = await createMasterdata({ orderProduct: 'XY' });
    const genericItemId = piItemIdOf(masterdata.packingInstructions.PI.luPIItemTestId);
    const xyItemId = piItemIdOf(masterdata.packingInstructions.PI.additionalLUPIItemTestIds[0]);

    const ppOrderId = await openReceiptDialog({ masterdata, orderProduct: 'XY' });

    const options = await ReceiptDialog.getLUPIItemOptions();
    const optionIds = options.map((option) => option.id);
    const optionTexts = options.map((option) => option.text);
    expect(optionTexts.some((text) => text.includes('(40 x ')), `a "(40 x " entry among ${optionTexts}`).toBe(true);
    expect(optionTexts.some((text) => text.includes('(30 x ')), `no "(30 x " entry among ${optionTexts}`).toBe(false);
    expect(optionIds, `pallet entries ${JSON.stringify(options)}`).toContain(xyItemId);
    expect(optionIds, `pallet entries ${JSON.stringify(options)}`).not.toContain(genericItemId);

    await expect.poll(() => ReceiptDialog.getSelectedLUPIItem()).toContain('(40 x ');
    await expect.poll(() => ReceiptDialog.getFieldValue({ columnName: 'QtyCUsPerTU' }).then(Number)).toBe(PIECES_PER_TU);
    await expect.poll(() => ReceiptDialog.getFieldValue({ columnName: 'QtyTU' }).then(Number)).toBe(40);
    await expect.poll(() => ReceiptDialog.getFieldValue({ columnName: 'QtyLU' }).then(Number)).toBe(2);

    await ReceiptDialog.startProcess();

    await Backend.expect({
      manufacturings: {
        [ppOrderId]: { receivedHUs: [{ lu: 'lu1', qty: '160 PCE' }, { lu: 'lu2', qty: '160 PCE' }] },
      },
      hus: {
        lu1: expectedPallet({ product: 'XY', tusPerPallet: 40 }),
        lu2: expectedPallet({ product: 'XY', tusPerPallet: 40 }),
      },
    });
  });

  // eslint-disable-next-line no-unused-vars
  test('OTHER without its own pallet item: the generic 30 entry is offered, pallets hold 30 TUs', async ({ page }) => {
    const masterdata = await createMasterdata({ orderProduct: 'OTHER' });
    const genericItemId = piItemIdOf(masterdata.packingInstructions.PI.luPIItemTestId);
    const xyItemId = piItemIdOf(masterdata.packingInstructions.PI.additionalLUPIItemTestIds[0]);

    const ppOrderId = await openReceiptDialog({ masterdata, orderProduct: 'OTHER' });

    const options = await ReceiptDialog.getLUPIItemOptions();
    const optionIds = options.map((option) => option.id);
    const optionTexts = options.map((option) => option.text);
    expect(optionTexts.some((text) => text.includes('(30 x ')), `a "(30 x " entry among ${optionTexts}`).toBe(true);
    expect(optionIds, `pallet entries ${JSON.stringify(options)}`).toContain(genericItemId);
    expect(optionIds, `pallet entries ${JSON.stringify(options)}`).not.toContain(xyItemId);

    await expect.poll(() => ReceiptDialog.getSelectedLUPIItem()).toContain('(30 x ');
    await expect.poll(() => ReceiptDialog.getFieldValue({ columnName: 'QtyCUsPerTU' }).then(Number)).toBe(PIECES_PER_TU);
    await expect.poll(() => ReceiptDialog.getFieldValue({ columnName: 'QtyTU' }).then(Number)).toBe(30);
    await expect.poll(() => ReceiptDialog.getFieldValue({ columnName: 'QtyLU' }).then(Number)).toBe(3);

    await ReceiptDialog.startProcess();

    await Backend.expect({
      manufacturings: {
        [ppOrderId]: {
          receivedHUs: [
            { lu: 'lu1', qty: '120 PCE' },
            { lu: 'lu2', qty: '120 PCE' },
            { lu: 'lu3', qty: '120 PCE' },
          ],
        },
      },
      hus: {
        lu1: expectedPallet({ product: 'OTHER', tusPerPallet: 30 }),
        lu2: expectedPallet({ product: 'OTHER', tusPerPallet: 30 }),
        lu3: expectedPallet({ product: 'OTHER', tusPerPallet: 30 }),
      },
    });
  });
});
