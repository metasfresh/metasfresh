import { computeGS1CheckDigit, randomDigits } from './gs1.js';

export const generateEAN13 = ({ prefix = '761', productCode: productCodeParam } = {}) => {

    const productCode = productCodeParam ? productCodeParam : randomDigits(12 - prefix.length);
    const codeWithoutCheckDigit = `${prefix}${productCode}`;
    const checkDigit = computeGS1CheckDigit(codeWithoutCheckDigit);
    const ean13Code = `${codeWithoutCheckDigit}${checkDigit}`;

    return {
        ean13: ean13Code,
        productCode: productCode
    };
}
