
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
        cy.log(`DiscountSchema - apply - START (poReference=${this.poReference})`);
        applyPurchaseOrder(this);
        cy.log(`DiscountSchema - apply - END (poReference=${this.poReference})`);
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
          
            setBPartner(bPartner)
            {
               cy.log(`PurchaseOrderBuilder - setBPartner = ${bPartner}`);
               this.bPartner = bPartner;
               return this;
            }
            setPoReference(poReference)
            {
               cy.log(`PurchaseOrderBuilder - setPoReference = ${poReference}`);
               this.poReference = poReference;
               return this;
            }
            setDropShip(isDropShip)
            {
               cy.log(`PurchaseOrderBuilder - setDropShip = ${isDropShip}`);
               this.isDropShip = isDropShip;
               return this;
            }

            setDocAction(docAction)
            {
               cy.log(`PurchaseOrderBuilder - setDocAction = ${docAction}`);
               this.docAction = docAction;
               return this;
            }
            setDocStatus(docStatus)
            {
               cy.log(`PurchaseOrderBuilder - setDocStatus = ${docStatus}`);
               this.docStatus = docStatus;
               return this;
            }
        
            addLine(purchaseOrderLine) 
            {
                cy.log(`PurchaseOrderBuilder - addLine = ${JSON.stringify(purchaseOrderLine)}`);
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

            setProduct(product)
            {
                cy.log(`PurchaseOrderLineBuilder - setProduct = ${product}`);
                this.product = product;
                return this;
            }
            setTuQuantity(tuQuantity) 
            {
                cy.log(`PurchaseOrderLineBuilder - setTuQuantity = ${tuQuantity}`);
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
        cy.wait(500)

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
                applyPurchaseOrderLineAddNew(purchaseOrderLine);
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

function applyPurchaseOrderLineAddNew(purchaseOrderLine)
{
    cy.selectTab('C_OrderLine')
    cy.pressAddNewButton()
  
    cy.writeIntoLookupListField('M_Product_ID', purchaseOrderLine.product, purchaseOrderLine.product)
    //cy.wait('@patchAddress');
    //cy.get('#lookup_M_HU_PI_Item_Product_ID input').should('have.value', 'IFCO 6410 x 10 Stk');
    //cy.writeIntoLookupListField('M_HU_PI_Item_Product_ID', 'IFCO 6410 x 10 Stk');
    cy.writeIntoStringField('QtyEnteredTU', purchaseOrderLine.tuQuantity)
    cy.wait(500)
    cy.pressDoneButton()
    //});
}

// doesn't work
// function applyPurchaseOrderLineBatchEntry(purchaseOrderLine)
// {
//     cy.selectTab('C_OrderLine');
//     cy.pressBatchEntryButton();

//     //cy.wait('@postAddress').then(xhr => {
//         //const requestId = xhr.response.body.id;

//         //cy.route('PATCH', `/rest/api/window/143/1000489/187/quickInput/${requestId}`).as('patchAddress');
//         cy.writeIntoLookupListField('M_Product_ID', purchaseOrderLine.product, purchaseOrderLine.product);
//         //cy.wait('@patchAddress');
//         //cy.get('#lookup_M_HU_PI_Item_Product_ID input').should('have.value', 'IFCO 6410 x 10 Stk');
//         cy.writeIntoStringField('Qty', purchaseOrderLine.tuQuantity).type('{enter}');
//     //});
// }