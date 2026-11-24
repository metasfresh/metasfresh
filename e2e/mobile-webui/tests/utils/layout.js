// Layout soundness of the screen that is shown right now, measured in the browser: nothing sticks out of the screen sideways, no text is cut
// off, no two controls overlap, every control is big enough to tap, and (a dialog covering the screen) every control of the dialog is on the
// screen without scrolling. A normal screen of the app scrolls inside `.app-content` (the footer is fixed); a control that must be reachable
// there is checked with `expectReachable`.
import fs from 'fs';
import path from 'path';
import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { page } from './common';

const MIN_TAP_SIZE = 36; // px; Bulma's default button is 40 px high

/** Runs in the browser: returns a list of problem descriptions (empty = sound). `scope` = CSS selector of the area to measure. */
const measureLayout = ({ scope, minTapSize }) => {
    const vw = window.innerWidth;
    const vh = window.innerHeight;
    const root = document.querySelector(scope);
    if (!root) return [`no element for scope '${scope}'`];
    const isOverlay = root.matches('.prompt-dialog') || !!root.closest('.prompt-dialog');

    const describe = (el) => {
        const text = (el.innerText || el.value || el.placeholder || '').trim().replace(/\s+/g, ' ').slice(0, 40);
        const testId = el.getAttribute('data-testid');
        return `${el.tagName.toLowerCase()}${el.id ? '#' + el.id : ''}${testId ? `[${testId}]` : ''}${text ? ` "${text}"` : ''}`;
    };
    const isShown = (el) => {
        const style = getComputedStyle(el);
        if (style.display === 'none' || style.visibility === 'hidden' || Number(style.opacity) === 0) return false;
        const r = el.getBoundingClientRect();
        return r.width > 0 && r.height > 0;
    };
    const clippedByScroller = (el) => {
        // inside a container that scrolls or clips sideways on purpose: its own box is what counts, not the content
        for (let p = el.parentElement; p && p !== document.body; p = p.parentElement) {
            const ox = getComputedStyle(p).overflowX;
            if (ox === 'auto' || ox === 'scroll' || ox === 'hidden') return true;
        }
        return false;
    };

    const issues = [];
    if (document.documentElement.scrollWidth > vw + 1) {
        issues.push(`page is wider than the screen (${document.documentElement.scrollWidth} > ${vw})`);
    }

    // The app's own chrome (caption bar with its sliding title, fixed footer) and the job header table / job line buttons are base screens,
    // not changed by the CU quantity entry modes: a long customer or slot name is cut there by the base layout (view-header.scss `td { overflow: hidden }`).
    const isNewModeScreen = (el) => !!el.closest('[data-cu-qty-entry-mode="SCAN_EACH_CU"], [data-cu-qty-entry-mode="SCAN_ONCE_ENTER_QTY"]');
    const isBaseChrome = (el) => !!el.closest('.app-header, .app-footer') || (!!el.closest('.view-header') && !isNewModeScreen(el));
    const isLineButtonText = (el) => !!el.closest('button[id^="line-"]');
    const all = [...root.querySelectorAll('*')].filter(isShown).filter((el) => !isBaseChrome(el));
    const controls = all.filter((el) => !el.closest('.Toastify') && el.matches('button, input:not([type=hidden]), select, textarea, a[href], [role=button]') && el.getBoundingClientRect().height >= 4);

    // 1. nothing sticks out sideways (text included)
    for (const el of all) {
        if (el.closest('.Toastify')) continue; // toasts are checked by their own rule
        const r = el.getBoundingClientRect();
        if (el.children.length === 0 && (el.textContent || '').trim() === '' && !el.matches('input, button')) continue;
        if ((r.left < -0.5 || r.right > vw + 0.5) && !clippedByScroller(el)) {
            issues.push(`sticks out sideways: ${describe(el)} (left ${Math.round(r.left)}, right ${Math.round(r.right)}, screen ${vw})`);
        }
    }

    // 2. text is not cut off (an element that clips its content and has more than it shows)
    for (const el of all) {
        const style = getComputedStyle(el);
        if (isLineButtonText(el) || style.textOverflow === 'ellipsis') continue; // ellipsis = the app's own, designed way to shorten a long value
        const clipsX = style.overflowX !== 'visible' && style.overflowX !== 'auto' && style.overflowX !== 'scroll';
        if (clipsX && el.scrollWidth > el.clientWidth + 1 && (el.innerText || '').trim() !== '') {
            issues.push(`text cut off sideways: ${describe(el)} (content ${el.scrollWidth} px in ${el.clientWidth} px)`);
        }
        const clipsY = style.overflowY === 'hidden' || style.overflowY === 'clip';
        if (clipsY && el.scrollHeight > el.clientHeight + 1 && (el.innerText || '').trim() !== '') {
            issues.push(`text cut off vertically: ${describe(el)} (content ${el.scrollHeight} px in ${el.clientHeight} px)`);
        }
    }

    // 2b. a plain button (dialog / scan step buttons) shows its whole caption: its text does not run over the button's edge
    for (const el of controls) {
        if (el.tagName === 'BUTTON' && !el.querySelector('.full-size-btn') && el.scrollWidth > el.clientWidth + 1) {
            issues.push(`button caption does not fit: ${describe(el)} (text ${el.scrollWidth} px in ${el.clientWidth} px)`);
        }
    }

    // 3. no two controls overlap
    for (let i = 0; i < controls.length; i++) {
        for (let j = i + 1; j < controls.length; j++) {
            const a = controls[i];
            const b = controls[j];
            if (a.contains(b) || b.contains(a)) continue;
            const ra = a.getBoundingClientRect();
            const rb = b.getBoundingClientRect();
            const overlapX = Math.min(ra.right, rb.right) - Math.max(ra.left, rb.left);
            const overlapY = Math.min(ra.bottom, rb.bottom) - Math.max(ra.top, rb.top);
            if (overlapX > 2 && overlapY > 2) issues.push(`controls overlap: ${describe(a)} and ${describe(b)}`);
        }
    }

    // 4. controls are big enough to tap
    for (const el of controls) {
        if (el.closest('.Toastify') || el.type === 'radio' || el.type === 'checkbox' || el.disabled) continue;
        const r = el.getBoundingClientRect();
        if (r.height < minTapSize || r.width < minTapSize) {
            issues.push(`too small to tap: ${describe(el)} (${Math.round(r.width)}x${Math.round(r.height)} px)`);
        }
    }

    // 5. a dialog over the screen: all its controls are on the screen, no scrolling needed
    if (isOverlay) {
        for (const el of controls) {
            const r = el.getBoundingClientRect();
            if (r.top < -0.5 || r.bottom > vh + 0.5) {
                issues.push(`dialog control off the screen: ${describe(el)} (top ${Math.round(r.top)}, bottom ${Math.round(r.bottom)}, screen ${vh})`);
            }
        }
    }

    // 6. error toasts: whole toast and its close button on the screen
    for (const toast of document.querySelectorAll('.Toastify__toast')) {
        const r = toast.getBoundingClientRect();
        if (r.width > 0 && (r.left < -0.5 || r.right > vw + 0.5 || r.top < -0.5 || r.bottom > vh + 0.5)) {
            issues.push(`toast off the screen: ${describe(toast)} (box ${Math.round(r.left)},${Math.round(r.top)} ${Math.round(r.right)},${Math.round(r.bottom)}, screen ${vw}x${vh})`);
        }
        if (toast.scrollHeight > toast.clientHeight + 1) issues.push(`toast text cut off: ${describe(toast)}`);
    }
    return issues;
};

