import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * The attribute popup ("Merkmale") opened from an order-line grid cell renders its widgets exactly
 * like the same popup outside a grid: the grid-cell editor sizing (26px height, no padding, no
 * minimum width) applies to the cell's own editor only, never to the widgets inside the popup.
 *
 * Real-life case: on an order line the user clicks the attribute button of the "Merkmale" column.
 * The popup lists the product's attributes (here a list attribute and a text attribute); each
 * field keeps its normal height, padding and the list's normal minimum width, so values and the
 * dropdown arrow are not squeezed.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const ATTRIBUTE_COLUMN = 'M_AttributeSetInstance_ID';

const STYLE_PROPERTIES = [
  'height',
  'minWidth',
  'paddingTop',
  'paddingBottom',
  'paddingLeft',
  'paddingRight',
];

/**
 * Reads the computed geometry of the popup's widgets twice: in place (inside the grid cell) and on
 * a copy of the popup attached to `<body>`, i.e. outside any grid. Returns both, per widget.
 */
async function readPopupWidgetGeometry(popup, properties) {
  return await popup.evaluate((root, props) => {
    const SELECTORS = {
      list: '.input-dropdown-container',
      textWrapper: '.form-group:not(.widgetType-List) .input-secondary',
      textInput: '.form-group:not(.widgetType-List) input',
    };
    const read = (container) => {
      const result = {};
      Object.entries(SELECTORS).forEach(([name, selector]) => {
        const el = container.querySelector(selector);
        const style = el ? getComputedStyle(el) : null;
        result[name] = style ? Object.fromEntries(props.map((p) => [p, style[p]])) : null;
      });
      return result;
    };

    const inGrid = read(root);
    const copy = root.cloneNode(true);
    document.body.appendChild(copy);
    const outsideGrid = read(copy);
    copy.remove();

    return { inGrid, outsideGrid };
  }, properties);
}

test.describe('Sales order-line grid — the attribute popup keeps its own widget geometry', () => {
  test('Widgets in the attribute popup opened from a grid cell are not sized like the cell editor', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — attribute popup widget geometry');
    allure.severity('normal');
    test.setTimeout(180000);

    const runId = Date.now();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'E2E', lastname: 'Tester' } },
        bpartners: {
          CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
        productCategories: {
          CAT1: { attributeSetName: 'POPUP_SET' },
        },
        attributes: {
          LIST_ATTR: {
            value: `E2EPOPL${runId}`,
            name: `E2E Popup List ${runId}`,
            attributeValueType: 'LIST',
            isInstanceAttribute: true,
            listValues: [
              { value: 'A', name: 'Alpha' },
              { value: 'B', name: 'Beta' },
            ],
            attributeSetNames: ['POPUP_SET'],
          },
          TEXT_ATTR: {
            value: `E2EPOPT${runId}`,
            name: `E2E Popup Text ${runId}`,
            attributeValueType: 'STRING',
            isInstanceAttribute: true,
            attributeSetNames: ['POPUP_SET'],
          },
        },
        products: {
          Product1: {
            name: 'POPUPPROD',
            type: 'Item',
            productCategory: 'CAT1',
            prices: [{ price: 12.5, currencyCode: 'EUR' }],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({
      product: masterdata.products.Product1.productCode,
      quantity: 1,
      recordId,
    });

    const cell = page.locator(`[data-cy="cell-${ATTRIBUTE_COLUMN}"]`).first();
    await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    const popup = page.locator('.attributes-dropdown');
    await test.step('Open the attribute popup from the grid cell', async () => {
      await cell.dblclick();
      await cell.locator('.attributes-in-table button').click();
      await popup.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(popup.locator('.input-dropdown-container')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    });

    const geometry = await readPopupWidgetGeometry(popup, STYLE_PROPERTIES);
    allure.attachment('Popup widget geometry', JSON.stringify(geometry, null, 2), 'application/json');

    await test.step('Each popup widget has the geometry it has outside a grid', async () => {
      for (const widget of Object.keys(geometry.outsideGrid)) {
        expect(geometry.outsideGrid[widget], `popup widget "${widget}" must be rendered`).not.toBeNull();
        expect(geometry.inGrid[widget], `popup widget "${widget}": in the grid cell vs outside a grid`).toEqual(
          geometry.outsideGrid[widget]
        );
      }
    });
  });
});
