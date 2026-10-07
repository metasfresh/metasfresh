import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

// Workplace window — auto-assign (Traffic Management) restriction fields
const WORKPLACE_WINDOW_ID = 541744;

// The 8 matching fields consolidated in the "restrictions" element group.
// Labels tooltips (description) come from AD_UI_Element.Description which has no _Trl table,
// so they are German in every session language. The two regular fields resolve their
// description from AD_Field_Trl and are language-specific.
const RESTRICTION_FIELDS = [
  { field: 'OrderPickingType', de: 'Kommissionierart', en: 'Order Picking Type', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Restriction for the automatic workplace assignment' },
  { field: 'PriorityRule', de: 'Priorität', en: 'Priority', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Restriction for the automatic workplace assignment' },
  { field: 'Labels_C_Workplace_Product', de: 'Produkte', en: 'Products', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung' },
  { field: 'Labels_C_Workplace_ProductCategory', de: 'Produktkategorien', en: 'Product Categories', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung' },
  { field: 'Labels_C_Workplace_Carrier_Product', de: 'Lieferweg-Produkte', en: 'Carrier Products', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung' },
  { field: 'Labels_C_Workplace_ExternalSystem', de: 'Externe Systeme', en: 'External Systems', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung' },
  { field: 'Labels_C_Workplace_BP_Group', de: 'Geschäftspartnergruppen', en: 'Business Partner Groups', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung' },
  { field: 'Labels_C_Workplace_DocType', de: 'Auftrags-Belegarten', en: 'Order Document Types', descrDe: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung', descrEn: 'Einschränkung für die automatische Arbeitsplatz-Zuordnung' },
];

function setAllureMeta() {
  allure.epic('E0105: Picking');
  allure.tag('F00251: Worplace Traffic Rules');
  allure.tag('F00251');
  allure.story('Workplace auto-assign restriction fields are grouped and explained');
}

/**
 * Fetch the window layout JSON for the current session and flatten all elements
 * (with their backing field names, caption and description).
 */
async function fetchLayoutElements(page) {
  const response = await page.request.get(
    `${FRONTEND_BASE_URL}/rest/api/window/${WORKPLACE_WINDOW_ID}/layout`
  );
  expect(response.ok()).toBeTruthy();
  const layout = await response.json();

  const elements = [];
  for (const section of layout.sections || []) {
    for (const column of section.columns || []) {
      for (const group of column.elementGroups || []) {
        for (const line of group.elementsLine || []) {
          for (const element of line.elements || []) {
            elements.push(element);
          }
        }
      }
    }
  }
  return elements;
}

function findElementByFieldName(elements, fieldName) {
  return elements.find((element) =>
    (element.fields || []).some((f) => f.field === fieldName)
  );
}

const languageCases = [
  { language: 'de_DE', label: 'German', captionKey: 'de', descrKey: 'descrDe' },
  { language: 'en_US', label: 'English', captionKey: 'en', descrKey: 'descrEn' },
];

test.describe('Workplace window — auto-assign restriction fields', () => {
  languageCases.forEach(({ language, label, captionKey, descrKey }) => {
    test(`Restriction fields: captions + matching-semantics tooltips (${label})`, async ({ page }) => {
      setAllureMeta();
      allure.severity('normal');

      const masterdata = await Backend.createMasterdata({
        request: { login: { user: { language } } },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await MasterWindowPage.goto(WORKPLACE_WINDOW_ID);
      await MasterWindowPage.expectWindowLoaded();

      const elements = await fetchLayoutElements(page);

      for (const expected of RESTRICTION_FIELDS) {
        const element = findElementByFieldName(elements, expected.field);
        expect(element, `layout element for ${expected.field}`).toBeTruthy();
        expect(element.caption, `caption of ${expected.field} (${language})`).toBe(
          expected[captionKey]
        );
        expect(
          element.description || '',
          `tooltip of ${expected.field} (${language})`
        ).toContain(expected[descrKey]);
      }
      console.log(`[PASS] all ${RESTRICTION_FIELDS.length} restriction fields verified (${label})`);
    });
  });

  test('BP-Group restriction can be set on a new Workplace and persists', async ({ page }) => {
    setAllureMeta();
    allure.severity('critical');

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'de_DE' } } },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Create a new Workplace via the UI: Name + Warehouse are the mandatory fields.
    await page.goto(`${FRONTEND_BASE_URL}/window/${WORKPLACE_WINDOW_ID}/NEW`);
    await page.locator('.form-field-Name input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    const workplaceName = `E2E Workplace ${Date.now()}`;
    await page.locator('.form-field-Name input').fill(workplaceName);
    await page.locator('.form-field-Name input').press('Tab');

    // Warehouse lookup: open the dropdown and take the first suggestion (language-independent)
    const warehouseInput = page.locator('#lookup_M_Warehouse_ID input');
    await warehouseInput.click();
    const warehouseOption = page.locator('.input-dropdown-list-option').first();
    await warehouseOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await warehouseOption.click();

    // Record is persisted once mandatory fields are valid — the URL then carries the record id
    await page.waitForURL(
      (url) => new RegExp(`/window/${WORKPLACE_WINDOW_ID}/\\d+`).test(url.toString()),
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    // Set a BP-Group restriction via the Labels multi-select.
    // The widget: focus the contentEditable `.labels-input` → it fetches and renders a
    // portaled `.input-dropdown-list` (outside the field container); the first row is a
    // header (also `.input-dropdown-list-option`), real options follow; selection fires on
    // mousedown (Playwright .click() issues mousedown).
    const bpGroupField = page.locator('.form-field-Labels_C_Workplace_BP_Group');
    await bpGroupField.scrollIntoViewIfNeeded();
    await bpGroupField.locator('.labels-input').click();

    // The dropdown is portaled to the document body, not inside the field.
    const dropdown = page.locator('.input-dropdown-list').last();
    await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const realOption = dropdown
      .locator('.input-dropdown-list-option:not(.input-dropdown-list-header)')
      .first();
    await realOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const chosenGroup = (await realOption.innerText()).trim();
    await realOption.click();

    // The chip must appear in the field
    const chips = bpGroupField.locator('.labels-wrap .labels-label');
    await expect(chips).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });

    // Reload — the restriction must have been persisted on the record
    await page.reload();
    await page.locator('.form-field-Labels_C_Workplace_BP_Group').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const chipsAfterReload = page.locator('.form-field-Labels_C_Workplace_BP_Group .labels-wrap .labels-label');
    await expect(chipsAfterReload).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
    // The chip's innerText is the caption plus the trailing remove-icon glyph (".labels-label-remove");
    // compare on the caption prefix only.
    const persistedGroup = (await chipsAfterReload.first().innerText()).replace(/\s*✕\s*$/, '').trim();
    expect(persistedGroup).toBe(chosenGroup);

    console.log(`[PASS] BP-Group restriction '${persistedGroup}' persisted on workplace '${workplaceName}'`);
  });
});
