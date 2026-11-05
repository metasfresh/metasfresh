import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, holdForCaptureIfEnabled, isUatCapture, SLOW_ACTION_TIMEOUT } from '../common';

export const SCANNABLE_CODE_FORMAT_WINDOW_ID = 541880;

/** In an evidence-capture run only: outline the element; returns the function that removes the outline again. */
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
 * In an evidence-capture run only (UAT_CAPTURE=1 and UAT_CAPTURE_DIR set): save a viewport still named <prefix>-<name>.png,
 * with the page URL in a banner at the bottom so the still is located. No-op otherwise.
 */
const stillIfEnabled = async (name) => {
  const dir = process.env.UAT_CAPTURE_DIR;
  if (!isUatCapture() || !dir) {
    return;
  }
  const page = getPage();
  await page.evaluate((url) => {
    const banner = document.createElement('div');
    banner.id = 'capture-url-banner';
    banner.textContent = url;
    banner.style.cssText =
      'position:fixed;left:0;right:0;bottom:0;z-index:2147483647;background:#222;color:#fff;font:14px monospace;padding:4px 8px';
    document.body.appendChild(banner);
  }, page.url());
  await page.screenshot({ path: `${dir}/${process.env.UAT_CAPTURE_PREFIX || ''}${name}.png` });
  await page.evaluate(() => document.getElementById('capture-url-banner')?.remove());
};

/**
 * Page object for the Scannable Code Format window (C_ScannableCode_Format, window 541880) and its part tab
 * (C_ScannableCode_Format_Part).
 */
export class ScannableCodeFormatPage {
  /** Name of the still taken by the next endNoInForm() call (new part form vs advanced edit). */
  static _formStill = '01-new-part-form-end-no';

  /** Log in as the local admin user in the given language, through the page's request context (shares the cookies). */
  static async loginAs(language) {
    return await test.step(`Log in (${language})`, async () => {
      this._formStill = '01-new-part-form-end-no';
      const page = getPage();
      // the WebUI takes its language (Accept-Language of every request) from local storage, as set by the login page's language selector
      await page.addInitScript((lang) => window.localStorage.setItem('metasfreshLanguage', lang), language);
      const api = `${FRONTEND_BASE_URL}/rest/api`;
      const headers = { 'Content-Type': 'application/json', 'Accept-Language': language };
      const auth = await page.request.post(`${api}/login/authenticate`, { headers, data: { username: 'metasfresh', password: 'metasfresh' } });
      if (!auth.ok()) {
        throw new Error(`login failed: HTTP ${auth.status()}`);
      }
      const role = (await auth.json()).roles[0];
      const done = await page.request.post(`${api}/login/loginComplete`, { headers, data: role });
      if (!done.ok()) {
        throw new Error(`role selection failed: HTTP ${done.status()}`);
      }
      await this.setUserLanguage(language);
    });
  }

  /** Set the language of the logged-in user (AD_User.AD_Language), as the WebUI's language switch does. */
  static async setUserLanguage(language) {
    const res = await getPage().request.put(`${FRONTEND_BASE_URL}/rest/api/userSession/language`, {
      headers: { 'Content-Type': 'application/json', 'Accept-Language': language },
      data: { key: language, caption: language },
    });
    if (!res.ok()) {
      throw new Error(`setting the user language ${language} failed: HTTP ${res.status()}`);
    }
  }

