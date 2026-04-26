import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PRODUCT_WINDOW_ID } from '../utils/WindowIds';

const MAX_PRODUCT_RESPONSE_BYTES = 1_000_000;

const WINDOW_RESPONSE_REGEX = /\/rest\/api\/window\/\d+\/\d+\/?(\?|$)/;

const captureWindowResponses = (page) => {
  const sizes = [];
  page.on('response', async (response) => {
    try {
      if (response.request().method() !== 'GET') return;
      if (!WINDOW_RESPONSE_REGEX.test(response.url())) return;
      const buf = await response.body();
      sizes.push({ url: response.url(), bytes: buf.length, status: response.status() });
    } catch {
      // body() throws on redirected/aborted requests — ignore
    }
  });
  return sizes;
};

const assertBounded = (sizes, label) => {
  expect(sizes.length, `${label}: expected at least one /rest/api/window/<id>/<recordId>/ response`).toBeGreaterThan(0);
  const max = sizes.reduce((acc, s) => (s.bytes > acc.bytes ? s : acc), sizes[0]);
  const mb = (max.bytes / 1024 / 1024).toFixed(2);
  console.log(`${label}: largest response ${max.bytes} bytes (${mb} MB) — ${max.url}`);
  expect(
    max.bytes,
    `${label}: product window response unexpectedly large (${mb} MB) — possible toString recursion regression (me03#29487)`,
  ).toBeLessThan(MAX_PRODUCT_RESPONSE_BYTES);
  return max;
};

test.describe('Product window — response-size regression guard (me03#29487)', () => {
  test('Loading a product window response stays under 1 MB', async ({ page }) => {
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.tag('F6000');
    allure.story('Document toString recursion regression guard');
    allure.severity('critical');
    allure.description(`
## Regression guard for me03#29487 — Produkt P005004 ist blank

Fix: https://github.com/metasfresh/metasfresh/pull/23672

The original bug produced a 16.7 MB / 33.8 s response for a single product
window because Document.toString() recursively re-embedded the saveStatus.reason
it had seeded into the next exception message — O(2^n) growth.

This is a defense-in-depth baseline: any future change that pushes the
product window response into the megabytes bracket fires this assertion.
The threshold is 1 MB; healthy responses are under ~100 KB.
    `);

    test.setTimeout(120_000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        products: {
          PROD: { name: 'me03-29487 response-size guard', type: 'Item' },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.PROD.id;
    expect(productId, 'masterdata.products.PROD.id').toBeGreaterThan(0);

    const sizes = captureWindowResponses(page);

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await page.goto(`${FRONTEND_BASE_URL}/window/${PRODUCT_WINDOW_ID}/${productId}`);
    await page.waitForLoadState('networkidle', { timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});

    allure.attachment('Captured responses', JSON.stringify(sizes, null, 2), 'application/json');

    assertBounded(sizes, 'Product window initial load');
  });

  test('Switching to the HU-PI Item Product tab keeps the response under 1 MB', async ({ page }) => {
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.tag('F6000');
    allure.story('HU-PI Item Product tab regression guard');
    allure.severity('critical');
    allure.description(`
## Regression guard for me03#29487 — Produkt P005004 ist blank (HU-PI tab)

The customer's reported case was specifically a product whose HU-PI Item
Product tab carried an orphaned child row, which seeded the toString
recursion. This test exercises the tab the customer hit: open the product
window, switch to the HU-PI Item Product included tab, observe that the
response of every \`GET /rest/api/window/<id>/<recordId>/\` stays bounded
even after the tab interaction.

Like test 1 this is a defense-in-depth check: it does not recreate the
orphaned-FK condition (that requires DB or a separate transactional delete
the frontendTesting masterdata API does not currently support). It catches
catastrophic regressions in the product window response payload.
    `);

    test.setTimeout(120_000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        products: {
          PROD: { name: 'me03-29487 HU-PI tab guard', type: 'Item' },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.PROD.id;
    expect(productId, 'masterdata.products.PROD.id').toBeGreaterThan(0);

    const sizes = captureWindowResponses(page);

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await page.goto(`${FRONTEND_BASE_URL}/window/${PRODUCT_WINDOW_ID}/${productId}`);
    await page.waitForLoadState('networkidle', { timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});

    assertBounded(sizes, 'Product window initial load');

    // The CU-TU Allocation tab is M_HU_PI_Item_Product on the Product window.
    // AD_Tab_ID 540517 — language-independent and stable across releases.
    const huPiTab = page.getByTestId('tab-AD_Tab-540517');

    expect(
      await huPiTab.count(),
      'expected the CU-TU Allocation tab (M_HU_PI_Item_Product, AD_Tab 540517) on the Product window',
    ).toBeGreaterThan(0);

    await huPiTab.scrollIntoViewIfNeeded({ timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await huPiTab.click({ timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForLoadState('networkidle', { timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});

    allure.attachment('Captured responses', JSON.stringify(sizes, null, 2), 'application/json');

    assertBounded(sizes, 'Product window after HU-PI tab switch');
  });
});

// TODO follow-up — me03#29487:
//   1. Add/remove HU-PI row cycle on the same window. Requires seeding a
//      M_HU_PI / M_HU_PI_Item via the frontendTesting masterdata API
//      (`packingInstructions`) so the row creation has a valid PI to point at.
//      Will exercise the eviction-on-error commit b877dd70a69 in addition to
//      the toString cap, but still does NOT reproduce the original orphan
//      condition (same-window delete goes through forRootDocumentWritable and
//      re-syncs the cache cleanly).
//   2. Cross-context external delete. Two browser contexts, both logged in,
//      one keeps the parent product cached, the other deletes the HU-PI row.
//      Then the first context tries to interact and the response is captured.
//      This is the only configuration that has a chance of failing on
//      pre-fix code, because the cache invalidation path between contexts
//      mirrors the customer's session-scoped, separate-transaction delete.
//      Designing it requires either:
//        - frontendTesting helper to delete an M_HU_PI_Item_Product row, or
//        - the standalone M_HU_PI_Item_Product window operated in context B.
