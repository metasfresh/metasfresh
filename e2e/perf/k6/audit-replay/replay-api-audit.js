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

import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Gauge, Rate, Trend } from 'k6/metrics';
import { DataManager } from './framework/data-manager.js';

// ==================== Configuration ====================

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8282/api/v2';
const AUTH_TOKEN = __ENV.AUTH_TOKEN || '';
const AUDIT_DATA_FILE = __ENV.AUDIT_DATA_FILE || './test-data/extracted-api-audit.json';
const REPLAY_MODE = __ENV.REPLAY_MODE || 'sequential'; // sequential | random
const THINK_TIME = parseFloat(__ENV.THINK_TIME || '1');
const VUS = parseInt(__ENV.VUS || '10');
const DURATION = __ENV.DURATION || '60s';
const COMPARE_RESPONSES = __ENV.COMPARE_RESPONSES === 'true';
const REPLACE_AUTH_TOKEN = __ENV.REPLACE_AUTH_TOKEN !== 'false'; // default true

// ==================== Custom Metrics ====================

const auditReplayDuration = new Trend('audit_replay_duration', true);
const auditReplaySuccess = new Rate('audit_replay_success');
const auditUnexpectedStatus = new Counter('audit_unexpected_status');
const auditResponseTimeDiff = new Trend('audit_response_time_diff', true);
const auditSlowerRequests = new Counter('audit_slower_requests');
const auditFasterRequests = new Counter('audit_faster_requests');
const auditCurrentIndex = new Gauge('audit_current_index');

// ==================== Data Manager Setup ====================

const dataManager = new DataManager({
  auditDataFile: AUDIT_DATA_FILE
});

const totalRequests = dataManager.getAuditRequestCount();
console.log(`Loaded ${totalRequests} audit requests from ${AUDIT_DATA_FILE}`);

if (totalRequests === 0) {
  throw new Error('No audit requests loaded. Please check AUDIT_DATA_FILE path.');
}

// ITERATIONS: 0 = use duration, >0 = use iterations, 'auto' = match request count
// Must be calculated AFTER totalRequests is known
let ITERATIONS = 0;
if (__ENV.ITERATIONS === 'auto') {
  ITERATIONS = totalRequests;
  console.log(`ITERATIONS=auto detected. Will execute exactly ${ITERATIONS} requests.`);
} else if (__ENV.ITERATIONS) {
  ITERATIONS = parseInt(__ENV.ITERATIONS);
}

// ==================== K6 Options ====================

// Build options dynamically based on execution mode
const buildOptions = () => {
  const opts = {};

  // Only set thresholds for load testing (duration mode)
  // For exact replay (iterations mode), we just want to see what happens
  if (ITERATIONS === 0) {
    opts.thresholds = {
      'audit_replay_success': ['rate>0.95'], // 95% success rate
      'audit_replay_duration': ['p(95)<5000'], // 95% under 5s
      'http_req_failed': ['rate<0.05'], // Less than 5% failures
    };
  }

  if (ITERATIONS > 0) {
    // Iterations mode: run exactly N iterations (perfect for exact replay)
    opts.scenarios = {
      replay: {
        executor: 'shared-iterations',
        vus: VUS,
        iterations: ITERATIONS,
        maxDuration: '1h', // Safety timeout
      }
    };
  } else {
    // Duration mode: run for a fixed time (for load testing)
    opts.vus = VUS;
    opts.duration = DURATION;
  }

  return opts;
};

export const options = buildOptions();

// ==================== Helper Functions ====================

/**
 * Prepare headers for replay
 */
function prepareHeaders(originalHeaders) {
  const headers = { ...originalHeaders };

  // Set Content-Type if not present
  if (!headers['Content-Type'] && !headers['content-type']) {
    headers['Content-Type'] = 'application/json';
  }

  // Set Accept header if not present
  if (!headers['Accept'] && !headers['accept']) {
    headers['accept'] = 'application/json';
  }

  // Replace authorization token if configured
  if (REPLACE_AUTH_TOKEN && AUTH_TOKEN) {
    headers['Authorization'] = `${AUTH_TOKEN}`;
  }

  // Remove headers that should not be replayed
  const headersToRemove = ['Host', 'Connection', 'Content-Length', 'Transfer-Encoding'];
  headersToRemove.forEach(h => {
    delete headers[h];
    delete headers[h.toLowerCase()];
  });

  return headers;
}

/**
 * Build full URL from base URL and RequestURI
 * Simply appends the RequestURI to BASE_URL
 */
function buildUrl(requestUri) {
  // RequestURI should already be a proper URI path (e.g., /api/v2/orders/sales)
  // Just append it to BASE_URL
  return `${BASE_URL}${requestUri}`;
}

/**
 * Execute a single audit request
 */
