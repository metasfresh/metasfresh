import { expect } from "@playwright/test";
import { test } from "../../playwright.config";
import { allure } from "allure-playwright";
import { Backend } from "../utils/Backend";
import { LoginPage } from "../utils/pages/LoginPage";
import { DashboardPage } from "../utils/pages/DashboardPage";
import {
  FRONTEND_BASE_URL,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from "../utils/common";
import {
  waitForRecordSaved,
  getValidationStatus,
  getFieldData,
} from "../utils/WebAPIValidation";

// HU Label Configuration window ("HU-Labels Konfiguration") = M_HU_Label_Config
const HU_LABEL_CONFIG_WINDOW_ID = 541647;

// A label process that is selectable as LabelReport_Process_ID (AD_Process 540412).
// LabelReport_Process_ID is the only mandatory field on M_HU_Label_Config without a
// default, so it is the one field the operator must pick to create a record.
const LABEL_PROCESS_NAME = "LU SSCC-Barcode-Etikett";

test.describe("HU Label Configuration window — create a new record", () => {
  //
  // Regression guard for the AutoPrintCopies NOT-NULL defect: M_HU_Label_Config.AutoPrintCopies
  // is a mandatory column whose field is hidden by DisplayLogic (@IsAutoPrint/N@=Y) — so on any
  // config where IsAutoPrint stays N (the default), the operator cannot fill it and the record
  // failed to save with a NOT-NULL violation. The fix gives the column a default of 1, so a
  // record now saves with only LabelReport_Process_ID set. This test asserts exactly that: a new
  // record, with IsAutoPrint left at its N default (AutoPrintCopies field stays hidden), becomes
  // valid and persists.
  //
  test("a new record saves with only the label process set (IsAutoPrint stays N)", async ({
    page,
  }) => {
    allure.epic("E0370: Intralogistic (HUs)");
    allure.story("HU Label Configuration — create record (AutoPrintCopies default)");
    allure.tag("F5210: HU Label Configuration & Printing");
    allure.tag("F5210");
    allure.severity("critical");

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: "de_DE" } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Open a NEW record directly in the detail form.
    await page.goto(`${FRONTEND_BASE_URL}/window/${HU_LABEL_CONFIG_WINDOW_ID}/NEW`);

    // The frontend creates the document and the URL resolves from /NEW to /window/<id>/<recordId>.
    await page.waitForURL(
      new RegExp(`/window/${HU_LABEL_CONFIG_WINDOW_ID}/\\d+`),
      { timeout: VERY_SLOW_ACTION_TIMEOUT }
    );
    const recordId = page.url().split("/").pop().split("?")[0];
    console.log(`[INFO] new M_HU_Label_Config record id = ${recordId}`);

    // Set the only mandatory-without-default field: the label report process (a lookup).
    const processInput = page.locator(
      "#lookup_LabelReport_Process_ID input.input-field"
    );
    await processInput.waitFor({ state: "visible", timeout: SLOW_ACTION_TIMEOUT });
    await processInput.click();
    await page.waitForTimeout(300);
    await processInput.fill(LABEL_PROCESS_NAME);
    await page.waitForTimeout(500);
    // Dropdown options carry no data-testid; match the (de_DE-pinned, unique) process name.
    // Substring match — the rendered option wraps the name with extra whitespace/markup, so
    // an exact match does not apply; .first() guards against an unexpected second match.
    await page
      .locator(".input-dropdown-list-option")
      .getByText(LABEL_PROCESS_NAME)
      .first()
      .click();
    await page
      .locator(".input-dropdown-list")
      .waitFor({ state: "detached", timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});

    // Blur to trigger the save.
    await page.keyboard.press("Tab");
    await page
      .locator(".rotating, .indicator-pending")
      .waitFor({ state: "detached", timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});

    // The record persists only when validStatus.valid === true — which requires the mandatory
    // AutoPrintCopies to carry a value. With the fix it gets a backend-side value even though its
    // field is hidden; without the fix this save never completes (NOT-NULL violation / invalid
    // record).
    await waitForRecordSaved(HU_LABEL_CONFIG_WINDOW_ID, recordId, {
      maxRetries: 20,
      retryDelayMs: 1000,
    });

    const validation = await getValidationStatus(HU_LABEL_CONFIG_WINDOW_ID, recordId);
    expect(
      validation.valid,
      `record should be valid; missing: ${JSON.stringify(validation.missingFields)}`
    ).toBe(true);

    // The WebAPI omits display-hidden fields from fieldsByName, so validity + persisted save are
    // the observable regression guard here: if AutoPrintCopies had not received a backend-side
    // value, the record would still be invalid and unsaved at this point.

    // And IsAutoPrint stayed at its N default — i.e. the AutoPrintCopies field was never shown,
    // reproducing the exact configuration that previously could not be saved.
    const isAutoPrint = await getFieldData(
      HU_LABEL_CONFIG_WINDOW_ID,
      recordId,
      "IsAutoPrint"
    );
    expect(isAutoPrint.value === false || isAutoPrint.value === "N").toBeTruthy();

    console.log(`[PASS] M_HU_Label_Config record ${recordId} created and saved`);
  });
});
