import { expect } from '@playwright/test';
import { FRONTEND_BASE_URL } from './common';
import { WEBAPI_BASE_URL } from './WebAPIValidation';

/**
 * Helpers for specs that prepare their data through the web API (REST) and then drive the UI.
 * Every REST setup call goes through the `request` fixture, every UI interaction through `page`: authenticating
 * on `page.request` would log the browser in as `metasfresh` and the UI login then dies on "User already logged in".
 */

/** @returns the first document of a web API response body (array, `{documents}` or a single document). */
export const firstDocument = (body) => (Array.isArray(body) ? body : body.documents || [body])[0];

/** Logs the `request` fixture in (as the setup user) unless it is logged in already. */
export const authenticateRestSetupSession = async (request) => {
  const sessionBody = await (await request.get(`${WEBAPI_BASE_URL}/userSession`)).json().catch(() => ({}));
  if (!sessionBody.loggedIn) {
    const authResponse = await request.post(`${WEBAPI_BASE_URL}/login/authenticate`, {
      data: { username: 'metasfresh', password: 'metasfresh' },
    });
    const authBody = await authResponse.json();
    if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
      await request.post(`${WEBAPI_BASE_URL}/login/loginComplete`, { data: authBody.roles[0] });
    }
  }
};

/** PATCHes a document (or one of its included rows when `tabId` is given) and returns the resulting document. */
export const patchDocument = async (request, windowId, documentId, tabId, rowId, changes) => {
  const path = tabId
    ? `${WEBAPI_BASE_URL}/window/${windowId}/${documentId}/${tabId}/${rowId}`
    : `${WEBAPI_BASE_URL}/window/${windowId}/${documentId}`;
  const response = await request.patch(path, { data: changes });
  if (!response.ok()) {
    throw new Error(`PATCH ${path} failed: HTTP ${response.status()} ${await response.text()}`);
  }
  return firstDocument(await response.json());
};

/**
 * Opens a window on a view scoped to exactly the given row ids (JSONCreateViewRequest.filterOnlyIds), created in the
 * browser's own session so the UI can open it via ?viewId=. Scoping to the test's own ids makes the lookup
 * independent of how many other rows a shared DB holds (paging, other runs).
 */
export const openWindowOnOwnRows = async (page, windowId, rowIds) => {
  const viewId = await page.evaluate(
    async ({ rest, windowId, ids }) => {
      const created = await fetch(`${rest}/documentView/${windowId}`, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify({ windowId: String(windowId), viewType: 'grid', filterOnlyIds: ids }),
      });
      if (!created.ok) {
        throw new Error(`view scoped to the own rows failed: HTTP ${created.status}`);
      }
      return (await created.json()).viewId;
    },
    { rest: WEBAPI_BASE_URL, windowId, ids: rowIds.map(Number) },
  );
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}?viewId=${viewId}`);
};

/**
 * Selects a grid row. The grid auto-selects its first row on load and a click on a selected row DESELECTS it, so
 * click only when the row is not selected yet, then assert it is.
 */
export const selectRowUnlessSelected = async (row) => {
  if (!/row-selected/.test((await row.getAttribute('class')) || '')) {
    await row.click();
  }
  await expect(row).toHaveClass(/row-selected/);
};

/**
 * The rows of a window that satisfy `isOwnRow`, read from a FRESH server-side view (newest first via `orderBy`,
 * paged to the end), so neither the grid's cached state nor other runs' records are involved. Use it for windows
 * without a filter field.
 * @param {{request: object, windowId: number|string, orderBy: string, isOwnRow: (row: object) => boolean}} options
 */
export const fetchOwnViewRows = async ({ request, windowId, orderBy, isOwnRow }) => {
  const view = await (
    await request.post(`${WEBAPI_BASE_URL}/documentView/${windowId}`, {
      data: { windowId: String(windowId), viewType: 'grid' },
    })
  ).json();

  const PAGE_LENGTH = 500;
  const ownRows = [];
  for (let firstRow = 0; ; firstRow += PAGE_LENGTH) {
    const rowsPage = await (
      await request.get(
        `${WEBAPI_BASE_URL}/documentView/${windowId}/${view.viewId}` +
          `?firstRow=${firstRow}&pageLength=${PAGE_LENGTH}&orderBy=${orderBy}`,
      )
    ).json();
    const rows = rowsPage.result || [];
    ownRows.push(...rows.filter(isOwnRow));
    if (rows.length < PAGE_LENGTH) {
      return ownRows;
    }
  }
};
