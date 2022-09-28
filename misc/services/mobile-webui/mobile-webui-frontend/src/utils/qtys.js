import { getLanguage } from './translations';

const MAX_maximumFractionDigits = 20; // ... to avoid "maximumFractionDigits value is out of range"

export const formatQtyToHumanReadable = ({ qty, uom, precision = null, tolerancePercent = null }) => {
  let qtyEffective = qty;
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
  if (maximumFractionDigits < MAX_maximumFractionDigits) {
    formatOptions.minimumFractionDigits = 0;
    formatOptions.maximumFractionDigits = maximumFractionDigits;
  }
  const qtyEffectiveStr = qtyEffective.toLocaleString(getLanguage(), formatOptions);

  let result = `${qtyEffectiveStr}${uomEffective ? uomEffective : ''}`;

  if (tolerancePercent != null && tolerancePercent !== 0) {
    result += ' ±' + tolerancePercent + '%';
  }

  // console.log('formatQtyToHumanReadable', {
  //   result,
  //   qty,
  //   uom,
  //   precision,
  //   tolerancePercent,
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
