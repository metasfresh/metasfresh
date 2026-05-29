#!/usr/bin/env node

/**
 * Layout Fetcher - Fetches window layouts from metasfresh webapi
 *
 * This script logs into metasfresh, captures session cookies, and fetches
 * window layout JSON from the webapi.
 *
 * Layout Types:
 *   - Detail view: /rest/api/window/{id}/layout (single record view)
 *   - Grid view: /rest/api/documentView/{id}/layout?viewType=grid (table view)
 *
 * Usage:
 *   node scripts/fetch-window-layout.js <windowId> [outputFile] [--grid]
 *
 *   # Fetch detail layout (default)
 *   node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json
 *
 *   # Fetch grid layout
 *   node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid
 *
 *   # Auto-generate filename
 *   node scripts/fetch-window-layout.js 183  # Creates: layouts/window-183.json
 *   node scripts/fetch-window-layout.js 183 --grid  # Creates: layouts/window-183-grid.json
 *
 * Environment Variables:
 *   FRONTEND_BASE_URL - Frontend URL (default: http://localhost:3000)
 *   WEBAPI_BASE_URL - WebAPI URL (default: http://localhost:8080)
 *   TESTING_API_BASE_URL - Testing API URL (default: http://localhost:8282/api/v2)
 */

const { chromium } = require('playwright');
const fs = require('fs');
const path = require('path');

// Configuration
const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3000';
const WEBAPI_BASE_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080';
const TESTING_API_BASE_URL = process.env.TESTING_API_BASE_URL || 'http://localhost:8282/api/v2';
const LANGUAGE = 'en_US';

/**
 * Create or retrieve a test user via the backend testing API
 */
async function createTestUser(page) {
  console.log('Creating test user via backend API...');

  const response = await page.request.post(`${TESTING_API_BASE_URL}/frontendTesting`, {
    data: {
      login: {
        user: { language: LANGUAGE },
      },
    },
    headers: { 'Content-Type': 'application/json' },
  });

  const responseBody = await response.json();

  if (responseBody.error || responseBody.errors || responseBody.stackTrace) {
    throw new Error('Failed to create test user: ' + JSON.stringify(responseBody, null, 2));
  }

  console.log('Test user created:', responseBody.login.user.username);
  return responseBody.login.user;
}

/**
 * Login to metasfresh frontend and capture session cookies
 */
async function login(page, username, password) {
  console.log(`Logging in as ${username}...`);

  // Navigate to login page
  await page.goto(`${FRONTEND_BASE_URL}/login`);

  // Wait for login form
  await page.locator('.login-container').waitFor({ state: 'visible', timeout: 20000 });

  // Fill credentials
  await page.locator('input[name="username"]').fill(username);
  await page.locator('input[name="password"]').fill(password);

  // Wait for login response
  const responsePromise = page.waitForResponse(
    (response) => response.url().includes('/login/authenticate')
  );

  // Click login button
  await page.locator('.btn-meta-success').click();

  const response = await responsePromise;
  const responseBody = await response.json();

  if (!response.ok()) {
    throw new Error('Login failed: ' + JSON.stringify(responseBody, null, 2));
  }

  // Wait for redirect away from login page
  await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: 20000 });

  console.log('Login successful!');

  // Extract session cookies
  const cookies = await page.context().cookies();
  const sessionCookie = cookies.find(c => c.name === 'SESSION');

  if (!sessionCookie) {
    throw new Error('No SESSION cookie found after login');
  }

  console.log('Session cookie captured:', sessionCookie.value.substring(0, 20) + '...');
  return sessionCookie.value;
}

/**
 * Fetch window layout using the session cookie
 * @param {Object} page - Playwright page object
 * @param {string} windowId - Window ID
 * @param {string} sessionCookie - Session cookie value
 * @param {boolean} isGrid - Whether to fetch grid layout (default: false for detail layout)
 */
