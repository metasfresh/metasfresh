export class TourVersion {
  constructor() {
    this.tourVersionLines = [];
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

  addLine(tourVersionLine) {
    this.tourVersionLines.push(tourVersionLine);
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
    cy.selectInListField('M_Tour_ID', tourVersion.tour);
    cy.writeIntoStringField('ValidFrom', tourVersion.validFrom, false, null, true);

    cy.setCheckBoxValue('IsWeekly', true);
    cy.setCheckBoxValue('OnMonday', true);
    cy.writeIntoStringField('PreparationTime_1', '17:00', false, null, true);
    cy.setCheckBoxValue('OnTuesday', true);
    cy.writeIntoStringField('PreparationTime_2', '17:00', false, null, true);
    cy.setCheckBoxValue('OnWednesday', true);
    cy.writeIntoStringField('PreparationTime_3', '17:00', false, null, true);
    cy.setCheckBoxValue('OnThursday', true);
    cy.writeIntoStringField('PreparationTime_4', '17:00', false, null, true);
    cy.setCheckBoxValue('OnFriday', true);
    cy.writeIntoStringField('PreparationTime_5', '17:00', false, null, true);

    tourVersion.tourVersionLines.forEach(line => {
      applyLine(line);
    });
    cy.expectNumberOfRows(tourVersion.tourVersionLines.length);
  });
}

function applyLine(tourVersionLine) {
  cy.selectTab('M_TourVersionLine');
  cy.pressAddNewButton();
  cy.writeIntoLookupListField('C_BPartner_ID', tourVersionLine.bPartner, tourVersionLine.bPartner, false, true);
  if (tourVersionLine.bPartnerAddress) {
    cy.log('Sorry but typing in the address field is too fiddly. Nothing will be typed. Please see: https://github.com/metasfresh/metasfresh-e2e/issues/260');
    // cy.writeIntoLookupListField('C_BPartner_Location_ID', tourVersionLine.bPartnerAddress, tourVersionLine.bPartnerAddress, false, true);
  }
  cy.writeIntoStringField('BufferHours', tourVersionLine.buffer, true);
  cy.pressDoneButton();
}

export class TourVersionLine {
  setBpartner(bPartner) {
    cy.log(`TourVersionLine - set bPartner = ${bPartner}`);
    this.bPartner = bPartner;
    return this;
  }

  setBuffer(buffer) {
    cy.log(`TourVersionLine - set buffer = ${buffer}`);
    this.buffer = buffer;
    return this;
  }

  setBPartnerAddress(bPartnerAddress) {
    cy.log(`TourVersionLine - set bPartnerAddress = ${bPartnerAddress}`);
    this.bPartnerAddress = bPartnerAddress;
    return this;
  }
}
