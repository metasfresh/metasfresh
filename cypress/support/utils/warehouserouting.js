/* eslint-disable no-undef */
import { getLanguageSpecific } from './utils';
export class WarehouseRouting {
  constructor(DocBaseType) {
    this.DocBaseType = DocBaseType;
  }

  setDocBaseType(DocBaseType) {
    cy.log(`WarehouseRouting - set DocBaseType = ${DocBaseType}`);
    this.DocBaseType = DocBaseType;
    return this;
  }

  apply() {
    cy.log(`Routing - apply - START (DocBaseType=${this.DocBaseType})`);
    applyRouting(this);
    cy.log(`Routing - apply - END (DocBaseType=${this.DocBaseType})`);
    return this;
  }
}
function applyRouting(Routing) {
  describe(`Create new Routing ${Routing.Routing}`, function() {
    cy.get(`#tab_M_Warehouse_Routing`).click();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Distribution Order', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Sales Order', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Purchase Order', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Material Receipt', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Manufacturing Order', true))
      .pressDoneButton();
  });
}
