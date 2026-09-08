import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

const COST_IMBALANCE_WINDOW_ID = 542175;

// Language-invariant handles. The filter bar carries no data-testid, so the panel is addressed by the
// structural classes the frontend derives from identifiers: `filter-<filterId>` on the panel body
// (FiltersItem.js) and `form-field-<ColumnName>` on each parameter widget (RawWidget.js).
const FILTER_TOGGLE = '.filters-not-frequent button';
const FILTER_PANEL = '.filter-content.filter-default';
const FILTER_APPLY = '.filter-btn-wrapper button.applyBtn';
const TABLE_ROWS = 'table.js-table tbody tr';
const EMPTY_RESULT = '.empty-info-text';

/** Open the window's filter panel, unless a previous step left it open (the button is a toggle). */
const openFilterPanel = async ({ page }) => {
  const panel = page.locator(FILTER_PANEL);
  if (!(await panel.isVisible())) {
    await page.locator(FILTER_TOGGLE).first().click();
  }
  await expect(panel).toBeVisible();
  return panel;
};

/**
 * Narrow the monitor to one order, optionally switching the HasCostDifference filter on.
 * Applying the panel closes it, so each call re-opens it.
 */
const applyMonitorFilter = async ({ page, documentNo, withHasCostDifference = false }) =>
  await test.step(
    `Filter the monitor by DocumentNo=${documentNo}` +
      (withHasCostDifference ? ' and HasCostDifference switched on' : ''),
    async () => {
      const panel = await openFilterPanel({ page });
      await panel.locator('.form-field-DocumentNo input').fill(documentNo);

      if (withHasCostDifference) {
        await panel.locator('.form-field-HasCostDifference label.input-checkbox').click();
        await expect(panel.locator('.form-field-HasCostDifference input[type="checkbox"]')).toBeChecked();
      }

      // Applying builds a NEW view server-side; await that response instead of sleeping.
      const viewCreated = page.waitForResponse(
        (response) =>
          response.request().method() === 'POST' &&
          response.url().includes(`/documentView/${COST_IMBALANCE_WINDOW_ID}`)
      );
      await page.locator(FILTER_APPLY).click();
      await viewCreated;
    }
  );

const seedCompletedManufacturingOrder = async () => {
  const masterdata = await Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US' } },
      warehouses: { wh: {} },
      products: {
        Component1: {},
        FinishedGood: {
          bom: { lines: [{ product: 'Component1', qty: 1 }] },
        },
      },
      manufacturingOrders: {
        PP1: {
          warehouse: 'wh',
          product: 'FinishedGood',
          qty: 5,
          datePromised: new Date().toISOString(),
        },
      },
    },
  });

  const documentNo = masterdata.manufacturingOrders.PP1.documentNo;
  expect(documentNo).toBeTruthy();

  return { masterdata, documentNo };
};

