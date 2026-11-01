/**
 * Grid-cell geometry measured before, while and after editing a cell, and the comparison of two
 * measurements. A cell editor must not change the row height, any column width or the cell's
 * component box.
 */

/** the largest difference, in px, that still counts as unchanged */
export const TOLERANCE_PX = 1;

/** the desktop viewport the geometry specs measure in */
export const VIEWPORT = { width: 1920, height: 1080 };

/** the row height and the width of every cell (`<td>`) of the row */
export async function measureRow(row) {
  return await row.evaluate((tr) => ({
    rowHeight: tr.getBoundingClientRect().height,
    columns: Array.from(tr.querySelectorAll('td')).map((td) => ({
      cell: td.getAttribute('data-cy') || '(no data-cy)',
      width: td.getBoundingClientRect().width,
    })),
  }));
}

/**
 * The cell's component box: the visible static presentation (`.cell-text-wrapper`) in display mode,
 * the editor (its `.form-group` root) in edit mode. The invisible width keeper is not part of it.
 * Returns null when neither is rendered.
 */
export async function measureComponentBox(cell) {
  return await cell.evaluate((td) => {
    const editor = td.querySelector('.form-group');
    const component = editor || td.querySelector(':scope > div:not(.cell-width-keeper) .cell-text-wrapper');
    if (!component) {
      return null;
    }
    const box = component.getBoundingClientRect();
    return { kind: editor ? 'editor' : 'static', width: box.width, height: box.height };
  });
}

/** the violations of "the component box keeps its width and height", one message each */
export function compareComponentBox(phase, activatedCell, before, now) {
  if (!before || !now) {
    return [`${activatedCell} ${phase}: component box not measurable (${JSON.stringify({ before, now })})`];
  }
  const violations = [];
  ['width', 'height'].forEach((dimension) => {
    if (Math.abs(now[dimension] - before[dimension]) > TOLERANCE_PX) {
      violations.push(
        `${activatedCell} ${phase}: component ${dimension} ${before.kind} ${before[dimension]} -> ${now.kind} ${now[dimension]}`
      );
    }
  });
  return violations;
}

/** the violations of "the row keeps its height and every column keeps its width", one message each */
export function compareGeometry(phase, activatedCell, before, now) {
  const violations = [];
  if (Math.abs(now.rowHeight - before.rowHeight) > TOLERANCE_PX) {
    violations.push(`${activatedCell} ${phase}: row height ${before.rowHeight} -> ${now.rowHeight}`);
  }
  before.columns.forEach((column, i) => {
    const nowWidth = now.columns[i] ? now.columns[i].width : NaN;
    if (!(Math.abs(nowWidth - column.width) <= TOLERANCE_PX)) {
      violations.push(`${activatedCell} ${phase}: column ${column.cell} width ${column.width} -> ${nowWidth}`);
    }
  });
  return violations;
}

/**
 * The cell's displayed (static) value: its text and whether it is cut off. A value that does not fit
 * is cut off with `…` (`.cell-text-wrapper` has `text-overflow: ellipsis`): then `scrollWidth`
 * exceeds `clientWidth`.
 */
export async function measureStaticText(cell) {
  return await cell.evaluate((td) => {
    const wrapper = td.querySelector(':scope > div:not(.cell-width-keeper) .cell-text-wrapper');
    return wrapper
      ? { text: wrapper.textContent, clientWidth: wrapper.clientWidth, scrollWidth: wrapper.scrollWidth }
      : null;
  });
}
