import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { TourVersion } from '../../support/utils/tour_version';

describe('Create a tour and a tour version', function() {
  const date = humanReadableNow();
  const customerName = `Customer ${date}`;
  const tourName = `TestTour ${date}`;
  const tourVersionName = `TestTourVersion ${date}`;

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
});
