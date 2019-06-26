import { Tour } from '../../support/utils/tour';
import { TransportationOrder } from '../../support/utils/transportationOrder';

describe('create new tour and transportation order', function() {
  const timestamp = new Date().getTime();
  const testTour = `TestTour ${timestamp}`;

  it('create tour and transportation order', function() {
    cy.fixture('logistics/tour.json').then(tour => {
      Object.assign(new Tour(), tour)
        .setName(testTour)
        .apply();
    });

    cy.fixture('logistics/transportation_order.json').then(tour => {
      Object.assign(new TransportationOrder(), tour)
        .setTour(testTour)
        .apply();
    });
  });
});
