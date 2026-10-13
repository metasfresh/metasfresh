/**
 * Inline lookup filter takes the widget size of its field.
 *
 * An inline filter is rendered with the widget size (AD_UI_Element.WidgetSize) of its field's layout
 * element in the window's main tab. Widget size L gives the filter box the same width as quick input's L
 * (min-width 30em = 420 px at 14 px font); the open dropdown and its "more results" footer are exactly as
 * wide as the lookup input and do not change width while typing. A filter without a widget size keeps the
 * default width (box 234 px, input and dropdown 204 px).
 *
 * Setup through the frontend testing endpoint (works on core data):
 * - window 167 (sales invoice), main tab: C_Invoice.C_BPartner_ID as inline filter with widget size L,
 *   C_Invoice.M_Warehouse_ID as inline filter without a widget size (its layout element has none in core);
 * - business partners with the same name start and lookup texts of about 30, 46, 52 and 106 characters,
 *   plus fillers, more than one lookup page in total, so that the "more results" footer is shown.
 *
 * The lookup text of a partner depends on the identifier columns of the database (core: Value_Name; other
 * databases may append further columns). So the spec first creates one probe partner, reads its lookup text
 * in the UI, and sizes the names of the other partners so that their lookup TEXT has the wanted length.
 *
 * The AD changes are not reverted: the test databases are throwaway, and the endpoint cannot clear a
 * widget size.
 */
import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { Backend } from '../utils/Backend';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

const SALES_INVOICE_WINDOW_ID = 167;
const SIZED_FILTER_ID = 'inline-C_BPartner_ID';
const UNSIZED_FILTER_ID = 'inline-M_Warehouse_ID';

// widget size L: min-width 30em at 14 px font
const L_BOX_WIDTH_PX = 420;
const L_BOX_TOLERANCE_PX = 10;
// no widget size: the default width of an inline lookup filter
const DEFAULT_BOX_WIDTH_PX = 234;
const DEFAULT_INPUT_WIDTH_PX = 204;
const DEFAULT_TOLERANCE_PX = 5;

const NAME_START = 'InlineFilterWidthTest';
// ordinary words (no wide glyphs), to pad names to a given length
const NAME_FILLER =
  ' Landwirtschaft Getreide und Futtermittel Nord Erzeugergemeinschaft Mittelland Hochebene' +
  ' fuer Getreide Milch und Saatgut Region West Talschaft Agrar';

const FILLER_PARTNERS_COUNT = 20; // plus probe, 46, 52 and 106 = 24 partners
const TARGET_TEXT_LENGTHS = { medium: 46, p99: 52, long: 106 };

