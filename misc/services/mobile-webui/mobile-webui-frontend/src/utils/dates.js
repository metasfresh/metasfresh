import { getLanguage } from './translations';

/**
 * @param isoDate a date without time, as the backend sends a `LocalDate`: `yyyy-MM-dd`
 * @returns the date in the user's language, e.g. `04.11.2026` for `de`; an empty string if there is no date
 */
export const formatDateToHumanReadableStr = (isoDate) => {
  if (!isoDate) {
    return '';
  }

  // A date without time is parsed as UTC midnight, so it has to be formatted in UTC, else it shifts by a day west of UTC.
  return new Date(isoDate).toLocaleDateString(getLanguage(), {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    timeZone: 'UTC',
  });
};
