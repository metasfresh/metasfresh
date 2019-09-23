import { DataEntrySubTab, DataEntryTab } from '../../support/utils/dataEntryTab';
import { DataEntryLine, DataEntrySection } from '../../support/utils/dataEntrySection';
import { DataEntryField, DataEntryListValue } from '../../support/utils/dataEntryField';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create bpartner with custom dataentry based tabs', function() {
  const date = humanReadableNow();
  const dataEntryTabName = `Group1 ${date}`;
  let dataEntryTabId;

  const dataEntrySubTab1Name = `SubGroup1-1 ${date}`;
  let dataEntrySubTab1Id;

  const dataEntrySection1Name = `Section1-1 ${date}`;
  const dataEntrySection2Name = `Section1-2 ${date}`;

  it('Create dataentry tab and subtab', function() {
    new DataEntryTab(dataEntryTabName, 'Business Partner')
      .setTabName('Group1-Tab1')
      .setSeqNo('21')
      .setDescription(`Description of ${dataEntryTabName}`)
      //.setActive(false) // you can set it to inactive, but then no subgroups can be added
      .addDataEntrySubTab(
        new DataEntrySubTab(dataEntrySubTab1Name)
          .setTabName('Group1-Tab1-SubTab1')
          .setDescription(`${dataEntrySubTab1Name} - Description`)
          .setSeqNo('11')
      )
      .apply();

    cy.getCurrentWindowRecordId().then(id => (dataEntryTabId = id));
    cy.get(`@${dataEntrySubTab1Name}`).then(dataEntrySubTab => {
      // noinspection JSUnresolvedVariable
      dataEntrySubTab1Id = dataEntrySubTab.documentId;
    });
  });

  it('Create dataentry section and lines', function() {
    new DataEntrySection(dataEntrySection1Name, dataEntrySubTab1Name)
      .setDescription('Section with 3 lines; in the 1st, just one col is used; in the 2nd, one field is long-text, yet the two fields of the 3rd line shall still be alligned!')
      .setSeqNo('15')
      .addDataEntryLine(new DataEntryLine().setSeqNo(11))
      .addDataEntryLine(new DataEntryLine().setSeqNo(22))
      .addDataEntryLine(new DataEntryLine().setSeqNo(33))
      .apply();
  });

  it('Create dataentry fields', function() {
    const section1FieldBuilder = new DataEntryField('Tab1-Section1-Line1-Field1', `${dataEntrySection1Name}_${dataEntryTabName}_${dataEntrySubTab1Name}_11`)
      .setDescription('Yes-No, single field in its line')
      .setMandatory(true)
      .setDataEntryRecordType('Yes-No')
      .setPersonalDataCategory('Personal')
      .setSeqNo('11');

    section1FieldBuilder.apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line2-Field2')
      .setDataEntryLine(`${dataEntrySection1Name}_${dataEntryTabName}_${dataEntrySubTab1Name}_22`)
      .setDescription('LongText, first field in its line')
      .setMandatory(false) // setting only the section's 1st field to be mandatory because right now, only the first field is actually displayed
      .setDataEntryRecordType('Long text')
      .setSeqNo('10')
      .apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line2-Field3')
      .setDescription('Yes-No, second fields in its line')
      .setDataEntryRecordType('Yes-No')
      .setSeqNo('20')
      .apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line3-Field4')
      .setDataEntryLine(`${dataEntrySection1Name}_${dataEntryTabName}_${dataEntrySubTab1Name}_33`)
      .setDescription('Text, first field in its line')
      .setDataEntryRecordType('Text')
      .setSeqNo('10')
      .apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line3-Field5')
      .setDescription('Text, second field in its line')
      .setSeqNo('20')
      .apply();
  });

  it('Create a 2nd dataentry section and fields', function() {
    new DataEntrySection(dataEntrySection2Name, dataEntrySubTab1Name)
      .setDescription('Section with one line; its two columns shall appear to be aligned with the first section')
      .setSeqNo('25')
      .addDataEntryLine(new DataEntryLine().setSeqNo('10'))
      .apply();

    new DataEntryField('Tab1-Section2-Line1-Field1', `${dataEntrySection2Name}_${dataEntryTabName}_${dataEntrySubTab1Name}_10`)
      .setDescription('Tab1-Section2-Field1 Description')
      .setMandatory(true)
      .setDataEntryRecordType('Date')
      .setSeqNo('10')
      .apply();

    new DataEntryField('Tab1-Section2-Line1-Field2', `${dataEntrySection2Name}_${dataEntryTabName}_${dataEntrySubTab1Name}_10`)
      .setDescription('Tab1-Section2-Field2 Description')
      .setMandatory(false) // setting only the section's 1st field to be mandatory because right now, only the first field is actually displayed
      .setDataEntryRecordType('List')
      .setSeqNo('22')
      .addDataEntryListValue(new DataEntryListValue('ListItem 2').setDescription('ListItem 2 with SeqNo10').setSeqNo('21'))
      .addDataEntryListValue(new DataEntryListValue('ListItem 1').setDescription('ListItem 1 with SeqNo20').setSeqNo('11'))
      .apply();
  });

  it('Create a bpartner and check the dataentry tab', function() {
    cy.visitWindow('123', 'NEW');
    cy.writeIntoStringField('CompanyName', `DataEntryBPartnerTestName ${date}`);

    cy.log(`going to open the tab for dataEntryTabId=${dataEntryTabId}`);
    cy.selectTab(`DataEntry_Tab_ID-${dataEntryTabId}`);

    cy.log(`going to open the tab for dataEntrySubTabId=${dataEntrySubTab1Id}`);
    cy.selectTab(`DataEntry_SubTab_ID-${dataEntrySubTab1Id}`);
  });

  it('Deactivate the dataentry tab (cleanup)', function() {
    // deactivate the custom tab, because we don't want other tests to unexpectedly have it among their respective bpartner-tabs
    cy.visitWindow('540571', dataEntryTabId);
    cy.clickOnIsActive();
  });
});
