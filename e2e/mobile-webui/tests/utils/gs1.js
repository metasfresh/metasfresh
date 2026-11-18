// GS1 helpers shared by the test-data generators (EAN-13 and GTIN-14).

/** Random string of `length` decimal digits. */
export const randomDigits = (length) => {
    let digits = '';
    for (let i = 0; i < length; i++) {
        digits += Math.floor(Math.random() * 10);
    }
    return digits;
};

/** GS1 mod-10 check digit of `digits` (weights 3, 1, 3, ... counted from the right; any length). */
export const computeGS1CheckDigit = (digits) => {
    let sum = 0;
    for (let i = 0; i < digits.length; i++) {
        const digit = parseInt(digits[digits.length - 1 - i], 10);
        sum += digit * (i % 2 === 0 ? 3 : 1);
    }
    return (10 - (sum % 10)) % 10;
};
