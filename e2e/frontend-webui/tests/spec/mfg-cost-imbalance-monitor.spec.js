import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

// Manufacturing cost-imbalance monitor window ("Kostenüberwachung Fertigung")
// Read-only window over PP_Order (DocStatus='CO') surfacing the CostDifference column.
const COST_IMBALANCE_WINDOW_ID = 542175;

test.describe('Manufacturing cost-imbalance monitor window', () => {
  //
  // The window's AD_Tab has WhereClause="DocStatus='CO'" over PP_Order and surfaces the
  // CostDifference virtual column. This test seeds a real completed manufacturing order via
  // the frontendTesting masterdata API (which always drives the PP_Order to DocStatus='CO' on
  // creation) and asserts it renders in the grid with the CostDifference column.
  //
  test('Completed manufacturing order appears with the CostDifference column', async ({ page }) => {
    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('Cost-imbalance monitor window renders completed (CO) PP_Orders with CostDifference');
    allure.severity('normal');
    allure.description(
      "Verifies the cost-imbalance monitor window (read-only monitor over PP_Order, WhereClause " +
        "DocStatus='CO') in successful action: a seeded completed manufacturing order appears in " +
        'the grid, proving the DocStatus filter, and the CostDifference grid column renders for that row.'
    );

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

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(COST_IMBALANCE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    // The CostDifference grid column header is present (language-independent ColumnName selector).
    const costDifferenceColumn = page.locator('th[data-testid="column-CostDifference"]');
    expect(await costDifferenceColumn.count()).toBeGreaterThan(0);

    // The seeded CO order appears in the grid, proving the tab's DocStatus='CO' WhereClause
    // filter let it through (a Drafted/InProgress order would not show up here).
    const row = page.locator('tr').filter({ hasText: documentNo });
    expect(await row.count()).toBeGreaterThan(0);

    // That row renders a CostDifference cell (value correctness is covered by a cucumber test;
    // here we only need the column to be wired to the row, per the window-design-rules
    // verification recipe for a new grid column).
    const costDifferenceCell = row.first().locator('[data-cy="cell-CostDifference"]');
    expect(await costDifferenceCell.count()).toBeGreaterThan(0);

    console.log(`Manufacturing order ${documentNo} rendered in cost-imbalance monitor with CostDifference column`);
  });
});
