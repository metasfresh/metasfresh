import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  workers: 1, // Sequential execution for test data isolation
  fullyParallel: false,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  timeout: 120000, // 2 minutes per test

  reporter: [
    ['list'],
    ['html', { outputFolder: 'playwright-report/html', open: 'never' }],
    ['junit', { outputFile: 'playwright-report/junit/results.xml' }],
  ],

  use: {
    baseURL: process.env.FRONTEND_BASE_URL || 'http://localhost:3000',
    trace: 'on',
    video: 'on',
    screenshot: 'only-on-failure',
    viewport: { width: 1920, height: 1080 },
    // Record all network traffic to HAR files
    recordHar: { path: 'playwright-report/network/', mode: 'minimal' },
  },

  projects: [
    {
      name: 'Desktop Chrome',
      use: {
        ...devices['Desktop Chrome'],
        launchOptions: {
          args: [
            '--no-sandbox', // Avoids sandboxing issues inside Docker
            '--unsafely-treat-insecure-origin-as-secure=http://webapi-test:8080', // Treats it as a secure origin
            '--disable-features=StrictOriginPolicy,HttpsOnlyMode,BlockInsecurePrivateNetworkRequests', // Disables HSTS enforcement
            '--disable-site-isolation-trials', // Helps disable security sandboxing
            '--disable-web-security', // Disables web security (CORS, mixed content, etc.)
            '--ignore-certificate-errors', // Ignores SSL certificate errors
            '--allow-insecure-localhost', // Allows HTTP on local addresses
            '--allow-running-insecure-content', // Allows mixed content (HTTP on HTTPS)
          ],
        },
      },
    },
  ],
});

// Test context shared across test steps
export const testContext = {};

// Custom test fixture to manage page and context
export const test = require('@playwright/test').test.extend({
  page: async ({ page }, use) => {
    global.currentPage = page;
    Object.keys(testContext).forEach(key => delete testContext[key]);
    await use(page);
  },
});
