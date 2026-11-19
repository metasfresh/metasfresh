import {
  computeCUEntryToRemember,
  computeLastTypedQty,
  computeSuggestedQty,
  isLineDoneAfterBooking,
} from '../../utils/picking';

describe('computeSuggestedQty', () => {
  it('is null before the first typed value', () => {
    expect(computeSuggestedQty({ lastTypedQty: null, qtyOpen: 12 })).toBeNull();
    expect(computeSuggestedQty({ lastTypedQty: undefined, qtyOpen: 12 })).toBeNull();
    expect(computeSuggestedQty({ qtyOpen: 12 })).toBeNull();
  });

  it('is the last typed value while it fits into the open quantity', () => {
    expect(computeSuggestedQty({ lastTypedQty: 5, qtyOpen: 12 })).toBe(5);
    expect(computeSuggestedQty({ lastTypedQty: 5, qtyOpen: 5 })).toBe(5);
  });

  it('is capped by the open quantity', () => {
    expect(computeSuggestedQty({ lastTypedQty: 5, qtyOpen: 2 })).toBe(2);
  });

  it('is 0 when nothing is open any more', () => {
    expect(computeSuggestedQty({ lastTypedQty: 5, qtyOpen: 0 })).toBe(0);
  });

  it('is the last typed value when the open quantity is unknown', () => {
    expect(computeSuggestedQty({ lastTypedQty: 5, qtyOpen: null })).toBe(5);
    expect(computeSuggestedQty({ lastTypedQty: 5, qtyOpen: undefined })).toBe(5);
  });

  it('accepts a typed value that arrives as a string', () => {
    expect(computeSuggestedQty({ lastTypedQty: '5', qtyOpen: 12 })).toBe(5);
    expect(computeSuggestedQty({ lastTypedQty: '5', qtyOpen: 2 })).toBe(2);
  });
});

describe('computeLastTypedQty', () => {
  it('a typed count becomes the last typed value', () => {
    expect(computeLastTypedQty({ previousLastTypedQty: null, suggestedQty: null, qtyBooked: 5 })).toBe(5);
    expect(computeLastTypedQty({ previousLastTypedQty: 3, suggestedQty: 3, qtyBooked: '5' })).toBe(5);
  });

  it('confirming a capped suggestion keeps the last typed value (typed 5, suggestion 2 confirmed: stays 5)', () => {
    expect(computeLastTypedQty({ previousLastTypedQty: 5, suggestedQty: 2, qtyBooked: 2 })).toBe(5);
  });

  it('confirming an uncapped suggestion keeps it unchanged', () => {
    expect(computeLastTypedQty({ previousLastTypedQty: 5, suggestedQty: 5, qtyBooked: 5 })).toBe(5);
  });
});

describe('isLineDoneAfterBooking', () => {
  it('is false while quantity stays open', () => {
    expect(isLineDoneAfterBooking({ qtyBooked: 5, qtyOpen: 12 })).toBe(false);
    expect(isLineDoneAfterBooking({ qtyBooked: '5', qtyOpen: 12 })).toBe(false);
  });

  it('is true when the open quantity reaches 0 or is exceeded', () => {
    expect(isLineDoneAfterBooking({ qtyBooked: 12, qtyOpen: 12 })).toBe(true);
    expect(isLineDoneAfterBooking({ qtyBooked: '13', qtyOpen: 12 })).toBe(true);
  });

  it('is true when the open quantity is unknown (today: leave the screen)', () => {
    expect(isLineDoneAfterBooking({ qtyBooked: 5, qtyOpen: null })).toBe(true);
    expect(isLineDoneAfterBooking({ qtyBooked: 5, qtyOpen: undefined })).toBe(true);
  });
});

describe('computeCUEntryToRemember', () => {
  const booking = { cuLabel: 'RAW', lotNo: 'LOTA', bestBeforeDate: '2026-11-07' };

  it('remembers the typed count and the label of the booking', () => {
    expect(
      computeCUEntryToRemember({ previousLastTypedQty: null, suggestedQty: null, qtyBooked: '5', ...booking })
    ).toEqual({ lastTypedQty: 5, label: booking });
  });

  it('keeps the typed count when the capped suggestion was confirmed', () => {
    expect(
      computeCUEntryToRemember({ previousLastTypedQty: 5, suggestedQty: 2, qtyBooked: 2, ...booking }).lastTypedQty
    ).toBe(5);
  });
});
