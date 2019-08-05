export class TransportationOrder {
  constructor(tour) {
    cy.log(`TransportationOrderBuilder - set tour = ${tour}`);
    this.tour = tour;
  }

  setTour(tour) {
    cy.log(`TransportationOrder - set tour = ${tour}`);
    this.tour = tour;
    return this;
  }
  setShipperBPartnerID(shipperBPartnerID) {
    cy.log(`TransportationOrder - set shipper bpartner = ${shipperBPartnerID}`);
    this.shipperBPartnerID = shipperBPartnerID;
    return this;
  }

  setShipperLocationID(shipperLocationID) {
    cy.log(`TransportationOrder - set shipper location = ${shipperLocationID}`);
    this.shipperLocationID = shipperLocationID;
    return this;
  }

  setShipper(shipper) {
    cy.log(`TransportationOrder - set shipper = ${shipper}`);
    this.shipper = shipper;
    return this;
  }

  setDocumentNo(documentNo) {
    cy.log(`TransportationOrder - set documentNo = ${documentNo}`);
    this.documentNo = documentNo;
    return this;
  }

  apply() {
    cy.log(`TransportationOrder - apply - START ${this.tour}`);
    applyTransportationOrder(this);
    cy.log(`TransportationOrder - apply - END ${this.tour}`);
    return this;
  }
}

function applyTransportationOrder(tOrder) {
  describe(`Create new TransportationOrder ${tOrder.tour}`, function() {
    cy.visitWindow(540020, 'NEW');
    cy.selectInListField('Shipper_BPartner_ID', tOrder.shipperBPartnerID);
    cy.selectInListField('Shipper_Location_ID', tOrder.shipperLocationID);
    cy.selectInListField('M_Shipper_ID', tOrder.shipper);
    cy.selectInListField('M_Tour_ID', tOrder.tour);
    cy.writeIntoStringField('DocumentNo', tOrder.documentNo);
  });
}
