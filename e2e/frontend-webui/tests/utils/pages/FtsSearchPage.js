import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT } from '../common';

const FTS_FILTER_ID = 'postgres-full-text-search';

/**
 * Freezes the painted screen for ~1.5 s, ONLY in a capture run (UAT_CAPTURE=1), so the recorder shows the result
 * a step just proved. Unset (normal and CI runs) it is a no-op: no slowdown.
 */
const holdForCaptureIfEnabled = async () => {
  if (process.env.UAT_CAPTURE && process.env.UAT_CAPTURE !== '0') {
    await getPage().waitForTimeout(1500);
  }
};

/** The "Search records" filter of a window list (the Postgres full-text-search filter once the ES drop is applied). */
export class FtsSearchPage {
  /** Types `term` into the "Search records" filter and checks the filter request the UI sent is the Postgres FTS filter. */
  static async searchRecords(term) {
    return await test.step(`Search records for "${term}"`, async () => {
      const page = getPage();
      const input = page.locator('.inline-filters input.input-field');
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const filterRequest = page.waitForRequest(
        (request) =>
          request.method() === 'POST' &&
          (request.postData() || '').includes(FTS_FILTER_ID) &&
          (request.postData() || '').includes(term),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await input.fill(term);
      await input.press('Enter');
      const request = await filterRequest;
      expect(JSON.parse(request.postData()).filters.map((f) => f.filterId)).toContain(FTS_FILTER_ID);
    });
  }

  static async expectSearchFilterVisible() {
    await expect(getPage().locator('.inline-filters input.input-field')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  }

  static async expectGridRow({ value, name }) {
    return await test.step(`Grid shows ${value} - ${name}`, async () => {
      const row = getPage().locator('table.js-table tbody tr', { hasText: value });
      await expect(row).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(row).toContainText(name);
      await holdForCaptureIfEnabled();
    });
  }
}

/** The KPI tiles ("indicator cards") of the dashboard. */
export class KpiTilesPage {
  /** @param {RegExp} amountPattern the amount the UI must render in the tile (it drops a trailing zero: 350.50 -> 350.5) */
  static async expectTile({ caption, amountText, amountPattern }) {
    return await test.step(`Tile "${caption}" shows ${amountText} EUR`, async () => {
      const tile = getPage().locator('.indicator-card', { hasText: caption });
      await expect(tile).toContainText(amountPattern, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(tile).toContainText('EUR');
      await holdForCaptureIfEnabled();
    });
  }
}
