import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { BusinessPartnerPage } from '../utils/pages/BusinessPartnerPage';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { TextWidget } from '../utils/widgets/TextWidget';
import { NumericWidget } from '../utils/widgets/NumericWidget';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, getPage } from '../utils/common';
import { assertRecordIsValid, getRecordData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * VAT-ID online-check status on the core Business Partner window (AD_Window_ID 123).
 *
 * The check writes three denormalised columns onto the record whose VAT-ID was
 * checked — `VATaxIDStatus`, `VATaxIDCheckedAt`, `VATaxID_CheckLog_ID` — on
 * `C_BPartner` (tab 220) and on `C_BPartner_Location` (tab 222) alike. All three
 * are placed in `VATaxID`'s own AD_UI_ElementGroup, directly after it, and are
 * `AD_Field.IsReadOnly='Y'`: they are outcomes of a check, never user input.
 *
 * What this spec proves, and why each part is here:
 *
 *  1. STRUCTURAL ADJACENCY, not mere co-presence. "The status is shown next to
 *     the VAT-ID" is a statement about layout, so it is asserted as one: the
 *     status field must sit in the SAME rendered element-group container as
 *     `VATaxID` and be the element line IMMEDIATELY after it (and the timestamp
 *     and check-log reference the two after that). An assertion that merely
 *     found both fields somewhere on the page would still pass if a later
 *     migration moved the status into an unrelated group at the bottom of the
 *     window — which is exactly the regression worth catching.
 *  2. BOTH GRAINS. Partner header AND address. On the address the pairing lives
 *     in the location's own detail form (see "Where the address VAT-ID actually
 *     renders" below), which is the only place the location's `VATaxID` is shown
 *     at all — so the status is exactly as reachable as the VAT-ID it annotates.
 *  3. READ-ONLY, asserted with a discriminating signal. `VATaxIDStatus` is a
 *     List widget, and `RawList.js` renders its `<input>` with `readOnly` and
 *     `tabIndex={-1}` UNCONDITIONALLY (selection happens through the dropdown,
 *     never by typing). The generic readonly-attribute check therefore returns
 *     `true` for an editable dropdown too and proves nothing here. The one DOM
 *     signal that tracks the field's actual read-only flag 1:1 is the
 *     `.input-disabled` class on the widget's `div.input-dropdown-container`
 *     (`RawList.js` — the same flag that nulls the container's `onClick` and
 *     hides the clear icon). To show that this signal really discriminates
 *     rather than being always-present, the same assertion is run as a NEGATIVE
 *     CONTROL against `AD_Language` — another List widget on the same rendered
 *     record, editable (`readonly: false` in the document JSON) — which must NOT
 *     carry the class.
 *  4. THE STATUS FOLLOWS A REAL CHECK. The second test does not fabricate status
 *     values (they are read-only, so no widget can set them, and hand-writing
 *     the columns would prove rendering while proving nothing about the
 *     feature). It configures the organisation's `VATaxID_Config` through the
 *     real configuration window, points `RestApiBaseURL` at the WireMock stub
 *     server the local/CI stack already runs, and then types VAT-IDs into the
 *     real `VATaxID` widget. Each save's after-commit check calls the stub and
 *     writes the status back — the same path a user's save takes in production.
 *  5. AT A GLANCE. Four statuses are driven through one record — NotChecked (the
 *     column default, before any check), Valid, Invalid and NotSupported — and
 *     the rendered captions must all differ from one another. Comparing the
 *     rendered captions against EACH OTHER (never against a literal) is what
 *     keeps this language-independent while still asserting what the user sees;
 *     the identity of each status is pinned separately on the language-invariant
 *     `AD_Ref_List.Value` read from the WebAPI.
 *
 * Where the address VAT-ID actually renders: tab 222 is an included tab whose
 * grid columns are exactly its `AD_UI_Element.IsDisplayedGrid='Y'` set, and
 * `VATaxID` is not one of them — verified live against the tab's own layout
 * endpoint, whose `elements` array holds those 13 columns and no VAT-ID field.
 * The location's `VATaxID` is reached the way a user reaches it: select the
 * address row and open its advanced edit from the ROW's context menu, which
 * renders that tab's single-row layout — where `VATaxID` is followed directly by
 * the three status fields. Note this is NOT the `Alt+E` chord, which belongs to
 * the partner header and opens a look-alike form; see
 * `openAddressRowAdvancedEdit` for why that distinction is load-bearing here.
 *
 * KNOWN GAP — the status is NOT reachable in any grid, and this spec asserts
 * nothing about grid columns. `AD_Field.IsDisplayedGrid='Y'` is set for
 * `VATaxIDStatus` on tabs 220, 222 and 540843, but the rendered grid is built
 * from `AD_UI_Element.IsDisplayedGrid`, which is `'N'` for all three (mirroring
 * `VATaxID`'s own element there). Verified live: window 123's list-view layout
 * returns exactly 9 columns — `Value, Name, Name2, Name3, IsActive, IsCompany,
 * C_BP_Group_ID, AD_Language, AD_Org_ID` — which is precisely the tab's
 * `AD_UI_Element.IsDisplayedGrid='Y'` set, while 46 of its `AD_Field` rows carry
 * that flag; neither `VATaxID` nor `VATaxIDStatus` appears. So a grid assertion
 * either way would be wrong to write here: asserting presence would fail, and
 * asserting absence would freeze the current state as intended. This gap is
 * reported for a decision rather than encoded in a test.
 *
 * Language independence: in the two status tests every identity assertion is on a
 * DB ColumnName (`.form-field-<Column>`), a structural class, a window/tab id, or
 * an `AD_Ref_List.Value` read from the WebAPI — never a caption or button label;
 * the only captions they read are compared to one another, never to a literal.
 * See e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent". The one
 * exception is the last, explicitly language-parameterised test, where the
 * translated caption IS the subject under test and is asserted per `AD_Language`
 * in both languages — see its own comment.
 */

const BPARTNER_WINDOW_ID = 123;
const LOCATION_TAB_ID = 'AD_Tab-222';
const VATID_CONFIG_WINDOW_ID = 542182;

/**
 * VAT-ID values chosen so that each drives a DIFFERENT outcome through the real
 * check, and every one of them passes the offline format check first (a value the
 * format check rejects is refused at save time and never reaches the online
 * service, so it could not be used here at all).
 *
 * All three are structurally valid AND check-digit valid per `EUVatIdValidator`
 * (`DE` = mod-11,10 over the 9 digits; `CHE` = the Swiss UID algorithm with a
 * mandatory VAT marker). `CHE…MWST` additionally has a prefix the online service
 * does not cover, which is a definite answer — `NotSupported`, decided by the
 * client without sending a request — and therefore needs no stub.
 */
const VATID_VALID = 'DE136695976';
const VATID_INVALID = 'DE111111117';
const VATID_NOT_SUPPORTED = 'CHE100155212MWST';

/**
 * Base URL of the WireMock stub server, as seen BY THE BACKEND (it is the
 * app/webapi JVM that calls it, not the browser). Same resolution the DHL
 * shipper stubs use in `mobile-webui/tests/spec/picking/picking.spec.js`: the
 * CI compose sets `WIREMOCK_BASE_URL=http://wiremock:8080` (docker-internal),
 * and a local run falls back to the infra stack's mapped host port.
 */
const WIREMOCK_BASE_URL = process.env.WIREMOCK_BASE_URL || 'http://localhost:18080';

/**
 * Sub-path under WireMock that stands in for the VIES REST API. Namespaced
 * rather than mounted at the root so these stubs can never collide with the
 * other stub families the same server hosts (DHL's OAuth + order endpoints).
 * The client appends `/check-vat-number` to whatever `RestApiBaseURL` holds.
 */
const VIES_STUB_PATH = '/vies';

/**
 * Teardown state, deliberately module-scoped rather than local to a test.
 *
 * `VATaxID_Config` is a SHARED, one-active-row-per-organisation record, and this
 * spec points it at the stub server and switches the online check ON. If that is
 * not undone, the modified configuration escapes into every later spec and every
 * later run against the same stack — a leak this branch has already paid for once
 * (a cucumber failure root-caused to `IsVIESCheckEnabled='Y'` surviving from one
 * feature into another). A trailing call at the end of the test body is NOT
 * enough: a test that times out never reaches it, and neither does one that
 * throws before the setup function has returned its state. So the state is
 * published to module scope the moment anything mutable has been touched, and
 * `test.afterEach` — which the runner guarantees — is what consumes it.
 */
let pendingConfigTeardown = null;
let pendingStubIds = [];

/**
 * Register one stub with WireMock and return its id so it can be removed again.
 * Stubs are registered through the admin API rather than committed as mapping
 * files because these two response bodies are fixtures OF THIS SPEC: registering
 * them here makes the run fail loudly at setup if the stub server is not
 * reachable, instead of silently exercising an unstubbed endpoint, and it cannot
 * be defeated by a stale mappings directory in an already-running container.
 */
async function registerWireMockStub(stub) {
  const page = getPage();
  const response = await page.request.post(`${WIREMOCK_BASE_URL}/__admin/mappings`, {
    data: stub,
    headers: { 'Content-Type': 'application/json' },
  });
  if (!response.ok()) {
    throw new Error(
      `Failed to register WireMock stub at ${WIREMOCK_BASE_URL}: HTTP ${response.status()} ${response.statusText()}`
    );
  }
  const body = await response.json();
  // Recorded here, not by the caller: a throw between two registrations must still
  // leave the first one removable by the guaranteed teardown.
  pendingStubIds.push(body.id);
  return body.id;
}

async function removeWireMockStub(stubId) {
  if (!stubId) return;
  try {
    await getPage().request.delete(`${WIREMOCK_BASE_URL}/__admin/mappings/${stubId}`);
  } catch (e) {
    // best-effort cleanup
  }
}

/**
 * A stub answering the online service's `POST /check-vat-number` for exactly one
 * VAT number, with the verdict `valid` carries. Matched on the request BODY (the
 * VAT number is not in the URL), so the two verdicts below can share one path.
 * The body shape mirrors the real service's documented response, because the
 * check log stores it verbatim as the evidence of what was answered.
 */
function viesCheckVatNumberStub({ countryCode, vatNumber, valid }) {
  return {
    priority: 5,
    request: {
      method: 'POST',
      url: `${VIES_STUB_PATH}/check-vat-number`,
      bodyPatterns: [{ matchesJsonPath: `$[?(@.vatNumber == '${vatNumber}')]` }],
    },
    response: {
      status: 200,
      headers: { 'Content-Type': 'application/json' },
      jsonBody: {
        countryCode,
        vatNumber,
        valid,
        requestIdentifier: `E2E-${valid ? 'VALID' : 'INVALID'}-${vatNumber}`,
        name: valid ? 'E2E TRADER' : '---',
        address: valid ? 'TESTSTR 1, 10115 BERLIN' : '---',
        traderNameMatch: 'NOT_PROCESSED',
        traderAddressMatch: 'NOT_PROCESSED',
      },
    },
  };
}

/**
 * The rendered element-group container of `fieldName`, i.e. the DOM node that an
 * AD_UI_ElementGroup becomes (`ElementGroup.js`: `div.panel.panel-spaced…`).
 * Every AD_UI_Element inside it renders as one `div.elements-line` child, in
 * `AD_UI_Element.SeqNo` order (`LayoutFactory.layoutSingleRow_ElementLines` /
 * `ADWindowDAO`, which orders by SeqNo), each holding one
 * `div.form-group…form-field-<Column>`.
 */
function elementGroupContaining(scope, fieldName) {
  return scope
    .locator('div.panel.panel-spaced')
    .filter({ has: scope.page().locator(`.form-field-${fieldName}`) });
}

/**
 * The ordered list of `form-field-<Column>` class sets, one entry per element
 * line directly inside `groupLocator` — the rendered order of that element
 * group. Non-displayed elements are dropped by the layout factory and empty
 * lines by the renderer, so positions are compared RELATIVE to each other, never
 * against a raw SeqNo ordinal.
 */
async function readElementLineFieldOrder(groupLocator) {
  return await groupLocator.evaluate((groupEl) =>
    Array.from(groupEl.querySelectorAll(':scope > .elements-line')).map((line) =>
      // EVERY form-group on the line, not just the first. `ElementsLine.js` maps
      // over `elementsLineLayout.elements`, so one line CAN render several
      // elements and therefore several `.form-group`s. Keeping only the first
      // would silently drop or misplace a field if a future migration ever packed
      // two onto one line — exactly the change this adjacency check exists to
      // catch. (Live layouts today: 0 of 35 lines on tab 220 and 0 of 34 on tab
      // 222 carry more than one element, so this costs nothing and removes the
      // dependency on that staying true.)
      Array.from(line.querySelectorAll('.form-group'))
        .flatMap((formGroup) =>
          Array.from(formGroup.classList).filter((cssClass) => cssClass.startsWith('form-field-'))
        )
        .join(' ')
    )
  );
}

/**
 * Assert that, inside one rendered element group, `VATaxID` is followed
 * immediately by the three status fields in that order.
 */
async function expectStatusFieldsDirectlyAfterVatId(scope, grainLabel) {
  const group = elementGroupContaining(scope, 'VATaxID');
  await expect(group, `${grainLabel}: exactly one element group must hold VATaxID`).toHaveCount(1);

  const lineFields = await readElementLineFieldOrder(group);
  const indexOfField = (fieldName) =>
    lineFields.findIndex((classes) => classes.split(' ').includes(`form-field-${fieldName}`));

  const vatIdIndex = indexOfField('VATaxID');
  expect(vatIdIndex, `${grainLabel}: VATaxID must render as an element line of its group`).toBeGreaterThanOrEqual(0);

  const expectedFollowers = ['VATaxIDStatus', 'VATaxIDCheckedAt', 'VATaxID_CheckLog_ID'];
  expectedFollowers.forEach((fieldName, offset) => {
    expect(
      indexOfField(fieldName),
      `${grainLabel}: ${fieldName} must be element line ${offset + 1} after VATaxID inside the SAME group ` +
        `(rendered order: ${JSON.stringify(lineFields)})`
    ).toBe(vatIdIndex + offset + 1);
  });

  console.log(`[PASS] ${grainLabel}: VATaxID is directly followed by ${expectedFollowers.join(', ')} in its own element group`);
}

/**
 * Assert `VATaxIDStatus` renders read-only, and that the signal used to say so
 * actually discriminates: the same check must come out FALSE for `AD_Language`,
 * an editable List widget on the same rendered record.
 */
async function expectStatusReadOnlyWithEditableControl(scope, grainLabel, { editableListField }) {
  const statusDropdown = scope.locator('.form-field-VATaxIDStatus div.input-dropdown-container');
  await expect(statusDropdown, `${grainLabel}: VATaxIDStatus must render a dropdown container`).toHaveCount(1);
  await expect(
    statusDropdown,
    `${grainLabel}: VATaxIDStatus must render read-only (input-disabled on its dropdown container)`
  ).toHaveClass(/input-disabled/);

  if (editableListField) {
    const editableDropdown = scope.locator(`.form-field-${editableListField} div.input-dropdown-container`);
    await expect(
      editableDropdown,
      `${grainLabel}: negative control ${editableListField} must render a dropdown container`
    ).toHaveCount(1);
    await expect(
      editableDropdown,
      `${grainLabel}: negative control ${editableListField} is editable, so it must NOT carry input-disabled ` +
        `— otherwise the class proves nothing about VATaxIDStatus`
    ).not.toHaveClass(/input-disabled/);
  }

  console.log(`[PASS] ${grainLabel}: VATaxIDStatus renders read-only${editableListField ? ` (control ${editableListField} editable)` : ''}`);
}

/**
 * Open the address row's own detail form and return its modal locator.
 *
 * The route matters, and the obvious one is wrong. Selecting the row and pressing
 * `Alt+E` opens the **partner header's** advanced edit, not the row's:
 * `GlobalContextShortcuts` claims that chord and calls `openModal` with no
 * `tabId`/`rowId`. The header form contains a `VATaxID` followed by the same three
 * status fields, so every assertion in this file passed against it while proving
 * nothing whatsoever about the address grain — caught only because the address
 * save then landed on `PATCH /window/123/<partnerId>?advanced=true` in the run's
 * trace instead of on the location. The row-level form is the one
 * `containers/Table.js#handleAdvancedEdit` opens (with `tabId`, `rowId` and
 * `isAdvanced`), reachable from the row's context menu — which is what this does.
 *
 * The menu item is located structurally, never by caption (its caption is
 * translated): among the context menu's items it is the only one carrying BOTH
 * the edit icon and a shortcut hint — the other items that carry a shortcut hint
 * carry a different icon. (The similarly-iconed "edit field" item cannot even
 * co-render here: `TableContextMenu.js` gates it on `mainTable` while gating
 * advanced-edit on `!mainTable`, so the two are mutually exclusive.)
 *
 * The modal is then verified to be the LOCATION's form via `C_Location_ID`, a
 * column that exists on `C_BPartner_Location` and not on `C_BPartner`, so the
 * header form cannot satisfy it. That check is what turns "wrong form opened"
 * into an immediate, named failure instead of a puzzling timeout further down.
 */
async function openAddressRowAdvancedEdit(page) {
  await BusinessPartnerPage.clickTab(BusinessPartnerPage.TAB_IDS.LOCATION);

  // The partner was created with exactly one location, so asserting the count
  // first turns `.first()` from a guess into the only possible choice — and the
  // context-menu item itself is gated on exactly one selected row.
  const addressRows = page.locator('.tabs-wrapper table tbody tr');
  await expect(addressRows, 'The partner must have exactly one address row').toHaveCount(1, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  const addressRow = addressRows.first();
  await addressRow.click();

  await addressRow.locator('td[data-cy^="cell-"]').first().click({ button: 'right' });
  const contextMenu = page.locator('.context-menu.context-menu-open');
  await contextMenu.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  const advancedEditItem = contextMenu
    .locator('.context-menu-item')
    .filter({ has: page.locator('i.meta-icon-edit') })
    .filter({ has: page.locator('span.tooltip-inline') });
  await expect(
    advancedEditItem,
    "The address row's context menu must offer exactly one advanced-edit item (edit icon + shortcut hint)"
  ).toHaveCount(1);
  await advancedEditItem.click();

  const modal = page.locator('.panel-modal-content');
  await modal.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await expect(
    modal.locator('.form-field-C_Location_ID'),
    "The opened detail form must be the ADDRESS's (C_Location_ID is a C_BPartner_Location column, absent from the partner header form)"
  ).toHaveCount(1);
  await modal.locator('.form-field-VATaxID').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  return modal;
}

/**
 * The language-invariant key of a List or Lookup field, plus its rendered caption.
 * Both widget types serve `{ key, caption }`; a plain scalar is returned as the key.
 */
function readFieldKeyAndCaption(recordData, fieldName) {
  const field = recordData.fieldsByName?.[fieldName];
  const value = field?.value;
  if (value === null || value === undefined) {
    return { key: null, caption: null };
  }
  return typeof value === 'object' ? { key: value.key, caption: value.caption } : { key: value, caption: null };
}

/** Read the header document (C_BPartner) of window 123. */
async function getPartnerRecordData(bpartnerId) {
  return await getRecordData(BPARTNER_WINDOW_ID, bpartnerId);
}

/**
 * Read a row of an included tab. `getRecordData` only addresses header
 * documents, while the location is a child row, whose document lives at
 * `/window/{windowId}/{documentId}/{tabId}/{rowId}` — the same shape the WebUI
 * itself uses for a child row.
 */
async function getLocationRecordData(bpartnerId, bpartnerLocationId) {
  const response = await getPage().request.get(
    `${WEBAPI_BASE_URL}/window/${BPARTNER_WINDOW_ID}/${bpartnerId}/${LOCATION_TAB_ID}/${bpartnerLocationId}`,
    { headers: { 'Content-Type': 'application/json' } }
  );
  if (!response.ok()) {
    throw new Error(
      `Failed to read location ${bpartnerLocationId} of partner ${bpartnerId}: HTTP ${response.status()} ${response.statusText()}`
    );
  }
  const body = await response.json();
  return Array.isArray(body) ? body[0] : body;
}

/**
 * Poll until `VATaxIDStatus` reaches `expectedKey`, and return its rendered
 * caption alongside.
 *
 * Polling — rather than a fixed wait — because the check is scheduled to run
 * AFTER the save's transaction commits (that is what keeps a slow or dead
 * service from ever failing a user's save), so the status is written a moment
 * after the save response the UI already got. The read goes through the WebAPI,
 * which is also how the caption is obtained language-invariantly next to its
 * key.
 */
async function waitForVatIdStatus({ readRecordData, expectedKey, label, timeout = VERY_SLOW_ACTION_TIMEOUT }) {
  const page = getPage();
  const deadline = Date.now() + timeout;
  let observed = { key: null, caption: null };

  while (Date.now() < deadline) {
    observed = readFieldKeyAndCaption(await readRecordData(), 'VATaxIDStatus');
    if (observed.key === expectedKey) {
      console.log(`[PASS] ${label}: VATaxIDStatus is "${expectedKey}" (rendered caption: "${observed.caption}")`);
      return observed;
    }
    await page.waitForTimeout(1000);
  }

  throw new Error(
    `${label}: VATaxIDStatus never reached "${expectedKey}" within ${timeout}ms (last observed: "${observed.key}")`
  );
}

/**
 * Type `vatIdValue` into a `VATaxID` widget and return only once THE RECORD
 * ITSELF holds that value.
 *
 * Why the postcondition and not the save event. The obvious form — arm
 * `page.waitForResponse` on the field's `PATCH`, then fill and blur — was tried
 * first and is genuinely unreliable here: the widget paints as soon as the
 * LAYOUT arrives, while the document's DATA lands a moment later and re-renders
 * the input from the server value. A fill landing inside that window is
 * overwritten before the blur, so the widget sees no change, **no PATCH is sent
 * at all**, and the step waits for an event that can never arrive. Verified from
 * the run's own trace: a failing attempt contained six `PATCH`es to the
 * configuration window and not one to the partner. Gating on "the widget shows
 * the persisted value" does not fix it either — on the first, legitimate save
 * the persisted value is the empty string, so that gate is satisfied by the very
 * pre-hydration state it is supposed to exclude.
 *
 * Reading the record back instead asserts the outcome the step exists to
 * produce, and it is a STRICTLY STRONGER grain discriminator than the PATCH URL
 * was: `readRecordData` addresses one specific document (the partner header, or
 * one specific `C_BPartner_Location` row), so a value that landed on the wrong
 * grain cannot satisfy it. The retype is bounded and only ever repeats a
 * no-op-so-far edit; a save the server actually REFUSED is not retried into a
 * timeout but raised immediately, from the record's own `validStatus`.
 */
async function setVatIdAndAwaitPersisted({ scope, vatIdValue, readRecordData, label }) {
  const page = getPage();
  const input = scope.locator('.form-field-VATaxID input').first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  // Refuse to "succeed" on a value that was already there. The success condition
  // below is "the record holds `vatIdValue`", which a record that ALREADY held it
  // satisfies without this step doing anything at all — the step would then pass
  // with the widget completely broken. Asserting the precondition is what makes
  // the postcondition evidence of THIS edit.
  const valueBefore = (await readRecordData()).fieldsByName?.VATaxID?.value ?? '';
  expect(
    valueBefore,
    `${label}: the record must NOT already hold "${vatIdValue}" — otherwise reaching that value proves nothing about this step`
  ).not.toBe(vatIdValue);

  const deadline = Date.now() + VERY_SLOW_ACTION_TIMEOUT;
  let attempts = 0;
  let persisted;

  // The widget only saves on blur when its own `isFocused` state is already
  // true, and it sets that state inside a `setTimeout` on focus
  // (`RawWidget.js#handleFocus` → `handleBlurWithParams`, which returns without
  // patching when `isFocused` is false). A fast focus-type-Tab sequence can
  // therefore blur BEFORE that timeout has run, and the edit is dropped in
  // silence — no PATCH, no error, the field just snaps back. That is what made
  // this step fail intermittently on the third value while the first two passed.
  // `div.input-body-container` carries a `focused` class rendered straight from
  // that same state, so waiting for the class is a deterministic gate on the
  // widget being genuinely ready to accept and save an edit.
  const inputBody = scope.locator('.form-field-VATaxID div.input-body-container');

  while (Date.now() < deadline) {
    attempts++;
    await input.click();

    const focusRegistered = await inputBody
      .evaluate((el) => el.classList.contains('focused'))
      .catch(() => false);
    if (!focusRegistered) {
      await page.waitForTimeout(250);
      continue;
    }

    await page.keyboard.press('Control+a');
    await input.fill(vatIdValue);

    // Blur only while the input still holds exactly what was typed: the widget
    // is re-rendered from the server document whenever fresh data arrives, and a
    // fill overwritten in that instant would blur the OLD text (again no change,
    // again no PATCH).
    const readyToSave = await inputBody
      .evaluate(
        (el, expected) => el.classList.contains('focused') && el.querySelector('input')?.value === expected,
        vatIdValue
      )
      .catch(() => false);
    if (!readyToSave) {
      await page.waitForTimeout(250);
      continue;
    }

    await WidgetCommon.triggerBlur();
    await WidgetCommon.waitForSaveComplete();

    const recordData = await readRecordData();
    if (recordData.validStatus && recordData.validStatus.valid === false) {
      throw new Error(
        `${label}: the server REFUSED VATaxID="${vatIdValue}": ${recordData.validStatus.reason}`
      );
    }

    persisted = recordData.fieldsByName?.VATaxID?.value ?? '';
    if (persisted === vatIdValue) {
      console.log(`[INFO] ${label}: VATaxID persisted as ${vatIdValue} (attempt ${attempts})`);
      return;
    }

    await page.waitForTimeout(1000);
  }

  throw new Error(
    `${label}: VATaxID never persisted as "${vatIdValue}" after ${attempts} attempt(s) (record still holds "${persisted}")`
  );
}

/**
 * The id of `orgId`'s existing active VAT-ID check configuration, or `null` when
 * that organisation has none.
 *
 * Asked of the BACKEND — the same view request the list view itself issues —
 * rather than counted in the rendered grid. A `locator.count()` on the list view
 * is a single non-retrying read that resolves as soon as the grid CONTAINER is
 * visible, which is before its rows have been fetched: it returned 0 with a row
 * present, the spec took the create branch, and the save was then refused by the
 * one-active-row-per-organisation index. Two HTTP responses give the answer with
 * no race at all, and hand back the record id directly, so the reuse branch needs
 * no grid interaction whatsoever.
 */
async function findExistingConfigRecordId(page, orgId) {
  const viewResponse = await page.request.post(`${WEBAPI_BASE_URL}/documentView/${VATID_CONFIG_WINDOW_ID}`, {
    data: { documentType: String(VATID_CONFIG_WINDOW_ID), viewType: 'grid', filters: [] },
    headers: { 'Content-Type': 'application/json' },
  });
  if (!viewResponse.ok()) {
    throw new Error(
      `Failed to open a view on window ${VATID_CONFIG_WINDOW_ID}: HTTP ${viewResponse.status()} ${viewResponse.statusText()}`
    );
  }
  const view = await viewResponse.json();
  if (!view.size) {
    return null;
  }

  const rowsResponse = await page.request.get(
    `${WEBAPI_BASE_URL}/documentView/${VATID_CONFIG_WINDOW_ID}/${view.viewId}?firstRow=0&pageLength=20`
  );
  if (!rowsResponse.ok()) {
    throw new Error(
      `Failed to read view ${view.viewId} of window ${VATID_CONFIG_WINDOW_ID}: HTTP ${rowsResponse.status()} ${rowsResponse.statusText()}`
    );
  }
  const rows = (await rowsResponse.json()).result ?? [];

  // Filter to the organisation whose configuration actually governs this run,
  // rather than assuming the view holds at most one row. The unique index is
  // per-organisation (`AD_Org_ID WHERE IsActive='Y'`), so a stack with several
  // organisations legitimately has several rows, and "the only row" would then be
  // both wrong and a spurious failure. `AD_Org_ID` is a grid column of this
  // window, so each row carries it, and its `key` is the language-invariant id.
  // Defend the premise instead of only asserting it in a comment. `AD_Org_ID` is
  // grid-displayed on this tab today (`AD_UI_Element.IsDisplayedGrid='Y'`,
  // SeqNoGrid 60, verified by query), so every row carries it. Were that ever to
  // change, `orgValue` would be `undefined` for every row, the filter would come
  // up empty, and the function would silently take the CREATE branch even with an
  // active row present — surfacing much later as "the newly created record is
  // invalid", nowhere near the cause. Fail here instead, naming the cause.
  // Both premises are guarded, symmetrically — `IsActive` is relied on just below
  // to skip deactivated history, and is grid-displayed today for the same reason
  // (`AD_UI_Element.IsDisplayedGrid='Y'`, SeqNoGrid 10, verified by query).
  for (const requiredField of ['AD_Org_ID', 'IsActive']) {
    if (rows.length > 0 && rows.every((row) => row.fieldsByName?.[requiredField] === undefined)) {
      throw new Error(
        `Window ${VATID_CONFIG_WINDOW_ID}'s view no longer exposes ${requiredField}, so this run cannot tell which` +
          ` organisation's active VAT-ID check configuration is which.` +
          ` Re-check AD_UI_Element.IsDisplayedGrid for that column.`
      );
    }
  }

  const rowsForOrg = rows.filter((row) => {
    const orgValue = row.fieldsByName?.AD_Org_ID?.value;
    const rowOrgId = typeof orgValue === 'object' && orgValue !== null ? orgValue.key : orgValue;
    if (String(rowOrgId) !== String(orgId)) {
      return false;
    }
    // The invariant is one ACTIVE row per organisation, so inactive history must
    // not count — rather than assuming the view filters it out for us.
    return row.fieldsByName?.IsActive?.value !== false;
  });

  if (rowsForOrg.length === 0) {
    return null;
  }
  expect(
    rowsForOrg.length,
    `Only ONE active VAT-ID check configuration can exist for organisation ${orgId} (partial unique index), found ${rowsForOrg.length}`
  ).toBe(1);
  return String(rowsForOrg[0].id);
}

/**
 * Point the organisation's VAT-ID check configuration at the stub server, through
 * the real configuration window.
 *
 * Reuse-or-create, with an unconditional restore, because the table permits only
 * ONE active row per organisation (partial unique index on `AD_Org_ID WHERE
 * IsActive='Y'`): a stack that already has one (a cucumber run leaves one behind)
 * cannot take a second, and a stack that has none must get one. The original
 * field values of a reused record are captured and written back in the caller's
 * `finally`, and a record this spec created is deleted there — otherwise the next
 * run collides with this run's leftovers.
 */
async function configureVatIdCheck(page, { restApiBaseURL, orgId }) {
  const existingRecordId = await findExistingConfigRecordId(page, orgId);

  let recordId;
  let wasCreated;
  if (existingRecordId) {
    await page.goto(`${FRONTEND_BASE_URL}/window/${VATID_CONFIG_WINDOW_ID}/${existingRecordId}`);
    wasCreated = false;
  } else {
    await page.goto(`${FRONTEND_BASE_URL}/window/${VATID_CONFIG_WINDOW_ID}`);
    await page.locator('.document-list-wrapper, .document-list').waitFor({
      state: 'visible',
      timeout: VERY_SLOW_ACTION_TIMEOUT,
    });
    await page.locator('body').click();
    await page.waitForTimeout(200);
    await page.keyboard.press('Alt+N');
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
    wasCreated = true;
  }
  await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await WidgetCommon.getFieldContainer('RestApiBaseURL').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  // On the reuse branch the id is already known from the view; on the create
  // branch it only exists in the URL the new record landed on (query string
  // stripped — the SPA may have attached one by now).
  recordId = wasCreated ? page.url().split('?')[0].split('/').pop() : existingRecordId;

  // Publish the teardown state HERE — before anything is asserted or written, and
  // the moment a record exists that teardown might have to undo. Returning it only
  // at the end (as this function first did) meant any throw in between — a failed
  // `assertRecordIsValid`, an `applyConfigFields` timeout — left the caller with no
  // state at all: a created row leaked permanently, or worse, a REUSED row stayed
  // half-rewritten towards the stub with its captured originals discarded. Silent
  // corruption of a record shared with every other spec on the stack.
  //
  // The steps ABOVE this line need no such protection, on two separate grounds —
  // stated separately because they rest on different kinds of evidence:
  //
  //   MEASURED: `Alt+N` persists nothing. It was driven on this window and the run
  //   left to sit at exactly this point — the URL took document id 1000003 while
  //   `VATaxID_Config` still held exactly its one pre-existing row. It opens an
  //   in-memory draft in the webapi's document collection; the row is INSERTed only
  //   by the first valid save. That is also why the earlier unique-index failures on
  //   document ids 1000001/1000002 left no rows behind. A document id in the URL is
  //   therefore NOT evidence that a row exists.
  //
  //   READ: the three statements between `Alt+N` and here — the URL wait, the
  //   pending-indicator wait and the field-container wait — are navigation/DOM waits
  //   that issue no write, so they cannot add persistence risk to that window.
  //
  // No runtime assertion is added here on purpose. It could only ever fire if a
  // future frontend change made record creation eager, which is a hypothetical
  // rather than a failure mode reachable today — and the teardown it would guard
  // has nothing to undo in the state it would detect.
  pendingConfigTeardown = { recordId, wasCreated, original: null };

  await assertRecordIsValid(VATID_CONFIG_WINDOW_ID, recordId, 'VAT-ID check configuration record');

  // Captured BEFORE the first write, and stored raw: `RestApiBaseURL` is genuinely
  // `null` on an untouched record, and null vs '' are indistinguishable on screen
  // yet different in the DB — so the restore replays exactly what was read here,
  // never a re-typed literal.
  const before = await getRecordData(VATID_CONFIG_WINDOW_ID, recordId);
  pendingConfigTeardown.original = {
    isViesCheckEnabled: before.fieldsByName?.IsVIESCheckEnabled?.value,
    restApiBaseURL: before.fieldsByName?.RestApiBaseURL?.value,
    recheckAfterDays: before.fieldsByName?.RecheckAfterDays?.value,
  };

  // Zero re-check window: every save must actually send a request, so the run can
  // never pass on a de-duplicated result an earlier run left in the check log.
  await applyConfigFields(recordId, {
    RestApiBaseURL: restApiBaseURL,
    RecheckAfterDays: 0,
    IsVIESCheckEnabled: true,
  });
  await assertRecordIsValid(VATID_CONFIG_WINDOW_ID, recordId, 'after enabling the online check');

  console.log(`[INFO] VAT-ID check configuration ${recordId} (${wasCreated ? 'created' : 'reused'}) -> ${restApiBaseURL}`);
}

/**
 * Write the given configuration fields and return only once the RECORD shows each
 * of them.
 *
 * The widget helpers are subject to the same silent-drop race as any other field
 * on this stack (`setVatIdAndAwaitPersisted` documents the mechanism: the widget
 * only patches on blur once its own focus state has been committed). A dropped
 * write here is especially damaging in both directions — a dropped
 * `RecheckAfterDays` would leave de-duplication on, letting the test pass on a
 * result an earlier run recorded rather than on one this run obtained; and a
 * dropped write during CLEANUP leaves the shared stack altered, which is exactly
 * how this spec's own first green runs left `RecheckAfterDays` at 0 instead of
 * the 30 they found. So every field is verified against the record and retried.
 */
async function applyConfigFields(recordId, fields) {
  const page = getPage();
  const deadline = Date.now() + VERY_SLOW_ACTION_TIMEOUT;
  let mismatches = [];

  while (Date.now() < deadline) {
    for (const [fieldName, value] of Object.entries(fields)) {
      if (typeof value === 'boolean') {
        await (value ? BooleanWidget.setTrue(fieldName) : BooleanWidget.setFalse(fieldName));
      } else if (typeof value === 'number') {
        await NumericWidget.setValue(fieldName, value);
      } else {
        await TextWidget.setValue(fieldName, value ?? '');
      }
    }
    await WidgetCommon.waitForSaveComplete();

    const recordData = await getRecordData(VATID_CONFIG_WINDOW_ID, recordId);
    mismatches = Object.entries(fields).filter(([fieldName, value]) => {
      const observed = recordData.fieldsByName?.[fieldName]?.value;
      // Loose equality on purpose: the WebAPI may return a numeric field as a
      // string, and an empty text field as null rather than ''.
      if (typeof value === 'number') return Number(observed) !== value;
      if (typeof value === 'boolean') return observed !== value;
      return (observed ?? '') !== (value ?? '');
    });
    if (mismatches.length === 0) {
      return;
    }

    await page.waitForTimeout(500);
  }

  throw new Error(
    `VAT-ID check configuration ${recordId}: could not persist ${mismatches
      .map(([fieldName, value]) => `${fieldName}=${JSON.stringify(value)}`)
      .join(', ')}`
  );
}

/**
 * Undo whatever `configureVatIdCheck` did. Invoked from `test.afterEach`, so it
 * runs whether the test passed, failed, or timed out.
 *
 * Driven through the WebAPI rather than the widgets, deliberately — teardown has
 * different requirements from the setup it undoes. It must work when the page is
 * on an arbitrary URL (or already torn down after a timeout); it must not depend
 * on the widget focus race that setup has to retry around; and it must be able to
 * write a genuine `null` back, which typing into a text widget cannot express.
 * Verified end-to-end against this window: a `replace` op with `value: null`
 * leaves the column `NULL`, not `''`.
 *
 * Idempotent by construction: it replays absolute captured values (or deletes a
 * record this spec created), so running it twice, or after a partially-applied
 * setup, converges on the same original state. A failure must NOT fail the test —
 * the assertions have already run — but must be loud, because an unrestored value
 * silently becomes the next run's baseline.
 */
async function restoreVatIdCheckConfiguration(page, state) {
  if (!state) return;
  try {
    if (state.wasCreated) {
      // `response.ok()` is checked, not assumed: `page.request.delete` rejects only
      // on a network-level failure, so a REFUSED delete (a 4xx/5xx from a
      // constraint, say) would otherwise be logged as a success and leak the row
      // this spec created — which then becomes the next run's "existing" row.
      const deleteResponse = await page.request.delete(
        `${WEBAPI_BASE_URL}/window/${VATID_CONFIG_WINDOW_ID}/${state.recordId}`,
        { headers: { Accept: 'application/json' } }
      );
      if (!deleteResponse.ok()) {
        throw new Error(`HTTP ${deleteResponse.status()} ${deleteResponse.statusText()} deleting the record`);
      }
      console.log(`[INFO] Cleanup: deleted VAT-ID check configuration ${state.recordId}`);
      return;
    }

    if (!state.original) {
      // Captured before the first write, so a null `original` means nothing was
      // written yet and there is nothing to undo.
      console.log(`[INFO] Cleanup: VAT-ID check configuration ${state.recordId} was never modified`);
      return;
    }

    const restored = {
      RestApiBaseURL: state.original.restApiBaseURL ?? null,
      RecheckAfterDays: Number(state.original.recheckAfterDays ?? 0),
      IsVIESCheckEnabled: state.original.isViesCheckEnabled === true,
    };
    const response = await page.request.patch(
      `${WEBAPI_BASE_URL}/window/${VATID_CONFIG_WINDOW_ID}/${state.recordId}`,
      {
        data: Object.entries(restored).map(([path, value]) => ({ op: 'replace', path, value })),
        headers: { 'Content-Type': 'application/json' },
      }
    );
    if (!response.ok()) {
      throw new Error(`HTTP ${response.status()} ${response.statusText()}`);
    }

    // Read back: a teardown that only fired the request would report success on a
    // silently-rejected restore, which is how an altered value escapes unnoticed.
    const after = await getRecordData(VATID_CONFIG_WINDOW_ID, state.recordId);
    const mismatches = Object.entries(restored).filter(([fieldName, value]) => {
      const observed = after.fieldsByName?.[fieldName]?.value;
      if (typeof value === 'number') return Number(observed) !== value;
      if (typeof value === 'boolean') return observed !== value;
      return (observed ?? null) !== (value ?? null);
    });
    if (mismatches.length > 0) {
      throw new Error(
        `read-back mismatch on ${mismatches.map(([f, v]) => `${f} (wanted ${JSON.stringify(v)})`).join(', ')}`
      );
    }

    console.log(`[INFO] Cleanup: restored VAT-ID check configuration ${state.recordId}`);
  } catch (e) {
    console.log(
      `[WARN] Cleanup of VAT-ID check configuration ${state.recordId} FAILED, the stack may be left altered: ${e.message}`
    );
  }
}

test.describe('VAT-ID check status on the Business Partner window (123)', () => {
  // Guaranteed teardown. Playwright runs `afterEach` even when the test body
  // throws OR times out, which a trailing call in the body does not — and this
  // spec mutates a record shared by every other spec on the stack.
  test.afterEach(async ({ page }) => {
    const configState = pendingConfigTeardown;
    const stubIds = pendingStubIds;
    pendingConfigTeardown = null;
    pendingStubIds = [];

    await restoreVatIdCheckConfiguration(page, configState);
    for (const stubId of stubIds) {
      await removeWireMockStub(stubId);
    }
  });

  test('The status fields render directly next to the VAT-ID, read-only, on the partner and on its address', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VAT-ID check status placement on C_BPartner (tab 220) and C_BPartner_Location (tab 222)');
    allure.severity('critical');
    allure.tag('VATaxIDStatus');
    allure.description(`
## VAT-ID check status placement (AD_Window_ID 123, tabs 220 + 222)

### Why this test exists

The check result is only useful where the user already looks — beside the VAT-ID
it belongs to. That is a claim about the rendered layout, so it is asserted
structurally: \`VATaxIDStatus\`, \`VATaxIDCheckedAt\` and \`VATaxID_CheckLog_ID\`
must be the three element lines immediately following \`VATaxID\` INSIDE THE SAME
rendered AD_UI_ElementGroup. Asserting only that the fields exist somewhere on
the page would still pass after a migration moved them into an unrelated group.

### What it proves

1. Partner header (tab 220): the three status fields directly follow \`VATaxID\`
   in its own element group.
2. Address (tab 222): the same, in the location's own detail form — reached by
   selecting the address row and opening Advanced Edit, which is where the
   location's \`VATaxID\` itself is shown (it is not a grid column of that tab).
3. \`VATaxIDStatus\` renders READ-ONLY on both grains, asserted on the one DOM
   signal that tracks the flag (\`.input-disabled\` on the List widget's dropdown
   container) — with \`AD_Language\`, an editable List widget on the same record,
   as the negative control that the signal discriminates at all.
4. A partner that was never checked carries the \`NotChecked\` status rather than
   an empty field, so the column reads as a state and not as missing data.
    `);

    test.setTimeout(180000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'VatidStatusPlacement' } },
        bpartners: { PARTNER1: { isCustomer: true, name: 'E2E VatidStatusPlacement Partner' } },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const bpartnerId = masterdata.bpartners.PARTNER1.id;
    const bpartnerLocationId = masterdata.bpartners.PARTNER1.bpartnerLocationId;
    expect(bpartnerId, 'Masterdata must return the created BPartner id').toBeTruthy();
    expect(bpartnerLocationId, 'Masterdata must return the created BPartner location id').toBeTruthy();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    // Deliberate URL check instead of DashboardPage.expectVisible(): the
    // dashboard's STOMP/KPI polling keeps the network permanently busy, so its
    // networkidle wait never settles.
    await LoginPage.expectLoggedIn();

    await BusinessPartnerPage.gotoRecord(bpartnerId);

    await test.step('Partner header: the three status fields directly follow VATaxID in its element group', async () => {
      const header = page.locator('.sections-wrapper');
      await header.locator('.form-field-VATaxID').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expectStatusFieldsDirectlyAfterVatId(header, 'partner header (tab 220)');
    });

    await test.step('Partner header: VATaxIDStatus is read-only while an editable List widget on the same record is not', async () => {
      await expectStatusReadOnlyWithEditableControl(page.locator('.sections-wrapper'), 'partner header (tab 220)', {
        editableListField: 'AD_Language',
      });
    });

    await test.step('An unchecked partner reads as NotChecked, not as an empty field', async () => {
      const { key, caption } = readFieldKeyAndCaption(await getPartnerRecordData(bpartnerId), 'VATaxIDStatus');
      expect(key, 'A partner that was never checked must carry the NotChecked status').toBe('NotChecked');
      expect(caption, 'The NotChecked status must render a non-empty caption').toBeTruthy();
      console.log(`[PASS] partner header (tab 220): unchecked partner reads NotChecked (caption: "${caption}")`);
    });

    await test.step('Address: the same pairing holds in the location detail form', async () => {
      const modal = await openAddressRowAdvancedEdit(page);

      await expectStatusFieldsDirectlyAfterVatId(modal, 'address (tab 222)');
      await expectStatusReadOnlyWithEditableControl(modal, 'address (tab 222)', { editableListField: null });

      const { key } = readFieldKeyAndCaption(await getLocationRecordData(bpartnerId, bpartnerLocationId), 'VATaxIDStatus');
      expect(key, 'An unchecked address must carry the NotChecked status').toBe('NotChecked');

      const screenshot = await page.screenshot({ fullPage: true });
      await allure.attachment('Address detail form with the VAT-ID status fields', screenshot, 'image/png');
    });

    console.log('[PASS] VAT-ID status fields render next to the VAT-ID, read-only, on both the partner and its address');
  });

  test('A real check writes a status the user can tell apart at a glance, on the partner and on its address', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VAT-ID check status values driven through the real after-commit check');
    allure.severity('critical');
    allure.tag('VATaxIDStatus');
    allure.description(`
## VAT-ID check status values (AD_Window_ID 123, tabs 220 + 222)

### Why this test exists

\`VATaxIDStatus\` is read-only, so no widget can set it and no test may
hand-write it: a spec that seeded the column would prove the field renders while
proving nothing about the feature that fills it. This test instead drives the
production path — configure the organisation's \`VATaxID_Config\` through its own
window, point \`RestApiBaseURL\` at the WireMock stub server the stack already
runs, then type VAT-IDs into the real \`VATaxID\` widget and let each save's
after-commit check write the status back.

### What it proves

1. A VAT-ID the service confirms lands as \`Valid\`; one the service rejects lands
   as \`Invalid\`; one whose member state the service does not cover lands as
   \`NotSupported\` (decided without a request).
2. Those three, plus the \`NotChecked\` the record started from, render four
   captions that all differ from one another — i.e. an invalid VAT-ID is
   distinguishable at a glance from valid, unchecked and not-verifiable.
3. The same holds for the address grain: setting the location's own \`VATaxID\`
   drives the location's own status.

### Notes

Each status is identified by its language-invariant \`AD_Ref_List.Value\` read
from the WebAPI; the captions are only ever compared to EACH OTHER, never to a
literal, so the test is language-independent. \`RecheckAfterDays\` is set to 0 so
every save really sends a request instead of re-using a result an earlier run
left in the check log.
    `);

    // Headroom over the theoretical worst case: up to eight sequential polls that
    // each allow VERY_SLOW_ACTION_TIMEOUT, plus login and configuration overhead.
    test.setTimeout(480000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'VatidStatusCheck' } },
        bpartners: { PARTNER1: { isCustomer: true, name: 'E2E VatidStatusCheck Partner' } },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const bpartnerId = masterdata.bpartners.PARTNER1.id;
    const bpartnerLocationId = masterdata.bpartners.PARTNER1.bpartnerLocationId;
    expect(bpartnerId, 'Masterdata must return the created BPartner id').toBeTruthy();
    expect(bpartnerLocationId, 'Masterdata must return the created BPartner location id').toBeTruthy();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // No try/finally here: teardown of BOTH the stubs and the shared configuration
    // record is owned by `test.afterEach`, which the runner also runs after a
    // timeout — the one case a trailing `finally` in the body never reaches.
    await test.step('Stub the online VAT-ID service with one confirming and one rejecting answer', async () => {
      await registerWireMockStub(
        viesCheckVatNumberStub({ countryCode: 'DE', vatNumber: VATID_VALID.substring(2), valid: true })
      );
      await registerWireMockStub(
        viesCheckVatNumberStub({ countryCode: 'DE', vatNumber: VATID_INVALID.substring(2), valid: false })
      );
      console.log(`[INFO] Registered ${pendingStubIds.length} WireMock stubs at ${WIREMOCK_BASE_URL}${VIES_STUB_PATH}`);
    });

    await test.step('Enable the online check for this organisation, pointed at the stub server', async () => {
      // The organisation is READ OFF THE PARTNER rather than hardcoded: the
      // check resolves its configuration by the checked record's own
      // AD_Org_ID (VATaxIDCheckService -> VATaxIDConfigRepository#getByOrgId),
      // so the configuration this test edits must be that same organisation's
      // or the run would configure one org and check another.
      const partnerOrg = readFieldKeyAndCaption(await getPartnerRecordData(bpartnerId), 'AD_Org_ID');
      // `!= null`, not a truthiness check: org id 0 is the metasfresh System-org
      // sentinel and is falsy in JS, so `toBeTruthy()` would reject a legitimate id.
      expect(partnerOrg.key, "The partner's organisation must be resolvable").not.toBe(null);
      expect(partnerOrg.key, "The partner's organisation must be resolvable").not.toBe(undefined);

      await configureVatIdCheck(page, {
        restApiBaseURL: `${WIREMOCK_BASE_URL}${VIES_STUB_PATH}`,
        orgId: partnerOrg.key,
      });
    });

    await BusinessPartnerPage.gotoRecord(bpartnerId);
    const header = page.locator('.sections-wrapper');
    await header.locator('.form-field-VATaxID').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await assertRecordIsValid(BPARTNER_WINDOW_ID, bpartnerId, 'before setting the partner VAT-ID');

    const readPartner = () => getPartnerRecordData(bpartnerId);
    const captionsByStatus = {};

    captionsByStatus.NotChecked = readFieldKeyAndCaption(await readPartner(), 'VATaxIDStatus').caption;
    expect(captionsByStatus.NotChecked, 'The starting NotChecked status must render a caption').toBeTruthy();

    await test.step(`A VAT-ID the service confirms becomes Valid`, async () => {
      await setVatIdAndAwaitPersisted({
        scope: header,
        vatIdValue: VATID_VALID,
        readRecordData: readPartner,
        label: 'partner header',
      });
      captionsByStatus.Valid = (
        await waitForVatIdStatus({ readRecordData: readPartner, expectedKey: 'Valid', label: 'partner header' })
      ).caption;
    });

    // No page.reload() between the three values, deliberately. A reload
    // re-mounts the widget from the LAYOUT first and hydrates it from the
    // document a moment later, and typing into that gap is silently discarded
    // (see setVatIdAndAwaitPersisted). Nothing needs the reload: each status is
    // read back from the record itself, so staying on the already-hydrated form
    // removes the race instead of retrying through it.
    await test.step(`A VAT-ID the service rejects becomes Invalid`, async () => {
      await setVatIdAndAwaitPersisted({
        scope: header,
        vatIdValue: VATID_INVALID,
        readRecordData: readPartner,
        label: 'partner header',
      });
      captionsByStatus.Invalid = (
        await waitForVatIdStatus({ readRecordData: readPartner, expectedKey: 'Invalid', label: 'partner header' })
      ).caption;
    });

    await test.step(`A VAT-ID from a member state the service does not cover becomes NotSupported`, async () => {
      await setVatIdAndAwaitPersisted({
        scope: header,
        vatIdValue: VATID_NOT_SUPPORTED,
        readRecordData: readPartner,
        label: 'partner header',
      });
      captionsByStatus.NotSupported = (
        await waitForVatIdStatus({ readRecordData: readPartner, expectedKey: 'NotSupported', label: 'partner header' })
      ).caption;
    });

    await test.step('The four statuses render four captions that all differ from one another', async () => {
      const captions = Object.values(captionsByStatus);
      console.log(`[INFO] Rendered status captions: ${JSON.stringify(captionsByStatus)}`);
      expect(
        new Set(captions).size,
        `NotChecked, Valid, Invalid and NotSupported must each render a distinct caption, got ${JSON.stringify(captionsByStatus)}`
      ).toBe(captions.length);

      const screenshot = await page.screenshot({ fullPage: true });
      await allure.attachment('Partner after the check reported NotSupported', screenshot, 'image/png');
    });

    await test.step('Setting the address VAT-ID drives the address status', async () => {
      const modal = await openAddressRowAdvancedEdit(page);

      await setVatIdAndAwaitPersisted({
        scope: modal,
        vatIdValue: VATID_VALID,
        readRecordData: () => getLocationRecordData(bpartnerId, bpartnerLocationId),
        label: 'address',
      });

      const observed = await waitForVatIdStatus({
        readRecordData: () => getLocationRecordData(bpartnerId, bpartnerLocationId),
        expectedKey: 'Valid',
        label: 'address',
      });
      expect(
        observed.caption,
        'The address must render the same caption for Valid as the partner does — the status is one shared reference list'
      ).toBe(captionsByStatus.Valid);

      const screenshot = await page.screenshot({ fullPage: true });
      await allure.attachment('Address detail form after the check reported Valid', screenshot, 'image/png');
    });

    console.log('[PASS] The real check drove Valid / Invalid / NotSupported on the partner and Valid on its address, each distinguishable at a glance');
  });
  /**
   * The caption of the VAT-ID element was relabelled across every `VATaxID` field
   * placement. `metasfresh-window-design-rules` § "Verification — MANDATORY
   * Playwright test" requires a field-translation change to be verified in the
   * rendered UI, so this test does that for the two placements this spec already
   * opens: the partner header (tab 220) and the address detail form (tab 222).
   *
   * HOW IT STAYS LANGUAGE-INDEPENDENT: the expectation is not a literal and not a
   * per-language table. It is read from the running backend's own window layout,
   * in the session's language, per placement, and each placement's rendered label
   * must equal ITS OWN published caption. That statement holds in every language,
   * so the spec needs no edit when a translation legitimately changes — and it
   * still runs in two languages, which proves the caption resolves per language
   * rather than being pinned to one.
   *
   * BE PRECISE ABOUT THE RULE, THOUGH. `e2e/frontend-webui/CLAUDE.md` forbids
   * comparing an `expect` against "a localized UI string or a layout caption".
   * This still compares rendered text against a layout caption — what it removes
   * is the hardcoded per-language literal, which is the failure the rule's own
   * "Why" describes (strings going stale, specs breaking when the language
   * changes). Do not read this comment as claiming full compliance with the
   * letter: it is a deliberate, narrower reading, kept because the mandate for
   * this test (`metasfresh-window-design-rules` § "Verification") is inherently
   * about a caption. If a reviewer wants the letter, this check belongs outside
   * Playwright entirely — asserted against the migration's target value.
   *
   * What it catches: a placement rendering a DIFFERENT element than the one the
   * AD publishes for it (the half-applied-relabel failure this exists for), a
   * placement whose caption is blank, a placement that publishes more than one
   * caption, and a stale cached label in the frontend.
   *
   * What it CANNOT catch, stated plainly: a wholesale reverted relabel. Both the
   * expectation and the observation come from the same backend, so if the element
   * still said "USt-ID" everywhere, the layout would say so, the DOM would agree,
   * and this would pass. The literal wording is AD data and is guarded where that
   * data lives — the relabel migration's usage enumeration across the placements
   * it touched, plus the `window-designer` pass — not by a browser test.
   *
   * Still read from the rendered `<label>`, not only from the layout: the rule is
   * about what the user actually sees. The layout supplies the expectation; the
   * DOM supplies the observation.
   *
   * NOT covered here: the `C_Fiscal_Representation` placement on window 110. That
   * table has no records on the test stacks, so there is no record to open and no
   * caption to render; covering it would mean inventing a fixture for a window
   * this spec is not about.
   */
  const CAPTION_LANGUAGES = ['de_DE', 'en_US'];

  /**
   * Every caption the backend's own window layout publishes for `columnName`,
   * collected by walking the payload rather than by indexing a fixed nesting.
   *
   * The layout groups fields differently per tab (sections -> columns -> element
   * groups -> element lines -> elements, with detail tabs nested again), and that
   * grouping is not part of what this test is about — so the walk is
   * shape-agnostic on purpose: any object carrying a `fields` array with an entry
   * whose `field` is `columnName` contributes its `caption`. A change in how the
   * layout nests elements must not turn this into a false red.
   */
  const collectLayoutCaptions = (node, columnName, found = new Set()) => {
    if (Array.isArray(node)) {
      node.forEach((child) => collectLayoutCaptions(child, columnName, found));
      return found;
    }
    if (!node || typeof node !== 'object') {
      return found;
    }
    const carriesColumn =
      Array.isArray(node.fields) && node.fields.some((f) => f && f.field === columnName);
    if (carriesColumn && typeof node.caption === 'string') {
      found.add(node.caption);
    }
    Object.values(node).forEach((child) => collectLayoutCaptions(child, columnName, found));
    return found;
  };

  /**
   * Reads the captions straight from the running backend, in the session's own
   * language.
   *
   * This is what makes the assertion language-INDEPENDENT: the expectation is
   * derived from AD metadata at run time instead of being hardcoded per language,
   * so the test states "the UI renders the caption the Application Dictionary
   * publishes for this column" — true in every language, and it never has to be
   * edited when a translation legitimately changes.
   *
   * What it still catches: a placement rendering a DIFFERENT element than the one
   * the AD publishes for it (the half-applied-relabel failure this test exists
   * for), a blank caption, a placement publishing more than one caption, and a
   * frontend rendering a stale cached label.
   *
   * What it does NOT pin is the caption's literal wording. Expectation and
   * observation both come from the same backend, so a wholesale reverted relabel
   * would pass here — that wording is guarded at the Application Dictionary level
   * by the relabel migration's usage enumeration and the `window-designer` pass,
   * not by a browser test.
   */
  const readLayoutCaptionsByPlacement = async (page, windowId, columnName) => {
    const response = await page.request.get(`${WEBAPI_BASE_URL}/window/${windowId}/layout`, {
      headers: { 'Content-Type': 'application/json' },
    });
    expect(response.ok(), `window ${windowId} layout must be readable (HTTP ${response.status()})`).toBe(true);
    const layout = await response.json();

    /**
     * Each placement is read from the SAME endpoint the UI itself uses to render
     * it, which is what makes "that placement's own caption" mean something:
     *
     *  - header form      -> `/window/{id}/layout`, elements under `sections`
     *  - address advanced -> `/window/{id}/{tabId}/layout?advanced=true`
     *
     * The window-level `tabs[]` entry is NOT the address form: it describes that
     * tab's grid columns, which do not include `VATaxID`. Reading it here returned
     * an empty set and failed loudly — which is how this endpoint was pinned down
     * rather than assumed.
     */
    const advancedResponse = await page.request.get(
      `${WEBAPI_BASE_URL}/window/${windowId}/${LOCATION_TAB_ID}/layout?advanced=true`,
      { headers: { 'Content-Type': 'application/json' } }
    );
    expect(
      advancedResponse.ok(),
      `window ${windowId} tab ${LOCATION_TAB_ID} advanced layout must be readable (HTTP ${advancedResponse.status()})`
    ).toBe(true);
    const advancedLayout = await advancedResponse.json();

    /**
     * Exactly one caption per placement — deliberately `toBe(1)`, not "at least one".
     *
     * This is what keeps the placements independent. Reading the whole window into
     * one accepted set would let a sibling placement's caption satisfy the
     * assertion, so a genuine divergence between the header and the address — one
     * of them overridden onto a different `AD_Element` via `AD_Field.AD_Name_ID`
     * — would pass unnoticed. That is not hypothetical: this feature's own relabel
     * missed exactly such an override elsewhere in the dictionary and needed a
     * second migration. Each placement is therefore compared against its OWN
     * caption, and a placement publishing two would fail loudly rather than
     * silently widen what the test accepts.
     */
    const onlyCaptionOf = (node, placement) => {
      const captions = collectLayoutCaptions(node, columnName);
      expect(
        captions.size,
        `${placement} must publish exactly one ${columnName} caption, got ${JSON.stringify([...captions])}`
      ).toBe(1);
      const [caption] = [...captions];
      expect(caption.trim(), `${placement}: the ${columnName} caption must not be blank`).not.toBe('');
      return caption;
    };

    return {
      header: onlyCaptionOf(layout.sections, `window ${windowId}'s header form`),
      address: onlyCaptionOf(advancedLayout, `window ${windowId} tab ${LOCATION_TAB_ID} (advanced edit)`),
    };
  };

  CAPTION_LANGUAGES.forEach((language) => {
    test(`The VAT-ID field caption matches the Application Dictionary on the partner and on its address (${language})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.story(`VAT-ID field caption per language (${language})`);
      allure.severity('normal');
      allure.tag('VATaxIDStatus');
      allure.description(`
## VAT-ID field caption (AD_Element 502388, AD_Window_ID 123, tabs 220 + 222)

### Why this test exists

The VAT-ID element's caption was relabelled across every placement. A
field-translation change must be verified in the rendered UI, per
\`metasfresh-window-design-rules\`, otherwise a half-applied relabel — one
placement left pointing at a different element — ships unnoticed.

### What it proves

Logged in as a ${language} user, the label rendered next to the VAT-ID input is
exactly the caption that placement itself publishes — the partner header checked
against the header form's own entry, the address checked against tab
\`${LOCATION_TAB_ID}\`'s. Scoping per placement is the point: a window-wide
accepted set would let one placement's caption satisfy the other, hiding exactly
the divergence this test exists to find.

### Why it asserts no literal text, and what that costs

The expectation is read from the running backend in the session's language rather
than hardcoded per language, so the spec needs no edit when a translation
legitimately changes. It does still compare against a layout caption, which
\`e2e/frontend-webui/CLAUDE.md\` names — what is removed is the hardcoded
localized literal, the failure that rule's rationale describes.

The cost, stated rather than hidden: expectation and observation both come from
the same backend, so a wholesale reverted relabel would pass here. That wording
is guarded at the Application Dictionary level by the relabel migration, not by
this test.
      `);

      test.setTimeout(180000);

      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'E2E', lastname: 'VatidCaption' } },
          bpartners: { PARTNER1: { isCustomer: true, name: 'E2E VatidCaption Partner' } },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      const bpartnerId = masterdata.bpartners.PARTNER1.id;
      expect(bpartnerId, 'Masterdata must return the created BPartner id').toBeTruthy();

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      await BusinessPartnerPage.gotoRecord(bpartnerId);

      const layoutCaptions = await readLayoutCaptionsByPlacement(page, BPARTNER_WINDOW_ID, 'VATaxID');
      console.log(
        `[INFO] ${language}: layout publishes VATaxID captions header=${JSON.stringify(layoutCaptions.header)} address=${JSON.stringify(layoutCaptions.address)}`
      );

      await test.step("Partner header: the VAT-ID label is that placement's own caption", async () => {
        const label = page.locator('.sections-wrapper .form-field-VATaxID > label.form-control-label');
        await expect(label, 'The partner header must render exactly one VAT-ID label').toHaveCount(1);
        const rendered = (await label.innerText()).trim();
        expect(
          rendered,
          `partner header (tab 220), ${language}: the rendered VAT-ID caption must be the one the header form itself publishes`
        ).toBe(layoutCaptions.header);
        console.log(`[PASS] partner header (tab 220): VAT-ID caption matches its own layout entry (${language})`);
      });

      await test.step("Address: the VAT-ID label is that placement's own caption", async () => {
        const modal = await openAddressRowAdvancedEdit(page);
        const label = modal.locator('.form-field-VATaxID > label.form-control-label');
        await expect(label, 'The address detail form must render exactly one VAT-ID label').toHaveCount(1);
        const rendered = (await label.innerText()).trim();
        expect(
          rendered,
          `address (${LOCATION_TAB_ID}), ${language}: the rendered VAT-ID caption must be the one that tab itself publishes`
        ).toBe(layoutCaptions.address);
        console.log(`[PASS] address (tab 222): VAT-ID caption matches its own layout entry (${language})`);

        const screenshot = await page.screenshot({ fullPage: true });
        await allure.attachment(`Address detail form (${language})`, screenshot, 'image/png');
      });
    });
  });
});
