/**
 * Exact-boundary match for a value as it is RENDERED on screen - i.e. the exact display string the
 * app's own formatting produces (`formatAmountToHumanReadableStr` / `formatQtyToHumanReadableStr`),
 * such as `'5,00'` or `'0,482 kg'` - never a bare unformatted number. Pass the string exactly as
 * displayed, not a raw amount (`'5,00'`, not `5` - `formatAmountToHumanReadableStr` always renders a
 * fixed number of decimals, so `5` never matches `'5,00'`).
 *
 * A plain substring match on a bare digit would false-positive: `'5'` also matches inside `'15,00'`
 * or `'50,00'`. This anchors the value to non-digit/non-separator boundaries so it only matches the
 * whole rendered number.
 *
 * @param {string|number} value - the exact rendered text to look for.
 * @returns {RegExp}
 */
export const exactTextMatch = (value) =>
    new RegExp(`(^|[^0-9.,])${String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}([^0-9.,]|$)`);