test.describe('Manufacturing cost-imbalance monitor window', () => {
  test('Completed manufacturing order appears with the CostDifference column', async ({ page }) => {
    test.setTimeout(120_000);

    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('Cost-imbalance monitor window renders completed (CO) PP_Orders with CostDifference');
    allure.severity('normal');
    allure.description(
      "Verifies the cost-imbalance monitor window (read-only monitor over PP_Order, WhereClause " +
        "DocStatus='CO') in successful action: a seeded completed manufacturing order appears in " +
        'the grid, proving the DocStatus filter, and the CostDifference grid column renders for that row. ' +
        'Also covers the HasCostDifference filter that this window (and only this window) gained: the flag ' +
        'is a grid column, it is offered as a filter but NOT pre-applied -- the window must open showing ' +
        'completed-not-closed orders including balanced ones, since those are exactly what the bulk-close ' +
        'action targets -- and switching it on really does narrow the list. CostDifference itself is no ' +
        'longer offered as a filter; it stays a grid column.'
    );

    const { masterdata, documentNo } = await seedCompletedManufacturingOrder();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(COST_IMBALANCE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    // Selecting on ColumnName keeps the assertion language-independent.
    const costDifferenceColumn = page.locator('th[data-testid="column-CostDifference"]');
    expect(await costDifferenceColumn.count()).toBeGreaterThan(0);

    // The Yes/No flag sits next to the amount in the grid: the flag says which orders to work on,
    // the amount says how much.
    const hasCostDifferenceColumn = page.locator('th[data-testid="column-HasCostDifference"]');
    expect(await hasCostDifferenceColumn.count()).toBeGreaterThan(0);

    // The seeded order is completed, so the tab's DocStatus='CO' filter must let it through. It is also
    // balanced (no cost collectors yet), so this doubles as the guard that the window does NOT open
    // pre-filtered on HasCostDifference: a pre-applied cost filter would hide exactly this order.
    const row = page.locator('tr').filter({ hasText: documentNo });
    expect(await row.count()).toBeGreaterThan(0);

    // Only the wiring is checked here; the value itself is covered by a cucumber scenario.
    const costDifferenceCell = row.first().locator('[data-cy="cell-CostDifference"]');
    expect(await costDifferenceCell.count()).toBeGreaterThan(0);

    await test.step('HasCostDifference is offered as a filter and is off on load; CostDifference is not offered', async () => {
      const panel = await openFilterPanel({ page });

      await expect(panel.locator('.form-field-HasCostDifference')).toHaveCount(1);
      await expect(panel.locator('.form-field-HasCostDifference input[type="checkbox"]')).not.toBeChecked();

      // CostDifference is a decimal whose filter would default to an exact-match EQUALS box, so it was
      // deliberately dropped from the filter set. It stays a grid column (asserted above).
      await expect(panel.locator('.form-field-CostDifference')).toHaveCount(0);
    });

    // Control: narrowing to the seeded order alone still finds it.
    await applyMonitorFilter({ page, documentNo });
    await expect(page.locator(TABLE_ROWS)).toHaveCount(1);

    // Switch the cost filter on and the same order is gone -- the filter really narrows, on this very
    // order, and nothing else about the query changed.
    await applyMonitorFilter({ page, documentNo, withHasCostDifference: true });
    await expect(page.locator(EMPTY_RESULT)).toBeVisible();
    await expect(page.locator(TABLE_ROWS).filter({ hasText: documentNo })).toHaveCount(0);

    console.log(`Manufacturing order ${documentNo} rendered in cost-imbalance monitor with CostDifference column`);
  });

  test('Close selection closes the selected order and it leaves the monitor', async ({ page }) => {
    test.setTimeout(120_000);

    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('PP_Order_CloseSelection is a quick action of the cost-imbalance monitor');
    allure.severity('normal');
    allure.description(
      'The monitor lists completed-but-not-closed manufacturing orders and its tab carries no DocAction ' +
        'field, so a balanced order could not be closed from it at all. Verifies the new ' +
        '"Auswahl schliessen" / "Close selection" quick action end to end: it is offered on this window, ' +
        'running it on the selected order closes it, and the closed order consequently drops out of the ' +
        'monitor. Also guards the window-scoped demotion of the Issue/Receipt launcher: it is still ' +
        'offered here but is no longer the default quick action -- a default action always sorts to the ' +
        'front of the action list, so the launcher not being first proves the demotion in any language.'
    );

    const { masterdata, documentNo } = await seedCompletedManufacturingOrder();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(COST_IMBALANCE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // Narrow to exactly the seeded order, so "select all on this page" selects exactly it.
    await applyMonitorFilter({ page, documentNo });
    await expect(page.locator(TABLE_ROWS)).toHaveCount(1);

    await test.step('Select the order (Alt+A selects all rows of the page, i.e. this one)', async () => {
      // The action list is re-evaluated server-side on every selection change; await that, don't sleep.
      const actionsReloaded = page.waitForResponse(
        (response) => response.request().method() === 'POST' && response.url().includes('/quickActions')
      );
      await page.keyboard.press('Alt+a');
      await actionsReloaded;
    });

    await page.getByTestId('quick-action-dropdown-toggle').click();
    const actionItems = page.locator('.quick-actions-dropdown .quick-actions-item');
    await expect(actionItems.first()).toBeVisible();

    const closeSelection = page.getByTestId('quick-action-PP_Order_CloseSelection');
    await expect(closeSelection).toBeVisible();
    await expect(closeSelection).not.toHaveClass(/quick-actions-item-disabled/);

    await test.step('Issue/Receipt is still offered here but is no longer the default quick action', async () => {
      const launcher = page.getByTestId('quick-action-WEBUI_PP_Order_IssueReceipt_Launcher');
      await expect(launcher).toHaveCount(1);
      // Actions are sorted default-quick-action first, so a still-default launcher would be actions[0].
      await expect(actionItems.first()).not.toHaveAttribute(
        'data-testid',
        'quick-action-WEBUI_PP_Order_IssueReceipt_Launcher'
      );

      // Only the DEMOTION half of the default-quick-action change is asserted, on purpose -- the
      // promotion of PP_Order_PostCalculation is not reachable from here and its absence below is not
      // an oversight. That process hides itself (an INTERNAL precondition rejection, so it is dropped
      // from the action list rather than shown disabled) unless exactly one row is selected AND the
      // accounting schema accumulates order costs (AveragePO / LastPOPrice / MovingAverageInvoice --
      // the core preloaded DB is Standard costing) AND the order already has issued components, which
      // the frontend-testing masterdata API cannot produce. Even where it does appear without issued
      // components it is rejected WITH a reason, i.e. disabled, and disabled actions sort last -- so it
      // would still not be actions[0]. Its behaviour is covered by the costing cucumber scenarios.
    });

    await test.step('Run Close selection', async () => {
      const processStarted = page.waitForResponse(
        (response) => response.request().method() === 'POST' && response.url().includes('/process/')
      );
      await closeSelection.click();
      await processStarted;
    });

    // The outcome, read back from a FRESH view: the monitor's tab is scoped to DocStatus='CO', so an
    // order that really got closed can no longer be found here.
    await MasterWindowPage.goto(COST_IMBALANCE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await applyMonitorFilter({ page, documentNo });
    await expect(page.locator(EMPTY_RESULT)).toBeVisible();
    await expect(page.locator(TABLE_ROWS).filter({ hasText: documentNo })).toHaveCount(0);

    console.log(
      `Manufacturing order ${documentNo} closed via PP_Order_CloseSelection and left the cost-imbalance monitor`
    );
  });
});
