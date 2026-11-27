import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { GetQuantityDialog } from '../utils/screens/picking/GetQuantityDialog';

// the rule of get-qty-dialog.scss .table-container: `max-height: 70vh` (release line) vs the forced `height: 70vh` (the "huge gap" defect)
const pageWith = (heightRule) => `
<style>
    body { margin: 0; }
    .get-qty-dialog { display: flex; flex-direction: column; }
    .table-container { ${heightRule}; overflow-y: auto; }
</style>
<div class="get-qty-dialog"><div class="table-container"><table><tr><th>Qty</th><td>5</td></tr></table></div></div>`;

test('a table area that shrinks to its short content passes the fit check', async ({ page }) => {
    await page.setContent(pageWith('max-height: 70vh'));
    await GetQuantityDialog.expectTableAreaFitsContent(await page.evaluate(() => window.innerHeight));
});

test('a table area with a forced height of 70vh fails the fit check', async ({ page }) => {
    await page.setContent(pageWith('height: 70vh'));
    await expect(GetQuantityDialog.expectTableAreaFitsContent(await page.evaluate(() => window.innerHeight))).rejects.toThrow('shrinks to its content');
});
