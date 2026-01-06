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

// #!/usr/bin/env node

import pg from 'pg';
import yargs from 'yargs';
import { hideBin } from 'yargs/helpers';
import fs from 'fs';
import path from 'path';

const { Pool } = pg;

/**
 * Extract API audit data from metasfresh database for k6 load testing
 */

// Parse CLI arguments
const argv = yargs(hideBin(process.argv))
  .option('db-host', {
    alias: 'h',
    description: 'Database host',
    type: 'string',
    default: process.env.DB_HOST || 'localhost'
  })
  .option('db-port', {
    alias: 'p',
    description: 'Database port',
    type: 'number',
    default: parseInt(process.env.DB_PORT || '5432')
  })
  .option('db-name', {
    alias: 'd',
    description: 'Database name',
    type: 'string',
    default: process.env.DB_NAME || 'metasfresh'
  })
  .option('db-user', {
    alias: 'u',
    description: 'Database user',
    type: 'string',
    default: process.env.DB_USER || 'metasfresh'
  })
  .option('db-password', {
    alias: 'w',
    description: 'Database password',
    type: 'string',
    default: process.env.DB_PASSWORD || 'metasfresh'
  })
  .option('start-time', {
    alias: 's',
    description: 'Start time (ISO 8601 format)',
    type: 'string',
    demandOption: false
  })
  .option('end-time', {
    alias: 'e',
    description: 'End time (ISO 8601 format)',
    type: 'string',
    demandOption: false
  })
  .option('min-id', {
    description: 'Minimum API_Request_Audit_ID (inclusive)',
    type: 'number',
    demandOption: false
  })
  .option('max-id', {
    description: 'Maximum API_Request_Audit_ID (inclusive)',
    type: 'number',
    demandOption: false
  })
  .option('path-filter', {
    alias: 'f',
    description: 'SQL LIKE pattern for path filtering (e.g., "/api/v2/%")',
    type: 'string',
    default: '%'
  })
  .option('status', {
    description: 'Filter by status (RECEIVED, PROCESSED, ERROR)',
    type: 'string',
    default: 'PROCESSED'
  })
  .option('output', {
    alias: 'o',
    description: 'Output file path',
    type: 'string',
    default: 'test-data/extracted-api-audit.json'
  })
  .option('limit', {
    alias: 'l',
    description: 'Maximum number of requests to extract',
    type: 'number',
    default: 10000
  })
  .option('exclude-headers', {
    description: 'Comma-separated list of headers to exclude',
    type: 'string',
    default: 'Host,Connection,Content-Length,User-Agent'
  })
  .option('include-responses', {
    description: 'Include expected response data in output',
    type: 'boolean',
    default: true
  })
  .option('show-sql', {
    description: 'Output the SQL query and exit (for debugging)',
    type: 'boolean',
    default: false
  })
  .option('debug', {
    description: 'Enable debug output (shows SQL queries)',
    type: 'boolean',
    default: false
  })
  .help()
  .alias('help', 'h')
  .parse();

// Database configuration
const dbConfig = {
  host: argv.dbHost,
  port: argv.dbPort,
  database: argv.dbName,
  user: argv.dbUser,
  password: argv.dbPassword,
  max: 5,
  idleTimeoutMillis: 30000,
  connectionTimeoutMillis: 5000,
};

/**
 * Build SQL query based on filters
 */
function buildQuery(includeResponses, countOnly = false) {
  const params = [];
  const whereClauses = [];
  let paramIndex = 1;

  // Base query
  let query = '';

  if (countOnly) {
    query = `
    SELECT COUNT(*) as total
    FROM API_Request_Audit r
  `;
  } else {
    query = `
    SELECT
      r.API_Request_Audit_ID,
      r.Method,
      r.RequestURI,
      r.Path,
      r.Body as RequestBody,
      r.HttpHeaders as RequestHeaders,
      r.Time as RequestTime,
      r.AD_User_ID,
      r.AD_Org_ID,
      r.Status
  `;

    if (includeResponses) {
      query += `,
      resp.HttpCode as ExpectedHttpCode,
      resp.Body as ExpectedResponseBody,
      resp.HttpHeaders as ResponseHeaders,
      resp.Time as ResponseTime,
      EXTRACT(EPOCH FROM (resp.Time - r.Time)) * 1000 as ActualResponseTime_ms
    `;
    }

    query += `
    FROM API_Request_Audit r
  `;

    if (includeResponses) {
      query += `
    LEFT JOIN API_Response_Audit resp
      ON r.API_Request_Audit_ID = resp.API_Request_Audit_ID
    `;
    }
  }

  // Add WHERE clauses
  if (argv.status) {
    whereClauses.push(`r.Status = $${paramIndex}`);
    params.push(argv.status);
    paramIndex++;
  }

  if (argv.minId) {
    whereClauses.push(`r.API_Request_Audit_ID >= $${paramIndex}`);
    params.push(argv.minId);
    paramIndex++;
  }

  if (argv.maxId) {
    whereClauses.push(`r.API_Request_Audit_ID <= $${paramIndex}`);
    params.push(argv.maxId);
    paramIndex++;
  }

  if (argv.startTime) {
    whereClauses.push(`r.Time >= $${paramIndex}`);
    params.push(argv.startTime);
    paramIndex++;
  }

  if (argv.endTime) {
    whereClauses.push(`r.Time <= $${paramIndex}`);
    params.push(argv.endTime);
    paramIndex++;
  }

  if (argv.pathFilter && argv.pathFilter !== '%') {
    whereClauses.push(`r.Path LIKE $${paramIndex}`);
    params.push(argv.pathFilter);
    paramIndex++;
  }

  if (whereClauses.length > 0) {
    query += `
    WHERE ${whereClauses.join(' AND ')}`;
  }

  if (!countOnly) {
    query += `
    ORDER BY r.Time ASC
    LIMIT $${paramIndex}
  `;
    params.push(argv.limit);
  }

  return { query, params };
}

