import { getLanguage } from './translations';

const MAX_maximumFractionDigits = 20; // ... to avoid "maximumFractionDigits value is out of range"

export const formatQtyToHumanReadableStr = ({ qty, uom, precision = null, tolerance = null }) => {
    //console.log('formatQtyToHumanReadable', { qty, uom, precision, tolerancePercent });

    const { qtyEffectiveStr, uomEffective } = formatQtyToHumanReadable({ qty, uom, precision });

    let result = `${qtyEffectiveStr}${uomEffective ? ' ' + uomEffective : ''}`;

    if (tolerance != null && typeof tolerance === 'object') {
        if (tolerance.percentage != null) {
            result += ' ±' + tolerance.percentage + '%';
        } else if (tolerance.qty != null) {
            const toleranceQtyStr = formatQtyToHumanReadableStr({
                qty: tolerance.qty,
                uom: tolerance.uom,
            });
            result += ' ±' + toleranceQtyStr;
        }
    }

    // console.log('formatQtyToHumanReadable', {
    //   result,
    //   qty,
    //   uom,
    //   precision,
    //   tolerance,
    //   qtyEffective,
    //   uomEffective,
    //   maximumFractionDigits,
    //   formatOptions,
    // });

    return result;
};

const countDecimalPlaces = (num) => {
    if (num == null || !isFinite(num)) return 0;
    const str = String(num);
    const dotIndex = str.indexOf('.');
    return dotIndex < 0 ? 0 : str.length - dotIndex - 1;
};

const getDefaultDisplayPrecision = (uom, defaultPrecision = 4) => {
    if (uom === 'kg') {
        return 3;
    } else if (uom === 'g') {
        return 0;
    } else if (uom === 'mg') {
        return 0;
    } else {
        return defaultPrecision;
    }
};

export const formatQtyToHumanReadable = ({ qty, uom, precision = null }) => {
    let qtyEffective = qty ?? 0;
    let uomEffective = uom;
    let decimalShift = 0;

    while (qtyEffective !== 0 && Math.abs(qtyEffective) < 1) {
        if (uomEffective === 'kg') {
            qtyEffective *= 1000;
            uomEffective = 'g';
            decimalShift += 3;
        } else if (uomEffective === 'g') {
            qtyEffective *= 1000;
            uomEffective = 'mg';
            decimalShift += 3;
        } else {
            break;
        }
    }

    const formatOptions = {
        useGrouping: false,
    };

    let effectivePrecision;
    if (precision != null) {
        effectivePrecision = precision;
    } else {
        const qtyDecimalPlaces = countDecimalPlaces(qty);
        effectivePrecision = Math.max(qtyDecimalPlaces - decimalShift, getDefaultDisplayPrecision(uomEffective));
    }

    const maximumFractionDigits = Math.min(effectivePrecision, MAX_maximumFractionDigits);

    qtyEffective = parseFloat(Number(qtyEffective).toFixed(maximumFractionDigits));

    if (maximumFractionDigits < MAX_maximumFractionDigits) {
        formatOptions.minimumFractionDigits = 0;
        formatOptions.maximumFractionDigits = maximumFractionDigits;
    }
    const qtyEffectiveStr = qtyEffective.toLocaleString(getLanguage(), formatOptions);

    return {
        qtyEffectiveStr,
        qtyEffective,
        uomEffective,
    };
};
