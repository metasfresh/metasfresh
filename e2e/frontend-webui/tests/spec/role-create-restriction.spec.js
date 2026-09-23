import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID, SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData, getTabInfo, getRecordData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

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
            // The block must be attributed to the ROLE (not a parent-is-new or tab-readonly reason): opening an
            // EXISTING partner rules out "parent is new", so the reason key proves it is the role restriction.
            expect(tabInfo.allowCreateNewReasonKey, 'the address tab must be disabled with the role restriction reason key').toBe(CREATE_RESTRICTION_MSG_KEY);
            console.log(`[${language}] PASS — restricted role cannot add an address (allowCreateNew=false, reasonKey=${tabInfo.allowCreateNewReasonKey})`);
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
                expect(tab.allowCreateNew, `a tab that forbids insert must not allow create (${stateLabel})`).toBe(false);
                // The block is the TAB's, never the role's — no role reason key surfaces on it.
                expect(tab.allowCreateNewReasonKey ?? null, `no role reason may surface on a tab-forbidden insert (${stateLabel})`).not.toBe(CREATE_RESTRICTION_MSG_KEY);
                console.log(`[${language}] PASS — tab-forbidden insert unchanged, no role reason (${stateLabel})`);
            });
        });
    });

    // TC8 — inclusion resolves the create permission by UNION (AC16). A role's effective create permission
    // is the union of its own and every included role's AD_Table_Access: a row grants or withholds CREATE,
    // and where NO role in the chain has a row the table falls back to the role default (allowed). Three
    // distinct role graphs, each asserted on the greyed-New restriction key in the documentView payload.
    // The "granting-row-lifts" case is the sole exception to the restrictive-only invariant, and its granter
    // row is written in the plain default (toolkit) shape — every flag at its default, IsCanCreateNewRecords='Y'
    // — i.e. the exact row support.ad_role_ensure_table_access writes when bulk role config re-opens a table.
    // (There is deliberately no separate "explicit true" case: the masterdata DTO defaults canCreateNewRecords
    // to true, so an omitted flag and an explicit `true` produce a byte-identical AD_Table_Access row — a
    // second case would exercise nothing new. See JsonRoleTableAccessRequest.canCreateNewRecords @Builder.Default.)
    const TC8_CASES = [
        {
            key: 'forbid-propagates', caseLabel: 'forbid on an included role propagates', expectRestricted: true,
            roles: {
                base: { name: `UnionBase_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                chain: { name: `UnionChain_${language}`, includedRoles: ['base'], user: { language } },
            },
        },
        {
            key: 'granting-row-lifts', caseLabel: 'a plain granting row (toolkit default shape) in the chain lifts the forbid', expectRestricted: false,
            roles: {
                base: { name: `UnionFbase_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                // Every flag left at its default => IsCanCreateNewRecords='Y': the exact row support.ad_role_ensure_table_access writes.
                granter: { name: `UnionGrant_${language}`, tableAccess: [{ tableName: 'C_BPartner' }] },
                chain: { name: `UnionAllow_${language}`, includedRoles: ['base', 'granter'], user: { language } },
            },
        },
        {
            key: 'no-row-anywhere', caseLabel: 'no row anywhere in the chain is unaffected', expectRestricted: false,
            roles: {
                chain: { name: `UnionNorow_${language}`, user: { language } },
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

    // TC15 — clone cannot bypass the restriction (AC18). A restricted role opens an existing partner: a
    // clone request against the /duplicate endpoint is rejected, and the Clone action is not rendered.
    // An unrestricted role clones normally. The endpoint is the real enforcement boundary
    // (DocumentCollection.duplicateDocumentInTrx → checkRoleCanCreateNewRecords); the DOM check confirms
    // the Clone standard action is removed (JSONDocumentPermissions drops it from standardActions).
    const TC15_CASES = [
        { key: 'restricted', restricted: true },
        { key: 'unrestricted', restricted: false },
    ];
    TC15_CASES.forEach(({ key, restricted }) => {
        test.describe(`Role create restriction — clone ${restricted ? 'blocked' : 'allowed'} (${label})`, () => {
            test(`clone ${restricted ? 'rejected for a restricted role' : 'works for an unrestricted role'} (${label} UI)`, async ({ page }) => {
                allure.epic('E0390: Business Partner');
                allure.story('Role create restriction — clone is closed for a restricted role');
                allure.tag('F33020: Roles');
                allure.tag('F33020');
                allure.severity('critical');
                allure.parameter('Language', language);
                allure.parameter('Role', key);
                allure.tag(language);
                test.setTimeout(120000);

                const role = restricted
                    ? { name: `CloneRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }], user: { language } }
                    : { name: `CloneOpen_${language}`, user: { language } };
                const masterdata = await Backend.createMasterdata({
                    request: { login: { user: { language } }, bpartners: { bp1: {} }, roles: { r: role } },
                });
                const roleUser = masterdata.roles.r.user;
                expect(roleUser, 'masterdata must return the role user').toBeTruthy();
                const roleName = masterdata.roles.r.name;
                const recordId = String(masterdata.bpartners.bp1.id);

                await LoginPage.goto();
                await LoginPage.login(roleUser);
                await DashboardPage.expectVisible();

                await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
                await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
                await assertRecordIsValid(BUSINESS_PARTNER_WINDOW_ID, recordId, 'clone test opens the partner');

                // Discriminating enforcement: the server-computed standardActions set the WebUI builds the
                // action menu from. Clone is REMOVED from it for a restricted role and KEPT for an unrestricted
                // one (JSONDocumentPermissions.getStandardActions it.remove() when create is not allowed).
                // Language-invariant (the action's internalName 'clone'), and independent of menu rendering.
                const record = await getRecordData(BUSINESS_PARTNER_WINDOW_ID, recordId);
                const standardActions = record.standardActions || [];
                console.log(`[${language}] standardActions (${key}) = ${JSON.stringify(standardActions)}`);

                if (restricted) {
                    expect(standardActions, 'Clone must be removed from the standard actions for a restricted role').not.toContain('clone');
                    // Defense in depth: the /duplicate endpoint itself rejects, blocked at the role permission
                    // check BEFORE any DB write (a plain AdempiereException, never reaching the DB layer). The
                    // message is localized, so assert on the language-invariant exception class, not the text.
                    const dupResp = await page.request.post(
                        `${WEBAPI_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}/duplicate`,
                        { headers: { 'Content-Type': 'application/json' } },
                    );
                    const dupBody = await dupResp.text().catch(() => '');
                    console.log(`[${language}] /duplicate (restricted) = ${dupResp.status()} body=${dupBody.slice(0, 240)}`);
                    expect(dupResp.status(), 'a restricted role must be refused at the /duplicate endpoint').toBeGreaterThanOrEqual(400);
                    // Pin the CREATE-restriction branch specifically: duplicateDocumentInTrx throws a plain
                    // AdempiereException from two branches (canEdit=false → cloning-not-allowed, a static message
                    // with no role name; create-check → the create-restriction message, which names the ROLE). The
                    // restricted role's edit is left open here, so the create branch fires — proven by the body
                    // naming the role. The role name is a generated, language-invariant token (the message's {0}).
                    expect(dupBody, 'the /duplicate refusal must be the role create-restriction (names the role), not a cloning/window error').toContain(roleName);
                    expect(dupBody, 'the restricted clone must be blocked at the permission gate, never reaching the DB').not.toContain('DBUniqueConstraint');
                    console.log(`[${language}] PASS — clone blocked (no 'clone' standard action; /duplicate permission-rejected ${dupResp.status()})`);
                } else {
                    expect(standardActions, 'Clone must be available in the standard actions for an unrestricted role').toContain('clone');
                    console.log(`[${language}] PASS — clone available (standard action present)`);
                }
            });
        });
    });

    // TC16 — the menu new-record node is hidden (AC18). A C_BPartner window menu node with IsCreateNew='Y'
    // (the "Neuer Geschäftspartner" node) produces a newRecord menu node ONLY when the role may create
    // C_BPartner (MenuTreeLoader.createNewRecordNode returns null otherwise). The desktop menu exposes NO
    // language-invariant per-node DOM handle, so this is asserted on the menu REST tree (type:"newRecord").
    // The node's elementId is deployment-dependent — the core Business Partner window on core CI, the
    // customer override window on the customer stack — so instead of hardcoding a window id, this compares
    // the newRecord-node set of an unrestricted role with that of a role restricted ONLY on C_BPartner:
    // restricting create must STRICTLY REMOVE node(s) (the business-partner one) and ADD none.
    test.describe(`Role create restriction — menu new-record node hidden (${label})`, () => {
        test(`restricting C_BPartner create removes the business-partner new-record menu node (${label})`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — the new-record menu node is hidden for a restricted role');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const md = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    roles: {
                        open: { name: `MenuOpen_${language}`, user: { language } },
                        restricted: { name: `MenuRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }], user: { language } },
                    },
                },
            });
            expect(md.roles.open.user && md.roles.restricted.user, 'masterdata must return both role users').toBeTruthy();

            // Collect the set of newRecord-node window ids in a role's full menu tree. Clears cookies first so
            // the second login starts from a clean session (otherwise the login form never appears).
            const newRecordWindowIds = async (user) => {
                await page.context().clearCookies();
                await LoginPage.goto();
                await LoginPage.login(user);
                await DashboardPage.expectVisible();
                const resp = await page.request.get(`${WEBAPI_BASE_URL}/menu/root?depth=50&childrenLimit=0`, { headers: { 'Content-Type': 'application/json' } });
                expect(resp.ok(), 'the menu tree must load').toBe(true);
                // Parse + recursively walk the tree (robust to JSON field ordering) collecting newRecord elementIds.
                const tree = JSON.parse(await resp.text());
                const ids = new Set();
                const walk = (node) => {
                    if (!node || typeof node !== 'object') return;
                    if (node.type === 'newRecord' && node.elementId != null) ids.add(String(node.elementId));
                    (node.children || []).forEach(walk);
                };
                walk(tree);
                return ids;
            };

            const openIds = await newRecordWindowIds(md.roles.open.user);
            const restrictedIds = await newRecordWindowIds(md.roles.restricted.user);
            console.log(`[${language}] newRecord window ids: open=${JSON.stringify([...openIds])} restricted=${JSON.stringify([...restrictedIds])}`);

            const removed = [...openIds].filter((id) => !restrictedIds.has(id));
            const added = [...restrictedIds].filter((id) => !openIds.has(id));
            // Restricting ONLY C_BPartner create is per-table, so the ONLY newRecord node(s) that may vanish
            // are the C_BPartner window's — the business-partner "new record" menu entry. It must vanish, and
            // nothing may be added. Environment-independent: no window id is hardcoded.
            expect(added, 'restricting create must not ADD any new-record menu node').toEqual([]);
            expect(removed.length, 'restricting C_BPartner create must remove the business-partner new-record menu node').toBeGreaterThanOrEqual(1);
            console.log(`[${language}] PASS — restricted menu removed new-record node(s) ${JSON.stringify(removed)}, added none`);
        });
    });

    // TC14 — the quick-input "new business partner" entry in a C_BPartner lookup is hidden (AC18). Typing a
    // no-match string in a C_BPartner lookup offers the "Neuer Geschäftspartner" entry (data-testid option-NEW)
    // for an unrestricted role and nothing for a role restricted on C_BPartner. The option is gated by
    // newRecordCaption, nulled when create is not allowed (JSONDocumentLayoutElementField → isTableAccess
    // CREATE). Driven through the RENDERED dropdown. The lookup is reached on a new Invoice (the role is
    // restricted only on C_BPartner, so it may still create an invoice); identical steps for both roles, so
    // the only variable is the create restriction.
    const TC14_CASES = [
        { key: 'restricted', restricted: true },
        { key: 'unrestricted', restricted: false },
    ];
    TC14_CASES.forEach(({ key, restricted }) => {
        test.describe(`Role create restriction — quick-input new-partner ${restricted ? 'hidden' : 'offered'} (${label})`, () => {
            test(`the C_BPartner lookup ${restricted ? 'hides' : 'offers'} the new-partner entry (${label} UI)`, async ({ page }) => {
                allure.epic('E0390: Business Partner');
                allure.story('Role create restriction — the lookup quick-input new-partner entry is hidden for a restricted role');
                allure.tag('F33020: Roles');
                allure.tag('F33020');
                allure.severity('critical');
                allure.parameter('Language', language);
                allure.parameter('Role', key);
                allure.tag(language);
                test.setTimeout(120000);

                const role = restricted
                    ? { name: `QuickRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }], user: { language } }
                    : { name: `QuickOpen_${language}`, user: { language } };
                const md = await Backend.createMasterdata({ request: { login: { user: { language } }, roles: { r: role } } });
                expect(md.roles.r.user, 'masterdata must return the role user').toBeTruthy();

                await LoginPage.goto();
                await LoginPage.login(md.roles.r.user);
                await DashboardPage.expectVisible();

                // Reach a C_BPartner lookup on a new Invoice, then type a no-match string.
                await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_INVOICE_WINDOW_ID}/NEW`);
                const bpInput = page.locator('#lookup_C_BPartner_ID input').first();
                await bpInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                await bpInput.click();
                await bpInput.fill(`ZZZ_NOMATCH_${Date.now()}`);

                const optionNew = page.getByTestId('option-NEW');
                if (restricted) {
                    // Wait for the dropdown to finish rendering its typeahead response (a no-results header or any
                    // option) BEFORE asserting the new-partner entry is absent — otherwise count 0 passes trivially
                    // at t=0, before the async round-trip completes (a vacuous pass under CI/cold-JVM load). The
                    // unrestricted branch's toBeVisible proves option-NEW DOES appear under identical steps.
                    await page.locator('.input-dropdown-list-header, .input-dropdown-list-option').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                    await expect(optionNew, 'the new-partner quick-input entry must be hidden for a restricted role').toHaveCount(0);
                    console.log(`[${language}] PASS — quick-input new-partner hidden`);
                } else {
                    await expect(optionNew, 'the new-partner quick-input entry must be offered for an unrestricted role').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
                    console.log(`[${language}] PASS — quick-input new-partner offered`);
                }
            });
        });
    });

    // TC6 — the administrator lifts the restriction (AC7). A restricted role's user cannot create; an
    // administrator clears the role's C_BPartner AD_Table_Access restriction in the Roles window (111); the
    // affected user, in a NEW session, can create again — no app restart (the permission cache invalidates on
    // the change). The "administrator" is the default login user (WebUI role, which has write access to window
    // 111); it deletes the restricted role's C_BPartner (AD_Table 291) row on the Table Access tab (AD_Tab-549493),
    // which returns the role to "no row" = allowed.
    const ROLES_WINDOW_ID = 111;
    const TABLE_ACCESS_TAB_ID = 'AD_Tab-549493';
    const C_BPARTNER_AD_TABLE_ID = '291';
    test.describe(`Role create restriction — administrator lifts it (${label})`, () => {
        test(`clearing the restriction re-enables creation without a restart (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — an administrator can lift it, no restart needed');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const md = await Backend.createMasterdata({
                request: {
                    login: { user: { language } },
                    roles: { restricted: { name: `LiftRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }], user: { language } } },
                },
            });
            const restrictedUser = md.roles.restricted.user;
            const roleId = md.roles.restricted.roleId;
            const adminUser = md.login.user; // WebUI role — has write access to the Roles window (111)
            expect(restrictedUser && roleId && adminUser, 'masterdata must return the restricted user, role id, and admin user').toBeTruthy();

            const canCreate = async (user) => {
                await page.context().clearCookies();
                await LoginPage.goto();
                await LoginPage.login(user);
                await DashboardPage.expectVisible();
                await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
                await page.waitForTimeout(3000);
                return isNewRecordUrl(page.url());
            };

            // BEFORE: the restricted user cannot create a C_BPartner.
            expect(await canCreate(restrictedUser), 'before lifting: the restricted role must NOT obtain a new record').toBe(false);

            // ADMIN lifts it: delete the role's C_BPartner table-access row via the Roles window (111).
            await page.context().clearCookies();
            await LoginPage.goto();
            await LoginPage.login(adminUser);
            await DashboardPage.expectVisible();
            const rowsResp = await page.request.get(`${WEBAPI_BASE_URL}/window/${ROLES_WINDOW_ID}/${roleId}/${TABLE_ACCESS_TAB_ID}`, { headers: { 'Content-Type': 'application/json' } });
            expect(rowsResp.ok(), 'the admin must be able to read the role table-access rows').toBe(true);
            const rows = (await rowsResp.json()).result || [];
            const bpRow = rows.find((r) => String(r.fieldsByName?.AD_Table_ID?.value?.key) === C_BPARTNER_AD_TABLE_ID);
            expect(bpRow, 'the role must have a C_BPartner (291) table-access row to clear').toBeTruthy();
            const delResp = await page.request.delete(`${WEBAPI_BASE_URL}/window/${ROLES_WINDOW_ID}/${roleId}/${TABLE_ACCESS_TAB_ID}/${bpRow.rowId}`, { headers: { 'Content-Type': 'application/json' } });
            console.log(`[${language}] admin DELETE table-access row ${bpRow.rowId} = ${delResp.status()}`);
            expect(delResp.ok(), 'the administrator must be able to clear the restriction row').toBe(true);

            // AFTER: the restricted user, in a NEW session, can now create — no app restart.
            expect(await canCreate(restrictedUser), 'after lifting: the role can create a new record (no restart)').toBe(true);
            console.log(`[${language}] PASS — administrator lifted the restriction; creation re-enabled without restart`);
        });
    });
});
