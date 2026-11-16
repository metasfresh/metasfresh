import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { getFieldData, getRecordData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { getPage } from '../utils/common';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation, window 541568), header field changes made in the UI
 * (callout de.metas.costrevaluation.callout.M_CostRevaluation):
 *  - changing the accounting schema of a draft Manual header without lines presets the cost element of the
 *    schema's costing method; a header with lines, or a Copy-from-cost-element header, keeps its cost element;
 *  - on a Copy-from-cost-element header, a changed Accounting Date moves the Evaluation Start Date (the cut-off date)
 *    only while it is defaulted (empty, or equal to the previous Accounting Date); a hand-set cut-off date is kept.
 *
 * The test client has ONE accounting schema (AD_ClientInfo.C_AcctSchema1_ID), and the masterdata API cannot create
 * another: a second schema of the client would be posted to by every document of every other test. So the schema is
 * changed the way the UI allows it with one schema: it is cleared, then the single schema is selected again. The callout
 * presets the cost element on both changes (an empty schema falls back to the client's primary one), so a hand-chosen
 * other cost element is replaced by the preset only if the callout runs.
 */

const ACCT_SCHEMA_WINDOW_ID = '125';
const COST_ELEMENT_WINDOW_ID = '343';

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

const allureTags = (story) => {
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.story(story);
  allure.severity('critical');
};

async function login(md) {
  await LoginPage.goto();
  await LoginPage.login(md.login.user);
  await DashboardPage.expectVisible();
}

async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: { login: { user: { language, firstname: 'CostReval', lastname: 'Callout' } } },
  });
}

/** Masterdata with PSTK, a stocked Item with 10 on hand, for a header that has a line. */
async function createStockedMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'Callout' } },
      warehouses: { WH: {} },
      products: { PSTK: { name: 'CR_CALLOUT_LINE', type: 'Item' } },
      handlingUnits: { HU1: { product: 'PSTK', warehouse: 'WH', qty: 10 } },
    },
  });
}

