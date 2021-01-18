interface DateFormatting {
  currentDay: Date;
  to?: string;
}

/**
 * @method formDate
 * @summary gets the Date object - either current or in the future/past
 * @param<Date> currentDay -  from where we calculate the next/prev one, current day
 * @param<string> to - string indicating we should return the next or previous day, calculated from the currentDay
 */
export function formDate({ currentDay, to }: DateFormatting): Date {
  const day = new Date(currentDay);
  let newDate;
  switch (to) {
    case 'prev':
      newDate = new Date(day.setDate(day.getDate() - 1));
      break;
    case 'next':
      newDate = new Date(day.setDate(day.getDate() + 1));
      break;
    default:
      newDate = day;
  }

  return newDate;
}

interface PrettyDate {
  lang: string;
  date: Date;
}
export function prettyDate({ lang, date }: PrettyDate): string {
  const day = (date.getDate() < 10 ? '0' : '') + date.getDate();
  const month = (date.getMonth() + 1 < 10 ? '0' : '') + (date.getMonth() + 1);
  const year = date.getFullYear();

  switch (lang) {
    case 'de_CH':
    case 'de_DE':
      return `${day}.${month}.${year}`;
    case 'en_US':
    case 'en_EN':
    default:
      return `${day}/${month}/${year}`;
  }
}

/**
 * @method slashSeparatedYYYYmmdd
 * @summary converts date to the standard allowed by the backend YYYY-MM-dd for the api calls
 * @param date
 */
export function slashSeparatedYYYYmmdd(date: Date): string {
  const dd = (date.getDate() < 10 ? '0' : '') + date.getDate();
  const MM = (date.getMonth() + 1 < 10 ? '0' : '') + (date.getMonth() + 1);
  const YYYY = date.getFullYear();

  return `${YYYY}-${MM}-${dd}`;
}
