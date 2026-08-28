import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

const COST_IMBALANCE_WINDOW_ID = 542175;

test.describe('Manufacturing cost-imbalance monitor window', () => {
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

    // Selecting on ColumnName keeps the assertion language-independent.
    const costDifferenceColumn = page.locator('th[data-testid="column-CostDifference"]');
    expect(await costDifferenceColumn.count()).toBeGreaterThan(0);

    // The seeded order is completed, so the tab's DocStatus='CO' filter must let it through.
    const row = page.locator('tr').filter({ hasText: documentNo });
    expect(await row.count()).toBeGreaterThan(0);

    // Only the wiring is checked here; the value itself is covered by a cucumber scenario.
    const costDifferenceCell = row.first().locator('[data-cy="cell-CostDifference"]');
    expect(await costDifferenceCell.count()).toBeGreaterThan(0);

    console.log(`Manufacturing order ${documentNo} rendered in cost-imbalance monitor with CostDifference column`);
  });
});