/**
 * Format SQL query with parameters for display
 */
function formatQueryForDisplay(query, params) {
  let formattedQuery = query;
  params.forEach((param, index) => {
    const placeholder = `$${index + 1}`;
    const value = typeof param === 'string' ? `'${param}'` : param;
    formattedQuery = formattedQuery.replace(placeholder, value);
  });
  return formattedQuery;
}

/**
 * Parse and filter HTTP headers
 */
function filterHeaders(headersJson, excludeList) {
  if (!headersJson) return {};

  try {
    const headers = typeof headersJson === 'string'
      ? JSON.parse(headersJson)
      : headersJson;

    const excludeSet = new Set(
      excludeList.split(',').map(h => h.trim().toLowerCase())
    );

    const filtered = {};
    for (const [key, value] of Object.entries(headers)) {
      if (!excludeSet.has(key.toLowerCase())) {
        filtered[key] = value;
      }
    }

    return filtered;
  } catch (error) {
    console.warn(`Failed to parse headers: ${error.message}`);
    return {};
  }
}

/**
 * Transform database row to k6 format
 */
function transformRow(row) {
  const request = {
    id: row.api_request_audit_id,
    method: row.method,
    path: row.requesturi || row.path,  // Use RequestURI (the actual URI path), fallback to Path if not available
    originalPath: row.path,  // Keep original path for reference
    body: row.requestbody,
    headers: filterHeaders(row.requestheaders, argv.excludeHeaders),
    timestamp: row.requesttime,
    userId: row.ad_user_id,
    orgId: row.ad_org_id,
    status: row.status
  };

  if (argv.includeResponses) {
    request.expectedHttpCode = row.expectedhttpcode;
    request.expectedResponseBody = row.expectedresponsebody;
    request.responseHeaders = filterHeaders(row.responseheaders, argv.excludeHeaders);
    request.actualResponseTime = row.actualresponsetime_ms;
  }

  return request;
}

/**
 * Main extraction function
 */
