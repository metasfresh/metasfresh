export class DataEntryField 
{
    constructor(builder) 
    {
        this.name = builder.name
        this.seqNo = builder.seqNo
        this.description = builder.description
        this.isActive = builder.isActive
        this.dataEntryLine = builder.dataEntryLine
        this.dataEntryRecordType = builder.dataEntryRecordType
        this.isMandatory = builder.isMandatory
        this.personalDataCategory = builder.personalDataCategory
        this.dataEntryListValues = builder.dataEntryListValues
    }

    apply() 
    {
        cy.log(`DataEntryField - apply - START (name=${this.name})`)
        applyDataEntryField(this)
        cy.log(`DataEntryField - apply - END (name=${this.name})`)
        return this
    }

    static get builder() 
    {
        class Builder 
        {
            constructor(name, dataEntryLine) 
            {
                cy.log(`DataEntryFieldBuilder - set name = ${name}; dataEntryLine = ${dataEntryLine}`);
                this.name = name
                this.dataEntryLine = dataEntryLine
                this.seqNo = undefined
                this.description = undefined
                this.isActive = true
                this.dataEntryRecordType = undefined
                this.isMandatory = undefined
                this.personalDataCategory = undefined
                this.dataEntryListValues = []
            }

            setName(name)
            {
               cy.log(`DataEntryFieldBuilder - set name = ${name}`);
               this.name = name;
               return this;
            }
            setDataEntryLine(dataEntryLine)
            {
               cy.log(`DataEntryFieldBuilder - set dataEntryLine = ${dataEntryLine}`);
               this.dataEntryLine = dataEntryLine;
               return this;
            }
            setSeqNo(seqNo)
            {
               cy.log(`DataEntryFieldBuilder - set seqNo = ${seqNo}`);
               this.seqNo = seqNo;
               return this;
            }
            setDescription(description)
            {
               cy.log(`DataEntryFieldBuilder - set description = ${description}`);
               this.description = description;
               return this;
            }
            setActive(isActive)
            {
                cy.log(`DataEntryFieldBuilder - set isActive = ${isActive}`);
                this.isActive = isActive;
                return this;
            }
            setDataEntryRecordType(dataEntryRecordType)
            {
                cy.log(`DataEntryFieldBuilder - set dataEntryRecordType = ${dataEntryRecordType}`);
                this.dataEntryRecordType = dataEntryRecordType;
                return this;
            }
            setMandatory(isMandatory)
            {
                cy.log(`DataEntryFieldBuilder - set isMandatory = ${isMandatory}`);
                this.isMandatory = isMandatory;
                return this;
            }
            setPersonalDataCategory(personalDataCategory)
            {
                cy.log(`DataEntryFieldBuilder - set personalDataCategory = ${personalDataCategory}`);
                this.personalDataCategory = personalDataCategory;
                return this;
            }

            addDataEntryListValue(dataEntryListValue) 
            {
                cy.log(`DataEntryFieldBuilder - add dataEntryListValue = ${JSON.stringify(dataEntryListValue)}`);
                this.dataEntryListValues.push(dataEntryListValue);
                return this;
            }

            build() 
            {
              return new DataEntryField(this);
            }
        }
        return Builder;
    }

}

export class DataEntryListValue 
{
    constructor(builder) 
    {
        this.name = builder.name;
        this.description = builder.description;
        this.seqNo = builder.seqNo;
        this.isActive = builder.isActive;
    }

    static get builder() 
    {
        class Builder 
        {
            constructor(name) 
            {
                cy.log(`DataEntryListValueBuilder - set name = ${name}`);
                this.name = name;
                this.seqNo = undefined;
                this.description = undefined;
                this.isActive = true;
            } 
            setSeqNo(seqNo)
            {
               cy.log(`DataEntryListValueBuilder - set seqNo = ${seqNo}`);
               this.seqNo = seqNo;
               return this;
            }
            setDescription(description)
            {
                cy.log(`DataEntryListValueBuilder - set description = ${description}`);
                this.description = description;
                return this;
            }
            setActive(isActive)
            {
                cy.log(`DataEntryListValueBuilder - set isActive = ${isActive}`);
                this.isActive = isActive;
                return this;
            }

            build() 
            {
                return new DataEntryListValue(this);
            }
        }
        return Builder;
    }
}


function applyDataEntryField(dataEntryField)
{
    describe(`Create new dataEntryField ${dataEntryField.name}`, function () {

        cy.visitWindow('540572', 'NEW')
       
        cy.writeIntoStringField('Name', dataEntryField.name);
        cy.writeIntoLookupListField('DataEntry_Line_ID', dataEntryField.dataEntryLine, dataEntryField.dataEntryLine)

        if(dataEntryField.seqNo) {
            cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntryField.seqNo}`)
        }
        if(dataEntryField.description) {
           cy.writeIntoTextField('Description', dataEntryField.description)
        }
        if(!dataEntryField.isActive) {
            cy.clickOnIsActive()
        }
        if(dataEntryField.isMandatory) {
            cy.clickOnCheckBox('IsMandatory')
        }
        if(dataEntryField.personalDataCategory) {
            cy.selectInListField('PersonalDataCategory', dataEntryField.personalDataCategory)
        }
        if(dataEntryField.dataEntryRecordType) {
            cy.selectInListField('DataEntry_RecordType', dataEntryField.dataEntryRecordType)
        }
        
        // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
        if(dataEntryField.dataEntryListValues.length > 0)
        {
            dataEntryField.dataEntryListValues.forEach(function (dataEntryListValue) {
                applyDataEntryListValue(dataEntryListValue);
            });
            cy.get('table tbody tr')
                .should('have.length', dataEntryField.dataEntryListValues.length);
        }
    });
}

function applyDataEntryListValue(dataEntryListValue)
{
    cy.selectTab('DataEntry_ListValue');
    cy.pressAddNewButton();

    cy.writeIntoStringField('Name', dataEntryListValue.name, true/*modal*/);
    if(dataEntryListValue.seqNo) {
        cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntryListValue.seqNo}`, true/*modal*/);
    }
    if(dataEntryListValue.description) {
        cy.writeIntoTextField('Description', dataEntryListValue.description, true/*modal*/);
    }
    if(!dataEntryListValue.isActive) {
        cy.clickOnIsActive(true/*modal*/);
    }

    cy.pressDoneButton();  
}
