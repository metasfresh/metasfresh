import { getLanguageSpecific } from '../../support/utils/utils';

export class ContractTransition {
  constructor({ name, ...vals }) {
    cy.log(`ContractTransition - set name = ${name};`);
    this.name = name;
    this.years = [];

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
  }

  setName(name) {
    cy.log(`ContractTransition - set name = ${name}`);
    this.name = name;
    return this;
  }

  setCalendar(calendar) {
    cy.log(`ContractTransition - set calendar = ${calendar}`);
    this.calendar = calendar;
    return this;
  }

  setNextConditions(nextConditions) {
    cy.log(`ContractTransition - set nextConditions = ${nextConditions}`);
    this.nextConditions = nextConditions;
    return this;
  }

  setExtensionType(extensionType) {
    cy.log(`ContractTransition - set extensionType = ${extensionType}`);
    this.extensionType = extensionType;
    return this;
  }

  addYear(year) {
    cy.log(`ContractTransition - add year = ${JSON.stringify(year)}`);
    this.years.push(year);
    return this;
  }

  apply() {
    cy.log(`ContractTransition - apply - START (name=${this.name})`);
    applyTransition(this);
    cy.log(`ContractTransition - apply - END (name=${this.name})`);
    return this;
  }
}

export class ExtensionType {
  static ExtendOne = 'extensionType_for_first_priod';

  static ExtendAll = 'extensionType_for_all_periods';
}

function applyTransition(transition) {
  cy.visitWindow('540120', 'NEW');

  cy.writeIntoStringField('Name', transition.name);

  cy.selectInListField('C_Calendar_Contract_ID', transition.calendar);

  cy.clearField('TermDuration');
  cy.writeIntoStringField('TermDuration', '1'); // note: there seems to be some bug somewhere, just '1' dos not work

  cy.selectInListField('TermDurationUnit', 'Jahr');

  cy.clearField('TermOfNotice');
  cy.writeIntoStringField('TermOfNotice', '1');
  cy.selectInListField('TermOfNoticeUnit', 'Monat');

  cy.clearField('DeliveryInterval');
  cy.writeIntoStringField('DeliveryInterval', '1');

  cy.selectInListField('DeliveryIntervalUnit', 'Monat');

  if (transition.extensionType) {
    cy.fixture('contract/contract_dictionary.json').then(contractDictionary => {
      const targetType = getLanguageSpecific(contractDictionary, transition.extensionType);
      cy.selectInListField('ExtensionType', targetType);
    });
  }
  if (transition.nextConditions) {
    cy.selectInListField('C_Flatrate_Conditions_Next_ID', transition.nextConditions);
  }

  cy.pressAddNewButton();

  cy.getStringFieldValue('DeadLine', true).then(deadLine => {
    // noinspection EqualityComparisonWithCoercionJS
    if (deadLine != 0) {
      cy.clearField('DeadLine');
      cy.writeIntoStringField('DeadLine', 0, true /*modal*/);
    }
  });
  cy.selectInListField('DeadLineUnit', 'Tag', true /*modal*/);
  cy.selectInListField('Action', 'Statuswechsel', true /*modal*/);
  cy.selectInListField('ContractStatus', 'Gekündigt', true /*modal*/);

  cy.pressDoneButton();
}
