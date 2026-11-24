import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { LoginPage } from '../utils/pages/LoginPage';
import { PickingSlotsClearingPage as ClearingPage } from '../utils/pages/PickingSlotsClearingPage';

/**
 * Features:
 * - F5001: Packing Instruction
 *
 * Desktop "Picking Tray Clearing" (window 540371): pallets built from the picked TUs of a picking slot use the
 * pallet item of the TUs' product.
 *
 * Pallet packing instruction "LU" holds TUs of 4 pieces:
 * - generic pallet item: 30 TUs per pallet
 * - pallet item bound to product "XY": 40 TUs per pallet
 * "OTHER" and "OTHER2" have no pallet item of their own and use the same TU packing instruction.
 *
 * Setup: single TUs are created in stock and picked whole into the slot through the mobile picking REST API
 * (the real flow that puts TUs into a picking slot), for one customer and one delivery location.
 * The slot sits on an after-picking locator, where the clearing actions put the new pallets.
 */

const PIECES_PER_TU = 4;

/**
 * @param {Object<string, number>} tusPerProduct - product identifier -> number of single TUs to pick into the slot
 */
const createMasterdata = async ({ tusPerProduct }) => {
  const handlingUnits = {};
  for (const [product, count] of Object.entries(tusPerProduct)) {
    for (let i = 1; i <= count; i++) {
      // "tuOnly": PI is a pallet packing instruction; the stock holds single TUs of it
      handlingUnits[`${product}_TU${i}`] = { product, warehouse: 'wh', packingInstructions: `PI_${product}`, tuOnly: true };
    }
  }

  return await Backend.createMasterdata({
    language: 'en_US',
    request: {
      login: { user: { language: 'en_US' } },
      mobileConfig: {
        picking: {
          aggregationType: 'sales_order',
          createShipmentPolicy: 'NO',
          allowPickingAnyHU: true,
          pickTo: ['TU'],
          allowCompletingPartialPickingJob: true,
          allowPickingAnyCustomer: false,
          customers: [{ customer: 'BP1' }],
        },
      },
      bpartners: { BP1: {} },
      warehouses: {
        // the first locator (by X) is the warehouse's default one: the stock is created there
        wh: { locators: { stock: { x: '1' }, afterPicking: { x: '2', afterPickingLocator: true } } },
      },
      pickingSlots: { slot1: { locator: 'afterPicking' } },
      products: { XY: { price: 1 }, OTHER: { price: 1 }, OTHER2: { price: 1 } },
      packingInstructions: {
        PI_XY: {
          lu: 'LU',
          qtyTUsPerLU: 30,
          tu: 'TU',
          product: 'XY',
          qtyCUsPerTU: PIECES_PER_TU,
          additionalLUItems: [{ product: 'XY', qtyTUsPerLU: 40 }],
        },
        // same TU packing instruction (reused by identifier), no product-specific pallet item
        PI_OTHER: { tu: 'TU', product: 'OTHER', qtyCUsPerTU: PIECES_PER_TU },
        PI_OTHER2: { tu: 'TU', product: 'OTHER2', qtyCUsPerTU: PIECES_PER_TU },
        // a pallet packing instruction that has no item for "TU"
        PI_LU2: { lu: 'LU2', qtyTUsPerLU: 10, tu: 'TU2', product: 'OTHER2', qtyCUsPerTU: PIECES_PER_TU },
      },
      handlingUnits,
      salesOrders: {
        SO1: {
          bpartner: 'BP1',
          warehouse: 'wh',
          datePromised: '2025-03-01T00:00:00.000+02:00',
          lines: Object.entries(tusPerProduct).map(([product, count]) => ({
            product,
            qty: count * PIECES_PER_TU,
            piItemProduct: `TU_${product}`,
          })),
        },
      },
    },
  });
};

/**
 * Picks every created TU whole into the slot (mobile picking REST API, like the mobile UI) and completes the job
 * (shipment policy "don't create", so the TUs stay picked in the slot).
 */
