/**
 * M_HU_PI_GRAI window — "GRAI to Packing Instruction Mapping" (AD_Window_ID=542157)
 *
 * This is the ONE user-facing surface of the GRAI scan-picking feature. It lets a key user
 * map a scanned GRAI's (GRAI_CompanyPrefix, GRAI_AssetType) pair to exactly
 * one packing instruction (M_HU_PI / TU type), so that at pick time a scanned
 * GRAI resolves to a single TU type.
 *
 * The table carries a GLOBAL unique index on (GRAI_CompanyPrefix, GRAI_AssetType)
 * — `M_HU_PI_GRAI_CompanyPrefix_AssetType_UIdx` — which enforces the domain
 * invariant "one GRAI resolves to exactly one TU type".
 *
 * Scope of this test:
 *   1. CREATE — open the window, create a mapping record (pick an existing
 *      M_HU_PI, enter a GRAI_CompanyPrefix + GRAI_AssetType), save and verify
 *      it persisted (record valid + saved in the DB, values read back via WebAPI).
 *   2. DUPLICATE REJECTION — create a SECOND record with the SAME
 *      (GRAI_CompanyPrefix, GRAI_AssetType); the save must be rejected (the
 *      unique index fires → error toast). This proves the invariant through
 *      the UI.
 *
 * Prerequisite M_HU_PI is provisioned through the Backend frontend-testing
 * masterdata API (`packingInstructions`) — never via the DB — so the same spec
 * runs identically locally and in CI.
 *
 * The window is pure AD-metadata (no custom JS); the M_HU_PI lookup renders as
 * a List widget and the GRAI columns as Text widgets (verified against the
 * running window/542157/layout).
 */

import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import {
  FRONTEND_BASE_URL,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from '../utils/common';
import { ListWidget } from '../utils/widgets/ListWidget';
import { TextWidget } from '../utils/widgets/TextWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import {
  getFieldData,
  getSaveStatus,
  getValidationStatus,
  waitForRecordSaved,
} from '../utils/WebAPIValidation';

// AD_Window_ID of the dedicated M_HU_PI_GRAI window (migration 5805800_sys_gh29853_M_HU_PI_GRAI_Window.sql)
const GRAI_WINDOW_ID = 542157;

// AD_Column.ColumnName values — language-independent selectors
const FIELD_M_HU_PI = 'M_HU_PI_ID';
const FIELD_COMPANY_PREFIX = 'GRAI_CompanyPrefix';
const FIELD_ASSET_TYPE = 'GRAI_AssetType';

/**
 * Navigate to the GRAI mapping window list view and wait for it to settle.
 */
async function gotoGraiWindow(page) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${GRAI_WINDOW_ID}`);
  await page.locator('.document-list-wrapper, .document-list').waitFor({
    state: 'visible',
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });
  await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.locator('.rotating, .panel-spaced-lg')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.waitForTimeout(500);
}

/**
 * Create a NEW record via Alt+N and return the new record id (from the URL).
 */
async function createNewRecord(page) {
  await page.locator('body').click();
  await page.waitForTimeout(200);
  await page.keyboard.press('Alt+N');

  await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.locator('.rotating, .indicator-pending')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.waitForTimeout(1000);

  return page.url().split('/').pop();
}

/**
 * Fill the three mapping fields (M_HU_PI list + the two GRAI text fields).
 */
async function fillMapping({ piName, companyPrefix, assetType }) {
  // M_HU_PI renders as a List widget; select by the (unique) PI name.
  await ListWidget.setValue(FIELD_M_HU_PI, piName, { exactMatch: true });
  await TextWidget.setValue(FIELD_COMPANY_PREFIX, companyPrefix);
  await TextWidget.setValue(FIELD_ASSET_TYPE, assetType);

  // Make sure the last edit is committed.
  await WidgetCommon.triggerBlur();
  await WidgetCommon.waitForSaveComplete();
}

test.describe('M_HU_PI_GRAI window — GRAI to Packing Instruction mapping', () => {
  test('Create a GRAI mapping and reject a duplicate (CompanyPrefix, AssetType)', async ({ page }) => {
    // === ALLURE METADATA ===
    // Epic/feature mirror the GRAI feature's own (mobile picking) E2E
    // (e2e/mobile-webui/.../picking-grai-scan.spec.js) — these are the IDs
    // already in use in the repo for this feature.
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.story('GRAI to Packing Instruction mapping window (desktop config)');
    allure.severity('critical');
    allure.description(`
## M_HU_PI_GRAI window (AD_Window_ID=542157)

The user-facing surface of GRAI scan-picking: maps (GRAI_CompanyPrefix, GRAI_AssetType)
to exactly one packing instruction (TU type).

1. **Create** a mapping (existing M_HU_PI + GRAI_CompanyPrefix + GRAI_AssetType) and
   verify it persists.