/**
 * Asserts that the screen shown now (or, with `scope`, the dialog / area) is laid out soundly at this viewport.
 * @param {string} label what is shown, for the failure message
 * @param {{width: number, height: number}} viewport
 * @param {string} [scope] CSS selector; default: the dialog when one is open, else the whole app (`#root`)
 */
export const expectLayoutSound = async (label, viewport, scope) => {
    const effectiveScope = scope ?? ((await page.locator('.prompt-dialog').count()) > 0 ? '.prompt-dialog' : '#root');
    let issues = await page.evaluate(measureLayout, { scope: effectiveScope, minTapSize: MIN_TAP_SIZE });
    // a toast slides in: wait until it has settled before judging its position
    for (let attempt = 0; attempt < 8 && issues.some((issue) => issue.startsWith('toast off the screen')); attempt++) {
        await page.waitForTimeout(400);
        issues = await page.evaluate(measureLayout, { scope: effectiveScope, minTapSize: MIN_TAP_SIZE });
    }
    // one line per checked state, for the screen inventory: LAYOUT <size> | <label> | OK or the problems
    console.log(`LAYOUT ${viewport.width}x${viewport.height} | ${label} | ${issues.length === 0 ? 'OK' : issues.join(' ; ')}`);
    if (process.env.LAYOUT_SHOTS_DIR) {
        const dir = path.join(process.env.LAYOUT_SHOTS_DIR, `${viewport.width}x${viewport.height}`);
        fs.mkdirSync(dir, { recursive: true });
        await page.screenshot({ path: path.join(dir, `${label.replace(/[^a-zA-Z0-9]+/g, '-').replace(/^-|-$/g, '')}.png`) });
    }
    // soft: the walk-through goes on, so one run reports every broken state
    expect.soft(issues, `${label} at ${viewport.width}x${viewport.height}: layout problems`).toEqual([]);
};

