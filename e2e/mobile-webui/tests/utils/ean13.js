export const generateEAN13 = ({ prefix = '761', productCode: productCodeParam } = {}) => {

    const productCode = productCodeParam ? productCodeParam : generateRandomDigits(12 - prefix.length);
    const codeWithoutCheckDigit = `${prefix}${productCode}`;
    const checkDigit = computeCheckDigit(codeWithoutCheckDigit);
    const ean13Code = `${codeWithoutCheckDigit}${checkDigit}`;

    return {
        ean13: ean13Code,
        productCode: productCode
    };
}

const computeCheckDigit = (digits) => {
    let sum = 0;
    for (let i = 0; i < digits.length; i++) {
        sum += parseInt(digits[i]) * (i % 2 === 0 ? 1 : 3);
    }
    return (10 - (sum % 10)) % 10;
};

const generateRandomDigits = (length) => {
    let digits = '';
    for (let i = 0; i < length; i++) {
        digits += Math.floor(Math.random() * 10);
    }
    return digits;
};
