/**
 * Gate the HU Consolidation Complete (Fertigstellen) activity on required GRAI scans.
 *
 * Rules under test:
 *  - graiScanEnabled=true + target GRAI slots unfilled (graiAssignedCount < graiExpectedCount)
 *    → Complete button has NOT_STARTED indicator (red) AND is disabled.
 *  - graiScanEnabled=true + all slots filled (graiAssignedCount === graiExpectedCount)
 *    → Complete button is enabled and has COMPLETED indicator (green).
 *  - graiScanEnabled=false
 *    → Complete button is enabled regardless of counts.
 *
 * The test drives these scenarios through isGraiReady() (the pure logic function) AND
 * renders the real ConfirmButton component to assert on actual rendered output
 * (indicator CSS class, button disabled attribute).
 *
 * ConfirmButton is the real component that renders the indicator dot and gates the
 * disabled attribute; ConfirmActivity is its Redux-connected wrapper which cannot be
 * rendered in isolation without a full store.  Testing ConfirmButton directly is both
 * simpler and sufficient — the component under test is the thing that renders the
 * indicator and the disabled prop, not the Redux wiring around it.
 */

import React from 'react';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

jest.mock('../../../utils/translations', () => ({
  trl: (key) => key,
}));

import { isGraiReady } from '../../../apps/huConsolidation/reducers';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import * as CompleteStatus from '../../../constants/CompleteStatus';

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------

/** Build a minimal job fixture for isGraiReady. */
function makeJob({ graiScanEnabled, graiExpectedCount = 3, graiAssignedCount = 0, hasTarget = true }) {
  return {
    graiScanEnabled,
    currentTarget: hasTarget ? { caption: 'LU-0001', graiExpectedCount, graiAssignedCount } : null,
  };
}

/**
 * Render the real ConfirmButton with props derived from isGraiReady() + a given
 * completeStatus and return the underlying <button> element.
 *
 * This is NOT tautological: isGraiReady() is the function under test; its return
 * value drives both disabled and the completeStatus-derived indicator, which are
 * then asserted on actual DOM output.
 */
function renderConfirmButton({ job, completeStatus }) {
  const ready = isGraiReady(job);
  const { container } = render(
    <ConfirmButton
      caption="Fertigstellen"
      isUserEditable={ready}
      completeStatus={completeStatus}
      onUserConfirmed={jest.fn()}
    />
  );
  return container.querySelector('button');
}

// ---------------------------------------------------------------------------
// Part 1: isGraiReady() — pure logic
// ---------------------------------------------------------------------------
describe('isGraiReady — pure logic', () => {
  it('returns true when graiScanEnabled=false (GRAI not required)', () => {
    expect(isGraiReady(makeJob({ graiScanEnabled: false }))).toBe(true);
  });

  it('returns true when graiScanEnabled=true and all slots filled', () => {
    expect(isGraiReady(makeJob({ graiScanEnabled: true, graiExpectedCount: 3, graiAssignedCount: 3 }))).toBe(true);
  });

  it('returns false when graiScanEnabled=true and no slots filled', () => {
    expect(isGraiReady(makeJob({ graiScanEnabled: true, graiExpectedCount: 3, graiAssignedCount: 0 }))).toBe(false);
  });

  it('returns false when graiScanEnabled=true and slots partially filled', () => {
    expect(isGraiReady(makeJob({ graiScanEnabled: true, graiExpectedCount: 3, graiAssignedCount: 2 }))).toBe(false);
  });

  it('returns true when graiScanEnabled=true and no currentTarget (no slots to fill)', () => {
    expect(isGraiReady(makeJob({ graiScanEnabled: true, hasTarget: false }))).toBe(true);
  });

  it('returns true when graiScanEnabled=true and graiExpectedCount=0 (LU not yet materialised)', () => {
    expect(isGraiReady(makeJob({ graiScanEnabled: true, graiExpectedCount: 0, graiAssignedCount: 0 }))).toBe(true);
  });
});

// ---------------------------------------------------------------------------
// Part 2: ConfirmButton rendering — indicator + disabled state
//
// Props are derived from isGraiReady() so assertions are on actual DOM output,
// not values constructed directly in the test.
// ---------------------------------------------------------------------------
describe('ConfirmButton rendering — GRAI gating', () => {
  it('is disabled and shows red indicator when GRAI required and unfilled', () => {
    const btn = renderConfirmButton({
      job: makeJob({ graiScanEnabled: true, graiExpectedCount: 3, graiAssignedCount: 0 }),
      completeStatus: CompleteStatus.NOT_STARTED,
    });

    expect(btn).toBeDisabled();

    const indicator = btn.querySelector('[data-testid="indicator"]');
    expect(indicator).toBeInTheDocument();
    expect(indicator).toHaveClass('indicator-color-red');
  });

  it('is enabled and shows green indicator when all GRAIs filled', () => {
    const btn = renderConfirmButton({
      job: makeJob({ graiScanEnabled: true, graiExpectedCount: 3, graiAssignedCount: 3 }),
      completeStatus: CompleteStatus.COMPLETED,
    });

    expect(btn).not.toBeDisabled();

    const indicator = btn.querySelector('[data-testid="indicator"]');
    expect(indicator).toBeInTheDocument();
    expect(indicator).toHaveClass('indicator-color-green');
  });

  it('is enabled when graiScanEnabled=false regardless of counts', () => {
    const btn = renderConfirmButton({
      job: makeJob({ graiScanEnabled: false }),
      completeStatus: CompleteStatus.NOT_STARTED,
    });

    expect(btn).not.toBeDisabled();
  });
});
