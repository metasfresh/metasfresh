describe('cypress pressDoneButton shall fail if the respective record was not saved #2083 - https://github.com/metasfresh/metasfresh-webui-frontend/issues/2083',  function() {
  it('Open bpartner window', function() {
    cy.visit('/window/123');
    cy.get('.cell-text-wrapper.text-cell')
      .contains('Test Kunde 1')
      .dblclick()

     // it ('cypress pressDoneButton shall fail if the respective record was not saved', function() 
     cy.selectTab('C_BPartner_Location');
      cy.pressAddNewButton();
      cy.writeIntoStringField('GLN', 'xxx');
    
      cy.pressDoneButton();
      
    });
  });