import { test } from '@playwright/test';
import { ErrorToastReplica } from '../utils/components/ErrorToastReplica';

test.use({ viewport: { width: 360, height: 640 } });

test('a long error message scrolls inside the toast and the close button stays on screen', async ({ page }) => {
    await ErrorToastReplica.render(page, { lines: 60 });
    await ErrorToastReplica.expectCloseButtonOnScreen(page);
    await ErrorToastReplica.expectLongMessageScrolls(page, { lastLine: 'line 59' });
});
