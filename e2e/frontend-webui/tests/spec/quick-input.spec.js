/**
 * Quick Input (Batch Entry) — Tab focus cycle — me03#29125
 *
 * Verifies that Tab on the last field wraps back to the first, and
 * Shift+Tab on the first wraps to the last, so focus never escapes the
 * quick-input-container to the page or browser chrome.
 *
 * The same focus-trap pattern was applied to the Advanced Edit modal in
 * me03#27080 (Modal.js handleTabKeyTrap). This spec mirrors the Tab-cycle
 * tests in advanced-edit-focus.spec.js but targets the inline Batch Entry
 * form instead of a modal.
 *
 * Window 143 — Sales Order — is used because it has Batch Entry support
 * on the Order Lines tab and is present in the standard E2E seed DB.
 */
import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';

const SALES_ORDER_WINDOW_ID = 143;

async function loginAsMetasfresh(page) {
  await page.goto('/logout', { waitUntil: 'load' });
  await page.goto('/', { waitUntil: 'load' });

  await page
    .locator('.login-container')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await page.locator('input[name="username"]').fill('metasfresh');
  await page.locator('input[name="password"]').fill('metasfresh');
  await page.locator('.login-container .btn-meta-success').click();

  // Click through role-selection screen if it appears.
  try {
    const roleSubmitBtn = page.locator(
      '.login-container .btn-meta-success:visible'
    );
    await roleSubmitBtn.waitFor({
      state: 'visible',
      timeout: FAST_ACTION_TIMEOUT,
    });
    await roleSubmitBtn.click();
  } catch {
    // No role selection needed.
  }

  await page
    .locator('.dashboard')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Create a new Drafted Sales Order and fill the mandatory Business Partner
 * so the document becomes valid and `tabInfo.allowCreateNew=true`, which is
 * required for the Batch Entry button to appear.
 *
 * We always create a fresh order (ALT+N) rather than reusing an existing one
 * because the Sales Order list view is backed by Elasticsearch — newly created
 * records may not be indexed yet, making the list appear empty even when a DB
 * record was created moments ago. Creating a new order each time gives
 * consistent state.
 */
async function openSalesOrder(page) {
  await page.goto(`/window/${SALES_ORDER_WINDOW_ID}`, { waitUntil: 'load' });
  await page
    .locator('.document-list-wrapper')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  // Always create a fresh Drafted order.
  await page.keyboard.press('Alt+n');
  await page.waitForURL(/\/window\/\d+\/\d+/, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page
    .locator('.window-wrapper')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(800);

  // Fill mandatory Business Partner so the document becomes valid
  // and `tabInfo.allowCreateNew=true` (required for Batch Entry button).
  const bpInput = page.locator('#lookup_C_BPartner_ID input').first();
  await bpInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await bpInput.click();
  await bpInput.fill('Test Kunde 1');
  // Short pause for the debounced API search to fire before polling for options.
  await page.waitForTimeout(500);
  // Wait for actual BP options (not the "Search Assistant" meta-entry whose
  // data-test-id starts with "SEARCH"). Click the first real match.
  await page
    .locator('.input-dropdown-list .input-dropdown-list-option:not([data-test-id^="SEARCH"])')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page
    .locator('.input-dropdown-list .input-dropdown-list-option:not([data-test-id^="SEARCH"])')
    .first()
    .click();
  // Wait for the document to become valid after BP selection — indicated by
  // the Batch Entry button appearing (requires allowCreateNew=true, which the
  // backend sets once mandatory fields are filled).
  await page
    .locator('.close-batch-entry')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

async function openBatchEntry(page) {
  const batchBtn = page.locator('.close-batch-entry').first();
  await batchBtn.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await batchBtn.click();
  await page
    .locator('.quick-input-container input')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(400);
}

// =========================================================================
// https://github.com/metasfresh/me03/issues/29125
// Quick Input (Batch Entry) panel: Tab focus cycle
//
// Bug: Tab on the last field in .quick-input-container escapes to the
// browser URL bar / <body> instead of wrapping to the first field.
// Shift+Tab on the first field also escapes the form.
//
// Expected: A document-level keydown capture listener (like Modal.js
// handleTabKeyTrap) keeps focus inside .quick-input-container.
// =========================================================================

test.describe('Quick Input Batch Entry — Tab focus cycle (https://github.com/metasfresh/me03/issues/29125)', () => {
  test.beforeEach(async ({ page }) => {
    await loginAsMetasfresh(page);
    await openSalesOrder(page);
    await openBatchEntry(page);
  });

  test('Tab 20 times never escapes the quick-input form', async ({ page }) => {
    await page.locator('.quick-input-container input').first().focus();

    const escapes = [];
    for (let i = 0; i < 20; i++) {
      await page.keyboard.press('Tab');
      await page.waitForTimeout(60);
      const info = await page.evaluate(() => {
        const form = document.querySelector('.quick-input-container');
        const el = document.activeElement;
        return {
          tag: el ? el.tagName : 'null',
          inForm: !!form && form.contains(el),
          isBody: el === document.body,
        };
      });
      escapes.push(info);
    }

    const bodyHits = escapes.filter((s) => s.isBody).length;
    const outsideForm = escapes.filter((s) => !s.inForm).length;
    expect(bodyHits, 'focus must never reach <body>').toBe(0);
    expect(outsideForm, 'focus must stay inside quick-input-container').toBe(0);
  });

  test('Shift+Tab from first field lands on last field inside the form', async ({
    page,
  }) => {
    const focusables = await page.evaluate(() => {
      const form = document.querySelector('.quick-input-container');
      if (!form) return [];
      const FOCUSABLE =
        'input:not([disabled]):not([tabindex="-1"]):not([type="hidden"]),' +
        'textarea:not([disabled]):not([tabindex="-1"]),' +
        'select:not([disabled]):not([tabindex="-1"]),' +
        '[tabindex]:not([tabindex="-1"])';
      const isVisible = (el) => {
        const r = el.getBoundingClientRect();
        if (r.width === 0 || r.height === 0) return false;
        const s = window.getComputedStyle(el);
        return (
          s.visibility !== 'hidden' &&
          s.display !== 'none' &&
          s.opacity !== '0'
        );
      };
      return [...form.querySelectorAll(FOCUSABLE)]
        .filter(isVisible)
        .map((el) => ({ tag: el.tagName, name: el.name || el.id || '' }));
    });

    expect(focusables.length).toBeGreaterThanOrEqual(2);

    await page.locator('.quick-input-container input').first().focus();
    await page.waitForTimeout(60);

    await page.keyboard.press('Shift+Tab');
    await page.waitForTimeout(60);

    const result = await page.evaluate(() => {
      const form = document.querySelector('.quick-input-container');
      const FOCUSABLE =
        'input:not([disabled]):not([tabindex="-1"]):not([type="hidden"]),' +
        'textarea:not([disabled]):not([tabindex="-1"]),' +
        'select:not([disabled]):not([tabindex="-1"]),' +
        '[tabindex]:not([tabindex="-1"])';
      const isVisible = (el) => {
        const r = el.getBoundingClientRect();
        if (r.width === 0 || r.height === 0) return false;
        const s = window.getComputedStyle(el);
        return (
          s.visibility !== 'hidden' &&
          s.display !== 'none' &&
          s.opacity !== '0'
        );
      };
      const list = [...form.querySelectorAll(FOCUSABLE)].filter(isVisible);
      const last = list[list.length - 1];
      return {
        inForm: form.contains(document.activeElement),
        isLast: document.activeElement === last,
        activeTag: document.activeElement.tagName,
      };
    });

    expect(result.inForm, 'focus must stay inside quick-input-container').toBe(true);
    expect(result.isLast, 'Shift+Tab from first field must land on last focusable').toBe(true);
  });
});
