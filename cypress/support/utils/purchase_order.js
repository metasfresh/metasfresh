
export class PurchaseOrder 
{
    constructor(builder) 
    {
        this.bPartner = builder.bPartner;
        this.poReference = builder.poReference;
        this.isDropShip = builder.isDropShip
        this.docAction = builder.docAction;
        this.docStatus = builder.docStatus;
        this.lines = builder.purchaseOrderLines;
    }

    apply() 
    {
        applyPurchaseOrder(this);
        return this;
    }

    static get builder() 
    {
        class Builder 
        {
            constructor() 
            {
              this.purchaseOrderLines = [];
            } 
          
            bPartner(bPartner)
            {
               cy.log(`PurchaseOrderBuilder - bPartner = ${bPartner}`);
               this.bPartner = bPartner;
               return this;
            }
            poReference(poReference)
            {
               cy.log(`PurchaseOrderBuilder - poReference = ${poReference}`);
               this.poReference = poReference;
               return this;
            }
            dropShip(isDropShip)
            {
               cy.log(`PurchaseOrderBuilder - dropShip = ${isDropShip}`);
               this.isDropShip = isDropShip;
               return this;
            }

            docAction(docAction)
            {
               cy.log(`PurchaseOrderBuilder - docAction = ${docAction}`);
               this.docAction = docAction;
               return this;
            }
            docStatus(docStatus)
            {
               cy.log(`PurchaseOrderBuilder - docStatus = ${docStatus}`);
               this.docStatus = docStatus;
               return this;
            }
        
            line(purchaseOrderLine) 
            {
                cy.log(`PurchaseOrderBuilder - add line = ${JSON.stringify(purchaseOrderLine)}`);
                this.purchaseOrderLines.push(purchaseOrderLine);
                return this;
            }

            build() 
            {
              return new PurchaseOrder(this);
            }
        }
        return Builder;
    }

}

export class PurchaseOrderLine 
{
    constructor(builder) 
    {
        this.product = builder.product;
        this.tuQuantity = builder.tuQuantity;
    }

    static get builder() 
    {
        class Builder 
        {
            constructor() 
            {
            } 

            product(product)
            {
                cy.log(`PurchaseOrderLineBuilder - product = ${product}`);
                this.product = product;
                return this;
            }
            tuQuantity(tuQuantity) 
            {
                cy.log(`PurchaseOrderLineBuilder - tuQuantity = ${tuQuantity}`);
                this.tuQuantity = tuQuantity;
                return this;
            }

            build() 
            {
                return new PurchaseOrderLine(this);
            }
        }
        return Builder;
    }
}


function applyPurchaseOrder(purchaseOrder)
{
    describe(`Create new purchaseOrder`, function () {

        cy.visit('/window/181/NEW');

        cy.writeIntoLookupListField('C_BPartner_ID', purchaseOrder.bPartner, purchaseOrder.bPartner);
        cy.writeIntoStringField('POReference', purchaseOrder.poReference);
       
        if(purchaseOrder.isDropShip)
        {
            cy.clickOnCheckBox('IsDropShip');
        }

        // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
        if(purchaseOrder.lines.length > 0)
        {
            purchaseOrder.lines.forEach(function (purchaseOrderLine) {
                applyPurchaseOrderLine(purchaseOrderLine);
            });
            cy.get('table tbody tr')
                .should('have.length', purchaseOrder.lines.length);
        }

        if(purchaseOrder.docAction)
        {
            if(purchaseOrder.docStatus)
            {
                cy.processDocument(purchaseOrder.docAction, purchaseOrder.docStatus);
            }
            else
            {
                cy.processDocument(purchaseOrder.docAction);
            }
        }
    });
}

function applyPurchaseOrderLine(purchaseOrderLine)
{
    cy.selectTab('C_OrderLine');
    cy.pressBatchEntryButton();

    cy.wait('@postAddress').then(xhr => {
        //const requestId = xhr.response.body.id;

        //cy.route('PATCH', `/rest/api/window/143/1000489/187/quickInput/${requestId}`).as('patchAddress');
        cy.writeIntoLookupListField('M_Product_ID', purchaseOrderLine.product, purchaseOrderLine.product);
        //cy.wait('@patchAddress');
        cy.get('#lookup_M_HU_PI_Item_Product_ID input').should('have.value', 'IFCO 6410 x 10 Stk');
        cy.writeIntoStringField('Qty', purchaseOrderLine.tuQuantity).type('{enter}');

        cy.get('.table-flex-wrapper')
          .find('tbody tr')
          .should('have.length', 3);
      });
}