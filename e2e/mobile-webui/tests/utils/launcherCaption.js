// Matchers for locating a job launcher button (`.wflauncher-button`) by one value of its caption.
//
// A launcher caption is a " | "-joined list of display fields (e.g. a manufacturing job:
// `<documentNo> | <product value> <product name> | <qty> <uom> | <date>`). Playwright's string `hasText`
// is a SUBSTRING match, so filtering by a bare documentNo also matches every other co-present launcher
// whose caption merely contains those characters - e.g. a neighbour's product named
// `BOM_20260910T163004976` contains `630049` - and the locator then resolves to two buttons (strict mode
// violation on tap). Pass these regexes to `hasText` instead, so the value must be a whole caption field.
// NOTE: Playwright (verified on 1.57) tests a `hasText` regex against the element's raw textContent, not the
// whitespace-normalized text. The button's only text is the caption, so `^`/`$` anchor at the caption start/end
// (no multiline flag needed); a "\n" only occurs inside a multi-line caption (e.g. an address block).

const escapeRegExp = (value) => String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&');

/**
 * Matches a caption whose FIRST field is exactly `value` (`<value> | ...`).
 * Use where the caption layout is fixed with the value in front, e.g. manufacturing job launchers.
 */
export const captionStartsWithField = (value) => new RegExp(`^\\s*${escapeRegExp(value)}\\s*\\|`);

/**
 * Matches a caption containing `value` as a whole field, i.e. delimited on both sides by the caption
 * start/end, the " | " field separator or a line break (multi-line captions, e.g. an address block).
 * Use where the field order is configurable, e.g. picking job launchers.
 */
export const captionHasField = (value) => new RegExp(`(?:^|[|\\n])[ \\t]*${escapeRegExp(value)}[ \\t]*(?=[|\\n]|$)`);
