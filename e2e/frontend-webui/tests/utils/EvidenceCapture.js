import fs from 'fs';
import path from 'path';
import { expect } from '@playwright/test';
import { holdForCaptureIfEnabled } from './common';

const HIGHLIGHT_OUTLINE = '5px solid #b00020';

/**
 * Evidence stills of a spec run. A no-op unless EVIDENCE_DIR is set, so normal and CI runs are unchanged.
 *
 * A still is the whole window (run with UAT_CAPTURE=1 for 1920x1080), the page URL in a banner at the bottom (a
 * headless capture has no address bar), and the given elements outlined (outline only, no fill). The outline is
 * removed again afterwards and, in an evidence run, held on screen for a moment so that the recorded video shows it too.
 *
 * Other places keep their own inline variants of the same idea (`highlightForCaptureIfEnabled` in CostRevaluationPage.js,
 * `evidenceStill` in payment-bonus-header.spec.js, `snap` in compensationGroupCalibration.js). They differ in the outline
 * (3 px, other colours: not verified by outline-contrast-check.py), in the switch (UAT_CAPTURE, PAYMENT_BONUS_EVIDENCE_DIR)
 * and in what they save, and are not migrated here; new evidence stills should use this one.
 */
export class EvidenceCapture {
  /**
   * @param {import('@playwright/test').Page} page
   * @param {string} fileName - file inside EVIDENCE_DIR
   * @param {import('@playwright/test').Locator[]} highlighted - the elements the still proves; each is scrolled into view
   * @param {{inset?: boolean}} options - inset: draw the outline inside the element, for one whose surroundings clip an outline (a dropdown list)
   */
  static async still(page, fileName, highlighted = [], { inset = false } = {}) {
    const evidenceDir = process.env.EVIDENCE_DIR;
    if (!evidenceDir) {
      return;
    }
    fs.mkdirSync(evidenceDir, { recursive: true });

    const handles = [];
    for (const locator of highlighted) {
      await locator.first().scrollIntoViewIfNeeded();
      await expect(locator.first()).toBeVisible();
      handles.push(await locator.first().elementHandle());
    }
    for (const handle of handles) {
      await handle.evaluate(
        (el, { outline, offset }) => {
          el.dataset.prevOutline = el.style.outline;
          el.dataset.prevPosition = el.style.position;
          el.dataset.prevZIndex = el.style.zIndex;
          el.style.outline = outline;
          el.style.outlineOffset = offset;
          // a following sibling would paint over the outline; stacking order only, no change of look or content
          if (getComputedStyle(el).position === 'static') {
            el.style.position = 'relative';
          }
          el.style.zIndex = '1';
        },
        { outline: HIGHLIGHT_OUTLINE, offset: inset ? '-5px' : '3px' }
      );
    }
    await page.evaluate(() => {
      let banner = document.getElementById('__uatUrlBanner');
      if (!banner) {
        banner = document.createElement('div');
        banner.id = '__uatUrlBanner';
        // bottom + no pointer events: a banner on top would cover the header and swallow clicks
        banner.style.cssText =
          'position:fixed;bottom:0;left:0;right:0;z-index:99999;pointer-events:none;background:#222;color:#fff;font:12px monospace;padding:4px 8px';
        document.body.appendChild(banner);
      }
      banner.textContent = location.href;
    });

    await holdForCaptureIfEnabled(500);
    await page.screenshot({ path: path.join(evidenceDir, fileName), fullPage: false });

    for (const handle of handles) {
      await handle.evaluate((el) => {
        el.style.outline = el.dataset.prevOutline || '';
        el.style.outlineOffset = '';
        el.style.position = el.dataset.prevPosition || '';
        el.style.zIndex = el.dataset.prevZIndex || '';
      });
    }
  }
}
