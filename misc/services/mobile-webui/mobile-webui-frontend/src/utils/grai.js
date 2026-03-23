/* global BigInt */
import GS1BarcodeParser from 'gs1-barcode-parser-mod';

/**
 * GRAI partition table: maps partition value to company prefix / asset type lengths.
 * See GS1 EPC Tag Data Standard, Table 14-3.
 */
const GRAI_PARTITION_TABLE = [
  { companyPrefixBits: 40, companyPrefixDigits: 12, assetTypeBits: 4, assetTypeDigits: 0 },
  { companyPrefixBits: 37, companyPrefixDigits: 11, assetTypeBits: 7, assetTypeDigits: 1 },
  { companyPrefixBits: 34, companyPrefixDigits: 10, assetTypeBits: 10, assetTypeDigits: 2 },
  { companyPrefixBits: 30, companyPrefixDigits: 9, assetTypeBits: 14, assetTypeDigits: 3 },
  { companyPrefixBits: 27, companyPrefixDigits: 8, assetTypeBits: 17, assetTypeDigits: 4 },
  { companyPrefixBits: 24, companyPrefixDigits: 7, assetTypeBits: 20, assetTypeDigits: 5 },
  { companyPrefixBits: 20, companyPrefixDigits: 6, assetTypeBits: 24, assetTypeDigits: 6 },
];

/**
 * Parse GRAI from an NFC NDEF message (GRAI-96 or GRAI-170 binary encoding).
 *
 * @param {NDEFMessage} ndefMessage - The NDEF message from the NFC reader
 * @returns {string|null} Dot-separated GRAI (e.g., "7613204.00307.1234567890") or null
 */
export const parseGraiFromNfc = (ndefMessage) => {
  if (!ndefMessage?.records) return null;

  for (const record of ndefMessage.records) {
    try {
      // Try to decode as text first
      if (record.recordType === 'text') {
        const decoder = new TextDecoder(record.encoding || 'utf-8');
        const text = decoder.decode(record.data);
        // If it's a text record containing a GRAI string, try to parse it
        const parsed = parseGraiFromRawInput(text);
        if (parsed) return parsed;
      }

      // Try to decode as binary EPC
      if (record.data) {
        const bytes = new Uint8Array(record.data.buffer || record.data);
        const parsed = parseGraiBinary(bytes);
        if (parsed) return parsed;
      }
    } catch (e) {
      console.warn('Failed to parse NFC record:', e);
    }
  }

  return null;
};

/**
 * Parse GRAI from binary EPC data (GRAI-96 or GRAI-170).
 *
 * @param {Uint8Array} bytes - Raw binary data
 * @returns {string|null} Dot-separated GRAI or null
 */
const parseGraiBinary = (bytes) => {
  if (!bytes || bytes.length < 12) return null;

  const header = bytes[0];

  if (header === 0x33) {
    // GRAI-96
    return parseGrai96(bytes);
  } else if (header === 0x37) {
    // GRAI-170
    return parseGrai170(bytes);
  }

  return null;
};

/**
 * Extract bits from a byte array.
 */
const extractBits = (bytes, bitOffset, numBits) => {
  let value = BigInt(0);
  for (let i = 0; i < numBits; i++) {
    const byteIndex = Math.floor((bitOffset + i) / 8);
    const bitIndex = 7 - ((bitOffset + i) % 8);
    if (byteIndex < bytes.length && (bytes[byteIndex] >> bitIndex) & 1) {
      value |= BigInt(1) << BigInt(numBits - 1 - i);
    }
  }
  return value;
};

/**
 * Parse GRAI-96 binary format.
 * Layout: header(8) | filter(3) | partition(3) | companyPrefix(var) | assetType(var) | serial(38)
 */
const parseGrai96 = (bytes) => {
  try {
    const partition = Number(extractBits(bytes, 11, 3));
    if (partition >= GRAI_PARTITION_TABLE.length) return null;

    const pt = GRAI_PARTITION_TABLE[partition];
    const companyPrefix = extractBits(bytes, 14, pt.companyPrefixBits);
    const assetType = extractBits(bytes, 14 + pt.companyPrefixBits, pt.assetTypeBits);
    const serial = extractBits(bytes, 58, 38);

    const companyPrefixStr = companyPrefix.toString().padStart(pt.companyPrefixDigits, '0');
    const assetTypeStr = assetType.toString().padStart(pt.assetTypeDigits, '0');
    const serialStr = serial.toString();

    return `${companyPrefixStr}.${assetTypeStr}.${serialStr}`;
  } catch (e) {
    console.warn('Failed to parse GRAI-96:', e);
    return null;
  }
};

