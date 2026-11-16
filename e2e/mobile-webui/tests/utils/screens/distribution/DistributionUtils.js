import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { Backend } from '../Backend';

export const DistributionUtils = {
    getJobIdFromPageUrl: async () => {
        const currentUrl = await page.url();

        const regex = /\/distribution-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    /**
     * Assert the screen is showing the given distribution job, reading the job from the URL.
     *
     * Polled, because the app reaches the next order WITHOUT a screen transition: the auto-advance
     * replaces the job in the URL while the same pick-from screen container stays mounted, so there
     * is nothing else for a caller to wait on — and when the order just picked needed no quantity
     * dialog, nothing in the flow blocks until the pick has even been posted. Reading the URL once
     * would then compare against the order the operator is still leaving.
     */
    expectJobId: async ({ distributionJobId }) => {
        await expect
            .poll(async () => await DistributionUtils.getJobIdFromPageUrl(), { timeout: SLOW_ACTION_TIMEOUT })
            .toEqual(distributionJobId);
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
        const lines = getJobLines({ wfProcess });

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

    /**
     * Assert the source HUs of the given distribution job's pre-allocated move plan are EXACTLY
     * `expectedHUQRCodes` (order-insensitive, one entry per step across all lines).
     *
     * The complement of expectPickAnyHUJobWithoutMovePlan below, and the check that makes the
     * auto-advance "different source HU" case (postDistributionPickFromThunk case 2) literal: it pins
     * BOTH that a plan exists at all — a job started with allowPickingAnyHU on has none, which is the
     * look-alike that lands the operator on the same Scan-HU prompt for a different reason — and that
     * the plan draws from this order's OWN HU, not from the one the operator just picked.
     */
    expectMovePlanSourceHUs: async ({ wfProcessId, expectedHUQRCodes }) => await test.step(`Backend: expect the move plan of wfProcess "${wfProcessId}" to draw from exactly ${JSON.stringify(expectedHUQRCodes)}`, async () => {
        const wfProcess = await Backend.getWFProcess({ wfProcessId });
        const lines = getJobLines({ wfProcess });
        const steps = lines.flatMap((line) => line.steps ?? []);

        expect(
            steps.map((step) => step.pickFromHU?.qrCode?.code).sort(),
            `the pre-allocated move plan of wfProcess "${wfProcessId}" was expected to draw from exactly the listed HUs:\n`
            + JSON.stringify(lines, null, 2)
        ).toEqual([...expectedHUQRCodes].sort());
    }),

    /**
     * Start recording every `nextEligiblePickFromLine` request the app fires from now on, and return
     * the recorder to read back later. The pick-from screen resolves an operator's scan through that
     * one endpoint, so its request bodies are where "what did the app send, and in which slot" is
     * answered — a scanned value can be legitimate as `productScannedCode` and wrong as `huQRCode`,
     * which no on-screen assertion can tell apart.
     *
     * Recording (rather than `page.waitForResponse` around one action) is what suits a NEGATIVE
     * assertion: there is no request to wait for, and the claim is about every request in a span.
     * Start it before the first operator action, so nothing can slip in ahead of the listener.
     *
     * The listener lives on the per-test `page` and dies with it, so it needs no teardown.
     */
    recordNextEligiblePickFromLineRequests: () => {
        const requestBodies = [];
        page.on('request', (request) => {
            if (request.method() !== 'POST' || !request.url().includes('/distribution/nextEligiblePickFromLine')) {
                return;
            }
            let body;
            try {
                body = request.postDataJSON();
            } catch {
                // Keep the raw payload rather than dropping the request: a body we cannot parse must
                // still show up in the assertion's failure output instead of silently reducing the
                // recording — and with it the assertion — to nothing.
                body = { unparsablePostData: request.postData() };
            }
            requestBodies.push(body);
        });
        return { requestBodies };
    },

    /**
     * Assert that none of the recorded `nextEligiblePickFromLine` requests sent `huQRCode` — i.e. the
     * app never offered that value to the backend as a handling-unit code. Pair it with a recorder
     * started before the actions it must cover (recordNextEligiblePickFromLineRequests above).
     */
    expectNoPickFromLineRequestCarriedHUQRCode: async ({ recorder, huQRCode }) => await test.step(`Expect no nextEligiblePickFromLine request carried "${huQRCode}" as huQRCode`, async () => {
        // Guard against a vacuous pass: this is a negative assertion over a recording, so a recorder
        // that captured nothing at all — a moved endpoint path, a listener started too late, a
        // scenario that never got as far as resolving a line — would satisfy it while proving
        // nothing. The scenarios this serves all book a pick, and a pick cannot be booked without at
        // least one nextEligiblePickFromLine round trip.
        expect(recorder.requestBodies.length, 'no nextEligiblePickFromLine request was recorded at all, so this assertion would prove nothing').toBeGreaterThan(0);

        const offending = recorder.requestBodies.filter((body) => body?.huQRCode === huQRCode);
        expect(
            offending,
            `"${huQRCode}" was sent to nextEligiblePickFromLine in the huQRCode slot. All recorded requests:\n`
            + JSON.stringify(recorder.requestBodies, null, 2)
        ).toEqual([]);
    }),

    /**
     * Assert the given distribution job is in "pick any HU" mode AND carries no pre-allocated move
     * plan: every line reports allowPickingAnyHU, and there is not a single step.
     *
     * A PRECONDITION check — it pins the state a scenario needs before it can mean anything, so the
     * scenario cannot silently degrade into covering a differently-configured job and still pass. It
     * says nothing about which path the app then took.
     */
    expectPickAnyHUJobWithoutMovePlan: async ({ wfProcessId }) => await test.step(`Backend: expect a pick-any-HU job with NO pre-allocated move plan (no steps) for wfProcess "${wfProcessId}"`, async () => {
        const wfProcess = await Backend.getWFProcess({ wfProcessId });
        const lines = getJobLines({ wfProcess });

        // Guard against a vacuous pass: if the response shape ever changes and the traversal finds no
        // lines at all, both assertions below would be trivially true and would prove nothing.
        expect(lines.length, `wfProcess "${wfProcessId}" has no distribution lines:\n` + JSON.stringify(wfProcess, null, 2)).toBeGreaterThan(0);

        expect(
            lines.map((line) => line.allowPickingAnyHU),
            `every line of wfProcess "${wfProcessId}" was expected to report allowPickingAnyHU=true:\n` + JSON.stringify(lines, null, 2)
        ).toEqual(lines.map(() => true));

        const steps = lines.flatMap((line) => line.steps ?? []);
        expect(steps, `wfProcess "${wfProcessId}" was expected to have NO pre-allocated steps`).toEqual([]);
    }),
};

// The distribution job's lines, read off the wfProcess JSON's move activity (the one activity that
// carries `componentProps.job.lines`).
const getJobLines = ({ wfProcess }) => {
    const moveActivity = wfProcess.activities?.find((activity) => activity.componentProps?.job?.lines != null);
    return moveActivity?.componentProps?.job?.lines ?? [];
};