function executeAuditRequest(auditRequest) {
  const url = buildUrl(auditRequest.path);
  const headers = prepareHeaders(auditRequest.headers || {});
  const body = auditRequest.body;

  // Debug logging (only first iteration)
  if (__ITER === 0) {
    console.log(`  URL: ${url}`);
    console.log(`  Method: ${auditRequest.method}`);
    console.log(`  Headers:`);
    Object.entries(headers).forEach(([key, value]) => {
      // Mask Authorization header for security
      if (key.toLowerCase() === 'authorization') {
        console.log(`    ${key}: ${value.substring(0, 8)}...`);
      } else {
        console.log(`    ${key}: ${value}`);
      }
    });
    if (body) {
      console.log(`  Body (first 200 chars): ${body.substring(0, 200)}...`);
    }
  }

  // Execute request
  const startTime = Date.now();
  const response = http.request(
    auditRequest.method,
    url,
    body,
    { headers }
  );
  const duration = Date.now() - startTime;

  // Debug logging (only first iteration)
  if (__ITER === 0) {
    console.log(`  Response: ${response.status} (${duration}ms)`);
    if (auditRequest.expectedHttpCode) {
      console.log(`  Expected: ${auditRequest.expectedHttpCode}`);
    }
    if (response.status >= 400) {
      console.log(`  Error body: ${response.body.substring(0, 500)}`);
    }
  }

  // Record metrics
  auditReplayDuration.add(duration);

  // Validate response
  const statusMatches = !auditRequest.expectedHttpCode ||
    response.status === parseInt(auditRequest.expectedHttpCode);

  const checks = check(response, {
    'status matches expected': () => statusMatches,
    'response time acceptable': () => response.timings.duration < 10000,
    'no request failure': () => !response.error,
  });

  // Record success/failure
  auditReplaySuccess.add(statusMatches ? 1 : 0);

  if (!statusMatches) {
    auditUnexpectedStatus.add(1);
    console.warn(
      `⚠️  Status mismatch for ${auditRequest.method} ${auditRequest.path}: ` +
      `expected ${auditRequest.expectedHttpCode}, got ${response.status}`
    );
  }

  // Log check failures
  if (!checks['status matches expected'] || !checks['no request failure']) {
    console.error(`❌ Request failed: ${auditRequest.method} ${auditRequest.path}`);
    if (response.error) {
      console.error(`   Error: ${response.error}`);
    }
  }

  // Compare response times if original data available
  if (auditRequest.actualResponseTime != null && typeof auditRequest.actualResponseTime === 'number') {
    const diff = duration - auditRequest.actualResponseTime;
    auditResponseTimeDiff.add(diff);

    if (diff > 0) {
      auditSlowerRequests.add(1);
    } else {
      auditFasterRequests.add(1);
    }

    // Log significant slowdowns (>50% slower)
    if (diff > auditRequest.actualResponseTime * 0.5) {
      console.warn(
        `Significantly slower: ${auditRequest.method} ${auditRequest.path} ` +
        `(original: ${auditRequest.actualResponseTime.toFixed(0)}ms, ` +
        `replay: ${duration.toFixed(0)}ms, diff: +${diff.toFixed(0)}ms)`
      );
    }
  }

  // Compare response bodies if configured
  if (COMPARE_RESPONSES && auditRequest.expectedResponseBody) {
    const bodyMatches = response.body === auditRequest.expectedResponseBody;
    check(response, {
      'response body matches': () => bodyMatches,
    });

    if (!bodyMatches) {
      console.warn(
        `Response body mismatch for ${auditRequest.method} ${auditRequest.path}`
      );
    }
  }

  return response;
}

// ==================== Test Lifecycle ====================

export function setup() {
  console.log('========================================');
  console.log('API Audit Replay Load Test');
  console.log('========================================');
  console.log(`Base URL: ${BASE_URL}`);
  console.log(`Audit Data: ${AUDIT_DATA_FILE}`);
  console.log(`Total Requests: ${totalRequests}`);
  console.log(`Replay Mode: ${REPLAY_MODE}`);
  console.log(`VUs: ${VUS}`);

  if (ITERATIONS > 0) {
    console.log(`Execution Mode: ITERATIONS (${ITERATIONS} total)`);
    console.log(`  Each request will be executed once`);
  } else {
    console.log(`Execution Mode: DURATION (${DURATION})`);
    console.log(`  Requests will loop continuously for ${DURATION}`);
  }

  console.log(`Think Time: ${THINK_TIME}s`);
  console.log(`Replace Auth Token: ${REPLACE_AUTH_TOKEN}`);
  console.log(`Compare Responses: ${COMPARE_RESPONSES}`);
  console.log('========================================');

  return {
    startTime: Date.now(),
    totalRequests: totalRequests
  };
}

export default function (data) {
  // Get next audit request
  const auditRequest = dataManager.getNextAuditRequest(REPLAY_MODE);

  if (!auditRequest) {
    console.error('No audit request returned from data manager');
    return;
  }

  // Debug logging (only on first few iterations)
  if (__ITER < 3) {
    console.log(`[VU ${__VU}, Iter ${__ITER}] Executing: ${auditRequest.method} ${auditRequest.path}`);
  }

  // Update current index metric
  auditCurrentIndex.add(dataManager.auditIndex);

  // Execute request
  executeAuditRequest(auditRequest);

  // Think time between requests
  if (THINK_TIME > 0) {
    sleep(THINK_TIME);
  }
}

export function teardown(data) {
  const duration = (Date.now() - data.startTime) / 1000;
  console.log('========================================');
  console.log('Test Complete');
  console.log('========================================');
  console.log(`Total Duration: ${duration.toFixed(2)}s`);
  console.log(`Total Audit Requests Available: ${data.totalRequests}`);

  if (ITERATIONS > 0) {
    console.log(`Executed: ${ITERATIONS} iterations (exact replay mode)`);
  }

  console.log('========================================');
  console.log('\nTo view detailed results:');
  console.log('  1. Check the JSON output file (if configured with --out)');
  console.log('  2. Use k6 Cloud: k6 cloud <results.json>');
  console.log('  3. Or use k6-reporter: https://github.com/benc-uk/k6-reporter');
  console.log('========================================');
}
