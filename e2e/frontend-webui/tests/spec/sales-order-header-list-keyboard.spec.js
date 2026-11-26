import { expect } from "@playwright/test";
import { test } from "../../playwright.config";
import { allure } from "allure-playwright";
import { Backend } from "../utils/Backend";
import { LoginPage } from "../utils/pages/LoginPage";
import { DashboardPage } from "../utils/pages/DashboardPage";
import { SalesOrderPage } from "../utils/pages/SalesOrderPage";
import { SLOW_ACTION_TIMEOUT } from "../utils/common";
import { SALES_ORDER_WINDOW_ID } from "../utils/WindowIds";
import { getRecordData } from "../utils/WebAPIValidation";
import {
  installDocumentEventRecorder,
  armHeaderReload,
  awaitHeaderReload,
  pickCustomerByKeyboard,
  expectFocusOn,
  expectFocusHeld,
  describeActiveElement,
} from "../utils/QuickInputFocus";

/**
 * Header keyboard flow requirement test (reproduction of REQUIREMENTS section 3 and TC7): in the sales order
 * header, after the partner Enter the cursor moves on to the address list and, after Enter there, to the contact
 * list; Enter takes the highlighted entry, so partner, address and contact are all filled. Red before the fix.
 */

const ADDRESS_LIST = "#lookup_C_BPartner_Location_ID .input-dropdown-container";
const CONTACT_LIST = "#lookup_AD_User_ID .input-dropdown-container";
const HIGHLIGHTED_OPTION =
  ".input-dropdown-list .input-dropdown-list-option-key-on";

async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: "SOHLK", lastname: "Test" } },
      bpartners: {
        MULTI: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: "Customer",
          locations: {
            LOC1: {
              city: "Bonn",
              postal: "53111",
              address1: "A 1",
              countryCode: "DE",
            },
            LOC2: {
              city: "Kiel",
              postal: "24103",
              address1: "B 2",
              countryCode: "DE",
            },
          },
          contacts: {
            C1: { firstName: "Anna", lastName: "One" },
            C2: { firstName: "Ben", lastName: "Two" },
          },
        },
      },
    },
  });
}

/**
 * A keyboard user presses Enter on an open list with a highlighted entry; a list that has the cursor but is not
 * open yet ignores Enter. So the cursor check is followed by the open list check.
 */
async function expectListOpen(page, list, description) {
  await expect(page.locator(list), `${description}: list is open`).toHaveClass(
    /\bopened\b/,
    { timeout: SLOW_ACTION_TIMEOUT },
  );
  await expect(
    page.locator(HIGHLIGHTED_OPTION).first(),
    `${description}: an entry is highlighted`,
  ).toBeVisible({
    timeout: SLOW_ACTION_TIMEOUT,
  });
}

/** Alt+N, partner by keyboard, address list reached, Enter, contact list reached. Returns the record id. */
async function advanceToContactList(page, masterdata) {
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  // Setup, not the bug: the new order opens with the cursor in the partner. A miss here is not reported as a focus failure.
  await expect
    .poll(
      () =>
        page.evaluate(
          () => !!document.activeElement?.closest("#lookup_C_BPartner_ID"),
        ),
      {
        timeout: SLOW_ACTION_TIMEOUT,
        message: "setup: new order opened with the cursor in the partner input",
      },
    )
    .toBe(true);
  const recordId = await pickCustomerByKeyboard({
    bpartnerCode: masterdata.bpartners.MULTI.bpartnerCode,
  });
  await expectFocusOn(
    page.locator(ADDRESS_LIST),
    "after partner Enter: cursor in the address list",
  );
  await expectListOpen(page, ADDRESS_LIST, "after partner Enter");
  await page.keyboard.press("Enter");
  await expectFocusOn(
    page.locator(CONTACT_LIST),
    "after address Enter: cursor in the contact list",
  );
  await expectListOpen(page, CONTACT_LIST, "after address Enter");
  return recordId;
}

const testCases = [
  { language: "en_US", label: "English" },
  { language: "de_DE", label: "German" },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Sales order header keyboard flow (${label})`, () => {
    const tag = (story) => {
      allure.epic("E0100: Sales");
      allure.tag("F00100: Sales Order Window");
      allure.tag("F00100");
      allure.tag("F00101: Sales Order Quick Entry");
      allure.tag("F00101");
      allure.tag("F50000: Frontend WebUI");
      allure.tag("F50000");
      allure.story(story);
      allure.severity("critical");
      allure.parameter("Language", language);
      allure.tag(language);
    };

    const start = async (page) => {
      const recorder = installDocumentEventRecorder(page);
      const masterdata = await createMasterdata(language);
      allure.attachment(
        "Test Data",
        JSON.stringify(masterdata, null, 2),
        "application/json",
      );
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      await Backend.resetWebApiCaches();
      return { recorder, masterdata };
    };

    test(`header keyboard flow: partner, address and contact by Enter (${label})`, async ({
      page,
    }) => {
      tag("Header keyboard flow: partner, address and contact by Enter");
      test.setTimeout(240000);
      const { masterdata } = await start(page);

      const recordId = await advanceToContactList(page, masterdata);
      await page.keyboard.press("Enter");

      await expect
        .poll(
          async () => {
            const f = (await getRecordData(SALES_ORDER_WINDOW_ID, recordId))
              .fieldsByName;
            return [
              f?.C_BPartner_ID?.value?.key,
              f?.C_BPartner_Location_ID?.value?.key,
              f?.AD_User_ID?.value?.key,
            ].map((k) => (k === undefined || k === null ? "unset" : "set"));
          },
          {
            timeout: SLOW_ACTION_TIMEOUT,
            message:
              "C_BPartner_ID, C_BPartner_Location_ID, AD_User_ID are all set",
          },
        )
        .toEqual(["set", "set", "set"]);
    });

    test(`header keyboard flow: cursor after the contact Enter (${label})`, async ({
      page,
    }) => {
      tag(
        "Header keyboard flow: the cursor stays where the field sends it after the contact Enter",
      );
      test.setTimeout(240000);
      const { recorder, masterdata } = await start(page);

      const recordId = await advanceToContactList(page, masterdata);
      const mark = armHeaderReload(recorder);
      await page.keyboard.press("Enter");

      // The reload the contact PATCH triggers; no other field is touched (typing would move the cursor itself).
      await awaitHeaderReload(page, recorder, mark, {
        windowId: SALES_ORDER_WINDOW_ID,
        recordId,
        step: "contact Enter",
      });
      const described = await describeActiveElement(page);
      // Mark the element in the DOM so the hold check can target exactly that element.
      await page.evaluate(
        () =>
          document.activeElement &&
          document.activeElement.setAttribute("data-header-active", "A"),
      );
      allure.attachment(
        "Active element after contact reload",
        described,
        "text/plain",
      );
      console.log(
        `Active element after contact reload (${language}): ${described}`,
      );

      // The element is wherever Lookup.focusNextFormField sent the cursor; the assertion is only that it stays there.
      await expectFocusHeld(
        page.locator('[data-header-active="A"]').first(),
        1000,
        `contact Enter: cursor left ${described}`,
      );
    });
  });
});
