import { DataEntryGroup, DataEntrySubGroup } from '../../support/utils/dataEntryGroup';
import { DataEntrySection, DataEntryLine } from '../../support/utils/dataEntrySection';
import { DataEntryField, DataEntryListValue } from '../../support/utils/dataEntryField';

describe('Create bpartner with custom dataentry based tabs', function() {
  before(function() {
    // login before each test and open the flatrate conditions window
    cy.loginByForm();
  });

  it('Create bpartner with custom dataentry based tabs', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const dataEntryGroupName = `Group1 ${timestamp}`;

    const dataEntrySubGroup1Name = `SubGroup1-1 ${timestamp}`;
    const dataEntrySection1Name = `Section1-1 ${timestamp}`;
    const dataEntrySection2Name = `Section1-2 ${timestamp}`;

    new DataEntryGroup(dataEntryGroupName, 'Business Partner')
      .setTabName('Group1-Tab1')
      //.setSeqNo(20)
      .setDescription(`Description of ${dataEntryGroupName}`)
      //.setActive(false) // you can set it to inactive, but then no subgroups can be added
      .addDataEntrySubGroup(
        new DataEntrySubGroup(dataEntrySubGroup1Name)
          .setTabName('Group1-Tab1-SubTab1')
          .setDescription(`${dataEntrySubGroup1Name} - Description`)
        //.setSeqNo('10')
      )
      .apply();

    new DataEntrySection(dataEntrySection1Name, dataEntrySubGroup1Name)
      .setDescription(
        'Section with 3 lines; in the 1st, just one col is used; in the 2nd, one field is long-text, yet the two fields of the 3rd line shall still be alligned!'
      )
      //.setSeqNo(15)
      .addDataEntryLine(
        new DataEntryLine()
        // .setSeqNo('10')
      )
      .addDataEntryLine(
        new DataEntryLine()
        // .setSeqNo('20')
      )
      .addDataEntryLine(
        new DataEntryLine()
        // .setSeqNo('30')
      )
      .apply();

    const section1FieldBuilder = new DataEntryField(
      'Tab1-Section1-Line1-Field1',
      `${dataEntrySection1Name}_${dataEntryGroupName}_${dataEntrySubGroup1Name}_10`
    )
      .setDescription('Yes-No, single field in its line')
      .setMandatory(true)
      .setDataEntryRecordType('Yes-No')
      .setPersonalDataCategory('Personal');
    // .setSeqNo('10');

    section1FieldBuilder.apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line2-Field2')
      .setDataEntryLine(`${dataEntrySection1Name}_${dataEntryGroupName}_${dataEntrySubGroup1Name}_20`)
      .setDescription('LongText, first field in its line')
      .setMandatory(false) // setting only the section's 1st field to be mandatory because right now, only the first field is actually displayed
      .setDataEntryRecordType('Long text')
      // .setSeqNo('00000000000000010')
      .apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line2-Field3')
      .setDescription('Yes-No, second fields in its line')
      .setDataEntryRecordType('Yes-No')
      // .setSeqNo('00000000000000020')
      .apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line3-Field4')
      .setDataEntryLine(`${dataEntrySection1Name}_${dataEntryGroupName}_${dataEntrySubGroup1Name}_30`)
      .setDescription('Text, first field in its line')
      .setDataEntryRecordType('Text')
      // .setSeqNo('00000000000000010')
      .apply();
    section1FieldBuilder
      .setName('Tab1-Section1-Line3-Field5')
      .setDescription('Text, second field in its line')
      // .setSeqNo('00000000000000020')
      .apply();

    new DataEntrySection(dataEntrySection2Name, dataEntrySubGroup1Name)
      .setDescription('Section with one line; its two columns shall appear to be aligned with the first section')
      // .setSeqNo(25)
      .addDataEntryLine(
        new DataEntryLine()
        // .setSeqNo('000000000000000000010')
      )
      .apply();

    new DataEntryField(
      'Tab1-Section2-Line1-Field1',
      `${dataEntrySection2Name}_${dataEntryGroupName}_${dataEntrySubGroup1Name}_10`
    )
      .setDescription('Tab1-Section2-Field1 Description')
      .setMandatory(true)
      .setDataEntryRecordType('Date')
      // .setSeqNo('000000000000000010')
      .apply();

    new DataEntryField(
      'Tab1-Section2-Line1-Field2',
      `${dataEntrySection2Name}_${dataEntryGroupName}_${dataEntrySubGroup1Name}_10`
    )
      .setDescription('Tab1-Section2-Field2 Description')
      .setMandatory(false) // setting only the section's 1st field to be mandatory because right now, only the first field is actually displayed
      .setDataEntryRecordType('List')
      // .setSeqNo('000000000000000020')
      .addDataEntryListValue(
        new DataEntryListValue('ListItem 2').setDescription('ListItem 2 with SeqNo10')
        // .setSeqNo('00000000000000020')
      )
      .addDataEntryListValue(
        new DataEntryListValue('ListItem 1').setDescription('ListItem 1 with SeqNo20')
        // .setSeqNo('00000000000000010')
      )
      .apply();

    cy.visitWindow('123', 'NEW');
    cy.writeIntoStringField('CompanyName', `DataEntryBPartnerTestName ${timestamp}`);

    cy.get(`@${dataEntryGroupName}`).then(dataEntryGroup => {
      cy.log(`going to open the tab for dataEntryGroup=${JSON.stringify(dataEntryGroup)}`);
      cy.selectTab(`DataEntry_Group_ID-${dataEntryGroup.documentId}`);
    });

    cy.get(`@${dataEntrySubGroup1Name}`).then(dataEntrySubGroup => {
      cy.log(`going to open the tab for dataEntrySubGroup=${JSON.stringify(dataEntrySubGroup)}`);
      cy.selectTab(`DataEntry_SubGroup_ID-${dataEntrySubGroup.documentId}`);
    });
  });
});
