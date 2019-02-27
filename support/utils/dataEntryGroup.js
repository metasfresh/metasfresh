export class DataEntryGroup 
{
    constructor(builder) 
    {
        this.name = builder.name;
        this.tabName = builder.tabName;
        this.seqNo = builder.seqNo;
        this.targetWindowName = builder.targetWindowName;
        this.description = builder.description;
        this.isActive = builder.isActive;
        this.dataEntrySubGroups = builder.dataEntrySubGroups;
        this.dataEntrySections = builder.dataEntrySections;
    }

    apply() 
    {
        cy.log(`DataEntryGroup - apply - START (name=${this.name})`);
        applyDataEntryGroup(this);
        cy.log(`DataEntryGroup - apply - END (name=${this.name})`);
        return this;
    }

    static get builder() 
    {
        class Builder 
        {
            constructor(name, targetWindowName) 
            {
              cy.log(`DataEntryGroupBuilder - set name = ${name}; targetWindowName = ${targetWindowName}`);
              this.name = name;
              this.tabName = name;
              this.targetWindowName = targetWindowName;
              this.seqNo = undefined;
              this.description = undefined;
              this.isActive = true;
              this.dataEntrySubGroups = [];
              this.dataEntrySections = [];
            }

            setTabName(tabName)
            {
               cy.log(`DataEntryGroupBuilder - set tabName = ${tabName}`);
               this.tabName = tabName;
               return this;
            }
            setSeqNo(seqNo)
            {
               cy.log(`DataEntryGroupBuilder - set seqNo = ${seqNo}`);
               this.seqNo = seqNo;
               return this;
            }
            setDescription(description)
            {
               cy.log(`DataEntryGroupBuilder - set description = ${description}`);
               this.description = description;
               return this;
            }
            setActive(isActive)
            {
                cy.log(`DataEntryGroupBuilder - set isActive = ${isActive}`);
                this.isActive = isActive;
                return this;
            }

            addDataEntrySubGroup(dataEntrySubGroup) 
            {
                cy.log(`DataEntryGroupBuilder - add dataEntrySubGroup = ${JSON.stringify(dataEntrySubGroup)}`);
                this.dataEntrySubGroups.push(dataEntrySubGroup);
                return this;
            }
            addDataEntrySection(dataEntrySection) 
            {
                cy.log(`DataEntryGroupBuilder - add dataEntrySection = ${JSON.stringify(dataEntrySection)}`);
                this.dataEntrySections.push(dataEntrySection);
                return this;
            }

            build() 
            {
              return new DataEntryGroup(this);
            }
        }
        return Builder;
    }

}

export class DataEntrySubGroup 
{
    constructor(builder) 
    {
        this.name = builder.name;
        this.tabName = builder.tabName;
        this.seqNo = builder.seqNo;
        this.description = builder.description;
        this.isActive = builder.isActive;
    }

    static get builder() 
    {
        class Builder 
        {
            constructor(name) 
            {
                cy.log(`DataEntrySubGroupBuilder - set name = ${name}`);
                this.name = name;
                this.tabName = name;
                this.seqNo = undefined;
                this.description = undefined;
                this.isActive = true;
            } 
            setTabName(tabName)
            {
               cy.log(`DataEntrySubGroupBuilder - set tabName = ${tabName}`);
               this.tabName = tabName;
               return this;
            }
            setSeqNo(seqNo)
            {
               cy.log(`DataEntrySubGroupBuilder - set seqNo = ${seqNo}`);
               this.seqNo = seqNo;
               return this;
            }
            setDescription(description)
            {
                cy.log(`DataEntrySubGroupBuilder - set description = ${description}`);
                this.description = description;
                return this;
            }
            setActive(isActive)
            {
                cy.log(`DataEntrySubGroupBuilder - set isActive = ${isActive}`);
                this.isActive = isActive;
                return this;
            }

            build() 
            {
                return new DataEntrySubGroup(this);
            }
        }
        return Builder;
    }
}

export class DataEntrySection
{
    constructor(builder) 
    {
        this.name = builder.name;
        this.sectionName = builder.sectionName;
        this.seqNo = builder.seqNo;
        this.description = builder.description;
        this.isActive = builder.isActive;
    }

