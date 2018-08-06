import config from '../config';

describe('New sales order test', function() {
  const windowId = 143;
  let caption = '';
  let nodeId = null;
  let menuOption = '';

  before(function() {
    cy.loginByForm();

    cy.request('GET', config.API_URL+'/menu/elementPath?type=window&elementId='+windowId+'&inclusive=true')
      .then((response) => {
        expect(response.body).to.have.property('captionBreadcrumb');
        expect(response.body).to.have.property('nodeId');
        caption = response.body.captionBreadcrumb;
        nodeId = response.body.nodeId;

        cy.request('GET', config.API_URL+'/menu/node/'+nodeId+'/breadcrumbMenu')
          .then((response) => {
            const resp = response.body;
            expect(resp.length).to.be.gt(0);

            for (let i=0; i<resp.length; i+= 1) {
              if (resp[i].nodeId.includes('-new')) {
                menuOption = resp[i].caption;

                break;
              }
            }
        });
    });

    cy.visit('/window/'+windowId);
  });

  context('Create a new sales order', function() {
    before(function() {
      cy.get('.header-breadcrumb')
        .contains('.header-item', caption, { timeout: 10000 })
        .click();

      cy.get('.header-breadcrumb')
        .find('.menu-overlay')
        .should('exist')
        .find('.menu-overlay-link')
        .contains(menuOption)
        .click();

      cy.get('.header-breadcrumb-sitename').should('contain', '<');
    });

    it('Fill Business Partner', function() {
      cy.get('.input-dropdown-container .raw-lookup-wrapper')
        .first()
        .find('input')
        .clear()
        .type('G0002');

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', 'Test Lieferant 1').click();
      cy
        .get('.input-dropdown-list .input-dropdown-list-header')
        .should('not.exist');

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    });

    it('Fill order reference to differentiate cypress tests', function() {
      cy.get('.form-field-POReference')
        .find('input')
        .type(`Cypress Test ${new Date().getTime()}{enter}`);

      cy.get('.indicator-pending').should('exist');
      cy.wait(100);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new product', function() {
      const addNewText = Cypress.messages.window.addNew.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.panel-modal').should('exist');

      cy.get('.form-field-M_Product_ID')
        .find('input')
        .type('C');

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', 'P002737_Convenience Salat 250g')
        .click();
      cy.get('.input-dropdown-list .input-dropdown-list-header')
        .should('not.exist');

      cy.get('.form-field-QtyEntered', { timeout: 12000 })
        .find('input')
        .should('not.have.value', '0');

      cy.get('.panel-modal-header')
        .find('.btn')
        .click();
    });

    it('Change document status', function() {
      let completeActionCaption = '';
      const draftedCaption = Cypress.reduxStore.getState()
        .windowHandler.master.data.DocStatus.value.caption;
      const docId = Cypress.reduxStore.getState().windowHandler.master.docId;

      cy.request('GET', config.API_URL+'/window/'+windowId+'/'+docId+'/field/DocAction/dropdown')
        .then((response) => {
          const resp = response.body;

          expect(resp).to.have.property('values');
          expect(resp.values.length).to.be.gt(0);

          for (let i=0; i<resp.values.length; i+= 1) {
            if (resp.values[i].key === 'CO') {
              completeActionCaption = resp.values[i].caption;

              break;
            }
          }

          cy.get('.form-field-DocAction')
            .find('.meta-dropdown-toggle')
            .click();

          cy.get('.form-field-DocAction')
            .find('.dropdown-status-toggler')
            .should('have.class', 'dropdown-status-open');

          cy.get('.form-field-DocAction .dropdown-status-list')
            .find('.dropdown-status-item')
            .contains(completeActionCaption)
            .click();

          cy.get('.indicator-pending', { timeout: 10000 }).should('not.exist');
          cy.get('.meta-dropdown-toggle .tag-success').should('not.contain', draftedCaption);
        });
    });
  });
});
