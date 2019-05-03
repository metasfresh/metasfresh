import { invoiceCandidates } from '../../page_objects/invoice_candidates';

describe('New sales order test', function() {
  describe('create an invoice', function() {
    it("Zoom to the sales order's invoice candidate", function() {
      cy.visit(`/window/${invoiceCandidates.windowId}?page=1&refId=1000002&refType=143&viewId=540092-G`);

      //invoiceCandidates.getRows().dosomething();

      // cy.get('Select all on this page').click(); // doesn't work

      cy.clickElementWithClass('.pagination-link.pointer');
      //cy.clickElementWithClass('pagination-link pointer');

      // const aliasName = `selectAll-${timestamp}`;
      // const patchUrlPattern = '/rest/api/documentView/.*/quickActions?selectedIds=all$';
      // cy.server();
      // cy.route('GET', new RegExp(patchUrlPattern)).as(aliasName);
      // cy.get('body').type('{alt}A'); // select all invoice candidates - doesn'T work
      // cy.wait(`@${aliasName}`);

      //cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
    });
  });
});
