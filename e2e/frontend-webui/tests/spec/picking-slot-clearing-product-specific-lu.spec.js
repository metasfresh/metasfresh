import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { getPage } from '../utils/common';
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
const TESTING_API_BASE_URL = process.env.TESTING_API_BASE_URL || 'http://localhost:8282/api/v2';

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
    const request = getPage().request;
    const headers = { 'Content-Type': 'application/json', Authorization: masterdata.login.user.token };
    const call = async (method, path, data) => {
      const response = await request.fetch(`${TESTING_API_BASE_URL}${path}`, { method, headers, data });
      const text = await response.text();
      expect(response.ok(), `${method} ${path}: ${text.substring(0, 1000)}`).toBe(true);
      return text ? JSON.parse(text) : null;
    };

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
  // setup creates up to 80 HUs and picks them one by one
  test.describe.configure({ timeout: 240000 });

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
});

