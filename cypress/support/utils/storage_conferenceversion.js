export class StorageConferenceVersion {
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
    cy.selectInListField('M_QualityInsp_LagerKonf_ID', storageConferenceVersion.lagerKonferenz);
    cy.writeIntoLookupListField('M_Product_ProcessingFee_ID', storageConferenceVersion.productProcessingFee, storageConferenceVersion.productProcessingFee);
    cy.writeIntoLookupListField('M_Product_Witholding_ID', storageConferenceVersion.productWitholding, storageConferenceVersion.productWitholding);
    cy.writeIntoLookupListField('M_Product_RegularPPOrder_ID', storageConferenceVersion.productRegularPPOrder, storageConferenceVersion.productRegularPPOrder);
    cy.writeIntoLookupListField('M_Product_Scrap_ID', storageConferenceVersion.productScrap, storageConferenceVersion.productScrap);
    cy.selectInListField('C_UOM_Scrap_ID', storageConferenceVersion.uomScrap);

    cy.writeIntoStringField('ValidFrom', storageConferenceVersion.validFrom, false, null, true);
    cy.writeIntoStringField('ValidTo', storageConferenceVersion.validTo, false, null, true);
    cy.writeIntoStringField('Percentage_Scrap_Treshhold', storageConferenceVersion.percentageScrapT);
    cy.writeIntoStringField('Scrap_Fee_Amt_Per_UOM', storageConferenceVersion.scrapFeeAmt);
  });
}
