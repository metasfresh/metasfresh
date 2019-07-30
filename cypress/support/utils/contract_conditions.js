import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';
import { getLanguageSpecific } from '../../support/utils/utils';

export class ContractConditions {
  constructor({ baseName, ...vals }) {
    cy.log(`ContractConditions - set baseName = ${baseName};`);
    this.baseName = baseName;
    this.timestamp = new Date().getTime();
    this.productAllocations = [];

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
  }

  setBaseName(baseName) {
    cy.log(`ContractConditions - set baseName = ${baseName}`);
    this.baseName = baseName;
    return this;
  }

  setTimestamp(timestamp) {
    cy.log(`ContractConditions - set timestamp = ${timestamp}`);
    this.timestamp = timestamp;
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
  cy.writeIntoStringField('Name', `${conditions.baseName} ${conditions.timestamp}`);

  cy.fixture('contract/contract_dictionary.json').then(contractDictionary => {
    cy.getStringFieldValue('Type_Conditions').then(currentType => {
      const targetType = getLanguageSpecific(contractDictionary, conditions.conditionsType);
      if (currentType !== targetType) {
        cy.selectInListField('Type_Conditions', targetType);
      }
    });
  });

  if (conditions.conditionsType == ConditionsType.QualityBased) {
    cy.selectInListField('M_QualityInsp_LagerKonf_ID', conditions.lagerKonferenz);
  }

  cy.selectInListField('OnFlatrateTermExtend', 'Co');
  cy.selectInListField('C_Flatrate_Transition_ID', conditions.transition);

  // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
  if (conditions.productAllocations.length > 0) {
    cy.selectTab('C_Flatrate_Matching');

    conditions.productAllocations.forEach(function(productAllocation) {
      applyProductAllocation(productAllocation);
    });

    cy.get('table tbody tr').should('have.length', conditions.productAllocations.length);
  }

  cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
    cy.processDocument(
      getLanguageSpecific(miscDictionary, DocumentActionKey.Complete),
      getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed)
    );
  });
}

function applyProductAllocation(productAllocation) {
  cy.pressAddNewButton();

  cy.selectInListField('M_Product_Category_Matching_ID', productAllocation.productcategory, true /*modal*/);

  if (productAllocation.product) {
    cy.selectInListField('M_Product_ID', productAllocation.product, true /*modal*/);
  }
  cy.pressDoneButton();
}