// self-contained like the other specs
async function loginAsMetasfresh(page) {
  await page.goto('/logout', { waitUntil: 'load' });
  await page.goto('/', { waitUntil: 'load' });

  await page
    .locator('.login-container')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await page.locator('input[name="username"]').fill('metasfresh');
  await page.locator('input[name="password"]').fill('metasfresh');
  await page.locator('.login-container .btn-meta-success').click();

  // Click through role-selection screen if it appears.
  try {
    const roleSubmitBtn = page.locator(
      '.login-container .btn-meta-success:visible'
    );
    await roleSubmitBtn.waitFor({
      state: 'visible',
      timeout: FAST_ACTION_TIMEOUT,
    });
    await roleSubmitBtn.click();
  } catch {
    // No role selection needed.
  }

  await page
    .locator('.dashboard')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

async function setupInlineFilters() {
  await Backend.createMasterdata({
    request: {
      inlineFilters: [
        {
          tableName: 'C_Invoice',
          columnName: 'C_BPartner_ID',
          showInline: true,
          windowId: SALES_INVOICE_WINDOW_ID,
          widgetSize: 'L',
        },
        {
          tableName: 'C_Invoice',
          columnName: 'M_Warehouse_ID',
          showInline: true,
          windowId: SALES_INVOICE_WINDOW_ID,
          // no widgetSize: the layout element keeps its own (none in core)
        },
      ],
    },
  });
  // the endpoint runs on the app server; the webapi must reload the window layout
  await Backend.resetWebApiCaches();
}

/** The caption of an inline filter's parameter, as shown as placeholder of its input. */
async function getInlineFilterCaption(page, filterId) {
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/documentView/${SALES_INVOICE_WINDOW_ID}/layout?viewType=grid`
  );
  expect(response.ok()).toBeTruthy();
  const layout = await response.json();
  const filter = (layout.filters || []).find((f) => f.filterId === filterId);
  expect(filter, `inline filter ${filterId} in the layout`).toBeTruthy();
  return filter.parameters[0].caption;
}

async function openSalesInvoices(page) {
  await page.goto(`/window/${SALES_INVOICE_WINDOW_ID}`, { waitUntil: 'load' });
  await page
    .locator('.document-list-wrapper')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

function inlineFilter(page, caption) {
  const filter = page.locator('.inline-filters').filter({
    has: page.locator(`input[placeholder="${caption}"]`),
  });
  return {
    box: filter.locator('.input-dropdown-container'),
    inputArea: filter.locator('.lookup-widget-wrapper.lookup-widget-wrapper-bcg'),
    input: filter.locator(`input[placeholder="${caption}"]`),
  };
}

const dropdownList = (page) => page.locator('.input-dropdown-list');
const dropdownFooter = (page) => page.locator('.input-dropdown-hasmore');
const dropdownOptions = (page) =>
  page.locator('.input-dropdown-list .input-dropdown-list-option:not(.input-dropdown-list-header)');

/** Record a measured value in the test report. */
const measurement = (description) =>
  test.info().annotations.push({ type: 'measurement', description });

const widthOf = async (locator) =>
  await locator.evaluate((el) => el.getBoundingClientRect().width);

/** Type the query into the lookup and wait until its results (or "no results") are shown. */
async function search(page, input, query) {
  await input.fill('');
  await input.fill(query);
  await expect(dropdownList(page)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  await expect(page.locator('.input-dropdown-list .icon-rotate')).toHaveCount(0, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
}

/** A name that makes the lookup text exactly `length` characters long. */
function nameForTextLength({ length, code, textOverhead }) {
  const nameLength = length - textOverhead - code.length - 1; // core lookup text: Value_Name
  expect(nameLength, 'name length').toBeGreaterThanOrEqual(NAME_START.length);
  let name = (NAME_START + NAME_FILLER.repeat(3)).substring(0, nameLength);
  if (name.endsWith(' ')) {
    name = name.substring(0, name.length - 1) + 'x'; // the endpoint trims names
  }
  return name;
}

test.describe('Inline lookup filter takes the widget size of its field', () => {
  test.beforeEach(async ({ page }) => {
    await loginAsMetasfresh(page);
    await setupInlineFilters();
  });

  test('widget size L: box 420 px, dropdown as wide as the input, 52 characters fully visible', async ({
    page,
  }) => {
    // unique per run, so the search only finds this run's partners
    const runId = Date.now().toString(36).slice(-7);
    const code = (suffix) => `${runId}${suffix}`;

    const caption = await getInlineFilterCaption(page, SIZED_FILTER_ID);

    //
    // Probe: how long is the lookup text of a partner compared to core's Value_Name?
    const probe = { code: code('00'), name: NAME_START };
    await Backend.createMasterdata({
      request: { bpartners: { probe: { bpartnerCode: probe.code, name: probe.name } } },
    });
    await Backend.resetWebApiCaches();
    await openSalesInvoices(page);
    let filter = inlineFilter(page, caption);
    await search(page, filter.input, probe.code);
    const probeText = (await dropdownOptions(page).first().textContent()).trim();
    expect(probeText).toContain(probe.code);
    const textOverhead = probeText.length - (probe.code.length + 1 + probe.name.length);
    measurement(`probe lookup text has ${probeText.length} characters (overhead ${textOverhead})`);

    //
    // The other partners
    const partners = {
      medium: { code: code('46') },
      p99: { code: code('52') },
      long: { code: code('99') },
    };
    Object.entries(TARGET_TEXT_LENGTHS).forEach(([key, length]) => {
      partners[key].name = nameForTextLength({ length, code: partners[key].code, textOverhead });
    });
    const bpartners = {};
    Object.entries(partners).forEach(([key, p]) => {
      bpartners[key] = { bpartnerCode: p.code, name: p.name };
    });
    for (let i = 1; i <= FILLER_PARTNERS_COUNT; i++) {
      const fillerCode = code(String(i + 10)); // 11..30, distinct from 46, 52 and 99
      bpartners[`filler${i}`] = {
        bpartnerCode: fillerCode,
        name: nameForTextLength({ length: 34 + (i % 10), code: fillerCode, textOverhead }),
      };
    }
    await Backend.createMasterdata({ request: { bpartners } });
    await Backend.resetWebApiCaches();

    await openSalesInvoices(page);
    filter = inlineFilter(page, caption);

    //
    // The box is as wide as widget size L
    const boxWidth = await widthOf(filter.box);
    const inputWidth = await widthOf(filter.inputArea);
    measurement(`box ${boxWidth} px, input ${inputWidth} px`);
    expect(boxWidth).toBeGreaterThanOrEqual(L_BOX_WIDTH_PX - L_BOX_TOLERANCE_PX);
    expect(boxWidth).toBeLessThanOrEqual(L_BOX_WIDTH_PX + L_BOX_TOLERANCE_PX);

    //
    // Dropdown and footer are as wide as the input, after every key
    await filter.input.click();
    const listWidths = [];
    for (const ch of runId) {
      const typeahead = page.waitForResponse(
        (r) => r.url().includes(`/filter/${SIZED_FILTER_ID}/`) && r.url().includes('/typeahead'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await filter.input.press(ch);
      await typeahead;
      await expect(dropdownList(page)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.input-dropdown-list .icon-rotate')).toHaveCount(0, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      listWidths.push(await widthOf(dropdownList(page)));
      expect(await widthOf(filter.inputArea)).toBe(inputWidth);
    }
    await expect(dropdownFooter(page)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    const footerWidth = await widthOf(dropdownFooter(page));
    measurement(`dropdown widths while typing ${listWidths.join(', ')} px, footer ${footerWidth} px`);
    listWidths.forEach((w) => expect(w).toBe(inputWidth));
    expect(footerWidth).toBe(inputWidth);

    //
    // Entries: 30 and 46 characters fit, 52 characters fit (no ellipsis), 106 characters are ellipsized
    const fits = async (partnerCode) => {
      await search(page, filter.input, partnerCode);
      const option = dropdownOptions(page).filter({ hasText: partnerCode }).first();
      await expect(option).toBeVisible();
      return await option.evaluate((el) => ({
        text: el.textContent.trim(),
        scrollWidth: el.scrollWidth,
        clientWidth: el.clientWidth,
      }));
    };

    const shortEntry = await fits(probe.code);
    const mediumEntry = await fits(partners.medium.code);
    const p99Entry = await fits(partners.p99.code);
    const longEntry = await fits(partners.long.code);
    [shortEntry, mediumEntry, p99Entry, longEntry].forEach((e) =>
      measurement(`${e.text.length} characters: scrollWidth ${e.scrollWidth}, clientWidth ${e.clientWidth}`)
    );

    expect(mediumEntry.text.length).toBe(TARGET_TEXT_LENGTHS.medium);
    expect(p99Entry.text.length).toBe(TARGET_TEXT_LENGTHS.p99);
    expect(longEntry.text.length).toBe(TARGET_TEXT_LENGTHS.long);

    expect(shortEntry.scrollWidth).toBeLessThanOrEqual(shortEntry.clientWidth);
    expect(mediumEntry.scrollWidth).toBeLessThanOrEqual(mediumEntry.clientWidth);
    expect(p99Entry.scrollWidth, '52 characters are fully visible').toBeLessThanOrEqual(
      p99Entry.clientWidth
    );
    expect(longEntry.scrollWidth, '106 characters are ellipsized').toBeGreaterThan(
      longEntry.clientWidth
    );
    expect(
      await dropdownOptions(page)
        .filter({ hasText: partners.long.code })
        .first()
        .evaluate((el) => getComputedStyle(el).textOverflow)
    ).toBe('ellipsis');
  });

  test('no widget size: the filter keeps the default width', async ({ page }) => {
    const caption = await getInlineFilterCaption(page, UNSIZED_FILTER_ID);
    await openSalesInvoices(page);
    const filter = inlineFilter(page, caption);

    const boxWidth = await widthOf(filter.box);
    const inputWidth = await widthOf(filter.inputArea);

    // any query opens the dropdown (with results or with "no results")
    await search(page, filter.input, 'a');
    const listWidth = await widthOf(dropdownList(page));
    measurement(`box ${boxWidth} px, input ${inputWidth} px, dropdown ${listWidth} px`);

    expect(Math.abs(boxWidth - DEFAULT_BOX_WIDTH_PX)).toBeLessThanOrEqual(DEFAULT_TOLERANCE_PX);
    expect(Math.abs(inputWidth - DEFAULT_INPUT_WIDTH_PX)).toBeLessThanOrEqual(DEFAULT_TOLERANCE_PX);
    expect(listWidth).toBe(inputWidth);
  });
});
