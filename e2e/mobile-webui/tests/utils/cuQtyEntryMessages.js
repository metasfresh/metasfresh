/**
 * Texts of the refusals and messages of the CU quantity entry that specs assert on, in the session language.
 * Each entry holds the part of the text that is asserted (`toContain`); `de` missing = the German text is not asserted.
 * Keep in step with `translations_en.js` / `translations_de.js` of the mobile frontend and the backend messages.
 */
export const MESSAGES = {
    noOpenTU: { en: "Please open a TU first (scan a GRAI or 'Next TU').", de: 'Bitte öffnen Sie zuerst eine Kiste' },
    noFormat: { en: 'No label format set on the picking profile', de: 'Kein Etikettenformat im Kommissionierprofil' },
    differentProduct: { en: 'The scanned QR Product does not match', de: 'Das gescannte QR Produkt stimmt nicht mit dem' },
    formatMismatch: { en: 'does not match the label format', de: 'Etikettenformat' },
    alreadyPicked: { en: 'This CU was already picked in this job', de: 'Diese Packung wurde' },
    graiCount: { en: 'TUs have a GRAI' },
    eachOnlyPieces: { en: "'Scan each CU' works only for lines counted in pieces", de: 'funktioniert nur für Zeilen, die in Stück geführt werden' },
    overPick: { en: 'Cannot pick more than the ordered quantity for this line.' },
    pickFromRequired: { en: "Please scan 'Pick From' first." },
    noCapacity: { en: 'has no capacity configured for this product' },
    nextTUBusy: { en: 'A TU is just being opened.' },
};

/** Text of a message in the session language (`en_US` or `de_DE`) */
export const txt = (lang, key) => {
    const { en, de } = MESSAGES[key];
    return lang === 'en_US' ? en : de ?? '';
};
