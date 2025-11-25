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
  },

  projects: [
    {
      name: 'Desktop Chrome',
      use: {
        ...devices['Desktop Chrome'],
        launchOptions: {
          args: [
            '--no-sandbox',
            '--disable-web-security',
            '--ignore-certificate-errors',
            '--allow-insecure-localhost',
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