async function extractAuditData() {
  const pool = new Pool(dbConfig);

  try {
    console.log('Connecting to database...');
    console.log(`  Host: ${argv.dbHost}:${argv.dbPort}`);
    console.log(`  Database: ${argv.dbName}`);
    console.log(`  User: ${argv.dbUser}`);

    // Test connection
    const client = await pool.connect();
    console.log('✓ Connected to database');
    client.release();

    // Build queries
    const countQueryObj = buildQuery(argv.includeResponses, true);
    const dataQueryObj = buildQuery(argv.includeResponses, false);

    console.log('\nExtraction filters:');
    if (argv.minId) console.log(`  Min ID: ${argv.minId}`);
    if (argv.maxId) console.log(`  Max ID: ${argv.maxId}`);
    if (argv.startTime) console.log(`  Start time: ${argv.startTime}`);
    if (argv.endTime) console.log(`  End time: ${argv.endTime}`);
    console.log(`  Path filter: ${argv.pathFilter}`);
    console.log(`  Status: ${argv.status}`);
    console.log(`  Limit: ${argv.limit}`);
    console.log(`  Include responses: ${argv.includeResponses}`);

    // Show SQL if requested
    if (argv.showSql || argv.debug) {
      console.log('\n========== COUNT QUERY ==========');
      console.log(formatQueryForDisplay(countQueryObj.query, countQueryObj.params));
      console.log('\n========== DATA QUERY ==========');
      console.log(formatQueryForDisplay(dataQueryObj.query, dataQueryObj.params));
      console.log('=================================\n');

      if (argv.showSql) {
        console.log('Exiting (--show-sql flag set)');
        process.exit(0);
      }
    }

    // First, count matching requests
    console.log('\nChecking how many requests match filters...');
    const countResult = await pool.query(countQueryObj.query, countQueryObj.params);
    const totalMatching = parseInt(countResult.rows[0].total);

    console.log(`✓ Found ${totalMatching} requests matching filters`);

    if (totalMatching === 0) {
      console.log('\n⚠️  WARNING: No requests found matching your filters!');
      console.log('\nTroubleshooting tips:');
      console.log('1. Check the time range - are there requests in that period?');
      console.log('   Try: SELECT MIN(Time), MAX(Time) FROM API_Request_Audit;');
      console.log('\n2. Check the path filter - does it match any paths?');
      console.log('   Try: SELECT DISTINCT Path FROM API_Request_Audit LIMIT 10;');
      console.log('\n3. Check the status filter - are there PROCESSED requests?');
      console.log('   Try: SELECT Status, COUNT(*) FROM API_Request_Audit GROUP BY Status;');
      console.log('\n4. Try removing all filters temporarily:');
      console.log('   --path-filter "%" --status "" (and remove time filters)');
      console.log('\nRun with --show-sql to see the exact query being executed.');
      process.exit(1);
    }

    if (totalMatching < argv.limit) {
      console.log(`  Note: Will extract all ${totalMatching} requests (less than limit of ${argv.limit})`);
    } else {
      console.log(`  Note: Will extract first ${argv.limit} requests (total matching: ${totalMatching})`);
    }

    console.log('\nExecuting extraction query...');
    const result = await pool.query(dataQueryObj.query, dataQueryObj.params);

    console.log(`✓ Retrieved ${result.rows.length} requests`);

    // Transform results
    console.log('\nTransforming data...');
    const requests = result.rows.map(transformRow);

    // Prepare output
    const output = {
      metadata: {
        extractedAt: new Date().toISOString(),
        source: {
          host: argv.dbHost,
          database: argv.dbName
        },
        filters: {
          startTime: argv.startTime || null,
          endTime: argv.endTime || null,
          pathFilter: argv.pathFilter,
          status: argv.status,
          limit: argv.limit
        },
        count: requests.length
      },
      requests: requests
    };

    // Write output file
    const outputPath = path.resolve(argv.output);
    const outputDir = path.dirname(outputPath);

    // Ensure output directory exists
    if (!fs.existsSync(outputDir)) {
      fs.mkdirSync(outputDir, { recursive: true });
      console.log(`✓ Created output directory: ${outputDir}`);
    }

    fs.writeFileSync(outputPath, JSON.stringify(output, null, 2));
    console.log(`✓ Wrote output to: ${outputPath}`);

    // Print summary statistics
    console.log('\nSummary:');
    console.log(`  Total requests: ${requests.length}`);

    const methodCounts = {};
    const pathCounts = {};
    requests.forEach(req => {
      methodCounts[req.method] = (methodCounts[req.method] || 0) + 1;
      const pathBase = req.path.split('?')[0];
      pathCounts[pathBase] = (pathCounts[pathBase] || 0) + 1;
    });

    console.log('\n  By method:');
    Object.entries(methodCounts)
      .sort((a, b) => b[1] - a[1])
      .forEach(([method, count]) => {
        console.log(`    ${method}: ${count}`);
      });

    console.log('\n  Top 10 request URIs:');
    Object.entries(pathCounts)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 10)
      .forEach(([path, count]) => {
        console.log(`    ${path}: ${count}`);
      });

    console.log('\n  Note: Using RequestURI column for replay (actual URI path on server)');
    console.log('        Path column contains original full URL for reference');

    if (argv.includeResponses) {
      const requestsWithResponseTime = requests.filter(r => r.actualResponseTime != null && typeof r.actualResponseTime === 'number');
      if (requestsWithResponseTime.length > 0) {
        const avgResponseTime = requestsWithResponseTime
          .reduce((sum, r) => sum + r.actualResponseTime, 0) / requestsWithResponseTime.length;
        console.log(`\n  Average response time: ${avgResponseTime.toFixed(2)}ms (${requestsWithResponseTime.length} requests with response data)`);
      } else {
        console.log(`\n  Average response time: N/A (no response time data available)`);
      }
    }

  } catch (error) {
    console.error('\n✗ Error during extraction:');
    console.error(error);
    process.exit(1);
  } finally {
    await pool.end();
    console.log('\n✓ Database connection closed');
  }
}

// Run extraction
extractAuditData();