    static get builder() 
    {
        class Builder 
        {
            constructor(name) 
            {
                cy.log(`DataEntrySectionBuilder - set name = ${name}`);
                this.name = name;
                this.sectionName = name;
                this.seqNo = undefined
                this.description = undefined
                this.isActive = true
            } 

            setSectionName(sectionName)
            {
                cy.log(`DataEntrySectionBuilder - set sectionName = ${sectionName}`);
                this.sectionName = sectionName;
                return this;
            }
            setSeqNo(seqNo)
            {
               cy.log(`DataEntrySectionBuilder - set seqNo = ${seqNo}`);
               this.seqNo = seqNo;
               return this;
            }
            setDescription(description)
            {
                cy.log(`DataEntrySectionBuilder - set description = ${description}`);
                this.description = description;
                return this;
            }
            setActive(isActive)
            {
                cy.log(`DataEntrySectionBuilder - set isActive = ${isActive}`);
                this.isActive = isActive;
                return this;
            }

            build() 
            {
                return new DataEntrySection(this);
            }
        }
        return Builder;
    }
}

function applyDataEntryGroup(dataEntryGroup)
{
    describe(`Create new dataEntryGroup ${dataEntryGroup.name}`, function () {

        cy.visitWindow('540571', 'NEW')

        cy.writeIntoStringField('Name', dataEntryGroup.name);
        cy.writeIntoStringField('TabName', dataEntryGroup.tabName);
        cy.writeIntoLookupListField('DataEntry_TargetWindow_ID', dataEntryGroup.targetWindowName, dataEntryGroup.targetWindowName)
        if(dataEntryGroup.seqNo) {
            cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntryGroup.seqNo}`);
        }
        if(dataEntryGroup.description) {
           cy.writeIntoTextField('Description', dataEntryGroup.description);
        }
        if(!dataEntryGroup.isActive) {
            cy.clickOnIsActive(true/*modal*/);
        }

        // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
        if(dataEntryGroup.dataEntrySubGroups.length > 0)
        {
            dataEntryGroup.dataEntrySubGroups.forEach(function (dataEntrySubGroup) {
                applyDataEntrySubGroup(dataEntrySubGroup);
            });
            cy.get('table tbody tr')
                .should('have.length', dataEntryGroup.dataEntrySubGroups.length);
        }
        if(dataEntryGroup.dataEntrySections.length > 0)
        {
            dataEntryGroup.dataEntrySections.forEach(function (dataEntrySection) {
                applyDataEntrySection(dataEntrySection);
            });
            cy.get('table tbody tr')
                .should('have.length', dataEntryGroup.dataEntrySections.length);
        }
    });
}

function applyDataEntrySubGroup(dataEntrySubGroup)
{
    cy.selectTab('DataEntry_SubGroup');
    cy.pressAddNewButton();

    cy.writeIntoStringField('Name', dataEntrySubGroup.name, true/*modal*/);
    cy.writeIntoStringField('TabName', dataEntrySubGroup.tabName, true/*modal*/);
    if(dataEntrySubGroup.seqNo) {
        cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntrySubGroup.seqNo}`, true/*modal*/);
    }
    if(dataEntrySubGroup.description) {
        cy.writeIntoTextField('Description', dataEntrySubGroup.description, true/*modal*/);
    }
    if(!dataEntrySubGroup.isActive) {
        cy.clickOnIsActive(true/*modal*/);
    }

    cy.pressDoneButton();  
}

function applyDataEntrySection(dataEntrySection)
{
    cy.selectTab('DataEntry_Section');
    cy.pressAddNewButton();

    cy.writeIntoStringField('Name', dataEntrySection.name, true/*modal*/);
    cy.writeIntoStringField('SectionName', dataEntrySection.sectionName);
    if(dataEntrySection.seqNo) {
        cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntrySection.seqNo}`, true/*modal*/);
    }
    if(dataEntrySection.description) {
        cy.writeIntoTextField('Description', dataEntrySection.description, true/*modal*/);
    }
    if(!dataEntrySection.isActive) {
        cy.clickOnIsActive(true/*modal*/);
    }

    cy.pressDoneButton();
}
