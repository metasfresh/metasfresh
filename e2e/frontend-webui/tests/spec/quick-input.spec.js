import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT, collectPageErrors } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForTabAllowsNew, getTabRows, waitForRecordSaved } from '../utils/WebAPIValidation';

/**
 * Quick Input (Batch Entry) E2E test suite.
 *
 * Features tested:
 * - F00100: Sales Order
 * - F00101.10: Sales Order Quick Entry packing instruction
 *
 * Tests:
 * 1. Enter-key focus advance: Product → Enter → focus advances to Qty
 * 2. Mouse-click selection regression: Product → mouse click → line created
 * 3. Multiple lines in sequence: Add two lines via Enter-key workflow
 * 4. Invalid product: no beep when sysconfig disabled (default behavior)
 * 5. Regular form lookup regression: Customer selection in header (non-quick-input)
 * 6. Enter-key selects packing instruction on secondary sub-field (the reported bug)
 * 7. Mouse-click selects packing instruction on secondary sub-field (regression)
 * 8. Regular-form composite partner lookup: server auto-fill of the location and
 *    contact secondary sub-fields (RawList-rendered sub-fields, not RawLookup)
 * 9. Keyboard-only line entry when the product has NO packing instruction: Enter
 *    confirms the empty entry and advances, Tab-identical (TC8)
 * (TESTs 6–9 are placed after TEST 3 in the file)
 */

// ============================================================================
// Shared helpers
// ============================================================================

/**
 * Create standard masterdata for quick input tests.
 * Creates a user, customer, and one or two products.
 */
async function createMasterdata(
  language,
  { twoProducts = false, withPackingInstruction = false, sysconfigs = undefined } = {}
) {
  const products = {
    Product1: {
      name: 'QIPROD',
      type: 'Item',
      prices: [{ price: 10.0, currencyCode: 'EUR' }],
    },
  };

  if (twoProducts) {
    products.Product2 = {
      name: 'QIPR2',
      type: 'Item',
      prices: [{ price: 20.0, currencyCode: 'EUR' }],
    };
  }

  const request = {
    login: {
      user: {
        language,
        firstname: 'QI',
        lastname: 'Test',
      },
    },
    bpartners: {
      CUSTOMER1: {
        isVendor: false,
        isCustomer: true,
        isSoPriceList: true,
        name: 'Customer',
      },
    },
    products,
  };

  if (withPackingInstruction) {
    request.packingInstructions = {
      PI: { tu: 'TU', product: 'Product1', qtyCUsPerTU: 4 },
    };
  }

  if (sysconfigs) {
    request.sysconfigs = sysconfigs;
  }

  const masterdata = await Backend.createMasterdata({ request });

  // A runtime sysconfig change here can feed the order-line quick-input descriptor,
  // which is cached (QuickInputDescriptors CCache) and is NOT invalidated by the
  // AD_SysConfig reset createMasterdata already did. The full webapi cache reset
  // for that descriptor is done in setupOrderWithBatchEntry (resetWebApiCaches
  // option), AFTER login — Backend.resetWebApiCaches() hits GET /cache/reset,
  // which requires an authenticated webapi session (userSession.assertLoggedIn());
  // at this point in the flow the page hasn't logged in yet, so calling it here
  // deterministically 401s. See setupOrderWithBatchEntry.

  return masterdata;
}

/**
 * Login → Create Sales Order → Select Customer → Go to Order Line tab → Open batch entry.
 * Returns { recordId, batchEntryButton }.
 */
async function setupOrderWithBatchEntry(
  page,
  masterdata,
  language,
  { resetWebApiCaches = false } = {}
) {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  // Must run AFTER login (GET /cache/reset requires an authenticated webapi
  // session) and BEFORE the batch-entry panel is opened below (so the
  // QuickInputDescriptors CCache is rebuilt from the just-committed sysconfigs
  // rather than serving a stale cached descriptor). See createMasterdata's
  // comment for why this can't run at masterdata-creation time.
  if (resetWebApiCaches) {
    await Backend.resetWebApiCaches();
  }

  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();

  const recordId = await SalesOrderPage.selectCustomer(
    masterdata.bpartners.CUSTOMER1.bpartnerCode
  );
  console.log(`[${language}] Sales Order ${recordId} created`);

  await SalesOrderPage.goToOrderLineTab();

  await waitForTabAllowsNew(SALES_ORDER_WINDOW_ID, recordId, 'AD_Tab-187', {
    maxRetries: 15,
    retryDelayMs: 1000,
  });

  const batchEntryButton = page.getByTestId('batch-entry-toggle');
  await batchEntryButton.scrollIntoViewIfNeeded();
  await batchEntryButton.waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await batchEntryButton.click();

  await page.locator('.quick-input-container').waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });

  return { recordId, batchEntryButton };
}

