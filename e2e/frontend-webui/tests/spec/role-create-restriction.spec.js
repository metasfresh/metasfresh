import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID, SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData, getTabInfo, getRecordData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Per-role "may create new records" restriction on C_BPartner.
 *
 * The role's per-table create permission is a subtractive flag on AD_Table_Access
 * (IsCanCreateNewRecords='N' removes Access.CREATE from the role default). When a role is
 * restricted on C_BPartner, the WebUI greys the "New" record action with the AD_Message key
 * ERR_Role_CreateNewRecordsNotAllowed, and the server rejects a create — while reading/editing
 * existing partners still works. Read-only (IsReadOnly='Y') removes WRITE - and, since creating is a
 * write, CREATE with it ("WRITE exclusion includes no CREATE, but not the other way around"); removing
 * CREATE alone leaves WRITE (edit) intact.
 *
 * Assertions are language-invariant: the greyed action by its data-testid (the message KEY, never the
 * localized sentence — CLAUDE.md:204,206); the block by the server's explicit rejection; read/edit by
 * the record's field-level `readonly` metadata. Each record-opening scenario provisions its own
 * business partner via the masterdata API (isolated, no dependency on ambient list data). Runs in
 * en_US and de_DE.
 */

const CREATE_RESTRICTION_MSG_KEY = 'ERR_Role_CreateNewRecordsNotAllowed';
const BPARTNER_QUICK_INPUT_WINDOW_ID = 540327; // "Neuer Geschäftspartner" — C_BPartner_QuickInput window
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

