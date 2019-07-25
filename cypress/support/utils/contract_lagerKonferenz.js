import { getLanguageSpecific } from './utils';

export class LagerKonferenz {
  constructor({ baseName, ...vals }) {
    cy.log(`LagerKonferenz - set baseName = ${baseName};`);
    this.baseName = baseName;
    this.timestamp = new Date().getTime();
    this.settingsLines = [];

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
  }

  setBaseName(baseName) {
    cy.log(`LagerKonferenz - set baseName = ${baseName}`);
    this.baseName = baseName;
    return this;
  }

  setTimestamp(timestamp) {
    cy.log(`LagerKonferenz - set timestamp = ${timestamp}`);
    this.timestamp = timestamp;
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
  constructor() {}

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
}

function applyQualitySettings(qualitySettings) {
  cy.visitWindow('540230', 'NEW', 'newQualitySettings');
  cy.writeIntoStringField('Name', `${qualitySettings.baseName} ${qualitySettings.timestamp}`);

  // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
  if (qualitySettings.settingsLines.length > 0) {
    cy.selectTab('M_QualityInsp_LagerKonf_Version');
    qualitySettings.settingsLines.forEach(function(settingsLine) {
      applyLine(settingsLine);
    });

    cy.get('table tbody tr').should('have.length', qualitySettings.settingsLines.length);
  }
}

function applyLine(settingsLine) {
  cy.pressAddNewButton();
  cy.writeIntoStringField('ValidFrom', settingsLine.validFrom, true /*modal*/, undefined, true /*norequest */);
  cy.writeIntoStringField('ValidTo', settingsLine.validTo, true /*modal*/, undefined, true /*norequest */);

  cy.writeIntoLookupListField(
    'M_Product_Scrap_ID',
    settingsLine.scrapProduct,
    settingsLine.scrapProduct,
    false,
    true /*modal*/
  );
  cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
    cy.selectInListField('C_UOM_Scrap_ID', getLanguageSpecific(miscDictionary, settingsLine.scrapUOM), true /*modal*/);
  });

  cy.writeIntoLookupListField(
    'M_Product_ProcessingFee_ID',
    settingsLine.processingFeeProduct,
    settingsLine.processingFeeProduct,
    false,
    true /*modal*/
  );

  cy.writeIntoLookupListField(
    'M_Product_Witholding_ID',
    settingsLine.witholdingProduct,
    settingsLine.witholdingProduct,
    false,
    true /*modal*/
  );

  cy.writeIntoLookupListField(
    'M_Product_RegularPPOrder_ID',
    settingsLine.regularProductionProduct,
    settingsLine.regularProductionProduct,
    false,
    true /*modal*/
  );

  cy.pressDoneButton();
}
