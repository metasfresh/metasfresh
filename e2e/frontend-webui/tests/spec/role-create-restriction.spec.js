import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Per-role "may create new records" restriction on C_BPartner.
 *
 * The role's per-table create permission is a subtractive flag on AD_Table_Access
 * (IsCanCreateNewRecords='N' removes Access.CREATE from the role default). When a role is
 * restricted on C_BPartner, the WebUI must refuse creating a new business partner: the "New"
 * record action is greyed with the AD_Message key ERR_Role_CreateNewRecordsNotAllowed, and a
 * create is rejected by the server — while reading/editing existing partners still works.
 *
 * TC1 — the customer's real case (top-level action, greyed out). Covers AC1, AC2, AC5.
 *
 * Assertions are language-invariant: the greyed-New explanation is asserted by its message KEY as
 * delivered in the webapi documentView payload (never the localized sentence — CLAUDE.md:204,206),
 * and the block itself by behaviour (a create is refused). Runs in en_US and de_DE.
 */

const CREATE_RESTRICTION_MSG_KEY = 'ERR_Role_CreateNewRecordsNotAllowed';
const isNewRecordUrl = (url) => /\/window\/\d+\/\d+(\?|$)/.test(url);

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

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

            // The webapi documentView payload carries the per-role create-restriction reason key — this
            // is exactly what the frontend renders as the greyed "New" action's data-testid. Assert on it
            // (language-invariant) rather than driving the lazily-rendered, timing-sensitive burger menu.
            let restrictionKeyDelivered = false;
            page.on('response', async (r) => {
                try {
                    if (!(r.headers()['content-type'] || '').includes('json')) return;
                    if (!r.url().includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) return;
                    if ((await r.text()).includes(CREATE_RESTRICTION_MSG_KEY)) restrictionKeyDelivered = true;
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

            // Step 3: open the Business Partner window (123) — this fetches the documentView payload.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({
                state: 'visible',
                timeout: VERY_SLOW_ACTION_TIMEOUT,
            });

            // Step 4 (AC2): the create restriction reaches the WebUI carrying its message KEY — the data
            // the frontend renders as the greyed "New" action.
            await expect
                .poll(() => restrictionKeyDelivered, {
                    timeout: VERY_SLOW_ACTION_TIMEOUT,
                    message: `the documentView payload must carry the create-restriction key ${CREATE_RESTRICTION_MSG_KEY}`,
                })
                .toBe(true);
            console.log(`[${language}] create restriction delivered with key ${CREATE_RESTRICTION_MSG_KEY}`);

            // Step 5 (AC1/AC5, behavioural — the customer requirement): navigating straight to a new
            // record must be REFUSED for the restricted role — the server rejects the create, so the URL
            // never resolves to a persisted record (/window/123/<numericId>).
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
            await page.waitForTimeout(3000);
            const urlAfterNew = page.url();
            console.log(`[${language}] URL after direct /NEW: ${urlAfterNew}`);
            expect(
                isNewRecordUrl(urlAfterNew),
                `a restricted role must not obtain a new C_BPartner record (url=${urlAfterNew})`
            ).toBe(false);

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
});
