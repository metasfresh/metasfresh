import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

const COST_IMBALANCE_WINDOW_ID = 542175;
// Both windows sit on PP_Order, so a row click cannot reach the order - hence the zoom field.
const PRODUCTION_ORDER_WINDOW_ID = 53009;

// The filter bar carries no data-testid, so it is addressed by the language-invariant structural
// classes the frontend derives from the identifiers (`form-field-<ColumnName>` per widget).
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

/** Narrow the monitor to one order, optionally switching the HasCostDifference filter on. */
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

      // Applying builds a NEW view server-side; await it instead of sleeping.
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

    const hasCostDifferenceColumn = page.locator('th[data-testid="column-HasCostDifference"]');
    expect(await hasCostDifferenceColumn.count()).toBeGreaterThan(0);

    // The seeded order is completed, so the tab's DocStatus='CO' filter must let it through. It is also
    // balanced, so finding it doubles as the guard that the window does not open pre-filtered on
    // HasCostDifference.
    const row = page.locator('tr').filter({ hasText: documentNo });
    expect(await row.count()).toBeGreaterThan(0);

    // Only the wiring is checked here; the value itself is covered by a cucumber scenario.
    const costDifferenceCell = row.first().locator('[data-cy="cell-CostDifference"]');
    expect(await costDifferenceCell.count()).toBeGreaterThan(0);

    await test.step('HasCostDifference is offered as a filter and is off on load; CostDifference is not offered', async () => {
      const panel = await openFilterPanel({ page });

      await expect(panel.locator('.form-field-HasCostDifference')).toHaveCount(1);
      await expect(panel.locator('.form-field-HasCostDifference input[type="checkbox"]')).not.toBeChecked();

      // A decimal filter would default to an exact-match EQUALS box, hence dropped from the filter set.
      await expect(panel.locator('.form-field-CostDifference')).toHaveCount(0);
    });

    // Control: narrowing to the seeded order alone still finds it.
    await applyMonitorFilter({ page, documentNo });
    await expect(page.locator(TABLE_ROWS)).toHaveCount(1);

    // Switch the cost filter on and the same order is gone -- nothing else about the query changed.
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
    allure.story('PP_Order_CloseSelection is a quick action of the cost-imbalance monitor and refreshes it in place');
    allure.severity('normal');
    allure.description(
      'The monitor lists completed-but-not-closed manufacturing orders and its tab carries no DocAction ' +
        'field, so a balanced order could not be closed from it at all. Verifies the new ' +
        '"Auswahl schliessen" / "Close selection" quick action end to end: it is offered on this window, ' +
        'running it on the selected order closes it, and the closed order consequently drops out of the ' +
        'monitor in place -- with no reload and no re-navigation, so the assertion covers the view ' +
        'refresh and not merely the tab filter. Also guards the window-scoped demotion of the ' +
        'Issue/Receipt launcher: it is still ' +
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

      // Only the DEMOTION half is asserted, deliberately: the matching promotion of
      // PP_Order_PostCalculation needs an accounting schema that accumulates order costs and an order
      // with issued components, neither of which this masterdata API can produce, so that process is
      // simply absent here. It is covered by the costing cucumber scenarios.
    });

    // Survives an SPA re-render but not a reload: that is what makes the assertions below refresh
    // coverage - a freshly opened window would pass either way, proving only the tab's filter.
    const pageLoadMarker = await page.evaluate(() => {
      window.__pageLoadMarker = Math.random().toString(36);
      return window.__pageLoadMarker;
    });

    await test.step('Run Close selection', async () => {
      // Running a quick action is two calls: the POST only creates the pinstance, the follow-up
      // /start executes the process. Awaiting the POST would read the result back before it commits.
      const processExecuted = page.waitForResponse((response) => response.url().endsWith('/start'));
      await closeSelection.click();
      await processExecuted;
    });

    await test.step('The closed order leaves the monitor in place, without reloading the page', async () => {
      // The process rebuilds the selection, so the closed order drops out of the list on its own.
      await expect(page.locator(EMPTY_RESULT)).toBeVisible({ timeout: 30_000 });
      await expect(page.locator(TABLE_ROWS).filter({ hasText: documentNo })).toHaveCount(0);
      expect(await page.evaluate(() => window.__pageLoadMarker)).toBe(pageLoadMarker);
    });

    console.log(
      `Manufacturing order ${documentNo} closed via PP_Order_CloseSelection and left the cost-imbalance monitor in place`
    );
  });

  test('Produktionsauftrag field zooms from the monitor into the Produktionsauftrag window', async ({ page }) => {
    test.setTimeout(120_000);

    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('The cost-imbalance monitor can navigate to the full manufacturing order');
    allure.severity('normal');
    allure.description(
      'The monitor is a read-only list over PP_Order and the Produktionsauftrag window (53009) is a ' +
        'second window over the SAME table, so clicking a monitor row only re-opens it in the monitor -- ' +
        'a controller had no way to reach the full order. Verifies the new "Produktionsauftrag" field in ' +
        'successful action: it renders as a grid column showing the order\'s document number, and zooming ' +
        'it lands on window 53009 on that same order. The landing window is the whole point: a lookup ' +
        'pointing back at its own table could plausibly resolve to the window the user is already in, so ' +
        'the assertion is on the target window id, not merely that "a zoom happened".'
    );

    const { masterdata, documentNo } = await seedCompletedManufacturingOrder();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(COST_IMBALANCE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // Narrow to exactly the seeded order so the cell we right-click is unambiguously its cell.
    await applyMonitorFilter({ page, documentNo });
    await expect(page.locator(TABLE_ROWS)).toHaveCount(1);

    // Selecting on ColumnName keeps this language-independent.
    await expect(page.locator('th[data-testid="column-Link_PP_Order_ID"]')).toHaveCount(1);

    const zoomCell = page.locator(TABLE_ROWS).first().locator('[data-cy="cell-Link_PP_Order_ID"]');
    await expect(zoomCell).toHaveCount(1);
    // AD_Ref_Table.AD_Display is PP_Order.DocumentNo, so the link is labelled with the document number.
    await expect(zoomCell).toContainText(documentNo);

    await test.step('Zoom Into is offered on the field', async () => {
      await zoomCell.click({ button: 'right' });
      await expect(page.locator('.context-menu-open')).toBeVisible();
    });

    // The menu caption is translated; the icon is not, so address the entry by its icon.
    const zoomIntoItem = page.locator('.context-menu-open .context-menu-item').filter({
      has: page.locator('i.meta-icon-share'),
    });
    await expect(zoomIntoItem).toHaveCount(1);

    // This endpoint IS the window resolution under test, so assert it, not only where routing lands.
    const zoomResolved = page.waitForResponse(
      (response) =>
        response.url().includes('/field/Link_PP_Order_ID/zoomInto') && response.status() === 200
    );
    // A grid zoom opens the target in a NEW TAB (containers/Table.js handleZoomInto ->
    // window.open(url, '_blank')), so the assertion is on the popup, not on this page's URL.
    const orderTabOpened = page.context().waitForEvent('page');

    await zoomIntoItem.click();

    const zoomResponse = await zoomResolved;
    const zoomPayload = await zoomResponse.json();
    expect(String(zoomPayload.documentPath.windowId)).toBe(String(PRODUCTION_ORDER_WINDOW_ID));

    const orderTab = await orderTabOpened;
    await orderTab.waitForLoadState('domcontentloaded');

    // The landing window is the point of the whole change: 53009, never back into 542175.
    expect(orderTab.url()).toMatch(new RegExp(`/window/${PRODUCTION_ORDER_WINDOW_ID}/\\d+`));
    expect(orderTab.url()).not.toMatch(new RegExp(`/window/${COST_IMBALANCE_WINDOW_ID}(/|$)`));

    // Same order, not just the right window.
    await expect(orderTab.locator(`text=${documentNo}`).first()).toBeVisible({ timeout: 60_000 });

    console.log(
      `Manufacturing order ${documentNo} zoomed from monitor ${COST_IMBALANCE_WINDOW_ID} into window ${PRODUCTION_ORDER_WINDOW_ID}`
    );
  });
  test('Grid drops Nr. and Belegart; the detail form regroups Belegart, the dates and Lager', async ({ page }) => {
    test.setTimeout(120_000);

    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('Cost-monitor layout: Nr. and Belegart off the grid, Belegart + dates grouped right, Lager promoted left');
    allure.severity('normal');
    allure.description(
      'Verifies the three layout changes the controller asked for: Nr. (DocumentNo) and Belegart ' +
        '(C_DocType_ID) are no longer grid columns while Nr. still works as a filter; in the detail ' +
        'view Belegart and the two date fields share one new group in the right column, below the ' +
        'Aktiv group and above Sektion/Mandant; and Lager sits in the left column primary group. ' +
        'Group assertions go on DOM ancestry, not captions, so they hold in either language.'
    );

    const { masterdata, documentNo } = await seedCompletedManufacturingOrder();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(COST_IMBALANCE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    await test.step('Nr. and Belegart are gone from the grid, the cost columns stay', async () => {
      // Control: on a grid that rendered headers, a zero count means removed, not "not rendered yet".
      await expect(page.locator('th[data-testid="column-CostDifference"]')).toHaveCount(1);
      await expect(page.locator('th[data-testid="column-M_Product_ID"]')).toHaveCount(1);

      await expect(page.locator('th[data-testid="column-DocumentNo"]')).toHaveCount(0);
      await expect(page.locator('th[data-testid="column-C_DocType_ID"]')).toHaveCount(0);
    });

    await test.step('Nr. survives as a filter even though it lost its grid column', async () => {
      const panel = await openFilterPanel({ page });
      await expect(panel.locator('.form-field-DocumentNo')).toHaveCount(1);
    });

    await applyMonitorFilter({ page, documentNo });
    await expect(page.locator(TABLE_ROWS)).toHaveCount(1);

    // The regrouping exists in the single-row view only.
    await MasterWindowPage.clickRow(0);

    const columns = page.locator('.section > .row').first().locator('> div');
    await expect(columns).toHaveCount(2);
    const leftColumn = columns.nth(0);
    const rightColumn = columns.nth(1);

    await test.step('Lager sits in the left column primary group', async () => {
      await expect(leftColumn.locator('.panel-primary .form-field-M_Warehouse_ID')).toHaveCount(1);
    });

    await test.step('Belegart and both date fields left the left column', async () => {
      await expect(leftColumn.locator('.form-field-C_DocType_ID')).toHaveCount(0);
      await expect(leftColumn.locator('.form-field-DatePromised')).toHaveCount(0);
      await expect(leftColumn.locator('.form-field-DateFinishSchedule')).toHaveCount(0);
    });

    await test.step('... and share ONE group in the right column, below Aktiv and above Sektion', async () => {
      const panels = rightColumn.locator('> .panel');
      const panelIndexOf = async (selector) => {
        const panelCount = await panels.count();
        for (let i = 0; i < panelCount; i += 1) {
          if ((await panels.nth(i).locator(selector).count()) > 0) {
            return i;
          }
        }
        return -1;
      };

      const activeIndex = await panelIndexOf('.form-field-IsActive');
      const docTypeIndex = await panelIndexOf('.form-field-C_DocType_ID');
      const orgIndex = await panelIndexOf('.form-field-AD_Org_ID');

      expect(activeIndex).toBeGreaterThanOrEqual(0);
      expect(docTypeIndex).toBeGreaterThanOrEqual(0);

      // One group, not three.
      expect(await panelIndexOf('.form-field-DatePromised')).toBe(docTypeIndex);
      expect(await panelIndexOf('.form-field-DateFinishSchedule')).toBe(docTypeIndex);

      expect(docTypeIndex).toBeGreaterThan(activeIndex);
      expect(orgIndex).toBeGreaterThan(docTypeIndex);
    });

    console.log(
      `Cost-monitor layout verified on order ${documentNo}: Nr./Belegart off the grid, Belegart+dates grouped right, Lager left`
    );
  });
});
