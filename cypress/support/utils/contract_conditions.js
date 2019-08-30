import { getLanguageSpecific } from '../../support/utils/utils';

export class ContractConditions {
  constructor({ name, ...vals }) {
    cy.log(`ContractConditions - set name = ${name};`);
    this.name = name;
    this.productAllocations = [];

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
  }

  setName(name) {
    cy.log(`ContractConditions - set name = ${name}`);
    this.name = name;
    return this;
  }

  setTransition(transition) {
    cy.log(`ContractConditions - set transition = ${transition}`);
    this.transition = transition;
    return this;
  }

  setConditionsType(conditionsType) {
    cy.log(`ContractConditions - set conditionsType = ${conditionsType}`);
    this.conditionsType = conditionsType;
    return this;
  }

  /** Required if conditionsType == QualityBased */
  setLagerKonferenz(lagerKonferenz) {
    cy.log(`ContractConditions - set lagerKonferenz = ${lagerKonferenz}`);
    this.lagerKonferenz = lagerKonferenz;
    return this;
  }

  addProductAllocation(productAllocation) {
    cy.log(`ContractConditions - add productAllocation = ${productAllocation}`);
    this.productAllocations.push(productAllocation);
    return this;
  }

  apply() {
    cy.log(`ContractConditions - apply - START (name=${this.name})`);
    applyConditions(this);
    cy.log(`ContractConditions - apply - END (name=${this.name})`);
    return this;
  }
}

export class ConditionsType {
  static Procurement = 'conditionsType_procuremnt';

  static Subscription = 'conditionsType_subscription';

  static QualityBased = 'conditionsType_quality_based';
}

export class ProductAllocation {
  setProductCategory(productcategory) {
    cy.log(`ProductAllocation - set productcategory = ${productcategory}`);
    this.productcategory = productcategory;
    return this;
  }

  setProduct(product) {
    cy.log(`ProductAllocation - set product = ${product}`);
    this.product = product;
    return this;
  }
}

function applyConditions(conditions) {
  cy.visitWindow('540113', 'NEW', 'newConditions');
  cy.writeIntoStringField('Name', conditions.name);

  cy.fixture('contract/contract_dictionary.json').then(contractDictionary => {
    cy.getStringFieldValue('Type_Conditions').then(currentType => {
      const targetType = getLanguageSpecific(contractDictionary, conditions.conditionsType);
      if (currentType !== targetType) {
        cy.selectInListField('Type_Conditions', targetType);
      }
    });
  });

  if (conditions.conditionsType === ConditionsType.QualityBased) {
    cy.selectInListField('M_QualityInsp_LagerKonf_ID', conditions.lagerKonferenz);
  }

  cy.selectInListField('OnFlatrateTermExtend', 'Co');
  cy.selectInListField('C_Flatrate_Transition_ID', conditions.transition);

  conditions.productAllocations.forEach(function(productAllocation) {
    applyProductAllocation(productAllocation);
  });
  cy.expectNumberOfRows(conditions.productAllocations.length);
}

function applyProductAllocation(productAllocation) {
  cy.selectTab('C_Flatrate_Matching');
  cy.pressAddNewButton();
  cy.selectInListField('M_Product_Category_Matching_ID', productAllocation.productcategory, true);
  if (productAllocation.product) {
    cy.selectInListField('M_Product_ID', productAllocation.product, true);
  }
  cy.pressDoneButton();
}
