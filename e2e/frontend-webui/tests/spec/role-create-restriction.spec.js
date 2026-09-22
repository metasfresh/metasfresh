import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData } from '../utils/WebAPIValidation';

/**
 * Per-role "may create new records" restriction on C_BPartner.
 *
 * The role's per-table create permission is a subtractive flag on AD_Table_Access
 * (IsCanCreateNewRecords='N' removes Access.CREATE from the role default). When a role is
 * restricted on C_BPartner, the WebUI greys the "New" record action with the AD_Message key
 * ERR_Role_CreateNewRecordsNotAllowed, and the server rejects a create — while reading/editing
 * existing partners still works.
 *
 * TC1 — the customer's real case (top-level action, greyed out). Covers AC1, AC2, AC5.
 *
 * Assertions are language-invariant: AC2 by the greyed action's data-testid (the message KEY,
 * never the localized sentence — CLAUDE.md:204,206); AC1/AC5 by the server's explicit rejection of
 * the create request. Runs in en_US and de_DE.
 */

const CREATE_RESTRICTION_MSG_KEY = 'ERR_Role_CreateNewRecordsNotAllowed';
const isNewRecordUrl = (url) => /\/window\/\d+\/\d+(\?|$)/.test(url);

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

// Open the toggle-gated document sub-header (top-left ⋮). It is NOT always visible — it is gated by
// Header state (isSubheaderShow), toggled by the ⋮. A trusted click does not reach the React handler
// on headless Chrome; a DOM element.click() does. The toggle nature means a stray click while it is
// mid-open can close it again, so click ONLY while it is closed, then wait for it to render — de_DE
// paints it more slowly, which is why this is a bounded poll rather than a single click.
async function ensureSubheaderOpen(page, targetLocator) {
    const burger = page.locator('.btn-square.btn-header').first();
    await burger.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    const anyItem = page.locator('.js-subheader-item');
    for (let i = 0; i < 8; i++) {
        if (await targetLocator.isVisible().catch(() => false)) return;
        if (!(await anyItem.first().isVisible().catch(() => false))) {
            await burger.evaluate((el) => el.click()).catch(() => {});
            await anyItem.first().waitFor({ state: 'visible', timeout: 6000 }).catch(() => {});
        }
        await page.waitForTimeout(600);
    }
}

