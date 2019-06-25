describe('Introduce Window Tabs Internal Name #4491', function() {
  it('Open bpartner window', function() {
    cy.visit('/window/123');
    cy.get('.cell-text-wrapper.text-cell')
      .contains('Test Kunde 1')
      .dblclick();

    // it ('Open tab using internal name', function()
    cy.selectTab('C_BPartner_Location');
  });
});
