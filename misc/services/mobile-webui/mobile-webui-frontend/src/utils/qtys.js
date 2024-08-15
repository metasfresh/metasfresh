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

  while (qtyEffective !== 0 && Math.abs(qtyEffective) < 1) {
    if (uomEffective === 'kg') {
      qtyEffective *= 1000;
      uomEffective = 'g';
    } else if (uomEffective === 'g') {
      qtyEffective *= 1000;
      uomEffective = 'mg';
    } else {
      break;
    }
  }

  const formatOptions = {
    useGrouping: false,
  };

  const maximumFractionDigits = Math.min(
    precision != null ? precision : getDefaultDisplayPrecision(uomEffective),
    MAX_maximumFractionDigits
  );

  qtyEffective = parseFloat(qtyEffective.toFixed(maximumFractionDigits));

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
