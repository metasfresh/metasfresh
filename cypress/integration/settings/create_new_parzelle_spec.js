describe('Create new Parzelle/Allotment(a plot of land rented for growing vegetables or flowers.)', function() {
  let name;
  it('Read fixture and prepare the names', function() {
    cy.fixture('settings/create_new_parzelle_spec.json').then(f => {
      name = f['name'];
    });
  });
  it('Create new parzelle', function() {
    cy.visitWindow(540210, 'NEW');
    cy.writeIntoStringField('Value', name);
    cy.writeIntoStringField('Name', name);
    /*show newly created parzelle in the grid view*/
    cy.visitWindow(540210);
  });
});