testCases.forEach(({ language, label }) => {
    test.describe(`Role create restriction — C_BPartner (${label})`, () => {
        test(`restricted role cannot create a business partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — New is greyed for a restricted role');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);

            test.setTimeout(120000);

            // The webapi documentView payload carries the per-role create-restriction reason key — this is
            // what the frontend renders as the greyed "New" action's data-testid; used as an AC2 precondition.
            let restrictionKeyDelivered = false;
            // AC5: while a create is attempted, the server must reject it (an error response on the window
            // endpoint) — captured explicitly rather than inferred from a timeout.
            let expectingCreate = false;
            let serverRejectedCreate = false;
            page.on('response', async (r) => {
                try {
                    const url = r.url();
                    if (url.includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) {
                        if ((r.headers()['content-type'] || '').includes('json') && (await r.text()).includes(CREATE_RESTRICTION_MSG_KEY)) {
                            restrictionKeyDelivered = true;
                        }
                    }
                    if (expectingCreate && url.includes(`/window/${BUSINESS_PARTNER_WINDOW_ID}`) && r.status() >= 400) {
                        serverRejectedCreate = true;
                    }
                } catch (e) { /* response body not readable — ignore */ }
            });

            // Step 1: a purpose-created role restricted from creating C_BPartner, with a user bound
            // only to that role (so login skips role selection).
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    roles: {
                        restricted: {
                            name: `CreateRestricted_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }],
                            user: { language },
                        },
                    },
                },
            });
            allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');
            const roleUser = masterdata.roles.restricted.user;
            expect(roleUser, 'masterdata must return the restricted role user').toBeTruthy();
            console.log(`[${language}] restricted role user: ${roleUser.username}, role=${masterdata.roles.restricted.name}`);

            // Step 2: log in as the restricted role's user.
            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // Step 3: open the Business Partner window (123).
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({
                state: 'visible',
                timeout: VERY_SLOW_ACTION_TIMEOUT,
            });

            // Step 4 (AC2): the create restriction reaches the WebUI (precondition), and the "New" record
            // action is actually rendered GREYED, carrying the restriction message KEY in its data-testid.
            await expect
                .poll(() => restrictionKeyDelivered, {
                    timeout: VERY_SLOW_ACTION_TIMEOUT,
                    message: `the documentView payload must carry the create-restriction key ${CREATE_RESTRICTION_MSG_KEY}`,
                })
                .toBe(true);
            const greyedNew = page.getByTestId(`disabledReasonKey-${CREATE_RESTRICTION_MSG_KEY}`);
            await ensureSubheaderOpen(page, greyedNew);
            await expect(greyedNew, 'the "New" record action must be rendered greyed with the restriction key').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect(greyedNew, 'the greyed "New" action must carry the disabled class').toHaveClass(/subheader-item-disabled/);
            console.log(`[${language}] "New" is greyed with key ${CREATE_RESTRICTION_MSG_KEY}`);
            await page.keyboard.press('Escape');
            await page.waitForTimeout(300);

            // Step 5 (AC1/AC5): a create must be REFUSED — the server rejects it (error response) and no
            // persisted record results (the URL never resolves to /window/123/<numericId>).
            expectingCreate = true;
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
            await page.waitForTimeout(3000);
            const urlAfterNew = page.url();
            console.log(`[${language}] URL after direct /NEW: ${urlAfterNew} ; serverRejectedCreate=${serverRejectedCreate}`);
            expect(serverRejectedCreate, 'the server must reject the create request for a restricted role').toBe(true);
            expect(
                isNewRecordUrl(urlAfterNew),
                `a restricted role must not obtain a new C_BPartner record (url=${urlAfterNew})`
            ).toBe(false);
            expectingCreate = false;

            // Step 6 (AC1): Alt+N must NOT create a new record either — the URL stays on the list view.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await page.locator('body').click();
            await page.waitForTimeout(200);
            await page.keyboard.press('Alt+N');
            await page.waitForTimeout(2000);
            const urlAfterAltN = page.url();
            console.log(`[${language}] URL after Alt+N: ${urlAfterAltN}`);
            expect(
                isNewRecordUrl(urlAfterAltN),
                `Alt+N must not open a new record for a restricted role (url=${urlAfterAltN})`
            ).toBe(false);

            console.log(`[${language}] PASS — restricted role blocked from creating C_BPartner`);
        });
    });

    // TC2 — the negative control: a role WITHOUT an AD_Table_Access row for C_BPartner creates a
    // partner exactly as before. Proves the block is specific to the restriction, not the harness.
    test.describe(`Role create restriction — unrestricted control (${label})`, () => {
        test(`role without the restriction creates a business partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — unrestricted role is unaffected');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            let restrictionKeyDelivered = false;
            page.on('response', async (r) => {
                try {
                    if (!(r.headers()['content-type'] || '').includes('json')) return;
                    if (!r.url().includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) return;
                    if ((await r.text()).includes(CREATE_RESTRICTION_MSG_KEY)) restrictionKeyDelivered = true;
                } catch (e) { /* ignore */ }
            });

            // A purpose-created role with NO table-access row (defaults to the WebUI role, full create access).
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    roles: { open: { name: `Unrestricted_${language}`, user: { language } } },
                },
            });
            const roleUser = masterdata.roles.open.user;
            expect(roleUser, 'masterdata must return the unrestricted role user').toBeTruthy();

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // Creating a new C_BPartner succeeds: the URL resolves to a persisted record.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
            await page.waitForURL((u) => isNewRecordUrl(u.toString()), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const recordId = page.url().split('/').pop().split('?')[0];
            console.log(`[${language}] unrestricted role created C_BPartner record ${recordId}`);
            expect(isNewRecordUrl(page.url()), 'an unrestricted role must obtain a new C_BPartner record').toBe(true);

            // And no create-restriction key was ever delivered for this role.
            expect(restrictionKeyDelivered, 'no create-restriction key must be delivered for an unrestricted role').toBe(false);
            console.log(`[${language}] PASS — unrestricted role creates C_BPartner normally`);
        });
    });

    // Neutral-row: the create restriction subtracts ONLY create — a role restricted from creating
    // C_BPartner still READS and EDITS existing partners normally (WRITE is not removed).
    test.describe(`Role create restriction — neutral row keeps read+edit (${label})`, () => {
        test(`restricted role still reads and edits an existing partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — read and edit are unaffected by the create block');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            // Capture an existing partner's id from the list documentView payload (the response returns a
            // bpartner code, not a numeric id, so the list row is the reliable handle to an existing record).
            let firstRowId = null;
            page.on('response', async (r) => {
                try {
                    if (firstRowId) return;
                    if (!r.url().includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) return;
                    if (!(r.headers()['content-type'] || '').includes('json')) return;
                    const j = JSON.parse(await r.text());
                    if (Array.isArray(j.result) && j.result.length > 0 && j.result[0].id != null) firstRowId = String(j.result[0].id);
                } catch (e) { /* ignore */ }
            });

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    roles: {
                        restricted: {
                            name: `CreateRestrictedRW_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }],
                            user: { language },
                        },
                    },
                },
            });
            const roleUser = masterdata.roles.restricted.user;
            expect(roleUser, 'masterdata must return the restricted role user').toBeTruthy();

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // Open the list, capture an existing partner id.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect.poll(() => firstRowId, { timeout: VERY_SLOW_ACTION_TIMEOUT, message: 'an existing partner row id must be available' }).not.toBeNull();
            console.log(`[${language}] existing partner id = ${firstRowId}`);

            // READ: the restricted role opens the existing partner and it loads as a valid record.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${firstRowId}`);
            await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${firstRowId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            await assertRecordIsValid(BUSINESS_PARTNER_WINDOW_ID, firstRowId, 'restricted role reads an existing partner');

            // EDIT still allowed: the create restriction subtracts CREATE only, not WRITE — so the loaded
            // record's fields remain editable (readonly=false). This is the discriminating counterpart to
            // the read-only role's readonly=true on the same field.
            const name2 = await getFieldData(BUSINESS_PARTNER_WINDOW_ID, firstRowId, 'Name2');
            expect(name2, 'the restricted role must still READ the partner fields').toBeTruthy();
            expect(name2.readonly, 'a create-restricted role must still be able to EDIT (WRITE not removed)').toBe(false);
            console.log(`[${language}] PASS — restricted role reads + can edit existing partner (Name2.readonly=${name2.readonly})`);
        });
    });

    // Read-only-flag subtract: IsReadOnly='Y' removes WRITE only — read-yes / edit-no. This is the one
    // assertion that distinguishes the subtract contract from the old replace encoding.
    test.describe(`Role read-only table access — read yes, edit no (${label})`, () => {
        test(`read-only role reads but cannot edit an existing partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role table access — read-only flag removes WRITE only (subtract contract)');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            let firstRowId = null;
            page.on('response', async (r) => {
                try {
                    if (firstRowId) return;
                    if (!r.url().includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) return;
                    if (!(r.headers()['content-type'] || '').includes('json')) return;
                    const j = JSON.parse(await r.text());
                    if (Array.isArray(j.result) && j.result.length > 0 && j.result[0].id != null) firstRowId = String(j.result[0].id);
                } catch (e) { /* ignore */ }
            });

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    roles: {
                        readonly: {
                            name: `ReadOnlyBP_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', readOnly: true }],
                            user: { language },
                        },
                    },
                },
            });
            const roleUser = masterdata.roles.readonly.user;
            expect(roleUser, 'masterdata must return the read-only role user').toBeTruthy();

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect.poll(() => firstRowId, { timeout: VERY_SLOW_ACTION_TIMEOUT, message: 'an existing partner row id must be available' }).not.toBeNull();

            // READ-YES: the record loads and its fields are readable.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${firstRowId}`);
            await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${firstRowId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const name2 = await getFieldData(BUSINESS_PARTNER_WINDOW_ID, firstRowId, 'Name2');
            expect(name2, 'a read-only role must still READ the partner fields').toBeTruthy();

            // EDIT-NO: WRITE is subtracted, so every field on the loaded record is read-only.
            expect(name2.readonly, 'a read-only role must NOT be able to edit (WRITE removed by the subtract)').toBe(true);
            console.log(`[${language}] PASS — read-only role: read yes, edit no (Name2.readonly=${name2.readonly})`);
        });
    });
});
