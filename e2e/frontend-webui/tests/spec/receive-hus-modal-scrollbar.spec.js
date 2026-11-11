import { expect } from '@playwright/test';
import config, { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { createPurchaseReceiptMasterdata, openReceiptCandidateOfNewPurchaseOrder } from '../utils/PurchaseReceiptSetup';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';
import { getPage, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * "Receive HUs" modal: a mouse press on the modal's scrollbar must keep the selected HU row.
 *
 * Customer case: in the receive-HUs modal the user selects an HU row; the "Merkmale" (attributes) panel below the
 * table makes the modal content taller than the window, so the content scrolls. Grabbing the scrollbar with the mouse
 * used to deselect the row (the panel vanished and the "Create receipt" action turned off). Scrolling with the wheel
 * never did.
 *
 * Cause: the press on the scrollbar of `.document-list-wrapper` is a mousedown whose target is that wrapper, which
 * the table's click-outside handling treated like a click on blank list area.
 *
 * Test mechanics only (logic unchanged): headless Chromium hides scrollbars by default, so `--hide-scrollbars` is
 * removed; a small viewport forces the content to overflow.
 *
 * Note: the core `e2e/frontend-webui` suite does not run in a customer build; the customer-build evidence is the
 * live UAT on the customer instance.
 */

// test.use replaces the project's launchOptions, so the project's arguments are taken from the config
const projectArgs = config.projects.find((p) => p.name === 'Desktop Chrome').use.launchOptions.args;

test.use({
  viewport: { width: 1400, height: 520 },
  launchOptions: {
    ignoreDefaultArgs: ['--hide-scrollbars'],
    args: projectArgs,
  },
});

const MODAL_ROWS = '.panel-modal .table tbody tr';
const MODAL_SELECTED_ROWS = '.panel-modal .table tbody tr.row-selected';
const MODAL_ATTRIBUTES_PANEL = '.panel-modal .attributes-selector';
const MODAL_QUICK_ACTION = '.panel-modal [data-testid="quick-action-button"]';

const BACKGROUND_SELECTED_ROWS = '.table tbody tr.row-selected';

/** Creates a purchase order and opens its receipt candidate (the non-modal receipt-candidates list). */
async function openReceiptCandidate() {
  await openReceiptCandidateOfNewPurchaseOrder(
    await createPurchaseReceiptMasterdata({ productName: 'Test Product for Receive HUs modal scrollbar' }),
  );
}

/** Opens the receive-HUs modal for a fresh purchase order's receipt candidate and selects one HU row. */
async function openReceiveModalAndSelectRow(page) {
  await openReceiptCandidate();

  await ReceiptCandidatesPage.executeDefaultQuickAction();
  await ReceiptCandidatesPage.waitForPlanningHURows();

  // select the last row: the row whose "Merkmale" panel makes the modal content overflow
  const rows = page.locator(MODAL_ROWS);
  await rows.last().click();
  await expectRowSelectedWithPanel(page);
}

/** Number of selected rows of the tables that are NOT inside the modal (the receipt-candidates list behind it). */
function countBackgroundSelectedRows(page) {
  return page.locator(BACKGROUND_SELECTED_ROWS).evaluateAll((rows) => rows.filter((r) => !r.closest('.panel-modal')).length);
}

/** First point inside `wrapperSelector`'s client box that hits the wrapper itself or a direct child outside `js-not-unselect`. */
function findBlankPoint(page, wrapperSelector, shouldExcludeModal) {
  return page.evaluate(
    ([sel, shouldExcludeModal]) => {
      const wrappers = [...document.querySelectorAll(sel)].filter((w) => !shouldExcludeModal || !w.closest('.panel-modal'));
      for (const el of wrappers) {
        const r = el.getBoundingClientRect();
        for (let y = r.top + el.clientTop + 2; y < r.top + el.clientTop + el.clientHeight; y += 4) {
          for (let x = r.left + el.clientLeft + 2; x < r.left + el.clientLeft + el.clientWidth; x += 4) {
            const hit = document.elementFromPoint(x, y);
            if (hit && (hit === el || hit.parentNode === el) && !hit.closest('.js-not-unselect')) {
              return { x, y };
            }
          }
        }
      }
      return null;
    },
    [wrapperSelector, shouldExcludeModal]
  );
}

async function expectRowSelectedWithPanel(page, when = 'before the press') {
  await expect(page.locator(MODAL_SELECTED_ROWS), `one HU row selected ${when}`).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
  await expect(page.locator(MODAL_ATTRIBUTES_PANEL).first(), 'Merkmale panel shown').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  await expect(page.locator(MODAL_QUICK_ACTION).first(), 'modal action enabled').not.toHaveClass(/disabled/);
}

function describeCase(story) {
  allure.epic('E0150: Material Receipt');
  allure.tag('F65010: Material Receipt Candidates');
  allure.tag('F65010');
  allure.story(story);
  allure.severity('normal');
}

test.describe('Receive HUs modal - scrollbar press keeps the selection', () => {
  test.setTimeout(180000);

  test('TC1a: wheel scroll keeps the selected HU row', async ({ page }) => {
    describeCase('Wheel scroll keeps the selection');
    await openReceiveModalAndSelectRow(page);

    const wrapper = page.locator('.panel-modal .document-list-wrapper').first();
    const box = await wrapper.boundingBox();
    await page.mouse.move(box.x + box.width / 2, box.y + box.height / 2);
    // the content only overflows once the Merkmale panel has rendered; a wheel before that is a no-op, so wait for
    // the overflow and retry the wheel as a unit
    await expect
      .poll(() => wrapper.evaluate((el) => el.scrollHeight > el.clientHeight), { timeout: SLOW_ACTION_TIMEOUT })
      .toBe(true);
    await expect(async () => {
      await page.mouse.wheel(0, 120);
      expect(await wrapper.evaluate((el) => el.scrollTop)).toBeGreaterThan(0);
    }).toPass({ timeout: SLOW_ACTION_TIMEOUT });

    await expectRowSelectedWithPanel(page, 'after the wheel scroll');
  });

  test('TC1b: pressing the vertical scrollbar keeps the selected HU row', async ({ page }) => {
    describeCase('Scrollbar press keeps the selection');
    await openReceiveModalAndSelectRow(page);

    const result = await ReceiptCandidatesPage.pressVerticalScrollbarOfModal();

    await expectRowSelectedWithPanel(page, 'after the scrollbar press');
    expect(result.scrollTopAfter, 'the press on the scrollbar track scrolled the content').toBeGreaterThan(result.scrollTopBefore);
  });

  test('TC1c: dragging the scrollbar thumb keeps the selected HU row', async ({ page }) => {
    describeCase('Scrollbar thumb drag keeps the selection');
    await openReceiveModalAndSelectRow(page);

    const result = await ReceiptCandidatesPage.pressVerticalScrollbarOfModal({ isDrag: true });

    await expectRowSelectedWithPanel(page, 'after the scrollbar press');
    expect(result.scrollTopAfter, 'dragging the scrollbar thumb scrolled the content').toBeGreaterThan(result.scrollTopBefore);
  });

  test('TC1d: a click on blank list area inside the modal still deselects (unchanged behaviour)', async ({ page }) => {
    describeCase('Blank area click still deselects');
    await openReceiveModalAndSelectRow(page);

    // a point on the list area outside the table and the Merkmale panel
    const point = await findBlankPoint(page, '.panel-modal .document-list-wrapper', false);
    expect(point, 'no blank list-area point found inside the modal list wrapper at this viewport').not.toBeNull();

    await page.mouse.click(point.x, point.y);

    await expect(page.locator(MODAL_SELECTED_ROWS)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
  });

  test('TC1e: a press on the modal scrollbar keeps the receipt-candidate row selected in the background window', async ({ page }) => {
    describeCase('Scrollbar press keeps the background selection');
    await openReceiveModalAndSelectRow(page);
    // the modal carries a parentView (the receipt-candidates view), so a wrongly handled press would also clear it
    const backgroundBefore = await countBackgroundSelectedRows(page);
    expect(backgroundBefore, 'the receipt candidate is selected in the background before the press').toBeGreaterThan(0);

    await ReceiptCandidatesPage.pressVerticalScrollbarOfModal();

    await expectRowSelectedWithPanel(page, 'after the scrollbar press');
    expect(await countBackgroundSelectedRows(page), 'background receipt-candidate row still selected').toBe(backgroundBefore);
  });

  test('TC1f: a blank-area click on a NON-modal grid still deselects (unchanged behaviour); records scrollbar behaviour there', async ({ page }) => {
    describeCase('Non-modal grid blank click unchanged');
    await openReceiptCandidate();

    if ((await page.locator(BACKGROUND_SELECTED_ROWS).count()) === 0) {
      await page.locator('.table tbody tr').first().click();
    }
    await expect(page.locator(BACKGROUND_SELECTED_ROWS)).not.toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });

    // informational (no assertion): does the non-modal list overflow, and which element scrolls?
    const info = await page.evaluate(() => {
      const w = document.querySelector('.document-list-wrapper');
      return {
        wrapperOverflowY: w ? w.scrollHeight > w.clientHeight : null,
        wrapperScrollbarWidth: w ? w.offsetWidth - w.clientWidth : null,
        documentOverflowY: document.documentElement.scrollHeight > document.documentElement.clientHeight,
      };
    });
    console.log('TC1f non-modal observation: ' + JSON.stringify(info));

    const point = await findBlankPoint(page, '.document-list-wrapper', true);
    expect(point, 'no blank list-area point found on the non-modal grid at this viewport').not.toBeNull();
    await page.mouse.click(point.x, point.y);

    await expect(page.locator(BACKGROUND_SELECTED_ROWS)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
  });
});