// Drive the real WebUI "create new BPartner via Quick Input" server sequence directly against the API
// (the UI hides the option for a restricted role, so the fail-open can only be reproduced by replaying the
// server calls the frontend makes). Sequence discovered from the frontend action + WindowRestController:
//   1. POST /address                            -> a new address (C_Location) document
//   2. PATCH /address/{id} (C_Country_ID, City) + POST /address/{id}/complete -> the C_Location lookup value
//   3. PATCH /window/540327/NEW                  -> a new C_BPartner_QuickInput template document
//   4. PATCH /window/540327/{docId}             -> fill Companyname, C_BP_Group_ID, C_Location_ID (make it valid)
//   5. POST  /window/540327/{docId}/processNewRecord -> creates the C_BPartner (returns its repo id) or refuses
// Returns { status, body, createdId } where createdId is the created C_BPartner id (a positive int) or null.
async function createBPartnerViaQuickInput(page) {
    const H = { 'Content-Type': 'application/json' };
    const WIN = BPARTNER_QUICK_INPUT_WINDOW_ID;

    // 1./2. address document with a valid country, then complete it into a C_Location lookup value
    const addrResp = await page.request.post(`${WEBAPI_BASE_URL}/address`, { headers: H, data: { templateId: 0 } });
    const addrId = (await addrResp.json()).id;
    const countryDropdown = await (await page.request.get(`${WEBAPI_BASE_URL}/address/${addrId}/field/C_Country_ID/dropdown`, { headers: H })).json();
    const country = (countryDropdown.values || [])[0];
    const addrEvents = [
        { op: 'replace', path: 'C_Country_ID', value: country.key },
        { op: 'replace', path: 'City', value: `QI Testcity ${Date.now()}` },
    ];
    await page.request.patch(`${WEBAPI_BASE_URL}/address/${addrId}`, { headers: H, data: addrEvents });
    const locValue = await (await page.request.post(`${WEBAPI_BASE_URL}/address/${addrId}/complete`, { headers: H, data: { events: addrEvents } })).json();

    // 3. new quick-input template document
    const createResp = await page.request.patch(`${WEBAPI_BASE_URL}/window/${WIN}/NEW`, { headers: H, data: [] });
    const createBody = await createResp.json();
    const docData = createBody.documents ? createBody.documents[0] : (Array.isArray(createBody) ? createBody[0] : createBody);
    const docId = docData.id;

    // 4. fill the mandatory template fields so the create would actually succeed (pins the gate, not validation)
    const groupDropdown = await (await page.request.get(`${WEBAPI_BASE_URL}/window/${WIN}/${docId}/field/C_BP_Group_ID/dropdown`, { headers: H })).json();
    const group = (groupDropdown.values || [])[0];
    await page.request.patch(`${WEBAPI_BASE_URL}/window/${WIN}/${docId}`, {
        headers: H,
        data: [
            { op: 'replace', path: 'Companyname', value: `QI BP ${Date.now()}` },
            { op: 'replace', path: 'C_BP_Group_ID', value: group.key },
            { op: 'replace', path: 'C_Location_ID', value: locValue },
        ],
    });

    // 5. the create endpoint under test
    const pr = await page.request.post(`${WEBAPI_BASE_URL}/window/${WIN}/${docId}/processNewRecord`, { headers: H, data: {} });
    const body = await pr.text();
    const trimmed = (body || '').trim();
    const createdId = /^\d+$/.test(trimmed) && Number(trimmed) > 0 ? Number(trimmed) : null;
    return { status: pr.status(), body, createdId };
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
            let isRestrictionKeyDelivered = false;
            // AC5: while a create is attempted, the server must reject it (an error response on the window
            // endpoint) — captured explicitly rather than inferred from a timeout.
            let isExpectingCreate = false;
            let hasServerRejectedCreate = false;
            page.on('response', async (r) => {
                try {
                    const url = r.url();
                    if (url.includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) {
                        if ((r.headers()['content-type'] || '').includes('json') && (await r.text()).includes(CREATE_RESTRICTION_MSG_KEY)) {
                            isRestrictionKeyDelivered = true;
                        }
                    }
                    if (isExpectingCreate && url.includes(`/window/${BUSINESS_PARTNER_WINDOW_ID}`) && r.status() >= 400) {
                        hasServerRejectedCreate = true;
                    }
                } catch (e) { /* response body not readable — ignore */ }
            });

            // Step 1: a purpose-created role restricted from creating C_BPartner, with a user bound
            // only to that role (so login skips role selection).
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language, role: 'restricted' } },
                    roles: {
                        restricted: {
                            name: `CreateRestricted_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }],
                        },
                    },
                },
            });
            allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');
            const roleUser = masterdata.login.user;
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
                .poll(() => isRestrictionKeyDelivered, {
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
            isExpectingCreate = true;
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
            await page.waitForTimeout(3000);
            const urlAfterNew = page.url();
            console.log(`[${language}] URL after direct /NEW: ${urlAfterNew} ; hasServerRejectedCreate=${hasServerRejectedCreate}`);
            expect(hasServerRejectedCreate, 'the server must reject the create request for a restricted role').toBe(true);
            expect(isNewRecordUrl(urlAfterNew), `a restricted role must not obtain a new C_BPartner record (url=${urlAfterNew})`).toBe(false);
            isExpectingCreate = false;

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

    // TC-QI (AC1/AC5, server enforcement) — the "create new BPartner via Quick Input" path must be refused
    // SERVER-SIDE for a restricted role. This is the ENDPOINT-ENFORCEMENT backstop; the VIDEO-VISIBLE counterpart
    // of this case is TC14 (the restricted role never sees the "new business partner" option in the lookup). The
    // option being hidden does not by itself protect the endpoint, so a direct/replayed POST to
    // /window/540327/{doc}/processNewRecord must still be refused. This drives the exact server sequence the
    // frontend uses (createBPartnerViaQuickInput)
    // and asserts the server REJECTS it (non-2xx + the role-create-not-allowed reason naming the role) and creates
    // NO C_BPartner. Language-invariant: asserts on the HTTP status and the generated role-name token (the reason's
    // {0}), never localized text.
    test.describe(`Role create restriction — Quick Input create refused server-side (${label})`, () => {
        test(`restricted role is refused at processNewRecord (${label})`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — the BPartner Quick Input create endpoint enforces the role restriction');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language, role: 'restricted' } },
                    roles: {
                        restricted: {
                            name: `QICreateRestricted_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }],
                        },
                    },
                },
            });
            const roleUser = masterdata.login.user;
            const roleName = masterdata.roles.restricted.name;
            expect(roleUser, 'masterdata must return the restricted role user').toBeTruthy();

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            const result = await createBPartnerViaQuickInput(page);
            console.log(`[${language}] Quick Input processNewRecord (restricted) -> status=${result.status} createdId=${result.createdId} body=${result.body.slice(0, 200)}`);

            // The server must refuse the create (an error response), with the role create-restriction reason (which
            // names the role — the {0} of ERR_Role_CreateNewRecordsNotAllowed), and NO C_BPartner may be created.
            expect(result.status, `processNewRecord must refuse a restricted role (was ${result.status})`).toBeGreaterThanOrEqual(400);
            expect(result.body, 'the refusal must be the role create-restriction reason (names the restricted role)').toContain(roleName);
            expect(result.createdId, 'no C_BPartner may be created for a restricted role via Quick Input').toBeNull();
            console.log(`[${language}] PASS — Quick Input create refused server-side for the restricted role`);
        });
    });

    // TC-QI control — an UNRESTRICTED role creates a BPartner through the very same Quick Input endpoint, proving
    // the gate is scoped to the restriction and does not block everyone. Its video-visible counterpart is
    // TC14-unrestricted (the option is offered and the New Business Partner dialog opens on-screen).
    test.describe(`Role create restriction — Quick Input create allowed for unrestricted role (${label})`, () => {
        test(`unrestricted role creates a business partner via processNewRecord (${label})`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — the BPartner Quick Input create endpoint stays open for an unrestricted role');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language, role: 'open' } },
                    roles: { open: { name: `QIUnrestricted_${language}` } },
                },
            });
            const roleUser = masterdata.login.user;
            expect(roleUser, 'masterdata must return the unrestricted role user').toBeTruthy();

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            const result = await createBPartnerViaQuickInput(page);
            console.log(`[${language}] Quick Input processNewRecord (unrestricted) -> status=${result.status} createdId=${result.createdId}`);

            expect(result.status, `an unrestricted role must be allowed to create via Quick Input (was ${result.status}, body=${result.body.slice(0, 200)})`).toBe(200);
            expect(result.createdId, 'an unrestricted role must create a C_BPartner via Quick Input').toBeGreaterThan(0);
            console.log(`[${language}] PASS — unrestricted role creates a C_BPartner via Quick Input`);
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

            let isRestrictionKeyDelivered = false;
            page.on('response', async (r) => {
                try {
                    if (!(r.headers()['content-type'] || '').includes('json')) return;
                    if (!r.url().includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) return;
                    if ((await r.text()).includes(CREATE_RESTRICTION_MSG_KEY)) isRestrictionKeyDelivered = true;
                } catch (e) { /* ignore */ }
            });

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language, role: 'open' } },
                    roles: { open: { name: `Unrestricted_${language}` } },
                },
            });
            const roleUser = masterdata.login.user;
            expect(roleUser, 'masterdata must return the unrestricted role user').toBeTruthy();

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
            await page.waitForURL((u) => isNewRecordUrl(u.toString()), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const recordId = page.url().split('/').pop().split('?')[0];
            console.log(`[${language}] unrestricted role created C_BPartner record ${recordId}`);
            expect(isNewRecordUrl(page.url()), 'an unrestricted role must obtain a new C_BPartner record').toBe(true);
            expect(isRestrictionKeyDelivered, 'no create-restriction key must be delivered for an unrestricted role').toBe(false);
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
                    login: { user: { language, role: 'restricted' } },
                    bpartners: { bp1: {} },
                    roles: {
                        restricted: {
                            name: `CreateRestrictedRW_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }],
                        },
                    },
                },
            });
            const roleUser = masterdata.login.user;
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

            // EDIT (visible + end result): actually type a new Name2 in the UI, blur to save, await the PATCH,
            // and confirm it PERSISTED. The recording shows the restricted role editing an existing partner
            // (WRITE is not removed by the create restriction).
            const newName2 = `Edited ${Date.now()}`;
            const name2Input = page.locator('.form-field-Name2 input').first();
            await name2Input.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            const patchDone = page.waitForResponse(
                (r) => r.url().includes(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`) && r.request().method() === 'PATCH',
                { timeout: VERY_SLOW_ACTION_TIMEOUT },
            );
            await name2Input.fill(newName2);
            await name2Input.blur();
            await patchDone;
            const name2After = await getFieldData(BUSINESS_PARTNER_WINDOW_ID, recordId, 'Name2');
            expect(name2After.value, "the restricted role's edit must persist (WRITE not removed)").toBe(newName2);
            console.log(`[${language}] restricted role edited Name2 -> "${newName2}" (persisted)`);

            // THIRD (AC9 — no leak to other tables): a row maps ONLY to its own table under the direct
            // mapping (§ 3.2c). The role restricts C_BPartner only; C_BPartner_Location has no row for it,
            // so creating an address stays allowed. This is the exact discriminator against the included-tab
            // scenario (role restricted on C_BPartner_Location → allowCreateNew=false there, true here).
            const addrTab = await getTabInfo(BUSINESS_PARTNER_WINDOW_ID, recordId, ADRESSE_TAB_ID);
            expect(addrTab.allowCreateNew, 'the C_BPartner create restriction must NOT leak to C_BPartner_Location (address creation stays allowed)').toBe(true);
            console.log(`[${language}] PASS — reads + edits partner AND restriction does not leak to other tables (Name2.readonly=${name2.readonly}, Adresse allowCreateNew=${addrTab.allowCreateNew})`);
        });
    });

    // Read-only-flag subtract: IsReadOnly='Y' removes WRITE — read-yes / edit-no — and CREATE with it
    // (creating is a write). Distinguishes the subtract contract from the old replace encoding.
    test.describe(`Role read-only table access — read yes, edit no, create no (${label})`, () => {
        test(`read-only role reads but cannot edit or create a partner (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role table access — read-only flag removes WRITE, and CREATE with it (subtract contract)');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const masterdata = await Backend.createMasterdata({
                request: {
                    login: { user: { language, role: 'readonly' } },
                    bpartners: { bp1: {} },
                    roles: {
                        readonly: {
                            name: `ReadOnlyBP_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner', readOnly: true }],
                        },
                    },
                },
            });
            const roleUser = masterdata.login.user;
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

            // EDIT-NO (visible): the Name2 field renders non-editable in the UI — the recording shows the greyed,
            // read-only field the read-only role cannot type into (WRITE removed).
            const name2Input = page.locator('.form-field-Name2 input').first();
            await name2Input.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect(name2Input, 'a read-only role must see the Name2 field as non-editable').not.toBeEditable();

            // CREATE-NO: "WRITE exclusion includes no CREATE, but not the other way around" — creating a
            // record is a write, so removing WRITE also removes CREATE. The read-only role's "New" action is
            // therefore greyed with the same create-restriction key, though no IsCanCreateNewRecords flag was
            // set on it; reached via the WRITE subtract instead of the CREATE flag.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            const greyedNew = page.getByTestId(`disabledReasonKey-${CREATE_RESTRICTION_MSG_KEY}`);
            await ensureSubheaderOpen(page, greyedNew);
            await expect(greyedNew, 'a read-only role must ALSO be create-blocked (WRITE exclusion removes CREATE)').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect(greyedNew, 'the greyed "New" action must carry the disabled class').toHaveClass(/subheader-item-disabled/);
            console.log(`[${language}] PASS — read-only role: read yes, edit no, create no (Name2.readonly=${name2.readonly})`);
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
                    login: { user: { language, role: 'restricted' } },
                    bpartners: { bp1: {} },
                    roles: {
                        restricted: {
                            name: `AddrRestricted_${language}`,
                            tableAccess: [{ tableName: 'C_BPartner_Location', canCreateNewRecords: false }],
                        },
                    },
                },
            });
            const roleUser = masterdata.login.user;
            expect(roleUser, 'masterdata must return the restricted role user').toBeTruthy();
            const recordId = String(masterdata.bpartners.bp1.id);

            await LoginPage.goto();
            await LoginPage.login(roleUser);
            await DashboardPage.expectVisible();

            // Open the role's own existing partner and SELECT the Adresse (Location) included tab — the tab
            // header is language-invariant (data-testid="tab-AD_Tab-222"). Selecting the tab renders its toolbar,
            // where the "Add new" button appears greyed for the restricted role (the disabled add-button carries
            // data-testid="disabledReasonKey-<key>", the same reason-key handle as the main New action). The
            // recording therefore shows the address tab open with its greyed "Add new" button.
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
            await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
            const adresseTab = page.getByTestId(`tab-${ADRESSE_TAB_ID}`);
            await adresseTab.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await adresseTab.click();
            const greyedAddAddress = page.getByTestId(`disabledReasonKey-${CREATE_RESTRICTION_MSG_KEY}`);
            await expect(greyedAddAddress, 'the address tab "Add new" button must be greyed with the role restriction key for the restricted role').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect(greyedAddAddress, 'the greyed address "Add new" button must carry the disabled class').toHaveClass(/subheader-item-disabled/);
            console.log(`[${language}] address tab "Add new" is greyed with key ${CREATE_RESTRICTION_MSG_KEY}`);

            // Backstop (language-invariant): the tab's create permission is false AND attributed to the ROLE.
            // Opening an EXISTING partner rules out "parent is new", so the reason key proves it is the role restriction.
            const tabInfo = await getTabInfo(BUSINESS_PARTNER_WINDOW_ID, recordId, ADRESSE_TAB_ID);
            console.log(`[${language}] Adresse tabInfo: ${JSON.stringify(tabInfo)}`);
            expect(tabInfo.allowCreateNew, 'the address included tab must forbid creating a new address for the restricted role').toBe(false);
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

                const role = { name: `TabBlock_${key}_${language}` };
                if (tableAccess) role.tableAccess = tableAccess;
                const masterdata = await Backend.createMasterdata({
                    request: { login: { user: { language, role: 'r' } }, bpartners: { bp1: {} }, roles: { r: role } },
                });
                const roleUser = masterdata.login.user;
                expect(roleUser, 'masterdata must return the role user').toBeTruthy();
                const recordId = String(masterdata.bpartners.bp1.id);

                await LoginPage.goto();
                await LoginPage.login(roleUser);
                await DashboardPage.expectVisible();

                await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
                await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });

                // SELECT the Vorgänge (Request) tab in the UI — it forbids insert structurally (IsInsertRecord='N'),
                // so its toolbar offers NO "Add new" button at all, and (crucially, AC4) NO role-restriction reason
                // ever surfaces there, whatever the role's own R_Request row says. The recording shows the tab open
                // with no create option and no "role not allowed" marker.
                const vorgTab = page.getByTestId(`tab-${VORGAENGE_TAB_ID}`);
                await vorgTab.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                await vorgTab.click();
                // No role-restriction greyed button may appear on this tab in ANY of the three role states.
                await expect(
                    page.getByTestId(`disabledReasonKey-${CREATE_RESTRICTION_MSG_KEY}`),
                    `no role-restriction reason may surface on a tab-forbidden insert (${stateLabel})`,
                ).toHaveCount(0);
                console.log(`[${language}] Vorgänge tab shows no role-restriction reason (${stateLabel})`);

                // Backstop (language-invariant): the tab forbids create structurally → allowCreateNew=false with a
                // reason that is NEVER the role key, regardless of the role row (AC3/AC4).
                const tab = await getTabInfo(BUSINESS_PARTNER_WINDOW_ID, recordId, VORGAENGE_TAB_ID);
                console.log(`[${language}] Vorgänge tabInfo (${stateLabel}): ${JSON.stringify(tab)}`);
                expect(tab.allowCreateNew, `a tab that forbids insert must not allow create (${stateLabel})`).toBe(false);
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
                chain: { name: `UnionChain_${language}`, includedRoles: ['base'] },
            },
        },
        {
            key: 'granting-row-lifts', caseLabel: 'a plain granting row (toolkit default shape) in the chain lifts the forbid', expectRestricted: false,
            roles: {
                base: { name: `UnionFbase_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                // Every flag left at its default => IsCanCreateNewRecords='Y': the exact row support.ad_role_ensure_table_access writes.
                granter: { name: `UnionGrant_${language}`, tableAccess: [{ tableName: 'C_BPartner' }] },
                chain: { name: `UnionAllow_${language}`, includedRoles: ['base', 'granter'] },
            },
        },
        {
            key: 'no-row-anywhere', caseLabel: 'no row anywhere in the chain is unaffected', expectRestricted: false,
            roles: {
                chain: { name: `UnionNorow_${language}` },
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

                let isRestrictionKeyDelivered = false;
                page.on('response', async (r) => {
                    try {
                        if (!(r.headers()['content-type'] || '').includes('json')) return;
                        if (!r.url().includes(`/documentView/${BUSINESS_PARTNER_WINDOW_ID}/`)) return;
                        if ((await r.text()).includes(CREATE_RESTRICTION_MSG_KEY)) isRestrictionKeyDelivered = true;
                    } catch (e) { /* ignore */ }
                });

                const masterdata = await Backend.createMasterdata({ request: { login: { user: { language, role: 'chain' } }, roles } });
                const roleUser = masterdata.login.user;
                expect(roleUser, 'masterdata must return the chain role user').toBeTruthy();

                await LoginPage.goto();
                await LoginPage.login(roleUser);
                await DashboardPage.expectVisible();

                if (expectRestricted) {
                    await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
                    await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                    await expect
                        .poll(() => isRestrictionKeyDelivered, { timeout: VERY_SLOW_ACTION_TIMEOUT, message: 'the union must resolve to RESTRICTED (create key delivered)' })
                        .toBe(true);
                    const greyedNew = page.getByTestId(`disabledReasonKey-${CREATE_RESTRICTION_MSG_KEY}`);
                    await ensureSubheaderOpen(page, greyedNew);
                    await expect(greyedNew, 'union restricted → the "New" action is greyed with the restriction key').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
                    console.log(`[${language}] PASS — union RESTRICTED (${caseLabel})`);
                } else {
                    await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/NEW`);
                    await page.waitForURL((u) => isNewRecordUrl(u.toString()), { timeout: VERY_SLOW_ACTION_TIMEOUT });
                    expect(isNewRecordUrl(page.url()), 'union allowed → a new C_BPartner record is created').toBe(true);
                    expect(isRestrictionKeyDelivered, 'no restriction key when the union resolves to ALLOWED').toBe(false);
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
                    ? { name: `CloneRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] }
                    : { name: `CloneOpen_${language}` };
                const masterdata = await Backend.createMasterdata({
                    request: { login: { user: { language, role: 'r' } }, bpartners: { bp1: {} }, roles: { r: role } },
                });
                const roleUser = masterdata.login.user;
                expect(roleUser, 'masterdata must return the role user').toBeTruthy();
                const roleName = masterdata.roles.r.name;
                const recordId = String(masterdata.bpartners.bp1.id);

                await LoginPage.goto();
                await LoginPage.login(roleUser);
                await DashboardPage.expectVisible();

                await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`);
                await page.waitForURL(new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${recordId}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
                await assertRecordIsValid(BUSINESS_PARTNER_WINDOW_ID, recordId, 'clone test opens the partner');

                // Open the ⋮ actions menu and observe the Clone action, located by its language-invariant icon
                // class (meta-icon-duplicate). Clone is present for an unrestricted role and REMOVED for a
                // restricted one (JSONDocumentPermissions.getStandardActions it.remove() when create is not
                // allowed). The recording shows the actions menu WITH / WITHOUT the Clone action. The advanced-edit
                // action (meta-icon-edit) is always present, so it is the anchor used to open the menu when Clone
                // is absent.
                const cloneItem = page.locator('.js-subheader-item:has(i.meta-icon-duplicate)');
                const advEditItem = page.locator('.js-subheader-item:has(i.meta-icon-edit)');
                await ensureSubheaderOpen(page, restricted ? advEditItem : cloneItem);

                // Discriminating enforcement backstop: the server-computed standardActions set the WebUI builds the
                // action menu from. Clone is REMOVED from it for a restricted role and KEPT for an unrestricted
                // one. Language-invariant (the action's internalName 'clone'), and independent of menu rendering.
                const record = await getRecordData(BUSINESS_PARTNER_WINDOW_ID, recordId);
                const standardActions = record.standardActions || [];
                console.log(`[${language}] standardActions (${key}) = ${JSON.stringify(standardActions)}`);

                if (restricted) {
                    await expect(cloneItem, 'the Clone action must be absent from the actions menu for a restricted role').toHaveCount(0);
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
                    await expect(cloneItem, 'the Clone action must be visible in the actions menu for an unrestricted role').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
                    expect(standardActions, 'Clone must be available in the standard actions for an unrestricted role').toContain('clone');
                    console.log(`[${language}] PASS — clone available (standard action visible in menu)`);
                }
            });
        });
    });

    // TC16 — the menu "new business partner" entry is hidden for a restricted role (AC18). A C_BPartner
    // window menu node with IsCreateNew='Y' produces a newRecord menu entry ("New Business Partner" /
    // "Neuer Geschäftspartner") ONLY when the role may create C_BPartner (MenuTreeLoader.createNewRecordNode
    // returns null otherwise). Driven through the REAL menu OVERLAY SEARCH the user performs: open the menu
    // (top-left menu icon), type "partner" (a substring of the entry in BOTH en_US and de_DE), and observe the
    // results — so the recording shows the search and the entry appearing / not appearing. Primary assertion is
    // language-invariant (the /menu/queryPaths response's newRecord leaf, keyed on its elementId — deployment-
    // agnostic, no hardcoded window id); the entry's on-screen presence/absence is additionally asserted, located
    // by the caption the RESPONSE itself carries (derived at runtime, never a hardcoded localized string).
    test.describe(`Role create restriction — menu new-partner entry hidden (${label})`, () => {
        test(`the menu search offers "new business partner" only for an unrestricted role (${label} UI)`, async ({ page }) => {
            allure.epic('E0390: Business Partner');
            allure.story('Role create restriction — the new-record menu entry is hidden for a restricted role');
            allure.tag('F33020: Roles');
            allure.tag('F33020');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);
            test.setTimeout(120000);

            const md = await Backend.createMasterdata({
                request: {
                    login: {
                        openUser: { language, role: 'open' },
                        restrictedUser: { language, role: 'restricted' },
                    },
                    roles: {
                        open: { name: `MenuOpen_${language}` },
                        restricted: { name: `MenuRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] },
                    },
                },
            });
            expect(md.login.openUser && md.login.restrictedUser, 'masterdata must return both role users').toBeTruthy();

            // A /menu/queryPaths response is a tree; its leaves map 1:1 (in order) to the rendered .js-menu-item
            // rows — same reduction MenuActions.js applies. A leaf with type==='newRecord' is a "new <window>" entry.
            const collectLeaves = (node) => (node.children ? node.children.flatMap(collectLeaves) : [node]);

            // Log in, open the menu overlay (top-left menu icon), type the search term, and return the
            // /menu/queryPaths leaves. Leaves the overlay open with results rendered so the caller can assert on
            // the visible DOM. Clears cookies first so the second login starts from a clean session.
            const menuSearch = async (user, term) => {
                await page.context().clearCookies();
                await LoginPage.goto();
                await LoginPage.login(user);
                await DashboardPage.expectVisible();
                const menuBtn = page.locator('.header-item-container-static').first();
                await menuBtn.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                await menuBtn.click();
                const searchInput = page.locator('.menu-overlay-query input.input-field');
                await searchInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                const [resp] = await Promise.all([
                    page.waitForResponse((r) => r.url().includes('/menu/queryPaths'), { timeout: VERY_SLOW_ACTION_TIMEOUT }),
                    searchInput.fill(term),
                ]);
                return collectLeaves(await resp.json());
            };

            // UNRESTRICTED: searching "partner" returns the business-partner "new record" entry AND it renders
            // in the menu (the recording shows the entry present).
            const openLeaves = await menuSearch(md.login.openUser, 'partner');
            const openNewRecords = openLeaves.filter((l) => l.type === 'newRecord' && l.elementId != null);
            console.log(`[${language}] open newRecord entries: ${JSON.stringify(openNewRecords.map((n) => ({ caption: n.caption, elementId: n.elementId })))}`);
            expect(openNewRecords.length, 'an unrestricted role must be offered a "new record" menu entry for the business partner window').toBeGreaterThanOrEqual(1);
            const bpNewRecord = openNewRecords[0];
            // On-screen (video): the entry is visible in the rendered menu, located by the caption the response
            // carries (runtime-derived, so language-independent — never a hardcoded localized string).
            const openEntry = page.locator('.menu-overlay-query .js-menu-item', { hasText: bpNewRecord.caption });
            await expect(openEntry.first(), 'the "new business partner" entry must be visible in the menu for an unrestricted role').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });

            // RESTRICTED: the same search returns NO newRecord entry for that business-partner window, and the
            // entry is absent from the rendered menu (the recording shows the search WITHOUT the entry). Keyed on
            // the elementId the unrestricted run captured — deployment-agnostic, no hardcoded window id.
            const restrictedLeaves = await menuSearch(md.login.restrictedUser, 'partner');
            const restrictedBpNewRecords = restrictedLeaves.filter((l) => l.type === 'newRecord' && String(l.elementId) === String(bpNewRecord.elementId));
            console.log(`[${language}] restricted newRecord entries for elementId ${bpNewRecord.elementId}: ${restrictedBpNewRecords.length}`);
            expect(restrictedBpNewRecords.length, 'a restricted role must NOT be offered the business-partner "new record" menu entry').toBe(0);
            const restrictedEntry = page.locator('.menu-overlay-query .js-menu-item', { hasText: bpNewRecord.caption });
            await expect(restrictedEntry, 'the "new business partner" entry must be absent from the menu for a restricted role').toHaveCount(0);
            console.log(`[${language}] PASS — menu offers "new business partner" only for the unrestricted role`);
        });
    });

    // TC14 — the quick-input "new business partner" entry in a C_BPartner lookup is hidden (AC18). On a new
    // Sales Order, typing a NON-EXISTING partner name into the C_BPartner (Kunde) lookup offers a
    // "New Business Partner" / "Neuer Geschäftspartner" entry (data-testid option-NEW) for an unrestricted role
    // and nothing for a role restricted on C_BPartner — so the recording shows, side by side across the two
    // cases, how the option normally appears and how the restriction removes it. The option is gated by
    // newRecordCaption, nulled when create is not allowed (JSONDocumentLayoutElementField → isTableAccess
    // CREATE). Driven through the RENDERED dropdown. The lookup is reached on a new Sales Order (the role is
    // restricted only on C_BPartner, so it may still create an order); identical steps for both roles, so the
    // only variable is the create restriction.
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
                    ? { name: `QuickRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] }
                    : { name: `QuickOpen_${language}` };
                const md = await Backend.createMasterdata({ request: { login: { user: { language, role: 'r' } }, roles: { r: role } } });
                expect(md.login.user, 'masterdata must return the role user').toBeTruthy();

                await LoginPage.goto();
                await LoginPage.login(md.login.user);
                await DashboardPage.expectVisible();

                // Reach the C_BPartner (Kunde) lookup on a new Sales Order, then type a non-existing partner name.
                await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/NEW`);
                const bpInput = page.locator('#lookup_C_BPartner_ID input').first();
                await bpInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
                await bpInput.click();

                // Synchronize on the actual typeahead HTTP response — race-free. A DOM-class wait cannot work
                // here: the dropdown's loading spinner and its settled no-results header share the same class
                // (SelectionDropdown.renderHeader), so a class wait can resolve DURING loading, before the
                // round-trip completes — leaving the absence assertion vacuous. Waiting on the typeahead response
                // guarantees the round-trip finished; option-NEW is layout-gated (newRecordCaption, nulled for a
                // restricted role), so once the response is in, its presence/absence is settled.
                const typeaheadDone = page.waitForResponse(
                    (r) => r.url().includes('/field/C_BPartner_ID/typeahead') && r.status() === 200,
                    { timeout: VERY_SLOW_ACTION_TIMEOUT },
                );
                await bpInput.fill(`ZZZ_NOMATCH_${Date.now()}`);
                await typeaheadDone;

                const optionNew = page.getByTestId('option-NEW');
                if (restricted) {
                    // The typeahead round-trip has completed (awaited above) and option-NEW is never added for a
                    // restricted role — assert it is absent. Non-vacuous: we are provably past the round-trip.
                    await expect(optionNew, 'the new-partner quick-input entry must be hidden for a restricted role').toHaveCount(0);
                    console.log(`[${language}] PASS — quick-input new-partner hidden`);
                } else {
                    await expect(optionNew, 'the new-partner quick-input entry must be offered for an unrestricted role').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
                    // Click it to open the New Business Partner quick-input dialog — the recording shows the
                    // create-new-partner flow actually starting for the unrestricted role (the visible counterpart
                    // to the server-side create-refused test for a restricted role).
                    await optionNew.click();
                    await expect(page.locator('.panel-modal-content'), 'the New Business Partner quick-input dialog must open for an unrestricted role').toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
                    console.log(`[${language}] PASS — quick-input new-partner offered and dialog opens`);
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
                    login: {
                        user: { language },
                        restrictedUser: { language, role: 'restricted' },
                    },
                    roles: { restricted: { name: `LiftRestricted_${language}`, tableAccess: [{ tableName: 'C_BPartner', canCreateNewRecords: false }] } },
                },
            });
            const restrictedUser = md.login.restrictedUser;
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
