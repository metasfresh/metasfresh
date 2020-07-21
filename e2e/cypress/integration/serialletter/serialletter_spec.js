import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Serial letter tests', function() {
  let printFormatName;
  let marketingPlatformName;
  let boilerPlateName;
  let marketingCampaignName;
  let marketingContactName;
  let boilerPlateSubject;
  let boilerPlateTextSnippet;
  let printFormatLetter;
  let printFormatSerialLetter;

  it('Read the fixture', function() {
    cy.fixture('serialletter/serialletter_spec.json').then(f => {
      printFormatName = appendHumanReadableNow(f['printFormatName']);
      marketingPlatformName = appendHumanReadableNow(f['marketingPlatformName']);
      boilerPlateName = appendHumanReadableNow(f['boilerPlateName']);
      marketingCampaignName = appendHumanReadableNow(f['marketingCampaignName']);
      marketingContactName = appendHumanReadableNow(f['marketingContactName']);

      boilerPlateSubject = f['boilerPlateSubject'];
      boilerPlateTextSnippet = f['boilerPlateTextSnippet'];
      printFormatLetter = f['printFormatLetter'];
      printFormatSerialLetter = f['printFormatSerialLetter'];
    });
  });

  it('Create print format', function() {
    cy.visitWindow('240', 'NEW');

    cy.writeIntoStringField('Name', printFormatName);
    cy.writeIntoLookupListField('AD_Table_ID', printFormatLetter, printFormatLetter);
    cy.writeIntoLookupListField('JasperProcess_ID', printFormatSerialLetter, printFormatSerialLetter);
    cy.url().should('not.contain', 'NEW');
    cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
  });

  it('Create outbound document', function() {
    cy.visitWindow('540173', 'NEW');
    cy.writeIntoLookupListField('AD_Table_ID', printFormatLetter, printFormatLetter);
    cy.writeIntoLookupListField('AD_PrintFormat_ID', printFormatName, printFormatName);
    // cy.clickOnCheckBox('IsCreatePrintJob');
  });

  it('creates Marketing platform', function() {
    cy.visitWindow('540437', 'NEW');
    cy.writeIntoStringField('Name', marketingPlatformName);
    cy.clickOnCheckBox('IsRequiredLocation');
  });

  it('Creates boiler plate (text-snippet)', function() {
    cy.visitWindow('504410', 'NEW');
    cy.writeIntoStringField('Name', boilerPlateName);
    cy.writeIntoStringField('Subject', boilerPlateSubject);
    cy.writeIntoTextField('TextSnippet', boilerPlateTextSnippet);
    cy.selectTab('AD_BoilerPlate_Ref'); // need to click somewhere outside of the text field
  });

  it('Creates contact person', function() {
    cy.visitWindow('540435', 'NEW');
    cy.writeIntoStringField('Name', marketingContactName);
    cy.writeIntoLookupListField('MKTG_Platform_ID', marketingPlatformName, marketingPlatformName);
    cy.editAddress('C_Location_ID', function(url) {
      cy.writeIntoStringField('City', 'Cologne', null, url);
      cy.writeIntoLookupListField('C_Country_ID', 'Deu', 'Deutschland', false /*typeList */, false /*modal */, url);
    });
  });

  it('Create marketing campaign', function() {
    cy.visitWindow('540434', 'NEW');
    cy.writeIntoStringField('Name', marketingCampaignName);
    cy.writeIntoLookupListField('MKTG_Platform_ID', marketingPlatformName, marketingPlatformName);
    cy.writeIntoLookupListField('AD_BoilerPlate_ID', boilerPlateName, boilerPlateName);
  });

  it('add contact person to campaign', function() {
    cy.selectTab('MKTG_Campaign_ContactPerson');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('MKTG_ContactPerson_ID', marketingContactName, marketingContactName, false /*typeList*/, true /*modal*/);
    cy.pressDoneButton();

    // needs https://github.com/metasfresh/metasfresh-webui-frontend/issues/1978#issuecomment-445274540
    cy.executeHeaderAction('C_Letter_CreateFrom_MKTG_ContactPerson');
  });
});
