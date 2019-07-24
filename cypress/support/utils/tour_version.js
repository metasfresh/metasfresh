export class TourVersion {
  constructor(name) {
    cy.log(`TourVersionBuilder - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`TourVersion - set name = ${name}`);
    this.name = name;
    return this;
  }

  setTour(tour) {
    cy.log(`TourVersion - set tour = ${tour}`);
    this.tour = tour;
    return this;
  }
  setValidFrom(validFrom) {
    cy.log(`TourVersion - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }
  setWeekly(isWeekely) {
    cy.log(`TourVersion - set isWeekely = ${isWeekely}`);
    this.isWeekely = isWeekely;
    return this;
  }
  setCustomer(customer) {
    cy.log(`TourVersion - set customer in tour version line = ${customer}`);
    this.customer = customer;
    return this;
  }
  apply() {
    cy.log(`TourVersion - apply - START (name=${this.name})`);
    applyTourVersion(this);
    cy.log(`TourVersion - apply - END (name=${this.name})`);
    return this;
  }
}
function applyTourVersion(tourVersion) {
  describe(`Create new TourVersion ${tourVersion.name}`, function() {
    cy.visitWindow(540333, 'NEW');
    cy.writeIntoStringField('Name', tourVersion.name);

    cy.selectInListField('M_Tour_ID', tourVersion.tour, false, null, true);
    cy.writeIntoStringField('ValidFrom', tourVersion.validFrom, false, null, true);
    cy.clickOnCheckBox('IsWeekly', true, false, null, true);

    cy.clickOnCheckBox('OnMonday', true, false, null, true);
    cy.writeIntoStringField('PreparationTime_1', '5:00 P', false, null, true);
    cy.clickOnCheckBox('OnTuesday', true, false, null, true);
    cy.writeIntoStringField('PreparationTime_2', '5:00 P', false, null, true);
    cy.clickOnCheckBox('OnWednesday', true, false, null, true);
    cy.writeIntoStringField('PreparationTime_3', '5:00 P', false, null, true);
    cy.clickOnCheckBox('OnThursday', true, false, null, true);
    cy.writeIntoStringField('PreparationTime_4', '5:00 P', false, null, true);
    cy.clickOnCheckBox('OnFriday', true, false, null, true);
    cy.writeIntoStringField('PreparationTime_5', '5:00 P', false, null, true);
    /**Add a new version line with a customer and a location */
    cy.selectTab('M_TourVersionLine');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('C_BPartner_ID', tourVersion.customer, tourVersion.customer, false, true, null, true);
    cy.selectInListField('C_BPartner_Location_ID', 'Address1', true, null, true, false);
    cy.pressDoneButton();
  });
}
