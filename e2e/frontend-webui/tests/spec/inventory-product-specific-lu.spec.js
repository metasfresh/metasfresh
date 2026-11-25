import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { DOCTYPE_INVENTORY } from '../utils/DocTypeIds';
import { LoginPage } from '../utils/pages/LoginPage';
import { InventoryPage } from '../utils/pages/InventoryPage';

/**
 * Features:
 * - F5001: Packing Instruction
 *
 * Desktop "Physical Inventory" (window 168): a line with a pallet packing ("Packvorschrift (LU)") gets its counted
 * quantity on new pallets when the inventory is completed. The pallets use the pallet item of the line's product,
 * and the pallet packing dropdown offers only pallet packings usable for that product.
 *
 * TU packing "TU_PS" holds 4 pieces. Pallet packings that take it:
 * - "LU_PS": generic item 30 TUs per pallet, item bound to product "XY" 40 TUs per pallet
 * - "LU_XY_ONLY": only an item bound to "XY"
 * - "LU_OTHER2_ONLY": only an item bound to "OTHER2"
 */

const PIECES_PER_TU = 4;

const createMasterdata = async () =>
  await Backend.createMasterdata({
    language: 'en_US',
    request: {
      login: { user: { language: 'en_US' } },
      warehouses: { wh: { locators: { loc1: { x: '1' } } } },
      products: { XY: {}, OTHER: {}, OTHER2: {}, CARRIER: {} },
      packingInstructions: {
        PI: {
          lu: 'LU_PS',
          qtyTUsPerLU: 30,
          tu: 'TU_PS',
          product: 'XY',
          qtyCUsPerTU: PIECES_PER_TU,
          additionalLUItems: [{ product: 'XY', qtyTUsPerLU: 40 }],
        },
        PI_OTHER: { tu: 'TU_PS', product: 'OTHER', qtyCUsPerTU: PIECES_PER_TU },
        // Each entry also creates a TU packing for its "product", and the testing API allows one per TU and product:
        // so the pallet-only entries carry TU packings of other products (not used by the inventory lines).
        PI_XY_ONLY: { lu: 'LU_XY_ONLY', qtyTUsPerLU: 40, luProduct: 'XY', tu: 'TU_PS', product: 'OTHER2', qtyCUsPerTU: PIECES_PER_TU },
        PI_OTHER2_ONLY: { lu: 'LU_OTHER2_ONLY', qtyTUsPerLU: 10, luProduct: 'OTHER2', tu: 'TU_PS', product: 'CARRIER', qtyCUsPerTU: PIECES_PER_TU },
      },
    },
  });

const idOfTestId = (testId) => Number(String(testId).replace(/^[^-]+-/, ''));

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

const pallet = ({ product, qty, includedTUsCount, palletItemQty }) => ({
  huUnitType: 'LU',
  storages: { [product]: `${qty} PCE` },
  includedTUsCount,
  tuParentPIItemQtys: [palletItemQty],
});

test.describe('Physical inventory: pallets use the product-specific pallet item', () => {
  // completing builds 100 TUs on 4 pallets; on a DB with many warehouses it takes up to about a minute
  test.describe.configure({ timeout: 3 * 60 * 1000 });

  test.beforeEach(() => {
    allure.epic('E0193: Handling Units');
    allure.tag('F5001: Packing Instruction');
    allure.tag('F5001');
  });

  test('XY on own pallets of 40, OTHER on generic pallets of 30; the pallet dropdown offers only usable pallets', async ({ page }) => { // eslint-disable-line no-unused-vars
    const masterdata = await createMasterdata();
    const pis = masterdata.packingInstructions;
    const LU_PS = idOfTestId(pis.PI.luPITestId);
    const LU_XY_ONLY = idOfTestId(pis.PI_XY_ONLY.luPITestId);
    const LU_OTHER2_ONLY = idOfTestId(pis.PI_OTHER2_ONLY.luPITestId);
    const XY = masterdata.products.XY;
    const OTHER = masterdata.products.OTHER;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    const inventoryId = await InventoryPage.createNew({ warehouseId: masterdata.warehouses.wh.warehouseId, docTypeId: DOCTYPE_INVENTORY });
    const locatorId = masterdata.warehouses.wh.locators.loc1.id;

    await test.step('XY line: 200 pieces on TU_PS', async () => {
      await InventoryPage.addLine({
        productId: XY.id,
        productValue: XY.productCode,
        locatorId,
        qtyCount: 200,
        tuPIItemProductId: idOfTestId(pis.PI.tuPIItemProductTestId),
      });

      const offered = await InventoryPage.selectLUPacking({ luPIId: LU_PS });
      expect(offered, 'a pallet usable only for OTHER2 is not offered for XY').not.toContain(LU_OTHER2_ONLY);
      expect(offered, 'pallet packings offered for XY').toContain(LU_PS);
      expect(offered, 'pallet packings offered for XY').toContain(LU_XY_ONLY);
      await InventoryPage.closeLine();
    });

    await test.step('OTHER line: 200 pieces on TU_PS', async () => {
      await InventoryPage.addLine({
        productId: OTHER.id,
        productValue: OTHER.productCode,
        locatorId,
        qtyCount: 200,
        tuPIItemProductId: idOfTestId(pis.PI_OTHER.tuPIItemProductTestId),
      });

      const offered = await InventoryPage.selectLUPacking({ luPIId: LU_PS });
      expect(offered, 'a pallet usable only for XY is not offered for OTHER').not.toContain(LU_XY_ONLY);
      expect(offered, 'a pallet usable only for OTHER2 is not offered for OTHER').not.toContain(LU_OTHER2_ONLY);
      expect(offered, 'pallet packings offered for OTHER').toContain(LU_PS);
      await InventoryPage.closeLine();
    });

    await InventoryPage.complete();

    await test.step('Expect the pallets built on completion', async () => {
      const actual = toPalletSet(await Backend.describeHUs({ inventoryId }));
      expect(actual, `actual pallets: ${JSON.stringify(actual)}`).toEqual(
        toPalletSet([
          pallet({ product: XY.productCode, qty: 160, includedTUsCount: 40, palletItemQty: 40 }),
          pallet({ product: XY.productCode, qty: 40, includedTUsCount: 10, palletItemQty: 40 }),
          pallet({ product: OTHER.productCode, qty: 120, includedTUsCount: 30, palletItemQty: 30 }),
          pallet({ product: OTHER.productCode, qty: 80, includedTUsCount: 20, palletItemQty: 30 }),
        ])
      );
    });
  });
});