/**
 * Type a product code in the quick input product field and wait for dropdown.
 */
async function typeProductAndWaitForDropdown(page, productCode) {
  const productInput = page.locator(
    '#lookup_M_Product_ID input.input-field'
  );
  await productInput.waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await productInput.click();

  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  await page.waitForTimeout(300);

  await productInput.fill(productCode);

  // Wait for typeahead debounce + search
  await page.waitForTimeout(500);

  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  // Wait for dropdown to populate
  await page.waitForTimeout(300);

  return productInput;
}

/**
 * Type into the Packvorschrift (M_HU_PI_Item_Product_ID) sub-field of the quick input
 * (index 2 of the composite product lookup) and wait for its dropdown.
 */
async function typePackingInstructionAndWaitForDropdown(page, text) {
  const piInput = page.locator('#lookup_M_HU_PI_Item_Product_ID input.input-field');
  await piInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await expect(piInput).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT }); // enabled once the product is set
  await piInput.click();
  await piInput.fill(text);
  await page.waitForTimeout(500);
  await page
    .locator('#lookup_M_HU_PI_Item_Product_ID .rotating, #lookup_M_HU_PI_Item_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.locator('.input-dropdown-list-option').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return piInput;
}

/** Assert the single order line carries the given M_HU_PI_Item_Product_ID. */
async function expectSingleLineWithPackingInstruction(recordId, expectedPiItemProductId) {
  const rows = await getTabRows(SALES_ORDER_WINDOW_ID, recordId, 'AD_Tab-187');
  expect(rows).toHaveLength(1);
  const piField = rows[0].fieldsByName.M_HU_PI_Item_Product_ID;
  // masterdata.packingInstructions.PI.tuPIItemProductTestId is a data-testid style string
  // ("tuPIItemProduct-<M_HU_PI_Item_Product_ID>"), not the bare id — strip the prefix.
  const expectedId = String(expectedPiItemProductId).replace(/^\D+/, '');
  expect(piField && piField.value && String(piField.value.key)).toBe(expectedId);
}

/**
 * Verify that a grid row exists in the order lines tab (after closing batch entry).
 * The grid renders as a standard HTML table: table > tbody > tr.
 */
async function expectOrderLineInGrid(page, expectedRowCount) {
  const gridRows = page.locator(
    '.table-flex-wrapper table tbody tr'
  );
  await gridRows.first().waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });

  if (expectedRowCount) {
    await expect(gridRows).toHaveCount(expectedRowCount, {
      timeout: SLOW_ACTION_TIMEOUT,
    });
  }
}

