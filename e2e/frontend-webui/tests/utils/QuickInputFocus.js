import { expect } from '@playwright/test';
import { SLOW_ACTION_TIMEOUT } from './common';
import { SALES_ORDER_WINDOW_ID } from './WindowIds';
import { getRecordData, waitForRecordSaved } from './WebAPIValidation';

/**
 * Helpers for the keyboard-only order-line quick entry (batch entry) tests: deterministic focus
 * checks that only count once the order header reload triggered by a step has happened.
 */

const ORDER_LINE_GRID_ROWS = '.table-flex-wrapper table tbody tr';

// ---------------------------------------------------------------------------
// Header-reload recorder
// ---------------------------------------------------------------------------

/**
 * Record every document websocket frame whose body has a top-level `stale === true` and every
 * GET /window/<windowId>/<recordId> response. Call it before LoginPage.goto() (the websocket is
 * opened when the app boots).
 */
export function installDocumentEventRecorder(page) {
  const recorder = { frames: [], responses: [], parseErrors: 0 };

  const handleStompFrame = (stompFrame) => {
    const sep = stompFrame.indexOf('\n\n');
    if (sep < 0) return;
    const headerLines = stompFrame.slice(0, sep).split('\n');
    if (headerLines[0].trim() !== 'MESSAGE') return;
    const destLine = headerLines.find((l) => l.startsWith('destination:'));
    const destination = destLine ? destLine.slice('destination:'.length).trim() : '';
    const m = destination.match(/^\/document\/([^/]+)\/([^/]+)$/);
    if (!m) return;
    const body = stompFrame
      .slice(sep + 2)
      .replace(/\u0000+$/, '')
      .trim();
    try {
      const parsed = JSON.parse(body);
      if (parsed && parsed.stale === true) {
        recorder.frames.push({
          t: Date.now(),
          destination,
          windowId: String(parsed.windowId ?? m[1]),
          recordId: String(parsed.id ?? m[2]),
          payload: parsed,
        });
      }
    } catch (error) {
      recorder.parseErrors++;
      console.error('[WARN] unparsable document frame', destination, error, stompFrame.slice(0, 200));
    }
  };

  page.on('websocket', (ws) => {
    ws.on('framereceived', (frame) => {
      let text = frame.payload;
      if (typeof text !== 'string') text = text.toString('utf8');
      if (!text.startsWith('a')) return; // o, h, c[...] and others are ignored
      let stompFrames;
      try {
        stompFrames = JSON.parse(text.slice(1));
      } catch (error) {
        recorder.parseErrors++;
        console.error('[WARN] unparsable document frame', '(sockjs array)', error, text.slice(0, 200));
        return;
      }
      if (!Array.isArray(stompFrames)) return;
      stompFrames.forEach((f) => typeof f === 'string' && handleStompFrame(f));
    });
  });

  page.on('response', (response) => {
    try {
      if (response.request().method() !== 'GET') return;
      const path = new URL(response.url()).pathname;
      if (/\/rest\/api\/window\/\d+\/[^/]+\/?$/.test(path)) {
        recorder.responses.push({ t: Date.now(), response, path });
      }
    } catch (e) {
      // ignore
    }
  });

  return recorder;
}

/** A mark taken before the key press. */
export function armHeaderReload(recorder) {
  return { frameIdx: recorder.frames.length, respIdx: recorder.responses.length };
}

// requireIncludedTabs: only a frame that also carries includedTabsInfo (the line-save reload) counts,
// so a late header-only frame of an earlier step cannot satisfy a line-save wait.
const matchesDoc = (frame, windowId, recordId, requireIncludedTabs = false) =>
  frame.destination === `/document/${windowId}/${recordId}` &&
  (!requireIncludedTabs || !!frame.payload?.includedTabsInfo);

/** True if a header-reload frame for the document was recorded after the mark (no waiting). */
export function hasReloadSince(recorder, mark, { windowId, recordId, requireIncludedTabs = false }) {
  return recorder.frames.slice(mark.frameIdx).some((f) => matchesDoc(f, windowId, recordId, requireIncludedTabs));
}

