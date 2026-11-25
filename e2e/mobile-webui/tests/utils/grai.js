/**
 * GRAI string helpers for the HU Consolidation "Scan (GRAI)" specs (pure functions, no screen access).
 */

/** GS1 mod-10 check digit of a digit string (weights 3,1,3,... from the right). */
const gs1CheckDigit = (digits) => {
    let sum = 0;
    for (let i = 0; i < digits.length; i++) {
        const digit = Number(digits[digits.length - 1 - i]);
        sum += digit * (i % 2 === 0 ? 3 : 1);
    }
    return (10 - (sum % 10)) % 10;
};

/** Canonical GRAI (companyPrefix.assetType.serial) of the PI's GRAI mapping, with the given serial. */
export const graiWithSerial = (piGrai, serial) => {
    const [companyPrefix, assetType] = piGrai.split('.');
    return `${companyPrefix}.${assetType}.${serial}`;
};

/** The same GRAI as GS1 AI 8003 barcode: 8003 + padding 0 + companyPrefix + assetType + check digit + serial. */
export const graiAs8003 = (canonicalGrai) => {
    const [companyPrefix, assetType, serial] = canonicalGrai.split('.');
    const assetReference = `0${companyPrefix}${assetType}`;
    return `8003${assetReference}${gs1CheckDigit(assetReference)}${serial}`;
};
