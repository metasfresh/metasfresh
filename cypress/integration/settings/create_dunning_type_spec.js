import { DunningType } from '../../support/utils/dunning_type';

describe('create dunning type', function() {
  // if creating more than 1 default dunning type will give an error
  // const timestamp = new Date().getTime();
  // const dunningTypeName = `dunning test ${timestamp}`;

  // before(function() {
  //   cy.fixture('settings/dunning_type.json').then(dunningType => {
  //     Object.assign(new DunningType(), dunningType)
  //       .setName(dunningTypeName)
  //       // .setCheckDefault(dunningType.default)
  //       .apply();
  //   });
  // });

  it('operations on BP', function() {
    cy.visit('/window/123');

    cy.get(':nth-child(1) > .text-left.td-sm').dblclick();
    cy.selectTab('Customer');
    cy.get('.tr-even > :nth-child(3)')
      .dblclick()
      .selectInListField('C_PaymentTerm_ID', 'immediately', false, null, true);
    cy.selectTab('Customer');
    cy.get('.tab-pane > :nth-child(1) > :nth-child(1) > :nth-child(1) > .panel').scrollTo('right');
    cy.get('.tr-even > :nth-child(9)')
      .dblclick()
      .selectInListField('C_Dunning_ID', 'test Dunning', false, null, true);
  });
});
