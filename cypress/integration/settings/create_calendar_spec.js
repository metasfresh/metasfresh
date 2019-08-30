import { Calendar, Year } from '../../support/utils/calendar';
import { humanReadableNow } from '../../support/utils/utils';

describe('Open calendar and create years', function() {
  const date = humanReadableNow();
  const calendarName = `TestCalendar_${date}`;

  // static
  const currentYear = new Date().getFullYear();

  it('Create calendar', function() {
    new Calendar()
      .setName(calendarName)
      .addYear(new Year(currentYear))
      .addYear(new Year(currentYear + 1))
      .addYear(new Year(currentYear + 2))
      .apply();
  });
});
