import { expect } from '@playwright/test';
import fs from 'fs';
import path from 'path';

const toastifyCss = fs.readFileSync(path.resolve(__dirname, '../../fixtures/ReactToastify-7.0.0.css'), 'utf8');

// the inline styles of the error toast, as in src/utils/toastLayout.js of the mobile frontend
const ERROR_TOAST_STYLE = { maxHeight: 'calc(100vh - 6rem)' };
const ERROR_TOAST_BODY_STYLE = { overflow: 'auto', maxHeight: 'calc(100vh - 8rem)' };
const css = (style) => Object.entries(style).map(([k, v]) => `${k.replace(/[A-Z]/g, (c) => `-${c.toLowerCase()}`)}:${v}`).join(';');

const NAME = 'ErrorToastReplica';

/** A static copy of an error toast (react-toastify markup and CSS), not the app. Takes the `page` of the spec's own test fixture. */
export const ErrorToastReplica = {
    render: async (page, { lines }) => {
        const message = Array.from({ length: lines }, (_, i) => `<div>line ${i}</div>`).join('');
        await page.setContent(`<meta name="viewport" content="width=device-width, initial-scale=1"><style>${toastifyCss}</style>
        <div class="Toastify"><div class="Toastify__toast-container Toastify__toast-container--bottom-center">
            <div class="Toastify__toast Toastify__toast--error" style="${css(ERROR_TOAST_STYLE)}">
                <div role="alert" class="Toastify__toast-body" style="${css(ERROR_TOAST_BODY_STYLE)}">${message}</div>
                <button class="Toastify__close-button Toastify__close-button--error"><svg width="14" height="16"><path d="M0 0L14 16"/></svg></button>
            </div></div></div>`);
    },

    expectCloseButtonOnScreen: async (page) => {
        const closeBox = await page.locator('.Toastify__close-button--error').boundingBox();
        const viewport = page.viewportSize();
        expect(closeBox.y, `${NAME}: close button below the top edge`).toBeGreaterThanOrEqual(0);
        expect(closeBox.y + closeBox.height, `${NAME}: close button above the bottom edge`).toBeLessThanOrEqual(viewport.height);
    },

    expectLongMessageScrolls: async (page, { lastLine }) => {
        const body = page.locator('.Toastify__toast-body');
        const { scrollHeight, clientHeight } = await body.evaluate((el) => ({ scrollHeight: el.scrollHeight, clientHeight: el.clientHeight }));
        expect(scrollHeight, 'the message is taller than the body: it scrolls instead of being cut').toBeGreaterThan(clientHeight + 1);
        await body.evaluate((el) => { el.scrollTop = el.scrollHeight; });
        await expect(page.getByText(lastLine, { exact: true })).toBeInViewport({ ratio: 1 });
    },
};
