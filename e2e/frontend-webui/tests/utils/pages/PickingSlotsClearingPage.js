import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { WEBAPI_BASE_URL } from '../Backend';

// window id kept local (not in WindowIds.js), so that this file merges cleanly into branches whose WindowIds.js differs
/** Picking Tray Clearing window (PickingSlotsClearingViewFactory) */
const PICKING_SLOTS_CLEARING_WINDOW_ID = 540371;


/**
 * Page object for the desktop "Picking Tray Clearing" window (540371).
 *
 * Left view: picking slots (row `table-row-<pickingSlotId>`) with their top-level HUs
 * (row `table-row-<pickingSlotId>-<huId>`). Right view ("packingHUs"): the HUs on an after-picking locator of the
 * selected slot's customer and location; its top-level rows are `table-row-<huId>`.
 *
 * Quick actions are addressed by `quick-action-<process internal name>`, dialog fields by ColumnName
 * (`.form-field-<ColumnName>`), lookup entries by `option-<ID>`.
 */
export class PickingSlotsClearingPage {
  static #viewId = null;
  static #packingHUsViewId = null;

  /**
   * Opens the window filtered by the slot's customer (a DB holds the slots of many customers),
   * and remembers the right-hand view's id.
   */
  static async open({ bpartnerId, pickingSlotId }) {
    return await test.step(`PickingSlotsClearingPage - Open for customer ${bpartnerId}`, async () => {
      const page = getPage();
      const createViewResponse = await page.request.post(`${WEBAPI_BASE_URL}/documentView/${PICKING_SLOTS_CLEARING_WINDOW_ID}`, {
        headers: { 'Content-Type': 'application/json' },
        data: {
          windowId: String(PICKING_SLOTS_CLEARING_WINDOW_ID),
          viewType: 'grid',
          filters: [{ filterId: 'BPartner', parameters: [{ parameterName: 'C_BPartner_ID', value: bpartnerId }] }],
        },
      });
      expect(createViewResponse.ok(), 'picking slots clearing view created').toBe(true);
      this.#viewId = (await createViewResponse.json()).viewId;

      const rows = await this.#getViewRows({ windowId: PICKING_SLOTS_CLEARING_WINDOW_ID, viewId: this.#viewId });
      const slotRow = rows.find((row) => row.id === String(pickingSlotId));
      expect(slotRow, `picking slot ${pickingSlotId} listed`).toBeDefined();
      this.#packingHUsViewId = slotRow.includedView.viewId;

      await page.goto(`${FRONTEND_BASE_URL}/window/${PICKING_SLOTS_CLEARING_WINDOW_ID}?viewId=${this.#viewId}`);
      await page.getByTestId(`table-row-${pickingSlotId}`).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
  }

  /** Selects the picking slot row (it can come pre-selected; a click would then deselect it). */
  static async selectPickingSlot({ pickingSlotId }) {
    return await test.step(`PickingSlotsClearingPage - Select picking slot ${pickingSlotId}`, async () => {
      const row = getPage().getByTestId(`table-row-${pickingSlotId}`);
      if (!(await row.evaluate((el) => el.classList.contains('row-selected')))) {
        await row.locator('td').nth(1).click();
      }
      await expect(row).toHaveClass(/\brow-selected\b/, { timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** Selects the given top-level HU rows of the slot: click on the first, Ctrl+click on the others. */
  static async selectHUs({ pickingSlotId, huIds }) {
    return await test.step(`PickingSlotsClearingPage - Select ${huIds.length} HU(s)`, async () => {
      const page = getPage();
      for (let i = 0; i < huIds.length; i++) {
        const row = page.getByTestId(`table-row-${pickingSlotId}-${huIds[i]}`);
        await row.scrollIntoViewIfNeeded();
        await row.locator('td').nth(1).click(i === 0 ? {} : { modifiers: ['Control'] });
      }
      await expect(page.locator('table').first().locator('tbody tr.row-selected')).toHaveCount(huIds.length, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }

  static async runTakeOutTUsAndAddToNewLUs() {
    return await test.step('PickingSlotsClearingPage - TUs to new LUs', async () => {
      await this.#startProcess({
        actionInternalName: 'WEBUI_PickingSlotsClearingView_TakeOutTUsAndAddToNewLUs',
      });
    });
  }

  /** "Take out HUs and add to new HU" on the selected HUs; target = the packing instruction with that M_HU_PI_ID. */
  static async runTakeOutMultiHUsAndAddToNewHU({ huPIName, huPIId, expectSuccess = true }) {
    return await test.step(`PickingSlotsClearingPage - Take out HUs and add to new HU ${huPIName}`, async () => {
      return await this.#startProcess({
        actionInternalName: 'WEBUI_PickingSlotsClearingView_TakeOutMultiHUsAndAddToNewHU',
        fillParams: async () => await this.#selectLookupValue({ columnName: 'M_HU_PI_ID', text: huPIName, id: huPIId }),
        expectSuccess,
      });
    });
  }

  /** "Take out HU and add to new HU" on the selected HU; target = the packing instruction with that M_HU_PI_ID. */
  static async runTakeOutHUAndAddToNewHU({ huPIName, huPIId, qtyCUsPerTU, expectSuccess = true }) {
    return await test.step(`PickingSlotsClearingPage - Take out HU and add to new HU ${huPIName}`, async () => {
      return await this.#startProcess({
        actionInternalName: 'WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToNewHU',
        fillParams: async () => {
          await this.#selectLookupValue({ columnName: 'M_HU_PI_ID', text: huPIName, id: huPIId });
          const qtyField = getPage().locator('.panel-modal-content .form-field-QtyCUsPerTU input');
          await qtyField.fill(String(qtyCUsPerTU));
          await qtyField.press('Tab');
        },
        expectSuccess,
      });
    });
  }

  /**
   * @returns {Promise<string[]>} ids (= M_HU_IDs) of the top-level rows of the right-hand view with that unit type ("LU", "TU")
   */
  static async getPackingHUIds({ huUnitType }) {
    return await test.step(`PickingSlotsClearingPage - Get packing HU ids (${huUnitType})`, async () => {
      const rows = await this.#getViewRows({ windowId: 'packingHUs', viewId: this.#packingHUsViewId });
      return rows.filter((row) => row.type === huUnitType).map((row) => row.id);
    });
  }

  /** UI check: the right-hand view shows exactly these top-level rows. */
  static async expectPackingHURows({ huIds }) {
    return await test.step(`PickingSlotsClearingPage - Expect packing HU rows ${huIds}`, async () => {
      const page = getPage();
      const rightTable = page.locator('table').last();
      for (const huId of huIds) {
        await expect(rightTable.getByTestId(`table-row-${huId}`)).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
      }
      // top-level rows have a plain numeric row id; included rows are "<id>_T<parentId>" / "<id>-<productId>_T…"
      const topLevelRowIds = await rightTable
        .locator('tbody tr[data-testid^="table-row-"]')
        .evaluateAll((rows) => rows.map((row) => row.getAttribute('data-testid')).filter((id) => /^table-row-\d+$/.test(id)));
      expect(topLevelRowIds.sort()).toEqual(huIds.map((huId) => `table-row-${huId}`).sort());
    });
  }

  static async #startProcess({ actionInternalName, fillParams, expectSuccess = true }) {
    const page = getPage();
    // the first toggle belongs to the left (picking slots) view
    await page.getByTestId('quick-action-dropdown-toggle').first().click();
    const action = page.getByTestId(`quick-action-${actionInternalName}`);
    await action.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // the processes move every selected HU one by one: with 80 TUs they can take more than a minute
    const startResponseTimeout = 3 * 60 * 1000;
    const isStartResponse = (response) =>
      response.request().method() === 'GET' && /\/process\/[^/]+\/[^/]+\/start/.test(response.url());

    let startResponsePromise;
    if (fillParams) {
      await action.click();
      await page.locator('.panel-modal').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await fillParams();
      startResponsePromise = page.waitForResponse(isStartResponse, { timeout: startResponseTimeout });
      await page.getByTestId('process-modal-start-button').click();
    } else {
      startResponsePromise = page.waitForResponse(isStartResponse, { timeout: startResponseTimeout });
      await action.click();
    }

    const startResponse = await startResponsePromise;
    if (expectSuccess) {
      expect(startResponse.ok(), `${actionInternalName} succeeded: ${await startResponse.text()}`).toBe(true);
      await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.locator('.screen-freeze').waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
    }
    return startResponse;
  }

  static async #selectLookupValue({ columnName, text, id }) {
    const page = getPage();
    const input = page.locator(`.panel-modal-content .form-field-${columnName} input`);
    await input.click();
    await input.pressSequentially(text);
    const option = page.getByTestId(`option-${id}`);
    await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await option.click();
    await expect(input).toHaveValue(text, { timeout: SLOW_ACTION_TIMEOUT });
  }

  static async #getViewRows({ windowId, viewId }) {
    const response = await getPage().request.get(`${WEBAPI_BASE_URL}/documentView/${windowId}/${viewId}?firstRow=0&pageLength=500`);
    expect(response.ok(), `rows of view ${viewId}`).toBe(true);
    return (await response.json()).result ?? [];
  }
}