2. **Duplicate rejection**: a second mapping with the same (CompanyPrefix, AssetType)
   is rejected by the global unique index
   \`M_HU_PI_GRAI_CompanyPrefix_AssetType_UIdx\` — proving "one GRAI resolves to
   exactly one TU type" through the UI.
    `);

    test.setTimeout(120000); // 2 minutes — two record creations + WebAPI polling

    // The (CompanyPrefix, AssetType) pair used for BOTH records — the second must
    // collide on the unique index. Unique-per-run so reruns don't collide with
    // a leftover row from a previous run.
    const stamp = `${Date.now()}`;
    const companyPrefix = `CP${stamp}`;
    const assetType = `AT${stamp}`;

    // === STEP 0: provision prerequisite masterdata via the Backend API ===
    // A test user (for login) + a product + a TU packing instruction. The
    // `packingInstructions` command returns the (unique) PI name in `tuName`,
    // which we use to select the M_HU_PI in the window's List widget.
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: {} },
        products: {
          GRAI_PROD: { name: 'GRAI mapping test product' },
        },
        // NOTE: the packingInstructions map key MUST differ from the `tu`
        // identifier — the command registers the PI under the `tu` identifier
        // and then the PackingInstructions object under the map key, so reusing
        // the same string collides ("Object already exists").
        packingInstructions: {
          GRAI_PI: { tu: 'GRAI_TU', product: 'GRAI_PROD', qtyCUsPerTU: 10 },
        },
      },
    });

    const piName = masterdata.packingInstructions.GRAI_PI.tuName;
    expect(piName, 'masterdata must return the created TU packing-instruction name').toBeTruthy();
    console.log(`[INFO] Prerequisite M_HU_PI created: "${piName}"`);

    // === Login ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === STEP 1: CREATE a mapping record ===
    let firstRecordId;
    await test.step('Create the first GRAI mapping and verify it persists', async () => {
      await gotoGraiWindow(page);
      firstRecordId = await createNewRecord(page);
      console.log(`[INFO] New M_HU_PI_GRAI record: ${firstRecordId}`);

      await fillMapping({ piName, companyPrefix, assetType });

      // The record is only persisted to the DB once ALL mandatory fields are
      // filled and validStatus.valid === true. Poll the WebAPI to prove it.
      await waitForRecordSaved(String(GRAI_WINDOW_ID), firstRecordId);

      const validation = await getValidationStatus(String(GRAI_WINDOW_ID), firstRecordId);
      expect(validation.valid, `record ${firstRecordId} must be valid`).toBe(true);

      // Read the raw persisted values back via the WebAPI (language-independent).
      const prefixField = await getFieldData(String(GRAI_WINDOW_ID), firstRecordId, FIELD_COMPANY_PREFIX);
      const assetField = await getFieldData(String(GRAI_WINDOW_ID), firstRecordId, FIELD_ASSET_TYPE);
      const piField = await getFieldData(String(GRAI_WINDOW_ID), firstRecordId, FIELD_M_HU_PI);

      expect(prefixField.value).toBe(companyPrefix);
      expect(assetField.value).toBe(assetType);
      // M_HU_PI is a List/lookup → value is { key, caption }; assert it is set.
      const piValue = typeof piField.value === 'object' && piField.value !== null
        ? piField.value.key
        : piField.value;
      expect(piValue, 'M_HU_PI must be set on the saved record').toBeTruthy();

      console.log(`[PASS] Mapping saved: ${companyPrefix}/${assetType} -> PI "${piName}" (record ${firstRecordId})`);

      const shot = await page.screenshot({ fullPage: true });
      await allure.attachment('First GRAI mapping saved', shot, 'image/png');
    });

    // === STEP 2: DUPLICATE REJECTION (the domain invariant) ===
    await test.step('Reject a duplicate (CompanyPrefix, AssetType) mapping', async () => {
      await gotoGraiWindow(page);
      const secondRecordId = await createNewRecord(page);
      console.log(`[INFO] Second (duplicate) M_HU_PI_GRAI record: ${secondRecordId}`);

      // Fill the SAME (CompanyPrefix, AssetType) as the first record. Once the
      // second GRAI field completes the key and the save is triggered, the
      // global unique index M_HU_PI_GRAI_CompanyPrefix_AssetType_UIdx fires and
      // the WebUI save is rejected.
      await ListWidget.setValue(FIELD_M_HU_PI, piName, { exactMatch: true });
      await TextWidget.setValue(FIELD_COMPANY_PREFIX, companyPrefix);
      await TextWidget.setValue(FIELD_ASSET_TYPE, assetType);
      await WidgetCommon.triggerBlur();
      await WidgetCommon.waitForSaveComplete();

      // Assert the rejection via the authoritative WebUI save status (the
      // duplicate-key error surfaces in saveStatus/validStatus, language-
      // independently — not as a reliably-catchable transient toast).
      const saveStatus = await getSaveStatus(String(GRAI_WINDOW_ID), secondRecordId);
      console.log(`[INFO] Duplicate save status: ${JSON.stringify(saveStatus)}`);

      // The save must have FAILED with an error and the record must NOT be in the DB.
      expect(saveStatus.error, 'duplicate save must report an error').toBe(true);
      expect(saveStatus.saved, 'duplicate must not be saved').toBe(false);
      expect(saveStatus.presentInDatabase, 'duplicate must not be persisted to the DB').toBe(false);

      // The rejection reason must name the global unique index — proving it is
      // the (CompanyPrefix, AssetType) invariant that fired, not some other error.
      const reason = (saveStatus.reason || '').toLowerCase();
      expect(reason, `rejection reason should reference the unique index, was: "${saveStatus.reason}"`)
        .toContain('m_hu_pi_grai_companyprefix_assettype_uidx');

      console.log(`[PASS] Duplicate (${companyPrefix}, ${assetType}) rejected by unique index M_HU_PI_GRAI_CompanyPrefix_AssetType_UIdx.`);

      const shot = await page.screenshot({ fullPage: true });
      await allure.attachment('Duplicate GRAI mapping rejected', shot, 'image/png');
    });

    console.log('[PASS] M_HU_PI_GRAI window: create + duplicate-rejection verified.');
  });
});
