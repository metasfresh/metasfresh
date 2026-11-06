import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { Backend } from '../utils/Backend';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { LoginPage } from '../utils/pages/LoginPage';

/**
 * Change-password process (AD_User_ChangePassword) — regression guard.
 *
 * The process parameters OldPassword / NewPassword / NewPasswordRetype are Password widgets. Their
 * values reach the browser inside the process-parameter responses, and the show-password button
 * has to reveal exactly what the user typed. The process must still run with those values and
 * the new password must be the one that logs the user in afterwards.
 *
 * 1. Admin opens the user window on a freshly created system user
 * 2. Runs "Change password", types the three parameters, reveals each one with the show-password button
 * 3. Starts the process, logs out, and logs in as the target user with the new password
 */

const USER_WINDOW_ID = 108; // AD_User ("User (AD_User)"), table AD_User

test.describe('User window — change password process', () => {
  test('typed passwords are revealed exactly and the new password logs the user in', async ({ page }) => {
    allure.epic('E0182: System Configuration');
    allure.tag('F9111: Reference Type Password');
    allure.tag('F9111');
    allure.story('Change-password process parameters');
    allure.severity('critical');

    allure.description(`
## AD_User_ChangePassword — Password process parameters

Runs the "Change password" process of the user window against a freshly created system user:

1. The show-password button reveals exactly the typed text of OldPassword, NewPassword and NewPasswordRetype
2. The process runs with those values
3. After logging out, the target user can log in with the new password
`);

    const unique = `${Date.now()}${Math.random().toString(36).slice(2, 8)}`;
    const newPassword = `New!Pass-${unique}`;
    const login = `b3cp${unique}`;

    let targetUserId;
    await test.step('Create the target system user (password = login)', async () => {
      const masterdata = await Backend.createMasterdata({
        request: { login: { target: { login, language: 'en_US', firstname: 'ChangePw', lastname: 'Target' } } },
      });
      targetUserId = masterdata.context['de.metas.user.UserId:target'];
      expect(targetUserId, 'masterdata must report the id of the created user').toBeTruthy();
    });

    await test.step('Admin opens the user window on the target user', async () => {
      // LoginPage.login misses the 2nd /login/authenticate of the admin's two-role login (selectors-and-gotchas.md)
      await loginWithMasterdataUser(page, { username: 'metasfresh', password: 'metasfresh' });
      await page.goto(`${FRONTEND_BASE_URL}/window/${USER_WINDOW_ID}/${targetUserId}`);
      await page.locator('.form-field-Login').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Login input')).toHaveValue(login);
    });

    const modal = page.locator('.panel-modal');
    const fieldInput = (name) => modal.locator(`.form-field-${name} input`);

    await test.step('Open the Change password process', async () => {
      await page.locator('.header-left-side .btn-header').click();
      await page.locator('[data-testid="action-AD_User_ChangePassword"]').click();
      await modal.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      for (const name of ['OldPassword', 'NewPassword', 'NewPasswordRetype']) {
        await expect(fieldInput(name), `${name} is rendered masked`).toHaveAttribute('type', 'password');
      }
    });

    // The old password is the target user's current one: the masterdata gives a user the login as password.
    const typed = [
      ['OldPassword', login],
      ['NewPassword', newPassword],
      ['NewPasswordRetype', newPassword],
    ];
    for (const [name, value] of typed) {
      await test.step(`Type ${name} and reveal it`, async () => {
        const input = fieldInput(name);
        const patched = page.waitForResponse(
          (response) =>
            response.request().method() === 'PATCH' &&
            response.url().includes('/rest/api/process/') &&
            (response.request().postData() || '').includes(`"path":"${name}"`),
          { timeout: SLOW_ACTION_TIMEOUT }
        );
        await input.click();
        // per-key events like a real user's typing (fill() sets the value in one step)
        await input.pressSequentially(value);
        await input.press('Tab');
        const response = await patched;
        expect(response.ok(), `PATCH of ${name} failed: HTTP ${response.status()}`).toBe(true);

        const showButton = modal.locator(`.form-field-${name} .btn:has(.meta-icon-show)`);
        const box = await showButton.boundingBox();
        await page.mouse.move(box.x + box.width / 2, box.y + box.height / 2);
        await page.mouse.down();
        try {
          await expect(input, `${name} is revealed while the button is held`).toHaveAttribute('type', 'text');
          await expect(input, `${name} reveals exactly the typed text`).toHaveValue(value);
        } finally {
          await page.mouse.up();
        }
        await expect(input, `${name} is masked again after release`).toHaveAttribute('type', 'password');
      });
    }

    await test.step('Run the process', async () => {
      const started = page.waitForResponse(
        (response) => response.url().includes('/rest/api/process/') && response.url().endsWith('/start'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await modal.locator('[data-testid="process-modal-start-button"]').click();
      const response = await started;
      expect(response.ok(), `process start failed: HTTP ${response.status()}`).toBe(true);
      const body = await response.json();
      expect(body.error, 'process result must not be an error').toBeFalsy();
      await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Log out and log in as the target user with the new password', async () => {
      await LoginPage.logout();
      await LoginPage.login({ username: login, password: newPassword });
      await LoginPage.expectLoggedIn();
      const session = await (await page.request.get(`${WEBAPI_BASE_URL}/userSession`)).json();
      expect(session.loggedIn, 'the session must belong to the target user').toBe(true);
      expect(session.username).toBe(login);
    });
  });
});
