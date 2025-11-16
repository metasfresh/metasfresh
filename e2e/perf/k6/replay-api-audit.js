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

// ==================== K6 Options ====================

export const options = {
  vus: VUS,
  duration: DURATION,
  thresholds: {
    'audit_replay_success': ['rate>0.95'], // 95% success rate
    'audit_replay_duration': ['p(95)<5000'], // 95% under 5s
    'http_req_failed': ['rate<0.05'], // Less than 5% failures
  },
};

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

  // Replace authorization token if configured
  if (REPLACE_AUTH_TOKEN && AUTH_TOKEN) {
    headers['Authorization'] = `Bearer ${AUTH_TOKEN}`;
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
 * Build full URL from base URL and path
 */
function buildUrl(path) {
  // Strip /api/v2 prefix if present in path and BASE_URL already has it
  let cleanPath = path;
  if (BASE_URL.includes('/api/v2') && path.startsWith('/api/v2')) {
    cleanPath = path.substring(7); // Remove '/api/v2'
  }

  // Ensure proper URL construction
  if (cleanPath.startsWith('/')) {
    return `${BASE_URL}${cleanPath}`;
  } else {
    return `${BASE_URL}/${cleanPath}`;
  }
}

/**
 * Execute a single audit request
 */
function executeAuditRequest(auditRequest) {
  const url = buildUrl(auditRequest.path);
  const headers = prepareHeaders(auditRequest.headers || {});
  const body = auditRequest.body;

  // Execute request
  const startTime = Date.now();
  const response = http.request(
    auditRequest.method,
    url,
    body,
    { headers }
  );
  const duration = Date.now() - startTime;

  // Record metrics
  auditReplayDuration.add(duration);

  // Validate response
  const statusMatches = !auditRequest.expectedHttpCode ||
    response.status === parseInt(auditRequest.expectedHttpCode);

  check(response, {
    'status matches expected': () => statusMatches,
    'response time acceptable': () => response.timings.duration < 10000,
    'no request failure': () => !response.error,
  });

  // Record success/failure
  auditReplaySuccess.add(statusMatches ? 1 : 0);

  if (!statusMatches) {
    auditUnexpectedStatus.add(1);
    console.warn(
      `Status mismatch for ${auditRequest.method} ${auditRequest.path}: ` +
      `expected ${auditRequest.expectedHttpCode}, got ${response.status}`
    );
  }

  // Compare response times if original data available
  if (auditRequest.actualResponseTime) {
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
  console.log(`Duration: ${DURATION}`);
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
  console.log('========================================');
}