async function fetchWindowLayout(page, windowId, sessionCookie, isGrid = false) {
  const layoutType = isGrid ? 'grid' : 'detail';
  console.log(`Fetching ${layoutType} layout for window ${windowId}...`);

  // Construct URL based on layout type
  let url;
  if (isGrid) {
    // Grid view: paginated table view
    url = `${WEBAPI_BASE_URL}/rest/api/documentView/${windowId}/layout?viewType=grid`;
  } else {
    // Detail view: single record view
    url = `${WEBAPI_BASE_URL}/rest/api/window/${windowId}/layout`;
  }

  const response = await page.request.get(url, {
    headers: {
      'Accept': 'application/json, text/plain, */*',
      'Accept-Language': LANGUAGE,
      'Cookie': `SESSION=${sessionCookie}`,
    },
  });

  if (!response.ok()) {
    const text = await response.text();
    throw new Error(`Failed to fetch ${layoutType} layout (${response.status()}): ${text}`);
  }

  const layout = await response.json();
  console.log(`${layoutType.charAt(0).toUpperCase() + layoutType.slice(1)} layout fetched successfully`);

  return layout;
}

/**
 * Save layout JSON to file
 */
function saveLayout(layout, outputPath) {
  // Ensure the layouts directory exists
  const dir = path.dirname(outputPath);
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir, { recursive: true });
    console.log(`Created directory: ${dir}`);
  }

  // Write the JSON file with pretty formatting
  fs.writeFileSync(outputPath, JSON.stringify(layout, null, 2), 'utf-8');
  console.log(`Layout saved to: ${outputPath}`);
}

/**
 * Main function
 */
async function main() {
  // Parse command line arguments
  const args = process.argv.slice(2);

  if (args.length < 1) {
    console.error('Usage: node fetch-window-layout.js <windowId> [outputFile] [--grid]');
    console.error('');
    console.error('Examples:');
    console.error('  node fetch-window-layout.js 181  # Detail layout');
    console.error('  node fetch-window-layout.js 181 --grid  # Grid layout');
    console.error('  node fetch-window-layout.js 181 layouts/purchase-order-181.json');
    console.error('  node fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid');
    process.exit(1);
  }

  const windowId = args[0];
  const isGrid = args.includes('--grid');

  // Determine output file
  let outputFile;
  if (args.length >= 2 && !args[1].startsWith('--')) {
    outputFile = args[1];
  } else {
    const suffix = isGrid ? '-grid' : '';
    outputFile = `layouts/window-${windowId}${suffix}.json`;
  }

  const outputPath = path.resolve(__dirname, '..', outputFile);
  const layoutType = isGrid ? 'grid' : 'detail';

  console.log('=== metasfresh Window Layout Fetcher ===');
  console.log(`Window ID: ${windowId}`);
  console.log(`Layout Type: ${layoutType}`);
  console.log(`Output: ${outputPath}`);
  console.log(`Frontend: ${FRONTEND_BASE_URL}`);
  console.log(`WebAPI: ${WEBAPI_BASE_URL}`);
  console.log(`Testing API: ${TESTING_API_BASE_URL}`);
  console.log('');

  // Launch browser
  const browser = await chromium.launch({ headless: true });
  const context = await browser.newContext({
    viewport: { width: 1920, height: 1080 },
  });
  const page = await context.newPage();

  try {
    // Step 1: Create test user
    const user = await createTestUser(page);

    // Step 2: Login and get session cookie
    const sessionCookie = await login(page, user.username, user.password);

    // Step 3: Fetch window layout
    const layout = await fetchWindowLayout(page, windowId, sessionCookie, isGrid);

    // Step 4: Save to file
    saveLayout(layout, outputPath);

    console.log('');
    console.log('✓ Layout fetched successfully!');

  } catch (error) {
    console.error('');
    console.error('✗ Error:', error.message);
    process.exit(1);
  } finally {
    await browser.close();
  }
}

// Run main function
if (require.main === module) {
  main().catch(err => {
    console.error('Unhandled error:', err);
    process.exit(1);
  });
}

module.exports = { createTestUser, login, fetchWindowLayout, saveLayout };
