/*
 * #%L
 * master
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import { check } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';
import { DataManager } from '../framework/data-manager.js';
import { createHttpClient } from '../framework/http-client.js';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080/rest-api/v2';
const AUTH_TOKEN = __ENV.AUTH_TOKEN || '';
const DATA_STRATEGY = __ENV.DATA_STRATEGY || 'mixed'; // 'existing' | 'generated' | 'mixed'
const EXISTING_RATIO = Number(__ENV.EXISTING_RATIO || 0.7); // used when DATA_STRATEGY === 'mixed'
const ORG_CODE = __ENV.ORG_CODE || ''; // optional org, not required for /bpartner PUT
const BP_BODY_FILES_ENV = (__ENV.BP_BODY_FILES || '../test-data/extracted-bpartner-requests.json')
  .split(',')
  .map((s) => s.trim())
  .filter(Boolean);

function buildOptions() {
  const VUS = __ENV.VUS ? Number(__ENV.VUS) : undefined;
  const DURATION = __ENV.DURATION || undefined;
  const ITERATIONS = __ENV.ITERATIONS ? Number(__ENV.ITERATIONS) : undefined;

  // If user provided ITERATIONS, use a shared-iterations executor
  if (ITERATIONS) {
    return {
      scenarios: {
        default: {
          executor: 'shared-iterations',
          vus: VUS || 1,
          iterations: ITERATIONS,
          maxDuration: DURATION || '10m',
        },
      },
    };
  }

  // If user provided VUS and/or DURATION, use simple constant VUs
  if (VUS || DURATION) {
    return {
      vus: VUS || 1,
      duration: DURATION || '10s',
    };
  }

  // Fallback: original ramping-vus plan
  return {
    scenarios: {
      default: {
        executor: 'ramping-vus',
        startVUs: 0,
        stages: [
          { duration: '2m', target: 10 },
          { duration: '5m', target: 10 },
          { duration: '2m', target: 20 },
          { duration: '5m', target: 20 },
          { duration: '1m', target: 0 },
        ],
        gracefulRampDown: '30s',
      },
    },
    thresholds: {
      http_req_failed: ['rate<0.05'],
      http_req_duration: ['p(95)<1000'],
      'bpartner_upsert_duration{type:put}': ['p(95)<1500'],
      'bpartner_get_duration{type:get}': ['p(95)<800'],
    },
  };
}

export const options = buildOptions();

// Metrics
const upsertTrend = new Trend('bpartner_upsert_duration', true);
const getTrend = new Trend('bpartner_get_duration', true);
const upsertCount = new Counter('bpartner_upsert_count');
const getCount = new Counter('bpartner_get_count');
const errorRate = new Rate('errors');

// Init-stage singletons
const dataManager = new DataManager({ bpartnerFiles: BP_BODY_FILES_ENV });
const httpClient = createHttpClient({ baseUrl: BASE_URL, authToken: AUTH_TOKEN });

export default function() {
  // 1) Build payload: generated, existing, or mixed
  const upsertPayload = dataManager.getBPartnerUpsert(DATA_STRATEGY, EXISTING_RATIO);

  // 2) PUT /bpartner (create or update)
  const putPath = ORG_CODE ? `/bpartner/${ORG_CODE}` : '/bpartner';
  const putStart = Date.now();

  const putRes = httpClient.putJson(putPath, upsertPayload);

  const putDur = Date.now() - putStart;
  upsertTrend.add(putDur, { type: 'put' });
  upsertCount.add(1);

  const putOk = check(putRes, {
    'PUT bpartner status 201': (r) => r.status === 201,
  });

  if (!putOk) {
    errorRate.add(1);
    // Short think time and bail to avoid cascading errors
    //sleep(0.5);
    return;
  }

  // 3) GET /bpartner/{bpartnerIdentifier}
  const identifier = dataManager.extractBPartnerIdentifier(upsertPayload);
  if (identifier) {
    const getPath = `/bpartner/${identifier}`;
    const getStart = Date.now();
    const getRes = httpClient.get(getPath);
    const getDur = Date.now() - getStart;
    getTrend.add(getDur, { type: 'get' });
    getCount.add(1);

    const getOk = check(getRes, {
      'GET bpartner status 200': (r) => r.status === 200,
    });
    if (!getOk) {
      errorRate.add(1);
    }
  }

  // 4) Small think time to simulate user pacing
  //sleep(1);
}
