export function getInitialDate(lang: string) {
  const today = new Date();
  const tomorrow = new Date(today);
  tomorrow.setDate(tomorrow.getDate() + 1);

  const dd = (tomorrow.getDate() < 10 ? '0' : '') + tomorrow.getDate();
  const mm = (tomorrow.getMonth() + 1 < 10 ? '0' : '') + (tomorrow.getMonth() + 1);
  const yyyy = tomorrow.getFullYear();

  let days;
  let dayFormat;
  switch (lang) {
    case 'de_DE':
      days = ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'];
      dayFormat = `${dd}.${mm}.${yyyy}`;
      break;
    case 'en_EN':
    default:
      days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
      dayFormat = `${dd}/${mm}/${yyyy}`;
  }

  return { caption: days[tomorrow.getDay()], dayFormat };
}
