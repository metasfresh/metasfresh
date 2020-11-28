import { getLanguageSpecific } from './utils';

export class LagerKonferenz {
  constructor({ name, ...vals }) {
    cy.log(`LagerKonferenz - set name = ${name};`);
    this.name = name;
    this.settingsLines = [];

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
  }

  setName(name) {
    cy.log(`LagerKonferenz - set name = ${name}`);
    this.name = name;
    return this;
  }

  addSettingsLine(settingsLine) {
    cy.log(`LagerKonferenz - add settingsLine = ${JSON.stringify(settingsLine)}`);
    this.settingsLines.push(settingsLine);
    return this;
  }

  apply() {
    cy.log(`LagerKonferenz - apply - START (name=${this.name})`);
    applyQualitySettings(this);
    cy.log(`LagerKonferenz - apply - END (name=${this.name})`);
    return this;
  }
}

export class LagerKonferenzVersion {
  setValidFrom(validFrom) {
    cy.log(`LagerKonferenzVersion - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }

  setValidTo(validTo) {
    cy.log(`LagerKonferenzVersion - set validTo = ${validTo}`);
    this.validTo = validTo;
    return this;
  }

  setScrapProduct(scrapProduct) {
    cy.log(`LagerKonferenzVersion - set scrapProduct = ${scrapProduct}`);
    this.scrapProduct = scrapProduct;
    return this;
  }

  setScrapUOM(scrapUOM) {
    cy.log(`LagerKonferenzVersion - set scrapUOM = ${scrapUOM}`);
    this.scrapUOM = scrapUOM;
    return this;
  }

  setProcessingFeeProduct(processingFeeProduct) {
    cy.log(`LagerKonferenzVersion - set processingFeeProduct = ${processingFeeProduct}`);
    this.processingFeeProduct = processingFeeProduct;
    return this;
  }

  setWitholdingProduct(witholdingProduct) {
    cy.log(`LagerKonferenzVersion - set witholdingProduct = ${witholdingProduct}`);
    this.witholdingProduct = witholdingProduct;
    return this;
  }

  setRegularProductionProduct(regularProductionProduct) {
    cy.log(`LagerKonferenzVersion - set regularProductionProduct = ${regularProductionProduct}`);
    this.regularProductionProduct = regularProductionProduct;
    return this;
  }

  setScrapFeeAmt(scrapFeeAmount) {
    cy.log(`LagerKonferenzVersion - set scrap fee amount = ${scrapFeeAmount}`);
    this.scrapFeeAmount = scrapFeeAmount;
    return this;
  }

  setPercentageScrapTreshhold(treshhold) {
    cy.log(`LagerKonferenzVersion - set percentage scrap treshhold = ${treshhold}`);
    this.treshhold = treshhold;
    return this;
  }
}

function applyQualitySettings(qualitySettings) {
  cy.visitWindow('540230', 'NEW', 'newQualitySettings');
  cy.writeIntoStringField('Name', qualitySettings.name);

  // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties

  qualitySettings.settingsLines.forEach(function(settingsLine) {
    applyLine(settingsLine);
  });
  cy.expectNumberOfRows(qualitySettings.settingsLines.length);
}

function applyLine(settingsLine) {
  cy.selectTab('M_QualityInsp_LagerKonf_Version');
  cy.pressAddNewButton();
  cy.writeIntoStringField('ValidFrom', settingsLine.validFrom, true /*modal*/, undefined, true /*norequest */);
  cy.writeIntoStringField('ValidTo', settingsLine.validTo, true /*modal*/, undefined, true /*norequest */);

  cy.writeIntoLookupListField('M_Product_Scrap_ID', settingsLine.scrapProduct, settingsLine.scrapProduct, false, true /*modal*/);
  cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
    cy.selectInListField('C_UOM_Scrap_ID', getLanguageSpecific(miscDictionary, settingsLine.scrapUOM), true /*modal*/);
  });

  cy.writeIntoLookupListField('M_Product_ProcessingFee_ID', settingsLine.processingFeeProduct, settingsLine.processingFeeProduct, false, true /*modal*/);
  cy.writeIntoLookupListField('M_Product_Witholding_ID', settingsLine.witholdingProduct, settingsLine.witholdingProduct, false, true /*modal*/);
  cy.writeIntoLookupListField('M_Product_RegularPPOrder_ID', settingsLine.regularProductionProduct, settingsLine.regularProductionProduct, false, true /*modal*/);
  if (settingsLine.scrapFeeAmount) {
    cy.writeIntoStringField('Scrap_Fee_Amt_Per_UOM', settingsLine.scrapFeeAmount, true, null, true);
  }
  if (settingsLine.treshhold) {
    cy.writeIntoStringField('Percentage_Scrap_Treshhold', settingsLine.treshhold, true, null, true);
  }
  cy.pressDoneButton();
}
