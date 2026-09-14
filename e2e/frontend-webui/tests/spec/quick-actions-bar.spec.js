import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, step } from '../utils/common';
import { SHIPMENT_CANDIDATES_WINDOW_ID } from '../utils/WindowIds';

/**
 * Quick-actions bar of a list view.
 *
 * Features tested:
 * - F50000: Frontend WebUI
 *
 * The quick-actions bar is shared by every list view (DocumentList.js), so these tests use a
 * plain list view and need no masterdata.
 *
 * Layout stability: hovering the quick-actions dropdown-toggle button used to render a tooltip that
 * reached past the right edge of `.document-list-wrapper` (an `overflow: auto` box). That gave the
 * wrapper a horizontal scrollbar, which — where scrollbars consume layout space — cascaded into a
 * vertical scrollbar as well and shifted the whole grid sideways, so the grid appeared to resize
 * whenever the pointer crossed the quick-action button operators use all day.
 *
 * These tests assert on the *overflow*, not on the visible shift: the overflow is the defect and is
 * observable everywhere, whereas whether it visibly moves the grid depends on the platform drawing
 * classic rather than overlay scrollbars.
 */

const TOOLTIP_DELAY = 1500; // the tooltip is revealed after a 1s delay (Tooltips.js + buttons.scss)

/**
 * Every scroll container on the page that currently overflows, with the amount.
 * A stable layout means this set is identical before, during and after hovering.
 */
const overflowingScrollContainers = async (page) =>
  await page.evaluate(() => {
    const found = [];
    for (const el of document.querySelectorAll('*')) {
      const cs = getComputedStyle(el);
      const scrollsX = cs.overflowX === 'auto' || cs.overflowX === 'scroll';
      const scrollsY = cs.overflowY === 'auto' || cs.overflowY === 'scroll';
      if (!scrollsX && !scrollsY) continue;
      const dx = el.scrollWidth - el.clientWidth;
      const dy = el.scrollHeight - el.clientHeight;
      if (dx === 0 && dy === 0) continue;
      const classes = String(el.className).trim().split(/\s+/).filter(Boolean).join('.');
      found.push(`${el.tagName.toLowerCase()}${classes ? '.' + classes : ''} dx=${dx} dy=${dy}`);
    }
    return found.sort();
  });

const gridBox = async (page) =>
  await page.evaluate(() => {
    const g = document.querySelector('.document-list-table');
    if (!g) return null;
    const b = g.getBoundingClientRect();
    return {
      x: Math.round(b.x),
      y: Math.round(b.y),
      w: Math.round(b.width),
      h: Math.round(b.height),
    };
  });

/**
 * Inline login — see claude-docs/selectors-and-gotchas.md "Default Login — Role Selection".
 * LoginPage.login() is deliberately not used: its waitForResponse can miss the second
 * authenticate call of the role-selection step.
 */
const login = async (page) => {
  await page.goto(`${FRONTEND_BASE_URL}/login`);
  await page
    .locator('.login-container')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.locator('input[name="username"]').fill('metasfresh');
  await page.locator('input[name="password"]').fill('metasfresh');
  await page.locator('.btn-meta-success').click();

  await page.waitForTimeout(1000);
  if (page.url().includes('/login')) {
    const sendButton = page.locator('.btn-meta-success');
    if (await sendButton.isVisible()) {
      await sendButton.click();
    }
  }
  await page.waitForURL((url) => !url.toString().includes('/login'), {
    timeout: SLOW_ACTION_TIMEOUT,
  });
};

const openListView = async (page) => {
  await login(page);
  await page.goto(`${FRONTEND_BASE_URL}/window/${SHIPMENT_CANDIDATES_WINDOW_ID}`);
  await page
    .locator('.document-list-wrapper')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page
    .getByTestId('quick-action-dropdown-toggle')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // the grid settles its column widths shortly after the layout+data arrive
  await page.waitForTimeout(1500);
};

/** Park the pointer far from the quick-actions area so no hover state is active. */
const restPointer = async (page) => {
  const vp = page.viewportSize();
  await page.mouse.move(5, Math.round(vp.height / 2));
  await page.waitForTimeout(600);
};

const hoverTargets = [
  { testId: 'quick-action-dropdown-toggle', label: 'dropdown toggle' },
  { testId: 'quick-action-button', label: 'green quick-action button' },
];

test.describe('Quick actions', () => {
  hoverTargets.forEach(({ testId, label }) => {
    test(`Hovering the ${label} leaves the list view layout untouched`, async ({ page }) => {
      allure.tag('F50000: Frontend WebUI');
      allure.tag('F50000'); // Standalone tag for Tags section
      allure.story('Quick-actions bar does not disturb the list view layout on hover');
      allure.description(
        `Moving the pointer onto the ${label} must not make any scroll container overflow and ` +
          'must not move the grid — otherwise scrollbars flicker on and off and the grid appears ' +
          'to resize under the pointer.'
      );

      await openListView(page);

      const [restOverflow, restGrid] = await step('Measure the resting layout', async () => {
        await restPointer(page);
        return [await overflowingScrollContainers(page), await gridBox(page)];
      });

      await step(`Hover the ${label}`, async () => {
        await page.getByTestId(testId).hover();
        await page.waitForTimeout(TOOLTIP_DELAY);
      });

      await step('No scroll container gained overflow and the grid did not move', async () => {
        expect(await overflowingScrollContainers(page)).toEqual(restOverflow);
        expect(await gridBox(page)).toEqual(restGrid);
      });

      await step('Layout is still unchanged after the pointer leaves', async () => {
        await restPointer(page);
        expect(await overflowingScrollContainers(page)).toEqual(restOverflow);
        expect(await gridBox(page)).toEqual(restGrid);
      });
    });
  });

  test('The dropdown-toggle tooltip is shown and fits inside the list view', async ({ page }) => {
    allure.tag('F50000: Frontend WebUI');
    allure.tag('F50000'); // Standalone tag for Tags section
    allure.story('Quick-actions bar does not disturb the list view layout on hover');
    allure.description(
      'Guards against removing the hover feedback to fix the layout: the tooltip must still be ' +
        'shown, and must fit inside the scroll container it lives in.'
    );

    await openListView(page);
    await restPointer(page);

    await step('Hover the dropdown toggle', async () => {
      await page.getByTestId('quick-action-dropdown-toggle').hover();
      await page.waitForTimeout(TOOLTIP_DELAY);
    });

    await step('The tooltip is visible', async () => {
      await expect(page.locator('.tooltip-wrapp')).toBeVisible();
    });

    await step('The tooltip fits inside .document-list-wrapper', async () => {
      const outside = await page.evaluate(() => {
        const t = document.querySelector('.tooltip-wrapp').getBoundingClientRect();
        const w = document.querySelector('.document-list-wrapper').getBoundingClientRect();
        return {
          pastRight: Math.round(t.right - w.right),
          pastLeft: Math.round(w.left - t.left),
          pastBottom: Math.round(t.bottom - w.bottom),
        };
      });
      console.log('[INFO] tooltip outside .document-list-wrapper by', JSON.stringify(outside));
      expect(outside.pastRight).toBeLessThanOrEqual(0);
      expect(outside.pastLeft).toBeLessThanOrEqual(0);
      expect(outside.pastBottom).toBeLessThanOrEqual(0);
    });
  });
});
