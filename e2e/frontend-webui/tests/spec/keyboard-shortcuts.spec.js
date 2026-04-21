import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';

const LOGIN_USERNAME = 'metasfresh';
const LOGIN_PASSWORD = 'metasfresh';

// Organisation window — always present in any metasfresh DB
const WINDOW_URL = '/window/110';

/**
 * Login and navigate to the Organisation window.
 * Handles role selection if prompted.
 */
async function loginAndNavigate(page) {
  await page.goto('/logout', { waitUntil: 'load' });
  await page.goto('/', { waitUntil: 'load' });

  // Wait for login form to appear
  await page
    .locator('.login-container')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  // Fill username
  await page.locator('input[name="username"]').fill(LOGIN_USERNAME);
  // Fill password
  await page.locator('input[name="password"]').fill(LOGIN_PASSWORD);
  // Click login
  await page.locator('.login-container .btn-meta-success').click();

  // Handle role selection if it appears (click the first available role submit button)
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
    // No role selection needed — login completed directly
  }

  // Wait for dashboard to load
  await page
    .locator('.dashboard')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  // Navigate to Organisation window
  await page.goto(WINDOW_URL, { waitUntil: 'load' });

  // Wait for window content to load (the document list or single document view)
  await page
    .locator('.document-list-wrapper, .header-breadcrumb')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Navigate to the Product window (140) and open the first product record
 * in single-document view. Used by me03#27080 tests because Product records
 * are consistently editable for the default test user across DB seeds
 * (unlike the first Organisation record, which can be read-only on some
 * seeds and would cause unrelated test failures).
 */
