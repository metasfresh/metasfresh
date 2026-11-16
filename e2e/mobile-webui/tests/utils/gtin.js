// Invented GTIN-14 test values (never a real article's GTIN).

let _gtinSeq = 0;

/**
 * A 14-digit GTIN that is unique per run: the label/GTIN lookup must find exactly one product, and the DB keeps the products of earlier runs.
 * Layout: 1 prefix digit + 2 sequence digits + 11 digits of the clock = 14 digits. Pass a different prefix for a different "family" of GTINs
 * within one test; '0' gives the customer-data shape of a stored GTIN (a leading 0 + a 13-digit EAN; the leading zero must survive).
 */
export const uniqueGtin14 = (prefix = '7') => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `${prefix}${seq}${ts}`;
};
