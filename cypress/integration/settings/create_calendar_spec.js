import { Calendar, Year } from '../../support/utils/calendar';

describe('Open calendar and create years', function() {
  const timestamp = new Date().getTime();

  const currentYear = new Date().getFullYear();
  const calendarBaseName = `test-calendar`;

  it('Create calendar', function() {
    new Calendar(calendarBaseName)
      .setTimestamp(timestamp)
      .addYear(new Year(`${currentYear}`))
      .addYear(new Year(`${currentYear + 1}`))
      .addYear(new Year(`${currentYear + 2}`))
      .apply();
  });
});
