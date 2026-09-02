#!/usr/bin/env node

/**
 * Simple script to create a test user via the Backend API.
 *
 * Usage:
 *   node scripts/create-test-user.mjs
 *   node scripts/create-test-user.mjs de_DE
 *   node scripts/create-test-user.mjs en_US with-vendor
 *
 * This solves the problem of Claude trying to use hardcoded credentials
 * when exploring with Playwright MCP tools. Always run this first!
 */

// Node 20+ has built-in fetch, no import needed

const TESTING_API_BASE_URL = process.env.TESTING_API_BASE_URL || 'http://localhost:8282/api/v2';

async function createTestUser(language = 'en_US', includeVendor = false) {
  const request = {
    login: {
      user: {
        language,
      },
    },
  };

  // Optionally include vendor and product for Material Receipt testing
  if (includeVendor) {
    request.bpartners = {
      VENDOR1: {
        isVendor: true,
        isCustomer: false,
        isSoPriceList: false,
      },
    };
    request.products = {
      PRODUCT1: {
        name: 'Test Product',
        value: 'TEST-001',
        type: 'Item',
        prices: [
          {
            price: 10.0,
            currencyCode: 'EUR',
          },
        ],
      },
    };
  }

  try {
    console.error(`Creating test user (language: ${language})...`);
    const response = await fetch(`${TESTING_API_BASE_URL}/frontendTesting`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      throw new Error(`HTTP ${response.status}: ${await response.text()}`);
    }

    const data = await response.json();

    // Output credentials in easy-to-use format
    const result = {
      username: data.login.user.username,
      password: data.login.user.password,
      language,
    };

    if (includeVendor) {
      result.vendor = data.bpartners.VENDOR1.bpartnerCode;
      result.product = data.products.PRODUCT1.productName;
    }

    // JSON output to stdout (for scripting)
    console.log(JSON.stringify(result, null, 2));

    // Human-readable to stderr (for manual use)
    console.error('\n✓ Test user created successfully!');
    console.error(`  Username: ${result.username}`);
    console.error(`  Password: ${result.password}`);
    console.error(`  Language: ${result.language}`);
    if (includeVendor) {
      console.error(`  Vendor: ${result.vendor}`);
      console.error(`  Product: ${result.product}`);
    }

    return result;
  } catch (error) {
    console.error('✗ Failed to create test user:', error.message);
    process.exit(1);
  }
}

// Parse command line arguments
const language = process.argv[2] || 'en_US';
const includeVendor = process.argv.includes('with-vendor');

createTestUser(language, includeVendor);
