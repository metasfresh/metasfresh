import { test } from '../../../../playwright.config';
import { page } from '../../common';
import { expect } from '@playwright/test';
import { Backend } from '../Backend';

export const DistributionUtils = {
    getJobIdFromPageUrl: async () => {
        const currentUrl = await page.url();

        const regex = /\/distribution-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    expectJobId: async ({ distributionJobId }) => {
        await expect(await DistributionUtils.getJobIdFromPageUrl()).toEqual(distributionJobId);
    },

    // Assert a row of the shared `.view-header` table (the job/line header rendered on the
    // distribution job, line and pick-from screens — same DOM everywhere). The caption is matched
    // EXACTLY (`th:text-is`) so a substring caption can't hit the wrong row. `exact` controls the
    // value: true matches the cell text exactly (`td:text-is`), false matches a substring
    // (`td:has-text`) — use false when the value carries trailing content you don't want to pin
    // (e.g. a qty followed by its UOM symbol). The row must exist exactly once AND be visible
    // (painted), not merely present in the DOM.
    expectHeaderProperty: async ({ caption, value, exact }) => {
        const valueSelector = exact ? `td:text-is("${value}")` : `td:has-text("${value}")`;
        const row = page.locator(`tr:has(th:text-is("${caption}")):has(${valueSelector})`);
        await expect(row).toHaveCount(1);
        await expect(row).toBeVisible();
    },

    /**
     * Resolves the QR code (global QR code JSON string) of the HU that a distribution `pickFrom`
     * event picked, for the LAST recorded step of the given line. Used to assert the backend
     * end-state of a distribution pick when the picked HU has no masterdata identifier — a partial
     * pick off a shared staging LU creates a NEW split HU only at pick time, so it can only be
     * referenced via the QR code the job step reports (`Backend.expect`'s HU matcher resolves a raw
     * QR-code-JSON string as a fallback identifier).
     *
     * A distribution job can have several lines (one per DD Order line), so `lineId` is REQUIRED
     * whenever the job has more than one line; it may be omitted only for a genuinely single-line
     * job (this throws rather than silently guessing the wrong line's steps otherwise).
     */
    getPickedHUQRCode: async ({ wfProcessId, lineId }) => await test.step(`Backend: get distribution picked HU QR code for wfProcess "${wfProcessId}"${lineId != null ? ` line "${lineId}"` : ''}`, async () => {
        const wfProcess = await Backend.getWFProcess({ wfProcessId });
        const moveActivity = wfProcess.activities?.find((activity) => activity.componentProps?.job?.lines != null);
        const lines = moveActivity?.componentProps?.job?.lines ?? [];

        let line;
        if (lineId != null) {
            line = lines.find((candidate) => String(candidate.lineId) === String(lineId));
            if (!line) {
                throw new Error(
                    `wfProcess "${wfProcessId}" has no distribution line "${lineId}"; available lineIds:\n` +
                    JSON.stringify(lines.map((l) => l.lineId), null, 2)
                );
            }
        } else if (lines.length === 1) {
            line = lines[0];
        } else {
            throw new Error(
                `wfProcess "${wfProcessId}" has ${lines.length} distribution lines; pass "lineId" to disambiguate:\n` +
                JSON.stringify(lines.map((l) => l.lineId), null, 2)
            );
        }

        const steps = line?.steps ?? [];
        const lastStep = steps[steps.length - 1];
        const qrCode = lastStep?.pickFromHU?.qrCode?.code;
        if (!qrCode) {
            throw new Error(`No picked HU found for wfProcess "${wfProcessId}"${lineId != null ? ` line "${lineId}"` : ''}:\n` + JSON.stringify(wfProcess, null, 2));
        }
        return qrCode;
    }),
};