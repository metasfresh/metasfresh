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

export const roundToQtyPrecision = (
  qty = { qty: undefined, uom: undefined },
  roundToQty = { qty: undefined, uom: undefined }
) => {
  if (!qty.qty || !qty.uom || !roundToQty.qty || !roundToQty.uom) {
    return qty;
  }

  const roundToQtyConverted = convertToQtyUOM(roundToQty, qty.uom);
  if (!roundToQtyConverted) {
    return qty;
  }

  const decimalAdjustment = Math.pow(
    10,
    Math.max(getNumberOfDecimalsOrZero(roundToQtyConverted.qty), getNumberOfDecimalsOrZero(qty.qty), 0)
  );
  const adjustedQty = qty.qty * decimalAdjustment;
  const adjustedRoundToQty = roundToQtyConverted.qty * decimalAdjustment;

  return ((Math.round(adjustedQty / adjustedRoundToQty) * adjustedRoundToQty) / decimalAdjustment).toFixed(
    getNumberOfDecimalsOrZero(qty.qty)
  );
};

const convertToQtyUOM = (qtyToConvert = { qty: undefined, uom: undefined }, targetUom) => {
  if (!targetUom) {
    return qtyToConvert;
  }

  if (!qtyToConvert || !qtyToConvert.uom || !qtyToConvert.qty) {
    return undefined;
  }

  const convertedValue = qtyToConvert.qty * getConversionRate(qtyToConvert.uom, targetUom);
  return { qty: convertedValue, uom: targetUom };
};

const getConversionRate = (source, target) => {
  switch (source) {
    case 'mg':
      return getConversionRateFromMg(target);
    case 'GRM':
    case 'g':
      return getConversionRateFromGram(target);
    case 'kg':
      return getConversionRateFromKg(target);
    default:
      throw new Error('No ConversionRate available!');
  }
};

const getConversionRateFromMg = (targetUOM) => {
  switch (targetUOM) {
    case 'mg':
      return 1;
    case 'GRM':
    case 'g':
      return 1 / 1000;
    case 'kg':
      return 1 / (1000 * 1000);
    default:
      throw new Error('No ConversionRate available!');
  }
};

const getConversionRateFromGram = (targetUOM) => {
  switch (targetUOM) {
    case 'mg':
      return 1000;
    case 'GRM':
    case 'g':
      return 1;
    case 'kg':
      return 1 / 1000;
    default:
      throw new Error('No ConversionRate available!');
  }
};

const getConversionRateFromKg = (targetUOM) => {
  switch (targetUOM) {
    case 'mg':
      return 1000 * 1000;
    case 'GRM':
    case 'g':
      return 1000;
    case 'kg':
      return 1;
    default:
      throw new Error('No ConversionRate available!');
  }
};

const getNumberOfDecimalsOrZero = (numberArg) => {
  if (!numberArg) {
    return 0;
  }

  const numberParts = numberArg.toString().split('.');

  if (numberParts.length === 1) {
    return 0;
  } else if (numberParts.length === 2) {
    return numberParts[1].length;
  } else {
    return 0;
  }
};
