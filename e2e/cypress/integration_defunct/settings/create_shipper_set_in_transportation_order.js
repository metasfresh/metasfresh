import { appendHumanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { Shipper } from '../../support/utils/shipper';
import { TransportationOrder } from '../../support/utils/transportationOrder';

describe('create new shipper and set it to transportation order', function() {
  let tourName;
  let shipperName;
  let shipperDescription;

  // test
  let shipperBPartner;

  it('Read the fixture', function() {
    cy.fixture('settings/create_shipper_set_in_transportation_order.json').then(f => {
      tourName = appendHumanReadableNow(f['tourName']);
      shipperName = appendHumanReadableNow(f['shipperName']);
      shipperDescription = appendHumanReadableNow(f['shipperDescription']);
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

  it('Create transportation order with tour and shipper', function() {
    cy.fixture('logistics/transportation_order.json').then(tour => {
      Object.assign(new TransportationOrder(), tour)
        .setShipper(shipperName)
        .setTour(tourName)
        .apply();
    });
  });
});
