import { execFileSync } from 'node:child_process';
import path from 'node:path';
import { test } from '../../playwright.config';
import { getPage } from './common';
import { WEBAPI_BASE_URL } from './WebAPIValidation';

const SEED_SQL = path.join(__dirname, '..', 'fixtures', 'es-drop-seed.sql');

/** Seeded business partner / product / order sums, as asserted by es-drop-fts-and-kpi.spec.js. */
export const ES_DROP_SEED = {
  partner: { value: 'ESDROPBP1', nameWord: 'Zyxquarta', name: 'Zyxquarta Handels GmbH' },
  product: { value: 'ESDROPPRD1', nameWord: 'Zyxbanane', name: 'Zyxbanane Bio Spezialsorte' },
  salesNetAmount: '350.50',
  purchaseNetAmount: '100.25',
};

/**
 * Loads tests/fixtures/es-drop-seed.sql (idempotent) into the DB behind the stack under test with the local `psql`.
 * Connection = the standard libpq environment (PGHOST, PGPORT, PGUSER, PGPASSWORD, PGDATABASE); defaults are
 * localhost / 5432 / metasfresh / metasfresh / metasfresh. Then drops the webapi's cached dashboard definition.
 */
export const seedEsDropData = async () =>
  await test.step('Seed: partner "Zyxquarta Handels GmbH", product "Zyxbanane Bio Spezialsorte", sales + purchase orders', async () => {
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