/** Wait for the stale frame and the header GET that follows it, then for the rendering to settle. */
export async function awaitHeaderReload(
  page,
  recorder,
  mark,
  { windowId, recordId, requireIncludedTabs = false, timeout = SLOW_ACTION_TIMEOUT, step = 'header reload' }
) {
  const deadline = Date.now() + timeout;
  const failWith = (missing) =>
    new Error(
      `${step}: no header reload (${missing}) within ${timeout} ms; stale frames recorded after the mark: ${recorder.frames.length - mark.frameIdx}; unparsable frames: ${recorder.parseErrors}`
    );

  let frame;
  while (!(frame = recorder.frames.slice(mark.frameIdx).find((f) => matchesDoc(f, windowId, recordId, requireIncludedTabs)))) {
    if (Date.now() > deadline) throw failWith(`no stale event for /document/${windowId}/${recordId}`);
    await page.waitForTimeout(100);
  }

  let resp;
  const pathOk = (p) => p.endsWith(`/window/${windowId}/${recordId}`) || p.endsWith(`/window/${windowId}/${recordId}/`);
  while (!(resp = recorder.responses.slice(mark.respIdx).find((r) => pathOk(r.path) && r.t >= frame.t))) {
    if (Date.now() > deadline) throw failWith(`stale event seen, but no GET /window/${windowId}/${recordId} after it; GETs recorded after the mark: ${recorder.responses.length - mark.respIdx}`);
    await page.waitForTimeout(100);
  }

  await resp.response.finished();
  await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
}

// ---------------------------------------------------------------------------
// Focus checks (failure message: "<description>: ... <ELEMENT> in lookup_<Column>")
// ---------------------------------------------------------------------------

/** True when the element is the document's active element (no window-focus dependency). */
async function isFocused(locator) {
  return await locator.evaluate((el) => el === document.activeElement).catch(() => false);
}

/** Describe the active element, for failure messages. */
async function describeActiveElement(page) {
  return await page.evaluate(() => {
    const e = document.activeElement;
    if (!e) return 'none';
    const lookup = e.closest('[id^="lookup_"]');
    return `${e.tagName}${e.id ? '#' + e.id : ''}${lookup ? ' in ' + lookup.id : ''}${
      e.closest('.quick-input-container') ? ' (quick input)' : ''
    }`;
  });
}

/** Wait until the given element has the focus. */
export async function expectFocusOn(locator, description) {
  const page = locator.page();
  const deadline = Date.now() + SLOW_ACTION_TIMEOUT;
  while (!(await isFocused(locator))) {
    if (Date.now() > deadline) {
      throw new Error(`${description}: cursor is not in the expected field, it is in ${await describeActiveElement(page)}`);
    }
    await page.waitForTimeout(100);
  }
}

/** Assert the element keeps the focus for the whole period, sampled every 100 ms. */
export async function expectFocusHeld(locator, millis, description) {
  const page = locator.page();
  const deadline = Date.now() + millis;
  while (Date.now() < deadline) {
    if (!(await isFocused(locator))) {
      throw new Error(`${description}: cursor moved to ${await describeActiveElement(page)}`);
    }
    await page.waitForTimeout(100);
  }
}

/** awaitHeaderReload, then the cursor must be in the field and stay there for 1000 ms. */
export async function expectFocusAfterReload(locator, page, recorder, mark, { windowId, recordId, requireIncludedTabs }, description) {
  await awaitHeaderReload(page, recorder, mark, { windowId, recordId, requireIncludedTabs, step: description });
  await expectFocusOn(locator, description);
  await expectFocusHeld(locator, 1000, description);
}

// ---------------------------------------------------------------------------
// Flow helpers
// ---------------------------------------------------------------------------

/** Record id from the current URL: last path segment without ?query / #hash / trailing slash. */
function currentRecordId(page) {
  return page
    .url()
    .replace(/[?#].*$/, '')
    .replace(/\/+$/, '')
    .split('/')
    .pop();
}

async function waitForOrderSaved(page) {
  await expect
    .poll(() => currentRecordId(page), { timeout: SLOW_ACTION_TIMEOUT, message: 'order URL carries a record id' })
    .toMatch(/^\d+$/);
  const recordId = currentRecordId(page);
  // The header is filled by the server after the partner pick; a validation error before that is
  // transient, so retry until the record is saved.
  const deadline = Date.now() + 40000;
  for (;;) {
    try {
      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 6, retryDelayMs: 500 });
      return recordId;
    } catch (error) {
      if (Date.now() > deadline) throw error;
      await page.waitForTimeout(1000);
    }
  }
}