/**
 * Parse GRAI-170 binary format.
 * Layout: header(8) | filter(3) | partition(3) | companyPrefix(var) | assetType(var) | serial(112, alphanumeric)
 */
const parseGrai170 = (bytes) => {
  try {
    const partition = Number(extractBits(bytes, 11, 3));
    if (partition >= GRAI_PARTITION_TABLE.length) return null;

    const pt = GRAI_PARTITION_TABLE[partition];
    const companyPrefix = extractBits(bytes, 14, pt.companyPrefixBits);
    const assetType = extractBits(bytes, 14 + pt.companyPrefixBits, pt.assetTypeBits);

    // Serial in GRAI-170 is 112 bits, encoded as 6-bit characters
    const serialBitOffset = 14 + pt.companyPrefixBits + pt.assetTypeBits;
    let serialStr = '';
    for (let i = 0; i < 16; i++) {
      const charVal = Number(extractBits(bytes, serialBitOffset + i * 7, 7));
      if (charVal === 0) break;
      serialStr += String.fromCharCode(charVal);
    }

    const companyPrefixStr = companyPrefix.toString().padStart(pt.companyPrefixDigits, '0');
    const assetTypeStr = assetType.toString().padStart(pt.assetTypeDigits, '0');

    return `${companyPrefixStr}.${assetTypeStr}.${serialStr.trim()}`;
  } catch (e) {
    console.warn('Failed to parse GRAI-170:', e);
    return null;
  }
};

/**
 * Parse GRAI from a GS1 barcode string containing AI 8003.
 *
 * AI 8003 format: 0{companyPrefix}{assetType}{checkDigit}{serial}
 * - Leading 0 (1 digit)
 * - Company prefix + asset type = 13 digits
 * - Check digit = 1 digit (position 14)
 * - Serial = variable length
 *
 * @param {string} barcodeString - Raw barcode string
 * @returns {string|null} Dot-separated GRAI or null
 */
export const parseGraiFromGs1Barcode = (barcodeString) => {
  try {
    const parsedBarcode = GS1BarcodeParser.parseBarcode(barcodeString);
    const graiElement = parsedBarcode.parsedCodeItems?.find((element) => element?.ai === '8003');

    if (!graiElement) return null;

    const graiData = '' + graiElement.data;
    // AI 8003 data: starts with 0, then 13 digits (company prefix + asset type), then check digit, then serial
    if (graiData.length < 15) return null;

    // The leading 0 is an indicator digit
    const base = graiData.substring(1, 14); // 13 digits: company prefix + asset type
    // Position 14 (index 14) is the check digit — skip it
    const serial = graiData.substring(15);

    // For GRAI, the company prefix is typically 7 digits (GS1 standard for most European prefixes)
    // We use a heuristic: GS1 prefixes starting with 76 (Switzerland) are 7 digits
    const companyPrefix = base.substring(0, 7);
    const assetType = base.substring(7);

    if (!serial) return null;

    return `${companyPrefix}.${assetType}.${serial}`;
  } catch (e) {
    // Not a valid GS1 barcode
    return null;
  }
};

/**
 * Try to parse a GRAI from raw input. First tries GS1 barcode parsing,
 * then checks if it's already in dot-separated format.
 *
 * @param {string} inputString - Raw input (barcode or manual entry)
 * @returns {string|null} Dot-separated GRAI or null
 */
export const parseGraiFromRawInput = (inputString) => {
  if (!inputString || typeof inputString !== 'string') return null;

  const trimmed = inputString.trim();

  // Try GS1 barcode first
  const gs1Result = parseGraiFromGs1Barcode(trimmed);
  if (gs1Result) return gs1Result;

  // Check if already in dot-separated format
  if (isValidGrai(trimmed)) return trimmed;

  return null;
};

/**
 * Validate a GRAI string in dot-separated format.
 * Expected format: {companyPrefix}.{assetType}.{serial}
 *
 * @param {string} graiString
 * @returns {boolean}
 */
export const isValidGrai = (graiString) => {
  if (!graiString || typeof graiString !== 'string') return false;

  const parts = graiString.split('.');
  if (parts.length !== 3) return false;

  const [companyPrefix, assetType, serial] = parts;
  return companyPrefix.length > 0 && assetType.length > 0 && serial.length > 0;
};
