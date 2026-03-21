import { defineConfig, devices } from '@playwright/test';
import * as os from 'node:os';

export default defineConfig({
  testDir: './tests',
  workers: 1, // Sequential execution for test data isolation
  fullyParallel: false,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  timeout: 60000, // 1 minute per test (reduced for faster validation)

  reporter: [
    ['list'],
    ['html', { outputFolder: 'playwright-report/html', open: 'never' }],
    ['junit', { outputFile: 'playwright-report/junit/results.xml' }],
    // Allure reporter for rich, stakeholder-friendly reports
    [
      'allure-playwright',
      {
        resultsDir: 'allure-results',
        detail: true,
        suiteTitle: true,
        // Link templates for clickable references in reports
        links: {
          issue: {
            nameTemplate: 'GitHub Issue #%s',
            urlTemplate: 'https://github.com/metasfresh/metasfresh/issues/%s',
          },
          tms: {
            nameTemplate: 'Feature %s',
            urlTemplate: 'https://docs.google.com/spreadsheets/d/1HYDaiNZVseCg4WtIaxJQ-LLclNl7vkHXp6WsMEaKK9A/edit#gid=1284833774&range=A%s',
          },
          epic: {
            nameTemplate: 'Epic %s',
            urlTemplate: 'https://docs.google.com/spreadsheets/d/1HYDaiNZVseCg4WtIaxJQ-LLclNl7vkHXp6WsMEaKK9A/edit#gid=0&range=A%s',
          },
        },
        // Categorize failures for easier analysis
        categories: [
          {
            name: 'Language-specific failures',
            messageRegex: '.*language.*|.*translation.*|.*locale.*',
            matchedStatuses: ['failed', 'broken'],
          },
          {
            name: 'Timing/Timeout issues',
            messageRegex: '.*timeout.*|.*timed out.*|.*waiting.*',
            matchedStatuses: ['failed', 'broken'],
          },
          {
            name: 'Backend API errors',
            messageRegex: '.*Backend API error.*|.*500.*|.*503.*|.*ECONNREFUSED.*',
            matchedStatuses: ['failed', 'broken'],
          },
          {
            name: 'PDF validation failures',
            messageRegex: '.*PDF.*|.*pdf.*',
            matchedStatuses: ['failed'],
          },
        ],
        // Environment info displayed in report
        environmentInfo: {
          os_platform: os.platform(),
          os_release: os.release(),
          node_version: process.version,
          test_environment: process.env.CI ? 'CI' : 'Local',
          frontend_url: process.env.FRONTEND_BASE_URL || 'http://localhost:3000',
        },
      },
    ],
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