async function navigateToProductDetail(page) {
  await page.goto('/window/140', { waitUntil: 'load' });
  await page
    .locator('.document-list-wrapper')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const firstRow = page.locator('.table-flex-wrapper tbody tr').first();
  await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await firstRow.dblclick();
  await page.waitForURL(/\/window\/\d+\/\d+/, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page
    .locator('.window-wrapper, .row-selected .form-group')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(1000);
  await page.locator('.window-wrapper').first().click();
}

/**
 * After ALT+E, wait for the modal to open and for the initial focus (placed
 * by SectionGroup.requestElementGroupFocus) to land on an editable element
 * inside the modal. Event-driven: polls document.activeElement rather than
 * sleeping a fixed amount.
 */
async function waitForModalFocus(page) {
  await page
    .locator('.panel-modal')
    .waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
  await page.waitForFunction(
    () => {
      const el = document.activeElement;
      return (
        !!el &&
        !!el.closest('.modal-content-wrapper, .panel-modal') &&
        (['INPUT', 'TEXTAREA', 'SELECT'].includes(el.tagName) ||
          el.classList.contains('input-dropdown-container'))
      );
    },
    { timeout: FAST_ACTION_TIMEOUT }
  );
}

/**
 * Navigate to a single document view by opening the first record.
 * Navigates directly via double-click then waits for URL to contain a record ID.
 */
async function navigateToSingleDocument(page) {
  // We should be on the list view — double-click the first row to open it
  const firstRow = page
    .locator('.table-flex-wrapper tr.row-selected, .table-flex-wrapper .row-0')
    .first();
  await firstRow.waitFor({
    state: 'visible',
    timeout: FAST_ACTION_TIMEOUT,
  });
  await firstRow.dblclick();

  // Wait for navigation to a single record URL (e.g., /window/110/1000000)
  await page.waitForURL(/\/window\/\d+\/\d+/, {
    timeout: SLOW_ACTION_TIMEOUT,
  });

  // Wait for the document to render (form fields visible)
  await page
    .locator('.window-wrapper, .row-selected .form-group')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  // Brief pause to let React register shortcut handlers after navigation
  await page.waitForTimeout(1000);

  // Click on the document area to ensure keyboard focus
  await page.locator('.window-wrapper').first().click();
}

test.describe('Keyboard Shortcuts — Regression Tests (me03#27082)', () => {
  test.beforeEach(async ({ page }) => {
    await loginAndNavigate(page);
  });

  test('ALT+E opens Advanced Edit, ALT+ENTER confirms (closes) it', async ({
    page,
  }) => {
    await navigateToSingleDocument(page);

    // Press ALT+E to open Advanced Edit overlay
    await page.keyboard.press('Alt+e');

    // Verify the modal overlay appeared
    const modal = page.locator('.panel-modal');
    await modal.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(true);

    // Verify the screen freeze overlay is present
    const screenFreeze = page.locator('.screen-freeze');
    expect(await screenFreeze.first().isVisible()).toBe(true);

    // Press ALT+ENTER to confirm (DONE) and close the modal
    await page.keyboard.press('Alt+Enter');

    // Verify the modal is closed
    await modal.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(false);
  });

  test('ALT+E opens Advanced Edit, Escape cancels (closes) it', async ({
    page,
  }) => {
    await navigateToSingleDocument(page);

    // Press ALT+E to open Advanced Edit overlay
    await page.keyboard.press('Alt+e');

    // Verify the modal opened
    const modal = page.locator('.panel-modal');
    await modal.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(true);

    // Press Escape to cancel and close the modal
    await page.keyboard.press('Escape');

    // Verify the modal is closed
    await modal.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(false);
  });

  test('Shortcuts work after re-open (no stale handlers)', async ({
    page,
  }) => {
    await navigateToSingleDocument(page);

    const modal = page.locator('.panel-modal');

    // First cycle: Open with ALT+E, close with Escape
    await page.keyboard.press('Alt+e');
    await modal.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    await page.keyboard.press('Escape');
    await modal.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });

    // Brief pause to let React unmount/remount shortcut handlers
    await page.waitForTimeout(500);

    // Second cycle: Open with ALT+E, close with ALT+ENTER
    await page.keyboard.press('Alt+e');
    await modal.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });

    await page.keyboard.press('Alt+Enter');
    await modal.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(false);
  });

  test('ALT+1 opens SubHeader/Actions menu', async ({ page }) => {
    // Press ALT+1 to open SubHeader
    await page.keyboard.press('Alt+1');

    // Verify the subheader opened
    const subheader = page.locator('.subheader-container.subheader-open');
    await subheader.waitFor({
      state: 'visible',
      timeout: FAST_ACTION_TIMEOUT,
    });
    expect(await subheader.isVisible()).toBe(true);
  });

  test('ALT+3 opens Inbox', async ({ page }) => {
    // Press ALT+3 to open Inbox
    await page.keyboard.press('Alt+3');

    // The .inbox div inside .js-inbox-wrapper only renders when open=true
    const inbox = page.locator('.js-inbox-wrapper .inbox');
    await inbox.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    expect(await inbox.isVisible()).toBe(true);
  });

  test('ALT+4 opens User dropdown', async ({ page }) => {
    // Press ALT+4 to open User dropdown
    await page.keyboard.press('Alt+4');

    // Verify the user dropdown list appeared
    const dropdown = page.locator('.user-dropdown-list');
    await dropdown.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    expect(await dropdown.isVisible()).toBe(true);
  });

  test('Shortcuts work after table filter interaction (UPDATE_HOTKEYS regression)', async ({
    page,
  }) => {
    // We're on the Organisation list view from beforeEach.
    // Click on a column header to trigger table sort — this causes a re-render
    // cycle where Shortcut components unmount/remount, firing UPDATE_HOTKEYS.
    // This was the specific Redux action whose reducer had the spread-operator bug.
    const headerCell = page
      .locator('.table-flex-wrapper thead th')
      .nth(1); // Skip first column (row selection checkbox)
    await headerCell.waitFor({
      state: 'visible',
      timeout: FAST_ACTION_TIMEOUT,
    });
    await headerCell.click();

    // Wait for sort re-render + Redux UPDATE_HOTKEYS dispatch
    await page.waitForTimeout(1000);

    // Now navigate to a single record
    await navigateToSingleDocument(page);

    // Press ALT+E to open Advanced Edit overlay
    await page.keyboard.press('Alt+e');

    // Verify the modal overlay appeared
    const modal = page.locator('.panel-modal');
    await modal.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(true);

    // Press ALT+ENTER to confirm — the exact shortcut that was broken by the bug
    await page.keyboard.press('Alt+Enter');

    // Verify the modal closed — proves shortcuts survived UPDATE_HOTKEYS
    await modal.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });
    expect(await modal.isVisible()).toBe(false);
  });

  test('PageDown/PageUp navigate between pages in list view', async ({
    page,
  }) => {
    // Navigate to Products list (772 records — enough for multiple pages)
    // Window 140 may redirect to override window 541690 on this branch
    await page.goto('/window/140', { waitUntil: 'load' });

    // Wait for the document list and pagination to load
    await page
      .locator('.document-list-wrapper')
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page
      .locator('.pagination-wrapper')
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Wait for table rows to appear
    const rows = page.locator('.table-flex-wrapper table tbody tr');
    await rows.first().waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // Get the active page number before navigation
    const activePage = page.locator('.pagination .page-item.active .page-link');
    await activePage.waitFor({
      state: 'visible',
      timeout: FAST_ACTION_TIMEOUT,
    });
    const initialPageText = await activePage.textContent();

    // Click on the table area to ensure it has focus (PageDown needs focus)
    await rows.first().click();
    await page.waitForTimeout(300);

    // Press PageDown to go to the next page
    await page.keyboard.press('PageDown');

    // Wait for the page to change — the active page number should update
    await page.waitForFunction(
      (initialText) => {
        const active = document.querySelector(
          '.pagination .page-item.active .page-link'
        );
        return active && active.textContent !== initialText;
      },
      initialPageText,
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    // Verify we moved forward (page number should be greater)
    const nextPageText = await activePage.textContent();
    expect(Number(nextPageText)).toBeGreaterThan(Number(initialPageText));

    // Brief pause to let the page fully render
    await page.waitForTimeout(500);

    // Press PageUp to go back
    await page.keyboard.press('PageUp');

    // Wait for the page to return to original
    await page.waitForFunction(
      (originalText) => {
        const active = document.querySelector(
          '.pagination .page-item.active .page-link'
        );
        return active && active.textContent === originalText;
      },
      initialPageText,
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    // Verify we're back on the original page
    const finalPageText = await activePage.textContent();
    expect(finalPageText).toBe(initialPageText);
  });

  // =========================================================================
  // me03#27080 — Advanced Edit modal focus / Tab navigation
  //
  // Before the fix: ALT+E opened the modal but the two "focus-into-modal"
  // mechanisms (Modal.js wrapper `.focus()` and
  // SectionGroup.requestElementGroupFocus) were no-ops because both targeted
  // <div>s without tabindex. Focus stayed on the background element (which
  // the same render had just disabled with tabIndex=-1), so Tab wandered
  // through background DIVs / <body> before reaching the modal — the
  // "cursor lost / jumps erratically" the user reported.
  //
  // Fix: SectionGroup.requestElementGroupFocus now queries for and focuses
  // the first editable <input>/<textarea>/<select> inside the first element
  // group of the modal body. Modal.js's wrapper `.focus()` now skips if
  // anything inside it already has focus (guard against the SectionGroup
  // fix being stolen). A `didInitialFocus` instance flag on SectionGroup
  // prevents re-renders mid-typing from re-focusing the first field.
  //
  // Test strategy: the me03#27080 tests use the **Product** window (140)
  // instead of the Organisation window the other tests use, because the
  // preloaded E2E DB can give the test user read-only access to the first
  // Organisation record, which would make all four assertions fail for
  // permission-related reasons that have nothing to do with the fix. The
  // Product window is known to have plenty of editable master-data fields
  // in the first element group of its advanced-edit modal.
  // =========================================================================

  test('me03#27080: ALT+E moves focus to first editable modal input', async ({
    page,
  }) => {
    await navigateToProductDetail(page);

    // Install a focusin tracer *before* pressing ALT+E so we capture the
    // focus event fired by SectionGroup.requestElementGroupFocus.
    await page.evaluate(() => {
      window.__focusLog = [];
      document.addEventListener(
        'focusin',
        (e) => {
          const t = e.target;
          window.__focusLog.push({
            tag: t.tagName,
            ti: t.tabIndex,
            inModal: !!t.closest?.('.modal-content-wrapper, .panel-modal'),
          });
        },
        true
      );
    });

    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

    const { active, fixedFirstFocus } = await page.evaluate(() => {
      const el = document.activeElement;
      const isEditableEl = (node) =>
        !!node &&
        ((['INPUT', 'TEXTAREA', 'SELECT'].includes(node.tagName) &&
          !node.disabled &&
          node.tabIndex === 0) ||
          (node.tabIndex === 0 &&
            node.classList.contains('input-dropdown-container')));
      return {
        active: el
          ? {
              tag: el.tagName,
              ti: el.tabIndex,
              inModal: !!el.closest('.modal-content-wrapper, .panel-modal'),
              isEditable: isEditableEl(el),
            }
          : null,
        fixedFirstFocus: window.__focusLog.find(
          (ev) =>
            ev.inModal &&
            ev.ti === 0 &&
            ['INPUT', 'TEXTAREA', 'SELECT'].includes(ev.tag)
        ),
      };
    });

    // The fix fires a focusin event on an editable element inside the modal.
    expect(fixedFirstFocus).toBeTruthy();
    // Post-open active element is inside the modal and is itself editable.
    expect(active).toBeTruthy();
    expect(active.inModal).toBe(true);
    expect(active.isEditable).toBe(true);
  });

  test('me03#27080: Tab inside modal walks form fields, never escapes to <body>', async ({
    page,
  }) => {
    await navigateToProductDetail(page);
    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

    // Walk 6 Tab presses and record where focus lands after each
    const stops = [];
    for (let i = 0; i < 6; i += 1) {
      await page.keyboard.press('Tab');
      await page.waitForTimeout(150);
      const info = await page.evaluate(() => {
        const el = document.activeElement;
        return el
          ? {
              tag: el.tagName,
              ti: el.tabIndex,
              inModal: !!el.closest('.modal-content-wrapper, .panel-modal'),
            }
          : null;
      });
      stops.push(info);
    }

    // None of the stops should be on <body> or outside the modal
    const bodyStops = stops.filter((s) => s?.tag === 'BODY').length;
    const outsideModalStops = stops.filter((s) => s && !s.inModal).length;
    expect(bodyStops).toBe(0);
    expect(outsideModalStops).toBe(0);
  });

  test('me03#27080: typing does not steal focus back to the first field (re-render safety)', async ({
    page,
  }) => {
    await navigateToProductDetail(page);
    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

    // Skip forward a few tab stops so we are NOT on the first field that
    // the fix targets. If focus were to be stolen on re-render, it would
    // land back on the first field.
    await page.keyboard.press('Tab');
    await page.keyboard.press('Tab');
    await page.waitForTimeout(300);

    const beforeTyping = await page.evaluate(() => {
      const el = document.activeElement;
      return el
        ? {
            tag: el.tagName,
            ti: el.tabIndex,
            cls: (el.className || '').slice(0, 60),
          }
        : null;
    });

    // Only meaningful if we landed on a text-typeable element. If not, skip
    // the steal assertion (the Tab navigation test already covers the walk).
    if (
      beforeTyping &&
      ['INPUT', 'TEXTAREA'].includes(beforeTyping.tag) &&
      beforeTyping.ti === 0
    ) {
      await page.keyboard.type('X', { delay: 40 });
      await page.waitForTimeout(1200); // wait for any PATCH + re-render

      const afterTyping = await page.evaluate(() => {
        const el = document.activeElement;
        return el
          ? {
              tag: el.tagName,
              ti: el.tabIndex,
              cls: (el.className || '').slice(0, 60),
            }
          : null;
      });

      // Focus must remain on the same element (identity checked by tag +
      // className, which is stable across re-renders when the element
      // is not unmounted).
      expect(afterTyping?.tag).toBe(beforeTyping.tag);
      expect(afterTyping?.cls).toBe(beforeTyping.cls);
    }
  });

  test('me03#27080: closing and reopening modal refocuses the first editable element', async ({
    page,
  }) => {
    await navigateToProductDetail(page);
    const modal = page.locator('.panel-modal');

    // Open, close, reopen — each cycle must place focus inside the modal on
    // an editable element (input / textarea / select / lookup-dropdown).
    for (let cycle = 0; cycle < 2; cycle += 1) {
      await page.keyboard.press('Alt+e');
      await waitForModalFocus(page);

      const info = await page.evaluate(() => {
        const el = document.activeElement;
        if (!el) return null;
        return {
          tag: el.tagName,
          ti: el.tabIndex,
          inModal: !!el.closest('.modal-content-wrapper, .panel-modal'),
          isEditable:
            (['INPUT', 'TEXTAREA', 'SELECT'].includes(el.tagName) &&
              !el.disabled &&
              el.tabIndex === 0) ||
            (el.tabIndex === 0 &&
              el.classList.contains('input-dropdown-container')),
        };
      });

      expect(info).toBeTruthy();
      expect(info.inModal).toBe(true);
      expect(info.isEditable).toBe(true);

      await page.keyboard.press('Escape');
      await modal.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });
      await page.waitForTimeout(300);
    }
  });

  test('Escape closes each menu after opening', async ({ page }) => {
    // --- ALT+1 → Escape ---
    await page.keyboard.press('Alt+1');
    const subheader = page.locator('.subheader-container.subheader-open');
    await subheader.waitFor({
      state: 'visible',
      timeout: FAST_ACTION_TIMEOUT,
    });
    await page.keyboard.press('Escape');
    await subheader.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });

    // Brief pause between shortcuts
    await page.waitForTimeout(300);

    // --- ALT+3 → click outside to close ---
    // Note: Inbox uses onClickOutside handler, not Escape key
    await page.keyboard.press('Alt+3');
    const inbox = page.locator('.js-inbox-wrapper .inbox');
    await inbox.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    // Click on the document list area (outside the inbox) to close it
    await page.locator('.document-list-wrapper, .header-breadcrumb').first().click();
    await inbox.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });

    // Brief pause between shortcuts
    await page.waitForTimeout(300);

    // --- ALT+4 → Escape ---
    await page.keyboard.press('Alt+4');
    const dropdown = page.locator('.user-dropdown-list');
    await dropdown.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    await page.keyboard.press('Escape');
    await dropdown.waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT });
  });
});
