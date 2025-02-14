// @ts-check
import { defineConfig, devices, test as testOrig } from '@playwright/test';
import { setCurrentPage } from "./tests/utils/common";

/**
 * @see https://playwright.dev/docs/test-configuration
 */
export default defineConfig({
    testDir: './tests',
    workers: 1, // Runs test files sequentially
    fullyParallel: false, // Run tests in files in parallel
    forbidOnly: !!process.env.CI, // Fail the build on CI if you accidentally left test.only in the source code.
    retries: process.env.CI ? 2 : 0, // Retry on CI only
    reporter: [ // See https://playwright.dev/docs/test-reporters
        ['list'],
        ['html', { outputFolder: 'playwright-report/html', open: 'never' }],
        ['junit', { outputFile: 'playwright-report/junit/results.xml' }],
    ],
    timeout: 120000, // Set global timeout
    /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
    use: {
        /* Base URL to use in actions like `await page.goto('/')`. */
        // baseURL: 'http://127.0.0.1:3000',

        /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
        //trace: 'on-first-retry',
        trace: 'on',

        react: true,  // Enables React component detection

        // actionTimeout: 60000, // per action timeout
    },

    /* Configure projects for major browsers */
    projects: [
        // {
        //     name: 'chromium',
        //     use: {...devices['Desktop Chrome']},
        // },
        // {
        //     name: 'firefox',
        //     use: {...devices['Desktop Firefox']},
        // },
        // {
        //     name: 'webkit',
        //     use: {...devices['Desktop Safari']},
        // },

        /* Test against mobile viewports. */
        {
            name: 'Mobile Chrome',
            use: {
                ...devices['Pixel 5'],
                launchOptions: {
                    args: [
                        '--no-sandbox', // Avoids sandboxing issues inside Docker
                        '--unsafely-treat-insecure-origin-as-secure=http://app-test:8282', // Treats it as a secure origin
                        '--disable-features=StrictOriginPolicy,HttpsOnlyMode,BlockInsecurePrivateNetworkRequests', // Disables HSTS enforcement
                        '--disable-site-isolation-trials', // Helps disable security sandboxing
                        '--disable-web-security', // Disables web security (CORS, mixed content, etc.)
                        '--ignore-certificate-errors', // Ignores SSL certificate errors
                        '--allow-insecure-localhost', // Allows HTTP on local addresses
                        '--allow-running-insecure-content', // Allows mixed content (HTTP on HTTPS)
                        // '--user-data-dir=/tmp/chrome-test-profile' // Ensures fresh profile each time (prevents stored HSTS rules)
                    ],
                },
            },
        },
        // {
        //   name: 'Mobile Safari',
        //   use: { ...devices['iPhone 12'] },
        // },

        /* Test against branded browsers. */
        // {
        //   name: 'Microsoft Edge',
        //   use: { ...devices['Desktop Edge'], channel: 'msedge' },
        // },
        // {
        //   name: 'Google Chrome',
        //   use: { ...devices['Desktop Chrome'], channel: 'chrome' },
        // },
    ],

    /* Run your local dev server before starting the tests */
    // webServer: {
    //   command: 'npm run start',
    //   url: 'http://127.0.0.1:3000',
    //   reuseExistingServer: !process.env.CI,
    // },
});

export const test = testOrig.extend({
    page: async ({ page }, use) => {
        setCurrentPage(page);
        await use(page);
    },
});
