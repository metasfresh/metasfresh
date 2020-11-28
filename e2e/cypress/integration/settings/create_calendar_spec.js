import { Calendar, Year } from '../../support/utils/calendar';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Open calendar and create years', function() {
  let calendarName;
  let year1;
  let year2;
  let year3;

  it('Read the fixture', function() {
    cy.fixture('settings/create_calendar_spec.json').then(f => {
      calendarName = appendHumanReadableNow(f['calendarName']);
      year1 = f['year1'];
      year2 = f['year2'];
      year3 = f['year3'];
    });
  });

  it('Create calendar', function() {
    new Calendar()
      .setName(calendarName)
      .addYear(new Year(year1))
      .addYear(new Year(year2))
      .addYear(new Year(year3))
      .apply();
  });
});
