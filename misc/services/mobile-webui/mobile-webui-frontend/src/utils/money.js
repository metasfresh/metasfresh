import { getLanguage } from './translations';

const MAX_maximumFractionDigits = 20; // ... to avoid "maximumFractionDigits value is out of range"

export const formatAmountToHumanReadableStr = ({ amount, currency, precision = null }) => {
  let amountEffective = amount ?? 0;

  const maximumFractionDigits = Math.min(precision != null ? precision : 2, MAX_maximumFractionDigits);

  amountEffective = parseFloat(amountEffective.toFixed(maximumFractionDigits));

  const formatOptions = {
    useGrouping: true,
  };
  if (maximumFractionDigits < MAX_maximumFractionDigits) {
    formatOptions.minimumFractionDigits = 0;
    formatOptions.maximumFractionDigits = maximumFractionDigits;
  }

  const amountEffectiveStr = amountEffective.toLocaleString(getLanguage(), formatOptions);

  return `${amountEffectiveStr}${currency ? ' ' + currency : ''}`;
};
