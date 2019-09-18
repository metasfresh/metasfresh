/**
 * Why do we have this test?
 *
 * It's a single test of the customer's test list; they have other test including Handling Unit Editor as well,
 * but they also have this where the whole test is just opening the Handling Unit Editor window (and checking how long it takes until the window is open)
 *
 * so, i would like to keep this test in our list; if the customer says they don't need it  bc it's covered in other tests, fine, then we can get rid of it
 *
 * todo: add an upper limit for how long the window is supposed to take to load
 */

describe('Create test: visit handling unit editor window, https://github.com/metasfresh/metasfresh-e2e/issues/125', function() {
  it('visit handling unit editor window', function() {
    cy.visitWindow('540189');
  });
});
