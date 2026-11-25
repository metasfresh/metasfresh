import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FAST_ACTION_TIMEOUT, FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { INVENTORY_WINDOW_ID } from '../WindowIds';
import { getFieldData } from '../WebAPIValidation';

/**
 * Page object for the desktop "Physical Inventory" window (168).
 *
 * Header fields are addressed by ColumnName (`.form-field-<ColumnName>`), dropdown entries by `option-<ID>`.
 * A line is created with the "Add new" button of the lines tab; it opens the line in a modal
 * (`.panel-modal`, closed with its "Done" button), whose fields are addressed the same way.
 */
export class InventoryPage {
  static #inventoryId = null;

  /** Creates a new inventory with the given warehouse and document type; returns its M_Inventory_ID. */
  static async createNew({ warehouseId, docTypeId }) {
    return await test.step(`InventoryPage - Create new inventory (warehouse ${warehouseId})`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${INVENTORY_WINDOW_ID}/NEW`);
      await page.waitForURL(new RegExp(`/window/${INVENTORY_WINDOW_ID}/\\d+$`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      this.#inventoryId = page.url().match(/\/(\d+)$/)[1];

      await this.#selectOption({ scope: page.locator('body'), columnName: 'M_Warehouse_ID', id: warehouseId });
      await this.#selectOption({ scope: page.locator('body'), columnName: 'C_DocType_ID', id: docTypeId });

      // the header is saved once the mandatory fields are set: only then the lines tab offers "Add new"
      await this.#addNewButton().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      return this.#inventoryId;
    });
  }

  /**
   * Adds a counted line and leaves its modal open (see {@link selectLUPacking}, {@link closeLine}).
   * @param {Object} options
   * @param {number} options.productId - M_Product_ID
   * @param {string} options.productValue - M_Product.Value, typed to search the product
   * @param {number} options.locatorId - M_Locator_ID
   * @param {number} options.qtyCount - counted quantity in the product's UOM
   * @param {number} options.tuPIItemProductId - M_HU_PI_Item_Product_ID of the TU packing
   */
  static async addLine({ productId, productValue, locatorId, qtyCount, tuPIItemProductId }) {
    return await test.step(`InventoryPage - Add line for product ${productId}`, async () => {
      const page = getPage();
      await this.#addNewButton().click();
      const modal = page.locator('.panel-modal');
      await modal.locator('.form-field-M_Product_ID input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await this.#selectOption({ scope: modal, columnName: 'M_Product_ID', id: productId, searchText: productValue });
      await this.#selectOption({ scope: modal, columnName: 'M_Locator_ID', id: locatorId });

      const qtyCountInput = modal.locator('.form-field-QtyCount input');
      await qtyCountInput.fill(String(qtyCount));
      await qtyCountInput.press('Tab');
      await expect(qtyCountInput).toHaveValue(String(qtyCount), { timeout: SLOW_ACTION_TIMEOUT });

      // "Counted": the inventory completes only when all its lines are counted.
      // The counted quantity's save can re-render the line and drop a click made meanwhile: let pending saves settle,
      // then click only while unchecked (idempotent, retried).
      await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      const isCounted = modal.locator('.form-field-IsCounted input[type="checkbox"]').first();
      await expect(async () => {
        if (!(await isCounted.isChecked())) {
          await isCounted.evaluate((el) => el.click()); // a click on the styled wrapper does not reach the input
        }
        await expect(isCounted).toBeChecked({ timeout: FAST_ACTION_TIMEOUT });
      }).toPass({ timeout: VERY_SLOW_ACTION_TIMEOUT });

      await this.#selectOption({ scope: modal, columnName: 'M_HU_PI_Item_Product_ID', id: tuPIItemProductId });
    });
  }

  /**
   * Opens the line's pallet packing ("Packvorschrift (LU)") dropdown, reads the offered M_HU_PI_IDs and selects
   * {@code luPIId} if it is offered (else the dropdown stays open and the caller's assertion on the returned ids fails).
   * One dropdown interaction: Escape would close the line's modal.
   * @returns {Promise<number[]>} the offered M_HU_PI_IDs
   */
  static async selectLUPacking({ luPIId }) {
    return await test.step(`InventoryPage - Select pallet packing ${luPIId}`, async () => {
      const page = getPage();
      let offeredIds = [];
      await expect(async () => {
        const dropdown = await this.#openDropdown({ scope: page.locator('.panel-modal'), columnName: 'M_LU_HU_PI_ID' });
        // the lookup lists its empty entry together with the loaded options: once it is there, the list is complete
        await dropdown.locator('[data-testid="option-null"]').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
        const testIds = await dropdown.locator('.input-dropdown-list-option').evaluateAll((options) =>
          options.map((option) => option.getAttribute('data-testid'))
        );
        offeredIds = testIds
          .map((testId) => Number(String(testId).replace(/^option-/, '')))
          .filter((id) => Number.isInteger(id) && id > 0); // drops the empty entry ("option-null")

        if (offeredIds.includes(luPIId)) {
          await dropdown.locator(`[data-testid="option-${luPIId}"]`).click({ timeout: FAST_ACTION_TIMEOUT });
          await dropdown.waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
        }
      }).toPass({ timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      return offeredIds;
    });
  }

  static async closeLine() {
    return await test.step('InventoryPage - Close the line', async () => {
      const page = getPage();
      // the record modal's "Done" button (Modal.js gives it this test id for every modal type)
      await page.locator('.panel-modal').getByTestId('process-modal-cancel-button').click();
      await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** Completes the inventory (document action CO) and waits until its DocStatus is CO. */
  static async complete() {
    return await test.step('InventoryPage - Complete', async () => {
      const page = getPage();
      await page.getByTestId('status-button').click();
      await page.getByTestId('status-CO').click();

      await expect
        .poll(async () => {
          const docStatus = await getFieldData(INVENTORY_WINDOW_ID, this.#inventoryId, 'DocStatus');
          return docStatus.value?.key ?? docStatus.value;
        }, { message: 'DocStatus of the inventory', timeout: 2 * 60 * 1000 }) // completion builds the HUs synchronously
        .toBe('CO');
    });
  }

  static #addNewButton() {
    // language-independent: the lines tab's "Add new" button (its caption is translated), not the batch-entry toggle
    return getPage()
      .locator('.table-filter-line .filter-panel-buttons button.btn-distance:not(.close-batch-entry)')
      .first();
  }

  /** @param {string} [searchText] - typed into the field first, for lookups that list nothing until searched */
  static async #openDropdown({ scope, columnName, searchText }) {
    const page = getPage();
    const input = scope.locator(`.form-field-${columnName} input`).first();
    await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await input.click();
    if (searchText) {
      await input.fill(searchText);
    }
    const dropdown = page.locator('.input-dropdown-list');
    await dropdown.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    return dropdown;
  }

  /**
   * Opens the field's dropdown and clicks the option with that ID. Retried: a field saved just before (e.g. the counted
   * quantity) can re-render the line and close a dropdown that was opened meanwhile.
   */
  static async #selectOption({ scope, columnName, id, searchText }) {
    const page = getPage();
    await expect(async () => {
      const dropdown = await this.#openDropdown({ scope, columnName, searchText });
      await dropdown.locator(`[data-testid="option-${id}"]`).click({ timeout: FAST_ACTION_TIMEOUT });
      await dropdown.waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
    }).toPass({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  }
}