const pickAllTUsIntoSlot = async ({ masterdata }) =>
  await test.step('Pick the TUs into the picking slot (mobile picking REST API)', async () => {
    const call = async (method, path, data) =>
      await Backend.callAppApi({ method, path, data, authToken: masterdata.login.user.token });

    let launcher = null;
    await expect
      .poll(
        async () => {
          const launchers = await call('POST', '/userWorkflows/launchers/query', { applicationId: 'picking' });
          launcher = (launchers.launchers ?? []).find((l) => String(l.wfParameters?.salesOrderId) === String(masterdata.salesOrders.SO1.id));
          return !!launcher;
        },
        { message: 'picking job launcher of SO1', timeout: 30000 }
      )
      .toBe(true);

    let wfProcess = await call('POST', '/userWorkflows/wfProcess/start', { wfParameters: launcher.wfParameters });
    const scanSlot = wfProcess.activities.find((a) => a.componentType === 'common/scanBarcode');
    if (scanSlot && scanSlot.status !== 'COMPLETED') {
      wfProcess = await call('POST', `/userWorkflows/wfProcess/${wfProcess.id}/${scanSlot.activityId}/scannedBarcode`, {
        barcode: masterdata.pickingSlots.slot1.qrCode,
      });
    }
    const pickActivity = wfProcess.activities.find((a) => a.componentType === 'picking/pickProducts');
    const lines = pickActivity.componentProps.pickingJob.lines;

    for (const [huIdentifier, hu] of Object.entries(masterdata.handlingUnits)) {
      const productIdentifier = huIdentifier.replace(/_TU\d+$/, '');
      const productValue = masterdata.products[productIdentifier].productCode;
      const line = lines.find((l) => l.productNo === productValue);
      expect(line, `picking line of ${productIdentifier}`).toBeDefined();
      await call('POST', '/picking/event', {
        wfProcessId: wfProcess.id,
        wfActivityId: pickActivity.activityId,
        pickingLineId: line.pickingLineId,
        type: 'PICK',
        huQRCode: hu.qrCode,
        qtyPicked: 1, // the line's unit is TU
        pickWholeTU: true,
      });
    }

    await call('POST', `/picking/job/${wfProcess.id}/complete`);
  });

const huIdsOf = ({ masterdata, productIdentifier }) =>
  Object.entries(masterdata.handlingUnits)
    .filter(([identifier]) => identifier.startsWith(`${productIdentifier}_TU`))
    .map(([, hu]) => hu.huId);

/** Normalizes describeHUs output into comparable pallets, sorted so that two sets compare independent of order. */
const toPalletSet = (hus) =>
  hus
    .map((hu) => ({
      huUnitType: hu.huUnitType,
      storages: hu.storages,
      includedTUsCount: hu.includedTUsCount,
      tuParentPIItemQtys: hu.tuParentPIItemQtys,
    }))
    .sort((a, b) =>
      `${JSON.stringify(a.storages)}|${a.includedTUsCount}`.localeCompare(`${JSON.stringify(b.storages)}|${b.includedTUsCount}`)
    );

const expectPallets = async ({ luIds, expected }) =>
  await test.step('Expect the new pallets', async () => {
    const actual = toPalletSet(await Backend.describeHUs({ huIds: luIds }));
    expect(actual, `actual pallets: ${JSON.stringify(actual)}`).toEqual(toPalletSet(expected));
  });

const pallet = ({ storages, includedTUsCount, palletItemQty }) => ({
  huUnitType: 'LU',
  storages,
  includedTUsCount,
  tuParentPIItemQtys: [palletItemQty],
});

const openClearingWindow = async ({ masterdata }) => {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await ClearingPage.open({
    bpartnerId: masterdata.bpartners.BP1.id,
    pickingSlotId: masterdata.pickingSlots.slot1.id,
  });
};

