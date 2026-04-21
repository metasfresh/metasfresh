import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';

const LOGIN_USERNAME = 'metasfresh';
const LOGIN_PASSWORD = 'metasfresh';

// Window IDs used by the tests.
//   140 — Product (always has editable header fields → baseline)
//   194 — Bank Statement (the exact window reported in me03#27080)
const PRODUCT_WINDOW_ID = 140;
const BANK_STATEMENT_WINDOW_ID = 194;

/**
 * Login flow — kept compact; mirrors the helper in `keyboard-shortcuts.spec.js`
 * which was written for me03#27082. Duplicated intentionally rather than
 * imported from a shared utils file so that each spec stays self-contained,
 * matching the project's existing spec-file style.
 */
async function loginAsMetasfresh(page) {
  await page.goto('/logout', { waitUntil: 'load' });
  await page.goto('/', { waitUntil: 'load' });

  await page
    .locator('.login-container')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await page.locator('input[name="username"]').fill(LOGIN_USERNAME);
  await page.locator('input[name="password"]').fill(LOGIN_PASSWORD);
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
 * Open a single-record view for the given window. For the Bank Statement
 * window (194), which has no existing records in the preloaded E2E DB, we
 * create a new record via ALT+N. For other windows we open the first list
 * row.
 */
async function openSingleRecord(page, windowId) {
  await page.goto(`/window/${windowId}`, { waitUntil: 'load' });
  await page
    .locator('.document-list-wrapper')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  const rows = page.locator('.table-flex-wrapper tbody tr');
  const rowCount = await rows.count();

  if (rowCount === 0) {
    // Create a new record — ALT+N navigates to `/window/<id>/NEW` and persists
    // it, ending up on `/window/<id>/<new-id>` with a Drafted document.
    await page.keyboard.press('Alt+n');
  } else {
    await rows.first().dblclick();
  }

  await page.waitForURL(/\/window\/\d+\/\d+/, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page
    .locator('.window-wrapper, .row-selected .form-group')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // Give React a beat to register shortcut handlers after navigation.
  await page.waitForTimeout(1000);
  // Click the window wrapper so keyboard events target the document (not the
  // empty list behind it).
  await page.locator('.window-wrapper').first().click();
}

/**
 * After ALT+E, wait for the modal to open and for SectionGroup's initial focus
 * to land on an editable element inside it. Event-driven — polls
 * document.activeElement rather than sleeping a fixed amount.
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
 * Focus-in tracer installed via page.evaluate — call once BEFORE pressing
 * ALT+E to record every focusin event that fires during the modal-open
 * sequence.
 */
async function installFocusTracer(page) {
  await page.evaluate(() => {
    window.__focusLog = [];
    document.addEventListener(
      'focusin',
      (e) => {
        const t = e.target;
        window.__focusLog.push({
          tag: t.tagName,
          ti: t.tabIndex,
          cls: (t.className || '').slice(0, 60),
          inModal: !!t.closest?.('.modal-content-wrapper, .panel-modal'),
        });
      },
      true
    );
  });
}

// =========================================================================
// me03#27080 — Advanced Edit modal: focus placement and Tab navigation.
//
// Before the fix two "focus-into-modal" mechanisms were silently no-ops
// because they called `.focus()` on a plain `<div>` without `tabindex`:
//   - `Modal.js` — `ref={c => c.focus()}` on `.panel-modal-content`.
//   - `SectionGroup.requestElementGroupFocus` — `.focus()` on the first
//     element-group `<div>`.
// Consequence: focus stayed on the background element (which the same
// render disabled with `tabIndex=-1`), so the user's Tab keys wandered
// through background DIVs and `<body>` before reaching the modal — the
// "cursor lost / jumps erratically" described in the original report.
//
// Fix (commits 9818dd27a59 + a1d8caba2a5):
//   - `SectionGroup.requestElementGroupFocus` now queries for the first
//     editable `<input>`/`<textarea>`/`<select>` and focuses it. Fallbacks
//     cover "data still loading" (bail and retry next render), "all fields
//     readonly" (focus primary action button), and "empty modal" (focus
//     the container with tabindex=-1). A per-instance `didInitialFocus`
//     flag prevents re-renders mid-typing from re-focusing the first field.
//   - `Modal.js` — the wrapper `.focus()` now skips if a descendant is
//     already focused, so the SectionGroup fix is not stolen.
//
// Tests use two windows to show the fix is not layout-specific:
//   - Product (140) — baseline; has many editable header fields.
//   - Bank Statement (194) — the exact window in the original report.
//
// Feature tags: F50030 Keyboard Shortcuts + F50000 Frontend WebUI (E0294).
// =========================================================================

test.describe('Advanced Edit modal — focus placement & Tab navigation (me03#27080)', () => {
  test.beforeEach(async ({ page }) => {
    await loginAsMetasfresh(page);
  });

  test('ALT+E on Product window focuses an editable input inside the modal', async ({
    page,
  }) => {
    await openSingleRecord(page, PRODUCT_WINDOW_ID);
    await installFocusTracer(page);

    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

    const { active, firstFocusInModal } = await page.evaluate(() => {
      const el = document.activeElement;
      const isEditable = (node) =>
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
              isEditable: isEditable(el),
            }
          : null,
        firstFocusInModal: window.__focusLog.find(
          (ev) =>
            ev.inModal &&
            ev.ti === 0 &&
            ['INPUT', 'TEXTAREA', 'SELECT'].includes(ev.tag)
        ),
      };
    });

    expect(firstFocusInModal).toBeTruthy();
    expect(active).toBeTruthy();
    expect(active.inModal).toBe(true);
    expect(active.isEditable).toBe(true);
  });

  test('ALT+E on Bank Statement window (me03#27080 original report) focuses an editable input', async ({
    page,
  }) => {
    // Same assertions as the Product test but against the exact window the
    // customer reported. This proves the fix is not specific to a single
    // window's AD_UI_Element layout.
    await openSingleRecord(page, BANK_STATEMENT_WINDOW_ID);
    await installFocusTracer(page);

    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

    const { active, focusEventsInModal } = await page.evaluate(() => {
      const el = document.activeElement;
      const isEditable = (node) =>
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
              isEditable: isEditable(el),
            }
          : null,
        focusEventsInModal: window.__focusLog.filter((ev) => ev.inModal).length,
      };
    });

    expect(active).toBeTruthy();
    expect(active.inModal).toBe(true);
    expect(active.isEditable).toBe(true);
    // Before the fix, zero focusin events fired inside the modal on open.
    // After the fix, at least one must fire.
    expect(focusEventsInModal).toBeGreaterThanOrEqual(1);
  });

  test('Tab inside modal walks form fields, never escapes to <body>', async ({
    page,
  }) => {
    await openSingleRecord(page, PRODUCT_WINDOW_ID);
    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

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

    const bodyStops = stops.filter((s) => s?.tag === 'BODY').length;
    const outsideModalStops = stops.filter((s) => s && !s.inModal).length;
    expect(bodyStops).toBe(0);
    expect(outsideModalStops).toBe(0);
  });

  test('Typing does not steal focus back to the first field (re-render safety)', async ({
    page,
  }) => {
    await openSingleRecord(page, PRODUCT_WINDOW_ID);
    await page.keyboard.press('Alt+e');
    await waitForModalFocus(page);

    // Tab forward a couple of fields so we are NOT on the one the fix
    // initially targets. If a PATCH-induced re-render were to call
    // requestElementGroupFocus again it would yank focus back to the first
    // field — the invariant this test guards against.
    await page.keyboard.press('Tab');
    await page.keyboard.press('Tab');
    await page.waitForTimeout(300);

    const before = await page.evaluate(() => {
      const el = document.activeElement;
      return el
        ? {
            tag: el.tagName,
            ti: el.tabIndex,
            cls: (el.className || '').slice(0, 60),
          }
        : null;
    });

    if (
      before &&
      ['INPUT', 'TEXTAREA'].includes(before.tag) &&
      before.ti === 0
    ) {
      await page.keyboard.type('X', { delay: 40 });
      // Allow PATCH + re-render to complete.
      await page.waitForTimeout(1200);

      const after = await page.evaluate(() => {
        const el = document.activeElement;
        return el
          ? {
              tag: el.tagName,
              ti: el.tabIndex,
              cls: (el.className || '').slice(0, 60),
            }
          : null;
      });

      expect(after?.tag).toBe(before.tag);
      expect(after?.cls).toBe(before.cls);
    }
  });

  test('Closing and reopening the modal refocuses the first editable element', async ({
    page,
  }) => {
    await openSingleRecord(page, PRODUCT_WINDOW_ID);
    const modal = page.locator('.panel-modal');

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
});