  /** Create a new format with the given name and wait until it is saved. */
  static async createFormat(name) {
    return await test.step(`Create the scannable code format "${name}"`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${SCANNABLE_CODE_FORMAT_WINDOW_ID}/NEW`, { waitUntil: 'domcontentloaded' });
      const nameInput = page.locator('.form-field-Name input').first();
      await nameInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const saved = page.waitForResponse((r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${SCANNABLE_CODE_FORMAT_WINDOW_ID}/`), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await nameInput.fill(name);
      await page.keyboard.press('Tab');
      await saved;
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** Click "Add new" of the part tab; waits for the new-part form (modal). */
  static async openNewPartForm() {
    return await test.step('Open the new part form', async () => {
      const page = getPage();
      await page.locator('.panel-modal-content').first().waitFor({ state: 'detached', timeout: 1000 }).catch(() => {});
      await page.locator('.tabs-wrapper button, .tab-pane button, button').filter({ hasText: /^(Add new|Neu hinzufügen|Neu)$/ }).first().click();
      await page.locator('.panel-modal-content .form-field-EndNo').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** @returns {import('@playwright/test').Locator} the End No field of the open part form (new part or advanced edit) */
  static endNoField() {
    return getPage().locator('.panel-modal-content .form-field-EndNo').first();
  }

  /**
   * In the open part form: the caption and hint (label title = the field's description) of End No, and its current value.
   */
  static async endNoInForm() {
    const field = this.endNoField();
    const label = field.locator('label').first();
    const caption = (await label.innerText()).trim();
    const hint = await label.getAttribute('title');
    const value = await field.locator('input').first().inputValue();
    const unhighlight = await highlightForCaptureIfEnabled(field);
    await label.hover();
    await holdForCaptureIfEnabled(2500);
    await stillIfEnabled(this._formStill);
    await unhighlight();
    return { caption, hint, value };
  }

  /** Fill Start No and Data Type (by AD_Ref_List value) of the open new part form; End No is left empty. */
  static async fillStartNoAndDataType(startNo, dataTypeValue) {
    await test.step(`Fill Start No ${startNo} and Data Type ${dataTypeValue}; End No stays empty`, async () => {
      const page = getPage();
      const modal = page.locator('.panel-modal-content').first();
      const start = modal.locator('.form-field-StartNo input').first();
      await start.click();
      await page.keyboard.press('Control+a');
      const startSaved = page.waitForResponse((r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${SCANNABLE_CODE_FORMAT_WINDOW_ID}/`), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await start.fill(String(startNo));
      await page.keyboard.press('Tab');
      await startSaved;
      await modal.locator('.form-field-DataType .input-dropdown, .form-field-DataType input').first().click();
      await page.locator('.input-dropdown-list').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const saved = page.waitForResponse((r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${SCANNABLE_CODE_FORMAT_WINDOW_ID}/`), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await page.locator(`.input-dropdown-list [data-testid="option-${dataTypeValue}"]`).first().click();
      await page.locator('.input-dropdown-list').waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      await saved;
    });
  }

  /** Close the open part form with "Done" and wait until it is gone. */
  static async done() {
    await test.step('Done', async () => {
      const page = getPage();
      await holdForCaptureIfEnabled(1500);
      await page.keyboard.press('Alt+Enter'); // "Done" (the shortcut is language-invariant)
      await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** @returns {Promise<{caption: string, hint: string}>} the End No column header of the part grid */
  static async endNoGridHeader() {
    const header = getPage().getByTestId('column-EndNo').first();
    await header.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const titled = header.locator('span[title]').first();
    const caption = (await titled.innerText()).trim();
    const hint = await titled.getAttribute('title');
    const unhighlight = await highlightForCaptureIfEnabled(header);
    await titled.hover();
    await holdForCaptureIfEnabled(2500);
    await stillIfEnabled('03-grid-end-no-column');
    await unhighlight();
    return { caption, hint };
  }

  /** @returns {Promise<string[]>} the cell texts of the first part row (Start No, End No, Data Type, ...) */
  static async firstPartRowCells() {
    const row = getPage().locator('.table-flex-wrapper tbody tr').first();
    await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await stillIfEnabled('02-grid-part-saved-end-no-empty');
    return (await row.locator('td').allInnerTexts()).map((t) => t.trim());
  }

  /** Open the first part row in its single-row view (Advanced edit, Alt+E). */
  static async openFirstPartInAdvancedEdit() {
    await test.step('Open the part in Advanced edit (Alt+E)', async () => {
      const page = getPage();
      await page.locator('.table-flex-wrapper tbody tr').first().click();
      await page.keyboard.press('Alt+e');
      await this.endNoField().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      this._formStill = '04-advanced-edit-end-no';
    });
  }

  static async closeForm() {
    const page = getPage();
    await page.keyboard.press('Escape');
    await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
  }
}
