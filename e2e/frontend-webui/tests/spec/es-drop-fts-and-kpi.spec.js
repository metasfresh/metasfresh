import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { BUSINESS_PARTNER_WINDOW_ID, PRODUCT_WINDOW_ID } from '../utils/WindowIds';
import { ES_DROP_SEED, seedEsDropMasterdata, seedEsDropKpi } from '../utils/EsDropSeed';
import { FtsSearchPage, KpiTilesPage } from '../utils/pages/FtsSearchPage';
import { getPage } from '../utils/common';

/**
 * Proof that the Elasticsearch -> Postgres replacement works end to end through the real UI:
 *  - the window "Search records" filter is the Postgres full-text-search filter (filter id postgres-full-text-search)
 *    and finds a seeded Business Partner / Product by a name word and by its value;
 *  - the two SQL KPIs of dashboard 540000 show the seeded order sums.
 *
 * Prerequisites (stack under test): migrations of the ES-drop branch applied (the postgres-full-text-search filter
 * enabled, C_BPartner_FTS / M_Product_FTS populated). The two FTS scenarios seed the partner + product over the
 * frontendTesting REST endpoint (CI-safe). The KPI scenario seeds backdated orders via psql and is SKIPPED in CI
 * (the KPIs sum ALL orders of the period for the session org, which the shared CI DB cannot keep deterministic);
 * it runs on isolated stacks (local + the UAT deploy), where its fixture asserts the exact sums. See EsDropSeed.js.
 *
 * Features (Google Sheets registry): F15010 Fulltext Search Partner, F15021 Fulltext Search Product (epic E0194);
 * the dashboard KPIs have no feature of their own, so F71000 Build Infrastructure (epic E2600) carries them.
 */
const login = async () => {
  const masterdata = await Backend.createMasterdata({ request: { login: { user: { language: 'en_US' } } } });
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();
};

const searchWindow = async ({ windowId, term, expected }) => {
  await MasterWindowPage.goto(windowId);
  await FtsSearchPage.expectSearchFilterVisible();
  await FtsSearchPage.searchRecords(term);
  await FtsSearchPage.expectGridRow(expected);
};

test.describe('ES drop: Postgres full-text search and SQL KPIs', () => {
  test.beforeEach(async () => {
    allure.epic('E0194: System Search and Navigation');
    allure.parentSuite('Elasticsearch to Postgres replacement');
  });

  test('Business Partner search finds the seeded partner by name word and by value', async ({ page }) => {
    allure.tag('F15010: Fulltext Search Partner');
    allure.tag('F15010');
    allure.story('Postgres FTS: Business Partner');
    allure.severity('critical');
    allure.description('The Business Partner window "Search records" filter (Postgres FTS) finds the seeded partner ("Zyxquarta… Handels GmbH") by its unique name word and by its value (per-run-unique keys).');

    await login();
    await seedEsDropMasterdata();
    const { partner } = ES_DROP_SEED;
    await searchWindow({ windowId: BUSINESS_PARTNER_WINDOW_ID, term: partner.nameWord, expected: partner });
    await searchWindow({ windowId: BUSINESS_PARTNER_WINDOW_ID, term: partner.value, expected: partner });
  });

  test('Product search finds the seeded product by name word and by value', async ({ page }) => {
    allure.tag('F15021: Fulltext Search Product');
    allure.tag('F15021');
    allure.story('Postgres FTS: Product');
    allure.severity('critical');
    allure.description('The Product window "Search records" filter (Postgres FTS) finds the seeded product ("Zyxbanane… Bio Spezialsorte") by its unique name word and by its value (per-run-unique keys).');

    await login();
    await seedEsDropMasterdata();
    const { product } = ES_DROP_SEED;
    await searchWindow({ windowId: PRODUCT_WINDOW_ID, term: product.nameWord, expected: product });
    await searchWindow({ windowId: PRODUCT_WINDOW_ID, term: product.value, expected: product });
  });

  test('Dashboard 540000 shows the seeded sales and purchase order net amounts', async ({ page }) => {
    // The KPIs sum ALL orders of the period for the session org; the shared CI frontend-webui DB accumulates
    // today-dated orders from other specs in org MAIN, so the sums are not deterministic there. This scenario runs
    // on isolated stacks only (local + the UAT deploy), where the psql fixture asserts the exact sums itself.
    test.skip(!!process.env.CI, 'KPI sums all in-period orders of the org; not deterministic on the shared CI DB — runs on isolated stacks (local + UAT) only');

    allure.tag('F71000: Build Infrastructure');
    allure.tag('F71000');
    allure.story('SQL KPIs: order net amount');
    allure.severity('critical');
    allure.description('Dashboard 540000 tiles "Sales orders net amt last week" = 350.50 and "Purchase net amt this week" = 100.25 (seeded orders; a 999.99 sales order outside the period is not counted).');

    await login();
    await seedEsDropKpi();
    await test.step('Open the dashboard', async () => {
      await getPage().goto('/');
      await DashboardPage.expectVisible();
    });

    // the UI drops a trailing zero (350.50 is rendered 350.5), hence the optional last 0 for the sales amount
    await KpiTilesPage.expectTile({
      caption: 'Sales orders net amt',
      amountText: ES_DROP_SEED.salesNetAmount,
      amountPattern: /(?<![\d.,])350[.,]50?(?!\d)/,
    });
    await KpiTilesPage.expectTile({
      caption: 'Purchase net amt',
      amountText: ES_DROP_SEED.purchaseNetAmount,
      amountPattern: /(?<![\d.,])100[.,]25(?!\d)/,
    });
  });
});
