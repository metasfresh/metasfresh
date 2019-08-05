import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';
import { Tour } from '../../support/utils/tour';
import { TransportationOrder } from '../../support/utils/transportationOrder';

describe('create new tour and transportation order', function() {
  const date = humanReadableNow();
  const shipperBPartner = `metasfresh AG`;
  const shipperLocation = `Am Nossbacher Weg 2`;
  const tourName = `TestTour_${date}`;
  const shipper = `Eigentransport`;
  const documentNo = `X`;

  it('Create a tour', function() {
    cy.fixture('logistics/tour.json').then(tourJson => {
      Object.assign(new Tour(), tourJson)
        .setName(tourName)
        .apply();
    });
  });

  it('create tour and transportation order', function() {
    cy.fixture('logistics/transportation_order.json').then(tour => {
      Object.assign(new TransportationOrder(), tour)
        .setShipperBPartnerID(shipperBPartner)
        .setShipperLocationID(shipperLocation)
        .setShipper(shipper)
        .setTour(tourName)
        .setDocumentNo(documentNo)
        .apply();
    });
  });
});
