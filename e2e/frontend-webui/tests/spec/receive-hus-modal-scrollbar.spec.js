import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
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

test.use({
  viewport: { width: 1400, height: 520 },
  launchOptions: {
    ignoreDefaultArgs: ['--hide-scrollbars'],
    // test.use replaces the project's launchOptions, so the project's arguments are repeated
    args: [
      '--no-sandbox',
      '--disable-features=StrictOriginPolicy,HttpsOnlyMode,BlockInsecurePrivateNetworkRequests',
      '--disable-site-isolation-trials',
      '--disable-web-security',
      '--ignore-certificate-errors',
      '--allow-insecure-localhost',
      '--allow-running-insecure-content',
    ],
  },
});

const MODAL_ROWS = '.panel-modal .table tbody tr';
const MODAL_SELECTED_ROWS = '.panel-modal .table tbody tr.row-selected';
const MODAL_ATTRIBUTES_PANEL = '.panel-modal .attributes-selector';
const MODAL_QUICK_ACTION = '.panel-modal [data-testid="quick-action-button"]';

/** Opens the receive-HUs modal for a fresh purchase order's receipt candidate and selects one HU row. */
async function openReceiveModalAndSelectRow(page) {
  const masterdata = await Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US' } },
      bpartners: { VENDOR1: { isVendor: true, isCustomer: false, isSoPriceList: false } },
      products: {
        Product1: {
          name: 'Test Product for Receive HUs modal scrollbar',
          type: 'Item',
          prices: [{ price: 10.0, currencyCode: 'EUR' }],
        },
      },
    },
  });

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await PurchaseOrderPage.goto();
  await PurchaseOrderPage.clickNew();
  await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
  await PurchaseOrderPage.goToOrderLineTab();
  await PurchaseOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: '5' });
  await PurchaseOrderPage.complete();

  await PurchaseOrderPage.openRelatedReceiptCandidate();
  await ReceiptCandidatesPage.expectQuickActionsVisible();

  await ReceiptCandidatesPage.executeDefaultQuickAction();
  await ReceiptCandidatesPage.waitForPlanningHURows();

  // select the last row: the row whose "Merkmale" panel makes the modal content overflow
  const rows = page.locator(MODAL_ROWS);
  await rows.last().click();
  await expectRowSelectedWithPanel(page);
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

  test('TC1a: wheel scroll keeps the selected HU row (AC7)', async ({ page }) => {
    describeCase('Wheel scroll keeps the selection');
    await openReceiveModalAndSelectRow(page);

    const wrapper = page.locator('.panel-modal .document-list-wrapper').first();
    const box = await wrapper.boundingBox();
    await page.mouse.move(box.x + box.width / 2, box.y + box.height / 2);
    await page.mouse.wheel(0, 120);
    await expect.poll(() => wrapper.evaluate((el) => el.scrollTop)).toBeGreaterThan(0);

    await expectRowSelectedWithPanel(page, 'after the wheel scroll');
  });

  test('TC1b: pressing the vertical scrollbar keeps the selected HU row (AC5, AC6)', async ({ page }) => {
    describeCase('Scrollbar press keeps the selection');
    await openReceiveModalAndSelectRow(page);

    const result = await ReceiptCandidatesPage.pressVerticalScrollbarOfModal();

    await expectRowSelectedWithPanel(page, 'after the scrollbar press');
    expect(result.scrollTopAfter, 'the press on the scrollbar track scrolled the content').toBeGreaterThan(result.scrollTopBefore);
  });

  test('TC1c: dragging the scrollbar thumb keeps the selected HU row (AC5, AC6)', async ({ page }) => {
    describeCase('Scrollbar thumb drag keeps the selection');
    await openReceiveModalAndSelectRow(page);

    const result = await ReceiptCandidatesPage.pressVerticalScrollbarOfModal({ drag: true });

    await expectRowSelectedWithPanel(page, 'after the scrollbar press');
    expect(result.scrollTopAfter, 'dragging the scrollbar thumb scrolled the content').toBeGreaterThan(result.scrollTopBefore);
  });

  test('TC1d: a click on blank list area inside the modal still deselects (unchanged behaviour)', async ({ page }) => {
    describeCase('Blank area click still deselects');
    await openReceiveModalAndSelectRow(page);

    // a point on the list area outside the table and the Merkmale panel: the wrapper itself or one of its direct
    // children (e.g. the toolbar strip) that is not inside a `js-not-unselect` region
    const point = await page.locator('.panel-modal .document-list-wrapper').first().evaluate((el) => {
      const r = el.getBoundingClientRect();
      for (let y = r.top + el.clientTop + 2; y < r.top + el.clientTop + el.clientHeight; y += 4) {
        for (let x = r.left + el.clientLeft + 2; x < r.left + el.clientLeft + el.clientWidth; x += 4) {
          const hit = document.elementFromPoint(x, y);
          if (hit && (hit === el || hit.parentNode === el) && !hit.closest('.js-not-unselect')) {
            return { x, y };
          }
        }
      }
      return null;
    });
    expect(point, 'no blank list-area point found inside the modal list wrapper at this viewport').not.toBeNull();

    await page.mouse.click(point.x, point.y);

    await expect(page.locator(MODAL_SELECTED_ROWS)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
  });
});
