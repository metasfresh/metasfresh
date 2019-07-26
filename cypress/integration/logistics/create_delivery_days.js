import { BPartner } from '../../support/utils/bpartner';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';
import { humanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { TourVersion } from '../../support/utils/tour_version';

describe('Create a tour and a tourversion', function() {
  const date = humanReadableNow();
  const customerName = `Customer ${date}`;
  const tourName = `TestTour ${date}`;
  const tourVersionName = `TestTourVersion ${date}`;
  // let mainTourId;

  it('Create customer for which we will create a tour', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customerName }).setCustomer(true).apply();
    });
  });
  it('Create a tour', function() {
    cy.fixture('logistics/tour.json').then(tourJson => {
      Object.assign(new Tour(), tourJson)
        .setName(tourName)
        .apply();
    });
    // cy.get(`@${tourName}`).then(mainTour => {
    //   mainTourId = mainTour.documentId;
    // });
  });
  it('Create a tour version', function() {
    cy.fixture('logistics/tourversion.json').then(tourVersionJson => {
      Object.assign(new TourVersion(), tourVersionJson)
        .setName(tourVersionName)
        .setTour(tourName)
        .setCustomer(customerName)
        .apply();
    });
  });
  it('Create delivery days for this tour', function() {
    cy.visitWindow(540331);
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', tourName, false, null, true);
    applyFilters();
    cy.get('tbody tr')
      .eq(0)
      .dblclick();
    /**Create delivery days for this tour */
    cy.executeHeaderActionWithDialog('GenerateDeliveryDays');
    cy.writeIntoStringField('DateFrom', '07/01/2019', true, null, true);
    cy.writeIntoStringField('DateTo', '07/19/2019', true, null, true);
    cy.pressStartButton();
    cy.waitUntilProcessIsFinished();
    cy.visitWindow(540110);
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_TourVersion_ID', tourVersionName, tourVersionName, false, false, null, true);
    cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName, false, false, null, true);
    applyFilters();
    /**the tour version is created weekly - each day from Monday to Friday; the delivery dates were generated
     * for the interval 1st of July - 19th of July - this means that there will be 15 delivery days in this interval
     * => we will check that the number of rows after filtering to be equal to 15;
     */
    cy.get('tbody tr').should(el => {
      expect(el.length).to.be.equal(15);
    });
  });
});