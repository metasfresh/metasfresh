import { Calendar, Year } from '../../support/utils/calendar';
import { humanReadableNow } from '../../support/utils/utils';

describe('Open calendar and create years', function() {
  const date = humanReadableNow();

  const currentYear = new Date().getFullYear();
  const calendarBaseName = `test-calendar`;

  it('Create calendar', function() {
    new Calendar(calendarBaseName)
      .setTimestamp(date)
      .addYear(new Year(`${currentYear}`))
      .addYear(new Year(`${currentYear + 1}`))
      .addYear(new Year(`${currentYear + 2}`))
      .apply();
  });
});
