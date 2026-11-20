import { expect, test } from '@playwright/test';
import fs from 'fs';
import path from 'path';
import { ERROR_TOAST_BODY_STYLE, ERROR_TOAST_STYLE } from '../../../../misc/services/mobile-webui/mobile-webui-frontend/src/utils/toastLayout';

/**
 * Layout of a long error toast on a handheld screen, in a real browser with the real react-toastify stylesheet and the
 * very style values `toast.jsx` passes: the message scrolls inside the toast and the close button stays on screen.
 * Needs no backend and no login.
 */
const toastifyCss = fs.readFileSync(
    path.resolve(__dirname, '../../../../misc/services/mobile-webui/mobile-webui-frontend/node_modules/react-toastify/dist/ReactToastify.css'),
    'utf8');
const css = (style) => Object.entries(style).map(([k, v]) => `${k.replace(/[A-Z]/g, (c) => `-${c.toLowerCase()}`)}:${v}`).join(';');

test.use({ viewport: { width: 360, height: 640 } });

test('a long error message scrolls inside the toast and the close button stays on screen', async ({ page }) => {
    const longMessage = Array.from({ length: 60 }, (_, i) => `<div>line ${i}</div>`).join('');
    await page.setContent(`<meta name="viewport" content="width=device-width, initial-scale=1"><style>${toastifyCss}</style>
        <div class="Toastify"><div class="Toastify__toast-container Toastify__toast-container--bottom-center">
            <div class="Toastify__toast Toastify__toast--error" style="${css(ERROR_TOAST_STYLE)}">
                <div role="alert" class="Toastify__toast-body" style="${css(ERROR_TOAST_BODY_STYLE)}">${longMessage}</div>
                <button class="Toastify__close-button Toastify__close-button--error"><svg width="14" height="16"><path d="M0 0L14 16"/></svg></button>
            </div></div></div>`);

    const closeBox = await page.locator('.Toastify__close-button--error').boundingBox();
    const viewport = page.viewportSize();
    expect(closeBox.y, 'close button below the top edge').toBeGreaterThanOrEqual(0);
    expect(closeBox.y + closeBox.height, 'close button above the bottom edge').toBeLessThanOrEqual(viewport.height);
    const body = page.locator('.Toastify__toast-body');
    const { scrollHeight, clientHeight } = await body.evaluate((el) => ({ scrollHeight: el.scrollHeight, clientHeight: el.clientHeight }));
    expect(scrollHeight, 'the message is taller than the body: it scrolls instead of being cut').toBeGreaterThan(clientHeight + 1);
    await body.evaluate((el) => { el.scrollTop = el.scrollHeight; });
    await expect(page.getByText('line 59', { exact: true })).toBeInViewport({ ratio: 1 });
});
