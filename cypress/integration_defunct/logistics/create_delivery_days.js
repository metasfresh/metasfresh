import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { TourVersion, TourVersionLine } from '../../support/utils/tour_version';

describe('Create a Tour, a Tour Version and Delivery Days', function() {
  let customerName;

  let tourName;
  let tourVersionName;

  // test
  let tourID;

  it('Read the fixture', function() {
    cy.fixture('logistics/create_delivery_days.json').then(f => {
      customerName = appendHumanReadableNow(f['customerName']);

      tourName = appendHumanReadableNow(f['tourName']);
      tourVersionName = appendHumanReadableNow(f['tourVersionName']);
    });
  });

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

    cy.getCurrentWindowRecordId().then(id => {
      tourID = id;
    });
  });

  it('Create a tour version', function() {
    cy.fixture('logistics/tourversion.json').then(tourVersionJson => {
      Object.assign(new TourVersion(), tourVersionJson)
        .setName(tourVersionName)
        .setTour(tourName)
        // eslint-disable-next-line
        .addLine(new TourVersionLine().setBpartner(customerName).setBuffer(0))
        .apply();
    });
  });

  it('Create delivery days for the tour', function() {
    cy.visitWindow(540331, tourID);
    cy.executeHeaderActionWithDialog('GenerateDeliveryDays');
    cy.writeIntoStringField('DateFrom', '07/01/2019', true, null, true);
    cy.writeIntoStringField('DateTo', '07/19/2019', true, null, true);
    cy.pressStartButton();
  });

  it('Open Referenced Delivery Days and expect 15 rows', function() {
    const expectedDeliveryDays = 15;
    cy.getNotificationModal(`Created #${expectedDeliveryDays}`);
    cy.openReferencedDocuments('540110');
    /**
     * the tour version is created weekly - each day from Monday to Friday; the delivery dates were generated
     * for the interval 1st of July - 19th of July - this means that there will be 15 delivery days in this interval
     * => we will check that the number of rows after filtering to be equal to 15;
     */
    cy.expectNumberOfRows(expectedDeliveryDays);
  });
});
