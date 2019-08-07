import { humanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { Shipper } from '../../support/utils/shipper';
import { TransportationOrder } from '../../support/utils/transportationOrder';

describe('create new tour and transportation order', function() {
  const date = humanReadableNow();
  let shipperBPartner;
  const shipperLocation = `Am Nossbacher Weg 2`;
  const tourName = `TestTour_${date}`;
  const shipperName = `ShipperTest ${date}`;
  const shipperDescription = `ShipperDescriptionTest ${date}`;
  const documentNo = `X`;

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