test.describe('Picking tray clearing: new pallets use the product-specific pallet item', () => {
  // setup creates up to 80 HUs and picks them one by one; the clearing action then moves them one by one
  test.describe.configure({ timeout: 6 * 60 * 1000 });

  test.beforeEach(() => {
    allure.epic('E0193: Handling Units');
    allure.tag('F5001: Packing Instruction');
    allure.tag('F5001');
  });

  test('TUs to new LUs: XY on own pallets of 40, OTHER and OTHER2 mixed on generic pallets of 30', async ({ page }) => { // eslint-disable-line no-unused-vars
    const masterdata = await createMasterdata({ tusPerProduct: { XY: 50, OTHER: 20, OTHER2: 10 } });
    await pickAllTUsIntoSlot({ masterdata });
    const XY = masterdata.products.XY.productCode;
    const OTHER = masterdata.products.OTHER.productCode;
    const OTHER2 = masterdata.products.OTHER2.productCode;

    await openClearingWindow({ masterdata });
    expect(await ClearingPage.getPackingHUIds({ huUnitType: 'LU' }), 'no pallets before the action').toEqual([]);

    await ClearingPage.selectPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });
    await ClearingPage.runTakeOutTUsAndAddToNewLUs();

    const luIds = await ClearingPage.getPackingHUIds({ huUnitType: 'LU' });
    expect(luIds, 'new pallets').toHaveLength(3);
    await ClearingPage.expectPackingHURows({ huIds: luIds });

    await expectPallets({
      luIds,
      expected: [
        pallet({ storages: { [XY]: '160 PCE' }, includedTUsCount: 40, palletItemQty: 40 }),
        pallet({ storages: { [XY]: '40 PCE' }, includedTUsCount: 10, palletItemQty: 40 }),
        pallet({ storages: { [OTHER]: '80 PCE', [OTHER2]: '40 PCE' }, includedTUsCount: 30, palletItemQty: 30 }),
      ],
    });
  });

  /**
   * "Take out HUs and add to new HU" with a pallet packing instruction as target: the pallet item is the one of the moved
   * product (none if several products are moved), and the moved TUs fill the new pallets up to its capacity.
   */
  const takeOutMultiHUsToNewPallet = async ({ tusPerProduct, targetPallet = 'LU', expectSuccess = true }) => {
    const masterdata = await createMasterdata({ tusPerProduct });
    await pickAllTUsIntoSlot({ masterdata });
    await openClearingWindow({ masterdata });

    const huIds = Object.keys(tusPerProduct).flatMap((productIdentifier) => huIdsOf({ masterdata, productIdentifier }));
    await ClearingPage.selectHUs({ pickingSlotId: masterdata.pickingSlots.slot1.id, huIds });
    const piResponse = targetPallet === 'LU' ? masterdata.packingInstructions.PI_XY : masterdata.packingInstructions.PI_LU2;
    const startResponse = await ClearingPage.runTakeOutMultiHUsAndAddToNewHU({
      huPIName: piResponse.luName,
      huPIId: piResponse.luPITestId.replace('pi-', ''),
      expectSuccess,
    });
    return { masterdata, startResponse };
  };

  /** "Take out HU and add to new HU" of one TU's worth (4 pieces) with the pallet packing instruction "LU" as target. */
  const takeOutHUToNewPallet = async ({ productIdentifier }) => {
    const masterdata = await createMasterdata({ tusPerProduct: { [productIdentifier]: 1 } });
    await pickAllTUsIntoSlot({ masterdata });
    await openClearingWindow({ masterdata });

    await ClearingPage.selectHUs({
      pickingSlotId: masterdata.pickingSlots.slot1.id,
      huIds: huIdsOf({ masterdata, productIdentifier }),
    });
    await ClearingPage.runTakeOutHUAndAddToNewHU({
      huPIName: masterdata.packingInstructions.PI_XY.luName,
      huPIId: masterdata.packingInstructions.PI_XY.luPITestId.replace('pi-', ''),
      qtyCUsPerTU: PIECES_PER_TU,
    });
    return masterdata;
  };

  test('Take out HUs and add to new HU, pallet target: 50 OTHER TUs on generic pallets of 30', async ({ page }) => { // eslint-disable-line no-unused-vars
    const { masterdata } = await takeOutMultiHUsToNewPallet({ tusPerProduct: { OTHER: 50 } });
    const OTHER = masterdata.products.OTHER.productCode;

    const luIds = await ClearingPage.getPackingHUIds({ huUnitType: 'LU' });
    await ClearingPage.expectPackingHURows({ huIds: luIds });
    await expectPallets({
      luIds,
      expected: [
        pallet({ storages: { [OTHER]: '120 PCE' }, includedTUsCount: 30, palletItemQty: 30 }),
        pallet({ storages: { [OTHER]: '80 PCE' }, includedTUsCount: 20, palletItemQty: 30 }),
      ],
    });
  });

  test('Take out HUs and add to new HU, pallet target: 50 XY TUs on XY pallets of 40', async ({ page }) => { // eslint-disable-line no-unused-vars
    const { masterdata } = await takeOutMultiHUsToNewPallet({ tusPerProduct: { XY: 50 } });
    const XY = masterdata.products.XY.productCode;

    const luIds = await ClearingPage.getPackingHUIds({ huUnitType: 'LU' });
    await ClearingPage.expectPackingHURows({ huIds: luIds });
    await expectPallets({
      luIds,
      expected: [
        pallet({ storages: { [XY]: '160 PCE' }, includedTUsCount: 40, palletItemQty: 40 }),
        pallet({ storages: { [XY]: '40 PCE' }, includedTUsCount: 10, palletItemQty: 40 }),
      ],
    });
  });

  test('Take out HUs and add to new HU, pallet target: XY and OTHER together use the generic pallet item', async ({ page }) => { // eslint-disable-line no-unused-vars
    const { masterdata } = await takeOutMultiHUsToNewPallet({ tusPerProduct: { XY: 20, OTHER: 20 } });
    const XY = masterdata.products.XY.productCode;
    const OTHER = masterdata.products.OTHER.productCode;

    const luIds = await ClearingPage.getPackingHUIds({ huUnitType: 'LU' });
    await ClearingPage.expectPackingHURows({ huIds: luIds });
    // the split per pallet depends on the loading order; assert the item and the totals
    const pallets = await Backend.describeHUs({ huIds: luIds });
    const totals = {};
    let tusCount = 0;
    for (const p of pallets) {
      expect(p.huUnitType).toBe('LU');
      expect(p.tuParentPIItemQtys, `pallet ${p.huId} is on the generic item`).toEqual([30]);
      tusCount += p.includedTUsCount;
      for (const [product, qtyStr] of Object.entries(p.storages)) {
        totals[product] = (totals[product] ?? 0) + Number(qtyStr.split(' ')[0]);
      }
    }
    expect(totals).toEqual({ [XY]: 80, [OTHER]: 80 });
    expect(tusCount).toBe(40);
  });

  test('Take out HUs and add to new HU: a pallet type without an item for the TU gives an error, no new HU', async ({ page }) => {
    const { masterdata, startResponse } = await takeOutMultiHUsToNewPallet({
      tusPerProduct: { OTHER: 2 },
      targetPallet: 'LU2',
      expectSuccess: false,
    });
    await test.step('Expect the "pallet type has no item for this TU" user error', async () => {
      // ERR_LU_HAS_NO_TU_SUB_PACK_INSTR names the pallet and the TU packing instruction; the names are masterdata, not translated
      const errorText = await startResponse.text();
      expect(startResponse.ok(), 'the action fails').toBe(false);
      expect(errorText).toContain(masterdata.packingInstructions.PI_LU2.luName);
      expect(errorText).toContain(masterdata.packingInstructions.PI_OTHER.tuName);
      // the process dialog shows it as an error notification (not a Toastify toast)
      await expect(page.locator('.notification-item.error')).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
    for (const huId of huIdsOf({ masterdata, productIdentifier: 'OTHER' })) {
      await expect(page.getByTestId(`table-row-${masterdata.pickingSlots.slot1.id}-${huId}`), 'the TU stays in the slot').toBeAttached();
    }
    expect(await ClearingPage.getPackingHUIds({ huUnitType: 'LU' }), 'no new pallet').toEqual([]);
    expect(await ClearingPage.getPackingHUIds({ huUnitType: 'TU' }), 'no new TU').toEqual([]);
  });

  test('Take out HU and add to new HU, pallet target: one OTHER TU on the generic pallet item', async ({ page }) => { // eslint-disable-line no-unused-vars
    const masterdata = await takeOutHUToNewPallet({ productIdentifier: 'OTHER' });
    const OTHER = masterdata.products.OTHER.productCode;

    const luIds = await ClearingPage.getPackingHUIds({ huUnitType: 'LU' });
    await ClearingPage.expectPackingHURows({ huIds: luIds });
    await expectPallets({
      luIds,
      expected: [pallet({ storages: { [OTHER]: '4 PCE' }, includedTUsCount: 1, palletItemQty: 30 })],
    });
  });

  test('Take out HU and add to new HU, pallet target: one XY TU on the XY pallet item', async ({ page }) => { // eslint-disable-line no-unused-vars
    const masterdata = await takeOutHUToNewPallet({ productIdentifier: 'XY' });
    const XY = masterdata.products.XY.productCode;

    const luIds = await ClearingPage.getPackingHUIds({ huUnitType: 'LU' });
    await ClearingPage.expectPackingHURows({ huIds: luIds });
    await expectPallets({
      luIds,
      expected: [pallet({ storages: { [XY]: '4 PCE' }, includedTUsCount: 1, palletItemQty: 40 })],
    });
  });
});

