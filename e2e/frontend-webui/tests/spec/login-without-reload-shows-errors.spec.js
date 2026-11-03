import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * The web UI shows a server error as a notification only while the user is logged in. The app
 * registers its error handler once, at boot - so after a login WITHOUT a page reload (the app booted on
 * the login page) it used to keep seeing "logged out" and swallowed every server error.
 *
 * The error used here: completing a sales order that has no lines. The server refuses with a
 * user-friendly message, which the web UI must show as a notification.
 */

// AD_Message 'NoLines' (AdempiereException.MSG_NoLines), the refusal to complete an order without lines
const NO_LINES_MESSAGE = {
  en_US: 'No Document Lines found',
  de_DE: 'Keine Belegpositionen gefunden',
};

const createFixture = async (language) =>
  Backend.createMasterdata({
    request: {
      login: { user: { language } },
      bpartners: {
        CUSTOMER: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
      },
    },
  });

// the web UI's own notification (not a toast) of a server error
const errorNotification = (message) =>
  getPage().locator('.notification-handler .notification-item.error').filter({ hasText: message });

// The notification closes by itself after 5 seconds, which the completion's own waiting can outlast:
// start watching for it before the completion is triggered.
const expectErrorNotificationAfterCompletingAnOrderWithoutLines = async (title, message) =>
  await test.step(title, async () => {
    await Promise.all([
      expect(errorNotification(message).first()).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT }),
      SalesOrderPage.complete(),
    ]);
  });

test.describe('Server errors after a login without page reload', () => {
  test.beforeEach(() => {
    allure.epic('E0294: Frontend WebUI');
    allure.tag('F50020: Error handling');
    allure.tag('F50020');
    allure.story('Server errors are shown after login without page reload');
    allure.severity('normal');
  });

  ['en_US', 'de_DE'].forEach((language) => {
    // `page` is destructured but not referenced: the custom fixture in playwright.config.js publishes it
    // as `global.currentPage` for the page objects, and only runs for a test that requests `page`.
    // eslint-disable-next-line no-unused-vars
    test(`a server error is shown after logging in through the form, also after logging out and in again (${language})`, async ({ page }) => {
      allure.parameter('Language', language);
      allure.tag(language);
      const message = NO_LINES_MESSAGE[language];
      const masterdata = await createFixture(language);
      const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;

      // fresh browser context: no stored login, so the app boots on the login page
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);

      // stay in the running SPA: no page reload after the login
      await SalesOrderPage.gotoWithoutReload();
      await SalesOrderPage.clickNew();
      const recordId = await SalesOrderPage.selectCustomer(customer);

      await expectErrorNotificationAfterCompletingAnOrderWithoutLines('Completing the order without lines shows the error', message);

      // the first notification is gone, so the next one can only come from the second completion
      await expect(errorNotification(message)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });

      await LoginPage.logout();
      await LoginPage.login(masterdata.login.user);
      await SalesOrderPage.gotoWithoutReload(recordId);

      await expectErrorNotificationAfterCompletingAnOrderWithoutLines('After logout and login again the error is shown again', message);
    });
  });
});
