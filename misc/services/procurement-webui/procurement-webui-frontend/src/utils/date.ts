interface DateFormatting {
  lang: string;
  currentDay: Date;
  to: string;
}

/**
 * @method formDate
 * @summary gets the object contianing the caption and dayFormat for the next day based on the lang parameter given
 * @param<string> lang - language format - i.e: en_US
 * @param<Date> currentDay -  from where we calculate the next/prev one, current day
 * @param<string> to - string indicating we should return the next or previous day, calculated from the currentDay
 */
export function formDate({ lang, currentDay, to }: DateFormatting): { caption: string; dayFormat: string } {
  const tomorrow = new Date(currentDay);
  switch (to) {
    case 'prev':
      tomorrow.setDate(tomorrow.getDate() - 1);
      break;
    case 'next':
    default:
      tomorrow.setDate(tomorrow.getDate() + 1);
  }

  const dd = (tomorrow.getDate() < 10 ? '0' : '') + tomorrow.getDate();
  const mm = (tomorrow.getMonth() + 1 < 10 ? '0' : '') + (tomorrow.getMonth() + 1);
  const yyyy = tomorrow.getFullYear();

  let days;
  let dayFormat;
  switch (lang) {
    case 'de_CH':
    case 'de_DE':
      days = ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'];
      dayFormat = `${dd}.${mm}.${yyyy}`;
      break;
    case 'en_US':
    case 'en_EN':
    default:
      days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
      dayFormat = `${dd}/${mm}/${yyyy}`;
  }

  return { caption: days[tomorrow.getDay()], dayFormat };
}
