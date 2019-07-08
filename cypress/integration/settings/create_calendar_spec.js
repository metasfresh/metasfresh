import { Calendar, Year } from '../../support/utils/calendar';

describe('Open calendar and create years', function() {
  const timestamp = new Date().getTime();
  //const timestamp = 'latest timestamp'; // useful to repeat parts of the spec while developing

  const currentYear = new Date().getFullYear();
  const calendarName = `calendar ${timestamp}`;

  it('Create calendar', function() {
    //   cy.visitWindow('117', 'NEW');
    //   cy.writeIntoStringField('Name', calendarName);

    //   addFiscalYear(currentYear, timestamp);
    //   addFiscalYear(currentYear + 1, timestamp);
    //   addFiscalYear(currentYear + 2, timestamp);
    new Calendar(calendarName)
      .addYear(new Year(`${currentYear} ${timestamp}`))
      .addYear(new Year(`${currentYear + 1} ${timestamp}`))
      .addYear(new Year(`${currentYear + 2} ${timestamp}`))
      .apply();
  });
});

// function addFiscalYear(yearName, timestamp) {
//   cy.selectTab('C_Year');
//   cy.pressAddNewButton();
//   cy.writeIntoStringField('FiscalYear', `${yearName} ${timestamp}`, true /*modal*/);
//   cy.pressDoneButton();
// }