/**
 * Customer picked by keyboard: the cursor is in the partner input after Alt+N; type the code,
 * wait for the highlighted option, Enter. No Tab, no click. Returns the record id.
 */
export async function pickCustomerByKeyboard({ bpartnerCode }) {
  const page = global.currentPage;
  const partnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
  await partnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await expectFocusOn(partnerInput, 'partner input after Alt+N');
  await page.keyboard.type(bpartnerCode, { delay: 50 });
  // The options are portaled out of #lookup_C_BPartner_ID (RawLookup TetherComponent), so page-level.
  await expect(page.locator('.input-dropdown-list .input-dropdown-list-option-key-on')).toContainText(bpartnerCode, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page.keyboard.press('Enter');
  return await waitForOrderSaved(page);
}

/** Customer picked by mouse from the lookup list. No Tab. Returns the record id. */
export async function pickCustomerByMouse({ searchText, bpartnerCode }) {
  const page = global.currentPage;
  const partnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
  await partnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await partnerInput.click();
  await partnerInput.fill(searchText);
  const option = page.locator('.input-dropdown-list-option').filter({ hasText: bpartnerCode }).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  return await waitForOrderSaved(page);
}

/**
 * Wait for the observable end of the customer pick's walk through the header: the saved location and
 * the caret resting in the contact field (Lookup walks partner -> location -> contact and waits there),
 * then two animation frames. A header reload that follows the contact step is not waited for: it is not
 * emitted in every run, so no deterministic signal exists. Used before the quick entry is opened by
 * the button, so the walk cannot land after the open and take the cursor away.
 */
export async function awaitPartnerAdvanceSettled({ windowId, recordId }) {
  const page = global.currentPage;
  await expect
    .poll(
      async () => {
        const f = (await getRecordData(windowId, recordId)).fieldsByName;
        const key = f?.C_BPartner_Location_ID?.value?.key;
        return key === undefined || key === null ? 'unset' : 'set';
      },
      { timeout: SLOW_ACTION_TIMEOUT, message: 'partner advance: C_BPartner_Location_ID saved' }
    )
    .toBe('set');
  await expect(page.locator('#lookup_AD_User_ID :focus'), 'partner advance: caret moved on to the contact field').toHaveCount(1, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
}

/**
 * Enter one order line per product by keyboard only; after every step the cursor is checked once
 * the header reload that the step triggers has happened.
 */
export async function enterLinesKeyboardOnly({
  recorder,
  windowId,
  recordId,
  products,
  qtys,
  packingInstructionsField,
  rowsBefore = 0,
}) {
  const page = global.currentPage;
  const quickInput = page.locator('.quick-input-container');
  const productInput = quickInput.locator('#lookup_M_Product_ID input.input-field');
  const qtyInput = quickInput.locator('input[inputmode="decimal"]');
  const piInput = quickInput.locator('#lookup_M_HU_PI_Item_Product_ID input.input-field');
  const doc = { windowId, recordId };

  for (let i = 0; i < products.length; i++) {
    const n = i + 1;
    await expectFocusOn(productInput, `opened line ${n}: cursor in Produkt`);
    await page.keyboard.type(products[i], { delay: 30 });
    try {
      await page
        .locator('.input-dropdown-list-option')
        .filter({ hasText: products[i] })
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    } catch (error) {
      // No option: if the cursor left Produkt while typing, name the field that holds it.
      if (!(await isFocused(productInput))) {
        throw new Error(`typing article, line ${n}: cursor left Produkt, it is in ${await describeActiveElement(page)}`);
      }
      throw error;
    }
    // The article Enter is not always followed by a header reload (none on line 1 in several runs):
    // wait for the article PATCH, check the cursor, and only re-check after a reload that did come.
    let mark = armHeaderReload(recorder);
    const articlePatch = page.waitForResponse(
      (r) => r.request().method() === 'PATCH' && /\/quickInput\/[^/]+$/.test(new URL(r.url()).pathname),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    await page.keyboard.press('Enter');
    await articlePatch;
    const afterArticle = packingInstructionsField === 'Y' ? piInput : qtyInput;
    const afterArticleName = packingInstructionsField === 'Y' ? 'Packvorschrift' : 'Menge';
    await expectFocusOn(afterArticle, `after article Enter, line ${n}: cursor in ${afterArticleName}`);
    await expectFocusHeld(afterArticle, 1000, `after article Enter, line ${n}: cursor stays in ${afterArticleName}`);
    if (hasReloadSince(recorder, mark, doc)) {
      await awaitHeaderReload(page, recorder, mark, { ...doc, step: `after article Enter, line ${n}` });
      await expectFocusOn(afterArticle, `after article reload, line ${n}: cursor in ${afterArticleName}`);
      await expectFocusHeld(afterArticle, 1000, `after article reload, line ${n}: cursor stays in ${afterArticleName}`);
    }
    // Frames of the article step handled so far; a later header-only frame is caught after typing the quantity.
    const articleRespIdx = mark.respIdx;
    let handledFrames = recorder.frames.length;

    if (packingInstructionsField === 'Y') {
      mark = armHeaderReload(recorder);
      await page.keyboard.press('Enter'); // empty Packvorschrift
      await expectFocusOn(qtyInput, `after Packvorschrift Enter, line ${n}: cursor in Menge`);
      await expectFocusHeld(qtyInput, 1000, `after Packvorschrift Enter, line ${n}: cursor stays in Menge`);
      if (hasReloadSince(recorder, mark, doc)) {
        await awaitHeaderReload(page, recorder, mark, { ...doc, step: `after Packvorschrift Enter, line ${n}` });
        await expectFocusOn(qtyInput, `after Packvorschrift reload, line ${n}: cursor in Menge`);
        await expectFocusHeld(qtyInput, 1000, `after Packvorschrift reload, line ${n}: cursor stays in Menge`);
      }
      handledFrames = recorder.frames.length;
    }

    await expectFocusOn(qtyInput, `before typing quantity, line ${n}: cursor in Menge`);
    await page.keyboard.type(String(qtys[i]), { delay: 30 });
    // A header-only frame of the article / Packvorschrift step may arrive while the quantity is typed:
    // await it and check the cursor and the typed value again before the line is saved.
    const lateMark = { frameIdx: handledFrames, respIdx: articleRespIdx };
    if (hasReloadSince(recorder, lateMark, doc)) {
      await awaitHeaderReload(page, recorder, lateMark, { ...doc, step: `late article reload after typing quantity, line ${n}` });
      await expectFocusOn(qtyInput, `late article reload, line ${n}: cursor in Menge`);
      await expectFocusHeld(qtyInput, 1000, `late article reload, line ${n}: cursor stays in Menge`);
      await expect(qtyInput, `late article reload, line ${n}: typed quantity still in Menge`).toHaveValue(String(qtys[i]));
    }
    // Own mark right before the quantity Enter: only the line-save reload (it carries includedTabsInfo) counts.
    mark = armHeaderReload(recorder);
    await page.keyboard.press('Enter');
    await expect(page.locator(ORDER_LINE_GRID_ROWS)).toHaveCount(rowsBefore + n, { timeout: SLOW_ACTION_TIMEOUT });
    await expectFocusAfterReload(productInput, page, recorder, mark, { ...doc, requireIncludedTabs: true }, `back in Produkt after line ${n}`);
  }
}

async function pollRecord(windowId, recordId, predicate, description) {
  for (let i = 0; i < 20; i++) {
    const record = await getRecordData(windowId, recordId);
    if (predicate(record)) return record;
    await new Promise((r) => setTimeout(r, 1000));
  }
  throw new Error(`Timeout waiting for: ${description}`);
}

/** Close the quick entry with Alt+Q and complete the order with Alt+U (keyboard only). */
export async function closeQuickInputAndComplete({ recorder, windowId, recordId }) {
  const page = global.currentPage;
  await page.keyboard.press('Alt+Q');
  await page.locator('.quick-input-container').waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
  await page.keyboard.press('Alt+U');
  await pollRecord(
    windowId,
    recordId,
    (r) => r.fieldsByName?.DocStatus?.value?.key === 'CO',
    `order ${recordId} completed via Alt+U`
  );
}
