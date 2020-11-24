export class StorageConferenceVersion {
  constructor() {
    this.lines = [];
    this.months = [];
    this.contributions = [];
  }

  setLagerKonferenz(lagerKonferenz) {
    cy.log(`StorageConferenceVersionBuilder - set LagerKonferenz = ${lagerKonferenz}`);
    this.lagerKonferenz = lagerKonferenz;
    return this;
  }

  setProductProcessingFee(productProcessingFee) {
    cy.log(`StorageConferenceVersionBuilder - set productProcessingFee = ${productProcessingFee}`);
    this.productProcessingFee = productProcessingFee;
    return this;
  }

  setProductWitholding(productWitholding) {
    cy.log(`StorageConferenceVersionBuilder - set productWitholding = ${productWitholding}`);
    this.productWitholding = productWitholding;
    return this;
  }

  setProductRegularPPOrder(productRegularPPOrder) {
    cy.log(`StorageConferenceVersionBuilder - set productRegularPPOrder = ${productRegularPPOrder}`);
    this.productRegularPPOrder = productRegularPPOrder;
    return this;
  }

  setProductScrap(productScrap) {
    cy.log(`StorageConferenceVersionBuilder - set productScrap = ${productScrap}`);
    this.productScrap = productScrap;
    return this;
  }

  setUOMScrap(uomScrap) {
    cy.log(`StorageConferenceVersionBuilder - set uomScrap = ${uomScrap}`);
    this.uomScrap = uomScrap;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`StorageConferenceVersionBuilder - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }

  setValidTo(validTo) {
    cy.log(`StorageConferenceVersionBuilder - set validTo = ${validTo}`);
    this.validTo = validTo;
    return this;
  }

  setPercentageScrapTreshhold(percentageScrapT) {
    cy.log(`StorageConferenceVersionBuilder - set percentageScrapT = ${percentageScrapT}`);
    this.percentageScrapT = percentageScrapT;
    return this;
  }

  setScrapFeeAmt(scrapFeeAmt) {
    cy.log(`StorageConferenceVersionBuilder - set scrapFeeAmt = ${scrapFeeAmt}`);
    this.scrapFeeAmt = scrapFeeAmt;
    return this;
  }

  addLine(costLine) {
    cy.log(`StorageConferenceVersion - add Costs lines = ${JSON.stringify(costLine)}`);
    this.lines.push(costLine);
    return this;
  }

  addMonth(month) {
    cy.log(`StorageConferenceVersion - add Months = ${JSON.stringify(month)}`);
    this.months.push(month);
    return this;
  }

  addContribution(contribution) {
    cy.log(`StorageConferenceVersion - add contribution = ${JSON.stringify(contribution)}`);
    this.contributions.push(contribution);
    return this;
  }

  apply() {
    cy.log(`StorageConferenceVersion - apply - START`);
    applyStorageConferenceVersion(this);
    cy.log(`StorageConferenceVersion - apply - END`);
    return this;
  }
}

function applyStorageConferenceVersion(storageConferenceVersion) {
  describe(`Create new StorageConferenceVersion ${storageConferenceVersion.lagerKonferenz}`, function() {
    cy.visitWindow(540364, 'NEW');
    cy.writeIntoLookupListField('M_QualityInsp_LagerKonf_ID', storageConferenceVersion.lagerKonferenz, storageConferenceVersion.lagerKonferenz);
    cy.writeIntoLookupListField('M_Product_ProcessingFee_ID', storageConferenceVersion.productProcessingFee, storageConferenceVersion.productProcessingFee);
    cy.writeIntoLookupListField('M_Product_Witholding_ID', storageConferenceVersion.productWitholding, storageConferenceVersion.productWitholding);
    cy.writeIntoLookupListField('M_Product_RegularPPOrder_ID', storageConferenceVersion.productRegularPPOrder, storageConferenceVersion.productRegularPPOrder);
    cy.writeIntoLookupListField('M_Product_Scrap_ID', storageConferenceVersion.productScrap, storageConferenceVersion.productScrap);
    cy.selectInListField('C_UOM_Scrap_ID', storageConferenceVersion.uomScrap);

    cy.writeIntoStringField('ValidFrom', storageConferenceVersion.validFrom, false, null, true);
    cy.writeIntoStringField('ValidTo', storageConferenceVersion.validTo, false, null, true);
    cy.writeIntoStringField('Percentage_Scrap_Treshhold', storageConferenceVersion.percentageScrapT);
    cy.writeIntoStringField('Scrap_Fee_Amt_Per_UOM', storageConferenceVersion.scrapFeeAmt);

    storageConferenceVersion.lines.forEach(line => {
      applyCostLine(line);
    });
    cy.expectNumberOfRows(storageConferenceVersion.lines.length);

    storageConferenceVersion.months.forEach(month => {
      applyLagerKonfMonth(month);
    });
    cy.expectNumberOfRows(storageConferenceVersion.months.length);

    storageConferenceVersion.contributions.forEach(contribution => {
      applyAdditionalContribution(contribution);
    });
    cy.expectNumberOfRows(storageConferenceVersion.contributions.length);
  });
}

function applyCostLine(costLine) {
  cy.selectTab('M_QualityInsp_LagerKonf_ProcessingFee');
  cy.pressAddNewButton();
  cy.writeIntoStringField('PercentFrom', costLine.percentFrom, false, null, true);
  cy.writeIntoStringField('Processing_Fee_Amt_Per_UOM', costLine.processingFeeAmtPerUOM, false, null, true);
  cy.selectInListField('C_UOM_ID', costLine.uomId);
  cy.pressDoneButton();
}

function applyLagerKonfMonth(lagerKonfMonth) {
  cy.selectTab('M_QualityInsp_LagerKonf_Month_Adj');
  cy.pressAddNewButton();
  cy.selectInListField('QualityAdjustmentMonth', lagerKonfMonth.month);
  cy.writeIntoStringField('QualityAdj_Amt_Per_UOM', lagerKonfMonth.adjAmount, false, null, true);
  cy.selectInListField('C_UOM_ID', lagerKonfMonth.uomId);
  cy.pressDoneButton();
}

function applyAdditionalContribution(contribution) {
  cy.selectTab('M_QualityInsp_LagerKonf_AdditionalFee');
  cy.pressAddNewButton();
  cy.writeIntoStringField('SeqNo', contribution.seqNo);
  cy.writeIntoLookupListField('M_Product_ID', contribution.product, contribution.product);
  cy.writeIntoStringField('Additional_Fee_Amt_Per_UOM', contribution.additionalFeeAmount);
  cy.pressDoneButton();
}

export class CostLine {
  setPercentFrom(percentFrom) {
    cy.log(`CostLine - set percentFrom = ${percentFrom}`);
    this.percentFrom = percentFrom;
    return this;
  }

  setProcessingFeeAmtPerUOM(processingFeeAmtPerUOM) {
    cy.log(`CostLine - set processingFeeAmtPerUOM = ${processingFeeAmtPerUOM}`);
    this.processingFeeAmtPerUOM = processingFeeAmtPerUOM;
    return this;
  }

  setCUOMID(uomId) {
    cy.log(`CostLine - set uomId = ${uomId}`);
    this.uomId = uomId;
    return this;
  }
}

export class LagerKonfMonth {
  setMonth(month) {
    cy.log(`LagerKonfMonth - set month = ${month}`);
    this.month = month;
    return this;
  }

  setQualityAdjAmt(adjAmount) {
    cy.log(`LagerKonfMonth - set adjAmount = ${adjAmount}`);
    this.adjAmount = adjAmount;
    return this;
  }

  setCUOMID(uomId) {
    cy.log(`LagerKonfMonth - set uomId = ${uomId}`);
    this.uomId = uomId;
    return this;
  }
}

export class AdditionalContribution {
  setSeqNo(seqNo) {
    cy.log(`AdditionalContribution - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setProduct(product) {
    cy.log(`AdditionalContribution - set product = ${product}`);
    this.product = product;
    return this;
  }

  setAdditionalFeeAmount(additionalFeeAmount) {
    cy.log(`AdditionalContribution - set additionalFeeAmount = ${additionalFeeAmount}`);
    this.additionalFeeAmount = additionalFeeAmount;
    return this;
  }
}
