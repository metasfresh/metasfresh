import { getLanguage } from './translations';

const MAX_maximumFractionDigits = 20; // ... to avoid "maximumFractionDigits value is out of range"

export const formatAmountToHumanReadableStr = ({ amount, currency, precision = null }) => {
  let amountEffective = amount ?? 0;

  const fractionDigits = Math.min(precision != null ? precision : 2, MAX_maximumFractionDigits);

  amountEffective = parseFloat(amountEffective.toFixed(fractionDigits));

  const formatOptions = {
    useGrouping: true,
  };
  if (fractionDigits < MAX_maximumFractionDigits) {
    formatOptions.minimumFractionDigits = fractionDigits;
    formatOptions.maximumFractionDigits = fractionDigits;
  }

  const amountEffectiveStr = amountEffective.toLocaleString(getLanguage(), formatOptions);

  return `${amountEffectiveStr}${currency ? ' ' + currency : ''}`;
};
