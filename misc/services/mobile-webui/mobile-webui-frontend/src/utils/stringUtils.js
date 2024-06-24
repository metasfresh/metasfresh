// \u{10FFFF} makes sure empty | undefined | null come last
export const compareStringEmptyLast = (s1, s2) => (s1 || '\\u{10FFFF}').localeCompare(s2 || '\\u{10FFFF}');
