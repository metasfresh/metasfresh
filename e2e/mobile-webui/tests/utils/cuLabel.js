// CU (pack) label builders for the CU quantity entry specs.
// Pure functions: no Playwright import, usable from node and from the specs.
//
// Label layout (test values only):
//   01 GTIN(14) | 91 product value (variable, GS) | 17 best-before yyMMdd | 10 lot (variable) | [GS 92 pack ID (variable)]
// Raw scanner form: AI digits, GS = \u001d, no "]d2" prefix.

import { computeGS1CheckDigit, randomDigits } from './gs1.js';

export const GS = '\u001d';

/** Product value length in the label. */
const PRODUCT_VALUE_LENGTH = 7;
/** Lot length in the label. */
const LOT_LENGTH = 9;

/**
 * Type code of the pack-ID scannable-code part; null while that part type is not available,
 * then `buildCULabelFormat({withPackId: true})` throws.
 */
export const PACK_ID_PART_TYPE = null;

const ALNUM = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

const randomChars = (length, alphabet) => {
    let result = '';
    for (let i = 0; i < length; i++) {
        result += alphabet[Math.floor(Math.random() * alphabet.length)];
    }
    return result;
};

/** GS1 GTIN-14 check digit over the first 13 digits. */
export const computeGTIN14CheckDigit = (first13Digits) => {
    if (!/^\d{13}$/.test(first13Digits)) {
        throw new Error(`computeGTIN14CheckDigit: need 13 digits, got '${first13Digits}'`);
    }
    return computeGS1CheckDigit(first13Digits);
};

/** Random 14-digit GTIN with a valid check digit (indicator digit 0). */
export const uniqueGTIN = () => {
    const first13 = '0' + randomDigits(12);
    return `${first13}${computeGTIN14CheckDigit(first13)}`;
};

/** Random product value of 7 characters. */
export const uniqueProductValue = () => `T${randomChars(PRODUCT_VALUE_LENGTH - 1, ALNUM)}`;

/** Random lot of 9 characters. */
export const uniqueLot = () => `L${randomChars(LOT_LENGTH - 1, ALNUM)}`;

/** 'YYYY-MM-DD' -> 'yyMMdd'. */
const toYYMMDD = (isoDate) => {
    const match = /^(\d{4})-(\d{2})-(\d{2})$/.exec(isoDate);
    if (!match) {
        throw new Error(`buildCULabel: bestBefore must be 'YYYY-MM-DD', got '${isoDate}'`);
    }
    return `${match[1].substring(2)}${match[2]}${match[3]}`;
};

/**
 * Builds the raw label of one CU.
 * @param {object} p
 * @param {string} p.gtin 14-digit GTIN
 * @param {string} p.productValue product value (7 characters)
 * @param {string} p.lot lot (9 characters)
 * @param {string} [p.bestBefore='2026-11-07'] best-before, 'YYYY-MM-DD'
 * @param {string|null} [p.packId=null] pack ID (counter); null = no pack-ID tail
 * @returns {string}
 */
export const buildCULabel = ({ gtin, productValue, lot, bestBefore = '2026-11-07', packId = null }) => {
    if (!/^\d{14}$/.test(gtin)) {
        throw new Error(`buildCULabel: gtin must be 14 digits, got '${gtin}'`);
    }
    let label = `01${gtin}91${productValue}${GS}17${toYYMMDD(bestBefore)}10${lot}`;
    if (packId !== null && packId !== undefined) {
        label += `${GS}92${packId}`;
    }
    return label;
};

/**
 * Builds a scannable-code format (the shape of `customQRCodeFormats` in the masterdata fixtures).
 *
 * Positions follow the label above: markers `01` 1-2, `91` 17-18, `17` 27-28, `10` 35-36,
 * data PRODUCT_CODE 19-25, BEST_BEFORE_DATE 29-34 (yyMMdd), LOT 37-45.
 * withPackId=true adds the `92` marker 47-48 and the pack-ID part 49 with no endPosition (open end).
 * withPackId=false keeps only the four markers, so a label without a counter still parses; the
 * counter tail of a longer label is ignored by that format.
 *
 * @param {object} p
 * @param {string} p.name unique format name (formats are DB-global in the shared E2E DB)
 * @param {boolean} [p.withPackId=false]
 * @returns {{name: string, parts: object[]}}
 */
export const buildCULabelFormat = ({ name, withPackId = false }) => {
    if (!name) {
        throw new Error('buildCULabelFormat: name is required');
    }
    if (withPackId && PACK_ID_PART_TYPE === null) {
        throw new Error('PackId type not merged: a pack-ID format needs the pack-ID part type merged (PACK_ID_PART_TYPE)');
    }

    const parts = [
        { startPosition: 1, endPosition: 2, type: 'CONSTANT', constantValue: '01' },
        { startPosition: 17, endPosition: 18, type: 'CONSTANT', constantValue: '91' },
        { startPosition: 19, endPosition: 25, type: 'PRODUCT_CODE' },
        { startPosition: 27, endPosition: 28, type: 'CONSTANT', constantValue: '17' },
        { startPosition: 29, endPosition: 34, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
        { startPosition: 35, endPosition: 36, type: 'CONSTANT', constantValue: '10' },
        { startPosition: 37, endPosition: 45, type: 'LOT' },
    ];

    if (withPackId) {
        parts.push({ startPosition: 47, endPosition: 48, type: 'CONSTANT', constantValue: '92' });
        parts.push({ startPosition: 49, type: PACK_ID_PART_TYPE });
    }

    return { name, parts };
};
