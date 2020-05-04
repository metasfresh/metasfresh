import { appendHumanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { Shipper } from '../../support/utils/shipper';
import { TransportationOrder } from '../../support/utils/transportationOrder';

describe('create new tour and transportation order', function() {
  let shipperBPartner;
  let shipperLocation;
  let tourName;
  let shipperName;
  let shipperDescription;
  let documentNo;

  it('Read the fixture', function() {
    cy.fixture('logistics/create_new_tour_and_transportation_order_spec.json').then(f => {
      shipperLocation = f['shipperLocation'];
      tourName = appendHumanReadableNow(f['tourName']);
      shipperName = appendHumanReadableNow(f['shipperName']);
      shipperDescription = appendHumanReadableNow(f['shipperDescription']);
      documentNo = f['documentNo'];
    });
  });

  it('Create a tour', function() {
    cy.fixture('logistics/tour.json').then(tourJson => {
      Object.assign(new Tour(), tourJson)
        .setName(tourName)
        .apply();
    });
    cy.getStringFieldValue('AD_Org_ID').then(organisation => {
      shipperBPartner = organisation;
    });
  });

  it('Create a shipper', function() {
    cy.fixture('settings/shipper.json').then(shipperJson => {
      Object.assign(new Shipper(), shipperJson)
        .setName(shipperName)
        .setBPartner(shipperBPartner)
        .setDescription(shipperDescription)
        .apply();
    });
  });

  it('create tour and transportation order', function() {
    cy.fixture('logistics/transportation_order.json').then(tour => {
      Object.assign(new TransportationOrder(), tour)
        .setShipperBPartnerID(shipperBPartner)
        .setShipperLocationID(shipperLocation)
        .setShipper(shipperName)
        .setTour(tourName)
        .setDocumentNo(documentNo)
        .apply();
    });
  });
});
