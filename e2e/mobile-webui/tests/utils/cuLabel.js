// CU (pack) label builders for the CU quantity entry specs.
// Pure functions: no Playwright import, usable from node and from the specs.
//
// Label layout (test values only):
//   01 GTIN(14) | 91 product value(7) | 17 best-before yyMMdd(6) | 10 lot(9) | [92 pack ID (variable)]
// Raw scanner form as agreed with the customer: 47 characters with a pack ID of one digit, no separator characters (scanner setting
// "Send Control Characters as Events" OFF: the two GS characters of the printed code are dropped), no "]d2" prefix.

import { computeGS1CheckDigit, randomDigits } from './gs1.js';

/** A best-before date a year ahead of the run, 'YYYY-MM-DD': a fixed date would turn the specs red once it is past. */
export const defaultBestBefore = () => {
    const date = new Date();
    date.setFullYear(date.getFullYear() + 1);
    return date.toISOString().slice(0, 10);
};

/** Product value length in the label. */
const PRODUCT_VALUE_LENGTH = 7;
/** Lot length in the label. */
const LOT_LENGTH = 9;

/**
 * Type code of the pack-ID scannable-code part (`C_ScannableCode_Format_Part.DataType` PackId).
 */
export const PACK_ID_PART_TYPE = 'PACK_ID';

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
 * @param {string} [p.bestBefore=defaultBestBefore()] best-before, 'YYYY-MM-DD'
 * @param {string|null} [p.packId=null] pack ID (counter); null = no pack-ID tail
 * @returns {string}
 */
export const buildCULabel = ({ gtin, productValue, lot, bestBefore = defaultBestBefore(), packId = null }) => {
    if (!/^\d{14}$/.test(gtin)) {
        throw new Error(`buildCULabel: gtin must be 14 digits, got '${gtin}'`);
    }
    if (productValue.length !== PRODUCT_VALUE_LENGTH) {
        throw new Error(`buildCULabel: productValue must be ${PRODUCT_VALUE_LENGTH} characters (no separators: a wrong length shifts every later field), got '${productValue}'`);
    }
    if (lot.length !== LOT_LENGTH) {
        throw new Error(`buildCULabel: lot must be ${LOT_LENGTH} characters, got '${lot}'`);
    }
    let label = `01${gtin}91${productValue}17${toYYMMDD(bestBefore)}10${lot}`;
    if (packId !== null && packId !== undefined) {
        label += `92${packId}`;
    }
    return label;
};

/**
 * Builds a scannable-code format (the shape of `customQRCodeFormats` in the masterdata fixtures).
 *
 * Positions follow the 47-character label above: markers `01` 1-2, `91` 17-18, `17` 26-27, `10` 34-35,
 * data PRODUCT_CODE 19-25, BEST_BEFORE_DATE 28-33 (yyMMdd), LOT 36-44.
 * withPackId=true adds the `92` marker 45-46 and the pack-ID part 47 with no endPosition (open end).
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

    const parts = [
        { startPosition: 1, endPosition: 2, type: 'CONSTANT', constantValue: '01' },
        { startPosition: 17, endPosition: 18, type: 'CONSTANT', constantValue: '91' },
        { startPosition: 19, endPosition: 25, type: 'PRODUCT_CODE' },
        { startPosition: 26, endPosition: 27, type: 'CONSTANT', constantValue: '17' },
        { startPosition: 28, endPosition: 33, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
        { startPosition: 34, endPosition: 35, type: 'CONSTANT', constantValue: '10' },
        { startPosition: 36, endPosition: 44, type: 'LOT' },
    ];

    if (withPackId) {
        parts.push({ startPosition: 45, endPosition: 46, type: 'CONSTANT', constantValue: '92' });
        parts.push({ startPosition: 47, type: PACK_ID_PART_TYPE });
    }

    return { name, parts };
};
