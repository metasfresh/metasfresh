import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import {
  FRONTEND_BASE_URL,
  getPage,
  holdForCaptureIfEnabled,
  isUatCapture,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from '../common';
import { getAccountingFacts, getFieldData, WEBAPI_BASE_URL } from '../WebAPIValidation';

export const COST_REVAL_WINDOW_ID = '541568';
export const COST_REVAL_LINE_TAB_ID = 'AD_Tab-546465';

/**
 * Render isoDate (yyyy-MM-dd) in the display format of a date field whose current text is `shown` while its
 * stored value is currentIsoDate. Supports the day/month/year orders of the tested languages (en_US, de_DE);
 * on a day where day == month, MM/dd/yyyy and dd/MM/yyyy cannot be told apart and the first candidate wins.
 */
const toDisplayFormat = (shown, currentIsoDate, isoDate) => {
  const [ty, tm, td] = currentIsoDate.substring(0, 10).split('-');
  const [y, m, d] = isoDate.split('-');
  const candidates = [
    { today: `${td}.${tm}.${ty}`, text: `${d}.${m}.${y}` },
    { today: `${tm}/${td}/${ty}`, text: `${m}/${d}/${y}` },
    { today: `${td}/${tm}/${ty}`, text: `${d}/${m}/${y}` },
    { today: `${ty}-${tm}-${td}`, text: `${y}-${m}-${d}` },
  ];
  const match = candidates.find((c) => shown.trim() === c.today);
  if (!match) {
    throw new Error(`Cannot derive the date display format from ${JSON.stringify(shown)} (stored value ${currentIsoDate})`);
  }
  return match.text;
};

/**
 * In an evidence-capture run only: outline the given element so the viewer's eye lands on it.
 * Returns a function that removes the outline again. No-op (and no DOM change) otherwise.
 */
const highlightForCaptureIfEnabled = async (locator) => {
  if (!isUatCapture()) {
    return async () => {};
  }
  await locator.evaluate((el) => {
    el.dataset.captureOutline = el.style.outline || '';
    el.style.outline = '3px solid #e8a100';
    el.style.outlineOffset = '2px';
  });
  return async () => {
    await locator
      .evaluate((el) => {
        el.style.outline = el.dataset.captureOutline || '';
        el.style.outlineOffset = '';
      })
      .catch(() => {});
  };
};

/**
 * Page object for the Kosten Neubewertung (Cost Revaluation, M_CostRevaluation) window 541568
 * and its per-product quick-input.
 */
export class CostRevaluationPage {
  /**
   * Open a NEW header. The mandatory Evaluation Start Date auto-defaults to the posting date (today),
   * so the header is saved without the user typing it.
   * @returns {Promise<string>} the new record id
   */
  static async createHeader() {
    return await test.step('Create Kosten Neubewertung header (EvaluationStartDate auto-defaults to the posting date)', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/new`);

      // Asserted at the UI layer: the field arrives pre-filled, no click, no fill.
      const dateInput = page.locator('.form-field-EvaluationStartDate input').first();
      await dateInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(dateInput).not.toHaveValue('', { timeout: SLOW_ACTION_TIMEOUT });

      // The document acquires its numeric record id in the URL once all mandatory header fields are satisfied.
      await page.waitForFunction(
        () => {
          const last = window.location.pathname.split('/').pop();
          return last && last !== 'new' && /^\d+$/.test(last);
        },
        undefined,
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const recordId = page.url().split('/').pop();

      // At the data layer (language-independent): the auto-filled value IS the header's posting date (DateAcct,
      // defaulted by the server; compared to it rather than to the test runner's clock, which may differ in time zone).
      const dateAcct = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct');
      const evalStart = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate');
      expect(dateAcct.value).toBeTruthy();
      expect(String(evalStart.value).substring(0, 10)).toBe(String(dateAcct.value).substring(0, 10));

      const unhighlight = await highlightForCaptureIfEnabled(page.locator('.form-field-EvaluationStartDate').first());
      await holdForCaptureIfEnabled(2500);
      await unhighlight();

      return recordId;
    });
  }

  /** @returns {import('@playwright/test').Locator} the header date input of the given ColumnName */
  static headerDateInput(columnName) {
    return getPage().locator(`.form-field-${columnName} input[type="text"]`).first();
  }

  /**
   * Type a date into a header date field the way a user does (select all, type, Tab) and wait for the save.
   * The date is typed in the display format the field currently shows (derived from its shown text and its
   * stored value), so this works for every login language.
   * @param {string} recordId the header record id
   * @param {string} columnName e.g. DateAcct
   * @param {string} isoDate yyyy-MM-dd
   */
  static async typeHeaderDate(recordId, columnName, isoDate) {
    const page = getPage();
    const input = this.headerDateInput(columnName);
    await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const shown = await input.inputValue();
    const current = await getFieldData(COST_REVAL_WINDOW_ID, recordId, columnName);
    const text = toDisplayFormat(shown, String(current.value), isoDate);

    const unhighlight = await highlightForCaptureIfEnabled(page.locator(`.form-field-${columnName}`).first());
    const saved = page.waitForResponse(
      (r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${COST_REVAL_WINDOW_ID}/`),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    saved.catch(() => {}); // awaited below; avoids an unhandled rejection if a step in between throws
    await input.click();
    await input.press('ControlOrMeta+a');
    await input.pressSequentially(text);
    await input.press('Tab');
    await saved;
    // Tab moves the focus into the next date field, which opens its calendar over the line tab; close it.
    // The calendar opens asynchronously after the focus move, so wait for it (bounded) before closing it.
    // The focused field was only just entered and holds no edit, so Escape discards nothing.
    await page
      .locator('.rdtOpen')
      .waitFor({ state: 'attached', timeout: 2000 })
      .catch(() => {});
    await page.keyboard.press('Escape');
    await page.locator('.rdtOpen').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    await holdForCaptureIfEnabled(1500);
    await unhighlight();
  }

  /** In a capture run: point at both header dates so the viewer can compare them. */
  static async showHeaderDates() {
    const page = getPage();
    const u1 = await highlightForCaptureIfEnabled(page.locator('.form-field-DateAcct').first());
    const u2 = await highlightForCaptureIfEnabled(page.locator('.form-field-EvaluationStartDate').first());
    await holdForCaptureIfEnabled(2500);
    await u1();
    await u2();
  }

  /**
   * The line tab adds lines by quick-input only: it offers Batch entry, already opened, and no "Add new" button
   * (whose modal would ask for a Product that is read-only on the line).
   */
  static async expectQuickInputOpened() {
    await test.step('Line tab offers only the per-product quick-input (batch entry opens by itself, no "Add new")', async () => {
      const page = getPage();
      const toggle = page.getByTestId('batch-entry-toggle');
      await toggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const container = page.locator('.quick-input-container');
      await container.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // "Add new" is hidden while the batch entry is open in every tab, so check it with the batch entry closed:
      // the tab's buttons are rendered (the batch entry toggle), but "Add new" is not.
      await toggle.click();
      await container.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      await expect(toggle).toBeVisible();
      await expect(
        page.getByTestId('add-new-record'),
        'The line tab must not offer "Add new" (lines are added by quick-input only)'
      ).toHaveCount(0);
      await toggle.click();
      await container.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      if (isUatCapture()) {
        // Keep the entry row clear of the caption band at the bottom of the recording.
        await container.evaluate((el) => el.scrollIntoView({ block: 'center' }));
      }
    });
  }

  /** @returns {import('@playwright/test').Locator} a field of the open quick-input, by ColumnName */
  static quickInputField(columnName) {
    return getPage().locator(`.quick-input-container .form-field-${columnName}`);
  }

  /**
   * Search the quick-input product picker for a code.
   * @returns {import('@playwright/test').Locator} the result options (the "no results" / loading header is not an option)
   */
  static async searchProduct(code) {
    const page = getPage();
    const productInput = page.locator('#lookup_M_Product_ID input.input-field');
    await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await productInput.click();
    await productInput.fill('');
    await expect(productInput).toHaveValue('');
    const searched = page.waitForResponse(
      (r) => r.url().includes(`/M_Product_ID/typeahead?query=${encodeURIComponent(code)}`),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    searched.catch(() => {}); // awaited below; avoids an unhandled rejection if fill throws
    await productInput.fill(code);
    expect((await searched).ok()).toBe(true);
    await page
      .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    return page.locator('.input-dropdown-list [data-testid^="option-"]');
  }

  /** Search the picker for a code and expect exactly one result option for it. */
  static async expectProductOffered(code) {
    const options = await this.searchProduct(code);
    await expect(options.filter({ hasText: code })).toHaveCount(1);
    await this.holdPickerResultForCapture();
  }

  /**
   * Search the picker for a code and expect NO result at all: the previous search's options are gone and the
   * finished (not loading) "no results" row is shown.
   */
  static async expectProductNotOffered(code) {
    const page = getPage();
    const options = await this.searchProduct(code);
    await expect(options.filter({ hasText: code })).toHaveCount(0);
    await expect(options).toHaveCount(0);
    await expect(
      page.locator('.input-dropdown-list .input-dropdown-list-header').filter({ hasNot: page.locator('.icon-rotate') })
    ).toBeVisible();
    await this.holdPickerResultForCapture();
  }

  /** In a capture run: keep the settled picker result list on screen. */
  static async holdPickerResultForCapture() {
    const unhighlight = await highlightForCaptureIfEnabled(getPage().locator('#lookup_M_Product_ID'));
    await holdForCaptureIfEnabled(2500);
    await unhighlight();
  }

  /** Pick the dropdown option of the given product code (after searchProduct). */
  static async pickProduct(code) {
    const page = getPage();
    const options = await this.searchProduct(code);
    const option = options.filter({ hasText: code }).first();
    await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const picked = page.waitForResponse(
      (r) => r.request().method() === 'PATCH' && r.url().includes('/quickInput/'),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    picked.catch(() => {}); // awaited below; avoids an unhandled rejection if the click throws
    await option.click();
    expect((await picked).ok()).toBe(true);
  }

  /**
   * Hover the New cost price label and return its hint (the label's `title`, i.e. the field help).
   * The browser paints a `title` tooltip as a native OS widget that a page recording never contains, so a
   * recording shows only the hovered (outlined) label; the hint text itself is reported from this value.
   */
  static async hoverNewCostPriceHint() {
    const page = getPage();
    const label = page.locator('.quick-input-container .form-field-NewCostPrice label');
    await label.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await label.hover();
    const title = await label.getAttribute('title');

    const unhighlight = await highlightForCaptureIfEnabled(page.locator('.quick-input-container .form-field-NewCostPrice'));
    await holdForCaptureIfEnabled(4000);
    await unhighlight();
    return title;
  }

  /**
   * Choose the header's Cost Element (Kostenart) by its M_CostElement_ID and wait for the save.
   * Only allowed while the document has no lines.
   */
  static async selectCostElement(costElementId) {
    await test.step(`Select Kostenart (cost element) ${costElementId}`, async () => {
      const page = getPage();
      const field = page.locator('.form-field-M_CostElement_ID').first();
      await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const unhighlight = await highlightForCaptureIfEnabled(field);
      await field.locator('.input-dropdown').first().click();
      const option = page.locator('.input-dropdown-list').getByTestId(`option-${costElementId}`);
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const saved = page.waitForResponse(
        (r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${COST_REVAL_WINDOW_ID}/`),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      saved.catch(() => {});
      await option.click();
      await saved;
      await holdForCaptureIfEnabled(2000);
      await unhighlight();
    });
  }

  /**
   * Wait until the completed document is posted and return its accounting facts (Fact_Acct rows' fieldsByName).
   * Read through the document's "Accounting facts" reference, so it does not depend on the window showing a Posted field.
   * @param {string} recordId the header record id
   */
  static async waitUntilPosted(recordId) {
    let facts = [];
    await expect
      .poll(
        async () => {
          facts = await getAccountingFacts(COST_REVAL_WINDOW_ID, recordId);
          return facts.length;
        },
        { message: `Cost revaluation ${recordId} is posted (has accounting facts)`, timeout: 30000 }
      )
      .toBeGreaterThan(0);
    return facts;
  }

  /** Type the New cost price into the open quick-input (does not submit). */
  static async enterNewCostPrice(newCostPrice) {
    const page = getPage();
    const priceInput = page.locator('.form-field-NewCostPrice input');
    await priceInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await priceInput.click();
    await priceInput.fill(String(newCostPrice));

    // Product + entered price, both visible, before the line is submitted.
    const unhighlight = await highlightForCaptureIfEnabled(page.locator('.quick-input-container'));
    await holdForCaptureIfEnabled(2500);
    await unhighlight();
  }

  /**
   * Type the New cost price into the open quick-input, submit the line (Enter) and wait until the server created it
   * (the quick-input's complete request). Fails if the submit never reaches the server.
   */
  static async submitLine(newCostPrice) {
    const page = getPage();
    await this.enterNewCostPrice(newCostPrice);
    const created = page.waitForResponse(
      (r) => r.request().method() === 'POST' && /\/quickInput\/[^/]+\/complete/.test(r.url()),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    created.catch(() => {}); // awaited below; avoids an unhandled rejection if the key press throws
    await page.keyboard.press('Enter'); // "(Press 'Enter' to add)"
    const response = await created;
    expect(response.ok()).toBe(true);
  }

  /** Add one quick-input line: pick the product, type the New cost price, submit (Enter). */
  static async addLine(productCode, newCostPrice) {
    await test.step(`Pick product ${productCode} + enter New cost price ${newCostPrice}`, async () => {
      await this.pickProduct(productCode);
      await this.submitLine(newCostPrice);
    });
  }

  /** @returns {Promise<Object[]>} the document's lines (rows of the line tab, read via the WebAPI) */
  static async getLines(recordId) {
    const response = await getPage().request.get(
      `${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/${COST_REVAL_LINE_TAB_ID}`
    );
    expect(response.ok()).toBe(true);
    return (await response.json()).result || [];
  }

  /**
   * Complete the document via the status button -> Complete (CO).
   * The Complete DocAction runs the revaluation (creates the cost details) AND posts in one step.
   */
  static async complete() {
    await test.step('Complete document (runs revaluation + posts)', async () => {
      const page = getPage();
      // Close the quick-input if still open so the status button is reachable.
      const quickInput = page.locator('.quick-input-container');
      if (await quickInput.isVisible().catch(() => false)) {
        await page.getByTestId('batch-entry-toggle').click();
        await quickInput.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      }
      const statusButton = page.getByTestId('status-button');
      await statusButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await statusButton.click();
      const completeOption = page.getByTestId('status-CO');
      await completeOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const completed = page.waitForResponse(
        (r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${COST_REVAL_WINDOW_ID}/`),
        { timeout: VERY_SLOW_ACTION_TIMEOUT }
      );
      completed.catch(() => {}); // awaited below; avoids an unhandled rejection if the click throws
      await completeOption.click();
      expect((await completed).ok()).toBe(true);
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT })
        .catch(() => {});
    });
  }

  /**
   * Pick the product in the open quick-input, type the New cost price, submit (Enter) and expect the server to refuse
   * the line: the quick-input's complete request fails and the error notification shows the given (translated) message.
   * @param {string} expectedMessage the expected error message text (or a fragment of it)
   */
  static async addLineExpectingRefusal(productCode, newCostPrice, expectedMessage) {
    await test.step(`Pick product ${productCode} + enter New cost price ${newCostPrice} -> the line is refused`, async () => {
      const page = getPage();
      await this.pickProduct(productCode);
      await this.enterNewCostPrice(newCostPrice);
      const created = page.waitForResponse(
        (r) => r.request().method() === 'POST' && /\/quickInput\/[^/]+\/complete/.test(r.url()),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      created.catch(() => {}); // awaited below; avoids an unhandled rejection if the key press throws
      await page.keyboard.press('Enter');
      expect((await created).ok(), 'the quick-input line is refused').toBe(false);
      await this.expectErrorNotification(expectedMessage);
    });
  }

  /**
   * Expect the error notification to show the given (translated) message, not framed as a technical failure
   * (no "Server error" prefix).
   * @param {string} expectedMessage the expected error message text (or a fragment of it)
   */
  static async expectErrorNotification(expectedMessage) {
    const page = getPage();
    // The error notification shows a shortened message; hovering keeps it open, "(read more)" shows the full text.
    const error = page.locator('.notification-handler .notification-item.error').last();
    await error.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await error.hover();
    const readMore = error.locator('.notification-content u.pointer');
    if (await readMore.isVisible().catch(() => false)) {
      await readMore.click();
    }
    await expect(error.locator('.notification-content')).toContainText(expectedMessage);
    // A business refusal is a validation message, not framed as a technical failure
    await expect(error.locator('.notification-content')).not.toContainText('Server error');
    const unhighlight = await highlightForCaptureIfEnabled(error);
    await holdForCaptureIfEnabled(4000);
    await unhighlight();
  }

  /** @returns {import('@playwright/test').Locator} the given column's cell of the line grid row with the given row id */
  static lineCell(rowId, columnName) {
    return getPage().getByTestId(`table-row-${rowId}`).locator(`td[data-cy="cell-${columnName}"]`);
  }

  /**
   * Edit a line's New cost price in the line grid the way a user does (double-click the cell, type, Enter)
   * and wait for the line's save.
   */
  static async editLineNewCostPrice(rowId, newCostPrice) {
    await test.step(`Edit the line's New cost price to ${newCostPrice}`, async () => {
      const page = getPage();
      const quickInput = page.locator('.quick-input-container');
      if (await quickInput.isVisible().catch(() => false)) {
        await page.getByTestId('batch-entry-toggle').click();
        await quickInput.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      }
      const cell = this.lineCell(rowId, 'NewCostPrice');
      await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await cell.scrollIntoViewIfNeeded();
      const unhighlight = await highlightForCaptureIfEnabled(cell);
      await cell.dblclick();
      const input = cell.locator('input').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const saved = page.waitForResponse(
        (r) => r.request().method() === 'PATCH' && r.url().includes(`/${COST_REVAL_LINE_TAB_ID}/${rowId}`),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      saved.catch(() => {}); // awaited below; avoids an unhandled rejection if the typing throws
      await input.press('ControlOrMeta+a');
      await input.pressSequentially(String(newCostPrice));
      await input.press('Enter');
      expect((await saved).ok()).toBe(true);
      await holdForCaptureIfEnabled(2000);
      await unhighlight();
    });
  }

  /**
   * The hint (title) of a line grid column header, i.e. the field's description.
   * @returns {Promise<{caption: string, hint: string}>}
   */
  static async lineColumnHeader(columnName) {
    const header = getPage().getByTestId(`column-${columnName}`).first();
    await header.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const captionElement = header.locator('span[title]').first();
    const caption = (await captionElement.innerText()).trim();
    const hint = await captionElement.getAttribute('title');
    const unhighlight = await highlightForCaptureIfEnabled(header);
    await captionElement.hover();
    await holdForCaptureIfEnabled(1500);
    await unhighlight();
    return { caption, hint };
  }

  /** Open the single-row view (Advanced edit, Alt+E) of the line with the given row id. */
  static async openLine(rowId) {
    await test.step('Open the line in its single-row view (Advanced edit)', async () => {
      const page = getPage();
      const quickInput = page.locator('.quick-input-container');
      if (await quickInput.isVisible().catch(() => false)) {
        await page.getByTestId('batch-entry-toggle').click();
        await quickInput.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      }
      // The row's context menu offers "Advanced edit"; its item is identified by its (language-invariant) shortcut.
      await this.lineCell(rowId, 'M_Product_ID').click({ button: 'right' });
      const advancedEdit = page
        .locator('.context-menu-item')
        .filter({ has: page.locator('.tooltip-inline', { hasText: /^alt\+e$/i }) });
      await advancedEdit.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await advancedEdit.click();
      await page
        .locator(`.panel-modal-content .form-field-DeltaAmt`)
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * In the open single-row view: the hint (label title, i.e. the field's description) of the given field.
   * @returns {Promise<{caption: string, hint: string}>}
   */
  static async openLineFieldLabel(columnName) {
    const field = getPage().locator(`.panel-modal-content .form-field-${columnName}`).first();
    await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const label = field.locator('label').first();
    const caption = (await label.innerText()).trim();
    const hint = await label.getAttribute('title');
    const unhighlight = await highlightForCaptureIfEnabled(field);
    await label.hover();
    await holdForCaptureIfEnabled(1500);
    await unhighlight();
    return { caption, hint };
  }

  /** Close the open single-row view. */
  static async closeLine() {
    const page = getPage();
    await page.keyboard.press('Escape');
    await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
  }

  /** @returns {import('@playwright/test').Locator} the header Revaluation Source (Neubewertungsquelle) field */
  static revaluationSourceField() {
    return getPage().locator('.form-field-RevaluationSource').first();
  }

  /**
   * @returns {Promise<string>} the shown text of the header Revaluation Source. A list field shows "Value_Name"
   * (e.g. Manual_Manuell).
   */
  static async revaluationSourceText() {
    const field = this.revaluationSourceField();
    await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    return (await field.locator('input').first().inputValue()).trim();
  }

  /**
   * Choose the header Revaluation Source by its list value (Manual, CopyFromCostElement) and wait for the save.
   */
  static async selectRevaluationSource(valueKey) {
    await test.step(`Select Revaluation Source ${valueKey}`, async () => {
      const page = getPage();
      const field = this.revaluationSourceField();
      await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const unhighlight = await highlightForCaptureIfEnabled(field);
      const saved = page.waitForResponse(
        (r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${COST_REVAL_WINDOW_ID}/`),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      saved.catch(() => {}); // awaited below; avoids an unhandled rejection if a click throws
      await field.locator('.input-dropdown-container').click();
      const option = page.locator(`.input-dropdown-list-option[data-testid="option-${valueKey}"]`).first();
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await option.click();
      await saved;
      await page
        .locator('.input-dropdown-list')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await holdForCaptureIfEnabled(1500);
      await unhighlight();
    });
  }

  /**
   * The captions of the document's actions (processes) in the header action menu, by process internal name.
   * @param {string[]} internalNames e.g. ['M_CostRevaluation_Run']
   * @returns {Promise<Object<string, string>>}
   */
  static async actionCaptions(internalNames) {
    return await test.step('Open the action menu (Alt+1) and read the process names', async () => {
      const page = getPage();
      await page.locator('body').click({ position: { x: 5, y: 5 } });
      await page.keyboard.press('Alt+1');
      await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const captions = {};
      for (const internalName of internalNames) {
        const action = page.getByTestId(`action-${internalName}`).first();
        await action.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        captions[internalName] = (await action.innerText()).split('\n')[0].trim();
      }
      const unhighlight = await highlightForCaptureIfEnabled(page.locator('.subheader-container'));
      await holdForCaptureIfEnabled(2500);
      await unhighlight();
      await page.keyboard.press('Escape');
      await page.locator('.subheader-container').waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      return captions;
    });
  }

  /** In a capture run: keep the completed document (status + lines) on screen. */
  static async showCompletedDocument() {
    const page = getPage();
    if (!isUatCapture()) {
      return;
    }
    await page.evaluate(() => window.scrollTo(0, 0));
    // Reload so the header shows the document status the server has set after Complete.
    await page.reload();
    const statusButton = page.getByTestId('status-button');
    await statusButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForLoadState('networkidle');
    const unhighlightStatus = await highlightForCaptureIfEnabled(statusButton);
    const unhighlightLines = await highlightForCaptureIfEnabled(page.locator('.table-flex-wrapper-row, .table-flex-wrapper').first());
    await holdForCaptureIfEnabled(4000);
    await unhighlightStatus();
    await unhighlightLines();
  }
}
