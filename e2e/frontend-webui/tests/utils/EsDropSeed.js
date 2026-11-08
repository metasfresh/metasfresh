import { execFileSync } from 'node:child_process';
import path from 'node:path';
import { test } from '../../playwright.config';
import { Backend } from './Backend';
import { getPage } from './common';
import { WEBAPI_BASE_URL } from './WebAPIValidation';

// Per-run-unique suffix for the FTS partner/product keys. Two reasons: (1) a Playwright retry runs in a fresh worker
// process -> fresh RUN -> the REST seed never collides with the rows the failed attempt already created; (2) on an
// isolated stack where the KPI scenario also runs, these keys stay disjoint from that scenario's fixed psql keys
// (ESDROPBP1 / ESDROPPRD1), so its DELETE never hits a REST-created partner (which carries an FK'd location row).
// Letters only, so each stays a single full-text-search token.
const RUN = Array.from({ length: 8 }, () => String.fromCharCode(97 + Math.floor(Math.random() * 26))).join('');

/** Seeded business partner / product (per-run-unique) + order sums, as asserted by es-drop-fts-and-kpi.spec.js. */
export const ES_DROP_SEED = {
  partner: { value: `ESDROPBP${RUN}`, nameWord: `Zyxquarta${RUN}`, name: `Zyxquarta${RUN} Handels GmbH` },
  product: { value: `ESDROPPRD${RUN}`, nameWord: `Zyxbanane${RUN}`, name: `Zyxbanane${RUN} Bio Spezialsorte` },
  salesNetAmount: '350.50',
  purchaseNetAmount: '100.25',
};

const SEED_SQL = path.join(__dirname, '..', 'fixtures', 'es-drop-seed.sql');

let masterdataSeeded;
let kpiSeeded;

/**
 * FTS scenarios — CI-safe. Creates the partner and the product over the frontendTesting REST endpoint only
 * (the suite's HTTP-only channel; no DB access). The model-layer insert fires the FTS triggers, which fill
 * C_BPartner_FTS / M_Product_FTS, so the Postgres full-text-search filter finds them. Runs once per process.
 */
export const seedEsDropMasterdata = async () => {
  masterdataSeeded ??= test.step('Seed (REST): partner "Zyxquarta Handels GmbH" + product "Zyxbanane Bio Spezialsorte"', async () => {
    await Backend.createMasterdata({
      request: {
        bpartners: { ESDROPBP: { bpartnerCode: ES_DROP_SEED.partner.value, name: ES_DROP_SEED.partner.name } },
        products: { ESDROPPRD: { value: ES_DROP_SEED.product.value, name: ES_DROP_SEED.product.name } },
      },
    });
  });
  await masterdataSeeded;
};

/**
 * KPI scenario — LOCAL / UAT ONLY (the spec skips it when CI is set). Loads tests/fixtures/es-drop-seed.sql with the
 * local `psql`, which raw-inserts the partner, the product and five backdated orders (sales 350.50 in the "last week"
 * bucket + a 999.99 out-of-period order; purchase 100.25 in the "this week" bucket) and activates dashboard 540000.
 * Then drops the webapi's cached dashboard definition. Connection = the standard libpq env (PGHOST/PGPORT/PGUSER/
 * PGPASSWORD/PGDATABASE), defaults localhost / 5432 / metasfresh / metasfresh / metasfresh.
 *
 * NOT run in CI: the KPI sums ALL orders of the period for the session's org, and the shared CI frontend-webui DB
 * accumulates today-dated orders from other specs in org MAIN, so the sums are not deterministic there. On an isolated
 * stack (local + the UAT deploy) the precondition holds and the fixture asserts the exact sums itself.
 */
export const seedEsDropKpi = async () => {
  kpiSeeded ??= test.step('Seed (psql, local only): partner + product + backdated orders + dashboard 540000', async () => {
    const env = {
      PGHOST: 'localhost',
      PGUSER: 'metasfresh',
      PGPASSWORD: 'metasfresh',
      PGDATABASE: 'metasfresh',
      ...process.env,
    };
    execFileSync('psql', ['-X', '-q', '-v', 'ON_ERROR_STOP=1', '-f', SEED_SQL], { env, stdio: 'pipe' });

    // the seed (re)activates the dashboard row, which the webapi caches
    const response = await getPage().request.get(`${WEBAPI_BASE_URL}/cache/resetByTable?tableName=WEBUI_Dashboard`);
    if (!response.ok()) {
      throw new Error(`Failed to reset the webapi dashboard cache: HTTP ${response.status()}`);
    }
  });
  await kpiSeeded;
};