/** Asserts that the control is reachable: after the screen's own scroll it lies on the screen and above the fixed footer. */
export const expectReachable = async (locator, description, viewport) => {
    // scroll the screen until the control is in the middle: it is then clear of the fixed footer (scrollIntoViewIfNeeded leaves a control that peeks out above the footer where it is)
    await locator.first().evaluate((el) => el.scrollIntoView({ block: 'center' }));
    const box = await locator.first().boundingBox();
    expect(box, `${description}: has a box`).not.toBeNull();
    const footer = await page.locator('.app-footer').boundingBox();
    const bottomLimit = Math.min(viewport.height, footer ? footer.y : viewport.height);
    const where = `${description}: box ${JSON.stringify(box)}, footer top ${footer?.y}, screen ${viewport.width}x${viewport.height}`;
    expect(box.x, `${where} (left edge)`).toBeGreaterThanOrEqual(0);
    expect(box.x + box.width, `${where} (right edge)`).toBeLessThanOrEqual(viewport.width + 0.5);
    expect(box.y, `${where} (top edge)`).toBeGreaterThanOrEqual(0);
    expect(box.y + box.height, `${where} (bottom edge)`).toBeLessThanOrEqual(bottomLimit + 0.5);
};

/** Asserts that the control is on the screen as it opens, without any scrolling: inside the screen and above the fixed footer. */
export const expectOnScreenWithoutScrolling = async (locator, description, viewport) => {
    await locator.first().waitFor({ state: 'visible' });
    const box = await locator.first().boundingBox();
    const footer = await page.locator('.app-footer').boundingBox();
    const bottomLimit = Math.min(viewport.height, footer ? footer.y : viewport.height);
    const where = `${description}: box ${JSON.stringify(box)}, footer top ${footer?.y}, screen ${viewport.width}x${viewport.height}`;
    expect(box.y, `${where} (top edge)`).toBeGreaterThanOrEqual(0);
    expect(box.y + box.height, `${where} (bottom edge: not visible without scrolling)`).toBeLessThanOrEqual(bottomLimit + 0.5);
    expect(box.x, `${where} (left edge)`).toBeGreaterThanOrEqual(0);
    expect(box.x + box.width, `${where} (right edge)`).toBeLessThanOrEqual(viewport.width + 0.5);
};

/**
 * The phone sizes the layout specs run at: the Pixel 5 preset of the config; the customer's handheld (Zebra TC22, 6.0 in, 1080x2160 physical
 * px; CSS size not measured: 360x720 @3 or 393x786 @2.75); worst case = a Chrome with address and tool bars shown (360x640 @3).
 * Add a size here to cover it in every layout spec.
 */
export const PHONE_VIEWPORTS = [
    { name: 'Pixel 5 393x727', width: 393, height: 727, deviceScaleFactor: 2.75 },
    { name: 'Zebra TC22 360x720', width: 360, height: 720, deviceScaleFactor: 3 },
    { name: 'Zebra TC22 393x786', width: 393, height: 786, deviceScaleFactor: 2.75 },
    { name: 'small phone, browser bars shown 360x640', width: 360, height: 640, deviceScaleFactor: 3 },
];

/** The session languages the layout specs run in (texts differ in length). */
export const LAYOUT_LANGUAGES = ['en_US', 'de_DE'];

/** Asserts that the element is completely inside the screen (its box lies within 0..width / 0..height). */
export const expectInsideScreen = async (locator, description, { width, height }) => {
    await locator.first().waitFor({ state: 'visible' });
    const box = await locator.first().boundingBox();
    expect(box, `${description}: has a box`).not.toBeNull();
    expect(box.x, `${description}: left edge, box ${JSON.stringify(box)}`).toBeGreaterThanOrEqual(0);
    expect(box.y, `${description}: top edge, box ${JSON.stringify(box)}`).toBeGreaterThanOrEqual(0);
    expect(box.x + box.width, `${description}: right edge, box ${JSON.stringify(box)}`).toBeLessThanOrEqual(width + 0.5);
    expect(box.y + box.height, `${description}: bottom edge, box ${JSON.stringify(box)}, screen ${width}x${height}`).toBeLessThanOrEqual(height + 0.5);
};

/** Saves a screenshot of the screen into the test's own output folder (never a fixed path of the machine). */
export const saveScreenshot = async (name) => {
    await page.screenshot({ path: test.info().outputPath(`${name.replace(/[^a-zA-Z0-9]+/g, '-')}.png`) });
};
