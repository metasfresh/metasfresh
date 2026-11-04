// Invented product labels for the HU Manager "create from label" tests: the label SHAPES are those of real labels, the code VALUES are invented.
// A custom label format is a fixed-position definition (Backend.createMasterdata `customQRCodeFormats`); a label is the text a scan delivers.

/**
 * Main format of the tests: the bracketed shape of the real label pictures
 * "(01)<GTIN-14>(11)<yyMMdd production date>(10)<6-character lot>" (38 characters; no best-before date).
 * Label of the invented values: production date 15.03.2027 (en_US display '03/15/2027'), lot '481732'.
 */
export const FORMAT_BRACKETED_GTIN_PRODDATE_LOT = {
    name: 'my bracketed label (HU Manager)',
    parts: [
        { startPosition: 1, endPosition: 4, type: 'CONSTANT', constantValue: '(01)' },
        { startPosition: 5, endPosition: 18, type: 'GTIN' },
        { startPosition: 19, endPosition: 22, type: 'CONSTANT', constantValue: '(11)' },
        { startPosition: 23, endPosition: 28, type: 'PRODUCTION_DATE', dateFormat: 'yyMMdd' },
        { startPosition: 29, endPosition: 32, type: 'CONSTANT', constantValue: '(10)' },
        { startPosition: 33, endPosition: 38, type: 'LOT' },
    ],
};
export const BRACKETED_LABEL_LOT = '481732';
export const BRACKETED_LABEL_PRODUCTION_DATE_DISPLAY = '03/15/2027'; // en_US display of 2027-03-15
export const bracketedLabel = (gtin) => `(01)${gtin}(11)270315(10)${BRACKETED_LABEL_LOT}`;

/**
 * For the tests that need a best-before date (MHD): GTIN 1-14, LOT 15-19, BEST_BEFORE_DATE 20-25 (yyMMdd), no product-code part.
 * e.g. '02001234567893' + 'L2311' + '271231'. The product's stored GTIN is the 14-digit form (the customer-data shape: leading 0 + 13-digit EAN).
 */
export const FORMAT_GTIN_LOT_BBD = {
    name: 'my gtin label (HU Manager)',
    parts: [
        { startPosition: 1, endPosition: 14, type: 'GTIN' },
        { startPosition: 15, endPosition: 19, type: 'LOT' },
        { startPosition: 20, endPosition: 25, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
    ],
};
export const gtinLotBbdLabel = (gtin) => `${gtin}L2311271231`; // lot 'L2311', best-before 2027-12-31 (en_US '12/31/2027')

// GS1: (01) GTIN-14, (17) use-by yyMMdd, (10) lot — the shape of an INVENTED placeholder label (not a real customer label); needs no custom format
export const gs1Label = (gtin) => `01${gtin}1727123110L2311`; // lot 'L2311', use-by 2027-12-31 = best-before date (en_US '12/31/2027')
