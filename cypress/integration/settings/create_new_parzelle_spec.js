describe('Create new Parzelle/Allotment', function() {
  let name;
  it('Read fixture and prepare the names', function() {
    cy.fixture('settings/create_new_parzelle_spec.json').then(f => {
      name = f['name'];
    });
  });
  it('Open new parzelle', function() {
    cy.visit('/window/540210');
    cy.clickHeaderNav('new');
  });

  it('Create it', function() {
    cy.writeIntoStringField('Value', name);
    cy.writeIntoStringField('Name', name);
    cy.get(':nth-child(4) > .header-btn > .header-item-container > .header-item').click();
  });
});
