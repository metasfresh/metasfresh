/**
 * Exact-boundary match for a value as it is RENDERED on screen - i.e. the exact display string the
 * app's own formatting produces (`formatAmountToHumanReadableStr` / `formatQtyToHumanReadableStr`),
 * never a bare unformatted number. Pass the string exactly as displayed, not a raw amount
 * (`'5,00'`, not `5` - `formatAmountToHumanReadableStr` always renders a fixed number of decimals,
 * so `5` never matches `'5,00'`).
 *
 * IMPORTANT - the rendered string depends on the LOGIN LANGUAGE, because amounts/quantities are
 * formatted via `toLocaleString(getLanguage(), ...)` (`utils/money.js`, `utils/qtys.js`), and
 * `getLanguage()` reflects the language the test logged in with (`utils/translations.js`,
 * `hooks/useAuth.js`). The decimal separator changes accordingly:
 *   - `de_DE` login -> `'2,50'`, `'0,482 kg'` (comma decimal)
 *   - `en_US` login -> `'2.50'`, `'0.482 kg'` (dot decimal)
 * POS specs log in as `de_DE` (the customer locale) - see `pos_smoke.spec.js`'s `createMasterdata`.
 * Pass the string exactly as it renders for whichever language the spec's masterdata logs in with;
 * do not assume `de_DE` if the spec uses a different login language.
 *
 * A plain substring match on a bare digit would false-positive: `'5'` also matches inside `'15,00'`
 * or `'50,00'`. This anchors the value to non-digit/non-separator boundaries so it only matches the
 * whole rendered number.
 *
 * @param {string|number} value - the exact rendered text to look for, in the spec's login language.
 * @returns {RegExp}
 */
export const exactTextMatch = (value) =>
    new RegExp(`(^|[^0-9.,])${String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}([^0-9.,]|$)`);