// ============================================================================
// Test cases
// ============================================================================

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Quick Input (${label})`, () => {
    // ------------------------------------------------------------------
    // TEST 1: Enter-key focus advance (the new feature)
    // ------------------------------------------------------------------
    test(`Enter-key selects product and advances focus to Qty (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Enter-key focus advance');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Enter-key focus advance

### Test Scenario
Validates that pressing Enter to select a product from the dropdown
in quick input (batch entry) advances focus to the Qty field.

1. Create masterdata → Login → Create Sales Order → Open batch entry
2. Type product code → wait for dropdown → press **Enter**
3. Verify product was resolved
4. Fill quantity → press Enter → verify line created

### Business Value
Keyboard-only workflow: product → Enter → qty → Enter → line added.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type product and wait for dropdown
      const productCode = masterdata.products.Product1.productCode;
      const productInput = await typeProductAndWaitForDropdown(
        page,
        productCode
      );

      // KEY ACTION: Press Enter to select from dropdown
      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      // Verify product was resolved
      const resolvedValue = await productInput.inputValue();
      expect(resolvedValue).toBeTruthy();
      console.log(`[${language}] Product resolved to: "${resolvedValue}"`);

      // Fill quantity (click explicitly — toBeFocused unreliable in headless)
      const quantityInput = page.getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('5');

      // Submit line
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Close batch entry and verify line
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 1);

      console.log(
        `[${language}] Enter-key focus advance test passed`
      );
    });

    // ------------------------------------------------------------------
    // TEST 2: Mouse-click selection regression
    // Ensures mouse-click on dropdown option still works after
    // the conditional this.focus() change in RawLookup.js.
    // ------------------------------------------------------------------
    test(`Mouse-click selects product in batch entry (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Mouse-click selection (regression)');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Mouse-click selection regression

### Test Scenario
Regression test: verifies that mouse-clicking a product option in the
quick input dropdown still works correctly after the focus-advance change.

1. Create masterdata → Login → Create Sales Order → Open batch entry
2. Type product code → wait for dropdown → **mouse click** on option
3. Verify product was resolved
4. Fill quantity → submit → verify line created

### Regression Target
RawLookup.handleSelect_RegularItem — conditional this.focus() with isMouseEvent=true.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type product and wait for dropdown
      const productCode = masterdata.products.Product1.productCode;
      const productInput = await typeProductAndWaitForDropdown(
        page,
        productCode
      );

      // KEY ACTION: Mouse click on dropdown option (NOT Enter)
      await page
        .locator('.input-dropdown-list-option')
        .getByText(productCode)
        .first()
        .click();

      await page.waitForTimeout(1000);

      // Verify product was resolved
      const resolvedValue = await productInput.inputValue();
      expect(resolvedValue).toBeTruthy();
      console.log(
        `[${language}] Mouse-click: Product resolved to: "${resolvedValue}"`
      );

      // Fill quantity
      const quantityInput = page.getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('7');

      // Submit line
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Close batch entry and verify line
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 1);

      console.log(
        `[${language}] Mouse-click selection regression test passed`
      );
    });

    // ------------------------------------------------------------------
    // TEST 3: Multiple lines in sequence
    // Verifies that quick input resets properly after adding a line,
    // so a second line can be added immediately via Enter-key workflow.
    // ------------------------------------------------------------------
    test(`Add two lines in sequence via Enter-key (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Multiple lines in sequence');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Multiple lines in sequence

### Test Scenario
Validates that quick input resets properly after adding a line,
allowing a second line to be added immediately.

1. Create masterdata with two products
2. Open batch entry, add first line via Enter-key workflow
3. Batch entry should stay open → add second line
4. Close batch entry, verify both lines exist

### Business Value
Continuous keyboard entry: line1 → line2 → ... without reopening batch entry.
      `);

      test.setTimeout(180000); // 3 minutes for two lines

      const masterdata = await createMasterdata(language, {
        twoProducts: true,
      });
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // --- LINE 1 ---
      const product1Code = masterdata.products.Product1.productCode;
      const productInput1 = await typeProductAndWaitForDropdown(
        page,
        product1Code
      );

      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      const resolved1 = await productInput1.inputValue();
      expect(resolved1).toBeTruthy();
      console.log(`[${language}] Line 1 product resolved: "${resolved1}"`);

      const quantityInput1 = page.getByRole('spinbutton');
      await quantityInput1.click();
      await quantityInput1.fill('3');

      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      console.log(`[${language}] Line 1 submitted`);

      // --- LINE 2 ---
      // Quick input should still be open; product field should be ready again
      const product2Code = masterdata.products.Product2.productCode;
      const productInput2 = await typeProductAndWaitForDropdown(
        page,
        product2Code
      );

      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      const resolved2 = await productInput2.inputValue();
      expect(resolved2).toBeTruthy();
      console.log(`[${language}] Line 2 product resolved: "${resolved2}"`);

      const quantityInput2 = page.getByRole('spinbutton');
      await quantityInput2.click();
      await quantityInput2.fill('8');

      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      console.log(`[${language}] Line 2 submitted`);

      // Close batch entry and verify both lines
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 2);

      console.log(
        `[${language}] Multiple lines test passed — 2 lines created`
      );
    });

    // ------------------------------------------------------------------
    // TEST 6 (TC2): Enter on the SECONDARY sub-field (Packvorschrift) of the
    // composite product lookup — the reported bug. Index 2, not index 0.
    // ------------------------------------------------------------------
    test(`Enter-key selects packing instruction on secondary sub-field (${label})`, async ({ page }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00101.10: Sales Order Quick Entry packing instruction');
      allure.tag('F00101.10');
      allure.story('Quick Input: packing instruction via Enter');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
      test.setTimeout(150000);

      const errors = collectPageErrors(page);
      const masterdata = await createMasterdata(language, { withPackingInstruction: true });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      const pi = masterdata.packingInstructions.PI;

      const { recordId } = await setupOrderWithBatchEntry(page, masterdata, language);

      errors.length = 0; // contract: zero browser errors during the quick-input steps only (TC2/TC6 steps 3–5)
      await typeProductAndWaitForDropdown(page, masterdata.products.Product1.productCode);
      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      const piInput = await typePackingInstructionAndWaitForDropdown(page, pi.tuName);
      await page.keyboard.press('Enter');          // ← the defective path (RawLookup.handleAutoSelectAndAdvance, index 2)
      await page.waitForTimeout(1000);
      expect(await piInput.inputValue()).toContain(pi.tuName);

      const quantityInput = page.getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('3');
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      expect(errors, `browser errors: ${errors.join('\n')}`).toEqual([]);
      await expectSingleLineWithPackingInstruction(recordId, pi.tuPIItemProductTestId);
      await expect(page.locator('#lookup_M_HU_PI_Item_Product_ID input.input-field')).toHaveValue('');
      await expect(page.locator('#lookup_M_Product_ID input.input-field')).toHaveValue('');
    });

    // ------------------------------------------------------------------
    // TEST 7 (TC6): same sub-field, MOUSE selection (handleSelect_RegularItem, index 2).
    // Works today; must keep working after the shared-helper refactor.
    // ------------------------------------------------------------------
    test(`Mouse-click selects packing instruction on secondary sub-field (${label})`, async ({ page }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00101.10: Sales Order Quick Entry packing instruction');
      allure.tag('F00101.10');
      allure.story('Quick Input: packing instruction via mouse');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);
      test.setTimeout(150000);

      const errors = collectPageErrors(page);
      const masterdata = await createMasterdata(language, { withPackingInstruction: true });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      const pi = masterdata.packingInstructions.PI;

      const { recordId } = await setupOrderWithBatchEntry(page, masterdata, language);

      errors.length = 0; // contract: zero browser errors during the quick-input steps only (TC2/TC6 steps 3–5)
      await typeProductAndWaitForDropdown(page, masterdata.products.Product1.productCode);
      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      await typePackingInstructionAndWaitForDropdown(page, pi.tuName);
      // getByText on a masterdata-generated TU name (language-invariant), not a localized caption
      await page.locator('.input-dropdown-list-option').getByText(pi.tuName).first().click();
      await page.waitForTimeout(1000);

      const quantityInput = page.getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('3');
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      expect(errors, `browser errors: ${errors.join('\n')}`).toEqual([]);
      await expectSingleLineWithPackingInstruction(recordId, pi.tuPIItemProductTestId);
      await expect(page.locator('#lookup_M_HU_PI_Item_Product_ID input.input-field')).toHaveValue('');
      await expect(page.locator('#lookup_M_Product_ID input.input-field')).toHaveValue('');
    });

    // ------------------------------------------------------------------
    // TEST 8 (TC7 part a): regular-form composite BPartner lookup — the
    // secondary sub-fields (Location, Contact) auto-fill from the server
    // after selecting the customer. In THIS window these sub-fields are
    // RawList-rendered (not RawLookup), so this guards the server auto-fill
    // reaching the secondary sub-fields — not the RawLookup call sites
    // themselves (those are covered by TESTs 6/7).
    // ------------------------------------------------------------------
    test(`Composite partner lookup: location and contact auto-fill in the regular form (${label})`, async ({ page }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Composite lookup: secondary sub-fields auto-filled in a regular form');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);
      test.setTimeout(150000);

      // Contract: zero browser errors for the whole scenario — not reset mid-test.
      const errors = collectPageErrors(page);
      const masterdata = await createMasterdata(language);
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const recordId = await SalesOrderPage.selectCustomer(
        masterdata.bpartners.CUSTOMER1.bpartnerCode
      );

      // Server auto-fill reached the secondary sub-fields — asserted, not logged.
      await expect(page.locator('#lookup_C_BPartner_Location_ID input.input-field')).not.toHaveValue(
        '',
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      // The server defaults AD_User_ID only from a contact flagged IsSalesContact_Default (CalloutOrder),
      // which the frontend-testing masterdata API cannot set — so only the sub-field's presence is asserted here; Location is the auto-filled value under test.
      await expect(page.locator('#lookup_AD_User_ID input.input-field')).toBeVisible();

      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 20, retryDelayMs: 1000 });

      expect(errors, `browser errors: ${errors.join('\n')}`).toEqual([]);
    });

    // ------------------------------------------------------------------
    // TEST 9 (TC8): keyboard-only line entry when the packing-instruction lookup
    // resolves to NO match. The product deliberately has no M_HU_PI_Item_Product,
    // so the dropdown offers only the synthetic empty row.
    //
    // Four lines in one order, same masterdata: the Tab path is the CONTROL, and the
    // three Enter shapes must reach the same outcome. The packing instruction is
    // asserted EQUAL TO THE TAB LINE's, never against a hard-coded id.
    // ------------------------------------------------------------------
    test(`Keyboard-only line entry when the product has no packing instruction (${label})`, async ({ page }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00101.10: Sales Order Quick Entry packing instruction');
      allure.tag('F00101.10');
      allure.story('Quick Input: Enter confirms the empty packing instruction');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
      test.setTimeout(240000);

      const errors = collectPageErrors(page);
      // No packingInstructions → the product has none at all (the reported data state).
      const masterdata = await createMasterdata(language);
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      const productCode = masterdata.products.Product1.productCode;

      const { recordId } = await setupOrderWithBatchEntry(page, masterdata, language);

      const piInput = page.locator('#lookup_M_HU_PI_Item_Product_ID input.input-field');
      const quantityInput = page.getByRole('spinbutton');

      errors.length = 0; // contract: zero browser errors during the keyboard steps

      /**
       * Enter one line keyboard-only. `escapeKeys` is what the operator presses on the
       * packing-instruction sub-field. Quantity is typed WITHOUT clicking, so the assertion
       * that it lands in Menge is the proof that focus advanced (TC8 step 5).
       */
      const enterOneLine = async (escapeKeys, qty, stepLabel) => {
        await test.step(`Line ${qty}: ${stepLabel}`, async () => {
          await test.step('Type the product code, press Enter', async () => {
            await typeProductAndWaitForDropdown(page, productCode);
            await page.keyboard.press('Enter');
            await page.waitForTimeout(1500);
            await expect(piInput).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
          });

          await test.step(
            `Packvorschrift has no value - press ${stepLabel}`,
            async () => {
              for (const key of escapeKeys) {
                if (key.startsWith('type:')) {
                  await page.keyboard.type(key.slice('type:'.length));
                } else {
                  await page.keyboard.press(key);
                }
                await page.waitForTimeout(500);
              }
              await page.waitForTimeout(1000);
            }
          );

          // Focus must now be on Menge: type the quantity blind and assert it landed there.
          await test.step(
            `Type quantity ${qty} blind - it must land in Menge, not in Packvorschrift`,
            async () => {
              await page.keyboard.type(String(qty));
              await page.waitForTimeout(400);
              await expect(quantityInput).toHaveValue(String(qty), {
                timeout: SLOW_ACTION_TIMEOUT,
              });
              await expect(piInput).toHaveValue('');
            }
          );

          await test.step('Press Enter - the order line is created', async () => {
            await page.keyboard.press('Enter');
            await page.waitForTimeout(2500);
          });
        });
      };

      // CONTROL: Tab — the pre-existing escape that already worked.
      await enterOneLine(['Tab'], 3, 'Tab (control: already worked before the fix)');
      // Bare Enter on the empty field (dropdown closed).
      await enterOneLine(['Enter'], 4, 'Enter on the empty field');
      // ArrowDown twice (first opens the list, second highlights the empty row), then Enter.
      await enterOneLine(
        ['ArrowDown', 'ArrowDown', 'Enter'],
        5,
        'ArrowDown x2 then Enter (the reported case)'
      );
      // Typed text matching nothing, then Enter — Tab-identical by decision 2026-09-09.
      await enterOneLine(
        ['type:xyzzy', 'Enter'],
        6,
        'type text matching nothing, then Enter'
      );

      expect(errors, `browser errors: ${errors.join('\n')}`).toEqual([]);

      const rows = await getTabRows(SALES_ORDER_WINDOW_ID, recordId, 'AD_Tab-187');
      expect(rows).toHaveLength(4);

      // NOTE: fieldsByName.<X> is the field WRAPPER; the lookup id lives at .value.key
      // (same accessor as expectSingleLineWithPackingInstruction above).
      const byQty = {};
      for (const row of rows) {
        const piField = row.fieldsByName.M_HU_PI_Item_Product_ID;
        byQty[String(row.fieldsByName.QtyEntered.value)] = piField && piField.value;
      }
      expect(Object.keys(byQty).sort()).toEqual(['3', '4', '5', '6']);

      // The Tab line defines the expected packing instruction; the three Enter lines must match it.
      const tabLinePi = byQty['3'];
      // Pin the control itself: without this, a regression that dropped the packing instruction
      // from EVERY line would let the loop below pass by mutual absence (undefined === undefined).
      expect(
        tabLinePi && tabLinePi.key,
        'the Tab control line must itself carry a packing instruction'
      ).toBeTruthy();
      for (const qty of ['4', '5', '6']) {
        expect(
          byQty[qty] && byQty[qty].key,
          `line qty=${qty} must carry the same packing instruction as the Tab line`
        ).toBe(tabLinePi && tabLinePi.key);
      }
    });

    // ------------------------------------------------------------------
    // TEST 4: Invalid product — no beep when sysconfig disabled (default)
    // Verifies that pressing Enter on a non-existent product does NOT
    // advance focus and does NOT beep when sysconfig is off (default).
    // ------------------------------------------------------------------
    test(`Invalid product: no beep when sysconfig disabled (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Invalid product no beep (default)');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Invalid product no beep (sysconfig disabled)

### Test Scenario
Validates that pressing Enter with a non-existent product code:
1. Does NOT trigger an audible beep (sysconfig beepOnInvalidProduct defaults to N)
2. Does NOT advance focus to the quantity field
3. The product field retains the invalid text (not resolved)
4. No order line is created

### Business Value
Default behavior: no beep. Beep must be explicitly enabled via SysConfig.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      // Spy on Web Audio API to detect beep (headless browsers produce no audio)
      await page.addInitScript(() => {
        window.__beepCount = 0;
        const origStart = OscillatorNode.prototype.start;
        OscillatorNode.prototype.start = function (...args) {
          window.__beepCount++;
          return origStart.apply(this, args);
        };
      });

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type an invalid product code
      const productInput = page.locator(
        '#lookup_M_Product_ID input.input-field'
      );
      await productInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await productInput.click();

      await page
        .locator(
          '#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner'
        )
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await page.waitForTimeout(300);

      await productInput.fill('NONEXISTENT_PRODUCT_XYZ_99999');

      // Wait for typeahead debounce
      await page.waitForTimeout(1500);

      // Press Enter — should beep and NOT advance focus
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Verify: NO beep was initiated (sysconfig default is N)
      const beepCount = await page.evaluate(() => window.__beepCount);
      expect(beepCount).toBe(0);
      console.log(
        `[${language}] Invalid product: no beep (sysconfig disabled, beepCount=${beepCount})`
      );

      // Verify: product input should still contain the invalid text
      // (not cleared, not resolved to a real product)
      const productValue = await productInput.inputValue();
      expect(productValue).toContain('NONEXISTENT');
      console.log(
        `[${language}] Invalid product: field still shows "${productValue}"`
      );

      // Close batch entry and verify NO order lines were created
      await batchEntryButton.click();
      await page.waitForTimeout(500);

      const gridRows = page.locator('.table-flex-wrapper table tbody tr');
      const rowCount = await gridRows.count();
      expect(rowCount).toBe(0);

      console.log(
        `[${language}] Invalid product test passed — no lines created`
      );
    });

    // ------------------------------------------------------------------
    // TEST 5: Regular form lookup regression
    // Verifies that selecting a customer via the BPartner lookup in the
    // Sales Order header (non-quick-input context) still works correctly.
    // In this context, onChange returns a Promise, so shouldKeepFocus
    // must remain true and this.focus() must still be called.
    // ------------------------------------------------------------------
    test(`Regular form lookup: customer selection still works (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Regular form lookup: selection regression');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Regular form lookup regression

### Test Scenario
Regression test: verifies that selecting a customer in the Sales Order
header (BPartner composed lookup — NOT inside quick input) still works
correctly after the setNextProperty/focus changes.

1. Create masterdata → Login → Create Sales Order
2. Select customer via lookup (mouse click on dropdown option)
3. Verify customer is saved (record saved, field shows customer name)

### Regression Target
Lookup.setNextProperty return value + RawLookup conditional this.focus().
In non-quick-input context (onChange returns Promise), shouldKeepFocus
must remain true — behavior must be identical to before the change.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();

      // Select customer — this uses mouse click on dropdown option internally
      // and waits for record to be saved (validates auto-fill completes).
      // If our change broke regular form lookups, this would fail.
      const recordId = await SalesOrderPage.selectCustomer(
        masterdata.bpartners.CUSTOMER1.bpartnerCode
      );

      expect(recordId).toBeTruthy();
      console.log(
        `[${language}] Regular form lookup regression passed — SO ${recordId} created with customer`
      );

      // Verify the BPartner field shows the selected customer
      const bpartnerInput = page.locator(
        '#lookup_C_BPartner_ID input.input-field'
      );
      const bpartnerValue = await bpartnerInput.inputValue();
      expect(bpartnerValue).toBeTruthy();
      expect(bpartnerValue).toContain(
        masterdata.bpartners.CUSTOMER1.bpartnerCode
      );

      console.log(
        `[${language}] BPartner field value: "${bpartnerValue}"`
      );
    });

    // ------------------------------------------------------------------
    // TESTS 10-11 (TC1/TC2): both flip webui.quickinput.EnablePackingInstructionsField
    // and/or webui.quickinput.ProductFieldWidgetSize at runtime via createMasterdata's
    // `sysconfigs` option. Unlike the AD_SysConfig defaults SysconfigCommand resets on
    // every call (barcode-scanner keys only — see SysconfigCommand.SCANNER_SYSCONFIG_DEFAULTS),
    // these two quick-input keys are NOT in that reset set, so a value written by one
    // test persists globally (it's a live DB row) until something else overwrites it.
    // Left unrestored, that leaks into every later test in this file and other specs:
    // EnablePackingInstructionsField='N' hides the Packvorschrift field (breaking the
    // packing-instruction tests above, which expect it visible/enabled), and
    // ProductFieldWidgetSize='L' makes an unrelated "default width" assertion see the L
    // width instead. Nest these two tests in their own describe with an afterEach that
    // restores both keys to their core migration defaults
    // (EnablePackingInstructionsField='Y' — 5482620_sys_gh745webui_..., empty for
    // ProductFieldWidgetSize — 5821510_sys_gh31653_...) and drops the webapi node's
    // QuickInputDescriptors cache, so nothing survives past either test regardless of
    // pass/fail.
    // ------------------------------------------------------------------
    test.describe('Produkt field width (ProductFieldWidgetSize) - sysconfig isolation', () => {
      test.afterEach(async () => {
        // Restore to core migration defaults — see SysconfigCommand doc above for why
        // this can't rely on the automatic per-test reset (that only covers scanner keys).
        await Backend.setSysconfigs({
          'webui.quickinput.EnablePackingInstructionsField': 'Y',
          'webui.quickinput.ProductFieldWidgetSize': '',
        });
        // Drop the webapi-node QuickInputDescriptors cache too, so the restored value is
        // observed immediately rather than serving the stale (test-set) descriptor to the
        // next test that opens batch entry. Needs an authenticated webapi session
        // (GET /cache/reset -> userSession.assertLoggedIn()); guard it so a test that
        // failed BEFORE login (no session yet) still gets its sysconfigs restored above
        // instead of throwing out of afterEach and masking the real failure.
        try {
          await Backend.resetWebApiCaches();
        } catch (err) {
          console.log(
            `[sysconfig cleanup] resetWebApiCaches skipped/failed (likely not logged in yet): ${err}`
          );
        }
      });

      // ------------------------------------------------------------------
      // TEST 10 (TC1): Produkt field keeps its default width when
      // webui.quickinput.ProductFieldWidgetSize is unset.
      //
      // Precondition (load-bearing): packing instructions must be OFF, else
      // the Produkt field renders as a Composed widget (not a single-field
      // widgetType-Lookup) and widgetSize-L never applies to it. The e2e
      // seed DB defaults packing instructions ON, so this must be disabled
      // explicitly via the sysconfigs map.
      //
      // ProductFieldWidgetSize is explicitly set to '' (the default) rather than
      // left unset, so this test asserts the default-width case regardless of
      // whatever a previous test in the run may have left behind (order-independent).
      // ------------------------------------------------------------------
      test(`quick-input Produkt field keeps default width when ProductFieldWidgetSize is unset (${label})`, async ({
        page,
      }) => {
        allure.epic('E0100: Sales');
        allure.tag('F00100: Sales Order');
        allure.tag('F00100');
        allure.story('Quick Input: Produkt field default width');
        allure.severity('normal');
        allure.parameter('Language', language);
        allure.tag(language);

        allure.description(`
## F00100: Sales Order — Produkt quick-input default width

### Test Scenario
Validates that the Produkt field in the quick input (batch entry) keeps
its default width when webui.quickinput.ProductFieldWidgetSize is unset.

### Business Value
No regression to the default layout when the new sysconfig is not set.
      `);

        test.setTimeout(120000);

        const masterdata = await createMasterdata(language, {
          sysconfigs: {
            'webui.quickinput.EnablePackingInstructionsField': 'N',
            // Explicit (not omitted): asserts the default-width case regardless of
            // whatever a previous test left behind — see the describe-level comment.
            'webui.quickinput.ProductFieldWidgetSize': '',
          },
        });
        allure.attachment(
          'Test Data',
          JSON.stringify(masterdata, null, 2),
          'application/json'
        );

        await setupOrderWithBatchEntry(page, masterdata, language, {
          resetWebApiCaches: true,
        });

        const productGroup = page.locator('.quick-input-container .form-group', {
          has: page.locator('#lookup_M_Product_ID'),
        });
        await expect(productGroup).toBeVisible();
        await expect(productGroup).not.toHaveClass(/widgetSize-L/);

        const fontPx = await productGroup.evaluate((el) =>
          parseFloat(getComputedStyle(el).fontSize)
        );
        const box = await productGroup.boundingBox();
        // Default Lookup is capped at 20em; assert below the 30em widgetSize-L floor.
        // Font-size-relative so it holds regardless of the app's base font size.
        expect(box.width).toBeLessThan(25 * fontPx);

        console.log(
          `[${language}] Produkt field default width: ${box.width}px`
        );
      });

      // ------------------------------------------------------------------
      // TEST 11 (TC2): Produkt field is wider when
      // webui.quickinput.ProductFieldWidgetSize=L. Same packing-instructions
      // precondition as TEST 10 (see comment above).
      // ------------------------------------------------------------------
      test(`quick-input Produkt field is wider when ProductFieldWidgetSize=L (${label})`, async ({
        page,
      }) => {
        allure.epic('E0100: Sales');
        allure.tag('F00100: Sales Order');
        allure.tag('F00100');
        allure.story('Quick Input: Produkt field widened via ProductFieldWidgetSize=L');
        allure.severity('normal');
        allure.parameter('Language', language);
        allure.tag(language);

        allure.description(`
## F00100: Sales Order — Produkt quick-input widened width

### Test Scenario
Validates that the Produkt field in the quick input (batch entry) is
rendered wider when webui.quickinput.ProductFieldWidgetSize=L, carrying
a widgetSize-L class on its form-group.

### Business Value
The order-line quick-input Produkt field can be widened via SysConfig
(gh31653), improving legibility of long product names/codes.
      `);

        test.setTimeout(120000);

        const masterdata = await createMasterdata(language, {
          sysconfigs: {
            'webui.quickinput.EnablePackingInstructionsField': 'N',
            'webui.quickinput.ProductFieldWidgetSize': 'L',
          },
        });
        allure.attachment(
          'Test Data',
          JSON.stringify(masterdata, null, 2),
          'application/json'
        );

        await setupOrderWithBatchEntry(page, masterdata, language, {
          resetWebApiCaches: true,
        });

        const productGroup = page.locator('.quick-input-container .form-group', {
          has: page.locator('#lookup_M_Product_ID'),
        });
        await expect(productGroup).toBeVisible();
        await expect(productGroup).toHaveClass(/widgetSize-L/);

        const fontPx = await productGroup.evaluate((el) =>
          parseFloat(getComputedStyle(el).fontSize)
        );
        const box = await productGroup.boundingBox();
        // widgetSize-L sets min-width:30em; assert >= ~30em, font-size-relative so it
        // holds regardless of the app's base font size (and materially wider than TC1's <25em).
        expect(box.width).toBeGreaterThanOrEqual(29 * fontPx);

        console.log(
          `[${language}] Produkt field widened width: ${box.width}px`
        );
      });
    });
  });
});
