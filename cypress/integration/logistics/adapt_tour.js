import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { TourVersion, TourVersionLine } from '../../support/utils/tour_version';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';

describe('Create Purchase order - material receipt - invoice', function() {
  const date = humanReadableNow();
  const customerName = `Customer ${date}`;
  const tourName = `TestTour ${date}`;
  const tourVersionName = `TestTourVersion ${date}`;

  it('Create customer for which we will create a tour', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customerName })
        .setCustomer(true)
        .addLocation(new BPartnerLocation('Address2').setCity('Cologne').setCountry('Deutschland'))
        .apply();
    });
  });
  it('Create a tour', function() {
    cy.fixture('logistics/tour.json').then(tourJson => {
      Object.assign(new Tour(), tourJson)
        .setName(tourName)
        .apply();
    });
  });
  it('Create a tour version and adapt it', function() {
    cy.fixture('logistics/tourversion.json').then(tourVersionJson => {
      Object.assign(new TourVersion(), tourVersionJson)
        .setName(tourVersionName)
        .setTour(tourName)
        .addLine(new TourVersionLine().setBpartner(customerName).setBuffer(0))
        .apply();
    });
    cy.writeIntoStringField('PreparationTime_5', '2:00 P', false, null, true);
    /**Add a new version line */
    cy.selectTab('M_TourVersionLine');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName, false, true, null, true);
    cy.pressDoneButton();
  });
});
