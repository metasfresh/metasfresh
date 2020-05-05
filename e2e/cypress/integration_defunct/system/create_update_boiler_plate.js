import { BoilerPlate } from '../../support/utils/boiler_plate';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

describe('Create a new boiler plate, update it and create a new one', function() {
  let boilerPlateName1;
  let subject1;
  let textSnippet1;
  let changedText;

  let boilerPlateName2;
  let subject2;
  let textSnippet2;
  let jasperProcess;

  it('Read the fixture', function() {
    cy.fixture('system/create_update_boiler_plate.json').then(f => {
      boilerPlateName1 = appendHumanReadableNow(f['boilerPlateName1']);
      subject1 = appendHumanReadableNow(f['subject1']);
      textSnippet1 = f['textSnippet1'];
      boilerPlateName2 = appendHumanReadableNow(f['boilerPlateName2']);
      subject2 = appendHumanReadableNow(f['subject1']);
      textSnippet2 = f['textSnippet1'];
      jasperProcess = f['jasperProcess'];
      changedText = f['changedText'];
    });
  });
  it('Create new boiler plate1', function() {
    cy.fixture('system/boiler_plate.json').then(boilerPlateJson => {
      Object.assign(new BoilerPlate(), boilerPlateJson)
        .setName(boilerPlateName1)
        .setSubject(subject1)
        .setTextSnippet(textSnippet1)
        .apply();
    });
    cy.visitWindow('504410');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', boilerPlateName1, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(1);
    cy.selectNthRow(0).dblclick();
    cy.writeIntoTextField('TextSnippet', changedText, false, null, true);
  });

  it('Create new boiler plate2', function() {
    cy.fixture('system/boiler_plate.json').then(boilerPlateJson => {
      Object.assign(new BoilerPlate(), boilerPlateJson)
        .setName(boilerPlateName2)
        .setSubject(subject2)
        .setTextSnippet(textSnippet2)
        .apply();
    });
    cy.selectInListField('JasperProcess_ID', jasperProcess);
    cy.visitWindow('504410');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', boilerPlateName2, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(1);
  });
});
