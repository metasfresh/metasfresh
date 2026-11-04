import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { BankStatementPage } from '../utils/pages/BankStatementPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { DOCTYPE_AR_RECEIPT } from '../utils/DocTypeIds';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Bank statement (Bankauszug, window 194), tab "Auszugs-Position" (tab 329): the sales order of a bank statement line
 * (C_BankStatementLine.C_Order_ID, AD_Field 555325) is shown on the line, and its dropdown offers only the prepay sales
 * orders that still wait for payment (AD_Val_Rule 540811, migrations 5832820 + 5832940).
 *
 * Real-life flow: two prepay orders of the same customer wait for payment; the customer paid one of them (a completed
 * payment carries it). The bank statement line of that customer must offer only the order that still waits for payment.
 *  - the field is part of the line's grid columns and of the line's advanced-edit form;
 *  - the dropdown offers the waiting prepay order;
 *  - the dropdown does not offer the already paid order (completing its payment released it: it no longer waits for
 *    payment; the val rule's extra exclusion of orders carried by a CO/CL payment is covered by the Cucumber scenarios).
 *
 * Data: master data via the frontendTesting API; bank statement, payment and payment completion through the WebUI REST
 * API (the same calls the window makes). No raw SQL.
 */

const BANK_STATEMENT_WINDOW_ID = 194;
const BANK_STATEMENT_LINE_TAB_ID = 'AD_Tab-329';
const PAYMENT_WINDOW_ID = 195;

const firstDocument = (body) => (Array.isArray(body) ? body : body.documents || [body])[0];

const rest = async (page, method, path, data) => {
  const response = await page.request.fetch(`${WEBAPI_BASE_URL}/${path}`, { method, data });
  if (!response.ok()) {
    throw new Error(`${method} ${path} failed: HTTP ${response.status()} ${await response.text()}`);
  }
  const text = await response.text();
  return text ? JSON.parse(text) : {};
};

const patchDocument = async (page, windowId, documentId, changes) =>
  firstDocument(await rest(page, 'PATCH', `window/${windowId}/${documentId}`, changes));

/** The first value the WebUI dropdown of a field offers (e.g. the org's bank account), as the lookup value to patch */
const firstDropdownValue = async (page, windowId, documentId, fieldName) => {
  const body = await rest(page, 'GET', `window/${windowId}/${documentId}/field/${fieldName}/dropdown`);
  const values = Array.isArray(body) ? body : body.values || [];
  if (!values.length) {
    throw new Error(`the dropdown of ${fieldName} in window ${windowId} offers no value`);
  }
  return values[0];
};

const replace = (path, value) => ({ op: 'replace', path, value });

/** The value of a field of a (root) document as returned by the WebUI API; a lookup value is returned as its key */
const fieldValue = (document, fieldName) => {
  const field = (document.fieldsByName || {})[fieldName];
  const value = field && field.value;
  return value && typeof value === 'object' && 'key' in value ? value.key : value;
};

test.describe('Bank statement line: order field and dropdown', () => {
  test('the line shows the order field; its dropdown offers the waiting prepay order but not the paid one', async ({
    page,
  }) => {
    allure.epic('E0220: Financial');
    allure.tag('F00963: Bank Statement');
    allure.story('Sales order on the bank statement line');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Customer P has two prepay orders waiting for payment: PAID and OPEN. A completed payment of P carries PAID.
2. A bank statement has a line for P.
3. The line (grid column and advanced-edit form) shows the Auftrag field.
4. The Auftrag dropdown of the line offers OPEN and does not offer PAID.
    `);

    test.setTimeout(600000);

    let masterdata;
    await test.step('Master data: customer P with two prepay orders, login', async () => {
      masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language: 'de_DE', firstname: 'BankLine', lastname: 'E2E' } },
          bpartners: { P: { isCustomer: true, isSoPriceList: true, orderDocSubType: 'PR' } },
          products: { PROD: { name: 'PROD', type: 'Item', prices: [{ price: 51.9, currencyCode: 'EUR' }] } },
        },
      });
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();
    });

    const partnerId = Number(masterdata.bpartners.P.id);

    // The customer's default order document type is the prepay one (orderDocSubType PR), so completing an order
    // in the sales order window (as the order clerk does) leaves it waiting for payment.
    const createPrepayOrder = async (qty) => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.P.bpartnerCode);
      await SalesOrderPage.addOrderLine({ product: masterdata.products.PROD.productName, quantity: qty, recordId });
      await SalesOrderPage.complete();
      return Number(recordId);
    };
    let paidOrderId;
    let openOrderId;
    await test.step('Two prepay orders of P are completed', async () => {
      paidOrderId = await createPrepayOrder(2);
      openOrderId = await createPrepayOrder(3);
    });

    await test.step('Both orders wait for payment (DocStatus WP)', async () => {
      for (const id of [paidOrderId, openOrderId]) {
        const order = firstDocument(await rest(page, 'GET', `window/143/${id}`));
        expect(fieldValue(order, 'DocStatus'), `order ${id} DocStatus`).toBe('WP');
      }
    });

    const paidOrderNo = await test.step('Completed payment of P carries order PAID', async () => {
      const paymentId = (await patchDocument(page, PAYMENT_WINDOW_ID, 'NEW', [])).id;
      await patchDocument(page, PAYMENT_WINDOW_ID, paymentId, [
        replace('C_DocType_ID', DOCTYPE_AR_RECEIPT),
        replace('C_BP_BankAccount_ID', await firstDropdownValue(page, PAYMENT_WINDOW_ID, paymentId, 'C_BP_BankAccount_ID')),
        replace('C_BPartner_ID', partnerId),
        replace('C_Order_ID', paidOrderId),
        replace('PayAmt', 100),
      ]);
      const payment = await patchDocument(page, PAYMENT_WINDOW_ID, paymentId, [replace('DocAction', 'CO')]);
      expect(fieldValue(payment, 'DocStatus'), 'payment DocStatus').toBe('CO');
      const order = firstDocument(await rest(page, 'GET', `window/143/${paidOrderId}`));
      return String(fieldValue(order, 'DocumentNo'));
    });
    const openOrderNo = String(
      fieldValue(firstDocument(await rest(page, 'GET', `window/143/${openOrderId}`)), 'DocumentNo')
    );

    let statementId;
    await test.step('Bank statement with a line for P', async () => {
      statementId = (await patchDocument(page, BANK_STATEMENT_WINDOW_ID, 'NEW', [])).id;
      const header = await patchDocument(page, BANK_STATEMENT_WINDOW_ID, statementId, [
        replace('Name', `E2E order field ${Date.now()}`),
        replace('StatementDate', new Date().toISOString().substring(0, 10) + 'T00:00:00.000Z'),
        replace('C_BP_BankAccount_ID', await firstDropdownValue(page, BANK_STATEMENT_WINDOW_ID, statementId, 'C_BP_BankAccount_ID')),
      ]);
      expect(header.validStatus && header.validStatus.valid, `bank statement header is valid: ${JSON.stringify(header.validStatus)}`).toBe(true);
      const created = await rest(
        page,
        'PATCH',
        `window/${BANK_STATEMENT_WINDOW_ID}/${statementId}/${BANK_STATEMENT_LINE_TAB_ID}/NEW`,
        [replace('C_BPartner_ID', partnerId)]
      );
      expect(firstDocument(created), 'line created').toBeTruthy();
    });

    await BankStatementPage.goto(statementId);
    await BankStatementPage.expectOrderColumnInLineGrid();
    await BankStatementPage.openFirstLineAdvancedEditWithOrderField();
    const offered = await BankStatementPage.openOrderDropdownAndGetOptions();

    await test.step('The dropdown offers the waiting order and not the paid one', async () => {
      expect(
        offered.some((text) => text.includes(openOrderNo)),
        `order ${openOrderNo} (waiting) is offered, offered: ${JSON.stringify(offered)}`
      ).toBe(true);
      expect(
        offered.some((text) => text.includes(paidOrderNo)),
        `order ${paidOrderNo} (paid) is not offered, offered: ${JSON.stringify(offered)}`
      ).toBe(false);
    });
  });
});
