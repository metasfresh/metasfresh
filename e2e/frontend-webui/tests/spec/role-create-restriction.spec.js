import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData, getTabInfo } from '../utils/WebAPIValidation';

/**
 * Per-role "may create new records" restriction on C_BPartner.
 *
 * The role's per-table create permission is a subtractive flag on AD_Table_Access
 * (IsCanCreateNewRecords='N' removes Access.CREATE from the role default). When a role is
 * restricted on C_BPartner, the WebUI greys the "New" record action with the AD_Message key
 * ERR_Role_CreateNewRecordsNotAllowed, and the server rejects a create — while reading/editing
 * existing partners still works. Read-only (IsReadOnly='Y') independently removes only WRITE.
 *
 * Assertions are language-invariant: the greyed action by its data-testid (the message KEY, never the
 * localized sentence — CLAUDE.md:204,206); the block by the server's explicit rejection; read/edit by
 * the record's field-level `readonly` metadata. Each record-opening scenario provisions its own
 * business partner via the masterdata API (isolated, no dependency on ambient list data). Runs in
 * en_US and de_DE.
 */

const CREATE_RESTRICTION_MSG_KEY = 'ERR_Role_CreateNewRecordsNotAllowed';
const ADRESSE_TAB_ID = 'AD_Tab-222'; // C_BPartner_Location included tab in window 123
const VORGAENGE_TAB_ID = 'AD_Tab-540829'; // R_Request included tab in window 123, IsInsertRecord='N' (forbids insert already)
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
            expect(isNewRecordUrl(urlAfterNew), `a restricted role must not obtain a new C_BPartner record (url=${urlAfterNew})`).toBe(false);
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
            expect(isNewRecordUrl(urlAfterAltN), `Alt+N must not open a new record for a restricted role (url=${urlAfterAltN})`).toBe(false);

            console.log(`[${language}] PASS — restricted role blocked from creating C_BPartner`);
        });
    });

    // The negative control: a role WITHOUT an AD_Table_Access row for C_BPartner creates a partner
    // exactly as before. Proves the block is specific to the restriction, not the harness.
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

            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
            await page.waitForURL((u) => isNewRecordUrl(u.toString()), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const recordId = page.url().split('/').pop().split('?')[0];
            console.log(`[${language}] unrestricted role created C_BPartner record ${recordId}`);
            expect(isNewRecordUrl(page.url()), 'an unrestricted role must obtain a new C_BPartner record').toBe(true);
            expect(restrictionKeyDelivered, 'no create-restriction key must be delivered for an unrestricted role').toBe(false);
            console.log(`[${language}] PASS — unrestricted role creates C_BPartner normally`);
        });
    });

    // Neutral-row: the create restriction subtracts ONLY create — a role restricted from creating
    // C_BPartner still READS and can EDIT existing partners (WRITE is not removed). Asserted on the
    // role's own purpose-created partner (isolated), via the field-level readonly metadata: this is the
    // discriminating counterpart to the read-only role's readonly=true on the same field.
    test.describe(`Role create restriction — neutral row keeps read+edit (${label})`, () => {
        test(`restricted role still reads and can edit an existing partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — read, edit, and other-table access are unaffected by the create block');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    bpartners: { bp1: {} },
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
            const recordId = String(masterdata.bpartners.bp1.id);

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // READ: open the role's own partner; it loads as a valid record.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
            await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            await assertRecordIsValid(BUSINESS_PARTNER_WINDOW_ID, recordId, 'restricted role reads an existing partner');

            // EDIT still allowed: the create restriction subtracts CREATE only, not WRITE — fields stay editable.
            const name2 = await getFieldData(BUSINESS_PARTNER_WINDOW_ID, recordId, 'Name2');
            expect(name2, 'the restricted role must still READ the partner fields').toBeTruthy();
            expect(name2.readonly, 'a create-restricted role must still be able to EDIT (WRITE not removed)').toBe(false);

            // THIRD (AC9 — no leak to other tables): a row maps ONLY to its own table under the direct
            // mapping (§ 3.2c). The role restricts C_BPartner only; C_BPartner_Location has no row for it,
            // so creating an address stays allowed. This is the exact discriminator against the included-tab
            // scenario (role restricted on C_BPartner_Location → allowCreateNew=false there, true here).
            const addrTab = await getTabInfo(BUSINESS_PARTNER_WINDOW_ID, recordId, ADRESSE_TAB_ID);
            expect(addrTab.allowCreateNew, 'the C_BPartner create restriction must NOT leak to C_BPartner_Location (address creation stays allowed)').toBe(true);
            console.log(`[${language}] PASS — reads + edits partner AND restriction does not leak to other tables (Name2.readonly=${name2.readonly}, Adresse allowCreateNew=${addrTab.allowCreateNew})`);
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

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    bpartners: { bp1: {} },
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
            const recordId = String(masterdata.bpartners.bp1.id);

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // READ-YES: the record loads and its fields are readable.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
            await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const name2 = await getFieldData(BUSINESS_PARTNER_WINDOW_ID, recordId, 'Name2');
            expect(name2, 'a read-only role must still READ the partner fields').toBeTruthy();

            // EDIT-NO: WRITE is subtracted, so the loaded record's field is read-only.
            expect(name2.readonly, 'a read-only role must NOT be able to edit (WRITE removed by the subtract)').toBe(true);
            console.log(`[${language}] PASS — read-only role: read yes, edit no (Name2.readonly=${name2.readonly})`);
        });
    });

    // Included tab: a role restricted from creating C_BPartner_Location cannot add a new address on an
    // existing partner — the Adresse included tab reports allowCreateNew=false. Opening an EXISTING
    // (already-persisted) partner rules out the "parent is new" reason, so a false here is the role
    // restriction, discriminating from an unrestricted role (allowCreateNew=true).
    test.describe(`Role create restriction — included address tab disabled (${label})`, () => {
        test(`restricted role cannot add an address on an existing partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — included address tab disabled with the reason');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    bpartners: { bp1: {} },
                    roles: {
                        restricted: {
                            name: `AddrRestricted_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner_Location', canCreateNewRecords: false }],
                            user: { language },
                        },
                    },
                },
            });
            const roleUser = masterdata.roles.restricted.user;
            expect(roleUser, 'masterdata must return the restricted role user').toBeTruthy();
            const recordId = String(masterdata.bpartners.bp1.id);

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // Open the role's own existing partner and read the Adresse included-tab info.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
            await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const tabInfo = await getTabInfo(BUSINESS_PARTNER_WINDOW_ID, recordId, ADRESSE_TAB_ID);
            console.log(`[${language}] Adresse tabInfo: ${JSON.stringify(tabInfo)}`);
            expect(tabInfo.allowCreateNew, 'the address included tab must forbid creating a new address for the restricted role').toBe(false);
            console.log(`[${language}] PASS — restricted role cannot add an address (allowCreateNew=false)`);
        });
    });

    // TC3 — tab-level insert block unchanged (AC3, AC4). On a tab that ALREADY forbids insert — Vorgänge
    // (R_Request), included tab 540829 of window 123, IsInsertRecord='N' on the customer stack — creation
    // stays blocked and NO role reason ever surfaces, whatever the role's own R_Request row says. Three role
    // states prove it: no row; an ALLOWING row (AC4: setting "allowed" never enables what the tab forbids);
    // and a FORBIDDING row (the tab's block, not the role's, is what shows). No AD metadata is changed — the
    // tab forbids insert already.
    const TC3_STATES = [
        { key: 'norow', stateLabel: 'no row', tableAccess: undefined },
        { key: 'allow', stateLabel: 'allowing row', tableAccess: [{ tableName: 'R_Request', canCreateNewRecords: true }] },
        { key: 'forbid', stateLabel: 'forbidding row', tableAccess: [{ tableName: 'R_Request', canCreateNewRecords: false }] },
    ];
    TC3_STATES.forEach(({ key, stateLabel, tableAccess }) => {
        test.describe(`Role create restriction — tab-forbidden insert unchanged, ${stateLabel} (${label})`, () => {
            test(`tab-forbidden insert stays blocked with no role reason — ${stateLabel} (${label} UI)`, async ({ page }) => {
                allure.epic('E0390: Business Partner');
                allure.story('Role create restriction — a tab that forbids insert is unchanged by the role setting');
                allure.tag('F33020: Roles');
                allure.tag('F33020');
                allure.severity('critical');
                allure.parameter('Language', language);
                allure.parameter('RoleState', stateLabel);
                allure.tag(language);
                test.setTimeout(120000);

                const role = { name: `TabBlock_${key}_${language}`, user: { language } };
                if (tableAccess) role.tableAccess = tableAccess;
                const masterdata = await Backend.createMasterdata({
                    request: { login: { user: { language } }, bpartners: { bp1: {} }, roles: { r: role } },
                });
                const roleUser = masterdata.roles.r.user;
                expect(roleUser, 'masterdata must return the role user').toBeTruthy();
                const recordId = String(masterdata.bpartners.bp1.id);

                await LoginPage.goto();
                await LoginPage.login(roleUser);
                await DashboardPage.expectVisible();

                await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
                await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });

                const tab = await getTabInfo(BUSINESS_PARTNER_WINDOW_ID, recordId, VORGAENGE_TAB_ID);
                console.log(`[${language}] Vorgänge tabInfo (${stateLabel}): ${JSON.stringify(tab)}`);
                // The tab forbids insert structurally → creation is not allowed regardless of the role row (AC3/AC4).
                expect(tab.allowCreateNew, `a tab that forbids insert must not allow create (${stateLabel})`).not.toBe(true);
                // The block is the TAB's, never the role's — no role reason key surfaces on it.
                expect(tab.allowCreateNewReasonKey ?? null, `no role reason may surface on a tab-forbidden insert (${stateLabel})`).not.toBe(CREATE_RESTRICTION_MSG_KEY);
                console.log(`[${language}] PASS — tab-forbidden insert unchanged, no role reason (${stateLabel})`);
            });
        });
    });

    // TC8 — inclusion resolves the create permission by UNION (AC16). A role's effective create permission
    // is the union of its own and every included role's AD_Table_Access: a row grants or withholds CREATE,
    // and where NO role in the chain has a row the table falls back to the role default (allowed). Four role
    // graphs, each asserted on the greyed-New restriction key in the documentView payload. Case 4 pins the
    // sole exception to the restrictive-only invariant — a plain granting row in the default (toolkit) shape,
    // exactly what bulk role configuration writes, lifts a forbid under the union.
    const TC8_CASES = [
        {
            key: 'forbid-propagates', caseLabel: 'forbid on an included role propagates', expectRestricted: true,
            roles: {
                base: { name: `UnionBase_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                chain: { name: `UnionChain_${language}`, includedRoles: ['base'], user: { language } },
            },
        },
        {
            key: 'explicit-allow-lifts', caseLabel: 'an allowing row in the chain lifts the forbid', expectRestricted: false,
            roles: {
                base: { name: `UnionFbase_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                granter: { name: `UnionGrant_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: true }] },
                chain: { name: `UnionAllow_${language}`, includedRoles: ['base', 'granter'], user: { language } },
            },
        },
        {
            key: 'no-row-anywhere', caseLabel: 'no row anywhere in the chain is unaffected', expectRestricted: false,
            roles: {
                chain: { name: `UnionNorow_${language}`, user: { language } },
            },
        },
        {
            key: 'toolkit-grant-lifts', caseLabel: 'a plain granting row (toolkit default shape) lifts the forbid', expectRestricted: false,
            roles: {
                base: { name: `UnionTKbase_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                // Every flag left at its default => IsCanCreateNewRecords='Y': the exact row support.ad_role_ensure_table_access writes.
                granter: { name: `UnionTKgrant_${language}`, tableAccess: [{ tableName: 'C_BPartner' }] },
                chain: { name: `UnionTKchain_${language}`, includedRoles: ['base', 'granter'], user: { language } },
            },
        },
    ];
    TC8_CASES.forEach(({ key, caseLabel, expectRestricted, roles }) => {
        test.describe(`Role create restriction — inclusion resolves by union, ${caseLabel} (${label})`, () => {
            test(`union: ${caseLabel} (${label} UI)`, async ({ page }) => {
                allure.epic('E0390: Business Partner');
                allure.story('Role create restriction — inclusion resolves the create permission by union');
                allure.tag('F33020: Roles');
                allure.tag('F33020');
                allure.severity('critical');
                allure.parameter('Language', language);
                allure.parameter('UnionCase', caseLabel);
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

                const masterdata = await Backend.createMasterdata({ request: { login: { user: { language } }, roles } });
                const roleUser = masterdata.roles.chain.user;
                expect(roleUser, 'masterdata must return the chain role user').toBeTruthy();

                await LoginPage.goto();
                await LoginPage.login(roleUser);
                await DashboardPage.expectVisible();

                if (expectRestricted) {
                    await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
                    await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                    await expect
                        .poll(() => restrictionKeyDelivered, { timeout: VERY_SLOW_ACTION_TIMEOUT, message: 'the union must resolve to RESTRICTED (create key delivered)' })
                        .toBe(true);
                    const greyedNew = page.getByTestId(`disabledReasonKey-${CREATE_RESTRICTION_MSG_KEY}`);
                    await ensureSubheaderOpen(page, greyedNew);
                    await expect(greyedNew, 'union restricted → the "New" action is greyed with the restriction key').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
                    console.log(`[${language}] PASS — union RESTRICTED (${caseLabel})`);
                } else {
                    await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
                    await page.waitForURL((u) => isNewRecordUrl(u.toString()), { timeout: VERY_SLOW_ACTION_TIMEOUT });
                    expect(isNewRecordUrl(page.url()), 'union allowed → a new C_BPartner record is created').toBe(true);
                    expect(restrictionKeyDelivered, 'no restriction key when the union resolves to ALLOWED').toBe(false);
                    console.log(`[${language}] PASS — union ALLOWED (${caseLabel})`);
                }
            });
        });
    });
});