/** @returns {Promise<{key: string, caption: string}[]>} the options of a header list field */
async function getHeaderOptions(page, recordId, columnName) {
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/field/${columnName}/dropdown`
  );
  expect(response.status()).toBe(200);
  return ((await response.json()).values || []).map((v) => ({ key: String(v.key), caption: v.caption }));
}

/** @returns {Promise<{key: string, caption: string} | null>} a header list field's value */
async function getListValue(recordId, columnName) {
  const value = (await getFieldData(COST_REVAL_WINDOW_ID, recordId, columnName)).value;
  return value ? { key: String(value.key), caption: value.caption } : null;
}

async function getDate(recordId, columnName) {
  const value = (await getFieldData(COST_REVAL_WINDOW_ID, recordId, columnName)).value;
  return value ? String(value).substring(0, 10) : null;
}

/**
 * The cost element a header presets for its accounting schema: the one active material cost element of the
 * schema's costing method, read from the accounting schema and the cost element windows.
 * @returns {Promise<{key: string, caption: string}>}
 */
async function getPresetCostElement(page, recordId, acctSchemaId) {
  const costingMethod = (await getFieldData(ACCT_SCHEMA_WINDOW_ID, acctSchemaId, 'CostingMethod')).value.key;
  const matching = [];
  for (const option of await getHeaderOptions(page, recordId, 'M_CostElement_ID')) {
    const element = (await getRecordData(COST_ELEMENT_WINDOW_ID, option.key)).fieldsByName;
    if (
      element.CostingMethod.value.key === costingMethod &&
      element.CostElementType.value.key === 'M' &&
      element.IsActive.value === true
    ) {
      matching.push(option);
    }
  }
  expect(matching, `exactly one active material cost element for costing method ${costingMethod}`).toHaveLength(1);
  return matching[0];
}

/** @returns {Promise<{key: string, caption: string}>} a cost element option other than the given one */
async function getOtherCostElement(page, recordId, costElementKey) {
  const other = (await getHeaderOptions(page, recordId, 'M_CostElement_ID')).find((o) => o.key !== costElementKey);
  expect(other, 'a second cost element to choose').toBeTruthy();
  return other;
}

/**
 * Prepare a new header for the accounting schema change: the cost element is hand-set to another one than the preset.
 * @returns {Promise<{acctSchema: {key: string, caption: string}, preset: {key: string, caption: string}, other: {key: string, caption: string}}>}
 */
async function chooseOtherCostElement(page, recordId) {
  const acctSchema = await getListValue(recordId, 'C_AcctSchema_ID');
  expect(acctSchema, 'the header has an accounting schema').toBeTruthy();
  const preset = await getPresetCostElement(page, recordId, acctSchema.key);
  expect((await getListValue(recordId, 'M_CostElement_ID')).key, 'the new header shows the preset').toBe(preset.key);

  const other = await getOtherCostElement(page, recordId, preset.key);
  await CostRevaluationPage.selectCostElement(other.key);
  expect((await getListValue(recordId, 'M_CostElement_ID')).key).toBe(other.key);
  await expect(CostRevaluationPage.headerListInput('M_CostElement_ID')).toHaveValue(other.caption);
  return { acctSchema, preset, other };
}

/** Change the accounting schema in the UI: clear it, then select the client's single schema again. */
async function changeAcctSchema(recordId, acctSchema) {
  expect(
    (await getHeaderOptions(getPage(), recordId, 'C_AcctSchema_ID')).map((o) => o.key),
    'the client has this one accounting schema'
  ).toEqual([acctSchema.key]);
  await CostRevaluationPage.reloadDocument(recordId);
  await CostRevaluationPage.clearAndReselectSingleOption('C_AcctSchema_ID', acctSchema.key);
  expect((await getListValue(recordId, 'C_AcctSchema_ID')).key).toBe(acctSchema.key);
  await expect(CostRevaluationPage.headerListInput('C_AcctSchema_ID')).toHaveValue(acctSchema.caption);
}

/** Expect the header's cost element, through the WebAPI and in the field, and the header saved (valid). */
async function expectCostElement(recordId, costElement) {
  expect((await getListValue(recordId, 'M_CostElement_ID')).key).toBe(costElement.key);
  await expect(CostRevaluationPage.headerListInput('M_CostElement_ID')).toHaveValue(costElement.caption);
  const record = await getRecordData(COST_REVAL_WINDOW_ID, recordId);
  expect(record.validStatus.valid, JSON.stringify(record.validStatus)).toBe(true);
  expect(record.saveStatus.saved, JSON.stringify(record.saveStatus)).toBe(true);
}

/** Switch a new header to Copy from cost element and choose the cost element to copy from (so the header saves). */
async function switchToCopyFromCostElement(page, recordId, targetCostElementKey) {
  await CostRevaluationPage.selectRevaluationSource('CopyFromCostElement');
  const copyFrom = (await getHeaderOptions(page, recordId, 'CopyFrom_M_CostElement_ID')).find(
    (o) => o.key !== targetCostElementKey
  );
  expect(copyFrom, 'a cost element to copy from').toBeTruthy();
  await CostRevaluationPage.selectHeaderListOption('CopyFrom_M_CostElement_ID', copyFrom.key);
  expect((await getListValue(recordId, 'RevaluationSource')).key).toBe('CopyFromCostElement');
  expect((await getListValue(recordId, 'CopyFrom_M_CostElement_ID')).key).toBe(copyFrom.key);
}

/** @returns {string} the given day (1..28) of the month of isoDate */
const dayOfMonth = (isoDate, day) => `${isoDate.substring(0, 8)}${String(day).padStart(2, '0')}`;

/** @returns {number[]} count days of the month (1..28) that differ from the day of isoDate */
const otherDays = (isoDate, count) =>
  [5, 8, 11, 14, 17, 20, 23].filter((d) => d !== Number(isoDate.substring(8, 10))).slice(0, count);

testCases.forEach(({ language, label }) => {
  // eslint-disable-next-line no-unused-vars
  test(`Changing the accounting schema of a Manual header without lines presets the schema's cost element (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: the accounting schema change presets the cost element');
    allure.description(`
## F1500: Costing — the accounting schema presets the cost element

On a new Manual Kosten Neubewertung header without lines, the Cost Element (Kostenart) is set by hand to another
cost element than the preset one. Changing the Accounting Schema (Buchführungs-Schema) in the UI sets the Cost
Element back to the active material cost element of the schema's costing method; the header is saved with it.
    `);

    const md = await createMasterdata(language);
    await login(md);
    const recordId = await CostRevaluationPage.createHeader();
    const { acctSchema, preset } = await chooseOtherCostElement(page, recordId);

    await test.step('Change the accounting schema -> the cost element is the schema\'s preset again', async () => {
      await changeAcctSchema(recordId, acctSchema);
      await expectCostElement(recordId, preset);
    });
  });

  // eslint-disable-next-line no-unused-vars
  test(`Changing the accounting schema of a header with lines keeps the cost element (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: the accounting schema change keeps the cost element of a header with lines');
    allure.description(`
## F1500: Costing — a header with lines keeps its cost element

A Manual Kosten Neubewertung header with another cost element than the preset gets a quick-input line. Changing the
Accounting Schema in the UI keeps the Cost Element (the lines were made for it); the header keeps its one line.
    `);

    const md = await createStockedMasterdata(language);
    await login(md);
    const recordId = await CostRevaluationPage.createHeader();
    const { acctSchema, other } = await chooseOtherCostElement(page, recordId);

    await CostRevaluationPage.expectQuickInputOpened();
    await CostRevaluationPage.addLine(md.products.PSTK.productCode, '15');
    expect((await CostRevaluationPage.getLines(recordId)).length).toBe(1);

    await test.step('Change the accounting schema -> the cost element is kept', async () => {
      await changeAcctSchema(recordId, acctSchema);
      await expectCostElement(recordId, other);
      expect((await CostRevaluationPage.getLines(recordId)).length).toBe(1);
    });
  });

  // eslint-disable-next-line no-unused-vars
  test(`Changing the accounting schema of a Copy-from-cost-element header keeps the cost element (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: the accounting schema change keeps the target cost element of a Copy from cost element header');
    allure.description(`
## F1500: Costing — a Copy from cost element header keeps its target cost element

A new Kosten Neubewertung header gets another (target) cost element than the preset and is switched to Copy from cost
element. Changing the Accounting Schema in the UI keeps the chosen target Cost Element.
    `);

    const md = await createMasterdata(language);
    await login(md);
    const recordId = await CostRevaluationPage.createHeader();
    const { acctSchema, other } = await chooseOtherCostElement(page, recordId);
    await switchToCopyFromCostElement(page, recordId, other.key);
    await expectCostElement(recordId, other);

    await test.step('Change the accounting schema -> the target cost element is kept', async () => {
      await changeAcctSchema(recordId, acctSchema);
      await expectCostElement(recordId, other);
    });
  });

  // eslint-disable-next-line no-unused-vars
  test(`Copy from cost element: the Evaluation Start Date follows a changed Accounting Date only while defaulted (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: the cut-off date follows the Accounting Date only while defaulted');
    allure.description(`
## F1500: Costing — the cut-off date of a Copy from cost element revaluation

On a Copy from cost element Kosten Neubewertung header, the Evaluation Start Date is the cut-off date. When the
Accounting Date is changed in the UI:
 - an Evaluation Start Date equal to the previous Accounting Date moves along to the new Accounting Date;
 - a hand-set Evaluation Start Date is kept;
 - an empty Evaluation Start Date is set to the new Accounting Date.
    `);

    const md = await createMasterdata(language);
    await login(md);
    const recordId = await CostRevaluationPage.createHeader();
    const target = await getListValue(recordId, 'M_CostElement_ID');
    await switchToCopyFromCostElement(page, recordId, target.key);

    const today = await getDate(recordId, 'DateAcct');
    expect(await getDate(recordId, 'EvaluationStartDate'), 'precondition: the cut-off date is the Accounting Date').toBe(today);
    // Days of the header's month (an open period) that differ from the initial Accounting Date and from each other.
    const [d1, d2, d3, d4] = otherDays(today, 4).map((d) => dayOfMonth(today, d));
    const dateAcctInput = CostRevaluationPage.headerDateInput('DateAcct');
    const evalStartInput = CostRevaluationPage.headerDateInput('EvaluationStartDate');

    await test.step(`Cut-off date = Accounting Date; change the Accounting Date to ${d1} -> the cut-off date follows`, async () => {
      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', d1);
      expect(await getDate(recordId, 'DateAcct')).toBe(d1);
      expect(await getDate(recordId, 'EvaluationStartDate')).toBe(d1);
      await expect(evalStartInput).toHaveValue(await dateAcctInput.inputValue());
      await CostRevaluationPage.showHeaderDates();
    });

    await test.step(`Hand-set cut-off date ${d2}; change the Accounting Date to ${d3} -> the cut-off date is kept`, async () => {
      await CostRevaluationPage.typeHeaderDate(recordId, 'EvaluationStartDate', d2);
      expect(await getDate(recordId, 'EvaluationStartDate')).toBe(d2);
      const handSetText = await evalStartInput.inputValue();

      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', d3);
      expect(await getDate(recordId, 'DateAcct')).toBe(d3);
      expect(await getDate(recordId, 'EvaluationStartDate')).toBe(d2);
      await expect(evalStartInput).toHaveValue(handSetText);
      await expect(evalStartInput).not.toHaveValue(await dateAcctInput.inputValue());
      await CostRevaluationPage.showHeaderDates();
    });

    await test.step(`Empty cut-off date; change the Accounting Date to ${d4} -> the cut-off date is set to it`, async () => {
      await CostRevaluationPage.clearHeaderDate('EvaluationStartDate');
      expect(await getDate(recordId, 'EvaluationStartDate')).toBeNull();

      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', d4);
      expect(await getDate(recordId, 'DateAcct')).toBe(d4);
      expect(await getDate(recordId, 'EvaluationStartDate')).toBe(d4);
      await expect(evalStartInput).toHaveValue(await dateAcctInput.inputValue());
      const record = await getRecordData(COST_REVAL_WINDOW_ID, recordId);
      expect(record.saveStatus.saved, JSON.stringify(record.saveStatus)).toBe(true);
      await CostRevaluationPage.showHeaderDates();
    });
  });
});
