interface DateFormatting {
  lang: string;
  currentDay: Date;
  to?: string;
}

/**
 * @method formDate
 * @summary gets the object contianing the caption and dayFormat for the next day based on the lang parameter given
 * @param<Date> currentDay -  from where we calculate the next/prev one, current day
 * @param<string> to - string indicating we should return the next or previous day, calculated from the currentDay
 */
export function formDate({ lang, currentDay, to }: DateFormatting): { /*caption: string;*/ day: Date } {
  const day = new Date(currentDay);
  switch (to) {
    case 'prev':
      day.setDate(day.getDate() - 1);
      break;
    case 'next':
    default:
      day.setDate(day.getDate() + 1);
  }

  // let days;
  // switch (lang) {
  //   case 'de_CH':
  //   case 'de_DE':
  //     days = ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'];
  //     break;
  //   case 'en_US':
  //   case 'en_EN':
  //   default:
  //     days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  // }

  return { /*caption: days[day.getDay()],*/ day };
}

interface PrettyDate {
  lang: string;
  date: Date;
}
export function prettyDate({ lang, date }: PrettyDate): string {
  let dayFormat;
  const dd = (date.getDate() < 10 ? '0' : '') + date.getDate();
  const mm = (date.getMonth() + 1 < 10 ? '0' : '') + (date.getMonth() + 1);
  const yyyy = date.getFullYear();
  switch (lang) {
    case 'de_CH':
    case 'de_DE':
      dayFormat = `${dd}.${mm}.${yyyy}`;
      break;
    case 'en_US':
    case 'en_EN':
    default:
      dayFormat = `${dd}/${mm}/${yyyy}`;
  }
  return dayFormat;
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
