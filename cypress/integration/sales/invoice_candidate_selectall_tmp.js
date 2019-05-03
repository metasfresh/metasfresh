import { invoiceCandidates } from '../../page_objects/invoice_candidates';

describe('New sales order test', function() {
  const timestamp = new Date().getTime();

  describe('create an invoice', function() {
    it("Zoom to the sales order's invoice candidate", function() {
      cy.server();

      //cy.visit(`/window/${invoiceCandidates.windowId}?page=1&refId=1000002&refType=143&viewId=540092-G`);

      cy.visitWindow(143, 1000008);
      cy.get('body').type('{alt}6'); // open referenced-records-sidelist

      // zoom to the order's invoice candide(s), but also make sure to wait until the data is available
      const getDataAlias = `data-${timestamp}`;
      cy.route('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*?firstRow=0&pageLength=*`).as(
        getDataAlias
      );
      cy.selectReference('C_Order_C_Invoice_Candidate').click();
      cy.wait(`@${getDataAlias}`);

      // select all invoice candidates and wait for the list of available quick action to be updated
      cy.route('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*/quickActions?selectedIds=all`).as(
        'selectAll'
      );
      cy.clickElementWithClass('.pagination-link.pointer');
      cy.wait('@selectAll');

      // and *now* execute the invoiceing action
      cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
      cy.writeIntoStringField(
        'POReference',
        `Invoice -${timestamp}`,
        true /*modal*/,
        '/rest/api/process' /*rewriteUrl*/
      );
      cy.pressStartButton();
    });
  });
});
