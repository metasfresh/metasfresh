export class PaymentTerm {

    getNameAndValue(){
        const timestamp = new Date().getTime(); // used in the document names, for ordering
        this.paymenttermName = `ListPaymentTermName ${timestamp}`;
        this.paymenttermValue = `ListPaymentTermValue ${timestamp}`;
        return this;
    }

    setNetDays(netDays){
        cy.log(`Paymentterm - set net days = ${netDays}`);
        this.netDays = netDays;
        return this;
    }

    setGraceDays(graceDays){
        cy.log(`Paymentterm - set grace days = ${graceDays}`);
        this.graceDays = graceDays;
        return this;
    }
    
    fillField(field, content){
        cy.clearField(field);
        cy.writeIntoStringField(field, content);
        
    }

    apply(){
        cy.visitWindow('141','NEW');
        this.fillField('Name',  this.paymenttermName );
        this.fillField('Value', this.paymenttermValue );
        this.fillField('NetDays',  this.netDays  );
        this.fillField('GraceDays',  this.graceDays );
        cy.getCheckboxValue('IsValid').then(isValidValue => {
            expect(isValidValue).to.equal(true);
            })
        return this;
    }

}
