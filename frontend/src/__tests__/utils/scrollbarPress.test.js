import { isMouseDownOnScrollbar } from '../../utils/scrollbarPress';

/**
 * Builds a fake mousedown event on a box at (left=100, top=50) with border 1px, 200x100 client box and the given
 * scrollbar sizes; `isScrollable` controls whether the content overflows.
 */
const eventAt = (
  clientX,
  clientY,
  { vBar = 15, hBar = 0, isScrollable = true } = {}
) => {
  const border = 1;
  const clientWidth = 200;
  const clientHeight = 100;
  const target = {
    getBoundingClientRect: () => ({
      left: 100,
      top: 50,
      width: clientWidth + vBar + 2 * border,
      height: clientHeight + hBar + 2 * border,
    }),
    clientLeft: border,
    clientTop: border,
    clientWidth,
    clientHeight,
    scrollHeight: isScrollable ? 400 : clientHeight,
    scrollWidth: isScrollable ? 400 : clientWidth,
  };
  return { clientX, clientY, target };
};

describe('isMouseDownOnScrollbar', () => {
  it('is true for a point on the vertical scrollbar', () => {
    // client box ends at x = 100 + 1 + 200 = 301; bar spans 301..316
    expect(isMouseDownOnScrollbar(eventAt(308, 100))).toBe(true);
  });

  it('is true for a point on the horizontal scrollbar', () => {
    // client box ends at y = 50 + 1 + 100 = 151; bar spans 151..166
    expect(
      isMouseDownOnScrollbar(eventAt(200, 158, { vBar: 0, hBar: 15 }))
    ).toBe(true);
  });

  it('is false for a point in the client area', () => {
    expect(isMouseDownOnScrollbar(eventAt(200, 100))).toBe(false);
  });

  it('is false for an overlay scrollbar (no space reserved)', () => {
    expect(isMouseDownOnScrollbar(eventAt(300, 100, { vBar: 0 }))).toBe(false);
  });

  it('is false for a point on the border of a non-scrollable box', () => {
    expect(
      isMouseDownOnScrollbar(eventAt(308, 100, { isScrollable: false }))
    ).toBe(false);
  });
});
