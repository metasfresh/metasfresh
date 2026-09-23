/**
 * Matches `value` as the whole rendered display string, never as part of a larger number ('5' vs '15,00').
 * Contract: callers pass the de_DE-formatted string (e.g. '2,50') and must log in as de_DE.
 */
export const exactTextMatch = (value) =>
    new RegExp(`(^|[^0-9.,])${String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}([^0-9.,]|$)`);
