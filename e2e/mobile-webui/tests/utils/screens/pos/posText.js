/**
 * Matches `value` as the whole rendered display string, never as part of a larger number ('5' vs '15,00').
 * Pass the string as rendered for the spec's login language: '2,50' (de_DE), '2.50' (en_US).
 */
export const exactTextMatch = (value) =>
    new RegExp(`(^|[^0-9.,])${String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}([^0-9.,]|$)`);
